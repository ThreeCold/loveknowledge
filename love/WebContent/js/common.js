function sendEmailCode(url, email, btnDom) {
    if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)) {
        alert('邮箱格式不正确');
        return;
    }
    btnDom.disabled = true;
    $.post(url, {
        'email' : email
    }, function(result) {
        if (result.status == 'error') {
            alert(result.data);
            btnDom.disabled = false;
        } else {
            var remainTime = 60;
            btnDom.value = remainTime + '秒后可重试';
            var intervalId = setInterval(function() {
                remainTime--;
                if (remainTime <= 0) {
                    clearInterval(intervalId);
                    btnDom.disabled = false;
                    btnDom.value = '获取邮箱验证码';
                } else {
                    btnDom.value = remainTime + '秒后可重试';
                }
            }, 1000);
        }
    }, 'json');
}
// 发送短信验证码
function sendSMSCode(url, phone, btnDom) {
    if (!/^1(\d{10})$/.test(phone)) {
        alert('手机号格式不正确');
        return;
    }
    btnDom.disabled = true;
    $.post(url, {
        'phone' : phone
    }, function(result) {
        if (result.status == 'error') {
            alert(result.data);
            btnDom.disabled = false;
        } else {
            var remainTime = 60;
            btnDom.value = remainTime + '秒后可重试';
            var intervalId = setInterval(function() {
                remainTime--;
                if (remainTime <= 0) {
                    clearInterval(intervalId);
                    btnDom.disabled = false;
                    btnDom.value = '获取短信验证码';
                } else {
                    btnDom.value = remainTime + '秒后可重试';
                }
            }, 1000);
        }
    }, 'json');
}

function ajaxSubmitForm(formDom){
	   var action=$(formDom).attr("action");
	   $.post(action,$(formDom).serialize(),function(result){
		   if(result.status=="success"){
			   alert(result.data);
			   location.reload();
		   }else{
			   layerMsg(result.data);
		   }
	   },"json");
	   event.preventDefault();
}

function layerMsg(message){
	layer.msg(message, {
		  time: 2500, //2s后自动关闭
		  btn: ['关闭'],
		  area: ['400px', '200px']
	  });
}
/**
 * 使用ajax发送一个请求
 */
function ajaxSubmit(url, params) {
    $.post(url, params, function(ajaxResult) {
        alert(ajaxResult.data);
        if (ajaxResult.status == 'success') {
            location.reload();
        }
    }, 'json');
}


// 自动调节mainDiv的最小高度，使得footer区域总是处在最下方，而且尽可能不出现滚动条
$(function() {
    $("#mainDiv").css("min-height", window.innerHeight - $("#mainDiv").prop("offsetTop") - $("#footerDiv").prop("offsetHeight") + "px");
});

function ajaxReloadCommentPages(curr,articleId){
    $.post('/love/comment',{action:'loadPages',articleId:articleId,pageNum:curr},function(result){
	   if(result.status=="success"){
		   $("#commentList").children("li").remove();
		   var data=result.data;
		   for(var i=0;i<data.length;i++){
			   var element='<li class="comment even thread-even depth-1" id="li-comment-'+data[i].id+'"><article id="comment-2">'+
               '<a href="#"><img src="'+data[i].imagePath+'" class="avatar avatar-60 photo" height="60" width="60"></a>'+
               '<div class="comment-meta"><h5 class="author"><cite class="fn"><a href="#" rel="external nofollow" class="url">'+data[i].username+
               '</a></cite></h5><p class="date"><time>'+data[i].createDateTime+
               '</time>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="'+data[i].id+'" onclick="openOrCloseResp(this);" name="loadChildren" style="color:red;" href="javascript:void(0)">展开回复</a></p></div><div class="comment-body">'+data[i].content+'</div></article>';
			   $("#commentList").append(element);
		   }
	   }else{
		   alert(result.data);
	   }
   });
	
}
function ajaxReloadAnswerPages(curr,questionId){
    $.post('/love/question',{action:'loadAnswers',questionId:questionId,pageNum:curr},function(result){
	   if(result.status=="success"){
		   $("#commentList").children("li").remove();
		   var data=result.data;
		   for(var i=0;i<data.length;i++){
			   var element='<li class="comment even thread-even depth-1" id="li-comment-'+data[i].id+'"><article id="comment-2">'+
               '<a href="#"><img src="'+data[i].imagePath+'" class="avatar avatar-60 photo" height="60" width="60"></a>'+
               '<div class="comment-meta"><h5 class="author"><cite class="fn"><a href="#" rel="external nofollow" class="url">'+data[i].username+
               '</a></cite></h5><p class="date"><time>'+data[i].createDateTime+
               '</time>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a id="'+data[i].id+'" onclick="openOrCloseResp(this);" name="loadChildren" style="color:red;" href="javascript:void(0)">展开回复</a></p></div><div class="comment-body">'+data[i].content+'</div></article>';
			   $("#commentList").append(element);
		   }
	   }else{
		   alert(result.data);
	   }
   });
	
}
function StringBuffer(){ 
	this.content = new Array; 
	} 
	StringBuffer.prototype.append = function( str ){ 
	this.content.push( str ); 
	} 
	StringBuffer.prototype.toString = function(){ 
	return this.content.join(""); 
	} 
	
