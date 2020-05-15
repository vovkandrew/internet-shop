package internetshop.dao.jdbc;

import internetshop.controller.LoginController;
import internetshop.dao.ProductDao;
import internetshop.exception.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Product;
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
public class ProductDaoJbdcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO internetshop.products (name, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();
            LOGGER.info("Product has been created");
        } catch (SQLException e) {
            throw new DataProcessingException("Product creation has failed", e);
        }
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM internetshop.products WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getProductFromResultSet(resultSet));
            }
            LOGGER.info("Product has been found");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find product with following id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM internetshop.products";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("id");
                Product product = getProductFromResultSet(resultSet);
                product.setId(productId);
                productList.add(product);
            }
            LOGGER.info("List of products has been sent");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't retreave all available products from DB", e);
        }
        return productList;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE internetshop.products SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("Product has been updated");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product details", e);
        }
        return product;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM internetshop.orders_products WHERE product_id = ?";
        String query2 = "DELETE FROM internetshop.shopping_cart_products WHERE product_id = ?";
        String query3 = "DELETE FROM internetshop.products WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setLong(1, id);
            preparedStatement2.executeUpdate();
            PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
            preparedStatement3.setLong(1, id);
            preparedStatement3.executeUpdate();
            LOGGER.info("Product has been deleted");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete product from DB", e);
        }
    }

    public Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        String productName = resultSet.getString("name");
        Double productPrice = resultSet.getDouble("price");
        return new Product(productName, productPrice);
    }
}
