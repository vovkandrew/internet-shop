package internetshop.dao.jdbc;

import internetshop.controller.LoginController;
import internetshop.dao.OrderDao;
import internetshop.exception.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.util.conection.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Override ////to check Travis
    public Order create(Order order) {
        String insertNewOrderInfo =
                "INSERT INTO internetshop.orders (user_id) VALUES (?)";
        String insertOrderProductsInfo =
                "INSERT INTO internetshop.orders_products (order_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(insertNewOrderInfo,
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            PreparedStatement preparedStatement2 =
                    connection.prepareStatement(insertOrderProductsInfo);
            for (Product product: order.getProducts()) {
                preparedStatement2.setLong(1, order.getId());
                preparedStatement2.setLong(2, product.getId());
                preparedStatement.executeUpdate();
            }
            LOGGER.info("Order has been created");
        } catch (SQLException e) {
            throw new DataProcessingException("Order creation has failed", e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        String getProductInfo =
                "SELECT internetshop.orders.*, internetshop.orders_products.product_id, "
                + "internetshop.products.name, internetshop.products.price "
                        + "FROM internetshop.orders "
                + "INNER JOIN internetshop.orders_products ON orders.id = orders_products.order_id "
                + "INNER JOIN internetshop.products ON orders_products.product_id = products.id "
                + "WHERE orders.id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(getProductInfo);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(getProductInfo);
            resultSet.next();
            Order order = new Order(resultSet.getLong("user_id"));
            order.setId(id);
            List<Product> orderProducts = new ArrayList<>();
            do {
                orderProducts.add(getProductFromResultSet(resultSet));
            } while (resultSet.next());
            LOGGER.info("Order has been found in the database");
            order.setProducts(orderProducts);
            return Optional.of(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find this order", e);
        }
    }

    @Override
    public List<Order> getAll() {
        String getAllProductsInfo =
                "SELECT internetshop.orders.id FROM internetshop.orders";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(getAllProductsInfo);
            ResultSet resultSet = preparedStatement.executeQuery(getAllProductsInfo);
            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                Optional order = get(resultSet.getLong("id"));
                if (order.isPresent()) {
                    orders.add((Order) order.get());
                }
            }
            LOGGER.info("Orders have been found in the database");
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't show all orders", e);
        }
    }

    @Override
    public Order update(Order order) {
        String deleteOldOrderInfo = "DELETE FROM internetshop.orders_products "
                + "WHERE orders_products.order_id = ?";
        String insertNewOrderInfo =
                "INSERT INTO internetshop.orders_products VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(deleteOldOrderInfo);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 =
                    connection.prepareStatement(insertNewOrderInfo);
            for (Product product: order.getProducts()) {
                preparedStatement2.setLong(1, order.getId());
                preparedStatement2.setLong(2, product.getId());
                preparedStatement2.executeUpdate();
            }
            LOGGER.info("Order details has been updated");
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this order details", e);
        }
    }

    @Override
    public void delete(Long id) {
        String deleteOrderProductsFromDb =
                "DELETE FROM internetshop.orders_products "
                + "WHERE internetshop.orders_products.order_id = ?";
        String deleteOrderFromDb = "DELETE FROM internetshop.orders WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(deleteOrderProductsFromDb);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 =
                    connection.prepareStatement(deleteOrderFromDb);
            preparedStatement2.setLong(1, id);
            preparedStatement2.executeUpdate();
            LOGGER.info("Shopping cart deleted");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this shopping cart", e);
        }
    }

    public Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("name");
        Double productPrice = resultSet.getDouble("price");
        Product product = new Product(productName, productPrice);
        product.setId(productId);
        return product;
    }
}
