package com.base.web.company;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDao;

public interface CompanyDao extends BaseDao<Company,Long>
{

	public List<Company> queryIdAndName(long cid);

}
