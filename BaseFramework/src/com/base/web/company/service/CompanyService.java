package com.base.web.company.service;

import java.util.List;

import com.base.core.page.PageBean;
import com.base.core.ssh.l2service.BaseService;
import com.base.web.company.Company;

public interface CompanyService extends BaseService<Company,Long>
{
	//添加公司时的检查
	int checkIsExists(Company company);

	//公司分页
	PageBean<Company> query(Company company,PageBean<Company> pageBean);

	//
	List<Company> query(Company company);

	//通过ID查询公司
	Company query(long id);

	//通过ID查询公司（ID和NAME）
	List<Company> queryIdAndName(long cid);

	public boolean addCompany(Company company);
	public boolean deleteCompany(long companyId);
}
