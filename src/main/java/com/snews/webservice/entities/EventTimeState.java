package com.snews.webservice.entities;

public class EventTimeState {
	
	private int compareTodayDate;
	private int compareTodayTime;
	public EventTimeState() {
	}
	public EventTimeState(int date, int time) {
		setCompareTodayDate(date);
		setCompareTodayTime(time);
		
	}
	
	public void setCompareTodayDate(int compareTodayDate) {
		this.compareTodayDate = compareTodayDate;
	}
	public void setCompareTodayTime(int compareTodayTime) {
		this.compareTodayTime = compareTodayTime;
	}
	public int getCompareTodayDate() {
		return compareTodayDate;
	}
	public int getCompareTodayTime() {
		return compareTodayTime;
	}
}
