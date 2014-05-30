package com.lock.callbill;

import java.util.List;

public interface CallbillDao {
	// 根据sql查询
	public List<Callbill> query(String sql, int pageNo, int pageSize);

	// 根据sql查询记录数
	public int count(String sql);
}
