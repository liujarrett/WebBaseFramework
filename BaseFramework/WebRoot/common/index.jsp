<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/style/index.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/style/adImg.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../style/indexAreaWindow.css" type="text/css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/goTop.js"></script>
<script type="text/javascript" src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
<title>我惠省首页</title>
<script type="text/javascript">
	//鼠标移过来的动作。显示当前图片，清除轮询。
	function adchangea(adid) {
		dochange(adid);
		clearTimeout(adisround);
	}
	//自动轮询
	function adchangea2(adid) {
		dochange(adid);
		var adbigimg = document.getElementById("adpica").getElementsByTagName(
				"div");
		if ((adnext = adid + 1) > adbigimg.length)
			adnext = 1;
		adisround = setTimeout('adchangea2(' + adnext + ')', 3000);
	}
	//显示当前图片
	function dochange(adid) {
		id = adid;
		var adbigimg = document.getElementById("adpica").getElementsByTagName(
				"div");
		if (adbigimg.length != 1) {
			var adsmallimg = document.getElementById("adtipa")
					.getElementsByTagName("li");
			for ( var adi = 0; adi < adbigimg.length; adi++) {
				adbigimg[adi].className = "hidden";
				adsmallimg[adi].className = "";
			}
			adbigimg[adid - 1].className = "show";
			adsmallimg[adid - 1].className = "current";
		}
	}
	var adisround = setTimeout("adchangea2(2)", 3000);
	var id;//当前激活id
	//鼠标移开ul块时，恢复轮询
	function change() {
		adchangea2(id);
	}
	//店铺详情
	function shop(id) {
		window.location.href = "${pageContext.request.contextPath}/client/shop.action?shop.id="
				+ id;
	}
	//店铺列表
	function shopList(id, classify) {
		if (classify == "all") {
			window.location.href = "${pageContext.request.contextPath}/client/shopList.action?shop.infoId.tid.id="
					+ id + "&classify=" + classify;
		} else if (classify == "sub") {
			window.location.href = "${pageContext.request.contextPath}/client/shopList.action?shop.infoId.id="
					+ id + "&classify=" + classify;
		} else {
			searchForm.action = "${pageContext.request.contextPath}/client/shopList.action?shop.id="
					+ id + "&classify=" + classify;
			searchForm.method = "POST";
			searchForm.submit();
		}
	}
	function init() {
		navlist.className = "hover";
		var url = "${pageContext.request.contextPath}/client/queryCityByName.action?cityName="+remote_ip_info.city;
		sendAjax(encodeURI(encodeURI(url)),checkNowCityCallBack);
	}
	
	var nowAreaName;
	var nowAreaId;
	var nowCityId;
	var nowCityName;
	
	function checkNowCityCallBack() {
		if (testxmlhttp.readyState == 4) {
			if (testxmlhttp.status == 200) {
				var nowcity = testxmlhttp.responseText;
				//当前所在的城市支持
				if (nowcity != -1) {
					nowcity = eval("("+decodeURI(nowcity)+")");
					//并且之前的cookie中 不存在  这个时候就把区域列表窗口展示
					nowAreaName =  nowcity.defaultAreaNameOfNowCity;
					nowAreaId =  nowcity.defaltAreaIdOfNowCity;
					nowCityId =  nowcity.NowCityId;
					nowCityName = nowcity.NowCityName;
					var oldCityId = getCookie("cityid"); 
					if(!oldCityId) {
						document.getElementById("comeintocity").innerHTML="您现在位于"+nowCityName+",点击进入<a href='javascript:comeintoNowCity();'style='text-decoration: none' class='nowcityhref'><font style='font-size: 16px;color:black;font-family: Microsoft YaHei;margin-left:10px;'>"+nowCityName+"</font></a>";
						$("#citywindow").css("display","block");
						$("#layer").css("display","block");
					}
				} 
			}
		}
	}
	var testxmlhttp;
	function sendAjax(actionurl,callback) {
		if (!testxmlhttp && window.XMLHttpRequest) {
			testxmlhttp = new XMLHttpRequest();
		} else if (!testxmlhttp && window.ActiveXObject) {
			testxmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		testxmlhttp.open("POST", actionurl, true);
		testxmlhttp.send(null);
		testxmlhttp.onreadystatechange = callback;
	}
function comeintoNowCity() {
	$("#citywindow").css("display","none");
	$("#layer").css("display","none");
	var path;
    //如果用户所在的城市支持     就查询用户所在的城市
    SetCookie("areaid",nowAreaId);
    SetCookie("areaname",nowAreaName);
    SetCookie("cityid",nowCityId);
    SetCookie("cityname",nowCityName);
    path = "${pageContext.request.contextPath}/client/queryIndex.action";
    window.location.href=path;
}
function comeintoarea(areaid,areaname,cityid,cityname) {
	SetCookie("areaid",areaid);
	SetCookie("areaname",areaname);
	SetCookie("cityid",cityid);
	SetCookie("cityname",cityname);
	$("#citywindow").css("display","none");
	$("#layer").css("display","none");
	window.location.href = "${pageContext.request.contextPath}/client/queryIndex.action";
}
//如果用户选择了 关闭区域列表窗口 那么 如果原来有cookie 则什么也不做  否则存默认的绥化
function comeIntoOldCity() {
	$("#citywindow").css("display","none");
	$("#layer").css("display","none");
	var areaname = getCookie("areaname");
	if(!areaname) {
		//如果当前用户所在的城市不支持  就默认查询绥化市  北林区  
    	SetCookie("areaid",2);
    	SetCookie("areaname","北林区");
    	SetCookie("cityid","1");
    	SetCookie("cityname","绥化市");
	}
}
/*
功能：保存cookies函数
参数：name，cookie名字；value，值
 */
function SetCookie(name, value) {
	var Days = 60; //cookie 将被保存两个月
	var exp = new Date(); //获得当前时间
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000); //换成毫秒
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
/*
功能：获取cookies函数
参数：name，cookie名字
 */
function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name
			+ "=([^;]*)(;|$)"));
	if (arr != null) {
		return unescape(arr[2]);
	} else {
		return null;
	}
}
</script>
</head>
<body onload="init()">
	<div id="layer" style="display: none;height: 100%; width: 100%; position: fixed; left: 0px; top: 0px; background-color: rgb(0, 0, 0); z-index: 9999; filter:alpha(opacity=50);-moz-opacity:0.5; opacity:0.5;"></div>
	<div id="citywindow" style="display: none; z-index: 9999; position: fixed; left: 50%; top: 50%; margin-top: -181.5px; margin-left: -367.5px;width: 100%;height: 100%;">
		<div style="width: 600px;height: 400px;left:30%;top:175px;background:none repeat scroll 0 0 #FFFFFF;z-index: 999999;border:solid 2px #f7dd8c;position:fixed;">
			<div style="width: 60%;margin:5px 0;background: #eeeeee;margin-left: 10px;margin-right: 10px;height: 25px;margin-top: 8px;padding-top: 10px;border:solid 1px #f7dd8c;"
				 onclick="comeintoNowCity();">
				<font style="font-size: 16px;color:black;padding-left: 10px;font-family: Microsoft YaHei" id="comeintocity"></font>
			</div>
			<div style="position: absolute;left: 90%;top:3%; z-index: 1000000;border:solid 1px #f7dd8c;">
				<input type="button" onclick="comeIntoOldCity();" value="关闭" />
			</div>
			<div style="overflow:auto;width: 100%;height: 350px;">
				<s:iterator value="#request.cities" id="city">
					<div style="margin-top: 5px; clear: both;">
						<div style="width: 100px;margin-left: 10px;height: auto; margin-top: 10px;background-color:#FF7000;border:solid 1px #f7dd8c;text-align: center;">
							<font style="font-size: 16px;color:#ffffff;font-family: Microsoft YaHei">${city.cityName}</font>
						</div>
						<div style="width: 100%; float: left;">
							<c:forEach items="${city.areas}" var="area">
								<div style="float:left; width:75px;margin-left: 25px;margin-right: 15px;margin-top: 5px;margin-bottom: 5px;">
									<a href="javascript:comeintoarea('${area.id}','${area.areaName}','${city.id}','${city.cityName}');" style="text-decoration: none" class='areahref'>
									   <font style="font-size:15px;color:gray;text-align: center; font-family: Microsoft YaHei">${area.areaName}</font>
									</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</s:iterator>
			</div>
		</div>
	</div>
	<s:action name="queryArea" executeResult="true"></s:action>
	<jsp:include page="top.jsp" />
	<div id="indexmain" style="height: auto;">
		<div class="indexmain-wt" style="min-height: 494px;">
			<div id="indexleft" style="height:494px;overflow: hidden;">
				<!-- 这里是迭代展示主分类以及子分类  开始 -->
				<s:iterator value="#request.tradeList" id="item" status="st">
					<div id="indexmedia1">
						<div id="mediatitle">
							<font class="font06">·</font> 
							<a href="javascript:void(0);" onclick="javascript:shopList('${item.id}','all')" class="dd3">
								<span id="trade${item.id}"></span> 
							</a>
							<script type="text/javascript">
								document.getElementById("trade${item.id}").innerHTML = decodeURIComponent("${item.name}");
							</script>
							<font class="font14">(${item.shopcounts})</font>
						</div>
						<div id="mediatext">
							<c:forEach items="${item.tradeInfo}" var="t" varStatus="st">
								<c:if test="${st.count<=4}">
									<c:if test="${st.count==1||st.count==3}">
										<a href="javascript:void(0);" onclick="javascript:shopList('${t.id}','sub')" class="dd2">
											<span id="jh${t.id}"></span> 
										</a>&nbsp;
										<script type="text/javascript">
											document.getElementById("jh${t.id}").innerHTML = decodeURIComponent("${t.name}");
										</script>
									</c:if>
									<c:if test="${st.count==2}">
										<a href="javascript:void(0);" onclick="javascript:shopList('${t.id}','sub')" class="dd2">
											<span id="jh${t.id}"></span>
									    </a>
										<br />
										<script type="text/javascript">
											document.getElementById("jh${t.id}").innerHTML = decodeURIComponent("${t.name}");
										</script>
									</c:if>
									<a href="javascript:void(0);" onclick="javascript:shopList('${t.id}','sub')" class="dd2">
										<span id="jh${t.id}"></span> 
									</a>
									<script type="text/javascript">
										document.getElementById("jh${t.id}").innerHTML = decodeURIComponent("${t.name}");
									</script>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</s:iterator>
				<!-- 这里是迭代展示主分类以及子分类  结束 -->
			</div>
			<div id="bannerbox">
				<!-- 广告图片展示区域 -->
				<div id="bannerbox1">
					<c:if test="${!empty request.adImgList}">
						<div class="turn">
							<div id="adpica">
								<c:forEach items="${request.adImgList}" var="list" varStatus="st">
									<div class="show">
										<a href="javascript:shop('${list.sid}')"> 
										  <img width="686" height="482" src="${pageContext.request.contextPath}${list.img}" /> 
										</a>
									</div>
								</c:forEach>
							</div>
							<div id="adtipa">
								<ul onmouseout="change()">
									<c:forEach items="${request.adImgList}" var="list" varStatus="st">
										<fmt:formatNumber var="c" value="${st.count}" pattern="#" />
										<li class="current" onmouseover="adchangea('${c}')">${c}</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${empty request.adImgList}">
						<div class="turn">
							<div id="adpica">
								<div class="show">
									<img width="686" height="482" src="${pageContext.request.contextPath}/image/client/ad_img.jpg" />
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
		<!-- //这里是迭代展示每一个分类的店铺等信息  开始-->
		<s:iterator value="#request.tradeList" id="column">
			<div id="production">
				<div id="productiontitle2">
					<div id="productiontitle">
						<!--  
						<div id="productionf">
							<font class="font06">
							   <s:property value="#column.id" /> 
							</font>
						</div>
						-->
						<div id="productionf1" style="margin-left: 55px;">
							<font class="font07"> 
							    <span id="tradeFloor${column.id}"></span>
								<script type="text/javascript">
									document.getElementById("tradeFloor${column.id}").innerHTML = decodeURIComponent("${column.name}");
								</script>
						    </font>
						</div>
					</div>
				</div>
				<div class="insex-lirenhj">
					<div id="productlist">
						<div id="productlist1">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<s:iterator value="#column.tradeInfo" status="st" id="t">
									<c:if test="${st.count<=10}">
										<tr>
											<td width="13%" height="25"
												background="${pageContext.request.contextPath}/image/client/line.png">&nbsp;</td>
											<td width="15%" height="25"
												background="${pageContext.request.contextPath}/image/client/line.png">
												<img src="${pageContext.request.contextPath}/image/client/dian.png" width="5" height="5" /></td>
											<td width="72%" height="25"
												background="${pageContext.request.contextPath}/image/client/line.png">
												<a href="javascript:void(0);" onclick="javascript:shopList('${t.id}','sub')" class="dd2">
													<span id="floor${t.id}"></span> </a> 
													<script type="text/javascript">
														document.getElementById("floor${t.id}").innerHTML = decodeURIComponent("${t.name}");
													</script>
											</td>
										</tr>
									</c:if>
								</s:iterator>
							</table>
						</div>
					</div>
					<div id="productright">
						<s:iterator value="#column.productAd" id="p">
							<c:if test="${p.sid.aid.id==cookie.areaid.value || p.scope==0 }">
								<div id="productionimg1">
									<div id="productionimg2" style="position:relative;">
										<div style="position:absolute;z-index:0;">
											<a href="javascript:shop('${p.sid.id}')" title="${p.sid.companyNameDecode}">
											<img src="${pageContext.request.contextPath}${p.img}" width="172" height="120" /> </a>
										</div>
										<div style="position:absolute;z-index:10;top:100px;left:0px;width:162px;height:20px; padding-left:10px;line-height:20px;background-color:#000; filter:alpha(opacity=50);-moz-opacity:0.5; opacity:0.5;">
											<a href="javascript:shop('${p.sid.id}')" class="dd5">${p.sid.companyNameDecode}</a>
										</div>
									</div>
								</div>
							</c:if>
						</s:iterator>
					</div>
				</div>
			</div>
		</s:iterator>
		<!-- //这里是迭代展示每一个分类的店铺等信息    结束-->
	</div>
	<!-- //背景结束 -->
	<jsp:include page="foot.jsp" />
</body>
</html>