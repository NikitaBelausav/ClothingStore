<!-- Base Website tag -->
<%@ tag description="Base template" pageEncoding="UTF-8"%>
<%@ taglib prefix="u" uri="/WEB-INF/taglibs/util.tld" %>

<%@ attribute name="title" type="String" %>
<%@ attribute name="context" type="String" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <base href="/cs336-buyme/"> 
        <title><% if (title != null) { %>${title} | <% } %>BuyMe</title>
    </head>
    <body>
        <header>
            <a href="."><h1>BuyMe</h1></a>
        </header>
        <nav>
            <u:get-navlinks />
        </nav>
        <main>
            <jsp:doBody />
        </main>
        <footer>
        </footer>
    </body>
</html>