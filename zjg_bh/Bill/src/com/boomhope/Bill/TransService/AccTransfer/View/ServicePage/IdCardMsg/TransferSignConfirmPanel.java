package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.SignaturePanel;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView.TransferAccSelectPanel;
import com.boomhope.Bill.Util.Property;

/**
 * 客户签字确认页面
 * 
 * @author hao
 *
 */
public class TransferSignConfirmPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferSignConfirmPanel.class);
	private static final long serialVersionUID = 1L;
	
	private JLabel label;//汇款金额小写
	private JLabel label_1;//汇款用途
	private JLabel lblNewLabel_2;//汇款用途
	private JLabel label_12;//汇款金额小写
	private JLabel label_9;//到账方式
	private JLabel label_13;//到账方式
	private JLabel lblNewLabel_3;//客户签字区
	private ImageIcon image;//签名图片
	private JPanel panelNew;//签名图片面板
	private JPanel panel_1;//背景图片
	private JPanel panel;//背景图片
	private PublicAccTransferBean transferMoneyBean;
	private JLabel labelLine_2;//第二条虚线
	private JLabel labelLine_1;//第一条虚线
	private boolean on_off=true;
	
	/**
	 * 初始化
	 */
	public TransferSignConfirmPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入客户签字确认页面");
		
		transferMoneyBean=transferBean;
		
		//设置倒计时
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();
		
		//清空拍照次数
		transferMoneyBean.setCameraCount("");
		
		panel = new JPanel();
		panel.setBounds(15, 40, 980, 130);
		panel.setOpaque(isOpaque());
		this.showJpanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("转出卡号：");
		lblNewLabel.setBounds(10, 10, 130, 24);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		panel.add(lblNewLabel);

		JLabel label_2 = new JLabel("转出户名：");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_2.setBounds(10, 50, 130, 24);
		panel.add(label_2);

		JLabel label_3 = new JLabel("开 户 行 ：");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_3.setBounds(10, 90, 130, 24);
		panel.add(label_3);
		
		//转出卡号
		JLabel lblNewLabel_1 = new JLabel(transferMoneyBean.getChuZhangCardNo());
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(150, 10, 840, 24);
		panel.add(lblNewLabel_1);
		//转出姓名
		JLabel label_7 = new JLabel("<HTML>"+transferMoneyBean.getChuZhangcardName()+"</br></HTML>");
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_7.setBounds(150, 50, 840, 24);
		panel.add(label_7);
		//开户行
		JLabel label_8 = new JLabel("<HTML>"+transferMoneyBean.getPayHbrInstName()+"</br></HTML>");
		label_8.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_8.setBounds(150, 90, 840, 24);
		panel.add(label_8);
		
		labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(20,170,980,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 24));
		this.showJpanel.add(labelLine_1);

		panel_1 = new JPanel();
		panel_1.setBounds(15, 190, 980, 130);
		panel_1.setOpaque(isOpaque());
		this.showJpanel.add(panel_1);
		panel_1.setLayout(null);
		

		JLabel label_4 = new JLabel("转入卡号：");
		label_4.setBounds(10, 10, 130, 24);
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setFont(new Font("微软雅黑", Font.BOLD, 20));
		panel_1.add(label_4);
		JLabel label_5 = new JLabel("转入户名：");
		label_5.setBounds(10, 50, 130, 24);
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(new Font("微软雅黑", Font.BOLD, 20));
		panel_1.add(label_5);

		JLabel label_6 = new JLabel("开 户 行 ：");
		label_6.setBounds(10, 90, 130, 24);
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("微软雅黑", Font.BOLD, 20));
		panel_1.add(label_6);

