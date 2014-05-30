		function hideGridLoading(){
			$("#divAjax,#divAjaxBG").hide();
		}
		function showGridLoading(){
			var height = $(window).height() > $(document).height() ? $(window).height() : $(document).height();
			$("#divAjax,#divAjaxBG").width($(window).width()-10);
			$("#divAjaxBG").height(height);
			$("#divAjax").css("top",(height/2-50)+"px");
			$("#divAjax,#divAjaxBG").show();
		}
		function isOldIE(){
			var version=parseInt($.browser.version);
        	if($.browser.msie &&!isNaN(version) && version < 8 )
        		return true;
        	return false;
		}
		function initGridLoading(loadingImg){
			if($("#divAjaxBG").length==0)
				$(document.body).append("<div id='divAjaxBG' style='display: none; background-color: blue; position: absolute; z-index: 99990; filter: Alpha(opacity=10); opacity: 0.1; left: 0px; top: 0px;'>透明层</div><div id='divAjax' style='position: absolute;  z-index: 999990;display: none;text-align: center;'><img src='"+loadingImg+"'  /></div>");
		}
		function enterEvent(){
			/*********************回车查询事件********************/
			$(document.body).find(":text:not(#txtCurrentPage,[nfpTag])").bind("keydown",
				//绑定查询事件,在文本上按回车执行查询,查询按钮ID必须为btnSearch
				function (event){
				event.stopPropagation();
				if(event.keyCode==13 && $("#btnSearch").attr("id")){
					$("#btnSearch").trigger("click");
				}
			});
			$(document).unbind("keydown");
			$(document).bind("keydown",function(event){
				//防止失去焦点时按退格回到上一页
				if (event.keyCode == 8){
					event.preventDefault();
				}
			});
		}