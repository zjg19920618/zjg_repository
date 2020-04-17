package com.boomhope.Bill.PublicControl.adminTrans.Bean;

/**
 * 管理员bean
 * @author hk
 *
 */

public class AdminBean {
	
	private String startBo; // 起始凭证号
	private String endBo; // 结尾凭证号
	private String nowBo; // 当前凭证号
	private String busBillId; // 凭证id
	private String nowNumber; // 剩余存单数
	private String createDate; // 创建时间
	private String updateDate; // 修改时间
	private String adminName; //管理员用户
	private String paw; //管理员密码
	private String paw2; //管理员密码修改
	private String errmsg;  // 错误原因
	
	
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPaw() {
		return paw;
	}

	public void setPaw(String paw) {
		this.paw = paw;
	}

	public String getPaw2() {
		return paw2;
	}

	public void setPaw2(String paw2) {
		this.paw2 = paw2;
	}

	public String getNowNumber() {
		return nowNumber;
	}

	public void setNowNumber(String nowNumber) {
		this.nowNumber = nowNumber;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getBusBillId() {
		return busBillId;
	}

	public void setBusBillId(String busBillId) {
		this.busBillId = busBillId;
	}

	public String getNowBo() {
		return nowBo;
	}

	public void setNowBo(String nowBo) {
		this.nowBo = nowBo;
	}

	public String getEndBo() {
		return endBo;
	}

	public void setEndBo(String endBo) {
		this.endBo = endBo;
	}

	public String getStartBo() {
		return startBo;
	}

	public void setStartBo(String startBo) {
		this.startBo = startBo;
	}
	
	
	
}
