package com.boomhope.Bill.Util;

import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;


public class ComboBox extends JComboBox {
	
	private List<Map<String, Object>> elements=null;
	
	public ComboBox(){
		super();
	}
	/**
	 * 添加数据项
	 * @param provinces
	 */
	public void setElements(List<Map<String, Object>> provinces){
		elements=provinces;
		for (int i = 0; i < provinces.size(); i++) {
			super.addItem(provinces.get(i).get("text"));
		}
	}
	
	/**
	 * 获取数据项
	 * @return
	 */
	public Map getSelectItem(){
		if(super.getSelectedIndex()==-1){
			return elements.get(super.getSelectedIndex()+1);
		}
		return elements.get(super.getSelectedIndex());
	}
}
