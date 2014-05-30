package com.base.core.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * @author yanxin
 * @version 2011-8-1
 */
public class FileUploadServlet extends HttpServlet
{

	private static final long serialVersionUID=-3096800116651263134L;

	private String fileSizeLimit;
	private File oldFile;

	public void init(ServletConfig config) throws ServletException
	{
		this.fileSizeLimit=config.getInitParameter("fileSizeLimit");
	}

	public void destroy()
	{
		this.fileSizeLimit=null;
		super.destroy();
	}

	class MyFileRenamePolicy implements FileRenamePolicy
	{
		public File rename(File file)
		{
			oldFile=file;
			String fileSaveName=StringUtils.join(new String[]{java.util.UUID.randomUUID().toString(),".",FilenameUtils.getExtension(file.getName())});
			File result=new File(file.getParentFile(),fileSaveName);
			return result;
		}
	}

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		System.out.println("--- BEGIN DOPOST ---");
		//UpLoadOrDownService upLoadService = (UpLoadOrDownService) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("upLoadOrDownService");
		HttpSession session=request.getSession();
		String str="";
		String ids="";
		if(session.getAttribute("filename")!=null)
		{
			str=session.getAttribute("filename").toString();
		}
		if(session.getAttribute("ids")!=null)
		{
			ids=session.getAttribute("ids").toString();
		}
		//文件保存位置，当前项目下的upload/mail
		String uploadDir="upload"+File.separatorChar+"mail"+File.separatorChar;
		//每天上传的文件根据日期存放在不同的文件夹
		String autoCreatedDateDirByParttern="yyyy"+File.separatorChar+"MM"+File.separatorChar+"dd"+File.separatorChar;
		String autoCreatedDateDir=DateFormatUtils.format(new java.util.Date(),autoCreatedDateDirByParttern);
		String ctxDir=session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if(!ctxDir.endsWith(String.valueOf(File.separatorChar)))
		{
			ctxDir=ctxDir+File.separatorChar;
		}
		File savePath=new File(ctxDir+uploadDir+autoCreatedDateDir);
		if(!savePath.exists())
		{
			savePath.mkdirs();
		}
		System.out.println(savePath.getName()+"======");
		String saveDirectory=ctxDir+uploadDir+autoCreatedDateDir;

		if(StringUtils.isBlank(this.fileSizeLimit.toString()))
		{
			this.fileSizeLimit="80";// 默认100M
		}
		int maxPostSize=Integer.parseInt(this.fileSizeLimit)*1024*1024;
		String encoding="UTF-8";
		FileRenamePolicy rename=new MyFileRenamePolicy();
		MultipartRequest multi=null;
		try
		{
			multi=new MultipartRequest(request,saveDirectory,maxPostSize,encoding,rename);
			System.out.println(oldFile.getName()+"==================");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
		}
		String fileSavePath="";
		String lastFileName="";
		ResultSet rs=null;
		// 输出
		Enumeration files=multi.getFileNames();
		while(files.hasMoreElements())
		{
			String name=(String)files.nextElement();
			File f=multi.getFile(name);
			System.out.println(oldFile.getName()+"*****************");
			System.out.println(f.getName());
			if(f!=null)
			{
				String fileName=multi.getFilesystemName(name);
				lastFileName=saveDirectory+fileName;
				fileSavePath=uploadDir+autoCreatedDateDir+fileName;
				System.out.println("SimpleUploaderServlet");
				System.out.println("文件地址:"+lastFileName);
				System.out.println("保存路径:"+fileSavePath);
				response.getWriter().print(fileSavePath);
			}
			try
			{
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				String url="jdbc:jtds:sqlserver://192.168.1.12:1433;DatabaseName=NFP";
				String user="xiangxiang";
				String password="xiangxiang";
				java.sql.Connection con=DriverManager.getConnection(url,user,password);

				String addsql="insert into tb_UploadRecord(OldFileName,FileName,FilePath,CreatedTime)values('"+oldFile.getName()+"','"+f.getName()+"','"+lastFileName+"','"+SJDateUtil.getCurrentTimeStamp()+"')";
				PreparedStatement ps=con.prepareStatement(addsql);
				ps.executeUpdate();
				PreparedStatement psmt=con.prepareStatement("select @@identity");

				Integer id=null;
				rs=psmt.executeQuery();
				if(rs.next())
				{
					id=rs.getInt(1);
				}

				ps.close();
				con.close();
				str=oldFile.getName()+","+f.getName()+"|"+str;
				ids=id+","+ids;
			}
			catch(Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		session.setAttribute("filename",str);
		session.setAttribute("ids",ids);
		System.out.println("--- END DOPOST ---");
	}

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		this.doPost(request,response);
	}

	public String getFileSizeLimit()
	{
		return fileSizeLimit;
	}

	public void setFileSizeLimit(String fileSizeLimit)
	{
		this.fileSizeLimit=fileSizeLimit;
	}

}
