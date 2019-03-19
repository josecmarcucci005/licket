package org.licket.demo.view;

import org.licket.core.view.LicketLabel;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.hippo.vue.annotation.LicketSecurity;
import org.licket.core.view.hippo.vue.annotation.LicketSecurityType;
import org.licket.core.view.media.LicketImage;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;


@LicketMountPoint("/sayHello")
@LicketSecurity(LicketSecurityType.NO_ROLE)
public class LicketHello extends AbstractLicketMultiContainer<Void> {

  public LicketHello(String id) {
    super(id, Void.class, emptyComponentModel(), fromComponentClass(LicketHello.class));
  }

  @Override
  protected void onInitializeContainer() {
    add(new LicketLabel("hello-label"));
  }

}
