package com.base.web.user.action;

import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.core.utilities.OutExcel;
import com.base.core.utilities.SJDateUtil;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.log.LoginLog;
import com.base.web.log.service.LoginLogService;
import com.base.web.log.service.OperateLogService;
import com.base.web.permission.Permission;
import com.base.web.permission.Resource;
import com.base.web.permission.Role;
import com.base.web.permission.service.PermissionService;
import com.base.web.permission.service.RoleService;
import com.base.web.user.User;
import com.base.web.user.service.UserService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

public class UserAction extends BaseAction
{
	private static final long serialVersionUID=-7005835674200487450L;

	private String orginalPassword="c4ca4238a0b923820dcc509a6f75849b";
	private UserService userService;
	private User user;
	private PageBean<User> pageBean;
	private CompanyService companyService;
	private Company company;
	private List<Company> companyList;
	private RoleService roleService;
	private List<Role> roleList;
	private PermissionService permissionService;
	private String manuType;
	private String Ids;
	private int flag;
	private int loginStatus;
	//outExcel
	private long outExcelCompanyId;
	private String outExcelUserName;
	private String outExcelCurrentState;
	// 输出流
	private InputStream excelOrWordStream;
	// 导出文件名
	private String fileName;

	//***********日志相关 start ****************
	private LoginLogService loginLogService;
	private OperateLogService operateLogService;

	public LoginLogService getLoginLogService()
	{
		return loginLogService;
	}

	public void setLoginLogService(LoginLogService loginLogService)
	{
		this.loginLogService=loginLogService;
	}

	public OperateLogService getOperateLogService()
	{
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService)
	{
		this.operateLogService=operateLogService;
	}

	//***********日志相关 end ******************

	public String getOrginalPassword()
	{
		return orginalPassword;
	}

	public void setOrginalPassword(String orginalPassword)
	{
		this.orginalPassword=orginalPassword;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService=userService;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user=user;
	}

