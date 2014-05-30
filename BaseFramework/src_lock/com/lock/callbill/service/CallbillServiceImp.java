package com.lock.callbill.service;

import java.text.SimpleDateFormat;
import java.util.List;

import com.base.core.page.PageBean;
import com.lock.callbill.Callbill;
import com.lock.callbill.CallbillDao;

public class CallbillServiceImp  implements CallbillService {


	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private CallbillDao callbillDao;

	public CallbillDao getCallbillDao() {
		return callbillDao;
	}

	public void setCallbillDao(CallbillDao callbillDao) {
		this.callbillDao = callbillDao;
	}

	public void query(Callbill callbill, PageBean<Callbill> pageBean) {
		if (callbill == null || pageBean == null) {
			return;
		}
		// 获得查询条件
		String strCondition = "";

		if (callbill.getOpenlocktel() != null
				&& !callbill.getOpenlocktel().equals("")) {
			strCondition += "(calleeid = '" + callbill.getOpenlocktel()
					+ "' or cvtid = '" + callbill.getOpenlocktel() + "')";
		} else {

			if (callbill.getCallerid() != null
					&& !callbill.getCallerid().equals("")) {
				strCondition += " callerid=" + callbill.getCallerid();
			}

			if (callbill.getCalleeid() != null
					&& !callbill.getCalleeid().equals("")) {
				if (!strCondition.equals("")) {
					strCondition += " and calleeid=" + callbill.getCalleeid();
				} else {
					strCondition += " calleeid=" + callbill.getCalleeid();
				}
			}
		}

		if (callbill.getHangontime() != null) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}

			strCondition += " hangontm >= to_date('"
					+ sdf.format(callbill.getHangontime())
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}

		if (callbill.getHangofftime() != null) {
			if (!strCondition.equals("")) {
				strCondition += " and ";
			}
			strCondition += " hangofftm <= to_date('"
					+ sdf.format(callbill.getHangofftime())
					+ "','yyyy-MM-dd hh24:mi:ss')";
		}

		String sql = "from Callbill";

		if (!strCondition.equals("")) {
			sql += " where " + strCondition;
		}

		List<Callbill> callbills = callbillDao.query(sql,
				pageBean.getCurrentPage(), pageBean.getPageSize());

		// 获取符合条件的总记录数
		String sql_count = "select count(id) from Callbill";
		if (!strCondition.equals("")) {
			sql_count += " where " + strCondition;
		}

		int rowCount = callbillDao.count(sql_count);
		pageBean.setList(callbills);
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
