package edu.rutgers.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.User;

/**
 * Filter to test user permissions.
 * This is done with the premise that there is a valid session.
 * 
 */
public class PermissionsFilter implements Filter {
    private enum Level {
        END_USER,
        CUSTOMER_REP,
        ADMIN
    }

    private Level level;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        HttpSession session = request.getSession(false);
        boolean isAllowed = false;

        switch(level) {
            case ADMIN:
                isAllowed |= userDao.getAdmin().equals((User) session.getAttribute("user"));
            break;
            case CUSTOMER_REP:
                isAllowed |= userDao.findCustomerRep(((User) session.getAttribute("user")).getLogin()) != null;
            break;
            default:
                isAllowed |= userDao.findEndUser(((User) session.getAttribute("user")).getLogin()) != null;
            break;
        }

        if (isAllowed)
            chain.doFilter(request, response);
        else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Insufficient permissions to access this resource. Need " + level);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            level = Level.valueOf(filterConfig.getInitParameter("level"));
        } catch (Exception e) {
            throw new ServletException("Invalid permissions level " + filterConfig.getInitParameter("level"));
        }
    }

    @Override
    public void destroy() {}
}
