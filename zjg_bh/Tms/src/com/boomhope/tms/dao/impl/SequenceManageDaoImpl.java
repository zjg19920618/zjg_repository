package com.boomhope.tms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.boomhope.tms.dao.IAuthenticFlowDao;
import com.boomhope.tms.dao.IBaseDictDao;
import com.boomhope.tms.dao.ISequenceManageFlowDao;
import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BaseDict;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.SequenceManage;
@Repository(value="SequenceManageDaoImpl")
public class SequenceManageDaoImpl extends BaseDao<SequenceManage> implements ISequenceManageFlowDao {

	@Override
	public SequenceManage getSequenceManageByCode(String seqCode)
			throws Exception {
		List list=findList("from SequenceManage where seqCode='"+seqCode+"'");
		getCurrentSession().clear();
		if(list!=null&&list.size()!=0){
			return (SequenceManage)list.get(0);
		}
		return null;
	}

	@Override
	public void updateSequence(SequenceManage seq) throws Exception {
		String sql="update SequenceManage set seqValue='"+seq.getSeqValue()+"',seqDate='"+seq.getSeqDate()+"' where seqId="+seq.getSeqId();
		executeHql(sql);
	}

}
