package com.lock.workorder.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.utilities.SJDateUtil;
import com.lock.workorder.WorkOrder;
import com.lock.workorder.WorkOrderDao;

public class WorkOrderServiceImp implements WorkOrderService {
	private WorkOrderDao workorderDao;

	public WorkOrderDao getWorkorderDao() {
		return workorderDao;
	}

	public void setWorkorderDao(WorkOrderDao workorderDao) {
		this.workorderDao = workorderDao;
	}

	public void query(WorkOrder workOrder, PageBean<WorkOrder> pageBean) {
		if (workOrder == null || pageBean == null) {
			return;
		}
		// 获得查询条件
		String strCondition = "";

		if (workOrder.getOpenlockname() != null
				&& !workOrder.getOpenlockname().equals("")) {
			strCondition += " openlockname='" + workOrder.getOpenlockname() +"'";
		}
		if (workOrder.getCallerid() != null && !workOrder.getCallerid().equals("")) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}
			strCondition += " callerid=" + workOrder.getCallerid();
		}

		if (workOrder.getCalleeid() != null && !workOrder.getCalleeid().equals("")) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}
			strCondition += " calleeid=" + workOrder.getCalleeid();
		}

		if (workOrder.getAcceptStartTime() != null) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}

			strCondition += " accepttime >= to_date('"
					+ SJDateUtil.DEFAULT_FORMAT.format(workOrder.getAcceptStartTime())
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}

		if (workOrder.getAcceptEndTime() != null) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}
			strCondition += " accepttime <= to_date('"
					+ SJDateUtil.DEFAULT_FORMAT.format(workOrder.getAcceptEndTime())
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}

		String sql = "from WorkOrder";

		if (!strCondition.equals("")) {
			sql += " where " + strCondition;
		}
		sql += " order by accepttime desc";

		List<WorkOrder> workOrders = workorderDao.query(sql, pageBean.getCurrentPage(), pageBean.getPageSize());

		// 获取符合条件的总记录数
		String sql_count = "select count(id) from WorkOrder";
		if (!strCondition.equals("")) {
			sql_count += " where " + strCondition;
		}

		int rowCount = workorderDao.count(sql_count);
		pageBean.setList(workOrders);
		pageBean.setAllRow(rowCount);

		if (pageBean.getCurrentPage() == 1) {
			pageBean.setFirstPage(true);
		} else {
			pageBean.setFirstPage(false);
		}

		if (rowCount / pageBean.getPageSize() + 1 == pageBean.getCurrentPage()) {
			pageBean.setLastPage(true);
		} else {
			pageBean.setLastPage(false);
		}

		if (rowCount / pageBean.getPageSize() + 1 > pageBean.getCurrentPage()) {
			pageBean.setHasNextPage(true);
		} else {
			pageBean.setHasNextPage(false);
		}

		if (pageBean.getCurrentPage() == 1) {
			pageBean.setHasPreviousPage(true);
		} else {
			pageBean.setHasPreviousPage(false);
		}

		pageBean.setTotalPage(rowCount / pageBean.getPageSize() + 1);
	}

}
