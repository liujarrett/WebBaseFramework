package com.base.web.area;

import java.util.Set;

import com.base.core.ssh.l0model.BaseEntity;
import com.base.web.company.Company;

public class Area extends BaseEntity {

	private static final long serialVersionUID = -3684468471596282991L;

	// 区域标识
	private long id;

	// 上级区域
	private Area parent;

	// 下级组织
	private Set<Area> childList;

	// 所有公司
	private Set<Company> companyList;

	// 当前区域层级
	private int areaLevel;// 1,2,3

	// 区域编号
	private String areaCode;// 0451,

	// 区域类型
	private String areaType;// province（1）,city（2）,district（3）

	// 区域名称
	private String areaName;

	public Area() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public Set<Area> getChildList() {
		return childList;
	}

	public void setChildList(Set<Area> childList) {
		this.childList = childList;
	}

	public Set<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(Set<Company> companyList) {
		this.companyList = companyList;
	}

	public int getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(int areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
