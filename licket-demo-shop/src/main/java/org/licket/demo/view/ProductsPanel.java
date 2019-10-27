package org.licket.demo.view;

import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.demo.model.Products;
import org.licket.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.internalTemplateView;
import static org.licket.demo.model.Products.fromIterable;

/**
 * @author josemarcucci005
 */
public class ProductsPanel extends AbstractLicketMultiContainer<Products> {

    @Autowired
    private ProductList productsList;

    @Autowired
    private ProductsService productsService;

    public ProductsPanel(String id) {
        super(id, Products.class, emptyComponentModel(), internalTemplateView());
    }

    @Override
    protected void onInitializeContainer() {
        add(productsList);

        readProducts();
    }

    private void readProducts() {
        setComponentModelObject(fromIterable(productsService.getAllProducts()));
    }

    public void reloadList() {
        readProducts();
    }
}
