package org.licket.demo.view;

import org.licket.semantic.component.form.AbstractSemanticUIForm;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author josemarcucci005
 */
public class AccountForm extends AbstractSemanticUIForm<Void> {

    public AccountForm(String id) {
        super(id, Void.class, emptyComponentModel(), internalTemplateView());
    }
}
