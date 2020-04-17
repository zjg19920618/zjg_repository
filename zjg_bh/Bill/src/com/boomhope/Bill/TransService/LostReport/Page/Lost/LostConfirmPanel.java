package com.boomhope.Bill.TransService.LostReport.Page.Lost;

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
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicAuthorNoPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicSignaturePanel;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.Util.Property;

/**
 * 存单确认信息页面
 * 
 * @author hk 2017-3-15
 */
@SuppressWarnings("static-access")
public class LostConfirmPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());

	private JLabel lblNewLabel_3;//显示图片的框
	private ImageIcon image;//加载签字的图片
	private JPanel panelNew;//加载显示图片的面板
	private boolean on_off=true;//用于控制页面跳转的开关
	private JPanel jPanel;
	private JLabel jLabel;
	
	public LostConfirmPanel() {
		logger.info("挂失确认信息页面。。。");
		lostPubBean.setThisComponent(this);
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("在意确认信息页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	delayTimer.stop();
            	serverStop("温馨提示：服务已超时，请结束您的交易 ！","","");
            }      
        });            
		delayTimer.start();
		
		//显示挂失账户信息
		showMsg();
		
		//加载签名的图片
		lblNewLabel_3=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH);
		}
		if(!"1".equals(lostPubBean.getAllPubIsSign()) && !"2".equals(lostPubBean.getAllPubIsSign())){
			lblNewLabel_3.setText("客户签字区");
			lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			lblNewLabel_3.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(780, 200, Image.SCALE_DEFAULT));
			lblNewLabel_3.setIcon(image);
		}
		lblNewLabel_3.setBounds(115, 390, 780, 195);
		lblNewLabel_3.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.showJpanel.add(lblNewLabel_3);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!"1".equals(lostPubBean.getAllPubIsSign()) && !"2".equals(lostPubBean.getAllPubIsSign())){
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					signView();
				}else{
					askLook();
				}
				
			}
			
		});
		
		// 上一步按钮
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
				OpenDeposit();
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
				if("客户签字区".equals(lblNewLabel_3.getText()) || "请先签字再确认".equals(lblNewLabel_3.getText())){
					lblNewLabel_3.setText("请先签字再确认");
					on_off=true;
					return;
				}
				logger.info("确认存单信息后是否打印存单");
				nextStep();
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
				//清空倒计时和语音
				clearTimeText();
				exit();
			}
		});
		add(backButton);
		
	}
	/**
	 * 确定后下一步
	 * @param transBean
	 * @param comp
	 * @param params
	 */
	private void nextStep() {
		lostPubBean.setUpStepName("LOST_CONFIRM_PANEL");
		lostPubBean.setNextStepName("ACTION_CHECK_AUTHOR_METHOD");
		if(lostPubBean.getAllPubIsCheckAuthor()!=null && "1".equals(lostPubBean.getAllPubIsCheckAuthor())){
			lostPubBean.setAllPubIsCheckAuthor("2");
		}
		openPanel(new AllPublicAuthorNoPanel());
	}
	/**
	 * 上一步按钮，返回到整存整取页面,需要保留数据交易码和返回码
	 */
	private void OpenDeposit() {
		// 跳转
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				clearTimeText();
				logger.info("返回上一步：是否为授权程序："+lostPubBean.getAllPubIsCheckAuthor());
				//若为客户签字时，则返回上一步为信息展示页面
				if(lostPubBean.getAllPubIsCheckAuthor()==null || "0".equals(lostPubBean.getAllPubIsCheckAuthor()) || "".equals(lostPubBean.getAllPubIsCheckAuthor())){
					lostPubBean.setAllPubIsSign("");
					//判断是否为本人
					if(lostPubBean.getAllPubIsDeputy()!=null && "0".equals(lostPubBean.getAllPubIsDeputy())){
						//判断是否为补领
						if(lostPubBean.getLostOrSolve()!=null && "1".equals(lostPubBean.getLostOrSolve())){

							    //返回是否补领选择页面
							    openPanel(new LostCheckIsReceivePage());
							
						//如果是挂失销户页面
						}else if(lostPubBean.getLostOrSolve()!=null && "2".equals(lostPubBean.getLostOrSolve())){
								String result= ((ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg")).getCardOrAccOrAcc1();
								//如果挂失销户转入电子账户
								if("1_1".equals(result)||"1_2".equals(result)){
									openPanel(new LostGetCardOrEcardPage());
									//如果挂失销户转入卡下
								}else{
									openPanel(new LostCancelAccCardPage());
								}

						}else{
							
							if("0".equals(lostPubBean.getYseNoPass())){
								
								if("0".equals(lostPubBean.getRecOrCan())){
							    	//账户状态异常只支持挂失跳转   返回选择账户页面
									if(!"".equals(lostPubBean.getStateName())){
										lostPubBean.setStateName("");
										openPanel(new LostShowAccNoPage());
									}else{
										//返回不补发确认页面
										openPanel(new LostCheckNoReceivePage());
									}
								}else{
								
									lostPubBean.setLostOrSolve("2");
									if("0".equals(lostPubBean.getIsCan())){									
										//返回是否销户选择页面
										openPanel(new LostCheckIsCancelPage());									
									}else{
										//返回挂失账户选择页面
										openPanel(new LostShowAccNoPage());
									}
								}
							}else{
								//返回挂失账户选择页面
								openPanel(new LostShowAccNoPage());
							}
						}
					}else{
						//若为代理人，则返回输入账号页面
						lostPubBean.setAllPubAccPwd("");
						openPanel(new LostEnterAccNoMsgPage());
					}
					return;
				//若是授权柜员正在核查签字时
				}else if("1".equals(lostPubBean.getAllPubIsCheckAuthor())){
					logger.info("授权过程：是否为代理人标志："+lostPubBean.getAllPubIsDeputy());
					if("0".equals(lostPubBean.getAllPubIsDeputy())){//若是本人办理
						lostPubBean.setNextStepName("ALL_PUBLIC_CHECK_PHOTOS_PANLE");
						allPubTransFlow.transFlow();
						return;
					}else{
						lostPubBean.setNextStepName("ALL_PUBLIC_CHECK_AGENT_PHOTOS_PANLE");
						allPubTransFlow.transFlow();
						return;
					}
				}
			}
		});
	}
	
	public void showMsg(){
		
		ShowAccNoMsgBean accMsg = (ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg");
		
		jPanel = new JPanel();
		if("0".equals(lostPubBean.getAllPubIsDeputy())){//本人
			jPanel.setBounds(10, 50, 989, 300);
		}else{//代理人
			jPanel.setBounds(10, 28, 989, 345);
		}
		jPanel.setLayout(null);
		jPanel.setOpaque(false);
		
		jLabel = new JLabel();
		ImageIcon image = null;
		if("0".equals(lostPubBean.getAllPubIsDeputy())){//本人
			
			jLabel.setLocation(10, 50);
			image = new ImageIcon("pic/newPic/mebeijing.png");
			image.setImage(image.getImage().getScaledInstance(989, 300,Image.SCALE_DEFAULT ));
			jLabel.setIcon(image);
			jLabel.setSize(989, 300);
			
		}else{//代理人
			jLabel.setLocation(10, 28);
			image = new ImageIcon("pic/newPic/agentbeijing.png");
			image.setImage(image.getImage().getScaledInstance(989, 345,Image.SCALE_DEFAULT ));
			jLabel.setIcon(image);
			jLabel.setSize(989, 345);
		}
		
		this.showJpanel.add(jPanel);
		this.showJpanel.add(jLabel);
		
		//标题
		JLabel biaoTi = new JLabel();
		biaoTi.setBounds(10, 10, 950, 40);
		biaoTi.setFont(new Font("微软雅黑",Font.PLAIN,30));
		biaoTi.setHorizontalAlignment(SwingUtilities.CENTER);
		jPanel.add(biaoTi);
		
		String biaoName = "";
		if("0".equals(lostPubBean.getYseNoPass())){//知道密码
			
			if("0".equals(lostPubBean.getLostOrSolve())){
				biaoName = "挂失申请";
			}else if("1".equals(lostPubBean.getLostOrSolve())){
				biaoName = "挂失补发申请";
			}else if("2".equals(lostPubBean.getLostOrSolve())){
				biaoName = "挂失销户申请";
			}
			
		}else{//忘记密码
			biaoName = "挂失申请";
		}
		biaoTi.setText(biaoName);		
		
		//卡/账号
		String name ="";
		if("0".equals(accMsg.getCardOrAccOrAcc1())){
			name = "卡       号：";
		}else{
			name = "账       号：";
		}
		JLabel label = new JLabel(name.trim());
		label.setBounds(10, 65, 300, 40);
		label.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label);
		
		//卡/账号(值)
		String accNo ="";
		if("0".equals(accMsg.getCardOrAccOrAcc1())){
			accNo = accMsg.getCardNo();
		}else if ("2".equals(accMsg.getCardOrAccOrAcc1())){
			accNo = accMsg.getAccNo1();
		}else{
			accNo = accMsg.getAccNo();
		}
		JLabel label1 = new JLabel(accNo.trim());
		label1.setBounds(110, 65, 300, 40);
		label1.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label1.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label1);
		
		//证件类型
		JLabel label2 = new JLabel("证件类型：");
		label2.setBounds(10, 110, 300, 40);
		label2.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label2.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label2);
		
		//证件类型(值)
		JLabel label22 = new JLabel("身  份  证");
		label22.setBounds(110, 110, 300, 40);
		label22.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label22.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label22);
		
		//开户日期
		JLabel label3 = new JLabel("开户日期：");
		label3.setBounds(10, 155, 300, 40);
		label3.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label3.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label3);
		
		//开户日期(值)
		JLabel label33 = new JLabel(accMsg.getOpenDate());
		label33.setBounds(110, 155, 300, 40);
		label33.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label33.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label33);
		
		//挂失方式
		JLabel label4 = new JLabel("挂失方式：");
		label4.setBounds(10, 200, 300, 40);
		label4.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label4.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label4);
		
		//挂失方式(值)
		JLabel label44 = new JLabel("正式挂失");
		label44.setBounds(110, 200, 300, 40);
		label44.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label44.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label44);
		
		//挂失日期
		JLabel label5 = new JLabel("挂失日期：");
		label5.setBounds(10, 245, 300, 40);
		label5.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label5.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label5);
		
		//挂失日期(值)
		JLabel label55 = new JLabel(lostPubBean.getAllPubSvrDate().replaceAll("/", "").trim());
		label55.setBounds(110, 245, 300, 40);
		label55.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label55.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label55);
		
		//客户名称
		JLabel label6 = new JLabel("客户名称：");
		label6.setBounds(450, 65, 300, 40);
		label6.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label6.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label6);
		
		//客户名称(值)
		JLabel label66 = new JLabel(accMsg.getCustName());
		label66.setBounds(550, 65, 300, 40);
		label66.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label66.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label66);
		
		//证件号码
		JLabel label7 = new JLabel("证件号码：");
		label7.setBounds(450, 110, 300, 40);
		label7.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label7.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label7);
		
		//证件号码(值)
		JLabel label77 = new JLabel(lostPubBean.getAllPubIDNo());
		label77.setBounds(550, 110, 300, 40);
		label77.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label77.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label77);
		
		//凭证号
		JLabel label8 = new JLabel("凭证号码：");
		label8.setBounds(450, 155, 300, 40);
		label8.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label8.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label8);
		
		//凭证号(值)
		JLabel label88 = new JLabel(accMsg.getCertNo());
		label88.setBounds(550, 155, 300, 40);
		label88.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label88.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label88);
		
		//挂失种类
		JLabel label9 = new JLabel("挂失种类：");
		label9.setBounds(450, 200, 300, 40);
		label9.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label9.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label9);
		
		String kingName = "";
		if("0".equals(lostPubBean.getYseNoPass())){
			
			if("0".equals(lostPubBean.getLostType())){
				kingName = "银行卡";
			}else if("1".equals(lostPubBean.getLostType())){
				kingName = "存单";
			}else if("2".equals(lostPubBean.getLostType())){
				kingName = "存折";
			}else{
				
			}
		}else{
			
			if("0".equals(accMsg.getCardOrAccOrAcc1())){
				if("N".equals(accMsg.getCardState())){
					kingName = "银行卡";
				}else{
					kingName = "银行卡+密码";					
				}							
			}else if("1".equals(accMsg.getCardOrAccOrAcc1()) || "1_1".equals(accMsg.getCardOrAccOrAcc1()) || "1_2".equals(accMsg.getCardOrAccOrAcc1())){				
				if("1".equals(accMsg.getDrawCond())){
					kingName = "存单+密码";
				}else{
					kingName = "存单";
				}
			}else if("2".equals(accMsg.getCardOrAccOrAcc1())){				
				if("1".equals(accMsg.getDrawCond())){
					kingName = "存折+密码";
				}else{
					kingName = "存折";
				}
			}else{
				
			}
		}
		//挂失种类(值)
		JLabel label99 = new JLabel(kingName);
		label99.setBounds(550, 200, 300, 40);
		label99.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label99.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(label99);
		
		if("0".equals(lostPubBean.getYseNoPass())){//知道密码
			
			if("2".equals(lostPubBean.getLostOrSolve())){//挂失销户
				//转入卡号
				JLabel label10 = new JLabel("转入卡号：");
				label10.setBounds(450, 245, 300, 40);
				label10.setFont(new Font("微软雅黑",Font.PLAIN,20));
				label10.setHorizontalAlignment(SwingUtilities.LEFT);
				jPanel.add(label10);
				
				//转入卡号
				JLabel label1010 = new JLabel(lostPubBean.getSelectCardNo());
				label1010.setBounds(550, 245, 300, 40);
				label1010.setFont(new Font("微软雅黑",Font.PLAIN,20));
				label1010.setHorizontalAlignment(SwingUtilities.LEFT);
				jPanel.add(label1010);
			}
		}
		
		if("1".equals(lostPubBean.getAllPubIsDeputy())){//代理人
			//代理人姓名
			JLabel label10 = new JLabel("代理人姓名：");
			label10.setBounds(450, 245, 300, 40);
			label10.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label10.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label10);
			
			//代理人姓名(值)
			JLabel label1010 = new JLabel(lostPubBean.getAllPubAgentIdCardName());
			label1010.setBounds(570, 245, 300, 40);
			label1010.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1010.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label1010);
			
			//代理人证件类型
			JLabel label11 = new JLabel("代理人证件类型：");
			label11.setBounds(10, 290, 300, 40);
			label11.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label11.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label11);
			
			//代理人证件类型(值)
			JLabel label1111 = new JLabel("身份证");
			label1111.setBounds(170, 290, 300, 40);
			label1111.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1111.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label1111);
			
			//代理人证件号码
			JLabel label12 = new JLabel("代理人证件号码：");
			label12.setBounds(450, 290, 300, 40);
			label12.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label12.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label12);
			
			//代理人证件号码(值)
			JLabel label1212 = new JLabel(lostPubBean.getAllPubAgentIDNo());
			label1212.setBounds(610, 290, 300, 40);
			label1212.setFont(new Font("微软雅黑",Font.PLAIN,20));
			label1212.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(label1212);
		}
	}

	//点击查看签名图片
	public void askLook(){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		jPanel.setVisible(false);
		lblNewLabel_3.setVisible(false);
		jLabel.setVisible(false);
			
		//创建显示签名图片的面板
		panelNew = new JPanel();
		panelNew.setBounds(103, 120, 783, 420);
		panelNew.setLayout(null);
		panelNew.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
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
		reSignButton.setBounds(0, 360, 200, 50);
		reSignButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lostPubBean.setAllPubIsSign("");
				signView();
			}
		});
		
		//取消按钮
		JLabel lookButton = new JLabel(new ImageIcon("pic/cancel_icon.png"));
		panelNew.add(lookButton);
		lookButton.setBounds(580, 360, 200, 50);
		lookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				panelNew.setVisible(false);
				//使图片自适应窗格大小
				image.setImage(image.getImage().getScaledInstance(780, 200, Image.SCALE_DEFAULT));
				lblNewLabel_3.setVisible(true);
				jPanel.setVisible(true);
				jLabel.setVisible(true);
			}
		});
	}
	
	//显示签字区
	public void signView(){
		//清空倒计时
		clearTimeText();
		lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
		//跳转到签字页面
		openPanel(new AllPublicSignaturePanel());
	}
	/**
	 * 退出交易
	 */
	public void exit() {

		returnHome();
	}
}
