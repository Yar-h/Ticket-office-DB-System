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
import classes.Station;
import classes.Transport;
import classes.Trip;

/**
 * Servlet implementation class TripServlet
 */
@WebServlet("/TripServlet")
public class TripServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TripServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	private void printForm(HttpServletRequest request, HttpServletResponse r) {
		r.setCharacterEncoding("UTF-8");
		boolean admin=false;
		if (request.getSession().getAttribute("type").equals("admin")) admin=true;
		try {
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Trip</h1>");
			
			List<Trip> result = Factory.getInstance().getTripDAO().getAll();
			result.sort(new Comparator<Trip>() {
				public int compare(Trip arg0, Trip arg1) {
					if (arg0.getTripID()>arg1.getTripID()) return 1;
					if (arg0.getTripID()<arg1.getTripID()) return -1;
					return 0;
				};
			});

			String table;
			if (result!=null) {
				table="<details open=\"open\"><summary>Show table</summary><table>";
				table+="<tr><th>Id</th><th>Dispatch station Id</th><th>Arrival station Id</th><th>Dispatch date</th><th>Arrival date</th><th>Transport id + Type</th>";
				if (admin) table+="<th></th><th></th>";
				table+="</tr>";
				if (result.size()==0) table+="<tr><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td><td>*</td></tr>"; else
				{
					for (Trip trip : result) {
						table+="<tr>";
							table+="<td>"+trip.getTripID()+"</td>";
							table+="<td>"+trip.getStation_start().getStationID()+"</td>";
							table+="<td>"+trip.getStation_end().getStationID()+"</td>";
							table+="<td>"+new SimpleDateFormat("dd-MM-yyyy").format(trip.getStart_date())+"</td>";
							table+="<td>"+new SimpleDateFormat("dd-MM-yyyy").format(trip.getEnd_date())+"</td>";
							table+="<td>"+trip.getTransport().getTransID()+" ("+trip.getTransport().getType()+")</td>";
							if (admin){
								table+="<td><img class=\"DelButtons\" id=\""+trip.getTripID();
									table+="\" onclick=\"delTrip(this)\" width=30 src=\"images/icon_del.png\"></img></td>";
								table+="<td><img class=\"LoadButtons\" id=\""+trip.getTripID();
									table+="\" onclick=\"loadTrip(this)\" width=25 src=\"images/icon_edit.png\"></img></td>";
							}
						table+="</tr>";
					}
				}
					table+="</table></details><br><br>";
					r.getWriter().print(table);;
				}
			
			if (admin){
			r.getWriter()
					.print("<form id=\"tripForm\" name=\"tripForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"AddTrip()\"><fieldset><legend>Add a trip:</legend>");
			
			List<Station> station_list = Factory.getInstance().getStationDAO().getAll();
			List<Transport> transport_list = Factory.getInstance().getTransportDAO().getAll();
			
			// Select menu (Dispatch Station)
				r.getWriter().print(" <p>   <label for=\"station_fr\">From:</label>    <select type=\"text\" name=\"station_fr\" id=\"station_fr\">");
				r.getWriter().print("<option selected disabled>Choose a station</option>");
				for (Station station : station_list) {
					r.getWriter().print("<option>"+station.getStationID()+" ("+station.getName()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Dispatch Station) END
			
			// Select menu (Arrival Station)
				r.getWriter().print(" <p>   <label for=\"station_to\">To:</label>    <select type=\"text\" name=\"station_to\" id=\"station_to\">");
				r.getWriter().print("<option selected disabled>Choose a station</option>");
				for (Station station : station_list) {
					r.getWriter().print("<option>"+station.getStationID()+" ("+station.getName()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Arrival Station) END
			
			// Select menu (Transport)
				r.getWriter().print(" <p>   <label for=\"transport\">Transport:</label>    <select type=\"text\" name=\"transport\" id=\"transport\">");
				r.getWriter().print("<option selected disabled>Choose a transport</option>");
				for (Transport transport : transport_list) {
					r.getWriter().print("<option>"+transport.getTransID()+" ("+transport.getType()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Transport) END
			
			r.getWriter().print(" <p>    <label for=\"date\">Outbound:</label>    <input type=\"date\" name=\"date\" id=\"date\">  </p>  <p>    <label for=\"trip_duration\">Duration(in hours):</label>    <input type=\"text\" name=\"trip_duration\" id=\"trip_duration\">  </p>			  <p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_trip\" value=\"Отправить\">    			  </p></fieldset></form>");
			}
			} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		printForm(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action=request.getParameter("action");
		
		if (action.matches("del")) {
			if (!Factory.isNumeric(request.getParameter("tripid"))){
				response.getWriter().write("<p>id не может быть пустым!</p>");
				return;
			}
			int delid = Integer.parseInt(request.getParameter("tripid"));
			if (delid < 0) {
				response.getWriter().write("<p>id '"+delid+"' не верен!</p>");
				return;
			}
			if (delid >= 0) {
				try {
					Factory.getInstance().getTripDAO().delete(delid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			printForm(request,response);
		} else if (action.matches("load")) {
			int editid = Integer.parseInt(request.getParameter("tripid"));
			printFormEdit(response, editid);
		} else if (action.matches("edit")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp date = new Timestamp((new Date().getTime()));
		Timestamp dateEnd = new Timestamp((new Date().getTime()));
		int duration = 0;
		
		String station_from = request.getParameter("station_fr");
		String station_to = request.getParameter("station_to");
		String transp = request.getParameter("transport");
		
		int station_start_id = Integer.parseInt(station_from.substring(0, station_from.indexOf(" (")));
		int station_end_id = Integer.parseInt(station_to.substring(0, station_to.indexOf(" (")));
		int transport_id = Integer.parseInt(transp.substring(0, transp.indexOf(" (")));
		duration += Integer.parseInt(request.getParameter("trip_duration"));
		
		try {
			date = new Timestamp(sdf.parse(request.getParameter("date")).getTime());
			dateEnd = new Timestamp(sdf.parse(request.getParameter("date")).getTime()+duration*3600*1000);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//------------------------------------------
		try {
			Trip buf = Factory.getInstance().getTripDAO().getByID(Integer.parseInt(request.getParameter("id")));
			
			Station station_st = Factory.getInstance().getStationDAO().getByID(station_start_id);
			Station station_fn = Factory.getInstance().getStationDAO().getByID(station_end_id);
			buf.setStation_start(station_st);
			buf.setStation_end(station_fn);
			buf.setStart_date(date);	
			buf.setEnd_date(dateEnd);
			Transport transprt = Factory.getInstance().getTransportDAO().getByID(transport_id);
			buf.setTransport(transprt);
			Factory.getInstance().getTripDAO().update(buf);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printForm(request,response);
		
		} else if (action.matches("add")){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp date = new Timestamp((new Date().getTime()));
		Timestamp dateEnd = new Timestamp((new Date().getTime()));
		int duration = 0;
		
		String station_from = request.getParameter("station_fr");
		String station_to = request.getParameter("station_to");
		String transp = request.getParameter("transport");
		
		int station_start_id = Integer.parseInt(station_from.substring(0, station_from.indexOf(" (")));
		int station_end_id = Integer.parseInt(station_to.substring(0, station_to.indexOf(" (")));
		int transport_id = Integer.parseInt(transp.substring(0, transp.indexOf(" (")));
		duration += Integer.parseInt(request.getParameter("trip_duration"));
		
		try {
			date = new Timestamp(sdf.parse(request.getParameter("date")).getTime());
			dateEnd = new Timestamp(sdf.parse(request.getParameter("date")).getTime()+duration*3600*1000);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//------------------------------------------
		try {
			Trip buf = new Trip();
			Station station_st = Factory.getInstance().getStationDAO().getByID(station_start_id);
			Station station_fn = Factory.getInstance().getStationDAO().getByID(station_end_id);
			buf.setStation_start(station_st);
			buf.setStation_end(station_fn);
			buf.setStart_date(date);	
			buf.setEnd_date(dateEnd);
			Transport transprt = Factory.getInstance().getTransportDAO().getByID(transport_id);
			buf.setTransport(transprt);
			Factory.getInstance().getTripDAO().add(buf);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printForm(request,response);
		}

	}

	private void printFormEdit(HttpServletResponse r, int editid) {
		r.setCharacterEncoding("UTF-8");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			r.getWriter().write("<html> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> </html><h1>Ticket</h1>");
			
			r.getWriter()
					.print("<form id=\"tripForm\" name=\"tripForm\" method=\"post\" action=\"javascript:void(null);\" onsubmit=\"EditTrip()\"><fieldset><legend>Edit the trip:</legend>"
							+ "<input hidden type=\"text\" name=\"id\" id=\"id\" value=\""
							+ Factory.getInstance().getTripDAO().getByID(editid).getTripID()+"\">");
			
			List<Station> station_list = Factory.getInstance().getStationDAO().getAll();
			List<Transport> transport_list = Factory.getInstance().getTransportDAO().getAll();

			station_list.sort(new Comparator<Station>() {
				public int compare(Station arg0, Station arg1) {
					if (arg0.getStationID()>arg1.getStationID()) return 1;
					if (arg0.getStationID()<arg1.getStationID()) return -1;
					return 0;
				};
			});
			transport_list.sort(new Comparator<Transport>() {
				public int compare(Transport arg0, Transport arg1) {
					if (arg0.getTransID()>arg1.getTransID()) return 1;
					if (arg0.getTransID()<arg1.getTransID()) return -1;
					return 0;
				};
			});

			
			// Select menu (Dispatch Station)
				r.getWriter().print(" <p>   <label for=\"station_fr\">From:</label>    <select type=\"text\" name=\"station_fr\" id=\"station_fr\">");
				r.getWriter().print("<option selected disabled>Choose a station</option>");
				for (Station station : station_list) {
					r.getWriter().print("<option");
					if (Factory.getInstance().getTripDAO().getByID(editid).getStation_start().getStationID()==station.getStationID()) r.getWriter().print(" selected");
					r.getWriter().print(">"+station.getStationID()+" ("+station.getName()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Dispatch Station) END
			
			// Select menu (Arrival Station)
				r.getWriter().print(" <p>   <label for=\"station_to\">To:</label>    <select type=\"text\" name=\"station_to\" id=\"station_to\">");
				r.getWriter().print("<option selected disabled>Choose a station</option>");
				for (Station station : station_list) {
					r.getWriter().print("<option");
					if (Factory.getInstance().getTripDAO().getByID(editid).getStation_end().getStationID()==station.getStationID()) r.getWriter().print(" selected");
					r.getWriter().print(">"+station.getStationID()+" ("+station.getName()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Arrival Station) END
			
			// Select menu (Transport)
				r.getWriter().print(" <p>   <label for=\"transport\">Transport:</label>    <select type=\"text\" name=\"transport\" id=\"transport\">");
				r.getWriter().print("<option selected disabled>Choose a transport</option>");
				for (Transport transport : transport_list) {
					r.getWriter().print("<option");
					if (Factory.getInstance().getTripDAO().getByID(editid).getTransport().getTransID()==transport.getTransID()) r.getWriter().print(" selected");
					r.getWriter().print(">"+transport.getTransID()+" ("+transport.getType()+")</option>");
				}
				r.getWriter().print("</select></p>");
			// Select menu (Transport) END
			
			r.getWriter().print(" <p>    <label for=\"date\">Outbound:</label>    <input type=\"date\" name=\"date\" id=\"date\" value=\""+sdf.format(Factory.getInstance().getTripDAO().getByID(editid).getStart_date())+"\">  </p>  <p>    <label for=\"trip_duration\">Duration(in hours):</label>    <input type=\"text\" name=\"trip_duration\" id=\"trip_duration\" value=\""+((Factory.getInstance().getTripDAO().getByID(editid).getEnd_date().getTime()-Factory.getInstance().getTripDAO().getByID(editid).getStart_date().getTime())/3600/1000)+"\">  </p>			  <p>    			    <input type=\"submit\" name=\"submit\" id=\"submit_trip\" value=\"Отправить\">    			  </p></fieldset></form>");
			
			} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}


