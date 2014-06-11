package com.base.web.permission.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.base.core.model.ZTreeNode;
import com.base.core.ssh.l1action.BaseAction;
import com.base.core.utilities.SJDateUtil;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.permission.Function;
import com.base.web.permission.Permission;
import com.base.web.permission.Resource;
import com.base.web.permission.Role;
import com.base.web.permission.service.FunctionService;
import com.base.web.permission.service.PermissionService;
import com.base.web.permission.service.ResourceService;
import com.base.web.permission.service.RoleService;
import com.base.web.user.User;

import org.apache.struts2.ServletActionContext;

public class RoleAction extends BaseAction
{
	private static final long serialVersionUID=6951466777786876950L;
	//
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	private CompanyService companyService;
	private List<Company> companyList;
	private Company company;
	private FunctionService functionService;
	private ResourceService resourceService;
	private PermissionService permissionService;
	private List<Permission> permissionList;
	private List<Function> functionList;
	private List<Object[]> funcList;
	private long functionId;
	private long funcParentId;
	private long currentUserRoleId;
	private long currentRoleId;
	private int flag;
	private List<ZTreeNode> permissionTree;
	private String updateSource;

	public RoleService getRoleService()
	{
		return roleService;
	}

	public void setRoleService(RoleService roleService)
	{
		this.roleService=roleService;
	}

	public List<Role> getRoleList()
	{
		return roleList;
	}

