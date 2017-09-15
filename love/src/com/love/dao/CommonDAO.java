package com.love.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.love.domain.User;
import com.love.utils.JDBCUtils;

public class CommonDAO<T> {
	public void insert(T t) throws SQLException {
		Connection conn=JDBCUtils.getConnection();
		insert(conn, t);
	}
	
	public void insert(Connection conn,T t) throws SQLException{
		Class cla = t.getClass();
		String tableName="T_"+cla.getSimpleName()+"s";
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2=new StringBuilder();
		sql.append("insert into ").append(tableName).append(" (");
		sql2.append("(");
		Field[] fields = cla.getDeclaredFields();
		Object[] params = new Object[fields.length-1];
		int index=0;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if(!"id".equals(fieldName)){
				sql.append(fieldName+",");
				sql2.append("?,");
				char firstChar = fieldName.charAt(0);
				char upperFirstChar = (char) (firstChar - 'a' + 'A');
				fieldName = upperFirstChar + fieldName.substring(1);
				try {
					Method method = cla.getDeclaredMethod("get" + fieldName);
					params[index] = method.invoke(t);
					index++;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
		}
	    sql.deleteCharAt(sql.length()-1);
		sql2.deleteCharAt(sql2.length()-1);
		sql.append(")values").append(sql2).append(")");
		//System.out.println(sql.toString());
		JDBCUtils.executeInsert(conn,sql.toString(), params);
	}

	public static void main(String[] args) throws Exception {
		CommonDAO<User> dao=new CommonDAO<User>();
		List<User> users=dao.selectAll(User.class);
		for(User user:users){
			System.out.println(user.getEmail());
		}
	}
	
	public void update(T t) throws SQLException{
		Class cla = t.getClass();
		String tableName="T_"+cla.getSimpleName()+"s";
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableName).append(" set ");
		Field[] fields = cla.getDeclaredFields();
		Object[] params = new Object[fields.length];
		int index=0;
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			char firstChar = fieldName.charAt(0);
			char upperFirstChar = (char) (firstChar - 'a' + 'A');
			fieldName = upperFirstChar + fieldName.substring(1);
			try {
				Method method = cla.getDeclaredMethod("get" + fieldName);
				fieldName=fields[i].getName();
				if("id".equals(fieldName)){
					params[params.length-1]=method.invoke(t);
				}else{
					sql.append(fieldName+"=?,");
					params[index]=method.invoke(t);
				    index++;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			
		}
	    sql.deleteCharAt(sql.length()-1);
	    sql.append(" where id=?");
	    JDBCUtils.executeNonQuery(sql.toString(), params);
	    
		
	}
	
	public T selectById(int id,Class<T> cla) throws Exception{
		StringBuilder sql=new StringBuilder();
		String tableName="T_"+cla.getSimpleName()+"s";
	    sql.append("select * from ").append(tableName).append(" where id=?");
		ResultSet rs=JDBCUtils.executeQuery(sql.toString(), id);
		List<T> result=toResults(rs);
		if(result.size()==1){
			return result.get(0);
		}else{
			return null;
		}
	}
	
	public List<T> selectAll(Class<T> cla) throws Exception{
		StringBuilder sql=new StringBuilder();
		String tableName="T_"+cla.getSimpleName()+"s";
	    sql.append("select * from ").append(tableName);
		ResultSet rs=JDBCUtils.executeQuery(sql.toString());
		return toResults(rs);
	}
	
	public List<T> selectPages(Class<T> cla,int pageSize,int pageNum,String orderBy) throws SQLException{
		StringBuilder sql=new StringBuilder();
		String tableName="T_"+cla.getSimpleName()+"s";
	    sql.append("select * from ").append(tableName).append(" order by ?");
	    sql.append(" limit ?,?");
		ResultSet rs=JDBCUtils.executeQuery(sql.toString(),orderBy,(pageNum-1)*pageSize,pageSize);
		return toResults(rs);
	}
	public List<T> selectPages(Class<T> cla,int pageSize,int pageNum) throws SQLException{
		StringBuilder sql=new StringBuilder();
		String tableName="T_"+cla.getSimpleName()+"s";
	    sql.append("select * from ").append(tableName);
	    sql.append(" limit ?,?");
		ResultSet rs=JDBCUtils.executeQuery(sql.toString(),(pageNum-1)*pageSize,pageSize);
		return toResults(rs);
	}
	
	public List<T> toResults(ResultSet rs)throws SQLException{
		return null;
	}
	
	public void deleteById(Connection conn,int id,Class<T> cla) throws SQLException{
		String tableName=cla.getSimpleName();
		StringBuilder sql=new StringBuilder();
		sql.append("delete from T_").append(tableName).append("s where id=?");
		JDBCUtils.executeNonQuery(conn,sql.toString(), id);
		
		
	}
	
}
