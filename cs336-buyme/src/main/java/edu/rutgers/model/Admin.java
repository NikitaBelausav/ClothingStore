package edu.rutgers.model;

/**
 * Admin model for the database.
 * Models the admin relation for the backend.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class Admin extends User {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return String.format("Admin[login=%s]", getLogin());
    } 
}
