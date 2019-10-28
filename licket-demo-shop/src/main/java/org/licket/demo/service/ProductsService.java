package org.licket.demo.service;

import com.github.javafaker.Faker;
import org.licket.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.synchronizedList;

@Service
public class ProductsService {

    private List<String> pictureUrls;


    private Faker faker = new Faker();
    @Autowired
    private IdGenerator idGenerator;

    private List<Product> products = synchronizedList(newArrayList());

    @PostConstruct
    private void initContacts() {
        pictureUrls = newArrayList(
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/bunnie.jpeg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/bunny.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/cat.jpeg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/cotton-dog.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/dinosaur.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/giraffe-toy.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/lion.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/monkey.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/moon_cloud.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/pirate.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/unicorn.jpg",
                "https://raw.githubusercontent.com/josecmarcucci005/ecommerce_pictures/master/veggie-crate.jpg"
        );

        products = generateProducts();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> generateProducts() {
        List<Product> products = newArrayList();
        for (String url : pictureUrls) {
            Product product = new Product();
            product.setDescription(faker.lorem().sentence(30));
            product.setPictureUrl(url);

            double min = 5.00;
            double max = 35.00;
            double rand = new Random().nextDouble();

            Double result = min + (rand * (max - min));

            product.setPrice("$" + String.format("%.2f", result));
            product.setId(idGenerator.generateId().toString());
            product.setName(url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")).toUpperCase());

            products.add(product);
        }
        return products;
    }

    public Optional<Product> getContactById(String productId) {
        return products.stream().filter(product -> product.getId().equals(productId)).findFirst();
    }

}