	public PageBean<User> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<User> pageBean)
	{
		this.pageBean=pageBean;
	}

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public List<Company> getCompanyList()
	{
		return companyList;
	}

	public void setCompanyList(List<Company> companyList)
	{
		this.companyList=companyList;
	}

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService=roleService;
	}

	public List<Role> getRoleList()
	{
		return roleList;
	}

	public void setRoleList(List<Role> roleList)
	{
		this.roleList=roleList;
	}

	public PermissionService getPermissionService()
	{
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService)
	{
		this.permissionService=permissionService;
	}

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public String getIds()
	{
		return Ids;
	}

	public void setIds(String ids)
	{
		Ids=ids;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public int getLoginStatus()
	{
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus)
	{
		this.loginStatus=loginStatus;
	}

	public long getOutExcelCompanyId()
	{
		return outExcelCompanyId;
	}

	public void setOutExcelCompanyId(long outExcelCompanyId)
	{
		this.outExcelCompanyId=outExcelCompanyId;
	}

	public String getOutExcelUserName()
	{
		return outExcelUserName;
	}

	public void setOutExcelUserName(String outExcelUserName)
	{
		this.outExcelUserName=outExcelUserName;
	}

	public String getOutExcelCurrentState()
	{
		return outExcelCurrentState;
	}

	public void setOutExcelCurrentState(String outExcelCurrentState)
	{
		this.outExcelCurrentState=outExcelCurrentState;
	}

	public InputStream getExcelOrWordStream()
	{
		return excelOrWordStream;
	}

	public void setExcelOrWordStream(InputStream excelOrWordStream)
	{
		this.excelOrWordStream=excelOrWordStream;
	}

	/**
	 * 获取格式化后的导出文件名称
	 * 
	 * @return 文件名称
	 */
	public String getFileName()
	{
		try
		{
			this.fileName=new String("用户列表.xls".getBytes(),"ISO8859-1");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return this.fileName;
	}

	public void setFileName(String fileName)
	{
		try
		{
			this.fileName=new String("用户列表.xls".getBytes(),"ISO8859-1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//*******************获取客户端IP地址***************************
	public static String getClientIpAddress(HttpServletRequest request)
	{
		String ip=request.getHeader("x-forwarded-for");
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip))
		{
			ip=request.getHeader("Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip))
		{
			ip=request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip==null||ip.length()==0||"unknown".equalsIgnoreCase(ip))
		{
			ip=request.getRemoteAddr();
		}
		return ip;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String login()
	{
		User u=userService.query(user.getUserName(),user.getPassword(),company.getCompanyCode());
		if(u==null)
		{
			loginStatus=0; // 登录失败
		}
		else if(u.getRole()==null)
		{
			loginStatus=2; // 无角色
		}
		else
		{
			//获取资源ID列表
			List<Permission> permissions=permissionService.queryPermissionListByRoleId(u.getRole().getId());
			List<Long> resourceIds=new ArrayList<Long>();
			for(Permission p:permissions)
			{
				Resource resource=p.getResource();
				if(resource!=null)
				{
					resourceIds.add(resource.getId());
				}
			}
			// 将有关数据存入Session
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			session.setAttribute("userId",u.getId());
			session.setAttribute("userName",u.getUserName());
			session.setAttribute("roleId",u.getRole().getId());
			session.setAttribute("roleName",u.getRole().getName());
			session.setAttribute("companyId",u.getCompany().getId());
			session.setAttribute("companyCode",u.getCompany().getCompanyCode());
			session.setAttribute("companyName",u.getCompany().getCompanyName());
			session.setAttribute("resourceIds",resourceIds);
			loginStatus=1;// 登录成功
			
			//***************日志相关 start****************
			LoginLog log = new LoginLog();
			log.setUser(u);
			String clientIp = getClientIpAddress(request);
			log.setLoginIP(clientIp);
			log.setLoginTime(SJDateUtil.getCurrentStr(null));
			log.setLoginType("2");
			long logId = (Long)loginLogService.insert(log);
			session.setAttribute("clientIp",clientIp);
			session.setAttribute("logId",logId);
			//***************日志相关 end****************
			
		}
		return SUCCESS;
	}

	public String goToAddUser()
	{
		roleList=roleService.queryRoleList(1);//[1:管理公司;company.getId():当前公司]
		return SUCCESS;
	}

	public String gotoWithCompany()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		Long cid=(Long)session.getAttribute("companyId");
		if(cid==null||cid<=1)
		{
			companyList=companyService.queryIdAndName(-1);
		}
		else
		{
			companyList=companyService.queryIdAndName(cid);
		}
		return SUCCESS;
	}

	public String query()
	{
		pageBean=userService.query(user,pageBean);
		return SUCCESS;
	}

	public String queryById()
	{//**
		long userId=0;
		if(user==null||user.getId()<=0)
		{
			HttpSession session=ServletActionContext.getRequest().getSession();
			userId=(Long)session.getAttribute("userId");
		}
		else
		{
			userId=user.getId();
		}
		user=userService.queryByPK(userId);
		if(manuType!=null&&manuType.equals("edit"))
		{
			roleList=roleService.queryRoleList(1);//[1:管理公司;company.getId():当前公司]
		}
		return manuType;
	}

	public String gotoChangePwd()
	{//**
		HttpSession session=ServletActionContext.getRequest().getSession();
		user=userService.queryByPK((Long)session.getAttribute("userId"));
		return GOTO;
	}

	public String changePassword()
	{
		String password=user.getPassword();
		user=userService.queryByPK(user.getId());
		user.setPassword(password);
		boolean isSuccess=userService.update(user);
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String addUser()
	{
		boolean isNameExist=userService.isUserNameExist(user);
		if(isNameExist)
		{
			flag=2;//用户名存在
			return SUCCESS;
		}

		boolean isPhoneExist=userService.isPhoneExist(user);
		if(isPhoneExist)
		{
			flag=3;//手机号存在
			return SUCCESS;
		}

		if(user.getOrganization()!=null&&user.getOrganization().getId()<=0)
		{
			user.setOrganization(null);
		}
		if(user.getRole()!=null&&user.getRole().getId()<=0)
		{
			user.setRole(null);
		}
		user.setPassword(orginalPassword);
		//
		user.setIsDelete("0");
		String currentTime=SJDateUtil.DEFAULT_FORMAT.format(new Date());
		user.setCreateTime(currentTime);
		user.setUpdateTime(currentTime);
		//
		Serializable serializable=userService.insert(user);
		if(serializable==null)
		{
			flag=0;
		}
		else
		{
			flag=1;
		}
		return SUCCESS;
	}

	public String resetPassword()
	{
		user=userService.queryByPK(user.getId());
		user.setPassword(orginalPassword);
		boolean isSuccess=userService.update(user);
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String modifyUser()
	{
		if(flag!=-1)
		{ //等于-1时，是修改个人信息界面调用此接口,不必进行检查
			boolean isNameExist=userService.isUserNameExist(user);
			if(isNameExist)
			{
				flag=2;//用户名存在
				return SUCCESS;
			}
		}

		boolean isPhoneExist=userService.isPhoneExist(user);
		if(isPhoneExist)
		{
			flag=3;//手机号存在
			return SUCCESS;
		}

		if(user.getOrganization()!=null&&user.getOrganization().getId()<=0)
		{
			user.setOrganization(null);
		}
		if(user.getRole()!=null&&user.getRole().getId()<=0)
		{
			user.setRole(null);
		}

		boolean isSuccess=userService.update(user);
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String deleteById()
	{
		boolean isSuccess=userService.deleteForLogic(user.getId());
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String batchCheck()
	{
		String[] idArray=Ids.split(";");
		List<User> users=new ArrayList<User>();
		for(String id:idArray)
		{
			User user=userService.queryByPK(Long.parseLong(id));
			user.setCurrentState("1");
			users.add(user);
		}
		boolean isSuccess=userService.batchUpdate(users);
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String batchDelete()
	{
		if(Ids==null)
		{
			flag=1;
			return SUCCESS;
		}
		String[] idArray=Ids.split(";");
		List<User> users=new ArrayList<User>();
		for(String id:idArray)
		{
			User user=new User();
			user.setId(Long.parseLong(id));
			users.add(user);
		}
		boolean isSuccess=userService.batchDelete(users);
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * excel导出
	 */
	private String[] exportTitle={"序号","用户姓名","手机号","公司名称","身份证号","家庭住址"};

	public String outExcel()
	{
		// 获取数据源
		HSSFWorkbook workbook=OutExcel.printExcel(exportTitle,convertEntityToObejct());
		// 导出
		excelOrWordStream=OutExcel.exportExcel(excelOrWordStream,workbook);
		return SUCCESS;
	}

	/**
	 * 将实体转换为对象
	 */
	private List<Object[]> convertEntityToObejct()
	{
		if(user==null)
		{
			user=new User();
		}
		Company c=new Company();
		c.setId(outExcelCompanyId);
		user.setCompany(c);
		user.setUserName(outExcelUserName);
		user.setCurrentState(outExcelCurrentState);
		//
		List<User> userList=userService.query(user);

		if(userList!=null&&userList.size()>0)
		{
			List<Object[]> dataList=new ArrayList<Object[]>();
			for(int i=0;i<userList.size();i++)
			{
				User u=userList.get(i);
				Object[] obj=new Object[exportTitle.length-1];
				for(int j=0;j<(exportTitle.length-1);j++)
				{
					switch(j)
					{
						case 0:
							obj[0]=u.getUserName();
							break;
						case 1:
							obj[1]=u.getMobilePhone();
							break;
						case 2:
							obj[2]=u.getCompany().getCompanyName();
							break;
						case 3:
							obj[3]=u.getIdCard();
							break;
						case 4:
							obj[4]=u.getHomeAddress();
							break;
						default:
							break;
					}
				}
				dataList.add(obj);
			}
			return dataList;
		}
		return null;
	}

}
