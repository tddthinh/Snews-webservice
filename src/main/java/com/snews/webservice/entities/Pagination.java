package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * The persistent class for the pagination database table.
 * 
 */
@Entity
@Table(name="pagination")
@NamedQuery(name="Pagination.findAll", query="SELECT p FROM Pagination p")
@JsonPropertyOrder(value= {"id", "number"})
public class Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="P_ID", unique=true, nullable=false, length=16)
	private String pId;

	@Column(name="P_NUMBER")
	private int pNumber;

	public Pagination() {
	}
	@JsonProperty("id")
	public String getPId() {
		return this.pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}
	@JsonProperty("number")
	public int getPNumber() {
		return this.pNumber;
	}

	public void setPNumber(int pNumber) {
		this.pNumber = pNumber;
	}

}