package com.base.core.utilities;

import java.util.ArrayList;
import java.util.List;

public class ExcelCells {
	public static String[] model = {"用户名称","真实姓名","身份证号","办公电话","移动电话","Email","家庭住址"};
	public static int maxPostSize=10000000 * 1024 * 1024;
	public static String strTips="";//导入错误提示语
	/**
	 * 
	 * 判断是否是数字
	 * @author chenjianyuan	
	 * @param str 需要判断的字符串
	 * @return 
	 * 
	 */
    public static boolean isNumber(String str){
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
        java.util.regex.Matcher match=pattern.matcher(str);
        if(match.matches()==false){
           return false;
        }else{
           return true;
        }
    }
	/**
	 * 
	 * 导入用分页
	 * @author chenjianyuan	
	 * @param page 当前页
	 * @param ids 需要分页的数据集合
	 * @param pageSize 每页多少条
	 * @return 
	 * 
	 */
	public static List<String> find(Integer page,List<String> ids,Integer pageSize){
		Integer srefpage=0;
		List<String> returnlist=new ArrayList<String>();
		if(page<=1){
			srefpage=0;
		}
		if((page-1)*pageSize>=ids.size()){
			srefpage=ids.size()/pageSize*pageSize;
			if(ids.size()%pageSize==0){
				srefpage-=pageSize;
			}
		}
		if(page>1 && (page-1)*pageSize<ids.size()){
			srefpage=(page-1)*pageSize;
		}
		for(int i=srefpage;i<ids.size()&& i<(srefpage+pageSize);i++){
			returnlist.add(ids.get(i));
		}
		return returnlist;
	}
}
