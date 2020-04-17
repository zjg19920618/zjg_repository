package com.boomhope.Bill.TransService.BillAddPwd.ServicePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelDeputySelectionPanel;
import com.boomhope.Bill.Util.Property;
/**
 * 核对存单信息页面
 * @author hp
 *
 */
public class SetPwdCheckBillPanel  extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(SetPwdCheckBillPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	private JPanel panel1;//显示所有信息的基础面板
	private JPanel panelPic;//显示大图片

	/***
	 * 初始化
	 */
	public SetPwdCheckBillPanel(){
              logger.info("存单信息核对页面");
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

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
            	closeVoice();//关闭语音流
            	excuteVoice("voice/check.wav");
            }      
        });            
		voiceTimer.start();
		
		panel1=new JPanel();
		panel1.setBounds(0, 120, 1009, 490);
		panel1.setLayout(null);
		panel1.setOpaque(false);
		this.showJpanel.add(panel1);
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请核对您的存单");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 80, GlobalParameter.TRANS_WIDTH, 40);
		this.showJpanel.add(titleLabel);
		
		/* 账号信息 */
		JLabel accNoLabel = new JLabel();
		accNoLabel.setBounds(61, 60, 100, 20);
		accNoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNoLabel.setHorizontalAlignment(JLabel.LEFT);
		accNoLabel.setText("账      号");
		panel1.add(accNoLabel);
		
		JLabel accNoText = new JLabel();
		accNoText.setBounds(146, 60, 300, 20);
		accNoText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNoText.setHorizontalAlignment(JLabel.LEFT);
		accNoText.setText(":   " + addPwdBean.getAccNo());
		panel1.add(accNoText);

		/* 户名信息 */
		JLabel accNameLabel = new JLabel();
		accNameLabel.setBounds(61, 103, 100, 20);
		accNameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNameLabel.setHorizontalAlignment(JLabel.LEFT);
		accNameLabel.setText("户      名") ;
		panel1.add(accNameLabel);

		JLabel accNameText = new JLabel();
		accNameText.setBounds(146, 103, 300, 20);
		accNameText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		accNameText.setHorizontalAlignment(JLabel.LEFT);
		accNameText.setText(":   " + addPwdBean.getIdName()) ;
		panel1.add(accNameText);
		
		/* 凭证号信息 */
		JLabel billNoLabel = new JLabel();
		billNoLabel.setBounds(61, 196, 100, 20);
		billNoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		billNoLabel.setHorizontalAlignment(JLabel.LEFT);
		billNoLabel.setText("凭  证 号") ;
		panel1.add(billNoLabel);
		
		JLabel billNoText = new JLabel();
		billNoText.setBounds(146, 196, 300, 20);
		billNoText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		billNoText.setHorizontalAlignment(JLabel.LEFT);
		billNoText.setText(":   " + addPwdBean.getCertNo()) ;
		panel1.add(billNoText);
		
		/* 金额信息 */
		JLabel amountLabel = new JLabel();
		amountLabel.setBounds(61, 152, 100, 20);
		amountLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amountLabel.setHorizontalAlignment(JLabel.LEFT);
		amountLabel.setText("金      额") ;
		panel1.add(amountLabel);
		
		JLabel amountText = new JLabel();
		amountText.setBounds(146, 152, 300, 20);
		amountText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		amountText.setHorizontalAlignment(JLabel.LEFT);
		amountText.setText(":   " + addPwdBean.getTotalAmt()+"元") ;//显示存折余额
		panel1.add(amountText);
		
		/* 开户日期信息 */
		JLabel openDateLabel = new JLabel();
		openDateLabel.setBounds(61, 244, 100, 20);
		openDateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		openDateLabel.setHorizontalAlignment(JLabel.LEFT);
		openDateLabel.setText("开  户 日") ;
		panel1.add(openDateLabel);
		
		JLabel openDateText = new JLabel();
		openDateText.setBounds(146, 244, 300, 20);
		openDateText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		openDateText.setHorizontalAlignment(JLabel.LEFT);
		openDateText.setText(":   " + addPwdBean.getOpenDate()) ;
		panel1.add(openDateText);
		
		/* 到期日信息 */
		JLabel dueDateLabel = new JLabel();
		dueDateLabel.setBounds(61, 297, 100, 20);
		dueDateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		dueDateLabel.setHorizontalAlignment(JLabel.LEFT);
		dueDateLabel.setText("\u5230  \u671F \u65E5") ;
		panel1.add(dueDateLabel);
		
		JLabel dueDateText = new JLabel();
		dueDateText.setBounds(146, 297, 300, 20);
		dueDateText.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		dueDateText.setHorizontalAlignment(JLabel.LEFT);
		dueDateText.setText(":   " + addPwdBean.getDueDate()) ;
		panel1.add(dueDateText);
		
		String picPath = addPwdBean.getBillPath_fc();
		ImageIcon image = new ImageIcon(picPath);
		/* 存单图片 */
		image.setImage(image.getImage().getScaledInstance(420, 257, Image.SCALE_DEFAULT));
		JLabel labelpic1 = new JLabel();
		labelpic1.setBounds(547, 60, 420, 257);
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
		lblNewLabel.setBounds(678, 362, 165, 28);
		panel1.add(lblNewLabel);
		
		//确认
		JLabel okButton = new JLabel();
		okButton.setIcon(new ImageIcon("pic/newPic/confirm.png"));
		okButton.setBounds(1075, 770, 150, 50);
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//确认
				nextTrans();
			}
		});
		add(okButton);		
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				accCancelExit();
			}
		});		
		add(label_1);
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
	 * 下一步
	 */
	private void nextTrans(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		
		          //存单金额
				Float money = Float.valueOf(addPwdBean.getAmount());
				
				//增设密码鉴伪不过限额10万
				String amt = Property.getProperties().getProperty("acc_zm_amt");
				Float addPwdAmt = Float.valueOf(amt);

				if("2".equals(addPwdBean.getCheckStatus())){//鉴伪不通过
					if(money>=addPwdAmt){//金额大于等于10万
						
						if("2".equals(addPwdBean.getTellNoState())){
							
							logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万:"+money+"元，二次都已授权，继续增设密码流程");
							
						}else{
							
							//进入提示页
							logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万:"+money+"元，先第一次授权、再第二次授权，进入提示页，重新插入存单");
							clearTimeText();//清空倒计时
							
							openPanel(new SetPwdAmtOverrunPanel("金额超限，请双人对存单真伪进行鉴别"));
							return;
						}
						
					}else{//金额小于10万
						
						if("1".equals(addPwdBean.getTellNoState())){
							
							logger.info("第一次鉴伪不通过，手动输入，金额小于10万:"+money+"元，已授权，继续增设密码流程");
							
						}else{
							
							//进入提示页
							logger.info("第一次鉴伪不通过，手动输入，金额小于10万:"+money+"元，进行一次授权，进入提示页，重新插入存单");
							clearTimeText();//清空倒计时
							
							openPanel(new SetPwdAmtOverrunPanel("鉴伪或OCR识别失败，请手工对存单真伪进行鉴别"));
							return;
						}
					
				     }	
		          }	
		logger.info("鉴伪识别成功");
		clearTimeText();//清空倒计时
		stopTimer(voiceTimer);//关闭语音
		closeVoice();//关闭语音流                                                
		//识别本人身份证
		openPanel(new SetPwdInputIdCardPanel());
				
	}
}
