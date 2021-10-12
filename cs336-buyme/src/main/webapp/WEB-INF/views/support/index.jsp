<!-- Customer Support index -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Customer Support">
    <h2>Customer Support</h2>
    <form method="get" action="./support">
        <label for="search">Search</label>
        <input type="text" name="search" placeholder="Enter search text here" value="${param.search}" required>
        <button type="submit">Search</button>
    </form>

    ${content}
</t:base>