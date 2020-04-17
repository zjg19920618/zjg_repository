package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.InputCardAndPwd.AllPublicInputPasswordPanel;
import com.boomhope.Bill.TransService.LostReport.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 银行卡/存单/存折账户信息展现页
 * @author zjg
 *
 */
@SuppressWarnings({"unchecked","static-access"})
public class LostShowAccNoPage extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostShowAccNoPage.class);
	private boolean on_off=true;//用于控制页面跳转的开关
	private JPanel[] jPanels = null;
	private JLabel[] jLabels = null;
	private ImageIcon imageYes = null;
	private ImageIcon imageNo = null;
	private JLabel backStep = null;//上一页
	private JLabel nextStep = null;//下一页
	private JLabel jLabela = new JLabel();	
	private JLabel jLabelb = new JLabel();	
	private JLabel jLabelc = new JLabel();	
	private int nowPage = 1;
	private int totalPage;
	private List<ShowAccNoMsgBean> list= null;//展现的账户信息
	String name1 = "";//账户状态
	
	public LostShowAccNoPage(){
		
		baseTransBean.setThisComponent(this);
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondLongestTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongestTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！","","");
			}
		});
		delayTimer.start();
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	excuteVoice("voice/selectLostAcc.wav");
            	
            }      
        });            
		voiceTimer.start();
		
		list = (List<ShowAccNoMsgBean>)lostPubBean.getAccMap().get("list");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).isSelect()){
				list.get(i).setSelect(false);
			}
		}
		
		totalPage = list.size() % 2 == 0 ? list.size()/2 : list.size()/2+1;//总页数
		jPanels = new JPanel[list.size()];
		jLabels = new JLabel[list.size()];
		
		imageYes=new ImageIcon("pic/newPic/accYesSelect.png");
		imageYes.setImage(imageYes.getImage().getScaledInstance(989, 240,Image.SCALE_DEFAULT ));
		
		imageNo=new ImageIcon("pic/newPic/accNoSelect.png");
		imageNo.setImage(imageNo.getImage().getScaledInstance(989, 240,Image.SCALE_DEFAULT ));
		
		showPanel();
		
		//上一页
		backStep = new JLabel(new ImageIcon("pic/back_page.png"));
		if(totalPage==1 || nowPage==1){
			backStep.setIcon(new ImageIcon("pic/back_page1.png"));
		}
		backStep.setBounds(526, 770, 150, 50);
		backStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一页按钮");
            	backStep();
			}
		});
		add(backStep);	
		
		//下一页
		nextStep = new JLabel(new ImageIcon("pic/next_page.png"));
		if(totalPage==1){
			nextStep.setIcon(new ImageIcon("pic/next_page1.png"));
		}
		nextStep.setBounds(709, 770, 150, 50);
		nextStep.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击下一页按钮");
            	nextStep();
			}
		});
		add(nextStep);
		
		//确认
		JLabel okButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(892, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确定按钮");
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
		
		//上一步
		JLabel back = new JLabel(new ImageIcon("pic/preStep.png"));
		back.setBounds(1075, 770, 150, 50);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
            	back();
			}
		});
		add(back);	
		
		//退出
		JLabel exit= new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	
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
		
		for (int i=(nowPage-1)*2;i<list.size()&&i<(nowPage*2);i++) {
			final int a = i;
			int x=10;
			int y=20;
			if(i-((nowPage-1)*2)>=1){
				y=270;
			}
			jPanels[i] = new JPanel();
			jPanels[i].setBounds(x, y, 989, 240);
			jPanels[i].setLayout(null);
			jPanels[i].setOpaque(false);
			
			jLabels[i] = new JLabel();
			jLabels[i].setLocation(x, y);
			if(list.get(i).isSelect()){
				
				jLabels[i].setIcon(imageYes);
			}else{
				
				jLabels[i].setIcon(imageNo);
			}
//			//已正式挂失的变灰，不支持挂失
//			if("0".equals(list.get(i).getCardOrAccOrAcc1())){//卡
//				
//				if(list.get(i).getAccState() != null && "2".equals(String.valueOf(list.get(i).getAccState().charAt(4)))){
//					
//					image = new ImageIcon("pic/newPic/noSelect.png");
//				}
//				
//			}else if("1".equals(list.get(i).getCardOrAccOrAcc1()) || "2".equals(list.get(i).getCardOrAccOrAcc1()) ||
//					"1_1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_2".equals(list.get(i).getCardOrAccOrAcc1())){//个人存单/存折/卡下子账户存单/电子子账户存单
//				
//				if(list.get(i).getAccState() != null && "2".equals(String.valueOf(list.get(i).getAccState().charAt(7)))){
//					
//					image = new ImageIcon("pic/newPic/noSelect.png");
//				}
//			}
//			
//			//卡下子账户/电子子账户忘记密码且已打印的变灰，不支持挂失
//			if("1".equals(lostPubBean.getYseNoPass())){
//				if(("1_1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_2".equals(list.get(i).getCardOrAccOrAcc1())) && 
//						"2".equals(list.get(i).getPrintState()) ){
//					
//					image = new ImageIcon("pic/newPic/noSelect.png");
//					list.get(i).setAccStateFY("已打印");
//				}
//			}else{
//				list.get(i).setAccStateFY("已打印");
//			}
			
			jLabels[i].setSize(989, 240);
			
			this.showJpanel.add(jPanels[i]);
			this.showJpanel.add(jLabels[i]);
			
			
			jLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					
					for (int i=(nowPage-1)*2;i<list.size()&&i<(nowPage*2);i++) {
						
						if(jLabels[i].equals(jLabels[a])){
							
							if("0".equals(list.get(i).getCardOrAccOrAcc1())){
								
								if(list.get(i).getAccState() != null && "2".equals(String.valueOf(list.get(i).getAccState().trim().charAt(4)))){
									
									openMistakeDialog("此账户状态异常!");
									return;
								}
								
							}else if("1".equals(list.get(i).getCardOrAccOrAcc1()) || "2".equals(list.get(i).getCardOrAccOrAcc1()) || 
									"1_1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_2".equals(list.get(i).getCardOrAccOrAcc1())){
								
								if(list.get(i).getAccState() != null && "2".equals(String.valueOf(list.get(i).getAccState().trim().charAt(7)))){
									
									openMistakeDialog("此账户状态异常!");
									return;
								}
							}
							
							if(("1_1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_2".equals(list.get(i).getCardOrAccOrAcc1())) && 
									"1".equals(lostPubBean.getYseNoPass()) && "2".equals(list.get(i).getPrintState()) ){
								
								openMistakeDialog("该账户不支持密码+存单挂失!");
								return;
							}
							
							jLabels[a].setIcon(imageYes);
							jLabels[a].setSize(989, 240);
							list.get(a).setSelect(true);
							
							for (int j = 0; j < list.size(); j++) {
								
								if(a!=j){//判断非当前的
									
									if(list.get(j).isSelect()){//将其他已选中的取消选中
										
										jLabels[j].setIcon(imageNo);
										jLabels[j].setSize(989, 240);
										list.get(j).setSelect(false);
										
									}
								}
							}
						}
					}
				}
			});
			
			//卡/账号
			String name ="";
			if("0".equals(list.get(i).getCardOrAccOrAcc1())){
				name = "卡       号：";
			}else{
				name = "账       号：";
			}
			JLabel label1 = new JLabel(name.trim());
			label1.setBounds(10, 10, 300, 40);
			label1.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label1);
			
			//卡/账号(值)
			String accNo ="";
			if("0".equals(list.get(i).getCardOrAccOrAcc1())){
				accNo = list.get(i).getCardNo();
			}else if ("2".equals(list.get(i).getCardOrAccOrAcc1())){
				accNo = list.get(i).getAccNo1();
			}else{
				accNo = list.get(i).getAccNo();
			}
			JLabel label11 = new JLabel(accNo);
			label11.setBounds(110, 10, 300, 40);
			label11.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label11.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label11);
			
			//开户日期
			JLabel label2 = new JLabel("开户日期：");
			label2.setBounds(10, 55, 300, 40);
			label2.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label2.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label2);
			
			//开户日期(值)
			JLabel label22 = new JLabel(list.get(i).getOpenDate());
			label22.setBounds(110, 55, 300, 40);
			label22.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label22.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label22);
			
			//账户类型
			JLabel label3 = new JLabel("账户类型：");
			label3.setBounds(10, 100, 300, 40);
			label3.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label3.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label3);
			
			//账户类型(值)
			JLabel label33 = new JLabel(list.get(i).getAccType());
			label33.setBounds(110, 100, 300, 40);
			label33.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label33.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label33);
			
			//支付条件
			JLabel label4 = new JLabel("支付条件：");
			label4.setBounds(10, 145, 300, 40);
			label4.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label4.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label4);
			
			//支付条件(值)
			JLabel label44 = new JLabel(list.get(i).getDrawCondFY());
			label44.setBounds(110, 145, 300, 40);
			label44.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label44.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label44);
			
			//凭证号码
			JLabel label5 = new JLabel("凭证号码：");
			label5.setBounds(10, 190, 300, 40);
			label5.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label5.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label5);
			
			//凭证号码(值)
			JLabel label55 = new JLabel(list.get(i).getCertNo());
			label55.setBounds(110, 190, 300, 40);
			label55.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label55.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label55);
			
			//客户名称
			JLabel label6 = new JLabel("客户名称：");
			label6.setBounds(450, 10, 300, 40);
			label6.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label6.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label6);
			
			//客户名称(值)
			JLabel label66 = new JLabel(list.get(i).getCustName());
			label66.setBounds(550, 10, 300, 40);
			label66.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label66.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label66);
			
			//结存额
			JLabel label7 = new JLabel("结  存  额：");
			label7.setBounds(450, 55, 300, 40);
			label7.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label7.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label7);
			
			//结存额(值)
			JLabel label77 = new JLabel(list.get(i).getEndAmt()+" 元");
			label77.setBounds(550, 55, 300, 40);
			label77.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label77.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label77);
			
			//账户状态
			JLabel label8 = new JLabel("账户状态：");
			label8.setBounds(450, 100, 300, 40);
			label8.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label8.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label8);
			
			//账户状态(值)
			JLabel label88 = new JLabel(list.get(i).getAccStateFY());
			label88.setBounds(550, 100, 410, 40);
			label88.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label88.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label88);
			
			//产品名称
			JLabel label9 = new JLabel("产品名称：");
			label9.setBounds(450, 145, 300, 40);
			label9.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label9.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label9);
			
			//产品名称(值)
			JLabel label99 = new JLabel(list.get(i).getProName());
			label99.setBounds(550, 145, 300, 40);
			label99.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label99.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label99);
			
			//存期
			JLabel label10 = new JLabel("存       期：");
			label10.setBounds(450, 190, 300, 40);
			label10.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label10.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label10);
			
			//存期(值)
			JLabel label1010 = new JLabel(list.get(i).getDepTermFY());
			label1010.setBounds(550, 190, 300, 40);
			label1010.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1010.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanels[i].add(label1010);
			
			//温馨提示
			if("1".equals(lostPubBean.getYseNoPass())){
				JLabel tishi = new JLabel("<html><p>温馨提示：银行卡子账号/电子账户子账号打印出存单的，不支持密码+存单挂失，</p>"
						+ "<p>请先使用银行卡/随身银行重置密码，再进行存单挂失.</p></html>");
				tishi.setBounds(0, 520, 989, 55);
				tishi.setForeground(Color.RED);
				tishi.setFont(new Font("微软雅黑",Font.PLAIN,20));
				tishi.setHorizontalAlignment(SwingUtilities.CENTER);
				this.showJpanel.add(tishi);
			}
			
			//当前页
			jLabela.setText(String.valueOf(nowPage));
			jLabela.setBounds(348, 571, 100, 30);
			jLabela.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabela.setHorizontalAlignment(SwingUtilities.RIGHT);
			this.showJpanel.add(jLabela);	
			
			jLabelb.setText("/");
			jLabelb.setBounds(428, 571, 100, 30);
			jLabelb.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabelb.setHorizontalAlignment(SwingUtilities.CENTER);
			this.showJpanel.add(jLabelb);
			
			//总页数
			jLabelc.setText(String.valueOf(totalPage));
			jLabelc.setBounds(508, 571, 100, 30);
			jLabelc.setFont(new Font("微软雅黑",Font.PLAIN,30));
			jLabelc.setHorizontalAlignment(SwingUtilities.LEFT);
			this.showJpanel.add(jLabelc);		
		}
	}
	
	/**
	 * 挂失销户限额查询（75209）-前置【08178】
	 */
	public boolean checkcardLimitAmt(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08178");
			Map inter08178 = InterfaceSendMsg.inter08178(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08178.get("resCode"), (String)inter08178.get("errMsg"));
			if(!"000000".equals(inter08178.get("resCode"))){
				
				logger.info("挂失销户限额查询（75209）-前置【08178】调用失败："+inter08178.get("errMsg"));
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("限额查询失败，请联系大堂经理。", (String)inter08178.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用挂失销户限额查询（75209）-前置【08178】失败："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08178接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("限额查询失败，请联系大堂经理。", "", "调用挂失销户限额查询（75209）-前置【08178】接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 挂失销户限额查询（20097）-前置【08177】
	 */
	public boolean checkAccLimitAmt(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08177");
			Map inter08177 = InterfaceSendMsg.inter08177(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08177.get("resCode"), (String)inter08177.get("errMsg"));
			if(!"000000".equals(inter08177.get("resCode"))){
				logger.info("挂失销户限额查询（20097）-前置【08177】调用失败："+inter08177.get("errMsg"));
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("限额查询失败，请联系大堂经理。", (String)inter08177.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用挂失销户限额查询（20097）-前置【08177】失败："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08177接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("限额查询失败，请联系大堂经理。", "", "调用挂失销户限额查询（20097）-前置【08177】接口异常");
			return false;
		}
		return true;
	}
	/**
	 * 
	 * 黑名单查询【20115】-前置【08236】
	 */
	public boolean checkHMD(){
		// 黑名单查询
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08236");
			Map map08236 = InterfaceSendMsg.inter08236(lostPubBean);
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
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("黑名单查询失败",(String) map08236.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08236黑名单查询接口失败：" + e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08236接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("黑名单查询失败，请联系大堂经理","", "调用黑名单查询接口08236失败");
			return false;
		}

		return true;
		
	}
	
	/**
	 * 
	 * 查询黑灰名单查询【20115】-前置【08236】
	 */
	public boolean TelephoneFraud(){
		//0-正常 1-涉案名单 2-可疑名单
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("01597");
			Map map01597 = InterfaceSendMsg.inter01597(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map01597.get("resCode"), (String)map01597.get("errMsg"));
			String resCode = (String)map01597.get("resCode");
			if(!"000000".equals(resCode)){
				if("0010".equals(resCode)){
					
					closeDialog(prossDialog);
					logger.info("账户涉案："+resCode+(String)map01597.get("errMsg"));
					openMistakeDialog("该账户涉案，禁止办理业务");
					on_off=true;
					return false;
					  
				}else if("0020".equals(resCode)){
					
					closeDialog(prossDialog);
					logger.info("账户可疑："+resCode+(String)map01597.get("errMsg"));
					openMistakeDialog("该账户可疑，禁止办理业务");
					on_off=true;
					return false;
						  
				}else{
					closeDialog(prossDialog);
					logger.error((String)map01597.get("errMsg"));
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop("黑灰名单查询失败，请联系大堂经理",(String)map01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用01597黑灰名单查询接口失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用01597接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("黑灰名单查询失败，请联系大堂经理","","调用黑灰名单查询接口失败");
			return false;
		}		
		return true;
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
	
	/**
	 * 确认
	 */
	public void ok(){
		
		int x = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).isSelect()){
				logger.info("选中第"+i+"条账户信息：");
				//存放账号
				if("0".equals(list.get(i).getCardOrAccOrAcc1())){//卡账号
					lostPubBean.setAllPubAccNo(list.get(i).getCardNo());
					lostPubBean.setAllPubPassAccNo(list.get(i).getCardNo());
					
				}else if("2".equals(list.get(i).getCardOrAccOrAcc1())){//存折账号
					lostPubBean.setAllPubAccNo(list.get(i).getAccNo1());
					lostPubBean.setAllPubPassAccNo(list.get(i).getAccNo1());
					
				}else if("1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_1".equals(list.get(i).getCardOrAccOrAcc1()) || "1_2".equals(list.get(i).getCardOrAccOrAcc1())){
					lostPubBean.setAllPubAccNo(list.get(i).getAccNo());//存单账号
					if(list.get(i).getAccNo().contains("-")){
						lostPubBean.setAllPubPassAccNo(list.get(i).getAccNo().substring(0, list.get(i).getAccNo().indexOf("-")));
						lostPubBean.setSelectCardNo(list.get(i).getAccNo().substring(0, list.get(i).getAccNo().indexOf("-")));
					}else{
						lostPubBean.setAllPubPassAccNo(list.get(i).getAccNo());
					}
				}
				lostPubBean.setOpenDate(list.get(i).getOpenDate());
				lostPubBean.setEndDate(list.get(i).getEndDate());
				lostPubBean.setDepTerm(list.get(i).getDepTerm());
				lostPubBean.setOpenRate(list.get(i).getOpenRate());
				lostPubBean.setAccType(list.get(i).getAccType());
				lostPubBean.setCertNo(list.get(i).getCertNo());
				lostPubBean.setCustName(list.get(i).getCustName());
				lostPubBean.setEndAmt(list.get(i).getEndAmt());
				lostPubBean.setCardEndAmt(list.get(i).getCardEndAmt());
				lostPubBean.setProCode(list.get(i).getProCode());
				lostPubBean.setProName(list.get(i).getProName());
				lostPubBean.setDrawCond(list.get(i).getDrawCond());
				lostPubBean.setTotalAmt(list.get(i).getTotalAmt());
				lostPubBean.setCardState(list.get(i).getCardState());
				lostPubBean.setOpenChannel(list.get(i).getOpenChannel());
				lostPubBean.setPreDate(list.get(i).getPreDate());
				lostPubBean.setIsAgreedDep(list.get(i).getIsAgreedDep());
				lostPubBean.setStateName("");
				lostPubBean.setCustNo(list.get(i).getCustNo());
				//存放选中的账户信息
				lostPubBean.getAccMap().put("selectMsg", list.get(i));
				x++;
			}
		}
		
		if(x==0){
			on_off=true;
			openMistakeDialog("请先选择一条账户信息!");
			return;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				openProssDialog();
				//查询账号黑名单
				if(!checkHMD()){
					return;
				}	
				
				//查询电信诈骗黑灰名单
				if(!TelephoneFraud()){
					return;
				}
				
				//账户状态校验
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).isSelect()){
						
						String name = "";
						if("0".equals(lostPubBean.getRecOrCan())){//补发
							name = "补发";
						}else if("1".equals(lostPubBean.getRecOrCan())){//销户
							name = "销户";
						}
						
						if("0".equals(list.get(i).getCardOrAccOrAcc1())){
							
							if(list.get(i).getCardState()!= null ){
								//卡账户状态
								if("0".equals(list.get(i).getCardState().trim()) || "B".equals(list.get(i).getCardState().trim())){//允许正常或者Ic卡已收回的 
									
								}else{
									
									if("N".equals(list.get(i).getCardState().trim())){
										if("0".equals(lostPubBean.getYseNoPass())){
											on_off=true;
											closeDialog(prossDialog);
											openMistakeDialog("该银行卡未激活，请选择“本人+忘记密码/无密码”申请挂失!");
											return;
										}
									}else if("1".equals(list.get(i).getCardState().trim())){
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡未制卡!");
										return;
									}else if("2".equals(list.get(i).getCardState().trim())){
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡已制卡,未发卡!");
										return;
									}else if("3".equals(list.get(i).getCardState().trim())){
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡未领!");
										return;
									}else if("4".equals(list.get(i).getCardState().trim())){
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡已没收!");
										return;
									}else if("*".equals(list.get(i).getCardState().trim())){
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡已作废!");
										return;
									}else{
										on_off=true;
										closeDialog(prossDialog);
										openMistakeDialog("该银行卡状态异常!");
										return;
									}
								}
							}
							
							//查询卡限额
							if(!checkcardLimitAmt()){
								return;
							}
							logger.info("查询出的卡限额："+lostPubBean.getLimitAmt()+"元");
							logger.info("选中卡的结存额："+lostPubBean.getCardEndAmt()+"元");
							//查询出的卡限额
							Float limitAmt = Float.valueOf(lostPubBean.getLimitAmt().trim());
							//卡结存额
							Float cardAmt = Float.valueOf(lostPubBean.getCardEndAmt().trim());
							if(cardAmt>limitAmt){
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该银行卡已超出机具挂失限额，请于柜面办理.");
								return;
							}
							
							//卡账户状态
							if("1".equals(String.valueOf(list.get(i).getAccState().charAt(11))) || "2".equals(String.valueOf(list.get(i).getAccState().charAt(11)))){
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该银行卡已密码挂失");
								return;
							}
							if(list.get(i).getCardState1()!= null && !"".equals(list.get(i).getCardState1())){//停用
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该银行卡"+list.get(i).getCardState1());
								return;
							}
							if(list.get(i).getCardState2()!= null && !"".equals(list.get(i).getCardState2())){//渠道限制
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该银行卡"+list.get(i).getCardState2());
								return;
							}
								
							if("0".equals(lostPubBean.getYseNoPass())){//补发/销户
								
								//卡账户状态
								if("1".equals(String.valueOf(list.get(i).getAccState().charAt(1)))){
									name1 = "封闭冻结";
								}else if("1".equals(String.valueOf(list.get(i).getAccState().charAt(2)))){
									name1 = "部分冻结";
								}else if("2".equals(String.valueOf(list.get(i).getAccState().charAt(3)))){
									name1 = "电信诈骗全额止付";
								}else if("3".equals(String.valueOf(list.get(i).getAccState().charAt(3)))){
									name1 = "电信诈骗全额冻结";
								}else if("1".equals(String.valueOf(list.get(i).getAccState().charAt(21)))){
									name1 = "部分止付";
								}else if("2".equals(String.valueOf(list.get(i).getAccState().charAt(21)))){
									name1 = "全额止付";
								}else if("1".equals(String.valueOf(list.get(i).getAccState().charAt(3)))){
									name1 = "只收不付";
								}		
							}
							
							if(!"".equals(name1)){
								closeDialog(prossDialog);
								openConfirmDialog("您的账户状态异常，只支持挂失申请，无法实现"+name+"处理，是否继续：是：只进行挂失。否：退出。");
								confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mouseReleased(MouseEvent e) {
										clearTimeText();//清空倒计时
										stopTimer(voiceTimer);//关闭语音
										closeVoice();//关闭语音流
										closeDialog(confirmDialog);
										lostPubBean.setStateName(name1);
										lostPubBean.setLostOrSolve("0");
										lostPubBean.getReqMCM001().setTranscode("11039");
										//录入密码页	
										lostPubBean.setNextStepName("ACTION_PWD");
										openPanel(new AllPublicInputPasswordPanel());
									}
								});
								confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mouseReleased(MouseEvent e) {
										
										on_off=true;
										name1="";
										closeDialog(confirmDialog);
									}
								});
								return;
							}
										
						}else{
							
							//查询存单/存折限额
							if(!checkAccLimitAmt()){
								return;
							}
							logger.info("查询出的账户限额："+lostPubBean.getLimitAmt()+"元");
							logger.info("选中账户的结存额："+lostPubBean.getEndAmt()+"元");
							//查询出的账户限额
							Float limitAmt = Float.valueOf(lostPubBean.getLimitAmt().trim());
							//账户结存额
							Float accAmt = Float.valueOf(lostPubBean.getEndAmt().trim());
							if(accAmt>limitAmt){
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该账户已超出机具挂失限额，请于柜面办理.");
								return;
							}
							
							if(list.get(i).getDrawCond() != null){
								
								if("0".equals(list.get(i).getDrawCond().trim()) && "0".equals(lostPubBean.getYseNoPass())){
									on_off=true;
									closeDialog(prossDialog);
									openMistakeDialog("该账户无密码，请选择“忘记密码/无密码”申请挂失!");
									return;
								}
								
								if("2".equals(list.get(i).getDrawCond().trim()) && "0".equals(lostPubBean.getYseNoPass())){
									on_off=true;
									closeDialog(prossDialog);
									openMistakeDialog("该账户凭证件，请选择“忘记密码/无密码”申请挂失!");
									return;
								}
								
								if("3".equals(list.get(i).getDrawCond().trim()) && "0".equals(lostPubBean.getYseNoPass())){
									on_off=true;
									closeDialog(prossDialog);
									openMistakeDialog("该账户凭印鉴，请选择“忘记密码/无密码”申请挂失!");
									return;
								}
							}
							
							if("1".equals(String.valueOf(list.get(i).getAccState().charAt(0)))){//停用
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该账户已停用");
								return;
							}if("3".equals(String.valueOf(list.get(i).getAccState().charAt(0)))){//渠道限制
								on_off=true;
								closeDialog(prossDialog);
								openMistakeDialog("该账户渠道限制");
								return;
							}
							
							if("0".equals(lostPubBean.getYseNoPass())){//补发/销户
								
								//存单/存折账户,卡下子账户、电子子账户状态
								if("1".equals(String.valueOf(list.get(i).getAccState().charAt(2)))){
									name1 = "封闭冻结";
								}else if("2".equals(String.valueOf(list.get(i).getAccState().charAt(2)))){
									name1 = "部分冻结";
								}else if("1".equals(String.valueOf(list.get(i).getAccState().charAt(3)))){
									name1 = "止付";
								}else if("3".equals(String.valueOf(list.get(i).getAccState().charAt(2)))){
									name1 = "只收不付";
								}
								
								if(!"".equals(name1)){
									closeDialog(prossDialog);
									openConfirmDialog("您的账户状态异常，只支持挂失申请，无法实现"+name+"处理，是否继续：是：只进行挂失。否：退出。");
									confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
										
										@Override
										public void mouseReleased(MouseEvent e) {
											clearTimeText();//清空倒计时
											stopTimer(voiceTimer);//关闭语音
											closeVoice();//关闭语音流
											closeDialog(confirmDialog);
											lostPubBean.setStateName(name1);
											lostPubBean.setLostOrSolve("0");
											lostPubBean.getReqMCM001().setTranscode("11039");
											//录入密码页	
											lostPubBean.setNextStepName("ACTION_PWD");
											openPanel(new AllPublicInputPasswordPanel());
										}
									});
									confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
										
										@Override
										public void mouseReleased(MouseEvent e) {
											
											on_off=true;
											name1="";
											closeDialog(confirmDialog);
										}
									});
									return;
								}
								
								//判断销户存单是否未到期
								if("1".equals(lostPubBean.getRecOrCan())){
									//查询个人存单、卡下/电子子账户存单是否已到期
									lostPubBean.setTangChaErrMsg("");
									lostPubBean.setXYDChaErrMsg("");
									logger.info("到期日:"+lostPubBean.getEndDate().trim().replace("/", ""));//到期日
									logger.info("当前核心日:"+lostPubBean.getAllPubSvrDate().trim().replace("/", ""));//当前核心日
									int endDate = Integer.parseInt(lostPubBean.getEndDate().trim().replace("/", ""));//到期日
									int nowDate = Integer.parseInt(lostPubBean.getAllPubSvrDate().trim().replace("/", ""));//当前核心日
									lostPubBean.setTiZhiQu("0");//已到期标识
									if(endDate > nowDate){//未到期
										
										lostPubBean.setTiZhiQu("1");//未到期标识
										checkTD();//查询唐豆
										checkXYD();//查询幸运豆-("改为已付收益")
										String tdMsg = "";
										String xydMsg = "";
										String msg = "";
										if(lostPubBean.getTangChaErrMsg()!=null && !"".equals(lostPubBean.getTangChaErrMsg().trim())){
											
											tdMsg = lostPubBean.getTangChaErrMsg() +"、";
											logger.info("唐豆查询失败:"+lostPubBean.getTangChaErrMsg());
											
											if(lostPubBean.getXYDChaErrMsg()!=null && !"".equals(lostPubBean.getXYDChaErrMsg().trim())){
												
												xydMsg = lostPubBean.getXYDChaErrMsg();
												logger.info("已付收益查询失败:"+lostPubBean.getXYDChaErrMsg());
												
											}else{
												if(lostPubBean.getAdvnInit()!=null && !"".equals(lostPubBean.getAdvnInit().trim())){
													double xyd = Double.valueOf(lostPubBean.getAdvnInit());
													if(xyd!=0){
														
														xydMsg = "提前支取扣回已付收益"+lostPubBean.getAdvnInit().trim()+"元";
														logger.info("已付收益查询成功:"+lostPubBean.getAdvnInit().trim()+"元");
													}
												}
											}
											
											msg = tdMsg + xydMsg;
													
										}else{
											if(lostPubBean.getShtdy()!=null && !"".equals(lostPubBean.getShtdy().trim())){
												double td = Double.valueOf(lostPubBean.getShtdy());
												if(td!=0){
													
													tdMsg = "提前支取将扣回唐豆"+lostPubBean.getShtdy().trim()+"元"+"、";
													logger.info("唐豆查询成功:"+lostPubBean.getShtdy().trim()+"元");
												}
											}
											if(lostPubBean.getXYDChaErrMsg()!=null && !"".equals(lostPubBean.getXYDChaErrMsg().trim())){
												
												xydMsg = lostPubBean.getXYDChaErrMsg();
												logger.info("已付收益查询失败:"+lostPubBean.getXYDChaErrMsg());
												
											}else{
												if(lostPubBean.getAdvnInit()!=null && !"".equals(lostPubBean.getAdvnInit().trim())){
													double xyd = Double.valueOf(lostPubBean.getAdvnInit());
													if(xyd!=0){
														
														xydMsg = "扣回已付收益"+lostPubBean.getAdvnInit().trim()+"元";
														logger.info("已付收益查询成功:"+lostPubBean.getAdvnInit().trim()+"元");
													}
												}
											}
											
											msg = tdMsg + xydMsg;
										}
										if(!"".equals(msg)){
											msg = msg+",";
										}
										//弹框提示是否继续
										logger.info("该存单未到期，仅支持挂失，无法实现销户");
										logger.info("msg："+msg);
										closeDialog(prossDialog);
										openConfirmDialog("存单未到期，"+msg+"是否继续：是：继续挂失销户。否：仅对账户进行挂失，不做销户处理。");
										confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
											
											@Override
											public void mouseReleased(MouseEvent e) {
												clearTimeText();//清空倒计时
												stopTimer(voiceTimer);//关闭语音
												closeVoice();//关闭语音流
												closeDialog(confirmDialog);
												//录入密码页	
												lostPubBean.setNextStepName("ACTION_PWD");
												openPanel(new AllPublicInputPasswordPanel());
												
											}
											
										});
										confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
											
											@Override
											public void mouseReleased(MouseEvent e) {
												clearTimeText();//清空倒计时
												stopTimer(voiceTimer);//关闭语音
												closeVoice();//关闭语音流
												closeDialog(confirmDialog);
												lostPubBean.setLostOrSolve("0");
												lostPubBean.getReqMCM001().setTranscode("11039");
												//录入密码页	
												lostPubBean.setNextStepName("ACTION_PWD");
												openPanel(new AllPublicInputPasswordPanel());
											}
											
										});
										return;
									}
								}
							}
						}
						
						//05-03 zb 增加忘记密码存折判断提示框
						if("1".equals(lostPubBean.getYseNoPass()) && "2".equals(list.get(i).getCardOrAccOrAcc1())){
							closeDialog(prossDialog);
							openConfirmDialog("存折类账户于机具申请双挂，后续仅支持销户处理，是否继续：是：继续。否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									clearTimeText();//清空倒计时
									stopTimer(voiceTimer);//关闭语音
									closeVoice();//关闭语音流
									closeDialog(confirmDialog);							
									//签字确认页
						    		openPanel(new LostConfirmPanel());
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {							
									on_off=true;
									closeDialog(confirmDialog);
								}
							});
							return;
						}
					}
				}
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
				closeVoice();//关闭语音流
				closeDialog(prossDialog);
				
		    	if("0".equals(lostPubBean.getYseNoPass())){//知道密码
		    		
		    		//录入密码页	
					lostPubBean.setNextStepName("ACTION_PWD");
					openPanel(new AllPublicInputPasswordPanel());
		    		
		    	}else{//忘记密码
		    		
		    		//签字确认页
		    		openPanel(new LostConfirmPanel());
		    	}
			}
		}).start();
	}
	
	/**
	 * 07515唐豆查询
	 */
	public void checkTD(){
		logger.info("开始调用唐豆信息查询【02217】-前置07515");
		Map inter07515 = null;
		Date svrdate = null;//核心日
		Date openDate = null;//开户日期
		String demTerm = null;//获取存期
		inter07515 = null;
		try {
			inter07515 = InterfaceSendMsg.inter07515(lostPubBean); 
			if(!"000000".equals(inter07515.get("resCode"))){
				
				logger.info(inter07515.get("errMsg"));
				lostPubBean.setTangChaErrMsg((String)inter07515.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07515唐豆信息查询失败"+e);
			lostPubBean.setTangChaErrMsg("唐豆信息查询失败");
		}	
		
		List<AccTdMsgBean> list = (List<AccTdMsgBean>)inter07515.get("TdMsg");
		
		if(list!=null && list.size()!=0){
			
			for (AccTdMsgBean accTdMsgBean : list) {
				
				if(!"0.00".equals(accTdMsgBean.getShouhuiAmt())){//选择不为0的唐豆
					
					logger.info("查询唐豆返回list："+list);
					logger.info("查询唐豆账户："+accTdMsgBean.getAccNo().trim());
					logger.info("查询唐豆有效标识："+accTdMsgBean.getMark().trim());
					logger.info("查询唐豆金额："+accTdMsgBean.getShouhuiAmt().trim());
					logger.info("查询唐豆存期："+accTdMsgBean.getDepTerm().trim());
					
					if("0".equals(accTdMsgBean.getMark().trim())){//查询唐豆有效标志
						
						try {
							svrdate =  DateUtil.getDate(lostPubBean.getAllPubSvrDate().contains("/")?lostPubBean.getAllPubSvrDate().replace("/", ""):lostPubBean.getAllPubSvrDate());
							openDate = DateUtil.getDate(lostPubBean.getOpenDate().contains("/")?lostPubBean.getOpenDate().replace("/", ""):lostPubBean.getOpenDate());
							//获取开户日期之后的一年
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(openDate);
							int year = calendar.get(Calendar.YEAR);
							calendar.set(Calendar.YEAR, year+1);
							//一年后的开户日
							openDate = calendar.getTime();
							demTerm = accTdMsgBean.getDepTerm().trim();
							
						} catch (ParseException e) {
							logger.info("获取唐豆信息失败:"+e);
							lostPubBean.setTangChaErrMsg("获取唐豆信息失败");
						}
						
						//判断存期，当存期为两年，超过1年再支取的，不调用唐豆收回接口
						if(demTerm.contains("Y") && Integer.parseInt(demTerm.replace("Y", "")) == 2 && openDate.compareTo(svrdate) <= 0){
							
							logger.info("存期为两年，超过1年再支取,不扣回唐豆");
							lostPubBean.setTangChaErrMsg("存期为两年，超过1年再支取,不扣回唐豆");
							
						}else{
							
							logger.info("获取提前支取需要扣回的唐豆金额:"+accTdMsgBean.getShouhuiAmt());
							lostPubBean.setShtdy(accTdMsgBean.getShouhuiAmt());//获取唐豆金额
						}
					}
				}
			}
		}
	}
	/**
	 * 已付收益查询
	 */
	public void checkXYD(){
		logger.info("开始调用存单预计利息【02944】-前置07696/查询已付收益");
		Map inter07696 = null;
		try {
			inter07696 = InterfaceSendMsg.inter07696(lostPubBean); 
			if(!"000000".equals(inter07696.get("resCode"))){
				
				logger.info(inter07696.get("errMsg"));
				lostPubBean.setXYDChaErrMsg((String)inter07696.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07696已付收益查询失败"+e);
			lostPubBean.setXYDChaErrMsg("已付收益查询失败");
		}	
	}
	
	/**
	 * 返回上一步
	 */
	public void back(){
		
		if("0".equals(lostPubBean.getYseNoPass())){//知道密码
			lostPubBean.setAllPubPassAccNo("");
			lostPubBean.setAllPubAccNo("");
			//返回银行卡/存单/存折挂失选择页
			openPanel(new LostTypeSelectPage());
    		
    	}else{//忘记密码
    		//在是否知道密码页面返回上一步是进入到拍照页面
    		lostPubBean.setUpStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
    		lostPubBean.setLostType("");
			lostPubBean.setRecOrCan("");
			lostPubBean.setAllPubAccNo("");
			lostPubBean.setAllPubPassAccNo("");
			//返回密码选择页
    		openPanel(new LostPassSelectPage());
    	}
	}
	
	/**
	 * 退出交易
	 */
	public void exit(){
		
		returnHome();
	}

}
