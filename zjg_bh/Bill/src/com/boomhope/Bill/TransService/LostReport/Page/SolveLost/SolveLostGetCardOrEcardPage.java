/**
 * 
 */
package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * @Description:解挂销户卡/电子子账户自动带出转入银行卡页页面
 * @author zjg
 * @date 2018年4月2日 上午10:23:13
 */
public class SolveLostGetCardOrEcardPage extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SolveLostGetCardOrEcardPage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关
	String name = "";

	@SuppressWarnings("static-access")
	public SolveLostGetCardOrEcardPage(){
		
		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondMaxTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondMaxTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
					}
				});
		delayTimer.start();

		String name = "";
		if("1_1".equals(lostPubBean.getSolveLostType())){
			name = "银行卡";
		}else if("1_2".equals(lostPubBean.getSolveLostType())){
			name = "电子账户";
		}
		
		// 标题
		JLabel depoLum = new JLabel("销户转入的"+name);
		depoLum.setBounds(0, 15, 1009, 180);
		this.showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);		
		
		JPanel jPanel = new JPanel();
		jPanel.setBounds(130, 190, 750, 100);
		jPanel.setLayout(null);
		jPanel.setOpaque(false);
		
		JLabel jLabel = new JLabel();
		jLabel.setLocation(130, 190);
		ImageIcon image = null;
		
		image = new ImageIcon("pic/newPic/accYesSelect.png");
		
		image.setImage(image.getImage().getScaledInstance(750, 100,Image.SCALE_DEFAULT ));
		jLabel.setIcon(image);
		jLabel.setSize(750, 100);
		
		this.showJpanel.add(jPanel);
		this.showJpanel.add(jLabel);
		
		//卡号
		JLabel label1 = new JLabel(name+"："+lostPubBean.getSelectCardNo());
		label1.setBounds(-50, 30, 850, 40);
		label1.setFont(new Font("微软雅黑",Font.PLAIN,35));
		label1.setHorizontalAlignment(SwingUtilities.CENTER);
		jPanel.add(label1);
		
		// 确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(892, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;

				ok();
			}
		});
		add(okButton);

		// 上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				back();
			}
		});
		add(back);

		// 退出
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流

				exit();
			}
		});
		add(exit);		
		
	}
	
	//查询二类帐户限制金额
	public boolean checkErInfo(){
		logger.info("进入二类帐户限额交易查询");
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("02781");
			Map<String,String> map = InterfaceSendMsg.inter02781(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter(map.get("resCode"), map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				if(map.get("errMsg").startsWith("CU77")){
					logger.info("超出限额"+map.get("errMsg"));
					closeDialog(prossDialog);
					openMistakeDialog("销户转入金额超出账户类别限额，无法进行挂失销户");
					on_off=true;
				}else{
					logger.info("查询二类帐户限制金额失败"+map.get("errMsg"));
					closeDialog(prossDialog);
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop("二类帐户限额交易查询失败，请联系大堂经理",map.get("errMsg"),"");
				}
				return false;
			}
			
		} catch (Exception e) {
			logger.error("二类帐户限额交易查询失败"+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用02781接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("二类帐户限额交易查询失败，请联系大堂经理","","调用查询二类账限额接口异常");
			return false;
		}
		return true;
	}				
	
	//查询卡信息
	public boolean checkcard(LostPubBean bean){
		Map inter03845 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03845");
			inter03845 = InterfaceSendMsg.inter03845(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if(!"000000".equals(inter03845.get("resCode"))){
				
				   logger.info("调用卡信息查询【75010】--03845接口失败："+inter03845.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) inter03845.get("errMsg"), "");
				   return false;
			}
		
		} catch (Exception e) {
			
			logger.error("调用卡信息查询【75010】--03845接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03845接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询银行卡信息失败，请联系大堂经理", "", "调用03845接口失败");
			return false;
		}
		return true;	
	}
			
	//查询转入卡状态1
	public Map checkcard1(LostPubBean bean){
		Map card03521 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03521");
			card03521 = InterfaceSendMsg.card03521(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)card03521.get("resCode"), (String)card03521.get("errMsg"));
			if(!"000000".equals(card03521.get("resCode"))){
				
				   logger.info("调用账户信息查询及密码验证-前置03521接口失败："+card03521.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
				   return card03521;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用 03521接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop( "查询银行卡信息失败，请联系大堂经理", "", "调用03521接口失败");
			card03521.put("resCode", "999999");
			return card03521;
		}
		return card03521;
	}						
				
	//查询转入卡状态2
	public Map checkcard2(LostPubBean bean){
		Map card07601 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07601");
			card07601 = InterfaceSendMsg.card07601(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
			if(!"000000".equals(card07601.get("resCode"))){
				
				   logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+card07601.get("errMsg"));
				   closeDialog(prossDialog);
				 //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) card07601.get("errMsg"), "");
				   return card07601;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用 07601接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询银行卡信息失败，请联系大堂经理", "", "调用07601接口失败");
			card07601.put("resCode", "999999");
			return card07601;
		}
		return card07601;
	}		
	
	//查询转入卡黑灰名单
	public Map checkCardTel(LostPubBean bean){
		Map card01597 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("01597");
			card01597 = InterfaceSendMsg.card01597(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)card01597.get("resCode"), (String)card01597.get("errMsg"));
			if(!"000000".equals(card01597.get("resCode"))){
				
				if("0010".equals(card01597.get("resCode")) || "0020".equals(card01597.get("resCode"))){
					
					logger.info("电信诈骗黑灰名单查询-前置01597失败："+card01597.get("errMsg"));
					closeDialog(prossDialog);
					return card01597;
					
				}else{
					
					logger.info("调用电信诈骗黑灰名单查询-前置01597接口失败："+card01597.get("errMsg"));
					closeDialog(prossDialog);
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop("查询黑灰名单信息失败，请联系大堂经理", (String) card01597.get("errMsg"), "");
					return card01597;
				}
			}
		
		} catch (Exception e) {
			
			logger.error("调用电信诈骗黑灰名单查询-前置01597接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用 01597接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("查询黑灰名单信息失败，请联系大堂经理", "", "调用01597接口失败");
			card01597.put("resCode", "999999");
			return card01597;
		}
		return card01597;
	}						
						
	/**
	 * 
	 * 黑名单查询【20115】-前置【08236】
	 */
	public boolean checkHMD(LostPubBean bean){
		// 黑名单查询
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08236");
			Map map08236 = InterfaceSendMsg.interCard08236(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map08236.get("resCode"), (String)map08236.get("errMsg"));
			if ("000000".equals(map08236.get("resCode"))) {
				String IDstata = (String) map08236.get("IDstata");
				logger.info("此账户状态：" + IDstata + "(2.买卖账户 3.失信账户)");
				if ("2".equals(IDstata) || "3".equals(IDstata)) {
					String type = null;
					if ("2".equals(IDstata)) {
						type = "买卖账户";
					} else if ("3".equals(IDstata)) {
						type = "失信账户";
					}				
					closeDialog(prossDialog);
					openMistakeDialog("此账户为"+type+"!");
					on_off=true;
					return false;
				}

			} else {
				logger.error((String) map08236.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("黑名单查询失败",(String) map08236.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08236黑名单查询接口失败：" + e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用 08236接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("黑名单查询失败，请联系大堂经理","", "调用黑名单查询接口08236失败");
			return false;
		}

		return true;
		
	}
	
	/**
	 * 账户信息查询
	 */
	public Map checkSolveLostAcc(LostPubBean bean){
		
		Map inter08239 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08239");
			inter08239 = InterfaceSendMsg.inter08239(bean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08239.get("resCode"), (String)inter08239.get("errMsg"));
			if(!"000000".equals(inter08239.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用账户信息查询【96010】-前置【08239】接口失败："+inter08239.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("账户信息查询失败，请联系大堂经理",(String)inter08239.get("errMsg"), "调用账户信息查询【96010】-前置【08239】接口失败");
				return inter08239;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用账户信息查询【96010】-前置【08239】接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用 08239接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("账户信息查询失败，请联系大堂经理","", "调用账户信息查询【96010】-前置【08239】接口失败");
			inter08239.put("resCode", "999999");
			return inter08239;
		}
		return inter08239;
		
	}
	
	/**
	 *  跳转下页
	 */
	public void ok(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				openProssDialog();
				
				LostPubBean bean = new LostPubBean();
				bean.setCardNo(lostPubBean.getSelectCardNo());
				bean.setSelectCardNo(lostPubBean.getSelectCardNo());
				
				if(!checkHMD(bean)){
					return;
				}
				
				//查询限额
				if(!checkErInfo()){
					return;
				}
				
				//查询黑灰名单
				Map telPhone = checkCardTel(bean);
				if(!"000000".equals((String)telPhone.get("resCode"))){
					
					if(!"0010".equals(telPhone.get("resCode")) && !"0020".equals(telPhone.get("resCode"))){
						return;
					}
				}
				
				//查询转入账户状态
				String accName = "";
				if(!"0008".equals(lostPubBean.getSelectCardNo().substring(6,10))){//银行卡
					
					//查询银行卡
					accName = "银行卡";
					if(!checkcard(bean)){
						return;
					}
					Map card1 = checkcard1(bean);
					if(!"000000".equals((String)card1.get("resCode"))){
						return;
					}
					Map card2 = checkcard2(bean);
					if(!"000000".equals((String)card2.get("resCode"))){
						return;
					}
					
					if("0010".equals(telPhone.get("resCode")) || "0020".equals(telPhone.get("resCode"))){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡"+(String)telPhone.get("errMsg"));
						on_off=true;
						return;
					}			
					if("*".equals(bean.getCardState().trim())){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡已作废");
						on_off=true;
						return;
					}
					if("Q".equals(bean.getCardState().trim())){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡已销户");
						on_off=true;
						return;
					}
					if("B".equals(bean.getCardState().trim())){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡已收回");
						on_off=true;
						return;
					}
					if("1".equals(String.valueOf(bean.getAccState().charAt(1)))){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡已冻结");
						on_off=true;
						return;
					}
					if(card1.get("cardState1")!=null){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡"+(String)card1.get("cardState1"));
						on_off=true;
						return;
					}
					if(card2.get("cardState2")!=null){
						closeDialog(prossDialog);
						openMistakeDialog("该银行卡"+(String)card2.get("cardState2"));
						on_off=true;
						return;
					}
					
					if("1".equals(bean.getCardState().trim())){
						name = "未制卡";
					}else if("N".equals(bean.getCardState().trim())){
						name = "未激活";
					}else if("2".equals(bean.getCardState().trim())){
						name = "未发卡";
					}else if("3".equals(bean.getCardState().trim())){
						name = "未领卡";
					}else if("4".equals(bean.getCardState().trim())){
						name = "已没收";
					}else if("1".equals(String.valueOf(bean.getAccState().charAt(2)))){
						name = "部分冻结";
					}else if("1".equals(String.valueOf(bean.getAccState().charAt(4))) || "2".equals(String.valueOf(bean.getAccState().charAt(4)))){
						name = "已挂失";
					}else if("1".equals(String.valueOf(bean.getAccState().charAt(3)))){
						name = "只收不付";
					}else if("2".equals(String.valueOf(bean.getAccState().charAt(3)))){
						name = "电信诈骗全额止付";
					}else if("3".equals(String.valueOf(bean.getAccState().charAt(3)))){
						name = "电信诈骗全额冻结";
					}else if("1".equals(String.valueOf(bean.getAccState().charAt(11))) || "2".equals(String.valueOf(bean.getAccState().charAt(11)))){
						name = "已密码挂失";
					}if("1".equals(String.valueOf(bean.getAccState().charAt(21))) || "2".equals(String.valueOf(bean.getAccState().charAt(21)))){
						name = "止付";
					}
					
				}else{//电子账户
					
					//查询电子账户
					accName = "电子账户";
					Map checkSolveLostAcc = checkSolveLostAcc(bean);
					if(!"000000".equals((String)checkSolveLostAcc.get("resCode"))){
						return;
					}
					
					if("0010".equals(telPhone.get("resCode")) || "0020".equals(telPhone.get("resCode"))){
						closeDialog(prossDialog);
						openMistakeDialog("该电子账户"+(String)telPhone.get("errMsg"));
						on_off=true;
						return;
					}			
					if("Q".equals((String)checkSolveLostAcc.get("ACCT_STAT"))){
						closeDialog(prossDialog);
						openMistakeDialog("该电子账户已销户");
						on_off=true;
						return;
					}
					if("G".equals((String)checkSolveLostAcc.get("LOST_STAT"))){
						closeDialog(prossDialog);
						openMistakeDialog("该电子账户已挂失");
						on_off=true;
						return;
					}
					if("D".equals((String)checkSolveLostAcc.get("HOLD_STAT"))){
						closeDialog(prossDialog);
						openMistakeDialog("该电子账户已封闭冻结");
						on_off=true;
						return;
					}
					if("B".equals((String)checkSolveLostAcc.get("HOLD_STAT"))){
						name = "部分冻结";
					}
					if("S".equals((String)checkSolveLostAcc.get("HOLD_STAT"))){
						name = "只收不付";
					}
					if("Z".equals((String)checkSolveLostAcc.get("HOLD_STAT"))){
						name = "全额止付";
					}
					if("F".equals((String)checkSolveLostAcc.get("HOLD_STAT"))){
						name = "部分止付";
					}
				}
					
				if(!"".equals(name)){
					
					closeDialog(prossDialog);
					openConfirmDialog("该"+accName+""+name+"，是否继续：是：继续。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							clearTimeText();//清空倒计时
							stopTimer(voiceTimer);//关闭语音
							closeVoice();//关闭语音流
							closeDialog(confirmDialog);
							//进入账户信息签字页面(解挂销户)
							openPanel(new SolveLostApplNoInfosConfirmPage());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							
							on_off=true;
							name="";
							closeDialog(confirmDialog);
						}
					});
					return;
				}
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
				closeVoice();//关闭语音流
				
				closeDialog(prossDialog);
				//进入账户信息签字页面(解挂销户)
				openPanel(new SolveLostApplNoInfosConfirmPage());
			}
		}).start();
	}

	/**
	 * 返回上一步
	 */
	public void back() {
		
		//返回挂失账户信息显示页
		new Thread(new Runnable() {
			@Override
			public void run() {
				lostAction.checkLostBook();
			}
		}).start();
		
	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
