package com.base.web.log.action;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.web.log.LoginLog;
import com.base.web.log.OperateLog;
import com.base.web.log.service.LoginLogService;
import com.base.web.log.service.OperateLogService;

public class LogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1378356828979285418L;
	private LoginLogService loginLogService;
	private OperateLogService operateLogService;
	private PageBean<LoginLog> loginLogPageBean;
	private PageBean<OperateLog> operateLogPageBean;
	private LoginLog loginLog;
	private OperateLog operateLog;

	public PageBean<LoginLog> getLoginLogPageBean() {
		return loginLogPageBean;
	}

	public void setLoginLogPageBean(PageBean<LoginLog> loginLogPageBean) {
		this.loginLogPageBean = loginLogPageBean;
	}

	public PageBean<OperateLog> getOperateLogPageBean() {
		return operateLogPageBean;
	}

	public void setOperateLogPageBean(PageBean<OperateLog> operateLogPageBean) {
		this.operateLogPageBean = operateLogPageBean;
	}

	public LoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(LoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public OperateLog getOperateLog() {
		return operateLog;
	}

	public void setOperateLog(OperateLog operateLog) {
		this.operateLog = operateLog;
	}

	public LoginLogService getLoginLogService() {
		return loginLogService;
	}

	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}

	public OperateLogService getOperateLogService() {
		return operateLogService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	// *****************************************************************************
	public String queryLoginLogForGrid() {
		loginLogPageBean = loginLogService.query(loginLog,loginLogPageBean);
		return SUCCESS;
	}

	public String queryOperateLogForGrid() {
		operateLogPageBean = operateLogService.query(operateLog,operateLogPageBean);
		return SUCCESS;
	}

}
