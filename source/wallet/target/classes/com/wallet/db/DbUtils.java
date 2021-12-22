package com.wallet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wallet.pojo.Card;
import com.wallet.pojo.Transaction;
import com.wallet.pojo.User;
import com.wallet.pojo.Wallet;

public class DbUtils {
	
	/*
	 * Checks whether user with the given username exist in DB
	 * returns boolean
	 */
	public boolean checkUserExistence(String username) {
		String query = "SELECT * from user where username='" + username + "'";
		Connection conn = DbConnectionProvider.getConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (rs.getFetchSize() != 0) {
				System.out.println("user exists");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * Used to log in the user with the given username and password.
	 */
	public User login(String username, String password) throws SQLException {
		String query = "SELECT id,username,email,password from user where username='" + username + "' and password='" + password + "'";
		Statement statement = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			// ps = conn.prepareStatement(query);
			// ps.setString(1, username);
			// ps.setString(2, password);
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			User user = new User();
			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
			}
			return user;
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// return null;
	}
	
	/*
	 * Creates user wallet with an initial amount of '0.0'
	 */
	public boolean createUserWallet(User user) throws Exception {
		String query = "INSERT INTO wallet(cash,uid) VALUES(?,?)";

		Connection conn = DbConnectionProvider.getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setDouble(1, 0.0);
		ps.setInt(2, user.getId());
		int rs = ps.executeUpdate();
		if (rs==1) {
			System.out.println("User wallet created.");
			return true;
		}

		return false;
	}
	
	/*
	 * Retrieves user wallet balance
	 */
	public Wallet getUserWalletBalance(int userId) {
		String query = "SELECT id,cash,uid from wallet where uid=?";

		PreparedStatement ps = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			Wallet wallet = new Wallet();
			while (rs.next()) {
				wallet.setId(rs.getInt(1));
				wallet.setCash(rs.getDouble(2));
				wallet.setUid(rs.getInt(3));
			}
			return wallet;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * Updates user wallet balance with the given amount
	 */
	public Wallet updateUserWalletBalance(int userId,double cash) {
		
		Wallet uWallet = getUserWalletBalance(userId);
		
		String query = "UPDATE wallet SET cash=" + cash + " where uid=" + userId;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			Statement s = conn.createStatement();
			int rs = s.executeUpdate(query);
			if (rs==1) {
				uWallet.setCash(cash);
				System.out.println("User wallet updated.");
				return uWallet;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/*
	 * Gets user object based on the userId
	 */
	public User getUserById(int userId) {
		String query = "SELECT id,username,email,password from user where id=" + userId;
		
		Statement s = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			User user = new User();
			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * Gets User Object based on username
	 */
	public User getUserByUserName(String username) {
		String query = "SELECT id,username,email,password from user where username='" + username +"'";

		Statement s = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(query);
			User user = new User();
			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setPassword(rs.getString(4));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/*
	 * Do a fresh transaction
	 */
	public boolean doTransaction(Transaction trans) throws SQLException {
		String query = "INSERT INTO transactions(account, amount, user, comments) VALUES('" +trans.getAccount() +"'," + trans.getAmount() + "," + trans.getUser() + ",'" + trans.getComments()  + "')";

		Connection conn = DbConnectionProvider.getConnection();
		Statement s = conn.createStatement();
		try {
			int rs = s.executeUpdate(query);
			if (rs==1) {
				System.out.println("User has done a transaction.");
				return true;
			}
		}
		finally{
			s.close();
		}

		return false;
	}
	
	public List<Card> getCards(User user) {
		String query = "SELECT * FROM card where uid=?";
		
		List<Card> cards = new ArrayList<Card>();

		PreparedStatement ps = null;
		
		Card card = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, user.getId());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				card = new Card(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5));
				cards.add(card);
			}
			return cards;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean addCard(Card card) throws SQLException {
		String query = "INSERT INTO card(cardnumber, expirydate, cvv, uid) VALUES(?, ? , ?, ?)";

		Connection conn = DbConnectionProvider.getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, card.getCardNumber());
		ps.setDate(2, new java.sql.Date(card.getExpiry().getTime()) );
		ps.setInt(3, card.getCVV());
		ps.setInt(4, card.getUid());
		try {
			int rs = ps.executeUpdate();
			if (rs==1) {
				System.out.println("User has added a card.");
				return true;
			}
		}
		finally{
			ps.close();
		}
		return false;
	}
	public HashMap<String, String> getUserCard(int id) {
		String query = "SELECT card.cardnumber, user.username FROM card, user where card.uid = user.id and card.id=?";
		
		HashMap<String, String> map = new HashMap<>();

		PreparedStatement ps = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				map.put("cardnumber", rs.getString(1));
				map.put("username", rs.getString(2));
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public boolean deleteCard(int id) throws SQLException {
		String query = "delete from card where id = ?";

		Connection conn = DbConnectionProvider.getConnection();
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, id);
		try {
			int rs = ps.executeUpdate();
			if (rs==1) {
				System.out.println("User has deleted a card.");
				return true;
			}
		}
		finally{
			ps.close();
		}
		return false;
	}
	
	/*
	 * Retrieves the last n transactions of the user
	 */
	public List<Transaction> getTransactions(int number,User user) {
		String query = "SELECT id, account, amount, comments, time, user FROM transactions where user=? order by time desc limit ?";
		
		List<Transaction> transactions = new ArrayList<Transaction>();

		PreparedStatement ps = null;
		
		Transaction transaction = null;

		Connection conn = DbConnectionProvider.getConnection();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, user.getId());
			ps.setInt(2, number);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				transaction = new Transaction(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getDate(5), rs.getInt(6));
				transactions.add(transaction);
			}
			return transactions;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	

}
