<!-- Support Answer Form tag -->
<%@ tag description="Support Answer Form" pageEncoding="UTF-8"%>

<%@ attribute name="action" type="String" %>
<%@ attribute name="questionID" type="Integer" %>
<%@ attribute name="question" type="String" %>
<%@ attribute name="crLogin" type="String" %>

<form method="post" <% if (request.getParameter("action") != null) { %>action="${action}"<% } %> >
    <input type="hidden" name="questionID" value="${questionID}" />
    <input type="hidden" name="crLogin" value="${crLogin}" />

    <label for="answer_text">${question}</label><br>

    <textarea name="answer_text" placeholder="Write your answer here" rows="4" cols="50"></textarea>

    <input type="submit" value="Submit Answer" />
</form>