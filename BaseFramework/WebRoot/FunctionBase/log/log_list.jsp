<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>日志列表</title>
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
			loadData(1,1);
			//查询事件
			$("#btnSearch").click(function(event){
				var searchStartDate=$("#search_start_time").val();
				var searchEndDate=$("#search_end_time").val();
				if(searchStartDate!="" && searchEndDate!="" &&
						(new Date(Date.parse(searchStartDate.replace(/-/g,"/"))))>(new Date(Date.parse(searchEndDate.replace(/-/g,"/"))))){
					alert("开始时间不能晚于结束时间!");
					$("#search_end_time").focus();
					return;
				}
				var logType = $("#search_logType").val();
				loadData(1,logType);
			});
			//重置事件
			$("#btnReset").click(function(event){
				$("#search_operator").val("");
				$("#search_clientType").val("-1");
				$("#search_logType").val("1");
				$("#search_operateType").val("-1");
				$("#search_start_time").val("");
				$("#search_end_time").val("");
				document.getElementById("operateType_tr").style.display="none";
				loadData(1,1);
			});
			$("#btnOutWord").click(function(){});
			$("#btnOutExcel").click(function(){});
		});
		
		//pageIndex 分页   logType日志类型
		//不同的logType，接口不同
		function loadData(pageIndex,logType){ 
			if(!pageIndex) {
				pageIndex=1;
			}
			
			showGridLoading();
			
			var operator_name = $("#search_operator").val().trim();
		    var client_type = $("#search_clientType").val().trim();
			var start_time = $("#search_start_time").val();
			var end_time = $("#search_end_time").val();
			
			if(logType == 1) { //登陆日志
				var gridAction = "<%=contextPath%>/web/log/queryLoginLogForGrid";
				$("#divGrid").load(gridAction,{
					 "loginLog.user.userName":operator_name,
					 "loginLog.loginType":client_type,
					 "loginLog.searchStartTime":start_time,
					 "loginLog.searchEndTime":end_time,
					 "loginLogPageBean.currentPage":pageIndex
			    	 },
				     function(){
					     hideGridLoading();
					 }
				 );
			} else {//操作日志
				var gridAction = "<%=contextPath%>/web/log/queryOperateLogForGrid";
				var operate_type = $("#search_operateType").val();
				$("#divGrid").load(gridAction,{
					 "operateLog.user.userName":operator_name,
					 "operateLog.clientType":client_type,
					 "operateLog.operateType":operate_type,
					 "operateLog.searchStartTime":start_time,
					 "operateLog.searchEndTime":end_time,
					 "operateLogPageBean.currentPage":pageIndex
			    	 },
					 function(){
						hideGridLoading();
					 }
				);
			}
		}
		
		function switchLogType() {
			var val = $("#search_logType").val();
			if(val == 1) {
				document.getElementById("search_operateType").value=-1;
				document.getElementById("operateType_tr").style.display="none";
			} else if(val == 2) {
				document.getElementById("operateType_tr").style.display="";
			}
		}
	</script>
</head>
<body >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
	<!-- 导入页面标题栏 -->
	<tr>
		<td>
			<%@ include file="../../common/pagehead.jsp"%>
		</td>
	</tr>
	<!-- 顶部查询部分 -->
	<tr>
		<td height="54" >
		    <!-- SeachPanel Begin -->
		    <form name="form">
			    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="nei_tit tit_background">
			      <tr>
			          <td width="3%" height="46">&nbsp;</td>
			          <td width="8%" class="nei_searchTd">操 作 者:</td>
			          <td width="15%">
			              <input id="search_operator" maxlength="50" style='width:150px;'/>
			          </td>
			          <td width="8%" class="nei_searchTd">客户端类型:</td>
			          <td width="15%">
				          <select id="search_clientType" style="width: 155px;">
                              <option value="-1" selected="selected">请选择客户端类型</option>
                              <option value="1">手机客户端</option>			
                              <option value="2">浏览器客户端</option>							          
				          </select>
			          </td>
			          <td width="8%" class="nei_searchTd">日志类型:</td>
			          <td width="15%">
			               <select id="search_logType" style="width: 155px;" onchange="switchLogType();">
                              <option value="1" selected="selected">登录日志</option>			
                              <option value="2">操作日志</option>							          
				          </select>
			          </td>
			          <td style='padding-left: 20px;'>
			              <input type="button" class="searchButton" id="btnSearch" />
				      </td>
			      </tr>
			      
			      <tr style="display: none;" id="operateType_tr">
			          <td width="3%" height="46">&nbsp;</td>
			          <td width="8%" class="nei_searchTd">操作类型:</td>
			          <td width="15%">
			              <select id="search_operateType" style="width: 155px;">
                              <option value="-1" selected="selected">请选择操作类型</option>
                              <option value="1">增加数据</option>	
                              <option value="2">删除数据</option>	
                              <option value="3">更新数据</option>			
				          </select>
			          </td>
			      </tr>
			      <tr>
			          <td height="35" >&nbsp;</td>
			          <td class="nei_searchTd">操作时间:</td>
			          <td>
			              <input id="search_start_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			          </td>
			          <td width="8%" class="nei_searchTd" >至:</td>
			          <td>
			              <input id="search_end_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			          </td>
			          <td style='padding-left: 20px;'>
						  <input type="button" class="resetButton" id="btnReset" />
					  </td>
			      </tr>
			    </table>
		    </form>
		    <!-- SeachPanel End -->
	    </td>
	</tr>
 	<!-- 操作部分 -->
	<tr>
	    <td height="34" class="tit2_background">
	    <!-- ButtonTools Begin -->
	     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td style="padding-left:10px;" >
			 		<div class="tool_td tool_excel"><a href="#" id="btnOutExcel">Excel导出</a></div>
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
