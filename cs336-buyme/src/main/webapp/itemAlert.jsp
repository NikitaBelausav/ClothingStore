<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<%@ page
	import="java.io.IOException,
 java.sql.Connection,
 java.sql.ResultSet,
 java.sql.SQLException,
 java.sql.Statement,
 java.time.LocalDate,
 java.time.LocalTime,
 javax.servlet.http.HttpSession,
 edu.rutgers.dao.DAOException,
 edu.rutgers.dao.DAOFactory,
 edu.rutgers.model.User,
 java.lang.String, 
 java.util.ArrayList,
 java.util.List, 
 java.lang.System"%>
 <body>
 <%
 User user = (User) session.getAttribute("user");
 
 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			String login = user.getLogin();
			Statement st = con.createStatement();
			String item_name = request.getParameter("item_name");
			String addAlert = "insert into item_alerts VALUES ('" + login + "' , '" + item_name + "')";
			int rs = st.executeUpdate(addAlert);
 } catch (SQLException e) {
		throw new DAOException(e);
		}
			%>