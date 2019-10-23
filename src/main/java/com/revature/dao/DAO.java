package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.userAccounts;
import com.revature.util.ConnectionUtil;

public class DAO {
	Connection conn;

	public void setConnection(Connection conn) {
		try {
			if (this.conn != null && !this.conn.isClosed()) {
				System.out.println("Closing connection");
				this.conn.close();
			}
			this.conn = conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public DAO() {
		this.conn = ConnectionUtil.getConnection();
	}
	public static User getUser(String name){
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM users WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, name);
			
			ResultSet resultSet = statement.executeQuery();
			

			if (resultSet.next()) {
				User user = extractUser(resultSet);
				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public static User extractUser(ResultSet resultSet) throws SQLException {
		String userName = resultSet.getString("name");
		String password = resultSet.getString("pass");
		User user = new User(userName, password);
		return user;
	}
	public static Account getAccount(int id){
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			

			if (resultSet.next()) {
				Account account= extractAccount(resultSet);
				return account;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	public static Account extractAccount(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		int amount = resultSet.getInt("amount");
		String type = resultSet.getString("type");
		Account account = new Account(id, type,amount);
		return account;
	}
	public static List<userAccounts> getUserAccounts(String username) {
		try (Connection connection = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM useraccounts WHERE username = ?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			List<userAccounts> ans = new ArrayList<>();

			while (resultSet.next()) {
				userAccounts userAccount = extractUserAccount(resultSet);
				ans.add(userAccount);
			}

			return ans;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static userAccounts extractUserAccount(ResultSet resultSet) throws SQLException {
		int account = resultSet.getInt("account");
		String username = resultSet.getString("username");
		userAccounts userAccount = new userAccounts(username,account);
		return userAccount;
	}
}
