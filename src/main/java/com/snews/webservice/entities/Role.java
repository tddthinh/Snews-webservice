package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
@JsonPropertyOrder(value= {"id", "name"})
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="R_ID", unique=true, nullable=false)
	private int rId;

	@Column(name="R_NAME", nullable=false, length=8)
	private String rName;

	@OneToMany(mappedBy = "role")
	private List<Account> accounts;
	
	public Role() {
	}
	@JsonProperty("id")
	public int getRId() {
		return this.rId;
	}

	public void setRId(int rId) {
		this.rId = rId;
	}
	@JsonProperty("name")
	public String getRName() {
		return this.rName;
	}

	public void setRName(String rName) {
		this.rName = rName;
	}
	@JsonIgnore 
	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}