	public void setRoleList(List<Role> roleList)
	{
		this.roleList=roleList;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role=role;
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

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public FunctionService getFunctionService()
	{
		return functionService;
	}

	public void setFunctionService(FunctionService functionService)
	{
		this.functionService=functionService;
	}

	public ResourceService getResourceService()
	{
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService)
	{
		this.resourceService=resourceService;
	}

	public PermissionService getPermissionService()
	{
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService)
	{
		this.permissionService=permissionService;
	}

	public List<Permission> getPermissionList()
	{
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList)
	{
		this.permissionList=permissionList;
	}

	public List<Function> getFunctionList()
	{
		return functionList;
	}

	public void setFunctionList(List<Function> functionList)
	{
		this.functionList=functionList;
	}

	public List<Object[]> getFuncList()
	{
		return funcList;
	}

	public void setFuncList(List<Object[]> funcList)
	{
		this.funcList=funcList;
	}

	public long getFunctionId()
	{
		return functionId;
	}

	public void setFunctionId(long functionId)
	{
		this.functionId=functionId;
	}

	public long getFuncParentId()
	{
		return funcParentId;
	}

	public void setFuncParentId(long funcParentId)
	{
		this.funcParentId=funcParentId;
	}

	public long getCurrentUserRoleId()
	{
		return currentUserRoleId;
	}

	public void setCurrentUserRoleId(long currentUserRoleId)
	{
		this.currentUserRoleId=currentUserRoleId;
	}

	public long getCurrentRoleId()
	{
		return currentRoleId;
	}

	public void setCurrentRoleId(long currentRoleId)
	{
		this.currentRoleId=currentRoleId;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public List<ZTreeNode> getPermissionTree()
	{
		return permissionTree;
	}

	public void setPermissionTree(List<ZTreeNode> permissionTree)
	{
		this.permissionTree=permissionTree;
	}

	public String getUpdateSource()
	{
		return updateSource;
	}

	public void setUpdateSource(String updateSource)
	{
		this.updateSource=updateSource;
	}

	public String getFunctionIds()
	{
		return functionIds;
	}

	public void setFunctionIds(String functionIds)
	{
		this.functionIds=functionIds;
	}

	public String getResourceIds()
	{
		return resourceIds;
	}

	public void setResourceIds(String resourceIds)
	{
		this.resourceIds=resourceIds;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	// 根据上级菜单获取子菜单节点
	// 返回形式
	// [
	// [1000, (id)
	// "<div style=display:inline;>
	// <img src= '/CommonFramework/common/blue/Images/1.jpg'
	// style=vertical-align:middle; height=15 width=15 />
	// 原有部分
	// </div>", (样式)
	// null, (点击url)
	// "004", (level)
	// "0001:true", (不知道)
	// "原有部分", (名称)
	// "/CommonFramework\/common\/blue\/Images\/1.jpg", (显示图片)
	// 2 (子节点个数)
	// ]
	// ]
	public String queryMenu()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		Long roleId=(Long)session.getAttribute("roleId");
		if(roleId==null||roleId<=0)
		{
			return LOGOUT;
		}
		List<Function> functions=permissionService.queryFunctionListByParentId(funcParentId,roleId);
		if(functions==null)
		{
			return SUCCESS;
		}
		List<Object[]> newList=new ArrayList<Object[]>();
		for(int i=0;i<functions.size();i++)
		{
			Object[] o=new Object[8];
			Function func=functions.get(i);
			String str="<div style=display:inline;><img src= '"+func.getIcon()+"' style=vertical-align:middle;  height=15 width=15/>"+func.getName()+"</div>";
			o[0]=func.getId();
			o[1]=str;
			o[2]=func.getUrl();
			o[3]=func.getFunctionCode();
			o[4]="0001:true";
			o[5]=func.getName();
			o[6]=func.getIcon();
			if(func.getChildList()!=null)
			{
				o[7]=func.getChildList().size();
			}
			else
			{
				o[7]=0;
			}
			newList.add(o);
		}

		funcList=newList;
		return SUCCESS;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */
	/***
	 * 根据角色ID，查询所有权限
	 * */
	public String permissionList()
	{
		Long roleId=role.getId();
		permissionList=permissionService.queryPermissionListByRoleId(roleId==null?0:roleId);
		return SUCCESS;
	}

	/***
	 * 根据当前用户的公司ID，查询公司；如果是超级管理员，则查询所有公司
	 * */
	public String companyList()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		Long cid=(Long)session.getAttribute("companyId");
		if(cid==null||cid<=1)
		{
			companyList=companyService.queryIdAndName(-1);
		}
		else
		{
			companyList=companyService.queryIdAndName(cid);
		}
		return SUCCESS;
	}

	/***
	 * 根据公司ID，查询角色；返回该公司的所有角色列表
	 * */
	public String roleList()
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		long rid=(Long)session.getAttribute("roleId");
		roleList=new ArrayList<Role>();
		List<Role> list=roleService.queryRoleList(company.getId());
		for(Role role:list)
		{
			if(rid==role.getId())
			{
				continue;
			}
			Role r=new Role();
			r.setId(role.getId());
			r.setName(role.getName());
			roleList.add(r);
		}
		return SUCCESS;
	}

	/***
	 * 根据功能父ID和角色ID，查询子功能列表；
	 * */
	public String functionList()
	{
		Long roleId=role.getId();
		functionList=permissionService.queryFunctionListByParentId(functionId,roleId==null?0:roleId);
		return SUCCESS;
	}

	/***
	 * 添加一个新的角色
	 * */
	public String roleAdd()
	{
		//是否存在同名角色
		if(roleService.isExistRole(role.getCompanyId(),role.getId(),role.getName()))
		{
			flag=2;
			return SUCCESS;
		}
		//
		role.setCurrentState("0");
		role.setIsDelete("0");
		String currentTime=SJDateUtil.DEFAULT_FORMAT.format(new Date());
		role.setCreateTime(currentTime);
		role.setUpdateTime(currentTime);
		//
		Serializable serializable=roleService.insert(role);
		if(serializable==null)
			flag=0;
		else
			flag=1;
		return SUCCESS;
	}

	/***
	 * 进入角色编辑页面，注入角色信息
	 * */
	public String gotoEdit()
	{
		role=roleService.queryByPK(role.getId());
		return GOTO;
	}

	/***
	 * 跟新角色信息
	 * */
	public String roleEdit()
	{
		//是否存在同名角色
		if(roleService.isExistRole(role.getCompanyId(),role.getId(),role.getName()))
		{
			flag=2;
			return SUCCESS;
		}
		
		boolean isSuccess=roleService.update(role);
		if(isSuccess)
			flag=1;
		else
			flag=0;
		return SUCCESS;
	}

	/***
	 * 该角色，是否绑定了用户
	 * */
	public String isRoleBindingUsers()
	{
		Long roleId=role.getId();
		List<User> userList=roleService.queryUserList(roleId==null?0:roleId);
		if(userList!=null&&userList.size()>0)
			flag=1;
		else
			flag=0;
		return SUCCESS;
	}

	/***
	 * 删除角色
	 * */
	public String roleDelete()
	{
		boolean isSuccess=roleService.deleteForLogic(role.getId());
		if(isSuccess)
			flag=1;
		else
			flag=0;
		return SUCCESS;
	}

	/***
	 * 返回权限树
	 * */
	private String functionIds;
	private String resourceIds;

	public String rolePermissionTree()
	{
		functionIds="";
		List<Function> oldFunctionlist=permissionService.queryFunctionListByRoleId(currentRoleId);
		for(Function function:oldFunctionlist)
		{
			functionIds+="["+function.getId()+"]";
		}
		//
		resourceIds="";
		List<Resource> oldResourcelist=permissionService.queryResourceListByRoleId(currentRoleId);
		for(Resource resource:oldResourcelist)
		{
			resourceIds+="["+resource.getId()+"]";
		}
		/**/
		permissionTree=new ArrayList<ZTreeNode>();
		List<Function> list=permissionService.queryFunctionListByParentId(0,currentUserRoleId);
		for(Function function:list)
		{
			ZTreeNode zTreeNode=new ZTreeNode();
			zTreeNode.setId(function.getId());
			zTreeNode.setpId(0);
			zTreeNode.setName(function.getName());
			zTreeNode.setOpen(true);
			if(functionIds.indexOf("["+String.valueOf(function.getId())+"]")!=-1)
			{
				zTreeNode.setChecked(true);
			}
			else
			{
				zTreeNode.setChecked(false);
			}
			permissionTree.add(zTreeNode);
			rolePermissionTree(function.getId(),currentUserRoleId);
		}
		return SUCCESS;
	}

	private void rolePermissionTree(long functionParentId,long roleId)
	{
		List<Function> list=permissionService.queryFunctionListByParentId(functionParentId,roleId);
		if(list==null||list.size()==0)
		{
			List<Resource> resourceList=permissionService.queryResourceListByFunctionId(functionParentId,roleId);
			for(Resource resource:resourceList)
			{
				ZTreeNode zTreeNode=new ZTreeNode();
				zTreeNode.setId(10000+resource.getId());//防止功能id和资源id重复，给资源id+1W。
				zTreeNode.setpId(functionParentId);
				zTreeNode.setName(resource.getName());
				zTreeNode.setOpen(true);
				if(resourceIds.indexOf("["+String.valueOf(resource.getId())+"]")!=-1)
				{
					zTreeNode.setChecked(true);
				}
				else
				{
					zTreeNode.setChecked(false);
				}
				permissionTree.add(zTreeNode);
			}
		}
		else
		{
			for(Function function:list)
			{
				ZTreeNode zTreeNode=new ZTreeNode();
				zTreeNode.setId(function.getId());
				zTreeNode.setpId(functionParentId);
				zTreeNode.setName(function.getName());
				zTreeNode.setOpen(true);
				if(functionIds.indexOf("["+String.valueOf(function.getId())+"]")!=-1)
				{
					zTreeNode.setChecked(true);
				}
				else
				{
					zTreeNode.setChecked(false);
				}
				permissionTree.add(zTreeNode);
				rolePermissionTree(function.getId(),roleId);
			}
		}

	}

	/***
	 * 保存权限树
	 * */
	public String rolePermissionSave()
	{
		List<Permission> newPermissionList=new ArrayList<Permission>();
		Role role=roleService.queryByPK(currentRoleId);
		String[] ids=updateSource.split(",");
		for(int i=0;i<ids.length;i++)
		{
			Function function;
			Resource resource;
			//
			long id=Long.valueOf(ids[i]);
			if(id<10000)
			{
				function=functionService.queryByPK(id);
				resource=null;
			}
			else
			{
				id=id-10000;
				resource=resourceService.queryByPK(id);
				function=resource.getFunction();
			}
			//
			Permission permission=new Permission();
			permission.setRole(role);
			permission.setFunction(function);
			permission.setResource(resource);
			//
			permission.setCurrentState("0");
			permission.setIsDelete("0");
			permission.setCreateTime(SJDateUtil.DEFAULT_FORMAT.format(new Date()));
			newPermissionList.add(permission);
		}
		permissionService.savePermission(currentRoleId,newPermissionList);
		return SUCCESS;
	}
}
