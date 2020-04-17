package com.boomhope.tms.service;

import java.util.List;
import java.util.Map;

import com.boomhope.tms.entity.Machine;
import com.boomhope.tms.entity.Manu;
import com.boomhope.tms.entity.Page;

public interface IManuService {
	
	/**
	 * 查询厂商信息
	 * @param page
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList(Page page,Map<String,String> map);
	
	/**
	 * 查询厂商信息
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList(Map<String,String> map);
	
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
	 * 保存
	 * @param manuCode
	 */
	public void saveManu(Manu manu);

	public List<Manu> findManu(String manuCode);
	public List<Manu> findManu1(String manuName);
	public List<Manu> findManu2(String manuCode, String manuName);

	/**
	 * 不分页查询
	 * @param map
	 * @return
	 */
	public List<Manu> findManuList1(Map<String,String> map);
	
	/**
	 * 编辑
	 * @param manu
	 */
	public void editManu1(Manu manu);

	
}
