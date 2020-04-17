package com.boomhope.tms.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IAuthenticFlowDao;
import com.boomhope.tms.dao.IBaseUserDao;
import com.boomhope.tms.dao.IFlowDao;
import com.boomhope.tms.dao.ISequenceManageFlowDao;
import com.boomhope.tms.entity.AuthenticFlow;
import com.boomhope.tms.entity.BusBillMan;
import com.boomhope.tms.entity.BusFlow;
import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Page;
import com.boomhope.tms.entity.PeripheralsMachine;
import com.boomhope.tms.entity.SequenceManage;
import com.boomhope.tms.entity.orcl.CdjOrder;
import com.boomhope.tms.pojo.ReturnAccountPojo;
import com.boomhope.tms.pojo.ReturnCloseAccountPojo;
import com.boomhope.tms.pojo.ReturnForBusFlowPojo;
import com.boomhope.tms.service.IAuthenticFlowService;
import com.boomhope.tms.service.IFlowService;
import com.boomhope.tms.service.ISequenceManageService;
@SuppressWarnings("unused")
@Service("SequenceManageImpl")
public class SequenceManageImpl implements ISequenceManageService{

private static final Log log = LogFactory.getLog(SequenceManageImpl.class);
 	
	private ISequenceManageFlowDao sequenceManageFlowDao;

	@Resource(name="SequenceManageDaoImpl")
	public void setSequenceManageFlowDao(
			ISequenceManageFlowDao sequenceManageFlowDao) {
		this.sequenceManageFlowDao = sequenceManageFlowDao;
	}

	@Override
	public String getSequenceNom(String seqCode,int length) throws Exception {
		SequenceManage seq=sequenceManageFlowDao.getSequenceManageByCode(seqCode);
		SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMdd");
		String date=seq.getSeqDate();
		int id=Integer.valueOf(seq.getSeqValue());
		Date now =new Date();
		if("".equals(date)){
			date=sdf.format(now);
		}
		if(date.equals(sdf.format(now))){
			id++;
		}else{
			date=sdf.format(now);
			id=1;
		}
		seq.setSeqDate(date);
		seq.setSeqValue(intToString(id,length));
		sequenceManageFlowDao.updateSequence(seq);
		return seq.getSeqValue();
	}
	
	/**
	 * 转换int成String
	 * @return
	 */
	private String intToString(int id,int len){
		String str="";
		int length=len-(""+id).length();
		for (int i = 0; i < length; i++) {
			str+="0";
		}
		str+=id;
		log.info("获取的序列号："+str);
		return str;
	}

	@Override
	public String getTaskNum(String seqCode,int length,String svrDate) throws Exception {
		SequenceManage seq=sequenceManageFlowDao.getSequenceManageByCode(seqCode);
		String date=seq.getSeqDate();
		int id=Integer.valueOf(seq.getSeqValue());
		Date now =new Date();
		if("".equals(date)){
			date=svrDate;
		}
		if(date.equals(svrDate)){
			id++;
		}else{
			date=svrDate;
			id=200;
		}
		seq.setSeqDate(date);
		seq.setSeqValue(intToString(id,length));
		sequenceManageFlowDao.updateSequence(seq);
		return seq.getSeqValue();
	}
	
}
