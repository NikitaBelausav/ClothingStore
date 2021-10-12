package edu.rutgers.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Generic interface for implementing a DAO for an object model.
 * A DAO should be implemented for every type of strong entity in the database model.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public abstract class DAO<T> {
    protected final DAOFactory FACTORY;

    public DAO(DAOFactory f) { 
        FACTORY = f;
    }

    /**
     * Returns a list of all {@code T} from the database ordered by their primary key. 
     * The list is never null and is empty when the database does not contain any {@code T}.
     * 
     * @return              A list of all {@code T} from the database ordered by their primary key.
     * @throws DAOException If something fails at database level.
     */
    public abstract List<T> list() throws DAOException;

    /**
     * Create the given {@code T} in the database. 
     * The assignment of the primary key can be handled in here, but does not need to be.
     * 
     * @param  t The {@code T} to be created in the database.
     * @throws DAOException If something fails at database level.
     */
    public abstract void create(T t) throws DAOException;

    /**
     * Update the given {@code T} in the database. 
     * 
     * @param user The user to be updated in the database.
     * @throws DAOException If something fails at database level.
     */
    public abstract void update(T t) throws DAOException;

    /**
     * Delete the given {@code T} from the database. 
     * Primary key handling can be done here as well.
     *  
     * @param  t            The {@code T} object to be deleted from the database.
     * @throws DAOException If something fails at database level.
     */
    public abstract void delete(T t) throws DAOException;

    /**
     * Maps the current result to a {@code T} object
     * 
     * @param  resultSet    the set of info to map to this {@code T}
     * @return              an instance of {@code T} with the specified fields
     * @throws SQLException if 
     */
    protected abstract T map(ResultSet resultSet) throws SQLException;
}
