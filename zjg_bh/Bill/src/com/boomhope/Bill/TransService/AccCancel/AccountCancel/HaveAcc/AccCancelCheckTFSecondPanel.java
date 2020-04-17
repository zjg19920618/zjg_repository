package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 扫描存单，查询存单信息
 * @author zjg
 *
 */
public class AccCancelCheckTFSecondPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelCheckTFSecondPanel.class);
	private static final long serialVersionUID = 1L;
	private String billImgPath;//扫描图片路径
	
	public AccCancelCheckTFSecondPanel() {
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
            	//查询存单信息
            	checkBill();
            }      
        });            
		voiceTimer.start();
		// 标题
		JLabel depoLum = new JLabel("查询存单信息中，请稍候......");
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
	 * 外设扫描存单信息
	 */
	public void checkBill(){
		new Thread(){
			public void run(){
				//机器扫描存单
//				Map<String, String> check = jwCheck();
//				if("999999".equals(check.get("resCode"))){//存单扫描失败
//					
//					clearTimeText();//清空倒计时
//					stopTimer(voiceTimer);//关闭语音
//	            	closeVoice();//关闭语音流
//					serverStop("存单扫描失败","", check.get("errMsg"));
//					return;
//					
//				}
				
				//查询存单信息
				if(!checkBillMsg()){
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					return;
				}
				
				//存单信息判断
				Map<String, String> judgeBillMsg = judgeBillMsg();
				accCancelBean.getReqMCM001().setIntereturnmsg(judgeBillMsg.get("errMsg"));
				if("999999".equals(judgeBillMsg.get("resCode"))){
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	accCancelBean.getReqMCM001().setLendirection("");
	            	accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
	            	MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(judgeBillMsg.get("errMsg"), "", "");
					return;
				}
				if("888888".equals(judgeBillMsg.get("resCode"))){
					
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					
					openConfirmDialog(judgeBillMsg.get("errMsg")+"。是：重新输入。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							//重新输入
							openPanel(new AccCancelEnterDepositMsgPanel());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							//返回时跳转页面
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
			            	MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							returnHome();
						}
					});
					return;
				}
				
				accCancelBean.setCheckStatus("2");//标记位，存在数据库中，标记这个存单是手动输入的一单
				//全部成功，进入存单展现页
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	openPanel(new AccCancelCheckBillPanel());
				
			}
		}.start();
	}
	
