package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;



/**
 * Title: 重置密码成功提示
 *
 * @author: hk
 * 
 * @date: 2018年4月16日 下午1:40:29  
 * 
 */
@SuppressWarnings("static-access")
public class SolveLostReMakePwdSusscessPage extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(SolveLostReMakePwdSusscessPage.class);
	private static final long serialVersionUID = 1L;
	
	private boolean on_off=true;
	
	public SolveLostReMakePwdSusscessPage(){
		logger.info("进入密码重置成功页面");
		lostPubBean.setThisComponent(this);
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("重置密码成功页面倒计时结束");
						/* 倒计时结束退出交易 */ 
		            	clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
					}
				});
		delayTimer.start();
		
		String title = "";
		if("0".equals(lostPubBean.getSolveLostType())){//银行卡
			
			if("N".equals(lostPubBean.getCardState())){//未激活
				title = "已申请密码激活！";
			}else{
				title = "已申请密码重置！";
			}
		}else{
			title = "已申请密码重置！";
		}
		JLabel labelTitle = new JLabel(title);
		labelTitle.setBounds(0, 200, 1009, 100);
		labelTitle.setFont(new Font("微软雅黑",Font.BOLD,50));
		labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(labelTitle);
		
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
				ok();
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
	}
	
	/**
	 * 
	 * @Description:上一步 
	 * @Author: hk
	 * @date 2018年4月16日 下午1:38:59
	 */
	public void back(){
		logger.info("返回上一步");
		clearTimeText();
		lostPubBean.setReMakePwdNo("");//清除重置密码标志
		lostPubBean.setReMakePwd("");//清除重置的密码
		if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//解挂补发/销户
			
			new Thread(new Runnable() {
    			@Override
    			public void run() {
    				lostAction.checkLostBook();
    			}
    		}).start();
			
			
		}else{
			openPanel(new SolveLostApplNoInfos());//返回挂失申请书信息页面
		}
	}
	
	/**
	 * 
	 * @Description:确认 
	 * @Author: hk
	 * @date 2018年4月16日 下午1:39:12
	 */
	public void ok(){
		logger.info("重新输入重置后的密码");
		clearTimeText();
		lostPubBean.setReMakePwdNo("");//清除重置密码标志
		//重新输入密码
		lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL");
		allPubTransFlow.transFlow();
	}
	
}
