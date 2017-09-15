package com.love.domain;

import java.util.HashMap;
import java.util.Map;

public class Condition {
	private Map<String,Object> conditions=new HashMap<String,Object>();

	public Map<String, Object> getConditions() {
		return conditions;
	}
	public void addCondition(String sql,Object value){
		conditions.put(sql, value);
	}
	
	
	
	

}
	
