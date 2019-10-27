package org.licket.demo.view.modal;

import org.licket.core.view.container.AbstractLicketMonoContainer;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

/**
 * @author josemarcucci005
 */
public class ViewAccountFormSection extends AbstractLicketMonoContainer<Void> {

  public ViewAccountFormSection(String id) {
    super(id, Void.class, emptyComponentModel(),
            fromComponentClass(ViewAccountFormSection.class));
  }

}
