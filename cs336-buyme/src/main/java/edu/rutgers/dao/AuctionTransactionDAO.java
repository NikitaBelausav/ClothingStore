package edu.rutgers.dao;

import static edu.rutgers.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rutgers.model.AuctionTransaction;

/**
 * This is the DAO to interface with the {@code AuctionTransaction} model.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class AuctionTransactionDAO extends DAO<AuctionTransaction> {
    // Query constants for easy access and change
    private static final String SQL_LIST_AUCTIONS = "SELECT * FROM auction_transactions ORDER BY auction_ID";

    private static final String SQL_GET_BEST_SELLING = "SELECT i.name AS item, SUM(IFNULL(a.final_price, 0)) AS amount FROM auction_transactions AS a JOIN item i ON a.item_ID=i.item_ID WHERE winner IS NOT NULL GROUP BY item ORDER BY amount DESC";

    private static final String SQL_GET_BEST_BUYERS = "SELECT winner AS buyer, SUM(IFNULL(final_price, 0)) AS amount FROM auction_transactions WHERE winner IS NOT NULL GROUP BY buyer ORDER BY amount DESC";

    private static final String SQL_GET_TOTAL_EARNINGS = "SELECT SUM(IFNULL(final_price, 0)) AS earnings FROM auction_transactions";

    private static final String SQL_GET_EARNINGS_PER_ITEM = "SELECT i.name AS item, SUM(IFNULL(a.final_price, 0)) AS earnings FROM auction_transactions AS a JOIN item i ON a.item_ID=i.item_ID WHERE winner IS NOT NULL GROUP BY i.name ORDER BY item DESC";

    // TODO: Figure out if this needs changing
    private static final String SQL_GET_EARNINGS_PER_TYPE = "SELECT c.category_number AS category, SUM(IFNULL(a.final_price, 0)) AS earnings FROM auction_transactions AS a JOIN belongs_to c ON a.item_ID=c.item_ID WHERE winner IS NOT NULL GROUP BY c.category_number ORDER BY category DESC";

    private static final String SQL_GET_EARNINGS_PER_USER = "SELECT login AS user, SUM(IFNULL(final_price, 0)) AS earnings FROM auction_transactions WHERE winner IS NOT NULL GROUP BY winner ORDER BY user DESC";

    private static final String SQL_FIND_AUCTION_BY_ID = "SELECT * FROM auction_transactions WHERE auction_ID=?";

    private static final String SQL_CREATE_AUCTION = 
        "INSERT INTO auction_transactions (itemID, login, close_date, close_time, winner, init_price, bid_increment, minimum, final_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_AUCTION = 
        "UPDATE auction_transactions SET close_date=IFNULL(?, close_date), close_time=IFNULL(?, close_time), winner=IFNULL(?, winner), init_price=IFNULL(?, init_price), bid_increment=IFNULL(?, bid_increment), minimum=IFNULL(?, minimum), final_price=(?, final_price) WHERE auctionID=?";

    private static final String SQL_DELETE_AUCTION = "DELETE FROM auction_transactions WHERE auctionID=?";

    AuctionTransactionDAO(DAOFactory f) {
        super(f);
    }

    /**
     * Lists auctions by ID.
     * 
     * @return              a list of {@code AuctionTransaction} objects, sorted by ID
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public List<AuctionTransaction> list() throws DAOException {
        List<AuctionTransaction> auctions = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_AUCTIONS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of auctions.
            // if we get one, we can just fill it full of auctions.
            while (resultSet.next()) {
                auctions.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return auctions;
    }

    /**
     * Gets a lump sum of the earnings made via finished auctions.
     *  
     * @return              the total earnings over all auctions 
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Float getTotalEarnings() throws DAOException {
        Float ret = 0f;
        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_TOTAL_EARNINGS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of auctions.
            // if we get one, we can just fill it full of auctions.
            if (resultSet.next()) {
                ret = resultSet.getFloat("earnings");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return ret;
    }

    /**
     * Gets a list of the best selling items via finished auctions,
     * that is, the list of items that have paid the most in total.
     *  
     * @return              the list of best selling items
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Map<String, Float> getBestSellingItems() throws DAOException {
        Map<String, Float> map = new HashMap<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_BEST_SELLING, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next())
                map.put(resultSet.getString("item"), resultSet.getFloat("amount"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return map;
    }

    /**
     * Gets a list of the best buyers via finished auctions,
     * that is, the list of users that have paid the most in total.
     *  
     * @return              the list of best buyers 
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Map<String, Float> getBestBuyers() throws DAOException {
        Map<String, Float> map = new HashMap<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_BEST_BUYERS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next())
                map.put(resultSet.getString("buyer"), resultSet.getFloat("amount"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return map;
    }

    /**
     * Gets a list of the earnings made via finished auctions,
     * per item.
     *  
     * @return              the total earnings of each item 
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Map<String, Float> getEarningsPerItem() throws DAOException {
        Map<String, Float> map = new HashMap<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_EARNINGS_PER_ITEM, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next())
                map.put(resultSet.getString("item"), resultSet.getFloat("earnings"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return map;
    }

    /**
     * Gets a list of the earnings made via finished auctions,
     * per item type.
     *  
     * @return              the total earnings of each item type 
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Map<String, Float> getEarningsPerItemType() throws DAOException {
        Map<String, Float> map = new HashMap<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_EARNINGS_PER_TYPE, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // TODO: change when/if we get a category name implemented
            while (resultSet.next()) 
                map.put("" + resultSet.getInt("category"), resultSet.getFloat("earnings"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return map;
    }

    /**
     * Gets a list of the earnings made via finished auctions,
     * per end-user.
     *  
     * @return              the total earnings of each end-user 
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Map<String, Float> getEarningsPerUser() throws DAOException {
        Map<String, Float> map = new HashMap<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_GET_EARNINGS_PER_USER, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) 
                map.put(resultSet.getString("user"), resultSet.getFloat("earnings"));
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return map;
    }

    /**
     * Finds an auction by ID.
     * 
     * @param  id           the auction ID to look for
     * @return              an {@code AuctionTransaction} object with the given ID,
     *                      or {@code null} if no {@code AuctionTransaction} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public AuctionTransaction find(int id) throws DAOException {
        AuctionTransaction auction = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_AUCTION_BY_ID, true, id);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get an auction.
            if (resultSet.next())
                auction = map(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return auction;
    }

    /**
     * Attempt to create a new auction using the given info.
     * 
     * @param  auction      the auction to add to the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void create(AuctionTransaction auction) throws DAOException {
        Object[] values = new Object[] {
            auction.getItemID(),
            auction.getLogin(),
            auction.getCloseDate(),
            auction.getCloseTime(),
            auction.getWinner(),
            auction.getInitPrice(),
            auction.getBidIncrement(),
            auction.getMinimum(),
            auction.getFinalPrice()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_CREATE_AUCTION, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to create auction, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Updates the auction's information in the database, matched by the given {@code AuctionTransaction}'s ID.
     * You cannot update the auction's ID, itemID, or owner login.
     * <p>
     * Any field left {@code null} will not be updated.
     * 
     * @param  auction      the auction with which to base the change on
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void update(AuctionTransaction auction) throws DAOException {
        Object[] values = new Object[] {
            auction.getCloseDate(),
            auction.getCloseTime(),
            auction.getWinner(),
            auction.getInitPrice(),
            auction.getBidIncrement(),
            auction.getMinimum(),
            auction.getFinalPrice()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_AUCTION, false, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to update auction, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Deletes the auction from the database and sets the auctionID to null.
     * 
     * @param  auction      the auction to delete from the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void delete(AuctionTransaction auction) throws DAOException {
        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE_AUCTION, false, auction.getAuctionID());
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to delete auction, no affected rows.");
            else
                auction.setAuctionID(null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Map the result set to a new {@code AuctionTransaction} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              an {@code AuctionTransaction} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    @Override
    public AuctionTransaction map(ResultSet resultSet) throws SQLException {
        AuctionTransaction auction = new AuctionTransaction();

        auction.setAuctionID(resultSet.getInt("auction_ID"));
        auction.setItemID(resultSet.getInt("item_ID"));
        auction.setLogin(resultSet.getString("login"));
        auction.setCloseDate(resultSet.getDate("close_date"));
        auction.setCloseTime(resultSet.getDate("close_time"));
        auction.setWinner(resultSet.getString("winner"));
        auction.setInitPrice(resultSet.getFloat("init_price"));
        auction.setBidIncrement(resultSet.getFloat("bid_increment"));
        auction.setMinimum(resultSet.getFloat("minimum"));
        auction.setFinalPrice(resultSet.getFloat("final_price"));

        return auction;
    }
}