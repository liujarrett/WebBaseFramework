package com.base.web.company.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.company.Company;

public interface CompanyService extends BaseService<Company,Integer>
{

	PageBean<Company> query(Company company,PageBean<Company> pageBean);

	int checkIsExists(Company company);

	Company query(int id);

	List<Company> queryNameAndId(int cid);

}
