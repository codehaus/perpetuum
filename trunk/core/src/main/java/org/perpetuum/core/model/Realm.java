package org.perpetuum.core.model;

import java.util.HashSet;
import java.util.Set;

public class Realm {
	private Long id;
	private String realmname;
	private String description;
	private boolean enabled;
	private Long parentid;
	private Set children = new HashSet();
	
	public Realm() {}
	
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
	 * @return Returns the parentid.
	 */
	public Long getParentid() {
		return parentid;
	}
	
	/**
	 * @param parentid The parentid to set.
	 */
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	
	/**
	 * @return Returns the realmname.
	 */
	public String getRealmname() {
		return realmname;
	}
	
	/**
	 * @param realmname The realmname to set.
	 */
	public void setRealmname(String realmname) {
		this.realmname = realmname;
	}

	/**
	 * @return Returns the children.
	 */
	public Set getChildren() {
		return children;
	}

	/**
	 * @param children The children to set.
	 */
	public void setChildren(Set children) {
		this.children = children;
	}
}
