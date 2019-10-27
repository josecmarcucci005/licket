package org.licket.demo.resource;

import org.licket.core.resource.image.ImageResource;
import org.licket.core.resource.image.ImageType;

public class ApplicationImageResource extends ImageResource  {

    public ApplicationImageResource() {
        super("images", "static/images/**", ImageType.JPG);
    }
}
