package internetshop.controller;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector INJECTOR = Injector.getInstance("internetshop");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> shoppingCart = shoppingCartService.getByUserId(USER_ID).getProducts();
        req.setAttribute("products", shoppingCart);
        req.getRequestDispatcher("/WEB-INF/views/shoppingcart.jsp").forward(req, resp);
    }
}
