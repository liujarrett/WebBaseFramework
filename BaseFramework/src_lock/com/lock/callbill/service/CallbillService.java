package com.lock.callbill.service;

import com.base.core.page.PageBean;
import com.lock.callbill.Callbill;


public interface CallbillService {
	public void query(Callbill callbill,PageBean<Callbill> pageBean);
}
