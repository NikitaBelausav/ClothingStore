<!-- Account Create Form tag -->
<%@ tag description="Account Create Form" pageEncoding="UTF-8"%>

<%@ attribute name="action" type="String" %>

<form method="post" <% if (request.getParameter("action") != null) { %>action="${action}"<% } %> >
    <label for="login">Login</label>
    <input type="text" name="login" />

    <label for="password">Password</label>
    <input type="password" name="password" />

    <label for="email">E-Mail</label>
    <input type="text" name="email" />

    <input type="submit" value="Submit" />
</form>