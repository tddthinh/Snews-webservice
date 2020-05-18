package com.snews.webservice.entities;

public class AttendeeStatistics {
	private int present;
	private int absent;
	private int onlyIn;
	private int onlyOut;
	
	public AttendeeStatistics() {
		// TODO Auto-generated constructor stub
	}
	public AttendeeStatistics(int present, int absent, int onlyIn, int onlyOut) {
		this.present = present;
		this.absent = absent;
		this.onlyIn = onlyIn;
		this.onlyOut = onlyOut;
	}
	public int getAbsent() {
		return absent;
	}
	public void setAbsent(int absent) {
		this.absent = absent;
	}
	public int getOnlyIn() {
		return onlyIn;
	}
	public void setOnlyIn(int onlyIn) {
		this.onlyIn = onlyIn;
	}
	public int getOnlyOut() {
		return onlyOut;
	}
	public void setOnlyOut(int onlyOut) {
		this.onlyOut = onlyOut;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	
}
