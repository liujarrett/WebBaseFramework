/*
 * 	方法:判断表单中所有的 text,password,textarea控件输入内容是否合法(非法字符跳出提示并锁定该控件)
 *  参数:form控件
 *	返回值：	true:验证成功,false:验证失败
 */
function checkForm(objForm) {
	var length=objForm.elements.length;
	//循环验证所有输入标签
	for ( var i = 0; i < length; i++) {
		var objItem = objForm.elements[i];
		if(!checkText(objItem))
			return false;
	}
	return true;
}

 function isValidMail(sText) {
  var reMail = /^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/;
  return reMail.test(sText);
 }
/*
 * 	方法:判断text,password,textarea控件输入内容是否合法(非法字符跳出提示并锁定该控件)
 *  参数:text,password,textarea控件
 *	返回值：	true:验证成功,false:验证失败
 */
function checkText(objText) {
	//判断控件类型
	if (objText.type == "text" || objText.type == "password" || objText.type == "textarea") {
		if (objText.value.indexOf("'") != -1 && objText.type != "textarea") {//校验‘
			alert("提交的内容中含有非法字符『 ' 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("’") != -1) {//校验‘
			alert("提交的内容中含有非法字符『 ’ 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("select ") != -1) {//校验 select
			alert("提交的内容中含有非法字符『 select 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("insert ") != -1) {//校验 insert
			alert("提交的内容中含有非法字符『 insert 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf(" or ") != -1) {//校验 select
			alert("提交的内容中含有非法字符『 or 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf(" and ") != -1) {//校验 insert
			alert("提交的内容中含有非法字符『 and 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("%") != -1) {//校验 %
			alert("提交的内容中含有非法字符『 % 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("*") != -1) {//校验 *
			alert("提交的内容中含有非法字符『 * 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("+") != -1) {//校验 insert
			alert("提交的内容中含有非法字符『 + 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("update ") != -1) {//校验 update
			alert("提交的内容中含有非法字符『 update 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("delete ") != -1) {//校验 delete
			alert("提交的内容中含有非法字符『 delete 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("truncate") != -1) {//校验 truncate
			alert("提交的内容中含有非法字符『 truncate 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("declare") != -1) {//校验 delete
			alert("提交的内容中含有非法字符『 declare 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("exec") != -1) {//校验 exec
			alert("提交的内容中含有非法字符『 exec 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("master") != -1) {//校验 master
			alert("提交的内容中含有非法字符『 master 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("count") != -1) {//校验 count
			alert("提交的内容中含有非法字符『 count 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("&") != -1) {//校验 &
			alert("提交的内容中含有非法字符『 & 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("<") != -1) {//校验 <
			alert("提交的内容中含有非法字符『 < 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf(">") != -1) {//校验 >
			alert("提交的内容中含有非法字符『 > 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("#") != -1) {//校验 #
			alert("提交的内容中含有非法字符『 # 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf(" table ") != -1) {//校验 table
			alert("提交的内容中含有非法字符『 table 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("alter ") != -1) {//校验 alter
			alert("提交的内容中含有非法字符『 alter 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("chr") != -1) {//校验 chr
			alert("提交的内容中含有非法字符『 chr 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("char") != -1) {//校验 char
			alert("提交的内容中含有非法字符『 char 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		if (objText.value.indexOf("mid") != -1) {//校验 mid
			alert("提交的内容中含有非法字符『 mid 』，请重新填写！");
			objText.focus();
			objText.select();
			return false;
		}
		//textarea将半角'转换成全角＇
		if(objText.type == "textarea"){
			var temp = objText.value;
			var intquotation = 0;
			while (true) {
				intquotation = temp.indexOf("'");
				if (intquotation == -1) {
					break;
				}
				temp = temp.substring(0, intquotation) + "＇"
						+ temp.substring(intquotation + 1);
			}
			objText.value = temp;
		}
	}
	return true;
}