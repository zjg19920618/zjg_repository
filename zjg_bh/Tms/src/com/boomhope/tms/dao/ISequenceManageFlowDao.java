package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.SequenceManage;


public interface ISequenceManageFlowDao extends IBaseDao<SequenceManage>{
	
	public SequenceManage getSequenceManageByCode(String seqCode)throws Exception;
	public void updateSequence(SequenceManage seq)throws Exception;
}
