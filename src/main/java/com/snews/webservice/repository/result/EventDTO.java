package com.snews.webservice.repository.result;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty; 
public class EventDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@JsonProperty("date")
	private Date date;
	private String errorDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="HH:mm")
	@JsonProperty("end_time")
	private Date end_time;

	@JsonProperty("name")
	private String name;
	private String errorName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="HH:mm")
	@JsonProperty("start_time")
	private Date start_time;
	private String errorTime;
	@JsonProperty("account")
	private int account;
	
	@JsonProperty("location")
	private String location;
	
	private int timeout;
	private int count;
	
	private boolean isError;
	
	public void setDate(Date date) {
		this.date = date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public void setAccountId(int uid) {
		this.account = uid;
	}
	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}
	public void setErrorDate(String errorDate) {
		this.errorDate = errorDate;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getAccount() {
		return account;
	}
	public Date getDate() {
		return date;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public Date getStart_time() {
		return start_time;
	}
	public String getErrorTime() {
		return errorTime;
	}
	public String getErrorDate() {
		return errorDate;
	}
	public String getErrorName() {
		return errorName;
	}
	public boolean isError() {
		return isError;
	}
	public String getLocation() {
		return location;
	}
	public int getTimeout() {
		return timeout;
	}
	public int getCount() {
		return count;
	}

}
