package com.base.core.utilities;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;

public class SJGlobalFunction
{

	public SJGlobalFunction()
	{
	}

	/***
	 * 获取MD5
	 * */
	public static String getMD5(String str)
	{
		try
		{
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] digest=md.digest(str.getBytes("UTF-8"));
			return String.valueOf(digest);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	/***
	 * 获取HttpServlet Request 参数
	 * */
	public static String getHttpServletRequestParameter(HttpServletRequest request,String parameter)
	{
		return request.getParameter(parameter)!=null?request.getParameter(parameter):"";
	}

}
