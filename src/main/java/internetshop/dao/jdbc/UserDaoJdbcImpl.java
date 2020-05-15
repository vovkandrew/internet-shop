package internetshop.dao.jdbc;

import internetshop.controller.LoginController;
import internetshop.dao.OrderDao;
import internetshop.dao.ShoppingCartDao;
import internetshop.dao.UserDao;
import internetshop.exception.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.util.conection.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Inject
    OrderDao orderDao;
    ShoppingCartDao shoppingCartDao;

    @Override
    public User create(User user) {
        String query = "INSERT INTO internetshop.users (name, password) VALUES (?, ?)";
        String query3 =
                "SELECT internetshop.users.id "
                        + "FROM internetshop.users WHERE name = ? AND password = ?";
        String query2 = "INSERT INTO internetshop.users_roles VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
            preparedStatement3.setString(1, user.getName());
            preparedStatement3.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement3.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
                preparedStatement2.setLong(1, resultSet.getLong("id"));
                preparedStatement2.setLong(2, 2L);
                preparedStatement2.executeUpdate();
            }
            LOGGER.info("New user created");
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("User creation has failed", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query =
                "SELECT internetshop.users.*, internetshop.users_roles.role_id, "
                + "internetshop.roles.name AS role_name "
                + "FROM internetshop.users "
                + "INNER JOIN internetshop.users_roles ON users.id = users_roles.user_id "
                + "INNER JOIN internetshop.roles ON  users_roles.role_id = roles.id "
                + "WHERE users.id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                LOGGER.info("User has been found");
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can't find this user", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM internetshop.users";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> usersList = new ArrayList<>();
            while (resultSet.next()) {
                if (get(resultSet.getLong("id")).isPresent()) {
                    usersList.add(get(resultSet.getLong("id")).get());
                }
            }
            LOGGER.info("List of users has been found");
            return usersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find all users from DB", e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE internetshop.users SET name = ?, password = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.executeUpdate();
            LOGGER.info("User details have been updated");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update this user details", e);
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        String allUserOrders =
                "SELECT internetshop.users.id, internetshop.orders.id as order_id "
                        + "FROM internetshop.users "
                        + "INNER JOIN internetshop.orders "
                        + "ON internetshop.users.id = internetshop.orders.user_id "
                        + "WHERE users.id = ?";
        String allUserShopCarts = "SELECT internetshop.users.id, "
                + "internetshop.shopping_carts.id as shopcart_id "
                + "FROM internetshop.users "
                + "INNER JOIN internetshop.shopping_carts "
                + "ON  internetshop.users.id = internetshop.shopping_carts.id "
                + "WHERE users.id = ?";
        String deleteUser = "DELETE FROM internetshop.users "
                + "WHERE internetshop.users.id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(allUserOrders);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orderDao.delete(resultSet.getLong("order_id"));
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement(allUserShopCarts);
            preparedStatement2.setLong(1, id);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                shoppingCartDao.delete(resultSet2.getLong("shopcart_id"));
            }
            PreparedStatement preparedStatement3 = connection.prepareStatement(deleteUser);
            preparedStatement3.setLong(1, id);
            preparedStatement3.executeUpdate();
            LOGGER.info("User details have been deleted");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete this user details", e);
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        String query = "SELECT internetshop.users.* FROM internetshop.users WHERE name = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                LOGGER.info("User has been found by login");
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find a user with the following login", e);
        }
        return Optional.empty();
    }

    public User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("id");
        String userName = resultSet.getString("name");
        String userPassword = resultSet.getString("password");
        User user = new User(userName, userPassword);
        user.setId(userId);
        user.setRoles(getUserRoles(userId));
        return user;
    }

    public Set<Role> getUserRoles(Long userId) {
        String query = "SELECT internetshop.users_roles.*, internetshop.roles.name "
                + "FROM internetshop.users_roles "
                + "INNER JOIN internetshop.roles "
                + "ON internetshop.users_roles.role_id = internetshop.roles.id "
                + "WHERE internetshop.users_roles.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("name")));
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find this user roles", e);
        }
    }

}
