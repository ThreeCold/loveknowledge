<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我要提问</title>
<%@include file="/WEB-INF/header.jsp"%>
<script type="text/javascript" src="<%=path%>/js/common.js?version=1.7"></script>
<script type="text/javascript"
	src="<%=path%>/lib/ueditor/ueditor.config.js"></script>
<script type="text/javascript"
	src="<%=path%>/lib/ueditor/ueditor.all.min.js?version=1.2"></script>


</head>
<body>
	<%@include file="/WEB-INF/nav.jsp"%>
	<!-- Start of Page Container -->
	<div class="page-container">
		<div class="container">
			<div class="row">

				<!-- start of page content -->
				<div class="span8 page-content">

					<div id="respond">

						<h3>我要提问</h3>

						<form action="<%=path%>/question"  onsubmit="ajaxSubmitForm(this);">
							<span style="color:red;">*</span><span>问题所属学科：</span><select name="subjectId" style="margin-top:10px;margin-right:15px;">
								<c:forEach items="${subjects }" var="subject">
									<option value="${subject.id }">${ subject.name}</option>
								</c:forEach>
							</select>
							<br/>
							<span style="color:red;">*</span><span>问题标题：</span></span><input name="title" type="text" placeholder="用一句话描述你的问题" style="width:450px;"/> 
							<input type="hidden" name="action" value="submitQuestion" />
							<script id="description" name="description" type="text/plain"></script>
							<span>问题标签：</span><input name="tags" type="text" placeholder="标签之间以空格分隔" style="margin-top:10px;"/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span>悬赏金额：</span><input id="reward" readonly="readonly" name="reward" value="1" style="width:50px;text-align: center"/>
							<input id="add-btn" class="btn" type="button" value="+"/>
							<input id="reduce-btn" class="btn" type="button" value="-" style="display:none;"/>
							
							<script type="text/javascript">
							<!-- 实例化编辑器 -->
								var ue1 = UE.getEditor('description', {
									"elementPathEnabled" : false,
									"wordCount" : false,
									"initialFrameHeight" : 200,
									"toolbars" : [ [ 'simpleupload', 'emotion',
											'attachment' ] ]
								});
							</script>
							<div>
								<input class="btn" type="submit" id="submit" value="提交"  style="margin-top:15px;">
							</div>

						</form>

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
						<h3 class="title">往期提问</h3>
						<ul id="questions" class="articles">
							<c:forEach items="${questions }" var="question">
								<li class="article-entry standard">
									<h4>
										<a
											href="<%=path%>/question?action=detail&questionId=${question.id}">${question.title }</a>
									</h4> <span class="article-meta"><fmt:formatDate value="${question.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
										&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" >${question.subjectName }</a>
								</span> <span class="coin-count">${question.reward }</span>
								</li>
							</c:forEach>
						</ul>
						
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
                        	$.post('<%=path%>/user',{action:'askQuestion',pageNum:curr},function(result){
                        		if(result.status=="success"){
                        			$("#questions").children().remove();
                        			var ul=$("#questions");
                        			var data=result.data;
                        			for(var i=0;i<data.length;i++){
                        				var li='<li class="article-entry standard"><h4><a href="<%=path%>/question?action=detail&questionId='+data[i].id+'">'+data[i].title+
                        						'</a></h4> <span class="article-meta">'+data[i].createTime+'&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" >'+data[i].subjectName+
                        						'</a></span> <span class="coin-count">'+data[i].reward+'</span></li>';
                        						
										ul.append(li);
                        			}
                        			
                        		}
                        	});
                        	
                        }
                    }
                });
            </script>
          </c:if> 
					</section>

				</aside>
				<!-- end of sidebar -->
			</div>
		</div>
	</div>
	<!-- End of Page Container -->
    <script type="text/javascript">
        $("#add-btn").click(function(){
        	var valueStr=$("#reward").val();
        	var valueInt=parseInt(valueStr);
        	if(valueInt==1){
        		$("#reduce-btn").attr("style","");
        	}
        	$("#reward").val(valueInt+1);
        });
        $("#reduce-btn").click(function(){
        	var valueStr=$("#reward").val();
        	var valueInt=parseInt(valueStr);
        	$("#reward").val(valueInt-1);
        	if(valueInt==2){
        		$("#reduce-btn").attr("style","display:none");
        	}
        });
    </script>

	<%@include file="/WEB-INF/footer.jsp"%>

</body>
</html>