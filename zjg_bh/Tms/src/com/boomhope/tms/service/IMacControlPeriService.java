package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.BaseParameter;
import com.boomhope.tms.entity.MacControlPeri;
import com.boomhope.tms.entity.MacWarning;
import com.boomhope.tms.entity.MacWarningView;
import com.boomhope.tms.entity.Page;

public interface IMacControlPeriService {

	/**
	 * 终端信息查询详细
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacControlPeri> findMacControlPeriList(Map<String,String> map);
	
	/**
	 * 预警查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<MacWarningView> findMacWarningViewList(Page page,Map<String,String> map);
	
	/**
	 * 保存预警信息
	 * @param m
	 */
	public void updateMacWaring(MacWarning m);
	
	/**
	 * 保存预警信息
	 * @param m
	 */
	public void saveMacWaring(MacWarning m);

	
	/**
	 * 判断是否已登记
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
	 * 添加预警信息
	 */
	public void saveBaseParameter(BaseParameter baseParameter);
	
	/**
	 * 修改预警信息
	 */
	public void updateBaseParameter(BaseParameter baseParameter);

	

	

}
