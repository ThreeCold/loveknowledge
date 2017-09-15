<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>问题列表</title>
<%@include file="/WEB-INF/header.jsp" %>
</head>
<body>
<%@include file="/WEB-INF/nav.jsp" %>
 <!-- Start of Page Container -->
                <div class="page-container">
                        <div class="container">
                                <div class="row">

                                        <!-- start of page content -->
                                        <div id="container" class="span8 main-listing">
                                            <c:forEach items="${questions }" var="question">
                                                <article class="format-standard type-post hentry clearfix">

                                                        <header class="clearfix">

                                                                <h3 class="post-title">
                                                                        <a href="<%=path%>/question?action=detail&questionId=${question.id}">${question.title }</a>
                                                                </h3>

                                                                <div class="post-meta clearfix">
                                                                        <span class="date"><fmt:formatDate value="${question.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                                                        <span class="category">${question.subjectName }</span>
                                                                        <span class="category"><a href="javascript:void(0)" >${question.tags }</a></span>
                                                                        <span class="coin-count">${question.reward }</span>
                                                                </div><!-- end of post meta -->

                                                        </header>
                                                       
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
                            	var curr=obj.curr;
                            	$.post('<%=path%>/question',{action:'list',pageNum:curr},function(result){
                            		if(result.status=="success"){
                            			$("#container").find("article").remove();
                            			var div=$("#container");
                            			var data=result.data;
                            			for(var i=0;i<data.length;i++){
                            				var article=' <article class="format-standard type-post hentry clearfix"><header class="clearfix">'+
                            				'<h3 class="post-title"><a href="<%=path%>/question?action=detail&questionId='+data[i].id+'">'+data[i].title+
                            				'</a></h3><div class="post-meta clearfix"> <span class="date">'+data[i].createTime+
                            				'</span><span class="category">'+data[i].subjectName+'</span><span class="category"><a href="javascript:void(0)" >'+data[i].tags+
                            				'</a></span><span class="coin-count">'+data[i].reward+'</span></div></header></article>';
                                            div.prepend(article);
                            			}
                            			
                            		}
                            	});
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
                                                        <h3 class="title">优质文章</h3>
                                                        
                                                        <ul class="articles">
                                                               <c:forEach items="${articles }" var="article">
                                                                <li class="article-entry standard">
                                                                        <h4><a href="<%=path %>/article?action=detail&id=${article.id}">${article.title }</a></h4>
                                                                        <span class="article-meta"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/><a href="#" >Server &amp; Database</a></span>
                                                                        <span class="like-count">${article.praiseCount}</span>
                                                                </li>
                                                              </c:forEach>   
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