package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicAuthorNoPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicSignaturePanel;
import com.boomhope.Bill.TransService.LostReport.Bean.LostCheckBean;
import com.boomhope.Bill.Util.Property;

/**
 * Title:解挂申请确认页面
 *
 * @author: hk
 * 
 * @date: 2018年4月13日 上午11:44:37  
 * 
 */
@SuppressWarnings("static-access")
public class SolveLostApplNoInfosConfirmPage extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(SolveLostApplNoInfosConfirmPage.class);
	private static final long serialVersionUID = 1L;
	
	private JLabel labelSign;//显示图片的框
	private ImageIcon image;//加载签字的图片
	private JPanel panelNew;//加载显示图片的面板
	private JPanel jPanel;
	private JLabel jLabel;
	private int y = 55;//显示信息起始高度
	private int y1 = 22;//显示信息起始高度
	
	private boolean on_off=true;
	
	public SolveLostApplNoInfosConfirmPage(){
		logger.info("进入挂失申请书信息确认页面");
		baseTransBean.setThisComponent(this);
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("挂失申请书信息确认页面倒计时结束");
						/* 倒计时结束退出交易 */ 
		            	clearTimeText();
		            	serverStop("温馨提示：服务已超时，请结束您的交易 ！","","");
					}
				});
		delayTimer.start();
		
		
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
				if("客户签字区".equals(labelSign.getText()) || "请先签字再确认".equals(labelSign.getText())){
					labelSign.setText("请先签字再确认");
					on_off=true;
					return;
				}
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
				returnHome();
			}
		});
		add(backButton);
		
		//显示挂失申请书信息
		showInfos();
		
		//加载签名的图片
		labelSign=new JLabel();
		image = new ImageIcon(Property.SIGNATURE_PATH);
		if(image != null){
			image.getImage().flush();
			image = new ImageIcon(Property.SIGNATURE_PATH);
		}
		if(!"1".equals(lostPubBean.getAllPubIsSign()) && !"2".equals(lostPubBean.getAllPubIsSign())){
			labelSign.setText("客户签字区");
			labelSign.setHorizontalAlignment(SwingConstants.CENTER);
			labelSign.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			labelSign.setForeground(Color.blue);
		}else{
			//使图片自适应窗格大小
			image.setImage(image.getImage().getScaledInstance(780, 185, Image.SCALE_DEFAULT));
			labelSign.setIcon(image);
		}
		labelSign.setBounds(115, 410, 780, 185);
		labelSign.setBorder(BorderFactory.createLineBorder(Color.blue));
		this.showJpanel.add(labelSign);
		labelSign.addMouseListener(new MouseAdapter() {
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
	}
	
	/**
	 * @Description:显示所有信息 
	 * @Author: hk
	 * @date 2018年4月13日 下午12:27:21
	 */
	public void showInfos(){
		logger.info("加载挂失申请书号的信息");
		//获取查询到的申请书信息
		LostCheckBean bean = lostPubBean.getApplInfos();
		
		jPanel = new JPanel();
		if(bean.getId_AgentName()==null || "".equals(bean.getId_AgentName().trim())){//本人
			jPanel.setBounds(10, 40, 989, 320);
		}else{//代理人
			jPanel.setBounds(10, 28, 989, 363);
		}
		jPanel.setLayout(null);
		jPanel.setOpaque(false);
		
		jLabel = new JLabel();
		ImageIcon image = null;
		if(bean.getId_AgentName()==null || "".equals(bean.getId_AgentName().trim())){//本人
			y=90;
			y1=57;
			jLabel.setLocation(10, 50);
			image = new ImageIcon("pic/newPic/mebeijing.png");
			image.setImage(image.getImage().getScaledInstance(989, 320,Image.SCALE_DEFAULT ));
			jLabel.setIcon(image);
			jLabel.setSize(989, 320);
			
		}else{//代理人
			jLabel.setLocation(10, 28);
			image = new ImageIcon("pic/newPic/agentbeijing.png");
			image.setImage(image.getImage().getScaledInstance(989, 363,Image.SCALE_DEFAULT ));
			jLabel.setIcon(image);
			jLabel.setSize(989, 363);
		}
		
		this.showJpanel.add(jPanel);
		this.showJpanel.add(jLabel);
		
		//标题
		JLabel biaoTi = new JLabel();
		biaoTi.setBounds(10, y-53, 950, 40);
		biaoTi.setFont(new Font("微软雅黑",Font.PLAIN,30));
		biaoTi.setHorizontalAlignment(SwingUtilities.CENTER);
		jPanel.add(biaoTi);
		
		String biaoName = "解挂补发";
		
		if("3".equals(lostPubBean.getLostOrSolve())){
			biaoName = "解挂补发";
		}else if("4".equals(lostPubBean.getLostOrSolve())){
			biaoName = "解挂销户";
		}else if("5".equals(lostPubBean.getLostOrSolve())){
			biaoName = "挂失撤销";
		}
		biaoTi.setText(biaoName);			
		
		//账号
		JLabel labelAccNo = new JLabel("账(卡)        号："+lostPubBean.getAllPubAccNo());
		labelAccNo.setBounds(50, y,800 , 25);
		labelAccNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelAccNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelAccNo);
		
		//挂失申请书号
		JLabel labelApplNo = new JLabel("挂失申请书号："+bean.getLostApplNo());
		labelApplNo.setBounds(50, y+=33, 800, 25);
		labelApplNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelApplNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelApplNo);
		
		//客户名称
		JLabel labelName = new JLabel("客  户  名   称："+bean.getCust_name());
		labelName.setBounds(50, y+=33, 800, 25);
		labelName.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelName.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelName);
		
		//挂失人证件类型
		JLabel labelLostCerType = new JLabel("证  件  类   型：身份证");
		labelLostCerType.setBounds(50, y+=33, 800, 25);
		labelLostCerType.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelLostCerType.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelLostCerType);
		
		//挂失人证件号码
		JLabel labelLostCerNo = new JLabel("证  件  号   码："+bean.getId_no());
		labelLostCerNo.setBounds(50, y+=33, 800, 25);
		labelLostCerNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelLostCerNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelLostCerNo);
		
		/**
		 * 如果没有代理人信息，则不用显示
		 */
		if(bean.getId_AgentName()!=null && !"".equals(bean.getId_AgentName().trim())){
			//代理人名称
			JLabel labelAgentName = new JLabel("代  理  人 名  称："+bean.getId_AgentName());
			labelAgentName.setBounds(50, y+=33, 800, 25);
			labelAgentName.setFont(new Font("微软雅黑",Font.PLAIN,20));
			labelAgentName.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(labelAgentName);
			
			//代理人证件类型
			JLabel labelAgentCerType = new JLabel("代理人证件类型：身份证");
			labelAgentCerType.setBounds(50, y+=33, 800, 25);
			labelAgentCerType.setFont(new Font("微软雅黑",Font.PLAIN,20));
			labelAgentCerType.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(labelAgentCerType);
			
			//代理人证件号码
			JLabel labelAgentCerNo = new JLabel("代理人证件号码："+bean.getId_AgentNo());
			labelAgentCerNo.setBounds(50, y+=33, 800, 25);
			labelAgentCerNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
			labelAgentCerNo.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(labelAgentCerNo);
		}
		
		//如果是挂失补发，则显示凭证号
		if("3".equals(lostPubBean.getLostOrSolve())){
			JLabel labelCertNo = new JLabel("凭  证  号："+bean.getCert_no());
			labelCertNo.setBounds(559, y1+=33, 400, 25);
			labelCertNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
			labelCertNo.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(labelCertNo);
		}
		
		//挂失日期
		JLabel labelDate = new JLabel("挂失日期："+bean.getLostApplNo().substring(5,13));
		labelDate.setBounds(559, y1+=33, 400, 25);
		labelDate.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelDate.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelDate);
		
		//挂失方式
		JLabel labelLostMethod = new JLabel("挂失方式："+"正式挂失");
		labelLostMethod.setBounds(559, y1+=33, 450, 25);
		labelLostMethod.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelLostMethod.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelLostMethod);
		
		//挂失种类
		JLabel labelLostType = new JLabel("挂失种类："+getLostType(bean.getLostType()));
		labelLostType.setBounds(559, y1+=33, 400, 25);
		labelLostType.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelLostType.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelLostType);
		
		//币种 
		JLabel labelCurrecy = new JLabel("币       种："+"人民币");
		labelCurrecy.setBounds(559, y1+=33, 450, 25);
		labelCurrecy.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelCurrecy.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelCurrecy);
		
		//结存额
		JLabel labelbanlance = new JLabel("结  存  额："+lostPubBean.getEndAmt());
		labelbanlance.setBounds(559, y1+=33, 400, 25);
		labelbanlance.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelbanlance.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelbanlance);
		
		//支取方式
		JLabel labelDownCond = new JLabel("支取方式："+getDownCond(lostPubBean.getDrawCond()));
		labelDownCond.setBounds(559, y1+=33, 400, 25);
		labelDownCond.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelDownCond.setHorizontalAlignment(SwingUtilities.LEFT);
		jPanel.add(labelDownCond);			
				
		//如果是挂失销户，则显示转入卡号
		if("4".equals(lostPubBean.getLostOrSolve())){
			JLabel labelCertNo = new JLabel("转入卡号："+lostPubBean.getSelectCardNo());
			labelCertNo.setBounds(559, y1+=33, 400, 25);
			labelCertNo.setFont(new Font("微软雅黑",Font.PLAIN,20));
			labelCertNo.setHorizontalAlignment(SwingUtilities.LEFT);
			jPanel.add(labelCertNo);
		}
	}
	
	/**
	 * 返回上一步
	 * @Description: 
	 * @Author: hk
	 * @date 2018年4月13日 上午11:43:41
	 */
	public void back(){
		//如果在柜员授权时
		if("1".equals(lostPubBean.getAllPubIsCheckAuthor())){
			logger.info("授权过程：返回授权检查照片页面");
			clearTimeText();
			lostPubBean.setNextStepName("ALL_PUBLIC_CHECK_PHOTOS_PANLE");
			allPubTransFlow.transFlow();
			return;
		}else{//客户办理业务签字时
			clearTimeText();
			lostPubBean.setAllPubIsSign("");
			if(lostPubBean.getReMakePwdNo()!=null && !"".equals(lostPubBean.getReMakePwdNo())){//如果重置过密码
				lostPubBean.setReMakePwdNo("");//清除重置密码标志
				lostPubBean.setReMakePwd("");//清除重置的密码
			}
			if("3".equals(lostPubBean.getLostOrSolve())){//解挂补发/
				
				new Thread(new Runnable() {
	    			@Override
	    			public void run() {
	    				lostAction.checkLostBook();
	    			}
	    		}).start();
				
			}else if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
				
				if("0".equals(lostPubBean.getSolveLostType()) || "2".equals(lostPubBean.getSolveLostType()) || "1".equals(lostPubBean.getSolveLostType())){//银行卡、存折/个人普通存单
					
					//进入选择解挂销户转入卡选择页
					openPanel(new SolveLostCancelAccCardPage());
					
				}else if("1_1".equals(lostPubBean.getSolveLostType()) || "1_2".equals(lostPubBean.getSolveLostType())){//存单卡下子账号、存单电子子账号
					
					//进入自动带出卡/电子账号显示页
					openPanel(new SolveLostGetCardOrEcardPage());
				}
					
			}else{
				
				openPanel(new SolveLostApplNoInfos());//返回挂失申请书信息页面
			}
		}
	}
	
	/**
	 * 确认进入下一步
	 * @Description: 
	 * @Author: hk
	 * @date 2018年4月13日 上午11:43:49
	 */
	public void ok(){
		lostPubBean.setUpStepName("LOST_CONFIRM_PANEL");
		lostPubBean.setNextStepName("ACTION_CHECK_AUTHOR_METHOD");
		if(lostPubBean.getAllPubIsCheckAuthor()!=null && "1".equals(lostPubBean.getAllPubIsCheckAuthor())){
			lostPubBean.setAllPubIsCheckAuthor("2");
		}
		openPanel(new AllPublicAuthorNoPanel());
	}
	
	//显示签字区
	public void signView(){
		//清空倒计时
		clearTimeText();
		lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
		//跳转到签字页面
		openPanel(new AllPublicSignaturePanel());
	}
	
	//点击查看签名图片
	public void askLook(){
		logger.info("进入查看签名图片方法");
		//设置信息内容消失
		jPanel.setVisible(false);
		jLabel.setVisible(false);
		labelSign.setVisible(false);
			
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
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
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
				image.setImage(image.getImage().getScaledInstance(780, 185, Image.SCALE_DEFAULT));
				labelSign.setVisible(true);
				jLabel.setVisible(true);
				jPanel.setVisible(true);
			}
		});
	}
	
	//处理挂失状态
	public String getLostType(String lostType){
		String resultLostType = null;
		if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//如果是挂失补发/销户
			
			if("0".equals(lostPubBean.getSolveLostType())){//银行卡
				
				if(lostType!=null && "4".equals(lostType)){//双挂
					String mark=lostPubBean.getApplInfos().getLostOrSolveState().substring(3,4);
					if("1".equals(mark)){
						resultLostType="银行卡+密码";
					}else if("2".equals(mark)){
						resultLostType="银行卡";
					}
				}else{
					resultLostType="银行卡";
				}
				
			}else if("2".equals(lostPubBean.getSolveLostType())){//存折
				
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存折+密码";
					}else if("2".equals(mark)){
						resultLostType="存折";
					}
				}else{
					resultLostType="存折";
				}
				
			}else{//存单
				
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存单+密码";
					}else if("2".equals(mark)){
						resultLostType="存单";
					}
				}else{
					resultLostType="存单";
				}
				
			}
			
		}else{//如果是挂失撤销
			if("1".equals(lostPubBean.getSolveLostType()) || "1_1".equals(lostPubBean.getSolveLostType()) || "1_2".equals(lostPubBean.getSolveLostType())){
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存单+密码";
					}else if("2".equals(mark)){
						resultLostType="存单";
					}
				}else{
					resultLostType="存单";
				}
			}else if("2".equals(lostPubBean.getSolveLostType())){//存折
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存折+密码";
					}else if("2".equals(mark)){
						resultLostType="存折";
					}
				}else{
					resultLostType="存折";
				}			
			}else{
				if("4".equals(lostType)){//如果挂失状态为双挂
					String mark=lostPubBean.getApplInfos().getLostOrSolveState().substring(3,4);
					if("1".equals(mark)){
						resultLostType="银行卡+密码";
					}else if("2".equals(mark)){
						resultLostType="银行卡";
					}
				}else{
					resultLostType="银行卡";
				}
				
			}
		}
		return resultLostType;
	}
	
	//处理支取方式
	public String getDownCond(String downCond){
		if(downCond!=null && !"".equals(downCond.trim())){
			
			if("0".equals(downCond.trim())){
				return "无密码";
			}else if("1".equals(downCond.trim())){
				return "凭密码";
			}else if("2".equals(downCond.trim())){
				return "凭证件";
			}else if("3".equals(downCond.trim())){
				return "凭印鉴";
			}else{
				return "无";
			}
		}else{
			return "无";
		}
	}	
}
