package edu.rutgers.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.dao.BidPostForDAO;
import edu.rutgers.dao.DAOFactory;
import edu.rutgers.model.BidPostFor;

/**
 * Customer support servlet for managing bids
 */
@WebServlet("/support/manage/bids")
public class ManageBidServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        BidPostForDAO bidDao = daoFactory.getBidPostForDAO();
        StringBuilder content = new StringBuilder();

        List<BidPostFor> bids = bidDao.list();

        // Dynamically populate content
        // We make a JS call here, but I couldn't care less.
        if (bids.isEmpty()) {
            content.append("<p>Sorry, no bids!</p>");
        } else {
            bids.forEach(b -> {
                content.append("<div class=\"bid\">");
                content.append("<p class=\"bid__text\">" + b + "</p>");
                content.append("<button onClick=\"askDelete(" + b.getBidNumber() + ")\">Delete bid</button>");
                content.append("</div>");
            });
        }

        request.setAttribute("content", content);

        request.getRequestDispatcher("/WEB-INF/views/support/manage/bids.jsp").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        BidPostForDAO bidDao = daoFactory.getBidPostForDAO();

        // Make sure that there is a bid to get 
        if (request.getParameter("bidID") == null)
            throw new IllegalArgumentException("Bid ID not specified.");
        else { 
            BidPostFor bid = bidDao.find(Integer.parseInt(request.getParameter("bidID")));
            if (bid == null)
                throw new ServletException("Invalid bid ID.");
            else
                bidDao.delete(bid);
        }

        response.sendRedirect(request.getContextPath() + "/support/manage/bids");
    }
}