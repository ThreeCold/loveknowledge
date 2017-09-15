package com.love.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.love.domain.Answer;
import com.love.domain.Article;
import com.love.domain.Question;
import com.love.domain.Subject;
import com.love.domain.User;
import com.love.service.AnswerService;
import com.love.service.ArticleService;
import com.love.service.QuestionService;
import com.love.service.SubjectService;
import com.love.utils.AjaxResult;
import com.love.utils.CommonUtils;

@WebServlet("/question")
public class QuestionServlet extends HttpServlet {
	private QuestionService questionService=new QuestionService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action=req.getParameter("action");
		if("detail".equals(action)){
			detail(req,resp);
		}else if("submitQuestion".equals(action)){
			submitQuestion(req, resp);
		}else if("list".equals(action)){
			list(req,resp);
		}else if("submitAnswer".equals(action)){
			submitAnswer(req,resp);
		}else if("loadAnswers".equals(action)){
			loadAnswers(req,resp);
		}else if("loadChildren".equals(action)){
			loadChildren(req,resp);
		}
		
	}
	
	public void list(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<Question> questions=null;
		String header=req.getHeader("X-Requested-With");
		if(header==null||header.length()==0){
			SubjectService subjectService=new SubjectService();
			List<Subject> subjects=subjectService.selectAll(Subject.class);
			ArticleService articleService=new ArticleService();
			List<Article> articles=articleService.selectPages(Article.class, 6, 1);
			questions=questionService.selectPages(10, 1, "createTime desc", null);
			long totalCount=(long) questionService.selectCountOfAllQuestions();
			long totalPages=totalCount/10==0?totalCount/10:totalCount/10+1;
			req.setAttribute("subjects", subjects);
			req.setAttribute("questions", questions);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("pageNum", 1);
			req.setAttribute("articles", articles);
			req.getRequestDispatcher("/WEB-INF/question/questionList.jsp").forward(req, resp);
		}else{
			int pageNum=Integer.parseInt(req.getParameter("pageNum"));
			questions=questionService.selectPages(10, pageNum, "createTime", null);
			CommonUtils.sendJson(resp, new AjaxResult("success",questions));
		}
		
		
	}
	private void loadChildren(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int parentId=Integer.parseInt(req.getParameter("id"));
		Answer answer=new Answer();
		AnswerService answerService=new AnswerService();
		answer.setId(parentId);
		answerService.loadChildren(answer);
		CommonUtils.sendJson(resp, new AjaxResult("success",answer));
		
	}
	private void loadAnswers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int questionId=Integer.parseInt(req.getParameter("questionId"));
		int pageNum=Integer.parseInt(req.getParameter("pageNum"));
		AnswerService answerService=new AnswerService();
		List<Answer> answers=answerService.selectRootAnswersByQuestionId(questionId, 6, pageNum, "createDateTime desc");
		CommonUtils.sendJson(resp,new AjaxResult("success",answers));
		
		
	}
	private void submitAnswer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user=(User) req.getSession().getAttribute("user");
		if(user==null){
			CommonUtils.sendJson(resp, new AjaxResult("error","你还没有登录"));
			return;
		}
		if(user.getUsername()==null){
			CommonUtils.sendJson(resp, new AjaxResult("error","你还没有完善个人信息"));
			return;
		}
		String content=req.getParameter("content");
		if(CommonUtils.hasEmpty(content)){
			CommonUtils.sendJson(resp, new AjaxResult("error","评论不能为空"));
			return;
		}
		int questionId=Integer.parseInt(req.getParameter("questionId"));
		String parentIdStr=req.getParameter("parentId");
		Integer parentId=null;
		if(parentIdStr!=null&&parentIdStr.length()>0){
			parentId=Integer.parseInt(parentIdStr);
		}
		Answer answer=new Answer();
		AnswerService answerService=new AnswerService();
		answer.setContent(content);
		answer.setUserId(user.getId());
		answer.setQuestionId(questionId);
		answer.setParentId(parentId);
		answerService.insert(answer);
		CommonUtils.sendJson(resp, new AjaxResult("success","答复成功"));
		
	}
	public void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int questionId=Integer.parseInt(req.getParameter("questionId"));
		Question question=questionService.selectById(questionId);
		AnswerService answerService=new AnswerService();
		List<Answer> answers=answerService.selectRootAnswersByQuestionId(questionId, 6, 1, "createDateTime desc");
		long totalCountOfAnswers=(long) answerService.selectCountOfAllAnswers(questionId);
		long totalCountOfRootAnswers=(long) answerService.selectCountOfAllRootAnswers(questionId);
		long totalPages=totalCountOfRootAnswers%6==0?totalCountOfRootAnswers/6:totalCountOfRootAnswers/6+1;
		req.setAttribute("question", question);
		req.setAttribute("answers", answers);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("pageNum", 1);
		req.setAttribute("totalCountOfAnswers", totalCountOfAnswers);
		req.getRequestDispatcher("/WEB-INF/question/singleQuestion.jsp").forward(req, resp);
		
	}
	public void submitQuestion(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String title=req.getParameter("title");
		String description=req.getParameter("description");
		if(CommonUtils.hasEmpty(title,description)){
			CommonUtils.sendJson(resp, new AjaxResult("error","问题标题或问题描述为空"));
			return;
		}
		int subjectId=Integer.parseInt(req.getParameter("subjectId"));
		int reward=Integer.parseInt(req.getParameter("reward"));
		User user=(User) req.getSession().getAttribute("user");
		String tags=req.getParameter("tags");
		if(tags!=null&&tags.trim().length()>0){
			String[] parts=tags.split("\\s+");
			int length=parts.length;
			tags="";
			for(int i=0;i<length-1;i++ ){
				tags+=parts[i]+"&amp;";
			}
			tags+=parts[length-1];
			
		}
		Question question=new Question();
		question.setDescription(description);
		question.setReward(reward);
		question.setSubjectId(subjectId);
		question.setTags(tags);
		question.setTitle(title);
		question.setUserId(user.getId());
		questionService.insert(question);
		CommonUtils.sendJson(resp, new AjaxResult("success","提问成功"));
	}
	
	
	

}
