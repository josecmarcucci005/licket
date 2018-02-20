package org.licket.core.view.hippo.vue.extend;

import org.licket.core.view.ComponentFunctionCallback;

/**
 * @author lukaszgrabski
 */
public abstract class AbstractCallableVueClass implements VueClass {

  public AbstractVueClassCallableAPI call(ComponentFunctionCallback functionCallback) {
    return new DefaultVueClassCallableAPI(functionCallback);
  }
}
