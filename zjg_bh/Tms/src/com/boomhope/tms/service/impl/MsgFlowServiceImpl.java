package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.IMsgFlowDao;
import com.boomhope.tms.dao.ITransFlowDao;
import com.boomhope.tms.entity.MsgFlow;
import com.boomhope.tms.service.IMsgFlowService;

@SuppressWarnings("unused")
@Service("MsgFlowService")
public class MsgFlowServiceImpl implements IMsgFlowService {

	private static final Log log = LogFactory.getLog(LoginServiceImpl.class);
	
	private IMsgFlowDao msgFlowDao;
    
	@Resource(name="msgFlowDao")
	public void setMsgFlowDao(IMsgFlowDao msgFlowDao) {
		this.msgFlowDao = msgFlowDao;
	}

	@Override
	public MsgFlow getMsgFlow(String msgFlowId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MsgFlow> getMsgFlows(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMsgFlow(MsgFlow msgFlow) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateMsgFlow(MsgFlow msgFlow) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deteleMsgFlow(String msgFlowId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MsgFlow> getMsgFlowByTransFlowId(String trasnFlowId) {
		// TODO Auto-generated method stub
		return null;
	}

}
