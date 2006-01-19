package org.perpetuum.core.model;

public class Role {
	private Long id;
	private String rolename;
	private String description;
	private boolean enabled;
	
	public Role() {}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return Returns the rolename.
	 */
	public String getRolename() {
		return rolename;
	}
	
	/**
	 * @param rolename The rolename to set.
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
