package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import classes.Ticket;
import classes.Trip;

/**
 * Servlet implementation class TicketServlet
 */
@WebServlet("/TicketServlet")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
        super();
       
    }
    
	private void printForm(HttpServletRequest request, HttpServletResponse r) {
		r.setCharacterEncoding("UTF-8");
		boolean admin=false;
		if (request.getSession().getAttribute("type").equals("admin")) admin=true;
		try {
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Ticket</h1>");
			
			List<Ticket> result = Factory.getInstance().getTicketDAO().getAll();
			result.sort(new Comparator<Ticket>() {
				public int compare(Ticket arg0, Ticket arg1) {
					if (arg0.getTicketId()>arg1.getTicketId()) return 1;
					if (arg0.getTicketId()<arg1.getTicketId()) return -1;
					return 0;
				};
			});
			String table;
			if (result!=null) {
				table = "<details open=\"open\"><summary>Show table</summary><table>";
				table += "<tr><th>Id</th><th>Category</th><th>Price</th><th>Sell date</th><th>Reserve date</th><th>Trip id</th><th>Passenger id</th>";
				if (admin)
					table += "<th></th><th></th>";
				table += "</tr>";
				if (result.size()==0) table+="<tr><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td></tr>"; else
				{
					for (Ticket ticket : result) {
						table+="<tr>";
							table+="<td>"+ticket.getTicketId()+"</td>";
							table+="<td>"+ticket.getCategory()+"</td>";
							table+="<td>"+ticket.getPrice()+"</td>";
							table+="<td>"+new SimpleDateFormat("dd-MM-yyyy").format(ticket.getSell_date())+"</td>";
							table+="<td>"+new SimpleDateFormat("dd-MM-yyyy").format(ticket.getReservation_date())+"</td>";
							table+="<td>"+ticket.getTrip_num().getTripID()+"</td>";
							table+="<td>"+ticket.getPass_id().getPassengerID()+"</td>";
						if (admin) {
							table += "<td><img class=\"DelButtons\" id=\""
									+ ticket.getTicketId();
							table += "\" onclick=\"delTicket(this)\" width=30 src=\"images/icon_del.png\"></img></td>";
							table += "<td><img class=\"LoadButtons\" id=\""
									+ ticket.getTicketId();
							table += "\" onclick=\"loadTicket(this)\" width=25 src=\"images/icon_edit.png\"></img></td>";
						}
						table+="</tr>";
					}
				}
					table+="</table></details><br><br>";
					r.getWriter().print(table);;
				}
			if (admin){
			r.getWriter()
					.print("<form id=\"ticketForm\" name=\"ticketForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"AddTicket()\"><fieldset><legend>Add a ticket:</legend>  <p>    <label for=\"category\">Category:</label>    <select name=\"category\" id=\"category\"><option disabled>Choose a category</option><option selected >Common</option><option >Premium</option></select>  </p>  <p>    <label for=\"price\">Price:</label>    <input type=\"text\" name=\"price\" id=\"price\">  </p>  <p>    <label for=\"sell_date\">Sell date:</label>    <input type=\"date\" name=\"sell_date\" id=\"sell_date\">  </p>    <p>    <label for=\"reserve_date\">Reserve date:</label>    <input type=\"date\" name=\"reserve_date\" id=\"reserve_date\">  </p>");
			List<Trip> trip_list = Factory.getInstance().getTripDAO().getAll();
			List<Passenger> passenger_list = Factory.getInstance().getPassengerDAO().getAll();
			trip_list.sort(new Comparator<Trip>() {
				public int compare(Trip arg0, Trip arg1) {
					if (arg0.getTripID()>arg1.getTripID()) return 1;
					if (arg0.getTripID()<arg1.getTripID()) return -1;
					return 0;
				};
			});
			passenger_list.sort(new Comparator<Passenger>() {
				public int compare(Passenger arg0, Passenger arg1) {
					if (arg0.getPassengerID()>arg1.getPassengerID()) return 1;
					if (arg0.getPassengerID()<arg1.getPassengerID()) return -1;
					return 0;
				};
			});
			
			// Select menu (Trip)
				r.getWriter().print(" <p>   <label for=\"trip_id\">Trip:</label>    <select type=\"text\" name=\"trip_id\" id=\"trip_id\">");
				r.getWriter().print("<option selected disabled>Choose a trip</option>");
				for (Trip trip : trip_list) {
					r.getWriter().print("<option>"+trip.getTripID()+" ("+trip.getStation_start().getName()+" - "+trip.getStation_end().getName()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Trip) END
				
				// Select menu (Passenger)
					r.getWriter().print(" <p>   <label for=\"passenger_id\">Passenger id:</label>    <select type=\"text\" name=\"passenger_id\" id=\"passenger_id\">");
					r.getWriter().print("<option selected disabled>Choose a passenger</option>");
					for (Passenger passenger : passenger_list) {
						r.getWriter().print("<option>"+passenger.getPassengerID()+" ("+passenger.getFIO()+")</option>");
					}
					r.getWriter().print("</select></p>");
				// Select menu (Passenger) END
			r.getWriter()
					.print("<p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_trans\" value=\"Отправить\">    			  </p></fieldset>    			</form>");
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
			if (!Factory.isNumeric(request.getParameter("ticketid"))){
				response.getWriter().write("<p>id не может быть пустым!</p>");
				return;
			}
			int delid = Integer.parseInt(request.getParameter("ticketid"));
			if (delid < 0) {
				response.getWriter().write("<p>id '"+delid+"' не верен!</p>");
				return;
			}
			if (delid >= 0) {
				try {
					Factory.getInstance().getTicketDAO().delete(delid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			printForm(request, response);
		} else if (action.matches("load")) {
			int editid = Integer.parseInt(request.getParameter("ticketid"));
			printFormEdit(response, editid);
		} else if (action.matches("edit")) {

			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp sell_date = new Timestamp((new Date().getTime()));
			Timestamp reserve_date = new Timestamp((new Date().getTime()));
			
			String category = request.getParameter("category");
			String price = request.getParameter("price");
			String trip_name = request.getParameter("trip_id");
			String passenger= request.getParameter("passenger_id");
			
			int trip_id = Integer.parseInt(trip_name.substring(0, trip_name.indexOf(" (")));
			int passenger_id = Integer.parseInt(passenger.substring(0, passenger.indexOf(" (")));
			try {
				sell_date = new Timestamp(sdf.parse(request.getParameter("sell_date")).getTime());
				reserve_date = new Timestamp(sdf.parse(request.getParameter("reserve_date")).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			//------------------------------------------
			try {
				Ticket buf = Factory.getInstance().getTicketDAO().getByID(Integer.parseInt(request.getParameter("id")));
				Trip trip = Factory.getInstance().getTripDAO().getByID(trip_id);
				Passenger pass = Factory.getInstance().getPassengerDAO().getByID(passenger_id);
				buf.setPrice(Long.parseLong(price));
				buf.setCategory(category);
				buf.setSell_date(sell_date);
				buf.setReservation_date(reserve_date);	
				buf.setTrip_num(trip);
				buf.setPass_id(pass);
				Factory.getInstance().getTicketDAO().update(buf);
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			printForm(request, response);
		
		} else if (action.matches("add")){

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp sell_date = new Timestamp((new Date().getTime()));
		Timestamp reserve_date = new Timestamp((new Date().getTime()));
		
		String category = request.getParameter("category");
		String price = request.getParameter("price");
		String trip_name = request.getParameter("trip_id");
		String passenger= request.getParameter("passenger_id");
		
		int trip_id = Integer.parseInt(trip_name.substring(0, trip_name.indexOf(" (")));
		int passenger_id = Integer.parseInt(passenger.substring(0, passenger.indexOf(" (")));
		System.out.println(trip_id);
		try {
			sell_date = new Timestamp(sdf.parse(request.getParameter("sell_date")).getTime());
			reserve_date = new Timestamp(sdf.parse(request.getParameter("reserve_date")).getTime());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//------------------------------------------
		try {
			Ticket buf = new Ticket();
			Trip trip = Factory.getInstance().getTripDAO().getByID(trip_id);
			Passenger pass = Factory.getInstance().getPassengerDAO().getByID(passenger_id);
			buf.setPrice(Long.parseLong(price));
			buf.setCategory(category);
			buf.setSell_date(sell_date);
			buf.setReservation_date(reserve_date);	
			buf.setTrip_num(trip);
			buf.setPass_id(pass);
			Factory.getInstance().getTicketDAO().add(buf);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		printForm(request, response);
		}

	}

	private void printFormEdit(HttpServletResponse r, int editid) {
		r.setCharacterEncoding("UTF-8");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Ticket</h1>");
			
			r.getWriter()
					.print("<form id=\"ticketForm\" name=\"ticketForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"EditTicket()\"><fieldset><legend>Edit the ticket:</legend>  <p>   <input hidden type=\"text\" name=\"id\" id=\"id\" value=\""+String.valueOf(editid)+"\"> <label for=\"category\">Category:</label>    <select name=\"category\" id=\"category\"><option disabled>Choose a category</option><option selected >");
			r.getWriter()
				.print(Factory.getInstance().getTicketDAO().getByID(editid).getCategory()+"</option><option >Premium</option></select>  </p>  <p>    <label for=\"price\">Price:</label>    <input type=\"text\" name=\"price\" id=\"price\" value=\""+Factory.getInstance().getTicketDAO().getByID(editid).getPrice()+"\">  </p>  <p>    <label for=\"sell_date\">Sell date:</label>    <input type=\"date\" name=\"sell_date\" id=\"sell_date\" value=\""+sdf.format(Factory.getInstance().getTicketDAO().getByID(editid).getSell_date())+ "\">  </p>    <p>    <label for=\"reserve_date\">Reserve date:</label>    <input type=\"date\" name=\"reserve_date\" id=\"reserve_date\" value=\""+sdf.format(Factory.getInstance().getTicketDAO().getByID(editid).getReservation_date())+"\">  </p>");
			List<Trip> trip_list = Factory.getInstance().getTripDAO().getAll();
			List<Passenger> passenger_list = Factory.getInstance().getPassengerDAO().getAll();
			trip_list.sort(new Comparator<Trip>() {
				public int compare(Trip arg0, Trip arg1) {
					if (arg0.getTripID()>arg1.getTripID()) return 1;
					if (arg0.getTripID()<arg1.getTripID()) return -1;
					return 0;
				};
			});
			passenger_list.sort(new Comparator<Passenger>() {
				public int compare(Passenger arg0, Passenger arg1) {
					if (arg0.getPassengerID()>arg1.getPassengerID()) return 1;
					if (arg0.getPassengerID()<arg1.getPassengerID()) return -1;
					return 0;
				};
			});
			// Select menu (Trip)
			r.getWriter().print(" <p>   <label for=\"trip_id\">Trip:</label>    <select type=\"text\" name=\"trip_id\" id=\"trip_id\">");
			for (Trip trip : trip_list) {
				r.getWriter().print("<option");
				if (Factory.getInstance().getTicketDAO().getByID(editid).getTrip_num().getTripID()==trip.getTripID()) r.getWriter().print(" selected");
				r.getWriter().print(">"+trip.getTripID()+" ("+trip.getStation_start().getName()+" - "+trip.getStation_end().getName()+")</option>");
			}
			r.getWriter().print("</select></p>");
		// Select menu (Trip) END
				
				// Select menu (Passenger)
					r.getWriter().print(" <p>   <label for=\"passenger_id\">Passenger id:</label>    <select type=\"text\" name=\"passenger_id\" id=\"passenger_id\">");
					for (Passenger passenger : passenger_list) {
						r.getWriter().print("<option");
						if (Factory.getInstance().getTicketDAO().getByID(editid).getPass_id().getPassengerID()==passenger.getPassengerID()) r.getWriter().print(" selected");
						r.getWriter().print(">"+passenger.getPassengerID()+" ("+passenger.getFIO()+")</option>");
					}
					r.getWriter().print("</select></p>");
				// Select menu (Passenger) END
			r.getWriter()
					.print("<p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_trans\" value=\"Отправить\">    			  </p></fieldset>    			</form>");
			
			} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}

}
