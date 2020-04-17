package com.boomhope.Bill.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 读取银行信息
 * @author hao
 *
 */
public class GetBankInfo {
	static Logger logger = Logger.getLogger(GetBankInfo.class);
	GetBankStress stress=new GetBankStress();

	public static void main(String[] args) {
	GetBankInfo g=new GetBankInfo();
//	List<Map<String,Object>> l=g.moTextFind("河北省", "路北区", "机场路");
//	for (Map<String, Object> map : l) {
//		System.out.println(map.get("text"));
//	}
	System.out.println(contain("北京市海淀区东直门支行", "啊东", 0));
//	System.out.println("农商".length());
	}
	/**
	 * 根据城市代码找到银行信息
	 * @param bankCode
	 * @return
	 */
	public List<Map<String,Object>> readBankInfo(String bankCode)  {
		logger.info("银行信息对应的城市代码"+bankCode);
		String encoding = "GBK";
		File file = null;
		InputStreamReader reader=null;
		List<Map<String,Object>> bankInfos=new ArrayList<Map<String,Object>>();
		Map map=null;
		BankInfo bankInfo=null;
		try {
			file = new File("config/BANKINFO");
			logger.info("文件"+file);
			reader = new InputStreamReader(new FileInputStream(file), encoding);
			logger.info("读取文件流"+reader);
			BufferedReader buffer = new BufferedReader(reader);
			String str = null;
			
			int i=1;
			while ((str = buffer.readLine()) != null) {
				if(i==1){
					i++;
					continue;
				}
				String[] read=str.split("\\|\\|");
				String s=read[6].trim();
				if (bankCode.equals(s) && bankCode != null && !"".equals(bankCode) ) {
					bankInfo = new BankInfo();
					bankInfo.setBankId(read[0].trim());
					bankInfo.setHbCode(read[1].trim());
					bankInfo.setZjBankCode(read[2].trim());
					bankInfo.setTreeCode(read[3].trim());
					bankInfo.setPreCanyu(read[4].trim());
					bankInfo.setPeopleBankCode(read[5].trim());
					bankInfo.setCityCode(read[6].trim());
					bankInfo.setName(read[7].trim());
					bankInfo.setNamej(read[8].trim());
					bankInfo.setAddress(read[9].trim());
					bankInfo.setEmail(read[10].trim());
					bankInfo.setPhone(read[11].trim());
					bankInfo.setEMAIL(read[12].trim());
					bankInfo.setChlid(read[13].trim());
					bankInfo.setJgType(read[14].trim());
					bankInfo.setBelongPeople(read[15].trim());
					bankInfo.setCjBankCode(read[16].trim());
					bankInfo.setSysBusinessChlid(read[17].trim());
					bankInfo.setTimes(read[18].trim());
					bankInfo.setStartDate(read[19].trim());
					bankInfo.setEndDate(read[20].trim());
					bankInfo.setBgTyoe(read[21].trim());
					logger.info("符合条件的银行信息"+bankInfo);
					map=new HashMap();
					map.put("text", read[7].trim());
					map.put("info",bankInfo);
					bankInfos.add(map);
				}
			}
			return bankInfos;
		}catch(FileNotFoundException e) {
			logger.error("文件找不到"+e);
		}catch (IOException e) {
			logger.error("读取文件流异常"+e);
		}catch(Exception e1){
			logger.error(e1);
		}finally{
			
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("关闭流失败"+e);
			}
		}
		
