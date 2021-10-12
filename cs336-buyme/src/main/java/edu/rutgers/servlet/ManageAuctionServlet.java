package edu.rutgers.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.dao.AuctionTransactionDAO;
import edu.rutgers.dao.DAOFactory;
import edu.rutgers.model.AuctionTransaction;

/**
 * Customer support servlet for managing auctions
 */
@WebServlet("/support/manage/auctions")
public class ManageAuctionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        AuctionTransactionDAO auctionDao = daoFactory.getAuctionTransactionDAO();
        StringBuilder content = new StringBuilder();

        List<AuctionTransaction> auctions = auctionDao.list();

        // Dynamically populate content
        // We make a JS call here, unsafe but I couldn't care less.
        if (auctions.isEmpty()) {
            content.append("<p>Sorry, no auctions!</p>");
        } else {
            auctions.forEach(a -> {
                content.append("<div class=\"auction\">");
                content.append("<p class=\"auction__text\">" + a + "</p>");
                content.append("<button onClick=\"askDelete(" + a.getAuctionID() + ")\">Delete auction</button>");
                content.append("</div>");
            });
        }

        request.setAttribute("content", content);

        request.getRequestDispatcher("/WEB-INF/views/support/manage/auctions.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        AuctionTransactionDAO auctionDao = daoFactory.getAuctionTransactionDAO();

        // Make sure that there is a bid to get 
        if (request.getParameter("auctionID") == null)
            throw new IllegalArgumentException("Auction ID not specified.");
        else { 
            AuctionTransaction auction = auctionDao.find(Integer.parseInt(request.getParameter("auctionID")));
            if (auction == null)
                throw new ServletException("Invalid auction ID.");
            else
                auctionDao.delete(auction);
        }

        response.sendRedirect(request.getContextPath() + "/support/manage/auctions");
    }
}