package com.love.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.love.dao.InterestDAO;
import com.love.domain.Interest;
import com.love.utils.JDBCUtils;

public class InterestService {
	private InterestDAO interestDAO=new InterestDAO();
	public void updateInterests(int userId,int[] subjectIds){
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			conn.setAutoCommit(false);
            interestDAO.deleteByUserId(conn, userId);			
			for(int i=0;i<subjectIds.length;i++){
				Interest interest=new Interest();
				interest.setUserId(userId);
				interest.setSubjectId(subjectIds[i]);
				interestDAO.insert(conn, interest);
			}
			conn.commit();
		} catch (SQLException e) {
			JDBCUtils.rollBack(conn);
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.closeQuietly(conn);
		}
	}
	
	public List<Interest> selectByUserId(int userId){
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			return interestDAO.selectByUserId(conn,userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.closeQuietly(conn);
		}
	}
	

}
