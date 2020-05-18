package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The persistent class for the attendees database table.
 * 
 */
@Entity
@Table(name = "attendees")
@NamedQuery(name = "Attendee.findAll", query = "SELECT a FROM Attendee a")
public class Attendee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "A_ID", unique = true, nullable = false)
	@JsonProperty("id")
	private int aId;

	@Column(name = "A_CODE", length = 64, unique = true)
	@JsonProperty("code")
	private String aCode;

	@Column(name = "A_EMAIL", length = 64, unique = true)
	@JsonProperty("email")
	private String aEmail;

	@Column(name = "A_FULLNAME", length = 64)
	@JsonProperty("fullname")
	private String aFullname;

	@Column(name = "A_RFID", length = 64, unique = true)
	@JsonProperty("rfid")
	private String aRfid;

	@ManyToMany
	@JoinTable(name = "attendees_label", joinColumns = {
			@JoinColumn(name = "A_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "LB_ID", nullable = false) })
	@JsonProperty("labels")
	@OrderBy("lbContent")
	private List<LabelInfo> labelInfos;

	@ManyToMany(mappedBy = "attendees")
	@JsonIgnore
	private List<Event> events;

	@Column(name = "A_IN")
	@JsonIgnore
	private boolean isIn;
	@Column(name = "A_OUT")
	@JsonIgnore
	private boolean isOut;

	@Column(name = "search_string", length = 1000)
	@JsonIgnore
	private String searchString;

	@Transient
	private String errorCode;
	@Transient
	private String errorFullname;
	@Transient
	private String errorEmail;
	@Transient
	private String errorRfid;
	@Transient
	private boolean error;

	@Transient
	private int eventId;
	@Transient
	private boolean isExists;

	@Column(name = "A_OWN")
	private int ownId;

	public Attendee(String code, String fullname, String email, String rfid) {
		this.aCode = code;
		this.aFullname = fullname;
		this.aEmail = email;
		this.aRfid = rfid;
	}

	public Attendee() {
	}

	@JsonIgnore
	public int getAId() {
		return this.aId;
	}

	public void setAId(int aId) {
		this.aId = aId;
	}

	@JsonIgnore
	public String getACode() {
		return this.aCode;
	}

	public void setACode(String aCode) {
		this.aCode = aCode;
	}

	@JsonIgnore
	public String getAEmail() {
		return this.aEmail;
	}

	public void setAEmail(String aEmail) {
		this.aEmail = aEmail;
	}

	@JsonIgnore
	public String getAFullname() {
		return this.aFullname;
	}

	public void setAFullname(String aFullname) {
		this.aFullname = aFullname;
	}

	@JsonIgnore
	public String getARfid() {
		return this.aRfid;
	}

	public void setARfid(String aRfid) {
		this.aRfid = aRfid;
	}

	public List<LabelInfo> getLabelInfos() {
		return this.labelInfos;
	}

	public void setLabelInfos(List<LabelInfo> labelInfos) {
		this.labelInfos = labelInfos;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorEmail() {
		return errorEmail;
	}

	public String getErrorFullname() {
		return errorFullname;
	}

	public boolean isError() {
		return error;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void setErrorEmail(String errorEmail) {
		this.errorEmail = errorEmail;
	}

	public void setErrorFullname(String errorFullname) {
		this.errorFullname = errorFullname;
	}

	public void setOwnId(int ownId) {
		this.ownId = ownId;
	}

	public int getOwnId() {
		return ownId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}

	public String getErrorRfid() {
		return errorRfid;
	}

	public void setErrorRfid(String errorRfid) {
		this.errorRfid = errorRfid;
	}

	public boolean isIn() {
		return isIn;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

	public boolean isOut() {
		return isOut;
	}

	public void setOut(boolean isOut) {
		this.isOut = isOut;
	}

	@PreUpdate
	@PrePersist
	void updateSearchString() {
		String in_out = "";
		if (this.isIn() && this.isOut())
			in_out = "có mặt";
		else if (!this.isIn() && !this.isOut())
			in_out = "vắng mặt";
		else if (this.isIn() && !this.isOut())
			in_out = "chỉ vào";
		else
			in_out = "chỉ ra";
		
		final String fullSearchString = StringUtils.join(
				Arrays.asList(this.aFullname, this.aRfid, this.aCode, this.aEmail, in_out),
				this.getLabelInfos().stream().map(e -> e.getLbContent()).collect(Collectors.toList()));
		this.searchString = StringUtils.substring(fullSearchString, 0, 999);
	}

	@Override
	public String toString() {
		return getACode() + " " + getLabelInfos().stream().map(l -> "{" + l.getLbId() + "," + l.getLbContent() + "}")
				.collect(Collectors.toList());
	}
}
