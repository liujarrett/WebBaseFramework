package com.base.core.utilities;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author yanxin
 * @version 2011-8-1
 */
public class DeleteFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7431603423471454618L;

	/**
	 * Constructor of the object.
	 */
	public DeleteFileServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ResultSet rs=null;

		String filePath = request.getParameter("filePath");
		String prjPath = request.getSession().getServletContext().getRealPath("/");
		File file = new File(prjPath+filePath);
		if(file.exists()){
			file.delete();
			 try {
					Class.forName("net.sourceforge.jtds.jdbc.Driver");
				    String url="jdbc:jtds:sqlserver://192.168.1.12:1433;DatabaseName=NFP";
				    String user="xiangxiang";
				    String password="xiangxiang";
				    java.sql.Connection con=DriverManager.getConnection(url,user,password);

				    String filename=prjPath.toString()+filePath.toString();
				   String subStr= filename.substring(filename.lastIndexOf("\\"));
				   subStr=subStr.substring(1);
				    String selsql="select * from tb_UploadRecord where FileName= '"+subStr+"'";
				    PreparedStatement ps=con.prepareStatement(selsql);
				    rs=ps.executeQuery();
				    Integer id=null;
				    while(rs.next())
				    {
				    	id=rs.getInt("RecordID");
				    }

				    System.out.println(rs.getRow());
				    String delsql="delete from tb_UploadRecord where RecordID= "+ id;
				    PreparedStatement psdel=con.prepareStatement(delsql);
				    psdel.executeUpdate();
				    ps.close();
				    con.close();

				 	} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
	}

	/**e
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
