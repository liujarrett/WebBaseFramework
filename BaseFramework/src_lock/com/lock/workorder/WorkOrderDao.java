package com.lock.workorder;

import java.util.List;

public interface WorkOrderDao {
	// 根据sql查询
	public List<WorkOrder> query(String sql, int pageNo, int pageSize);

	// 根据sql查询记录数
	public int count(String sql);
}
