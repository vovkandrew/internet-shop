package internetshop.controllers;

import internetshop.lib.Inject;
import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import internetshop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Long USER_ID = 1L;
    @Inject
    private static final Injector injector = Injector.getInstance("internetshop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User andrew = new User("Andrew");
        userService.create(andrew);
        User vince = new User("Vincent");
        userService.create(vince);
        User tyler = new User("Tyler");
        userService.create(tyler);
        Product iphone = new Product("iPhone", 1000.00);
        productService.create(iphone);
        Product samsung = new Product("Samsung", 700.00);
        productService.create(samsung);
        Product huawei = new Product("Huawei", 500.00);
        productService.create(huawei);
        ShoppingCart userCart = new ShoppingCart(userService.get(USER_ID));
        shoppingCartService.create(userCart);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
