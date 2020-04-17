package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.BJ.BillChangeOpenAuthorNoPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenCameraPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenCheckAgentPhotosPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenCheckPhotosPanel;
import com.boomhope.Bill.Util.Property;

/**
 * 换开信息确认
 * @author zjg
 *
 */
public class BillChangeOpenDepositAffirmSignaPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BillChangeOpenDepositAffirmSignaPanel.class);
	private JPanel panel1;//显示所有信息的基础面板
	private JPanel panelPic;//显示大图片
	private JLabel lblNewLabel_3;//客户签字区
	private JLabel reSignButton;//重新签名
	private JLabel lookButton ;//取消查看
	private ImageIcon imagel;//签名图片
	private JPanel panelNew;//签名图片面板
	private boolean on_off=true;//用于控制页面跳转的开关
	public BillChangeOpenDepositAffirmSignaPanel() {
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondLongTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongTime*1000,new ActionListener(){

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
            	
            }      
        });            
		voiceTimer.start();
		
		
		panel1=new JPanel();
		panel1.setBounds(0, 120, 1009, 490);
		panel1.setLayout(null);
		panel1.setOpaque(false);
		this.showJpanel.add(panel1);
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请您仔细确认以下信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 40, GlobalParameter.TRANS_WIDTH, 40);
		this.showJpanel.add(titleLabel);
		
		/* 账号信息 */
		JLabel accNoLabel = new JLabel();
		accNoLabel.setBounds(61, 30, 100, 20);
		accNoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNoLabel.setHorizontalAlignment(JLabel.LEFT);
		accNoLabel.setText("账      号");
		panel1.add(accNoLabel);
		
		JLabel accNoText = new JLabel();
		accNoText.setBounds(146, 30, 300, 20);
		accNoText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNoText.setHorizontalAlignment(JLabel.LEFT);
		accNoText.setText(":   " + bcOpenBean.getAccNo());
		panel1.add(accNoText);

		/* 户名信息 */
		JLabel accNameLabel = new JLabel();
		accNameLabel.setBounds(61, 73, 100, 20);
		accNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNameLabel.setHorizontalAlignment(JLabel.LEFT);
		accNameLabel.setText("户      名") ;
		panel1.add(accNameLabel);

		JLabel accNameText = new JLabel();
		accNameText.setBounds(146, 73, 300, 20);
		accNameText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNameText.setHorizontalAlignment(JLabel.LEFT);
		accNameText.setText(":   " + bcOpenBean.getAccName()) ;
		panel1.add(accNameText);
		
		/* 凭证号信息 */
		JLabel billNoLabel = new JLabel();
		billNoLabel.setBounds(61, 166, 100, 20);
		billNoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		billNoLabel.setHorizontalAlignment(JLabel.LEFT);
		billNoLabel.setText("凭  证 号") ;
		panel1.add(billNoLabel);
		
		JLabel billNoText = new JLabel();
		billNoText.setBounds(146, 166, 300, 20);
		billNoText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		billNoText.setHorizontalAlignment(JLabel.LEFT);
		billNoText.setText(":   " + bcOpenBean.getCertNo()) ;
		panel1.add(billNoText);
		
		/* 金额信息 */
		JLabel amountLabel = new JLabel();
		amountLabel.setBounds(61, 122, 100, 20);
		amountLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amountLabel.setHorizontalAlignment(JLabel.LEFT);
		amountLabel.setText("金      额") ;
		panel1.add(amountLabel);
		
		JLabel amountText = new JLabel();
		amountText.setBounds(146, 122, 300, 20);
		amountText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amountText.setHorizontalAlignment(JLabel.LEFT);
		amountText.setText(":   " + bcOpenBean.getTotalAmt()+"元") ;//显示存折余额
		panel1.add(amountText);
		
		/* 开户日期信息 */
		JLabel openDateLabel = new JLabel();
		openDateLabel.setBounds(61, 214, 100, 20);
		openDateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		openDateLabel.setHorizontalAlignment(JLabel.LEFT);
		openDateLabel.setText("开  户 日") ;
		panel1.add(openDateLabel);
		
		JLabel openDateText = new JLabel();
		openDateText.setBounds(146, 214, 300, 20);
		openDateText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		openDateText.setHorizontalAlignment(JLabel.LEFT);
		openDateText.setText(":   " + bcOpenBean.getOpenDate()) ;
		panel1.add(openDateText);
		
		/* 到期日信息 */
		JLabel dueDateLabel = new JLabel();
		dueDateLabel.setBounds(61, 267, 100, 20);
		dueDateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		dueDateLabel.setHorizontalAlignment(JLabel.LEFT);
		dueDateLabel.setText("\u5230  \u671F \u65E5") ;
		panel1.add(dueDateLabel);
		
		JLabel dueDateText = new JLabel();
		dueDateText.setBounds(146, 267, 300, 20);
		dueDateText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		dueDateText.setHorizontalAlignment(JLabel.LEFT);
		dueDateText.setText(":   " + bcOpenBean.getDueDate()) ;
		panel1.add(dueDateText);
		
		/* 业务类型*/
		JLabel busTypeLabel = new JLabel("业务类型");
		busTypeLabel.setBounds(61, 317, 100, 20);
		busTypeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		busTypeLabel.setHorizontalAlignment(JLabel.LEFT);
		panel1.add(busTypeLabel);
		
		JLabel busTypeText = new JLabel(":   存单换开");
		busTypeText.setBounds(146, 317, 300, 20);
		busTypeText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		busTypeText.setHorizontalAlignment(JLabel.LEFT);
		panel1.add(busTypeText);
		
		
		String picPath = bcOpenBean.getBillPath_fc();
		ImageIcon image = new ImageIcon(picPath);
		/* 存单图片 */
		image.setImage(image.getImage().getScaledInstance(420, 257, Image.SCALE_DEFAULT));
		JLabel labelpic1 = new JLabel();
		labelpic1.setBounds(547, 40, 420, 257);
		labelpic1.setIcon(image);
		panel1.add(labelpic1);
		labelpic1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showPic();
			}
		});
		
		panelPic = new JPanel();
		panelPic.setBounds(0, 120,1009 , 490);
		panelPic.setOpaque(false);
		panelPic.setLayout(null);
		panelPic.setVisible(false);
		this.showJpanel.add(panelPic);
		ImageIcon image2 = new ImageIcon(picPath);
		image2.setImage(image2.getImage().getScaledInstance(800, 450, Image.SCALE_DEFAULT));
		JLabel labelPic = new JLabel();
		labelPic.setBounds(105, 20, 800, 450);
		labelPic.setIcon(image2);
		panelPic.add(labelPic);
		labelPic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				showInfos();
			}
		});
		JLabel lblNewLabel = new JLabel("点击图片放大查看");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel.setBounds(678, 310, 165, 28);
		panel1.add(lblNewLabel);
	
		
				
		
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		imagel = new ImageIcon(Property.ACCCANCEL_SIGNATRUE_PATH);
		if(imagel != null){
			imagel.getImage().flush();
			imagel = new ImageIcon(Property.ACCCANCEL_SIGNATRUE_PATH);
		}
		if("".equals(bcOpenBean.getXHBankMsgIsSign())||bcOpenBean.getXHBankMsgIsSign()==null){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			imagel.setImage(imagel.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(imagel);
		}
		lblNewLabel_3.setBounds(115, 490, 780, 100);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("".equals(bcOpenBean.getXHBankMsgIsSign())||bcOpenBean.getXHBankMsgIsSign()==null){
					signView();
				}else{
					askLook();
				}
				
			}
			
		});
		this.showJpanel.add(lblNewLabel_3);
		
		/* 确认 */
		JLabel submitButton = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		submitButton.setBounds(890, 770, 150, 50);
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// 清空倒计时
				clearTimeText();
				//录入密码页面
				nextStep();
			}
			
		});
		add(submitButton);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.setBounds(1075, 770, 150, 50);
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// 清空倒计时
				clearTimeText();
				// 跳转回上一个页面
				backStep();
			}
		});
		add(submitBtn);		
		
		
		/* 退出 */
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				clearTimeText();
				billHKExit();
			}
		});
		add(exit);
		
	}
	/**
	 * 显示基本内容
	 */
	public void showInfos(){
		panel1.setVisible(true);
		panelPic.setVisible(false);
	}
	
	/**
	 * 显示大图片
	 */
	public void showPic(){
		panel1.setVisible(false);
		panelPic.setVisible(true);
	}
	/**
	 * 显示签字区
	 */
	public void signView(){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new BillChangeOpenSignaturePanel());
	}
	
	/**
	 * 点击查看签名图片
	 */
	public void askLook(){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		panel1.setVisible(false);
		lblNewLabel_3.setVisible(false);
		
		//创建显示签名图片的面板
		panelNew = new JPanel();
		panelNew.setBounds(115, 120, 780, 420);
		panelNew.setLayout(null);
		panelNew.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelNew.setOpaque(false);
		this.showJpanel.add(panelNew);
		panelNew.setVisible(true);
		
		
		//使图片自适应窗格大小
		imagel.setImage(imagel.getImage().getScaledInstance(780, 350, Image.SCALE_DEFAULT));
		//签名的图片
		JLabel imageLabel = new JLabel(imagel);
		imageLabel.setBounds(2, 1, 780, 350);
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.decode("#f7f2ff")));
		panelNew.add(imageLabel);
		
		//重新签名的按钮
		reSignButton = new JLabel(new ImageIcon("pic/reSign.png"));
		panelNew.add(reSignButton);
		reSignButton.setBounds(140, 360, 200, 50);
		reSignButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				bcOpenBean.setXHBankMsgIsSign("");
				signView();
			}
		});
		
		//取消按钮
		lookButton = new JLabel(new ImageIcon("pic/cancel_icon.png"));
		panelNew.add(lookButton);
		lookButton.setBounds(440, 360, 200, 50);
		lookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panelNew.setVisible(false);
				panel1.setVisible(true);
				//使图片自适应窗格大小
				imagel.setImage(imagel.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
				lblNewLabel_3.setVisible(true);
				
			}
		});
		
	}
	
	/**
	 * 确认
	 */
	public void nextStep(){
		if("".equals(bcOpenBean.getXHBankMsgIsSign())||bcOpenBean.getXHBankMsgIsSign()==null){
			lblNewLabel_3.setText("请点击签字区进行签字");
			return;
		}
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		//清空倒计时
		clearTimeText();
		
		if("1".equals(bcOpenBean.getTellerIsChecked())){
			//第二次授权检查，更改状态
			bcOpenBean.setTellerIsChecked("2");
		}
		//电子签名确认后授权
		openPanel(new BillChangeOpenAuthorNoPanel());
	
	}
		
	/**
	 * 上一步
	 */
	public void backStep(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		//清空倒计时
		clearTimeText();
		
		if("1".equals(bcOpenBean.getTellerIsChecked())){
			
			//有代理人
    		if ("1".equals(bcOpenBean.getHavAgentFlag())) {
    			
    			//进入代理人图片核查页
    			openPanel(new BillChangeOpenCheckAgentPhotosPanel());
    			
    		} else {
    			
    			//进入本人图片核查页
    			openPanel(new BillChangeOpenCheckPhotosPanel());
    			
    		}	
        		
        	return;
		}
		bcOpenBean.setXHBankMsgIsSign("");//清空签名标识
		bcOpenBean.setTellerIsChecked("");//清空授权检查状态
		
		//拍照页面
		openPanel(new BillChangeOpenCameraPanel());
			

	
	}
}
