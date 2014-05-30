/*
 * 中科方德select左右框选择控件(需依赖于JQUERY,引用此js前需先添加JQUERY引用)
 * 阎鑫
 * 2011-4-27
 * 		
 */
/************************************左右框选择控件 开始***********************************/
$.fn.nfpSelectBox = function (opt) {
    return this.each(function () {
        var BOX = this; 	   //本容器
        var interv = null;	   //滚动循环事件
        var myPressed = false; //鼠标按下
        BOX.option = {
            /***********可配置内容(可扩展)************/
            width:600,
            height:400,
            topHeight:32,
            centerWidth:32,
            sizeAuto:true,
            leftTitle:"&nbsp;",
            rightTitle:"&nbsp;",
            /**********事件*************/
            afterGoRight: false,
            afterGoLeft: false,
            afterAllRight: false,
            afterAllLeft: false
            /*********可扩展自定义属性或方法********/
        };
        BOX.option = $.extend(BOX.option, opt);

        //设置事件
        BOX.setSelectEvent = function (select) {
            select.dblclick(function (event) {
                //双击事件
                myPressed = false;
                if(this.id=="boxRight")
                	$("#btnGoLeft").trigger("click");
                if(this.id=="boxLeft")
                	$("#btnGoRight").trigger("click");
                return false;
            }).mousedown(function (event) {
                if (event.which != 1)
                    return false;
                //鼠标按下事件,执行滑动选中
                myPressed = true;
                $(document).bind("mousemove",{ SELECT: $(this) }, BOX.dragStart).bind("mouseup", BOX.dragEnd);
            });
        };
        BOX.clearScroll = function () {
            window.clearInterval(interv);
            interv = null;
        }
        
        //停止拖拽事件,清理临时参数
        BOX.dragEnd = function () {
            BOX.clearScroll();
            $(document).unbind('mousemove', BOX.dragStart).unbind('mouseup',BOX.dragEnd);
        	myPressed = false;
        };

        //开始拖拽
        BOX.dragStart = function (event) {
            if (myPressed) {
            	var SELECT = $(event.data.SELECT);
            	var BASE=SELECT.parent();
                if (event.pageY > BASE.height() + SELECT.offset().top + BASE.scrollTop()) {
                    if (interv == null)
                    //向下滚动
                        interv = window.setInterval(function () { BASE.scrollTop(BASE.scrollTop() + BOX.scrollSize()); }, 10);
                } else if (event.pageY < SELECT.offset().top + BASE.scrollTop()) {
                    if (interv == null)
                    //向下滚动
                        interv = window.setInterval(function () { BASE.scrollTop(BASE.scrollTop() - BOX.scrollSize()); }, 10);
                } else {
                    BOX.clearScroll();
                }
            }
            return true;
        };
        BOX.scrollSize=function(){
        	if($.browser.msie){
				return 10;
			}else{
				return 4;
			}
        };

        //控件初始化
        BOX.init = function () {
        	var boxWidth=(BOX.option.width-BOX.option.centerWidth)/2;
        	var boxHeight=BOX.option.height-BOX.option.topHeight;
        	$(BOX).append('<table border="0" cellpadding="0" cellspacing="0" width="'+BOX.option.width+'" height="'+BOX.option.height+'"><tr><td width="'+boxWidth+'" class="boxTd"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="topTd" height='+BOX.option.topHeight+'>'+BOX.option.leftTitle+'</td></tr><tr><td><div style="height:'+boxHeight+'px;width:'+boxWidth+'px;"><select id="boxLeft" style="border:none;height:'+boxHeight+'px;width:'+boxWidth+'px;" multiple="multiple"></select></div></td></tr></table></td><td class="centerTd" width="'+BOX.option.centerWidth+'"><table width="100%" border="0" cellspacing="0" cellpadding="0"><tr><td><input type="button" id="btnGoRight"/></td></tr><tr><td><input type="button" id="btnGoLeft"/></td></tr><tr><td><input type="button" id="btnAllRight"/></td></tr><tr><td><input type="button" id="btnAllLeft"/></td></tr></table></td><td width="'+boxWidth+'" class="boxTd"><table border="0" cellpadding="0" cellspacing="0" width="100%"><tr><td class="topTd" height="'+BOX.option.topHeight+'">'+BOX.option.rightTitle+'</td></tr><tr><td><div style="height:'+boxHeight+'px;width:'+boxWidth+'px;"><select id="boxRight" style="border:none;height:'+boxHeight+'px;width:'+boxWidth+'px;" multiple="multiple"></select></div></td></tr></table></td></tr></table>');
            BOX.setSelectEvent($('#boxLeft,#boxRight'));
            if(BOX.option.sizeAuto){
            	$('#boxLeft,#boxRight').attr("sizeAuto","1");
            }
            //button初始化
            $("#btnGoRight").click(function(){
            	$("#boxRight,#boxLeft").hide();
            	$('#boxLeft >option:selected').appendTo('#boxRight');
            	boxAutoSize($("#boxLeft"),false,$("#boxRight"));
            	$("#boxRight,#boxLeft").show();
            	if (typeof (BOX.option.afterGoRight) == 'function') {
	                BOX.option.afterGoRight();
	            }
            });
            $("#btnGoLeft").click(function(){
            	$("#boxRight,#boxLeft").hide();
            	$('#boxRight >option:selected').appendTo('#boxLeft');
            	boxAutoSize($("#boxLeft"),false,$("#boxRight"));
            	$("#boxRight,#boxLeft").show();
            	if (typeof (BOX.option.afterGoLeft) == 'function') {
	                BOX.option.afterGoLeft();
	            }
            });
            $("#btnAllRight").click(function(){
            	$("#boxRight,#boxLeft").hide();
            	$('#boxLeft >option').appendTo('#boxRight');
            	boxAutoSize($("#boxLeft"),false,$("#boxRight"));
            	$("#boxRight,#boxLeft").show();
            	if (typeof (BOX.option.afterAllRight) == 'function') {
	                BOX.option.afterAllRight();
	            }
            });
            $("#btnAllLeft").click(function(){
            	$("#boxRight,#boxLeft").hide();
            	$('#boxRight >option').appendTo('#boxLeft');
            	boxAutoSize($("#boxLeft"),false,$("#boxRight"));
            	$("#boxRight,#boxLeft").show();
            	if (typeof (BOX.option.afterAllLeft) == 'function') {
	                BOX.option.afterAllLeft();
	            }
            });
        };
        BOX.init();
    });
}
//方法：左侧绑定数据
//source :数据源
//clearOld:true：清除原有数据
function leftBoxBind(source,clearOld){
	boxBind("left",source,clearOld);
}
//方法：右侧绑定数据
//source :数据源
//clearOld:true：清除原有数据
function rightBoxBind(source,clearOld){
	boxBind("right",source,clearOld);
}

