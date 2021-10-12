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
 All Auctions <%
 User user = (User) session.getAttribute("user");
 out.print(user);%>
 has bid for
 <% 
 		List<String> list = new ArrayList<String>();
 
try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			String login = user.getLogin();
			Statement st = con.createStatement();
			//query to check what logged in user has bid on
			String checkHistory = "select distinct bpf.auction_ID AuctionID, i.name ItemName from bid_posts_for bpf, item i, auction_transactions atran where bpf.login = \"" + login + "\"" + " and atran.auction_ID = bpf.auction_ID and i.item_ID = atran.item_ID";
			ResultSet rs = st.executeQuery(checkHistory);
			//build a table to display results
			out.print("<table>");
			out.print("<tr>");
			out.print("<td>");
			out.print("AuctionID");
			out.print("</td>");
			out.print("<td>");
			out.print("ItemName");
			out.print("</td>");
			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>");
				out.print(rs.getString("AuctionID"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("ItemName"));
				out.print("</td>");
				}
			out.print("</table>");
			rs.close();
			con.close();
		} catch (SQLException e) {
				throw new DAOException(e);
				}
%>

All Auctions <%
 out.print(user);%>
 has sold
 <% 

try {
		DAOFactory dao = new DAOFactory();
		Connection con = dao.getConnection();
		String login = user.getLogin();
		Statement st = con.createStatement();
		//query to check what logged in user has sold
		String checkHistorySell = "select atran.auction_ID AuctionID, atran.name ItemName from auction_transactions atran where atran.login = \"" + login + "\"";
		ResultSet rs = st.executeQuery(checkHistorySell);
		//build a table to display results
		out.print("<table>");
		out.print("<tr>");
		out.print("<td>");
		out.print("AuctionID");
		out.print("</td>");
		out.print("<td>");
		out.print("ItemName");
		out.print("</td>");
		while (rs.next()) {
			out.print("<tr>");
			out.print("<td>");
			out.print(rs.getString("AuctionID"));
			out.print("</td>");
			out.print("<td>");
			out.print(rs.getString("ItemName"));
			out.print("</td>");
			}
		out.print("</table>");
		rs.close();
		con.close();
	} catch (SQLException e) {
			throw new DAOException(e);
			}
%>
</body>