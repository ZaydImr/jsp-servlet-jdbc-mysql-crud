package net.javaguides.usermanagement.dao;

import net.javaguides.usermanagement.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost/userManagement?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";

	private static final String SELECT_ALL_USERS = "SELECT * FROM USERS;";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM USERS WHERE ID =?;";
	private static final String INSERT_USER_SQL = "INSERT INTO USERS (name,email,country) VALUES (?,?,?);";
	private static final String UPDATE_USER_SQL = "UPDATE USERS set name = ? , email = ? ,country= ? where id = ?;";
	private static final String DELETE_USER_SQL = "DELETE FROM USERS where id = ?;";

	protected Connection getConnection() {
		Connection connection= null;
		try {
			Class.forName("com.mysql.jdc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void insertUser(User user) throws SQLException {
		try(Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try(Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public User GetUser(int id){
		User user = null;
		try(Connection connection = getConnection();
				)
	}
}
