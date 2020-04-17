package com.boomhope.Bill.Comm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintJobAttributeSet;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.Property;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 *   打印挂失解挂申请书工具类
 * @author zb
 *
 */
public class GSPrintApplication {
	private static Logger logger = Logger.getLogger(GSPrintApplication.class);
	private static Dispatch doc = null;
	private static Dispatch docs = null;
	private static Dispatch selection = null;
	private static ActiveXComponent word = null;
	private static String printName = null;// 打印机名            
	private static boolean printstate=false;
	String date=DateUtil.getDateTime("HH:mm:ss");
	/**
	 * 初始化Doc
	 */
	private static boolean getDocInit() {
		logger.info("开始加载word模板");
		try {
			Property.getProperties();
			 // 获取可用打印机
     	    getPrinterName();
			word = new ActiveXComponent("Word.Application");
			Dispatch.put(word, "Visible", new Variant(false));
			docs = word.getProperty("Documents").toDispatch();
			// 设置打印机
			word.setProperty("ActivePrinter", new Variant(printName));
			Property.initProperty();
			doc = Dispatch.call(docs, "Open", Property.Lost_Path).toDispatch();
			selection = Dispatch.get(word, "Selection").toDispatch();
			return true;
		} catch (Exception e) {
			logger.error("打印机初始化失败：" + e);
			return false;
		}
	}

