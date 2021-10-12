package edu.rutgers.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import edu.rutgers.dao.DAOException;
import edu.rutgers.dao.DAOFactory;
import edu.rutgers.model.User;

/**
 * Servlet implementation class Autobid
 */
public class Autobid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Autobid() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("/webapp/autobid.jsp").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter output = response.getWriter();
		response.setContentType("text/html");

		int auctionId;
		double upperLimit;
		double bidIncrement;

		auctionId = Integer.parseInt(request.getParameter("auction_id"));

		upperLimit = Double.parseDouble(request.getParameter("upper_limit"));

		bidIncrement = Double.parseDouble(request.getParameter("bid_increment"));

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String login = user.getLogin();

		try {

			DAOFactory dao = new DAOFactory();
			Connection con = dao.getConnection();
			Statement st = con.createStatement();

			// check if the entered ID is valid
			String checkID = "select max(auction_id) as auction_id from auction_transactions;";
			Statement st2 = con.createStatement();
			ResultSet id = st2.executeQuery(checkID);
			id.next();
			int maxID = id.getInt("auction_id");
			id.close();


			if (auctionId <= maxID && checkAuctionClosing(st, auctionId)) {

				String previousAutobid = "select * from autobid where login = \"" + login + "\"";
				ResultSet rs = st.executeQuery(previousAutobid);
				if (rs.next()) {
					String entry = "update autobid set bid_increment = " + bidIncrement + ", upper_limit =" + upperLimit
							+ "where login=\"" + login + "\" and auction_ID =" + auctionId + ")";
					st.executeUpdate(entry);
					output.println("Autobid Updated");
				} else {
					String entry = "insert into Autobid values (\"" + login + "\"," + auctionId + "," + bidIncrement
							+ "," + upperLimit + ")";
					st.executeUpdate(entry);
					output.println("Autobid Created");

				}

			} else {
				output.println("Autobid rejected");
				output.println("<br>");
				output.println("Redirecting in 3 seconds...");
				response.setHeader("Refresh", "3; URL=enduser.jsp");

			}
			st.close();
			con.close();

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	// return true if auction has not ended yet
	boolean checkAuctionClosing(Statement st, int auctionId) {
		String auctionEndTime = "select close_time from Auction_Transactions where auction_ID =" + auctionId;
		String auctionEndDate = "select close_date from Auction_Transactions where auction_ID =" + auctionId;

		try {
			ResultSet rs = st.executeQuery(auctionEndTime);
			rs.next();

			LocalTime endTime = LocalTime.parse(rs.getString("close_time"));

			rs = st.executeQuery(auctionEndDate);
			rs.next();

			LocalDate endDate = LocalDate.parse(rs.getString("close_date"));
			LocalTime currentTime = LocalTime.now();
			LocalDate currentDate = LocalDate.now();

			rs.close();

			if (currentTime.compareTo(endTime) <= 0 && currentDate.compareTo(endDate) <= 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	double makeBid(Statement st, String login, int auctionID, double bidIncrement, double currentMaxBid) {

		try {

			// get max bid or set to 1 if no bids exist
			ResultSet bid = st.executeQuery("select max(bid_number) as max_bid_id from bid_posts_for");
			int bidId;

			if (bid.next()) {

				bidId = bid.getInt("max_bid_id");
				bidId++;

			} else {
				bidId = 1;
			}

			String currentTime = LocalTime.now().toString();
			String currentDate = LocalDate.now().toString();

			double amount = currentMaxBid + bidIncrement;

			String bidEntry = "insert into bid_posts_for values (" + bidId + ", \"" + login + "\", " + auctionID + ", "
					+ amount + ", \"" + currentDate + "\", \"" + currentTime + "\")";
			
			//System.out.println(bidEntry);
			st.executeUpdate(bidEntry);

			bid.close();

			return amount;

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	void runAutobid(Statement st, int auctionId) {

		String maxBid = "select max(amount) as amount from Bid_Posts_For where auction_ID = " + auctionId;
		String maxLogin = "select distinct login from bid_posts_for where auction_ID=" + auctionId
				+ " and amount = (select max(amount) as amount from bid_posts_for where auction_ID=" + auctionId + ")";
		String getAutobids = "select * from autobid where Auction_ID=" + auctionId;
		double currentMaxBid;
		String currentMaxLogin;

		try {

			ResultSet rs = st.executeQuery(maxBid);
			rs.next();
			currentMaxBid = rs.getFloat("amount");

			rs = st.executeQuery(maxLogin);
			rs.next();
			currentMaxLogin = rs.getString("login");

			rs = st.executeQuery(getAutobids);

			while (rs.next()) {

				String username = rs.getString("login");

				double upperLimit = rs.getFloat("upper_limit");
				double bidIncrement = rs.getFloat("bid_increment");

				if (currentMaxLogin.compareTo(username) != 0) {
					if (currentMaxBid + bidIncrement <= upperLimit) {
						rs.close();
						currentMaxBid = makeBid(st, username, auctionId, bidIncrement, currentMaxBid);
						currentMaxLogin = username;
						
						//System.out.println("new maxbid =" + currentMaxBid);
						//System.out.println("new max login =" + currentMaxLogin);
						
						rs = st.executeQuery(getAutobids);
					} else {
						// System.out.println("limit reached");
						continue;
					}
				} else {
					// System.out.println("same login");
					continue;
				}

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
