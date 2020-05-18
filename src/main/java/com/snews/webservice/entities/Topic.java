package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


/**
 * The persistent class for the topic database table.
 * 
 */
@Entity
@Table(name="topic")
@NamedQuery(name="Topic.findAll", query="SELECT t FROM Topic t")
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TP_ID", unique=true, nullable=false)
	@JsonProperty("id")
	private int tpId;

	@Lob
	@Column(name="TP_BRIEF")
	@JsonProperty("brief")
	private String tpBrief;

	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name="TP_DATE")
	@JsonProperty("date")
	private Date tpDate;

	@Lob
	@Column(name="TP_IMAGE")
	@JsonProperty("image")
	private String tpImage;

	@Lob
	@Column(name="TP_TITLE")
	@JsonProperty("title")
	private String tpTitle;

	@Lob
	@Column(name="TP_CONTENT")
	@JsonProperty("content")
	private String content;

	@ManyToOne
	@JoinColumn(name="U_ID", nullable=false)
	private Account account;
	
	@JsonProperty("author")
	@Transient
	private String author;
	
	public Topic() {
	}
	public int getTpId() {
		return this.tpId;
	}

	public void setTpId(int tpId) {
		this.tpId = tpId;
	}
	public String getTpBrief() {
		return this.tpBrief;
	}

	public void setTpBrief(String tpBrief) {
		this.tpBrief = tpBrief;
	}
	public Date getTpDate() {
		return this.tpDate;
	}

	public void setTpDate(Date tpDate) {
		this.tpDate = tpDate;
	}
	public String getTpImage() {
		return this.tpImage;
	}

	public void setTpImage(String tpImage) {
		this.tpImage = tpImage;
	}
	public String getTpTitle() {
		return this.tpTitle;
	}

	public void setTpTitle(String tpTitle) {
		this.tpTitle = tpTitle;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonIgnore
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	public String getAuthor() {
		return account.getUFullname();
	}
	
}