package edu.rutgers.tag;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.rutgers.dao.AuctionTransactionDAO;
import edu.rutgers.dao.DAOFactory;

/**
 * This tag programmatically generates a sales report.
 * 
 */
public class GenerateReportTag extends TagSupport {
    public static final int
        TOTAL_EARNINGS = 0b000001,
        BEST_SELLING = 0b000010,
        BEST_BUYERS = 0b000100,
        EARNINGS_PER_ITEM = 0b001000,
        EARNINGS_PER_TYPE = 0b010000,
        EARNINGS_PER_USER = 0b100000;


    private DAOFactory daoFactory = new DAOFactory();
    private AuctionTransactionDAO auctionDao = daoFactory.getAuctionTransactionDAO();

    private int flags;

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();

    public void setFlags(int f) {
        flags = f;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public int doStartTag() throws JspException {
        try (JspWriter out = pageContext.getOut()){
            // Total Earnings
            if ((flags & TOTAL_EARNINGS) != 0) {
                float earnings = auctionDao.getTotalEarnings();

                out.write("<div class=report__total>");
                out.write("<h6>Total Earnings: " + CURRENCY_FORMAT.format(earnings));
                out.write("</div");
            }

            // Best Selling
            if ((flags & BEST_SELLING) != 0) {
                Map<String, Float> map = auctionDao.getBestSellingItems();

                out.write("<div class=report__best-selling>");
                out.write("<h6>Best Selling Items</h6>");
                out.write("<table class=report__table>");
                        out.write("<tr>");
                            out.write("<th>Item</th>");
                            out.write("<th>Amount</th>");
                        out.write("</tr>");
                map.forEach((item, amount) -> {
                    try {
                        out.write("<tr>");
                            out.write("<td>" + item + "</td>");
                            out.write("<td>" + CURRENCY_FORMAT.format(amount) + "</td>");
                        out.write("</tr>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); 
                out.write("</table>");
                out.write("</div");
            }

            // Best Buyers
            if ((flags & BEST_BUYERS) != 0) {
                Map<String, Float> map = auctionDao.getBestBuyers();

                out.write("<div class=report__best-buyers>");
                out.write("<h6>Best Buyers</h6>");
                out.write("<table class=report__table>");
                        out.write("<tr>");
                            out.write("<th>Buyer</th>");
                            out.write("<th>Amount</th>");
                        out.write("</tr>");
                map.forEach((buyer, amount) -> {
                    try {
                        out.write("<tr>");
                            out.write("<td>" + buyer + "</td>");
                            out.write("<td>" + CURRENCY_FORMAT.format(amount) + "</td>");
                        out.write("</tr>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); 
                out.write("</table>");
                out.write("</div");
            }

            // Earnings per item
            if ((flags & EARNINGS_PER_ITEM) != 0) {
                Map<String, Float> map = auctionDao.getEarningsPerItem();

                out.write("<div class=report__per-item>");
                out.write("<h6>Earnings per Item</h6>");
                out.write("<table class=report__table>");
                        out.write("<tr>");
                            out.write("<th>Item</th>");
                            out.write("<th>Earnings</th>");
                        out.write("</tr>");
                map.forEach((item, earnings) -> {
                    try {
                        out.write("<tr>");
                            out.write("<td>" + item + "</td>");
                            out.write("<td>" + CURRENCY_FORMAT.format(earnings) + "</td>");
                        out.write("</tr>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); 
                out.write("</table>");
                out.write("</div");
            }

            // Earnings per type
            if ((flags & EARNINGS_PER_TYPE) != 0) {
                Map<String, Float> map = auctionDao.getEarningsPerItemType();

                out.write("<div class=report__per-type>");
                out.write("<h6>Earnings per Item Type</h6>");
                out.write("<table class=report__table>");
                        out.write("<tr>");
                            out.write("<th>Type</th>");
                            out.write("<th>Earnings</th>");
                        out.write("</tr>");
                map.forEach((type, earnings) -> {
                    try {
                        out.write("<tr>");
                            out.write("<td>" + type + "</td>");
                            out.write("<td>" + CURRENCY_FORMAT.format(earnings) + "</td>");
                        out.write("</tr>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); 
                out.write("</table>");
                out.write("</div");
            }

            // Earnings per user
            if ((flags & EARNINGS_PER_USER) != 0) {
                Map<String, Float> map = auctionDao.getEarningsPerUser();

                out.write("<div class=report__per-user>");
                out.write("<h6>Earnings per User</h6>");
                out.write("<table class=report__table>");
                        out.write("<tr>");
                            out.write("<th>User</th>");
                            out.write("<th>Earnings</th>");
                        out.write("</tr>");
                map.forEach((user, earnings) -> {
                    try {
                        out.write("<tr>");
                            out.write("<td>" + user + "</td>");
                            out.write("<td>" + CURRENCY_FORMAT.format(earnings) + "</td>");
                        out.write("</tr>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }); 
                out.write("</table>");
                out.write("</div");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
