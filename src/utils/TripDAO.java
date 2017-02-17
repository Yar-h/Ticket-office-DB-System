package utils;

import java.sql.SQLException;
import java.util.List;

import classes.Trip;


public interface TripDAO {
    public void add(Trip trans) throws SQLException;  
    public void update(Trip trans) throws SQLException;   
    public List<Trip> getAll() throws SQLException;     
    public Trip getByID(int id)throws SQLException;
    public void delete(int id) throws SQLException;
}