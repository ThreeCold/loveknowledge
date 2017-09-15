package com.love.service;

import java.sql.SQLException;
import java.util.List;

import com.love.dao.QuestionDAO;
import com.love.domain.Condition;
import com.love.domain.Question;
import com.love.domain.User;

public class QuestionService {
	private QuestionDAO dao=new QuestionDAO();
	public List<Question> selectPages(int pageSize,int pageNum,String orderBy,Condition condition){
		try {
			return dao.selectPages(pageSize, pageNum,orderBy,condition);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void insert(Question question){
		try {
			dao.insert(question);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Question selectById(int questionId){
		try {
			return dao.selectById(questionId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Number selectQuestionsCountOfTheUser(int userId){
		try {
			return dao.selectQuestionsCountOfTheUser(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Question> selectQuestionsAnsweredJustOfTheUser(User user){
		try {
			return dao.selectQuestionsAnsweredJustOfTheUser(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Number selectCountOfAllQuestions(){
		try {
			return dao.selectCountOfAllQuestions();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
