package edu.rutgers.dao;

import static edu.rutgers.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.rutgers.model.Question;

/**
 * This is the DAO to interface with the {@code Question} model.
 * 
 * @author Mikita Belausau
 * @author Muskan Burman
 * @author Dorian Hobot
 * @author Jared Tulayan
 * 
 * @see edu.rutgers.model.Question Question
 */
public class QuestionDAO extends DAO<Question> {
    public static final String SQL_LIST_QUESTIONS = "SELECT * FROM questions";

    public static final String SQL_FIND_QUESTION_BY_ID = "SELECT * FROM questions WHERE id=?";

    public static final String SQL_CREATE_QUESTION = "INSERT INTO questions (eu_login, cr_login, question_text, answer_text) VALUES (?, ?, ?, ?)";
    
    public static final String SQL_UPDATE_QUESTION = "UPDATE questions SET eu_login=?, cr_login=?, question_text=?, answer_text=? WHERE id=?";
    
    public static final String SQL_DELETE_QUESTION = "DELETE FROM questions WHERE id=?";

    public QuestionDAO(DAOFactory f) {
        super(f);
    }

    /**
     * Lists questions by question ID.
     * 
     * @return              a list of {@code Question} objects, sorted by ID
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public List<Question> list() throws DAOException {
        List<Question> questions = new ArrayList<>();

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_LIST_QUESTIONS, true);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a query of users.
            // if we get one, we can just fill it full of users.
            while (resultSet.next()) {
                questions.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return questions;
    }

    /**
     * Finds a question by ID.
     * 
     * @param  id           the question ID to look for
     * @return              a {@code Question} object with the given ID, 
     *                      or {@code null} if no {@code Question} was found
     * @throws DAOException if there is an issue with interfacing with the database
     */
    public Question find(Integer id) throws DAOException {
        Question question = null;

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_FIND_QUESTION_BY_ID, true, id);
            ResultSet resultSet = statement.executeQuery();
        ) {
            // Attempt to get a user.
            if (resultSet.next())
                question = map(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return question;
    }

    /**
     * Attempt to create a new question using the given info.
     * 
     * @param  question     the question to add to the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void create(Question question) throws DAOException {
        Object[] values = new Object[] {
            question.getEuLogin(),
            question.getCrLogin(),
            question.getQuestionText(),
            question.getAnswerText()
        };

        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_CREATE_QUESTION, true, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to create question, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Updates the question's information in the database, matched by the given {@code Question}.
     * <p>
     * Any field left {@code null} will not be updated.
     * 
     * @param  question     the question with which to base the change on
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void update(Question question) throws DAOException {
        Object[] values = new Object[] {
            question.getEuLogin(),
            question.getCrLogin(),
            question.getQuestionText(),
            question.getAnswerText(),
            question.getID()
        };


        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_UPDATE_QUESTION, false, values);
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to update question, no affected rows.");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Deletes the question from the database and sets their id to null.
     * 
     * @param  question     the question to delete from the database
     * @throws DAOException if there is an issue with interfacing with the database
     */
    @Override
    public void delete(Question question) throws DAOException {
        try (
            Connection connection = FACTORY.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_DELETE_QUESTION, false, question.getID());
        ) {
            if (statement.executeUpdate() == 0)
                throw new DAOException("Failed to delete question, no affected rows.");
            else
                question.setID(null);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Map the result set to a new {@code Question} object.
     * 
     * @param  resultSet    the {@code ResultSet} to use for mapping
     * @return              a {@code Question} with the fields from the {@code ResultSet}
     *                      mapped to its field
     * @throws SQLException if there is an issue accessing the values in the database
     */
    @Override
    protected Question map(ResultSet resultSet) throws SQLException {
        Question question = new Question();

        question.setID(resultSet.getInt("id"));
        question.setEuLogin(resultSet.getString("eu_login"));
        question.setCrLogin(resultSet.getString("cr_login"));
        question.setQuestionText(resultSet.getString("question_text"));
        question.setAnswerText(resultSet.getString("answer_text"));

        return question;
    }
    
}
