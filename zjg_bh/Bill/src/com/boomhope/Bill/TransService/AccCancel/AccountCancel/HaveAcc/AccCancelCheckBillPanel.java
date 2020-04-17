package com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelDeputySelectionPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/***
 * 核对存单Panel
 * @author zjg
 *
 */
public class AccCancelCheckBillPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(AccCancelCheckBillPanel.class);
	private static final long serialVersionUID = 1L;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	private JPanel panel1;//显示所有信息的基础面板
	private JPanel panelPic;//显示大图片
	
	/***
	 * 初始化
	 */
	public AccCancelCheckBillPanel(){
		
		
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
		accNoText.setText(":   " + accCancelBean.getAccNo());
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
		accNameText.setText(":   " + accCancelBean.getIdName()) ;
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
		billNoText.setText(":   " + accCancelBean.getBillNo()) ;
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
		amountText.setText(":   " + accCancelBean.getTotalAmt()+"元") ;//显示存折余额
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
		openDateText.setText(":   " + accCancelBean.getOpenDate()) ;
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
		dueDateText.setText(":   " + accCancelBean.getDueDate()) ;
		panel1.add(dueDateText);
		
		String picPath = accCancelBean.getBillPath_fc();
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
		okButton.setBounds(890, 770, 150, 50);
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
		
		int endDate; 
		int nowDate;
		int result;
		
		//存单金额
		Float money = Float.valueOf(accCancelBean.getAmount());
		
		//销户鉴伪不过限额10万
		String amt = Property.getProperties().getProperty("acc_cancel_amt");
		Float accCancelAmt = Float.valueOf(amt);
		
		if("2".equals(accCancelBean.getCheckStatus())){//鉴伪不通过
			if(money>=accCancelAmt){//金额大于等于10万
				
				if("2".equals(accCancelBean.getTellNoState())){
					
					logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万:"+money+"元，二次都已授权，继续销户流程");
					
				}else{
					
					//进入提示页
					logger.info("第一次鉴伪不通过，手动输入，金额大于等于10万:"+money+"元，先第一次授权、再第二次授权，进入提示页，重新插入存单");
					openPanel(new AccCancelAmtOverrunPanel("金额超限，请双人对存单真伪进行鉴别"));
					return;
				}
				
			}else{//金额小于10万
				
				if("1".equals(accCancelBean.getTellNoState())){
					
					logger.info("第一次鉴伪不通过，手动输入，金额小于10万:"+money+"元，已授权，继续销户流程");
					
				}else{
					
					//进入提示页
					logger.info("第一次鉴伪不通过，手动输入，金额小于10万:"+money+"元，进行一次授权，进入提示页，重新插入存单");
					openPanel(new AccCancelAmtOverrunPanel("鉴伪或OCR识别失败，请手工对存单真伪进行鉴别"));
					return;
				}
			}	
		}
		
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
			
			//判断是否兑付唐豆或有已付收益
			if(!"002".equals(accCancelBean.getAccCancelType())){
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
				openConfirmDialog("该笔业务未到期,"+msg+"是否继续:是：继续。否：返回。");
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
						openPanel(new AccCancelCheckBillPanel());//停留在当前页
					}
				});
			}else{//电子子账户不调唐豆查询、幸运豆查询
				//提前支取，进入代理人选择
				openConfirmDialog("该笔业务未到期,是否继续:是：继续。否：返回。");
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
						openPanel(new AccCancelCheckBillPanel());//停留在当前页
					}
				});
			}
			
			
		}else{//到期支取
			
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关闭语音
			closeVoice();//关闭语音流
			
			logger.info("存单属于到期支取");
			accCancelBean.setJudgeState("2");//到期支取标识
			//判断金额
			if(result>=0){//开户金额大于等于50000（代理人选择）
				
				logger.info("存单金额大于等于50000元，走授权");
				accCancelBean.setAmtState("1");
				openPanel(new AccCancelDeputySelectionPanel());//代理人选择
				
			}else{//开户金额小于50000（走简易流程）
				accCancelBean.setHavAgentFlag("0");//无代理人标志
				
				logger.info("存单金额小于50000元，走简易流程");
				accCancelBean.setAmtState("0");
				//判断是否为电子账户
				if("002".equals(accCancelBean.getAccCancelType())){
					
					logger.info("该存单为电子子账户");
					//校验电子账户信息
					openPanel(new AccCancelCheckingBankcardPanel());
					
				}else{
					
					//判断是否卡下子账户
					if("0".equals(accCancelBean.getSubAccNoCancel())){
						
						logger.info("该存单为卡下子账户");
						//校验卡账户信息
						openPanel(new AccCancelCheckingBankcardPanel());
						
					}else{
						
						logger.info("该存单为普通账户");
						//进入插入银行卡/手动输入卡号选择页
						openPanel(new AccCancelSelectMethodPanel());
						
					}
				}
			}
		}
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
}
