package com.boomhope.tms.message.out;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  Out01597ResBodyBean 
* @Description   黑灰名单返回bean
* @author zh.m 
* @date 2016年12月2日 下午2:56:41  
*/
@XStreamAlias("BODY")
public class Out01597ResBodyBean {
	
	public String ESB_LISTCount;//列表数
	public Out01597ResBodyList ESB_LIST;//数组列表
	
	public String getESB_LISTCount() {
		return ESB_LISTCount;
	}
	public void setESB_LISTCount(String eSB_LISTCount) {
		ESB_LISTCount = eSB_LISTCount;
	}
	public Out01597ResBodyList getESB_LIST() {
		return ESB_LIST;
	}
	public void setESB_LIST(Out01597ResBodyList eSB_LIST) {
		ESB_LIST = eSB_LIST;
	}
	
}
