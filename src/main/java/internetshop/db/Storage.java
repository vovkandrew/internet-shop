package internetshop.db;

import internetshop.model.Bucket;
import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    public static final List<Product> products = new ArrayList();
    public static final List<Order> orders = new ArrayList();
    public static final List<Bucket> buckets = new ArrayList();
    public static final List<User> users = new ArrayList();
    private static Long productId = 0L;
    private static Long userId = 0L;
    private static Long orderId = 0L;
    private static Long bucketId = 0L;

    public static void addItem(Product product) {
        productId++;
        product.setId(productId);
        products.add(product);
    }

    public static void addOrder(Order order) {
        orderId++;
        order.setId(orderId);
        orders.add(order);
    }

    public static void addBucket(Bucket bucket) {
        bucketId++;
        bucket.setId(bucketId);
        buckets.add(bucket);
    }

    public static void addUser(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
    }
}
