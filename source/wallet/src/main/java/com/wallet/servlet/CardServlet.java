package com.wallet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

public class CardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtils dbUtils = new DbUtils();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardServlet() {
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
            String path = request.getRequestURI();
            if (path != null && path.contains("deleteCard")) {
                int id = Integer.parseInt(request.getParameter("id"));
                HashMap<String, String> map = dbUtils.getUserCard(id);
                if(dbUtils.deleteCard(id)) {
                    request.setAttribute("deletecardsuccess", map.get("username") + "'s card " + map.get("cardnumber") + " has been deleted.");
                }
                else {
                    request.setAttribute("deletecardserror", "Failed to delete " + map.get("username") + "'s card " + map.get("cardnumber"));
                }
            }
			List<Card> cards  = dbUtils.getCards(user);
			request.setAttribute("cards", cards);
			request.getRequestDispatcher(WalletViews.CardView).forward(request, response);
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
		String cardNumber = request.getParameter("cardnumber");
        Date expiry = Date.valueOf(request.getParameter("expiry"));
        int cvv = Integer.parseInt(request.getParameter("cvv"));
		int uid = user.getId();
        System.out.println("card uid" + uid);
        Card card = new Card(cardNumber, expiry, cvv, uid);

		try {
		    dbUtils.addCard(card);
            request.setAttribute("cardSuccess", "Card added successfully");
			doGet(request, response);
			return;
		}
		catch (SQLException e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stacktracestr = sw.toString();
            String error;
            if (stacktracestr.contains("Duplicate entry")) {
                error = "Card number already exists";
            }
            else {
                error = "Card addition failed due to unknown error";
            }

            request.setAttribute("cardError", error);
			doGet(request, response);
		}
        return;
	}

}
