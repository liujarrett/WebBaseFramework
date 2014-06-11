<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>开锁信息列表</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/blue/main.css" ></link>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/nfpTreeThemes/nfpTreeCss.css" ></link>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/ajaxPager.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/nfpTree.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/inputCheck.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/js/convertChinese.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	
	<script language="javascript" type="text/javascript">
	    
	    var currentChooseType = "";
	    var currentChooseId = -1;
	    
		$(function(){
			initGridLoading("<%=contextPath%>/common/Images/gridloading.gif");//初始化ajax层，loading图片
			//loadData(1);
			loadTree();
			//查询事件
			$("#btnSearch").click(function(event){
				var searchStartDate=$("#search_start_time").val();
				var searchEndDate=$("#search_end_time").val();
				
				if(searchStartDate!="" && searchEndDate!="" &&
						(new Date(Date.parse(searchStartDate.replace(/-/g,"/"))))>(new Date(Date.parse(searchEndDate.replace(/-/g,"/")))))
				{
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
				// outExcel
				$("#outExcelOpenerUserName").val("");
				$("#outExcelCustomerName").val("");
				$("#outExcelStartTime").val("");
				$("#outExcelEndTime").val("");
				$("#outExcelOpenType").val("");
				
				
				loadData(1);
			});
			
			$("#btnOutWord").click(function(){});
			
			$("#btnOutExcel").click(function(){
				
				$("#submitExcel").submit();
				
			});
		});
		
		function loadTree(){
	    	$("#areaTree").nfpTree({
	    		ajaxUrl: "<%=contextPath%>/web/unlock/json/queryTreeOfAreaAndCompany",
		        themeUrl:'<%=contextPath%>/common/nfpTreeThemes',
		        paramsName:"treeParentId",
		 		expandLevel:2,
		 		clickStyle:false,
		        afterClick: function (node, treeObj) {
		        	var nodeObj=node.children("[isNodeText]")
					$("span.active",$('#areaTree')).attr("class","text");
					nodeObj.attr("class","active");   	//切换选中节点颜色
					var nodeValue = node.attr("code"); 
					var values= new Array();
					values = nodeValue.split("-");
					if(values[0] == 0) 
					{
						currentChooseType = "COMPANY_TYPE";
						currentChooseId = values[1];
					} else if(values[1] == 0) 
					{
						currentChooseType = "AREA_TYPE";
						currentChooseId = values[0];
					}
					loadData(1);
		        }
		    });
	    }
		
		function loadData(pageIndex)
		{
			//获取当前页数
			if(!pageIndex) 
			{
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
			
			// outExcel
			$("#outExcelOpenerUserName").val(opener_name);
			$("#outExcelCustomerName").val(customer_name);
			$("#outExcelStartTime").val(start_time);
			$("#outExcelEndTime").val(end_time);
			$("#outExcelOpenType").val(open_type);
			$("#outExcelChooseType").val(currentChooseType);
			$("#outExcelChooseId").val(currentChooseId);
			
			
			//加载列表数据
			var gridAction = "<%=contextPath%>/web/unlock/queryForGrid?manuType=" + currentChooseType + "&id=" + currentChooseId;
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
	 <div region="north" border="false" style="height:153;background:#FFFFFF;">
		 <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
		 <%-- 导入页面标题栏 --%>
		 <tr>
			 <td>
				<%@ include file="../../common/pagehead.jsp"%>
			 </td>
		 </tr>
		 <%-- 搜索栏 --%>
	     <tr>
	       <td height="54" >
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
				                  <option value="100">其它</option>
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
	        </td>
	      </tr>
	      <%-- 工具栏 --%>
		  <tr>
		 	<td height="34" class="tit2_background">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td style="padding-left:10px;" >
		            	<c:if test="${fn:contains(sessionScope.resourceIds,236)}">
				 			<div class="tool_td tool_excel">
				 				<a href="#" id="btnOutExcel">Excel导出</a>
							</div>
                        </c:if>
		            </td>
		          </tr>
				</table>
		    </td>
		 </tr>
	    </table>
	</div>

	<!-- 区域列表 -->
    <div region="west" id="west" split="false" title="区域列表"  style="width:200px;padding:10px;">
	    <ul id="areaTree"></ul>
	</div>
	
	<!-- 开锁信息列表 -->
	<div region="center" title="开锁信息列表"  style="width:600px;">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="nei_box" align="center">
		  <tr>
		     <td>
		       <div id="divGrid"></div>
		      	<form action="<%=contextPath%>/web/unlock/outExcel" method="post" id="submitExcel">
					<input type="hidden" id="outExcelOpenerUserName" name="outExcelOpenerUserName" />
					<input type="hidden" id="outExcelCustomerName" name="outExcelCustomerName" />
					<input type="hidden" id="outExcelStartTime" name="outExcelStartTime" />
					<input type="hidden" id="outExcelEndTime" name="outExcelEndTime" />
					<input type="hidden" id="outExcelOpenType" name="outExcelOpenType" />
					<input type="hidden" id="outExcelChooseType" name="outExcelChooseType" />
					<input type="hidden" id="outExcelChooseId" name="outExcelChooseId" />
		 	 	</form>
		     </td>
		  </tr>
		</table>
	</div>
</body>
</html>
