package org.anttijuustila.sqlinject.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BadDatabase implements DatabaseInterface {

	private Connection connection = null;
	private static BadDatabase singleton = null;

	public static synchronized BadDatabase getInstance() {
		if (null == singleton) {
			singleton = new BadDatabase();
		}
		return singleton;
	}

	private BadDatabase() {	
		// Empty
	}

	public void open(String dbName) throws SQLException {
		boolean createBadDatabase = false;
		File file = new File(dbName);
		if (!file.exists() && !file.isDirectory()) {
			createBadDatabase = true;
		}
		String database = "jdbc:sqlite:" + dbName;
		connection = DriverManager.getConnection(database);
		if (createBadDatabase) {
			initializeBadDatabase();
			addTestUsers();
		}
	}

	private void addTestUsers() throws SQLException {
		User user = new User("Antti", "password", "antti@oulu.fi");
		addUser(user);
		user = new User("Jouni", "kalakana", "jouni@oulu.fi");
		addUser(user);
		user = new User("Mikko", "123456", "mikko@oulu.fi");
		addUser(user);
	}

	public void close() {
		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("*** ERROR in closing the BadDatabase connection: " + e.getMessage());
				e.printStackTrace();
			}
			connection = null;
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		if (null != connection) {
			try {
				String queryUser = "select * from user";
				Statement queryStatement = connection.createStatement();
				ResultSet rs = queryStatement.executeQuery(queryUser);
				while (rs.next()) {
					final String id = rs.getString("id");
					final String user = rs.getString("name");
					final String email = rs.getString("email");
					final String passwd = rs.getString("passwd");
					User aUser = new User(id, user, passwd, email);
					users.add(aUser);
				}
				queryStatement.close();
			} catch (SQLException e) {
				System.out.println("Reason: " + e.getErrorCode() + " " + e.getMessage());
			}
		}
		return users;
	}

	public boolean addUser(User user) throws SQLException {
		boolean result = false;
		if (null != connection && !isUserNameRegistered(user.getName())) {
			String sqlStatement = "insert into user (id, name, passwd, email) values ('";
			sqlStatement += user.getId() + "', '";
			sqlStatement += user.getName() + "', '";
			sqlStatement += user.getPassword() + "', '";
			sqlStatement += user.getEmail() + "')";
			Statement statement = connection.createStatement();
			System.out.println(sqlStatement);
			statement.executeUpdate(sqlStatement);
			statement.close();
			result = true;
		} else {
			System.out.println("User already registered: " + user.getName());
		}
		return result;
	}

	@Override
	public boolean saveUser(User user) throws SQLException {
		boolean result = false;
		if (null != connection) {
			String updateUser = "update user set name='" + user.getName();
			updateUser += "', passwd='" + user.getPassword();
			updateUser += "', email='" +user.getEmail();
			updateUser += "' where id='" + user.getId() + "'";
			Statement statement = connection.createStatement();
			statement.executeUpdate(updateUser);
			statement.close();
			result = true;
		} else {
			System.out.println("User already registered: " + user.getName());
		}
		return result;
	}
	public boolean isUserNameRegistered(String username) {
		boolean result = false;
		if (null != connection) {
			try {
				String queryUser = "select name from user where name = '" + username + "'";
				Statement queryStatement = connection.createStatement();
				ResultSet rs = queryStatement.executeQuery(queryUser);
				while (rs.next()) {
					String user = rs.getString("name");
					if (user.equals(username)) {
						result = true;
						break;
					}
				}
				queryStatement.close();
			} catch (SQLException e) {
				System.out.println("Could not check isUserNameRegistered: " + username);
				System.out.println("Reason: " + e.getErrorCode() + " " + e.getMessage());
			}

		}
		return result;
	}

	public boolean isRegisteredUser(String username, String password) {
		boolean result = false;
		Statement queryStatement = null;
		if (null != connection) {
			try {
				String queryUser = "select name, passwd from user where name = '" + username + "'";
				queryStatement = connection.createStatement();
				ResultSet rs = queryStatement.executeQuery(queryUser);
				while (rs.next()) {
					String user = rs.getString("name");
					String dbPassword = rs.getString("passwd");
					if (user.equals(username) && dbPassword.equals(password)) {
						result = true;
						break;
					}
				}
				queryStatement.close();
			} catch (SQLException e) {
				System.out.println("Could not check isRegisteredUser: " + username);
				System.out.println("Reason: " + e.getErrorCode() + " " + e.getMessage());
			}
			if (!result) {
				System.out.println("Not a registered user!");
			}
		}
		return result;
	}


	private boolean initializeBadDatabase() throws SQLException {
		if (null != connection) {
			String createUsersString = "create table user " + 
				"(id varchar(32) NOT NULL, " +
				"name varchar(32) NOT NULL, " +
				"passwd varchar(32) NOT NULL, " +
				"email varchar(32) NOT NULL, " +
				"PRIMARY KEY (id))";
			Statement createStatement = connection.createStatement();
			createStatement.executeUpdate(createUsersString);
			createStatement.close();
			return true;
		}
		return false;
	}	
}
