<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ticket-office</title>
<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='jquery-2.1.0.js'></script>
<script type='text/javascript' src='jsmenu.js'></script>
<script type='text/javascript' src='forms.js'></script>
<style>
label {
	display: inline-block;
	width: 120px;
}
</style>
</head>
<body>
	<%
		String type = (String) session.getAttribute("type");
		if (type == null) {
			out.print("<div id=\"login\"><h1>Log in</h1><form action=\"LoginServlet\" method=\"post\">"+
					"<input placeholder=\"User name\" type=\"text\" name=\"name\"><br> "+
					"<input placeholder=\"Password\" type=\"password\"name=\"password\"><br>"+
					"<input type=\"submit\" value=\"Log in\"></fieldset></form></div>");
			return;
		}
		out.print("<div id=\"header\">");
			out.print("Welcome, <font color=\"#F44\" size=\"+2\">"+session.getAttribute("name")+"</font> | ");
			out.print("<a href=\"LogoutServlet\">Log out</a>");
		out.print("</div>");
	%>
	<div id="templatemo_wrapper">
		<div id="templatemo_header">
			<div id="site_title">
				<a href="#">Title</a>
			</div>
		</div>

		<div id="templatmeo_menu">
			<ul>
				<li><a href="#" class="home" onclick="HomeLoad()"><span></span></a></li>
				<li><a href="#" class="ticket" onclick="TicketLoad()"><span></span></a></li>
				<li><a href="#" class="passenger" onclick="PassengerLoad()"><span></span></a></li>
				<li><a href="#" class="trans" onclick="TransportLoad()"><span></span></a></li>
				<li><a href="#" class="trip" onclick="TripLoad()"><span></span></a></li>
				<li><a href="#" class="station" onclick="StationLoad()"><span></span></a></li>
			</ul>
			<div style="padding: 30px;"></div>
		</div>

		<div id="templatemo_main">
			<div id="my_area" class="main_box">
				<h1>Home</h1>
				<h2>Datebase Coursework [Ticket-Office]</h2>
				<div class="home_div"></div>
				<!-- END of home -->
			</div>
		</div>
		<!-- <hr> -->
		<div id="templatemo_footer">Hrushchak Yaroslav @ 2015</div>
	</div>
</body>
</html>