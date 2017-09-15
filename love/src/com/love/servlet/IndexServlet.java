package com.love.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.love.utils.VerifyCodeUtils;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action=req.getParameter("action");
		if("imageCode".equals(action)){
			imageCode(req, resp);
		}
		
	}
	
	
	public void imageCode(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
  	  String imageCode=VerifyCodeUtils.generateVerifyCode(4);
  	  response.setContentType("image/jpeg");
  	  request.getSession().setAttribute("imageCode", imageCode);
   	  VerifyCodeUtils.outputImage(100, 40, response.getOutputStream(), imageCode);
  }

}
