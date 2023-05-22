package org.anttijuustila.sqlinject.model;

import java.io.File;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.codec.digest.Crypt;

public class GoodDatabase implements DatabaseInterface {
	
	private Connection connection = null;
	private static DatabaseInterface singleton = null;
	private SecureRandom secureRandom = null;

	public static synchronized DatabaseInterface getInstance() {
		if (null == singleton) {
			singleton = new GoodDatabase();
		}
		return singleton;
	}

	private GoodDatabase() {	
		secureRandom = new SecureRandom();
	}

	@Override
	public void open(String dbName) throws SQLException {
		boolean createDatabase = false;
		File file = new File(dbName);
		if (!file.exists() && !file.isDirectory()) {
			createDatabase = true;
		}
		String database = "jdbc:sqlite:" + dbName;
		connection = DriverManager.getConnection(database);
		if (createDatabase) {
			initializeDatabase();
			addTestUsers();
		}
	}

	private void addTestUsers() throws SQLException {
		User user = new User("Antti", "password", "antti@oulu.fi");
		addUser(user);
		user = new User("Jouni", "kalakana", "jouni@oulu.fi");
		addUser(user);
		user = new User("Mikko", "123456", "jouni@oulu.fi");
		addUser(user);
	}

	@Override
	public void close() {
		if (null != connection) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("*** ERROR in closing the database connection: " + e.getMessage());
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
				PreparedStatement queryStatement = connection.prepareStatement(queryUser);
				ResultSet rs = queryStatement.executeQuery();
				while (rs.next()) {
					final String id = rs.getString("id");
					final String user = rs.getString("name");
					final String email = rs.getString("email");
					final String pw = rs.getString("passwd");
					User aUser = new User(id, user, pw, email);
					users.add(aUser);
				}
				queryStatement.close();
			} catch (SQLException e) {
				System.out.println("Reason: " + e.getErrorCode() + " " + e.getMessage());
			}
		}
		return users;
	}

	@Override
	public boolean addUser(User user) throws SQLException {
		boolean result = false;
		if (null != connection && !isUserNameRegistered(user.getName())) {
			String hashedPassword = user.getPassword();
			if (hashedPassword.length() > 0) {
				hashedPassword = Crypt.crypt(user.getPassword());
			}
			String insertUser = "insert into user values (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(insertUser);
			statement.setString(1, user.getId());
			statement.setString(2, user.getName());
			statement.setString(3, hashedPassword);
			statement.setString(4, user.getEmail());
			statement.executeUpdate();
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
			String hashedPassword = user.getPassword();
			if (hashedPassword.length() > 0) {
				hashedPassword = Crypt.crypt(user.getPassword());
			}
			String updateUser = "update user set name=?, passwd=?, email=? where id = ?";
			PreparedStatement statement = connection.prepareStatement(updateUser);
			statement.setString(1, user.getName());
			statement.setString(2, hashedPassword);
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getId());
			statement.executeUpdate();
			statement.close();
			result = true;
		} else {
			System.out.println("No connection to db");
		}
		return result;
	}

	@Override
	public boolean isUserNameRegistered(String username) {
		boolean result = false;
		if (null != connection) {
			try {
				String queryUser = "select name from user where name = ?";
				PreparedStatement queryStatement = connection.prepareStatement(queryUser);
				queryStatement.setString(1, username);
				ResultSet rs = queryStatement.executeQuery();
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

	@Override
	public boolean isRegisteredUser(String username, String password) {
		boolean result = false;
		PreparedStatement queryStatement = null;
		if (null != connection) {
			try {
				String queryUser = "select name, passwd from user where name = ?";
				queryStatement = connection.prepareStatement(queryUser);
				queryStatement.setString(1, username);
				ResultSet rs = queryStatement.executeQuery();
				while (rs.next()) {
					String user = rs.getString("name");
					String hashedPassword = rs.getString("passwd");
					if (user.equals(username)) { // should match since the SQL query...
					 	if (hashedPassword.equals(Crypt.crypt(password, hashedPassword))) {
							result = true;
							break;
						}
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

	private boolean initializeDatabase() throws SQLException {
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
