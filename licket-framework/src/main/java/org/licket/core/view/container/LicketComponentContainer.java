package org.licket.core.view.container;

import org.licket.core.view.LicketComponent;
import org.licket.core.view.hippo.vue.annotation.LicketAuthentication;
import org.licket.core.view.hippo.vue.annotation.LicketAuthenticationType;

/**
 * @author grabslu
 */
@LicketAuthentication(LicketAuthenticationType.DEFAULT)
public interface LicketComponentContainer<T> extends LicketComponent<T> {

    void add(LicketComponent<?> licketComponent);
}
