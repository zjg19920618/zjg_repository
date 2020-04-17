package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.JFormEx.DemoDLL;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse.BillChangeOpenOutputDepositPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 辨别真伪查询中
 * @author hao
 *
 */
public class BillChangeOpenCheckTFPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(BillChangeOpenCheckTFPanel.class);
	private static final long serialVersionUID = 1L;
	private String billImgPath;//扫描图片路径
	
	public BillChangeOpenCheckTFPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
			
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	excuteVoice("voice/checktf.wav");
            	//鉴别存单真伪
            	checkBill();
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("正在鉴别真伪，请稍后......");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(depoLum);
		/* 加载凭证动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404,170, 340, 253);
		this.showJpanel.add(billImage);
		
	}
	
	/**
	 * 外设扫描存单信息并鉴伪
	 */
	public void checkBill(){
		new Thread(){
			public void run(){
				//机器核查存单真伪
				Map<String, String> check = jwCheck();
				if("999999".equals(check.get("resCode"))){//存单信息获取失败，进入服务终止页
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					serverStop("存单扫描失败","", check.get("errMsg"));
					return;
					
				}else if("999998".equals(check.get("resCode"))){//鉴伪不通过，进入手动输入选择页
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	//退出存单
	        		try {
	        			outBill();
	        			
	        			//存单退出，变更存单状态
	        			GlobalParameter.ACC_STATUS=false;
	        		} catch (Exception e1) {
	        			logger.error("回单模块异常，退存单失败"+e1);
	        			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
	        		}
					openPanel(new BillChangeOpenCheckTFFailPanel(check.get("errMsg")));
					return;
				}
				
				//机器核查扫描OCR
				Map<String, String> ocrCheck = OCRCheck();
				if("999999".equals(ocrCheck.get("resCode"))){//OCR识别不通过,进入手动输入选择页
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	//退出存单
	        		try {
	        			outBill();
	        			
	        			//存单退出，变更存单状态
	        			GlobalParameter.ACC_STATUS=false;
	        		} catch (Exception e1) {
	        			logger.error("回单模块异常，退存单失败"+e1);
	        			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
	        		}
					openPanel(new BillChangeOpenCheckTFFailPanel(ocrCheck.get("errMsg")));
					return;
					
				}else if("999998".equals(ocrCheck.get("resCode"))){//OCR识别账号与选择换开方式不匹配，弹框重新选择换开方式
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	checkResult(ocrCheck.get("errMsg"));
					return;
				}
				
				//查询存单信息
				if(!checkBillMsg()){
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					return;
				}
				
				//存单信息判断
				Map<String, String> judgeBillMsg = judgeBillMsg();
				if("999999".equals(judgeBillMsg.get("resCode"))){
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					serverStop(judgeBillMsg.get("errMsg"), "", "");
					return;
				}else if("999998".equals(judgeBillMsg.get("resCode"))){//OCR识别账号不一致，弹框重新选择换开方式
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	//退出存单
	        		try {
	        			outBill();
	        			
	        			//存单退出，变更存单状态
	        			GlobalParameter.ACC_STATUS=false;
	        		} catch (Exception e1) {
	        			logger.error("回单模块异常，退存单失败"+e1);
	        			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
	        		}
					openPanel(new BillChangeOpenCheckTFFailPanel(judgeBillMsg.get("errMsg")));
					return;
				}
				
				bcOpenBean.setCheckStatus("1");//标记位，存在数据库中，标记这个存单是自动扫描成功的一单
				//全部成功，进入存单展现页
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	openPanel(new BillChangeOpenCheckBillPanel());
				
			}
		}.start();
	}
	
	/**
	 * 核查存单真伪
	 */
	public Map<String,String> jwCheck(){
		Map<String,String> map = new HashMap<String,String>();
		String fc = null;
		String rc = null;
		String ir = null;
		String uv  = null;
		//扫描存单
		try{
			GlobalParameter.ACC_STATUS = true;   //设为有存单标识
			
			logger.debug("开始扫描存单：BH_Scan,扫描前先睡100ms");
			sleep(100);
			
			if(Dispatch.call(BaseLoginFrame.dispath, "BH_Scan",new Variant("0")).getInt() == 0){
				logger.debug("扫描完毕睡100ms");
				Thread.sleep(100);
				logger.debug("开始获取存单信息：BH_Scan");
				
				//获取扫描存单路径
				billImgPath = Dispatch.call(BaseLoginFrame.dispath, "ScanInfo").getString();
				logger.info("存单路径："+billImgPath);
				
				//扫描存单路径格式不符，报错
				if(billImgPath.indexOf("<Results>") == -1){
					logger.error("扫描存单返回格式异常");
					map.put("resCode", "999999");
					map.put("errMsg", "扫描存单返回格式异常");
					return map;
				}
			}
			
			billImgPath = billImgPath.substring(billImgPath.indexOf("<Results>"));
			
			fc = billImgPath.substring(billImgPath.indexOf("<FColor>")+8, billImgPath.indexOf("</FColor>"));
			rc = billImgPath.substring(billImgPath.indexOf("<RColor>")+8, billImgPath.indexOf("</RColor>"));
			ir = billImgPath.substring(billImgPath.indexOf("<IR>")+4, billImgPath.indexOf("</IR>"));
			uv = billImgPath.substring(billImgPath.indexOf("<UV>")+4, billImgPath.indexOf("</UV>"));
			
			logger.debug("正面彩图:"+fc);
			logger.debug("反面彩图:"+rc);
			logger.debug("红外图:"+ir);
			logger.debug("紫外图:"+uv);
			logger.debug("等待100ms然后开始复制图片");
			sleep(100);
			
			if(fc == null || "".equals(fc)){
				logger.error("存单扫描失败，未能生成正面彩图");
				map.put("resCode", "999999");
				map.put("errMsg", "存单扫描失败，未能生成正面彩图");
				return map;
			}else{
				String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +bcOpenBean.getFid()+"\\";
				// --------------------------fc上传----------------
				// 源目录
				File from_fc = new File(fc);
				// 目标目录
				File to_fc = new File(tmp+from_fc.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_fc,to_fc);
				logger.info("完成正面彩图fc拷贝");
				
				// --------------------------rc上传----------------
				// 源目录
				File from_rc = new File(rc);
				// 目标目录
				File to_rc = new File(tmp+from_rc.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_rc,to_rc);
				logger.info("完成反面彩图rc拷贝");
				
				// --------------------------ir上传----------------
				// 源目录
				File from_ir = new File(ir);
				// 目标目录
				File to_ir = new File(tmp+from_ir.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_ir,to_ir);
				logger.info("完成红外ir拷贝");
				
				// --------------------------uv上传----------------
				// 源目录
				File from_uv = new File(uv);
				// 目标目录
				File to_uv = new File(tmp+from_uv.getName());
				// 拷贝文件
				FileUtil.copyFileUsingJava7Files(from_uv,to_uv);
				logger.info("完成紫外uv拷贝");
				
				bcOpenBean.setBillPath_fc(tmp+from_fc.getName());
				bcOpenBean.setBillPath_rc(tmp+from_rc.getName());	
			}
			
		}catch(Exception e){
			logger.error("扫描存单图片失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "扫描存单图片失败");
			return map;
		}
		
		//回单模块取消等待
		try {
			logger.debug("回单模块取消等待：BH_CancelWaitInsert");
			Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
		} catch (Exception e) {
			logger.error("回单模块取消等待失败"+e);
		}
		
		//生成灰度图
		try{
			logger.debug("睡100ms，等图片完全生成后开始生成灰度图：BH_ToGrayImg");
			sleep(100);
			logger.debug("判断正面彩图是否存在："+fc);
			if(!(new File(fc)).exists()){
				logger.error("存单正面彩图不存在："+fc);
				map.put("resCode", "999999");
				map.put("errMsg", "存单正面彩图不存在");
				return map;
			}
			
			// 获取灰度图
			int h = Dispatch.call(BaseLoginFrame.dispath, "BH_ToGrayImg",new Variant(fc),new Variant(Property.getProperties().getProperty("gary_img_path"))).getInt();
			if(h != 0){
				logger.error("生成灰度图结果失败："+h);
				map.put("resCode", "999999");
				map.put("errMsg", "生成存单灰度图失败");
				return map;
			}
			
			logger.debug("生成灰度图路径"+Property.getProperties().getProperty("gary_img_path"));
			
		}catch(Exception e){
			logger.error("生成灰度图失败:"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "生成存单灰度图失败");
			return map;
		}
		
		//存单鉴伪
		try{
			String resultPath = System.getProperty("user.dir");
			logger.debug("睡100ms开始鉴伪：BH_ReceiptValidate");
			sleep(100);
			
			// 鉴伪识别结果
			int i = Dispatch.call(BaseLoginFrame.dispath, "BH_ReceiptValidate",new Variant(fc),new Variant(Property.getProperties().getProperty("gary_img_path")),new Variant(ir),new Variant(uv),new Variant(resultPath)).getInt();
			logger.info("存单鉴定真伪结果:"+i);
			
			bcOpenBean.setJwResult(String.valueOf(i));
			//上送鉴伪结果
			try {
			
				IntefaceSendMsg.tms0007(bcOpenBean);
			} catch (Exception e) {
				logger.error("上送鉴伪结果失败"+e);
				
			}
			
			logger.debug("获取鉴伪返回信息：BH_GetScanErrMSG");
			logger.info(Dispatch.call(BaseLoginFrame.dispath, "BH_GetScanErrMSG",new Variant(i)));
			
			if(i != 0){
				logger.error("存单鉴伪为假存单:"+i);
				map.put("resCode", "999998");
				map.put("errMsg", "存单鉴伪结果不通过,请重新选择识读方式");
				return map;
			}
			
			logger.debug("存单鉴伪为真存单:"+i);
			
		}catch(Exception e){
			logger.error("存单鉴别真伪失败:"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "存单鉴伪调用异常");
			return map;
		}
		
		map.put("resCode", "000000");
		return map;
	}
	
	/**
	 * 存单OCR识别
	 * @return
	 */
	public Map<String,String> OCRCheck(){
		Map<String,String> maps = new HashMap<String,String>();
		String accNo="";
		String billNo="";
		// OCR识别
		try {
			logger.debug("开始OCR识别");
			String fc = billImgPath.substring(billImgPath.indexOf("<FColor>")+8, billImgPath.indexOf("</FColor>"));
			
			Map<String,String> map = new DemoDLL().ocrBill(fc,Property.OCR_MODE_PATH,Property.OCR_SDK_PATH);
			accNo=map.get("acctNo");
			billNo = map.get("billNo");
			logger.info("OCR识别账号结果："+accNo);
			logger.info("OCR识别凭证号结果："+billNo);
			
			if(accNo == null || "".equals(accNo.trim())){
				
				maps.put("resCode", "999999");
				maps.put("errMsg", "账号识别失败，请重新选择识读方式");
				return maps;
			}
			if(billNo == null || "".equals(billNo.trim())){
				
				maps.put("resCode", "999999");
				maps.put("errMsg", "凭证号识别失败，请重新选择识读方式");
				return maps;
			}
			accNo = accNo.trim().replace("\r\n", "");
			billNo = billNo.trim().replace("\r\n", "");
			
		} catch (Exception e) {
			logger.error("OCR识别失败"+e);
			maps.put("resCode", "999999");
			maps.put("errMsg", "票面信息识别失败，请重新选择识读方式");
			return maps;
		}
		//判断识别为账号/卡号-子账号
		try {
			//子账户
			logger.debug("判断账号："+accNo);
			bcOpenBean.setBillType("");//凭证种类
			bcOpenBean.setBillNo(billNo);//凭证号
			
			
				
			if(accNo.contains("-") ){
				if("0008".equals(accNo.substring(6,10))){
			
			       	logger.debug("选择电子子账户");
				    bcOpenBean.setSubAccNoCancel("3");
			     	logger.debug("识别账号为电子子账号，匹配成功");
			    	String[] split = accNo.split("-");
			    	bcOpenBean.setAccNo(accNo);//账号-子账号
			    	bcOpenBean.setSubCardNo(split[0]);//账号
				    bcOpenBean.setSubAccNo(split[1]);//子账号
				
				
			     }else if(!"0008".equals(accNo.substring(6,10))){//识别为卡下子账号
					
					logger.debug("识别账号为卡下子账号，匹配成功");
					bcOpenBean.setSubAccNoCancel("0");//卡下子账户标识
					bcOpenBean.setBillType("102");//凭证种类
					String[] split = accNo.split("-");
					bcOpenBean.setAccNo(accNo);//账号-子账号
					bcOpenBean.setSubCardNo(split[0]);//账号
					bcOpenBean.setSubAccNo(split[1]);//子账号
			     }
			       
			  }else	if(!accNo.contains("-")){//识别为普通账号
				//选择普通账户
				
				logger.debug("识别账号为普通账号，匹配成功");
				bcOpenBean.setSubAccNoCancel("1");//普通账号标识
				bcOpenBean.setAccNo(accNo);//账号
			
		       }else{
		    	   
		    	 //识别非普通账号、卡下子账号报错
				
				logger.debug("识别账号非电子账号、普通账号、卡下子账号，匹配失败");
				maps.put("resCode", "999998");
				maps.put("errMsg", "该存单非电子账号、普通账号、卡下子账号，请选择其他换开方式");
				return maps;
			   }
			
		}catch (Exception e) {
			logger.error("存单账号识别异常"+e);
			maps.put("resCode", "999998");
			maps.put("errMsg", "存单账号匹配失败");
			return maps;
		}
		
		maps.put("resCode", "000000");
		return maps;
	}
	
	/**
	 * 调用存单信息查询接口
	 */
	public boolean checkBillMsg(){
		try {
			bcOpenBean.setIsCheckPass("0");//不验密
			bcOpenBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = IntefaceSendMsg.inter07601(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info((String)inter07601.get("errMsg"));
            	//退出存单
        		try {
        			outBill();
        			
        			//存单退出，变更存单状态
        			GlobalParameter.ACC_STATUS=false;
        		} catch (Exception e1) {
        			logger.error("回单模块异常，退存单失败"+e1);
        			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
        		}
				openPanel(new BillChangeOpenCheckTFFailPanel((String)inter07601.get("errMsg")));
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用07601存单账号信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07601存单账号信息查询失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop("存单信息查询失败","调用07601存单账号信息查询失败","");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 判断存单信息
	 */
	public Map<String, String> judgeBillMsg(){
		Map<String,String> map = new HashMap<String,String>();
		
		//判断该存单是否存单密码，如果不存在密码，则拒单
		try {
			String drawCoun = bcOpenBean.getDrawCoun().trim();
			logger.info("存单是否有密码："+drawCoun);
			logger.info("存单机构号"+bcOpenBean.getOpenInstNo());
			
			if("0".equals(drawCoun)){
				logger.info("存单无密码，该存单无密码，请对存单增设密码后再进行换开！");
				map.put("resCode", "999999");
				map.put("errMsg", "该存单无密码，请对存单增设密码后再进行换开！");
				return map;
			}
		} catch (Exception e) {
			
			logger.error("判断存单是否验密失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "返回存单信息异常");
			return map;
		}
		
		//判断存单状态异常，拒单
		try {
			String accStatus = bcOpenBean.getAccState();
			logger.info("判断存单状态："+accStatus);
			if(!"0000000000000000".equals(accStatus)){
				
				if(!"0".equals(String.valueOf(accStatus.charAt(0)))){
					
					if("Q".equals(String.valueOf(accStatus.charAt(0)))){
						
						logger.error("存单已销户,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单已销户,请联系大堂经理处理!");
						return map;
						
					}else if("1".equals(String.valueOf(accStatus.charAt(0)))){
						
						logger.error("账户已停用,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "账户已停用,请联系大堂经理处理!");
						return map;
						
					}else if("3".equals(String.valueOf(accStatus.charAt(0)))){
						
						logger.error("渠道限制,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "渠道限制,请联系大堂经理处理!");
						return map;
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
					}
				}else if(!"0".equals(String.valueOf(accStatus.charAt(2)))){
					
					if("1".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单为封闭冻结状态可继续换开!");
						
					}else if("2".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单为部分冻结状态可继续换开!");
						
					}else if("3".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单为只收不付状态可继续换开!");
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
					}
				}else if(!"0".equals(String.valueOf(accStatus.charAt(3)))){
					
					if("1".equals(String.valueOf(accStatus.charAt(3)))){
						
						logger.error("存单为止付状态可继续换开!");
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
						
					}
				}else if(!"0".equals(String.valueOf(accStatus.charAt(7)))){
					
					if("1".equals(String.valueOf(accStatus.charAt(7))) || "2".equals(String.valueOf(accStatus.charAt(7)))){
						
						logger.error("存单已挂失,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单已挂失,请联系大堂经理处理!");
						return map;
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
					}
				}else{
					
					logger.error("存单状态异常,请联系大堂经理处理!");
					map.put("resCode", "999999");
					map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
					return map;
				}
			}
			
			logger.info("存单开户性质："+bcOpenBean.getOpenQlt());
//			if(!"0".equals(bcOpenBean.getOpenQlt())){
//				
//				logger.error("存单为挂失补发或部提开户，请您去柜面办理!");
//				map.put("resCode", "999999");
//				map.put("errMsg", "存单为挂失补发或部提开户，请您去柜面办理!");
//				return map;
//			}
			
		} catch (Exception e) {
			logger.error("判断存单状态失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "返回存单信息异常");
			return map;
		}
		
		//判断存单凭证号是否存在
		try {
			
			String q_certNo = bcOpenBean.getCertNo().trim();
			logger.info("存单帐号"+bcOpenBean.getAccNo());
			logger.info("识别出来的存单账号---》"+bcOpenBean.getAccNo());
			logger.info("前置返回的存单凭证号---》"+q_certNo);
			
			// 判断凭证号是否一致
			logger.info("存单凭证号"+bcOpenBean.getBillNo().replace("\r\n", ""));
			if(!q_certNo.equals(bcOpenBean.getBillNo().replace("\r\n", ""))){
				
				logger.error("存单凭证号不存在");
				map.put("resCode", "999998");
				map.put("errMsg", "票面信息识别失败，请重新选择识读方式");
				return map;
			}
			
		} catch (Exception e) {
			logger.error("存单凭证号核查失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "返回存单信息异常");
			return map;
		}
		
		map.put("resCode", "000000");
		return map;
	}
	
	/**
	 * 失败，提示处理
	 * @param comp
	 * @param params
	 */
	public void checkResult(String msg){
		openConfirmDialog(msg +"。是：重新选择换开方式;否：退出业务;");
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				logger.info("进入重新选择换开页面");
				closeDialog(confirmDialog);
				//退出存单
        		try {
        			outBill();
        			
        			//存单退出，变更存单状态
        			GlobalParameter.ACC_STATUS=false;
        		} catch (Exception e1) {
        			logger.error("回单模块异常，退存单失败"+e1);
        			serverStop("存单未退出，请联系工作人员解决","","");//退存单失败页面
        		}
				openPanel(new BillChangeOpenOutputDepositPanel());
			}
		});
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				logger.info("进入退存单页面");
				closeDialog(confirmDialog);
				openPanel(new BillChangeOpenOutputDepositPanel());
			}
		});
	}
}
