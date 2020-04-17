package com.boomhope.tms.pojo;

public class ControlPojo {

	private  String createDate;//时间
	private String warDesc;//描述
	
	public ControlPojo(){
		
	}
	public ControlPojo(Object[] objects){
		super();
		this.createDate = (String) objects[0];
		this.warDesc = (String) objects[1];
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getWarDesc() {
		return warDesc;
	}
	public void setWarDesc(String warDesc) {
		this.warDesc = warDesc;
	}
	
	
}
