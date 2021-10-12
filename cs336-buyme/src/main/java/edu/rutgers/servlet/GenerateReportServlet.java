package edu.rutgers.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.tag.GenerateReportTag;
import edu.rutgers.util.URLQuery;

/**
 * Admin servlet for generating sales reports
 */
@WebServlet("/admin/generate-report")
public class GenerateReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FORM_JSP = "/WEB-INF/views/admin/generate-report.jsp";
    private static final String OUT_JSP = "/WEB-INF/views/admin/report.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flagNum = (String) request.getParameter("flags");
        String jsp = FORM_JSP;

        // Either get the report
        // or get the generation form.
        if (flagNum != null) {
            request.setAttribute("flags", Integer.parseInt(flagNum));
            jsp = OUT_JSP;
        }  

        request.getRequestDispatcher(jsp).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Attempt to update the password
        int flags = 0;

        flags |= request.getParameter("fTotal") != null ? GenerateReportTag.TOTAL_EARNINGS : 0;
        flags |= request.getParameter("fBestSelling") != null ? GenerateReportTag.BEST_SELLING : 0;
        flags |= request.getParameter("fBestBuyers") != null ? GenerateReportTag.BEST_BUYERS : 0;
        flags |= request.getParameter("fPerItem") != null ? GenerateReportTag.EARNINGS_PER_ITEM : 0;
        flags |= request.getParameter("fPerType") != null ? GenerateReportTag.EARNINGS_PER_TYPE : 0;
        flags |= request.getParameter("fPerUser") != null ? GenerateReportTag.EARNINGS_PER_USER : 0;

        response.sendRedirect(request.getContextPath() + "/admin/generate-report?" + URLQuery.encode("flags", ""+ flags));
    }
}