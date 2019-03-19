package org.licket.demo.licket;

import org.licket.core.model.LicketComponentModel;
import org.licket.core.resource.Resource;
import org.licket.core.view.hippo.vue.annotation.LicketAuthentication;
import org.licket.core.view.hippo.vue.annotation.LicketAuthenticationType;
import org.licket.demo.resource.ApplicationCssResource;
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
@LicketAuthentication(LicketAuthenticationType.DEFAULT)
public class LicketConfiguration {

    @LicketRootContainer
    public ContactsAppRoot root() {
        return new ContactsAppRoot("contacts-page");
    }

    @LicketComponent
    public ContactsPanel contactsPanel() {
        return new ContactsPanel("contacts-panel");
    }

    @LicketComponent
    public ContactsList contactsList() {
        return new ContactsList("contact", new LicketComponentModel("contacts"));
    }

    @LicketComponent
    public AddContactForm addContactForm() {
        return new AddContactForm("new-contact-form");
    }

    @LicketComponent
    public ViewContactPanel viewContactPanel() {
        return new ViewContactPanel("view-contact-panel");
    }

    private ModalSettings modalDialogSettings() {
        return builder().showActions().build();
    }

    @LicketComponent
    public AddContactPanel addContactPanel() {
        return new AddContactPanel("add-contact-panel", modalDialogSettings());
    }

    @LicketComponent
    public LicketHello addLicketHello() {
      return new LicketHello("licket-hello");
    }

    @Bean
    public IdGenerator idGenerator() {
        return new JdkIdGenerator();
    }

    @Bean
    public Resource applicationCssResource() {
        return new ApplicationCssResource();
    }
}
