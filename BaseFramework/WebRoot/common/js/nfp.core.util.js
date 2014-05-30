/**
 *
 * 项目名称：nfp_hlj
 * JS库名称：nfp.core.util.js
 * JS库描述：NFP工具JS类库
 * 创建人：liuyang
 * 创建时间：2011-3-11 下午10:40:07
 * 修改人：liuyang
 * 修改时间：2011-3-11 下午10:40:07
 * 修改备注：
 * @version
 *
 */



/*
 * 用于处理特定格式的输入参数
 * 奇数序的输入参数（即第1、3、5……个输入参数）应该为窗体或框架对象
 * 偶数序的输入参数则为需要重定向的网页地址
 * 比如按以下方式调用此函数：MM_goToURL("window","http://www.sina.com")
 * 其作用就等效于 window.location='http://www.sina.com'
 * 函数中MM_goToURL.arguments就是表示 MM_goToURL的输入参数
 */
function MM_goToURL() { //v3.0
  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;
  for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");
}

