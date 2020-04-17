package com.boomhope.Bill.JFormEx;

import java.util.HashMap;
import java.util.Map;

import com.wintone.JFormEx.JFormEx;

public class DemoDLL 
{
	static 
	{
		System.loadLibrary("JFormEx");	
	}

	/**
	 * OCR识别类
	 * @param fc  灰度图
	 * @param ocrMode  ocr识别模版路径
	 * @param ocrSdk   ocr核心SDK路径
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> ocrBill(String fcBill,String ocrMode,String ocrSdk) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		int ret = JFormEx.jInitRecogForm(ocrMode,ocrSdk);
		System.out.println("ret = " + ret);
		if (ret == 0) {
			String imgPath = fcBill;
			char[] modeName = new char[256];
			char[] result = new char[0];
			int[] cell = { 0 };
			ret = JFormEx.jRecognizeSingleForm(imgPath, modeName, result, cell);
			if (ret == 0) {
				String strMod = new String(modeName);
				strMod = strMod.substring(0, strMod.indexOf('\0'));
				System.out.println("模板名：" + strMod);

				int[] countOut = { 0 };
				char[] resultOut = new char[0];
				ret = JFormEx.jGetRecognizeResult(resultOut, countOut);
				if (countOut[0] != 0) {
					resultOut = new char[countOut[0] + 1];
					JFormEx.jGetRecognizeResult(resultOut, countOut);
					resultOut[countOut[0]] = 0;
					String strRes = new String(resultOut);
					strRes = strRes.substring(0, strRes.indexOf('\0'));
					String billNo = strRes.substring(strRes.indexOf(":")+1, strRes.lastIndexOf(":")-2);
					String acctNo = strRes.substring(strRes.lastIndexOf(":")+1, strRes.length());
					System.out.println("识别结果：" + strRes);
					/*String[] aaa = strRes.split(":");

					map.put("accNo", aaa[2]);
					map.put("billNo", aaa[1]);*/
					strRes = strRes.replace(":", "");
					map.put("billNo", billNo);
					map.put("acctNo", acctNo);
					System.out.print("单元个数：");
					System.out.println(cell[0]);
				}
			} else {
				throw new Exception("识别异常...");
			}

			ret = JFormEx.jUninitRecogForm();
//			System.out.println(ret);
		} else {
			throw new Exception("识别异常......");
		}
		return map;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try {
			//DemoDLL.testNew();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
