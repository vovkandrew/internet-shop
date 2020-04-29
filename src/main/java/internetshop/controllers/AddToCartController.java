package internetshop.controllers;

import internetshop.lib.Inject;
import internetshop.lib.Injector;
import internetshop.model.ShoppingCart;
import internetshop.service.ProductService;
import internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToCartController extends HttpServlet {
    private static final Long USER_ID = 1L;

    @Inject
    private static final Injector injector = Injector.getInstance("internetshop");
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        String product = req.getParameter("id");
        shoppingCartService.addProduct(shoppingCart, productService.get(Long.valueOf(product)));
        resp.sendRedirect(req.getContextPath() + "/shoppingcart");
    }
}
