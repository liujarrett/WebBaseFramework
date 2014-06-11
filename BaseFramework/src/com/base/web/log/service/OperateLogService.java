package com.base.web.log.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.log.OperateLog;

public interface OperateLogService extends BaseService<OperateLog, Long> {

	PageBean<OperateLog> query(OperateLog operateLog,PageBean<OperateLog> operateLogPageBean);

}
