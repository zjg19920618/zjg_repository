package com.boomhope.Bill.TransService.BudingJrnlNo.bean;

public class BulidingJrnNoBean {
	//卡系统-交易辅助登记
		private String workNo;//工号
		private String jrnlNo;//交易流水号
		private String accessCode;//领取码
		private String custNos;//客户号
		private String recName;//推荐人姓名
		private String recTelNo;//推荐人手机号
		private String name;//被推荐人姓名 
		private String telNos;//被推荐人手机号
		private String qryType;//查询条件
		private String success;//成功
		private String tag;//标记	
		private String errmsg;//错误信息
		private String checkService;//选择办理的业务
		
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
		public String getWorkNo() {
			return workNo;
		}
		public void setWorkNo(String workNo) {
			this.workNo = workNo;
		}
		public String getJrnlNo() {
			return jrnlNo;
		}
		public void setJrnlNo(String jrnlNo) {
			this.jrnlNo = jrnlNo;
		}
		public String getAccessCode() {
			return accessCode;
		}
		public void setAccessCode(String accessCode) {
			this.accessCode = accessCode;
		}
		public String getCustNos() {
			return custNos;
		}
		public void setCustNos(String custNos) {
			this.custNos = custNos;
		}
		public String getRecName() {
			return recName;
		}
		public void setRecName(String recName) {
			this.recName = recName;
		}
		public String getRecTelNo() {
			return recTelNo;
		}
		public void setRecTelNo(String recTelNo) {
			this.recTelNo = recTelNo;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTelNos() {
			return telNos;
		}
		public void setTelNos(String telNos) {
			this.telNos = telNos;
		}
		public String getQryType() {
			return qryType;
		}
		public void setQryType(String qryType) {
			this.qryType = qryType;
		}
		public String getSuccess() {
			return success;
		}
		public void setSuccess(String success) {
			this.success = success;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public String getCheckService() {
			return checkService;
		}
		public void setCheckService(String checkService) {
			this.checkService = checkService;
		}
		
}
