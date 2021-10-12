package edu.rutgers.dao;

import static edu.rutgers.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.rutgers.model.Admin;
import edu.rutgers.model.CustomerRep;
import edu.rutgers.model.EndUser;
import edu.rutgers.model.User;
import edu.rutgers.util.Crypto;

/**
 * This is the DAO to interface with the {@code User} model.
 * <p> 
 * This includes other models such as the admin, customer rep, and end user.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class UserDAO extends DAO<User> {
    private static final String SQL_LIST_USERS = "SELECT * FROM user ORDER BY login";

    private static final String SQL_LIST_ENDUSERS = "SELECT u.login, u.email, e.bid_alert FROM user u JOIN end_user e ON u.login = e.login ORDER BY u.login";

    private static final String SQL_LIST_REPS = "SELECT u.login, u.email FROM user u JOIN customer_rep c ON u.login = c.login ORDER BY u.login";

    private static final String SQL_LIST_ADMIN = "SELECT u.login, u.email FROM user u JOIN admin a ON u.login = a.login ORDER BY u.login";

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT login, email FROM user WHERE login=?";

    private static final String SQL_FIND_ENDUSER_BY_LOGIN = "SELECT u.login, u.email, e.bid_alert FROM user u JOIN end_user e ON u.login = e.login WHERE u.login=?";

    private static final String SQL_FIND_REP_BY_LOGIN = "SELECT u.login, u.email FROM user u JOIN customer_rep c ON u.login = c.login WHERE u.login=?";

    private static final String SQL_ATTEMPT_LOGIN = "SELECT * FROM user WHERE login=?";

    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT login, email FROM user WHERE email=?";

    private static final String SQL_CREATE_USER = "INSERT INTO user (login, email, hash, salt) VALUES (?, ?, ?, ?)";

    private static final String SQL_ADD_ENDUSER = "INSERT INTO end_user (login) VALUES (?)";

    private static final String SQL_ADD_REP = "INSERT INTO customer_rep (login) VALUES (?)";

    private static final String SQL_UPDATE_USER = "UPDATE user SET email=IFNULL(NULLIF(?, ''), email), hash=IFNULL(NULLIF(?, ''), hash), salt=IFNULL(NULLIF(?, ''), salt) WHERE login=?";

    private static final String SQL_UPDATE_ENDUSER = "UPDATE end_user SET bid_alert=IFNULL(?, TRUE) WHERE login=?";

    private static final String SQL_UPDATE_LOGIN = "UPDATE user SET login=NULLIF(?, '') WHERE login=?";

    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE login=?";

    UserDAO(DAOFactory f) {
        super(f);
    }

    /**
     * Lists users by user login.
     * 
     * @return              a list of {@code User} objects, sorted by login name
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public List<User> list() throws DAOException {
        List<User> users = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_USERS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of users.
            // if we get one, we can just fill it full of users.
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    /**
     * Lists end-users by user login.
     * 
     * @return              a list of {@code User} objects, sorted by login name
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public List<EndUser> listEndUsers() throws DAOException {
        List<EndUser> users = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_ENDUSERS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of users.
            // if we get one, we can just fill it full of users.
            while (resultSet.next()) {
                users.add(mapEndUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    /**
     * Lists customer representatives by user login.
     * 
     * @return              a list of {@code CustomerRep} objects, sorted by login name
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public List<CustomerRep> listCustomerReps() throws DAOException {
        List<CustomerRep> users = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_REPS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of users.
            // if we get one, we can just fill it full of users.
            while (resultSet.next()) {
                users.add(mapCustomerRep(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    /**
     * Gets the admin of the buyme database.
     * This assumes there is only one admin in the entire database.
     * 
     * @return              the first entry of an admin, mapped to an {@code Admin} object
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Admin getAdmin() throws DAOException {
        Admin admin = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_ADMIN, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Get the admin
            if (resultSet.next())
                admin = mapAdmin(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return admin;
    }

    /**
     * Finds a user by login name.
     * 
     * @param  login        the user login name to look for
     * @return              a {@code User} object with the given login name,
     *                      or {@code null} if no {@code User} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public User find(String login) throws DAOException {
        User user = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_USER_BY_LOGIN, true, login);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a user.
            if (resultSet.next())
                user = map(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    /**
     * Finds an end-user by login name.
     * 
     * @param  login        the user login name to look for
     * @return              an {@code EndUser} object with the given login name,
     *                      or {@code null} if no {@code EndUser} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public EndUser findEndUser(String login) throws DAOException {
        EndUser user = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_ENDUSER_BY_LOGIN, true, login);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get an end user.
            if (resultSet.next())
                user = mapEndUser(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    /**
     * Finds a customer rep by login name.
     * 
     * @param  login        the user login name to look for
     * @return              a {@code CustomerRep} object with the given login name,
     *                      or {@code null} if no {@code CustomerRep} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public CustomerRep findCustomerRep(String login) throws DAOException {
        CustomerRep user = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_REP_BY_LOGIN, true, login);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get an end user.
            if (resultSet.next())
                user = mapCustomerRep(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    /**
     * Attempt a login by matching a login and password to a user.
     * 
     * @param  login        the login to match
     * @param  password     the password to match
     * @return              the {@code User} object if the login and password match a user,
     *                      null otherwise
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public User tryLogin(String login, String password) throws DAOException {
        User user = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_ATTEMPT_LOGIN, true, login);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a user.
            if (resultSet.next()) {
                String hash = resultSet.getString("hash");
                String salt = resultSet.getString("salt");

                if (password.equals(Crypto.decrypt(hash, salt)))
                    user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        
        return user;
    }

    /**
     * Check if a email is currently being used in the users database.
     * 
     * @param  email        the email to test against the database
     * @return              true if the login is being used,
     *                      false otherwise
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public boolean checkEmailExists(String email) throws DAOException {
        boolean exists = false;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_USER_BY_EMAIL, true, email);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Find a user that has the login name.
            exists = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exists;
    }

    /**
     * Attempt to create a new user using the given info.
     * 
     * @param  user         the user to add to the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void create(User user) throws DAOException {
        Object[] values = new Object[] {
            user.getLogin(),
            user.getEmail(),
            user.getHash(),
            user.getSalt(),
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_CREATE_USER, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to create user, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Attempt to add a new end-user using the given info.
     * 
     * @param  user         the user to add to the {@code end_user} table
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public void addEndUser(User user) throws DAOException {
        Object[] values = new Object[] {
            user.getLogin()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_ADD_ENDUSER, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to add end user, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Attempt to add a new customer representative using the given info.
     * 
     * @param  user         the user to add to the {@code customer_rep} table
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public void addCustomerRep(User user) throws DAOException {
        Object[] values = new Object[] {
            user.getLogin()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_ADD_REP, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to add customer rep, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Updates the user's information in the database, matched by the given {@code User}.
     * If an end-user is passed in, it will update {@code bid_alert} as well.
     * <p>
     * Any field left {@code null} will not be updated.
     * 
     * @param  user         the user with which to base the change on
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void update(User user) throws DAOException {
        String query = SQL_UPDATE_USER;
        Object[] values = new Object[] {
            user.getEmail(),
            user.getHash(),
            user.getSalt(),
            user.getLogin()
        };


        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, query, false, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to update user, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        if (user instanceof EndUser) {
            values = new Object[] {
                ((EndUser)user).getBidAlerts(),
                user.getLogin()
            };

            try (
                Connection connection = FACTORY.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_ENDUSER, false, values);
            ) {
                if (statement.executeUpdate() == 0)
                    throw new DAOException("Failed to update user, no affected rows.");
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    /**
     * Updates the login of the specified {@code User}.
     * 
     * @param  user         the user with which to base the change on
     * @param  newLogin     the new login to associate with this {@code User}
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public void updateLogin(User user, String newLogin) throws DAOException {
        Object[] values = new Object[] {
            newLogin,
            user.getLogin()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_LOGIN, false, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to update user, no affected rows.");
            else
                user.setLogin(newLogin);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Deletes the user from the database and sets their login name to null.
     * 
     * @param  user         the user to delete from the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void delete(User user) throws DAOException {
        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE_USER, false, user.getLogin());
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to delete user, no affected rows.");
            else
                user.setLogin(null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Map the result set to a new {@code User} object.
     * <p>
     * For security reasons we do not map the hash and salt to the {@code User}.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              a {@code User} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }

    /**
     * Map the result set to a new {@code EndUser} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              an {@code EndUser} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    public EndUser mapEndUser(ResultSet resultSet) throws SQLException {
        EndUser user = new EndUser();

        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setBidAlert(resultSet.getBoolean("bid_alert"));

        return user;
    }

    /**
     * Map the result set to a new {@code CustomerRep} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              a {@code CustomerRep} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    public CustomerRep mapCustomerRep(ResultSet resultSet) throws SQLException {
        CustomerRep user = new CustomerRep();

        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }

    /**
     * Map the result set to a new {@code Admin} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              an {@code Admin} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    public Admin mapAdmin(ResultSet resultSet) throws SQLException {
        Admin user = new Admin();

        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }
}