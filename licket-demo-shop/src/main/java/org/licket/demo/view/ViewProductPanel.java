package org.licket.demo.view;

import org.licket.core.view.ComponentActionCallback;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.LicketLabel;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;
import org.licket.core.view.media.LicketImage;
import org.licket.core.view.mount.MountedComponentLink;
import org.licket.core.view.mount.params.MountingParams;
import org.licket.demo.model.Product;
import org.licket.demo.service.ProductsService;
import org.licket.semantic.component.segment.AbstractSemanticUISegment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.licket.core.model.LicketComponentModel.ofModelObject;
import static org.licket.core.view.LicketComponentView.fromComponentClass;
import static org.licket.core.view.LicketComponentView.internalTemplateView;

/**
 * @author josemarcucci005
 */
@LicketMountPoint("/product/{id}")
public class ViewProductPanel extends AbstractLicketMultiContainer<Product> {

  @Autowired
  private ProductsService productsService;

  private AbstractSemanticUISegment<Product> segment;

  public ViewProductPanel(String id) {
    super(id, Product.class, ofModelObject(new Product()), fromComponentClass(ViewProductPanel.class));
  }

  @Override
  protected void onInitializeContainer() {
    add(segment = new AbstractSemanticUISegment<Product>("segment", Product.class, getComponentModel(), internalTemplateView()) {

      @Override
      protected void onInitializeContainer() {
        add(new LicketLabel("name"));
        add(new LicketLabel("description"));
        add(new LicketImage("pictureUrl"));

        add(new MountedComponentLink<>("rootLink", ProductsAppRoot.class));
      }
    });
  }

  @Override
  protected void onBeforeComponentMounted(ComponentFunctionCallback componentFunctionCallback) {
    segment.api(componentFunctionCallback).showLoading(this);
  }

  @Override
  protected void onComponentMounted(MountingParams componentMountingParams) {
    Optional<String> contactId = componentMountingParams.getString("id");
    if (!contactId.isPresent()) {
      return;
    }
    Optional<Product> productOptional = productsService.getContactById(contactId.get());
    if (!productOptional.isPresent()) {
      return;
    }
    segment.setComponentModelObject(productOptional.get());
  }

  @Override
  protected void onAfterComponentMounted(ComponentActionCallback componentActionCallback) {
    segment.api(componentActionCallback).hideLoading(this);
    componentActionCallback.reload(segment);
  }
}
