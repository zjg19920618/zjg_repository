package com.boomhope.tms.util;


/***
 * 汇划次日到账后，生成非当日记账核心流水文件实体类
 * @author Administrator
 *
 */
@Number(id = 1)
public class ShjdFileBean {
	
	//交易任务号（24）||核心记账日期（8）||核心记账流水号（8）
	@FieldSort(NO = 0)
	private String TASK_IDTRNS;//交易任务号
	@FieldSort(NO = 1)
	private String svrDate;//核心记账日期
	@FieldSort(NO = 2)
	private String svrJrnlNo;//核心记账流水
	@FieldSort(NO = 3)
	private String state;//成功标志
	@FieldSort(NO = 4)
	private String tellerNo;//柜员号
	@FieldSort(NO = 5)
	private String branchNo;//机构号
	
	public String getTASK_IDTRNS() {
		return TASK_IDTRNS;
	}
	public void setTASK_IDTRNS(String tASK_IDTRNS) {
		TASK_IDTRNS = tASK_IDTRNS;
	}
	public String getSvrDate() {
		return svrDate;
	}
	public void setSvrDate(String svrDate) {
		this.svrDate = svrDate;
	}
	public String getSvrJrnlNo() {
		return svrJrnlNo;
	}
	public void setSvrJrnlNo(String svrJrnlNo) {
		this.svrJrnlNo = svrJrnlNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTellerNo() {
		return tellerNo;
	}
	public void setTellerNo(String tellerNo) {
		this.tellerNo = tellerNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
}
