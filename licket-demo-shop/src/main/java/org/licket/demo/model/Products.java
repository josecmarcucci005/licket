package org.licket.demo.model;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author josemarcucci005
 */
public class Products {

    public static Products fromIterable(Iterable<Product> allProducts) {
        return new Products(allProducts);
    }

    private Products(Iterable<Product> allProducts) {
        products = newArrayList(allProducts);
    }

    private List<Product> products = newArrayList();

    public final List<Product> getProducts() {
        return products;
    }
}
