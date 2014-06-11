package com.lock.unlockInfo.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.base.core.page.PageBean;
import com.base.core.ssh.l1action.BaseAction;
import com.base.core.utilities.OutExcel;
import com.base.web.area.Area;
import com.base.web.area.service.AreaService;
import com.base.web.company.Company;
import com.base.web.company.service.CompanyService;
import com.base.web.user.User;
import com.lock.unlockInfo.UnlockInfo;
import com.lock.unlockInfo.service.UnlockInfoService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class UnlockInfoAction extends BaseAction
{

	private static final long serialVersionUID=1904763586093277851L;

	private UnlockInfoService unlockInfoService;
	private UnlockInfo unlockInfo;
	private PageBean<UnlockInfo> pageBean;
	private long id;
	private String manuType;
	//
	private long treeParentId;
	private AreaService areaService;
	private CompanyService companyService;
	private List<Object[]> treeList;

	//outExcel
	private String outExcelOpenerUserName;
	private String outExcelCustomerName;
	private Date outExcelStartTime;
	private Date outExcelEndTime;
	private String outExcelOpenType;
	private String outExcelChooseType;
	private long outExcelChooseId;
	// 输出流
	private InputStream excelOrWordStream;
	// 导出文件名
	private String fileName;

	public UnlockInfoService getUnlockInfoService()
	{
		return unlockInfoService;
	}

	public void setUnlockInfoService(UnlockInfoService unlockInfoService)
	{
		this.unlockInfoService=unlockInfoService;
	}

	public UnlockInfo getUnlockInfo()
	{
		return unlockInfo;
	}

	public void setUnlockInfo(UnlockInfo unlockInfo)
	{
		this.unlockInfo=unlockInfo;
	}

	public PageBean<UnlockInfo> getPageBean()
	{
		return pageBean;
	}

	public void setPageBean(PageBean<UnlockInfo> pageBean)
	{
		this.pageBean=pageBean;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id=id;
	}

	public String getManuType()
	{
		return manuType;
	}

	public void setManuType(String manuType)
	{
		this.manuType=manuType;
	}

	public long getTreeParentId()
	{
		return treeParentId;
	}

	public void setTreeParentId(long treeParentId)
	{
		this.treeParentId=treeParentId;
	}

	public AreaService getAreaService()
	{
		return areaService;
	}

	public void setAreaService(AreaService areaService)
	{
		this.areaService=areaService;
	}

	public CompanyService getCompanyService()
	{
		return companyService;
	}

	public void setCompanyService(CompanyService companyService)
	{
		this.companyService=companyService;
	}

	public List<Object[]> getTreeList()
	{
		return treeList;
	}

	public void setTreeList(List<Object[]> treeList)
	{
		this.treeList=treeList;
	}

	public String getOutExcelOpenerUserName()
	{
		return outExcelOpenerUserName;
	}

	public void setOutExcelOpenerUserName(String outExcelOpenerUserName)
	{
		this.outExcelOpenerUserName=outExcelOpenerUserName;
	}

	public String getOutExcelCustomerName()
	{
		return outExcelCustomerName;
	}

	public void setOutExcelCustomerName(String outExcelCustomerName)
	{
		this.outExcelCustomerName=outExcelCustomerName;
	}

	public Date getOutExcelStartTime()
	{
		return outExcelStartTime;
	}

	public void setOutExcelStartTime(Date outExcelStartTime)
	{
		this.outExcelStartTime=outExcelStartTime;
	}

	public Date getOutExcelEndTime()
	{
		return outExcelEndTime;
	}

	public void setOutExcelEndTime(Date outExcelEndTime)
	{
		this.outExcelEndTime=outExcelEndTime;
	}

	public String getOutExcelOpenType()
	{
		return outExcelOpenType;
	}

	public void setOutExcelOpenType(String outExcelOpenType)
	{
		this.outExcelOpenType=outExcelOpenType;
	}

	public String getOutExcelChooseType()
	{
		return outExcelChooseType;
	}

	public void setOutExcelChooseType(String outExcelChooseType)
	{
		this.outExcelChooseType=outExcelChooseType;
	}

	public long getOutExcelChooseId()
	{
		return outExcelChooseId;
	}

	public void setOutExcelChooseId(long outExcelChooseId)
	{
		this.outExcelChooseId=outExcelChooseId;
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
			this.fileName=new String("开锁信息列表.xls".getBytes(),"ISO8859-1");
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
			this.fileName=new String("开锁信息列表.xls".getBytes(),"ISO8859-1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	public String queryForGrid()
	{
		if(unlockInfo.getUser()==null)
		{
			User user=new User();
			unlockInfo.setUser(user);
		}
		/**/
		if(manuType!=null&&manuType.equals("AREA_TYPE"))
		{ // 根据区域id查询
			User user=unlockInfo.getUser();
			Area area=new Area();
			area.setId(id);
			Company company=new Company();
			company.setArea(area);
			user.setCompany(company);
		}
		else if(manuType!=null&&manuType.equals("COMPANY_TYPE"))
		{ //根据公司id查询
			User user=unlockInfo.getUser();
			Company company=new Company();
			company.setId(id);
			user.setCompany(company);
		}
		/**/
		pageBean=unlockInfoService.query(unlockInfo,pageBean);
		return SUCCESS;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/***
	 * 获取区域树
	 * */
	public String queryTreeOfAreaAndCompany()
	{
		List<Area> list=areaService.queryByParentId(treeParentId);
		if(list!=null&&list.size()>0)
		{
			for(Area area:list)
			{
				if(treeList==null)
				{
					treeList=new ArrayList<Object[]>();
				}
				Object[] obj=new Object[3];
				obj[0]=area.getId()+"-"+0; // 区域id + "-" + 公司id
				obj[1]=area.getAreaName(); // 名称
				obj[2]=0; // 下级机构个数
				/*下级机构个数*/
				List<Area> childList=areaService.queryByParentId(area.getId());
				if(childList!=null&&childList.size()>0)
				{
					obj[2]=childList.size();
				}
				else
				{
					/*下级公司个数*/
					int size=0;
					Set<Company> companList=area.getCompanyList();
					for(Company company:companList)
					{
						if(company.getIsDelete().equalsIgnoreCase("0"))
						{
							size++;
						}
					}
					obj[2]=size;
				}
				treeList.add(obj);
			}
		}
		else
		{
			Area area=areaService.queryByPK(treeParentId);
			Set<Company> companList=area.getCompanyList();
			for(Company company:companList)
			{
				if(company.getIsDelete().equalsIgnoreCase("0"))
				{
					if(treeList==null)
					{
						treeList=new ArrayList<Object[]>();
					}
					Object[] obj=new Object[3];
					obj[0]=0+"-"+company.getId(); //  区域id + "-" + 公司id
					obj[1]=company.getCompanyName(); // 名称
					obj[2]=0;
					treeList.add(obj);
				}
			}
		}
		return SUCCESS;
	}

	/* ################################################################################################## */
	/* 这 是 一 条 和 谐 的 分 割 线 */
	/* ################################################################################################## */

	/**
	 * excel导出
	 */
	private String[] exportTitle={"序号","开锁人","客户姓名","客户身份证号","开锁时间","开锁地点","开锁类型","开锁公司","服务单","客户身份证号","驾驶证","行驶证","执照","介绍信"};

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
		if(unlockInfo==null)
		{
			unlockInfo=new UnlockInfo();
		}
		if(unlockInfo.getUser()==null)
		{
			User user=new User();
			unlockInfo.setUser(user);
		}
		/**/
		if(outExcelChooseType!=null&&outExcelChooseType.equals("AREA_TYPE"))
		{ // 根据区域id查询
			User user=unlockInfo.getUser();
			Area area=new Area();
			area.setId(outExcelChooseId);
			Company company=new Company();
			company.setArea(area);
			user.setCompany(company);
		}
		else if(outExcelChooseType!=null&&outExcelChooseType.equals("COMPANY_TYPE"))
		{ //根据公司id查询
			User user=unlockInfo.getUser();
			Company company=new Company();
			company.setId(outExcelChooseId);
			user.setCompany(company);
		}
		unlockInfo.getUser().setUserName(outExcelOpenerUserName);
		unlockInfo.setCustomerName(outExcelCustomerName);
		unlockInfo.setUnlockStartTime(outExcelStartTime);
		unlockInfo.setUnlockEndTime(outExcelEndTime);
		unlockInfo.setUnlockType(outExcelOpenType);
		//
		List<UnlockInfo> unlockInfoList=unlockInfoService.query(unlockInfo);

		if(unlockInfoList!=null&&unlockInfoList.size()>0)
		{
			List<Object[]> dataList=new ArrayList<Object[]>();
			for(int i=0;i<unlockInfoList.size();i++)
			{
				UnlockInfo unlock=unlockInfoList.get(i);
				Object[] obj=new Object[exportTitle.length-1];
				for(int j=0;j<(exportTitle.length-1);j++)
				{
					switch(j)
					{
						case 0:
							obj[0]=unlock.getUser().getUserName();
							break;
						case 1:
							obj[1]=unlock.getCustomerName();
							break;
						case 2:
							obj[2]=unlock.getCustomerIdNo();
							break;
						case 3:
							obj[3]=unlock.getUnlockTime();
							break;
						case 4:
							obj[4]=unlock.getUnlockLocation();
							break;
						case 5:
						{
							if(unlock.getUnlockType().equals("1"))
							{
								obj[5]="开民用锁";
							}
							else if(unlock.getUnlockType().equals("2"))
							{
								obj[5]="开汽车锁";
							}
							else if(unlock.getUnlockType().equals("3"))
							{
								obj[5]="开保险箱";
							}
							else if(unlock.getUnlockType().equals("4"))
							{
								obj[5]="开ATM取款机";
							}
							else if(unlock.getUnlockType().equals("5"))
							{
								obj[5]="开金库";
							}
							else if(unlock.getUnlockType().equals("100"))
							{
								obj[5]="其它";
							}
						}
							break;
						case 6:
							obj[6]=unlock.getUser().getCompany().getCompanyName();
							break;
						case 7:
							obj[7]=unlock.getUnlockWorkOrderImg();
							break;
						case 8:
							obj[8]=unlock.getCustomerIdImg();
							break;
						case 9:
							obj[9]=unlock.getCustomerDrivingLicenseImg();
							break;
						case 10:
							obj[10]=unlock.getCustomerVehicleLicenseImg();
							break;
						case 11:
							obj[11]=unlock.getCustomerBusinessLicenseImg();
							break;
						case 12:
							obj[12]=unlock.getCustomerIntroductionLetterImg();
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
