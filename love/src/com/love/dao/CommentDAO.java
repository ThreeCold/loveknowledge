package com.love.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.love.domain.Article;
import com.love.domain.Comment;
import com.love.utils.JDBCUtils;

public class CommentDAO  {
	public void insert(Comment comment) throws SQLException{
		String sql="insert into T_Comments(userId,articleId,content,parentId,createDateTime)values(?,?,?,?,now())";
		JDBCUtils.executeNonQuery(sql, comment.getUserId(),comment.getArticleId(),comment.getContent(),comment.getParentId());
	}
	
	public List<Comment> toResults(ResultSet rs) throws SQLException {
		List<Comment> comments=new ArrayList<Comment>();
		try {
			while(rs.next()){
				Comment comment=new Comment();
				comment.setArticleId(rs.getInt("articleId"));
				comment.setUserId(rs.getInt("userId"));
				comment.setContent(rs.getString("content"));
				Timestamp timestamp=rs.getTimestamp("createDateTime");
				comment.setCreateDateTime(new Date(timestamp.getTime()));
				comment.setUsername(rs.getString("username"));
				comment.setImagePath(rs.getString("imagePath"));
				comment.setParentId((Integer)rs.getObject("parentId"));
				comment.setId(rs.getInt("id"));
				comment.setPraiseCount(rs.getInt("praiseCount"));
				comments.add(comment);
			}
			return comments;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}
	
	public List<Comment> toResults2(ResultSet rs) throws SQLException {//避免conn关闭
		List<Comment> comments=new ArrayList<Comment>();
		try {
			while(rs.next()){
				Comment comment=new Comment();
				comment.setArticleId(rs.getInt("articleId"));
				comment.setUserId(rs.getInt("userId"));
				comment.setContent(rs.getString("content"));
				Timestamp timestamp=rs.getTimestamp("createDateTime");
				comment.setCreateDateTime(new Date(timestamp.getTime()));
				comment.setUsername(rs.getString("username"));
				comment.setImagePath(rs.getString("imagePath"));
				comment.setParentId((Integer)rs.getObject("parentId"));
				comment.setId(rs.getInt("id"));
				comment.setPraiseCount(rs.getInt("praiseCount"));
				comments.add(comment);
			}
			return comments;
		} finally{
			JDBCUtils.closeQuietly(rs);
			JDBCUtils.closeQuietly(rs.getStatement());
		}
	}
    
	public List<Comment> selectPageCommentsByArticleId(int id,int pageSize,int pageNum,String orderBy) throws SQLException{
		String sql="select c.*,u.username,u.imagePath from T_Comments c join T_Users u on c.userId=u.id where c.articleId=? and c.parentId is null order by "+orderBy+" limit ?,? ";
		ResultSet rs=JDBCUtils.executeQuery(sql, id,(pageNum-1)*pageSize,pageSize);
		return toResults(rs);
	}
	
	public Number selectTotalCountByArticleId(int id) throws SQLException{
		String sql="select count(*) from T_Comments where articleId=?";
		return  (Number) JDBCUtils.executeSingle(sql, id);
	}
	
	public Number selectRootCommentTotalCountByArticleId(int id) throws SQLException{
		String sql="select count(*) from T_Comments where articleId=? and parentId is null";
		return  (Number) JDBCUtils.executeSingle(sql, id);
	}
	
	public void selectChildrenComments(Connection conn,Comment parentComment) throws SQLException{
		String sql="select c.*,u.username,u.imagePath from T_Comments c  join T_Users u on c.userId=u.id where c.parentId=?";
		ResultSet rs=JDBCUtils.executeQuery(conn, sql,parentComment.getId());
		List<Comment> comments=toResults2(rs);
		for(Comment comment:comments){
			selectChildrenComments(conn,comment);
		}
		parentComment.setChildren(comments);
	}
}
