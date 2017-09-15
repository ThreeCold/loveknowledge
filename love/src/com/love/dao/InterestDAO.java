package com.love.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.love.domain.Interest;
import com.love.utils.JDBCUtils;

public class InterestDAO extends CommonDAO<Interest> {
	@Override
    public List<Interest> toResults(ResultSet rs) throws SQLException {
    	List<Interest> interests=new ArrayList<Interest>();
		try {
			while(rs.next()){
	    		Interest interest=new Interest();
			    interest.setId(rs.getInt("id"));
			    interest.setSubjectId(rs.getInt("subjectId"));
				interest.setUserId(rs.getInt("userId"));
				interests.add(interest);
			}
			return interests;
		} finally{
			JDBCUtils.closeAll(rs);
		}
    	
    }
	
	public List<Interest> selectByUserId(Connection conn,int userId) throws SQLException{
		String sql="select * from T_Interests where userId=?";
		ResultSet rs=JDBCUtils.executeQuery(conn,sql, userId);
		return toResults(rs);
	}
	
	public void deleteByUserId(Connection conn,int userId) throws SQLException{
		String sql="delete from T_Interests where userId=?";
		JDBCUtils.executeNonQuery(conn,sql, userId);
	}
	
	
}