		return null;
	}
	/**
	 * 若没有城市代码，支持模糊查询
	 */
	public List<Map<String,Object>> moFind(String proName,String name){
		logger.info("城市或县的名字"+name);
		logger.info("省份的名字"+proName);
		if (name==null) {
			return null;
		}
		char[] password = name.toCharArray();
		if (password != null && password.length > 0) {
			char[] copyOf = Arrays.copyOf(password, password.length - 1);
			logger.info("修改后的城市名"+new String(copyOf));
			name=new String(copyOf);

		}
		if (proName==null) {
			return null;
		}
		char[] password1 = proName.toCharArray();
		if (password1 != null && password1.length > 0) {
			char[] copyOf = Arrays.copyOf(password1, password1.length - 1);
			logger.info("修改后的省名"+new String(copyOf));
			proName=new String(copyOf);

		}
		Map map=null;
		String encoding = "GBK";
		File file = null;
		InputStreamReader reader=null;
		List<Map<String,Object>> bankInfos=new ArrayList<Map<String,Object>>();
		BankInfo bankInfo=null;
		try {
			file = new File("config/BANKINFO");
			logger.info("文件"+file);
			reader = new InputStreamReader(new FileInputStream(file), encoding);
			logger.info("读取文件流"+reader);
			BufferedReader buffer = new BufferedReader(reader);
			String str = null;
			
			int i=1;
			while ((str = buffer.readLine()) != null) {
				if(i==1){
					i++;
					continue;
				}
				String[] read=str.split("\\|\\|");
		
				String s=read[7].trim();
				if ( name != null && !"".equals(name)&&s.contains(name)) {
					bankInfo = new BankInfo();
					bankInfo.setBankId(read[0].trim());
					bankInfo.setHbCode(read[1].trim());
					bankInfo.setZjBankCode(read[2].trim());
					bankInfo.setTreeCode(read[3].trim());
					bankInfo.setPreCanyu(read[4].trim());
					bankInfo.setPeopleBankCode(read[5].trim());
					bankInfo.setCityCode(read[6].trim());
					bankInfo.setName(read[7].trim());
					bankInfo.setNamej(read[8].trim());
					bankInfo.setAddress(read[9].trim());
					bankInfo.setEmail(read[10].trim());
					bankInfo.setPhone(read[11].trim());
					bankInfo.setEMAIL(read[12].trim());
					bankInfo.setChlid(read[13].trim());
					bankInfo.setJgType(read[14].trim());
					bankInfo.setBelongPeople(read[15].trim());
					bankInfo.setCjBankCode(read[16].trim());
					bankInfo.setSysBusinessChlid(read[17].trim());
					bankInfo.setTimes(read[18].trim());
					bankInfo.setStartDate(read[19].trim());
					bankInfo.setEndDate(read[20].trim());
					bankInfo.setBgTyoe(read[21].trim());
					logger.info("符合条件的银行信息"+bankInfo);
					map=new HashMap();
					map.put("text", read[7].trim());
					map.put("info",bankInfo);
					bankInfos.add(map);
				}
			}
			return bankInfos;
		}catch(FileNotFoundException e) {
			logger.error("文件找不到"+e);
		}catch (IOException e) {
			logger.error("读取文件流异常"+e);
		}catch(Exception e1){
			logger.error(e1);
		}finally{
			
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("关闭流失败"+e);
			}
		}
		
		return null;
	}
	
		
	
	public List<Map<String, Object>> moTextFind(String proName,String cityName,String text) {
		if (cityName==null||text==null) {
			return null;
		}
		List<Map<String, Object>> list=null;
		list=moFind(proName,cityName);
		List<Map<String, Object>> list1=new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			
			BankInfo bankInfo=(BankInfo) list.get(i).get("info");
			
				if(contain(((String)list.get(i).get("text")), text, 0)){
				
					Map map=new HashMap();
					map.put("text", bankInfo.getName());
					map.put("info", bankInfo);
					list1.add(map);
				}
				
		}
		
		return list1;
	}
	public static  boolean contain(String bankName,String text,int i){
		
			String z=text.substring(i, i+1);
			if(bankName.contains(z)){
				i++;
				if(i==text.length()){
					return true;
					
				}
				boolean b=contain(bankName, text,i);
				if(!b){
					return false;
				}
			}else{
				return false;
			}
			return true;
	}
}
