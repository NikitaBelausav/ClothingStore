<!-- Manage User Form -->
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags/forms" %>

<t:base title="Manage ${editUser.login}">
    <h2>Manage ${param.login}</h2>
    <tf:account-edit login="${editUser.login}" email="${editUser.email}" />
</t:base>