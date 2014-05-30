/*
 * 中科方德树形控件,树形下拉控件(需依赖于JQUERY,引用此js前需先添加JQUERY引用)
 * 康齐(构建基于simple tree)(无限级)
 * 2011-3-30
 * 功能简述:
 * 		支持拖拽,缓存,复选框,单选,控制默认展开层级,全部展开,收起,右键菜单,自定义图标,自定义方法,可扩展
 * 		
 */
var nfpTreeCacheParams=new Array(new Array(),new Array());//缓存参数值数组(启用缓存才会使用此功能)
var nfpTreeCacheSource=new Array(new Array(),new Array());//缓存数据数组(启用缓存才会使用此功能)
var nfpTreeCacheUrl=new Array();//缓存地址数组(启用缓存才会使用此功能)
var nfpCurrentTree=new Array();	//已加载树形控件ID集合(启用下拉树使用,防止重复加载)
/************************************树形控件 开始***********************************/
$.fn.nfpTree = function (opt) {
    return this.each(function () {
    	if(!this.id)
    		return;
    	//启用下拉树使用,防止重复加载
    	if(opt.isCommTree){
    		if($.inArray(this.id,nfpCurrentTree)==-1)
	    		nfpCurrentTree[nfpCurrentTree.length]=this.id;
	    	else
	    		return;
    	}
        var TREE = this;		//本树形控件
        var isFirstNode = true; //是否第一个节点(用于加载样式)
        var mousePressed = false;//鼠标按下
        var mouseMoved = false;  //鼠标移动
        var dragParent=false;	 //拖拽节点父节点ID
        var dragNode_source = false;//拖拽节点数据
        var dragDropTimer = false; //拖拽timer事件

        TREE.option = {
            /***********可配置内容(可扩展)************/
            ajaxUrl: false, //ajax请求路径		(必填)
            themeUrl:'',	//themes文件夹路径	(必填)
            paramsName: false, //参数名称		(必填)

            animate: true,  //展开是否存在动画效果
            autoclose: false,  //展开节点时是否关闭同一父节点下其他节点
            checkbox: false,    //是否添加checkbox
			clickStyle:true,//是否单击选中改为active样式
			docToFolderConvert: true,//拖拽时是否允许往不存在子级节点拖拽
			drag: false,  //是否允许拖拽
			expandLevel: 1,	//默认展开行数
			inPanel:false, //是否存在easyui的panel控件中,解决显示串行BUG
            isCommTree:false, //设置树类型(commTree右键和拖拽后不执行选中高亮显示)
			speed: 200, //展开关闭时的速度('slow'|'normal'|'fast' or 毫秒数字)
			nodesImage:false,//节点图标(位于文本前,数组类型,填写图片对应地址,节点level值与数组进行匹配,当节点level超出数组长度,默认加载数组最后的图标)
			useCache:false,	//使用缓存
            
			/**********事件*************/
            afterAjax: false, //ajax读取完数据后执行方法
            afterMove: false, //拖拽完后执行的方法
            afterClick: false, //单击完执行的方法
            afterDblClick: false, //双击完执行的方法
            afterContextMenu: false, //节点上鼠标右键后执行的方法
            afterExtendAll:false//全部展开完成后执行的方法
            /*********可扩展自定义属性或方法********/
        };
        TREE.option = $.extend(TREE.option, opt);
        //关闭节点
        TREE.closeNearby = function (obj) {
            $(obj).siblings().filter('.folder-open, .folder-open-last').each(function () {
                var childUl = $('>ul', this);
                var className = this.className;
                this.className = className.replace('open', 'close');
                if (TREE.option.animate) {
                	//特效关闭
                    childUl.animate({ height: "toggle" }, TREE.option.speed);
                } else {
                	//隐藏
                    childUl.hide();
                }
            });
        };
        //节点展开或收起
        TREE.nodeToggle = function (obj) {
            var childUl = $('>ul', obj);
            if (childUl.is(':visible')) {
            	//隐藏
                obj.className = obj.className.replace('open', 'close');
                if (TREE.option.animate) {
                    childUl.animate({ height: "toggle" }, TREE.option.speed);
                } else {
                    childUl.hide();
                }
            } else {
            	//显示
                obj.className = obj.className.replace('close', 'open');
                //显示时需判断是否存在ajax节点,存在则读取
                if (TREE.option.animate) {
                    childUl.animate({ height: "toggle" }, TREE.option.speed, function () {
                        if (TREE.option.autoclose) TREE.closeNearby(obj);
                        if (childUl.is('.ajax')) TREE.setAjaxNodes(childUl);
                    });
                } else {
                    childUl.show();
                    if (TREE.option.autoclose) TREE.closeNearby(obj);
                    if (childUl.is('.ajax')) TREE.setAjaxNodes(childUl);
                }
            }
        };
        //设置ajax节点源
        TREE.setAjaxNodes = function (node, callback,isInit) {
            var paramValue = isInit ? '0':$(":hidden",node).val();
            if (paramValue && paramValue!=null) {
            	node.html(isInit? "" : "<ul></ul>");
            	var listSource=null;
            	//使用缓存
            	if(TREE.option.useCache){
            		//判断缓存中是否存在该数据,不存在则添加至缓存中
            		var urlIndex=$.inArray(TREE.option.ajaxUrl,nfpTreeCacheUrl);
            		if(urlIndex==-1){
            			nfpTreeCacheUrl[nfpTreeCacheUrl.length]=TREE.option.ajaxUrl;
            		}else{
            			var paramIndex=$.inArray(paramValue,nfpTreeCacheParams[urlIndex]);
            			if(paramIndex>-1){
            				listSource=nfpTreeCacheSource[urlIndex][paramIndex];
            			}
            		}
                }
            	//若缓存中不存在,则使用ajax加载
            	if(listSource==null){
                	$.ajax({
	                    async: false,
	                    url: TREE.option.ajaxUrl,
	                    data: eval("({" + TREE.option.paramsName + ":"+paramValue+"})"),
	                    type: "GET",
	                    cache:false,
	                    dataType: "json",
	                    success: function (data) {
                			//若启用缓存 ,则添加至缓存中
	                		if(TREE.option.useCache){
	                			var urlIndex=$.inArray(TREE.option.ajaxUrl,nfpTreeCacheUrl);
	                			var sourceIndex=nfpTreeCacheParams[urlIndex].length;
	                			nfpTreeCacheParams[urlIndex][sourceIndex]=paramValue;
	                			nfpTreeCacheSource[urlIndex][sourceIndex]=data;
	                		}
	                		listSource=data;
	                    },
	                    error: function () {
	                        alert("加载失败,请确保您的网络畅通!");
	                    }
	                });
                }
            	if(listSource!=null){
            		//加载节点样式
            		node.removeAttr('class');
            		if(isInit){
		            	$(TREE).attr({"class":"nfpTree","level":"0"});
		            }
            		//加载内容
                	node.html(TREE.convertListToNfpTree(listSource,isInit ? 0 : node.parent().attr("level")));
	                TREE.setTreeNodes(node);
	                //判断属性菜单是否存在初始值,存在则附加样式
	                if(TREE.option.clickStyle && !TREE.option.checkbox && $(TREE).attr("initValue")){
						var initSelected = $("#"+TREE.id+$(TREE).attr("initValue"),node);
						if(initSelected.size()==1){
							initSelected.find("span.text:first").attr("class","active");
							$(TREE).removeAttr("initValue");
						}
	                }
	                //判断是否已设置afterAjax方法
	                if (typeof TREE.option.afterAjax == 'function') {
	                    TREE.option.afterAjax(node);
	                }
	                if (typeof callback == 'function') {
	                    callback(node);
	                }
	                //全部展开,当全部展开完成后调用事件
	                if($(TREE).attr("openAll") && $(TREE).find("ul.ajax").size()==0){
                    	$(TREE).removeAttr("openAll");
						if (typeof TREE.option.afterExtendAll == 'function') {
	                    	TREE.option.afterExtendAll();
	                    }
            		}
            	}
            }
        };
        //设置节点样式
        TREE.setTreeNodes = function (obj) {
        	//添加样式
            $('li>span', obj).addClass('text')
			.bind('selectstart', function () {
			    return false;
			}).click(function () {
				if(TREE.option.clickStyle && !TREE.option.checkbox){
					//单选,添加单击事件,判断是否存在初始值
					$('.active', TREE).attr('class', 'text');
				    if (this.className == 'text') {
				        this.className = 'active';
				    }
				    $(TREE).removeAttr("initValue");
				}else{
					//多选,添加单击事件,判断是否存在初始值
		    		var checkboxObj=$(this).find(":checkbox[tag='nfpTree']:first");
		        	if(checkboxObj.attr("checked"))
		        		checkboxObj.removeAttr("checked");
		        	else
		            	checkboxObj.attr("checked",'true');
		    	}
				//单击后执行事件
			    if (typeof TREE.option.afterClick == 'function') {
			        TREE.option.afterClick($(this).parent());
			        return false;
			    }
			    return true;
			}).dblclick(function () {
				//双击事件
			    mousePressed = false;
			    TREE.nodeToggle($(this).parent().get(0));
			    //双击后执行事件
			    if (typeof TREE.option.afterDblClick == 'function') {
			        TREE.option.afterDblClick($(this).parent());
			        return false;
			    }
			    return true;
			}).bind("contextmenu", function (event) {
				//右键事件(可用于右键菜单)
				if(TREE.option.clickStyle && !TREE.option.checkbox && !TREE.option.isCommTree){
					$('.active', TREE).attr('class', 'text');
				    if (this.className == 'text') {
				        this.className = 'active';
				    }
				}
				//右键后执行事件
			    if (typeof TREE.option.afterContextMenu == 'function') {
			        TREE.option.afterContextMenu($(this).parent(), event);
			        return false;
			    }
			    return true;
			}).mousedown(function (event) {
				//鼠标按下事件,执行拖拽
			    mousePressed = true;
			    if (TREE.option.drag) {
			    	//保存拖拽前节点,跳出浮动拖拽提示层
			    	cloneNode = $(this).parent().clone();
			    	var LI = $(this).parent();
			    	dragParent=LI.parents("[id][level]:first").attr("id");
			        $('>ul', cloneNode).hide();
			        $('body').append('<div id="drag_container"><ul></ul></div>');
			        $('#drag_container').hide().css({ opacity: '0.8' });
			        $('#drag_container >ul').append(cloneNode);
			        //添加拖拽提示图标
			        $("<img>").attr({ id: "tree_plus", src: TREE.option.themeUrl+"/images/plus.gif" }).css({ position: "absolute", left: "5px", top: "5px", display: 'none','z-index':99999 }).appendTo("body");
			        $(document).bind("mousemove", { LI: LI }, TREE.dragStart).bind("mouseup", TREE.dragEnd);
			    }
			    return false;
			}).mouseup(function () {
				//鼠标松开事件,完成拖拽
			    if (mousePressed && mouseMoved && dragNode_source) {
			        TREE.moveNodeToFolder($(this).parent());
			    }
			    TREE.eventDestroy();
			});
            $(":checkbox[tag='nfpTree']",obj).click(function(event){
            	//复选框单击事件
            	event.stopPropagation();
            	if (typeof TREE.option.afterClick == 'function') {
			        TREE.option.afterClick($(this).parent());
			    }
            });
            $('li', obj).each(function (i) {
            	//格式化node样式
                var className = this.className;
                var open = false;
                var cloneNode = false;
                var LI = this;
                var childNode = $('>ul', this);
                //添加线
                $(this).before('<li class="line'+(isFirstNode? '-first':'')+'">&nbsp;</li>');
                if (childNode.size() > 0) {
                	//存在子级节点,添加样式
                    var setClassName = 'folder-';
                    if (className && className.indexOf('open') >= 0) {
                        setClassName = setClassName + 'open';
                        open = true;
                    } else {
                        setClassName = setClassName + 'close';
                    }
                    //判断是否为第一个节点(修改样式)
                    if (isFirstNode) {
                        this.className = setClassName + ($(this).is(':last-child') ? '-last-first' : '-first');
                        isFirstNode = false;
                    }
                    else
                        this.className = setClassName + ($(this).is(':last-child') ? '-last' : '');
                    //设置展开图片
                    TREE.setTrigger(this);
                    if (!open || className.indexOf('ajax') >= 0 ) {
                    	childNode.hide();
                    	//判断默认展开节点层级以及是否全部展开而展开节点
                    	if((!isNaN(TREE.option.expandLevel)&& TREE.option.expandLevel > (parseInt($(this).attr("level")))) ||$(TREE).attr("openAll") || $(TREE).attr("initValue"))
                    	{
                    		$(">img",this).trigger("click");
                    	}	
                    }
                } else {
                	//不存在子节点
                    var setClassName = 'doc';
                    if (isFirstNode) {
                        this.className = setClassName + ($(this).is(':last-child') ? '-last-first' : '-first');
                        isFirstNode = false;
                    }
                    else
                        this.className = setClassName + ($(this).is(':last-child') ? '-last' : '');
                }
            })
			.filter(':last-child').after('<li class="line-last"></li>');
            //添加node之间线的拖拽事件
            TREE.setEventLine($('.line, .line-last,.line-first', obj));
        };
        //设置展开关闭图片事件
        TREE.setTrigger = function (node) {
        	//添加点击图片
        	$('>span', node).before('<img class="trigger" src="'+TREE.option.themeUrl+'/images/spacer.gif" border=0>');
            var trigger = $('>.trigger', node);
            trigger.click(function (event) {
            	event.stopPropagation();
            	//单击展开或关闭
                TREE.nodeToggle(node);
            });
            //判断浏览器解对齐决兼容问题
            if (!$.browser.msie) { 
                trigger.css('float', 'left');
            }else{
            	var version=parseInt($.browser.version);
            	//判断是否存放于EASYUI中,或者其他浏览器解决对齐兼容问题
            	if(!TREE.option.inPanel && !isNaN(version) && version >= 8 && !is360()){
            		trigger.css('float', 'left');
            	}
            }
        };
        //开始拖拽
        TREE.dragStart = function (event) {
            var LI = $(event.data.LI);
            if (mousePressed) {
                mouseMoved = true;
                if (dragDropTimer) clearTimeout(dragDropTimer);
                if ($('#drag_container:not(:visible)')) {
                	//保存拖拽参数
                    $('#drag_container').show();
                    LI.prev().hide();
                    //修复IE拖拽BUG,判断按下是否为图标
                    if(TREE.option.nodesImage)
                    	LI.find("[icon='0']").remove();
                    LI.next().attr("tempTag","true");
                    dragNode_source = LI;
                }
                //拖拽浮动提示层
                $('#drag_container').css({ position: 'absolute', "left": (event.pageX + 5), "top": (event.pageY + 15) });
                if (LI.is(':visible')) LI.hide();
                if (event.target.tagName.toLowerCase() == 'span' && ($(event.target).attr("isNodeText")=='true') || $("img",$(event.target).parent()).attr("icon")) {
                	//判断是否是拖拽有效区域
                	var parent = $(event.target).parents("[id][level]:first");
                    var offs = parent.offset();
                    if(!offs || offs==null)
                    	return false;
                    var screenScroll = { x: (offs.left + 16), y: event.pageY - offs.top };
                    var isrc = $("#tree_plus").attr('src');
                    var ajaxChildSize = $('>ul.ajax', parent).size();
                    var ajaxChild = $('>ul.ajax', parent);
                    screenScroll.y = event.pageY - screenScroll.y + 5;
                    //判断事件节点是否存在子级节点,存在则提示禁止拖拽图标,展开后修改为为可添加提示状态
 					if (parent.attr("class").indexOf('folder-close') >= 0 && ajaxChildSize == 0) {
                        if (isrc.indexOf('minus') != -1) $("#tree_plus").attr('src', TREE.option.themeUrl + '/images/plus.gif');
                        $("#tree_plus").css({ "left": screenScroll.x, "top": screenScroll.y }).show();
                        dragDropTimer = setTimeout(function () {
                        	parent.attr("class",parent.attr("class").replace('close', 'open'));
                            $('>ul', parent).show();
                        }, 700);
                    //判断事件节点是否存在子级节点,若不存在则提示为可添加提示状态
                    } else if (parent.attr("class").indexOf('folder') >= 0 && ajaxChildSize == 0) {
                        if (isrc.indexOf('minus') != -1) $("#tree_plus").attr('src', TREE.option.themeUrl + '/images/plus.gif');
                        $("#tree_plus").css({ "left": screenScroll.x, "top": screenScroll.y }).show();
                    //判断事件节点是否存在子级节点,存在则提示禁止拖拽图标,展开后修改为为可添加提示状态
                    } else if (parent.attr("class").indexOf('folder-close') >= 0 && ajaxChildSize > 0) {
                        mouseMoved = false;
                        $("#tree_plus").attr('src', TREE.option.themeUrl + '/images/minus.gif');
                        $("#tree_plus").css({ "left": screenScroll.x, "top": screenScroll.y }).show();
                        $('>ul', parent).show();
                        //展开子节点
                        TREE.setAjaxNodes(ajaxChild, function () {
                            parent.attr("class",parent.attr("class").replace('close', 'open'));
                            mouseMoved = true;
                            $("#tree_plus").attr('src', TREE.option.themeUrl + '/images/plus.gif');
                            $("#tree_plus").css({ "left": screenScroll.x, "top": screenScroll.y }).show();
                        });
                    } else {
                    	//判断是否允许拖拽至无节点下,若参数设置允许则显示提示效果
                        if (TREE.option.docToFolderConvert) {
                            $("#tree_plus").css({ "left": screenScroll.x, "top": screenScroll.y }).show();
                        } else {
                            $("#tree_plus").hide();
                        }
                    }
                } else {
                	//超出范围隐藏
                    $("#tree_plus").hide();
                }
                return false;
            }
            return true;
        };
        //拖拽完成
        TREE.dragEnd = function () {
        	//停止拖拽事件,清理临时参数
            if (dragDropTimer) clearTimeout(dragDropTimer);
            TREE.eventDestroy();
        };
        //设置节点之间线的拖拽事件
        TREE.setEventLine = function (obj) {
            obj.mouseover(function () {
            	//可拖拽时,移动至此线上时提示效果
                if (this.className.indexOf('over') < 0 && mousePressed && mouseMoved) {
                    this.className = this.className.replace('line', 'line-over');
                }
            }).mouseout(function () {
            	//可拖拽时,移动离开后恢复原样
                if (this.className.indexOf('over') >= 0) {
                    this.className = this.className.replace('-over', '');
                }
            }).mouseup(function () {
            	//可拖拽时,鼠标松开则将拖拽数据放置于此
                if (mousePressed && dragNode_source && mouseMoved) {
                	if (this.className.indexOf('over') >= 0) {
                    	this.className = this.className.replace('-over', '');
                	}
                	TREE.moveNodeToLine(this);
                	TREE.eventDestroy();
                }
            });
        };
        //设置节点level属性
        TREE.setNodeLevel = function (node,parent) {
        	//根据父节点level加1
        	var thisLevel=parseInt(parent.attr("level"))+1;
        	node.attr("level",thisLevel);
        	//根据层级获取图片
        	var nodeImageSrc =TREE.getImageSrcByLevel(thisLevel-1);
			if(nodeImageSrc!='')
				node.find("[isNodeText]:first").before("<img icon='0' src='" + nodeImageSrc + "' style='vertical-align:middle;' height='15' width='15'/>");
			//设置子级节点level值及设置获取图片
            node.find("li[id][level]").each(function(){
            	thisLevel=parseInt($(this).parents("[id][level]:first").attr("level"))+1;
            	$(this).attr("level",thisLevel);
            	nodeImageSrc =TREE.getImageSrcByLevel(thisLevel-1);
	        	if(nodeImageSrc!='')
        			$(this).find("[isNodeText]:first").before("<img icon='0' src='" + nodeImageSrc + "' style='vertical-align:middle;' height='15' width='15'/>");
            });
        };
        //获取节点图标
        TREE.getImageSrcByLevel=function(parentLevel){
        	var nodeImageSrc='';
        	if(TREE.option.nodesImage && TREE.option.nodesImage.length > 0){
				if(TREE.option.nodesImage.length>parentLevel)
					nodeImageSrc=TREE.option.nodesImage[parentLevel];//加载对应图片
				else 
					nodeImageSrc=TREE.option.nodesImage[TREE.option.nodesImage.length-1];//加载数组最末尾的图片
			}
        	return nodeImageSrc;
        };
        //判断当前节点是否是第一个节点或者为最后的节点,用于加载样式
        TREE.checkNodeIsLastOrFirst = function (node) {
        	if(node.className.indexOf('first') >= 0){
        		//若当前为第一个节点,拖拽后设置拖拽后的第一个节点的样式
            	var next_source = dragNode_source.next().next();
                if (next_source.size() > 0) {
                	dragNode_source.next()[0].className+="-first";//线
                    next_source[0].className += '-first';	//节点
                }
                dragNode_source.prev().attr("class",dragNode_source.prev().attr("class").replace('-first', ''));
                node.className = node.className.replace('-first', '');
            }
            else if (node.className.indexOf('last') >= 0) {
            	//若当前为最后的节点,拖拽后设置拖拽后的最后一个节点的样式
                var prev_source = dragNode_source.prev().prev();
                if (prev_source.size() > 0) {
                    prev_source[0].className += '-last';
                }
                node.className = node.className.replace('-last', '');
            }
        };
        //判断当前线是否是第一条线或者为最后的线,用于加载样式
        TREE.checkLineIsLastOrFirst = function (line) {
        	if (line.className.indexOf('first') >= 0) {
        		//若当前为第一条线,拖拽后设置拖拽后的第一条线的样式
                var next = $(line).next();
                if (next.size() > 0) {
                    next[0].className = next[0].className.replace('-first', '');
                    line.className=line.className.replace('-first', '');
                }
                dragNode_source[0].className += '-first';
                dragNode_source.prev().attr("class",dragNode_source.prev().attr("class")+'-first');
            }
            else if (line.className.indexOf('last') >= 0) {
            	//若当前为最后的线,拖拽后设置拖拽后的最后一条线的样式
                var prev = $(line).prev();
                if (prev.size() > 0) {
                    prev[0].className = prev[0].className.replace('-last', '');
                }
                dragNode_source[0].className += '-last';
            }
        };
        //停止拖拽事件,清理临时参数
        TREE.eventDestroy = function () {
            if (TREE.option.drag) {
	            $(document).unbind('mousemove', TREE.dragStart).unbind('mouseup').unbind('mousedown');
	            $('#drag_container, #tree_plus').remove();
	            $("li[tempTag]").removeAttr("tempTag");
	            if (dragNode_source) {
        			TREE.setNodeLevel(dragNode_source,dragNode_source.parents("[id][level]:first"));
	                dragNode_source.show().prev().show();
	            }
	            dragParent = dragNode_source = mousePressed = mouseMoved = false;
            }
        };
        //将不存在节点的节点样式改为存在节点的样式 (可展开收起)
        TREE.convertToFolder = function (node) {
            node[0].className = node[0].className.replace('doc', 'folder-open');
            node.append('<ul><li class="line-last"></li></ul>');
            TREE.setTrigger(node[0]);
            TREE.setEventLine($('.line-last', node));
        };
        //将存在节点的节点样式改为不存在节点的样式 (不可展开收起)
        TREE.convertToDoc = function (node) {
            $('>ul', node).remove();
            $('img.trigger', node).remove();
            node[0].className = node[0].className.replace(/folder-(open|close)/gi, 'doc');
        };
        //将节点移动置节点中
        TREE.moveNodeToFolder = function (node) {
        	//判断当前节点是否已经存在子节点,根据参数配置是否可拖拽至此
        	if (!TREE.option.docToFolderConvert && node[0].className.indexOf('doc') != -1) {
                return true;
            //若当前不存在子节点,切参数允许拖拽至此将样式改为可展开收起节点
            } else if (TREE.option.docToFolderConvert && node[0].className.indexOf('doc') != -1 && node.attr("id")!=dragParent) {
                TREE.convertToFolder(node);
            }
        	//获取拖拽后节点最后的线的位置,将拖拽节点放置至此
            var lastLine = $('>ul >.line-last', node);
            if (lastLine.size() > 0) {
                TREE.moveNodeToLine(lastLine[0]);
            }
        };
        //将节点移动至线之前
        TREE.moveNodeToLine = function (line) {
        	//拖拽回原位置,不做任何修改
        	if($(line).attr("tempTag")=='true'){
        		return true;
        	}
        	//判断拖拽后节点样式
            TREE.checkNodeIsLastOrFirst(dragNode_source[0]);
            TREE.checkLineIsLastOrFirst(line);
            //获取拖拽前父节点
            var parent = $("#"+dragParent);
            var preline = $(dragNode_source).prev();
            //节点移动
            $(line).before(dragNode_source);
            $(dragNode_source).before(preline);
            preline.show();
            //恢复线提示状态
            line.className = line.className.replace('-over', '');
            if(dragParent!=TREE.id){
            	//拖拽前父节点若不为根节点则判断该父节点是否存在子节点,若拖拽后不,则修改节点样式为不可展开收起状态
            	var nodeSize = $('>ul >li', parent).not('.line, .line-last').filter(':visible').size();
	            if (TREE.option.docToFolderConvert && nodeSize == 0) {
	                TREE.convertToDoc(parent);
	            } else if (nodeSize == 0) {
	                parent[0].className = parent[0].className.replace('open', 'close');
	                $('>ul', parent).hide();
	            }
            }
            //拖拽后的父节点
            var newParent=$(dragNode_source).parents("[id][level]:first");
            //若参数设置可用样式,则将拖拽后节点高亮显示
            if (TREE.option.clickStyle && !TREE.option.checkbox && !TREE.option.isCommTree && $('span:first', dragNode_source).attr('class') == 'text') {
                $('.active', TREE).attr('class', 'text');
                $('span:first', dragNode_source).attr('class', 'active');
            }
            //拖拽后事件
            if (typeof (TREE.option.afterMove) == 'function') {
                TREE.option.afterMove($(dragNode_source), newParent, parent);
            }
        };
        //转换数据,根据ajax返回数据转换成nfpTree可识别节点格式
        TREE.convertListToNfpTree=function (list,parentLevel) {
			var hasCheckBox=TREE.option.checkbox;//是否存在复选框
			var initValueArray;//保存checkBox默认选中值
			var treeObject=$(TREE);
			var initValueArray;//保存checkBox默认选中值
			if(hasCheckBox && treeObject.attr("initValue") && treeObject.attr("initValue")!=""){
				 initValueArray=treeObject.attr("initValue").split(';');
			}
			var returnString = "";
			if (list!=null && list.length>0) {
				//存在数据,加载数据
				for (var j = 0;j<list.length;j++) {
					//循环读取节点内容
					var objects=list[j];
					var childText = "";
					if (parseInt(objects[objects.length - 1]) > 0) {
						//存在子节点,添加加载中状态节点
						childText ="<ul class='ajax'><input type='hidden' value='"+objects[0]+"'/></ul>";
					}
					//获取节点文本内容
					var nodeText ="<span isNodeText='true'>" + objects[1] + "</span>";
					var checkStatus="";
					//存在初始选中值
					if(initValueArray){
						if(treeObject.attr("initValue")==objects[0]){
							treeObject.removeAttr("initValue");//匹配成功,清空初始数据
							checkStatus=" checked='true' ";
						}else{
							//查找匹配结果
							var initcboIndex=-1;
							for(var v=0;v<initValueArray.length;v++){
								if(initValueArray[v]==objects[0]){
									checkStatus=" checked='true' ";
									initcboIndex=v;
									break;
								}				
							}
							//查找匹配成功
							if(initcboIndex!=-1){
								var treeInitValue=treeObject.attr("initValue");
								var tempCheckedValue=objects[0].toString();
								//根据索引值获取去除选中的格式
								if(initcboIndex==0){
									//第一个数值
									treeObject.attr("initValue",treeInitValue.substr(tempCheckedValue.length+1));
								}
								else if((initcboIndex+1)==initValueArray.length){
									//最后的数值
									treeObject.attr("initValue",treeInitValue.substr(0,treeInitValue.length-tempCheckedValue.length-1));
								}
								else{
									//中间的数值
									tempCheckedValue=";" + tempCheckedValue + ";";
									treeObject.attr("initValue",treeInitValue.substr(0,treeInitValue.indexOf(tempCheckedValue))+treeInitValue.substr(treeInitValue.indexOf(tempCheckedValue)+tempCheckedValue.length-1));
								}
								//重新赋值数组
								initValueArray=treeObject.attr("initValue").split(';');
							}
						}
					}
					//复选框文字
					var checkBoxText= hasCheckBox ? "<input type='checkbox' style='vertical-align:middle;' tag='nfpTree' value='"+ objects[0] +"' "+checkStatus+"/>":"";
					var nodeImageSrc =TREE.getImageSrcByLevel(parentLevel);
					//需要加载对应图片
					if(nodeImageSrc!=''){
						nodeText="<span style='white-space:nowrap;'><div style='display:inline;'>"+checkBoxText+"<img icon='0' src='" + nodeImageSrc + "' style='vertical-align:middle;' height='15' width='15'/>" + nodeText + "</div></span>";
					}else if(hasCheckBox){
						//需要加载复选框
						nodeText="<span style='white-space:nowrap;'><div style='display:inline;'>"+checkBoxText + nodeText + "</div></span>";
					}
					returnString += "<li id='" + TREE.id + objects[0] + "' code='"+objects[0]+"' level='"+(parseInt(parentLevel)+1)+"'";
					// 存在隐藏属性,读取隐藏属性值
					for (var i = 2; i < objects.length - 1; i++) {
						returnString += " nodeAttr" + (i - 1) + "='" + objects[i] + "'";
					}
					returnString += ">" + nodeText+childText + "</li>";
				}
			}
			return returnString;
		}
		//树形控件初始化
        TREE.init = function () {
            if (TREE.option.ajaxUrl) {
            	TREE.setAjaxNodes($(TREE),null,true);
            }
        };
        TREE.init();
    });
}

