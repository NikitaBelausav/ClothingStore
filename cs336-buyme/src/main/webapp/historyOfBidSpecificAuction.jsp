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
 		//First box in checklist just a list of all items and their current bids
 		List<String> list = new ArrayList<String>();
 
try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			String auctionID = request.getParameter("auction_id");
			Statement st = con.createStatement();
			//query to select all bids for a specific auctionID
			String bidHistory = "select amount, bid_date, bid_time from bid_posts_for where auction_id = " + auctionID + " order by bid_number desc";
			ResultSet rs = st.executeQuery(bidHistory);
			//build a table to display results
			out.print("<table>");
			out.print("<tr>");
			out.print("<td>");
			out.print("Amount");
			out.print("</td>");
			out.print("<td>");
			out.print("Bid Date");
			out.print("</td>");
			out.print("<td>");
			out.print("Bid Time");
			out.print("</td>");
			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>");
				out.print(rs.getString("amount"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("bid_date"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("bid_time"));
				out.print("</td>");
				out.print("<td>");
			}
			out.print("</table>");
			rs.close();
			con.close();
		} catch (SQLException e) {
				throw new DAOException(e);
				}
%>
			</body>