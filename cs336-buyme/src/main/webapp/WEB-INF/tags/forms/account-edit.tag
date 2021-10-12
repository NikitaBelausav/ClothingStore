<!-- Account Edit Form tag -->
<%@ tag description="Account Form" pageEncoding="UTF-8"%>

<%@ attribute name="action" type="String" %>
<%@ attribute name="login" type="String" %>
<%@ attribute name="email" type="String" %>

<form id="account-edit" method="post" <% if (request.getParameter("action") != null) { %>action="${action}"<% } %> >
    <input type="hidden" name="loginOld" value="${login}" />

    <label for="loginNew">Login</label>
    <input type="text" name="loginNew" value="${login}" />

    <label for="password">Password</label>
    <input type="password" name="password" />

    <label for="email">E-Mail</label>
    <input type="text" name="email" value="${email}" />

    <button type="submit">Save changes</button> 
    <button type="button" onclick="tryDelete('${login}')">Delete ${login}</button>
</form>

<script>
    function tryDelete(login) {
        if (confirm("Are you sure you want to delete " + login + "?") == true) {
            alert("Deleting " + login + "!");
            fetch(window.location.pathname + "?login=" + login, {
              method: "DELETE",
              redirect: "follow"
            }).then(res => {
                self.location = res.url;
            });
        }
    }
</script>