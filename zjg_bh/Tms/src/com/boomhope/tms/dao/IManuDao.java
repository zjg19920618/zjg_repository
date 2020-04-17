package com.boomhope.tms.dao;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;

public interface IManuDao extends IBaseDao<Manu>{

	/**
	 * 查询厂商信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList(Page page,Map<String,String> map);
	
	/**
	 * 查询厂商信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList(Map<String,String> map);
	
	/**
	 * 保存厂商信息
	 * @param manu
	 */
	public void saveManu(Manu manu);
	
	/**
	 * 编辑
	 * @param manu
	 */
	public void editManu(Manu manu);
	
	/**
	 * 删除
	 * @param manuCode
	 */
	public void delManu(String manuCode);

	
	/**
	 * 厂商维护添加判重
	 * @param manu
	 */
	public List<Manu> findManu(String manuCode);
	public List<Manu> findManu1(String manuName);
	
	/**
	 * 不分页查询
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList1(Map<String,String> map);
	
	/**
	 * 编辑
	 * @param manu
	 */
	public void editManu1(Manu manu);

	/**
	 * 厂商维护编辑判重
	 * @param manu
	 */
	public List<Manu> findManu2(String manuCode, String manuName);
}
