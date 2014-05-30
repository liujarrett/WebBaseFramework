<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>开锁信息列表</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/blue/main.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/icon.css"/>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	
	<script language="javascript" type="text/javascript">
		$(function(){
			initGridLoading("<%=contextPath%>/common/Images/gridloading.gif");//初始化ajax层，loading图片
			loadData(1);
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
				loadData(1);
			});
			//重置事件
			$("#btnReset").click(function(event){
				$("#search_opener_userName").val("");
				$("#search_customer_name").val("");
				$("#search_start_time").val("");
				$("#search_end_time").val("");
				$("#search_open_type").val(-1); 
				loadData(1);
			});
			$("#btnOutWord").click(function(){});
			$("#btnOutExcel").click(function(){});
		});
	
		function loadData(pageIndex){
			//获取当前页数
			if(!pageIndex) {
				pageIndex="${pageBean.currentPage + 1}";
			}
		    //展示背景
			showGridLoading();
			//获取参数
			var opener_name = jQuery.trim($("#search_opener_userName").val());
		    var customer_name = jQuery.trim($("#search_customer_name").val());
			var open_type = $("#search_open_type").val();
			var start_time = $("#search_start_time").val();
			var end_time = $("#search_end_time").val();
			//加载列表数据
			var gridAction = "<%=contextPath%>/web/unlock/queryForGrid";
			//执行查询
			$("#divGrid").load(gridAction,{
				"unlockInfo.user.userName":opener_name,
				"unlockInfo.customerName":customer_name,
				"unlockInfo.unlockType":open_type,
				"unlockInfo.unlockStartTime":start_time,
				"unlockInfo.unlockEndTime":end_time,
				"pageBean.currentPage":pageIndex
			    },
				function(){
					hideGridLoading();//隐藏ajax层
				}
			);
		}
	</script>
</head>
<body class="easyui-layout">

	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
		<%-- 导入页面标题栏 --%>
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
			          <td width="8%" class="nei_searchTd">开锁人:</td>
			          <td width="15%">
			              <input id="search_opener_userName" maxlength="50" style='width:150px;'/>
			          </td>
			          <td width="8%" class="nei_searchTd">客户姓名:</td>
			          <td width="15%">
			              <input id="search_customer_name" maxlength="50" style='width:150px;' />
			          </td>
			          <td width="8%" class="nei_searchTd">开锁类型:</td>
			          <td width="12%">
			              <select id="search_open_type" style='width:100px;'>
			                  <option value="-1">请选择</option>
			                  <option value="1">开民用锁</option>
			                  <option value="2">开汽车锁</option>
			                  <option value="3">开保险箱</option>
			                  <option value="4">开ATM取款机</option>
			                  <option value="5">开金库</option>
			              </select>
			          </td>
			          <td style='padding-left: 20px;'>
			              <input type="button" class="searchButton" id="btnSearch" />
				      </td>
			      </tr>
			      <tr>
			          <td height="35" >&nbsp;</td>
			          <td class="nei_searchTd">开锁时间:</td>
			          <td>
			              <input id="search_start_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
			          </td>
			          <td class="nei_searchTd">至:</td>
			          <td>
			              <input id="search_end_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
			          </td>
			          <td>&nbsp;</td>
			          <td>&nbsp;</td>
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
			 		<div class="tool_td tool_word"><a href="#" id="btnOutWord">Word导出</a></div>
			 		<div class="tool_td tool_excel"><a href="#" id="btnOutExcel">Excel导出</a></div>
	            </td>
	            </tr>
			</table>
			<!-- ButtonTools End -->
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
