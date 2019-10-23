package com.revature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.dao.DAO;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.models.userAccounts;
import com.revature.util.ConnectionUtil;
import com.revature.util.scannerUtil;

public class p0 {
	private static User currentUser;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//login or register menu
		while(true) {
			System.out.println("0: Login");
			System.out.println("1. Register");
			if(scannerUtil.getInputMax(1)==0) {
				login();
				
			}
			else {
				register();
			}
		}
		
		
		
	}

	private static void register() {
		// TODO Auto-generated method stub
		//get user name
		System.out.println("Enter username: ");
		String userName=scannerUtil.getStringInput();
		System.out.println("Enter password: ");
		String password=scannerUtil.getStringInput();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO USERS(name,pass) VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setString(2, password);
			statement.execute();
		} catch (SQLException e) {
			System.out.println("User already exists.");
		
		}
		
		//get password
	}

	private static void login() {
		// TODO Auto-generated method stub
		//get user name
		System.out.println("Enter username: ");
		String userName=scannerUtil.getStringInput();
		currentUser= DAO.getUser(userName);
		if(currentUser==null) {
			System.out.println("User does not exist.");
			
		}
		else {
			System.out.println("Enter password: ");
			String password=scannerUtil.getStringInput();
			if(currentUser.getPassword().equals(password)) {
				userMenu();
			}
			else {
				System.out.println("Incorrect Password");
			}
		}
		//get password
	}

	private static void userMenu() {
		// TODO Auto-generated method stub
		while(true) {
			List<userAccounts> UAs=DAO.getUserAccounts(currentUser.getName());
			//display user menu
			System.out.println("0: exit");
			System.out.println("1: create account");
			int count=2;
			for(userAccounts ua:UAs) {
				System.out.println(count+": access account "+ua.getAccountId());
				count++;
			}
			int input=scannerUtil.getInputMax(count-1);
			//get user input
			if(input==0)
				break;
			else if(input==1) {
				newAccountMenu();
			}
			else {
				userAccounts ua =UAs.get(input-2);
				accountMenu(ua.getAccountId());
			}
		}
	}

	private static void newAccountMenu() {
		// TODO Auto-generated method stub
		//checking or savings
		//get input
		//create account
		System.out.println("0: Savings  1: Checkings");
		int input= scannerUtil.getInputMax(1);
		try (Connection connection = ConnectionUtil.getConnection()) {
			connection.setAutoCommit(false);
			String sql;
			if(input==0)sql= "INSERT INTO Accounts(type,amount) VALUES ('Savings' ,0) returning *";
			else sql= "INSERT INTO Accounts(type,amount) VALUES ('Checkings' ,0) Returning *";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet RS = statement.executeQuery();
			Account acc;
			RS.next();
			acc=DAO.extractAccount(RS);
			sql="INSERT INTO UserAccounts(username,account) values(?,?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, currentUser.getName());
			statement.setInt(2, acc.getId());
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

	private static void accountMenu(int id) {
		// TODO Auto-generated method stub
		//display account menu
		//get user input
		while(true) {
			//switch(input){
			Account acc=DAO.getAccount(id);
			System.out.println("Account id: "+id+" "+acc.getType()+" IQ Points: "+acc.getAmount());
			System.out.println("0: Exit");
			System.out.println("1: Deposit funds");
			System.out.println("2: Withdraw funds");
			System.out.println("3: Transfer funds");
			System.out.println("4: Add User");
			System.out.println("5: Delete Account");
			int input = scannerUtil.getInputMax(5);
			if(input ==0) break;
			else if(input==1)depositFunds(acc);
			else if(input==2)withdrawFunds(acc);
			else if(input==3)transferFunds(acc);
			else if(input==4)addUser(acc);
			else if(input==5) {
				deleteAccount(acc);
				break;
			}
			//exit
			//}
		}
	}

	private static void transferFunds(Account acc) {
		// TODO Auto-generated method stub
		System.out.println("Enter target account id: ");
		int input=scannerUtil.getInput();
		Account acc2=DAO.getAccount(input);
		if(acc2==null) {
			System.out.println("Account does not exist");
		}
		else {
			System.out.println("Amount to transfer:");
			input=scannerUtil.getInputMax(acc.getAmount());
			try (Connection connection = ConnectionUtil.getConnection()) {
				connection.setAutoCommit(false);
				String sql="update accounts set amount= ? where id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, acc.getAmount()-input);
				statement.setInt(2, acc.getId());
				statement.execute();
				sql="update accounts set amount= ? where id=?";
				statement = connection.prepareStatement(sql);
				statement.setInt(1, acc2.getAmount()+input);
				statement.setInt(2, acc2.getId());
				statement.execute();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			
			}
		}
	}

	private static void addUser(Account acc) {
		// TODO Auto-generated method stub
		System.out.println("Enter username to add to account");
		String username=scannerUtil.getStringInput();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql="insert into useraccounts(username,account) values(?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setInt(2, acc.getId());
			statement.execute();
			
		} catch (SQLException e) {
			//e.printStackTrace();;
			System.out.println("Either the user does not exist or is already added to the account.");
		}
	}

	private static void deleteAccount(Account acc) {
		// TODO Auto-generated method stub
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql="delete from useraccounts where account=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, acc.getId());
			statement.execute();
			sql="delete from accounts where id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, acc.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

	private static void withdrawFunds(Account acc) {
		// TODO Auto-generated method stub
		System.out.println("Enter amount of IQ points to withdraw");
		int input=scannerUtil.getInputMax(acc.getAmount());
		input=acc.getAmount()-input;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql="update accounts set amount= ? where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, input);
			statement.setInt(2, acc.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

	private static void depositFunds(Account acc) {
		// TODO Auto-generated method stub
		System.out.println("Enter amount of IQ points to deposit");
		int input=scannerUtil.getInput();
		input+=acc.getAmount();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql="update accounts set amount= ? where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, input);
			statement.setInt(2, acc.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}

}
