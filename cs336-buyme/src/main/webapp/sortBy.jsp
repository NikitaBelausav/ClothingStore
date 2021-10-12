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
 List<String> list = new ArrayList<String>();
 String asc = "asc";
 String desc = "desc";
 String pants = "pants";
 String shirt = "shirt";
 String shoes = "shoes";
 String entity = request.getParameter("sortBy");
 // sort by asc order
 if(entity.equals(asc)){
	 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id order by CurrentBid asc";
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
 }
 //sort by desc order
 else if(entity.equals(desc)){
	 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id order by CurrentBid desc";
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
 }
 else if(entity.equals(shirt)){
	 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where i.item_id = atran.item_id	and i.subcategory like '%shirt%' and atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id";
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
 }
 else if(entity.equals(pants)){
	 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where i.item_id = atran.item_id	and i.subcategory like '%pants%' and atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id";
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
 }
 else if(entity.equals(shoes)){
	 try {
			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();
			//query to select items and their current bids (currently is just selecting final price tho/add auctionID also)
			String listOfItems = "select i.item_id Item, i.name Name, max(coalesce(bpf.amount, 0)) CurrentBid, atran.auction_id AuctionID from item i join auction_transactions atran on i.item_id = atran.item_id left outer join bid_posts_for bpf on atran.auction_ID = bpf.auction_ID where i.item_id = atran.item_id	and i.subcategory like '%shoes%' and atran.close_date > current_date() || (atran.close_date = current_date() && atran.close_time > current_time()) group by i.item_id, i.name, atran.auction_id";
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
 }
 %>
 