package org.anttijuustila.sqlinject.model;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseInterface {

	void open(String dbName) throws SQLException;
	void close();
	List<User> getAllUsers();
	boolean addUser(User user) throws SQLException;
	boolean isUserNameRegistered(String username);
	boolean isRegisteredUser(String username, String password);
}