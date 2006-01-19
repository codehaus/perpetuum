package org.perpetuum.core.model;

import java.util.HashSet;
import java.util.Set;

public class User {
	private Long id;
	private String username;
	private String password;
	private String email;
	private boolean enabled;
	private Set memberships = new HashSet();
	
	public User() {}
	
	/**
	 * @return Returns the description.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email The description to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return Returns the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return Returns the username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return Returns the memberships.
	 */
	public Set getMemberships() {
		return memberships;
	}

	/**
	 * @param memberships The memberships to set.
	 */
	public void setMemberships(Set memberships) {
		this.memberships = memberships;
	}
}
