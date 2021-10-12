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
import edu.rutgers.model.EndUser;
import edu.rutgers.model.Question;
import edu.rutgers.model.User;

/**
 * Customer Support servlet for asking questions
 */
@WebServlet("/support/ask")
public class AskQuestionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        UserDAO userDao = daoFactory.getUserDAO();
        HttpSession session = request.getSession(false);

        // Make sure the user is logged in before asking a question
        if (session == null || session.getAttribute("user") == null) {
            throw new IllegalStateException("On ask page, but user is not logged in.");
        } else {
            EndUser eu = userDao.findEndUser(((User) session.getAttribute("user")).getLogin());

            if (eu == null)
                throw new IllegalStateException("On ask page, but user is not end-user.");
            
            request.setAttribute("euLogin", eu.getLogin());
        }

        request.getRequestDispatcher("/WEB-INF/views/support/ask.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = new DAOFactory();
        QuestionDAO questionDao = daoFactory.getQuestionDAO();
        String redirectURI = request.getContextPath() + "/support";

        Question question = new Question();

        question.setEuLogin(request.getParameter("euLogin"));
        question.setQuestionText(request.getParameter("question_text"));

        questionDao.create(question);

        response.sendRedirect(redirectURI);
    }
}
