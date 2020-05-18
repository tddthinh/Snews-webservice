package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event_")
@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	@Column(name = "E_ID", unique = true, nullable = false)
	private int eId;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonProperty("date")
	@Column(name = "E_DATE")
	private Date eDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("end_time")
	@Column(name = "E_END")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Date eEnd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonProperty("start_time")
	@Column(name = "E_START")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private Date eStart;
	
	@JsonProperty("name")
	@Column(name = "E_NAME", length = 128)
	private String eName;
	


	@JsonProperty("location")
	@Column(name = "E_LOC")
	private String eLocation;

	@JsonProperty("timeout")
	@Column(name = "E_TIMEOUT")
	private int eTimeout;
	// bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name = "U_ID", nullable = false, updatable = false)
	private Account account;

	// bi-directional many-to-many association to Attendee
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "register_list", joinColumns = {
			@JoinColumn(name = "E_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "A_ID", nullable = false) })
	@JsonProperty("attendees")
	@JsonIgnore
	private List<Attendee> attendees;
	
	public Event() {
		
	}

	public int getEId() {
		return this.eId;
	}

	public void setEId(int eId) {
		this.eId = eId;
	}

	public Date getEDate() {
		return this.eDate;
	}

	public void setEDate(Date eDate) {
		this.eDate = eDate;
	}

	public Date getEEnd() {
		return this.eEnd;
	}

	public String geteLocation() {
		return eLocation;
	}

	public void setEEnd(Date eEnd) {
		this.eEnd = eEnd;
	}

	public String getEName() {
		return this.eName;
	}

	public int geteTimeout() {
		return eTimeout;
	}

	public void setEName(String eName) {
		this.eName = eName;
	}

	public Date getEStart() {
		return this.eStart;
	}

	public void setEStart(Date eStart) {
		this.eStart = eStart;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Attendee> getAttendees() {
		return this.attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

	public void seteLocation(String eLocation) {
		this.eLocation = eLocation;
	}

	public void seteTimeout(int eTimeout) {
		this.eTimeout = eTimeout;
	}
}