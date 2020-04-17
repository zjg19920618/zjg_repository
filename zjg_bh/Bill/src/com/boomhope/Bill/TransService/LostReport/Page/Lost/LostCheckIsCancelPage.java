package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.InputCardAndPwd.AllPublicInputPasswordPanel;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.Util.UtilButton;

/**
 * @Description:选择是否立即销户
 * @author zjg
 * @date 2018年3月23日 上午10:41:51
 */
@SuppressWarnings("static-access")
public class LostCheckIsCancelPage extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(LostCheckIsCancelPage.class);
	private boolean on_off = true;// 用于控制页面跳转的开关

	public LostCheckIsCancelPage() {

		baseTransBean.setThisComponent(this);

		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,
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
				excuteVoice("voice/lostSelect.wav");

			}
		});
		voiceTimer.start();

		// 标题
		JLabel depoLum = new JLabel("是否立即销户？");
		depoLum.setBounds(0, 30, 1009, 180);
		this.showJpanel.add(depoLum);
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setHorizontalAlignment(JLabel.CENTER);

		/* 是(销户) */
		UtilButton trueButton = new UtilButton("pic/newPic/selectYes.jpg",
				"pic/newPic/selectYes.jpg");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
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

				yesCan();

			}
		});
		trueButton.setBounds(160, 280, 200, 90);
		this.showJpanel.add(trueButton);

		/* 否(不销户) */
		UtilButton falseButton = new UtilButton("pic/newPic/selectNo.jpg",
				"pic/newPic/selectNo.jpg");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
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

				noCan();
			}
		});
		falseButton.setBounds(640, 280, 200, 90);
		this.showJpanel.add(falseButton);
		
		//温馨提示
		JLabel tishi = new JLabel("温馨提示：选择“否”，仅对账户进行挂失，不做销户处理.");
		tishi.setBounds(0, 450, 989, 55);
		tishi.setForeground(Color.RED);
		tishi.setFont(new Font("微软雅黑",Font.PLAIN,25));
		tishi.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi);

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

	/**
	 * 选择销户
	 */
	public void yesCan() {

		lostPubBean.setIsCan("1");//选择立即销户
		lostPubBean.getReqMCM001().setTranscode("11043");
		ShowAccNoMsgBean accMsg = (ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg");
		// 银行卡
		if("0".equals(accMsg.getCardOrAccOrAcc1())){
			
			// 判断是否开通约定转存
			if("1".equals(lostPubBean.getIsAgreedDep())){//开通约定转存
				
				openConfirmDialog("该银行卡已开通约定转存，将要解除，是否继续：是：继续。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						
						new Thread(new Runnable() {
			    			@Override
			    			public void run() {
			    				//查询转入的银行卡
			    				openProssDialog();
			    				lostAction.checkAllCards();
			    			}
			    		}).start();
					}
					
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						on_off = true;
						closeDialog(confirmDialog);
					}
					
				});
				
			}else{//未开通
				
				new Thread(new Runnable() {
	    			@Override
	    			public void run() {
	    				//查询转入的银行卡
	    				openProssDialog();
	    				lostAction.checkAllCards();
	    			}
	    		}).start();
			}
			
		}else if("1".equals(accMsg.getCardOrAccOrAcc1()) || "2".equals(accMsg.getCardOrAccOrAcc1())){//个人存单/存折
			
			new Thread(new Runnable() {
    			@Override
    			public void run() {
    				//查询转入的银行卡
    				openProssDialog();
    				lostAction.checkAllCards();
    			}
    		}).start();
			
		}else if("1_1".equals(accMsg.getCardOrAccOrAcc1()) || "1_2".equals(accMsg.getCardOrAccOrAcc1())){
			
			// 进入自动带出卡/电子账号显示页
			lostPubBean.setLostOrSolve("2");
			openPanel(new LostGetCardOrEcardPage());

		}
	}

	/**
	 * 选择不销户
	 */
	public void noCan() {
		lostPubBean.setLostOrSolve("0");
		lostPubBean.setIsCan("0");//选择不立即销户
		lostPubBean.getReqMCM001().setTranscode("11039");
		//进入账户信息签字页面(只挂失)
		lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
		allPubTransFlow.transFlow();

	}

	/**
	 * 返回上一步
	 */
	public void back() {
		
		lostPubBean.setIsCan("");//清空选择是否立即销户标识
		//进入账户信息选择页
		openPanel(new LostShowAccNoPage());
	}

	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
