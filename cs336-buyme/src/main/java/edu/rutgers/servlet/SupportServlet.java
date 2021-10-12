package edu.rutgers.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.QuestionDAO;
import edu.rutgers.model.Question;

/**
 * Customer support servlet for displaying questions
 */
@WebServlet("/support")
public class SupportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        StringBuilder content = new StringBuilder();

        // Create a question display
        // From here we can simply pass it as an attribute to the view 
        List<Question> questions = questionDao.list();

        if (request.getParameter("search") != null) {
            StringBuilder queryBuilder = new StringBuilder();
            String[] keys = request.getParameter("search").split(" ");
            String queryString;

            for (String key : keys) {
                if (!key.matches("I|THE|YOU")) {
                    queryBuilder.append(".*\\b" + key + "\\b.*");
                    queryBuilder.append("|");
                }
            }

            queryBuilder.setLength(queryBuilder.length() - 1);
            queryString = queryBuilder.toString();

            questions.removeIf(q ->!q.getQuestionText().toLowerCase().matches(queryString));
        }

        if (questions.isEmpty()) {
            content.append("<p>Sorry, no questions!</p>");
        } else {
            questions.forEach(q -> {
                content.append("<div class=\"question\">");
                content.append("<a class=\"question__title\" href=\"./support/view?questionID=" + q.getID() + "\">");
                content.append("<h3>" + q.getQuestionText() + "<h3>");
                content.append("</a>");
                content.append("</div>");
            });
        }

        request.setAttribute("content", content.toString());
        request.getRequestDispatcher("/WEB-INF/views/support/index.jsp").forward(request, response);
    }
}