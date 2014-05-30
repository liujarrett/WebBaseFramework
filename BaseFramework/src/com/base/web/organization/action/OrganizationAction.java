package com.base.web.organization.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.organization.Organization;
import com.base.web.organization.service.OrganizationService;

import org.apache.struts2.ServletActionContext;

public class OrganizationAction extends BaseAction<Organization>
{

	private static final long serialVersionUID=-991670450548264687L;
	private final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
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

	public int getOrganizationParentId()
	{
		return organizationParentId;
	}

	public void setOrganizationParentId(int organizationParentId)
	{
		this.organizationParentId=organizationParentId;
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setOrganization(Organization organization)
	{
		this.organization=organization;
	}

	public List<Object[]> getTreeList()
	{
		return treeList;
	}

	public void setTreeList(List<Object[]> treeList)
	{
		this.treeList=treeList;
	}

	public List<Company> getCompanyList()
	{
		return companyList;
	}

	public void setCompanyList(List<Company> companyList)
	{
		this.companyList=companyList;
	}

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	public OrganizationService getOrganizationService()
	{
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService)
	{
		this.organizationService=organizationService;
	}

	public String gotoWithCompanys()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		int cid=(Integer)session.getAttribute("companyId");
		if(cid==1)
		{
			companyList=companyService.queryNameAndId(-1);
		}
		else
		{
			companyList=companyService.queryNameAndId(cid);
		}
		return SUCCESS;
	}

	// 组织机构树状图
	public String queryTreeNodeData()
	{
		if(organizationParentId==0)
		{ // 查询公司
			Company company=companyService.queryNameAndId(companyId).get(0);
			if(treeList==null)
			{
				treeList=new ArrayList<Object[]>();
			}
			Object[] obj=new Object[3];
			obj[0]=-1;
			obj[1]=company.getFullName();
			int directOrganizationCount=organizationService.queryCountByCompanyId(true,companyId);
			obj[2]=directOrganizationCount;
			treeList.add(obj);
		}
		else
		{
			List<Organization> organizations=organizationService.query(organizationParentId,companyId);
			for(Organization organization:organizations)
			{
				if(treeList==null)
				{
					treeList=new ArrayList<Object[]>();
				}
				Object[] obj=new Object[3];
				obj[0]=organization.getId(); // id
				obj[1]=organization.getFullName(); // 名称
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
		pageBean=organizationService.query(organization,companyId,pageBean);
		return SUCCESS;
	}

	public String isExists()
	{
		boolean isExists=organizationService.isExists(organization);
		if(isExists)
		{
			flag=1;
		}
		else
		{
			flag=0;
		}
		return SUCCESS;
	}

	public String add()
	{
		if(organization.getParent()==null||organization.getParent().getId()<0)
		{
			organization.setParent(null);
		}
		//
		organization.setCurrentState("0");
		organization.setIsDelete("0");
		String currentTime=sdf.format(new Date());
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
			flag=1;
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
		org.setFullName(organization.getFullName());
		org.setDescribes(organization.getDescribes());
		if(organization.getParent()==null||organization.getParent().getId()<=0)
		{ //修改后上级机构为空
			org.setParent(null);
		}
		else
		{
			Set<Organization> children=org.getChildList();
			for(Organization child:children)
			{
				if(child.getId()==organization.getParent().getId())
				{ //修改后的上级机构之前是自己的下级机构
					child.setParent(org.getParent());
					org.setParent(child);
					organizationService.batchUpdate(org,child);
					return SUCCESS;
				}
			}
			org.setParent(organization.getParent()); //修改后上级机构为其他
		}

		organizationService.batchUpdate(org);
		//		organizationService.update(org);  用这个方法竟然不好使
		return SUCCESS;
	}

	public String delete()
	{ //HM
		boolean isSuccess=organizationService.delete(organization.getId());
		flag=isSuccess?1:0;
		return SUCCESS;
	}
}
