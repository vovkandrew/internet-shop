package internetshop.dao.jdbc;

import internetshop.controller.LoginController;
import internetshop.dao.ProductDao;
import internetshop.lib.Dao;
import internetshop.model.Product;
import internetshop.util.conection.ConnectionUtil;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoJbdcImpl implements ProductDao {
    private static Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO internetshop.products (name, price) VALUES (?, ?)";
        try ( Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.executeUpdate();
            logger.info(String.format(
                    "Product %s with price %s has been created",
                    element.getName(),
                    String.valueOf(element.getPrice())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return element;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM internetshop.products WHERE id = ?";
        try ( Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                Double productPrice = resultSet.getDouble("price");
                Product product = new Product(productName, productPrice);
                return Optional.of(product);
            }
            logger.info(String.format("Request to get a product with ID %s fulfilled", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM internetshop.products";
        List<Product> productList = new ArrayList<>();
        try ( Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("id");
                String productName = resultSet.getString("name");
                Double productPrice = resultSet.getDouble("price");
                Product product = new Product(productName, productPrice);
                product.setId(productId);
                productList.add(product);
            }
            logger.info(String.format("List of products has been sent"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product update(Product element) {
        String query = "UPDATE internetshop.products SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, element.getName());
            preparedStatement.setDouble(2, element.getPrice());
            preparedStatement.setLong(3, element.getId());
            preparedStatement.executeUpdate();
            logger.info(String.format("Product %s with ID %s has been updated",
                    element.getName(), String.valueOf(element.getId())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return element;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM internetshop.products WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info(String.format("Product with ID %s has been deleted", String.valueOf(id)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
