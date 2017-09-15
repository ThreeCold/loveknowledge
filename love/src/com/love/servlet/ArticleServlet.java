package com.love.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.love.domain.Article;
import com.love.domain.Comment;
import com.love.domain.Subject;
import com.love.service.ArticleService;
import com.love.service.CommentService;
import com.love.service.SubjectService;
import com.love.utils.AjaxResult;
import com.love.utils.CommonUtils;

@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
	private ArticleService articleService=new ArticleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action=req.getParameter("action");
		if("detail".equals(action)){
			detail(req, resp);
		}else if("addPraiseCount".equals(action)){
			addPraiseCount(req, resp);
		}else if("list".equals(action)){
			list(req, resp);
		}
		
	}
	
	public void detail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
  	    String pageNumStr=request.getParameter("pageNum");
  	    int pageSize=6;
  	    int pageNum=1;
  	    String orderBy="praiseCount desc";
  	    if(!CommonUtils.hasEmpty(pageNumStr)){
  	    	pageNum=Integer.parseInt(pageNumStr);
  	    }
		int id=Integer.parseInt(request.getParameter("id"));
  	    Article article=articleService.selectById(id);
  	    List<Article> articles=articleService.selectPages(Article.class, 6, 1);//得到推荐的其他的文章
  	    CommentService commentService=new CommentService();
	    List<Comment> comments=commentService.selectPageCommentsByArticleId(id, pageNum, pageSize, orderBy);
	    long totalCount=commentService.selectTotalCountByArticleId(id);
	    long rootTotalCount=commentService.selectRootCommentTotalCountByArticleId(id);
	    long totalPages=rootTotalCount%6==0?rootTotalCount/6:rootTotalCount/6+1;
  	    request.setAttribute("article", article);
  	    request.setAttribute("articles", articles);
  	    request.setAttribute("comments", comments);
  	    request.setAttribute("totalCount", totalCount);
  	    request.setAttribute("pageNum", pageNum);
  	    request.setAttribute("totalPages", totalPages);
		request.getRequestDispatcher("/WEB-INF/article/singleArticle.jsp").forward(request, response);
  }
	
	public void addPraiseCount(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String articleIdStr=request.getParameter("articleId");
		int articleId=Integer.parseInt(articleIdStr);
		articleService.addPraiseCount(articleId);
		CommonUtils.sendJson(response, new AjaxResult("success","点赞成功"));
	}
	public void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		SubjectService subjectService=new SubjectService();
		List<Subject> subjects=subjectService.selectAll(Subject.class);
		List<Article> articles=articleService.selectPages(Article.class, 5, 1);
		request.setAttribute("articles", articles);
		request.setAttribute("subjects", subjects);
		request.setAttribute("totalPages", 10);
		request.setAttribute("pageNum", 1);
		request.getRequestDispatcher("/WEB-INF/article/articleList.jsp").forward(request, response);
	}
	
	

}
