package com.easybuild.site.dto;

import javax.validation.constraints.NotNull;

public class UserDto {
	
	 
	    private String username;

	    @NotNull
	    private String password;
	    @NotNull
	    private String email;
	    
	    private String gender;


	    /**
	     * Default constructor
	     */
	    protected UserDto() {
	    }

	    /**
	     * Full constructor
	     *
	     * @param username
	     * @param password
	     */
	    public UserDto(String username, String password, String email) {
	        this.username = username;
	        this.password = password;
	        this.email = email;
	    }
	    
	    public UserDto(String password, String email) {
	        this.password = password;
	        this.email = email;
	    }


	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

}
