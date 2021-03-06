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
		if(!isNaN(inputValue) && inputValue>0 && inputValue<=parseInt($("#hidMax").val()) && inputValue !="${pageBean.currentPage}")
			getPageFromIndex(inputValue);
		else
			$("#txtCurrentPage").val("${pageBean.currentPage}");
	});
</script>
<input type="hidden" id="hidMax" value="${pageBean.totalPage}"/>
<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
     <td height="5" colspan="9"></td>
   </tr>
   <tr>
     <c:choose>
         <c:when test="${pageBean.currentPage > 1}">
            <td width="22">
               <a href="javascript:void(0)" onclick="getPageFromIndex(1);">
                   <img src="<%=localPath %>/common/Images/l1.gif" title="第一页" />
	           </a>
	        </td>
	        <td width="22">
	           <a href="javascript:void(0)" onclick="getPageFromIndex(${pageBean.currentPage-1});">
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
     <td width="120px">
        <input type="text" id="txtCurrentPage"  size="5" value="${pageBean.currentPage}" style="text-align:center" />&nbsp;/&nbsp;${pageBean.totalPage }页
     </td>
     
     <c:choose>
        <c:when test="${pageBean.currentPage != pageBean.totalPage && 0 != pageBean.totalPage}">
           <td width="22">
              <a href="javascript:void(0)" onclick="getPageFromIndex(${pageBean.currentPage+1});">
                  <img src="<%=localPath %>/common/Images/r.gif" title="下一页" />
              </a>
           </td>
           <td width="22">
              <a href="javascript:void(0)" onclick="getPageFromIndex(${pageBean.totalPage})">
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
                        当前数据显示:${pageBean.currentPage==0 ? 0 : (pageBean.currentPage-1) * pageBean.pageSize + 1} - ${(pageBean.currentPage) * pageBean.pageSize < pageBean.allRow ? (pageBean.currentPage) * pageBean.pageSize : pageBean.allRow}条&nbsp;&nbsp;共${pageBean.allRow }条
     </td>
   </tr>
 </table>
