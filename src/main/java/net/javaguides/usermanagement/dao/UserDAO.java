package net.javaguides.usermanagement.dao;

import net.javaguides.usermanagement.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/userManagement?useSSL=false";
	private String jdbcUsername = "pluto";
	private String jdbcPassword = "mr.Ziko1";

	private static final String SELECT_ALL_USERS = "SELECT * FROM USERS;";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM USERS WHERE ID =?;";
	private static final String INSERT_USER_SQL = "INSERT INTO USERS (name,email,country) VALUES (?,?,?);";
	private static final String UPDATE_USER_SQL = "UPDATE USERS set name = ? , email = ? ,country= ? where id = ?;";
	private static final String DELETE_USER_SQL = "DELETE FROM USERS where id = ?;";

	protected Connection getConnection(){
		System.out.println("Connected to Database.");
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	        return connection;
	    } catch (SQLException  e ) {
	        throw new RuntimeException("Cannot connect to database", e);
	    }catch (ClassNotFoundException  e ) {
	        throw new RuntimeException("Class not found", e);
	    }
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
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);){
				preparedStatement.setInt(1,id);
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()){
					String name = rs.getString("name");
					String email = rs.getString("email");
					String country = rs.getString("country");
					user = new User(id,name,email,country);
				}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user;
	}

	public List<User> GetAll(){
		List<User> users = new ArrayList<>();
		try(Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);){
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id,name,email,country));
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return users;
	}

	public boolean deleteUser(int id) throws SQLException{
		boolean rowDeleted;
		try(Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);){
			preparedStatement.setInt(1,id);
			rowDeleted = preparedStatement.executeUpdate()>0;
		}
		return rowDeleted;
	}
}
