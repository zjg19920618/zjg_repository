package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * 
 * @Description:解挂销户 转入正常银行卡 选择页面
 * @author zjg
 * @date 2018年4月2日 上午10:23:13
 */
public class SolveLostCancelAccCardPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SolveLostCancelAccCardPage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关
	private JPanel[] jPanels = null;
	private JLabel[] jLabels = null;
	private JLabel backStep = null;// 上一页
	private JLabel nextStep = null;// 下一页
	private JLabel jLabela = new JLabel();
	private JLabel jLabelb = new JLabel();
	private JLabel jLabelc = new JLabel();
	private int nowPage = 1;
	private int totalPage;
	private Map<Integer,Boolean> selectMap = new HashMap<Integer, Boolean>();//<第几条, 是否选中>
	private List<String> list = lostPubBean.getBackCards();// 展现的银行卡
	String name = "";
	
	@SuppressWarnings("static-access")
	public SolveLostCancelAccCardPage() {

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

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);// 关语音
				closeVoice();// 关语音流
				excuteVoice("voice/lostRuCard.wav");

			}
		});
		voiceTimer.start();
		
		totalPage = list.size() % 3 == 0 ? list.size() / 3
				: list.size() / 3 + 1;// 总页数
		jPanels = new JPanel[list.size()];
		jLabels = new JLabel[list.size()];
		for (int i = 0; i < list.size(); i++) {
			selectMap.put(i, false);
		}

		showPanel();

		// 上一页
		backStep = new JLabel(new ImageIcon("pic/back_page.png"));
		if (totalPage == 1 || nowPage == 1) {
			backStep.setIcon(new ImageIcon("pic/back_page1.png"));
		}
		backStep.setBounds(526, 770, 150, 50);
		backStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				backStep();
			}
		});
		add(backStep);

		// 下一页
		nextStep = new JLabel(new ImageIcon("pic/next_page.png"));
		if (totalPage == 1) {
			nextStep.setIcon(new ImageIcon("pic/next_page1.png"));
		}
		nextStep.setBounds(709, 770, 150, 50);
		nextStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				nextStep();
			}
		});
		add(nextStep);

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
	
	public void showPanel(){
		
		for (int i = 0; i < jPanels.length; i++) {
			if (jPanels[i] != null) {
				this.showJpanel.remove(jLabels[i]);
				jPanels[i].removeAll();
				jPanels[i].repaint();
				this.showJpanel.remove(jPanels[i]);
				this.showJpanel.remove(jLabela);
				this.showJpanel.remove(jLabelb);
				this.showJpanel.remove(jLabelc);
				this.showJpanel.repaint();
			}
		}
		
		int x=130;
		int y=120;
		for (int i=(nowPage-1)*3;i<list.size()&&i<(nowPage*3);i++) {
			final int a = i;
			if(i-((nowPage-1)*3)>=1){
				y=y+100+50;
			}
			jPanels[i] = new JPanel();
			jPanels[i].setBounds(x, y, 750, 100);
			jPanels[i].setLayout(null);
			jPanels[i].setOpaque(false);
			
			jLabels[i] = new JLabel();
			jLabels[i].setLocation(x, y);
			ImageIcon image = null;
			
			if(selectMap.get(i)){
				image = new ImageIcon("pic/newPic/accYesSelect.png");
			}else{
				image = new ImageIcon("pic/newPic/accNoSelect.png");
			}	
			
			image.setImage(image.getImage().getScaledInstance(750, 100,Image.SCALE_DEFAULT ));
			jLabels[i].setIcon(image);
			jLabels[i].setSize(750, 100);
			
			this.showJpanel.add(jPanels[i]);
			this.showJpanel.add(jLabels[i]);
			
			jLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					
					for (int i=(nowPage-1)*3;i<list.size()&&i<(nowPage*3);i++) {
						
						if(jLabels[i].equals(jLabels[a])){
							
							ImageIcon image = new ImageIcon("pic/newPic/accYesSelect.png");
							image.setImage(image.getImage().getScaledInstance(750, 100,Image.SCALE_DEFAULT ));
							jLabels[a].setIcon(image);
							jLabels[a].setSize(750, 100);
							selectMap.put(a, true);
							
							//遍历Map
							Set<Integer> keySet = selectMap.keySet();
							for (Integer integer : keySet) {
								
								if(a!=integer){//判断非当前的
									ImageIcon image1 = new ImageIcon("pic/newPic/accNoSelect.png");
									image1.setImage(image1.getImage().getScaledInstance(750, 100,Image.SCALE_DEFAULT ));
									jLabels[integer].setIcon(image1);
									jLabels[integer].setSize(750, 100);
									selectMap.put(integer, false);
								}
							}
						}
					}
				}
			});
			
			//标题
			JLabel label = new JLabel("请选择销户要转入的银行卡");
			label.setBounds(60, 30, 850, 40);
			label.setFont(new Font("微软雅黑",Font.PLAIN,35));
			label.setHorizontalAlignment(SwingUtilities.CENTER);
			this.showJpanel.add(label);
			
			//卡号
			JLabel label1 = new JLabel("银行卡："+list.get(i));
			label1.setBounds(-50, 30, 850, 40);
			label1.setFont(new Font("微软雅黑",Font.PLAIN,35));
			label1.setHorizontalAlignment(SwingUtilities.CENTER);
			jPanels[i].add(label1);
			
			//当前页
			jLabela.setText(String.valueOf(nowPage));
			jLabela.setBounds(348, 550, 100, 30);
			jLabela.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabela.setHorizontalAlignment(SwingUtilities.RIGHT);
			this.showJpanel.add(jLabela);	
			
			jLabelb.setText("/");
			jLabelb.setBounds(428, 550, 100, 30);
			jLabelb.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabelb.setHorizontalAlignment(SwingUtilities.CENTER);
			this.showJpanel.add(jLabelb);
			
			//总页数
			jLabelc.setText(String.valueOf(totalPage));
			jLabelc.setBounds(508, 550, 100, 30);
			jLabelc.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabelc.setHorizontalAlignment(SwingUtilities.LEFT);
			this.showJpanel.add(jLabelc);		
		}
	}
	
	/**
	 * 上一页
	 */
	public void backStep(){
		if(totalPage==1 || nowPage==1){
			return;
		}
		if(nowPage==2){
			backStep.setIcon(new ImageIcon("pic/back_page1.png"));
		}
		nextStep.setIcon(new ImageIcon("pic/next_page.png"));
		if(nowPage<=1){
			backStep.setIcon(new ImageIcon("pic/back_page1.png"));
			return;
		}
		nowPage--;
		showPanel();
	}
	
	/**
	 * 下一页
	 */
	public void nextStep(){
		if(totalPage==1){
			return;
		}
		if(totalPage-nowPage==1){
			nextStep.setIcon(new ImageIcon("pic/next_page1.png"));
		}
		backStep.setIcon(new ImageIcon("pic/back_page.png"));
		if(nowPage>=totalPage){
			nextStep.setIcon(new ImageIcon("pic/next_page1.png"));
			return;
		}
		nowPage++;
		showPanel();
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
					openMistakeDialog("转账金额超出账户类别限额");
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
			//接口调用异常，上送流水信息
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
				   //上送流水信心
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop("查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
				   return card03521;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03521接口失败");
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
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口失败");
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
			lostPubBean.getReqMCM001().setIntereturnmsg("调用01597接口失败");
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
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08236接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("黑名单查询失败，请联系大堂经理","", "调用黑名单查询接口08236失败");
			return false;
		}

		return true;
		
	}
	
	/**
	 * 确认
	 */
	public void ok(){
		
		int y = 0;
		Set<Integer> keySet = selectMap.keySet();
		for (Integer integer : keySet) {
			if(selectMap.get(integer)){//统计选中的
				lostPubBean.setSelectCardNo(lostPubBean.getBackCards().get(integer));
				y++;
			}
		}
		
		if(y == 0){
			
			openMistakeDialog("请先选择一个银行卡");
			on_off=true;
			return;
		}
		
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
				
				//查询转入卡状态
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
				
				if(!"".equals(name)){
					
					closeDialog(prossDialog);
					openConfirmDialog("该银行卡"+name+"，是否继续：是：继续。否：退出。");
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
	public void back(){
		
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
	public void exit(){
		
		returnHome();
	}

}
