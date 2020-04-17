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

public class MachineExcel {
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	/**
	 * 初始化
	 * @param path2
	 */
	public MachineExcel(String path2){
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path2 + "/ReportTemplate/jiqiweihubiao.xls"));
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
	public boolean makeReport(List<MachineBean> dataList, String filename) throws IOException{

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
	private void makeReportData(List<MachineBean> dataList){
		
		for (int i=0; i<dataList.size(); i++){
			MachineBean dataBean = dataList.get(i);
			makeReportDataRow(i+3, dataBean);
		}
		
	}
	/**
	 * 生成单行数据
	 * @param rowNumber
	 * @param dataBean
	 */
	private void makeReportDataRow(int rowNumber, MachineBean dataBean){
		
		/* 数据行数 */
		HSSFRow row = sheet.createRow(rowNumber);
		
		/* 填充数据 */
		row.createCell(0).setCellValue(dataBean.getMachineCode());	// 机器型号
		row.createCell(1).setCellValue(dataBean.getMachineType());	// 机器类型
		row.createCell(2).setCellValue(dataBean.getMachineName());	// 机器名称
		row.createCell(3).setCellValue(dataBean.getManuCode());	//所属厂商
		row.createCell(4).setCellValue(dataBean.getMachineDesc());// 备注
		
		for (int i=0; i<5; i++){
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

