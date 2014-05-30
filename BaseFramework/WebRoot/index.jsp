<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/blue/main.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTreeSource.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/popup.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/popupclass.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/initMonitor.js"></script>
<script language="javascript" type="text/javascript">
	    $(function () {
	    	loadTree(0);
	    	$("#btnLogout").click(function(){
	    		if (confirm("确定要退出系统吗？")) {
	    			window.location="<%=contextPath%>/logout.jsp";
	    		}
	    	});
	    	//默认转到首页面
	    	$("#iframeBody").attr("src","<%=contextPath%>/main.jsp"); 
	    	//顶部展开收起层初始化
	    	topPanelInit();
	    });
	    //加载菜单树
	    function loadTree(num){
	    	$('#treeMenu').nfpTree({
		        ajaxUrl: "<%=contextPath%>/web/role/json/queryMenu",
		        themeUrl:'<%=contextPath%>/common/nfpTreeThemes',
		        paramsName:"funcParentId",
		        animate:false,
		        clickStyle:false,
		 		expandLevel:2,
		 		inPanel:true,
		        afterClick: function (node, treeObj) {
		        	var nodeObj=node.children("[isNodeText]");
		        	var url = node.attr("nodeattr1");
		        	
					//加载页面地址
					if(url && url!= 'null' && url !=""){
						$("#iframeBody").attr("src",'<%=contextPath%>/' + url);
						$("span.active",$('#treeMenu')).attr("class","text");
						nodeObj.attr("class","active");
					}
		        }
		    });
	    }
</script>
</head>
<body class="easyui-layout">
    <!-- 点击收起顶部面板 -->
	<div style="position: absolute; left: 0px; top: 0px; z-index: 9000; display: none; height: 0px; font-size: 12px; cursor: pointer; text-align: center; color: White; background-color: Black;"
		 id="divTopAuto" title="点击收起顶部面板">
    </div>
	
	<div region="north" id="pnTop" border="false" style=" overflow:hidden;height:70px;background:#B3DFDA;background-image: url(<%=contextPath%>/common/Images/Images/topbg1.jpg); ">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr width="100%" background="<%=contextPath%>/common/Images/Images/topbg1.jpg">
				<td width="50%" height="70" >
					<img src="<%=contextPath%>/common/Images/Images/top1.png" />
				</td>
				<td width="50%">
					<table width="290" border="0" align="right" cellpadding="0" cellspacing="0">
						<tr>
							<td align="center">
							    <a href="#"><img src="<%=contextPath%>/common/Images/gy.png" /></a>
							</td>
							<td align="center">
							    <a href="#"><img src="<%=contextPath%>/common/Images/sm.png" /></a>
							</td>
							<td align="center">
							    <a href="#" id="btnLogout"><img src="<%=contextPath%>/common/Images/exit.png" /></a>
							</td>
						</tr>
						<tr>
							<td align="center"><font color="white">关于</font></td>
							<td align="center"><font color="white">帮助</font></td>
							<td align="center"><font color="white">退出</font></td>
						</tr>
					</table>
			    </td>
			</tr>
		</table>
	</div>
	
	<div region="west" split="true" id="pnLeft" title="系统菜单" style="width: 180px; padding: 10px;">
		 <ul id="treeMenu"></ul>
	</div>
	
	<div region="south" border="false" style="height:20px;background:#A9FACD;background-image: url(<%=contextPath%>/common/blue/Images/banner_bg.gif);">
		<div style="text-align:center">96110</div>
    </div>
    <!-- 主窗体 -->
	<div region="center" id="pnMain">
		<iframe width="100%" frameborder="no" style="border : none;" height="100%" id="iframeBody"></iframe>
	</div>
</body>
</html>