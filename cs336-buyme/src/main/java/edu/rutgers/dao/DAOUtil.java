package edu.rutgers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a utility class for commonly used functions in DAOs.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public final class DAOUtil {
    // Private constructor, do not use.
    private DAOUtil() {}

    /**
     * Prepares a statement by creating one and filling it dynamically with the specified values.
     * 
     * @param  connection   the database connection to use for creating the statement
     * @param  sql          the SQL to use for the statement
     * @param  values       the values to use to fill the SQL statement
     * @return              the SQL statement to use, with blanks filled out
     * @throws SQLException if the database has an issue with creating the statement
     */
    public static PreparedStatement prepareStatement(Connection connection, String sql, boolean returnGenerated, Object... values) throws SQLException {
        PreparedStatement ret = connection.prepareStatement(sql, returnGenerated ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

        for (int i = 0; values != null && i < values.length; i++)
            ret.setObject(i + 1, values[i]);

        return ret;
    }
}