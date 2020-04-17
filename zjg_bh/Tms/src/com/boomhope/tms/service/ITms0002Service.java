package com.boomhope.tms.service;

import com.boomhope.tms.message.in.tms.Tms0002ReqBean;

/***
 * 交易Tms0002(机器状态监控)Service接口
 * @author shaopeng
 *
 */
public interface ITms0002Service {
	
	/***
	 * 机器状态正常处理
	 * @param reqBean
	 */
	public String doSuccess(Tms0002ReqBean reqBean) throws Exception;
	
	/***
	 * 机器状态异常处理
	 * @param reqBean
	 */
	public String doFail(Tms0002ReqBean reqBean) throws Exception;

}
