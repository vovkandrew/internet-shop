package internetshop.dao.jdbc;

import internetshop.controller.LoginController;
import internetshop.dao.ShoppingCartDao;
import internetshop.exception.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.util.conection.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO internetshop.shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            LOGGER.info("Shopping сart created");
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Shopping сart creation has failed", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query =
                "SELECT internetshop.shopping_carts.*, "
                        + "internetshop.shopping_cart_products.product_id, "
                        + "internetshop.products.name, internetshop.products.price "
                        + "FROM internetshop.shopping_carts "
                        + "INNER JOIN internetshop.shopping_cart_products "
                        + "ON internetshop.shopping_carts.id = "
                        + "internetshop.shopping_cart_products.cart_id "
                        + "INNER JOIN internetshop.products "
                        + "ON internetshop.shopping_cart_products.product_id "
                        + "= internetshop.products.id "
                        + "WHERE shopping_carts.id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ShoppingCart shoppingCart = new ShoppingCart(resultSet.getLong("user_id"));
                shoppingCart.setId(id);
                do {
                    Product product = new Product(resultSet.getString("name"),
                            resultSet.getDouble("price"));
                    product.setId(resultSet.getLong("product_id"));
                    shoppingCart.addProduct(product);
                } while (resultSet.next());
                LOGGER.info("Shopping cart created");
                return Optional.of(shoppingCart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find this shopping cart", e);
        }
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT internetshop.shopping_carts.id FROM internetshop.shopping_carts";
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<ShoppingCart> shoppingCartList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (get(resultSet.getLong("id")).isPresent()) {
                    shoppingCartList.add(get(resultSet.getLong("id")).get());
                }
            }
            LOGGER.info("List of shopping carts provided");
            return shoppingCartList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find shopping carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String query = "DELETE FROM internetshop.shopping_cart_products "
                + "WHERE internetshop.shopping_cart_products.cart_id = ?";
        String query2 = "INSERT INTO internetshop.shopping_cart_products VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            if (!shoppingCart.getProducts().isEmpty()) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, shoppingCart.getId());
                preparedStatement.executeUpdate();
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            for (Product product: shoppingCart.getProducts()) {
                preparedStatement2.setLong(1, shoppingCart.getId());
                preparedStatement2.setLong(2, product.getId());
                preparedStatement2.executeUpdate();
            }
            LOGGER.info("Shopping cart updated");
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this shopping cart", e);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM internetshop.shopping_cart_products WHERE cart_id = ?";
        String query2 = "DELETE FROM internetshop.shopping_carts WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setLong(1, id);
            preparedStatement2.executeUpdate();
            LOGGER.info("Shopping cart deleted");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this shopping cart", e);
        }

    }
}
