package com.snews.webservice.entities;

public class Progress {
	private int value;
	private int max;
	public Progress() {
	}
	public Progress(int value, int max) {
		this.value = value;
		this.max = max;
		
	}
	
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	} 
}
