package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.TransFlow;

/***
 * 交易流水Service接口
 * @author shaopeng
 *
 */
public interface ISequenceManageService {
	public String getSequenceNom(String seqCode,int length)throws Exception;
	
	public String getTaskNum(String seqCode,int length,String svrDate)throws Exception;
}