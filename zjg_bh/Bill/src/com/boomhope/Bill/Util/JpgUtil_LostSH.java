package com.boomhope.Bill.Util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.LostReport.Bean.AccLostAndReturnInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class JpgUtil_LostSH {
	private static Logger logger = Logger.getLogger(JpgUtil_LostSH.class);	
	private static Dispatch doc = null;
	private static Dispatch docs = null;
	private static Dispatch selection = null;
	private static ActiveXComponent word = null;
	static BufferedImage image;
	String date=DateUtil.getDateTime("HH:mm:ss");
	/**
	 * 初始化Doc
	 */
	private static boolean getDocInit() {
		logger.info("开始加载word模板");
		try {
			word = new ActiveXComponent("Word.Application");
			Dispatch.put(word, "Visible", new Variant(false));
			docs = word.getProperty("Documents").toDispatch();
			Property.initProperty();
			doc = Dispatch.call(docs, "Open",Property.Lost_Shjd_Path).toDispatch();
			selection = Dispatch.get(word, "Selection").toDispatch();
			return true;
		} catch (Exception e) {
			logger.error("打印机初始化失败：" + e);
			return false;
		}
	}
	/**
 	 * 向word申请书里添加对相应数据
 	 * @return
 	 */
 	public   String  ShjdJpgPrint(LostPubBean lostPubBean){
 	try{
 		if(!getDocInit()){//加载word模板
 			logger.info("加载word模板失败");
 	         return "";
 		}
 		ShowAccNoMsgBean show=(ShowAccNoMsgBean) lostPubBean.getAccMap().get("selectMsg");//挂失种类
 		if(find("K0")){
			Dispatch.put(selection, "Text", "银行联");
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
		  if("0".equals(lostPubBean.getLostOrSolve()) || "1".equals(lostPubBean.getLostOrSolve()) ||"2".equals(lostPubBean.getLostOrSolve())){
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
		  }else  if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//解挂补发  //解挂销户
			  if("0".equals(lostPubBean.getSolveLostType())){//银行卡					
					if("0".equals(lostPubBean.getSolveAccState())){//单挂
						Dispatch.put(selection, "Text", "银行卡");
					}else{//双挂
						 Dispatch.put(selection, "Text", "银行卡+密码");
					}					
				}else if("2".equals(lostPubBean.getSolveLostType())){//存折
					if("4".equals(lostPubBean.getSolveAccState())){//单挂
						 Dispatch.put(selection, "Text", "存折");
					}else{//双挂
						 Dispatch.put(selection, "Text", "存折+密码");
					}
				}else{//存单
					if("2".equals(lostPubBean.getSolveAccState())){//单挂
						 Dispatch.put(selection, "Text", "存单"); 
					}else{//双挂
						 Dispatch.put(selection, "Text", "存单+密码");  
					}
				}
		  }else	 if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销
			  if("3".equals(lostPubBean.getSolveAccState())){
				  Dispatch.put(selection, "Text", "存单+密码");  
			  }else if("2".equals(lostPubBean.getSolveAccState())){
				  Dispatch.put(selection, "Text", "存单"); 
			  }else if("1".equals(lostPubBean.getSolveAccState())){
				  Dispatch.put(selection, "Text", "银行卡+密码"); 
			  }else if("0".equals(lostPubBean.getSolveAccState())){
				  Dispatch.put(selection, "Text", "银行卡");
			  }else if("4".equals(lostPubBean.getSolveAccState())){
				  Dispatch.put(selection, "Text", "存折");
			  }else{
				  Dispatch.put(selection, "Text", "存折+密码");
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
		if("5".equals(lostPubBean.getLostOrSolve()) ||"4".equals(lostPubBean.getLostOrSolve()) ||"3".equals(lostPubBean.getLostOrSolve())){
			//解挂补发   解挂销户  挂失撤销
			if(find("K7")){
				if(lostPubBean.getAllPubAgentIdCardName()!=null && !"".equals(lostPubBean.getAllPubAgentIdCardName().trim()) ){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIdCardName());//代理挂失人
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("K8")){
				if(lostPubBean.getAllPubAgentIDNo()!=null && !"".equals(lostPubBean.getAllPubAgentIDNo().trim())){
					Dispatch.put(selection, "Text", "身份证");//证件名称
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("K9")){
				if(lostPubBean.getAllPubAgentIDNo()!=null && !"".equals(lostPubBean.getAllPubAgentIDNo().trim())){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIDNo());//证件号码
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("K10")){
				if(lostPubBean.getAllPubAddress()!=null &&!"".equals(lostPubBean.getAllPubAddress().trim())){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubAddress());//申请人地址
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}  
			if(find("K11")){
				if(lostPubBean.getAllPubAgentAddress()!=null&&!"".equals(lostPubBean.getAllPubAgentAddress().trim())){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentAddress());//代理人地址
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("K12")){
				if(lostPubBean.getAllPubPhone()!=null && !"".equals(lostPubBean.getAllPubPhone().trim())){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubPhone());//申请人电话
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
			if(find("K13")){
				if(lostPubBean.getAllPubAgentPhone()!=null && !"".equals(lostPubBean.getAllPubAgentPhone().trim())){
					Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentPhone());//代理人联系电话
				}else{
					Dispatch.put(selection, "Text", " ");
				}
				Dispatch.call(selection, "MoveRight"); 
			}
		}else{//单独挂失  挂失补发  挂失销户
			 //有代理人
			if( lostPubBean.getAllPubIsDeputy()!=null && "1".equals(lostPubBean.getAllPubIsDeputy())){
				if(find("K7")){
						Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIdCardName());//代理挂失人
					    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K8")){
						Dispatch.put(selection, "Text", "身份证");//代理人证件名称
					    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K9")){
						Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentIDNo());//代理人证件号码
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
						Dispatch.put(selection, "Text", lostPubBean.getAllPubPhone());//申请人电话
					    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K13")){
						Dispatch.put(selection, "Text", lostPubBean.getAllPubAgentPhone());//代理人联系电话
				    	Dispatch.call(selection, "MoveRight"); 
				}
			}else{ //本人
				if(find("K7")){
					Dispatch.put(selection, "Text", " ");//代理挂失人
				    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K8")){
						Dispatch.put(selection, "Text", " ");//代理人证件名称
					    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K9")){
						Dispatch.put(selection, "Text"," ");//代理人证件号码
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
						Dispatch.put(selection, "Text", lostPubBean.getAllPubPhone());//申请人电话
					    Dispatch.call(selection, "MoveRight"); 
				}
				if(find("K13")){
						Dispatch.put(selection, "Text", " ");//代理人联系电话
				    	Dispatch.call(selection, "MoveRight"); 
				}
			}
		}
		if(find("K15")){
			Dispatch.put(selection, "Text", lostPubBean.getAllPubAccNo());//卡号
			Dispatch.call(selection, "MoveRight"); 
		}
		if(find("K16")){
			if("0".equals(lostPubBean.getLostOrSolve()) || "1".equals(lostPubBean.getLostOrSolve()) ||"2".equals(lostPubBean.getLostOrSolve())){
			       Dispatch.put(selection, "Text", show.getOpenDate());//开户日期
			}else{
				    Dispatch.put(selection, "Text", lostPubBean.getOpenDate().replace("/","").trim());//开户日期
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		if(find("K17")){
			if("0".equals(lostPubBean.getLostOrSolve()) || "1".equals(lostPubBean.getLostOrSolve()) ||"2".equals(lostPubBean.getLostOrSolve())){
			   Dispatch.put(selection, "Text", show.getCertNo());//凭证号
			}else if("3".equals(lostPubBean.getLostOrSolve()) ||"4".equals(lostPubBean.getLostOrSolve())){//3.解挂补发  4.解挂销户
				if("0".equals(lostPubBean.getSolveType())){//银行卡
					Dispatch.put(selection, "Text", "无");
				}else{
					Dispatch.put(selection, "Text", lostPubBean.getCertNo());//凭证号
				}
			}else if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销
				if("0".equals(lostPubBean.getSolveType())){//银行卡
					Dispatch.put(selection, "Text", "无");
				}else{
					Dispatch.put(selection, "Text", lostPubBean.getApplInfos().getCert_no());//凭证号
				}
			}
	        Dispatch.call(selection, "MoveRight"); 
		}
		
		if(find("K19")){
			if(!"5".equals(lostPubBean.getLostOrSolve()) && !"4".equals(lostPubBean.getLostOrSolve()) &&!"3".equals(lostPubBean.getLostOrSolve())){
				   Dispatch.put(selection, "Text", "申请日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","."));//申请日期				
			   }else{
				   Dispatch.put(selection, "Text", " ");  
			   }
			Dispatch.call(selection, "MoveRight"); 
		}
	     //解挂补发 解挂销户 挂失撤销 不打印申请人签字
		if(!"5".equals(lostPubBean.getLostOrSolve()) && !"4".equals(lostPubBean.getLostOrSolve()) &&!"3".equals(lostPubBean.getLostOrSolve())){
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

			  }  
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

   		    }
     	}      	   	   	
       String date=DateUtil.getDateTime("HH:mm:ss");
   	if("0".equals(lostPubBean.getLostOrSolve())){//(0-单独挂失，1-挂失补领,2-挂失销户，3-解挂补发,4-解挂销户,5-挂失撤销)
   		logger.info("选择挂失");    	   	
   		if("0".equals(lostPubBean.getAllPubIsDeputy())){//  是否为代理人挂失(知道密码/忘记密码)  0.本人 1.代理人
       		logger.info("生成本人(知道密码/忘记密码)挂失申请书图片");
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
		        if(find("K56")){
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
    		    	 if("0".equals(lostPubBean.getYseNoPass())){
       		    	    Dispatch.put(selection, "Text", " ");
       		    	}else{
       		    	    String enddata=data7(lostPubBean);//日期加七天
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
   		      if(find("K52")){
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
   				if (find("K53")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			    }
			    if (find("K54")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			    }
			    if (find("K55")) {
					Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
					Dispatch.call(selection, "MoveRight");
			    }
    	   }else{//代理人挂失(忘记密码/知道密码)
    			logger.info("生成代理人(忘记密码/知道密码)挂失申请书图片");
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
   		    if(find("K56")){
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
   		      if(find("K52")){
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
   				if (find("K53")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			 }
			if (find("K54")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			 }
		   if (find("K55")) {
				Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
				Dispatch.call(selection, "MoveRight");
		    }
   		}
       }else if("1".equals(lostPubBean.getLostOrSolve())||"3".equals(lostPubBean.getLostOrSolve())){//挂失补发   解挂补发 
       		logger.info("生成本人挂失立即补发存单申请书图片");
       		 if(find("K20")){
       			 if("1".equals(lostPubBean.getLostOrSolve())){//挂失补发
 				       Dispatch.put(selection, "Text", "挂失补发");
 		          }else{//解挂补发	
 		    	   if("0".equals(lostPubBean.getSolveLostType())){//银行卡
		    		    if("0".equals(lostPubBean.getSolveAccState())){//单挂
					   		Dispatch.put(selection, "Text", "挂失补发");
					   	}else{//双挂
							Dispatch.put(selection, "Text", "挂失补发+密码重置");
						}	
					}else if("2".equals(lostPubBean.getSolveLostType())){//存折
						if("4".equals(lostPubBean.getSolveAccState())){//单挂
						     Dispatch.put(selection, "Text", "挂失补发");
						}else{//双挂
							 Dispatch.put(selection, "Text", "挂失补发+密码重置");
						}	
					}else{//存单
						if("2".equals(lostPubBean.getSolveAccState())){//单挂
						     Dispatch.put(selection, "Text", "挂失补发");
						}else{//双挂
							 Dispatch.put(selection, "Text", "挂失补发+密码重置");
						}	
					}
 		         }
        	         Dispatch.call(selection, "MoveRight");      
        	      }
    		    if(find("K21")){
    		    	if("1".equals(show.getCardOrAccOrAcc1())){
         			     Dispatch.put(selection, "Text", "账    号："+lostPubBean.getNewAccNo());
         			  }else{
         				 Dispatch.put(selection, "Text", "账    号："+lostPubBean.getAllPubAccNo());
         			  }        		   
        	           Dispatch.call(selection, "MoveRight"); 
        	      }  
    		   if(find("K49")){
      		        Dispatch.put(selection, "Text", "新凭证号："+lostPubBean.getCertNoAdd());
      	            Dispatch.call(selection, "MoveRight"); 
      	         }
    		   if(find("K56")){
    				Dispatch.put(selection, "Text", "申请日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","."));//申请日期				
    				Dispatch.call(selection, "MoveRight"); 
    			}
    		   if(find("K22")){
     			    Dispatch.put(selection, "Text", "交易日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","").trim()+" "+date);
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
     			   if("3".equals(lostPubBean.getLostOrSolve())){
     				   Dispatch.put(selection, "Text", "开户日期："+lostPubBean.getOpenDate().replace("/","").trim());
     			   }else{
     				   Dispatch.put(selection, "Text", "开户日期："+show.getOpenDate());
     			   }
     	               Dispatch.call(selection, "MoveRight"); 
     		      }
     		   if(find("K28")){
     			  if("3".equals(lostPubBean.getLostOrSolve())){
     				   Dispatch.put(selection, "Text", "结 存 额："+lostPubBean.getEndAmt());
     			  }else{
      				   Dispatch.put(selection, "Text", "结 存 额："+show.getEndAmt());
      			   }
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
      	          
      			 if("1".equals(lostPubBean.getLostOrSolve())){//挂失补发
 				       Dispatch.put(selection, "Text", "挂失种类：存单");
 		         }else{//解挂补发	
 		        	 if("0".equals(lostPubBean.getSolveLostType())){//银行卡					
 		        		 if("0".equals(lostPubBean.getSolveAccState())){//单挂
      	            		Dispatch.put(selection, "Text", "挂失种类：银行卡");
      	            	 }else{//双挂
      	            		Dispatch.put(selection, "Text", "挂失种类：银行卡+密码");
      	            	 }					
      	             }else if("2".equals(lostPubBean.getSolveLostType())){//存折
      	            	 if("4".equals(lostPubBean.getSolveAccState())){//单挂
      	            		Dispatch.put(selection, "Text", "挂失种类：存折");
      	            	 }else{//双挂
      	            		Dispatch.put(selection, "Text", "挂失种类：存折+密码");
      	            	 }
      	             }else{//存单
      	            	 if("2".equals(lostPubBean.getSolveAccState())){//单挂
      	            		Dispatch.put(selection, "Text", "挂失种类：存单"); 
      	            	 }else{//双挂
      	            		Dispatch.put(selection, "Text", "挂失种类：存单+密码");  
      	            	 }
      	             }
 		          }
      			  Dispatch.call(selection, "MoveRight"); 
      		   }
      		   if(find("K32")){
      			 if("1".equals(show.getCardOrAccOrAcc1())){
     			     Dispatch.put(selection, "Text", "新 账 号："+lostPubBean.getNewAccNo());
     			   }else{
     			     Dispatch.put(selection, "Text", "新 账 号："+lostPubBean.getAllPubAccNo());
     			   }
     	             Dispatch.call(selection, "MoveRight");      			  
      		    }
      		 if(find("K33")){
      			  if("3".equals(lostPubBean.getLostOrSolve())){
      				  Dispatch.put(selection, "Text", "产品名称："+lostPubBean.getProName());
     			    }else{
     			      Dispatch.put(selection, "Text", "产品名称："+show.getProName());
     			    }
      			      Dispatch.call(selection, "MoveRight");
      		     }
      		if(find("K34")){
     			  AccLostAndReturnInfoBean bean = (AccLostAndReturnInfoBean)lostPubBean.getAccMap().get("resAccInfo");
     			  String depTerm="";
		  			if(bean.getProCode().startsWith("RJ")){//产品为如意存+ 则转换为天数
		  				String startdate=bean.getStartRateDate().replace("/","");//起息日期
		  				String enddate=bean.getEndIntDate().replace("/","");//到期日期
		  				SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
		  				Date a1 = fommter.parse(startdate);
		  				Date b1 = fommter.parse(enddate);
		  			    int	datee = DateTool.differentsDays(a1, b1);
		  			    depTerm=String.valueOf(datee)+"天";
		  			    Dispatch.put(selection, "Text","存    期："+depTerm);//如意存+ 的存期
		  			}else{
		  				//产品为其他
		  				if(bean.getDepTerm().startsWith("0")){
		  				    depTerm=bean.getDepTerm().replace(bean.getDepTerm().substring(0,2), bean.getDepTerm().substring(1,2));
		  				}else{
		  					depTerm=bean.getDepTerm();
		  				}
		  				if(depTerm.contains("Y")){
		  					Dispatch.put(selection, "Text","存    期："+depTerm.replace("Y", "年"));//存期
		  				}else if(depTerm.contains("M")){
		  					Dispatch.put(selection, "Text","存    期："+depTerm.replace("M", "个月"));//存期
		  				}else if(depTerm.contains("D")){
		  					Dispatch.put(selection, "Text","存    期："+depTerm.replace("D", "天"));//存期
		  				}else{
		  					Dispatch.put(selection, "Text","存    期："+depTerm+"天");//存期
		  				}
		  			}
     			     Dispatch.call(selection, "MoveRight"); 
     		  }
      		  if(find("K35")){
      			  if("3".equals(lostPubBean.getLostOrSolve())){
      				 Dispatch.put(selection, "Text", "凭 证 号："+lostPubBean.getCertNo());	
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
      			       Dispatch.put(selection, "Text", "新凭证号："+lostPubBean.getCertNoAdd());     			   
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
   			        Dispatch.put(selection, "Text", " ");
   	                Dispatch.call(selection, "MoveRight"); 
   		       }
   		     if(find("K46")){
   			        Dispatch.put(selection, "Text", " ");
   	                Dispatch.call(selection, "MoveRight"); 
   		        }
   		     if(find("K52")){
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
   			if (find("K53")) {
				Dispatch.put(selection, "Text", " ");
				Dispatch.call(selection, "MoveRight");
		    }
		   if (find("K54")) {
				Dispatch.put(selection, "Text", " ");
				Dispatch.call(selection, "MoveRight");
		    }
		   if (find("K55")) {
				Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
				Dispatch.call(selection, "MoveRight");
		    }
       }else if("2".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve()) ){// 2.挂失销户  4.解挂销户
  		  logger.info("生成本人挂失立即销户申请书图片");
 		    if(find("K20")){
 		      if("2".equals(lostPubBean.getLostOrSolve())){//挂失销户
 				       Dispatch.put(selection, "Text", "挂失销户");
 		       }else{//解挂销户	
 		    	  if("0".equals(lostPubBean.getSolveLostType())){//银行卡
		    		   	if("N".equals(lostPubBean.getCardState())){//未激活
		    		   		Dispatch.put(selection, "Text", "挂失销户+密码激活");
					   	}else if("0".equals(lostPubBean.getSolveAccState())){//单挂
					   		Dispatch.put(selection, "Text", "挂失销户");
					   	}else{//双挂
							Dispatch.put(selection, "Text", "挂失销户+密码重置");
						}	
					}else if("2".equals(lostPubBean.getSolveLostType())){//存折
						if("4".equals(lostPubBean.getSolveAccState())){//单挂
						     Dispatch.put(selection, "Text", "挂失销户");
						}else{//双挂
							 Dispatch.put(selection, "Text", "挂失销户+密码重置");
						}	
					}else{//存单
						if("2".equals(lostPubBean.getSolveAccState())){//单挂
						     Dispatch.put(selection, "Text", "挂失销户");
						}else{//双挂
							 Dispatch.put(selection, "Text", "挂失销户+密码重置");
						}	
					}
 		       }
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
 		    if(find("K56")){
 				Dispatch.put(selection, "Text", "申请日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","."));//申请日期				
 				Dispatch.call(selection, "MoveRight"); 
 			}
 		    if(find("K22")){
 			     Dispatch.put(selection, "Text", "交易日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","").trim()+" "+date);
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
 			   if("4".equals(lostPubBean.getLostOrSolve())){
   				   Dispatch.put(selection, "Text", "开户日期："+lostPubBean.getOpenDate().replace("/","").trim());
   			    }else{
    				   Dispatch.put(selection, "Text", "开户日期："+show.getOpenDate());
    			    }
 	             Dispatch.call(selection, "MoveRight"); 
 		      }
 		   if(find("K28")){
 			   if("4".equals(lostPubBean.getLostOrSolve())){
   				   Dispatch.put(selection, "Text", "结 存 额："+lostPubBean.getEndAmt());
   			    }else{
    				   Dispatch.put(selection, "Text", "结 存 额："+show.getEndAmt());
    			    }
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
 			    if("2".equals(lostPubBean.getLostOrSolve())){//挂失销户
 			    	  if("0".equals(show.getCardOrAccOrAcc1())){			   
 					       Dispatch.put(selection, "Text", "挂失种类：银行卡");
 			            }else if("2".equals(show.getCardOrAccOrAcc1())){
 					       Dispatch.put(selection, "Text", "挂失种类：存折");
 			            }else{
 		    			   Dispatch.put(selection, "Text", "挂失种类：存单");     
 		    		    }   
 			       }else{//解挂销户	
 			    	   if("0".equals(lostPubBean.getSolveLostType())){//银行卡					
 							if("0".equals(lostPubBean.getSolveAccState())){//单挂
 							     Dispatch.put(selection, "Text", "挂失种类：银行卡");
 							}else{//双挂
 								 Dispatch.put(selection, "Text", "挂失种类：银行卡+密码");
 							}					
 						}else if("2".equals(lostPubBean.getSolveLostType())){//存折
 							if("4".equals(lostPubBean.getSolveAccState())){//单挂
 								 Dispatch.put(selection, "Text", "挂失种类：存折");
 							}else{//双挂
 								 Dispatch.put(selection, "Text", "挂失种类：存折+密码");
 							}
 						}else{//存单
 							if("2".equals(lostPubBean.getSolveAccState())){//单挂
 								 Dispatch.put(selection, "Text", "挂失种类：存单"); 
 							}else{//双挂
 								 Dispatch.put(selection, "Text", "挂失种类：存单+密码");  
 							}
 						}
 			       }
 	            Dispatch.call(selection, "MoveRight"); 
 		     }
 		   if(find("K32")){		 
       	         Dispatch.put(selection, "Text", "转入卡号："+lostPubBean.getSelectCardNo());        
 		         Dispatch.call(selection, "MoveRight");
 		    }
 		   if(find("K33")){
 			     Dispatch.put(selection, "Text", "销户本金："+lostPubBean.getCanceAmt());
 	             Dispatch.call(selection, "MoveRight"); 
 		     }
 		  if(find("K34")){
 			     Dispatch.put(selection, "Text", "实付利息："+lostPubBean.getCancelRealInte());
 	             Dispatch.call(selection, "MoveRight"); 
 		     }
 		  if(find("K35")){
 			  if(lostPubBean.getClearRate()!=null && !"".equals(lostPubBean.getClearRate()) && Float.parseFloat(lostPubBean.getClearRate())!=0){
 				  Dispatch.put(selection, "Text", "利    率："+lostPubBean.getClearRate().trim()+"%");
 			  }else{
 				  Dispatch.put(selection, "Text", " ");
 			  }
 	             Dispatch.call(selection, "MoveRight"); 
 		    }
 		 if(find("K36")){
 			    Dispatch.put(selection, "Text", "交易机构："+GlobalParameter.branchNo);
 	            Dispatch.call(selection, "MoveRight"); 
 		    }
 		  if(find("K37")){
 			    Dispatch.put(selection, "Text", "设备编号："+GlobalParameter.tellerNo);
 		    }
 		  if(find("K38")){
 			  if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
 			      if("0".equals(lostPubBean.getSolveLostType())){//银行卡
 			    	  Dispatch.put(selection, "Text", " ");
 			      }else{
 			    	  Dispatch.put(selection, "Text", "凭 证 号："+lostPubBean.getCertNo()); 
 			      } 
 			   }else{//挂失销户
 				   if("0".equals(show.getCardOrAccOrAcc1())){//挂失种类	
 					   Dispatch.put(selection, "Text", " ");
 				   }else{ 
 					   Dispatch.put(selection, "Text", "凭 证 号："+show.getCertNo());  		
 				   }
 			   }
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
			   if(!"".equals(lostPubBean.getShtdy()) && lostPubBean.getShtdy()!=null && !"0.00".equals(lostPubBean.getShtdy())){
					Dispatch.put(selection, "Text", "收回唐豆金额："+lostPubBean.getShtdy());
				 }else{
					if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
					   }else{	
							  Dispatch.put(selection, "Text", " ");
					   }
				 }
	            Dispatch.call(selection, "MoveRight"); 
		    }	  
 			List<String> list=getPrint(lostPubBean);//获取销户数据
 			 if(list.size()==0 || list==null){
 				  if(find("K42")){	
 					 Dispatch.put(selection, "Text", " ");	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
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
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text", " ");	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
 						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
 						Dispatch.call(selection, "MoveRight");
 				    }
 			 }else if(list.size()==1){
 				 if(find("K42")){	
 					 Dispatch.put(selection, "Text", list.get(0));	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
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
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text", " ");	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
						Dispatch.call(selection, "MoveRight");
				    }
 			 }else if(list.size()==2){
 				 if(find("K42")){	
 					 Dispatch.put(selection, "Text", list.get(0));	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K45")){			 
 						Dispatch.put(selection, "Text", list.get(1));
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K46")){			 
 						Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text", " ");	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
 						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
 						Dispatch.call(selection, "MoveRight");
 				    }
 			 }else if(list.size()==3){
 				 if(find("K42")){	
 					 Dispatch.put(selection, "Text", list.get(0));	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K45")){			 
 						Dispatch.put(selection, "Text", list.get(1));
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K46")){			 
 						Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text",list.get(2));	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
 						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
 						Dispatch.call(selection, "MoveRight");
 				    }
 			 }else if(list.size()==4){
 				 if(find("K42")){	
 					 Dispatch.put(selection, "Text", list.get(0));	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K45")){			 
 						Dispatch.put(selection, "Text", list.get(1));
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K46")){			 
 						Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text",list.get(2));	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", list.get(3));	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
 						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
 						Dispatch.call(selection, "MoveRight");
 				    }
 			 }else{
 				 if(find("K42")){	
 					 Dispatch.put(selection, "Text", list.get(0));	
 					 Dispatch.call(selection, "MoveRight"); 
 				  }
 				  if(find("K43")){
 					  if(lostPubBean.getAgreeRealInt()!=null && !"".equals(lostPubBean.getAgreeRealInt())){
 						  Dispatch.put(selection, "Text", "约转结息："+lostPubBean.getAgreeRealInt());
 					  }else{
 					      Dispatch.put(selection, "Text", " ");
 					  }
 			              Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K44")){
 					   if(!"".equals(lostPubBean.getTdshJrnlNo()) && lostPubBean.getTdshJrnlNo()!=null ){
 							  Dispatch.put(selection, "Text", "收回唐豆流水号："+lostPubBean.getTdshJrnlNo());
 						  }else{	
 							  Dispatch.put(selection, "Text", " ");
 						  }
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K45")){			 
 						Dispatch.put(selection, "Text", list.get(1));
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K46")){			 
 						Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K52")){			 
 						Dispatch.put(selection, "Text", " ");		
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K47")){	 
 					    Dispatch.put(selection, "Text",list.get(2));	  
 					    Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K50")){
 					    Dispatch.put(selection, "Text", " ");
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K51")){
 					    Dispatch.put(selection, "Text", list.get(3));	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K53")){
 					    Dispatch.put(selection, "Text", list.get(4));	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				  if(find("K54")){
 					    Dispatch.put(selection, "Text", " ");	
 			            Dispatch.call(selection, "MoveRight"); 
 				    }
 				   if (find("K55")) {
 						Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
 						Dispatch.call(selection, "MoveRight");
 				    }
 			 }
		   	}else if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销		 
		   		logger.info("生成挂失撤销申请书图片");
	   		    if(find("K20")){
	   	       	    Dispatch.put(selection, "Text", " ");
	   	            Dispatch.call(selection, "MoveRight"); 
	   	         }
	   	        if(find("K21")){
				    Dispatch.put(selection, "Text", "挂失撤销");
				    Dispatch.call(selection, "MoveRight");
				}
				if (find("K49")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				 if(find("K56")){
	 				Dispatch.put(selection, "Text", "申请日期："+lostPubBean.getAllPubSvrDate().replaceAll("/","."));//申请日期				
	 				Dispatch.call(selection, "MoveRight"); 
	 			}
	   	         if(find("K22")){
	   			    Dispatch.put(selection, "Text", "交易日期："+lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim()+" "+date);
	   	            Dispatch.call(selection, "MoveRight"); 
	   		      }
				if (find("K23")) {
					Dispatch.put(selection, "Text",
							"客 户 号：" + lostPubBean.getCustNo());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K24")) {
					Dispatch.put(selection, "Text",
							"流 水 号：" + lostPubBean.getLostJrnlNo());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K25")) {
					Dispatch.put(selection, "Text",
							"客户名称：" + lostPubBean.getCustName());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K48")) {
					Dispatch.put(selection, "Text", "证件类型：身份证");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K26")) {
					Dispatch.put(selection, "Text","证件号码：" + lostPubBean.getAllPubIDNo());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K27")) {
					Dispatch.put(selection, "Text","开户日期："+ lostPubBean.getApplInfos().getOpenDate().replace("/", "").trim());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K28")) {
					if ("0".equals(lostPubBean.getSolveType())) {// 银行卡
						Dispatch.put(selection, "Text","结 存 额：" + lostPubBean.getCardEndAmt());
					} else {
						Dispatch.put(selection, "Text","结 存 额：" + lostPubBean.getEndAmt());
					}
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K29")) {
					Dispatch.put(selection, "Text","账号/卡号：" + lostPubBean.getAllPubAccNo());
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K30")) {
					Dispatch.put(selection, "Text", "挂失方式：正式挂失");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K31")) {
					if ("3".equals(lostPubBean.getSolveAccState())) {
						Dispatch.put(selection, "Text", "挂失种类：存单+密码");
					} else if ("2".equals(lostPubBean.getSolveAccState())) {
						Dispatch.put(selection, "Text", "挂失种类：存单");
					} else if ("1".equals(lostPubBean.getSolveAccState())) {
						Dispatch.put(selection, "Text", "挂失种类：银行卡+密码");
					} else if ("0".equals(lostPubBean.getSolveAccState())) {
						Dispatch.put(selection, "Text", "挂失种类：银行卡");
					} else if ("4".equals(lostPubBean.getSolveAccState())) {
						Dispatch.put(selection, "Text", "挂失种类：存折");
					} else {
						Dispatch.put(selection, "Text", "挂失种类：存折+密码");
					}
					Dispatch.call(selection, "MoveRight");
				}
		   		if(find("K32")){
		   		    Dispatch.put(selection, "Text", "授权柜员："+lostPubBean.getAllPubFristSupTellerNo());
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
		   		     if("0".equals(lostPubBean.getSolveType())){//银行卡
		   				Dispatch.put(selection, "Text", " ");
		        	 }else{
		   			    Dispatch.put(selection, "Text", "凭 证 号："+lostPubBean.getApplInfos().getCert_no());//凭证号
		   	    	 }
		   	            Dispatch.call(selection, "MoveRight"); 
		         }        		  
		   		if(find("K36")){
		   			 if(!"".equals(lostPubBean.getReMakePwdJrnlNo()) && lostPubBean.getReMakePwdJrnlNo()!=null){
		   				Dispatch.put(selection, "Text", "重置密码流水号："+lostPubBean.getReMakePwdJrnlNo());
		   			 }else{
		   			    Dispatch.put(selection, "Text", "挂失申请书编号："+lostPubBean.getLostApplNo());
		   			 }
		                Dispatch.call(selection, "MoveRight"); 
		   	      }
				if (find("K37")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K38")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
		   		if (find("K39")) {
		   			if(!"".equals(lostPubBean.getReMakePwdJrnlNo()) && lostPubBean.getReMakePwdJrnlNo()!=null){
		   				Dispatch.put(selection, "Text", "挂失申请书编号："+lostPubBean.getLostApplNo());
		   			}else{
		   				Dispatch.put(selection, "Text", " ");
		   			}
		   				Dispatch.call(selection, "MoveRight");
		   		}
				if (find("K40")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K41")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K42")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K43")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K44")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K45")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K46")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K52")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
				}
				if (find("K47")) {
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
				if (find("K53")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			    }
			    if (find("K54")) {
					Dispatch.put(selection, "Text", " ");
					Dispatch.call(selection, "MoveRight");
			    }
			    if (find("K55")) {
					Dispatch.put(selection, "Text", "本人已对上述申请事项确认无误。客户签字：");
					Dispatch.call(selection, "MoveRight");
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
	    	    Dispatch.put(shapeRange, "Top", new Variant(-85));
	    			
	    	   } catch (Exception e) {
	    		  logger.error("电子印章打印失败"+e);

	    	   }
	  	   } else {
				logger.error("获取电子印章失败");
			}
  	     //生成事后申请书图片
  	     String shPath=ToPdf(lostPubBean);
     	 if(!"".equals(shPath)){
     		 logger.info("转换事后申请书图片成功pdf--->"+shPath);   
     		return shPath;
     	 }else{
     		 logger.info("转换事后申请书图片pdf失败");    
     		 return "";
     	 }
     	
     	} catch (Exception e) {
			logger.error("生成申请书图片程序异常"+e);
			return "";
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
				logger.error("文档关闭失败"+e);
			}
			
		}   	   
 }
 	/**
 	 * 获取销户的数据
 	 * @param lostPubBean
 	 * @return
 	 */
    public List<String> getPrint(LostPubBean lostPubBean){
 		  List<String> list=new ArrayList<String>();
 		      if(!StringUtils.isEmpty(lostPubBean.getProName())&&!"无".equals(lostPubBean.getProName())){
 		    	  list.add("产品名称："+lostPubBean.getProName());
 		      }
 		      if(!StringUtils.isEmpty(lostPubBean.getAgreeDepJrnlNo())){
 		    	  list.add("取消约转流水号："+lostPubBean.getAgreeDepJrnlNo());
 		      }
 		      if(!StringUtils.isEmpty(lostPubBean.getAdvnInit())&&!"0.00".equals(lostPubBean.getAdvnInit())){
 		    	  list.add("收回已支付收益："+lostPubBean.getAdvnInit());
 		      }
 		      if(!StringUtils.isEmpty(lostPubBean.getXfdCount()) &&!"0.00".equals(lostPubBean.getXfdCount())){
 		    	  list.add( "收回消费豆数量："+lostPubBean.getXfdCount().substring(0,lostPubBean.getXfdCount().indexOf("."))+"个");
 		      }
 		      if(!StringUtils.isEmpty(lostPubBean.getLostApplNo())){
 		    	  list.add("挂失申请书编号："+lostPubBean.getLostApplNo());
 		      }
 		      
 		return list;
 		  
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
	  * pdf转换为jpg图片
	  * tofile参数为pdf文档的路径
	  */
  public static  String  PdfToJpg(String tofilel)  {
		logger.info("进入pdf文档转换为jpg图片方法");
		RandomAccessFile raf=null;
		FileChannel channel=null;
		FileOutputStream out=null;
		String jpg=null;
		try {
			File file = new File(tofilel);
			raf = new RandomAccessFile(file, "r");
			channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
					channel.size());
			PDFFile pdffile = new PDFFile(buf);
			BufferedImage tag = null;
			PDFPage page = pdffile.getPage(1);
			Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()
					.getWidth(), (int) page.getBBox().getHeight());
			Image img = page.getImage(785, 1112, rect,
					null, true, true);
			tag = new BufferedImage(785, 1112,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, 785,
					1112, null);
			jpg = tofilel.substring(0, tofilel.indexOf(".")) + ".jpg";// 保存jpg文件的路径和名字
			out = new FileOutputStream(jpg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(tag);
			// 1f是提高生成的图片质量
			param2.setQuality(1f, false);
			encoder.setJPEGEncodeParam(param2);
			encoder.encode(tag, param2); // JPEG编码		
			logger.info("pdf文档转换为jpg图片路径为:" + jpg);
			return jpg;
		} catch (Exception e) {
			logger.info("pdf文档转换为jpg图片失败"+e);
			return "";
		}finally{
			try{//关闭流
				if(channel!=null){
					channel.close();
				}
				if(out!=null){
					out.close();
				}
				if(raf!=null){
					raf.close();	
				}
			}catch(Exception e){
				logger.error(e);
			}
		}
		
  }
  /**
   * pdf转换并上传前置服务器
   */
  public  boolean pdfToJpgG(LostPubBean lostPubBean,String pdfPath){
	  try{
	    String shPath = PdfToJpg(pdfPath);	
		if(!"".equals(shPath)){//事后图片路径
			deleteFile(pdfPath);//删除pdf文件		 
     		logger.info("生成事后申请书图片jpg成功--->"+shPath); 
            //上传
     	    boolean b=Ftp(shPath);    	  
     	    if(!b){//上传失败保存在本地
     		  logger.info("事后申请书图片上传失败 --->复制图片到文件："+Property.bill_sb_path);   
     		  FileUtil.copyFileUsingJava7Files(new File(shPath),new File(Property.bill_sb_path+new File(shPath).getName()));
     		  return false;
     	     }  
     	   logger.info("事后申请书图片上传成功--->"+shPath); 
     	   lostPubBean.setLostJpgPath(Property.FTP_SH_PATH+new File(shPath).getName());
     	 }
		}catch(Exception e){
			logger.error("生成事后申请书图片失败"+e);
			return false;
		}
	return true;
	  
  }
  /**
   * 生成事后申请书图片pdf
   * @param args
   */
  public String  ToPdf(LostPubBean lostPubBean){
		// 保存转换pdf文档的路径
		logger.info("事后申请书图片保存中------>");
		try {
			//本地保存路径
			String bcPath=Property.bill_bd_path+lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim()+"\\";
			File dir = new File(bcPath);
			if (dir.isDirectory()) {//检测路径的文件是否存在
				logger.info("文件已存在");
			} else {
				dir.mkdirs();
				logger.info("文件不存在，创建文件--->"+dir);
			}
		if("0".equals(lostPubBean.getLostOrSolve())){//0.单独挂失
			   lostPubBean.setLostselect("0");
		  }else{//1.挂失补发  2.挂失销户  3.解挂补发 4.解挂销户 5.挂失撤销 
			   lostPubBean.setLostselect("1");
		  }
			//本地保存路径和名称
		String tofile =bcPath+ lostPubBean.getLostApplNo().trim()+ lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim()+lostPubBean.getLostselect()+lostPubBean.getLostJrnlNo().trim();
		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { tofile,new Variant(17) }, new int[1]);
		//保存之后的 pdf路径
		String tofilel = tofile + ".pdf";
		logger.info("转换之后pdf文档路径为------>" + tofilel);	
		return tofilel;
		}catch(Exception e){
			logger.error("生成事后申请书图片失败"+e);
		}
		return "";
  }
 
	/**
	 * 成功上传事后监督图片后删除本地图片的方法
	 */
	private void deleteFile(String filePaths) {
	   File filel =new File(filePaths);
		if (filel.isFile()) {// 如果是文件
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = filel.delete();
		if (!result) {// 判断是否全部删除
			filel.delete();
		}
		logger.info("删除成功" + filel);
		}
	}
	/**
	 * 
	 * 上传服务器方法
	 * @param args
	 * @throws IOException
	 */
	public boolean Ftp(String JpgPath){	
		FileInputStream fis = null;
	    	try {
	    		//连接FTP
	    		 File files = new File(JpgPath);
	    		 fis = new FileInputStream(files);
	    		boolean b= FtpUtils.uploadFile(Property.FTP_SH_IP,Integer.parseInt(Property.FTP_SH_PORT), Property.FTP_SH_USER, Property.FTP_SH_PWD, Property.FTP_SH_PATH, "",files.getName(),fis);	    		
		           if(!b){//上传失败
		        	  return false; 
		           }
	    	} catch (Exception e) {
				logger.error("事后申请书上传图片失败"+e);
				return  false;
			}finally{
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						logger.error("关闭流失败"+e);
					}
				}
			}
		
		return true;
		
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
