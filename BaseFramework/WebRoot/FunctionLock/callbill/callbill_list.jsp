<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>通话单列表</title>
	<link rel="stylesheet" href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" type="text/css"></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
  	<script type="text/javascript" src="<%=contextPath %>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	
	<script language="javascript" type="text/javascript">
		$(function(){
			initGridLoading("<%=systemColor%>/Images/gridloading.gif");//初始化ajax层，loading图片
			loadData(1);
			//查询事件
			$("#btnSearch").click(function(event){
				var searchStartDate=$("#search_hangon_time").val();
				var searchEndDate=$("#search_hangoff_time").val();
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
				$("#search_opencompany_phone").val("");
				$("#search_hangon_time").val("");
				$("#search_hangoff_time").val("");
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
			var caller_phone = $("#search_caller_phone").val().trim();
		    var callee_phone = $("#search_callee_phone").val().trim();
			var opencompany_phone = $("#search_opencompany_phone").val().trim();
			var hangon_time = $("#search_hangon_time").val();
			var hangoff_time = $("#search_hangoff_time").val();
			//加载列表数据
			var gridAction = "<%=contextPath%>/web/callbill/queryForGrid.action?"
					       + "callbill.callerid=" + caller_phone + "&"
					       + "callbill.calleeid=" + callee_phone + "&"
					       + "callbill.openlocktel" + opencompany_phone + "&"
					       + "pageBean.currentPage=" + pageIndex + "&"
					       + "pageBean.pageSize=10";
			//执行查询
			$("#divGrid").load(gridAction,{
			      hangontime : hangon_time,
			      hangofftime : hangoff_time
		    	},
				function(){
					hideGridLoading();//隐藏ajax层
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
		    <!-- SeachPanel Begin -->
		    <form name="form">
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
			          <td width="8%" class="nei_searchTd">开锁公司电话:</td>
			          <td width="12%">
			              <input id="search_opencompany_phone" maxlength="50" style='width:100px;' />
			          </td>
			          <td style='padding-left: 20px;'>
			              <input type="button" class="searchButton" id="btnSearch" />
				      </td>
			      </tr>
			      <tr>
			          <td height="35" >&nbsp;</td>
			          <td class="nei_searchTd">提机时间:</td>
			          <td>
			              <input id="search_hangon_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
			          </td>
			          <td class="nei_searchTd">至:</td>
			          <td>
			              <input id="search_hangoff_time" class="Wdate" maxlength="50" style='width:152px;height:16px;' type="text" onClick="WdatePicker({dateFmt:'yyyy-M-d H:m:s'})">
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
