package org.licket.core.view.hippo.vue.extend;

import org.licket.core.view.hippo.vue.annotation.VueComponentFunction;
import org.licket.framework.hippo.ObjectLiteralBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Optional;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang.StringUtils.trimToNull;
import static org.licket.framework.hippo.ObjectLiteralBuilder.objectLiteral;
import static org.licket.framework.hippo.ObjectPropertyBuilder.propertyBuilder;

/**
 * @author activey
 */
public class VueExtendMethodsDecorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VueExtendMethodsDecorator.class);

    private VueClass vueClass;

    public static VueExtendMethodsDecorator fromClass(VueClass vueClass) {
        return new VueExtendMethodsDecorator(vueClass);
    }

    private VueExtendMethodsDecorator(VueClass vueClass) {
        this.vueClass = vueClass;
    }

    public ObjectLiteralBuilder decorate(ObjectLiteralBuilder objectLiteral) {
        ObjectLiteralBuilder methodsObject = objectLiteral();
        stream(vueClass.getClass().getMethods()).forEach(method -> writeMemberFunctionBody(methodsObject, method));
        return methodsObject;
    }

    private void writeMemberFunctionBody(ObjectLiteralBuilder methodsObject, Method method) {
        Optional<VueComponentFunction> classFunction = getClassFunction(method);
        if (!classFunction.isPresent()) {
            LOGGER.trace("Skipping processing {} method.", method.getName());
            return;
        }
        // declaring member function
        VueExtendMemberFunction memberFunction = new VueExtendMemberFunction();

        // appending extend method declaration
        methodsObject.objectProperty(
                propertyBuilder()
                        .name(firstNonNull(trimToNull(classFunction.get().value()), method.getName()))
                        .value(memberFunction.toFunctionNode(method, vueClass)));
    }

    private Optional<VueComponentFunction> getClassFunction(Method method) {
        VueComponentFunction angularClassFunction = method.getAnnotation(VueComponentFunction.class);
        if (isPrivate(method.getModifiers())) {
            LOGGER.warn("Private methods, like {}, are not supported for now.", method.getName());
            return empty();
        }
        return ofNullable(angularClassFunction);
    }
}
