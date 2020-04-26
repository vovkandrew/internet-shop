package internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private Long id;
    private User user;
    private List<Product> products;

    public ShoppingCart(User user) {
        this.user = user;
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

}
