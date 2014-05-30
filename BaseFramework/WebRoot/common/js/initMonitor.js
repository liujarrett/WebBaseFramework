var currArray = null;
var shineTimer = null;
var shineDivTimer=null;
var originalTitle=document.title;
var contextPath='';
var enableMonitor='0';
function initContextPath(path,hasMonitor){
	enableMonitor=hasMonitor;
	contextPath=path;
}
Array.prototype.remove = function(from, to) {   
    var rest = this.slice((to || from) + 1 || this.length);   
    this.length = from < 0 ? this.length + from : from;   
    return this.push.apply(this, rest);   
}; 
//闪动标题提示来信息状态
function shineTitle(newTitle){
	var shineIndex=newTitle.length-1;
	document.title=originalTitle;
	if (shineTimer) clearInterval(shineTimer);
	var goRight=true;
	var sleepTimes=0;
	shineTimer =setInterval(function () {
					if(sleepTimes<8 && !goRight){
						sleepTimes++;//当提示消息完成时,停顿次数
						return;
					}
                	if(document.title==originalTitle)
		    			document.title="";
    				if(goRight){
    					//向右滚动
    					document.title=newTitle.substr(shineIndex,1)+document.title;
    					shineIndex--;
    				}else{
    					//向左滚动
    					document.title=document.title.substr(1);
    				}
    				if(document.title==newTitle){
    					goRight=false;
    				}
                	if(document.title==""){
                		//完成滚动
                		clearInterval(shineTimer);
						document.title=originalTitle;
                	}
                }, 200);
	
}
//消息窗体头部信息行数
function shineDiv(){
	var spanCount=$("#spanCount");
	if(shineDivTimer) clearInterval(shineDivTimer);
	spanCount.css("color","red");
	var times=0;
	shineDivTimer=setInterval(function () {
					if(currArray==null || currArray.length==0){
						clearInterval(shineDivTimer);
						return;
					}
					if(spanCount.html().substr(0,1)!="<")
						spanCount.html("<span style='color:white;'>"+currArray.length+"</span>");
					else
						spanCount.html(currArray.length);
					times++;
					if(times>=30){
						spanCount.html(currArray.length);
						clearInterval(shineDivTimer);
					}
				},200);
}
//跳出提示消息窗体
function showMessageDiv(dataArray){
	if(currArray==null){
		currArray=dataArray;
		$("#pnMain").attr("messageIndex","0");
	}
	else{
		if(currArray[0][0]==dataArray[0][0])
			return;//出现重复加载
		for(var i=currArray.length;i>0;i--){
			currArray[i]=currArray[i-1];
		}
		currArray[0]=dataArray[0];
		$("#pnMain").attr("messageIndex",(parseInt($("#pnMain").attr("messageIndex"))+1)+"");
	}
	if(frames[0].reloadPage){
		//若当前打开信息窗体则刷新窗体
		frames[0].reloadPage();
	}
	shineTitle("您有新的内部消息,请注意查收...");
	
	if($("#divMessage").size()==0){
		$("#pnMain").attr("messageIndex","0");
		//当前不存在面板则跳出提示窗体
		$.messager.show({
			title:'<a href="#" title="跳转至内部消息" onclick="goToMessage();" style="color:#1641B4">您一共有<span style="color:red" id="spanCount">'+currArray.length+'</span>条未读消息</a>',
			msg:"&nbsp;",
			timeout:0,
			height:130,
			showType:'fade'
		});
		$("[class='panel window']").attr("id","divMessage");//添加ID标示，提高后续访问效率
		$("#divMessage").find("[class$='window-body']").attr("id","divContents").css("border-bottom","dashed 1px #99bbe8");
		$("#divMessage").append("<div id='divPager' style='height:24px;background-color:white;overflow:hidden;border:solid 1px #99bbe8;border-top:none;'></div>");
	}
	displayMessageSource();
	shineDiv();//来新信息闪烁message标题
}
//初始化获取未读信息
function monitorInit(timeSpan){
	setTimeout(function(){ 
    	 $.ajax({
			url			: contextPath+"/message/json/message!queryUnReadSource.action",
			type 		: "POST",
			dataType 	: "json",
			success 	: function(data) {
				if(data.length>0){
					showMessageDiv(data);
				}
			},
			error		:function(){
				alert("请检查您的网络环境,确保其连接正常！");
			}
		});
   	 },timeSpan);
}
//转入内部消息界面
function goToMessage(){
	$("#divMessage").fadeOut(600,function(){$("#divMessage").remove()});
	$('#treeMenu').find("li[nodeattr1$='message!queryForList.action']:first >span").trigger("click");
}
//获取信息内容(格式化后)
function getMessageFormat(){
	var currIndex=parseInt($("#pnMain").attr("messageIndex"));
	return "<a href='#' style='cursor: pointer;' title='点击查看详细' onclick='checkMessage("+currArray[currIndex][0]+");'><table cellpadding='0' cellspacing='0' width='100%' style='TABLE-LAYOUT: fixed;'><tr><td style='word-WRAP: break-word;'><span style='color:#1641B4;white-space: nowrap;'>来自：</span>" +
			(currArray[currIndex][3] == null ? "<span style='color:red'>此用户已删除</span>":currArray[currIndex][3]) + "</td></tr><tr><td style='word-WRAP: break-word;'><span style='color:#1641B4'>时间：</span>"+currArray[currIndex][2].toString().replace("T"," ")+" </td></tr><tr><td style='word-WRAP: break-word;'><span style='color:#1641B4;white-space: nowrap;'>标题：</span>"+
			currArray[currIndex][1] + "</td></tr></table></a>";
}
//翻页
function changePage(tag){
	var currIndex=parseInt($("#pnMain").attr("messageIndex"));
	switch(tag){
		case "first":
			$("#pnMain").attr("messageIndex",0);
			break;
		case "pre":
			if(currIndex>0)
				$("#pnMain").attr("messageIndex",currIndex-1);
			break;
		case "next":
			if(currIndex<currArray.length-1)
				$("#pnMain").attr("messageIndex",currIndex+1);
			break;
		case "last":
			$("#pnMain").attr("messageIndex",currArray.length-1);
			break;
	}
	displayMessageSource();
}
//显示消息内容页面内容
function displayMessageSource(){
	$("#spanCount").html(currArray.length);
	var currIndex=parseInt($("#pnMain").attr("messageIndex"));
	//加载内容
	$("#divContents").html(getMessageFormat(currArray[currIndex]));
	var pageHtml="<table width='100%' height='100%'><tr><td width='26' align='center'>";
	//加载分页控件
	if(currIndex==0)
		pageHtml+="<img src='"+contextPath+"/common/Images/l1h.gif' />&nbsp;<img src='"+contextPath+"/common/Images/lh.gif' />";
	else
		pageHtml+="<a href='#' title='点击查看详细' onclick='changePage(\"first\");'><img src='"+contextPath+"/common/Images/l1.gif' /></a>"+
							"&nbsp;<a href='#' onclick='changePage(\"pre\");'><img src='"+contextPath+"/common/Images/l.gif' /></a>";
	pageHtml+="</td><td width='25' align='center' style='font-weight:bold;' valign='top'>"+(currIndex+1)+"</td><td width='26' align='center'>";
	if(currIndex==currArray.length-1)
		pageHtml+="<img src='"+contextPath+"/common/Images/rh.gif' />&nbsp;<img src='"+contextPath+"/common/Images/r1h.gif' />";
	else
		pageHtml+="<a href='#' onclick='changePage(\"next\");'><img src='"+contextPath+"/common/Images/r.gif' /></a>"+
				"&nbsp;<a href='#' onclick='changePage(\"last\");'><img src='"+contextPath+"/common/Images/r1.gif' /></a>";
	pageHtml+="</td><td align='right' valign='top' style='font-weight:bold;'><a href='#' onclick='nerverTip();'>[本次不再提示]</a></td></tr></table>";
	$("#divPager").html(pageHtml);
}
//本次不再提示
function nerverTip(){
	currArray=null;
	//隐藏
	if($("#divMessage").size()>0)
		$("#divMessage").fadeOut(600,function(){$("#divMessage").remove()});
	frames[1].stop();
}
//查看信息
function checkMessage(messageId){
	var currMenuId=$('#treeMenu').find("li[nodeattr1$='message!queryForList.action']:first").attr("code");
	//标记已读
	$.ajax({
		url			:contextPath+"/message/json/message!markReadyState.action",
		type		:"POST",
		dataType	:"json",
		data		:{"unReadMessage.messageId":messageId,"currMenuId":currMenuId},
		success		:function(data){
			if(frames[0].reloadPage){
				//若当前打开信息窗体则刷新窗体
				frames[0].reloadPage(messageId);
			}
			//标记成功，重新加载数据
			ShowIframe('查看-消息信息',contextPath+'/message/message!queryForObject.action?message.state=1&message.messageId='+messageId,586,450);
		},
		error		:function(){
			ShowIframe('查看-消息信息',contextPath+'/message/message!queryForObject.action?message.state=1&message.messageId='+messageId,586,450);
		}
	});
	modifyUnReadyArray(messageId);
}
 
