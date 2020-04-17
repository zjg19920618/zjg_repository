package com.boomhope.tms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.boomhope.tms.dao.ITransFlowDao;
import com.boomhope.tms.entity.TransFlow;
import com.boomhope.tms.service.ITransFlowService;

/***
 * 交易流水Service实现类
 * @author shaopeng
 *
 */
@SuppressWarnings("unused")
@Service("TransFlowService")
public class TransFlowServiceImpl implements ITransFlowService {

	private static final Log log = LogFactory.getLog(LoginServiceImpl.class);
 
	private ITransFlowDao transFlowDao;
    
	@Resource(name="transFlowDao")
	public void setTransFlowDao(ITransFlowDao transFlowDao) {
		this.transFlowDao = transFlowDao;
	}

	@Override
	public TransFlow getTransFlow(String transFlowId) {
		return this.transFlowDao.findOne(transFlowId);
	}

	@Override
	public List<TransFlow> getTransFlows(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTransFlow(TransFlow transFlow) {
		this.transFlowDao.save(transFlow);
	}

	@Override
	public void updateTransFlow(TransFlow transFlow) {
		this.transFlowDao.saveOrUpdate(transFlow);
	}

	@Override
	public void deleteTransFlow(String trasnFlowId) {
		this.transFlowDao.delete(trasnFlowId);

	}

}
