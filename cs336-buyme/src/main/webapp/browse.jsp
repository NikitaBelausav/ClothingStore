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
 <%User user = (User) session.getAttribute("user");%>
	<h2>Item Alerts</h2>

	<p>Items and their open Auctions</p>
	<p>
		<% 
 
try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			String login = user.getLogin();
			Statement st = con.createStatement();
			//query to check what logged in user has bid on
			String checkItem = "select atran.auction_ID AuctionID, atran.name ItemName from item_alerts ialert, auction_transactions atran where atran.name like concat('%', ialert.name , '%') and ialert.login = '" + login + "' and atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time())";
			ResultSet rs = st.executeQuery(checkItem);
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
	</p>
 
 Add alert for item
	<br>
		<form method="post" action="itemAlert.jsp">
		<table>
		<tr>    
		<td>Item Name</td><td><input type="text" name="item_name"></td>
		</tr>
		</table>
		<input type="submit" value="Add Alert">
		</form>
	<br>
 
 Search Items By Name
	<br>
		<form method="post" action="searchBarByName.jsp">
		<table>
		<tr>    
		<td>Item Name</td><td><input type="text" name="item_name"></td>
		</tr>
		</table>
		<input type="submit" value="Search">
		</form>
	<br>
	 Search Items By Brand
	<br>
		<form method="post" action="searchBarByBrand.jsp">
		<table>
		<tr>    
		<td>Brand Name</td><td><input type="text" name="item_brand"></td>
		</tr>
		</table>
		<input type="submit" value="Search">
		</form>
	<br>
	 Search Items By Color
	<br>
		<form method="post" action="searchBarByColor.jsp">
		<table>
		<tr>    
		<td>Color</td><td><input type="text" name="item_color"></td>
		</tr>
		</table>
		<input type="submit" value="Search">
		</form>
	<br>
	 Search for Similar Items That Closed In The Preceding Month
	<br>
		<form method="post" action="searchBarByPrecedingMonth.jsp">
		<table>
		<tr>    
		<td>Item Name</td><td><input type="text" name="item_name"></td>
		<td>Item Color</td><td><input type="text" name="item_color"></td>
		<td>Item Brand</td><td><input type="text" name="item_brand"></td>
		</tr>
		</table>
		<input type="submit" value="Search">
		</form>
	<br>
		Input AuctionID you'd like to see all bid history for
	<br>
		<form method="post" action="historyOfBidSpecificAuction.jsp">
		<table>
		<tr>    
		<td>AuctionID</td><td><input type="text" name="auction_id"></td>
		</tr>
		</table>
		<input type="submit" value="Search">
		</form>
	<br>
	<form method="post" action="seeWhatYouBidFor.jsp">
	 <input type="submit" value="Search"> Click here to see all auctions you participated in
	</form>
		Sort Items
	<br>
		<form method="get" action="sortBy.jsp">
			<select name="sortBy" size=1>
				<option value="asc">Ascending Price</option>
				<option value="desc">Descending Price</option>
				<option value="pants">All pants</option>
				<option value="shoes">All shoes</option>
				<option value="shirt">All shirts</option>
			</select>&nbsp;<br> <input type="submit" value="submit">
		</form>
	<br>
	
	<br>
	All Not-Closed Item Listings
	<br>
	<% 
	
 		//First box in checklist just a list of all items and their current bids
 		List<String> list = new ArrayList<String>();
 
try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id	left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id";
			ResultSet rs = st.executeQuery(listOfItems);
			//build a table to display results
			out.print("<table>");
			out.print("<tr>");
			out.print("<td>");
			out.print("Item");
			out.print("</td>");
			out.print("<td>");
			out.print("Name");
			out.print("</td>");
			out.print("<td>");
			out.print("CurrentBid");
			out.print("</td>");
			out.print("<td>");
			out.print("AuctionID");
			out.print("</td>");
			while (rs.next()) {
				out.print("<tr>");
				out.print("<td>");
				out.print(rs.getString("item"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("Name"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("CurrentBid"));
				out.print("</td>");
				out.print("<td>");
				out.print(rs.getString("AuctionID"));
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