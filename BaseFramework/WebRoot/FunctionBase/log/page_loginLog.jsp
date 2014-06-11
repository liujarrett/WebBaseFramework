<%@ page language="java" pageEncoding="UTF-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
    String localPath = request.getContextPath(); 
%>
<script type="text/javascript">
	//Ajax分页
	//分页方法,参数:转至第几页
	function getPageFromIndex(pageIndex){
		loadData(pageIndex);
	}
	
	$("#txtCurrentPage").keydown(function(event){
		//按回车执行跳转输入页面数字的页面
		event.stopPropagation();
		if(event.keyCode==13)
			$(this).trigger("blur");
	});
	
	$("#txtCurrentPage").blur(function(){
		//执行跳转输入页面数字的页面
		var inputValue=parseInt(jQuery.trim($("#txtCurrentPage").val()));
		//判断输入数字是否有效
		if(!isNaN(inputValue) && inputValue>0 && inputValue<=parseInt($("#hidMax").val()) && inputValue !="${loginLogPageBean.currentPage}")
			getPageFromIndex(inputValue);
		else
			$("#txtCurrentPage").val("${loginLogPageBean.currentPage}");
	});
</script>
<input type="hidden" id="hidMax" value="${loginLogPageBean.totalPage}"/>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
     <td height="5" colspan="9"></td>
   </tr>
   <tr>
     <c:choose>
         <c:when test="${loginLogPageBean.currentPage > 1}">
            <td width="22">
               <a href="javascript:void(0)" onclick="getPageFromIndex(1);">
                   <img src="<%=localPath %>/common/Images/l1.gif" title="第一页" />
	           </a>
	        </td>
	        <td width="22">
	           <a href="javascript:void(0)" onclick="getPageFromIndex(${loginLogPageBean.currentPage-1});">
	               <img src="<%=localPath %>/common/Images/l.gif" title="上一页" />
	           </a>
	        </td>
	    </c:when>
		<c:otherwise>
           <td width="22">
               <img src="<%=localPath %>/common/Images/l1h.gif" />
           </td>
	       <td width="22">
	           <img src="<%=localPath %>/common/Images/lh.gif" />
	       </td>
        </c:otherwise>
     </c:choose>
     <td width="70px">当前显示:</td>
     <td width="90px">
        <input type="text" id="txtCurrentPage"  size="5" value="${loginLogPageBean.currentPage}" style="text-align:center" />&nbsp;/&nbsp;${loginLogPageBean.totalPage }页
     </td>
     
     <c:choose>
        <c:when test="${loginLogPageBean.currentPage != loginLogPageBean.totalPage && 0 != loginLogPageBean.totalPage}">
           <td width="22">
              <a href="javascript:void(0)" onclick="getPageFromIndex(${loginLogPageBean.currentPage+1});">
                  <img src="<%=localPath %>/common/Images/r.gif" title="下一页" />
              </a>
           </td>
           <td width="22">
              <a href="javascript:void(0)" onclick="getPageFromIndex(${loginLogPageBean.totalPage})">
                  <img src="<%=localPath %>/common/Images/r1.gif" title="最后一页" />
              </a>
           </td>
	    </c:when>
        <c:otherwise>
          <td width="22">
	         <img src="<%=localPath %>/common/Images/rh.gif"  />
	      </td>
	      <td width="22">
	         <img src="<%=localPath %>/common/Images/r1h.gif"  />
	      </td>							
        </c:otherwise>
     </c:choose>
     <td align="right" style="padding-right:20px;">
                        当前数据显示:${loginLogPageBean.currentPage==0 ? 0 : (loginLogPageBean.currentPage-1) * loginLogPageBean.pageSize + 1} - ${(loginLogPageBean.currentPage) * loginLogPageBean.pageSize < loginLogPageBean.allRow ? (loginLogPageBean.currentPage) * loginLogPageBean.pageSize : loginLogPageBean.allRow}条&nbsp;&nbsp;共${loginLogPageBean.allRow }条
     </td>
   </tr>
 </table>
