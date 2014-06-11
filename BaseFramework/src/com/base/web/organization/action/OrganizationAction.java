package com.base.web.organization.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.core.utilities.SJDateUtil;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.organization.Organization;
import com.base.web.organization.service.OrganizationService;

import org.apache.struts2.ServletActionContext;

public class OrganizationAction extends BaseAction
{
	private static final long serialVersionUID=-991670450548264687L;

	private OrganizationService organizationService;
	private CompanyService companyService;
	private List<Company> companyList;
	private List<Object[]> treeList;
	private PageBean<Organization> pageBean;
	private int companyId;
	private Organization organization;
	private int organizationParentId;
	private int flag;
	private String manuType;

	public OrganizationService getOrganizationService()
	{
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService)
	{
		this.organizationService=organizationService;
	}

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	public List<Company> getCompanyList()
	{
		return companyList;
	}

	public void setCompanyList(List<Company> companyList)
	{
		this.companyList=companyList;
	}

	public List<Object[]> getTreeList()
	{
		return treeList;
	}

	public void setTreeList(List<Object[]> treeList)
	{
		this.treeList=treeList;
	}

	public PageBean<Organization> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<Organization> pageBean)
	{
		this.pageBean=pageBean;
	}

	public int getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(int companyId)
	{
		this.companyId=companyId;
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setOrganization(Organization organization)
	{
		this.organization=organization;
	}

	public int getOrganizationParentId()
	{
		return organizationParentId;
	}

	public void setOrganizationParentId(int organizationParentId)
	{
		this.organizationParentId=organizationParentId;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String gotoWithCompanys()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		long cid=(long)session.getAttribute("companyId");
		if(cid<=1)
		{
			companyList=companyService.queryIdAndName(-1);
		}
		else
		{
			companyList=companyService.queryIdAndName(cid);
		}
		return SUCCESS;
	}

	// 组织机构树状图
	public String queryTreeNodeData()
	{
		if(organizationParentId==0)
		{ // 查询公司
			Company company=companyService.queryIdAndName(companyId).get(0);
			if(treeList==null)
			{
				treeList=new ArrayList<Object[]>();
			}
			Object[] obj=new Object[3];
			obj[0]=-1;
			obj[1]=company.getCompanyName();
			int directOrganizationCount=organizationService.queryCountByCompanyId(companyId,true);
			obj[2]=directOrganizationCount;
			treeList.add(obj);
		}
		else
		{
			List<Organization> organizations=organizationService.query(companyId,organizationParentId);
			for(Organization organization:organizations)
			{
				if(treeList==null)
				{
					treeList=new ArrayList<Object[]>();
				}
				Object[] obj=new Object[3];
				obj[0]=organization.getId(); // id
				obj[1]=organization.getOrganizationName(); // 名称
				if(organization.getChildList()!=null)
				{
					obj[2]=organization.getChildList().size(); // 下级机构个数
				}
				else
				{
					obj[2]=0;
				}
				treeList.add(obj);
			}
		}
		return SUCCESS;
	}

	public String queryForGrid()
	{
		pageBean=organizationService.query(companyId,organization,pageBean);
		return SUCCESS;
	}

	public String add()
	{
		boolean isExists=organizationService.isExists(companyId,organization);
		if(isExists)
		{
			flag=1;
			return SUCCESS;
		}

		if(organization.getParent()==null||organization.getParent().getId()<0)
		{
			organization.setParent(null);
		}
		//
		organization.setCurrentState("0");
		organization.setIsDelete("0");
		String currentTime=SJDateUtil.DEFAULT_FORMAT.format(new Date());
		organization.setCreateTime(currentTime);
		organization.setUpdateTime(currentTime);
		//
		Serializable serializable=organizationService.insert(organization);
		if(serializable==null)
		{
			flag=0;
		}
		else
		{
			flag=100;
		}
		return SUCCESS;
	}

	public String queryById()
	{
		organization=organizationService.queryByPK(organization.getId());
		return manuType;
	}

	public String modify()
	{
		Organization org=organizationService.queryByPK(organization.getId());
		org.setOrganizationName(organization.getOrganizationName());
		org.setDescribes(organization.getDescribes());
		//
		if(organization.getParent()==null||organization.getParent().getId()<=0)
		{ //修改后上级机构为空
			org.setParent(null);
		}
		else
		{
			if(ischild(organization.getParent().getId(),org))
			{
				flag=1;
				return SUCCESS;
			}
			org.setParent(organization.getParent());
		}
		//
		if(organizationService.update(org))
		{
			flag=100;
		}
		else
		{
			flag=0;
		}

		return SUCCESS;
	}

	//新ID是否为子ID
	private boolean ischild(long newParentId,Organization organization)
	{
		Set<Organization> childList=organization.getChildList();
		for(Organization child:childList)
		{
			if(newParentId==child.getId())
			{
				return true;
			}
			else if(ischild(newParentId,child))
			{
				return true;
			}
		}
		return false;
	}

	public String delete()
	{ //HM
		boolean isSuccess=organizationService.delete(organization.getId());
		flag=isSuccess?1:0;
		return SUCCESS;
	}
}
