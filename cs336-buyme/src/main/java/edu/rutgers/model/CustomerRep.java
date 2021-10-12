package edu.rutgers.model;

/**
 * Customer representative model for the database.
 * Models the customer rep relation for the backend.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class CustomerRep extends User {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return String.format("CustomerRep[login=%s]", getLogin());
    } 
}
