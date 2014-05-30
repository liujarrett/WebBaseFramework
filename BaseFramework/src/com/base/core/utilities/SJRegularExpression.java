/**
 * @author liu_sijia
 * @date 2012-10-30
 */
package com.base.core.utilities;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SJRegularExpression
{
	@SuppressWarnings("unused")
	private static final String TAG=SJRegularExpression.class.getSimpleName();

	SJRegularExpression()
	{
	}

	public static Matcher getMatcher(String aRegEx,String aSource,boolean isIgnoreCase)
	{
		try
		{
			Pattern p;
			if(isIgnoreCase)
			{
				p=Pattern.compile(aRegEx,Pattern.CASE_INSENSITIVE);// 忽略大小写
			}
			else
			{
				p=Pattern.compile(aRegEx);// 不忽略大小写
			}
			Matcher matcher=p.matcher(aSource);
			return matcher;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static boolean find(String aRegEx,String aSource,boolean isIgnoreCase)
	{
		try
		{
			// 查询：
			// 如果str中有regEx，那么rs为true，否则为flase。
			// regEx="a|f"; // 表示a或f
			// str="abc efg ABC";
			Matcher m=getMatcher(aRegEx,aSource,isIgnoreCase);
			if(m!=null)
			{
				return m.find();
			}
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String replace(String aRegEx,String aOrigin,boolean isIgnoreCase)
	{
		try
		{
			// 替换（删除）：
			// regEx="a+"; // 表示一个或多个a
			// str="aaabbced a ccdeaa";
			Matcher m=getMatcher(aRegEx,aOrigin,isIgnoreCase);
			String s=m.replaceAll("A");
			// 结果为"Abbced A ccdeA"
			// 如果想删除：
			// String s=m.replaceAll("");
			// 结果为"bbced ccde"
			return s;
		}
		catch(Exception e)
		{
			return aOrigin;
		}
	}

	public String[] split(String aRegEx,String aOrigin,boolean isIgnoreCase)
	{
		try
		{
			// 分割：
			// regEx="::";
			// str="xd::abc::cde";
			Pattern p;
			if(isIgnoreCase)
			{
				p=Pattern.compile(aRegEx,Pattern.CASE_INSENSITIVE);// 忽略大小写
			}
			else
			{
				p=Pattern.compile(aRegEx);// 不忽略大小写
			}
			String[] sa=p.split(aOrigin);// 执行后，sa就是{"xd","abc","cde"}
			// 其实分割还有更简单的方法：
			// String[] sa=str.split("::");
			return sa;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 分成多行
	 */
	public static Hashtable<String,String> parseConfigInfo(String aConfigInfo)
	{
		try
		{
			Hashtable<String,String> htConfigInfo=new Hashtable<String,String>();// 配置key值,配置value值
			Matcher matcher=getMatcher("#[^\n]+",aConfigInfo,true);// #[^\n]+ 以#开始，到行尾。
			while(matcher.find())
			{
				String sLine=matcher.group().substring(1).trim();
				int index=sLine.indexOf('=');
				if(index>0)
				{
					String key=sLine.substring(0,index).trim();
					String value=sLine.substring(index+1,sLine.length()).trim();
					htConfigInfo.put(key,value);
				}
			}
			// 打印
			// for(Iterator<String> it=htConfigInfo.keySet().iterator();it.hasNext();)
			// {
			// String key=(String)it.next();
			// String value=(String)htConfigInfo.get(key);
			// System.out.println(key+"="+value);
			// }
			return htConfigInfo;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
