<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章详情</title>
<%@include file="/WEB-INF/header.jsp" %>
<script type="text/javascript" src="<%=path %>/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="<%=path %>/js/common.js?version=1.9"></script>
<script type="text/javascript">
	function openOrCloseResp(aDom){
		var id=$(aDom).attr('id');
		var articleId=${article.id};
		var ul=$("#li-comment-"+id).children("ul");
		if(ul.length>0){
			ul.remove();
		}else{
			ajaxReloadChildrenComments(aDom,articleId);
		}
	}
	
	function clickReply(aDom){
		var id=$(aDom).attr("id");
		var name=$(aDom).attr("name");
		var strs=new Array();
		strs=name.split("_");
		var username=strs[0];
		var textareaid=strs[1];
		$("#textarea_"+textareaid+"[name=comment]").attr('placeholder','回复：'+username);
		$("#parentId_"+textareaid).attr('value',id);
		
	}
     $(function(){
    	 $("#praiseBtn").click(function(){
     		var articleId=${article.id};
     		ajaxSubmit('<%=path%>/article',{action:'addPraiseCount',articleId:articleId});
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
                                                <article class=" type-post format-standard hentry clearfix">

                                                        <h1 class="post-title"><a href="#">${article.title}</a></h1>

                                                        <div class="post-meta clearfix">
                                                                <span class="date">${article.createTime }</span>
                                                                <span class="category">Server &amp; Database</span>
                                                                <span class="comments">${totalCount} Comments</span>
                                                                <span class="like-count">${article.praiseCount }</span>
                                                        </div><!-- end of post meta -->

                                                       ${article.content }

                                                </article>

                                                <div class="like-btn">
                                                        <span id="praiseBtn" class="like-it ">${article.praiseCount }</span>
                                                </div>

                                                <section id="comments">

                                                        <h3 id="comments-title">(${totalCount}) 评论</h3>

                                                        <ol id="commentList" class="commentlist">
                                                              <c:forEach items="${comments }" var="comment">
                                                                <li class="comment even thread-even depth-1" id="li-comment-${comment.id }">
                                                                        <article id="comment-2">
                                                                                <a href="#">
                                                                                        <img  src="<%=path %>/${comment.imagePath}" class="avatar avatar-60 photo" height="60" width="60">
                                                                                </a>

                                                                                <div class="comment-meta">

                                                                                        <h5 class="author">
                                                                                                <cite class="fn">
                                                                                                        <a href="#" rel="external nofollow" class="url">${comment.username }</a>
                                                                                                </cite>
                                                                                                
                                                                                        </h5>

                                                                                        <p class="date">
                                                                                                   <time><fmt:formatDate value="${comment.createDateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></time>
                                                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                <a    style="color:purple;" href="javascript:void(0)">赞(5)</a>
                                                                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                <a id="${comment.id }" onclick="openOrCloseResp(this);" name="loadChildren" style="color:red;" href="javascript:void(0)">展开回复</a>
                                                                                        </p>

                                                                                </div><!-- end .comment-meta -->

                                                                                <div class="comment-body">
                                                                                        ${comment.content }
                                                                                </div><!-- end of comment-body -->

                                                                        </article><!-- end of comment -->

                                                                           </li>
                                                             </c:forEach>
                                                               
                                                        </ol>
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
                        	var articleId=${article.id};
                            ajaxReloadCommentPages(curr,articleId);
                            
                        }
                    }
                });
            </script>
          </c:if> 
                                                        <div id="respond">

                                                                <h3>我要评论</h3>

                                                                <form action="<%=path %>/comment" method="post" onsubmit="ajaxSubmitForm(this);">
                                                                       <input type="hidden" name="action" value="submitComment"/>
                                                                       <input  type="hidden" name="articleId" value="${article.id }"/>
                                                                        <div>
                                                                                <label for="comment">评论</label>
                                                                                <textarea class="span8 input_key" name="comment" id="comment" cols="25" rows="5"></textarea>
                                                                        </div>

                                                                        <div>
                                                                                <input class="btn"  type="submit" id="submit"  value="提交">
                                                                        </div>

                                                               </form>

                                                        </div>

                                                </section><!-- end of comments -->

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
                                                        <h3 class="title">其他文章</h3>
                                                        <ul class="articles">
                                                           <c:forEach items="${articles }" var="otherArticle">
                                                                <li class="article-entry standard">
                                                                        <h4><a href="<%=path%>/article?action=detail&id=${otherArticle.id}">${otherArticle.title }</a></h4>
                                                                        <span class="article-meta">${otherArticle.createTime } <a href="#" title="View all posts in Server &amp; Database">Server &amp; Database</a></span>
                                                                        <span class="like-count">${otherArticle.praiseCount }</span>
                                                                </li>
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