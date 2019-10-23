package com.revature.models;

public class Account {
	private int id;
	public Account(int id, String type, int amount) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	private String type;
	private int amount;
}
