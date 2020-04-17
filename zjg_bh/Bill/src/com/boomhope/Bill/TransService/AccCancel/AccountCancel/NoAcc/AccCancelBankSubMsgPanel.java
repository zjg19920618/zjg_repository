package com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelCheckingBankcardPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelDeputySelectionPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.SubAccInfoBean;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/***
 * 显示卡下子账户信息
 * @author hk
 *
 */
public class AccCancelBankSubMsgPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccCancelBankSubMsgPanel.class);

	private int nowPage;
	private Component comp =null;
	private JPanel panel1 = null;
	
	private String cardNo;//卡号
	private String subAccNo;//子账号
	private String openInstNo;//开户机构
	private String openDate;//开户日期
	private String endIntDate;//到期日期
	private String openATM;//开户金额
	private String productName;//产品名称
	private String depTerm;//存期
	private String openRate;//利率
	private String balance;//结存额
	private String accNoState;//存单状态
	private String channel;//渠道
	private String bill;//凭证号
	private String printState;//打印状态
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public AccCancelBankSubMsgPanel() {
		this.comp = this;
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		//向左
		JLabel left=new JLabel();
		left.setIcon(new ImageIcon("pic/newPic/left.png"));
		left.setBounds(30,265 ,57, 98);
		left.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				upStep();
			}
		});
		this.showJpanel.add(left);
	   
		//向右
		JLabel right=new JLabel();
		right.setIcon(new ImageIcon("pic/newPic/right.png"));
		right.setBounds(922,265,57, 98);
		right.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nextStep();		
			}
		});
		this.showJpanel.add(right);
		
		JLabel labelName = new JLabel("请选择卡下子账户信息");
		labelName.setBounds(0, 60, 1009, 40);
		labelName.setFont(new Font("微软雅黑",Font.BOLD,34));
		labelName.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(labelName);
		
		//按钮
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(1075, 770, 150, 50);
		add(lblNewLabel);
		lblNewLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				confirm();
			}
		});
		
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		
		initPanelShow();
	}
	
	/**
	 * 创建显示模板
	 */
	public void initPanelShow(){
		nowPage = accCancelBean.getNowPage();
		SubAccInfoBean subBean = (SubAccInfoBean)accCancelBean.getImportMap().get("SUB_ACC_MSG").get(nowPage-1);
		cardNo = subBean.getAccNo();//卡号
		subAccNo = subBean.getSubAccNo();//子账号
		openInstNo = subBean.getOpenInstNo();//开户机构
		openDate = subBean.getOpenDate();//开户日期
		endIntDate = subBean.getEndIntDate();//到期日期
		openATM = subBean.getOpenATM();//开户金额
		productName = subBean.getProductName();//产品名称
		depTerm = subBean.getDepTerm();//存期
		openRate = subBean.getOpenRate();//利率
		balance = subBean.getBalance();//结存额
		accNoState = subBean.getAccNoState();//存单状态
		channel = subBean.getChannel();//渠道
		bill = subBean.getBill();//凭证号
		printState = subBean.getPrintState();//打印状态
		
		panel1 = new JPanel();
		panel1.setBounds(102, 120, 800, 440);
		panel1.setLayout(null);
		panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel1.setOpaque(false);
		this.showJpanel.add(panel1);
		
		//户名
		JLabel label15 = new JLabel("户       名：");
		label15.setBounds(30, 30, 200, 30);
		label15.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label15.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label15);
		
		//户名
		JLabel label16 = new JLabel(accCancelBean.getCardName());
		label16.setBounds(140, 30, 200, 30);
		label16.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label16.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label16);
		
		//产品名称
		JLabel label5 = new JLabel("产品名称：");
		label5.setBounds(30, 80, 200, 30);
		label5.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label5.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label5);
		
		//产品名称
		JLabel label6 = new JLabel(productName);
		label6.setBounds(140, 80, 400, 30);
		label6.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label6.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label6);
		
		//卡号
		JLabel label1 = new JLabel("账       号：");
		label1.setBounds(30, 130, 200, 40);
		label1.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label1.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label1);
		
		//卡号
		JLabel label2 = new JLabel(cardNo+"-"+subAccNo);
		label2.setBounds(140, 130, 300, 30);
		label2.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label2.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label2);
		
		//开户日期
		JLabel label7 = new JLabel("开  户  日：");
		label7.setBounds(30, 180, 200, 30);
		label7.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label7.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label7);
		
		//开户日期
		JLabel label8 = new JLabel(openDate);
		label8.setBounds(140, 180, 200,30);
		label8.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label8.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label8);
		
		//金额
		JLabel label9 = new JLabel("开户金额：");
		label9.setBounds(370, 180, 200, 30);
		label9.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label9.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label9);
		
		//金额
		JLabel label10 = new JLabel(openATM);
		label10.setBounds(480, 180, 200, 30);
		label10.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label10.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label10);
		
		
		//到期日
		JLabel label13 = new JLabel("到  期  日：");
		label13.setBounds(30, 230, 200, 30);
		label13.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label13.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label13);
		
		//到期日
		JLabel label14 = new JLabel(endIntDate);
		label14.setBounds(140, 230, 200, 30);
		label14.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label14.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label14);
		
		//结存额
		JLabel label17 = new JLabel("结  存  额：");
		label17.setBounds(370, 230, 200, 30);
		label17.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label17.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label17);
		
		//结存额
		JLabel label18 = new JLabel(balance);
		label18.setBounds(480, 230, 200, 30);
		label18.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label18.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label18);
		
		//开户机构
		JLabel label11 = new JLabel("开户机构：");
		label11.setBounds(30, 280, 200, 30);
		label11.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label11.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label11);
		
		//开户机构
		JLabel label12 = new JLabel(openInstNo);
		label12.setBounds(140, 280, 200, 30);
		label12.setFont(new Font("微软雅黑",Font.PLAIN,20));
		label12.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label12);
		
		//当前页
		JLabel label3 = new JLabel(String.valueOf(nowPage));
		label3.setBounds(550, 400, 100, 30);
		label3.setFont(new Font("微软雅黑",Font.PLAIN,30));
		label3.setHorizontalAlignment(SwingUtilities.RIGHT);
		panel1.add(label3);	
		
		JLabel label4 = new JLabel("/");
		label4.setBounds(630, 400, 100, 30);
		label4.setFont(new Font("微软雅黑",Font.PLAIN,30));
		label4.setHorizontalAlignment(SwingUtilities.CENTER);
		panel1.add(label4);
		
		//总页数
		JLabel label19 = new JLabel(String.valueOf(accCancelBean.getImportMap().get("SUB_ACC_MSG").size()));
		label19.setBounds(710, 400, 100, 30);
		label19.setFont(new Font("微软雅黑",Font.PLAIN,30));
		label19.setHorizontalAlignment(SwingUtilities.LEFT);
		panel1.add(label19);		
		
	}
	
	//确认
	public void confirm(){
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		
		logger.info("确认获取各字段值");
		accCancelBean.setSubAccNoCancel("0");//银行卡子账户标识
		accCancelBean.setAccNo(cardNo+"-"+subAccNo);//账号-子账号
		accCancelBean.setSubCardNo(cardNo);//账号
		accCancelBean.setSubAccNo(subAccNo);//子账号
		accCancelBean.setCardNo(cardNo);//转入卡号
		accCancelBean.getReqMCM001().setAccount(cardNo+"-"+subAccNo);
		
		//查询存单信息
		if(!checkBillMsg()){
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
        	closeVoice();//关闭语音流
			return;
		}
		accCancelBean.setCheckStatus("3");//标记位，存在数据库中，标记这个是无存单
		next();
	}
	
	/*选择对应账号跳转页面*/
	public void next() {
		
		int endDate; 
		int nowDate;
		int result;
		
		try {
			//判断存单是否到期
			logger.info("开始判断存期");
			logger.info("到期日："+accCancelBean.getDueDate());
			logger.info("当前核心日："+accCancelBean.getSvrDate());
			endDate = Integer.parseInt(accCancelBean.getDueDate());//到期日
			nowDate = Integer.parseInt(accCancelBean.getSvrDate());//当前核心日
			
		}catch (Exception e) {
		
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
        	closeVoice();//关闭语音流
        	
        	logger.error("日期转换失败："+e);
        	accCancelBean.getReqMCM001().setReqBefor("");
        	accCancelBean.getReqMCM001().setReqAfter("", "");
        	accCancelBean.getReqMCM001().setIntereturnmsg("存单日期解析失败");
        	accCancelBean.getReqMCM001().setLendirection("");
        	accCancelBean.getReqMCM001().setToaccount("");
        	accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
        	MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop("无法判断是否到期，请联系大堂经理", "", "");
			return;
		}
		
		try {
			//判断联网核查超限金额
			logger.info("存单金额："+accCancelBean.getAmount());
			BigDecimal Amt = new BigDecimal(accCancelBean.getAmount());
			BigDecimal maxAmt = new BigDecimal(Property.getProperties().getProperty("acc_cancel_ckeckIdAmt"));
			result = Amt.compareTo(maxAmt);
			
		} catch (Exception e) {
			
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
        	closeVoice();//关闭语音流
        	
        	logger.error("金额转换失败："+e);
        	accCancelBean.getReqMCM001().setReqBefor("");
        	accCancelBean.getReqMCM001().setReqAfter("", "");
        	accCancelBean.getReqMCM001().setIntereturnmsg("存单金额解析失败");
        	accCancelBean.getReqMCM001().setLendirection("");
        	accCancelBean.getReqMCM001().setToaccount("");
        	accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
        	MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop("无法判断存单金额，请联系大堂经理", "", "");
			return;
			
		}
		
		//判断提前支取、判断金额
		if(endDate > nowDate){//提前支取
			
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
			closeVoice();//关闭语音流

			logger.info("存单属于提前支取");
			accCancelBean.setJudgeState("1");
			
			//判断是否兑付唐豆或加息券
			checkTD();//查询唐豆
			checkXYD();//查询幸运豆-("改为已付收益")
			String tdMsg = "";
			String xydMsg = "";
			String msg = "";
			if(accCancelBean.getTangChaErrMsg()!=null && !"".equals(accCancelBean.getTangChaErrMsg().trim())){
				
				tdMsg = accCancelBean.getTangChaErrMsg() +"、";
				logger.info("唐豆查询失败:"+accCancelBean.getTangChaErrMsg());
				
				if(accCancelBean.getXYDChaErrMsg()!=null && !"".equals(accCancelBean.getXYDChaErrMsg().trim())){
					
					xydMsg = accCancelBean.getXYDChaErrMsg();
					logger.info("已付收益查询失败:"+accCancelBean.getXYDChaErrMsg());
					
				}else{
					if(accCancelBean.getAdvnInit()!=null && !"".equals(accCancelBean.getAdvnInit().trim())){
						double xyd = Double.valueOf(accCancelBean.getAdvnInit());
						if(xyd!=0){
							
							xydMsg = "提前支取扣回已付收益"+accCancelBean.getAdvnInit().trim()+"元";
							logger.info("已付收益查询成功:"+accCancelBean.getAdvnInit().trim()+"元");
						}
					}
				}
				
				msg = tdMsg + xydMsg;
						
			}else{
				if(accCancelBean.getShtdy()!=null && !"".equals(accCancelBean.getShtdy().trim())){
					double td = Double.valueOf(accCancelBean.getShtdy());
					if(td!=0){
						
						tdMsg = "提前支取将扣回唐豆"+accCancelBean.getShtdy().trim()+"元"+"、";
						logger.info("唐豆查询成功:"+accCancelBean.getShtdy().trim()+"元");
					}
				}
				if(accCancelBean.getXYDChaErrMsg()!=null && !"".equals(accCancelBean.getXYDChaErrMsg().trim())){
					
					xydMsg = accCancelBean.getXYDChaErrMsg();
					logger.info("已付收益查询失败:"+accCancelBean.getXYDChaErrMsg());
					
				}else{
					if(accCancelBean.getAdvnInit()!=null && !"".equals(accCancelBean.getAdvnInit().trim())){
						double xyd = Double.valueOf(accCancelBean.getAdvnInit());
						if(xyd!=0){
							
							xydMsg = "扣回已付收益"+accCancelBean.getAdvnInit().trim()+"元";
							logger.info("已付收益查询成功:"+accCancelBean.getAdvnInit().trim()+"元");
						}
					}
				}
				
				msg = tdMsg + xydMsg;
			}
			if(!"".equals(msg)){
				msg = msg+",";
			}
			logger.info("msg："+msg);
			if(accCancelBean.getProCode()!=null){
				if(accCancelBean.getProCode().startsWith("ZK")){
					openConfirmDialog("该笔业务未到期,若提前支取将收回未使用的课时券;如课时券已使用将扣回课时券面额等值的本金,是否继续:是：继续。否：返回。");
				}else{
					openConfirmDialog("该笔业务未到期,"+msg+"是否继续:是：继续。否：返回。");
				}
			}

			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(confirmDialog);
					//提前支取，进入代理人选择
					logger.info("提前支取，走授权");
					openPanel(new AccCancelDeputySelectionPanel());//代理人选择
				}
				
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(confirmDialog);
					//退出弹框
					openPanel(new AccCancelBankSubMsgPanel());//停留在当前页
				}
			});
			
		}else{//到期支取
			
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
			closeVoice();//关闭语音流
			
			logger.info("存单属于到期支取");
			accCancelBean.setJudgeState("2");
			//判断金额
			if(result>=0){//开户金额大于等于50000（进入代理人选择）
				
				logger.info("存单金额大于等于50000元，走授权");
				accCancelBean.setAmtState("1");
				openPanel(new AccCancelDeputySelectionPanel());//代理人选择
				
			}else{//开户金额小于50000（走简易流程）
				accCancelBean.setHavAgentFlag("0");//无代理人标志
				
				logger.info("存单金额小于50000元，走简易流程");
				accCancelBean.setAmtState("0");
				//判断是否卡下子账户
				logger.info("该存单为卡下子账户");
				//校验卡账户信息
				openPanel(new AccCancelCheckingBankcardPanel());	
					
			}
		}
	}	
	
	/**
	 * 调用存单信息查询接口
	 */
	public boolean checkBillMsg(){
		try {
			accCancelBean.setIsCheckPass("0");//不验密
			accCancelBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = IntefaceSendMsg.inter07601(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			accCancelBean.getReqMCM001().setCustomername(accCancelBean.getAccName());
			accCancelBean.getReqMCM001().setTonouns(accCancelBean.getAccName());
			if(!"000000".equals(inter07601.get("resCode"))){
				logger.info((String)inter07601.get("errMsg"));
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop("存单信息查询失败",(String)inter07601.get("errMsg"),"");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用07601存单账号信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop("存单信息查询失败","调用07601存单账号信息查询失败","");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 07515唐豆查询
	 */
	public void checkTD(){
		logger.info("开始调用唐豆信息查询【02217】-前置07515");
		Map inter07515 = null;
		Date svrdate = null;//核心日
		Date startDate = null;//开户日期
		String demTerm = null;//获取存期
		try {
			inter07515 = IntefaceSendMsg.inter07515(accCancelBean); 
			if(!"000000".equals(inter07515.get("resCode"))){
				
				logger.info(inter07515.get("errMsg"));
				accCancelBean.setTangChaErrMsg((String)inter07515.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07515唐豆信息查询失败"+e);
			accCancelBean.setTangChaErrMsg("唐豆信息查询失败");
		}	
		
		List<AccTdMsgBean> list = (List<AccTdMsgBean>)accCancelBean.getImportMap().get("TdMsg");
		
		if(list!=null && list.size()!=0){
			
			for (AccTdMsgBean accTdMsgBean : list) {
				
				if(!"0.00".equals(accTdMsgBean.getShouhuiAmt())){//选择不为0的唐豆
					
					logger.info("查询唐豆返回list："+list);
					logger.info("查询唐豆账户："+accTdMsgBean.getAccNo().trim());
					logger.info("查询唐豆有效标识："+accTdMsgBean.getMark().trim());
					logger.info("查询唐豆金额："+accTdMsgBean.getShouhuiAmt().trim());
					logger.info("查询唐豆存期："+accTdMsgBean.getDepTerm().trim());
					
					if("0".equals(accTdMsgBean.getMark().trim())){//查询唐豆有效标志
						
						try {
							svrdate =  DateUtil.getDate(accCancelBean.getSvrDate());
							startDate = DateUtil.getDate(accCancelBean.getStartDate());
							//获取开户日期之后的一年
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(startDate);
							int year = calendar.get(Calendar.YEAR);
							calendar.set(Calendar.YEAR, year+1);
							//一年后的开户日
							startDate = calendar.getTime();
							demTerm = accTdMsgBean.getDepTerm().trim();
							
						} catch (ParseException e) {
							logger.info("获取唐豆信息失败:"+e);
							accCancelBean.setTangChaErrMsg("获取唐豆信息失败");
						}
						
						//判断存期，当存期为两年，超过1年再支取的，不调用唐豆收回接口
						if(demTerm.contains("Y") && Integer.parseInt(demTerm.replace("Y", "")) == 2 && startDate.compareTo(svrdate) <= 0){
							
							logger.info("存期为两年，超过1年再支取,不扣回唐豆");
							accCancelBean.setTangChaErrMsg("存期为两年，超过1年再支取,不扣回唐豆");
							
						}else{
							
							logger.info("获取提前支取需要扣回的唐豆金额:"+accTdMsgBean.getShouhuiAmt());
							accCancelBean.setShtdy(accTdMsgBean.getShouhuiAmt());//获取唐豆金额
						}
					}
				}
			}
		}
	}
	/**
	 * 已付收益查询
	 */
	public void checkXYD(){
		logger.info("开始调用存单预计利息【02944】-前置07696/查询已付收益");
		Map inter07696 = null;
		try {
			inter07696 = IntefaceSendMsg.inter07696(accCancelBean); 
			if(!"000000".equals(inter07696.get("resCode"))){
				
				logger.info(inter07696.get("errMsg"));
				accCancelBean.setXYDChaErrMsg((String)inter07696.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07696已付收益查询失败"+e);
			accCancelBean.setXYDChaErrMsg("已付收益查询失败");
		}	
	}
	
	/**
	 * 上一页
	 * @return 
	 */
	public void  upStep(){
		clearTimeText();//清空倒计时
		if(nowPage<=1){
			openMistakeDialog("已经没有更多信息了");
			return;
		}
		nowPage--;
		accCancelBean.setNowPage(nowPage);
		panel1.removeAll();
		initPanelShow();
		panel1.repaint();
	}
	
	/**
	 * 下一页
	 */
	public void  nextStep(){
		clearTimeText();//清空倒计时
		if(nowPage>=accCancelBean.getSubPages()){
			openMistakeDialog("已经没有更多信息了");
			return;
		}
		nowPage++;
		accCancelBean.setNowPage(nowPage);
		panel1.removeAll();
		initPanelShow();
		panel1.repaint();
	}
	
}
