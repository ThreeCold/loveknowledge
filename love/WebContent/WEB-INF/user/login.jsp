<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%String path=request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <link rel="shortcut icon" href="<%=path %>/images/favicon.png" />
    <link rel="stylesheet"  href="<%=path %>/lib/bootstrap/css/bootstrap.min.css"/>
    <script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
    <script type="text/javascript"  src="<%=path %>/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/lib/validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/validation/additional-methods.min.js"></script>
	<script type="text/javascript" src="<%=path %>/lib/validation/localization/messages_zh.min.js"></script>
    <script type="text/javascript" src="<%=path %>/lib/layer/1.9.3/layer.js"></script>
    <style type="text/css">  
            body{background: url('<%=path%>/images/bg.jpg') no-repeat;background-size:cover;font-size: 16px;}  
            .form{background: rgba(255,255,255,0.2);width:400px;margin:100px auto;}  
            #login_form{display: block;}  
            .fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}  
            input[type="text"],input[type="password"]{padding-left:26px;}  
            .checkbox{padding-left:21px;}  
    </style>  
    
    <script type="text/javascript">
        $(function(){
        	$("#image").click(function(){
        		$(this).attr('src','<%=path%>/index?action=imageCode&random='+Math.random());
        	});
        	$("form").validate({
      		   rules:{
      			   'email':{
      				   required:true,
      				   email:true
      			   },
      			   'password':{
      				   required:true
      			   },
      			   'imageCode':{
      				   required:true
      			   }
      		   },
      		   messages:{
      			   'email':{
      				   required:"邮箱不能为空",
      				   email:"请输入正确的邮箱格式"
      			   },
      			   'password':{
      				   required:"密码不能为空"
      			   },
      			   'imageCode':{
      				   required:"图片验证码不能为空"
      			   }
      		   },
      		   submitHandler:function(form){
      			   $.post(form.action,$(form).serialize(),function(result){
      				   if(result.status=="success"){
      					   location.href="<%=path%>/user?action=index";
      				   }else{
      					  $("#image").click();
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
	<div class="container">  
        <div class="form row">  
            <form class="form-horizontal col-sm-offset-3 col-md-offset-3" id="login_form" action="<%=path%>/user?action=loginSubmit">  
                <h3 class="form-title">Login to your account</h3>  
                <div class="col-sm-9 col-md-9">  
                    <div class="form-group">  
                        <i class="fa fa-user fa-lg"></i>  
                        <input class="form-control required" type="text" placeholder="Email" name="email" autofocus="autofocus" maxlength="20"/>  
                    </div>  
                    <div class="form-group">  
                            <input class="form-control required" type="password" placeholder="Password" name="password" maxlength="8" />  
                    </div>
                    <div class="form-group">  
                            <input class="form-control required" type="text" placeholder="Imagecode" name="imageCode" maxlength="4" style="width:110px;display:inline"/>
                            <img id="image" src="<%=path%>/index?action=imageCode" class="img-rounded"/>  
                    </div>  
                    <div class="form-group">  
                        <label class="checkbox">  
                            <input type="checkbox" name="remember" value="1"/> Remember me
                        </label> 
                        <hr />  
                        <a href="<%=path %>/user?action=register" id="register_btn" class="">Create an account</a>  
                    </div>  
                    <div class="form-group">  
                        <input type="submit" class="btn btn-success pull-right" value="Login "/>     
                    </div>  
                </div>  
            </form>  
        </div>  
        </div>  
</body>
</html>