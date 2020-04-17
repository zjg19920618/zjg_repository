package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

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
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 扫描存单，查询存单信息
 * @author zjg
 *
 */
public class BillChangeOpenCheckTFSecondPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(BillChangeOpenCheckTFSecondPanel.class);
	private static final long serialVersionUID = 1L;
	private String billImgPath;//扫描图片路径
	
	public BillChangeOpenCheckTFSecondPanel() {
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
				if(!"".equals(bcOpenBean.getAccNo()) && bcOpenBean.getAccNo()!=null){
					//有账号查询存单信息
					if(!checkBillMsg()){
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
						return;
					}
				}else{
					//使用凭证号查询存单信息
					if(!checkBillNo()){
						clearTimeText();//清空倒计时
						stopTimer(voiceTimer);//关闭语音
		            	closeVoice();//关闭语音流
						return;
					}
				}
				
				
				//存单信息判断
				Map<String, String> judgeBillMsg = judgeBillMsg();
				if("999999".equals(judgeBillMsg.get("resCode"))){
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
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
							openPanel(new BillChangeOpenEnterDepositMsgPanel());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							//返回时跳转页面
							returnHome();
						}
					});
					return;
				}
				if("1".equals(bcOpenBean.getHKMethod())){
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
	            	openPanel(new BillChangeOpenCheckBillPanel());
				}else{
					bcOpenBean.setCheckStatus("2");//标记位，存在数据库中，标记这个存单是手动输入的一单
					//全部成功，进入存单展现页
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
		        	closeVoice();//关闭语音流
		        	openPanel(new BillChangeOpenCheckBillPanel());
				}
				
			}
		}.start();
	}
	
	
	/**
	 * 调用存单信息查询接口
	 */
	public boolean checkBillMsg(){
		//授权后的鉴伪与第一次鉴伪识别失败后的账户和凭证号比对，一致通过，不一致退出
		if("1".equals(bcOpenBean.getJwState())){
			if(!bcOpenBean.getAccNo().equals(bcOpenBean.getAccFirstNo()) || !bcOpenBean.getBillNo().equals(bcOpenBean.getBillFirstNo())){
				logger.info("与第二次手动输入比对不一致");
				openPanel(new BillChangeOpenCheckTFFailPanel("非同一张存单，请重新放入存单或手工输入"));
				return false;
			}
		}
		
		try {
			bcOpenBean.setIsCheckPass("0");//不验密
			bcOpenBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = IntefaceSendMsg.inter07601(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info((String)inter07601.get("errMsg"));				
					openConfirmDialog((String)inter07601.get("errMsg")+"，是否重新输入。是：请重新输入。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("进入重新手输页面");
							closeDialog(confirmDialog);
							//重新手输
							openPanel(new BillChangeOpenEnterDepositMsgPanel());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("返回首页");
							closeDialog(confirmDialog);
							returnHome();
						}
					});
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
	 * 使用凭证号号查询存单信息
	 */
	public boolean checkBillNo(){
		logger.info("进入使用凭证号查询存单信息");
		try {			
			bcOpenBean.getReqMCM001().setReqBefor("02791");
			Map inter02791 = IntefaceSendMsg.inter02791(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)inter02791.get("resCode"), (String)inter02791.get("errMsg"));
			if(!"000000".equals(inter02791.get("resCode"))){
				logger.info("凭证号查询存单信息02791接口查询失败");
				logger.info((String)inter02791.get("errMsg"));
				
				if(((String)inter02791.get("errMsg")).startsWith("H001")){//查询表出错
					
					bcOpenBean.setIsCheckPass("0");//不验密
					bcOpenBean.getReqMCM001().setReqBefor("07601");
					Map inter07601 = IntefaceSendMsg.inte07601(bcOpenBean);
					bcOpenBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
					if(!"000000".equals(inter07601.get("resCode"))){
						logger.info((String)inter07601.get("errMsg"));
						
							openConfirmDialog((String)inter07601.get("errMsg")+"，是否重新输入。是：请重新输入。否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("进入重新手输页面");
									closeDialog(confirmDialog);
									//重新手输
									openPanel(new BillChangeOpenEnterDepositMsgPanel());
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									logger.info("返回首页");
									closeDialog(confirmDialog);
									returnHome();
								}
							});
							return false;
						
					}
					String accno=bcOpenBean.getAccNo().trim();
					if("0008".equals(accno.substring(6,10))){
						
				       	
					    bcOpenBean.setSubAccNoCancel("3");
				     	logger.debug("查询为电子子账号，匹配成功");
				    	String[] split = accno.split("-");
				    	bcOpenBean.setAccNo(accno);//账号-子账号
				    	bcOpenBean.setSubCardNo(split[0]);//账号
					    bcOpenBean.setSubAccNo(split[1]);//子账号
					
					
				     }else if(!"0008".equals(accno.substring(6,10))){//识别为卡下子账号
						
						logger.debug("查询为卡下子账号，匹配成功");
						bcOpenBean.setSubAccNoCancel("0");//卡下子账户标识
						bcOpenBean.setBillType("102");//凭证种类
						String[] split = accno.split("-");
						bcOpenBean.setAccNo(accno);//账号-子账号
						bcOpenBean.setSubCardNo(split[0]);//账号
						bcOpenBean.setSubAccNo(split[1]);//子账号
				     }
				}else{
					
				openConfirmDialog((String)inter02791.get("errMsg")+"，是否重新输入。是：请重新输入。否：退出。");
				 confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("进入重新手输页面");
						closeDialog(confirmDialog);
						//重新手输
						openPanel(new BillChangeOpenEnterDepositMsgPanel());
					}
				   });
			    	confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("返回首页");
						closeDialog(confirmDialog);
						returnHome();
					}
			    	});
			    	return false;
				}
			}else{
				
			logger.info("查询成功,账号："+bcOpenBean.getAccNo()+"为普通账户");
			bcOpenBean.setSubAccNoCancel("1");//普通账号标识
			
			}
		} catch (Exception e) {
			logger.error("调用02791存单凭证号信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用存单凭证号信息接口查询失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop("存单信息查询失败","调用02791存单凭证号信息查询失败","");
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
				logger.info("该存单无密码，请对存单增设密码后再进行换开！");
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
			
		} catch (Exception e) {
			logger.error("判断存单状态失败"+e);
			map.put("resCode", "999999");
			map.put("errMsg", "返回存单信息异常");
			return map;
		}
		
		//判断存单凭证号是否存在
		try {
			if(!"".equals(bcOpenBean.getBillNo().trim()) && bcOpenBean.getBillNo().trim()!=null){
		       	String q_certNo = bcOpenBean.getCertNo().trim();
		    	logger.info("存单帐号"+bcOpenBean.getAccNo());
		    	logger.info("识别出来的存单账号---》"+bcOpenBean.getAccNo());
		    	logger.info("前置返回的存单凭证号---》"+q_certNo);
			
		    	// 判断凭证号是否一致
		    	logger.info("手动输入存单凭证号"+bcOpenBean.getBillNo().trim());
		    	if(!q_certNo.equals(bcOpenBean.getBillNo().trim())){
				
				    logger.error("存单凭证号不存在");
				    map.put("resCode", "888888");
			    	map.put("errMsg", "账号与凭证号不为同一账户，是否重新输入");
			    	return map;
			  }
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
