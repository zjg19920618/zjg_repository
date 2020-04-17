package com.boomhope.Bill.Comm;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Util.Property;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 打印协议
 * @author fu_xl 
 * @Date 2017.6.22
 *
 */
public class PrintAgreement {
	
	public static Logger log=Logger.getLogger(PrintAgreement.class);
	/**
	 * 分配协议
	 * @Param Agreement 协议类型
	 */
	public static void print(String Agreement){
		 
		String fileName=null;
		Property.initProperty();
		String agreement=Property.agreementPath;
		log.info(agreement);
		switch(Agreement){
		case "JX":fileName="jxc.doc";break;
		case "RY":fileName="ryc.doc";break;
		case "RJ":fileName="ryc+.doc";break;
		case "AX":fileName="axc.doc";break;
		case "LZ":fileName="ldc.doc";break;
		case "LT":fileName="ldc.doc";break;
		case "YA":fileName="yxcA.doc";break;
		case "YB":fileName="yxcB.doc";break;
		case "YC":fileName="yxcC.doc";break;
		
		}
		String filePath=agreement+fileName;
		log.info(filePath);
		startPrint(filePath);
	}
	/**
	 * 打印协议
	 * @param filePath 路径
	 */
	public static void startPrint(String filePath){
//		 String path=filePath;
	        System.out.println("开始打印");
	        ComThread.InitSTA();
	        ActiveXComponent word=new ActiveXComponent("Word.Application");
	        
	        Dispatch doc=null;
	        Dispatch.put(word, "Visible", new Variant(false));
	        Dispatch docs=word.getProperty("Documents").toDispatch();
	        //设置打印机
	        word.setProperty("ActivePrinter", new Variant("Brother HL-2260 Printer"));
	        //设置参数
	        doc=Dispatch.call(docs, "Open", filePath).toDispatch();
	        
	        try {
	            Dispatch.call(doc, "PrintOut");//打印
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("打印失败");
	        }finally{
	            try {
	                if(doc!=null){
	                    Dispatch.call(doc, "Close",new Variant(0));
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	            //释放资源
	            ComThread.Release();
	        }
	}
	
	//测试
	public static void main(String[] args) {
		String name="JXC";
		print(name);
	}
}
