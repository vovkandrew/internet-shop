package internetshop;

import internetshop.lib.Injector;
import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.OrderService;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;

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
        System.out.println(productService.getAll());
        System.out.println("");
        productService.delete(product1.getId());
        System.out.println(productService.getAll());
        System.out.println("");
        Product product4 = new Product("Anekdoti",9.99);

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = userService.create(new User("Andrew"));
        userService.get(user1.getId());
        System.out.println(user1.toString());
        User user2 = userService.create(new User("Vincent"));
        userService.get(user2.getId());
        System.out.println(user2.toString());
        User user3 = userService.create(new User("Tyler"));
        userService.get(user3.getId());
        System.out.println(user3.toString());
        System.out.println(userService.getAll());
        System.out.println("");
        userService.delete(user2.getId());
        System.out.println(userService.getAll());
        System.out.println("");

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart1 = new ShoppingCart(user3);
        ShoppingCart shoppingCart2 = new ShoppingCart(user1);
        shoppingCartService.addProduct(shoppingCart2, product1);

        System.out.println(shoppingCart1.getUser().toString());
        System.out.println("");
        shoppingCartService.addProduct(shoppingCart1, product1);
        shoppingCartService.addProduct(shoppingCart1, product2);
        shoppingCartService.addProduct(shoppingCart1, product3);
        System.out.println("");
        System.out.println(shoppingCartService.getAllProducts(shoppingCart1));
        System.out.println("");
        shoppingCartService.deleteProduct(shoppingCart1, product1);
        System.out.println(shoppingCartService.getAllProducts(shoppingCart1));
        System.out.println("");
        System.out.println(shoppingCartService.getByUserId(user3.getId()));
        System.out.println("");
        shoppingCartService.clear(shoppingCart1);
        System.out.println(shoppingCartService.getAllProducts(shoppingCart1));
        System.out.println("");

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        Order order1 = orderService
                .completeOrder(shoppingCart1.getProducts(),
                        shoppingCart1.getUser());
        Order order2 = orderService
                .completeOrder(shoppingCart1.getProducts(),
                        shoppingCart1.getUser());
        System.out.println(order1.getId().toString());
        System.out.println(orderService.get(order1.getId()));
        System.out.println(orderService.getAll());
        System.out.println(orderService.getUserOrders(user3));
        System.out.println(orderService.delete(order1.getId()));
    }
}
