package com.wallet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallet.db.DbUtils;
import com.wallet.pojo.User;
import com.wallet.util.WalletViews;

/**
 * Servlet implementation class SigninServlet
 */
@WebServlet("/SigninServlet")
public class SigninServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtils dbUtils = new DbUtils();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SigninServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(WalletViews.SignInView).forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String uName = request.getParameter("uName");
		String pWord = request.getParameter("pWord");
		
		if (uName == "" || pWord == "") {
			request.setAttribute("message", "User Name (or) Password shouldn't be empty");
			request.getRequestDispatcher(WalletViews.SignInView).forward(request, response);
			return;
		}
		try {
			User user = dbUtils.login(uName, pWord);
			
			if (null == user.getEmail()) {
				request.setAttribute("message", "Login failed. Please check User Name and Password.");
				request.getRequestDispatcher(WalletViews.SignInView).forward(request, response);
				return;
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user);
				request.setAttribute("message", "User successfully logged in.");
				request.setAttribute("user", user);
				request.getRequestDispatcher(WalletViews.UserDetailsView).forward(request, response);
			}
		}
		catch (SQLException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stacktracestr = sw.toString();
			request.setAttribute("error", stacktracestr);
			request.getRequestDispatcher(WalletViews.ErrorPage).forward(request, response);
		}
	}

}
