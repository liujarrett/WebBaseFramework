package com.lock.workorder.service;

import com.base.core.page.PageBean;
import com.lock.workorder.WorkOrder;

public interface WorkOrderService {
    //分页查询
	void query(WorkOrder workOrder, PageBean<WorkOrder> pageBean);

}
