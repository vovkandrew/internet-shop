package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Order;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return Optional.of(Storage.orders
                .stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(number -> order.getId().equals(Storage.users.get(number).getId()))
                .forEach(number -> Storage.orders.set(number, order));
        return order;
    }

    @Override
    public void delete(Long orderId) {
        Storage.orders.removeIf(order -> order.getId().equals(orderId));
    }
}
