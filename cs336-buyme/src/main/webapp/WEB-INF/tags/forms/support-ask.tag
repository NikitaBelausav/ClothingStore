<!-- Support Question Form tag -->
<%@ tag description="Support Question Form" pageEncoding="UTF-8"%>

<%@ attribute name="action" type="String" %>
<%@ attribute name="euLogin" type="String" %>

<form method="post" <% if (request.getParameter("action") != null) { %>action="${action}"<% } %> >
    <input type="hidden" name="euLogin" value="${euLogin}" />

    <label for="question_text">Question</label><br>

    <textarea name="question_text" placeholder="Ask your question here" rows="4" cols="50"></textarea>

    <input type="submit" value="Submit Question" />
</form>