package edu.rutgers.model;

/**
 * EndUser model for the database.
 * Models the end-user relation for the backend.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class EndUser extends User {
    private static final long serialVersionUID = 1L;

    private Boolean bidAlert;

    public void setBidAlert(Boolean b) {
        bidAlert = b;
    }

    public Boolean getBidAlerts() {
        return bidAlert;
    }

    @Override
    public String toString() {
        return String.format("EndUser[login=%s, bid_alert=%s]", getLogin(), bidAlert ? "true" : "false");
    } 
}