//方法：绑定数据
//tag: left or right
//source :数据源
//clearOld:true：清除原有数据
function boxBind(tag,source,clearOld){
	var boxControl=null;
	if(tag=="left"){
		//绑定左侧
		boxControl=$('#boxLeft');
	}else if(tag=="right"){
		//绑定右侧
		boxControl=$('#boxRight');
	}else{
		return;
	}
	boxControl.hide(); 
	if(clearOld){
		//清除原始数据
		boxControl.html("");
	}
	//判断数据源是否为空
	if(!source || source==null || source.length==0){
		boxControl.css({height:boxControl.parent().height()+"px",width:boxControl.parent().width()+"px"});
		boxControl.show();
		return;
	}
	var maxLength=0;
	//加载数据
	for(var i=0;i<source.length;i++){
		boxControl.append("<option value='"+source[i][0]+"'>"+source[i][1]+"</option>");
		if(source[i][1].length>maxLength)
			maxLength=source[i][1].length;
	}
	//自动适应宽高
	boxAutoSize(boxControl,maxLength);
	boxControl.show();
}
//方法：select控件宽高自动适应
//boxControl: select控件(jquery)
//maxLength:最长option长度
//otherControl:其他控件
function boxAutoSize(boxControl,maxLength,otherControl){
	var parentHeight=boxControl.parent().height();// 获取父控件的宽高
	var parentWidth=boxControl.parent().width();
	if(!maxLength){
		//若未传入最长Option长度参数则重新计算
		var options=boxControl.children("option");
		maxLength=0;
		options.each(function(){
			if($(this).html().length>maxLength)
				maxLength=$(this).html().length;
		});
	}
	var otherMaxLength=0;
	//设置原始值
	boxControl.css({height:parentHeight+"px",width:parentWidth+"px"});
	boxControl.parent().css({"overflow-x":"auto","overflow-y":"auto"});
	if(otherControl){
		//设置原始值
		otherControl.css({height:parentHeight+"px",width:parentWidth+"px"});
		otherControl.parent().css({"overflow-x":"auto","overflow-y":"auto"});
		//若未传入最长Option长度参数则重新计算
		var options=otherControl.children("option");
		options.each(function(){
			if($(this).html().length>otherMaxLength)
				otherMaxLength=$(this).html().length;
		});
	}
	var count=boxControl.children("option").size();
	var fontSize=0;
	var rowSize=0;
	//row Size 12px:FireFox:17px,IE:14px,Google:15px
	//row Size 14px:FireFox:20px,IE:17px,Google:20px
	//FireFox:optionSize
	//other:selectSize
	//根据浏览器不同获取适应宽高
	if($.browser.msie){
		fontSize=parseInt(boxControl.css("font-size"));
		rowSize=fontSize==12 ? 14 : fontSize + 3;
	}else if($.browser.mozilla){
		if(count>0)
			fontSize=parseInt(boxControl.children("option:first").css("font-size"));
		else
			fontSize=parseInt(boxControl.css("font-size"));
		rowSize=fontSize==12 ? 17 : fontSize + 6;
	}else{
		fontSize=parseInt(boxControl.css("font-size"));
		rowSize=fontSize==12 ? 15 : fontSize + 4;
	}
	if(count>0){
		//自动适应
		if(rowSize*count+10 > parentHeight){
			boxControl.height(rowSize*count+10);
			if($.browser.msie)
				boxControl.parent().css("overflow-y","scroll");
			//自动适应X轴滚动条
			if(fontSize*maxLength<parentWidth-20)
				boxControl.parent().css("overflow-x","hidden");
		}
		if(fontSize*maxLength+fontSize*2>parentWidth){
			boxControl.css("width","auto");	
			if($.browser.msie)
				boxControl.parent().css("overflow-x","scroll");
			//自动适应Y轴滚动条
			if(rowSize*count < parentHeight-20)
				boxControl.parent().css("overflow-y","hidden");
		}
	}
	if(otherMaxLength>0){
		var otherCount=otherControl.children("option").size();
		//自动适应
		if(rowSize*otherCount+10 > parentHeight){
			otherControl.height(rowSize*otherCount+10);
			if($.browser.msie)
				otherControl.parent().css("overflow-y","scroll");
			//自动适应X轴滚动条
			if(fontSize*otherMaxLength<parentWidth-20)
				otherControl.parent().css("overflow-x","hidden");
		}
		if(fontSize*otherMaxLength+fontSize*2>parentWidth){
			otherControl.css("width","auto");
			if($.browser.msie)
				otherControl.parent().css("overflow-x","scroll");
			//自动适应Y轴滚动条
			if(rowSize*otherCount < parentHeight-20)
				otherControl.parent().css("overflow-y","hidden");
		}
	}
}
