package com.base.web.log.service;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseServiceImp;
import com.base.web.log.OperateLog;
import com.base.web.log.OperateLogDao;

public class OperateLogServiceImp extends BaseServiceImp<OperateLog, Long> implements OperateLogService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303996707336263071L;
	private OperateLogDao operateLogDao;

	public OperateLogDao getOperateLogDao() {
		return operateLogDao;
	}

	public void setOperateLogDao(OperateLogDao operateLogDao) {
		super.setBaseDao(operateLogDao);
		this.operateLogDao = operateLogDao;
	}

	@Override
	public PageBean<OperateLog> query(OperateLog operateLog, PageBean<OperateLog> operateLogPageBean) {
		String where = "";
		if (operateLog.getUser() != null
				&& operateLog.getUser().getUserName() != null
				&& !operateLog.getUser().getUserName().equals("")) {
			where += " where user.userName like '%" +operateLog.getUser().getUserName()+ "%'"; 
		}
		if(operateLog.getClientType() != null && !operateLog.getClientType().equals("-1") && !operateLog.getClientType().equals("")) {
			if(where.equals("")) {
				where += " where clientType='" +operateLog.getClientType()+ "'";
			} else {
				where += " and clientType='" +operateLog.getClientType()+ "'";
			}
		}
		if(operateLog.getOperateType() != null && !operateLog.getOperateType().equals("-1") && !operateLog.getOperateType().equals("")) {
			if(where.equals("")) {
				where += " where operateType='" +operateLog.getOperateType()+ "'";
			} else {
				where += " and operateType='" +operateLog.getOperateType()+ "'";
			}
		}
		if(operateLog.getSearchStartTime() != null && !operateLog.getSearchStartTime().equals("")) {
			if(where.equals("")) {
				where += " where operateTime >= '" +operateLog.getSearchStartTime()+ "'";
			} else {
				where += " and operateTime >= '" +operateLog.getSearchStartTime()+ "'";
			}
		}
		
		if(operateLog.getSearchEndTime() != null && !operateLog.getSearchEndTime().equals("")) {
			if(where.equals("")) {
				where += " where operateTime <= '" +operateLog.getSearchEndTime()+ "'";
			} else {
				where += " and operateTime <= '" +operateLog.getSearchEndTime()+ "'";
			}
		}
		where += " order by id";
		return operateLogDao.selectByPage(where, 10, operateLogPageBean.getCurrentPage());
	}

}
