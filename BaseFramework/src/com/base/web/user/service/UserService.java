package com.base.web.user.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.user.User;

public interface UserService extends BaseService<User,Long>
{
	//网站登录
	public User query(String userName,String password,String companyCode);

	//	手机登录
	public User query(String mobilePhone,String password);

	//用户列表
	public List<User> query(User user);

	//用户列表（分页）
	public PageBean<User> query(User user,PageBean<User> pageBean);

	//判断用户名是否存在
	public boolean isUserNameExist(User user);

	//判断手机号是否存在
	public boolean isPhoneExist(User user);

	//批量更新用户信息
	boolean batchUpdate(List<User> users);

	//批量删除用户
	public boolean batchDelete(List<User> users);

}
