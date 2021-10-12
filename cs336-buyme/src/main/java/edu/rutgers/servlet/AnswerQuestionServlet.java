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
import edu.rutgers.model.CustomerRep;
import edu.rutgers.model.Question;
import edu.rutgers.model.User;

/**
 * Customer Support servlet for answering questions as a customer representative.
 */
@WebServlet("/support/answer")
public class AnswerQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        HttpSession session = request.getSession(false);

        // You need a valid question ID to go here
        if (request.getParameter("questionID") == null)
            throw new IllegalArgumentException("No question ID specified");
        else  {
            Question question = questionDao.find(Integer.parseInt(request.getParameter("questionID")));

            if (question == null)
                throw new IllegalArgumentException("Invalid question ID given.");

            request.setAttribute("question", question);
        }

        // Make sure the user is logged in before asking a question
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalStateException("On answer page, but user is not logged in.");
        } else {
            CustomerRep rep = userDao.findCustomerRep(((User) session.getAttribute("user")).getLogin());

            if (rep == null) 
                throw new IllegalStateException("On answer page, but not as a customer rep.");
            
            request.setAttribute("crLogin", rep.getLogin());
        }

        request.getRequestDispatcher("/WEB-INF/views/support/answer.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        String redirectURI = request.getContextPath() + "/support";

        Question question = questionDao.find((Integer.parseInt(request.getParameter("questionID"))));

        question.setCrLogin(request.getParameter("crLogin"));
        question.setAnswerText(request.getParameter("answer_text"));

        questionDao.update(question);

        response.sendRedirect(redirectURI);
    }
}
