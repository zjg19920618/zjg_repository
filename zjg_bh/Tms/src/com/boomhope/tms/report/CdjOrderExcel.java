package com.boomhope.tms.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CdjOrderExcel {
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	/**
	 * 初始化
	 * @param path2
	 */
	public CdjOrderExcel(String path)throws Exception{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
		workbook= new HSSFWorkbook(fs);
		sheet = workbook.getSheetAt(0);
	}
	
	/**
	 * 生成报表
	 * @param createDate
	 * @param dataList
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public boolean makeReport(List<Map> dataList, String filename) throws Exception{
		/* 生成报表数据 */
		makeReportData(dataList);
		
		/* 保存报表 */
		boolean isSave = saveToFile(filename);
		return isSave;
	}

	/***
	 * 生成报表数据
	 * @param dataList
	 */
	private void makeReportData(List<Map> dataList){
		
		for (int i=0; i<dataList.size(); i++){
			Map dataBean = dataList.get(i);
			makeReportDataRow(i+3, dataBean);
		}
		
	}
	/**
	 * 生成单行数据
	 * @param rowNumber
	 * @param dataBean
	 */
	private void makeReportDataRow(int rowNumber, Map dataBean){
		
		/* 数据行数 */
		HSSFRow row = sheet.createRow(rowNumber);
		Set keys=dataBean.keySet();
		HSSFCellStyle style = workbook.createCellStyle();
		for (Object key:keys) {
			String value;
			if(dataBean.get(key.toString())!=null){
				value=dataBean.get(key.toString()).toString();
			}else{
				value="";
			}
			row.createCell(Integer.valueOf((key.toString()))).setCellValue(value);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			row.getCell(Integer.valueOf((key.toString()))).setCellStyle(style);
		}
	}
	
	/***
	 * 保存报表文件
	 * @param filename
	 * @throws IOException 
	 */
	private boolean saveToFile(String filename) throws IOException{
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename);
		} catch (Exception e) {
			return false;
		}
		workbook.write(fileOut);
		fileOut.flush();
	    fileOut.close();
	    return true;
	}
	public static void main(String[] args) {
		
	}
}
