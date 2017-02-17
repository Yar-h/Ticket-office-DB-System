package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Transport;
import utils.Factory;

/**
 * Servlet implementation class TransportServlet
 */
@WebServlet("/TransportServlet")
public class TransportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TransportServlet() {
		super();

	}

	private void printForm(HttpServletRequest request, HttpServletResponse r) {
		boolean admin=false;
		if (request.getSession().getAttribute("type").equals("admin")) admin=true;
		r.setCharacterEncoding("UTF-8");
		try {
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Transport</h1>");
			
			List<Transport> result = Factory.getInstance().getTransportDAO().getAll();
			result.sort(new Comparator<Transport>() {
				public int compare(Transport arg0, Transport arg1) {
					if (arg0.getTransID()>arg1.getTransID()) return 1;
					if (arg0.getTransID()<arg1.getTransID()) return -1;
					return 0;
				};
			});
			String table;
			if (result!=null) {
				table="<details open=\"open\"><summary>Show table</summary><table>";
				table+="<tr><th>Id</th><th>Type</th><th>Seat count</th>";
					if (admin) table +="<th></th><th></th>";
				table+= "</tr>";
				if (result.size()==0) table+="<tr><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td></tr>"; else
				{
					for (Transport transport : result) {
						table+="<tr>";
							table+="<td>"+transport.getTransID()+"</td>";
							table+="<td>"+transport.getType()+"</td>";
							table+="<td>"+transport.getSeat_cnt()+"</td>";
						if (admin) {
							table += "<td><img class=\"DelButtons\" id=\""
									+ transport.getTransID();
							table += "\" onclick=\"delTransport(this)\" width=30 src=\"images/icon_del.png\"></img></td>";
							table += "<td><img class=\"LoadButtons\" id=\""
									+ transport.getTransID();
							table += "\" onclick=\"loadTransport(this)\" width=25 src=\"images/icon_edit.png\"></img></td>";
						}
						table+="</tr>";
					}
				}
					table+="</table></details><br><br>";
					r.getWriter().print(table);;
				}
			if (admin) {
				r.getWriter()
						.print("<form id=\"transportForm\" name=\"transportForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"AddTransport()\"><fieldset><legend>Add a transport:</legend><p>    			    <label for=\"type\">Type:</label>    			    <input type=\"text\" name=\"type\" id=\"type\">    			  </p>    			  <p>    			    <label for=\"seat_cnt\">Seat count:</label>    			    <input type=\"text\" name=\"seat_cnt\" id=\"seat_cnt\">    			  </p>    			  <p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_trans\" value=\"Отправить\">    			  </p></fieldset></form>");
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		printForm(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String action=request.getParameter("action");
		
		if (action.matches("del")) {
			if (!Factory.isNumeric(request.getParameter("transid"))){
				response.getWriter().write("<p>id не может быть пустым!</p>");
				return;
			}
			int delid = Integer.parseInt(request.getParameter("transid"));
			if (delid < 0) {
				response.getWriter().write("<p>id '"+delid+"' не верен!</p>");
				return;
			}
			if (delid >= 0) {
				try {
					Factory.getInstance().getTransportDAO().delete(delid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			printForm(request, response);
		} else if (action.matches("load")) {
			int editid = Integer.parseInt(request.getParameter("transid"));
			printFormEdit(response, editid);
		} else if (action.matches("edit")) {
			
			String temp;
			
			String type = request.getParameter("type");
			temp = request.getParameter("seat_cnt");
			long seat_cnt = 0;
			if (temp != null)
				seat_cnt = Integer.parseInt(temp);			
			//------------------------------------------
			
			try {
				Transport buf = Factory.getInstance().getTransportDAO().getByID(Integer.parseInt(request.getParameter("id")));
				buf.setType(type);
				buf.setSeat_cnt(seat_cnt);			
				Factory.getInstance().getTransportDAO().update(buf);	
			} catch (SQLException e) {

				e.printStackTrace();
			}
			printForm(request, response);
		
		} else if (action.matches("add")){
		
		String temp;
		
		String type = request.getParameter("type");
		temp = request.getParameter("seat_cnt");
		long seat_cnt = 0;
		if (temp != null)
			seat_cnt = Integer.parseInt(temp);			
		//------------------------------------------
		
		try {
			Transport buf = new Transport();
			buf.setType(type);
			buf.setSeat_cnt(seat_cnt);			
			Factory.getInstance().getTransportDAO().add(buf);			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		printForm(request, response);
		}

	}

	private void printFormEdit(HttpServletResponse r, int editid) {
		r.setCharacterEncoding("UTF-8");
		try {
			r.getWriter()
				.print("<form id=\"transportForm\" name=\"transportForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"EditTransport()\"><fieldset><legend>Edit the transport:</legend><p>    			    "
						+ "<input hidden type=\"text\" name=\"id\" id=\"id\" value=\""
							+ Factory.getInstance().getTransportDAO().getByID(editid).getTransID()+"\">"
						+ "<label for=\"type\">Type:</label>  <input type=\"text\" name=\"type\" id=\"type\" value=\""
							+ Factory.getInstance().getTransportDAO().getByID(editid).getType()+"\">   </p>  <p>   "
						+ "<label for=\"seat_cnt\">Seat count:</label>    	 <input type=\"text\" name=\"seat_cnt\" id=\"seat_cnt\" value=\""
							+ Factory.getInstance().getTransportDAO().getByID(editid).getSeat_cnt()+"\">  </p>   <p>    "
						+ "<input type=\"submit\" name=\"submit\" id=\"submit_trans\" value=\"Отправить\">    			  </p></fieldset></form>");
		} catch (IOException | SQLException e) {

			e.printStackTrace();
		}
		
	}
	
}
