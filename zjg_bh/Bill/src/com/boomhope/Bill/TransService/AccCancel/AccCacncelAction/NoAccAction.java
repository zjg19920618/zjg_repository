package com.boomhope.Bill.TransService.AccCancel.AccCacncelAction;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.XHPrintPt;
import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelBankPassPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelDepositAffirmSignaPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelSelectMethodPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.HaveAcc.AccCancelsubAccBankorBillPassPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelBankEMsgPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelBankSubMsgPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.NoAcc.AccCancelInputBankCardPasswordPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelOutputDepositPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelPtPrintProcessingPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelSuccessPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelCameraPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelDeputySelectionPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelInputAgentIdCardPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccCancelProFileBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccCancelTBMsgBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.TransService.AccCancel.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.ChangeStringFormat;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil_XH;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.Util.YiZhangUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class NoAccAction extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(NoAccAction.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * 输入密码后查询相关信息
	 * @param hk
	 */
	public void passWord(final Component comp){
		boolean result = true;
		if(!checkCardMsg(comp)){
			logger.info("查询卡信息结果："+result);
			return;
		}
		if(!checkCardAcct(comp)){
			logger.info("查询卡账户信息结果："+result);
			return;
		}
		if(!checkCardAcct1(comp)){
			logger.info("查询卡账户信息结果："+result);
			return;
		}
		if("001".equals(accCancelBean.getAccCancelType()) || "003".equals(accCancelBean.getAccCancelType())){
			result = checkSubInfo(comp);
			if(!result){
				logger.info("查询卡下子账户信息结果："+result);
				return;
			}
			if(accCancelBean.getImportMap().get("SUB_ACC_MSG")==null || accCancelBean.getImportMap().get("SUB_ACC_MSG").size()==0){
				logger.info("无子账户信息："+accCancelBean.getImportMap().get("SUB_ACC_MSG"));
				serverStop(comp, "该账户无相关的卡下子账户信息", "", "");
				return;
			}
			logger.info("进入卡下子账户列表");
			clearTimeText();
			openPanel(comp,new AccCancelBankSubMsgPanel());
		}else{
			
			result = checkEInfo(comp);
			if(!result){
				logger.info("查询电子子账户信息结果："+result);
				return;
			}
			
			if(accCancelBean.getImportMap().get("E_ACC_MSG")==null || accCancelBean.getImportMap().get("E_ACC_MSG").size()==0){
				logger.info("无电子账户信息："+accCancelBean.getImportMap().get("E_ACC_MSG"));
				serverStop(comp, "该账户无相关的电子子账户信息", "", "");
				return;
			}
			logger.info("进入电子子账户列表");
			clearTimeText();
			openPanel(comp,new AccCancelBankEMsgPanel());
		}
	}
	
	/**
	 * 本人身份信息核查
	 */
	public void mePolicecheck(final Component comp){
		
		//查询黑灰名单
		if(!meCheckTelephoneFraud(comp)){
			return;
		}
		
		//身份证联网核查
		if(!meCheckIdCardInfo(comp)){
			return;
		}
		
		if("1".equals(accCancelBean.getHavAgentFlag())){//有代理人
			
			logger.info("插入代理人身份证");
			openPanel(comp, new AccCancelInputAgentIdCardPanel());
			
		}else{//本人
			logger.info("进入拍照页面");
			openPanel(comp, new AccCancelCameraPanel());
		}
		
	}
	
	/**
	 * 代理人身份信息核查
	 */
	public void agentPolicecheck(final Component comp){
		
		if(accCancelBean.getReadIdNo().equals(accCancelBean.getReadAgentIdNo())){
			//弹框提示非同一人
			openConfirmDialog("代理人不能为本人,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//跳转代理人身份证插入页
					openPanel(comp, new AccCancelInputAgentIdCardPanel());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//进入代理人选择页
					openPanel(comp, new AccCancelDeputySelectionPanel());
				}
				
			});
			return;
		}
		
		//代理人查询
		if(!checkAgentInfo(comp)){
			return;
		}
		//是否为本行人员
		if("0".equals(accCancelBean.getCheckResult())){
			//弹框提示代理人不能为本行员工
			openConfirmDialog("本行在职员工不能办理代理人业务,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//跳转代理人身份证插入页
					openPanel(comp, new AccCancelInputAgentIdCardPanel());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//进入代理人选择页
					openPanel(comp, new AccCancelDeputySelectionPanel());
				}
				
			});
			return;
		}	
		
		//查询黑灰名单
		if(!agentCheckTelephoneFraud(comp)){
			return;
		}
		
		//身份证联网核查
		if(!agentCheckIdCardInfo(comp)){
			return;
		}
		
		//代理人
		logger.info("进入拍照页面");
		openPanel(comp, new AccCancelCameraPanel());
		
	}

	/**
	 * 转入账户信息查询
	 */
	public void checkCard(final Component comp){
		
		if ("002".equals(accCancelBean.getAccCancelType())) {
			//电子账户校验转入电子账户信息
			logger.info("开始校验转入的电子账户信息");
		}else if("0".equals(accCancelBean.getSubAccNoCancel())){
			//卡下子账户校验转入银行卡的账户信息
			logger.info("开始校验卡下子账户转入银行卡的账户信息");
		}else{
			//普通账户校验转入银行卡的账户信息
			logger.info("开始校验普通账户转入银行卡的账户信息");
		}
		
		//非电子账户调卡信息
		if (!"002".equals(accCancelBean.getAccCancelType())) {
			// 黑灰名单查询
			if(!checkCardTelephoneFraud(comp)){
				return;
			}
			
			//查询卡信息
			accCancelBean.setCardIsPin("0");//不验密
			
			if(!haveBillCheckCardMsg(comp)){
				return;
			}
			if(!haveBillCardAcct(comp)){
				return;
			}
			if(!haveBillCardAcct1(comp)){
				return;
			}
			
		//电子账户直接赋值	
		}else if(accCancelBean.getAccName()!=null && !"".equals(accCancelBean.getAccName().trim())){
			
			accCancelBean.setCardName(accCancelBean.getAccName().trim());//户名
			String cardName="*"+accCancelBean.getAccName().trim().substring(1);
			accCancelBean.setCardNames(cardName);
			
		}else{
			
			accCancelBean.setCardName("");
			accCancelBean.setCardNames("");
		}
		
		//判断卡信息
		if ("002".equals(accCancelBean.getAccCancelType())) {
			
		}else if("0".equals(accCancelBean.getSubAccNoCancel())){
			
		}else{
			//普通账户校验转入银行卡的账户信息
			try {
				
				if(accCancelBean.getBillIdNo().equals(accCancelBean.getIdNo()) && accCancelBean.getBillIdName().equals(accCancelBean.getIdName())){
					
				}else{
					logger.info("非本人银行卡，请插入或输入本人银行卡");
					//选择重新插入银行卡或输入银行卡号
					openConfirmDialog("非本人银行卡，是否继续：是：请重新插入银行卡或输入银行卡号。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//重新插入银行卡或输入银行卡号
							if("1".equals(accCancelBean.getInCardOrHandCard())){
								//退卡
								try {
									quitBankCard();
								} catch (Exception e1) {
									logger.error("退出银行卡失败"+e1);
								}
								openPanel(comp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
								
							}else{
								openPanel(comp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							}
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//退出存单页
							openPanel(comp, new AccCancelOutputDepositPanel());
						}
						
					});
					return;
				}
				
			} catch (Exception e) {
				logger.error("比对卡与存单客户信息失败"+e);
				serverStop(comp, "抱歉，客户信息校验失败", "","");
				return;
			}
		}
		
		//预计利息查询
		if(!yjLx(comp)){
			return;
		}
		
		//普通存单无密码
		if("0".equals(accCancelBean.getNoPass())){
			accCancelBean.setBillPass("");//存单密码
			//进入电子签名存单信息确认页
			openPanel(comp,new AccCancelDepositAffirmSignaPanel());
			return;
		}
		
		//进入输入密码页
		//有存单销户
		if("0".equals(accCancelBean.getHaveAcc())){
			//跳转至销户输入密码页(电子账户、卡下子账户、普通账户)
			openPanel(comp,new AccCancelsubAccBankorBillPassPanel());
			
		}else{//无存单销户
			if("0".equals(accCancelBean.getSubAccNoCancel())){
				//卡下子账户跳转至销户输入密码页(卡下子账户)
				openPanel(comp,new AccCancelDepositAffirmSignaPanel());
			}else{
				//电子子账户、普通账户跳转至销户输入密码页(电子账户、普通账户)
				openPanel(comp,new AccCancelsubAccBankorBillPassPanel());
			}
			
		}
	}
	
	/**
	 * 销户密码校验
	 */
	public void checkPass(final Component comp){
		openProssDialog();
		//调接口校验密码
		PublicAccCancelBean bean = new PublicAccCancelBean();
		if("002".equals(accCancelBean.getAccCancelType())){
			
			bean.setAccNo(accCancelBean.getCardNo());//电子账号
			bean.setBillPass(accCancelBean.getBillPass());
			
		}else if("0".equals(accCancelBean.getSubAccNoCancel())){
			
			bean.setAccNo(accCancelBean.getCardNo());//银行卡号
			bean.setBillPass(accCancelBean.getCardPwd());
			
		}else{
			
			bean.setAccNo(accCancelBean.getAccNo());//存单账号
			bean.setBillPass(accCancelBean.getBillPass());
		}
		bean.setIsCheckPass("1");//验密
		bean.setBillType("");
		bean.setBillNo("");
		try {
			accCancelBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = IntefaceSendMsg.inter07601(bean);
			accCancelBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
			if(!"000000".equals(inter07601.get("resCode"))){
				
				if (((String) inter07601.get("errMsg")).startsWith("A102")) {

					prossDialog.disposeDialog();
					logger.info(((String) inter07601.get("errMsg")));
					//选择重新输入密码或者退出
					openConfirmDialog("输入密码错误,请重新输入：是：重新输入密码。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//重新输入密码
							openPanel(comp, new AccCancelsubAccBankorBillPassPanel());//录入密码页
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//退出
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
							MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							if("0".equals(accCancelBean.getHaveAcc())){
								//有存单
								openPanel(comp, new  AccCancelOutputDepositPanel());//返回退存单页
							}else{
								//无存单
								returnHome();
							}
						}
						
					});
					return;
					
				} else if (((String) inter07601.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info(inter07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(comp, "您的密码输入次数已达上限，卡已经锁死。",(String) inter07601.get("errMsg"),"");
					return;
					
				}else{
					prossDialog.disposeDialog();
					logger.info(inter07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(comp, "存单密码校验失败，请联系大堂经理", (String)inter07601.get("errMsg"),"");
					return;
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
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
			serverStop(comp,"存单密码校验失败","调用07601存单账号信息查询失败","");
			return;
		}
		prossDialog.disposeDialog();
		//进入电子签名存单信息确认页
		openPanel(comp, new AccCancelDepositAffirmSignaPanel());
	}
	
	/**
	 * 销户处理
	 */
	public void xhBusinessDeal(final Component comp){
		
		openProssDialog();
		if("002".equals(accCancelBean.getAccCancelType())){//电子子账户销处理
			
			
			logger.info("开始电子子账户销户处理");
			if(!aCCXh(comp)){
				return;
			}
			
			//有存单则背书打印、回收
			if("0".equals(accCancelBean.getHaveAcc())){
				
				//背书打印
				sleep(1000);
				try {
					print();
				} catch (Exception e1) {
					logger.error("背书打印失败"+e1);
				}
				
				//回收存单
				sleep(1000);
				try {
					hdProcess();
				} catch (Exception e1) {
					logger.error("回收存单失败"+e1);
				}
				//存单开关关闭
				GlobalParameter.ACC_STATUS = false;//已无存单
			}
			
			//跳转是否打印凭条
			isPrint(comp);
			
		}else{//卡下子账户、普通账户销户处理
			
			
			if("0".equals(accCancelBean.getSubAccNoCancel())){
				//卡下子账户
				logger.info("开始卡下子账户销户处理");
			}else{
				//普通账户
				logger.info("开始普通账户销户处理");
			}
			
			//调用销户接口
			if(!aCCXh(comp)){
				return;
			}
			
			//唐豆收回查询
			tdCheck(comp);
			
			if(!"0.00".equals(accCancelBean.getShtdy())){//只有当accCancelBean.getShtdy()的值不为"0.00"时调唐豆收回
				//执行唐豆收回
				tdGetBack(comp);
			}
			
			//有存单则背书打印、回收
			if("0".equals(accCancelBean.getHaveAcc())){
				
				//背书打印
				sleep(1000);
				try {
					print();
				} catch (Exception e1) {
					logger.error("背书打印失败"+e1);
				}
				
				//回收存单
				sleep(1000);
				try {
					hdProcess();
				} catch (Exception e1) {
					logger.error("回收存单失败"+e1);
				}
				//存单开关关闭
				GlobalParameter.ACC_STATUS = false;//已无存单
			}
			
			
			if("003".equals(accCancelBean.getAccCancelType())){
				
				logger.info("选择转入唐行宝");
				//转入唐行宝金额
				if(accCancelBean.getRealPri()!=null && !"".equals(accCancelBean.getRealPri().trim()) && accCancelBean.getRealAmt()!=null && !"".equals(accCancelBean.getRealAmt().trim())){
					
					BigDecimal adIt = new BigDecimal("0.00");//扣回的幸运豆金额
					BigDecimal td = new BigDecimal("0.00");//扣回的唐豆金额
					BigDecimal xfd = new BigDecimal("0.00");//扣回的消费豆金额
					BigDecimal ins = new BigDecimal("0.00");//衍生品金额
					
					if(accCancelBean.getAdvnInit()!= null  && !"".equals(accCancelBean.getAdvnInit().trim())){
						
						adIt = new BigDecimal(accCancelBean.getAdvnInit().trim());
					}
					if(accCancelBean.getShtdy()!=null && !"".equals(accCancelBean.getShtdy().trim())){
						
						td = new BigDecimal(accCancelBean.getShtdy().trim());
					}
					if(accCancelBean.getXfdAmt()!=null&& !"".equals(accCancelBean.getXfdAmt().trim())){
						
						xfd = new BigDecimal(accCancelBean.getXfdAmt().trim());
					}	
					List<AccCancelProFileBean> InsList = accCancelBean.getImportMap().get("cancelProFile"); 
					if(InsList!=null && InsList.size()>0){
						for(int i=0;i<InsList.size();i++){
							ins=ins.add(new BigDecimal(InsList.get(i).getGetProSjAmt()));
							logger.info(ins);
						}
					}
					BigDecimal bd = new BigDecimal(accCancelBean.getRealPri().trim());
					BigDecimal bd1 = new BigDecimal(accCancelBean.getRealAmt());
					String str = String.valueOf(bd.add(bd1).subtract(adIt).subtract(td).subtract(xfd).subtract(ins)).trim();
					logger.info(str);
					accCancelBean.setTxbInputAmt(str);//本息一起转入唐行宝
				}
				
				//判断选择转唐行宝
				if("0".equals(accCancelBean.getSubAccNoCancel())){
					
					//卡下子账户转唐行宝，判断是否签约唐行宝
					Map checkTxb = checkTxb(comp);
					if(!"000000".equals(checkTxb.get("resCode"))){
						//查询唐行宝失败，弹框提示
						accCancelBean.setAccCancelType("001");//转入银行卡标识
						openMistakeDialog("抱歉,"+(String)checkTxb.get("errMsg")+",销户金额已转入您的银行卡");
						mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								mistakeDialog.dispose();
								prossDialog.disposeDialog();
								//跳转是否打印凭条
								isPrint(comp);
							}
						});
						accCancelBean.getReqMCM001().setTransresult("2");
					}else {//唐行宝查询成功
						
						List<AccCancelTBMsgBean> list = (List<AccCancelTBMsgBean>)checkTxb.get(IntefaceSendMsg.TB_MSG);
						BigDecimal transferAmt = new BigDecimal(accCancelBean.getTxbInputAmt());//转入金额
						
						if(list.size()==1){//一个唐行宝账号
							
							BigDecimal a1 = new BigDecimal(list.get(0).getProTotalAmt());//产品总额度
							BigDecimal a2 = new BigDecimal(list.get(0).getProUsedAmt());//产品已使用额度
							BigDecimal a3 =  a1.subtract(a2);//产品剩余额度
							if(a1.compareTo(a2)==0){//已满报错
								//查询唐行宝失败，弹框提示
								accCancelBean.setAccCancelType("001");//转入银行卡标识
								openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
								mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mouseReleased(MouseEvent e) {
										mistakeDialog.dispose();
										prossDialog.disposeDialog();
										//跳转是否打印凭条
										isPrint(comp);
									}
								});
							}else if(transferAmt.compareTo(a3)>0){
								//查询唐行宝失败，弹框提示
								accCancelBean.setAccCancelType("001");//转入银行卡标识
								openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
								mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mouseReleased(MouseEvent e) {
										mistakeDialog.dispose();
										prossDialog.disposeDialog();
										//跳转是否打印凭条
										isPrint(comp);
									}
								});
							}else {
								//未满或转入金额小于等于剩余额度，可转入
								accCancelBean.setTxbSubAccNo(list.get(0).getSubAcctNo());
								accCancelBean.setTxbProCode(list.get(0).getProdectCode());
									
								logger.info("可转入的唐行宝子账户:"+list.get(0).getSubAcctNo());
								logger.info("可转入的唐行宝产品代码:"+list.get(0).getProdectCode());
								
								//调入唐行宝
								logger.info("调用转入唐行宝");
								txb(comp);
							}
							
						}else if(list.size()==2){//两个唐行宝账号
							
							String haveA = "";
							
							for (int i = 0; i < list.size(); i++) {//循环找A
								
								if(list.get(i).getProdectCode().startsWith("TBA")){
									
									haveA = "A";
									
									BigDecimal a1 = new BigDecimal(list.get(i).getProTotalAmt());//A产品总额度
									BigDecimal a2 = new BigDecimal(list.get(i).getProUsedAmt());//A产品已使用额度
									BigDecimal a3 =  a1.subtract(a2);//A产品剩余额度
									
									if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//A池已满或转入金额大于剩余额度，去找B池或C池
										
										for (int j = 0; j < list.size(); j++) {//循环找B或者C 
										
											if(list.get(j).getProdectCode().startsWith("TBB") || list.get(j).getProdectCode().startsWith("TBC")){
												
												BigDecimal x1 = new BigDecimal(list.get(j).getProTotalAmt());//B或C产品总额度
												BigDecimal x2 = new BigDecimal(list.get(j).getProUsedAmt());//B或C产品已使用额度
												BigDecimal x3 =  x1.subtract(x2);//B或C产品剩余额度
												
												if(x1.compareTo(x2)==0){//B或C池已满报错
													//查询唐行宝失败，弹框提示
													accCancelBean.setAccCancelType("001");//转入银行卡标识
													openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
													mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
														
														@Override
														public void mouseReleased(MouseEvent e) {
															mistakeDialog.dispose();
															prossDialog.disposeDialog();
															//跳转是否打印凭条
															isPrint(comp);
														}
													});
												}else if(transferAmt.compareTo(x3)>0){//B或C池转入金额大于剩余额度
													//查询唐行宝失败，弹框提示
													accCancelBean.setAccCancelType("001");//转入银行卡标识
													openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
													mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
														
														@Override
														public void mouseReleased(MouseEvent e) {
															mistakeDialog.dispose();
															prossDialog.disposeDialog();
															//跳转是否打印凭条
															isPrint(comp);
														}
													});
												}else {
													//B或C池未满或转入金额小于等于剩余额度，可转入
													accCancelBean.setTxbSubAccNo(list.get(j).getSubAcctNo());
													accCancelBean.setTxbProCode(list.get(j).getProdectCode());
													
													logger.info("可转入的唐行宝子账户:"+list.get(j).getSubAcctNo());
													logger.info("可转入的唐行宝产品代码:"+list.get(j).getProdectCode());
													
													//调入唐行宝
													logger.info("调用转入唐行宝");
													txb(comp);
												}
											}
										}
										
									}else{
										//A池未满或转入金额小于等于剩余额度，可转入
										accCancelBean.setTxbSubAccNo(list.get(i).getSubAcctNo());
										accCancelBean.setTxbProCode(list.get(i).getProdectCode());
										
										logger.info("可转入的唐行宝子账户:"+list.get(i).getSubAcctNo());
										logger.info("可转入的唐行宝产品代码:"+list.get(i).getProdectCode());
										
										//调入唐行宝
										logger.info("调用转入唐行宝");
										txb(comp);
									}
								}
							}
							
							if("".equals(haveA)){//未找到A
								
								for (int x = 0; x < list.size(); x++) {//循环找B
									
									if(list.get(x).getProdectCode().startsWith("TBB")){
										
										BigDecimal a1 = new BigDecimal(list.get(x).getProTotalAmt());//B产品总额度
										BigDecimal a2 = new BigDecimal(list.get(x).getProUsedAmt());//B产品已使用额度
										BigDecimal a3 =  a1.subtract(a2);//B产品剩余额度
										
										if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//B池已满或转入金额大于剩余额度，去找C池
											
											for (int y = 0; y < list.size(); y++) {//循环找C 
											
												if(list.get(y).getProdectCode().startsWith("TBC")){
													
													BigDecimal x1 = new BigDecimal(list.get(y).getProTotalAmt());//C产品总额度
													BigDecimal x2 = new BigDecimal(list.get(y).getProUsedAmt());//C产品已使用额度
													BigDecimal x3 =  x1.subtract(x2);//C产品剩余额度
													
													if(x1.compareTo(x2)==0){//C池已满报错
														//查询唐行宝失败，弹框提示
														accCancelBean.setAccCancelType("001");//转入银行卡标识
														openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
														mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
															
															@Override
															public void mouseReleased(MouseEvent e) {
																mistakeDialog.dispose();
																prossDialog.disposeDialog();
																//跳转是否打印凭条
																isPrint(comp);
															}
														});
													}else if(transferAmt.compareTo(x3)>0){//B或C池转入金额大于剩余额度
														//查询唐行宝失败，弹框提示
														accCancelBean.setAccCancelType("001");//转入银行卡标识
														openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
														mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
															
															@Override
															public void mouseReleased(MouseEvent e) {
																mistakeDialog.dispose();
																prossDialog.disposeDialog();
																//跳转是否打印凭条
																isPrint(comp);
															}
														});
													}else {
														//C池未满或转入金额小于等于剩余额度，可转入
														accCancelBean.setTxbSubAccNo(list.get(y).getSubAcctNo());
														accCancelBean.setTxbProCode(list.get(y).getProdectCode());
														
														logger.info("可转入的唐行宝子账户:"+list.get(y).getSubAcctNo());
														logger.info("可转入的唐行宝产品代码:"+list.get(y).getProdectCode());
														
														//调入唐行宝
														logger.info("调用转入唐行宝");
														txb(comp);
													}
												}
											}
											
										}else{
											//B池未满或转入金额小于等于剩余额度，可转入
											accCancelBean.setTxbSubAccNo(list.get(x).getSubAcctNo());
											accCancelBean.setTxbProCode(list.get(x).getProdectCode());
											
											logger.info("可转入的唐行宝子账户:"+list.get(x).getSubAcctNo());
											logger.info("可转入的唐行宝产品代码:"+list.get(x).getProdectCode());
											
											//调入唐行宝
											logger.info("调用转入唐行宝");
											txb(comp);
										}
									}
								}
							}
							
						}else if(list.size()==3){//三个唐行宝账号
							
							for (int i = 0; i < list.size(); i++) {//循环找A
								
								if(list.get(i).getProdectCode().startsWith("TBA")){
									
									BigDecimal a1 = new BigDecimal(list.get(i).getProTotalAmt());//A产品总额度
									BigDecimal a2 = new BigDecimal(list.get(i).getProUsedAmt());//A产品已使用额度
									BigDecimal a3 =  a1.subtract(a2);//A产品剩余额度
									
									if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//A池已满或转入金额大于剩余额度，去找B池
										
										for (int j = 0; j < list.size(); j++) {//循环找B
										
											if(list.get(j).getProdectCode().startsWith("TBB")){
												
												BigDecimal b1 = new BigDecimal(list.get(j).getProTotalAmt());//B产品总额度
												BigDecimal b2 = new BigDecimal(list.get(j).getProUsedAmt());//B产品已使用额度
												BigDecimal b3 =  b1.subtract(b2);//B产品剩余额度
												
												if(b1.compareTo(b2)==0 || transferAmt.compareTo(b3)>0){//B池已满或转入金额大于剩余额度，去找C池
													
													for (int x = 0; x < list.size(); x++) {//循环找C
														
														if(list.get(x).getProdectCode().startsWith("TBC")){
															
															BigDecimal c1 = new BigDecimal(list.get(x).getProTotalAmt());//C产品总额度
															BigDecimal c2 = new BigDecimal(list.get(x).getProUsedAmt());//C产品已使用额度
															BigDecimal c3 =  c1.subtract(c2);//C产品剩余额度
															
															if(c1.compareTo(c2)==0){//C池已满报错
																//查询唐行宝失败，弹框提示
																accCancelBean.setAccCancelType("001");//转入银行卡标识
																openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
																mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
																	
																	@Override
																	public void mouseReleased(MouseEvent e) {
																		mistakeDialog.dispose();
																		prossDialog.disposeDialog();
																		//跳转是否打印凭条
																		isPrint(comp);
																	}
																});
															}else if(transferAmt.compareTo(c3)>0){//C池转入金额大于剩余额度
																//查询唐行宝失败，弹框提示
																accCancelBean.setAccCancelType("001");//转入银行卡标识
																openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
																mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
																	
																	@Override
																	public void mouseReleased(MouseEvent e) {
																		mistakeDialog.dispose();
																		prossDialog.disposeDialog();
																		//跳转是否打印凭条
																		isPrint(comp);
																	}
																});
															}else {
																//C池未满或转入金额小于等于剩余额度，可转入
																accCancelBean.setTxbSubAccNo(list.get(x).getSubAcctNo());
																accCancelBean.setTxbProCode(list.get(x).getProdectCode());
																
																logger.info("可转入的唐行宝子账户:"+list.get(x).getSubAcctNo());
																logger.info("可转入的唐行宝产品代码:"+list.get(x).getProdectCode());
																
																//调入唐行宝
																logger.info("调用转入唐行宝");
																txb(comp);
															}
														}
													}
													
												}else{
													//B池未满或转入金额小于等于剩余额度，可转入
													accCancelBean.setTxbSubAccNo(list.get(j).getSubAcctNo());
													accCancelBean.setTxbProCode(list.get(j).getProdectCode());
													
													logger.info("可转入的唐行宝子账户:"+list.get(j).getSubAcctNo());
													logger.info("可转入的唐行宝产品代码:"+list.get(j).getProdectCode());
													
													//调入唐行宝
													logger.info("调用转入唐行宝");
													txb(comp);
												}
											}
										}
										
									}else{
										//A池未满或转入金额小于等于剩余额度，可转入
										accCancelBean.setTxbSubAccNo(list.get(i).getSubAcctNo());
										accCancelBean.setTxbProCode(list.get(i).getProdectCode());
										
										logger.info("可转入的唐行宝子账户:"+list.get(i).getSubAcctNo());
										logger.info("可转入的唐行宝产品代码:"+list.get(i).getProdectCode());
										
										//调入唐行宝
										logger.info("调用转入唐行宝");
										txb(comp);
									}
								}
							}
						}
					}
					
				}else{
					
					//普通账户转唐行宝，进入银行卡密码输入页
					openPanel(comp, new AccCancelBankPassPanel());//银行卡密码输入页
				}
				
				
			}else{
				
				logger.info("未选择转入唐行宝，直接转卡");
				//未选择唐行宝，直接转入银行卡
				//跳转是否打印凭条
				isPrint(comp);
			}
		}
	}
	
	
	/**
	 * 转入唐行宝
	 */
	public void txb(final Component comp){
		Map<String, String> transferTxb = transferTxb(comp);
		if(!"000000".equals(transferTxb.get("resCode"))){
			if("0".equals(accCancelBean.getSubAccNoCancel())){
				
				accCancelBean.setAccCancelType("001");//转入银行卡标识
				//转入唐行宝失败，弹框提示
				openMistakeDialog("抱歉,转入唐行宝失败,"+(String)transferTxb.get("errMsg")+",销户金额已转入您的银行卡");
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						mistakeDialog.dispose();
						prossDialog.disposeDialog();
						//跳转是否打印凭条
						isPrint(comp);
					}
					
				});
			}else{
				if (((String) transferTxb.get("errMsg")).startsWith("A102")) {

					logger.info(((String) transferTxb.get("errMsg")));
					//选择重新输入密码或者退出
					openConfirmDialog("输入密码错误,是否重新输入：是：重新输入密码。否：销户金额已转入您的银行卡。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//重新输入密码
							openPanel(comp, new AccCancelBankPassPanel());//转入唐行宝，录入密码页
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							accCancelBean.setAccCancelType("001");//转入银行卡标识
							//跳转是否打印凭条
							isPrint(comp);
						}
						
					});
					
				} else if (((String) transferTxb.get("errMsg")).startsWith("A103")) {

					accCancelBean.setAccCancelType("001");//转入银行卡标识
					//转入唐行宝失败，弹框提示
					openMistakeDialog("抱歉,转入唐行宝失败,您的密码输入次数已达上限,销户金额已转入您的银行卡");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							prossDialog.disposeDialog();
							//跳转是否打印凭条
							isPrint(comp);
						}
						
					});
					
				}else{
					accCancelBean.setAccCancelType("001");//转入银行卡标识
					//转入唐行宝失败，弹框提示
					openMistakeDialog("抱歉,转入唐行宝失败,"+(String)transferTxb.get("errMsg")+",销户金额已转入您的银行卡");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							prossDialog.disposeDialog();
							//跳转是否打印凭条
							isPrint(comp);
						}
						
					});
				}
			}
			accCancelBean.getReqMCM001().setTransresult("2");
		}else{
			
			//唐行宝转入成功
			logger.info("销户金额已成功转入唐行宝");
			accCancelBean.getReqMCM001().setToaccount(accCancelBean.getCardNo()+accCancelBean.getTxbSubAccNo());//转入唐行宝账号
			//弹框提示是否打印凭条
			openConfirmDialog("销户金额已转入唐行宝，是否打印凭条:是：打印。否：不打印。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					confirmDialog.dispose();
					prossDialog.disposeDialog();
					//跳转凭条打印处理页
					openPanel(comp, new AccCancelPtPrintProcessingPanel());//凭条打印页
				}
				
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					confirmDialog.dispose();
					prossDialog.disposeDialog();
					//上传事后监督
					uploadPhoto();
					//进入销户成功页
					openPanel(comp, new AccCancelSuccessPanel());//销户成功页面
				}
			});
		}
	}
	
	/**
	 * 输入密码后，唐行宝签约查询
	 */
	public void txbCheck(final Component comp){
		
		//银行卡密码查询
		
		//卡下子账户转唐行宝，判断是否签约唐行宝
		Map checkTxb = checkTxb(comp);
		if(!"000000".equals(checkTxb.get("resCode"))){
			
			//查询唐行宝失败，弹框提示
			accCancelBean.setAccCancelType("001");//转入银行卡标识
			openMistakeDialog("抱歉,"+(String)checkTxb.get("errMsg")+",销户金额已转入您的银行卡");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					mistakeDialog.dispose();
					prossDialog.disposeDialog();
					//跳转是否打印凭条
					isPrint(comp);
				}
			});
			accCancelBean.getReqMCM001().setTransresult("2");
		
		}else {//唐行宝查询成功
			
			List<AccCancelTBMsgBean> list = (List<AccCancelTBMsgBean>)checkTxb.get(IntefaceSendMsg.TB_MSG);
			BigDecimal transferAmt = new BigDecimal(accCancelBean.getTxbInputAmt());//转入金额
			
			if(list.size()==1){//一个唐行宝账号
				
				BigDecimal a1 = new BigDecimal(list.get(0).getProTotalAmt());//产品总额度
				BigDecimal a2 = new BigDecimal(list.get(0).getProUsedAmt());//产品已使用额度
				BigDecimal a3 =  a1.subtract(a2);//产品剩余额度
				if(a1.compareTo(a2)==0){//已满报错
					//查询唐行宝失败，弹框提示
					accCancelBean.setAccCancelType("001");//转入银行卡标识
					openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							prossDialog.disposeDialog();
							//跳转是否打印凭条
							isPrint(comp);
						}
					});
				}else if(transferAmt.compareTo(a3)>0){
					//查询唐行宝失败，弹框提示
					accCancelBean.setAccCancelType("001");//转入银行卡标识
					openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							prossDialog.disposeDialog();
							//跳转是否打印凭条
							isPrint(comp);
						}
					});
				}else {
					//未满或转入金额小于等于剩余额度，可转入
					accCancelBean.setTxbSubAccNo(list.get(0).getSubAcctNo());
					accCancelBean.setTxbProCode(list.get(0).getProdectCode());
						
					logger.info("可转入的唐行宝子账户:"+list.get(0).getSubAcctNo());
					logger.info("可转入的唐行宝产品代码:"+list.get(0).getProdectCode());
					
					//调入唐行宝
					logger.info("调用转入唐行宝");
					txb(comp);
				}
				
			}else if(list.size()==2){//两个唐行宝账号
				
				String haveA = "";
				
				for (int i = 0; i < list.size(); i++) {//循环找A
					
					if(list.get(i).getProdectCode().startsWith("TBA")){
						
						haveA = "A";
						
						BigDecimal a1 = new BigDecimal(list.get(i).getProTotalAmt());//A产品总额度
						BigDecimal a2 = new BigDecimal(list.get(i).getProUsedAmt());//A产品已使用额度
						BigDecimal a3 =  a1.subtract(a2);//A产品剩余额度
						
						if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//A池已满或转入金额大于剩余额度，去找B池或C池
							
							for (int j = 0; j < list.size(); j++) {//循环找B或者C 
							
								if(list.get(j).getProdectCode().startsWith("TBB") || list.get(j).getProdectCode().startsWith("TBC")){
									
									BigDecimal x1 = new BigDecimal(list.get(j).getProTotalAmt());//B或C产品总额度
									BigDecimal x2 = new BigDecimal(list.get(j).getProUsedAmt());//B或C产品已使用额度
									BigDecimal x3 =  x1.subtract(x2);//B或C产品剩余额度
									
									if(x1.compareTo(x2)==0){//B或C池已满报错
										//查询唐行宝失败，弹框提示
										accCancelBean.setAccCancelType("001");//转入银行卡标识
										openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
										mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
											
											@Override
											public void mouseReleased(MouseEvent e) {
												mistakeDialog.dispose();
												prossDialog.disposeDialog();
												//跳转是否打印凭条
												isPrint(comp);
											}
										});
									}else if(transferAmt.compareTo(x3)>0){//B或C池转入金额大于剩余额度
										//查询唐行宝失败，弹框提示
										accCancelBean.setAccCancelType("001");//转入银行卡标识
										openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
										mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
											
											@Override
											public void mouseReleased(MouseEvent e) {
												mistakeDialog.dispose();
												prossDialog.disposeDialog();
												//跳转是否打印凭条
												isPrint(comp);
											}
										});
									}else {
										//B或C池未满或转入金额小于等于剩余额度，可转入
										accCancelBean.setTxbSubAccNo(list.get(j).getSubAcctNo());
										accCancelBean.setTxbProCode(list.get(j).getProdectCode());
										
										logger.info("可转入的唐行宝子账户:"+list.get(j).getSubAcctNo());
										logger.info("可转入的唐行宝产品代码:"+list.get(j).getProdectCode());
										
										//调入唐行宝
										logger.info("调用转入唐行宝");
										txb(comp);
									}
								}
							}
							
						}else{
							//A池未满或转入金额小于等于剩余额度，可转入
							accCancelBean.setTxbSubAccNo(list.get(i).getSubAcctNo());
							accCancelBean.setTxbProCode(list.get(i).getProdectCode());
							
							logger.info("可转入的唐行宝子账户:"+list.get(i).getSubAcctNo());
							logger.info("可转入的唐行宝产品代码:"+list.get(i).getProdectCode());
							
							//调入唐行宝
							logger.info("调用转入唐行宝");
							txb(comp);
						}
					}
				}
				
				if("".equals(haveA)){//未找到A
					
					for (int x = 0; x < list.size(); x++) {//循环找B
						
						if(list.get(x).getProdectCode().startsWith("TBB")){
							
							BigDecimal a1 = new BigDecimal(list.get(x).getProTotalAmt());//B产品总额度
							BigDecimal a2 = new BigDecimal(list.get(x).getProUsedAmt());//B产品已使用额度
							BigDecimal a3 =  a1.subtract(a2);//B产品剩余额度
							
							if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//B池已满或转入金额大于剩余额度，去找C池
								
								for (int y = 0; y < list.size(); y++) {//循环找C 
								
									if(list.get(y).getProdectCode().startsWith("TBC")){
										
										BigDecimal x1 = new BigDecimal(list.get(y).getProTotalAmt());//C产品总额度
										BigDecimal x2 = new BigDecimal(list.get(y).getProUsedAmt());//C产品已使用额度
										BigDecimal x3 =  x1.subtract(x2);//C产品剩余额度
										
										if(x1.compareTo(x2)==0){//C池已满报错
											//查询唐行宝失败，弹框提示
											accCancelBean.setAccCancelType("001");//转入银行卡标识
											openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
											mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
												
												@Override
												public void mouseReleased(MouseEvent e) {
													mistakeDialog.dispose();
													prossDialog.disposeDialog();
													//跳转是否打印凭条
													isPrint(comp);
												}
											});
										}else if(transferAmt.compareTo(x3)>0){//B或C池转入金额大于剩余额度
											//查询唐行宝失败，弹框提示
											accCancelBean.setAccCancelType("001");//转入银行卡标识
											openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
											mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
												
												@Override
												public void mouseReleased(MouseEvent e) {
													mistakeDialog.dispose();
													prossDialog.disposeDialog();
													//跳转是否打印凭条
													isPrint(comp);
												}
											});
										}else {
											//C池未满或转入金额小于等于剩余额度，可转入
											accCancelBean.setTxbSubAccNo(list.get(y).getSubAcctNo());
											accCancelBean.setTxbProCode(list.get(y).getProdectCode());
											
											logger.info("可转入的唐行宝子账户:"+list.get(y).getSubAcctNo());
											logger.info("可转入的唐行宝产品代码:"+list.get(y).getProdectCode());
											
											//调入唐行宝
											logger.info("调用转入唐行宝");
											txb(comp);
										}
									}
								}
								
							}else{
								//B池未满或转入金额小于等于剩余额度，可转入
								accCancelBean.setTxbSubAccNo(list.get(x).getSubAcctNo());
								accCancelBean.setTxbProCode(list.get(x).getProdectCode());
								
								logger.info("可转入的唐行宝子账户:"+list.get(x).getSubAcctNo());
								logger.info("可转入的唐行宝产品代码:"+list.get(x).getProdectCode());
								
								//调入唐行宝
								logger.info("调用转入唐行宝");
								txb(comp);
							}
						}
					}
				}
				
			}else if(list.size()==3){//三个唐行宝账号
				
				for (int i = 0; i < list.size(); i++) {//循环找A
					
					if(list.get(i).getProdectCode().startsWith("TBA")){
						
						BigDecimal a1 = new BigDecimal(list.get(i).getProTotalAmt());//A产品总额度
						BigDecimal a2 = new BigDecimal(list.get(i).getProUsedAmt());//A产品已使用额度
						BigDecimal a3 =  a1.subtract(a2);//A产品剩余额度
						
						if(a1.compareTo(a2)==0 || transferAmt.compareTo(a3)>0){//A池已满或转入金额大于剩余额度，去找B池
							
							for (int j = 0; j < list.size(); j++) {//循环找B
							
								if(list.get(j).getProdectCode().startsWith("TBB")){
									
									BigDecimal b1 = new BigDecimal(list.get(j).getProTotalAmt());//B产品总额度
									BigDecimal b2 = new BigDecimal(list.get(j).getProUsedAmt());//B产品已使用额度
									BigDecimal b3 =  b1.subtract(b2);//B产品剩余额度
									
									if(b1.compareTo(b2)==0 || transferAmt.compareTo(b3)>0){//B池已满或转入金额大于剩余额度，去找C池
										
										for (int x = 0; x < list.size(); x++) {//循环找C
											
											if(list.get(x).getProdectCode().startsWith("TBC")){
												
												BigDecimal c1 = new BigDecimal(list.get(x).getProTotalAmt());//C产品总额度
												BigDecimal c2 = new BigDecimal(list.get(x).getProUsedAmt());//C产品已使用额度
												BigDecimal c3 =  c1.subtract(c2);//C产品剩余额度
												
												if(c1.compareTo(c2)==0){//C池已满报错
													//查询唐行宝失败，弹框提示
													accCancelBean.setAccCancelType("001");//转入银行卡标识
													openMistakeDialog("抱歉,唐行宝产品额度值已满,销户金额已转入您的银行卡");
													mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
														
														@Override
														public void mouseReleased(MouseEvent e) {
															mistakeDialog.dispose();
															prossDialog.disposeDialog();
															//跳转是否打印凭条
															isPrint(comp);
														}
													});
												}else if(transferAmt.compareTo(c3)>0){//C池转入金额大于剩余额度
													//查询唐行宝失败，弹框提示
													accCancelBean.setAccCancelType("001");//转入银行卡标识
													openMistakeDialog("抱歉,转入金额已超过唐行宝产品剩余额度值,销户金额已转入您的银行卡");
													mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
														
														@Override
														public void mouseReleased(MouseEvent e) {
															mistakeDialog.dispose();
															prossDialog.disposeDialog();
															//跳转是否打印凭条
															isPrint(comp);
														}
													});
												}else {
													//C池未满或转入金额小于等于剩余额度，可转入
													accCancelBean.setTxbSubAccNo(list.get(x).getSubAcctNo());
													accCancelBean.setTxbProCode(list.get(x).getProdectCode());
													
													logger.info("可转入的唐行宝子账户:"+list.get(x).getSubAcctNo());
													logger.info("可转入的唐行宝产品代码:"+list.get(x).getProdectCode());
													
													//调入唐行宝
													logger.info("调用转入唐行宝");
													txb(comp);
												}
											}
										}
										
									}else{
										//B池未满或转入金额小于等于剩余额度，可转入
										accCancelBean.setTxbSubAccNo(list.get(j).getSubAcctNo());
										accCancelBean.setTxbProCode(list.get(j).getProdectCode());
										
										logger.info("可转入的唐行宝子账户:"+list.get(j).getSubAcctNo());
										logger.info("可转入的唐行宝产品代码:"+list.get(j).getProdectCode());
										
										//调入唐行宝
										logger.info("调用转入唐行宝");
										txb(comp);
									}
								}
							}
							
						}else{
							//A池未满或转入金额小于等于剩余额度，可转入
							accCancelBean.setTxbSubAccNo(list.get(i).getSubAcctNo());
							accCancelBean.setTxbProCode(list.get(i).getProdectCode());
							
							logger.info("可转入的唐行宝子账户:"+list.get(i).getSubAcctNo());
							logger.info("可转入的唐行宝产品代码:"+list.get(i).getProdectCode());
							
							//调入唐行宝
							logger.info("调用转入唐行宝");
							txb(comp);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 打印销户信息
	 */
	public void print()throws Exception{
		//打印
		String zqr = accCancelBean.getSvrDate().trim();//支取日
		String bj = new ChangeStringFormat().StringCentered(11, accCancelBean.getRealPri());//实付本金
		String lx = new ChangeStringFormat().StringCentered(10, accCancelBean.getRealAmt());//实付利息
		
		/* 本息合计 */
		String Amt = accCancelBean.getRealPri();
		String pri = accCancelBean.getRealAmt();
		BigDecimal bd = new BigDecimal(Amt);
		BigDecimal bd1 = new BigDecimal(pri);
		String str = String.valueOf(bd.add(bd1)).trim();
		String bxhj = new ChangeStringFormat().StringCentered(11, str);
		String gyh = GlobalParameter.tellerNo.trim();//柜员号
		String dy = "  "+zqr+"/"+bj.trim()+"/"+lx.trim()+"/"+bxhj.trim()+"/"+gyh.trim();
		logger.debug("备注打印：BH_Print");
		Dispatch.call(BaseLoginFrame.dispath, "BH_Print",new Variant(dy));
		logger.debug("打印完睡10000ms等待程序响应");
    	sleep(10000);
	}
	
	/**
	 * 调用存单回收模块
	 */
	public void hdProcess()throws Exception{
		// 回收存单
		logger.debug("回收存单:BH_Store");
		Dispatch.call(BaseLoginFrame.dispath, "BH_Store",new Variant("1"));
		//清理资源
		Dispatch.call(BaseLoginFrame.dispath, "BH_CleanResource");
		logger.debug("睡200ms后开始执行退票到回收箱：BH_Retract");
		sleep(200);
		//退票到回收箱
		Dispatch.call(BaseLoginFrame.dispath, "BH_Retract");
	}
	
	/**
	 * 凭条打印处理
	 */
	public void xhPrint(Component comp){
		//上传事后监督
		uploadPhoto();
		
		//凭条打印
		XHPrintPt print = new XHPrintPt();
		print.print(accCancelBean);
		sleep(5);
		logger.info("睡眠５s,进入销户成功展现页面");
		
		//进入销户成功页面
		openPanel(comp, new AccCancelSuccessPanel());//销户成功页面
	}
	
	/**
	 * 销户接口
	 */
	public boolean aCCXh(final Component comp){
		if("002".equals(accCancelBean.getAccCancelType())){
			
			//电子账户销户接口
			logger.info("调用电子账户销户");
			try {
				accCancelBean.getReqMCM001().setReqBefor("03522");
				Map inter03522 = IntefaceSendMsg.inter03522(accCancelBean); 
				accCancelBean.getReqMCM001().setReqAfter((String)inter03522.get("resCode"), (String)inter03522.get("errMsg"));
				accCancelBean.getReqMCM001().setCentertrjnno(accCancelBean.getXHSvrJrnlNo());
				accCancelBean.getReqMCM001().setTransamt(accCancelBean.getAmount());
				accCancelBean.getReqMCM001().setTurnflag("1");
				if(!"000000".equals(inter03522.get("resCode"))){
					
					if (((String) inter03522.get("errMsg")).startsWith("A102")) {

						prossDialog.disposeDialog();
						logger.info(((String) inter03522.get("errMsg")));
						//选择重新输入密码或者退出
						openConfirmDialog("电子子账户销户失败,输入密码错误,请重新输入：是：重新输入密码。否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//重新输入密码
								openPanel(comp, new AccCancelsubAccBankorBillPassPanel());//录入密码页
							}
							
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//退出
								accCancelBean.getReqMCM001().setLendirection("");
								accCancelBean.getReqMCM001().setToaccount("");
								accCancelBean.getReqMCM001().setCustomername("");
								accCancelBean.getReqMCM001().setTonouns("");
								accCancelBean.getReqMCM001().setAccount("");
								accCancelBean.getReqMCM001().setRvouchertype("");
								accCancelBean.getReqMCM001().setRvoucherno("");
								accCancelBean.getReqMCM001().setCentertrjnno("");
								accCancelBean.getReqMCM001().setTransamt("");
								accCancelBean.getReqMCM001().setTurnflag("");
								MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
								if("0".equals(accCancelBean.getHaveAcc())){
									//有存单
									openPanel(comp, new  AccCancelOutputDepositPanel());//返回退存单页
								}else{
									//无存单
									returnHome();
								}
							}
							
						});
						return false;
						
					} else if (((String) inter03522.get("errMsg")).startsWith("A103")) {

						prossDialog.disposeDialog();
						logger.info(inter03522.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "电子子账户销户失败,您的密码输入次数已达上限，卡已经锁死。",(String) inter03522.get("errMsg"),"");
						return false;
						
					}else{
						prossDialog.disposeDialog();
						logger.info(inter03522.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "电子子账户销户失败，请联系大堂经理", (String)inter03522.get("errMsg"),"");
						return false;
					}
				}
				accCancelBean.getReqMCM001().setTransresult("0");
				accCancelBean.getReqMCM001().setIntereturnmsg("销户成功");
				
			} catch (Exception e) {
				
				prossDialog.disposeDialog();
				logger.error("调用03522电子子账户销户失败"+e);
				accCancelBean.getReqMCM001().setIntereturnmsg("调用03522接口异常");
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				accCancelBean.getReqMCM001().setCentertrjnno("");
				accCancelBean.getReqMCM001().setTransamt("");
				accCancelBean.getReqMCM001().setTurnflag("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "电子子账户销户失败，请联系大堂经理", "","调用03522接口异常!");
				return false;
			}
			return true;
			
		}else if("0".equals(accCancelBean.getSubAccNoCancel())){
			
			//卡下子账户销户接口
			logger.info("调用卡下子账户销户");
			try {
				accCancelBean.getReqMCM001().setReqBefor("03517");
				Map inter03517 = IntefaceSendMsg.inter03517(accCancelBean); 
				accCancelBean.getReqMCM001().setReqAfter((String)inter03517.get("resCode"), (String)inter03517.get("errMsg"));
				accCancelBean.getReqMCM001().setCentertrjnno(accCancelBean.getXHSvrJrnlNo());
				accCancelBean.getReqMCM001().setTransamt(accCancelBean.getAmount());
				accCancelBean.getReqMCM001().setTurnflag("1");
				if(!"000000".equals(inter03517.get("resCode"))){
					
					if (((String) inter03517.get("errMsg")).startsWith("A102")) {

						prossDialog.disposeDialog();
						logger.info(((String) inter03517.get("errMsg")));
						//选择重新输入密码或者退出
						openConfirmDialog("卡下子账户销户失败,输入密码错误,请重新输入：是：重新输入密码。否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//重新输入密码
								openPanel(comp, new AccCancelsubAccBankorBillPassPanel());//录入密码页
							}
							
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//退出
								accCancelBean.getReqMCM001().setLendirection("");
								accCancelBean.getReqMCM001().setToaccount("");
								accCancelBean.getReqMCM001().setCustomername("");
								accCancelBean.getReqMCM001().setTonouns("");
								accCancelBean.getReqMCM001().setAccount("");
								accCancelBean.getReqMCM001().setRvouchertype("");
								accCancelBean.getReqMCM001().setRvoucherno("");
								accCancelBean.getReqMCM001().setCentertrjnno("");
								accCancelBean.getReqMCM001().setTransamt("");
								accCancelBean.getReqMCM001().setTurnflag("");
								MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
								if("0".equals(accCancelBean.getHaveAcc())){
									//有存单
									openPanel(comp, new  AccCancelOutputDepositPanel());//返回退存单页
								}else{
									//无存单
									returnHome();
								}
							}
							
						});
						return false;
						
					} else if (((String) inter03517.get("errMsg")).startsWith("A103")) {

						prossDialog.disposeDialog();
						logger.info(inter03517.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "卡下子账户销户失败,您的密码输入次数已达上限，卡已经锁死。",(String) inter03517.get("errMsg"),"");
						return false;
						
					}else{
						
						prossDialog.disposeDialog();
						logger.info(inter03517.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "卡下子账户销户失败，请联系大堂经理", (String)inter03517.get("errMsg"),"");
						return false;
					}
				}
				accCancelBean.getReqMCM001().setTransresult("0");
				accCancelBean.getReqMCM001().setIntereturnmsg("销户成功");
				
			} catch (Exception e) {
				
				prossDialog.disposeDialog();
				logger.error("调用03517卡下子账户销户失败"+e);
				accCancelBean.getReqMCM001().setIntereturnmsg("调用03517接口异常");
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				accCancelBean.getReqMCM001().setCentertrjnno("");
				accCancelBean.getReqMCM001().setTransamt("");
				accCancelBean.getReqMCM001().setTurnflag("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "卡下子账户销户失败，请联系大堂经理", "","调用03517接口异常!");
				return false;
			}
			return true;
			
		}else{
			//普通账户销户接口
			logger.info("调用个人普通账户销户");
			try {
				accCancelBean.getReqMCM001().setReqBefor("07624");
				Map inter07624 = IntefaceSendMsg.inter07624(accCancelBean); 
				accCancelBean.getReqMCM001().setReqAfter((String)inter07624.get("resCode"), (String)inter07624.get("errMsg"));
				accCancelBean.getReqMCM001().setCentertrjnno(accCancelBean.getXHSvrJrnlNo());
				accCancelBean.getReqMCM001().setTransamt(accCancelBean.getAmount());
				accCancelBean.getReqMCM001().setTurnflag("1");
				if(!"000000".equals(inter07624.get("resCode"))){
					
					if (((String) inter07624.get("errMsg")).startsWith("A102")) {

						prossDialog.disposeDialog();
						logger.info(((String) inter07624.get("errMsg")));
						//选择重新输入密码或者退出
						openConfirmDialog("个人普通账户销户失败,输入密码错误,请重新输入：是：重新输入密码。否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//重新输入密码
								openPanel(comp, new AccCancelsubAccBankorBillPassPanel());//录入密码页
							}
							
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								confirmDialog.dispose();
								prossDialog.disposeDialog();
								//退出
								accCancelBean.getReqMCM001().setLendirection("");
								accCancelBean.getReqMCM001().setToaccount("");
								accCancelBean.getReqMCM001().setCustomername("");
								accCancelBean.getReqMCM001().setTonouns("");
								accCancelBean.getReqMCM001().setAccount("");
								accCancelBean.getReqMCM001().setRvouchertype("");
								accCancelBean.getReqMCM001().setRvoucherno("");
								accCancelBean.getReqMCM001().setCentertrjnno("");
								accCancelBean.getReqMCM001().setTransamt("");
								accCancelBean.getReqMCM001().setTurnflag("");
								MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
								if("0".equals(accCancelBean.getHaveAcc())){
									//有存单
									openPanel(comp, new  AccCancelOutputDepositPanel());//返回退存单页
								}else{
									//无存单
									returnHome();
								}
							}
							
						});
						return false;
						
					} else if (((String) inter07624.get("errMsg")).startsWith("A103")) {

						prossDialog.disposeDialog();
						logger.info(inter07624.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "个人普通账户销户失败,您的密码输入次数已达上限，卡已经锁死。",(String) inter07624.get("errMsg"),"");
						return false;
						
					}else{
						
						prossDialog.disposeDialog();
						logger.info(inter07624.get("errMsg"));
						accCancelBean.getReqMCM001().setLendirection("");
						accCancelBean.getReqMCM001().setToaccount("");
						accCancelBean.getReqMCM001().setCustomername("");
						accCancelBean.getReqMCM001().setTonouns("");
						accCancelBean.getReqMCM001().setAccount("");
						accCancelBean.getReqMCM001().setRvouchertype("");
						accCancelBean.getReqMCM001().setRvoucherno("");
						accCancelBean.getReqMCM001().setCentertrjnno("");
						accCancelBean.getReqMCM001().setTransamt("");
						accCancelBean.getReqMCM001().setTurnflag("");
						MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
						serverStop(comp, "个人普通账户销户失败，请联系大堂经理", (String)inter07624.get("errMsg"),"");
						return false;
					}
				}
				accCancelBean.getReqMCM001().setTransresult("0");
				accCancelBean.getReqMCM001().setIntereturnmsg("销户成功");
				
			} catch (Exception e) {
				
				prossDialog.disposeDialog();
				logger.error("调用07624个人普通账户销户失败"+e);
				accCancelBean.getReqMCM001().setIntereturnmsg("调用07624接口异常");
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				accCancelBean.getReqMCM001().setCentertrjnno("");
				accCancelBean.getReqMCM001().setTransamt("");
				accCancelBean.getReqMCM001().setTurnflag("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "个人普通账户销户失败，请联系大堂经理", "","调用07624接口异常!");
				return false;
			}
			return true;
		}
	}
	
	/**
	 * 本人身份证黑灰名单
	 */
	public boolean meCheckTelephoneFraud(Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("01597");
			Map me01597 = IntefaceSendMsg.me01597(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)me01597.get("resCode"), (String)me01597.get("errMsg"));
			if(!"000000".equals(me01597.get("resCode"))){
				
				if ("0010".equals(me01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
					
				} else if ("0020".equals(me01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
				}else{
					
					prossDialog.disposeDialog();
					logger.info(me01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)me01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 代理人身份证黑灰名单
	 */
	public boolean agentCheckTelephoneFraud(Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("01597");
			Map agent01597 = IntefaceSendMsg.agent01597(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)agent01597.get("resCode"), (String)agent01597.get("errMsg"));
			if(!"000000".equals(agent01597.get("resCode"))){
				
				if ("0010".equals(agent01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) agent01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)agent01597.get("errMsg"), "","");
					return false;
					
				} else if ("0020".equals(agent01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) agent01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)agent01597.get("errMsg"), "","");
					return false;
				}else{
					
					prossDialog.disposeDialog();
					logger.info(agent01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)agent01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 卡黑灰名单
	 */
	public boolean checkCardTelephoneFraud(Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("01597");
			Map card01597 = IntefaceSendMsg.card01597(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)card01597.get("resCode"), (String)card01597.get("errMsg"));
			if(!"000000".equals(card01597.get("resCode"))){
				
				if ("0010".equals(card01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) card01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)card01597.get("errMsg"), "","");
					return false;
					
				} else if ("0020".equals(card01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) card01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String)card01597.get("errMsg"), "","");
					return false;
				}else{
					
					prossDialog.disposeDialog();
					logger.info(card01597.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)card01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 有存单查询卡信息
	 */
	public boolean haveBillCheckCardMsg(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = IntefaceSendMsg.inter03845(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if (!"000000".equals(inter03845.get("resCode"))) {
				
				if("0".equals(accCancelBean.getSubAccNoCancel())){
					
					prossDialog.disposeDialog();
					logger.info((String) inter03845.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) inter03845.get("errMsg"),"", "");
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					//选择重新插入银行卡或输入银行卡号
					openConfirmDialog((String) inter03845.get("errMsg")+"是否继续：是：请重新插入银行卡或输入银行卡号。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//重新插入银行卡或输入银行卡号
							if("1".equals(accCancelBean.getInCardOrHandCard())){
								//退卡
								try {
									quitBankCard();
								} catch (Exception e1) {
									logger.error("退出银行卡失败"+e1);
								}
								GlobalParameter.CARD_STATUS=false;
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
								
							}else{
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							}
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//退出存单页
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
							MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							openPanel(thisComp, new AccCancelOutputDepositPanel());
						}
						
					});
				}
				return false; 
			}
					
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡信息查询失败，请联系大堂经理","", "调用03845接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 有存单卡账户信息查询1-03521
	 */
	public boolean haveBillCardAcct(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = IntefaceSendMsg.inter03521(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				
				if("0".equals(accCancelBean.getSubAccNoCancel())){
					
					prossDialog.disposeDialog();
					logger.info((String) inter03521.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) inter03521.get("errMsg"),"", "");
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(inter03521.get("errMsg"));
					//选择重新插入银行卡或输入银行卡号
					openConfirmDialog((String) inter03521.get("errMsg")+"是否继续：是：请重新插入银行卡或输入银行卡号。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//重新插入银行卡或输入银行卡号
							if("1".equals(accCancelBean.getInCardOrHandCard())){
								//退卡
								try {
									quitBankCard();
								} catch (Exception e1) {
									logger.error("退出银行卡失败"+e1);
								}
								GlobalParameter.CARD_STATUS=false;
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
								
							}else{
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							}
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//退出存单页
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
							MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							openPanel(thisComp, new AccCancelOutputDepositPanel());
						}
						
					});
				}
				return false;
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 有存单卡账户信息查询2-07601
	 */
	public boolean haveBillCardAcct1(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("07601");
			Map card07601 = IntefaceSendMsg.card07601(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
			if(!"000000".equals(card07601.get("resCode"))){
				
				if("0".equals(accCancelBean.getSubAccNoCancel())){
					
					prossDialog.disposeDialog();
					logger.info((String) card07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) card07601.get("errMsg"),"", "");
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(card07601.get("errMsg"));
					//选择重新插入银行卡或输入银行卡号
					openConfirmDialog((String) card07601.get("errMsg")+"是否继续：是：请重新插入银行卡或输入银行卡号。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//重新插入银行卡或输入银行卡号
							if("1".equals(accCancelBean.getInCardOrHandCard())){
								//退卡
								try {
									quitBankCard();
								} catch (Exception e1) {
									logger.error("退出银行卡失败"+e1);
								}
								GlobalParameter.CARD_STATUS=false;
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
								
							}else{
								openPanel(thisComp, new AccCancelSelectMethodPanel());//重新插入银行卡或输入银行卡号
							}
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							//退出存单页
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
							MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							openPanel(thisComp, new AccCancelOutputDepositPanel());
						}
						
					});
				}
				return false; 
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用07601接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询卡信息
	 */
	public boolean checkCardMsg(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = IntefaceSendMsg.inter03845(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if (!"000000".equals(inter03845.get("resCode"))) {
				if (((String) inter03845.get("errMsg")).startsWith("A102")) {

					prossDialog.disposeDialog();
					logger.info(((String) inter03845.get("errMsg")));
					//选择重新输入密码或者退出
					openConfirmDialog("输入密码错误,请重新输入：是：重新输入密码。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//重新输入密码
							openPanel(thisComp, new AccCancelInputBankCardPasswordPanel());//录入密码页
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//退银行卡
							accCancelBean.getReqMCM001().setLendirection("");
							accCancelBean.getReqMCM001().setToaccount("");
							accCancelBean.getReqMCM001().setCustomername("");
							accCancelBean.getReqMCM001().setTonouns("");
							accCancelBean.getReqMCM001().setAccount("");
							accCancelBean.getReqMCM001().setRvouchertype("");
							accCancelBean.getReqMCM001().setRvoucherno("");
							MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
							openPanel(thisComp, new OutputBankCardPanel());
						}
						
					});
					return false;
					
				} else if (((String) inter03845.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "您的密码输入次数已达上限，卡已经锁死。",(String) inter03845.get("errMsg"),"");
					return false;
					
				}else if ("4444".equals(inter03845.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(inter03845.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) inter03845.get("errMsg"),"","");
					return false;
					
				} else if ("5555".equals(inter03845.get("resCode"))){
					
					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String) inter03845.get("errMsg"),"","");
					return false; 
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "卡信息查询失败",(String) inter03845.get("errMsg"),"");
					return false; 
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡信息查询失败，请联系大堂经理","", "调用03845接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 卡账户信息查询1-03521
	 */
	public boolean checkCardAcct(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = IntefaceSendMsg.inter03521(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				
				if("5555".equals(inter03521.get("resCode"))){
					
					prossDialog.disposeDialog();
					logger.info(inter03521.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String) inter03521.get("errMsg"),"","");
					return false; 
					
				}else if ("4444".equals(inter03521.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(inter03521.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) inter03521.get("errMsg"),"","");
					return false;
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(inter03521.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "卡账户信息查询失败",(String) inter03521.get("errMsg"),"");
					return false; 
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 卡账户信息查询2-07601
	 */
	public boolean checkCardAcct1(final Component thisComp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("07601");
			Map card07601 = IntefaceSendMsg.card07601(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
			if(!"000000".equals(card07601.get("resCode"))){
				
				if("5555".equals(card07601.get("resCode"))){
					
					prossDialog.disposeDialog();
					logger.info(card07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp,(String) card07601.get("errMsg"),"","");
					return false; 
					
				}else if ("4444".equals(card07601.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(card07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, (String) card07601.get("errMsg"),"","");
					return false;
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(card07601.get("errMsg"));
					accCancelBean.getReqMCM001().setLendirection("");
					accCancelBean.getReqMCM001().setToaccount("");
					accCancelBean.getReqMCM001().setCustomername("");
					accCancelBean.getReqMCM001().setTonouns("");
					accCancelBean.getReqMCM001().setAccount("");
					accCancelBean.getReqMCM001().setRvouchertype("");
					accCancelBean.getReqMCM001().setRvoucherno("");
					MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
					serverStop(thisComp, "卡账户信息查询失败",(String) card07601.get("errMsg"),"");
					return false; 
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 检验IC卡信息
	 */
	public boolean checkICInfo(final Component comp){
		try {
			logger.info("调用检验IC卡55域信息接口07602");
			openProssDialog();
			Map<String,String> map = new HashMap<String, String>();
			accCancelBean.getReqMCM001().setReqBefor("07602");
			map = IntefaceSendMsg.inter07602(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				String errMsg = map.get("errMsg");
				logger.info("检验未通过："+errMsg);
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp,"检验IC卡信息失败，请联系大堂经理",errMsg,"" );
				prossDialog.disposeDialog();
				return false;
			}
			prossDialog.disposeDialog();
		} catch (Exception e) {
			logger.error("检验IC卡信息异常："+e);
			prossDialog.disposeDialog();
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07602接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(comp, "检验IC卡信息时异常，请联系大堂经理", "","调用07602接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询卡下子账户
	 */
	public boolean checkSubInfo(final Component comp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("03519");
			Map<String,String> map = IntefaceSendMsg.inter03519(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询卡下子账户信息失败："+map.get("errMsg"));
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "查询卡下子账户失败，请联系大堂经理", map.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用查询卡下子账户异常："+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用03519接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(comp,"查询卡下子账户失败，请联系大堂经理", "", "调用03519接口异常");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 电子账户信息查询 
	 */
	public boolean checkEInfo(final Component comp){
		try {
			accCancelBean.getReqMCM001().setReqBefor("07819");
			Map<String,String> map = IntefaceSendMsg.inter07819(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询电子账户信息失败："+map.get("errMsg"));
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "查询电子账户信息失败，请联系大堂经理", map.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用电子账户信息接口异常："+e);
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07819接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(comp, "查询电子账户信息失败，请联系大堂经理", "", "调用07819接口失败");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询代理人身份信息
	 * @param comp
	 * @return
	 */
	public boolean checkAgentInfo(final Component comp){
		logger.info("调用代理人身份信息接口");
		try {
			accCancelBean.getReqMCM001().setReqBefor("08021");
			Map<String,String> map = IntefaceSendMsg.inter08021(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询代理人身份信息失败："+map.get("errMsg"));
				prossDialog.disposeDialog();
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(comp, "查询代理人身份信息失败，请联系大堂经理", map.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08021接口失败"+e);
			prossDialog.disposeDialog();
			accCancelBean.getReqMCM001().setIntereturnmsg("调用08021接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(comp, "查询代理人身份信息失败，请联系大堂经理", "","调用08021接口失败");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 本人联网核查
	 * @param thisComp
	 * @return
	 */
	public boolean meCheckIdCardInfo(final Component thisComp){
		logger.info("本人调用联网核查接口");
		try {
			accCancelBean.getReqMCM001().setReqBefor("07670");
			Map me07670 =IntefaceSendMsg.me07670(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)me07670.get("resCode"), (String)me07670.get("errMsg"));
			if(!"000000".equals(me07670.get("resCode"))){
				logger.info("本人联网核查失败："+me07670.get("errMsg"));
				prossDialog.disposeDialog();
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(thisComp, "本人联网核查失败，请联系大堂经理", (String)me07670.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用联网核查接口07670失败"+e);
			prossDialog.disposeDialog();
			accCancelBean.getReqMCM001().setIntereturnmsg("调用7670接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "调用联网核查接口失败，请联系大堂经理", "","调用07670接口异常");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 代理人联网核查
	 * @param thisComp
	 * @return
	 */
	public boolean agentCheckIdCardInfo(final Component thisComp){
		logger.info("代理人调用联网核查接口");
		try {
			accCancelBean.getReqMCM001().setReqBefor("07670");
			Map agent07670 =IntefaceSendMsg.agent07670(accCancelBean);
			accCancelBean.getReqMCM001().setReqAfter((String)agent07670.get("resCode"), (String)agent07670.get("errMsg"));
			if(!"000000".equals(agent07670.get("resCode"))){
				logger.info("代理人联网核查失败："+agent07670.get("errMsg"));
				prossDialog.disposeDialog();
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(thisComp, "代理人联网核查失败，请联系大堂经理", (String)agent07670.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用联网核查接口07670失败"+e);
			prossDialog.disposeDialog();
			accCancelBean.getReqMCM001().setIntereturnmsg("调用7670接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "调用联网核查接口失败，请联系大堂经理", "","调用07670接口异常");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 预计利息查询
	 */
	public boolean yjLx(final Component thisComp){
		try {
			logger.info("调用普通预计利息07696");
			accCancelBean.getReqMCM001().setReqBefor("07696");
			Map params=IntefaceSendMsg.inter07696(accCancelBean);
			String resCode=(String)params.get("resCode");
			String errMsg=(String)params.get("errMsg");
			accCancelBean.getReqMCM001().setReqAfter(resCode, errMsg);
			
			if(!IntefaceSendMsg.SUCCESS.equals(resCode)){
				logger.info("普通预计利息查询失败："+errMsg);
				clearTimeText();
				stopTimer(voiceTimer);
				closeVoice();
				accCancelBean.getReqMCM001().setLendirection("");
				accCancelBean.getReqMCM001().setToaccount("");
				accCancelBean.getReqMCM001().setCustomername("");
				accCancelBean.getReqMCM001().setTonouns("");
				accCancelBean.getReqMCM001().setAccount("");
				accCancelBean.getReqMCM001().setRvouchertype("");
				accCancelBean.getReqMCM001().setRvoucherno("");
				MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
				serverStop(thisComp,"预计利息查询失败", errMsg, "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("普通预计利息查询失败："+e);
			clearTimeText();
			stopTimer(voiceTimer);
			closeVoice();
			accCancelBean.getReqMCM001().setIntereturnmsg("调用07696接口异常");
			accCancelBean.getReqMCM001().setLendirection("");
			accCancelBean.getReqMCM001().setToaccount("");
			accCancelBean.getReqMCM001().setCustomername("");
			accCancelBean.getReqMCM001().setTonouns("");
			accCancelBean.getReqMCM001().setAccount("");
			accCancelBean.getReqMCM001().setRvouchertype("");
			accCancelBean.getReqMCM001().setRvoucherno("");
			MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
			serverStop(thisComp, "预计利息查询失败", "", "调用普通预计利息07696异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 唐豆收回查询
	 */
	public void tdCheck(final Component thisComp){
		logger.info("调用唐豆收回查询");
		try {
			Map inter07622 = IntefaceSendMsg.inter07622(accCancelBean); 
			if(!"000000".equals(inter07622.get("resCode"))){
				
				logger.info(inter07622.get("errMsg"));
				accCancelBean.setTangCode((String)inter07622.get("resCode"));
				accCancelBean.setTangErrMsg((String)inter07622.get("errMsg"));
				accCancelBean.setShtdy("0.00");//不调唐豆收回
				accCancelBean.getReqMCM001().setTransresult("2");

			}
			
		} catch (Exception e) {
			
			logger.error("调用07622唐豆收回查询失败"+e);
			accCancelBean.setTangCode("999999");
			accCancelBean.setTangErrMsg("唐豆收回查询失败");
			accCancelBean.setShtdy("0.00");//不调唐豆收回
			accCancelBean.getReqMCM001().setTransresult("2");
		}
	}
	
	/**
	 * 唐豆收回
	 */
	public void tdGetBack(final Component thisComp){
		logger.info("调用唐豆收回");
		try {
			Map inter07665 = IntefaceSendMsg.inter07665(accCancelBean); 
			if(!"000000".equals(inter07665.get("resCode"))){
				
				logger.info(inter07665.get("errMsg"));
				accCancelBean.setTangCode((String)inter07665.get("resCode"));
				accCancelBean.setTangErrMsg((String)inter07665.get("errMsg"));
				accCancelBean.getReqMCM001().setTransresult("2");
			}
			
		} catch (Exception e) {
			
			logger.error("调用07665唐豆收回失败"+e);
			accCancelBean.setTangCode("999999");
			accCancelBean.setTangErrMsg("唐豆收回失败");
			accCancelBean.getReqMCM001().setTransresult("2");
		}
	}
	
	/**
	 * 查询是否签约唐行宝
	 */
	public Map checkTxb(final Component thisComp){
		logger.info("查询是否签约唐行宝");
		Map inter07498 = null;
		try {
			accCancelBean.getReqMCM001().setReqBefor("07498");
			inter07498 = IntefaceSendMsg.inter07498(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter07498.get("resCode"), (String)inter07498.get("errMsg"));
			if(!"000000".equals(inter07498.get("resCode"))){
				if("4444".equals(inter07498.get("resCode"))){
					
					logger.info("查询唐行宝失败，"+inter07498.get("errMsg"));
				}else{
					
					logger.info("查询唐行宝失败，"+inter07498.get("errMsg"));
				}
				return inter07498;
			}
			
		} catch (Exception e) {
			
			prossDialog.disposeDialog();
			logger.error("查询唐行宝失败"+e);
			accCancelBean.getReqMCM001().setReqAfter("4444", "查询唐行宝失败");
			inter07498.put("resCode", "4444");
			inter07498.put("errMsg", "查询唐行宝失败");
			return inter07498;
		}
		return inter07498;
	}
	
	/**
	 * 唐行宝转入
	 */
	public Map<String,String> transferTxb(final Component thisComp){
		logger.info("转入唐行宝");
		Map inter07499 = null;
		try {
			accCancelBean.getReqMCM001().setReqBefor("07499");
			inter07499 = IntefaceSendMsg.inter07499(accCancelBean); 
			accCancelBean.getReqMCM001().setReqAfter((String)inter07499.get("resCode"), (String)inter07499.get("errMsg"));
			if(!"000000".equals(inter07499.get("resCode"))){
				if("4444".equals(inter07499.get("resCode"))){
					
					logger.info("转入唐行宝失败，"+inter07499.get("errMsg"));
				}else{
					
					logger.info("转入唐行宝失败，"+inter07499.get("errMsg"));
				}
				return inter07499;
			}
			
		} catch (Exception e) {
			
			prossDialog.disposeDialog();
			logger.error("转入唐行宝失败"+e);
			accCancelBean.getReqMCM001().setReqAfter("4444", "转入唐行宝失败");
			inter07499.put("resCode", "4444");
			inter07499.put("errMsg", "转入唐行宝失败");
			return inter07499;
		}
		return inter07499;
	}
	
	/***
	 * 是否打印凭条
	 */
	public void isPrint(final Component comp){
		sleep(5);
		openConfirmDialog("销户成功,是否打印凭条：是：打印。否：不打印。");
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				confirmDialog.dispose();
				prossDialog.disposeDialog();
				//跳转凭条打印处理页
				openPanel(comp, new AccCancelPtPrintProcessingPanel());//凭条打印页
			}
			
		});
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				confirmDialog.dispose();
				prossDialog.disposeDialog();
				//上传事后监督
				uploadPhoto();
				//进入销户成功页
				openPanel(comp, new AccCancelSuccessPanel());//销户成功页面
			}
			
		});
	}
	
	/**
	 * 上传事后监督
	 */
	public void uploadPhoto(){
		Map <String,String> map = new HashMap<String,String>();
		if("0".equals(accCancelBean.getHavAgentFlag()) && "0".equals(accCancelBean.getAmtState())){
			
			map.put("agentFlag","");// 简易流程，不需要插入身份证
		}else{
			
			map.put("agentFlag", accCancelBean.getHavAgentFlag());// 1-存在代理人  0-不存在代理人
		}
		map.put("haveBillAcc", accCancelBean.getHaveAcc());// 0-有 存单1-无存单
		map.put("canelBillId", accCancelBean.getXHSvrJrnlNo());//  销户核心流水号
		map.put("yflx", accCancelBean.getRealAmt().trim());// 实付利息
		map.put("rate", accCancelBean.getRate());// 销户利率
		map.put("tdAmt", accCancelBean.getShtdy());//唐豆收回金额
		map.put("tdSvrJrnlNo", accCancelBean.getTdSvrJrnlNo());//唐豆收回流水
		map.put("xydAmt", accCancelBean.getAdvnInit());//幸运豆金额
		map.put("canelBillDTransDate", accCancelBean.getSvrDate()+" "+DateUtil.getDateTime("HH:mm:ss"));//  销户日期
		map.put("accName", accCancelBean.getAccName());//  户名
		map.put("proName", accCancelBean.getProName());//产品名称
		map.put("branchNo", GlobalParameter.branchNo);//  机构号
		
		if("002".equals(accCancelBean.getAccCancelType())){
			map.put("transType", "电子子账户销户");//  交易类型
		}else if("0".equals(accCancelBean.getSubAccNoCancel())){
			map.put("transType", "银行卡子账户销户");//  交易类型
		}else{
			map.put("transType", "普通存单账户销户");//  交易类型
		}
		
		map.put("amount", accCancelBean.getRealPri().trim());//  交易金额
		
		if(accCancelBean.getYuanAccNo()!=null){
			map.put("yuanAccNo", accCancelBean.getYuanAccNo());//  原账号
		}else{
			map.put("yuanAccNo", "无");//  原账号
		}
		map.put("accNo", accCancelBean.getAccNo());//  账号
		map.put("billNo", accCancelBean.getBillNo());//  凭证号
		map.put("supTellerNo", accCancelBean.getSupTellerNo());// 授权号
		if(accCancelBean.getTellNo1()==null){
			
			map.put("supTellerNo1", "无");// 授权号1
		}else{
			map.put("supTellerNo1", accCancelBean.getTellNo1());// 授权号1
		}
		if(accCancelBean.getTellNo2()==null){
			
			map.put("supTellerNo2", "无");// 授权号2
		}else{
			map.put("supTellerNo2", accCancelBean.getTellNo2());// 授权号2
		}
		if(accCancelBean.getTellNo3()==null){
			
			map.put("supTellerNo3", "无");// 授权号2
		}else{
			map.put("supTellerNo3", accCancelBean.getTellNo3());// 授权号2
		}
		map.put("tellerNo", GlobalParameter.tellerNo);//柜员号
		map.put("cancleType", accCancelBean.getAccCancelType());//销户转存方式
		if("001".equals(accCancelBean.getAccCancelType())){
			map.put("transferAcc", accCancelBean.getCardNo());//转入银行卡号
		}else if("002".equals(accCancelBean.getAccCancelType())){
			map.put("transferAcc", accCancelBean.getCardNo());//转入电子账号
		}else if("003".equals(accCancelBean.getAccCancelType())){
			map.put("transferAcc", accCancelBean.getCardNo()+"-"+accCancelBean.getTxbSubAccNo());//转入唐行宝账号
		}
		
		if("1".equals(accCancelBean.getJudgeState())){
			map.put("judgeState", "未到期");//是否到期
		}else{
			map.put("judgeState", "到期");//是否到期
		}
		
		if("001".equals(accCancelBean.getAccCancelType())){
			map.put("transferMethed", "转入银行卡");//  交易类型
		}else if("002".equals(accCancelBean.getAccCancelType())){
			map.put("transferMethed", "转入电子账户");//  交易类型
		}else if("003".equals(accCancelBean.getAccCancelType())){
			if(accCancelBean.getTxbProCode().startsWith("TBA")){
				map.put("transferMethed", "转入唐行宝A");//  交易类型
			}else if(accCancelBean.getTxbProCode().startsWith("TBB")){
				map.put("transferMethed", "转入唐行宝B");//  交易类型
			}else {
				map.put("transferMethed", "转入唐行宝C");//  交易类型
			}
		}
		
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("bill_face",accCancelBean.getBillPath_fc());//存单正面照
		map.put("bill_verso",accCancelBean.getBillPath_rc());//存单反面照
		
		map.put("idCardName", accCancelBean.getReadIdName());//  本人姓名
		map.put("idCardNo", accCancelBean.getReadIdNo());// 本人身份证号
		map.put("qfjg", accCancelBean.getQfjg());// 签发机关
		
		map.put("agentIdCardName", accCancelBean.getReadAgentIdName());// 代理人姓名
		map.put("agentIdCardNo", accCancelBean.getReadAgentIdNo());// 代理人身份证号
		map.put("agentqfjg", accCancelBean.getAgentqfjg());// 代理人签发机关
		
		if(accCancelBean.getOpenDate()!=null && !"".equals(accCancelBean.getOpenDate())){
			map.put("openDate", accCancelBean.getOpenDate());//开户日期
		}else{
			map.put("openDate", "");//开户日期
		}
		
		if(accCancelBean.getCancelDepTerm()!=null && !"".equals(accCancelBean.getCancelDepTerm())){
			map.put("cancelDepterm", accCancelBean.getCancelDepTerm());//存期
		}else{
			map.put("cancelDepterm", "");//存期
		}
		
		if(accCancelBean.getExchflag()!=null && !"".equals(accCancelBean.getExchflag())){
			if("0".equals(accCancelBean.getExchflag())){
				map.put("exchflag", "非自动转存");//自动转存标志
				
			}else{
				map.put("exchflag", "自动转存");//自动转存标志
			}
		}else{
			map.put("exchflag", "非自动转存");//自动转存标志
		}
		map.put("txbInputAmt", accCancelBean.getTxbInputAmt());//转入唐行宝金额
		map.put("txbSvrJrnlNo", accCancelBean.getTxbSvrJrnlNo());//转入唐行宝流水号
		map.put("fileName", accCancelBean.getSvrDate()+accCancelBean.getXHSvrJrnlNo());//销户日期+流水号
		
		List<AccCancelProFileBean> listProMsg = (List<AccCancelProFileBean>)accCancelBean.getImportMap().get("cancelProFile");
		int proNum = 0;
		//衍生品数据
		if(listProMsg!=null && listProMsg.size()>0){
			
			for (int i = 0; i < listProMsg.size(); i++) {
				
				if(listProMsg.get(i).getProName() == null || "".equals(listProMsg.get(i).getProName().trim()) || 
					listProMsg.get(i).getGetProSjAmt() == null || "".equals(listProMsg.get(i).getGetProSjAmt().trim()) ){
					
					logger.info("销户衍生品信息返回不全");
							
				}else if(Double.valueOf(listProMsg.get(i).getGetProSjAmt().trim()) != 0){
					
					
					logger.info(listProMsg.get(i).getProName()+"实际收回金额为"+listProMsg.get(i).getGetProSjAmt().trim());
					
					if("006".equals(listProMsg.get(i).getProtype())){
						
						map.put("proMsg"+String.valueOf(proNum), "优惠保险业务收回金额 : " + listProMsg.get(i).getGetProSjAmt().trim() + "元");
						proNum++;
						
					}else if("009".equals(listProMsg.get(i).getProtype())){
						
						map.put("proMsg"+String.valueOf(proNum), "活动奖励收回金额 : " + listProMsg.get(i).getGetProSjAmt().trim() + "元");
						proNum++;
						
					}else if("008".equals(listProMsg.get(i).getProtype())){
						
						map.put("proMsg"+String.valueOf(proNum), "增益豆收回金额 : " + listProMsg.get(i).getGetProSjAmt().trim() + "元");
						proNum++;
					}else if("205".equals(listProMsg.get(i).getProtype())){
						
						map.put("proMsg"+String.valueOf(proNum), "课时券收回金额 : " + listProMsg.get(i).getGetProSjAmt().trim() + "元");
						proNum++;
					}
				}
			}
		}
		//衍生品数量
		map.put("proNum", String.valueOf(proNum));
		
		//生成电子印章
		YiZhangUtil yizhang = new YiZhangUtil();
		try {
			boolean yiZhangPhoto = yizhang.getYiZhangPhoto(map);
			if(yiZhangPhoto){
				logger.error("生成电子印章图片成功:"+yiZhangPhoto);
			}
		} catch (Exception e1) {
			logger.error("生成电子印章图片失败"+e1);
		}
		
		//生成事后图片
		JpgUtil_XH cg = new JpgUtil_XH();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration(map);
		} catch (Exception e2) {
			logger.error("事后监管程序，生成事后监管图片异常！"+ e2);
		}
		
		SFTPUtil sf = new SFTPUtil();
		ChannelSftp sftp = null;
		Session sshSession = null;
    	JSch jsch = new JSch();
    	
    	try {
    		//连接SFTP
    		sshSession = jsch.getSession(Property.FTP_USER, Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
    		logger.info("Session created.");
    		sshSession.setPassword(Property.FTP_PWD);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
    		logger.info("Session connected.");
    		logger.info("Opening Channel.");
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		logger.info("Connected to " + Property.FTP_IP + ".");
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = Property.FTP_SER_PATH + nowDate;
    		String[] ftpList = ftpPath.split("/");
    		String paths = "";
    		for (String path : ftpList) {
    			if(StringUtils.isNotBlank(path)){
    				paths += "/" + path;
    				try {
            			Vector content = sftp.ls(paths);
            			if (content == null) {
            				sftp.mkdir(paths);
            			}
    				} catch (Exception e) {
    					sftp.mkdir(paths);
    				}
    				sftp.cd(paths);
    			}
    		}
    		File file = new File(filePath);
    		boolean result = sf.upload(ftpPath, filePath, sftp);
    		if(!result){
    			logger.error("事后监管上传图片失败-->" + file.getName());
    		}else{
    			logger.info("事后监管上传图片成功-->" + file.getName());
    			//删除图片
    			deleteFile(file);
    		}
    		
		} catch (Exception e) {
			logger.error("事后监督上传图片，进入目录失败"+e);
		}finally{
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
			if (sshSession!= null && sshSession.isConnected()){
				sshSession.disconnect();
			}
		}
    	
	}
	/**
	 * 成功上传事后监督图片后删除本地图片的方法
	 * */
	private static void deleteFile(File file) {
		if (file.isFile()) {// 如果是文件
			// logger.info(f);
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = file.delete();
		if (!result) {// 判断是否全部删除
			file.delete();
		}
		logger.info("删除成功" + file);
		}
	}
	
}















