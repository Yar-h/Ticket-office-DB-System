package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.User;
import utils.Factory;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");
		String password = request.getParameter("password");

		User user = new User();
		try {
			user = Factory.getInstance().getUserDAO().getByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (user != null) {
			if (password.equals(user.getPassword())) {
				HttpSession session = request.getSession();
				session.setAttribute("name", name);

				session.setAttribute("type", user.getType());
				request.getRequestDispatcher("index.jsp").include(request,
						response);
				response.sendRedirect("#");
			} else {
				out.print("<div id=\"login\" style=\"h1{color:RED}\"><h1>Wrong password</h1></div>");
				request.getRequestDispatcher("index.jsp").include(request,
						response);
			}
		} else {
			out.print("<div id=\"login\"><h1>Wrong user</h1></div>");
			request.getRequestDispatcher("index.jsp").include(request,
					response);
		}
		out.close();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		response.sendRedirect("#");
	}

}
