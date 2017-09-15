package com.love.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.love.domain.Comment;
import com.love.domain.User;
import com.love.service.CommentService;
import com.love.utils.AjaxResult;
import com.love.utils.CommonUtils;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
	private CommentService commentService=new CommentService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action=req.getParameter("action");
		if("loadPages".equals(action)){
			loadPages(req, resp);
		}else if("submitComment".equals(action)){
			submitComment(req, resp);
		}else if("loadChildren".equals(action)){
			loadChildren(req, resp);
		}
		
	}
	
	public void loadPages(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String pageNumStr=req.getParameter("pageNum");
		String articleIdStr=req.getParameter("articleId");
		if(CommonUtils.hasEmpty(pageNumStr,articleIdStr)){
			CommonUtils.sendJson(resp, new AjaxResult("error","参数非法"));
			return;
		}
		int pageNum=Integer.parseInt(pageNumStr);
		int pageSize=6;
		String orderBy="praiseCount desc";
		int articleId=Integer.parseInt(articleIdStr);
		List<Comment> comments=commentService.selectPageCommentsByArticleId(articleId, pageNum, pageSize, orderBy);
		CommonUtils.sendJson(resp, new AjaxResult("success", comments));
	}
	
	public void submitComment(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			CommonUtils.sendJson(resp, new AjaxResult("error","你还没有登录"));
			return;
		}
		String content=req.getParameter("comment");
		String parentIdStr=req.getParameter("parentId");
		String articleId=req.getParameter("articleId");
		if(CommonUtils.hasEmpty(content)){
			CommonUtils.sendJson(resp, new AjaxResult("error","评论为空"));
			return;
		}
		if(CommonUtils.hasEmpty(user.getUsername())){
			CommonUtils.sendJson(resp, new AjaxResult("error","你还没有完善信息"));
			return;
		}
		Integer parentId=null;
		if(parentIdStr!=null){
			parentId=Integer.parseInt(parentIdStr);
		}
		Comment comment=new Comment();
		comment.setArticleId(Integer.parseInt(articleId));
		comment.setContent(content);
		comment.setParentId(parentId);
		comment.setUserId(user.getId());
		commentService.insert(comment);
		CommonUtils.sendJson(resp, new AjaxResult("success","评论成功"));
	}
	
	public void loadChildren(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		int id=Integer.parseInt(req.getParameter("id"));
		Comment comment=new Comment();
		comment.setId(id);
		commentService.selectChildrenComments(comment);
		CommonUtils.sendJson(resp, new AjaxResult("success",comment));
		
		
	}

}
