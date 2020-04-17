package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.BJ.AccCancelAuthorNoPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelBankEMsgPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelBankSubMsgPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelBusinessPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelOutputDepositPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCameraPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCheckAgentPhotosPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCheckPhotosPanel;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;

/**
 * 销户转卡信息确认
 * @author zjg
 *
 */
public class AccCancelDepositAffirmSignaPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccCancelDepositAffirmSignaPanel.class);
	private JPanel canelpanel;
	private JPanel bankCardpanel;
	private JLabel titleLabel;//标题
	private JLabel lblNewLabel_3;//客户签字区
	private JLabel reSignButton;//重新签名
	private JLabel lookButton ;//取消查看
	private ImageIcon image;//签名图片
	private JPanel panelNew;//签名图片面板
	private JLabel label_3;//
	private JLabel label_4;//
	private JLabel label_1;//
	private JLabel label_2;//
	private JLabel label;//
	private boolean on_off=true;//用于控制页面跳转的开关
	public AccCancelDepositAffirmSignaPanel() {
		
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
		/*提示信息*/
		titleLabel = new JLabel("请仔细确认以下信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,30));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 20, GlobalParameter.TRANS_WIDTH-50, 35);
		this.showJpanel.add(titleLabel);
		
		canelpanel = new JPanel(){
			protected void paintComponent(Graphics g) {  
                super.paintComponent(g); 
                ImageIcon icon = new ImageIcon("pic/canelbillInfo.png");
                g.drawImage(icon.getImage(), 0, 0, this);  
            } 
		};
		
		canelpanel.setBounds(20, 60, 470, 420);
		canelpanel.setLayout(null);
		this.showJpanel.add(canelpanel);
		
		bankCardpanel = new JPanel(){
			protected void paintComponent(Graphics g) {  
                super.paintComponent(g); 
                ImageIcon icon = new ImageIcon("pic/careInfoIcon.png");
                g.drawImage(icon.getImage(), 0, 0, this);  
            } 
		};
		
		bankCardpanel.setBounds(520, 60, 470, 420);
		bankCardpanel.setLayout(null);
		this.showJpanel.add(bankCardpanel);
		
		JLabel card_name = new JLabel("姓           名：");
		card_name.setHorizontalAlignment(SwingConstants.CENTER);
		card_name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		card_name.setBounds(0, 41, 142, 34);
		bankCardpanel.add(card_name);
		
		JLabel card_name_value = new JLabel(accCancelBean.getCardNames().trim());
		card_name_value.setHorizontalAlignment(SwingConstants.LEFT);
		card_name_value.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		card_name_value.setBounds(139, 41, 337, 34);
		bankCardpanel.add(card_name_value);
		
		JLabel card_no = new JLabel("卡           号：");
		card_no.setHorizontalAlignment(SwingConstants.CENTER);
		card_no.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		card_no.setBounds(0, 93, 142, 34);
		bankCardpanel.add(card_no);
		
		JLabel card_no_value = new JLabel(accCancelBean.getCardNo().trim());
		card_no_value.setHorizontalAlignment(SwingConstants.LEFT);
		card_no_value.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		card_no_value.setBounds(139, 93, 337, 34);
		bankCardpanel.add(card_no_value);
		
		JLabel accName = new JLabel("姓            名：");
		accName.setBounds(0, 41, 142, 34);
		accName.setHorizontalAlignment(JLabel.CENTER);
		accName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		canelpanel.add(accName);
		
		JLabel accNameValue = new JLabel(accCancelBean.getAccName().trim());
		accNameValue.setBounds(140, 41, 337, 34);
		accNameValue.setHorizontalAlignment(JLabel.LEFT);
		accNameValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		canelpanel.add(accNameValue);
		
		JLabel accNo = new JLabel("账            号：");
		accNo.setHorizontalAlignment(SwingConstants.CENTER);
		accNo.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNo.setBounds(0, 72, 142, 34);
		canelpanel.add(accNo);
		
		JLabel accNoValue =  new JLabel(accCancelBean.getAccNo().trim());
		accNoValue.setBounds(140, 72, 337, 34);
		canelpanel.add(accNoValue);
		accNoValue.setHorizontalAlignment(SwingConstants.LEFT);
		accNoValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		JLabel pzh = new JLabel("凭     证    号：");
		pzh.setHorizontalAlignment(SwingConstants.CENTER);
		pzh.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		pzh.setBounds(0, 103, 142, 34);
		canelpanel.add(pzh);
		
		String certNo = "";
		if(accCancelBean.getBillNo()!=null && !"".equals(accCancelBean.getBillNo())){
			certNo = accCancelBean.getBillNo();
		}else{
			certNo = "无";
		}
		JLabel pzhValue = new JLabel(certNo);
		pzhValue.setHorizontalAlignment(SwingConstants.LEFT);
		pzhValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		pzhValue.setBounds(140, 103, 337, 34);
		canelpanel.add(pzhValue);
		
		JLabel proName = new JLabel("产  品  名  称：");
		proName.setHorizontalAlignment(SwingConstants.CENTER);
		proName.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		proName.setBounds(0, 134, 142, 34);
		canelpanel.add(proName);
		
		JLabel proNameValue = new JLabel(accCancelBean.getProName().trim());
		proNameValue.setHorizontalAlignment(SwingConstants.LEFT);
		proNameValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		proNameValue.setBounds(140, 134, 337, 34);
		canelpanel.add(proNameValue);
		
		JLabel amount = new JLabel("本             金：");
		amount.setHorizontalAlignment(SwingConstants.CENTER);
		amount.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amount.setBounds(0, 165, 142, 34);
		canelpanel.add(amount);
		
		JLabel amountValue = new JLabel(accCancelBean.getTotalAmt().trim()+"元");//显示存折余额
		amountValue.setHorizontalAlignment(SwingConstants.LEFT);
		amountValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amountValue.setBounds(140, 165, 337, 34);
		canelpanel.add(amountValue);
		
		JLabel interests = new JLabel("预  付  利  息：");
		interests.setHorizontalAlignment(SwingConstants.CENTER);
		interests.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		interests.setBounds(0, 196, 142, 34);
		canelpanel.add(interests);
		
		JLabel interestsValue = new JLabel(accCancelBean.getYjlx().trim()+"元");
		interestsValue.setBounds(140, 196, 337, 34);
		canelpanel.add(interestsValue);
		interestsValue.setHorizontalAlignment(SwingConstants.LEFT);
		interestsValue.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		String openDate = "";//开户日
		String startDate = "";//起息日
		String endDate = "";//到期日
		try {
			openDate = DateUtil.getDate(accCancelBean.getOpenDate(),"yyyyMMdd","yyyy年MM月dd日");
		} catch (Exception e1) {
			logger.error(e1);
			
		}
		try {
			startDate = DateUtil.getDate(accCancelBean.getStartDate(),"yyyyMMdd","yyyy年MM月dd日");
		} catch (Exception e1) {
			logger.error(e1);
		}
		try {
			endDate = DateUtil.getDate(accCancelBean.getDueDate(),"yyyyMMdd","yyyy年MM月dd日");
		} catch (Exception e1) {
			logger.error(e1);
		}
		
		JLabel account_start = new JLabel("开  户  日  期：");
		account_start.setHorizontalAlignment(SwingConstants.CENTER);
		account_start.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		account_start.setBounds(0, 227, 142, 34);
		canelpanel.add(account_start);
		
		JLabel account_start_value = new JLabel(openDate);
		account_start_value.setHorizontalAlignment(SwingConstants.LEFT);
		account_start_value.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		account_start_value.setBounds(140, 227, 337, 34);
		canelpanel.add(account_start_value);
		
		JLabel start_interests_date = new JLabel("起  息  日  期：");
		start_interests_date.setHorizontalAlignment(SwingConstants.CENTER);
		start_interests_date.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		start_interests_date.setBounds(0, 258, 142, 34);
		canelpanel.add(start_interests_date);
		
		JLabel start_interests_date_value = new JLabel(startDate);
		start_interests_date_value.setHorizontalAlignment(SwingConstants.LEFT);
		start_interests_date_value.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		start_interests_date_value.setBounds(140, 258, 337, 34);
		canelpanel.add(start_interests_date_value);
		
		JLabel end_interests_date = new JLabel("到  期  日  期：");
		end_interests_date.setHorizontalAlignment(SwingConstants.CENTER);
		end_interests_date.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		end_interests_date.setBounds(0, 289, 142, 34);
		canelpanel.add(end_interests_date);
		
		JLabel end_interests_date_value = new JLabel(endDate);
		end_interests_date_value.setHorizontalAlignment(SwingConstants.LEFT);
		end_interests_date_value.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		end_interests_date_value.setBounds(140, 289, 337, 34);
		canelpanel.add(end_interests_date_value);
		
		label_3 = new JLabel("扣  回  唐  豆：");
		canelpanel.add(label_3);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setVisible(false);
		label_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		label_4 = new JLabel();
		canelpanel.add(label_4);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setVisible(false);
		label_4.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		String name = "";
		if(accCancelBean.getProCode()!=null){
			if(accCancelBean.getProCode().startsWith("ZK")){
				name = "扣回课时券：";
			}else{
				name = "扣回已付收益：";
			}
		}
		label_1 = new JLabel(name);
		canelpanel.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setVisible(false);
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		label_2 = new JLabel();
		canelpanel.add(label_2);
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setVisible(false);
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		
		
		label = new JLabel("温馨提示：测算利息不一定等于实际利息，请以实际利息为准");
		label.setFont(new Font("微软雅黑",Font.PLAIN, 17));
		label.setForeground(Color.red);
		label.setVisible(false);
		canelpanel.add(label);
		
		
		String tdState = "";
		String xydState = "";
		if(null != accCancelBean.getShtdy() && !"".equals(accCancelBean.getShtdy().trim()) && !"0.00".equals(accCancelBean.getShtdy().trim())){
			tdState = "0";//有唐豆
		}
		if(null != accCancelBean.getAdvnInit() && !"".equals(accCancelBean.getAdvnInit().trim())){
			float advnInit = Float.parseFloat(accCancelBean.getAdvnInit().trim());
			if(0!=advnInit){//已付收益不为0
				xydState = "0";//有已付收益
			}
		}
				
		if("0".equals(tdState)){
			if("0".equals(xydState)){
				//有唐豆
				label_3.setVisible(true);
				label_3.setBounds(0, 320, 142, 34);
				label_4.setVisible(true);
				label_4.setBounds(140, 320, 337, 34);
				label_4.setText(accCancelBean.getShtdy().trim()+"元");
				
				//有已付收益
				label_1.setVisible(true);
				label_1.setBounds(0, 351, 142, 34);
				label_2.setVisible(true);
				label_2.setBounds(140, 351, 337, 34);
				label_2.setText(accCancelBean.getAdvnInit().trim()+"元");
				
				label.setVisible(true);
				label.setBounds(5, 382, 500, 34);
			}else{
				//有唐豆无已付收益
				label_3.setVisible(true);
				label_3.setBounds(0, 320, 142, 34);
				label_4.setVisible(true);
				label_4.setBounds(140, 320, 337, 34);
				label_4.setText(accCancelBean.getShtdy().trim()+"元");
				
				label.setVisible(true);
				label.setBounds(5, 351, 500, 34);
			}
		}else{
			if("0".equals(xydState)){
				//有已付收益豆无唐豆
				label_1.setVisible(true);
				label_1.setBounds(0, 320, 142, 34);
				label_2.setVisible(true);
				label_2.setBounds(140, 320, 337, 34);
				label_2.setText(accCancelBean.getAdvnInit().trim()+"元");
				
				label.setVisible(true);
				label.setBounds(5, 351, 500, 34);
			}else{
				//无已付收益无唐豆
				label.setVisible(true);
				label.setBounds(5, 320, 500, 34);
			}
		}
				
		
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.ACCCANCEL_SIGNATRUE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.ACCCANCEL_SIGNATRUE_PATH);
		}
		if("".equals(accCancelBean.getXHBankMsgIsSign())||accCancelBean.getXHBankMsgIsSign()==null){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(image);
		}
		lblNewLabel_3.setBounds(115, 490, 780, 100);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if("".equals(accCancelBean.getXHBankMsgIsSign())||accCancelBean.getXHBankMsgIsSign()==null){
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
				accCancelExit();
			}
		});
		add(exit);
		
	}
	
	/**
	 * 显示签字区
	 */
	public void signView(){
		//清空倒计时
		clearTimeText();
		//跳转到签字页面
		openPanel(new AccCancelSignaturePanel());
	}
	
	/**
	 * 点击查看签名图片
	 */
	public void askLook(){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		bankCardpanel.setVisible(false);
		canelpanel.setVisible(false);
		titleLabel.setVisible(false);
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
		image.setImage(image.getImage().getScaledInstance(780, 350, Image.SCALE_DEFAULT));
		//签名的图片
		JLabel imageLabel = new JLabel(image);
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
				accCancelBean.setXHBankMsgIsSign("");
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
				bankCardpanel.setVisible(true);
				canelpanel.setVisible(true);
				titleLabel.setVisible(true);
				//使图片自适应窗格大小
				image.setImage(image.getImage().getScaledInstance(780, 100, Image.SCALE_DEFAULT));
				lblNewLabel_3.setVisible(true);
				
			}
		});
		
	}
	
	/**
	 * 确认
	 */
	public void nextStep(){
		if("".equals(accCancelBean.getXHBankMsgIsSign())||accCancelBean.getXHBankMsgIsSign()==null){
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
		if(accCancelBean.getBillNo()!=null && !"".equals(accCancelBean.getBillNo())){
			
		}else{
			accCancelBean.setBillNo("无");
		}
		
		//存单金额
		Float money = Float.valueOf(accCancelBean.getAmount());
		//销户授权限额10万
		String amt = Property.getProperties().getProperty("acc_cancel_tellNoAmt");
		Float accCancelAmt = Float.valueOf(amt);
		
		if(money>=accCancelAmt){
			
			if("1".equals(accCancelBean.getTellerIsChecked())){
				//第二次授权检查，更改状态
				accCancelBean.setTellerIsChecked("2");
			}
			//电子签名确认后授权
			openPanel(new AccCancelAuthorNoPanel());
			
		}else{
			
			//不授权直接进入销户业务处理页 
			openPanel(new AccCancelBusinessPanel());	
		}
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
		
		if("1".equals(accCancelBean.getTellerIsChecked())){
		
			//有代理人
    		if ("1".equals(accCancelBean.getHavAgentFlag())) {
    			
    			//进入代理人图片核查页
    			openPanel(new AccCancelCheckAgentPhotosPanel());
    			
    		} else {
    			
    			//进入本人图片核查页
    			openPanel(new AccCancelCheckPhotosPanel());
    			
    		}	
        		
        	return;
		}
		accCancelBean.setXHBankMsgIsSign("");//清空签名标识
		accCancelBean.setTellerIsChecked("");//清空授权检查状态
		
		//跳转
		if("0".equals(accCancelBean.getHaveAcc())){//有存单
			if("1".equals(accCancelBean.getJudgeState()) || "1".equals(accCancelBean.getAmtState())){//提前或金额大于5万
				
				if("002".equals(accCancelBean.getAccCancelType())){
					
					//返回
					openPanel(new AccCancelCameraPanel());
					
				}else{
					
					//判断是否卡下子账户
					if("0".equals(accCancelBean.getSubAccNoCancel())){
						
						//返回
						openPanel(new AccCancelCameraPanel());
						
					}else{
						
						logger.info("该存单为普通账户");
						//返回
						if("1".equals(accCancelBean.getInCardOrHandCard())){
							//退卡
							try {
								quitBankCard();
							} catch (Exception e1) {
								logger.error("退出银行卡失败"+e1);
							}
							GlobalParameter.CARD_STATUS=false;
							openPanel(new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							
						}else{
							openPanel(new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
						}
						
					}
				}
				
			}else{//简易流程
				
				if("002".equals(accCancelBean.getAccCancelType())){
					
					//返回
					openPanel(new AccCancelCheckBillPanel());
					
				}else{
					
					//判断是否卡下子账户
					if("0".equals(accCancelBean.getSubAccNoCancel())){
						
						//返回
						openPanel(new AccCancelCheckBillPanel());
						
					}else{
						
						logger.info("该存单为普通账户");
						//返回
						if("1".equals(accCancelBean.getInCardOrHandCard())){
							//退卡
							try {
								quitBankCard();
							} catch (Exception e1) {
								logger.error("退出银行卡失败"+e1);
							}
							GlobalParameter.CARD_STATUS=false;
							openPanel(new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							
						}else{
							openPanel(new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
						}
						
					}
				}
				
			}
			
		}else{//无存单
			if("1".equals(accCancelBean.getJudgeState()) || "1".equals(accCancelBean.getAmtState())){//提前或金额大于5万
				
				openPanel(new AccCancelCameraPanel());
						
				
			}else{//简易流程
				
				if("002".equals(accCancelBean.getAccCancelType())){
					
					//返回电子账户信息页
					openPanel(new AccCancelBankEMsgPanel());
					
				}else{
					
					//判断是否卡下子账户
					if("0".equals(accCancelBean.getSubAccNoCancel())){
						
						//返回卡下子账户信息页
						openPanel(new AccCancelBankSubMsgPanel());
						
					}
				}
			}
		}
	}
}
