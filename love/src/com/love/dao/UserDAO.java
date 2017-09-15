package com.love.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.love.domain.User;
import com.love.utils.CommonUtils;
import com.love.utils.JDBCUtils;

public class UserDAO extends CommonDAO<User> {
	@Override
	public List<User> toResults(ResultSet rs) throws SQLException{
		List<User> users=new ArrayList<User>();
		try {
			while(rs.next()){
				User user=new User();
				user.setCreateTime(rs.getDate("createTime"));
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setIsDeleted((Boolean)rs.getObject("isDeleted"));
				user.setIsEmailVerified((Boolean)rs.getObject("isEmailVerified"));
				user.setIsMale((Boolean)rs.getObject("isMale"));
				user.setPassword(rs.getString("password"));
				user.setPasswordSalt(rs.getString("passwordSalt"));
				user.setUsername(rs.getString("username"));
				user.setImagePath(rs.getString("imagePath"));
				Timestamp timestamp=rs.getTimestamp("logoutDateTime");
				if(timestamp!=null){
					user.setLogoutDateTime(new Date(timestamp.getTime()));
				}
				users.add(user);
			}
			return users;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public User selectByEmail(String email) throws SQLException{
		String sql="select * from T_Users where email=?";
		ResultSet rs=JDBCUtils.executeQuery(sql, email);
		List<User> users=toResults(rs);
		if(users.size()==1){
			return users.get(0);
		}else{
			return null;
		}
	}
	
	public User selectByEmailAndPassword(String email,String password) throws SQLException{
		String sql="select * from T_Users where email=? and isDeleted is null";
		ResultSet rs=JDBCUtils.executeQuery(sql, email);
		List<User> users=toResults(rs);
		if(users.size()==1){
			User user=users.get(0);
			String ps=user.getPassword();
			String passwordSalt=user.getPasswordSalt();
			if(CommonUtils.calcMD5(passwordSalt+password).equalsIgnoreCase(ps)){
				return user;
			}
		}
		return null;
		
	}
	
	public User selectByUsername(String username) throws SQLException{
		String sql="select * from T_Users where username=? and isDeleted is null";
		ResultSet rs=JDBCUtils.executeQuery(sql, username);
		List<User> users=toResults(rs);
		if(users.size()==1){
			return users.get(0);
		}else{
			return null;
		}
		
	}
	
	
	
	

}
