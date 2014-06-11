package com.base.web.log.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.log.LoginLog;

public interface LoginLogService extends BaseService<LoginLog, Long> {

	PageBean<LoginLog> query(LoginLog loginLog, PageBean<LoginLog> loginLogPageBean);

}
