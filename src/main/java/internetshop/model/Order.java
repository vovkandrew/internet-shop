package internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private User user;
    private List<Product> products;

    public Order(User user) {
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

    public String getProductsDescription() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product: this.products) {
            stringBuilder.append(" ").append(product.getId())
                    .append("-").append(product.getName()).append(".");
        }
        return stringBuilder.toString();
    }
}