	/**
	 * 获取打印机名称
	 * 
	 * @param productedCode
	 * @param transBean
	 * @return
	 */
	private static  boolean getPrinterName() {
		logger.info("获取可用打印机");
		HashPrintJobAttributeSet hpjas = new HashPrintJobAttributeSet();
		DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, hpjas);
		for (int i = 0; i < ps.length; i++) {
			if (ps[i].getName().contains("FX DocuPrint P228 db")) {
				printName = "FX DocuPrint P228 db";
				printstate =true;
			} else if (ps[i].getName().contains("Brother HL-2240 series")) {
				printName = "Brother HL-2240 series";
				printstate =true;
			} else if (ps[i].getName().contains("Brother HL-2260 Printer")) {
				   printName = "Brother HL-2260 Printer";
				   printstate =true;
			   }
		}
         return printstate;
	}

	// 扫描Doc文件，判断文件中是否包含该字符串，如果包含则返回true
	private static boolean find(String text) {
		Dispatch find = word.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", text);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		boolean b = Dispatch.call(find, "Execute").getBoolean();
		return b;
	}

	/**
	 *  向word申请书里添加对相应数据
	 * 
	 * @return
	 */
	public  Map PrintApplication(LostPubBean lostPubBean){
		logger.error("开始打印申请书");
     		boolean flag=false;
     		Map<String,String> map=new HashMap();
     	try{
     		if(!"Y".equals(lostPubBean.getAllPubDzyz())){
     		   logger.error("调用电子印章接口印章");
     		    // 拼接调用电子印章所需要的参数
     		   JSONObject jsonObject = getJsons(lostPubBean);
     		   // 调用电子印章http接口（base64编码）
     		   String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
     		   if (dzyz != null) {
     			    //将base64编码转换成图片
     				  flag = GenerateImage(dzyz, Property.dzyz_ml);
     		         // 获取电子印章图片
     			     if (!flag) {
     				     logger.error("获取电子印章图片失败");
 				           map.put("error", "获取电子印章图片失败");
 				           return map;
     			       }else{
     			    	lostPubBean.setAllPubDzyz("Y");//电子印章正常返回
     			       }
     	         } else {
     		        logger.error("获取电子印章失败");
 
     		      }
     		}
     		ComThread.InitSTA();
     		if(!getDocInit()){//加载申请书文档    			
     			map.put("error","打印申请书程序异常！");
     			return map;
     		}
     		ShowAccNoMsgBean show=(ShowAccNoMsgBean) lostPubBean.getAccMap().get("selectMsg");//挂失种类
     		if(find("K0")){
    			Dispatch.put(selection, "Text", "客户联");
    	        Dispatch.call(selection, "MoveRight"); 
    		}
     		if(find("K1")){
    			Dispatch.put(selection, "Text", lostPubBean.getLostApplNo());//挂失申请号
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K2")){
    			Dispatch.put(selection, "Text", "正式挂失");//挂失方式
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K3")){
    		    if("0".equals(lostPubBean.getYseNoPass())){// 0.知道密码  1.不知道密码
    	              if("0".equals(show.getCardOrAccOrAcc1())){//挂失种类			     
    			         Dispatch.put(selection, "Text", "银行卡");            
    	              }else if("2".equals(show.getCardOrAccOrAcc1())){
    			         Dispatch.put(selection, "Text", "存折");
    	              }else{
        			     Dispatch.put(selection, "Text", "存单");
        		     }
    	         }else{
    	        	 if("0".equals(show.getCardOrAccOrAcc1())){			
    	        		 if("N".equals(lostPubBean.getCardState())){
    	        			 Dispatch.put(selection, "Text", "银行卡"); 
    	        		 }else{
    	        			 Dispatch.put(selection, "Text", "银行卡+密码");    	        			
    	        		 }    	        		     
    		         }else if("2".equals(show.getCardOrAccOrAcc1())){
    		        	 if("1".equals(show.getDrawCond())){
    		        		 Dispatch.put(selection, "Text", "存折+密码");
    	        		 }else{
    				         Dispatch.put(selection, "Text", "存折");
    	        		 }
    		         }else{
    		        	 if("1".equals(show.getDrawCond())){
    		        		 Dispatch.put(selection, "Text", "存单+密码");       	    		 
    	        		 }else{
    	        			 Dispatch.put(selection, "Text", "存单");
    	        		 }   	    			 
    	    		 }
    	         }
    		    Dispatch.call(selection, "MoveRight");
    		 }
    		if(find("K4")){
    			Dispatch.put(selection, "Text", lostPubBean.getAllPubIdCardName());//申请挂失人
    			Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K5")){
    			Dispatch.put(selection, "Text", "身份证");//证件名称
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K6")){
    			
    			Dispatch.put(selection, "Text", lostPubBean.getAllPubIDNo()); //证件号码
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		if("0".equals(lostPubBean.getAllPubIsDeputy())){// 是否有代理人
    			//本人挂失  无代理人
    			if(find("K7")){
        			Dispatch.put(selection, "Text", " ");//代理挂失人
        	        Dispatch.call(selection, "MoveRight"); 
        		}
    			if(find("K8")){
        			Dispatch.put(selection, "Text", " ");//证件名称
        			Dispatch.call(selection, "MoveRight"); 
        		}
    			if(find("K9")){
        			Dispatch.put(selection, "Text", " ");//证件号码
        	        Dispatch.call(selection, "MoveRight"); 
        		}
    			if(find("K10")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAddress());//申请人地址
        	        Dispatch.call(selection, "MoveRight"); 
        		}   	
    			if(find("K11")){
        			Dispatch.put(selection, "Text", " ");//代理人地址
        			Dispatch.call(selection, "MoveRight"); 
        		}
    			if(find("K12")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubPhone());//申请人联系电话
        	        Dispatch.call(selection, "MoveRight"); 
        		}
    			if(find("K13")){
        			Dispatch.put(selection, "Text", " ");//代理人联系电话
        	        Dispatch.call(selection, "MoveRight"); 
        		}
    		}else{
    			//有代理人
    			if(find("K7")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIdCardName());//代理挂失人
        	        Dispatch.call(selection, "MoveRight"); 
        		}
        		if(find("K8")){
        			Dispatch.put(selection, "Text", "身份证");//证件名称
        			Dispatch.call(selection, "MoveRight"); 
        		}
        		if(find("K9")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIDNo());//证件号码
        	        Dispatch.call(selection, "MoveRight"); 
        		}
        		if(find("K10")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAddress());//申请人地址
        	        Dispatch.call(selection, "MoveRight"); 
        		}  
        		if(find("K11")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentAddress());//代理人地址
        			Dispatch.call(selection, "MoveRight"); 
        		}
        		if(find("K12")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubPhone());//申请人联系电话
        	        Dispatch.call(selection, "MoveRight"); 
        		}
        		if(find("K13")){
        			Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentPhone());//代理人联系电话
        	        Dispatch.call(selection, "MoveRight"); 
        		}
    		}  		
    		if(find("K15")){
    			Dispatch.put(selection, "Text", lostPubBean.getAllPubAccNo());//卡号
    			Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K16")){
    			Dispatch.put(selection, "Text", show.getOpenDate());//开户日期
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		if(find("K17")){		        
    			Dispatch.put(selection, "Text", show.getCertNo());//凭证号
    	        Dispatch.call(selection, "MoveRight"); 
    		}
    		
    		if(find("K19")){
    			Dispatch.put(selection, "Text", "申请日期："+lostPubBean.getAllPubSvrDate().replaceAll("/", "."));//申请日期
    			Dispatch.call(selection, "MoveRight"); 
    		}
          try {
          //打印申请人电子签名		
          Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
							"AddPicture",Property.SIGNATURE_PATH).toDispatch();
		  Dispatch.call(picture, "Select"); // 选中图片
		  Dispatch.put(picture, "Width", new Variant(60));
		  Dispatch.put(picture, "Height",new Variant(60));
		  Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
		  Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
		  Dispatch.put(WrapFormat, "Type", 5);
		  Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
		  Dispatch.put(shapeRange, "Left", new Variant(330));
		  Dispatch.put(shapeRange, "Top", new Variant(-90));
		       
		  } catch (Exception e) {
				  logger.error("打印申请人签名失败："+e);
				  map.put("error", "打印申请人签名失败");
	    			return map;
		  }        
       if(!"0".equals(lostPubBean.getLostOrSolve())){// 选择只挂失 则不打印客户签字
   		try {
   			   //打印客户电子签名		
   		   Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
   						"AddPicture",Property.SIGNATURE_PATH).toDispatch();
   		   Dispatch.call(picture, "Select"); // 选中图片
   		   Dispatch.put(picture, "Width", new Variant(60));
   		   Dispatch.put(picture, "Height",new Variant(60));
   		   Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
   		   Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
   		   Dispatch.put(WrapFormat, "Type", 5);
   		   Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
   	       Dispatch.put(shapeRange, "Left", new Variant(320));
   	       Dispatch.put(shapeRange, "Top", new Variant(40));
   	       
   		    } catch (Exception e) {
   			  logger.error("打印客户电子签名失败："+e);
   		    	map.put("error", "打印客户电子签名失败");
			   return map;
   		    }
     	}      	   	   	
    if("0".equals(lostPubBean.getLostOrSolve())){//选择挂失/解挂 (0-单独挂失，1-挂失补领,2-挂失销户，3-解挂补发,4-解挂销户,5-挂失撤销)
    		logger.info("选择单独挂失申请书"); 
    		if("0".equals(lostPubBean.getAllPubIsDeputy())){//是否为代理人挂失(知道密码/忘记密码)
    			logger.info("选择本人挂失申请书");//为本人挂失
    		    if(find("K20")){
        	     	Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K21")){
        		    Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K49")){
        		    Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K22")){
       			    Dispatch.put(selection, "Text", "交易日期："+lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim()+" "+date);
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K23")){
       			    Dispatch.put(selection, "Text", "客 户 号："+lostPubBean.getCustNo());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K24")){
       		        Dispatch.put(selection, "Text", "流 水 号："+lostPubBean.getLostJrnlNo());      			    
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K25")){
       		    	Dispatch.put(selection, "Text", "客户名称："+lostPubBean.getCustName());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K48")){
       		    	Dispatch.put(selection, "Text", "证件类型：身份证");
    	            Dispatch.call(selection, "MoveRight"); 
    		      }
       		    if(find("K26")){
       			    Dispatch.put(selection, "Text", "证件号码："+lostPubBean.getAllPubIDNo());   			   
    	            Dispatch.call(selection, "MoveRight"); 
    	       	 }
       		   if(find("K27")){
       			    Dispatch.put(selection, "Text", "开户日期："+show.getOpenDate());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		   if(find("K28")){
       			    Dispatch.put(selection, "Text", "结 存 额："+show.getEndAmt());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		   if(find("K29")){
       		    	Dispatch.put(selection, "Text", "账号/卡号："+lostPubBean.getAllPubAccNo());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		   if(find("K30")){
       			    Dispatch.put(selection, "Text", "挂失方式：正式挂失");
       	            Dispatch.call(selection, "MoveRight"); 
       		     }
       		   if(find("K31")){
       			if("0".equals(lostPubBean.getYseNoPass())){// 0.知道密码  1.不知道密码
			    	 if("0".equals(show.getCardOrAccOrAcc1())){	 //挂失种类			   
     				     Dispatch.put(selection, "Text", "挂失种类：银行卡");
     		         }else if("2".equals(show.getCardOrAccOrAcc1())){
     				     Dispatch.put(selection, "Text", "挂失种类：存折");
     		         }else{
     	    			 Dispatch.put(selection, "Text", "挂失种类：存单");     
     	    		 } 
		         }else{
		        	 if("0".equals(show.getCardOrAccOrAcc1())){	//不知道密码  无密码的时候判断
		        		 if("N".equals(lostPubBean.getCardState())){
    	        			 Dispatch.put(selection, "Text", "挂失种类：银行卡"); 
    	        		 }else{
    	        			 Dispatch.put(selection, "Text", "挂失种类：银行卡+密码");    	        			
    	        		 }     	        		     
    		         }else if("2".equals(show.getCardOrAccOrAcc1())){
    		        	 if("1".equals(show.getDrawCond())){
    		        		 Dispatch.put(selection, "Text", "挂失种类：存折+密码");
    	        		 }else{
    				         Dispatch.put(selection, "Text", "挂失种类：存折");
    	        		 }
    		         }else{
    		        	 if("1".equals(show.getDrawCond())){
    		        		 Dispatch.put(selection, "Text", "挂失种类：存单+密码");       	    		 
    	        		 }else{
    	        			 Dispatch.put(selection, "Text", "挂失种类：存单");
    	        		 }   	    			 
    	    		 }
		         }  
       	            Dispatch.call(selection, "MoveRight"); 
       		     }
       		    if(find("K32")){
       		     if("0".equals(show.getCardOrAccOrAcc1())){	 //挂失种类	      				
			         Dispatch.put(selection, "Text", " ");;
	             }else{
	            	 Dispatch.put(selection, "Text", "凭 证 号："+show.getCertNo());    
    		     } 
   		          Dispatch.call(selection, "MoveRight"); 
   		       }
       		   if(find("K33")){
       			     Dispatch.put(selection, "Text", "交易机构："+GlobalParameter.branchNo);  
	                 Dispatch.call(selection, "MoveRight"); 
       		     }
       		  if(find("K34")){
       			     Dispatch.put(selection, "Text", "设备编号："+GlobalParameter.tellerNo);
       	             Dispatch.call(selection, "MoveRight"); 
       		     }
       		  if(find("K35")){         	    
       			     Dispatch.put(selection, "Text", " ");	   
         	         Dispatch.call(selection, "MoveRight"); 
               }       		  
       		 if(find("K36")){
       			     Dispatch.put(selection, "Text", "授权柜员："+lostPubBean.getAllPubFristSupTellerNo());
    	             Dispatch.call(selection, "MoveRight"); 
    		     }
       		 if(find("K37")){
       			 Dispatch.put(selection, "Text", "手 续 费：0.00 ");
    	            Dispatch.call(selection, "MoveRight"); 
    		    }
       		 if(find("K38")){
    			    Dispatch.put(selection, "Text", " ");
    	            Dispatch.call(selection, "MoveRight"); 
    		    }
    		  if(find("K39")){
    			    Dispatch.put(selection, "Text", "挂失申请书编号："+lostPubBean.getLostApplNo());
    	            Dispatch.call(selection, "MoveRight"); 
    		    }
    		  if(find("K40")){
    			    Dispatch.put(selection, "Text", " ");
    	            Dispatch.call(selection, "MoveRight"); 
    		    }
    		  if(find("K41")){
     			    Dispatch.put(selection, "Text", " ");
     	            Dispatch.call(selection, "MoveRight"); 
     		    }
    		  if(find("K42")){
    			  if("0".equals(lostPubBean.getYseNoPass())){//知道密码
        				 Dispatch.put(selection, "Text", " ");	 
        			}else{
        				  String enddata=data7(lostPubBean);
        				  Dispatch.put(selection, "Text", "补发/销户起始日期："+enddata);	
        			}   
     	            Dispatch.call(selection, "MoveRight"); 
     		    }
     		  if(find("K43")){
     			    Dispatch.put(selection, "Text", " ");
     	            Dispatch.call(selection, "MoveRight"); 
     		    }
     		  if(find("K44")){
 			        Dispatch.put(selection, "Text", " ");
 	                Dispatch.call(selection, "MoveRight"); 
 		        }
 		      if(find("K45")){
 			        Dispatch.put(selection, "Text", " ");
 	                Dispatch.call(selection, "MoveRight"); 
 		        }
 		      if(find("K46")){
 			        Dispatch.put(selection, "Text", " ");
 	                Dispatch.call(selection, "MoveRight"); 
 		       }
 		      if(find("K47")){
 			        Dispatch.put(selection, "Text", " ");
 	                Dispatch.call(selection, "MoveRight"); 
 		        }
 		     if (find("K50")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K51")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}  
    	   }else{//代理人挂失(忘记密码/知道密码)
    			logger.info("选择代理人(忘记密码/知道密码)挂失申请书");
    		    if(find("K20")){
        	     	Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K21")){
        		    Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K49")){
        		    Dispatch.put(selection, "Text", " ");
        	        Dispatch.call(selection, "MoveRight"); 
        	      }
    		    if(find("K22")){
       			    Dispatch.put(selection, "Text", "交易日期："+lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim()+" "+date);
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K23")){
       			    Dispatch.put(selection, "Text", "客 户 号："+lostPubBean.getCustNo());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K24")){
       		        Dispatch.put(selection, "Text", "流 水 号："+lostPubBean.getLostJrnlNo());      			    
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K25")){
       		    	Dispatch.put(selection, "Text", "客户名称："+lostPubBean.getCustName());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K48")){
       		    	Dispatch.put(selection, "Text", "证件类型：身份证");
    	            Dispatch.call(selection, "MoveRight"); 
    		      }
       		    if(find("K26")){
       			    Dispatch.put(selection, "Text", "证件号码："+lostPubBean.getAllPubIDNo());   			   
    	            Dispatch.call(selection, "MoveRight"); 
    	       	 }
       		   if(find("K27")){
      			    Dispatch.put(selection, "Text", "代理人名称："+lostPubBean.getAllPubAgentIdCardName());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }
       		   if(find("K28")){
       			    Dispatch.put(selection, "Text", "证件类型：身份证");
       			    Dispatch.call(selection, "MoveRight"); 
       		      }
       		    if(find("K29")){
       			    Dispatch.put(selection, "Text", "证件号码："+lostPubBean.getAllPubAgentIDNo());
       	            Dispatch.call(selection, "MoveRight"); 
       		      }     	

       		   if(find("K30")){
      			    Dispatch.put(selection, "Text", "开户日期："+show.getOpenDate());
       	            Dispatch.call(selection, "MoveRight"); 
       		     }
       		   if(find("K31")){
      		    	Dispatch.put(selection, "Text", "结 存 额："+show.getEndAmt());
       	            Dispatch.call(selection, "MoveRight"); 
       		     }
       		   if(find("K32")){
      			    Dispatch.put(selection, "Text", "账号/卡号："+lostPubBean.getAllPubAccNo());
       	            Dispatch.call(selection, "MoveRight"); 
       		     }
       		   if(find("K33")){
   			         Dispatch.put(selection, "Text", "挂失方式：正式挂失");
       	             Dispatch.call(selection, "MoveRight"); 
       		     }
       		     if(find("K34")){
       		    	if("0".equals(lostPubBean.getYseNoPass())){// 0.知道密码  1.不知道密码
				    	 if("0".equals(show.getCardOrAccOrAcc1())){	 //挂失种类			   
	      				     Dispatch.put(selection, "Text", "挂失种类：银行卡");
	      		         }else if("2".equals(show.getCardOrAccOrAcc1())){
	      				     Dispatch.put(selection, "Text", "挂失种类：存折");
	      		         }else{
	      	    			 Dispatch.put(selection, "Text", "挂失种类：存单");     
	      	    		 } 
			         }else{
			        	 if("0".equals(show.getCardOrAccOrAcc1())){			//不知道密码  无密码的时候判断
			        		 if("N".equals(lostPubBean.getCardState())){
	    	        			 Dispatch.put(selection, "Text", "挂失种类：银行卡"); 
	    	        		 }else{
	    	        			 Dispatch.put(selection, "Text", "挂失种类：银行卡+密码");    	        			
	    	        		 }     	        		     
	    		         }else if("2".equals(show.getCardOrAccOrAcc1())){
	    		        	 if("1".equals(show.getDrawCond())){
	    		        		 Dispatch.put(selection, "Text", "挂失种类：存折+密码");
	    	        		 }else{
	    				         Dispatch.put(selection, "Text", "挂失种类：存折");
	    	        		 }
	    		         }else{
	    		        	 if("1".equals(show.getDrawCond())){
	    		        		 Dispatch.put(selection, "Text", "挂失种类：存单+密码");       	    		 
	    	        		 }else{
	    	        			 Dispatch.put(selection, "Text", "挂失种类：存单");
	    	        		 }   	    			 
	    	    		 }
			         }
       	               Dispatch.call(selection, "MoveRight"); 
       		     }
       		     if(find("K35")){
       		      if("0".equals(show.getCardOrAccOrAcc1())){	 //挂失种类	      				
    			         Dispatch.put(selection, "Text", " ");;
		            }else{
		            	 Dispatch.put(selection, "Text", "凭 证 号："+show.getCertNo());    
	    		    }   
				    Dispatch.call(selection, "MoveRight");
			     }  		 
       		    if(find("K36")){
 			          Dispatch.put(selection, "Text", "交易机构："+GlobalParameter.branchNo);
    	              Dispatch.call(selection, "MoveRight"); 
    		       }
       		    if(find("K37")){
  			          Dispatch.put(selection, "Text", "设备编号："+GlobalParameter.tellerNo);  
    	              Dispatch.call(selection, "MoveRight"); 
    		      }
       		    if(find("K38")){
       		          Dispatch.put(selection, "Text", " ");   
	                  Dispatch.call(selection, "MoveRight"); 
             	 }
    		     if(find("K39")){
  			           Dispatch.put(selection, "Text", "授权柜员："+lostPubBean.getAllPubFristSupTellerNo());   			       
    	               Dispatch.call(selection, "MoveRight"); 
    		       }
    		     if(find("K40")){
    		    	   Dispatch.put(selection, "Text", "手 续 费：0.00 ");
    	               Dispatch.call(selection, "MoveRight"); 
    		        }
    		    if(find("K41")){
    		    	   Dispatch.put(selection, "Text", " ");
     	               Dispatch.call(selection, "MoveRight"); 
     		         }
    		     if(find("K42")){
   			           Dispatch.put(selection, "Text", "挂失申请书编号："+lostPubBean.getLostApplNo());
     	               Dispatch.call(selection, "MoveRight"); 
     		       }
     		      if(find("K43")){
     			        Dispatch.put(selection, "Text", " ");
     	                Dispatch.call(selection, "MoveRight"); 
     		       }
     		      if(find("K44")){
    			        Dispatch.put(selection, "Text", " ");
    	                Dispatch.call(selection, "MoveRight"); 
    		        }
    		      if(find("K45")){
    			        if("0".equals(lostPubBean.getYseNoPass())){
           		    	    Dispatch.put(selection, "Text", " ");
           		    	}else{
           		    	    String enddata=data7(lostPubBean);//日期加七天
       				         Dispatch.put(selection, "Text", "补发/销户起始日期："+enddata);	
           		    	}		    	 		 
    	                Dispatch.call(selection, "MoveRight"); 
    		        }
    		      if(find("K46")){
    			        Dispatch.put(selection, "Text", " ");
    	                Dispatch.call(selection, "MoveRight"); 
    		       }
    		      if(find("K47")){
    			        Dispatch.put(selection, "Text", " ");
    	                Dispatch.call(selection, "MoveRight"); 
    		        }
    		      if (find("K50")) {
  					Dispatch.put(selection, "Text", " ");
  					Dispatch.call(selection, "MoveRight");
  				}
  				if (find("K51")) {
  					Dispatch.put(selection, "Text", " ");
  					Dispatch.call(selection, "MoveRight");
  				}  
    		}
    		
   }
  	   if("Y".equals(lostPubBean.getAllPubDzyz())){
        try {
    	//打印电子印章
    	    Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),"AddPicture", Property.dzyz_ml).toDispatch(); 			
    	    Dispatch.call(picture, "Select"); // 选中图片
    	    Dispatch.put(picture, "Width", new Variant(104));
    	    Dispatch.put(picture, "Height",new Variant(104));
    	    Dispatch ShapeRange = Dispatch.call(picture, "ConvertToShape").toDispatch(); // 取得图片区域
        	Dispatch WrapFormat = Dispatch.get(ShapeRange, "WrapFormat").toDispatch(); // 取得图片的格式对象
    	    Dispatch.put(WrapFormat, "Type", 5);
    	    Dispatch shapeRange = Dispatch.get(selection, "ShapeRange").toDispatch();  
    	    Dispatch.put(shapeRange, "Left", new Variant(385));
    	    Dispatch.put(shapeRange, "Top", new Variant(-65));
    			
    	   } catch (Exception e) {
    		  logger.error("电子印章打印失败"+e);
    		  map.put("error", "电子印章打印失败");
    			return map;
    	   }
  	   } else {
			logger.error("获取电子印章失败");
	   }
       boolean result=savePrintWord(lostPubBean);
       if(!result){
	     logger.info("保存打印客户联申请书失败");
       }	
       if(printstate){
    	   Dispatch.call(doc, "PrintOut");//打印
       }else{
    	   logger.error("获取可用打印机失败");
    	   map.put("error", "获取打印机失败");
    	   return map;
       }
       } catch (Exception e) {
			e.printStackTrace();
			logger.error("打印申请书程序异常"+e);
			map.put("error", "打印申请书程序异常");
		  return map;
	   }finally{
			try {
				if(doc!=null){
	                Dispatch.call(doc, "Close",new Variant(0));
	                Dispatch.call(word, "Quit");
	                ComThread.Release();
	                doc = null;
	                docs = null;
	                selection = null;
	            }
			} catch (Exception e) {
				e.printStackTrace();
	  }
			
  }
     	
     	map.put("resCode", "000000");
		return map;
 }
	 /**
     * 打印失败  则保存word 补打
     * @return
     */
    public boolean  savePrintWord(LostPubBean lostPubBean){
    	// 保存转换pdf文档的路径
    	logger.info("保存挂失申请书word------>");
    	try {
    		//本地保存路径
    		String bcPath=Property.bill_print_path;
    		File dir = new File(bcPath);
    		if (dir.isDirectory()) {//检测路径的文件是否存在
    			logger.info("文件已存在");
    		} else {
    			dir.mkdirs();
    			logger.info("文件不存在，创建文件--->"+dir);
    		}
        	//本地保存路径和名称
    		String tofile =bcPath+"lostprint1";
    		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { tofile,new Variant(12) }, new int[1]);
    		//保存之后的 word路径
    		String tofilel = tofile + ".docx";
    		logger.info("保存挂失申请书word文档路径为------>" + tofilel);	
    		}catch(Exception e){
    			logger.error("保存打印挂失申请书失败异常"+e);
    			return false;
    		}
		return true;
    	
    }	
	/**
	 * 拼接调用电子印章接口所需要的参数
	 * 
	 * @param
	 * @param globalParameter
	 * @return
	 */
	public JSONObject getJsons(LostPubBean lostPubBean) {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", lostPubBean.getOpenDate()
				+ lostPubBean.getLostJrnlNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "申请书");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接参数值
		Map<String, String> map = new HashMap<String, String>();
		if("0".equals(lostPubBean.getLostType())){//选择银行卡  获取电子印章
		   map.put("账号", lostPubBean.getAllPubAccNo());// 设置账号
		   map.put("户名", lostPubBean.getAccIdName());// 设置带*号的卡名
		   map.put("金额(小写)", "CNY" + lostPubBean.getCardEndAmt());// 设置存款金额（小写）
		   map.put("存入日", lostPubBean.getOpenDate());// 设置开户日期	
		}else{//选择存单、存折
		   map.put("账号", lostPubBean.getAllPubAccNo());// 设置账号
		   map.put("户名", lostPubBean.getAccIdName());// 户名
		   map.put("产品名称", lostPubBean.getProName());//产品名称
		   map.put("金额(小写)", "CNY" + lostPubBean.getOpenAmt());//开户金额（小写）
		   map.put("存入日", lostPubBean.getOpenDate());// 设置开户日期	
		   map.put("存期",lostPubBean.getDepTerm());//存期
		}
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->" + jsonObject.toString());
		return jsonObject;
	}

	/**
	 * base64编码转换图片
	 * 
	 * @param imgStr
	 *            //base64编码的字符串
	 * @param rootPath
	 *            //保存的路径（图片完整路径）
	 * @return
	 */
	public boolean GenerateImage(String imgStr, String rootPath) {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(rootPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 调用电子印章接口
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public String getDZYZ(String url, JSONObject paramMap) {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String result = httpClientUtil.httpPost(url, paramMap);
		return result;
	}
	/**
	 * 日期加七天
	 * @param args
	 */
    public String data7(LostPubBean lostPubBean){
    	String end=null;
		try {
	    	 String svrdata=lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim();//日期加七天
	   	     SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd");
			 Date d = form.parse(svrdata);
			 Calendar ca = Calendar.getInstance();
	   	     ca.setTime(d);
	   	     ca.add(Calendar.DATE, 7);
	   	     d = ca.getTime();
	   	     end = form.format(d);
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		}
   	    
		return end;
    	
    }
  
	public static void main(String[] args) throws IOException {
	
	}
}
