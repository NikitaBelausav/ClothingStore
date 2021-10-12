package edu.rutgers.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.Admin;
import edu.rutgers.model.User;
import edu.rutgers.util.Crypto;

/**
 * User servlet for viewing and editing their profile
 */
@WebServlet( "/profile" )
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        User user = (User) request.getSession(false).getAttribute("user");

        if (user == null || !userDao.find(user.getLogin()).equals(user))
            throw new IllegalStateException("Illegal session, invalid login information.");
        else 
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        String redirectURL = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // Create a user
        User user = userDao.find(request.getParameter("loginOld"));

        String newLogin = (String) request.getParameter("loginNew"); 
        String password = (String) request.getParameter("password");

        // Use the fields from the request to set up this user
        if (user != null) {
            user.setLogin(request.getParameter("loginOld"));
            user.setEmail(request.getParameter("email"));

            if (password != null) {
                user.setSalt(Long.toHexString(Calendar.getInstance().getTimeInMillis()));
                user.setHash(Crypto.encrypt(password, user.getSalt()));
            }
        }

        // Attempt to change the username, if applicable.
        if (newLogin != null && !newLogin.isEmpty() && !user.getLogin().equals(newLogin))
            userDao.updateLogin(user, request.getParameter("loginNew"));

        // Attempt to update the user info
        if (userDao.find(user.getLogin()) != null)
            userDao.update(user);

        // Update the user session object
        session.setAttribute("user", user);
        response.sendRedirect(redirectURL);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");

        if (user != null && !(user instanceof Admin)) {
            userDao.delete(user);
        }
        
        // Invalidate session
        session.invalidate();

        response.sendRedirect(request.getContextPath());
    }
}