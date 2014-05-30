var obj;//选中表格对象
var tar_inx;//位移数
var flag;//是否拖动
var interv=null;//时间序号
//鼠标抬起时初始化对象
document.onmouseup=nfs_mouseUp;
/*
 * 鼠标按下时事件
 */
function nfs_mouseDown(oevent){
	//获得事件对象
	if(window.event)oevent=window.event;
	//浏览器兼容转换
	if(oevent.srcElement){
		//IE
		obj = oevent.srcElement;
	}else{
		//DOM
		obj = oevent.target;
	}
	//获得选中表格
	if(obj.tagName=="TD") obj=obj.parentNode;           //如果是单元格
	if(obj.tagName!="TR") return false;                 //如果是单元行
	if(obj.rowIndex==0) return false; 
	//指针样式
	obj.style.cursor = "move";
	//移动横坐标
  	obj.mouseDownX=oevent.clientX+document.body.scrollLeft; 
  	//拖动开始
  	flag=true;
} 
/*
 * 鼠标抬起时事件
 */
function nfs_mouseUp(){
 //flag存在并且拖动开始时
 if(flag!="undefined"&&flag){
  //位置初始化
  flag=false;
  obj.style.top=0+"px";   
  obj.style.left=0+"px"; 
  //指针样式
  obj.style.cursor = "pointer";
  obj=null;
  //初始化屏幕滚动
  window.clearInterval(interv);
  interv=null;
 }
}   
/*
 * 鼠标移动时事件
 */
function nfs_dragover(oevent){                //鼠标拖动时的操作
 //flag存在并且拖动开始时
 if(flag!="undefined"&&flag){
  //获得事件对象
  oevent = fix(oevent);
  //鼠标未按下时,初始化并返回
  if(oevent.which == 0){
	  nfs_mouseUp();
	  return;
  }
  var p = obj.parentNode;//tbody对象
  //计算位移数
  tar_inx=Math.floor((oevent.clientY+getPageOffset()[1]-getPos(obj)[0])/obj.offsetHeight);
  //拖动的单元格的横位移
  obj.style.left= oevent.clientX-obj.mouseDownX+"px";
  //寻找当前拖动对象在子节点中的序号
  for(var i=0;i<p.childNodes.length;i++){
	  if(obj==p.childNodes[i]){
		  //浏览器是IE或OPERA
		  if(navigator.userAgent.indexOf('MSIE')>0||navigator.userAgent.indexOf('Presto')>0){
			//下移时
		  	if(tar_inx>0){
		  		//未到最后一个表格时,插入
				if(i+tar_inx+1<p.childNodes.length){
					p.insertBefore(p.childNodes[i],p.childNodes[i+tar_inx+1]);
				}
				//超出表格长度,移动到最后
				else{
				  	p.appendChild(p.childNodes[i]);
				}
			//上移时插入
			}else if(tar_inx<0){
			  	p.insertBefore(p.childNodes[i],p.childNodes[i+tar_inx]);
			}
		  	//设定当前对象事件纵坐标
			p.childNodes[i].lastMouseY=oevent.clientY;
			break;
		//DOM浏览器
	  	}else{
	  		tar_inx=tar_inx*2;
		  	if(tar_inx>0){
				if(i+tar_inx+2<p.childNodes.length){
					p.insertBefore(p.childNodes[i],p.childNodes[i+tar_inx+2]);
				}else{
				  	p.appendChild(p.childNodes[i]);
				}
			}else if(tar_inx<0){
			  	p.insertBefore(p.childNodes[i],p.childNodes[i+tar_inx]);
			}
		  	//鼠标控制控件的最后纵坐标
		  	obj.lastMouseY=oevent.clientY;
			break;
	  	}
	  }
  }
  //滚屏控制
  scroll();
 }
}
/*
 * 得到控件的绝对位置
 */
function getPos(cell)
{
var pos = new Array();
var t=cell.offsetTop;
var l=cell.offsetLeft;
while(cell=cell.offsetParent)	
{
t+=cell.offsetTop;
l+=cell.offsetLeft;
}
pos[0] = t;
pos[1] = l;
return pos;

}
/*
 * 获得当前页面的滚动横纵长度
 */
function getPageOffset() {
    var a = 0;
    var b = 0;
    if(typeof window.pageYOffset == "number") {
        a = window.pageXOffset;
        b = window.pageYOffset;
    } else if(document.body && (document.body.scrollLeft || document.body.scrollTop)) {
        a = document.body.scrollLeft;
        b = document.body.scrollTop;
    } else if(document.documentElement && (document.documentElement.scrollLeft || document.documentElement.scrollTop)) {
        a = document.documentElement.scrollLeft;
        b = document.documentElement.scrollTop;
    }
    return [a,b];
}
/*
 * 获得当前页面的可见高度
 */
function getClientHeight() {
    var a;
    if(window.innerHeight) {
        a = window.innerHeight;
    } else if(document.documentElement && document.documentElement.clientHeight) {
        a = document.documentElement.clientHeight;
    } else {
        a = document.body.offsetHeight;
    }
    if( a < document.body.clientHeight) {
        return a;
    }
    return document.body.clientHeight;
}
/*
 * 控制屏幕滚动
 */
function scroll() {
   if (obj!=null)
   {
    var b;//网页最大高度
    if(document.body.scrollHeight > document.documentElement.clientHeight)
       b = document.body.scrollHeight;
    else
       b = document.documentElement.clientHeight;
    //网页高度
    var c = getClientHeight();
    //滚动条当前位置距顶部高度
    var sclTop = getPageOffset()[1];
    //偏移量
    var f = 8;
    //触发滚动长度(1/20浏览器高度)
    var g = 0.05 * c;
    //滚动高度
    var h = sclTop;
    //向上滚动
    if(obj.lastMouseY <= g) {
       h = sclTop - f;
       var j = h - sclTop;
       if(j!=0&&interv==null) {
    	   //屏幕开始滚动
    	   interv=window.setInterval(function(){window.scrollBy(0, j)},10);
       }
    }
    //向下滚动
    else if(obj.lastMouseY >= c - g) {
       h = Math.min(b - c,sclTop + f);
       var j = h - sclTop;
       if(j!=0&&interv==null) {
    	   interv= window.setInterval(function(){window.scrollBy(0, j)},10);
       }
    }//移出滚动位置时
    else{
    	//屏幕停止滚动并初始化
    	window.clearInterval(interv);
		interv=null;
    }
   }
}
/*
 * 浏览器兼容转换
 */
function fix(oevent) {
    if (typeof oevent == "undefined") {
    	oevent = window.event;
    }
    if (typeof oevent.which == "undefined") {
    	oevent.which = oevent.button;
    }
    return oevent;
}