package com.base.mobile.version;

import java.util.List;

import com.base.core.ssh.l3dao.BaseDao;

import org.springframework.dao.DataAccessException;

public interface MobileVersionDao extends BaseDao<MobileVersion,Long>
{

	List<MobileVersion> selectAndroidVersion() throws DataAccessException;

}
