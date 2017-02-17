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

import utils.Factory;
import classes.Station;

/**
 * Servlet implementation class StationServlet
 */
@WebServlet("/StationServlet")
public class StationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StationServlet() {
        super();
  
    }
    
	private void printForm(HttpServletRequest request, HttpServletResponse r) {
		r.setCharacterEncoding("UTF-8");
		boolean admin=false;
		if (request.getSession().getAttribute("type").equals("admin")) admin=true;
		try {
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Station</h1>");
			List<Station> result = Factory.getInstance().getStationDAO().getAll();
			result.sort(new Comparator<Station>() {
				public int compare(Station arg0, Station arg1) {
					if (arg0.getStationID()>arg1.getStationID()) return 1;
					if (arg0.getStationID()<arg1.getStationID()) return -1;
					return 0;
				};
			});
			String table;
			if (result!=null) {
				table="<details open=\"open\"><summary>Show table</summary><table>";
				table+="<tr><th>Id</th><th>Name</th><th>Phone</th><th>Address</th>";
					if (admin) table+= "<th></th><th></th>";
				table+= "</tr>";
				if (result.size()==0) table+="<tr><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td></tr>"; else
				{
					for (Station station : result) {
						table+="<tr>";
							table+="<td>"+station.getStationID()+"</td>";
							table+="<td>"+station.getName()+"</td>";
							table+="<td>"+station.getPhone()+"</td>";
							table+="<td>"+station.getAddress()+"</td>";
						if (admin) {
							table += "<td><img class=\"DelButtons\" id=\""
									+ station.getStationID();
							table += "\" onclick=\"delStation(this)\" width=30 src=\"images/icon_del.png\"></img></td>";
							table += "<td><img class=\"LoadButtons\" id=\""
									+ station.getStationID();
							table += "\" onclick=\"loadStation(this)\" width=25 src=\"images/icon_edit.png\"></img></td>";
						}
						table+="</tr>";
					}
				}
					table+="</table></details><br><br>";
					r.getWriter().print(table);;
				}
			if (admin) {
				r.getWriter()
						.print("<form id=\"stationForm\" name=\"stationForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"AddStation()\"><fieldset><legend>Add a station:</legend><p>    <label for=\"name\">Name:</label>    <input type=\"text\" name=\"name\" id=\"name\">  </p>  <p>    <label for=\"phone\">Phone number:</label>    <input type=\"text\" name=\"phone\" id=\"phone\">  </p>  <p>    <label for=\"address\">Address:</label>    <input type=\"text\" name=\"address\" id=\"address\">  </p><p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_station\" value=\"Отправить\">    			  </p></fieldset></form>");
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
			if (!Factory.isNumeric(request.getParameter("stationid"))){
				response.getWriter().write("<p>id не может быть пустым!</p>");
				return;
			}
			int delid = Integer.parseInt(request.getParameter("stationid"));
			if (delid < 0) {
				response.getWriter().write("<p>id '"+delid+"' не верен!</p>");
				return;
			}
			if (delid >= 0) {
				try {
					Factory.getInstance().getStationDAO().delete(delid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			printForm(request, response);
		} else if (action.matches("load")) {
			int editid = Integer.parseInt(request.getParameter("stationid"));
			printFormEdit(response, editid);
		} else if (action.matches("edit")) {
			
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			
			try {
				Station buf = Factory.getInstance().getStationDAO().getByID(Integer.parseInt(request.getParameter("id")));
				buf.setName(name);
				buf.setPhone(phone);	
				buf.setAddress(address);
				Factory.getInstance().getStationDAO().update(buf);			
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
			printForm(request, response);
		
		} else if (action.matches("add")){


		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		//------------------------------------------
		
		try {
			Station buf = new Station();
			buf.setName(name);
			buf.setPhone(phone);	
			buf.setAddress(address);
			Factory.getInstance().getStationDAO().add(buf);			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		printForm(request, response);
		}

	}

	private void printFormEdit(HttpServletResponse r, int editid) {
		r.setCharacterEncoding("UTF-8");
		try{
			r.getWriter()
					.print("<form id=\"stationForm\" name=\"stationForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"EditStation()\"><fieldset><legend>Edit the station:</legend><p>    "
							+ "<input hidden type=\"text\" name=\"id\" id=\"id\" value=\""
								+ Factory.getInstance().getStationDAO().getByID(editid).getStationID()+"\">"
							+ "<label for=\"name\">Name:</label>    <input type=\"text\" name=\"name\" id=\"name\" value=\""
								+ Factory.getInstance().getStationDAO().getByID(editid).getName()+"\">  </p>  <p>    "
							+ "<label for=\"phone\">Phone number:</label>    <input type=\"text\" name=\"phone\" id=\"phone\" value=\""
								+ Factory.getInstance().getStationDAO().getByID(editid).getPhone()+"\">  </p>  <p>    "
							+ "<label for=\"address\">Address:</label>    <input type=\"text\" name=\"address\" id=\"address\" value=\""
								+ Factory.getInstance().getStationDAO().getByID(editid).getAddress()+"\">  </p><p>    			    "
							+ "<input type=\"submit\" name=\"submit\" id=\"submit_station\" value=\"Отправить\">    			  </p></fieldset></form>");
		} catch (IOException | SQLException e) {
	
			e.printStackTrace();
		}
		
	}
}
