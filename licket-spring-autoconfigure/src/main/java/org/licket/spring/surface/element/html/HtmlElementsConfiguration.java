package org.licket.spring.surface.element.html;

import org.licket.surface.attribute.AttributeProvider;
import org.licket.surface.element.ElementProvider;
import org.licket.surface.tag.ElementFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import static org.licket.spring.surface.element.html.SpringAutowiredAttributeProvider.provideAttribute;
import static org.licket.spring.surface.element.html.SpringAutowiredElementProvider.provideElement;

/**
 * @author activey
 */
@Configuration
public class HtmlElementsConfiguration {

    @Bean
    @RequestScope
    public ElementFactory elementFactory() {
        return new HtmlElementFactory();
    }

    @Bean
    @RequestScope
    public ElementProvider headElement() {
        return provideElement("head", () -> new HeadElement());
    }

    @Bean
    @RequestScope
    public AttributeProvider idAttribute() {
        return provideAttribute("id", () -> new IdAttribute());
    }
}
