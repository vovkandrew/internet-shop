package internetshop.service;

import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.User;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {

    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);
}
