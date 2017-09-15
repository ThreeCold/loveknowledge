package com.love.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.love.domain.User;
import com.love.service.UserService;

public class MySessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session=event.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			user.setLogoutDateTime(new Date());
			new UserService().update(user);
		}
		System.out.println("session过期了");
		
		
	}

}
