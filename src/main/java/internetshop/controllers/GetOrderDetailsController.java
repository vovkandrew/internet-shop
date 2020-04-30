package internetshop.controllers;

import internetshop.lib.Injector;
import internetshop.model.Product;
import internetshop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderDetailsController extends HttpServlet {
    private static final Injector INJECTOR =
            Injector.getInstance("internetshop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        List<Product> products = orderService.get(Long.valueOf(orderId))
                .getProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orderdetails.jsp")
                .forward(req, resp);
    }
}
