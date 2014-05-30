var isInit=false;
$(function(){
	if(!isInit)
		isInit=true;
	else
		return;
	//移到右边
	$('#add').click(function() {
	//获取选中的选项，删除并追加给对方
		$('#select1 option:selected').appendTo('#select2');
		//alert(document.getElementById("select2").length);
		validateSelect();
	});
	//移到左边
	$('#remove').click(function() {
		$('#select2 option:selected').appendTo('#select1');
		validateSelect();
	});
	//全部移到右边
	$('#add_all').click(function() {
		//获取全部的选项,删除并追加给对方
		$('#select1 option').appendTo('#select2');
		validateSelect();  
	});
	//全部移到左边
	$('#remove_all').click(function() {
		$('#select2 option').appendTo('#select1');
		validateSelect();
	});
	//双击选项
	$('#select1').dblclick(function(){ //绑定双击事件
		//获取全部的选项,删除并追加给对方
		$("option:selected",this).appendTo('#select2'); //追加给对方
		validateSelect();
	});
	
	//双击选项
	$('#select2').dblclick(function(){
	   $("option:selected",this).appendTo('#select1');
	   validateSelect();
	});
});
function validateSelect(){
	var theWidth=parseInt($("#mainTabDiv2").attr("tagWidth"));
	var theHeight=parseInt($("#mainTabDiv2").attr("tagHeight"));
	$('#select1,#select2').width(theWidth-18);
	$('#select1,#select2').height(theHeight-18);
	$("#select1 >option").each(function(){
		if($(this).html().length>=$("#mainTabDiv2").width()/10){
			$("#select1").css("width","auto");
			return;
		}
	});
	$("#select2 >option").each(function(){
		if($(this).html().length>=theWidth/10){
			$("#select2").css("width","auto");
			return;
		}
	});
	if($("#select1 >option").size()*15>theHeight){
   	 	$("#select1").height($("#select1 >option").size()*15+10);
   	 	$("#select1Div").height(theHeight);
    }
	if($("#select2 >option").size()*15>theHeight){
   	 	$("#select2").height($("#select2 >option").size()*15+10);
   	 	$("#select2Div").height(theHeight);
    }
	$("#select2Div,#select1Div").height(theHeight);
	$("#select2Div,#select1Div").width(theWidth);
}

