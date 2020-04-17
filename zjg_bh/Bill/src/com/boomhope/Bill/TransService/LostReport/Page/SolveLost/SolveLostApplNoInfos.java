package com.boomhope.Bill.TransService.LostReport.Page.SolveLost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Bean.LostCheckBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;

/**
 * Title:展示挂失申请书信息页面 
 *
 * @author: hk
 * 
 * @date: 2018年4月13日 上午11:44:37  
 * 
 */
@SuppressWarnings("static-access")
public class SolveLostApplNoInfos extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(SolveLostApplNoInfos.class);
	private static final long serialVersionUID = 1L;
	
	private JPanel jpanelInfo;
	private int y = 120;//显示信息起始高度
	private int y1 = 65;//第二列显示高度
	
	private boolean on_off=true;
	
	public SolveLostApplNoInfos(){
		logger.info("进入挂失申请书信息展示页面");
		baseTransBean.setThisComponent(this);
		
		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondLongestTime);
		delayTimer = new Timer(delaySecondLongestTime * 300,
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						logger.info("挂失申请书信息页面倒计时结束");
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
		
		jpanelInfo = new JPanel();
		jpanelInfo.setBounds(0, 0, this.showJpanel.getWidth(), this.showJpanel.getHeight());
		jpanelInfo.setVisible(true);
		jpanelInfo.setLayout(null);
		jpanelInfo.setOpaque(false);
		this.showJpanel.add(jpanelInfo);
		
		// 标题
		JLabel depoLum = new JLabel("挂失账户信息");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 27, 1009, 60);
		jpanelInfo.add(depoLum);		
		
		//账号
		JLabel labelAccNo = new JLabel("账(卡)            号："+lostPubBean.getAllPubAccNo());
		labelAccNo.setBounds(30, y,800 , 35);
		labelAccNo.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelAccNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelAccNo);
		
		//挂失申请书号
		JLabel labelApplNo = new JLabel("挂 失 申 请 书号："+bean.getLostApplNo());
		labelApplNo.setBounds(30, y+=55, 800, 35);
		labelApplNo.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelApplNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelApplNo);
		
		//客户名称
		JLabel labelName = new JLabel("客   户    名    称："+bean.getCust_name());
		labelName.setBounds(30, y+=55, 800, 35);
		labelName.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelName.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelName);
		
		//挂失人证件类型
		JLabel labelLostCerType = new JLabel("证    件    类    型：身份证");
		labelLostCerType.setBounds(30, y+=55, 800, 35);
		labelLostCerType.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelLostCerType.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelLostCerType);
		
		//挂失人证件号码
		JLabel labelLostCerNo = new JLabel("证    件    号    码："+bean.getId_no());
		labelLostCerNo.setBounds(30, y+=55, 800, 35);
		labelLostCerNo.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelLostCerNo.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelLostCerNo);
		
		/**
		 * 如果没有代理人信息则不显示
		 */
		if(bean.getId_AgentName()!=null && !"".equals(bean.getId_AgentName().trim())){
		
			//代理人名称
			JLabel labelAgentName = new JLabel("代  理 人  名  称："+bean.getId_AgentName());
			labelAgentName.setBounds(30, y+=55, 800, 35);
			labelAgentName.setFont(new Font("微软雅黑",Font.PLAIN,25));
			labelAgentName.setHorizontalAlignment(SwingUtilities.LEFT);
			jpanelInfo.add(labelAgentName);
			
			//代理人证件类型
			JLabel labelAgentCerType = new JLabel("代理人证件类型：身份证");
			labelAgentCerType.setBounds(30, y+=55, 800, 35);
			labelAgentCerType.setFont(new Font("微软雅黑",Font.PLAIN,25));
			labelAgentCerType.setHorizontalAlignment(SwingUtilities.LEFT);
			jpanelInfo.add(labelAgentCerType);
			
			//代理人证件号码
			JLabel labelAgentCerNo = new JLabel("代理人证件号码："+bean.getId_AgentNo());
			labelAgentCerNo.setBounds(30, y+=55, 800, 35);
			labelAgentCerNo.setFont(new Font("微软雅黑",Font.PLAIN,25));
			labelAgentCerNo.setHorizontalAlignment(SwingUtilities.LEFT);
			jpanelInfo.add(labelAgentCerNo);
		}
		
		//如果为挂失补发，则显示凭证号
		if("3".equals(lostPubBean.getLostOrSolve())){
			JLabel labelCertNo = new JLabel("凭    证   号："+bean.getCert_no());
			labelCertNo.setBounds(580, y1+=55, 400, 35);
			labelCertNo.setFont(new Font("微软雅黑",Font.PLAIN,25));
			labelCertNo.setHorizontalAlignment(SwingUtilities.LEFT);
			jpanelInfo.add(labelCertNo);
		}
		
		//挂失日期
		JLabel labelDate = new JLabel("挂 失 日 期："+bean.getLostApplNo().substring(5,13));
		labelDate.setBounds(580, y1+=55, 400, 35);
		labelDate.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelDate.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelDate);
		
		//挂失方式
		JLabel labelLostMethod = new JLabel("挂 失 方 式："+"正式挂失");
		labelLostMethod.setBounds(580, y1+=55, 450, 35);
		labelLostMethod.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelLostMethod.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelLostMethod);
		
		//挂失种类
		JLabel labelLostType = new JLabel("挂 失 种 类："+getLostType(bean.getLostType()));
		labelLostType.setBounds(580, y1+=55, 400, 35);
		labelLostType.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelLostType.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelLostType);
		
		//币种
		JLabel labelCurrecy = new JLabel("币          种："+"人民币");
		labelCurrecy.setBounds(580, y1+=55, 450, 35);
		labelCurrecy.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelCurrecy.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelCurrecy);
		
		//结存额
		JLabel labelbanlance = new JLabel("结    存   额："+lostPubBean.getEndAmt());
		labelbanlance.setBounds(580, y1+=55, 400, 35);
		labelbanlance.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelbanlance.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelbanlance);
		
		//支取方式
		JLabel labelDownCond = new JLabel("支 取 方 式："+getDownCond(lostPubBean.getDrawCond()));
		labelDownCond.setBounds(580, y1+=55, 400, 35);
		labelDownCond.setFont(new Font("微软雅黑",Font.PLAIN,25));
		labelDownCond.setHorizontalAlignment(SwingUtilities.LEFT);
		jpanelInfo.add(labelDownCond);		
	}
	
	/**
	 * 返回上一步
	 * @Description: 
	 * @Author: hk
	 * @date 2018年4月13日 上午11:43:41
	 */
	public void back(){
		clearTimeText();
		//进入到输入挂失申请书号页面
		openPanel(new SolveLostInputApplyNoPage());
	}
	
	/**
	 * 确认进入下一步
	 * @Description: 
	 * @Author: hk
	 * @date 2018年4月13日 上午11:43:49
	 */
	@SuppressWarnings("unchecked")
	public void ok(){
		//解挂种类(0-银行卡解挂，1-个人存单解挂，1_1-卡下子账户存单解挂，1_2-电子子账户存单解挂，2-存折解挂)
		ShowAccNoMsgBean bean = new ShowAccNoMsgBean();
		if(lostPubBean.getSolveLostType()!=null && "0".equals(lostPubBean.getSolveLostType())){
			bean.setCardOrAccOrAcc1("0");
		}else if(lostPubBean.getSolveLostType()!=null && "1".equals(lostPubBean.getSolveLostType())){
			bean.setCardOrAccOrAcc1("1");
		}else if(lostPubBean.getSolveLostType()!=null && "1_1".equals(lostPubBean.getSolveLostType())){
			bean.setCardOrAccOrAcc1("1_1");
		}else if(lostPubBean.getSolveLostType()!=null && "1_2".equals(lostPubBean.getSolveLostType())){
			bean.setCardOrAccOrAcc1("1_2");
		}else if(lostPubBean.getSolveLostType()!=null && "2".equals(lostPubBean.getSolveLostType())){
			bean.setCardOrAccOrAcc1("2");
		}
		//存放选中的账户信息
		lostPubBean.getAccMap().put("selectMsg", bean);
		
		if("3".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//解挂补发/销户
			
			new Thread(new Runnable() {
    			@Override
    			public void run() {
    				//判断账户是否允许解挂
    				lostAction.accIsSolve();
    			}
    		}).start();
			
			
		}else{
			
			//判断是卡的挂失撤销还是单折的挂失撤销
			String lostType = lostPubBean.getApplInfos().getLostType().trim();
			if("0".equals(lostPubBean.getSolveType())){//卡
				lostPubBean.setAllPubPassAccNo(lostPubBean.getApplInfos().getAcc_no());//获取卡号
				
				//如果该挂失账户为卡正式挂失，则下一步需要输入密码验证
				 if("4".equals(lostType) || "0".equals(lostPubBean.getDrawCond()) || "N".equals(lostPubBean.getCardState())){//如果是密码挂失或者双挂失或者无密码支取或者未激活
					clearTimeText();
					openPanel(new SolveLostApplNoInfosConfirmPage());
					return;
				}else if("2".equals(lostType)){
					clearTimeText();
					lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_PASSWORD_PANEL");
					allPubTransFlow.transFlow();
					return;
				}else{
					on_off=true;
					openMistakeDialog("抱歉，该途径不支持其他挂失方式的撤销，请返回上一步重新输入。");
					return;
				}
			}else{//单折
				if(lostPubBean.getAllPubAccNo().contains("-")){
					lostPubBean.setAllPubPassAccNo(lostPubBean.getAllPubAccNo().split("-")[0]);//获取卡号
				}else{
					lostPubBean.setAllPubPassAccNo(lostPubBean.getAllPubAccNo());//获取普通账号
				}
				//如果账户为正式挂失
				if(lostType != null && lostType.endsWith("6") ||"0".equals(lostPubBean.getDrawCond())){//如果为密码挂失或者双挂失
					clearTimeText();
					openPanel(new SolveLostApplNoInfosConfirmPage());
					return;
				}else if(lostType!=null && lostType.endsWith("2")){//单折正式挂失
					if("0".equals(lostPubBean.getDrawCond()) || "2".equals(lostPubBean.getDrawCond())){//如果是无密码或者凭证件支取则无需输入密码
						clearTimeText();
						openPanel(new SolveLostApplNoInfosConfirmPage());
						return;
					}else{//单折正式挂失凭密码支取
						clearTimeText();
						lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_PASSWORD_PANEL");
						allPubTransFlow.transFlow();
						return;
					}
				}else{
					on_off=true;
					openMistakeDialog("抱歉，该途径不支持其他挂失方式的撤销，请返回上一步重新输入。");
					return;
				}
			}
			
		}
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
						lostPubBean.setSolveAccState("1");
					}else if("2".equals(mark)){
						resultLostType="银行卡";
						lostPubBean.setSolveAccState("0");
					}
				}else{
					resultLostType="银行卡";
					lostPubBean.setSolveAccState("0");
				}
				
			}else if("2".equals(lostPubBean.getSolveLostType())){//存折
				
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存折+密码";
						lostPubBean.setSolveAccState("5");
					}else if("2".equals(mark)){
						resultLostType="存折";
						lostPubBean.setSolveAccState("4");
					}
				}else{
					resultLostType="存折";
					lostPubBean.setSolveAccState("4");
				}
				
			}else{//存单
				
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存单+密码";
						lostPubBean.setSolveAccState("3");
					}else if("2".equals(mark)){
						resultLostType="存单";
						lostPubBean.setSolveAccState("2");
					}
				}else{
					resultLostType="存单";
					lostPubBean.setSolveAccState("2");
				}
				
			}
			
		}else{//如果是挂失撤销
			if("1".equals(lostPubBean.getSolveLostType()) || "1_1".equals(lostPubBean.getSolveLostType()) || "1_2".equals(lostPubBean.getSolveLostType())){
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存单+密码";
						lostPubBean.setSolveAccState("3");
					}else if("2".equals(mark)){
						resultLostType="存单";
						lostPubBean.setSolveAccState("2");
					}
				}else{
					resultLostType="存单";
					lostPubBean.setSolveAccState("2");
				}
			}else if("2".equals(lostPubBean.getSolveLostType())){//存折
				if(lostType.trim().endsWith("6")){//如果是双挂失状态
					String mark = lostPubBean.getApplInfos().getLostOrSolveState().substring(2,3); //当前挂失状态标志
					if("1".equals(mark)){
						resultLostType="存折+密码";
						lostPubBean.setSolveAccState("5");
					}else if("2".equals(mark)){
						resultLostType="存折";
						lostPubBean.setSolveAccState("4");
					}
				}else{
					resultLostType="存折";
					lostPubBean.setSolveAccState("4");
				}			
			}else{
				if("4".equals(lostType)){//如果挂失状态为双挂
					String mark=lostPubBean.getApplInfos().getLostOrSolveState().substring(3,4);
					if("1".equals(mark)){
						resultLostType="银行卡+密码";
						lostPubBean.setSolveAccState("1");
					}else if("2".equals(mark)){
						resultLostType="银行卡";
						lostPubBean.setSolveAccState("0");
					}
				}else{
					resultLostType="银行卡";
					lostPubBean.setSolveAccState("0");
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
