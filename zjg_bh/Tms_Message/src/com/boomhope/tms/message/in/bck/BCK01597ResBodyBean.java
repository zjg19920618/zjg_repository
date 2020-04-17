package com.boomhope.tms.message.in.bck;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
* @ClassName  BCK01597ResBodyBean 
* @Description   电信诈骗黑灰名单查询-前置01597
* @author zhang.m 
* @date 2016年12月6日 下午8:15:09  
*/
@XStreamAlias("Body")
public class BCK01597ResBodyBean {
	public String ESB_LISTCount;//列表数
	public BCK01597ResBodyList ESB_LIST;//数组列表
	public String getESB_LISTCount() {
		return ESB_LISTCount;
	}
	public void setESB_LISTCount(String eSB_LISTCount) {
		ESB_LISTCount = eSB_LISTCount;
	}
	public BCK01597ResBodyList getESB_LIST() {
		return ESB_LIST;
	}
	public void setESB_LIST(BCK01597ResBodyList eSB_LIST) {
		ESB_LIST = eSB_LIST;
	}
	
	
}
