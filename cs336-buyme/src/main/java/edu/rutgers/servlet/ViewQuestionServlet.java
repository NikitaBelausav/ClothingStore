package edu.rutgers.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.rutgers.dao.DAOFactory;
import edu.rutgers.dao.QuestionDAO;
import edu.rutgers.dao.UserDAO;
import edu.rutgers.model.Question;
import edu.rutgers.model.User;

/**
 * Customer Support servlet for viewing a specific question
 */
@WebServlet("/support/view")
public class ViewQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        HttpSession session = request.getSession(false);
        User curUser = (User)session.getAttribute("user");

        Question question = null;

        if (request.getParameter("questionID") == null) {
            throw new IllegalArgumentException("No question ID specified for this servlet.");
        } else if ((question = questionDao.find(Integer.parseInt(request.getParameter("questionID")))) == null) {
            throw new IllegalArgumentException("Invalid question ID used.");
        } else {
            request.setAttribute("question", question);

            if (question.getAnswerText() != null && question.getCrLogin() != null) {
                request.setAttribute("content", 
                    "<h3>Answer by " + question.getCrLogin() + "</h3>" + System.lineSeparator() +
                    "<p>" + question.getAnswerText() + "</p>"
                );
            } else if (userDao.findCustomerRep(curUser.getLogin()) != null) {
                request.setAttribute("content",
                    "<a class=\"question__button\" href=\"./support/answer?questionID=" + question.getID() + "\">Answer Question</a>"
                );
            }

            request.getRequestDispatcher("/WEB-INF/views/support/view.jsp").forward(request, response);
        }
    }
}
