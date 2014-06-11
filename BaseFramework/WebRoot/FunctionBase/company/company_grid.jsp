<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%  
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>查看-单位信息</title>
<link rel="stylesheet"  href="<%=contextPath%>/common/blue/main.css" type="text/css"></link>
<link href="<%=contextPath%>/common/site.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	$(function(){
		//全选按钮
		$("#checkAll").click(function(){
			$(".CHECKBOX").attr("checked",$(this).attr("checked"));
		});
		//单选
		$("tr[tag='source'] :checkbox").click(function(){
			if($(this).attr("checked") && $("tr[tag='source'] :checkbox:not(:checked)").size()==0)
				$("#checkAll").attr("checked",true);
			else if($("#checkAll").attr("checked"))
				$("#checkAll").removeAttr("checked");
		});
	 });
	
	 function passAll() {
				var ids = "";
				$(".CHECKBOX:checked").each(function(){
					ids += $(this).val()+";";
				});
				if (ids.length > 0) {
					if(confirm("确定执行批量审核？")){
						$.ajax({
							url			:"<%=contextPath%>/web/company/json/passCompany?ids="+ids,
							type		:"POST",
							dataType	:"json",
							success		:function(data){
									alert("批量审核成功！");
									parent.frames[0].loadData('${pageBean.currentPage}');
							},
							error		:function(){
								alert("批量审核失败！");
								$(".CHECKBOX:checked").each(function(){
									$(this).removeAttr("checked");
								});
								$("#checkAll").removeAttr("checked");
							}
						});
					} else {
						$(".CHECKBOX:checked").each(function(){
							$(this).removeAttr("checked");
						});
						$("#checkAll").removeAttr("checked");
					}
				} else {
					alert("请先选择单位！");
				}
			
	}

	function rejectAll() {
			var ids = "";
			$(".CHECKBOX:checked").each(function(){
				ids += $(this).val()+";";
			});
			if (ids.length > 0) {
				if(confirm("确定执行驳回？")){
				
					$.ajax({
						url			:"<%=contextPath%>/web/company/json/rejectCompany?ids="+ids,
						type		:"POST",
						dataType	:"json",
						success		:function(data){
								alert("驳回成功！");
								parent.frames[0].loadData('${pageBean.currentPage}');
						},
						error		:function(){
							alert("驳回失败！");
							alert("请检查您的网络环境，确保其连接正常！");
							$(".CHECKBOX:checked").each(function(){
								$(this).removeAttr("checked");
							});
							$("#checkAll").removeAttr("checked");
						}
					});
				} else {
					$(".CHECKBOX:checked").each(function(){
						$(this).removeAttr("checked");
					});
					$("#checkAll").removeAttr("checked");
				}
			} else {
				alert("请先选择单位！");
			}
		
	}
	</script>
</head>
<body class="popDiv">
<div class="tit3_background" style="height: 30px;">
	<%-- 分页开始 --%>
	<%@ include file="../../common/pageajax.jsp"%>
    <%-- 分页结束 --%>
</div>
<div>
<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable" >
 	<tr>
 	        <th width="30" class="nei_txt_menu"><input type="checkbox" value="checkbox" id="checkAll" /></th>
		  	<th width="40" class="nei_txt_menu">序号</th>
		  	<th width="150" class="nei_txt_menu">所属区域</th>
		  	<th width="150" class="nei_txt_menu">登录代码</th>
		    <th width="150" class="nei_txt_menu">公司名称</th>
			<!-- 
			<th width="150" class="nei_txt_menu">公司地址</th>	    
			<th width="150" class="nei_txt_menu">联系电话</th>
		    <th width="150" class="nei_txt_menu">公司法人</th>
		    <th width="150" class="nei_txt_menu">工商注册号</th>
		    <th width="150" class="nei_txt_menu">所属派出所</th>
		     -->
		    <th width="80" class="nei_txt_menu">状态</th>
		    <th class="nei_txt_menu" width="180" style="border-right: none;">操 作</th>
	</tr>
  <c:forEach items="${pageBean.list}" var="company" varStatus="v" >
		 	<tr tag="source" id="trWithCB" <c:if test="${v.count%2==0}">class="trBg"</c:if> >
		 		<td class="noWrapTd" align="center"><input type="checkbox" value="${company.id }" class="CHECKBOX" /></td>
	    		<td class="noWrapTd">${(pageBean.currentPage-1) * pageBean.pageSize + v.count}</td>
	    		<td class="noWrapTd">${company.area.areaName }&nbsp;</td>
	    		<td class="noWrapTd">${company.companyCode }</td>
	    		<td class="noWrapTd">${company.companyName }</td>
				<%-- 
				<td class="noWrapTd">${company.address }&nbsp;</td>
			    <td class="noWrapTd">${company.telephone }&nbsp;</td>
			    <td class="noWrapTd">${company.corporationName }&nbsp;</td>
			    <td class="noWrapTd">${company.corporationId }&nbsp;</td>
			    <td class="noWrapTd">${company.remarks }&nbsp;</td> 
			    --%>
			    <td class="noWrapTd">${company.currentState==0?'未审核':company.currentState==1?'已审核':'驳回' }&nbsp;</td>
			    <td align="center">
			    <c:if test="${fn:contains(sessionScope.resourceIds,21)}">
			       <a href="#" 
			          onClick="parent.ShowIframe('查看-公司信息','<%=contextPath%>/web/company/queryCompany?company.id=${company.id}&pageBean.currentPage=${pageBean.currentPage}&manuType=query',480,400);">查看</a>
			          &nbsp;&nbsp;|&nbsp;&nbsp;
                </c:if>
			    <c:if test="${fn:contains(sessionScope.resourceIds,23)}">
				   <a href="#" 
				      onClick="parent.ShowIframe('编辑-公司信息','<%=contextPath%>/web/company/queryCompany?company.id=${company.id}&pageBean.currentPage=${pageBean.currentPage}&manuType=edit',480,400);">编辑</a>
				      &nbsp;&nbsp;
				</c:if>
				<c:if test="${fn:contains(sessionScope.resourceIds,24)}">
				   <c:if test="${company.isDelete!=1 }">
				      |&nbsp;&nbsp;
				      <a href="javascript:void(0);" onClick="deleteCompany(${company.id},${pageBean.currentPage});">删除</a>
				   </c:if>
				</c:if>
				</td>
	  		</tr>		 		
   </c:forEach>
</table>
</div>
</body>
</html>