package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.SoftKeyBoardPopups5;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/***
 * 录入挂失申请书号
 * 
 * @author hk
 *
 */
@SuppressWarnings("static-access")
public class SolveLostInputApplyNoPage extends BaseTransPanelNew {

	static Logger logger = Logger.getLogger(SolveLostInputApplyNoPage.class);
	private static final long serialVersionUID = 1L;
	private JTextField textField;//申请书号输入框
	public JPanel KeyBordJp;//软键盘窗口
	private SoftKeyBoardPopups2 keyPopup2;//软键盘类对象
	private JLabel labelErrMsg;
	
	private boolean on_off=true;
	
	/***
	 * 初始化
	 */
	public SolveLostInputApplyNoPage() {
		logger.info("进入挂失申请书号页面");
		baseTransBean.setThisComponent(this);
		
		voiceTimer = new Timer(voiceSecond, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(voiceTimer);//关语音
				closeVoice();//关语音流
				excuteVoice("voice/SolveLostNo.wav");
				
			}
		});
		voiceTimer.start();

		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaythreeLongTime);
		delayTimer = new Timer(delaythreeLongTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("录入挂失申请书页面倒计时结束");
						/* 倒计时结束退出交易 */ 
		            	clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
		delayTimer.start();
		// 输入框
		KeyBordJp = new JPanel(new BorderLayout());
		KeyBordJp.setBackground(Color.WHITE);
		KeyBordJp.setPreferredSize(new Dimension(2024, 30));
		KeyBordJp.setLayout(new BorderLayout());
		KeyBordJp.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(KeyBordJp);
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请录入挂失申请书号:");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 100, 1009, 40);
		this.showJpanel.add(titleLabel);

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理上一页 */
				logger.info("点击上一步");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				back();
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
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理下一页 */
				scanBill();
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
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				accCancelExit();
			}
		});
		add(backButton);
		

		textField = new JTextField();
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField.setBounds(200, 250, 609, 40);
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击输入框");
				scanBill12();
			}

		});
		textField.setColumns(10);
		this.showJpanel.add(textField);
		keyPopup2 = new SoftKeyBoardPopups2(textField);
		
		labelErrMsg = new JLabel();
		labelErrMsg.setBounds(100, 320, 809, 50);
		labelErrMsg.setFont(new Font("微软雅黑",Font.PLAIN,24));
		labelErrMsg.setHorizontalAlignment(SwingUtilities.CENTER);
		labelErrMsg.setForeground(Color.red);
		labelErrMsg.setVisible(false);
		this.showJpanel.add(labelErrMsg);

	}

	/***
	 * 下一步
	 */
	private void scanBill() {
		logger.info("进入确认方法");
		closeVoice();
		stopTimer(voiceTimer);
		// 业务处理标识判断
		String str = textField.getText();
		if(str==null || "".equals(str.trim())){
			on_off=true;
			openMistakeDialog("请录入挂失申请书号");
			return;
		}else if(str.length()<14){
			on_off=true;
			openMistakeDialog("挂失申请书号录入错误");
			return;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//清空代理人信息
				lostPubBean.setAllPubAgentIdCardName("");
				lostPubBean.setAllPubAgentIDNo("");
				lostPubBean.setAllPubAgentAddress("");
				lostPubBean.setAllPubAgentPhone("");
				//查询挂失申请书
				lostPubBean.setLostApplNo(textField.getText());
				if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){
					lostAction.checkLostBook();
				}else if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销
					openProssDialog();
					//挂失撤销查询结果
					logger.info("查询挂失申请书号结果");
					String result = lostAction.solveReBackApplNoInfos();
					closeDialog(prossDialog);
					if(!"".equals(result) && "0".equals(result)){//查询到信息
						lostAction.checkAllInfos();
						return;
					}else if(!"".equals(result) && result.startsWith("2-")){//查询失败
						labelErrMsg.setText(result.substring(2));
						labelErrMsg.setVisible(true);
						on_off=true;
						return;
					}else{//查询异常
						clearTimeText();
						//挂失申请书号查询失败，上送流水信息
						lostPubBean.getReqMCM001().setReqBefor("");
						lostPubBean.getReqMCM001().setIntereturnmsg("挂失申请书号查询失败");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop("查询申请书号异常，请联系大堂经理。", result.substring(2), "");
						return;
					}
				}
			}
		}).start();
		
		
	}
	
	/***
	 * 上一步
	 */
	private void back() {
		logger.info("进入上一步方法");
		// 关闭语音
		clearTimeText();
		closeVoice();
		stopTimer(voiceTimer);
		//清空代理人信息
		lostPubBean.setAllPubAgentIdCardName("");
		lostPubBean.setAllPubAgentIDNo("");
		lostPubBean.setAllPubAgentAddress("");
		lostPubBean.setAllPubAgentPhone("");
		lostPubBean.setAllPubAccNo("");
		lostPubBean.setAllPubPassAccNo("");
		if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){
			openPanel(new SolveLostTypeSelectPage());
		}else if("5".equals(lostPubBean.getLostOrSolve())){
			lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");//上一步返回拍照页面
			allPubTransFlow.transFlow();
		}
	}

	/***
	 * 键盘
	 */
	private void scanBill12() {
		logger.info("调用软键盘");
		keyPopup2.show(KeyBordJp, 300, 300);
		textField.grabFocus();
	}

}
