package com.revature.models;

public class userAccounts {
private String userName;
private int accountId;
public userAccounts(String userName, int accountId) {
	super();
	this.userName = userName;
	this.accountId = accountId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public int getAccountId() {
	return accountId;
}
public void setAccountId(int accountId) {
	this.accountId = accountId;
}
}
