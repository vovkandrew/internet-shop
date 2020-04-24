package internetshop.service.impl;

import internetshop.dao.OrderDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.User;
import internetshop.service.OrderService;
import internetshop.service.ShoppingCartService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        shoppingCartService.clear(shoppingCartService.getByUserId(user.getId()));
        Order order = new Order(user);
        order.addProducts(products);
        return orderDao.create(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream()
                .filter(order -> order.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long orderId) {
        return orderDao.get(orderId).orElseThrow();
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long orderId) {
        orderDao.delete(orderId);
        return true;
    }
}
