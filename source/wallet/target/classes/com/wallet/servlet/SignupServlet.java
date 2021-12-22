package com.wallet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallet.db.DbConnectionProvider;
import com.wallet.db.DbUtils;
import com.wallet.pojo.User;
import com.wallet.util.WalletViews;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtils dbUtils = new DbUtils();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignupServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(WalletViews.SignUpView).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User(request.getParameter("username"), request.getParameter("email"),
		request.getParameter("password"));
		String query = "insert into user(username,email,password) values('" + user.getUsername() + "','" + user.getEmail() + "','" + user.getPassword()   +"')";
		Connection conn = DbConnectionProvider.getConnection();
		
		
		
		boolean userExists = dbUtils.checkUserExistence(user.getUsername());

		if (userExists) {
			request.setAttribute("userExists", true);
			request.setAttribute("currentUser", user.getUsername());
			request.getRequestDispatcher(WalletViews.SignUpView).forward(request, response);
			return;
		}

		try {
			Statement s = conn.createStatement();
			int success = s.executeUpdate(query);
			if (success == 1) {
				user = dbUtils.getUserByUserName(request.getParameter("username"));
				dbUtils.createUserWallet(user);
				request.setAttribute("message", "Successfully created the user.");
				request.setAttribute("user", user);
				request.getRequestDispatcher(WalletViews.UserDetailsView).forward(request, response);
				return;
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stacktracestr = "The user creation process failed. User might have been created but might be in an inconsitent state !!!! " + sw.toString();
			request.setAttribute("error", stacktracestr);
			request.getRequestDispatcher(WalletViews.ErrorPage).forward(request, response);
		}
	}
}
