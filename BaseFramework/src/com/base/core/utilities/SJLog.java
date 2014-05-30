package com.base.core.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SJLog
{
	private final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static boolean isInfo=true;

	//普通日志打印
	public void i(Class<?> clazz,String method,String log)
	{
		this.i(clazz.getName(),method,log);
	}

	public void i(String tag,String method,String log)
	{
		if(isInfo)
		{
			Date date=new Date();
			System.out.println("["+sdf.format(date)+"] Information-->"+tag.substring(tag.lastIndexOf('.')+1)+"-->"+method+"--> "+log);
		}
	}

	//错误信息打印
	public void e(Class<?> clazz,String method,String e)
	{
		this.e(clazz.getName(),method,e);
	}

	public void e(String tag,String method,String e)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date=new Date();
		System.out.println("["+sdf.format(date)+"] Exception-->"+tag.substring(tag.lastIndexOf('.')+1)+"-->"+method+"--> "+e);
	}

	public void e(Class<?> clazz,String method,Exception e)
	{
		this.e(clazz.getName(),method,e);
	}

	public void e(String tag,String method,Exception e)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date=new Date();
		System.out.println("["+sdf.format(date)+"] Exception-->"+tag.substring(tag.lastIndexOf('.')+1)+"-->"+method+"--> "+e.toString());
		e.printStackTrace();
	}

}
