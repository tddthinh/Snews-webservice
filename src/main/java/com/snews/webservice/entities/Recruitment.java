package com.snews.webservice.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "recruitment")
@NamedQuery(name = "Recruitment.findAll", query = "SELECT t FROM Recruitment t")
public class Recruitment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RM_ID", unique = true, nullable = false)
	@JsonProperty("id")
	private int id;

	@Column(name = "RM_COMPANY")
	private String company;
	@Column(name = "RM_PHONE")
	private String phone;
	@Column(name = "RM_MAIL")
	private String mail;
	@Column(name = "RM_RECRUITER")
	private String recruiter;
	@Column(name = "RM_QUANITY")
	private int quanity;
	@Column(name = "RM_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	@Column(name = "RM_FIELD")
	private String field;
	@Column(name = "RM_VACANCY")
	private String vacancy;
	@Column(name = "RM_SKILL")
	private String skill;
	@Column(name = "RM_FILE")
	private String file;
	
	@ManyToOne
	@JoinColumn(name = "U_ID", nullable = false)
	private Account account;

	@JsonProperty("author")
	@Transient
	private String author;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@JsonIgnore
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getAuthor() {
		return getAccount().getUFullname();
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getdate() {
		return date;
	}
	public void setdate(Date date) {
		this.date = date;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getQuanity() {
		return quanity;
	}
	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}
	public String getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getVacancy() {
		return vacancy;
	}
	public void setVacancy(String vacancy) {
		this.vacancy = vacancy;
	}
}
