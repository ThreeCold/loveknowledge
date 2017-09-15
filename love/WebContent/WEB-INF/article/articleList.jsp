<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章列表</title>
<%@include file="/WEB-INF/header.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/nav.jsp" %>
 <!-- Start of Page Container -->
                <div class="page-container">
                        <div class="container">
                                <div class="row">

                                        <!-- start of page content -->
                                        <div class="span8 main-listing">
                                            <c:forEach items="${articles }" var="article">
                                                <article class="format-standard type-post hentry clearfix">

                                                        <header class="clearfix">

                                                                <h3 class="post-title">
                                                                        <a href="<%=path%>/article?action=detail&id=${article.id}">${article.title }</a>
                                                                </h3>

                                                                <div class="post-meta clearfix">
                                                                        <span class="date"><fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd"/></span>
                                                                        <span class="category"><a href="#" title="View all posts in Server &amp; Database">Server &amp; Database</a></span>
                                                                        <span class="comments"><a href="#" title="Comment on Integrating WordPress with Your Website">3 Comments</a></span>
                                                                        <span class="like-count">${article.praiseCount }</span>
                                                                </div><!-- end of post meta -->

                                                        </header>

                                                        ${article.description } <a style="color:red" href="<%=path%>/article?action=detail&id=${article.id}">Read more</a>

                                                </article>

                                               </c:forEach>
                                       <c:if test="${totalPages>1 }">
            <div id="pagination" style="margin: 20px;"></div>
            <script type="text/javascript" src="<%=path %>/lib/laypage/1.2/laypage.js"></script>
            <script type="text/javascript">
                laypage({
                    cont:'pagination',
                    pages:${totalPages},
                    curr: ${pageNum},
                    jump:function(obj,first){
                        if(!first){
                        	
                        }
                    }
                });
            </script>
          </c:if> 
                                                

                                                

                                                

                                               

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
                                                        <h3 class="title">Featured Articles</h3>
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



                                                <section class="widget"><h3 class="title">学科</h3>
                                                        <ul>
                                                           <c:forEach items="${subjects }" var="subject">
                                                                <li style="display:inline"><a href="#" title="Lorem ipsum dolor sit amet,">${subject.name }</a> </li>
                                                            </c:forEach>   
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