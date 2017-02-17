package utils;

import java.sql.SQLException;
import java.util.List;

import classes.Passenger;


public interface PassengerDAO {
    public void add(Passenger trans) throws SQLException;  
    public void update(Passenger trans) throws SQLException;   
    public List<Passenger> getAll() throws SQLException;     
	public Passenger getByID(int id)throws SQLException;
    public void delete(int id) throws SQLException;
}