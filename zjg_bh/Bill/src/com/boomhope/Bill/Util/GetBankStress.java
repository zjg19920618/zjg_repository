package com.boomhope.Bill.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 读取文件获得支行位置 * @author hao
 *
 */
public class GetBankStress {

	static Logger logger = Logger.getLogger(GetBankStress.class);

	public static void main(String[] args) {
		GetBankStress b = new GetBankStress();
		List<Map<String,Object>> list=b.getCity("4");
//		for (Map<String, Object> map : list) {
//			System.out.println(map.get("pro"));
//		}
		
		
	}

	public HSSFSheet getSheet(int sheetIndex) {
		logger.info("要查询的表格号"+sheetIndex);
		POIFSFileSystem fs = null;
		HSSFWorkbook work = null;// 代表Excel文档
		HSSFSheet st = null;
		Property.initProperty();
		File file = new File(Property.CITY_PATH);
		logger.info("文件"+file);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			logger.info("文件读取流"+in);
			fs = new POIFSFileSystem(in);
			logger.info("excel读取流");
			work = new HSSFWorkbook(fs);
			logger.info("excel文档"+work);
			st = work.getSheetAt(sheetIndex);
			logger.info("第"+sheetIndex+"excel文档"+st);
		} catch (FileNotFoundException e1) {
			logger.error(e1 + "文件找不到路径错误或文件不存在");
		} catch (IOException e) {
			logger.error(e + "读取文流异常");
		} catch (Exception e2) {
			logger.error(e2);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e + "关闭流失败");
				}
			}
		}
		return st;
	}

	// 获得省份表格
	public List<Map<String, Object>> getProvince() {
		HSSFSheet s0 = getSheet(0);
		logger.info("省份表格"+s0);
		Province pro = null;
		int rowSize = 0;
		List<Map<String, Object>> pros = new ArrayList<Map<String,Object>>();
		Map map=null;
		HSSFCell cell = null;
		for (int rowIndex = 0; rowIndex < s0.getLastRowNum() + 1; rowIndex++) {
			HSSFRow row = s0.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int tempRowSize = row.getLastCellNum() + 1;
			if (tempRowSize > rowSize) {
				rowSize = tempRowSize;
			}
			String[] values = new String[tempRowSize];
			Arrays.fill(values, "");
			for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
				cell = row.getCell(columnIndex);
				String value = "";
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = new DecimalFormat("0").format(cell
								.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						break;
					default:
						break;
					}
				}else if(cell==null){
					value="";
				}
				if (columnIndex == 0 && value.trim().equals("")) {
					break;
				}
				values[columnIndex] = value;
			}
			pro = new Province();
			pro.setProId(values[0].trim());
			pro.setProName(values[1].trim());
			map=new HashMap();
			map.put("text", values[1].trim());
			map.put("pro", pro);
			map.put("jdCode", values[2]);
			pros.add(map);
		}
		return pros;

	}

	// 获的对应的城市
	public List<Map<String, Object>> getCity(String proId) {
		logger.info("省份编号"+proId);
		HSSFSheet s1 = getSheet(1);
		logger.info("城市Excel"+s1);
		City pro = null;
		List<Map<String, Object>> pros = new ArrayList<Map<String, Object>>();
		Map map=null;
		HSSFCell cell = null;
		for (int rowIndex = 0; rowIndex < s1.getLastRowNum() + 1; rowIndex++) {
			HSSFRow row = s1.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			int tempRowSize = row.getLastCellNum() + 2;
			String[] values = new String[tempRowSize];
			Arrays.fill(values, "");
			for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
				cell = row.getCell(columnIndex);
				String value = "";
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						value = new DecimalFormat("0").format(cell
								.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						break;
					default:
						break;
					}
				}else if(cell==null){
					value="";
				}
				if (columnIndex == 0 && value.trim().equals("")) {
					break;
				}
				values[columnIndex] = value;

			}
			if ((values[2].trim()).equals(proId)) {
				map=new HashMap();
				pro = new City();
				pro.setCityId(values[0].trim());
				pro.setCityName(values[1].trim());
				pro.setProId(values[2].trim());
				pro.setCityCode(values[3].trim());
				map.put("text", values[1].trim());
				map.put("pro", pro);
				pros.add(map);
			}
			
		}
		
		
		return pros;
	}

	
		
	}
	

	

