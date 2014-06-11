package com.base.web.log.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.log.LoginLog;
import com.base.web.log.LoginLogDao;

public class LoginLogServiceImp extends BaseServiceImp<LoginLog, Long>
		implements LoginLogService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3823149641343652020L;
	private LoginLogDao loginLogDao;

	public LoginLogDao getLoginLogDao() {
		return loginLogDao;
	}

	public void setLoginLogDao(LoginLogDao loginLogDao) {
		super.setBaseDao(loginLogDao);
		this.loginLogDao = loginLogDao;
	}

	@Override
	public PageBean<LoginLog> query(LoginLog loginLog, PageBean<LoginLog> loginLogPageBean) {
		String where = "";
		if (loginLog.getUser() != null
				&& loginLog.getUser().getUserName() != null
				&& !loginLog.getUser().getUserName().equals("")) {
			where += " where user.userName like '%" +loginLog.getUser().getUserName()+ "%'"; 
		}
		if(loginLog.getLoginType() != null && !loginLog.getLoginType().equals("-1") && !loginLog.getLoginType().equals("")) {
			if(where.equals("")) {
				where += " where loginType='" +loginLog.getLoginType()+ "'";
			} else {
				where += " and loginType='" +loginLog.getLoginType()+ "'";
			}
		}
		if(loginLog.getSearchStartTime() != null && !loginLog.getSearchStartTime().equals("")) {
			if(where.equals("")) {
				where += " where loginTime >= '" +loginLog.getSearchStartTime()+ "'";
			} else {
				where += " and loginTime >= '" +loginLog.getSearchStartTime()+ "'";
			}
		}
		
		if(loginLog.getSearchEndTime() != null && !loginLog.getSearchEndTime().equals("")) {
			if(where.equals("")) {
				where += " where loginTime <= '" +loginLog.getSearchEndTime()+ "'";
			} else {
				where += " and loginTime <= '" +loginLog.getSearchEndTime()+ "'";
			}
		}
		where += " order by id";
		return loginLogDao.selectByPage(where, 10, loginLogPageBean.getCurrentPage());
	}

}
