<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页</title>
	<%@include file="/WEB-INF/header.jsp" %>
</head>
<body>
      <%@include file="/WEB-INF/nav.jsp" %>

       <!-- Start of Search Wrapper -->
       <div class="search-area-wrapper">
               <div class="search-area container">
                       <h3 class="search-header">Have a Question?</h3>
                       <p class="search-tag-line">If you have any question you can ask below or enter what you are looking for!</p>

                       <form id="search-form" class="search-form clearfix" method="get" action="#" autocomplete="off">
                               <input class="search-term required" type="text" id="s" name="s" placeholder="请输入你的关键词"  />
                               <input class="search-btn" type="submit" value="搜索" />
                               <div id="search-error-container"></div>
                       </form>
               </div>
       </div>

       <!-- Start of Page Container -->
       <div class="page-container">
               <div class="container">
                       <div class="row">

                               <!-- start of page content -->
                               <div class="span8 page-content">

                                       <!-- Basic Home Page Template -->
                                       <div class="row separator">
                                               <section class="span4 articles-list">
                                                       <h3>热门问题</h3>
                                                       <ul class="articles">
                                                          <c:forEach items="${questions }" var="question">
                                                               <li class="article-entry standard">
                                                                       <h4><a href="<%=path%>/question?action=detail&questionId=${question.id}">${question.title }</a></h4>
                                                                       <span class="article-meta"><fmt:formatDate value="${question.createTime }" pattern="yyyy-MM-dd"/>&nbsp;&nbsp; 
                                                                       <a href="javascript:void(0);" >${question.subjectName }</a>
                                                                       </span>
                                                                       <span class="coin-count">${question.reward }</span>
                                                               </li>
                                                          </c:forEach>
                                                        </ul>
                                               </section>


                                               <section class="span4 articles-list">
                                                       <h3>优质文章</h3>
                                                       <ul class="articles">
                                                          <c:forEach items="${articles }" var="article">
                                                               <li class="article-entry standard">
                                                                       <h4><a href="<%=path%>/article?action=detail&id=${article.id}">${article.title }</a></h4>
                                                                       <span class="article-meta"> <fmt:formatDate value="${article.createTime }" pattern="yyyy-MM-dd"/>  &nbsp;&nbsp;
                                                                       <a href="javascript:void(0);" >计算机 </a>
                                                                              
                                                                      </span>
                                                                       <span class="like-count">${article.praiseCount }</span>
                                                               </li>
                                                          </c:forEach>   
                                                       </ul>
                                               </section>
                                       </div>
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
                                               <div class="quick-links-widget">
                                                       <h3 class="title">Quick Links</h3>
                                                       <ul id="menu-quick-links" class="menu clearfix">
                                                               <li><a href="index-2.html">Home</a></li>
                                                               <li><a href="articles-list.html">Articles List</a></li>
                                                               <li><a href="faq.html">FAQs</a></li>
                                                               <li><a href="contact.html">Contact</a></li>
                                                       </ul>
                                               </div>
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
       <c:if test="${not empty questionTip }">
       <script type="text/javascript" src="<%=path%>/lib/layer/1.9.3/layer.js"></script>
       <link  rel="stylesheet" href="<%=path%>/lib/layer/1.9.3/skin/layer.css"/>
       <script type="text/javascript">
          function removeQuestion(liDom){
        	  var id=$(liDom).attr("id").split("_");
        	  $.post('<%=path%>/user',{action:'removeQuestionTip',id:id[1]},function(result){
        		  if(result.status=='success'){
        			  $(liDom).remove();
        		  }
        	  });
        	  
          }
	      layer.open({
	    	  offset:'rb',
	    	  title:'消息',
	    	  closeBtn:false,
	    	  id:'questionTip',
	    	  btn:'我知道了',
	    	  yes:function(index,layero){
	    		  $.post('<%=path%>/user',{action:'updateLogoutDateTime'},function(result){
	    			  if(result.status=='ok'){
	    				  layer.close(index);
	    			  }
	    		  });
	    		  
	    	  },
	    	  area:['300px','300px'],
	    	  content:${questionTip},
	    	  shade:0,
	      });
	      
	</script>
    </c:if>
       
</body>
</html>