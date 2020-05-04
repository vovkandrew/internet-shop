package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Product iphone = new Product("iPhone", 1000.00);
        productService.create(iphone);
        Product samsung = new Product("Samsung", 700.00);
        productService.create(samsung);
        Product huawei = new Product("Huawei", 500.00);
        productService.create(huawei);
        Product xiaomi = new Product("Xiaomi", 300.00);
        productService.create(xiaomi);
        Product oneplus = new Product("Oneplus", 450.00);
        productService.create(oneplus);
        Product meizu = new Product("Meizu", 400.00);
        productService.create(meizu);
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        ShoppingCart userCart = new ShoppingCart(userService.get(userId));
        shoppingCartService.create(userCart);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
