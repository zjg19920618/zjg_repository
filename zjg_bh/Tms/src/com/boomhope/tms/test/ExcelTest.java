package com.boomhope.tms.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.boomhope.tms.report.BckFlowReport;
import com.boomhope.tms.report.BckFlowReportBean;

public class ExcelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String startDate = "20160101";
		String endDate = "20160814";
		
		List<BckFlowReportBean> dataList = new ArrayList<BckFlowReportBean>();
		
		BckFlowReportBean bean = new BckFlowReportBean();
		bean.setBranchName("营业部");
		bean.setZczqCount("2");
		bean.setZczqAmount("380000.00");
		bean.setRycCount("10");
		bean.setRycAmount("1000000.00");
		bean.setYxcCount("0");
		bean.setYxcAmount("0.00");
		bean.setLdcCount("1");
		bean.setLdcAmount("10000.00");
		bean.setXfCount("3");
		bean.setXfAmount("60000.00");
		bean.setRycjCount("5");
		bean.setRycjAmount("75000.00");
		bean.setTotalCount("21");
		bean.setTotalAmount("1520000.00");
		dataList.add(bean);
		
		BckFlowReportBean bean1 = new BckFlowReportBean();
		bean1.setBranchName("xx支行");
		bean1.setZczqCount("2");
		bean1.setZczqAmount("380001.00");
		bean1.setRycCount("10");
		bean1.setRycAmount("1000001.00");
		bean1.setYxcCount("0");
		bean1.setYxcAmount("0.00");
		bean1.setLdcCount("1");
		bean1.setLdcAmount("10001.00");
		bean1.setXfCount("3");
		bean1.setXfAmount("60001.00");
		bean1.setRycjCount("5");
		bean1.setRycjAmount("75001.00");
		bean1.setTotalCount("21");
		bean1.setTotalAmount("1520005.00");
		dataList.add(bean1);
		
/*		try {
			new BckFlowReport().makeReport(startDate, endDate, dataList, "d:/BckReport.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}


}
