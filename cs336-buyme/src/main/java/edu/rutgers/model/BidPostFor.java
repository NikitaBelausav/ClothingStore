package edu.rutgers.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bid post model for the database.
 * Models the generic bid posts for auction relation in the backend.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 */
public class BidPostFor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer bidNumber;
    private String login;
    private Integer auctionID;
    private Float amount;
    private Date bidDate;
    private Date bidTime;

    public void setBidNumber(Integer num) {
        bidNumber = num;
    }

    public Integer getBidNumber() {
        return bidNumber;
    }

    public void setLogin(String l) {
        login = l;
    }

    public String getLogin() {
        return login;
    }

    public void setAuctionID(Integer id) {
        auctionID = id;
    }

    public Integer getAuctionID() {
        return auctionID;
    }

    public void setAmount(Float a) {
        amount = a;
    }

    public Float getAmount() {
        return amount;
    }

    public void setBidDate(Date date) {
        bidDate = date;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidTime(Date time) {
        bidTime = time;
    }

    public Date getBidTime() {
        return bidTime;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof BidPostFor) ? bidNumber.equals(((BidPostFor)other).bidNumber) : (other == this);
    }

    @Override
    public int hashCode() {
        return (bidNumber != null) ? (this.getClass().hashCode() + bidNumber.hashCode()) : super.hashCode();
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        return String.format("BidPostFor[bidNumber=%d,login=%s,auctionID=%d,bidDate=%s,bidTime=%s]", 
            bidNumber,
            login,
            auctionID, 
            currencyFormat.format(amount),
            dateFormat.format(bidDate),
            timeFormat.format(bidTime)
        );
    } 
}
