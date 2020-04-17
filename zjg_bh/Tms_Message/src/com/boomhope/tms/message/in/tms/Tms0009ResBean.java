package com.boomhope.tms.message.in.tms;

import com.boomhope.tms.message.in.InResBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/***
 * 开关状态查询
 * @author zjg
 *
 */
@XStreamAlias("Root")  
public class Tms0009ResBean extends InResBean{
	
	private Tms0009ResBodyBean bodyBean;
	
	public Tms0009ResBean(){
		this.bodyBean = new Tms0009ResBodyBean();
	}
	
	public void setBodyBean(Tms0009ResBodyBean bodyBean){
		this.bodyBean = bodyBean;
	}
	
	public Tms0009ResBodyBean getBodyBean(){
		return this.bodyBean;
	}

}
