package com.love.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;



public class JDBCUtils {
          private final static BasicDataSource ds;
          static{
        	  Properties pro=new Properties();
        	  try {
				pro.load(JDBCUtils.class.getResourceAsStream("/dbcp2.properties"));
				ds=BasicDataSourceFactory.createDataSource(pro);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
          }
        
          public static Connection getConnection() throws SQLException{
        	  return ds.getConnection();
          }
          public static void closeQuietly(AutoCloseable c){
        	  if(c!=null){
        		  try {
					c.close();
				} catch (Exception e) {
					
				}
        	  }
          }
          
          public static void rollBack(Connection conn){
        	  if(conn!=null){
	        		  try {
	      					conn.rollback();
		      		  } catch (SQLException e) {
		      				
		      		  }
        	  }
        	
          }
          
          public static void closeAll(ResultSet rs){
        	  if(rs!=null){
        		 
        		try {
        			 Statement stmt=rs.getStatement();
					Connection conn=stmt.getConnection();
					closeQuietly(rs);
					closeQuietly(stmt);
					closeQuietly(conn);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
        	  }
          }
          
          public static void closeStatementAndConnection(Statement stmt){
        	  if(stmt!=null){ 
        		try {
					Connection conn=stmt.getConnection();
					closeQuietly(stmt);
					closeQuietly(conn);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
        	  }
          }
          /**
           * 执行非查询代码
           * @param sql
           * @param params
           * @throws SQLException
           */
          public static void executeNonQuery(String sql, Object... params) throws SQLException{
        	  Connection conn=null;	  
        	 try {
        		  conn=ds.getConnection();
				executeNonQuery(conn, sql, params);
			} finally{
				closeQuietly(conn);
			}
          }
          
          public static void executeNonQuery(Connection conn,String sql, Object... params) throws SQLException{
	        	 PreparedStatement ps=null;	  
	        	  try {
					ps=conn.prepareStatement(sql);
					for(int i=0;i<params.length;i++){
						ps.setObject(i+1, params[i]);
					}
					ps.execute();
				} finally{
					closeQuietly(ps);
				}
          }
          
          public static ResultSet executeQuery(String sql,Object... params) throws SQLException{
        	  Connection conn=null;
        	  try{
        		  conn=ds.getConnection();
	        	 return executeQuery(conn, sql, params);
	        	  
        	  }catch(SQLException e){
        		  closeQuietly(conn);
        		  throw e;
        	  }
        			
          }
          
          public static ResultSet executeQuery(Connection conn,String sql,Object... params) throws SQLException{
        	  PreparedStatement ps=null;
        	  try{
	        	  ps=conn.prepareStatement(sql);
	        	  for(int i=0;i<params.length;i++){
	        		  ps.setObject(i+1, params[i]);
	        	  }
	        	  return  ps.executeQuery();
	        	  
        	  }catch(SQLException e){
        		  closeQuietly(ps);
        		  throw e;
        	  }
        			
          }
          
          /**
           * 得到最后插入的数据的id
           * @param sql
           * @param params
           * @return
           * @throws SQLException
           */
          public static long executeInsert(String sql,Object... params) throws SQLException{
        	  Connection conn=null;
         	  try {
 				conn=ds.getConnection();
 				return executeInsert(conn, sql, params);
 			} finally{
 				closeQuietly(conn);
 			}
          }
          
          public static long executeInsert(Connection conn,String sql,Object... params) throws SQLException{
         	 PreparedStatement ps=null;	
         	 PreparedStatement psForInsertId=null;
         	 ResultSet rs=null;
         	  try {
 				ps=conn.prepareStatement(sql);
 				for(int i=0;i<params.length;i++){
 					ps.setObject(i+1, params[i]);
 				}
 				ps.execute();
 				psForInsertId=conn.prepareStatement("select last_insert_id()");//需要使用同一个连接
 				rs=psForInsertId.executeQuery();
 				if(rs.next()){
 					return rs.getLong(1);
 				}else{
 					throw new RuntimeException("没有找到id字段");
 				}
 			} finally{
 				closeQuietly(ps);
 				closeQuietly(rs);
 				closeQuietly(psForInsertId);
 			}
          }
          
          public static Object executeSingle(String sql,Object... params) throws SQLException{
        	  Connection conn=null;
        	 try{
	        	  conn=ds.getConnection();
	        	 return executeSingle(conn, sql, params);
        	  }
        	  finally{
        		  closeQuietly(conn);
        	  }
          }
          
          public static Object executeSingle(Connection conn,String sql,Object... params) throws SQLException{
        	  PreparedStatement ps=null;
        	  ResultSet rs=null;
        	  try{
	        	  ps=conn.prepareStatement(sql);
	        	  for(int i=0;i<params.length;i++){
	        		  ps.setObject(i+1,params[i]);
	        	  }
	        	  rs= ps.executeQuery();
	        	  if(rs.next()){
	        		  return rs.getObject(1);
	        	  }else{
	        		  return null;
	        	  }
	        	  
        	  }
        	  finally{
        		 closeQuietly(rs);
        		 closeQuietly(ps);
        	  }
          }
          
         
}