function ajaxReloadChildrenComments(aDom,articleId){
	var id=$(aDom).attr('id');
	$.post('/love/comment?action=loadChildren',{id:id},function(result){
		if(result.status=='success'){
			$("#li-comment-"+id).children("ul").remove();	
		    var data=result.data.children;
		    var element=new StringBuffer();
		    element.append('<ul class="children"><li class="comment byuser comment-author-saqib-sarwar bypostauthor odd alt depth-2">');
		    element.append(add(data,'童利航',id));
		    element.append('</li>');
		    element.append('<form action="/love/comment" onsubmit="ajaxSubmitForm(this);"><input type="hidden" name="action" value="submitComment"/>');
		    element.append('<input  type="hidden" name="articleId" value="'+articleId+'"/>');
		    element.append('<input  id="parentId_'+id+'" type="hidden" name="parentId" value="'+id+'"/>');
            element.append('<textarea id="textarea_'+id+'" class="span5 input_key" name="comment" id="comment" cols="15" rows="1"></textarea>');
            element.append('<input class="btn btn-inverse" style="margin-bottom:12px;margin-left:20px" type="submit" id="submit"  value="回复"></form></ul>');
		    //alert(element.toString());
		    $("#li-comment-"+id).append(element.toString());

		}else{
			alert("展开回复失败");
		}
	});
}

function ajaxReloadChildrenAnswers(aDom,questionId){
	var id=$(aDom).attr('id');
	$.post('/love/question?action=loadChildren',{id:id},function(result){
		if(result.status=='success'){
			$("#li-comment-"+id).children("ul").remove();	
		    var data=result.data.children;
		    var element=new StringBuffer();
		    element.append('<ul class="children"><li class="comment byuser comment-author-saqib-sarwar bypostauthor odd alt depth-2">');
		    element.append(add2(data,'童利航',id));
		    element.append('</li>');
		    element.append('<form action="/love/question" onsubmit="ajaxSubmitForm(this);"><input type="hidden" name="action" value="submitAnswer"/>');
		    element.append('<input  type="hidden" name="questionId" value="'+questionId+'"/>');
		    element.append('<input  id="parentId_'+id+'" type="hidden" name="parentId" value="'+id+'"/>');
            element.append('<textarea id="textarea_'+id+'" class="span5 input_key" name="content" id="content" cols="15" rows="1"></textarea>');
            element.append('<input class="btn btn-inverse" style="margin-bottom:12px;margin-left:20px" type="submit" id="submit"  value="回复"></form></ul>');
		    //alert(element.toString());
		    $("#li-comment-"+id).append(element.toString());

		}else{
			alert("展开回复失败");
		}
	});
}

function add(comments,username,id){
	var result=new StringBuffer();
	for(var i=0;i<comments.length;i++){
		var comment=comments[i];
		var element=new StringBuffer();
		element.append('<div class="comment-body"><a style="color:#ff9966" href="javascript:void(0)">');
		element.append(comment.username);
		element.append('</a>');
		if(comment.parentId!=id){
			element.append(' 回复  <a style="color:#ff0000" href="javascript:void(0)">');
			element.append(username+'</a>');
		}
		element.append(' : ');
		element.append(comment.content);
		element.append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
		element.append(' <a style="color:#ff00ff" href="javascript:void(0)">赞(5)</a>&nbsp;&nbsp;&nbsp;<a id="'+comment.id+'" name="'+comment.username+'_'+id+'" style="color:#ff00ff" href="javascript:void(0)" onclick="clickReply(this);">回复</a><br>');
		element.append(comment.createDateTime+'<hr/></div>');
		element.append(add(comment.children,comment.username,id));
		result.append(element);
	}
	return result.toString();
}

function add2(comments,username,id){
	var result=new StringBuffer();
	for(var i=0;i<comments.length;i++){
		var comment=comments[i];
		var element=new StringBuffer();
		element.append('<div class="comment-body"><a style="color:#ff9966" href="javascript:void(0)">');
		element.append(comment.username);
		element.append('</a>');
		if(comment.parentId!=id){
			element.append(' 回复  <a style="color:#ff0000" href="javascript:void(0)">');
			element.append(username+'</a>');
		}
		element.append(' : ');
		element.append(comment.content);
		element.append('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
		element.append(' &nbsp;&nbsp;&nbsp;<a id="'+comment.id+'" name="'+comment.username+'_'+id+'" style="color:#ff00ff" href="javascript:void(0)" onclick="clickReply(this);">回复</a><br>');
		element.append(comment.createDateTime+'<hr/></div>');
		element.append(add2(comment.children,comment.username,id));
		result.append(element);
	}
	return result.toString();
}