<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联系我们</title>
<%@include file="/WEB-INF/header.jsp" %>
<script type="text/javascript">
$(function(){
	
	$("form").validate({
		   rules:{
			   'email':{
				   required:true,
				   email:true
			   },
			   'name':{
				   required:true
			   },
			   'message':{
				   required:true
			   }
		   },
		   messages:{
			   'email':{
				   required:"邮箱不能为空",
				   email:"请输入正确的邮箱格式"
			   },
			   'name':{
				   required:"名字不能为空"
			   },
			   'message':{
				   required:"建议或问题不能为空"
			   }
		   },
		   submitHandler:function(form){
			   $.post(form.action,$(form).serialize(),function(result){
				   if(result.status=="success"){
					   location.href="<%=path%>/user?action=submitContactSuccess";
				   }else{
					  layer.msg(result.data, {
						  time: 2500, //2s后自动关闭
						  btn: ['关闭'],
						  area: ['400px', '200px']
					  });
				   } 
			   },"json");
		   }
	   });
});
</script>
</head>
<body>
<%@include file="/WEB-INF/nav.jsp" %>
 <!-- Start of Page Container -->
                <div class="page-container">
                        <div class="container">
                                <div class="row">

                                        <!-- start of page content -->
                                        <div class="span8 page-content">

                                                <article class="type-page hentry clearfix">
                                                        <h1 class="post-title">
                                                                <a href="javascript:void(0)">联系我们</a>
                                                        </h1>
                                                        <hr>
                                                        <p>请输入你的联系方式以及你的问题</p>
                                                </article>


                                                <form  class="row" action="<%=path%>/user?action=submitContact" method="post">

                                                        <div class="span2">
                                                                <label for="name" ><strong>你的名字 </strong><span style="color:red">*</span> </label>
                                                        </div>
                                                        <div class="span6">
                                                                <input type="text" name="name" id="name" class="required input-xlarge" value="" title="* 请输入你的名字">
                                                        </div>

                                                        <div class="span2">
                                                                <label for="email"><strong>你的邮箱 </strong><span style="color:red">*</span></label>
                                                        </div>
                                                        <div class="span6">
                                                                <input type="text" name="email" id="email" class="email required input-xlarge" value="" title="* 请输入正确的邮箱">
                                                        </div>

                                                         <div class="span2">
                                                                <label for="message"><strong>你的问题或建议</strong><span style="color:red">*</span> </label>
                                                        </div>
                                                        <div class="span6">
                                                                <textarea name="message" id="message" class="required span6" rows="6" title="* 请输入你的问题或建议"></textarea>
                                                        </div>

                                                        
                                                        <div class="span6 offset2 bm30">
                                                                <input type="submit" value="提交" class="btn btn-inverse">
                                                        </div>

                                                       
                                                        

                                                </form>
                                        </div>
                                        <!-- end of page content -->


                                        <!-- start of sidebar -->
                                        <aside class="span4 page-sidebar">

                                                <section class="widget">
                                                        <div class="support-widget">
                                                                <h3 class="title">支持</h3>
                                                                <p class="intro">有任何需要都可以联系我们</p>
                                                        </div>
                                                </section>

                                                <section class="widget">
                                                        <h3 class="title">Latest Articles</h3>
                                                        <ul class="articles">
                                                                <li class="article-entry standard">
                                                                        <h4><a href="single.html">Integrating WordPress with Your Website</a></h4>
                                                                        <span class="article-meta">25 Feb, 2013 in <a href="#" title="View all posts in Server &amp; Database">Server &amp; Database</a></span>
                                                                        <span class="like-count">66</span>
                                                                </li>
                                                                <li class="article-entry standard">
                                                                        <h4><a href="single.html">WordPress Site Maintenance</a></h4>
                                                                        <span class="article-meta">24 Feb, 2013 in <a href="#" title="View all posts in Website Dev">Website Dev</a></span>
                                                                        <span class="like-count">15</span>
                                                                </li>
                                                                <li class="article-entry video">
                                                                        <h4><a href="single.html">Meta Tags in WordPress</a></h4>
                                                                        <span class="article-meta">23 Feb, 2013 in <a href="#" title="View all posts in Website Dev">Website Dev</a></span>
                                                                        <span class="like-count">8</span>
                                                                </li>
                                                                <li class="article-entry image">
                                                                        <h4><a href="single.html">WordPress in Your Language</a></h4>
                                                                        <span class="article-meta">22 Feb, 2013 in <a href="#" title="View all posts in Advanced Techniques">Advanced Techniques</a></span>
                                                                        <span class="like-count">6</span>
                                                                </li>
                                                        </ul>
                                                </section>


                                        </aside>
                                        <!-- end of sidebar -->
                                </div>
                        </div>
                </div>
                <!-- End of Page Container -->
                <%@include file="/WEB-INF/footer.jsp" %>
</body>
</html>