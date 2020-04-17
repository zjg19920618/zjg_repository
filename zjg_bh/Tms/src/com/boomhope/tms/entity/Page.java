package com.boomhope.tms.entity;


import java.io.Serializable;

/**
 * 分页信息表
 * @author zy
 */
@SuppressWarnings("all")
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 当前页数 */
	private Integer pageNo = 1;
	/** 每页显示的记录数 */
	private Integer pageSize = 20;
	/** 总页数 */
	private Integer pageCount = 0;
	/** 根据查询条件得到的总行数 */
	private Integer rowCount = 0;
	/** 起始行号 */
	private Integer rowStart = 0;
	/** 结束行号 */
	private Integer rowEnd = 0;
	/** 排序字符串 */
	private String sortString = "";
	/** 排序字段 */
	private String sort = "";
	/** 顺序 asc或者desc */
	private String order = "asc";

	public Page() {
		super();
	}

	public Page(Integer pageNo, Integer pageSize) {
		super();
		this.pageNo = pageNo == null ? 1 : pageNo;
		this.pageSize = pageSize == null ? 20 : pageSize;
	}

	public Integer getPageNo() {
		return pageNo == null ? 1 : pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo == null ? 1 : pageNo;
	}

	public Integer getPageSize() {
		return pageSize == null ? 20 : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize == null ? 20 : pageSize;
	}

	public Integer getPageCount() {
		return pageCount == null ? 0 : pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount == null ? 0 : pageCount;
	}

	public Integer getRowCount() {
		return rowCount == null ? 0 : rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount == null ? 0 : rowCount;
		setPageCount((this.rowCount + this.pageSize - 1) / this.pageSize);
		setRowStart((this.pageNo - 1) * this.pageSize);
		int divide = rowCount / this.pageSize;
		int residue = rowCount % this.pageSize;
		setRowEnd(((this.rowStart + this.pageSize < this.rowCount) ? (this.rowStart + this.pageSize) : this.rowCount));
	}

	public Integer getRowStart() {
		return rowStart == null ? 0 : rowStart;
	}

	public void setRowStart(Integer rowStart) {
		this.rowStart = rowStart == null ? 0 : rowStart;
	}

	public Integer getRowEnd() {
		return rowEnd == null ? 0 : rowEnd;
	}

	public void setRowEnd(Integer rowEnd) {
		this.rowEnd = rowEnd == null ? 0 : rowEnd;
	}

	public String getSortString() {
		return sortString;
	}

	public void setSortString(String sortString) {
		this.sortString = sortString;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
