package com.lock.unlockInfo.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.core.model.HttpResponseModel;
import com.base.core.utilities.SJGlobalFunction;
import com.base.web.user.User;
import com.base.web.user.service.UserService;
import com.lock.unlockInfo.UnlockInfo;
import com.lock.unlockInfo.service.UnlockInfoService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet implementation class SubmitUnlockInfo
 */
public class SubmitUnlockInfo extends HttpServlet
{
	private static final long serialVersionUID=1L;

	private static ApplicationContext context;
	static
	{
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public SubmitUnlockInfo()
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

			/*获取参数*/
			String userId=SJGlobalFunction.getHttpServletRequestParameter(request,"UserId");
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
			String unlockType=SJGlobalFunction.getHttpServletRequestParameter(request,"UnlockType");
			String unlockTime=SJGlobalFunction.getHttpServletRequestParameter(request,"UnlockTime");
			String unlockLocation=SJGlobalFunction.getHttpServletRequestParameter(request,"UnlockLocation");
			String customerName=SJGlobalFunction.getHttpServletRequestParameter(request,"CustomerName");
			String customerIdNo=SJGlobalFunction.getHttpServletRequestParameter(request,"CustomerIdNo");

			//
			UserService userService=(UserService)context.getBean("userService");
			User user=userService.queryByPK(Long.valueOf(userId));
			
			//
			UnlockInfo unlockInfo=new UnlockInfo();
			unlockInfo.setUser(user);
			unlockInfo.setUserImei(userIMEI);
			unlockInfo.setUserImsi(userIMSI);
			unlockInfo.setUserMobilePhone(userMobilePhone);
			unlockInfo.setUserDeviceBrand(userDeviceBrand);
			unlockInfo.setUserDeviceModel(userDeviceModel);
			unlockInfo.setUserDeviceVersion(userDeviceVersion);
			unlockInfo.setUserDeviceOs(userDeviceOS);
			unlockInfo.setUserDeviceVersion(userDeviceOSVersion);
			unlockInfo.setUnlockType(unlockType);
			unlockInfo.setUnlockTime(unlockTime);
			unlockInfo.setUnlockLocation(unlockLocation);
			unlockInfo.setCustomerName(customerName);
			unlockInfo.setCustomerIdNo(customerIdNo);
			
			UnlockInfoService unlockInfoService=(UnlockInfoService)context.getBean("unlockInfoService");
			Serializable serializable=unlockInfoService.insert(unlockInfo);
			if(serializable==null)
			{
				responseModel.responseCode="0";// 
			}
			else
			{
				responseModel.responseCode="1";// 
				responseModel.responseMessage=String.valueOf(unlockInfo.getId());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			responseModel.responseCode="0";
			responseModel.responseMessage=e.toString();
		}
		/* 发送响应 */
		response.setHeader("content-type","text/json;charset=utf-8");
		response.getOutputStream().write(responseModel.toByteArray());
	}

}
