package com.boomhope.Bill.TransService.BillPrint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 手动输入存单页
 * @author Administrator
 *
 */
public class TransHandInputAccInfoPanel extends BaseTransPanelNew{
	
	static Logger logger = Logger.getLogger(TransHandInputAccInfoPanel.class);
	private static final long serialVersionUID = 1L;
	public JTextField textField1;
	public JTextField textField2;
	public JTextField textField3;
	private JPanel passwordPanel1;
	private JPanel passwordPanel2;
	private SoftKeyBoardPopups2 keyPopup1;
	private SoftKeyBoardPopups2 keyPopup2;
	private SoftKeyBoardPopups2 keyPopup3;
	private JPanel passwordPanel3;
	private boolean off_on=true;//开关
	
	public TransHandInputAccInfoPanel() {
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondMaxTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondMaxTime*1000,new ActionListener(){
			public void actionPerformed(ActionEvent e) {
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
				excuteVoice("voice/certificateinformation.wav");

			}
		});
		voiceTimer.start();
		
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请输入存单信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);	
		
		JLabel lblNewLabel = new JLabel("请输入凭证号：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(40, 297, 192, 40);
	
		this.showJpanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("请输入存单账号：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(40, 204, 192, 40);
		this.showJpanel.add(lblNewLabel_1);
		
		// 输入框
		passwordPanel1 = new JPanel(new BorderLayout());
		passwordPanel1.setBackground(Color.WHITE);
		passwordPanel1.setPreferredSize(new Dimension(2024, 30));
		passwordPanel1.setLayout(new BorderLayout());
		passwordPanel1.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel1);
		/* 凭证号 */
		textField1 = new JTextField();
		textField1.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField1.setBounds(255, 297, 562, 50);
		textField1.setEditable(false);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				keyPopup1.show(passwordPanel1,250,350);
					
				
			}

		});
		textField1.setColumns(10);
		this.showJpanel.add(textField1);
		keyPopup1 = new SoftKeyBoardPopups2(textField1);

		
		// 输入框
		passwordPanel2 = new JPanel(new BorderLayout());
		passwordPanel2.setBackground(Color.WHITE);
		passwordPanel2.setPreferredSize(new Dimension(2024, 30));
		passwordPanel2.setLayout(new BorderLayout());
		passwordPanel2.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel2);
		
		/* 账号框 */
		textField2 = new JTextField();
		textField2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField2.setBounds(255, 200, 564, 50);
		textField2.setEditable(false);
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				keyPopup2.show(passwordPanel2, 250, 280);	
				
			}
		});
		textField2.setColumns(10);
		this.showJpanel.add(textField2);
		keyPopup2 = new SoftKeyBoardPopups2(textField2);
		
	
		JLabel lblNewLabel_2 = new JLabel("-");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(828, 200, 23, 50);
		this.showJpanel.add(lblNewLabel_2);
		// 输入框
		passwordPanel3 = new JPanel(new BorderLayout());
		passwordPanel3.setBackground(Color.WHITE);
		passwordPanel3.setPreferredSize(new Dimension(2024, 30));
		passwordPanel3.setLayout(new BorderLayout());
		passwordPanel3.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(passwordPanel3);
		/*子账号文本框*/
		textField3 = new JTextField();
		textField3.setBounds(858, 200, 93, 50);
		textField3.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField3.setEditable(false);
		textField3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				textField1.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField2.setBorder(javax.swing.BorderFactory.createLineBorder(Color.GRAY));
				textField3.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				keyPopup3.show(passwordPanel3, 600, 280);		
				
			}

		});
		this.showJpanel.add(textField3);
		textField3.setColumns(10);
		keyPopup3=new SoftKeyBoardPopups2(textField3);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");	
				if(!off_on){
					return;
				}
				off_on=false;
				bankTrans();
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		
		//确认
		JLabel okButton = new JLabel();
		okButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(890, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!off_on){
					return;
				}
				off_on=false;
				//确认
				nextStep();
			}
		});
		add(okButton);
		
		//返回
		JLabel backButton = new JLabel();
		backButton.setIcon(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//清空倒计时
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				//返回时跳转页面
				returnHome();
			}
		});
		add(backButton);
		billPrintBean.setSubAccNo(null);
	}
	
	/*
	 * 上一步
	 */
	public void bankTrans(){
		//清空倒计时
		clearTimeText();
		stopTimer(voiceTimer);
		closeVoice();
		openPanel(new TransPrintCheckTransPathPanel(billPrintBean));
	}
	
	/**
	 * 下一步
	 */
	public void nextStep(){
		logger.info("凭证号输入值"+textField1.getText());
		logger.info("账号输入值"+textField2.getText());
		logger.info("子账号输入值"+textField3.getText());
		
		if(textField2.getText()==null || "".equals(textField2.getText())){//未输入账号
			openMistakeDialog("请输入账号");
			return;
		}
		
		if(textField1.getText()==null || "".equals(textField1.getText())){//未输入凭证号
			openMistakeDialog("请输入凭证号");
			return;
		}
		
		billPrintBean.setBillType("102");//凭证种类
		billPrintBean.setBillNo(textField1.getText());//凭证号
		
		//判断是否为电子账户
		logger.info("判断是否为电子账户");
		
		if(StringUtils.isNotBlank(textField3.getText())){
			logger.info("输入账号为电子子账号："+textField2.getText()+"-"+textField3.getText());
			logger.info("输入账号为电子子账号，匹配成功");
			billPrintBean.setAccNo(textField2.getText());//账号
			billPrintBean.setSubAccNo(textField3.getText());//子账号
			
		}else{
			logger.info("输入账号为普通账号，匹配成功");
			billPrintBean.setAccNo(textField2.getText());
			
		}
		
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		if(!findInfos()){
			return;
		}
		
		openPanel(new TransInputBankCardPassPanel(billPrintBean,bankBeans));
	}
	
	//查询存单信息
	public boolean findInfos(){
		try {
			billPrintBean.setIsCheckPass("0");//不验密
			billPrintBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = InterfaceSendMsg.inter07601(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"),(String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info("查询账户信息失败："+(String)inter07601.get("errMsg"));
				clearTimeText();
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				serverStop("查询账户信息失败，请联系大堂经理。", (String)inter07601.get("errMsg"), "");
				return false;
			}else{
				BillPrintSubAccInfoBean subBean = (BillPrintSubAccInfoBean)inter07601.get("InputAccInfo");
				String accStatus =subBean.getAccNoState();
				logger.info("判断存单状态："+accStatus);
				if(accStatus.startsWith("Q")){
					
					logger.error("账户已销户!");
					clearTimeText();
					serverStop("您的账户已销户，不能办理此业务","","");
					
					return false;
				}else if("1".equals(String.valueOf(accStatus.charAt(15))) || "2".equals(String.valueOf(accStatus.charAt(15)))){
					
					logger.error("账户状态异常,请联系大堂经理处理!");
					clearTimeText();
					serverStop("您的账户为密码挂失状态，不能办理此业务","","");
					return false;
				}
				List<BillPrintSubAccInfoBean> list1=new ArrayList<>();
				list1.add(subBean);
				//将查询到的子账户列表放入到列表字段中
				billPrintBean.getSubInfo().put("SUB_ACC_NUMS",list1.size());//数据数量
				billPrintBean.getSubInfo().put("SUB_ACC_MSG", list1);
			}
			
		} catch (Exception e) {
			logger.error("查询存单信息失败："+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("查询账户信息失败，请联系大堂经理。", "", "调用查询接口失败(07601)");
			return false;
		}
		return true;
	}
}
