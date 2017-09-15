package com.love.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.love.dao.ArticleDAO;
import com.love.domain.Article;
import com.love.utils.JDBCUtils;

public class ArticleService {
	private ArticleDAO dao=new ArticleDAO();
	public List<Article> selectPages(Class<Article> cla,int pageSize,int pageNum){
		try {
			return dao.selectPages(cla, pageSize, pageNum);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Article selectById(int id){
		try {
			return dao.selectById(id, Article.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void addPraiseCount(int articleId){
		Connection conn=null;
		try {
			conn=JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			int praiseCount=dao.selectPraiseCount(conn, articleId);
			dao.addPraiseCount(conn, articleId, praiseCount+1);
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.closeQuietly(conn);
		}
	}

}
