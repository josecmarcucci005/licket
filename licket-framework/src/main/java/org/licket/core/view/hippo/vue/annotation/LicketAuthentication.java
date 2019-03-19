package org.licket.core.view.hippo.vue.annotation;

import java.lang.annotation.*;

/**
 * @author josecmarcucci
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface LicketAuthentication {
    LicketAuthenticationType value();
    
}
