package org.licket.demo.view;

import org.licket.core.view.ComponentActionCallback;;
import org.licket.core.view.ComponentFunctionCallback;
import org.licket.core.view.container.AbstractLicketMultiContainer;
import org.licket.core.view.hippo.vue.annotation.LicketMountPoint;;
import org.licket.core.view.link.AbstractLicketLink;
import org.licket.core.view.mount.params.MountingParams;
import org.licket.semantic.component.dimmer.AbstractSemanticUIDimmer;
import org.licket.semantic.component.dimmer.DimmerContent;
import org.licket.semantic.component.dimmer.DimmerSettings;
import org.licket.semantic.component.loader.SemanticUILoader;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.core.model.LicketComponentModel.emptyComponentModel;
import static org.licket.core.view.LicketComponentView.fromComponentClass;

@LicketMountPoint("/")
public class ProductsAppRoot extends AbstractLicketMultiContainer<Void> {

    @Autowired
    private ProductsPanel productsPanel;

    @Autowired
    private ViewAccountPanel viewAccountPanel;

    private AbstractSemanticUIDimmer dimmer;

    public ProductsAppRoot(String id) {
        super(id, Void.class, emptyComponentModel(), fromComponentClass(ProductsAppRoot.class));
    }

    @Override
    protected void onInitializeContainer() {
        add(productsPanel);
        add(viewAccountPanel);

        add(dimmer = new AbstractSemanticUIDimmer("dimmer", new DimmerSettings(true)) {
            @Override
            protected void onInitializeContent(DimmerContent dimmerContent, String contentId) {
                dimmerContent.add(new SemanticUILoader(contentId, "Loading..."));
            }
        });

        add(new AbstractLicketLink("view-account") {

            @Override
            protected void onClick(ComponentFunctionCallback componentActionCallback) {
                viewAccountPanel.showAccountModal(componentActionCallback, this);
            }
        });
    }

    @Override
    protected final void onComponentMounted(MountingParams componentMountingParams) {
        productsPanel.reloadList();
    }

    @Override
    protected void onAfterComponentMounted(ComponentActionCallback componentActionCallback) {
        componentActionCallback.patch(productsPanel);
    }
}
