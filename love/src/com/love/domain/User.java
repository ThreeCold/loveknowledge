package com.love.domain;

import java.util.Date;

public class User {
	private int id;
	private String email;
	private String password;
	private String passwordSalt;
	private String username;
	private Boolean isMale;
	private Boolean isEmailVerified;
	private Boolean isDeleted;
	private Date createTime;
	private String imagePath;
	private Date logoutDateTime;
	
	public Date getLogoutDateTime() {
		return logoutDateTime;
	}
	public void setLogoutDateTime(Date logoutDateTime) {
		this.logoutDateTime = logoutDateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getIsMale() {
		return isMale;
	}
	public void setIsMale(Boolean isMale) {
		this.isMale = isMale;
	}
	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}
	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
	
	
	
	

}
