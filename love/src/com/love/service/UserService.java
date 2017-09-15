package com.love.service;

import java.sql.SQLException;

import com.love.dao.UserDAO;
import com.love.domain.User;

public class UserService {
	private UserDAO userDAO=new UserDAO();
	public void insert(User user){
		try {
			userDAO.insert(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User selectByEmail(String email){
		try {
			return userDAO.selectByEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User selectByEmailAndPassword(String email,String password){
		try {
			return userDAO.selectByEmailAndPassword(email, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User selectByUserId(int id,Class<User> cla){
		try {
			return userDAO.selectById(id, cla);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void update(User user){
		try {
			userDAO.update(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public User selectByUsername(String username){
		try {
			return userDAO.selectByUsername(username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	

}
