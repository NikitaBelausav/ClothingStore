package edu.rutgers.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rutgers.util.URLQuery;

/**
 * Filter to check if a user is logged in.
 */
@WebFilter(filterName="LoginFilter")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";

        boolean isLoggedIn = session != null && session.getAttribute("user") != null;

        if (isLoggedIn)
            chain.doFilter(request, response);
        else {
            String queryString = URLQuery.encode(
                "redirectURI", 
                request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "")
            );

            response.sendRedirect(loginURI + "?" + queryString);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
