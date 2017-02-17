function TransportLoad() {
	$.ajax({
		type : 'GET',
		url : 'TransportServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
function TripLoad() {
	$.ajax({
		type : 'GET',
		url : 'TripServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
function PassengerLoad() {
	$.ajax({
		type : 'GET',
		url : 'PassengerServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
function TicketLoad() {
	$.ajax({
		type : 'GET',
		url : 'TicketServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
function HomeLoad() {
	$.ajax({
		type : 'GET',
		url : 'HomeServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
function StationLoad() {
	$.ajax({
		type : 'GET',
		url : 'StationServlet',
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
