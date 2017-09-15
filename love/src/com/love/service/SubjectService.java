package com.love.service;

import java.util.List;

import com.love.dao.SubjectDAO;
import com.love.domain.Subject;

public class SubjectService {
    private SubjectDAO subjectDAO=new SubjectDAO();
    public List<Subject> selectAll(Class<Subject> cla){
    	try {
			return subjectDAO.selectAll(cla);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
}
