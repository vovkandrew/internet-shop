package internetshop.dao.impl;

import internetshop.dao.OrderDao;
import internetshop.dao.Storage;
import internetshop.model.Order;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Order get(Long id) {
        return Storage.orders
                .stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(number -> order.getId().equals(Storage.orders.get(number).getId()))
                .forEach(number -> Storage.orders.set(number, order));
        return order;
    }

    @Override
    public void delete(Long id) {
        Storage.orders.removeIf(order -> order.getId().equals(id));
    }
}
