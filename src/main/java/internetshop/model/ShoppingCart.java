package internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private Long id;
    private Long userId;
    private List<Product> products;

    public ShoppingCart(Long userId) {
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
