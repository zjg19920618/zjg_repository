package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.BJ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenCheckBillPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenDepositAffirmSignaPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenInputDepositPanels;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpensubAccBankorBillPassPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.SoftKeyBoardPopups;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 鉴伪失败，录入授权柜员密码
 * @author zjg
 *
 */
public class BillChangeOpenAuthorPassJwPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(BillChangeOpenAuthorPassJwPanel.class);
	private static final long serialVersionUID = 1L;;
	final int voiceSecond = 500;
	private JPanel passwordPanel;
	JLabel errInfo = null;// 错误提示
	private SoftKeyBoardPopups keyPopup;
	private JPasswordField textField;
	private Map map;
	private Component comp;
	private boolean on_off=true;//用于控制页面跳转的开关
	/***
	 * 初始化
	 */
	public BillChangeOpenAuthorPassJwPanel() {
		logger.info("进入第一个柜员密码输入页面");
		
		//将当前页面传入流程控制进行操作
		this.comp=this;
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("柜员密码录入页面倒计时结束 ");
				/* 倒计时结束退出交易 */ 
				clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		delayTimer.start();

		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
				excuteVoice("voice/codes.wav");

			}
		});
		voiceTimer.start();

		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入授权柜员密码:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 209, 1009, 40);
		this.showJpanel.add(titleLabel);

		// 键盘面板
		passwordPanel = new JPanel(new BorderLayout());
		passwordPanel.setBackground(Color.WHITE);
		passwordPanel.setPreferredSize(new Dimension(2024, 30));
		passwordPanel.setLayout(new BorderLayout());
		passwordPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel);
		/* 密码框 */
		textField = new JPasswordField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(292, 259, 424, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showKeyBord();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup = new SoftKeyBoardPopups(textField,8);

		errInfo = new JLabel("提示：请录入授权员密码!");
		errInfo.setVisible(false);
		errInfo.setForeground(Color.RED);
		errInfo.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		errInfo.setBounds(349, 322, 258, 23);
		this.showJpanel.add(errInfo);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				logger.info("进入上一步方法");
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				//返回时跳转页面
						
				openPanel(new BillChangeOpenAuthorNoJwPanel());
					
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				nextStep(comp);
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				logger.info("点击退出按钮");
				clearTimeText();
				stopTimer(voiceTimer);//关语音
            	closeVoice();//关语音流
            	billHKExit();
			}
		});
		add(backButton);
	}

	/***
	 * 下一步(校验授权密码)
	 */
	private void nextStep(final Component comp){
		new Thread(){
			@Override
			public void run() {
				openProssDialog();
				
		        String pass = new String(textField.getPassword());
				if(pass == null || pass.equals("")){
					errInfo.setVisible(true);
					closeDialog(prossDialog);
					return;
				}
				
				//获取密码
				bcOpenBean.setSupPass(pass);
				
				PublicBillChangeOpenBean bean=new PublicBillChangeOpenBean();
				bean.setSupTellerNo(bcOpenBean.getSupTellerNo());
				bean.setSupPass(bcOpenBean.getSupPass());
				if("0".equals(bcOpenBean.getSubAccNoCancel())){
					bean.setAuthNoCode("03517");
				}else if("3".equals(bcOpenBean.getSubAccNoCancel())){
					bean.setAuthNoCode("03522");
				}else{
					bean.setAuthNoCode("07624");
				}
				logger.info("授权柜员号:"+bcOpenBean.getSupTellerNo());
				logger.info("授权密码:"+pass);
				
				try {
					logger.info("开始校验授权柜员密码");
					bcOpenBean.getReqMCM001().setReqBefor("77017");
					Map params=IntefaceSendMsg.inter77017(bean);
					String resCode=(String)params.get("resCode");
					String errMsg=(String)params.get("errMsg");
					bcOpenBean.getReqMCM001().setReqAfter(resCode, errMsg);
					if(!IntefaceSendMsg.SUCCESS.equals(resCode)){
						logger.info("柜员授权密码异常："+errMsg);
						closeDialog(prossDialog);
						textField.setText("");
						openMistakeDialog(errMsg);
						return;
					}
					
					logger.info("授权柜员密码校验成功");
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					closeDialog(prossDialog);
					//跳入下一页
					if(bcOpenBean.getTellNo1()!=null && !"".equals(bcOpenBean.getTellNo1())){
						
						if (bcOpenBean.getTellNo2()==null || "".equals(bcOpenBean.getTellNo2())){
							//第一次授权标识
							bcOpenBean.setTellNoState("1");
							//再次返回第二次授权
							openPanel(new BillChangeOpenAuthorNoJwPanel());
							
						}else{
							
							//第二次授权标识
							bcOpenBean.setTellNoState("2");
							//重新放入存单
							openPanel(new BillChangeOpenInputDepositPanels());
						}
					}
					
				} catch (Exception e) {
					//清空倒计时
					clearTimeText();
					stopTimer(voiceTimer);
					closeVoice();
					closeDialog(prossDialog);
					logger.error("柜员授权密码异常："+e);
					bcOpenBean.getReqMCM001().setIntereturnmsg("柜员授权密码异常");
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop("抱歉，授权柜员密码异常,请重新操作", "","调用校验授权密码接口异常");
				}
			}
		}.start();
	}

	/***
	 * 键盘
	 */
	private void showKeyBord() {
		logger.info("调用软键盘");
		keyPopup.show(passwordPanel, 160, 310);
	}
}
