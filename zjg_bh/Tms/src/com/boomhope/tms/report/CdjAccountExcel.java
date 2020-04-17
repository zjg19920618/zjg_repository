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

public class CdjAccountExcel {

	HSSFWorkbook workbook;
	HSSFSheet sheet;
	String startDate;	// 报表起始日期
	String endDate;		// 报表截止日期
	
	/***
	 * 初始化
	 * @param startDate 报表起始日期
	 * @param endDate	报表截止日期
	 */
	public CdjAccountExcel(String path2){
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path2 + "/ReportTemplate/cdjaccountExcel.xls"));
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
	
	/***
	 * 生成报表
	 * @param startDate	起始日期
	 * @param endDate	截止日期
	 * @param dataList	数据BeanList
	 * @param filename	报表文件全路径名称
	 * @throws IOException 
	 */
	public boolean makeReport(String startDate, String endDate, List<CdjAccountBean> dataList, String filename) throws IOException{

		this.startDate = startDate;
		this.endDate = endDate;

		/* 生成报表时间区域单元格 */
		makeDateCell();
		
		/* 生成报表数据 */
		makeReportData(dataList);
		
		/* 保存报表 */
		boolean isSave = saveToFile(filename);
		return isSave;
	}

	/***
	 * 生成报表时间区域单元格
	 */
	private void makeDateCell(){
		String dateText = this.startDate.substring(0, 4) + "-" + this.startDate.substring(4, 6) + "-" + 
						this.startDate.substring(6, 8) +"-"+this.startDate.substring(8, 10)+"-"+this.startDate.substring(10, 12)+"-"+this.startDate.substring(12, 14) +"至" +this.endDate.substring(0, 4) + "-" + this.endDate.substring(4, 6) + "-" + 
						this.endDate.substring(6, 8) +"-"+this.endDate.substring(8, 10)+"-"+this.endDate.substring(10, 12)+"-"+this.endDate.substring(12, 14);
		sheet.getRow(1).getCell(0).setCellValue(dateText);
	}
	
	/***
	 * 生成报表数据
	 * @param dataList
	 */
	private void makeReportData(List<CdjAccountBean> dataList){
		
		for (int i=0; i<dataList.size(); i++){
//			logger.debug("i=" + i);
			CdjAccountBean dataBean = dataList.get(i);
			makeReportDataRow(i+4, dataBean);
		}
		
	}
	
	/***
	 * 生成单行数据
	 * @param i
	 * @param dataBean
	 */
	private void makeReportDataRow(int rowNumber, CdjAccountBean dataBean){
		
		/* 数据行数 */
		HSSFRow row = sheet.createRow(rowNumber);
		
		/* 填充数据 */
		row.createCell(0).setCellValue(dataBean.getBranchName());	// 网点名称
		row.createCell(1).setCellValue(dataBean.getZcNum());	// 整存整取笔数
		row.createCell(2).setCellValue(dataBean.getZcAmt());	// 整存整取金额
		row.createCell(3).setCellValue(dataBean.getRycNum());	// 如意存笔数
		row.createCell(4).setCellValue(dataBean.getRycAmt());	// 如意存金额
		row.createCell(5).setCellValue(dataBean.getYxcNum());	// 约享存笔数
		row.createCell(6).setCellValue(dataBean.getYxcAmt());	// 约享存金额
		row.createCell(7).setCellValue(dataBean.getLdcNum());	// 立得存笔数
		row.createCell(8).setCellValue(dataBean.getLdcAmt());	// 立得存金额
		row.createCell(9).setCellValue(dataBean.getXfNum());	// 幸福1+1笔数
		row.createCell(10).setCellValue(dataBean.getXfAmt());	// 幸福1+1金额
		row.createCell(11).setCellValue(dataBean.getRjNum());	// 如意存+笔数
		row.createCell(12).setCellValue(dataBean.getRjAmt());	// 如意存+金额
		row.createCell(13).setCellValue(dataBean.getJxNum());	// 积享存笔数
		row.createCell(14).setCellValue(dataBean.getJxAmt());	// 积享存金额
		row.createCell(15).setCellValue(dataBean.getQxNum());	// 喜迎千禧笔数
		row.createCell(16).setCellValue(dataBean.getQxAmt());	// 喜迎千禧金额
		row.createCell(17).setCellValue(dataBean.getFlNum());	// 福临千禧笔数
		row.createCell(18).setCellValue(dataBean.getFlAmt());	// 福临千禧金额
		row.createCell(19).setCellValue(dataBean.getYcNum());	// 溢彩千禧笔数
		row.createCell(20).setCellValue(dataBean.getYcAmt());	// 溢彩千禧金额
		row.createCell(21).setCellValue(dataBean.getHyNum());	// 合赢千禧笔数
		row.createCell(22).setCellValue(dataBean.getHyAmt());	// 合赢千禧金额
		row.createCell(23).setCellValue(dataBean.getHdNum());	// 惠德千禧笔数
		row.createCell(24).setCellValue(dataBean.getHdAmt());	// 惠德千禧金额
		row.createCell(25).setCellValue(dataBean.getXrNum());	// 祥瑞千禧笔数
		row.createCell(26).setCellValue(dataBean.getXrAmt());	// 祥瑞千禧金额
		row.createCell(27).setCellValue(dataBean.getAllNum());	// 合计笔数
		row.createCell(28).setCellValue(dataBean.getAllAmt());	// 合计金额
		
		for (int i=0; i<29; i++){
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
//			e.printStackTrace();
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
