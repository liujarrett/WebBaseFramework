<%@ page language="java" pageEncoding="utf-8" import="java.util.*"%>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style>
html{overflow:hidden;}
</style>
<TITLE>主页面</TITLE>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.js"></script>
</head>
<body style="margin: 0px; padding:0px;">
<script>
	$(function(){
		//窗体改变大小时重新设置控件坐标
		$(window).resize(function() {
			display(false);
		});
		$("#imgCenter").attr("src","<%=contextPath%>/common/Images/hy.jpg");
		display(true);
	});
	function display(isInit){
		var topPx =($(window).height()-200)/2;
		if(topPx<60)
			topPx=60;
		if(isInit)
			$("#imgCenter").animate({
			   top: topPx,
			   left: ($(window).width()-700)/2,
			   opacity: 'show'
			 }, 500);
		else
			$("#imgCenter").animate({
			   top: topPx,
			   left: ($(window).width()-700)/2
			 }, 500);
		if($(window).width()>900)
			$("#divMainTop").width($(window).width());
		else 
			$("#divMainTop").width(900);
	}
</script>
	<%@ include file="../../common/pagehead.jsp"%>
	<img style="display: none; position: absolute;z-index:30;" id="imgCenter"/>
</body>
</HTML>