//刷新未读数组数据
function modifyUnReadyArray(messageIds){
	if(currArray==null || (enableMonitor=='0' && $("#divMessage").size()==0))
		return;
	if(currArray.length==1){
		currArray=null;
		//当前为最后一条信息,查看后隐藏
		$("#divMessage").fadeOut(600,function(){$("#divMessage").remove()});
		return;
	}
	var messageIdArray=messageIds.toString().split(",");
	var currIndex=parseInt($("#pnMain").attr("messageIndex"));
	for(var i=0;i<currArray.length;i++){
		if(messageIdArray.length==0)
			break;
		for(var j=0;j<messageIdArray.length;j++){
			if(currArray[i][0]==messageIdArray[j]){
				if(i<=currIndex)
					currIndex--;
				messageIdArray.remove(j);//移除对比数据
				currArray.splice(i,1);	 //移除原数据
				i--;
				break;
			}
		}
	}
	if(currArray==null || currArray=='' || currArray.length==0){
		currArray=null;
		//当前为最后一条信息,查看后隐藏
		$("#divMessage").fadeOut(600,function(){$("#divMessage").remove()});
		return;
	}
	if(currIndex<0)
		currIndex=0;
	else if(currIndex>currArray.length){
		currIndex=currArray.length-1;
	}
	$("#pnMain").attr("messageIndex",currIndex);
	//刷新面板
	displayMessageSource();
}
//删除数组
function removeCurrArray(currIndex){
	var isFind=false;
	for(var i = 0; i<currArray.length-1;i++){
		if(i==currIndex)
			isFind=true;
		if(isFind){
			currArray[i]=currArray[i+1];
		}
	}
	if(isFind){
		currArray.length=currArray.length-1;
	}
}
//顶部展开收起面板
function topPanelInit(){
	$("#divTopAuto").width($(window).width ()). animate({opacity:0},10).hover(
        function(){
             $("#divTopAuto").   animate({ 
                height: "30px",
                opacity:0.4
             }, 150 );
        },
        function(){
             $("#divTopAuto").   animate({ 
                height: "12px",
                opacity:0
             }, 150 );
        }).click(function(){
        	var topPanel=$("#pnTop");
            if($(this).attr("title")=="点击收起顶部面板"){
                $(this).attr("title","点击展开顶部面板");
                topPanel.fadeOut( 250 ,function(){
                	 topPanel.panel("options").height=0;
					 topPanel.panel("resize");
                     $("#pnMain,#pnLeft").parent().each(function(i){
                    	$(this).animate({
                    		top:(parseInt($(this).css("top"))-70)+"px",
                    		height:($(this).height()+70)+"px"
                    	},10,function(){
                    		if(i==1){
                				$(window).trigger("resize");
                				if(typeof(frames[0].resizeWin)=="function")
                					setTimeout(frames[0].resizeWin,500);
                    		}
                    	});
                     });
                 });
            }else{
                $(this).attr("title","点击收起顶部面板");
                 $("#pnMain,#pnLeft").parent().each(function(i){
                	$(this).animate({
                		top:(parseInt($(this).css("top"))+70)+"px",
                		height:($(this).height()-70)+"px"
                	},10,function(){
                		if(i==1){
                			$(window).trigger("resize");
                			topPanel.fadeIn( 30 ,function(){
                        	   topPanel.panel("options").height=70;
							   topPanel.panel("resize");
							   if(typeof(frames[0].resizeWin)=="function")
                					setTimeout(frames[0].resizeWin,500);
	                        });
                		}
                	});
                 })
            }
        }). animate({opacity:'show'},10);
    $("#divTopAuto").   animate({ 
        height: "15px",
        opacity:0
     }, 150 );
    $(window).resize(function(){
        $("#divTopAuto").width($(window).width ());
    });
}