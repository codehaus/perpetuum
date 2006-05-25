package org.codehaus.perpetuum.model;

/**
 * This object is used to represent an actual Perpetuum user object.
 */
public class User {
	private Long id;
	private String username;
	private String password;
    private String realname;
	private String email;
	private boolean enabled;
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email The email to set.
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
     * @return Returns the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname The realname to set.
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }
}
