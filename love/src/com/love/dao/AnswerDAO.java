package com.love.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.love.domain.Answer;
import com.love.domain.Question;
import com.love.utils.JDBCUtils;

public class AnswerDAO {
	public void insert(Answer answer) throws SQLException{
		String sql="insert into T_Answers (userId,questionId,content,createDateTime,parentId)values(?,?,?,now(),?)";
		JDBCUtils.executeNonQuery(sql, answer.getUserId(),answer.getQuestionId(),answer.getContent(),answer.getParentId());
	}
	
	public List<Answer> toResults(ResultSet rs) throws SQLException {
		List<Answer> answers=new ArrayList<Answer>();
		try{
			while(rs.next()){
				Answer answer=new Answer();
				answer.setContent(rs.getString("content"));
				Timestamp timestamp=rs.getTimestamp("createDateTime");
				answer.setCreateDateTime(new Date(timestamp.getTime()));
				answer.setId(rs.getInt("id"));
				answer.setImagePath(rs.getString("imagePath"));
				answer.setUsername(rs.getString("username"));
				answer.setParentId((Integer)rs.getObject("parentId"));
				answer.setQuestionId(rs.getInt("questionId"));
				answer.setUserId(rs.getInt("userId"));
				answers.add(answer);
			}
			return answers;
		}finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public List<Answer> toResults2(ResultSet rs) throws SQLException {
		List<Answer> answers=new ArrayList<Answer>();
		try{
			while(rs.next()){
				Answer answer=new Answer();
				answer.setContent(rs.getString("content"));
				Timestamp timestamp=rs.getTimestamp("createDateTime");
				answer.setCreateDateTime(new Date(timestamp.getTime()));
				answer.setId(rs.getInt("id"));
				answer.setImagePath(rs.getString("imagePath"));
				answer.setUsername(rs.getString("username"));
				answer.setParentId((Integer)rs.getObject("parentId"));
				answer.setQuestionId(rs.getInt("questionId"));
				answer.setUserId(rs.getInt("userId"));
				answers.add(answer);
			}
			return answers;
		}finally{
			JDBCUtils.closeQuietly(rs);
			JDBCUtils.closeQuietly(rs.getStatement());
			
		}
		
		
	}
	
	public List<Answer> selectRootAnswersByQuestionId(int questionId,int pageSize,int pageNum,String orderBy) throws SQLException{
		String sql="select a.*,u.username,u.imagePath from T_Answers a,T_Users u where a.questionId=? and a.parentId is null and a.userId=u.id order by "+orderBy+" limit ?,?";
		ResultSet rs=JDBCUtils.executeQuery(sql, questionId,(pageNum-1)*pageSize,pageSize);
		return toResults(rs);
	}
	
	public Number selectCountOfAllRootAnswers(int questionId) throws SQLException{
		String sql="select count(*) from T_Answers where questionId=? and parentId is null";
		return (Number) JDBCUtils.executeSingle(sql, questionId);
	}
	
	public void loadChildren(Connection conn,Answer parent) throws SQLException{
		String sql="select a.*,u.username,u.imagePath from T_Answers a,T_Users u where a.parentId=? and a.userId=u.id";
		ResultSet rs=JDBCUtils.executeQuery(conn, sql, parent.getId());
		List<Answer> children=toResults2(rs);
		for(Answer child:children){
			loadChildren(conn, child);
		}
		parent.setChildren(children);
	}
	
	public Number selectCountOfAllAnswers(int questionId) throws SQLException{
		String sql="select count(*) from T_Answers where questionId=?";
		return (Number) JDBCUtils.executeSingle(sql, questionId);
		
	}
	
	
	
	

}