//		转入卡号
		JLabel label_10 = new JLabel(transferMoneyBean.getRuZhangCardNo());
		label_10.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_10.setBounds(150, 10, 840, 24);
		panel_1.add(label_10);
		//开户名
		JLabel label_11 = new JLabel(transferMoneyBean.getRuZhangcardName());
		label_11.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_11.setBounds(150, 50, 840, 24);
		panel_1.add(label_11);
		//	转入行名
		JLabel label_14 = new JLabel(transferMoneyBean.getRecvBankName());
		label_14.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_14.setBounds(150, 90, 840, 24);
		panel_1.add(label_14);
		
		labelLine_2=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_2.setBounds(20,320,980,10);
		labelLine_2.setFont(new Font("", Font.PLAIN, 24));
		this.showJpanel.add(labelLine_2);
		
		
		label = new JLabel("汇款金额：");
		label.setBounds(25, 350, 130, 24);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("微软雅黑", Font.BOLD, 20));
		this.showJpanel.add(label);

		label_1 = new JLabel("汇款用途：");
		label_1.setBounds(25, 390, 130, 24);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
		this.showJpanel.add(label_1);
		
		//判断是个人还是单位卡的汇款用途
		String use="";
		if("0".equals(transferMoneyBean.getIsCardBank())){
			use = transferMoneyBean.getPurpos().trim();//汇款用途
		}else{
			use = transferMoneyBean.getAppdText().trim();//汇款用途
		}
		//汇款用途
		lblNewLabel_2 = new JLabel(use);
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(165, 390, 350, 26);
		this.showJpanel.add(lblNewLabel_2);
		//汇款金额
		NumberFormat fmt= NumberFormat.getCurrencyInstance();
		String cardAmt=fmt.format(Double.valueOf(transferMoneyBean.getRemitAmt()));
		
		label_12 = new JLabel(cardAmt+"元");
		label_12.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_12.setBounds(165, 350, 800, 26);
		this.showJpanel.add(label_12);
		//到账方式
		label_9 = new JLabel(transferMoneyBean.getZhangWay());
		label_9.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_9.setHorizontalTextPosition(SwingConstants.LEFT);
		label_9.setBounds(620, 390, 300, 26);
		this.showJpanel.add(label_9);
		
		label_13 = new JLabel("到账方式：");
		label_13.setFont(new Font("微软雅黑", Font.BOLD, 20));
		label_13.setHorizontalAlignment(SwingConstants.RIGHT);
		label_13.setBounds(480, 390, 130, 26);
		this.showJpanel.add(label_13);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				if("1".equals(transferMoneyBean.getTellerIsChecked())){
					//跳转回上一页面
					transferMoneyBean.setTellerIsChecked("");
					openPanel(new TransferCheckPhotos(transferMoneyBean));					
				}else if("3".equals(transferMoneyBean.getTellerIsChecked())){
					transferMoneyBean.setTellerIsChecked("2");
					openPanel(new TransferCheckPhotos(transferMoneyBean));
				}else if(!"1".equals(transferMoneyBean.getTellerIsChecked()) && !"3".equals(transferMoneyBean.getTellerIsChecked()) && Integer.parseInt(transferMoneyBean.getTransNo())>1){
					transferMoneyBean.setIsSign("");
					//跳转回转账方式确认页面
					openPanel(new TransferAccSelectPanel(transferMoneyBean));
				}else{
					transferMoneyBean.setIsSign("");
					//跳转回拍照页面
					openPanel(new TransferPrintCameraPanel(transferMoneyBean));
				}
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel backBtn = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确定按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep();
			}
		});
		backBtn.setSize(150, 50);
		backBtn.setLocation(890, 770);
		add(backBtn);
		// 返回
		JLabel utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				transferMoneyBean.setIsSign("");
				openPanel(new OutputBankCardPanel());
			}
		});
		add(utilButton);
		
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH);
		}
		if("".equals(transferMoneyBean.getIsSign())||transferMoneyBean.getIsSign()==null){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(image);
		}
		lblNewLabel_3.setBounds(115, 450, 780, 100);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.showJpanel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("".equals(transferMoneyBean.getIsSign())||transferMoneyBean.getIsSign()==null){
					logger.info("点击签字面板进行签字");
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					signView();
				}else{
					logger.info("查看签字图片");
					askLook(transferMoneyBean);
				}
				
			}
			
		});
	}
	
	//显示签字区
	public void signView(){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new SignaturePanel(transferMoneyBean));
	}
	
	//下一步
	public void nextStep(){
		if("".equals(transferMoneyBean.getIsSign())||transferMoneyBean.getIsSign()==null){
			lblNewLabel_3.setText("请点击签字区进行签字");
			on_off=true;
			return;
		}
		//清空倒计时
		clearTimeText();
		//跳转至授权页面
		openPanel(new TransferAuthorizationPanel(transferMoneyBean));
	}
	
	//点击查看签名图片
	public void askLook(final PublicAccTransferBean transferMoneyBean){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		panel.setVisible(false);
		panel_1.setVisible(false);
		label_13.setVisible(false);
		label_9.setVisible(false);
		label_12.setVisible(false);
		lblNewLabel_2.setVisible(false);
		label_1.setVisible(false);
		label.setVisible(false);
		lblNewLabel_3.setVisible(false);
		labelLine_1.setVisible(false);
		labelLine_2.setVisible(false);
		
		//创建显示签名图片的面板
		panelNew = new JPanel();
		panelNew.setBounds(140, 120, 783, 420);
		panelNew.setLayout(null);
		panelNew.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelNew.setOpaque(false);
		this.showJpanel.add(panelNew);
		panelNew.setVisible(true);
		
		
		//使图片自适应窗格大小
		image.setImage(image.getImage().getScaledInstance(780, 350, Image.SCALE_DEFAULT));
		//签名的图片
		JLabel imageLabel = new JLabel(image);
		imageLabel.setBounds(2, 1, 780, 350);
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#f7f2ff")));
		panelNew.add(imageLabel);
		
		//重新签名的按钮
		JLabel reSignButton = new JLabel(new ImageIcon("pic/reSign.png"));
		panelNew.add(reSignButton);
		reSignButton.setBounds(140, 360, 200, 50);
		reSignButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击重新签字按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				transferMoneyBean.setIsSign("");
				signView();
			}
		});
		
		//取消按钮
		JLabel lookButton = new JLabel(new ImageIcon("pic/cancel_icon.png"));
		panelNew.add(lookButton);
		lookButton.setBounds(440, 360, 200, 50);
		lookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panelNew.setVisible(false);
				panel_1.setVisible(true);
				panel.setVisible(true);
				//使图片自适应窗格大小
				image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
				lblNewLabel_3.setVisible(true);
				label_13.setVisible(true);
				label_9.setVisible(true);
				label_12.setVisible(true);
				lblNewLabel_2.setVisible(true);
				label_1.setVisible(true);
				label.setVisible(true);
				labelLine_1.setVisible(true);
				labelLine_2.setVisible(true);
				
			}
		});
		
	}
	
}
