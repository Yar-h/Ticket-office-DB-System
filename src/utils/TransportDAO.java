package utils;

import java.sql.SQLException;
import java.util.List;

import classes.Transport;


public interface TransportDAO {
    public void add(Transport trans) throws SQLException;  
    public void update(Transport trans) throws SQLException;   
    public List<Transport> getAll() throws SQLException;
	public Transport getByID(int id) throws SQLException;
    public void delete(int id) throws SQLException;
}