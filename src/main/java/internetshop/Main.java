package internetshop;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ProductService;

public class Main {
    public static Injector injector = Injector.getInstance("internetshop");

    public static void main(String [] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product product1 = productService.create(new Product("Azbuka", 99.99));
        productService.get(product1.getId());
        System.out.println(product1.toString());
        Product product2 = productService.create(new Product("Neznayka", 199.99));
        productService.get(product2.getId());
        System.out.println(product2.toString());
        Product product3 = productService.create(new Product("Bukvar", 149.99));
        productService.get(product3.getId());
        System.out.println(product3.toString());
    }
}