/*方法:全部展开
 *参数: nfpTreeObject:树形菜单控件(Jquery对象)
 */
function extendAllNFPNodes(nfpTreeObject,fnBegin,fnEnd){
	//若已经全部展开则直接返回
	if(!nfpTreeObject || nfpTreeObject.find("li[level][class*='close']").size()==0)
		return;
	//开始前事件(通常用于将全部收起按钮隐藏或禁用,防止暴力点击出现页面样式问题)
	if (typeof (fnBegin) == 'function') 
		fnBegin();
	nfpTreeObject.attr("openAll","true");
	var maxIndex=-1;
	if(nfpTreeObject.find("ul.ajax").size()==0){
		//获取当前未展开的节点个数
		maxIndex=nfpTreeObject.find("li[level][class*='close']").size()-1;
	}
	setTimeout(function(){
		//执行展开事件
		nfpTreeObject.find("li[level][class*='close']").each(function(i){
			$(this).find("img.trigger:first").trigger("click");
			if(maxIndex==i){
				nfpTreeObject.removeAttr("openAll");
				//结束前事件(通常用于将全部收起按钮显示或可用,防止暴力点击出现页面样式问题)
				if (typeof (fnEnd) == 'function') 
					setTimeout(fnEnd,500);
			}
		});
	},50);
}
/*方法:全部收起
 *参数:nfpTreeObject:树形菜单控件(Jquery对象)
 */
