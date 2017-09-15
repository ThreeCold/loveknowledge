<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="UTF-8">

  <title>走迷宫</title>
<%@include file="/WEB-INF/header.jsp" %>
 <style>
    #maze{
    	margin:10px auto;
    	width:350px;
    }
 </style>
</head>

<body>
   <%@include file="/WEB-INF/nav.jsp" %>
   <div id="maze">
  <div class="stat">步数: <span id="step">0</span> 关卡: <span id="complete">0</span></div>
<canvas id="canvas" style="background:#eeeeee;" ></canvas>
</div>


  <script src="<%=path %>/js/maze.js"></script>
  <%@include file="/WEB-INF/footer.jsp" %>
</body>

</html>