package edu.rutgers.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.User;
import edu.rutgers.util.Crypto;

/**
 * Admin servlet for creating new customer representatives
 */
@WebServlet("/admin/add-rep")
public class AddCustomerRepServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get customer support 
        request.getRequestDispatcher("/WEB-INF/views/admin/add-rep.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Attempt to add a new customer rep
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        String redirectURL = request.getContextPath() + "/admin/add-rep";

        // Create a user
        User user = new User();
        String salt = Long.toHexString(Calendar.getInstance().getTimeInMillis());
        String hash = Crypto.encrypt(request.getParameter("password"), salt);

        // Use the fields from the request to set up this user
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setHash(hash);
        user.setSalt(salt);

        // Add the user to the database as a customer representative
        if (userDao.find(user.getLogin()) == null) {
            userDao.create(user);
            userDao.addCustomerRep(user);
            redirectURL = request.getContextPath() + "/login";
        }

        response.sendRedirect(redirectURL);
    }
}