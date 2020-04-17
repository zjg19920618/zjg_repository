package com.boomhope.tms.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *厂商维护数据表
 * @author Administrator
 *
 */
public class MacManufacturer {
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	/**
	 * 初始化
	 * @param path2
	 */
	public MacManufacturer(String path2){
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path2 + "/ReportTemplate/changshangweihubiao.xls"));
			workbook= new HSSFWorkbook(fs);
			sheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * 生成报表
	 * @param createDate
	 * @param dataList
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public boolean makeReport2(List<MacManufacturerBean> dataList, String filename) throws IOException{

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
	private void makeReportData(List<MacManufacturerBean> dataList){
		
		for (int i=0; i<dataList.size(); i++){
			MacManufacturerBean dataBean = dataList.get(i);
			makeReportDataRow(i+3, dataBean);
		}
		
	}
	/**
	 * 生成单行数据
	 * @param rowNumber
	 * @param dataBean
	 */
	private void makeReportDataRow(int rowNumber, MacManufacturerBean dataBean){
		
		/* 数据行数 */
		HSSFRow row = sheet.createRow(rowNumber);
		
		/* 填充数据 */
		row.createCell(0).setCellValue(dataBean.getManuCode());	// 厂商编号
		row.createCell(1).setCellValue(dataBean.getManuName());	// 厂商名称
		row.createCell(2).setCellValue(dataBean.getConPerson());	// 联系人
		row.createCell(3).setCellValue(dataBean.getPhone());		// 座机号码
		row.createCell(4).setCellValue(dataBean.getTel());	// 手机号
		row.createCell(5).setCellValue(dataBean.getManuStatus());		// 厂商状态
		row.createCell(6).setCellValue(dataBean.getManuDesc());	// 厂商描述
		
		for (int i=0; i<7; i++){
			HSSFCellStyle style = workbook.createCellStyle();
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			row.getCell(i).setCellStyle(style);
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
