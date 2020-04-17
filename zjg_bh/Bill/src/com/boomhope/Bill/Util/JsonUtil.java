package com.boomhope.Bill.Util;

import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static String toStr(Object object){
		return JSONObject.fromObject(object).toString();
	}

}
