package edu.rutgers.dao;

import static edu.rutgers.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.rutgers.model.BidPostFor;

/**
 * This is the DAO to interface with the {@code BidPostFor} model.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class BidPostForDAO extends DAO<BidPostFor> {
    // Query constants for easy access and change
    private static final String SQL_LIST_BIDS = "SELECT * FROM bid_posts_for ORDER BY bid_number";

    private static final String SQL_FIND_BID_BY_ID = "SELECT * FROM bid_posts_for WHERE bid_number=?";

    private static final String SQL_CREATE_BID = 
        "INSERT INTO bid_posts_for (login, auction_ID, amount, bid_date, bid_time, bid_increment, upper_limit) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_BID = 
        "UPDATE bid_posts_for SET amount=IFNULL(?, amount), bid_date=IFNULL(?, bid_time), bid_time=IFNULL(?, bid_time), bid_increment=IFNULL(?, bid_increment), upper_limit=IFNULL(?, upper_limit) WHERE bid_number=?";

    private static final String SQL_DELETE_BID = "DELETE FROM bid_posts_for WHERE bid_number=?";

    BidPostForDAO(DAOFactory f) {
        super(f);
    }

    /**
     * Lists bids by bid number.
     * 
     * @return              a list of {@code BidPostsFor} objects, sorted by bid number
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public List<BidPostFor> list() throws DAOException {
        List<BidPostFor> bids = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_BIDS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of auctions.
            // if we get one, we can just fill it full of auctions.
            while (resultSet.next()) {
                bids.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return bids;
    }

    /**
     * Finds a bid by bid number.
     * 
     * @param  num          the bid number to look for
     * @return              a {@code BidPostsFor} object with the given bid number,
     *                      or {@code null} if no {@code BidPostsFor} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public BidPostFor find(int id) throws DAOException {
        BidPostFor bid = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_BID_BY_ID, true, id);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get an auction.
            if (resultSet.next())
                bid = map(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return bid;
    }

    /**
     * Attempt to create a new auction using the given info.
     * 
     * @param  auction      the auction to add to the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void create(BidPostFor bid) throws DAOException {
        Object[] values = new Object[] {
            bid.getLogin(),
            bid.getAuctionID(),
            bid.getAmount(),
            bid.getBidDate(),
            bid.getBidTime()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_CREATE_BID, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to create bid, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Updates the bids information in the database, matched by the given {@code BidPostFor}'s ID.
     * You cannot update the bid's bid number, login, or auction ID.
     * <p>
     * Any field left {@code null} will not be updated.
     * 
     * @param  bid          the auction with which to base the change on
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void update(BidPostFor bid) throws DAOException {
        Object[] values = new Object[] {
            bid.getAmount(),
            bid.getBidDate(),
            bid.getBidTime()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_BID, false, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to update bid, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Deletes the bid from the database and sets the bid number to null.
     * 
     * @param  bid          the bid to delete from the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void delete(BidPostFor bid) throws DAOException {
        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE_BID, false, bid.getBidNumber());
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to delete bid, no affected rows.");
            else
                bid.setAuctionID(null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Map the result set to a new {@code BidPostFor} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              a {@code BidPostFor} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    @Override
    public BidPostFor map(ResultSet resultSet) throws SQLException {
        BidPostFor bid = new BidPostFor();

        bid.setBidNumber(resultSet.getInt("bid_number"));
        bid.setLogin(resultSet.getString("login"));
        bid.setAuctionID(resultSet.getInt("auction_ID"));
        bid.setAmount(resultSet.getFloat("amount"));
        bid.setBidDate(resultSet.getDate("bid_date"));
        bid.setBidTime(resultSet.getDate("bid_time"));

        return bid;
    }
}