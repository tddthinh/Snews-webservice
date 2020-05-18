package com.snews.webservice.entities;

import java.util.ArrayList;

public class AuthToken {
	String token;
	ArrayList<String> grants;
	
	public AuthToken(String token, ArrayList<String> grants) {
		this.token = token;
		this.grants = grants;
	}
	public String getToken() {
		return token;
	}
	public ArrayList<String> getGrant() {
		return grants;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setGrant(ArrayList<String> grants) {
		this.grants = grants;
	}
	
}
