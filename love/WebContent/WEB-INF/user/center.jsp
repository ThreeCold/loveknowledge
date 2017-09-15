<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<%@include file="/WEB-INF/header.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js"></script>
<script type="text/javascript">
   $(function(){
	   $("#center-form").submit(function(){
		   var username=$("#username").val();
		   if(username==''){
			  alert("昵称不能为空");
			  return false;
		   }
		   var length=$("input:radio:checked").length;
		   if(length==0){
			   alert("性别必填");
			   return false;
		   }
		  
		   
	   });
   });
</script>
</head>
<body>
	<%@include file="/WEB-INF/nav.jsp"%>



	<!-- Start of Page Container -->
	<div class="page-container">
		<div class="container">
			<div class="row">

				<!-- start of page content -->
				<div class="span8 page-content">

					<article class="type-page hentry clearfix">
					<h1 class="post-title">
						<a href="#">个人信息</a>
					</h1>
					<hr>
					</article>


					<form id="center-form" class="row"
						action="<%=path %>/user?action=centerSubmit"
						method="post" enctype="multipart/form-data">
						<div class="span2">
							<label for="username">头像 </label>
						</div>
						<div class="span6">
							    <img src="<%=path %>/${sessionScope.user.imagePath}" class="avatar avatar-60 photo" height="60" width="60">
								<input name="pic" type="file" style="margin-top:25px;margin-left:25px;"/>
						</div>
						<div class="span2">
							<label for="username">昵称: <span style="color: red">*</span>
							</label>
						</div>
						<div class="span6">
							<input type="text" name="username" id="username"
								class="required input-xlarge"
								value="${sessionScope.user.username }">
						</div>

						<div class="span2">
							<label>性别：<span style="color: red">*</span></label>
						</div>
						<div class="span6">
							<input type="radio" name="gender" id="isMale" value="isMale"
								<c:if test="${sessionScope.user.isMale==true}">
                                                                   checked="checked"
                                                                   </c:if>><label
								for="isMale"  >男</label> <input type="radio" name="gender"
								id="isFemale" value="isFemale" 
								<c:if test="${sessionScope.user.isMale==false}">
                                                                   checked="checked"
                                                                   </c:if>><label
								for="isFemale" >女</label>
						</div>

						<div class="span2">
							<label>兴趣学科:</label>
						</div>
						<div class="span6">
							<c:forEach items="${subjects }" var="subject">
								<input type="checkbox" name="subjectId" value="${subject.id}"
									<c:forEach items="${interests }" var="interest">
									  <c:if test="${subject.id==interest.subjectId }">
									     checked="checked"
									  </c:if>
									</c:forEach>
									id="${subject.id}">
								<label for="${subject.id }" style="display: inline;">${subject.name}</label>
							</c:forEach>
						</div>



						<div class="span6 offset2 bm30">
							<input type="submit"  value="保存"
								class="btn btn-inverse"> <img src="images/loading.gif"
								id="contact-loader" alt="Loading...">
								<span style="color:red">${requestScope.message }</span>
						</div>

						<div class="span6 offset2 error-container"></div>
						<div class="span8 offset2" id="message-sent"></div>

					</form>
				</div>
				<!-- end of page content -->

				<!-- start of sidebar -->
				<aside class="span4 page-sidebar"> <section class="widget">
				<div class="support-widget">
					<h3 class="title">支持</h3>
					<p class="intro">有任何需要都可以和我们联系</p>
				</div>
				</section> <section class="widget">
				<h3 class="title">最新文章</h3>
				<ul class="articles">
					<li class="article-entry standard">
						<h4>
							<a href="single.html">Integrating WordPress with Your Website</a>
						</h4> <span class="article-meta">25 Feb, 2013 in <a href="#"
							title="View all posts in Server &amp; Database">Server &amp;
								Database</a></span> <span class="like-count">66</span>
					</li>
					<li class="article-entry standard">
						<h4>
							<a href="single.html">WordPress Site Maintenance</a>
						</h4> <span class="article-meta">24 Feb, 2013 in <a href="#"
							title="View all posts in Website Dev">Website Dev</a></span> <span
						class="like-count">15</span>
					</li>
					<li class="article-entry video">
						<h4>
							<a href="single.html">Meta Tags in WordPress</a>
						</h4> <span class="article-meta">23 Feb, 2013 in <a href="#"
							title="View all posts in Website Dev">Website Dev</a></span> <span
						class="like-count">8</span>
					</li>
					<li class="article-entry image">
						<h4>
							<a href="single.html">WordPress in Your Language</a>
						</h4> <span class="article-meta">22 Feb, 2013 in <a href="#"
							title="View all posts in Advanced Techniques">Advanced
								Techniques</a></span> <span class="like-count">6</span>
					</li>
				</ul>
				</section> </aside>
				<!-- end of sidebar -->
			</div>
		</div>
	</div>
	<!-- End of Page Container -->
	<%@include file="/WEB-INF/footer.jsp"%>
</body>
</html>