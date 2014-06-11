package com.base.web.company.action;

import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.core.utilities.OutExcel;
import com.base.core.utilities.SJDateUtil;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.user.User;
import com.base.web.user.service.UserService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CompanyAction extends BaseAction
{
	private static final long serialVersionUID=5709646655608951202L;
	//
	private CompanyService companyService;
	private Company company;
	private PageBean<Company> pageBean;
	private String manuType;
	private String ids;
	private int flag;
	private UserService userService;
	//outExcel
	private String outExcelCompanyName;
	private String outExcelCompanyAddress;
	private String outExcelCompanyPhone;
	// 输出流
	private InputStream excelOrWordStream;
	// 导出文件名
	private String fileName;

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	public Company getCompany()
	{
		return company;
	}

	public void setCompany(Company company)
	{
		this.company=company;
	}

	public PageBean<Company> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<Company> pageBean)
	{
		this.pageBean=pageBean;
	}

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids=ids;
	}

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag=flag;
	}

	public UserService getUserService()
	{
		return userService;
	}

	public void setUserService(UserService userService)
	{
		this.userService=userService;
	}

	public String getOutExcelCompanyName()
	{
		return outExcelCompanyName;
	}

	public void setOutExcelCompanyName(String outExcelCompanyName)
	{
		this.outExcelCompanyName=outExcelCompanyName;
	}

	public String getOutExcelCompanyAddress()
	{
		return outExcelCompanyAddress;
	}

	public void setOutExcelCompanyAddress(String outExcelCompanyAddress)
	{
		this.outExcelCompanyAddress=outExcelCompanyAddress;
	}

	public String getOutExcelCompanyPhone()
	{
		return outExcelCompanyPhone;
	}

	public void setOutExcelCompanyPhone(String outExcelCompanyPhone)
	{
		this.outExcelCompanyPhone=outExcelCompanyPhone;
	}

	public InputStream getExcelOrWordStream()
	{
		return excelOrWordStream;
	}

	public void setExcelOrWordStream(InputStream excelOrWordStream)
	{
		this.excelOrWordStream=excelOrWordStream;
	}

	/**
	 * 获取格式化后的导出文件名称
	 * 
	 * @return 文件名称
	 */
	public String getFileName()
	{
		try
		{
			this.fileName=new String("公司列表.xls".getBytes(),"ISO8859-1");
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return this.fileName;
	}

	public void setFileName(String fileName)
	{
		try
		{
			this.fileName=new String("公司列表.xls".getBytes(),"ISO8859-1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String query()
	{
		pageBean=companyService.query(company,pageBean);
		return SUCCESS;
	}

	public String addCompany()
	{
		// flag等于1 ，登录代码已经存在 ；flag等于2，公司名存在
		flag=companyService.checkIsExists(company);
		if(flag==1||flag==2)
		{
			return SUCCESS;
		}

		//
		company.setCurrentState("0");
		company.setIsDelete("0");
		String currentTime=SJDateUtil.DEFAULT_FORMAT.format(new Date());
		company.setCreateTime(currentTime);
		company.setUpdateTime(currentTime);
		//
		Serializable serializable=companyService.insert(company);
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

	public String queryCompany()
	{
		company=companyService.query(company.getId());
		return manuType;
	}

	public String modifyCompany()
	{
		flag=companyService.update(company)?1:0;
		return SUCCESS;
	}

	public String deleteCompany()
	{
		company=companyService.query(company.getId());
		companyService.deleteForLogic(company);
		Set<User> userList=company.getUsers();
		if(userList!=null)
		{
			for(User user:userList)
			{
				userService.deleteForLogic(user);
			}
		}
		flag=1;
		return SUCCESS;
	}

	public String passCompany()
	{
		String[] idArray=ids.split(";");
		for(String id:idArray)
		{
			Company company=companyService.query(Long.parseLong(id));
			if(!company.getCurrentState().equals("1"))
			{
				company.setCurrentState("1");
			}
			companyService.update(company);
		}
		return SUCCESS;
	}

	public String rejectCompany()
	{
		String[] idArray=ids.split(";");
		for(String id:idArray)
		{
			Company company=companyService.query(Long.parseLong(id));
			if(!company.getCurrentState().equals("2"))
			{
				company.setCurrentState("2");
			}
			companyService.update(company);
		}
		return SUCCESS;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * excel导出
	 */
	private String[] exportTitle={"序号","所属区域","登录代码","公司名称","公司地址","联系电话","公司法人","工商注册号","所属派出所"};

	public String outExcel()
	{
		// 获取数据源
		HSSFWorkbook workbook=OutExcel.printExcel(exportTitle,convertEntityToObejct());
		// 导出
		excelOrWordStream=OutExcel.exportExcel(excelOrWordStream,workbook);
		return SUCCESS;
	}

	/**
	 * 将实体转换为对象
	 */
	private List<Object[]> convertEntityToObejct()
	{
		if(company==null)
		{
			company=new Company();
		}
		company.setCompanyName(outExcelCompanyName);
		company.setAddress(outExcelCompanyAddress);
		company.setTelephone(outExcelCompanyPhone);

		//
		List<Company> companyList=companyService.query(company);

		if(companyList!=null&&companyList.size()>0)
		{
			List<Object[]> dataList=new ArrayList<Object[]>();
			for(int i=0;i<companyList.size();i++)
			{
				Company c=companyList.get(i);
				Object[] obj=new Object[exportTitle.length-1];
				for(int j=0;j<(exportTitle.length-1);j++)
				{
					switch(j)
					{
						case 0:
							obj[0]=c.getArea().getAreaName();
							break;
						case 1:
							obj[1]=c.getCompanyCode();
							break;
						case 2:
							obj[2]=c.getCompanyName();
							break;
						case 3:
							obj[3]=c.getAddress();
							break;
						case 4:
							obj[4]=c.getTelephone();
							break;
						case 5:
							obj[5]=c.getCorporationName();
							break;
						case 6:
							obj[6]=c.getCorporationId();
							break;
						case 7:
							obj[7]=c.getRemarks();
							break;
						default:
							break;
					}
				}
				dataList.add(obj);
			}
			return dataList;
		}
		return null;
	}
}