$.fn.selLAndR=function(opt){

//alert((opt.select2Width.substring(0,opt.select2Width.indexOf('p')))/10);
//$('#select2').html("<option value='21'>选项21</option>");
$('#select2').height(opt.tabHeight);
$('#mainTabDiv,#select1Div,#select2Div').width(opt.tabWidth);
$('#select1,#select2').width(opt.tabWidth-18);
$('#mainTabDiv1,#mainTabDiv2,#mainTabDiv3,#mainTabDiv').height(opt.tabHeight);
$('#select1,#select2').height(opt.tabHeight-18);
$.ajax({
	 url:opt.ajaxUrl,   
     dataType:"JSON",    
	 data:eval('({"' + opt.paramName + '":'+opt.paramValue+'})'),
     success:function(msg){
		$("#select1").html("");
		//alert(dataObj);
       // var dataObj=eval("("+msg+")");//转换为json对象
       // alert(dataObj);
		//alert(opt.id);
		//alert(opt.name);
        //$('#test').text(msg); //实现提取数据并更换div中内容，不刷新页面。
       //alert(msg); 
       //var dataObj=eval("("+msg+")");//转换为json对象
        if(msg!=null){
	        var dataObj=eval("("+msg+")");//转换为json对象
	        var strUser1="";//存储userList中的信息
	        var strUser2="";//存储testUserList中的信息
	        var lengthFlag1="";
	        var lengthFlag2="";
	        var flag1=false;
	        var flag2=false;
        	
        	if(dataObj!=null){//判断后台传回来的数据是否为空
        		//alert("zou");
	         	$.each(dataObj,function(key,values){ //values包含userList 和 testUserList
		         	if(key=="userList"){//判断KEY名称
		         	//alert("userList:"+this.userId+"--"+values);
		         		if(values!=null){
		         			$("#mainTabDiv2").attr("tagWidth", opt.tabWidth).attr("tagHeight", opt.tabHeight);
		         			$(values).each(function(){  
					     	//alert(this.userName);
					     	//alert(this.userName.length);
					     	
					     	
					     	//for(var i=0;i<values.length;i++){//循环拿到values中的对象信息
					     		lengthFlag1=this.userName;
						     	if(this!=null){
					     			if(lengthFlag1.length>=opt.tabWidth/10){
						     			flag1=true;
						     		}
					     			if("User"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.userId+"' value='"+this.userId+"'>"+this.trueName+"</option>";
					     			}else if("Role"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.roleId+"' value='"+this.roleId+"'>"+this.roleName+"</option>";
					     			}else if("Region"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.regionId+"' value='"+this.regionId+"'>"+this.regionName+"</option>";
					     			}else if("Dictionary"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.dictiId+"' value='"+this.dictiId+"'>"+this.dictiName+"</option>";
					     			}else if("DictionaryType"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.dictiTypeId+"' value='"+this.dictiTypeId+"'>"+this.dictiTypeName+"</option>";
					     			}else if("Authority"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.authorityId+"' value='"+this.authorityId+"'>"+this.authorityName+"</option>";
					     			}else if("Company"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.companyId+"' value='"+this.companyId+"'>"+this.companyName+"</option>";
					     			}else if("Department"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.departmentId+"' value='"+this.departmentId+"'>"+this.departmentName+"</option>";
					     			}else if("Menu"==opt.returnValueType){
					     				strUser1=strUser1+"<option id='"+this.menuId+"' value='"+this.menuId+"'>"+this.menuName+"</option>";
					     			}else{
					     				strUser1="请检查您调用本控件传入参数的格式！";
					     			}
						     		
						     		//alert(strUser1);
						     		//break;
						     	}else{
				         			strUser1="";//没有信息 将select 的 option设置空
				         		}
					     	//}
					     });
		         		}else{
		         			strUser1="";//没有信息 将select 的 option设置空
		         		}
		         		//alert(flag1);
		         		if(flag1){
		         			$("#select2").html("");
		         			$("#select2").html(strUser1).css("width","auto");//向ID为resultDept的select下拉框中添加OPtion
					    }else{
					    	$("#select2").html("");
					    	$("#select2").html(strUser1);
					    }

		         	}else{
		         		if(values!=null){
			         		$(values).each(function(){  
						     	 //对后台传回的数据循环拿出
					     		lengthFlag2=this.userName;
						     	if(this!=null){
						     		 //alert(lengthFlag2.length);
						     		if(lengthFlag2.length>=opt.tabWidth/10){
						     			flag2=true;
						     		}
					     			if("User"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.userId+"' value='"+this.userId+"'>"+this.trueName+"</option>";
					     			}else if("Role"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.roleId+"' value='"+this.roleId+"'>"+this.roleName+"</option>";
					     			}else if("Region"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.regionId+"' value='"+this.regionId+"'>"+this.regionName+"</option>";
					     			}else if("Dictionary"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.dictiId+"' value='"+this.dictiId+"'>"+this.dictiName+"</option>";
					     			}else if("DictionaryType"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.dictiTypeId+"' value='"+this.dictiTypeId+"'>"+this.dictiTypeName+"</option>";
					     			}else if("Authority"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.authorityId+"' value='"+this.authorityId+"'>"+this.authorityName+"</option>";
					     			}else if("Company"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.companyId+"' value='"+this.companyId+"'>"+this.companyName+"</option>";
					     			}else if("Department"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.departmentId+"' value='"+this.departmentId+"'>"+this.departmentName+"</option>";
					     			}else if("Menu"==opt.returnValueType){
					     				strUser2=strUser2+"<option id='"+this.menuId+"' value='"+this.menuId+"'>"+this.menuName+"</option>";
					     			}else{
					     				strUser2="请检查您调用本控件传入参数的格式！";
					     			}
						     	}else{
				         			strUser2="";//没有信息 将select 的 option设置空
				         		}
						     });
					     }else{
					     	strUser2="";//没有信息 将select 的 option设置空
					     }
					     //alert(flag);
					     if(flag2){
					    	 $("#select1").html("");
						     $("#select1").html(strUser2).css("width","auto");//向ID为resultDept的select下拉框中添加OPtion
					     } else{
					    	 $("#select1").html("");
					    	 $("#select1").html(strUser2);
					     }
					     var oldHeight=$("#select1Div").height();
					     if($(values).size()*15>$("#select1Div").height()){
					    	 $("#select1").height($(values).size()*15+10);
					    	 $("#select1Div").height(oldHeight);
					     }
					    	
		         	}
				 }); 
				 
        	}else{
        		//alert("zou1");
        		
        		$("#select2").html("");//清空option
        	}
        }else{
        	//alert("kon");
        	$("#select2").html("");//清空option
        	$("#select1").html("");//清空option
        }
     }   
});
}