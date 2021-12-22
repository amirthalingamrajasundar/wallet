package com.wallet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.text.SimpleDateFormat;  

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wallet.db.DbUtils;
import com.wallet.pojo.Card;
import com.wallet.pojo.User;
import com.wallet.pojo.Wallet;
import com.wallet.util.WalletViews;

/**
 * Servlet implementation class WalletServlet
 */
@WebServlet("/WalletServlet")
public class WalletServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtils dbUtils = new DbUtils();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WalletServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		try {
			User user = (User) session.getAttribute("user");
			Wallet wallet = dbUtils.getUserWalletBalance(user.getId());
			List<Card> cards  = dbUtils.getCards(user);
			request.setAttribute("cards", cards);
			
			request.setAttribute("wallet", wallet);
			request.setAttribute("user", user);
			request.getRequestDispatcher(WalletViews.WalletView).forward(request, response);
			return;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stacktracestr = sw.toString();
			request.setAttribute("error", stacktracestr);
			request.getRequestDispatcher(WalletViews.ErrorPage).forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");
		String cashString = request.getParameter("cash");
		Double cash = null;
		if (null == cashString || cashString == "") {
			request.setAttribute("message", "Invalid amount entered.");
			doGet(request, response);
			return;
		}
		try {
			cash = Double.parseDouble(cashString);
		} catch (NumberFormatException e) {
			request.setAttribute("message", "Invalid amount entered.");
			doGet(request, response);
			return;
		}
		
		Wallet userWallet = dbUtils.getUserWalletBalance(user.getId());
		
		Wallet updatedWallet = dbUtils.updateUserWalletBalance(user.getId(), userWallet.getCash()+cash);
		
		List<Card> cards  = dbUtils.getCards(user);

		request.setAttribute("wallet", updatedWallet);
		request.setAttribute("user", user);
		request.setAttribute("cards", cards);
		request.getRequestDispatcher(WalletViews.WalletView).forward(request, response);
	}

}
