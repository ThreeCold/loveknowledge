package com.love.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.love.domain.Article;
import com.love.domain.Comment;
import com.love.domain.User;
import com.love.utils.JDBCUtils;

public class ArticleDAO extends CommonDAO<Article> {
	@Override
	public List<Article> toResults(ResultSet rs) throws SQLException {
		List<Article> articles=new ArrayList<Article>();
		try {
			while(rs.next()){
				Article article=new Article();
				article.setAuthor(rs.getString("author"));
				article.setContent(rs.getString("content"));
				article.setCreateTime(rs.getDate("createTime"));
				article.setDescription(rs.getString("description"));
				article.setId(rs.getInt("id"));
				article.setPraiseCount(rs.getInt("praiseCount"));
				article.setPublishTime(rs.getDate("publishTime"));
				article.setSubjectId(rs.getInt("SubjectId"));
				article.setTitle(rs.getString("title"));
				article.setUserId((Integer)rs.getObject("userId"));
				articles.add(article);
			}
			return articles;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public int selectPraiseCount(Connection conn,int articleId) throws SQLException{
	   String sql="select praiseCount from T_Articles where id=? for update";
	   return  (int) JDBCUtils.executeSingle(conn, sql, articleId);
	}
	
	public void addPraiseCount(Connection conn,int articleId,int praiseCount) throws SQLException{
		String sql="update T_Articles set praiseCount=? where id=? ";
		JDBCUtils.executeNonQuery(conn, sql, praiseCount,articleId);
	}
	
	public void selectAllCount(){
		String sql="select count(*) from T_Articles";
		
	}
	
	
	
	
	
	
	
	
	
	

}
