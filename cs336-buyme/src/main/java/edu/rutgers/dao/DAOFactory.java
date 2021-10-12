package edu.rutgers.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles creating data access objects (DAO) to interface with the datastore.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class DAOFactory {
    // The information to connect to the MySQL table
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/buyme";
    private static final String MYSQL_DATABASE_USER = "root";
    private static final String MYSQL_DATABASE_PASSWORD = "cs336project";

    // A static block for simply loading the MySQL JDBC Driver
    static {
        try {
            // Load JDBC driver - the interface standardizing the connection procedure. 
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a connection to the database
     * 
     * @return the {@code Connection} to the database
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, MYSQL_DATABASE_USER, MYSQL_DATABASE_PASSWORD);
    }

    /**
     * Gets the DAO for users given this factory
     * 
     * @return the {@code UserDAO} constructed from this factory.
     */
    public UserDAO getUserDAO() {
        return new UserDAO(this);
    }

    /**
     * Gets the DAO for auctions given this factory
     * 
     * @return the {@code AuctionTransactionDAO} constructed from this factory.
     */
    public AuctionTransactionDAO getAuctionTransactionDAO() {
        return new AuctionTransactionDAO(this);
    }

    /**
     * Gets the DAO for bids given this factory
     * 
     * @return the {@code BidPostForDAO} constructed from this factory.
     */
    public BidPostForDAO getBidPostForDAO() {
        return new BidPostForDAO(this);
    }

    /**
     * Gets the DAO for questions given this factory
     * 
     * @return the {@code QuestionDAO} constructed from this factory.
     */
    public QuestionDAO getQuestionDAO() {
        return new QuestionDAO(this);
    }
}
