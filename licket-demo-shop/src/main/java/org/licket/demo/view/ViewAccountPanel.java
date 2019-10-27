package org.licket.demo.view;

import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketComponent;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.demo.view.modal.ViewAccountFormSection;
import org.licket.semantic.component.modal.AbstractSemanticUIModal;
import org.licket.semantic.component.modal.ModalSection;
import org.licket.semantic.component.modal.ModalSettings;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author josemarcucci005
 */
public class ViewAccountPanel extends AbstractLicketMultiContainer<Void> {

  private AbstractSemanticUIModal modal;
  private final ModalSettings modalSettings;

  @Autowired
  private AccountForm accountForm;

  public ViewAccountPanel(String id, ModalSettings modalSettings) {
    super(id, Void.class, emptyComponentModel(), internalTemplateView());
    this.modalSettings = modalSettings;
  }

  @Override
  protected void onInitializeContainer() {
    add(modal = new AbstractSemanticUIModal("account-modal", modalSettings) {
      @Override
      protected void onInitializeBody(ModalSection bodySection, String contentId) {
        bodySection.add(new ViewAccountFormSection(contentId) {
          @Override
          protected void onInitializeContainer() {
            add(accountForm);
          }
        });
      }

      @Override
      protected void onInitializeHeader(ModalSection modalSection, String contentId) { }

      @Override
      protected void onInitializeActions(ModalSection content, String contentId) {}
    });
  }

  public final void showAccountModal(ComponentFunctionCallback componentActionCallback, LicketComponent<?> caller) {
    modal.api(componentActionCallback).show(caller);
  }
}
