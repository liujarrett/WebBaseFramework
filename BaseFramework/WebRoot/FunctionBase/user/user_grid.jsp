<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String contextPath = request.getContextPath();
%>
<html>
<head>
<script language="javascript">
	$(function(){
		//删除用户
		$("a[isDelete]").click(function(){
			var userId=$(this).attr('code');
			if(confirm("确定删除吗？")){
				if (userId == ${sessionScope.userId}) {
					alert("用户不能删除自己！");
					return;
				}
				$.ajax({
					url			:"<%=contextPath%>/web/user/json/deleteById",
					type		:"POST",
					dataType	:"json",
					data		: {"user.id" : userId},
					success		:function(data){
							if(data == 1){
								alert("删除成功！");
								parent.frames[0].loadData('${pageBean.currentPage}');
							} else {
								alert("删除失败！");
							}
						},
					error		:function(){
							alert("删除失败！");
						}
					});
		    }
	    });
		//重置密码
		$("a[isResetPass]").click(function(){
			var userId=$(this).attr('code');
			if(confirm("确定重置密码？")){
				$.ajax({
					    url			:"<%=contextPath%>/web/user/json/resetPassword",
					    type		:"POST",
						dataType	:"json",
						data		: {"user.id" : userId},
							success		:function(data){
								if(data == 1){
									parent.frames[0].loadData('${pageBean.currentPage}');
									alert("重置密码成功，新密码为\"1\"！");
								} else {
									alert("重置密码失败！");
								}
							},
							error		:function(){
								alert("重置密码失败！");
							}
					});
				}
		});
		//查看用户信息
		$("a[isSelect]").click(function(){
			var userId=$(this).attr('code');
			parent.ShowIframe("查看-用户信息",
					"<%=contextPath%>/web/user/queryById?manuType=query&user.id="+userId,350,520);
		});
		//编辑
		$("a[isUpdate]").click(function(){
			var userId=$(this).attr('code');
			parent.ShowIframe("编辑-用户信息",
					"<%=contextPath%>/web/user/queryById?manuType=edit&user.id="+userId + "&company.id=" + currentCompanyId,350,520);
		});
		
		$("#checkAll").click(function(){
			if($(this).attr("checked")==true){
				$(":checkbox").attr("checked",true);
			}else{
				$(":checkbox").attr("checked",false);
			}
		});
	});
	//批量删除
	function delAll() {
			var str="";
			$(".CHECKBOX").each(function(){
				if($(this).attr("checked")==true){
					if ($(this).val() ==  ${sessionScope.userId}) {
						alert("用户不能删除自己！");
						return;
					}
					if($(this).val() == 1) {
						alert("超级管理员不可删除！");
						return;
					}
					str+=$(this).val()+";";
				}
			});
			
			if (str.length > 0) {
				if(confirm("确定删除吗？")){
					$.ajax({
						url			:"<%=contextPath%>/web/user/json/batchDelete?Ids="+str,
						type		:"POST",
						dataType	:"json",
						success		:function(data){
							if(data == 1){
								alert("删除成功！");
								parent.frames[0].loadData("${pageBean.currentPage}");
							} else {
								alert("删除失败！");
							}
						},
						error		:function(){
							alert("删除失败！");
							$(".CHECKBOX").each(function(){
								if($(this).attr("checked")==true){
									$(this).removeAttr("checked");
								}
							});
							$("#checkAll").removeAttr("checked");
						}
					});
				} else {
					$(".CHECKBOX").each(function(){
						if($(this).attr("checked")==true){
							$(this).removeAttr("checked");
						}
					});
					$("#checkAll").removeAttr("checked");
				}
			} else {
				alert("请先选择用户！");
			}
		}
	// 批量审核
	function checkStatusAll() {
			var str="";
			$(".CHECKBOX:checked").each(function(){
				str+=$(this).val()+";";
			});
			if (str.length > 0) {
				if(confirm("确定执行批量审核？")){
					$.ajax({
						url			:"<%=contextPath%>/web/user/json/batchCheck?Ids=" + str,
						type		:"POST",
						dataType	:"json",
						success		:function(data){
							if(data == 1){
								alert("批量审核成功！");
								parent.frames[0].loadData("${pageBean.currentPage}");
							} else {
								alert("批量审核失败！");
								$(".CHECKBOX:checked").each(function(){
									$(this).removeAttr("checked");
								});
								$("#checkAll").removeAttr("checked");
							}
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
				alert("请先选择用户！");
			}
		}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="tit3_background" style="height: 30px;">
			  		<%-- 分页开始 --%>
					<%@ include file="../../common/pageajax.jsp"%>
				    <%-- 分页结束 --%>
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="tableNoWrap gridtable">
						 <tr>
						 	<th width="30" class="nei_txt_menu">
							    <input type="checkbox" value="checkbox" id="checkAll" />
							</th>
						  	<th width="40" class="nei_txt_menu">序号</th>
						    <th width="80" class="nei_txt_menu">用户姓名</th>
						    <th width="80" class="nei_txt_menu">手机号</th>
						    <th width="80" class="nei_txt_menu">公司名称</th>
						    <th width="80" class="nei_txt_menu">所属机构</th>
						    <th width="80" class="nei_txt_menu">用户角色</th>
						    <th width="60" class="nei_txt_menu">状态</th>
						    <th width="200" class="nei_txt_menu" style="border-right: none;"  >操 作</th>
						  </tr>
						<c:forEach var="user" items="${pageBean.list}" varStatus="v">
						 <tr id="trWithCB" <c:if test="${v.count%2==0}">style="background-color:#EBEFF3;"</c:if>>
						 	<td class="noWrapTd" align="center">
						 		<input type="checkbox" value="${user.id }" class="CHECKBOX" />
						 	</td>
						 	<td class="noWrapTd" align="center">${(pageBean.currentPage-1) * pageBean.pageSize + v.count}</td>
						 	<td class="noWrapTd">${user.userName }</td>
						 	<td class="noWrapTd">${user.mobilePhone }</td>
						 	<td class="noWrapTd">${user.company.fullName}</td>
						 	<td class="noWrapTd">${user.organization.fullName}</td>
						 	<td class="noWrapTd">${user.role.name }</td>
						 	<td class="noWrapTd">${user.currentState==1?'已审核':'未审核' }</td>
						 	<td align="center">
						 	    <c:if test="${fn:contains(sessionScope.resourceIds,31)}">
						 		   <a href="#" isSelect="true" code='${user.id}'>查看</a>
						 		    &nbsp;|&nbsp;
						 		</c:if>
						 		<c:if test="${user.id != 1 }">
						 		    <c:if test="${fn:contains(sessionScope.resourceIds,33)}">
						 		       <a href="#" isUpdate="true" code='${user.id}'>编辑</a>
						 		        &nbsp;|&nbsp;
						 		    </c:if>
						 		    <c:if test="${fn:contains(sessionScope.resourceIds,34)}">
						 		       <a href="#" isDelete="true" code='${user.id}'>删除</a>
						 		       &nbsp;|&nbsp;
						 		    </c:if>
						 		    <c:if test="${fn:contains(sessionScope.resourceIds,39)}">
							 		   <a href="#" isResetPass="true" code='${user.id}'>密码重置</a>
						 		    </c:if>
						 		</c:if>
						 	</td>
						 </tr>
					    </c:forEach>
					</table>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>