//	/**
//	 * 扫描存单
//	 */
//	public Map<String,String> jwCheck(){
//		Map<String,String> map = new HashMap<String,String>();
//		String fc = null;
//		String rc = null;
//		String ir = null;
//		String uv  = null;
//		//扫描存单
//		try{
//			GlobalParameter.ACC_STATUS = true;   //设为有存单标识
//			
//			logger.debug("开始扫描存单：BH_Scan,扫描前先睡100ms");
//			sleep(100);
//			
//			if(Dispatch.call(BaseLoginFrame.dispath, "BH_Scan",new Variant("0")).getInt() == 0){
//				logger.debug("扫描完毕睡100ms");
//				Thread.sleep(100);
//				logger.debug("开始获取存单信息：BH_Scan");
//				
//				//获取扫描存单路径
//				billImgPath = Dispatch.call(BaseLoginFrame.dispath, "ScanInfo").getString();
//				logger.info("存单路径："+billImgPath);
//				
//				//扫描存单路径格式不符，报错
//				if(billImgPath.indexOf("<Results>") == -1){
//					logger.error("扫描存单返回格式异常");
//					map.put("resCode", "999999");
//					map.put("errMsg", "扫描存单返回格式异常");
//					return map;
//				}
//			}
//			
//			billImgPath = billImgPath.substring(billImgPath.indexOf("<Results>"));
//			
//			fc = billImgPath.substring(billImgPath.indexOf("<FColor>")+8, billImgPath.indexOf("</FColor>"));
//			rc = billImgPath.substring(billImgPath.indexOf("<RColor>")+8, billImgPath.indexOf("</RColor>"));
//			ir = billImgPath.substring(billImgPath.indexOf("<IR>")+4, billImgPath.indexOf("</IR>"));
//			uv = billImgPath.substring(billImgPath.indexOf("<UV>")+4, billImgPath.indexOf("</UV>"));
//			
//			logger.debug("正面彩图:"+fc);
//			logger.debug("反面彩图:"+rc);
//			logger.debug("红外图:"+ir);
//			logger.debug("紫外图:"+uv);
//			logger.debug("等待100ms然后开始复制图片");
//			sleep(100);
//			
//			if(fc == null || "".equals(fc)){
//				logger.error("存单扫描失败，未能生成正面彩图");
//				map.put("resCode", "999999");
//				map.put("errMsg", "存单扫描失败，未能生成正面彩图");
//				return map;
//			}else{
//				String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +accCancelBean.getFid()+"\\";
//				// --------------------------fc上传----------------
//				// 源目录
//				File from_fc = new File(fc);
//				// 目标目录
//				File to_fc = new File(tmp+from_fc.getName());
//				// 拷贝文件
//				FileUtil.copyFileUsingJava7Files(from_fc,to_fc);
//				logger.info("完成正面彩图fc拷贝");
//				
//				// --------------------------rc上传----------------
//				// 源目录
//				File from_rc = new File(rc);
//				// 目标目录
//				File to_rc = new File(tmp+from_rc.getName());
//				// 拷贝文件
//				FileUtil.copyFileUsingJava7Files(from_rc,to_rc);
//				logger.info("完成反面彩图rc拷贝");
//				
//				// --------------------------ir上传----------------
//				// 源目录
//				File from_ir = new File(ir);
//				// 目标目录
//				File to_ir = new File(tmp+from_ir.getName());
//				// 拷贝文件
//				FileUtil.copyFileUsingJava7Files(from_ir,to_ir);
//				logger.info("完成红外ir拷贝");
//				
//				// --------------------------uv上传----------------
//				// 源目录
//				File from_uv = new File(uv);
//				// 目标目录
//				File to_uv = new File(tmp+from_uv.getName());
//				// 拷贝文件
//				FileUtil.copyFileUsingJava7Files(from_uv,to_uv);
//				logger.info("完成紫外uv拷贝");
	
