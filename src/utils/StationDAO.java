package utils;

import java.sql.SQLException;
import java.util.List;

import classes.Station;


public interface StationDAO {
    public void add(Station station) throws SQLException;  
    public void update(Station station) throws SQLException;   
    public List<Station> getAll() throws SQLException;
    public Station getByID(int id) throws SQLException;
    public void delete(int id) throws SQLException;
}