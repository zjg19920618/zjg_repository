package com.boomhope.Bill.TransService.AllTransPublicPanel.TransFlow;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.LostReport.Flow.LostFlow;

/**
 * 公共交易页面路由
 * @author hp
 *
 */
public class AllPublicTransFlow {
	//挂失解挂业务路由
	private LostFlow lostFlow;
	
	//通过子业务代码进行区别
	public void transFlow(){
		lostFlow = new LostFlow();
		switch(GlobalParameter.ACC_NO){
		case "8":
			lostFlow.flow();
			break;
		default:
			break;
		}
	}

}
