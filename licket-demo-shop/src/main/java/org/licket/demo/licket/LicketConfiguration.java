package org.licket.demo.licket;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.resource.Resource;
import org.licket.demo.resource.ApplicationCssResource;
import org.licket.demo.resource.ApplicationImageResource;
import org.licket.demo.view.*;
import org.licket.semantic.SemanticUIPluginConfiguration;
import org.licket.semantic.component.modal.ModalSettings;
import org.licket.spring.annotation.LicketComponent;
import org.licket.spring.annotation.LicketRootContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

import static org.licket.semantic.component.modal.ModalSettingsBuilder.builder;

@Configuration
@Import(SemanticUIPluginConfiguration.class)
public class LicketConfiguration {

    @LicketRootContainer
    public ProductsAppRoot root() {
        return new ProductsAppRoot("products-page");
    }

    @LicketComponent
    public ProductsPanel productsPanel() {
        return new ProductsPanel("products-panel");
    }

    @LicketComponent
    public ProductList productsList() {
        return new ProductList("product", new LicketComponentModel("products"));
    }

    @LicketComponent
    public ViewProductPanel viewProductPanel() {
        return new ViewProductPanel("view-product-panel");
    }

    @LicketComponent
    public ViewAccountPanel viewAccountPanel() {
        return new ViewAccountPanel("view-account-panel", modalDialogSettings());
    }

    @LicketComponent
    public AccountForm accountForm() {
        return new AccountForm("account-form");
    }

    private ModalSettings modalDialogSettings() {
        return builder().showActions().build();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }

    @Bean
    public Resource applicationCssResource() {
        return new ApplicationCssResource();
    }

    @Bean
    public Resource applicationImageResource() {
        return new ApplicationImageResource();
    }
}
