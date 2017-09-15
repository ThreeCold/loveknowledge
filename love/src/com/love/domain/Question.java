package com.love.domain;

import java.util.Date;

public class Question {
	private int id;
	private int userId;
	private String username;
	private String imagePath;
	private int subjectId;
	private Date createTime;
	private String title;
	private String description;
	private int reward;
	private Date resolvedDateTime;
	private Boolean isResolved;
	private String tags;
	private String subjectName;
	private int countOfAnswers;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public Date getResolvedDateTime() {
		return resolvedDateTime;
	}
	public void setResolvedDateTime(Date resolvedDateTime) {
		this.resolvedDateTime = resolvedDateTime;
	}
	public Boolean getIsResolved() {
		return isResolved;
	}
	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getCountOfAnswers() {
		return countOfAnswers;
	}
	public void setCountOfAnswers(int countOfAnswers) {
		this.countOfAnswers = countOfAnswers;
	}
	
	
	
	
	

}
