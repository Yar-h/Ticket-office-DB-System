package utils;

import java.sql.SQLException;
import java.util.List;

import classes.User;

public interface UserDAO {
	public List<User> getAll() throws SQLException;
	public User getByName(String user) throws SQLException;

}