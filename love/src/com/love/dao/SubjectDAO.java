package com.love.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.love.domain.Subject;
import com.love.domain.User;
import com.love.utils.JDBCUtils;

public class SubjectDAO extends CommonDAO<Subject> {
	@Override
	public List<Subject> toResults(ResultSet rs) throws SQLException{
		List<Subject> subjects=new ArrayList<Subject>();
		try {
			while(rs.next()){
				Subject subject=new Subject();
				subject.setId(rs.getInt("id"));
				subject.setName(rs.getString("name"));
				subjects.add(subject);
			}
			return subjects;
		} finally{
			JDBCUtils.closeAll(rs);
		}
	}

}
