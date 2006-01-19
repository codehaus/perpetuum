package org.perpetuum.core.model;

public class RealmMembership {
	private Long id;
	private Long userid;
	private Long roleid;
	private long realmid;
	
	public RealmMembership() {}
	
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
	 * @return Returns the realmid.
	 */
	public long getRealmid() {
		return realmid;
	}
	
	/**
	 * @param realmid The realmid to set.
	 */
	public void setRealmid(long realmid) {
		this.realmid = realmid;
	}
	
	/**
	 * @return Returns the roleid.
	 */
	public Long getRoleid() {
		return roleid;
	}
	
	/**
	 * @param roleid The roleid to set.
	 */
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	
	/**
	 * @return Returns the userid.
	 */
	public Long getUserid() {
		return userid;
	}
	
	/**
	 * @param userid The userid to set.
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
