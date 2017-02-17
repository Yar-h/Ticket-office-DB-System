package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Factory;
import classes.Passenger;

/**
 * Servlet implementation class PassengerServlet
 */
@WebServlet("/PassengerServlet")
public class PassengerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassengerServlet() {
        super();
 
    }
    
	private void printForm(HttpServletRequest request, HttpServletResponse r) {
		r.setCharacterEncoding("UTF-8");
		boolean admin=false;
		if (request.getSession().getAttribute("type").equals("admin")) admin=true;
		try {
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><h1>Passenger</h1>");
			List<Passenger> result = Factory.getInstance().getPassengerDAO().getAll();
			result.sort(new Comparator<Passenger>() {
				public int compare(Passenger arg0, Passenger arg1) {
					if (arg0.getPassengerID()>arg1.getPassengerID()) return 1;
					if (arg0.getPassengerID()<arg1.getPassengerID()) return -1;
					return 0;
				};
			});
			String table;
			if (result!=null) {
				table="<details open=\"open\"><summary>Show table</summary><table>";
				table+="<tr><th>Id</th><th>FIO</th><th>Address</th><th>Birth date</th>";
					if (admin) table+= "<th></th><th></th>";
				table+= "</tr>";
				if (result.size()==0) table+="<tr><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td></tr>"; else
				{
					for (Passenger passenger : result) {
						table+="<tr>";
							table+="<td>"+passenger.getPassengerID()+"</td>";
							table+="<td>"+passenger.getFIO()+"</td>";
							table+="<td>"+passenger.getAddress()+"</td>";
							String date_result=new SimpleDateFormat("yyyy-MM-dd").format(passenger.getBirth_date());
							table+="<td>"+date_result+"</td>";
						if (admin) {
							table += "<td><img class=\"DelButtons\" id=\""
									+ passenger.getPassengerID();
							table += "\" onclick=\"delPassenger(this)\" width=30 src=\"images/icon_del.png\"></img></td>";
							table += "<td><img class=\"LoadButtons\" id=\""
									+ passenger.getPassengerID();
							table += "\" onclick=\"loadPassenger(this)\" width=25 src=\"images/icon_edit.png\"></img></td>";
						}
						table+="</tr>";
					}
				}
					table+="</table></details><br><br>";
					r.getWriter().print(table);;
				}
			if (admin) {
				r.getWriter()
						.print("<form id=\"passengerForm\" name=\"passengerForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"AddPassenger()\"><fieldset><legend>Add a passenger:</legend><p>    <label for=\"FIO\">FIO:</label>    <input type=\"text\" name=\"FIO\" id=\"FIO\">  </p>  <p>    <label for=\"date_of_birth\">Date of Birth:</label>    <input type=\"date\" name=\"date_of_birth\" id=\"date_of_birth\">  </p>  <p>    <label for=\"adress\">Address:</label>    <input type=\"text\" name=\"address\" id=\"address\">  </p><p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_passenger\" value=\"Отправить\">    			  </p></fieldset></form>");
				r.getWriter().print("</html>");
			}
		} catch (IOException | SQLException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		printForm(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String action=request.getParameter("action");
		
		if (action.matches("del")) {
			if (!Factory.isNumeric(request.getParameter("passengerid"))){
				response.getWriter().write("<p>id не может быть пустым!</p>");
				return;
			}
			int delid = Integer.parseInt(request.getParameter("passengerid"));
			if (delid < 0) {
				response.getWriter().write("<p>id '"+delid+"' не верен!</p>");
				return;
			}
			if (delid >= 0) {
				try {
					Factory.getInstance().getPassengerDAO().delete(delid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			printForm(request, response);
		} else if (action.matches("load")) {
			int editid = Integer.parseInt(request.getParameter("passengerid"));
			printFormEdit(response, editid);
		} else if (action.matches("edit")) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp date = new Timestamp((new Date().getTime()));
			String FIO = request.getParameter("FIO");
		
			try {
				date = new Timestamp(sdf.parse(request.getParameter("date_of_birth")).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			String address = request.getParameter("address");
			
			//------------------------------------------
			
			try {
				Passenger buf = Factory.getInstance().getPassengerDAO().getByID(Integer.parseInt(request.getParameter("id")));
				buf.setFIO(FIO);
				buf.setAddress(address);	
				buf.setBirth_date(date);
				Factory.getInstance().getPassengerDAO().update(buf);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			printForm(request, response);
		
		} else if (action.matches("add")){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp date = new Timestamp((new Date().getTime()));
		String FIO = request.getParameter("FIO");
	
		try {
			date = new Timestamp(sdf.parse(request.getParameter("date_of_birth")).getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String address = request.getParameter("address");
		
		//------------------------------------------
		
		try {
			Passenger buf = new Passenger();
			buf.setFIO(FIO);
			buf.setAddress(address);	
			buf.setBirth_date(date);
			Factory.getInstance().getPassengerDAO().add(buf);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		printForm(request, response);
		}

	}

	private void printFormEdit(HttpServletResponse r, int editid) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			r.getWriter()
				.print("<form id=\"passengerForm\" name=\"passengerForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"EditPassenger()\"><fieldset><legend>Edit the passenger:</legend><p>"
					+ "<input hidden type=\"text\" name=\"id\" id=\"id\" value=\""+String.valueOf(editid)+"\">    "
					+ "<label for=\"FIO\">FIO:</label>    <input type=\"text\" name=\"FIO\" id=\"FIO\" value=\""+Factory.getInstance().getPassengerDAO().getByID(editid).getFIO()+"\">  </p>  <p>    "
					+ "<label for=\"date_of_birth\">Date of Birth:</label>   <input type=\"date\" name=\"date_of_birth\" id=\"date_of_birth\" value=\""+sdf.format(Factory.getInstance().getPassengerDAO().getByID(editid).getBirth_date())+"\">  </p>  <p>    "
					+ "<label for=\"adress\">Address:</label>    <input type=\"text\" name=\"address\" id=\"address\" value=\""+Factory.getInstance().getPassengerDAO().getByID(editid).getAddress()+"\">  </p><p>    			    "
					+ "<input type=\"submit\" name=\"submit\" id=\"submit_passenger\" value=\"Отправить\">    			  </p></fieldset></form>");
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
