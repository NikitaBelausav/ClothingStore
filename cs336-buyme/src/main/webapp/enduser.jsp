<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base context="${pageContext.request.contextPath}" title="User Portal">

	<meta charset="ISO-8859-1">
Welcome, ${user.login}!
<br>
	<a href="bid">Manually Bid</a>
	<br>
	<a href="autobid.jsp">Create Autobid</a>
	<a href="browse.jsp"> Browse Items </a>


</t:base>


