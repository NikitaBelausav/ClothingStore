package edu.rutgers.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.EndUser;
import edu.rutgers.model.User;
import edu.rutgers.util.Crypto;
import edu.rutgers.util.URLQuery;

/**
 * Customer support servlet for managing a user
 */
@WebServlet("/support/manage/users")
public class ManageUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String USER_JSP = "/WEB-INF/views/support/manage/user.jsp";
    private static final String USERS_JSP = "/WEB-INF/views/support/manage/users.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        String login = (String) request.getParameter("login");

        // Either get the list of users
        // or get the user form.
        if (login != null) {
            EndUser editUser = userDao.findEndUser(login);

            if (editUser != null) {
                request.setAttribute("editUser", editUser);
                request.getRequestDispatcher(USER_JSP).forward(request, response);
            } else
                throw new IllegalArgumentException("No user found with the login " + login + ".");
        } else { 
            StringBuilder content = new StringBuilder();
            List<EndUser> users = userDao.listEndUsers();
            
            if (users.isEmpty())
                content.append("<p>No users found!</p>");
            else {
                users.forEach(u -> {
                    content.append("<div class=\"user\">");
                    content.append("<p class=\"user__desc\">" + u.toString() + "</p>");
                    content.append("<a href=\"./support/manage/users?" + URLQuery.encode("login", u.getLogin()) + "\" class=\"user__edit\">Edit</a>");
                    content.append("</div>");
                }); 
            }

            request.setAttribute("content", content);
            request.getRequestDispatcher(USERS_JSP).forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Attempt to update the password
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        String redirectURL = request.getRequestURI() + "?" + URLQuery.encode("login", request.getParameter("loginOld"));

        // Create a user
        User u = userDao.find(request.getParameter("loginOld"));
        EndUser user = new EndUser();

        String newLogin = (String) request.getParameter("loginNew"); 
        String password = (String) request.getParameter("password");

        // Use the fields from the request to set up this user
        if (u != null) {
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

        response.sendRedirect(redirectURL);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();

        User user = userDao.find(request.getParameter("login"));

        if (user != null)
            userDao.delete(user);

        response.sendRedirect(request.getContextPath());
    }
}