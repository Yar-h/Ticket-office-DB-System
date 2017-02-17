package utils;

import java.sql.SQLException;
import java.util.List;

import classes.Ticket;


public interface TicketDAO {
    public void add(Ticket trans) throws SQLException;  
    public void update(Ticket trans) throws SQLException;   
    public List<Ticket> getAll() throws SQLException;              
    public void delete(int id) throws SQLException;
    public Ticket getByID(int id) throws SQLException;
}