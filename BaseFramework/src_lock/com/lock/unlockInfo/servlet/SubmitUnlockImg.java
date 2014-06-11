package com.lock.unlockInfo.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.base.core.Constants;
import com.base.core.model.HttpResponseModel;
import com.lock.unlockInfo.UnlockInfo;
import com.lock.unlockInfo.service.UnlockInfoService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet implementation class SubmitUnlockImg
 */
public class SubmitUnlockImg extends HttpServlet
{
	private static final long serialVersionUID=1L;

	private static ApplicationContext context;
	static
	{
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	public SubmitUnlockImg()
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
			boolean isMultipart=ServletFileUpload.isMultipartContent(request); // 检查输入请求是否为附件表单数据
			if(isMultipart)
			{
				Hashtable<String,String> htSubmitParam=new Hashtable<String,String>(); // 用于存放表单普通参数
				List<String> fileList=new ArrayList<String>();// 用于存放文件路径

				// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。
				// 执行解析后，所有的表单项目都保存在一个List中。
				// 并从list中获得上传的文件以及普通表单参数
				FileItemFactory factory=new DiskFileItemFactory();
				ServletFileUpload upload=new ServletFileUpload(factory);
				List<FileItem> items=upload.parseRequest(request);
				Iterator<FileItem> iterator=items.iterator();
				while(iterator.hasNext())
				{
					FileItem item=iterator.next();
					if(item.isFormField())
					{
						// 普通参数
						String sFieldName=item.getFieldName();
						String sFieldValue=item.getString("UTF-8");
						htSubmitParam.put(sFieldName,sFieldValue);
					}
					else
					{
						// 文件,存储到服务器的文件的名称
						String newFileName=System.currentTimeMillis()+"_"+UUID.randomUUID().toString()+".jpg";
						File filePath=new File(getServletConfig().getServletContext().getRealPath(Constants.File_Upload));
						if(!filePath.exists())
						{
							filePath.mkdirs();
						}
						File file=new File(filePath,newFileName);
						if(!file.exists())
						{
							file.createNewFile();
						}
						//将文件写到服务器上
						BufferedInputStream bis=new BufferedInputStream(item.getInputStream());
						BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
						byte[] b=new byte[1024];
						int length=0;
						while((length=bis.read(b))>0)
						{
							bos.write(b,0,length);
						}
						bos.close();
						bis.close();
						//获取到文件的url
						String fileUrl=request.getRequestURL().toString();
						fileUrl=fileUrl.substring(0,fileUrl.lastIndexOf("/")); //获取项目根目录URL
						fileUrl=fileUrl+Constants.File_Upload+"/"+newFileName;
						/**/
						fileList.add(fileUrl);
					}
				}

				//
				String unlockInfoId=htSubmitParam.get("UnlockInfoId");
				String imgType=htSubmitParam.get("ImgType");
				String newFileUrl=fileList.get(0);

				UnlockInfoService unlockInfoService=(UnlockInfoService)context.getBean("unlockInfoService");
				UnlockInfo unlockInfo=unlockInfoService.queryByPK(Long.valueOf(unlockInfoId));
				if(imgType.equals("customerIdImg"))
				{
					unlockInfo.setCustomerIdImg(newFileUrl);
				}
				else if(imgType.equals("customerDrivingLicenseImg"))
				{
					unlockInfo.setCustomerDrivingLicenseImg(newFileUrl);
				}
				else if(imgType.equals("customerVehicleLicenseImg"))
				{
					unlockInfo.setCustomerVehicleLicenseImg(newFileUrl);
				}
				else if(imgType.equals("customerBusinessLicenseImg"))
				{
					unlockInfo.setCustomerBusinessLicenseImg(newFileUrl);
				}
				else if(imgType.equals("customerIntroductionLetterImg"))
				{
					unlockInfo.setCustomerIntroductionLetterImg(newFileUrl);
				}
				else if(imgType.equals("unlockWorkOrderImg"))
				{
					unlockInfo.setUnlockWorkOrderImg(newFileUrl);
				}
				/**/
				unlockInfoService.update(unlockInfo);
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
