/**
 * 文件名：Page.java
 *
 * 版本信息：
 * 日期：2011-3-14
 * Copyright 黑龙江中科方德软件有限公司 2011
 *
 */
package com.base.core.page;

import java.util.List;

import com.base.core.ssh.l0model.BaseEntity;

/**
 * 类名称：Page 类描述：公用分页类
 */
public class PageBean<T extends BaseEntity> {
	/**
	 * 要返回的某一页的记录列表
	 */
	private List<T> list;
	/**
	 * 总记录数
	 */
	private int allRow;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 是否为第一页
	 */
	@SuppressWarnings("unused")
	private boolean isFirstPage;

	/**
	 * 是否为最后一页
	 */
	@SuppressWarnings("unused")
	private boolean isLastPage;
	/**
	 * 是否有前一页
	 */
	@SuppressWarnings("unused")
	private boolean hasPreviousPage;
	/**
	 * 是否有下一页
	 */
	@SuppressWarnings("unused")
	private boolean hasNextPage;

	/**
	 * 　　 * 初始化分页信息 　　
	 */
	public void init() {
		this.isFirstPage = isFirstPage();
		this.isLastPage = isLastPage();
		this.hasPreviousPage = isHasPreviousPage();
		this.hasNextPage = isHasNextPage();
	}

	/**
	 * 判断当前页是否为第一页
	 * 
	 * @return (true:是 false:否)
	 */
	public boolean isFirstPage() {
		return (currentPage == 1);
	}

	/**
	 * 判断当前页是否为最后一页
	 * 
	 * @return true:是 false:否)
	 */
	public boolean isLastPage() {
		return (currentPage == totalPage);
	}

	/**
	 * 是否有上一节点
	 * 
	 * @return (true:是 false:否)
	 */
	public boolean isHasPreviousPage() {
		return (currentPage != 1);
	}

	/**
	 * 是否有下一节点
	 * 
	 * @return (true:是 false:否)
	 */
	public boolean isHasNextPage() {
		return (currentPage != totalPage);
	}

	/**
	 * 　　 * 计算总页数,静态方法,供外部直接通过类名调用 　　 * @param pageSize 每页记录数 　　 * @param allRow
	 * 总记录数 　　 * @return 总页数 　　
	 */
	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow
				/ pageSize + 1;
		return totalPage;
	}

	/**
	 * 　　 * 计算当前页开始记录 　　 * @param pageSize 每页记录数 　　 * @param currentPage 当前第几页
	 * 　　 * @return 当前页开始记录号 　　
	 */
	public static int countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/**
	 * 　　 * 计算当前页,若为0或者请求的URL中没有"?page=",则用1代替 　　 * @param page
	 * 传入的参数(可能为空,即0,则返回1) 　　 * @return 当前页 　　
	 */
	public static int countCurrentPage(int page) {
		final int curPage = (page == 0 ? 1 : page);
		return curPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getAllRow() {
		return allRow;
	}

	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
}
