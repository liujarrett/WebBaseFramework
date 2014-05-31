<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>业务单列表</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

	<script language="javascript" type="text/javascript">
	$(function(){
		initGridLoading("<%=contextPath%>/common/Images/gridloading.gif");//初始化ajax层，loading图片
		loadData(1);
		enterEvent();//添加按钮回车事件
		//查询事件
		$("#btnSearch").click(function(event){
			var searchStartDate=$("#search_starttime").val();
			var searchEndDate=$("#search_endtime").val();
			if(searchStartDate!="" && searchEndDate!="" && 
					(new Date(Date.parse(searchStartDate.replace(/-/g,"/"))))>(new Date(Date.parse(searchEndDate.replace(/-/g,"/"))))){
				alert("开始时间不能晚于结束时间!");
				$("#searchEndDate").focus();
				return;
			}
			loadData(1);
		});
		//重置事件
		$("#btnReset").click(function(event){
			$("#search_caller_phone").val("");
			$("#search_callee_phone").val("");
			$("#search_opencompany_name").val("");
			$("#search_starttime").val("");
			$("#search_endtime").val("");
			loadData(1);
		});
		
		$("#btnOutWord").click(function(){});
		$("#btnOutExcel").click(function(){});
	});

	function loadData(pageIndex){
		if(!pageIndex) {
			pageIndex='${pageBean.currentPage}';
		}
		//展示背景
		showGridLoading();
		//请求参数
		var callerphone = $("#search_caller_phone").val().trim();
		var calleephone = $("#search_callee_phone").val().trim();
		var opencompanyname = $("#search_opencompany_name").val().trim();
		var starttime = $("#search_starttime").val().trim();
		var endtime = $("#search_endtime").val().trim();
		
		var gridAction="<%=contextPath%>/web/workorder/queryForGrid?"
				       + "workOrder.callerid=" + callerphone + "&"
				       + "workOrder.calleeid=" + calleephone + "&"
				       + "workOrder.openlockname=" + opencompanyname + "&"
				       + "pageBean.currentPage=" + pageIndex + "&"
				       + "pageBean.pageSize=10";
		
		gridAction = encodeURI(encodeURI(gridAction));
		
		$("#divGrid").load(gridAction,{
			startTime : starttime,
			endTime : endtime
			},
			function(){
				//隐藏ajax层
				hideGridLoading();
			}
		);
	}
	</script>
</head>
<body >
    <%-- 导入页面标题栏 --%>
    <%@ include file="../../common/pagehead.jsp"%>
    
	<table width="99%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
	  <!-- 顶部查询部分 -->
	  <tr>
	    <td height="54" >
	    <form name="form">
	    <!-- SeachPanel Begin -->
	    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="nei_tit tit_background">
	      <tr>
	            <td width="3%" height="46">&nbsp;</td>
	            <td width="8%" class="nei_searchTd">主叫号码:</td>
	            <td width="15%">
	                <input id="search_caller_phone" maxlength="50" style='width:150px;'/>
	            </td>
	            <td width="8%" class="nei_searchTd">被叫号码:</td>
	            <td width="15%">
	                <input id="search_callee_phone" maxlength="50" style='width:150px;' />
	            </td>
	            <td width="8%" class="nei_searchTd">开锁公司:</td>
	            <td width="12%">
	                <input id="search_opencompany_name" maxlength="50" style='width:100px;' />
	            </td>
	            <td style='padding-left: 20px;'>
	                <input type="button" class="searchButton" id="btnSearch" />
				</td>
	      </tr>
	      <tr>
	            <td height="35" >&nbsp;</td>
	            <td class="nei_searchTd">受理时间:</td>
	            <td>
	                <input id="search_starttime" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
	            </td>
	            <td class="nei_searchTd">至:</td>
	            <td>
	                <input id="search_endtime" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
	            </td>
	            <td>&nbsp;</td>
	            <td>&nbsp;</td>
	            <td style='padding-left: 20px;'>
				    <input type="button" class="resetButton" id="btnReset" />
				</td>
	      </tr>
	    </table>
	    <!-- SeachPanel End -->
	    </form>
	    </td>
	  </tr>
	  
	  <!-- 操作部分 -->
	  <tr>
	    <td height="34" class="tit2_background">
	    <!-- ButtonTools Begin -->
	     <table width="99%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td style="padding-left:10px;" >
			 		<div class="tool_td tool_word" style="display: none;"><a href="#" id="btnOutWord">Word导出</a></div>
			 		<div class="tool_td tool_excel" style="display: none;"><a href="#" id="btnOutExcel">Excel导出</a></div>
	            </td>
	            </tr>
	        </table>
	      <!-- SeachPanel End -->
	      </td>
	  </tr>
	  
	  <!-- 数据展示部分 -->
	  <tr>
	     <td>
	        <div id="divGrid"></div>
	     </td>
	  </tr>
	</table> 
</body>
</html>
