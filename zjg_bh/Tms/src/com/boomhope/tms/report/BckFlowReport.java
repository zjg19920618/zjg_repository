package com.boomhope.tms.report;

import java.io.File;
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

/***
 * 存单回收机业务流水报表
 * @author shaopeng
 *
 */
public class BckFlowReport {
	
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	String startDate;	// 报表起始日期
	String endDate;		// 报表截止日期
	
	/***
	 * 初始化
	 * @param startDate 报表起始日期
	 * @param endDate	报表截止日期
	 */
	public BckFlowReport(String path2){
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path2 + "/ReportTemplate/yewuliushuibiao.xls"));
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
	public boolean makeReport(String startDate, String endDate, List<BckFlowReportBean> dataList, String filename) throws IOException{

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
	private void makeReportData(List<BckFlowReportBean> dataList){
		
		for (int i=0; i<dataList.size(); i++){
//			logger.debug("i=" + i);
			BckFlowReportBean dataBean = dataList.get(i);
			makeReportDataRow(i+4, dataBean);
		}
		
	}
	
	/***
	 * 生成单行数据
	 * @param i
	 * @param dataBean
	 */
	private void makeReportDataRow(int rowNumber, BckFlowReportBean dataBean){
		
		/* 数据行数 */
		HSSFRow row = sheet.createRow(rowNumber);
		
		/* 填充数据 */
		row.createCell(0).setCellValue(dataBean.getBranchName());	// 网点名称
		row.createCell(1).setCellValue(dataBean.getZczqCount());	// 整存整取笔数
		row.createCell(2).setCellValue(dataBean.getZczqAmount());	// 整存整取金额
		row.createCell(3).setCellValue(dataBean.getRycCount());		// 如意存笔数
		row.createCell(4).setCellValue(dataBean.getRycAmount());	// 如意存金额
		row.createCell(5).setCellValue(dataBean.getYxcCount());		// 约享存笔数
		row.createCell(6).setCellValue(dataBean.getYxcAmount());	// 约享存金额
		row.createCell(7).setCellValue(dataBean.getLdcCount());		// 立得存笔数
		row.createCell(8).setCellValue(dataBean.getLdcAmount());	// 立得存金额
		row.createCell(9).setCellValue(dataBean.getXfCount());		// 幸福1+1笔数
		row.createCell(10).setCellValue(dataBean.getXfAmount());	// 幸福1+1金额
		row.createCell(11).setCellValue(dataBean.getRycjCount());	// 如意存+笔数
		row.createCell(12).setCellValue(dataBean.getRycjAmount());	// 如意存+金额
		row.createCell(13).setCellValue(dataBean.getTotalCount());	// 合计笔数
		row.createCell(14).setCellValue(dataBean.getTotalAmount());	// 合计金额
		
		for (int i=0; i<15; i++){
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
