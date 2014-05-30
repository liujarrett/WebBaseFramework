/**
 * 文件名：LevelCodeUtil.java
 *
 * 版本信息：
 * 日期：2011-4-2
 * Copyright 黑龙江中科方德软件有限公司 2011
 *
 */
package com.base.core.utilities;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
*
* 项目名称：nfp_hlj
* 类名称：LevelCodeUtil
* 类描述：层级编码基础类
* 创建人：kangqi
* 创建时间：2011-4-2
* 修改人：
* 修改时间：
* 修改备注：
* @version
*
*/
public class LevelCodeUtil extends HibernateDaoSupport {
	private int codeLength = 3;// 每一层级编码长度
	private int startIndex=0; //开始Index,用于递归取值
	private int endIndex=0;	  //结尾Index,用于递归取值

	/**
	 * 根据层级编码list获取空位层级编码
	 * 规则:hqlString唯一的一列必须为levelCode,必须按levelCode升序排列
	 * @param name list 获取层级编码HQL语句
	 * @param name parentLevelCode 父级层级编码
	 * @return	空位层级编码
	 */
	public String queryLevelCodeByHql(List<Object> list, String parentLevelCode) {
		int listSize=list.size();
		//判断开始值是否存在,
		if (listSize == 0 || getLevelCodeNum(list.get(0).toString())!=1)
			return parentLevelCode + formatLevelCode("1");
		else {
			//获取当前存在的最后一位的INT值,判断是否存在空位
			Integer lastCodeNum = getLevelCodeNum(list.get(listSize - 1).toString());
			if (listSize == lastCodeNum) {
				return parentLevelCode + formatLevelCode(lastCodeNum + 1 + "");
			}else{
				//存在空位,获取空位
				return parentLevelCode + loopGetMaxLevelCode(list,listSize);
			}
		}
	}
	
	
	/**
	 * 递归获取空位层级编码
	 * @param name list 层级编码数据集
	 * @param name endSize 标记
	 * @return	空位层级编码
	 */
	public String loopGetMaxLevelCode(List<Object> list,int endSize){
		//取结尾size与开始size的中间值
		endIndex=(endSize - startIndex)/2 + startIndex;
		if(endIndex==getLevelCodeNum(list.get(endIndex-1).toString())){
			//位于后半段
			startIndex=endIndex;
			endIndex=endSize;
			//空位位于边界值,取值
			if(startIndex+1!=getLevelCodeNum(list.get(startIndex).toString()))
				return formatLevelCode(getLevelCodeNum(list.get(startIndex).toString())-1+"");
		}
		else{
			//位于前半段
			endSize=endIndex;
		}
		//已锁定空位,取值
		if(endIndex - startIndex==2)
			return formatLevelCode(getLevelCodeNum(list.get(startIndex).toString())+1+"");
		else
			//递归对半取值
			return loopGetMaxLevelCode(list,endSize);
	}
	
	/**
	 * 根据codeLength截取层级编码最后位数,转换成INT返回
	 * @param name levelCode 层级编码
	 * @return	最后codeLength位的INT值
	 */
	public Integer getLevelCodeNum(String levelCode){
		return Integer.parseInt(levelCode.substring(levelCode.length() - codeLength));
	}

	/**
	 * 根据codeLength给传入参数自动补零(例:传入"1",codeLength=3,返回"001")
	 * @param name levelCode 格式化前层级编码
	 * @return	格式后前层级编码
	 */
	public String formatLevelCode(String levelCode) {
		while (levelCode.length() < codeLength) {
			levelCode = "0" + levelCode;
		}
		return levelCode;
	}
}
