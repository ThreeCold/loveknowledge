<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Start of Header -->
       <div class="header-wrapper">
               <header>
                       <div class="container">
                               <div class="logo-container">
                                       <!-- Website Logo -->
                                       <a href="index-2.html"  title="知识即性感">
                                               <img src="${pageContext.request.contextPath }/images/logo.png" >
                                       </a>
                                       <span class="tag-line">大学生知识问答平台</span>
                               </div>


                               <!-- Start of Main Navigation -->
                               <nav class="main-nav">
                                       <div class="menu-top-menu-container">
                                               <ul id="menu-top-menu" class="clearfix">
                                                       <li class="current-menu-item"><a href="${pageContext.request.contextPath }/user?action=index">首页</a></li>
                                                       <li><a href="${pageContext.request.contextPath }/question?action=list">问题</a></li>
                                                       <li><a href="${pageContext.request.contextPath }/article?action=list">文章</a></li>
                                                       <c:if test="${not empty sessionScope.user}">
                                                           <li><a href="javascript:void(0);">个人中心</a>
                                                               <ul class="sub-menu">
                                                                       <li><a href="${pageContext.request.contextPath }/user?action=center">个人信息</a></li>
                                                                       <li><a href="${pageContext.request.contextPath }/1.html">我的收藏</a></li>
                                                                       <li><a href="${pageContext.request.contextPath }/1.html">我参与的</a></li>
                                                                       <li><a href="${pageContext.request.contextPath }/user?action=askQuestion">我要提问</a></li>
                                                                       <li><a href="${pageContext.request.contextPath }/user?action=logout">退出</a></li>
                                                               </ul>
                                                           </li>
                                                       </c:if>
                                                       <c:if test="${ empty sessionScope.user}">
                                                           <li><a href="${pageContext.request.contextPath }/user?action=login">登录|注册</a></li>
                                                       </c:if>
                                                       <li><a href="javascript:void(0)">轻松一刻</a>
                                                               <ul class="sub-menu">
                                                                       <li><a href="${pageContext.request.contextPath}/game/maze.jsp">走迷宫</a></li>
                                                                       <li><a href="${pageContext.request.contextPath}/game/tetris.jsp">俄罗斯方块</a></li>
                                                                       <li><a href="${pageContext.request.contextPath}/game/jigsaw.jsp">拼图游戏</a></li>
                                                               </ul>
                                                       </li>
                                                       <li><a href="${pageContext.request.contextPath }/user?action=contact">联系我们</a></li>
                                               </ul>
                                       </div>
                               </nav>
                               <!-- End of Main Navigation -->

                       </div>
               </header>
       </div>
       <!-- End of Header -->