package com.snews.webservice.repository.result;
import java.io.Serializable;
import com.snews.webservice.entities.Role;


public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String email;
	private String errorEmail;
	private String fullname;
	private String errorFullname;
	
	private String username;
	private String errorUsername;
	
	
	private String password;
	private String errorPassword;
	
	private Role role;
	
	private String token;
	private boolean isError;
	
	public String getEmail() {
		return email;
	}
	public String getFullname() {
		return fullname;
	}
	public int getId() {
		return id;
	}
	public Role getRole() {
		return role;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getToken() {
		return token;
	}
	public boolean getError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getErrorEmail() {
		return errorEmail;
	}
	public String getErrorFullname() {
		return errorFullname;
	}
	public String getErrorPassword() {
		return errorPassword;
	}
	public String getErrorUsername() {
		return errorUsername;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setErrorEmail(String errorEmail) {
		this.errorEmail = errorEmail;
	}
	public void setErrorFullname(String errorFullname) {
		this.errorFullname = errorFullname;
	}
	public void setErrorPassword(String errorPassword) {
		this.errorPassword = errorPassword;
	}
	public void setErrorUsername(String errorUsername) {
		this.errorUsername = errorUsername;
	}
}
