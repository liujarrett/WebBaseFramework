package com.base.web.user.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.user.User;
import com.base.web.user.UserDao;

import org.springframework.dao.DataAccessException;

public class UserServiceImp extends BaseServiceImp<User,Long> implements UserService
{

	private static final long serialVersionUID=2824842437017907620L;
	private UserDao userDao;

	public UserDao getUserDao()
	{
		return userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		super.setBaseDao(userDao);
		this.userDao=userDao;
	}

	@Override
	public User query(String userName,String password,String companyCode)
	{
		User user=null;
		try
		{
			String where="where isDelete='0' and userName='"+userName+"' and password='"+password+"' and company.companyCode='"+companyCode+"' and company.isDelete='0' ";
			List<User> users=userDao.select(where);
			if(users!=null&&users.size()>0)
			{
				user=users.get(0);
			}
		}
		catch(DataAccessException e)
		{
			user=null;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			user=null;
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User query(String mobilePhone,String password)
	{
		User user=null;
		try
		{
			String where="where isDelete='0' and mobilePhone='"+mobilePhone+"' and password='"+password+"'";
			List<User> users=userDao.select(where);
			if(users!=null&&users.size()>0)
			{
				user=users.get(0);
			}
		}
		catch(DataAccessException e)
		{
			user=null;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			user=null;
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public PageBean<User> query(User user,PageBean<User> pageBean)
	{
		String where=" where isDelete = '0'";
		if(user.getCompany()!=null&&user.getCompany().getId()>0)
		{
			where+=" and company.id="+user.getCompany().getId();
		}
		if(user.getUserName()!=null&&!user.getUserName().equals(""))
		{
			where+=" and userName like '%"+user.getUserName()+"%'";
		}
		if(user.getCurrentState().equals("0")||user.getCurrentState().equals("1"))
		{
			where+=" and currentState='"+user.getCurrentState()+"'";
		}
		return userDao.selectByPage(where,10,pageBean.getCurrentPage());
	}

	@Override
	public boolean isUserNameExist(User user)
	{
		User u=null;
		try
		{
			String where="where isDelete='0' and userName='"+user.getUserName()+"'";
			if(user.getCompany()!=null)
			{
				where+=" and company.id="+user.getCompany().getId();
			}
			if(user.getOrganization()!=null)
			{
				where+=" and organization.id="+user.getOrganization().getId();
			}

			if(user!=null&&user.getId()>0)
			{
				where+=" and id!="+user.getId();
			}
			List<User> users=userDao.select(where);
			if(users!=null&&users.size()>0)
			{
				u=users.get(0);
			}
		}
		catch(DataAccessException e)
		{
			u=null;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			u=null;
			e.printStackTrace();
		}
		return u!=null;
	}
	@Override
	public boolean isPhoneExist(User user) {
		if(user == null) {
			return true;
		}
		User u=null;
		try
		{
			String where="where isDelete='0'";
			if(user.getMobilePhone() != null && !user.getMobilePhone().equals("")) {
				where += " and mobilePhone='"+user.getMobilePhone()+"'";
			}

			if(user!=null && user.getId()>0)
			{
				where+=" and id!="+user.getId();
			}
			List<User> users=userDao.select(where);
			if(users!=null&&users.size()>0)
			{
				u=users.get(0);
			}
		}
		catch(DataAccessException e)
		{
			u=null;
			e.printStackTrace();
		}
		catch(Exception e)
		{
			u=null;
			e.printStackTrace();
		}
		return u!=null;
	}
	@Override
	public boolean batchUpdate(List<User> users)
	{
		boolean flag=true;
		if(users==null||users.size()<=0)
		{
			return true;
		}
		try
		{
			for(User user:users)
			{
				userDao.update(user);
			}
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean batchDelete(List<User> users)
	{
		boolean flag=true;
		if(users==null||users.size()<=0)
		{
			return true;
		}
		try
		{
			for(User user:users)
			{
				userDao.delete(user.getId());
			}
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
}
