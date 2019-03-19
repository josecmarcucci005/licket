package org.licket.spring.web;

import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.vue.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Configuration
@EnableWebSecurity
public class LicketSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractLicketComponent.class);
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        Set<BeanDefinition> beans = getSpringComponents(LicketAuthentication.class);
        
        if (beans.isEmpty()) {
            LOGGER.debug("No Security was found in the app.... ");
            return;
        }
        if (beans.size() > 1) {
            LOGGER.warn("The LicketAuthentication annotation appears in more than one place, " +
                    "only the first entry will be consider which is define in the class: {}", 
                    (beans.toArray(new BeanDefinition[beans.toArray().length])[0]).getBeanClassName());
        }

        LicketAuthentication authenAnnotation = Class.forName((beans.
                toArray(new BeanDefinition[beans.toArray().length])[0]).getBeanClassName())
                .getAnnotation(LicketAuthentication.class);
        
        //The authentication is done in memory by default
        if (authenAnnotation != null && authenAnnotation.value() == LicketAuthenticationType.DEFAULT) {
            authenticationManagerBuilder.inMemoryAuthentication()
                    .withUser("user").password("user").authorities("ROLE_USER")
                    .and()
                    .withUser("admin").password("admin").authorities("ROLE_USER", "ROLE_ADMIN");
        } else {
            //TODO: JDBC authentication implementation
        }
    }

    /**
     * Authorization
     * @param http
     * @throws Exception
     */ 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Set<BeanDefinition> securityBeans = getSpringComponents(LicketSecurity.class);
        
        //In case there are no security beans then the security is not apply in all the app
        if (securityBeans.isEmpty()) {
            http.authorizeRequests()
                    .antMatchers("/*").permitAll();
            return;
        }
        
        for (BeanDefinition bean : securityBeans) {
            LicketMountPoint mount = Class.forName(bean.getBeanClassName()).getAnnotation(LicketMountPoint.class);

            if (mount != null) {
                LicketSecurity sec = Class.forName(bean.getBeanClassName()).getAnnotation(LicketSecurity.class);
                
                String mountVal = mount.value() + (mount.value().equals("/") ? "" : "*");

                if (sec.value() != LicketSecurityType.NO_ROLE) {
                    http.authorizeRequests()
                            .antMatchers(mountVal).hasRole(sec.value().getRole())
                            .and().formLogin();
                } else {
                    http.authorizeRequests()
                            .antMatchers(mountVal).permitAll();
                }
            }
        }
    }
     
    
    private Set<BeanDefinition> getSpringComponents(Class<? extends Annotation> annotationType) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));

        Package[] packages = Package.getPackages();

        Set<BeanDefinition> components = newHashSet();
        
        for (Package pack : packages) {
            Set<BeanDefinition> beanDefs =  scanner.findCandidateComponents(pack.getName());
            
            components.addAll(beanDefs);
        }
        return components;
    }
    
}
