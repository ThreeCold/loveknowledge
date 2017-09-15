package com.love.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.love.dao.CommentDAO;
import com.love.domain.Comment;
import com.love.utils.JDBCUtils;

public class CommentService {
	private CommentDAO dao=new CommentDAO();
	public List<Comment> selectPageCommentsByArticleId(int id,int pageNum,int pageSize,String orderBy){
		try {
			return dao.selectPageCommentsByArticleId(id, pageSize, pageNum, orderBy);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public long selectTotalCountByArticleId(int id){
		try {
			return (long) dao.selectTotalCountByArticleId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public long selectRootCommentTotalCountByArticleId(int id){
		try {
			return (long) dao.selectRootCommentTotalCountByArticleId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(Comment comment){
		try {
			dao.insert(comment);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void selectChildrenComments(Comment comment) {
		Connection conn=null;
		try{
			conn=JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			dao.selectChildrenComments(conn, comment);
			conn.commit();
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.closeQuietly(conn);
		}
	}

}
