package internetshop.service;

import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.User;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Optional<Order> get(Long orderId);

    List<Order> getAll();

    boolean delete(Long id);
}
