package com.base.web.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.core.model.HttpResponseModel;
import com.base.core.utilities.SJGlobalFunction;
import com.base.mobile.version.MobileVersion;
import com.base.mobile.version.service.MobileVersionService;
import com.base.web.user.User;
import com.base.web.user.service.UserService;
import com.google.gson.Gson;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserLoginServlet extends HttpServlet
{
	private static final long serialVersionUID=2272046803975545947L;

	private static ApplicationContext context;
	static
	{
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public UserLoginServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		HttpResponseModel responseModel=new HttpResponseModel();
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			// StringBuffer sbRequestURL=request.getRequestURL();
			// String sRequestURI=request.getRequestURI();
			// String sContextPath=request.getContextPath();
			// String sServletPath=request.getServletPath();
			// String sPathInfo=request.getPathInfo();
			// String sQueryString=request.getQueryString();
			// String url="http:/"+sRequestURI;
			// LogEngine.Debug(TAG,"doServlet()","sbRequestURL="+sbRequestURL.toString());
			// LogEngine.Debug(TAG,"doServlet()","sRequestURI="+sRequestURI);
			// LogEngine.Debug(TAG,"doServlet()","sContextPath="+sContextPath);
			// LogEngine.Debug(TAG,"doServlet()","sServletPath="+sServletPath);
			// LogEngine.Debug(TAG,"doServlet()","sPathInfo="+sPathInfo);
			// LogEngine.Debug(TAG,"doServlet()","sQueryString="+sQueryString);
			// LogEngine.Debug(TAG,"doServlet()","url="+url);
			// MainServlet.doServlet().sbRequestURL=http://10.180.2.54:8080/MAPS/MainServlet
			// MainServlet.doServlet().sRequestURI=/MAPS/MainServlet
			// MainServlet.doServlet().sContextPath=/MAPS
			// MainServlet.doServlet().sServletPath=/MainServlet
			// MainServlet.doServlet().sPathInfo=null

			/*获取头数据*/
			//String sSessionId=request.getHeader("Session-Id");

			/*获取参数*/
			String companyCode=SJGlobalFunction.getHttpServletRequestParameter(request,"CompanyCode");
			String userName=SJGlobalFunction.getHttpServletRequestParameter(request,"UserName");
			String password=SJGlobalFunction.getHttpServletRequestParameter(request,"Password");
			//
			String userIMEI=SJGlobalFunction.getHttpServletRequestParameter(request,"UserIMEI");
			String userIMSI=SJGlobalFunction.getHttpServletRequestParameter(request,"UserIMSI");
			String userMobilePhone=SJGlobalFunction.getHttpServletRequestParameter(request,"UserMobilePhone");
			String userDeviceBrand=SJGlobalFunction.getHttpServletRequestParameter(request,"UserDeviceBrand");
			String userDeviceModel=SJGlobalFunction.getHttpServletRequestParameter(request,"UserDeviceModel");
			String userDeviceVersion=SJGlobalFunction.getHttpServletRequestParameter(request,"UserDeviceVersion");
			String userDeviceOS=SJGlobalFunction.getHttpServletRequestParameter(request,"UserDeviceOS");
			String userDeviceOSVersion=SJGlobalFunction.getHttpServletRequestParameter(request,"UserDeviceOSVersion");

			//
			UserService userService=(UserService)context.getBean("userService");
			User u=userService.query(userName,password);
			if(u==null)
			{
				responseModel.responseCode="0";// 登录失败
				responseModel.responseMessage="用户名或密码错误！";
			}
			else
			{
				responseModel.responseCode="1";// 登录成功
				/**/
				UserLoginSucceed userLoginSucceed=new UserLoginSucceed();
				userLoginSucceed.userId=String.valueOf(u.getId());
				MobileVersionService mobileVersionService=(MobileVersionService)context.getBean("mobileVersionService");
				MobileVersion mobileVersion=mobileVersionService.getNewMobileVersion();
				if(mobileVersion!=null)
				{
					userLoginSucceed.mobileVersion.setVersionName(mobileVersion.getVersionName());
					userLoginSucceed.mobileVersion.setVersionCode(mobileVersion.getVersionCode());
					userLoginSucceed.mobileVersion.setDescription(mobileVersion.getDescription());
					userLoginSucceed.mobileVersion.setReleaseTime(mobileVersion.getReleaseTime());
					userLoginSucceed.mobileVersion.setFileUrl(mobileVersion.getFileUrl());
					userLoginSucceed.mobileVersion.setFileSize(mobileVersion.getFileSize());
				}
				responseModel.responseMessage=userLoginSucceed.toJson();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		/* 发送响应 */
		response.setHeader("content-type","text/json;charset=utf-8");
		response.getOutputStream().write(responseModel.toByteArray());
	}

	private static class UserLoginSucceed
	{
		public String userId="";
		public MobileVersion mobileVersion=new MobileVersion();

		public String toJson()
		{
			Gson gson=new Gson();
			return gson.toJson(this);
		}
	}
}
