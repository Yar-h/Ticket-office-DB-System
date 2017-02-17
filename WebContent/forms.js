function AddTransport() {
	var serializedForm = $('#transportForm').serialize();
	var msg = serializedForm + "&action=add";
	$.ajax({
		async : false,
		type : 'POST',
		url : 'TransportServlet',
		data : msg,
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
															function delTransport(delBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TransportServlet',
																	data : {
																		'action' : 'del',
																		'transid' : delBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function loadTransport(loadBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TransportServlet',
																	data : {
																		'action' : 'load',
																		'transid' : loadBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function EditTransport() {
																var serializedForm = $('#transportForm').serialize();
																var msg = serializedForm + "&action=edit";
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TransportServlet',
																	data : msg,
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
function AddTicket() {
	var serializedForm = $('#ticketForm').serialize();
	var msg = serializedForm + "&action=add";
	$.ajax({
		async : false,
		type : 'POST',
		url : 'TicketServlet',
		data : msg,
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
															function delTicket(delBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TicketServlet',
																	data : {
																		'action' : 'del',
																		'ticketid' : delBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function loadTicket(loadBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TicketServlet',
																	data : {
																		'action' : 'load',
																		'ticketid' : loadBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function EditTicket() {
																var serializedForm = $('#ticketForm').serialize();
																var msg = serializedForm + "&action=edit";
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TicketServlet',
																	data : msg,
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
function AddPassenger() {
	var serializedForm = $('#passengerForm').serialize();
	var msg = serializedForm + "&action=add";
	$.ajax({
		async : false,
		type : 'POST',
		url : 'PassengerServlet',
		data : msg,
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
															function delPassenger(delBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'PassengerServlet',
																	data : {
																		'action' : 'del',
																		'passengerid' : delBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function loadPassenger(loadBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'PassengerServlet',
																	data : {
																		'action' : 'load',
																		'passengerid' : loadBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function EditPassenger() {
																var serializedForm = $('#passengerForm').serialize();
																var msg = serializedForm + "&action=edit";
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'PassengerServlet',
																	data : msg,
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
function AddStation() {
	var serializedForm = $('#stationForm').serialize();
	var msg = serializedForm + "&action=add";
	$.ajax({
		async : false,
		type : 'POST',
		url : 'StationServlet',
		data : msg,
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
															function delStation(delBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'StationServlet',
																	data : {
																		'action' : 'del',
																		'stationid' : delBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function loadStation(loadBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'StationServlet',
																	data : {
																		'action' : 'load',
																		'stationid' : loadBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function EditStation() {
																var serializedForm = $('#stationForm').serialize();
																var msg = serializedForm + "&action=edit";
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'StationServlet',
																	data : msg,
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
function AddTrip() {
	var serializedForm = $('#tripForm').serialize();
	var msg = serializedForm + "&action=add";
	$.ajax({
		async : false,
		type : 'POST',
		url : 'TripServlet',
		data : msg,
		success : function(data) {
			$('#my_area').html(data);
		}
	});
}
															function delTrip(delBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TripServlet',
																	data : {
																		'action' : 'del',
																		'tripid' : delBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function loadTrip(loadBtn) {
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TripServlet',
																	data : {
																		'action' : 'load',
																		'tripid' : loadBtn.id
																	},
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}
															function EditTrip() {
																var serializedForm = $('#tripForm').serialize();
																var msg = serializedForm + "&action=edit";
																$.ajax({
																	async : false,
																	type : 'POST',
																	url : 'TripServlet',
																	data : msg,
																	success : function(data) {
																		$('#my_area').html(data);
																	}
																});
															}