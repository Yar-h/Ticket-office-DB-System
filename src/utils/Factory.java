package utils;

public class Factory {

	private static TransportDAO transportDAO = null;
	private static StationDAO stationDAO = null;
	private static PassengerDAO passengerDAO = null;
	private static TicketDAO ticketDAO = null;
	private static TripDAO tripDAO = null;
	private static UserDAO userDAO = null;	
	private static Factory instance = null;
	
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static synchronized Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}

	public TransportDAO getTransportDAO() {
		if (transportDAO == null) {
			transportDAO = new TransportDAOImpl();
		}
		return transportDAO;
	}
	public StationDAO getStationDAO() {
		if (stationDAO == null) {
			stationDAO = new StationDAOImpl();
		}
		return stationDAO;
	}
	public PassengerDAO getPassengerDAO() {
		if (passengerDAO == null) {
			passengerDAO = new PassengerDAOImpl();
		}
		return passengerDAO;
	}
	public TicketDAO getTicketDAO() {
		if (ticketDAO == null) {
			ticketDAO = new TicketDAOImpl();
		}
		return ticketDAO;
	}
	public TripDAO getTripDAO() {
		if (tripDAO == null) {
			tripDAO = new TripDAOImpl();
		}
		return tripDAO;
	}

	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAOImpl();
		}
		return userDAO;
	}


}
