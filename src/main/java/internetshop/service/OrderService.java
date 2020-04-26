package internetshop.service;

import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.User;
import java.util.List;

public interface OrderService {

    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Order get(Long orderId);

    List<Order> getAll();

    boolean delete(Long id);
}