//				accCancelBean.setBillPath_fc(tmp+from_fc.getName());
//				accCancelBean.setBillPath_rc(tmp+from_rc.getName());	
//			}
//			
//		}catch(Exception e){
//			logger.error("扫描存单图片失败"+e);
//			map.put("resCode", "999999");
//			map.put("errMsg", "扫描存单图片失败");
//			return map;
//		}
//		
//		//回单模块取消等待
//		try {
//			logger.debug("回单模块取消等待：BH_CancelWaitInsert");
//			Dispatch.call(BaseLoginFrame.dispath, "BH_CancelWaitInsert");
//		} catch (Exception e) {
//			logger.error("回单模块取消等待失败"+e);
//		}
//		
//		map.put("resCode", "000000");
//		return map;
//	}
	
	/**
	 * 调用存单信息查询接口
	 */
	public boolean checkBillMsg(){
		//授权后的鉴伪与第一次鉴伪识别失败后的账户和凭证号比对，一致通过，不一致退出
		if("1".equals(accCancelBean.getJwState())){
			if(!accCancelBean.getAccNo().equals(accCancelBean.getAccFirstNo()) || !accCancelBean.getBillNo().equals(accCancelBean.getBillFirstNo())){
				logger.info("与第二次手动输入比对不一致");
				openPanel(new AccCancelCheckTFFailPanel("非同一张存单，请重新放入存单或手工输入"));
				return false;
			}
		}
		
		try {
			accCancelBean.setIsCheckPass("0");//不验密
			accCancelBean.getReqMCM001().setReqBefor("07601");
			final Map inter07601 = IntefaceSendMsg.inter07601(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			accCancelBean.getReqMCM001().setCustomername(accCancelBean.getAccName());
			accCancelBean.getReqMCM001().setTonouns(accCancelBean.getAccName());
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info((String)inter07601.get("errMsg"));
				openConfirmDialog((String)inter07601.get("errMsg")+"，是否重新输入。是：请重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("进入重新手输页面");
						closeDialog(confirmDialog);
						//重新手输
						openPanel(new AccCancelEnterDepositMsgPanel());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("返回首页");
						closeDialog(confirmDialog);
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						returnHome();
					}
				});
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用07601存单账号信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
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
		//判断该存单是否存单密码，如果不存在密码，且机构号也不相等，则拒单
		try {
			String drawCoun = accCancelBean.getDrawCoun().trim();
			logger.info("存单是否有密码："+drawCoun);
			logger.info("存单机构号"+accCancelBean.getOpenInstNo());
			
			if("1".equals(accCancelBean.getSubAccNoCancel())){//普通存单账户判断是否有密码
				
				if(drawCoun.equals("0") || drawCoun.equals("1")){
					
					if("0".equals(drawCoun)){//无密码
						
						if(!accCancelBean.getOpenInstNo().equals(GlobalParameter.branchNo)){//机构号不一致
							
							logger.info("存单无密码，且机构号也不相等，则拒单");
							map.put("resCode", "999999");
							map.put("errMsg", "存单无密码且非本支行开户，机具无法受理");
							return map; 
							
						}else{
							
							logger.info("存单无密码，机构号一致，可继续销户");
							accCancelBean.setNoPass("0");
						}
					}
					
				}else{
					logger.info("存单无密码，请到柜台办理");
					map.put("resCode", "999999");
					map.put("errMsg", "存单无密码，请到柜台办理");
					return map;
				}
			}
		} catch (Exception e) {
			
			logger.error("判断存单是否验密失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "返回存单信息异常");
			return map;
		}
		
		//判断存单状态异常，拒单
		try {
			String accStatus = accCancelBean.getAccState();
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
						
					}else if("N".equals(String.valueOf(accStatus.charAt(0)))){
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "888888");
						map.put("errMsg", "存单状态异常或存单信息输入有误，是否重新输入");
						return map;
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
					}
				}else if(!"0".equals(String.valueOf(accStatus.charAt(2)))){
					
					if("1".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单封闭冻结,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单封闭冻结,请联系大堂经理处理!");
						return map;
						
					}else if("2".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单部分冻结,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单部分冻结,请联系大堂经理处理!");
						return map;
						
					}else if("3".equals(String.valueOf(accStatus.charAt(2)))){
						
						logger.error("存单只收不付,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单只收不付,请联系大堂经理处理!");
						return map;
						
					}else{
						
						logger.error("存单状态异常,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单状态异常,请联系大堂经理处理!");
						return map;
					}
				}else if(!"0".equals(String.valueOf(accStatus.charAt(3)))){
					
					if("1".equals(String.valueOf(accStatus.charAt(3)))){
						
						logger.error("存单止付,请联系大堂经理处理!");
						map.put("resCode", "999999");
						map.put("errMsg", "存单止付,请联系大堂经理处理!");
						return map;
						
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
			
			logger.info("存单开户性质："+accCancelBean.getOpenQlt());
//			if(!"0".equals(accCancelBean.getOpenQlt())){
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
			
			String q_certNo = accCancelBean.getCertNo().trim();
			logger.info("存单帐号"+accCancelBean.getAccNo());
			logger.info("识别出来的存单账号---》"+accCancelBean.getAccNo());
			logger.info("前置返回的存单凭证号---》"+q_certNo);
			
			// 判断凭证号是否一致
			logger.info("存单凭证号"+accCancelBean.getBillNo().replace("\r\n", ""));
			if(!q_certNo.equals(accCancelBean.getBillNo().replace("\r\n", ""))){
				
				logger.error("存单凭证号不存在");
				map.put("resCode", "888888");
				map.put("errMsg", "存单凭证号不存在，是否重新输入");
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
	
}