function collapseAllNFPNodes(nfpTreeObject,fnBegin,fnEnd){
	//若已经全部收起则直接返回
	if(!nfpTreeObject || nfpTreeObject.find("li[level][class*='open']").size()==0)
		return;
	//开始前事件(通常用于将全部展开按钮隐藏或禁用,防止暴力点击出现页面样式问题)
	if (typeof (fnBegin) == 'function') 
		fnBegin();
	setTimeout(function(){
		//执行收起事件
		var maxIndex=nfpTreeObject.find("li[level][class*='open']").size()-1;
		nfpTreeObject.find("li[level][class*='open']").each(function(i){
			$(this).find("img.trigger:first").trigger("click");
			//结束前事件(通常用于将全部展开按钮显示或可用,防止暴力点击出现页面样式问题)
			if(maxIndex==i && typeof (fnEnd) == 'function'){
				setTimeout(fnEnd,500);
			}
		});
		
	},50);
	
}
/************************************树形控件 结束***********************************/

/************************************下拉树形控件 开始***********************************/
$.fn.nfpCommTree = function (opt) {
    return this.each(function () {
    	if(!this.id)
    		return;
    	var COMMTREE = this;
    	//判断是否为IE6,解决下拉BUG
    	var isIE6=$.browser.msie && $.browser.version=='6.0';
    	var treeControl;
    	var layerControl;
    	
        COMMTREE.option = {
            /***********Tree参数************/
            ajaxUrl: false, //ajax请求路径		(必填)
            themeUrl:'',	//themes文件夹路径	(必填)
            paramsName: false, //参数名称		(必填)

            animate: true,  //展开是否存在动画效果
            autoclose: false,  //展开节点时是否关闭同一父节点下其他节点
            checkbox: false,    //是否添加checkbox
			docToFolderConvert: true,//拖拽时是否允许往不存在子级节点拖拽
			drag: false,  //是否允许拖拽
			expandLevel: 1,	//默认展开行数
			speed: 200, //展开关闭时的速度('slow'|'normal'|'fast' or 毫秒数字)
			nodesImage:false,//节点图标(位于文本前,数组类型,填写图片对应地址,节点level值与数组进行匹配,当节点level超出数组长度,默认加载数组最后的图标)
			useCache:false,	//使用缓存
            
			/**********事件*************/
            afterAjax: false, //ajax读取完数据后执行方法
            afterMove: false, //拖拽完后执行的方法
            afterDblClick: false, //双击完执行的方法
            afterContextMenu: false, //节点上鼠标右键后执行的方法
            afterExtendAll:false,//全部展开完成后执行的方法

            /*********Comm扩展参数********/
            direction:"down",	//展开方向('up','down')
            dropSpeed:200,	//展开/收起 速度
            dropHeight:200,	//展开高度
            dropWidth:$(COMMTREE).width()+2,//展开宽度(默认为控件自身的宽度)
            dropStyle:false,//展开层的样式
            dropZIndex:9999,	//下拉层z-index值
			/**********事件*************/
            afterChange:false //选中value发生改变时执行的方法
        };
        
        COMMTREE.option = $.extend(COMMTREE.option, opt);
        //下拉控件中的树形控件初始化
        COMMTREE.initTree=function(){
        	//判断该树是否已经初始化,防止重复加载
        	if(!treeControl.attr("isInited")){
        		var control=$(COMMTREE);
        		//判断是否存在初始选中值
				if(control.attr("checkedValue"))
					treeControl.attr("initValue",control.attr("checkedValue"));
	        	//初始化树形控件
				treeControl.nfpTree({
		            drag: COMMTREE.option.drag,  //是否允许拖拽
		            afterMove: COMMTREE.option.afterMove, //拖拽完后执行的方法
		            docToFolderConvert: COMMTREE.option.docToFolderConvert,//拖拽时是否允许往不存在子级节点拖拽
			        ajaxUrl: COMMTREE.option.ajaxUrl, //ajax请求路径
		            themeUrl: COMMTREE.option.themeUrl,	//themes文件夹路径(若启用拖拽则必填,否则拖拽效果会无提示)
		            paramsName: COMMTREE.option.paramsName, //参数名称
		            useCache: COMMTREE.option.useCache,	//使用缓存
		            expandLevel: COMMTREE.option.expandLevel,	//默认展开行数
		            animate: COMMTREE.option.animate,  //展开是否存在动画效果
		            autoclose: COMMTREE.option.autoclose,  //展开节点时是否关闭同一父节点下其他节点
		            checkbox: COMMTREE.option.checkbox,    //是否添加checkbox
		            speed: COMMTREE.option.speed, //展开关闭时的速度('slow'|'normal'|'fast' or 毫秒数字)
		            afterAjax: COMMTREE.option.afterAjax, //ajax读取完数据后执行方法
		            afterClick: function(node){ //单击完执行的方法
						if(COMMTREE.option.checkbox){
							var checkBoxObj=node.find(":checkbox[tag='nfpTree']");
							COMMTREE.commTreeChecked(checkBoxObj,node);
						}else{
							var oldValue=control.attr("checkedValue");
							control.attr("checkedValue",node.attr("id").substr(COMMTREE.id.length+4));
							control.val(node.find("[isNodeText='true']").html());
							COMMTREE.hideLayer();
							control.trigger("blur");
							if(typeof (COMMTREE.option.afterChange) == 'function' && oldValue!=control.attr("checkedValue"))
								COMMTREE.option.afterChange(control.attr("checkedValue"));
						}
		            }, 
		            afterDblClick: COMMTREE.option.afterDblClick, //双击完执行的方法
		            afterContextMenu: COMMTREE.option.afterContextMenu, //节点上鼠标右键后执行的方法
		            afterExtendAll: COMMTREE.option.afterExtendAll,//全部展开完成后执行的方法
		            nodesImage: COMMTREE.option.nodesImage,//节点图标(位于文本前,数组类型,填写图片对应地址,节点level值与数组进行匹配,当节点level超出数组长度,默认加载数组最后的图标)
		            isCommTree: true	//设置树类型(commTree右键和拖拽后不执行选中高亮显示)
			    });
				treeControl.attr("isInited","true");
			}
        }
        //checkbox选中事件
        COMMTREE.commTreeChecked = function(jCheckBox,node){
			var checkedValue=jCheckBox.val();
			var checkedText=node.find("[isNodeText='true']").html();
			var control=$(COMMTREE);
			var controlText=control.val();//获取控件文本以及选中标示
			var controlValue=control.attr("checkedValue") ? control.attr("checkedValue") : "";
			if(jCheckBox.attr("checked")){
				var splitTag=control.attr("checkedValue") ? ";":""; 
				//选中
				control.val(controlText+splitTag+checkedText);
				control.attr("checkedValue",controlValue+splitTag+checkedValue);
			}else{
				//去除选中
				var valueArray=controlValue.split(';');
				var checkedIndex=-1;
				for(var i=0;i<valueArray.length;i++){
					if(valueArray[i]==checkedValue){
						checkedIndex=i;
						break;	
					}
				}
				//若去除选中value等于当前选中value则直接移除掉选中属性
				if(controlValue==checkedValue){
					control.val("");
					control.removeAttr("checkedValue");
				}else{
					//根据索引值获取去除选中的格式
					if(checkedIndex==0){
						//移除节点为当前选中第一个节点
						control.val(controlText.substr(checkedText.length+1));
						control.attr("checkedValue",controlValue.substr(checkedValue.length+1));
					}
					else if((checkedIndex+1)==valueArray.length){
						//移除节点为当前选中最后一个节点
						control.val(controlText.substr(0,controlText.length-checkedText.length-1));
						control.attr("checkedValue",controlValue.substr(0,controlValue.length-checkedValue.length-1));
					}
					else{
						//移除节点为中间节点
						checkedText=";" + checkedText + ";";
						checkedValue=";" + checkedValue + ";";
						control.val(controlText.substr(0,controlText.indexOf(checkedText))+controlText.substr(controlText.indexOf(checkedText)+checkedText.length-1));
						control.attr("checkedValue",controlValue.substr(0,controlValue.indexOf(checkedValue))+controlValue.substr(controlValue.indexOf(checkedValue)+checkedValue.length-1));
					}
				}
			}
			//执行value改变事件
			if(typeof (COMMTREE.option.afterChange) == 'function')
				COMMTREE.option.afterChange(control.attr("checkedValue"));
		}
        //设置下拉层坐标
        COMMTREE.setRelativePosition=function() {
			var layer_div= $("#"+COMMTREE.id+"Layer");//浮动层控件
			if(isIE6) //IE防止select标签置顶,加入iframe
				layer_div= $("#"+COMMTREE.id+"Layer,#"+COMMTREE.id+"Frame");
			var control=$("#"+COMMTREE.id);
			layer_div.css("left",control.offset().left + "px");
			if(COMMTREE.option.direction=="up")
				//向上滑动
				layer_div.css("top",(control.offset().top - layer_div.height()) + "px");
			else
				//向下滑动
				layer_div.css("top",(control.offset().top + control.height() + 5) + "px");
		}
        //展开下拉层
        COMMTREE.showLayer=function(){
        	//隐藏其他控件展开层控件
        	$(document.body).find("div[id$='Layer']:not([id='"+ COMMTREE.id +"Layer'])").hide();
        	//设置坐标
			COMMTREE.setRelativePosition();
			//设置下拉样式,解决浏览器下拉兼容BUG
			if($.browser.mozilla || COMMTREE.option.direction=="up")
				layerControl.fadeIn(COMMTREE.option.dropSpeed,COMMTREE.initTree);
			else
				layerControl.slideDown(COMMTREE.option.dropSpeed,COMMTREE.initTree);
        }
        //隐藏下拉层
        COMMTREE.hideLayer=function(){
			layerControl.hide();
        }
        //下拉控件初始化
        COMMTREE.init = function () {
            var control=$(COMMTREE);
            //设置控件只读样式
			control.attr({autocomplete:"off", readonly:"true"});
			control.css("cursor","pointer");
			//获取设置参数
			var dropHeight= COMMTREE.option.dropHeight;
			var dropSpeed= COMMTREE.option.dropSpeed;
			var dropWidth=COMMTREE.option.dropWidth;
			var dropStyle=COMMTREE.option.dropStyle ? COMMTREE.option.dropStyle:"border:solid 1px #A5B3C5;position:absolute;background-color: white; width:"+dropWidth+"px;display:none; z-index:"+COMMTREE.option.dropZIndex+"; height:"+dropHeight+"px;";
			var direction=COMMTREE.option.direction=="up" ? "up":"down";
			//初始化下拉层html代码
			var iframeDiv="<iframe id='"+COMMTREE.id+"Frame' frameborder='no' style='display:none;border:none;width:"+dropWidth+"px;display;none;left:0px;top:0px; position:absolute; height:"+dropHeight+"px;'></iframe>";
			var lodingDiv=COMMTREE.option.themeUrl ? "<div class='ajaxLoading' id='nfpLoading'></div>" : "";
			var treeDiv="<ul id='"+COMMTREE.id+"Tree'>"+lodingDiv+"</ul>";
			treeDiv="<div style='width:"+dropWidth+"px; height:"+dropHeight+"px;overflow:auto;'>"+treeDiv+"</div>";
			var dropDiv="<div style='overflow:auto;"+dropStyle+"' id='"+COMMTREE.id+"Layer'>"+treeDiv+"</div>";
			$(document.body).append((isIE6 ? iframeDiv: "") + dropDiv);
			treeControl=$("#"+COMMTREE.id +"Tree");
			layerControl= $("#"+COMMTREE.id+"Layer");
			//IE6解决下拉BUG
			if(isIE6){
				layerControl= $("#"+COMMTREE.id+"Layer,#"+COMMTREE.id+"Frame");
				layerControl.hide();
			}
			//为body添加点击关闭浮动层
			$(document.body).click(function(event){
				COMMTREE.hideLayer();
			});
			//下拉控件上点击时不关闭层
			$("#"+COMMTREE.id+",#"+COMMTREE.id+"Tree,"+"#"+COMMTREE.id+"Layer").click(function(event){
				event.stopPropagation();
				event.preventDefault();
			});
			//窗体改变大小时重新设置控件坐标
			$(window).resize(function() {
				if(layerControl.css("display")!="none")
					COMMTREE.setRelativePosition();
			});
			//focus显示下拉控件
			control.focus(function(event){
				COMMTREE.showLayer();
			});
			//键盘按下ESC或者退格键时清空选中value
			control.keydown(function(event){
				event.stopPropagation();
				event.preventDefault();
				if(event.keyCode==8 || event.keyCode==46){
					treeControl.removeAttr("initValue");
					if(COMMTREE.option.checkbox)
						layerControl.find(":checkbox[tag='nfpTree']").attr("checked",false);
					else
						layerControl.find("span.active").attr("class","text");
					var oldValue=$("#"+COMMTREE.id).attr("checkedValue");
					control.val("");
					control.removeAttr("checkedValue");
					//执行value改变事件
					if(typeof (COMMTREE.option.afterChange) == 'function' && oldValue!=control.attr("checkedValue"))
						COMMTREE.option.afterChange(control.attr("checkedValue"));
				}
			});
        };
        $(COMMTREE).attr("nfpTag","1");//标记控件类型
        //控件初始化
        COMMTREE.init();
    });
}
//重新加载树形控件(用于清空下拉树形控件)
function reloadCommTree(controlId){
	$("#"+controlId+"Tree").removeAttr("isInited");
	$("#"+controlId+"Tree").html("<div class='ajaxLoading' id='nfpLoading'></div>");
	nfpTreeCacheParams=new Array(new Array(),new Array()); 
	nfpTreeCacheSource=new Array(new Array(),new Array()); 
	nfpTreeCacheUrl=new Array(); 
	nfpCurrentTree=new Array();	 
}
/************************************下拉树形控件 结束***********************************/
//判断当前是否为360浏览器,用于解决树形控件对齐样式BUG
function is360()
{
	try{
		if (window.navigator.userAgent.toLowerCase().indexOf("360se")>=1)
		//如果浏览器为360
		{
			return true;
		}
		if(window.external&&window.external.twGetRunPath)
		{
		//获取路径
			var r=external.twGetRunPath();
			if(r&&r.toLowerCase().indexOf("360se")>-1)
				return true;
		}
		return false;
	}
	catch(err){
		return false;
	}
}
