package com.love.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.love.domain.Article;
import com.love.domain.Condition;
import com.love.domain.Interest;
import com.love.domain.Question;
import com.love.domain.Subject;
import com.love.domain.User;
import com.love.service.ArticleService;
import com.love.service.InterestService;
import com.love.service.QuestionService;
import com.love.service.SubjectService;
import com.love.service.UserService;
import com.love.utils.AjaxResult;
import com.love.utils.CommonUtils;
import com.love.utils.MailUtils;

@WebServlet("/user")
@MultipartConfig(fileSizeThreshold = 10000, maxFileSize = 1000000, maxRequestSize = 1000000)
public class UserServlet extends HttpServlet {
	private UserService userService = new UserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("register".equals(action)) {
			register(req, resp);
		} else if ("login".equals(action)) {
			login(req, resp);
		} else if ("registerSubmit".equals(action)) {
			registerSubmit(req, resp);
		} else if ("index".equals(action)) {
			index(req, resp);
		} else if ("loginSubmit".equals(action)) {
			loginSubmit(req, resp);
		} else if ("logout".equals(action)) {
			logout(req, resp);
		} else if ("center".equals(action)) {
			center(req, resp);
		} else if ("centerSubmit".equals(action)) {
			centerSubmit(req, resp);
		} else if ("contact".equals(action)) {
			contact(req, resp);
		} else if ("askQuestion".equals(action)) {
			askQuestion(req, resp);
		} else if ("submitContact".equals(action)) {
			submitContact(req, resp);
		}else if("submitContactSuccess".equals(action)){
			submitContactSuccess(req,resp);
		}else if("updateLogoutDateTime".equals(action)){
			updateLogoutDateTime(req, resp);
		}else if("removeQuestionTip".equals(action)){
			removeQuestionTip(req, resp);
		}

	}

	private void submitContactSuccess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/success.jsp").forward(req, resp);
		
	}

	private void submitContact(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String message = req.getParameter("message");
		if(CommonUtils.hasEmpty(name,message)){
			CommonUtils.sendJson(resp, new AjaxResult("error","姓名或建议内容不能为空"));
			return;
		}
		if(!CommonUtils.isEmail(email)){
			CommonUtils.sendJson(resp, new AjaxResult("error","邮箱格式不正确"));
			return;
		}
		String content = "用户：" + name + ",联系方式：" + email + ",建议或问题：" + message;
		try {
			MailUtils.sendMail("15869165643@sina.cn", content);
		} catch (MessagingException e) {
			throw new RuntimeException("邮件发送失败");
		}
		CommonUtils.sendJson(resp, new AjaxResult("success","提交成功"));
		

	}

	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/user/register.jsp").forward(request, response);
	}

	public void registerSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String imageCode = request.getParameter("imageCode");
		if (!CommonUtils.isEmail(email)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "邮箱格式非法"));
			return;
		}
		if (CommonUtils.hasEmpty(password, repassword, imageCode)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "密码或确认密码或图片验证码为空"));
			return;
		}
		if (!password.equals(repassword)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "两次输入的密码不一致"));
			return;
		}
		String imageCodeInSession = (String) request.getSession().getAttribute("imageCode");
		if (!imageCode.equalsIgnoreCase(imageCodeInSession)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "图片验证码错误"));
			return;
		}
		User user = userService.selectByEmail(email);
		if (user != null) {
			CommonUtils.sendJson(response, new AjaxResult("error", "该邮箱已经被注册"));
			return;
		}
		String passwordSalt = UUID.randomUUID().toString();
		user = new User();
		user.setPasswordSalt(passwordSalt);
		user.setPassword(CommonUtils.calcMD5(passwordSalt + password));
		user.setEmail(email);
		user.setCreateTime(new Date());
		user.setImagePath("upload/htl.jpg");
		userService.insert(user);
		CommonUtils.sendJson(response, new AjaxResult("success", "注册成功"));

	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
	}

	public void loginSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String imageCode = request.getParameter("imageCode");
		if (!CommonUtils.isEmail(email)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "邮箱格式非法"));
			return;
		}
		if (CommonUtils.hasEmpty(password, imageCode)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "密码或图片验证码为空"));
			return;
		}
		String imageCodeInSession = (String) request.getSession().getAttribute("imageCode");
		request.getSession().removeAttribute("imageCode");
		if (!imageCode.equalsIgnoreCase(imageCodeInSession)) {
			CommonUtils.sendJson(response, new AjaxResult("error", "图片验证码错误"));
			return;
		}
		User user = userService.selectByEmailAndPassword(email, password);
		if (user == null) {
			CommonUtils.sendJson(response, new AjaxResult("error", "邮箱或密码错误"));
			return;
		}

		request.getSession().setAttribute("user", user);
		CommonUtils.sendJson(response, new AjaxResult("success", "登录成功"));

	}

	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SubjectService subjectService = new SubjectService();
		ArticleService articleService = new ArticleService();
		QuestionService questionService = new QuestionService();
		List<Subject> subjects = subjectService.selectAll(Subject.class);
		List<Article> articles = articleService.selectPages(Article.class, 6, 1);
		List<Question> questions = questionService.selectPages(6, 1,null,null);
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			List<Question> questionsAnsweredJust=(List<Question>) session.getAttribute("questionTips");
			if(questionsAnsweredJust==null){
				questionsAnsweredJust=questionService.selectQuestionsAnsweredJustOfTheUser(user);
				if(questionsAnsweredJust.size()>0){
					session.setAttribute("questionTips", questionsAnsweredJust);
				}
			}
			//System.out.println(questionsAnsweredJust.size());
			if(questionsAnsweredJust!=null&&questionsAnsweredJust.size()>0){
				StringBuilder questionTipSB=new StringBuilder();
				questionTipSB.append("\"<div style='width:270px;height:160px;'><ul id='questions'>");
				String path=request.getContextPath();
				for(Question question:questionsAnsweredJust){
					questionTipSB.append("<li id='question_").append(question.getId()).append("' onclick='removeQuestion(this);'><a style='color:blue' target='_blank' href='");
					questionTipSB.append(path).append("/question?action=detail&questionId=").append(question.getId());
					questionTipSB.append("'>").append(question.getTitle()).append(" 收到新回复").append("</a></li>");
				}
				questionTipSB.append("</ul></div>\"");
				request.setAttribute("questionTip", questionTipSB.toString());
			}
		}
		
		request.setAttribute("subjects", subjects);
		request.setAttribute("articles", articles);
		request.setAttribute("questions", questions);
		request.getRequestDispatcher("/WEB-INF/user/index.jsp").forward(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		user.setLogoutDateTime(new Date());
		userService.update(user);
		session.invalidate();
		request.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(request, response);
	}

	public void center(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SubjectService subjectService = new SubjectService();
		List<Subject> subjects = subjectService.selectAll(Subject.class);
		User user = (User) request.getSession().getAttribute("user");
		InterestService interestService = new InterestService();
		List<Interest> interests = interestService.selectByUserId(user.getId());
		request.setAttribute("subjects", subjects);
		request.setAttribute("interests", interests);
		request.getRequestDispatcher("/WEB-INF/user/center.jsp").forward(request, response);
	}

	public void centerSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String genderStr = request.getParameter("gender");
		Part part = request.getPart("pic");
		String[] subjectIdsStr = request.getParameterValues("subjectId");
		if (CommonUtils.hasEmpty(username, genderStr)) {
			request.setAttribute("message", "昵称为空或性别未选择");
			center(request, response);
			return;
		}
		byte[] bytes = username.getBytes("ISO-8859-1");
		username = new String(bytes, "UTF-8");
		User userInSession = (User) request.getSession().getAttribute("user");
		User user = userService.selectByUsername(username);
		if (user != null && user.getId() != userInSession.getId()) {
			request.setAttribute("message", "昵称已存在");
			center(request, response);
			return;
		}
		if (part.getSize() != 0) {
			String rootPath = request.getServletContext().getRealPath("/");
			String imagePath = fileupload(part, rootPath);
			userInSession.setImagePath(imagePath);
		}
		boolean gender = "isMale".equals(genderStr) ? true : false;
		userInSession.setUsername(username);
		userInSession.setIsMale(gender);
		userService.update(userInSession);
		if (subjectIdsStr != null && subjectIdsStr.length > 0) {
			int[] subjectIds = new int[subjectIdsStr.length];
			for (int i = 0; i < subjectIdsStr.length; i++) {
				subjectIds[i] = Integer.parseInt(subjectIdsStr[i]);
			}
			InterestService interestService = new InterestService();
			interestService.updateInterests(userInSession.getId(), subjectIds);
		} else {
			InterestService interestService = new InterestService();
			interestService.updateInterests(userInSession.getId(), new int[0]);
		}
		request.getSession().setAttribute("user", userInSession);
		request.setAttribute("message", "保存成功");
		// CommonUtils.sendJson(response, new AjaxResult("success","保存成功"));
		center(request, response);

	}

	public void contact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/user/contact.jsp").forward(request, response);
		;
	}

	private String fileupload(Part part, String rootPath) {// servlet3.0
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		// 使用上传时的日期生成中间目录
		String path2 = "upload/" + sdf.format(new Date());
		// 生成随机文件名，后缀是上传的文件的后缀
		String filename = UUID.randomUUID().toString()
				+ part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf('.'));
		System.out.println(filename);
		File parentDir = new File(rootPath, path2);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = new FileOutputStream(new File(parentDir, filename));
			inputStream = part.getInputStream();

			// 数据copy
			byte[] buff = new byte[1024 * 128];
			int len = 0;
			while ((len = inputStream.read(buff)) != -1) {
				outputStream.write(buff, 0, len);
			}
			part.delete();
			// 返回保存的路径，注意，不带根目录
			return path2 + "/" + filename;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			// inputStream是part对象提供的，不是自己创建的，可不用自己手动关闭
		}
	}

	public void askQuestion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=(User) request.getSession().getAttribute("user");
		QuestionService questionService=new QuestionService();
		Condition condition=new Condition();
		condition.addCondition("userId=?",user.getId() );
		String header=request.getHeader("X-Requested-With");
		List<Question> questions=null;
		if(header==null||header.length()==0){
			SubjectService subjectService = new SubjectService();
			List<Subject> subjects = subjectService.selectAll(Subject.class);
			int pageNum=1;
			questions=questionService.selectPages(5, pageNum, "createTime desc",condition);
			long totalCount=(long) questionService.selectQuestionsCountOfTheUser(user.getId());
			long totalPages=totalCount%5==0?totalCount/5:totalCount/5+1;
			request.setAttribute("subjects", subjects);
			request.setAttribute("questions", questions);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("pageNum", pageNum);
			request.getRequestDispatcher("/WEB-INF/question/askQuestion.jsp").forward(request, response);
		}else{
			int pageNum=Integer.parseInt(request.getParameter("pageNum"));
			questions=questionService.selectPages(5, pageNum, "createTime desc", condition);
			CommonUtils.sendJson(response, new AjaxResult("success",questions));
		}
		
		
	}
	
	public void updateLogoutDateTime(HttpServletRequest request, HttpServletResponse response) throws IOException{
		User user=(User) request.getSession().getAttribute("user");
		user.setLogoutDateTime(new Date());
		request.getSession().removeAttribute("questionTips");
		CommonUtils.sendJson(response, new AjaxResult("ok","ok"));
	}
	
	public void removeQuestionTip(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		int id=Integer.parseInt(request.getParameter("id"));
		List<Question> questionTips=(List<Question>) session.getAttribute("questionTips");
		if(questionTips!=null){
			for(Question questionTip:questionTips){
				if(questionTip.getId()==id){
					questionTips.remove(questionTip);
					break;
				}
			}
		}
		if(questionTips.size()==0){
			session.removeAttribute("questionTips");
			User user=(User) session.getAttribute("user");
			user.setLogoutDateTime(new Date());
		}
		CommonUtils.sendJson(response, new AjaxResult("success","success"));
	}

}
