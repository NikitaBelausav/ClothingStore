package edu.rutgers.tag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.User;

/**
 * This tag gets the navlinks based on the current session.
 */
public class GetNavlinksTag extends TagSupport {
    private DAOFactory daoFactory = new DAOFactory();
    private UserDAO userDao = daoFactory.getUserDAO();

    private String itemClass;

    public void setItemClass(String c) {
        itemClass = c;
    }

    public String getItemClass() {
        return itemClass;
    }

    @Override
    public int doEndTag() throws JspException {
        // Start with a default itemClass empty string
        if (itemClass == null)
            itemClass = "";

        try {
            JspWriter out = pageContext.getOut();
            HttpSession session = pageContext.getSession();                
            if (session != null && session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");

                out.append("<a class=\"" + itemClass + "\" href=\"support\">Support</a>");
                out.append("<a class=\"" + itemClass + "\" href=\"profile\">Profile</a>");
                out.append("<a class=\"" + itemClass + "\" href=\"logout\">Logout</a>");

                if (userDao.getAdmin().equals(user)) { // Admin stuff
                    out.append("<a class=\"" + itemClass + "\" href=\"admin/add-rep\">Add Customer Representative</a>");
                } else if (userDao.findCustomerRep(user.getLogin()) != null) { // Customer Rep Stuff
                    out.append("<a class=\"" + itemClass + "\" href=\"support/manage/users\">Manage Users</a>");
                    out.append("<a class=\"" + itemClass + "\" href=\"support/manage/auctions\">Manage Auctions</a>");
                    out.append("<a class=\"" + itemClass + "\" href=\"support/manage/bids\">Manage Bids</a>");
                } else { // End user stuff
                    out.append("<a class=\"" + itemClass + "\" href=\"autobid.jsp\">Autobid</a>");
                    out.append("<a class=\"" + itemClass + "\" href=\"browse.jsp\">Browse</a>");
                }
            } else {
                out.append("<a class=\"" + itemClass + "\" href=\"login\">Login</a>");
                out.append("<a class=\"" + itemClass + "\" href=\"register\">Register</a>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}
