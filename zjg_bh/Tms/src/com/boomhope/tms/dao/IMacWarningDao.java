package com.boomhope.tms.dao;

import java.util.List;

import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.MacWarning;

public interface IMacWarningDao extends IBaseDao<MacWarning>{
	
	/**
	 * 判断预警信息是否已登记，并未处理
	 * @param machineNo
	 * @return
	 */
	public boolean ifRegiter(String machineNo);

	/**
	 *查询所有-预警设置
	 * */
	public BaseParameter findMacWarByType();

	/**
	 *查询预警信息
	 * */
	public List<BaseParameter> findBaseParameterList();


	/**
	 * 修改预警信息
	 */
	public void updateBaseParameter(BaseParameter baseParameter);
	

}
