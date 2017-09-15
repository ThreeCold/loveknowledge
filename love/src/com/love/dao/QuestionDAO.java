package com.love.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.love.domain.Condition;
import com.love.domain.Question;
import com.love.domain.User;
import com.love.utils.JDBCUtils;

public class QuestionDAO {
	
	public List<Question> toResults(ResultSet rs) throws SQLException {
		List<Question> questions=new ArrayList<Question>();
		try {
			while(rs.next()){
				Question question=new Question();
				Timestamp timestamp=rs.getTimestamp("createTime");
				question.setCreateTime(new Date(timestamp.getTime()));
				question.setId(rs.getInt("id"));
				question.setUserId(rs.getInt("userId"));
				question.setUsername(rs.getString("username"));
				question.setImagePath(rs.getString("imagePath"));
				question.setDescription(rs.getString("description"));
				question.setReward(rs.getInt("reward"));
				question.setSubjectId(rs.getInt("subjectId"));
				question.setTitle(rs.getString("title"));
				question.setResolvedDateTime(rs.getDate("resolvedDateTime"));
				question.setIsResolved((Boolean)rs.getObject("isResolved"));
				question.setTags(rs.getString("tags"));
				question.setSubjectName(rs.getString("subjectName"));
				questions.add(question);
			}
			return questions;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public List<Question> toResults2(ResultSet rs) throws SQLException {
		List<Question> questions=new ArrayList<Question>();
		try {
			while(rs.next()){
				Question question=new Question();
				Timestamp timestamp=rs.getTimestamp("createTime");
				question.setCreateTime(new Date(timestamp.getTime()));
				question.setId(rs.getInt("id"));
				question.setUserId(rs.getInt("userId"));
				question.setDescription(rs.getString("description"));
				question.setReward(rs.getInt("reward"));
				question.setSubjectId(rs.getInt("subjectId"));
				question.setTitle(rs.getString("title"));
				question.setResolvedDateTime(rs.getDate("resolvedDateTime"));
				question.setIsResolved((Boolean)rs.getObject("isResolved"));
				question.setTags(rs.getString("tags"));
				question.setSubjectName(rs.getString("subjectName"));
				questions.add(question);
			}
			return questions;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public void insert(Question question) throws SQLException{
		String sql="insert into T_Questions (userId,subjectId,createTime,title,description,reward,tags)values(?,?,now(),?,?,?,?)";
		JDBCUtils.executeNonQuery(sql, question.getUserId(),question.getSubjectId(),question.getTitle(),question.getDescription(),question.getReward(),question.getTags());
	}
	
	public List<Question> selectPages(int pageSize,int pageNum,String orderBy,Condition condition) throws SQLException{
		StringBuilder stringBuilder=new StringBuilder();
		List<Object> params=new ArrayList<Object>();
		stringBuilder.append("select q.*,s.name as subjectName from T_Questions q left join T_Subjects s on q.subjectId=s.id");
		if(condition!=null&&condition.getConditions().size()>0){
			stringBuilder.append(" where");
			Set<Entry<String,Object>> set=condition.getConditions().entrySet();
			for(Entry<String,Object> entry:set){
				String sql=entry.getKey();
				Object value=entry.getValue();
				stringBuilder.append(" ").append(sql).append(" and");
				params.add(value);
			}
			int length=stringBuilder.length();
			stringBuilder.delete(length-3, length);
		}
		if(orderBy!=null&&orderBy.length()>0){
			stringBuilder.append(" order by "+orderBy);
		}
		stringBuilder.append(" limit ?,? ");
		//System.out.println(stringBuilder.toString());
		params.add((pageNum-1)*pageSize);
		params.add(pageSize);
		ResultSet rs=JDBCUtils.executeQuery(stringBuilder.toString(), params.toArray(new Object[params.size()]));
		return toResults2(rs);
		
	}
	
	
	
	public Question selectById(int questionId) throws SQLException{
		String sql="select q.*,u.username,u.imagePath,s.name as subjectName from T_Questions q left join T_Subjects s on q.subjectId=s.id left join  T_Users u on q.userId=u.id where q.id=?";
		ResultSet rs=JDBCUtils.executeQuery(sql, questionId);
		return toResults(rs).get(0);
	}
	
	public Number selectQuestionsCountOfTheUser(int userId) throws SQLException{
		String sql="select count(*) from T_Questions where userId=?";
		return (Number) JDBCUtils.executeSingle(sql, userId);
	}
	
	public List<Question> selectQuestionsAnsweredJustOfTheUser(User user) throws SQLException{
		String sql="select q.*,s.name as subjectName from T_Questions q,T_Answers a,T_Subjects s where q.id=a.questionId and q.subjectId=s.id and  q.userId=? and  unix_timestamp(a.createDateTime) >unix_timestamp(?) group by q.id";
		ResultSet rs=JDBCUtils.executeQuery(sql, user.getId(),user.getLogoutDateTime());
		return toResults2(rs);
	}
	
	public Number selectCountOfAllQuestions() throws SQLException{
		String sql="select count(*) from T_Questions";
		return (Number) JDBCUtils.executeSingle(sql);
	}
	
	
	
	
	

}
