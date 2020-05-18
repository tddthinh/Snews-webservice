package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * The persistent class for the label_info database table.
 * 
 */
@Entity
@Table(name = "label_info")
@NamedQuery(name = "LabelInfo.findAll", query = "SELECT l FROM LabelInfo l")
public class LabelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LB_ID", unique = true, nullable = false)
	private int lbId;

	@Column(name = "LB_CONTENT", length = 64)
	private String lbContent;

	// bi-directional many-to-many association to Attendee
	@ManyToMany
	@JoinTable(name = "attendees_label", joinColumns = {
			@JoinColumn(name = "LB_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "A_ID", nullable = false) })
	private List<Attendee> attendees;

	public LabelInfo(String content) {
		this.lbContent = content;
	}

	public LabelInfo() {
	}

	@JsonProperty("id")
	public int getLbId() {
		return this.lbId;
	}

	public void setLbId(int lbId) {
		this.lbId = lbId;
	}

	@JsonProperty("content")
	public String getLbContent() {
		return this.lbContent;
	}

	public void setLbContent(String lbContent) {
		this.lbContent = lbContent;
	}

	@JsonIgnore
	public List<Attendee> getAttendees() {
		return this.attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

}