package com.base.web.user.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.user.User;

public interface UserService extends BaseService<User,Long>
{

	public User query(String userName,String password,String companyCode);
	
	public User query(String mobilePhone,String password);

	public PageBean<User> query(User user,PageBean<User> pageBean);

	public boolean isExist(User user);

	boolean batchUpdate(List<User> users);

	public boolean batchDelete(List<User> users);

}
