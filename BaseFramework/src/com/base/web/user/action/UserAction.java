package com.base.web.user.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.permission.Permission;
import com.base.web.permission.Resource;
import com.base.web.permission.Role;
import com.base.web.permission.service.PermissionService;
import com.base.web.user.User;
import com.base.web.user.service.UserService;

import org.apache.struts2.ServletActionContext;

public class UserAction extends BaseAction<User>
{
	private static final long serialVersionUID=-7005835674200487450L;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String orginalPassword="c4ca4238a0b923820dcc509a6f75849b";
	private UserService userService;
	private CompanyService companyService;
	private Company company;
	private List<Company> companyList;
	private PermissionService permissionService;
	private PageBean<User> pageBean;
	private User user;
	private String manuType;
	private Set<Role> roleList;
	private int flag;
	private String Ids;

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

	public Set<Role> getRoleList()
	{
		return roleList;
	}

	public void setRoleList(Set<Role> roleList)
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

	public List<Company> getCompanyList()
	{
		return companyList;
	}

	public void setCompanyList(List<Company> companyList)
	{
		this.companyList=companyList;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	private int loginStatus;

	public int getLoginStatus()
	{
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus)
	{
		this.loginStatus=loginStatus;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user=user;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService=userService;
	}

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
			// 将有关数据存入Session
			HttpServletRequest request=ServletActionContext.getRequest();
			HttpSession session=request.getSession();
			session.setAttribute("userId",u.getId());
			session.setAttribute("userName",u.getUserName());
			session.setAttribute("roleId",u.getRole().getId());
			session.setAttribute("roleName",u.getRole().getName());
			session.setAttribute("companyId",u.getCompany().getId());
			session.setAttribute("companyCode",u.getCompany().getCompanyCode());
			session.setAttribute("companyName",u.getCompany().getFullName());
			List<Permission> permissions=permissionService.queryPermissionListByRoleId(u.getRole().getId());
			List<Integer> resourceIds=new ArrayList<Integer>();
			for(Permission p:permissions)
			{
				Resource resource=p.getResource();
				if(resource!=null)
				{
					resourceIds.add(resource.getId());
				}
			}
			session.setAttribute("resourceIds",resourceIds);
			loginStatus=1;// 登录成功
		}
		return SUCCESS;
	}

	public String goToAddUser()
	{
		company=companyService.queryByPK(company.getId());
		roleList=company.getRoles();
		return SUCCESS;
	}

	public String gotoWithCompany()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		int cid=(Integer)session.getAttribute("companyId");
		if(cid==1)
		{
			companyList=companyService.queryNameAndId(-1);
		}
		else
		{
			companyList=companyService.queryNameAndId(cid);
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
			company=companyService.queryByPK(company.getId());
			roleList=company.getRoles();
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

	public String deleteById()
	{
		boolean isSuccess=userService.delete(user.getId());
		flag=isSuccess?1:0;
		return SUCCESS;
	}

	public String addUser()
	{
		boolean isExist=userService.isExist(user);
		if(isExist)
		{
			flag=2;
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
		user.setCurrentState("0");
		user.setIsDelete("0");
		String currentTime=sdf.format(new Date());
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
			boolean isExist=userService.isExist(user);
			if(isExist)
			{
				flag=2;
				return SUCCESS;
			}
		}
		boolean isSuccess=userService.update(user);
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

}
