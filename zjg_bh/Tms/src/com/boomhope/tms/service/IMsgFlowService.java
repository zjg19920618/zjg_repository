package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.MsgFlow;

/***
 * 报文流水Service接口
 * @author shaopeng
 *
 */
public interface IMsgFlowService {
	
	/***
	 * 根据报文流水id获取报文流水
	 * @param msgFlowId
	 * @return
	 */
	public MsgFlow getMsgFlow(String msgFlowId);
	
	/***
	 * 根据条件获取报文流水 
	 * @param map
	 * @return
	 */
	public List<MsgFlow> getMsgFlows(Map<String, String> map);
	
	/***
	 * 新增报文流水
	 * @param msgFlow
	 */
	public void addMsgFlow(MsgFlow msgFlow);
	
	/***
	 * 更新报文流水
	 * @param msgFlow
	 */
	public void updateMsgFlow(MsgFlow msgFlow);
	
	/***
	 * 删除报文流水
	 * @param msgFlowId
	 */
	public void deteleMsgFlow(String msgFlowId);
	
	/***
	 * 根据交易流水号获取报文流水List
	 * @param trasnFlowId
	 * @return
	 */
	public List<MsgFlow> getMsgFlowByTransFlowId(String trasnFlowId);

}
