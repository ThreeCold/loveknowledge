package com.love.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.love.dao.AnswerDAO;
import com.love.domain.Answer;
import com.love.utils.JDBCUtils;

public class AnswerService {
	private AnswerDAO answerDAO=new AnswerDAO();
	public void insert(Answer answer){
		try {
			answerDAO.insert(answer);
		} catch (SQLException e) {
			throw new RuntimeException(e);		}
	}
	
	public List<Answer> selectRootAnswersByQuestionId(int questionId,int pageSize,int pageNum,String orderBy){
		try {
			return answerDAO.selectRootAnswersByQuestionId(questionId, pageSize, pageNum, orderBy);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Number selectCountOfAllRootAnswers(int questionId){
		try {
			return answerDAO.selectCountOfAllRootAnswers(questionId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void loadChildren(Answer parent){
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			answerDAO.loadChildren(conn, parent);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.closeQuietly(conn);
			
		}
	}
	
	public Number selectCountOfAllAnswers(int questionId){
		try {
			return answerDAO.selectCountOfAllAnswers(questionId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
