package com.snews.webservice.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name = "account")
@NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
public class Account implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "U_ID", unique = true, nullable = false)
	private int uId;

	@Column(name = "U_EMAIL", length = 64, unique = true)
	private String uEmail;

	@Column(name = "U_FULLNAME", length = 64)
	private String uFullname;

	@Column(name = "U_PASSWORD", length = 64)
	private String uPassword;

	@Column(name = "U_USERNAME", length = 32, unique = true, nullable = false)
	private String uUsername;

	@OneToMany(mappedBy = "account")
	private List<Event> events;
	
	@ManyToOne
	@JoinColumn(name = "U_RID")
	private Role role;
	
	@OneToMany(mappedBy = "account")
	private List<Topic> topics;

	public Account() {
	}

	public Account(int id) {
		this.uId = id;
	}

	@JsonProperty("id")
	public int getUId() {
		return this.uId;
	}

	public void setUId(int uId) {
		this.uId = uId;
	}

	@JsonProperty("email")
	public String getUEmail() {
		return this.uEmail;
	}

	public void setUEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	@JsonProperty("fullname")
	public String getUFullname() {
		return this.uFullname;
	}

	public void setUFullname(String uFullname) {
		this.uFullname = uFullname;
	}

	@JsonProperty("password")
	public String getUPassword() {
		return this.uPassword;
	}

	public void setUPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	@JsonProperty("username")
	public String getUUsername() {
		return this.uUsername;
	}

	public void setUUsername(String uUsername) {
		this.uUsername = uUsername;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setAccount(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setAccount(null);

		return event;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic addTopic(Topic topic) {
		getTopics().add(topic);
		topic.setAccount(this);

		return topic;
	}

	public Topic removeTopic(Topic topic) {
		getTopics().remove(topic);
		topic.setAccount(null);

		return topic;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(getRole().getRName()));
		return authorities;
	}

	@Override
	public int hashCode() {
		return uId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return getUPassword();
	}

	@Override
	public String getUsername() {
		return getUUsername();
	}

}