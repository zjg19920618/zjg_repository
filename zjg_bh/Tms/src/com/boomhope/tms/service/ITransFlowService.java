package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.TransFlow;

/***
 * 交易流水Service接口
 * @author shaopeng
 *
 */
public interface ITransFlowService {
	
	/***
	 * 根据流水号获取交易流水
	 * @param transFlowId
	 * @return
	 */
	public TransFlow getTransFlow(String transFlowId);
	
	/***
	 * 根据输入条件获取交易流水List
	 * @param map
	 * @return
	 */
	public List<TransFlow> getTransFlows(Map<String, String> map);
	
	/***
	 * 新增交易流水
	 * @param transFlow
	 */
	public void addTransFlow(TransFlow transFlow);
	
	/***
	 * 更新交易流水
	 * @param transFlow
	 */
	public void updateTransFlow(TransFlow transFlow);
	
	/***
	 * 删除交易流水
	 * @param trasnFlowId
	 */
	public void deleteTransFlow(String trasnFlowId);

}