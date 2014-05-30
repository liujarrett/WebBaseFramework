var nfpTreeCacheParams=new Array(new Array(),new Array());var nfpTreeCacheSource=new Array(new Array(),new Array());var nfpTreeCacheUrl=new Array();var nfpCurrentTree=new Array();$.fn.nfpTree=function(opt){return this.each(function(){if(!this.id)
return;if(opt.isCommTree){if($.inArray(this.id,nfpCurrentTree)==-1)
nfpCurrentTree[nfpCurrentTree.length]=this.id;else
return;}
var TREE=this;var isFirstNode=true;var mousePressed=false;var mouseMoved=false;var dragParent=false;var dragNode_source=false;var dragDropTimer=false;TREE.option={ajaxUrl:false,themeUrl:'',paramsName:false,animate:true,autoclose:false,checkbox:false,clickStyle:true,docToFolderConvert:true,drag:false,expandLevel:1,inPanel:false,isCommTree:false,speed:200,nodesImage:false,useCache:false,afterAjax:false,afterMove:false,afterClick:false,afterDblClick:false,afterContextMenu:false,afterExtendAll:false};TREE.option=$.extend(TREE.option,opt);TREE.closeNearby=function(obj){$(obj).siblings().filter('.folder-open, .folder-open-last').each(function(){var childUl=$('>ul',this);var className=this.className;this.className=className.replace('open','close');if(TREE.option.animate){childUl.animate({height:"toggle"},TREE.option.speed);}else{childUl.hide();}});};TREE.nodeToggle=function(obj){var childUl=$('>ul',obj);if(childUl.is(':visible')){obj.className=obj.className.replace('open','close');if(TREE.option.animate){childUl.animate({height:"toggle"},TREE.option.speed);}else{childUl.hide();}}else{obj.className=obj.className.replace('close','open');if(TREE.option.animate){childUl.animate({height:"toggle"},TREE.option.speed,function(){if(TREE.option.autoclose)TREE.closeNearby(obj);if(childUl.is('.ajax'))TREE.setAjaxNodes(childUl);});}else{childUl.show();if(TREE.option.autoclose)TREE.closeNearby(obj);if(childUl.is('.ajax'))TREE.setAjaxNodes(childUl);}}};TREE.setAjaxNodes=function(node,callback,isInit){var paramValue=isInit?'0':$(":hidden",node).val();if(paramValue&&paramValue!=null){node.html(isInit?"":"<ul></ul>");var listSource=null;if(TREE.option.useCache){var urlIndex=$.inArray(TREE.option.ajaxUrl,nfpTreeCacheUrl);if(urlIndex==-1){nfpTreeCacheUrl[nfpTreeCacheUrl.length]=TREE.option.ajaxUrl;}else{var paramIndex=$.inArray(paramValue,nfpTreeCacheParams[urlIndex]);if(paramIndex>-1){listSource=nfpTreeCacheSource[urlIndex][paramIndex];}}}
if(listSource==null){$.ajax({async:false,url:TREE.option.ajaxUrl,data:eval("({"+TREE.option.paramsName+":"+paramValue+"})"),type:"GET",cache:false,dataType:"json",success:function(data){if(TREE.option.useCache){var urlIndex=$.inArray(TREE.option.ajaxUrl,nfpTreeCacheUrl);var sourceIndex=nfpTreeCacheParams[urlIndex].length;nfpTreeCacheParams[urlIndex][sourceIndex]=paramValue;nfpTreeCacheSource[urlIndex][sourceIndex]=data;}
listSource=data;},error:function(){alert("加载失败,请确保您的网络畅通!");}});}
if(listSource!=null){node.removeAttr('class');if(isInit){$(TREE).attr({"class":"nfpTree","level":"0"});}
node.html(TREE.convertListToNfpTree(listSource,isInit?0:node.parent().attr("level")));TREE.setTreeNodes(node);if(TREE.option.clickStyle&&!TREE.option.checkbox&&$(TREE).attr("initValue")){var initSelected=$("#"+TREE.id+$(TREE).attr("initValue"),node);if(initSelected.size()==1){initSelected.find("span.text:first").attr("class","active");$(TREE).removeAttr("initValue");}}
if(typeof TREE.option.afterAjax=='function'){TREE.option.afterAjax(node);}
if(typeof callback=='function'){callback(node);}
if($(TREE).attr("openAll")&&$(TREE).find("ul.ajax").size()==0){$(TREE).removeAttr("openAll");if(typeof TREE.option.afterExtendAll=='function'){TREE.option.afterExtendAll();}}}}};TREE.setTreeNodes=function(obj){$('li>span',obj).addClass('text').bind('selectstart',function(){return false;}).click(function(){if(TREE.option.clickStyle&&!TREE.option.checkbox){$('.active',TREE).attr('class','text');if(this.className=='text'){this.className='active';}
$(TREE).removeAttr("initValue");}else{var checkboxObj=$(this).find(":checkbox[tag='nfpTree']:first");if(checkboxObj.attr("checked"))
checkboxObj.removeAttr("checked");else
checkboxObj.attr("checked",'true');}
if(typeof TREE.option.afterClick=='function'){TREE.option.afterClick($(this).parent());return false;}
return true;}).dblclick(function(){mousePressed=false;TREE.nodeToggle($(this).parent().get(0));if(typeof TREE.option.afterDblClick=='function'){TREE.option.afterDblClick($(this).parent());return false;}
return true;}).bind("contextmenu",function(event){if(TREE.option.clickStyle&&!TREE.option.checkbox&&!TREE.option.isCommTree){$('.active',TREE).attr('class','text');if(this.className=='text'){this.className='active';}}
if(typeof TREE.option.afterContextMenu=='function'){TREE.option.afterContextMenu($(this).parent(),event);return false;}
return true;}).mousedown(function(event){mousePressed=true;if(TREE.option.drag){cloneNode=$(this).parent().clone();var LI=$(this).parent();dragParent=LI.parents("[id][level]:first").attr("id");$('>ul',cloneNode).hide();$('body').append('<div id="drag_container"><ul></ul></div>');$('#drag_container').hide().css({opacity:'0.8'});$('#drag_container >ul').append(cloneNode);$("<img>").attr({id:"tree_plus",src:TREE.option.themeUrl+"/images/plus.gif"}).css({position:"absolute",left:"5px",top:"5px",display:'none','z-index':99999}).appendTo("body");$(document).bind("mousemove",{LI:LI},TREE.dragStart).bind("mouseup",TREE.dragEnd);}
return false;}).mouseup(function(){if(mousePressed&&mouseMoved&&dragNode_source){TREE.moveNodeToFolder($(this).parent());}
TREE.eventDestroy();});$(":checkbox[tag='nfpTree']",obj).click(function(event){event.stopPropagation();if(typeof TREE.option.afterClick=='function'){TREE.option.afterClick($(this).parent());}});$('li',obj).each(function(i){var className=this.className;var open=false;var cloneNode=false;var LI=this;var childNode=$('>ul',this);$(this).before('<li class="line'+(isFirstNode?'-first':'')+'">&nbsp;</li>');if(childNode.size()>0){var setClassName='folder-';if(className&&className.indexOf('open')>=0){setClassName=setClassName+'open';open=true;}else{setClassName=setClassName+'close';}
if(isFirstNode){this.className=setClassName+($(this).is(':last-child')?'-last-first':'-first');isFirstNode=false;}
else
this.className=setClassName+($(this).is(':last-child')?'-last':'');TREE.setTrigger(this);if(!open||className.indexOf('ajax')>=0){childNode.hide();if((!isNaN(TREE.option.expandLevel)&&TREE.option.expandLevel>(parseInt($(this).attr("level"))))||$(TREE).attr("openAll")||$(TREE).attr("initValue"))
{$(">img",this).trigger("click");}}}else{var setClassName='doc';if(isFirstNode){this.className=setClassName+($(this).is(':last-child')?'-last-first':'-first');isFirstNode=false;}
else
this.className=setClassName+($(this).is(':last-child')?'-last':'');}}).filter(':last-child').after('<li class="line-last"></li>');TREE.setEventLine($('.line, .line-last,.line-first',obj));};TREE.setTrigger=function(node){$('>span',node).before('<img class="trigger" src="'+TREE.option.themeUrl+'/images/spacer.gif" border=0>');var trigger=$('>.trigger',node);trigger.click(function(event){event.stopPropagation();TREE.nodeToggle(node);});if(!$.browser.msie){trigger.css('float','left');}else{var version=parseInt($.browser.version);if(!TREE.option.inPanel&&!isNaN(version)&&version>=8&&!is360()){trigger.css('float','left');}}};TREE.dragStart=function(event){var LI=$(event.data.LI);if(mousePressed){mouseMoved=true;if(dragDropTimer)clearTimeout(dragDropTimer);if($('#drag_container:not(:visible)')){$('#drag_container').show();LI.prev().hide();if(TREE.option.nodesImage)
LI.find("[icon='0']").remove();LI.next().attr("tempTag","true");dragNode_source=LI;}
$('#drag_container').css({position:'absolute',"left":(event.pageX+5),"top":(event.pageY+15)});if(LI.is(':visible'))LI.hide();if(event.target.tagName.toLowerCase()=='span'&&($(event.target).attr("isNodeText")=='true')||$("img",$(event.target).parent()).attr("icon")){var parent=$(event.target).parents("[id][level]:first");var offs=parent.offset();if(!offs||offs==null)
return false;var screenScroll={x:(offs.left+16),y:event.pageY-offs.top};var isrc=$("#tree_plus").attr('src');var ajaxChildSize=$('>ul.ajax',parent).size();var ajaxChild=$('>ul.ajax',parent);screenScroll.y=event.pageY-screenScroll.y+5;if(parent.attr("class").indexOf('folder-close')>=0&&ajaxChildSize==0){if(isrc.indexOf('minus')!=-1)$("#tree_plus").attr('src',TREE.option.themeUrl+'/images/plus.gif');$("#tree_plus").css({"left":screenScroll.x,"top":screenScroll.y}).show();dragDropTimer=setTimeout(function(){parent.attr("class",parent.attr("class").replace('close','open'));$('>ul',parent).show();},700);}else if(parent.attr("class").indexOf('folder')>=0&&ajaxChildSize==0){if(isrc.indexOf('minus')!=-1)$("#tree_plus").attr('src',TREE.option.themeUrl+'/images/plus.gif');$("#tree_plus").css({"left":screenScroll.x,"top":screenScroll.y}).show();}else if(parent.attr("class").indexOf('folder-close')>=0&&ajaxChildSize>0){mouseMoved=false;$("#tree_plus").attr('src',TREE.option.themeUrl+'/images/minus.gif');$("#tree_plus").css({"left":screenScroll.x,"top":screenScroll.y}).show();$('>ul',parent).show();TREE.setAjaxNodes(ajaxChild,function(){parent.attr("class",parent.attr("class").replace('close','open'));mouseMoved=true;$("#tree_plus").attr('src',TREE.option.themeUrl+'/images/plus.gif');$("#tree_plus").css({"left":screenScroll.x,"top":screenScroll.y}).show();});}else{if(TREE.option.docToFolderConvert){$("#tree_plus").css({"left":screenScroll.x,"top":screenScroll.y}).show();}else{$("#tree_plus").hide();}}}else{$("#tree_plus").hide();}
return false;}
return true;};TREE.dragEnd=function(){if(dragDropTimer)clearTimeout(dragDropTimer);TREE.eventDestroy();};TREE.setEventLine=function(obj){obj.mouseover(function(){if(this.className.indexOf('over')<0&&mousePressed&&mouseMoved){this.className=this.className.replace('line','line-over');}}).mouseout(function(){if(this.className.indexOf('over')>=0){this.className=this.className.replace('-over','');}}).mouseup(function(){if(mousePressed&&dragNode_source&&mouseMoved){if(this.className.indexOf('over')>=0){this.className=this.className.replace('-over','');}
TREE.moveNodeToLine(this);TREE.eventDestroy();}});};TREE.setNodeLevel=function(node,parent){var thisLevel=parseInt(parent.attr("level"))+1;node.attr("level",thisLevel);var nodeImageSrc=TREE.getImageSrcByLevel(thisLevel-1);if(nodeImageSrc!='')
node.find("[isNodeText]:first").before("<img icon='0' src='"+nodeImageSrc+"' style='vertical-align:middle;' height='15' width='15'/>");node.find("li[id][level]").each(function(){thisLevel=parseInt($(this).parents("[id][level]:first").attr("level"))+1;$(this).attr("level",thisLevel);nodeImageSrc=TREE.getImageSrcByLevel(thisLevel-1);if(nodeImageSrc!='')
$(this).find("[isNodeText]:first").before("<img icon='0' src='"+nodeImageSrc+"' style='vertical-align:middle;' height='15' width='15'/>");});};TREE.getImageSrcByLevel=function(parentLevel){var nodeImageSrc='';if(TREE.option.nodesImage&&TREE.option.nodesImage.length>0){if(TREE.option.nodesImage.length>parentLevel)
nodeImageSrc=TREE.option.nodesImage[parentLevel];else
nodeImageSrc=TREE.option.nodesImage[TREE.option.nodesImage.length-1];}
return nodeImageSrc;};TREE.checkNodeIsLastOrFirst=function(node){if(node.className.indexOf('first')>=0){var next_source=dragNode_source.next().next();if(next_source.size()>0){dragNode_source.next()[0].className+="-first";next_source[0].className+='-first';}
dragNode_source.prev().attr("class",dragNode_source.prev().attr("class").replace('-first',''));node.className=node.className.replace('-first','');}
else if(node.className.indexOf('last')>=0){var prev_source=dragNode_source.prev().prev();if(prev_source.size()>0){prev_source[0].className+='-last';}
node.className=node.className.replace('-last','');}};TREE.checkLineIsLastOrFirst=function(line){if(line.className.indexOf('first')>=0){var next=$(line).next();if(next.size()>0){next[0].className=next[0].className.replace('-first','');line.className=line.className.replace('-first','');}
dragNode_source[0].className+='-first';dragNode_source.prev().attr("class",dragNode_source.prev().attr("class")+'-first');}
else if(line.className.indexOf('last')>=0){var prev=$(line).prev();if(prev.size()>0){prev[0].className=prev[0].className.replace('-last','');}
dragNode_source[0].className+='-last';}};TREE.eventDestroy=function(){if(TREE.option.drag){$(document).unbind('mousemove',TREE.dragStart).unbind('mouseup').unbind('mousedown');$('#drag_container, #tree_plus').remove();$("li[tempTag]").removeAttr("tempTag");if(dragNode_source){TREE.setNodeLevel(dragNode_source,dragNode_source.parents("[id][level]:first"));dragNode_source.show().prev().show();}
dragParent=dragNode_source=mousePressed=mouseMoved=false;}};TREE.convertToFolder=function(node){node[0].className=node[0].className.replace('doc','folder-open');node.append('<ul><li class="line-last"></li></ul>');TREE.setTrigger(node[0]);TREE.setEventLine($('.line-last',node));};TREE.convertToDoc=function(node){$('>ul',node).remove();$('img.trigger',node).remove();node[0].className=node[0].className.replace(/folder-(open|close)/gi,'doc');};TREE.moveNodeToFolder=function(node){if(!TREE.option.docToFolderConvert&&node[0].className.indexOf('doc')!=-1){return true;}else if(TREE.option.docToFolderConvert&&node[0].className.indexOf('doc')!=-1&&node.attr("id")!=dragParent){TREE.convertToFolder(node);}
var lastLine=$('>ul >.line-last',node);if(lastLine.size()>0){TREE.moveNodeToLine(lastLine[0]);}};TREE.moveNodeToLine=function(line){if($(line).attr("tempTag")=='true'){return true;}
TREE.checkNodeIsLastOrFirst(dragNode_source[0]);TREE.checkLineIsLastOrFirst(line);var parent=$("#"+dragParent);var preline=$(dragNode_source).prev();$(line).before(dragNode_source);$(dragNode_source).before(preline);preline.show();line.className=line.className.replace('-over','');if(dragParent!=TREE.id){var nodeSize=$('>ul >li',parent).not('.line, .line-last').filter(':visible').size();if(TREE.option.docToFolderConvert&&nodeSize==0){TREE.convertToDoc(parent);}else if(nodeSize==0){parent[0].className=parent[0].className.replace('open','close');$('>ul',parent).hide();}}
var newParent=$(dragNode_source).parents("[id][level]:first");if(TREE.option.clickStyle&&!TREE.option.checkbox&&!TREE.option.isCommTree&&$('span:first',dragNode_source).attr('class')=='text'){$('.active',TREE).attr('class','text');$('span:first',dragNode_source).attr('class','active');}
if(typeof(TREE.option.afterMove)=='function'){TREE.option.afterMove($(dragNode_source),newParent,parent);}};TREE.convertListToNfpTree=function(list,parentLevel){var hasCheckBox=TREE.option.checkbox;var initValueArray;var treeObject=$(TREE);var initValueArray;if(hasCheckBox&&treeObject.attr("initValue")&&treeObject.attr("initValue")!=""){initValueArray=treeObject.attr("initValue").split(';');}
var returnString="";if(list!=null&&list.length>0){for(var j=0;j<list.length;j++){var objects=list[j];var childText="";if(parseInt(objects[objects.length-1])>0){childText="<ul class='ajax'><input type='hidden' value='"+objects[0]+"'/></ul>";}
var nodeText="<span isNodeText='true'>"+objects[1]+"</span>";var checkStatus="";if(initValueArray){if(treeObject.attr("initValue")==objects[0]){treeObject.removeAttr("initValue");checkStatus=" checked='true' ";}else{var initcboIndex=-1;for(var v=0;v<initValueArray.length;v++){if(initValueArray[v]==objects[0]){checkStatus=" checked='true' ";initcboIndex=v;break;}}
if(initcboIndex!=-1){var treeInitValue=treeObject.attr("initValue");var tempCheckedValue=objects[0].toString();if(initcboIndex==0){treeObject.attr("initValue",treeInitValue.substr(tempCheckedValue.length+1));}
else if((initcboIndex+1)==initValueArray.length){treeObject.attr("initValue",treeInitValue.substr(0,treeInitValue.length-tempCheckedValue.length-1));}
else{tempCheckedValue=";"+tempCheckedValue+";";treeObject.attr("initValue",treeInitValue.substr(0,treeInitValue.indexOf(tempCheckedValue))+treeInitValue.substr(treeInitValue.indexOf(tempCheckedValue)+tempCheckedValue.length-1));}
initValueArray=treeObject.attr("initValue").split(';');}}}
var checkBoxText=hasCheckBox?"<input type='checkbox' style='vertical-align:middle;' tag='nfpTree' value='"+objects[0]+"' "+checkStatus+"/>":"";var nodeImageSrc=TREE.getImageSrcByLevel(parentLevel);if(nodeImageSrc!=''){nodeText="<span style='white-space:nowrap;'><div style='display:inline;'>"+checkBoxText+"<img icon='0' src='"+nodeImageSrc+"' style='vertical-align:middle;' height='15' width='15'/>"+nodeText+"</div></span>";}else if(hasCheckBox){nodeText="<span style='white-space:nowrap;'><div style='display:inline;'>"+checkBoxText+nodeText+"</div></span>";}
returnString+="<li id='"+TREE.id+objects[0]+"' code='"+objects[0]+"' level='"+(parseInt(parentLevel)+1)+"'";for(var i=2;i<objects.length-1;i++){returnString+=" nodeAttr"+(i-1)+"='"+objects[i]+"'";}
returnString+=">"+nodeText+childText+"</li>";}}
return returnString;}
TREE.init=function(){if(TREE.option.ajaxUrl){TREE.setAjaxNodes($(TREE),null,true);}};TREE.init();});}
function extendAllNFPNodes(nfpTreeObject,fnBegin,fnEnd){if(!nfpTreeObject||nfpTreeObject.find("li[level][class*='close']").size()==0)
return;if(typeof(fnBegin)=='function')
fnBegin();nfpTreeObject.attr("openAll","true");var maxIndex=-1;if(nfpTreeObject.find("ul.ajax").size()==0){maxIndex=nfpTreeObject.find("li[level][class*='close']").size()-1;}
setTimeout(function(){nfpTreeObject.find("li[level][class*='close']").each(function(i){$(this).find("img.trigger:first").trigger("click");if(maxIndex==i){nfpTreeObject.removeAttr("openAll");if(typeof(fnEnd)=='function')
setTimeout(fnEnd,500);}});},50);}
function collapseAllNFPNodes(nfpTreeObject,fnBegin,fnEnd){if(!nfpTreeObject||nfpTreeObject.find("li[level][class*='open']").size()==0)
return;if(typeof(fnBegin)=='function')
fnBegin();setTimeout(function(){var maxIndex=nfpTreeObject.find("li[level][class*='open']").size()-1;nfpTreeObject.find("li[level][class*='open']").each(function(i){$(this).find("img.trigger:first").trigger("click");if(maxIndex==i&&typeof(fnEnd)=='function'){setTimeout(fnEnd,500);}});},50);}
$.fn.nfpCommTree=function(opt){return this.each(function(){if(!this.id)
return;var COMMTREE=this;var isIE6=$.browser.msie&&$.browser.version=='6.0';var treeControl;var layerControl;COMMTREE.option={ajaxUrl:false,themeUrl:'',paramsName:false,animate:true,autoclose:false,checkbox:false,docToFolderConvert:true,drag:false,expandLevel:1,speed:200,nodesImage:false,useCache:false,afterAjax:false,afterMove:false,afterDblClick:false,afterContextMenu:false,afterExtendAll:false,direction:"down",dropSpeed:200,dropHeight:200,dropWidth:$(COMMTREE).width()+2,dropStyle:false,dropZIndex:9999,afterChange:false};COMMTREE.option=$.extend(COMMTREE.option,opt);COMMTREE.initTree=function(){if(!treeControl.attr("isInited")){var control=$(COMMTREE);control.attr("nfpCommTree","1");if(control.attr("checkedValue"))
treeControl.attr("initValue",control.attr("checkedValue"));treeControl.nfpTree({drag:COMMTREE.option.drag,afterMove:COMMTREE.option.afterMove,docToFolderConvert:COMMTREE.option.docToFolderConvert,ajaxUrl:COMMTREE.option.ajaxUrl+($(COMMTREE).attr("company")? "?companyId="+$(COMMTREE).attr("company"):""),themeUrl:COMMTREE.option.themeUrl,paramsName:COMMTREE.option.paramsName,useCache:COMMTREE.option.useCache,expandLevel:COMMTREE.option.expandLevel,animate:COMMTREE.option.animate,autoclose:COMMTREE.option.autoclose,checkbox:COMMTREE.option.checkbox,speed:COMMTREE.option.speed,afterAjax:COMMTREE.option.afterAjax,afterClick:function(node){if(COMMTREE.option.checkbox){var checkBoxObj=node.find(":checkbox[tag='nfpTree']");COMMTREE.commTreeChecked(checkBoxObj,node);}else{var oldValue=control.attr("checkedValue");control.attr("checkedValue",node.attr("id").substr(COMMTREE.id.length+4));control.val(node.find("[isNodeText='true']").html());COMMTREE.hideLayer();control.trigger("blur");if(typeof(COMMTREE.option.afterChange)=='function'&&oldValue!=control.attr("checkedValue"))
COMMTREE.option.afterChange(control.attr("checkedValue"));}},afterDblClick:COMMTREE.option.afterDblClick,afterContextMenu:COMMTREE.option.afterContextMenu,afterExtendAll:COMMTREE.option.afterExtendAll,nodesImage:COMMTREE.option.nodesImage,isCommTree:true});treeControl.attr("isInited","true");}}
COMMTREE.commTreeChecked=function(jCheckBox,node){var checkedValue=jCheckBox.val();var checkedText=node.find("[isNodeText='true']").html();var control=$(COMMTREE);var controlText=control.val();var controlValue=control.attr("checkedValue")?control.attr("checkedValue"):"";if(jCheckBox.attr("checked")){var splitTag=control.attr("checkedValue")?";":"";control.val(controlText+splitTag+checkedText);control.attr("checkedValue",controlValue+splitTag+checkedValue);}else{var valueArray=controlValue.split(';');var checkedIndex=-1;for(var i=0;i<valueArray.length;i++){if(valueArray[i]==checkedValue){checkedIndex=i;break;}}
if(controlValue==checkedValue){control.val("");control.removeAttr("checkedValue");}else{if(checkedIndex==0){control.val(controlText.substr(checkedText.length+1));control.attr("checkedValue",controlValue.substr(checkedValue.length+1));}
else if((checkedIndex+1)==valueArray.length){control.val(controlText.substr(0,controlText.length-checkedText.length-1));control.attr("checkedValue",controlValue.substr(0,controlValue.length-checkedValue.length-1));}
else{checkedText=";"+checkedText+";";checkedValue=";"+checkedValue+";";control.val(controlText.substr(0,controlText.indexOf(checkedText))+controlText.substr(controlText.indexOf(checkedText)+checkedText.length-1));control.attr("checkedValue",controlValue.substr(0,controlValue.indexOf(checkedValue))+controlValue.substr(controlValue.indexOf(checkedValue)+checkedValue.length-1));}}}
if(typeof(COMMTREE.option.afterChange)=='function')
COMMTREE.option.afterChange(control.attr("checkedValue"));}
COMMTREE.setRelativePosition=function(){var layer_div=$("#"+COMMTREE.id+"Layer");if(isIE6)
layer_div=$("#"+COMMTREE.id+"Layer,#"+COMMTREE.id+"Frame");var control=$("#"+COMMTREE.id);layer_div.css("left",control.offset().left+"px");if(COMMTREE.option.direction=="up")
layer_div.css("top",(control.offset().top-layer_div.height())+"px");else
layer_div.css("top",(control.offset().top+control.height()+5)+"px");}
COMMTREE.showLayer=function(){$(document.body).find("div[id$='Layer']:not([id='"+COMMTREE.id+"Layer'])").hide();COMMTREE.setRelativePosition();if($.browser.mozilla||COMMTREE.option.direction=="up")
layerControl.fadeIn(COMMTREE.option.dropSpeed,COMMTREE.initTree);else
layerControl.slideDown(COMMTREE.option.dropSpeed,COMMTREE.initTree);}
COMMTREE.hideLayer=function(){layerControl.hide();}
COMMTREE.init=function(){var control=$(COMMTREE);control.attr({autocomplete:"off",readonly:"true"});control.css("cursor","pointer");var dropHeight=COMMTREE.option.dropHeight;var dropSpeed=COMMTREE.option.dropSpeed;var dropWidth=COMMTREE.option.dropWidth;var dropStyle=COMMTREE.option.dropStyle?COMMTREE.option.dropStyle:"border:solid 1px #A5B3C5;position:absolute;background-color: white; width:"+dropWidth+"px;display:none; z-index:"+COMMTREE.option.dropZIndex+"; height:"+dropHeight+"px;";var direction=COMMTREE.option.direction=="up"?"up":"down";var iframeDiv="<iframe id='"+COMMTREE.id+"Frame' frameborder='no' style='display:none;border:none;width:"+dropWidth+"px;display;none;left:0px;top:0px; position:absolute; height:"+dropHeight+"px;'></iframe>";var lodingDiv=COMMTREE.option.themeUrl?"<div class='ajaxLoading' id='nfpLoading'></div>":"";var treeDiv="<ul id='"+COMMTREE.id+"Tree'>"+lodingDiv+"</ul>";treeDiv="<div style='width:"+dropWidth+"px; height:"+dropHeight+"px;overflow:auto;'>"+treeDiv+"</div>";var dropDiv="<div style='overflow:auto;"+dropStyle+"' id='"+COMMTREE.id+"Layer'>"+treeDiv+"</div>";$(document.body).append((isIE6?iframeDiv:"")+dropDiv);treeControl=$("#"+COMMTREE.id+"Tree");layerControl=$("#"+COMMTREE.id+"Layer");if(isIE6){layerControl=$("#"+COMMTREE.id+"Layer,#"+COMMTREE.id+"Frame");layerControl.hide();}
$(document.body).click(function(event){COMMTREE.hideLayer();});$("#"+COMMTREE.id+",#"+COMMTREE.id+"Tree,"+"#"+COMMTREE.id+"Layer").click(function(event){event.stopPropagation();event.preventDefault();});$(window).resize(function(){if(layerControl.css("display")!="none")
COMMTREE.setRelativePosition();});control.focus(function(event){COMMTREE.showLayer();});control.keydown(function(event){event.stopPropagation();event.preventDefault();if(event.keyCode==8||event.keyCode==46){treeControl.removeAttr("initValue");if(COMMTREE.option.checkbox)
layerControl.find(":checkbox[tag='nfpTree']").attr("checked",false);else
layerControl.find("span.active").attr("class","text");var oldValue=$("#"+COMMTREE.id).attr("checkedValue");control.val("");control.removeAttr("checkedValue");if(typeof(COMMTREE.option.afterChange)=='function'&&oldValue!=control.attr("checkedValue"))
COMMTREE.option.afterChange(control.attr("checkedValue"));}});};$(COMMTREE).attr("nfpTag","1");COMMTREE.init();});}
function reloadCommTree(controlId){$("#"+controlId+"Tree").removeAttr("isInited");$("#"+controlId+"Tree").html("<div class='ajaxLoading' id='nfpLoading'></div>");nfpTreeCacheParams=new Array(new Array(),new Array());nfpTreeCacheSource=new Array(new Array(),new Array());nfpTreeCacheUrl=new Array(); nfpCurrentTree=new Array();}
function is360(){try{if(window.navigator.userAgent.toLowerCase().indexOf("360se")>=1){return true;}if(window.external&&window.external.twGetRunPath){var r=external.twGetRunPath();if(r&&r.toLowerCase().indexOf("360se")>-1)return true;}return false;}catch(err){return false;}}