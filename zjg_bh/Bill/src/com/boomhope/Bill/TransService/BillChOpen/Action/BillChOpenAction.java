package com.boomhope.Bill.TransService.BillChOpen.Action;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BillChangeOpenInfoBean;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenCheckSVJN;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenDepositAffirmSignaPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenInputBankCardPasswordPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenSVJN;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpensubAccBankorBillPassPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse.BillChangeOpenOutputDepositPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse.BillChangeOpenPrintPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenCameraPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenDeputySelectionPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.chenckInfo.BillChangeOpenInputAgentIdCardPanel;
import com.boomhope.Bill.TransService.BillChOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil_HK;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.Util.YiZhangUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class BillChOpenAction extends BaseTransPanelNew{
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BillChOpenAction.class);
	//本人身份核查
	public void mePolicecheck(Component comp) {
		     //查询黑灰名单
				if(!meCheckTelephoneFraud(comp)){
					return;
				}
				
				//身份证联网核查
				if(!meCheckIdCardInfo(comp)){
					return;
				}
				if("1".equals(bcOpenBean.getHavAgentFlag())){//有代理人
					
					logger.info("插入代理人身份证");
					openPanel(comp, new BillChangeOpenInputAgentIdCardPanel());
					
				}else{//本人
					logger.info("进入拍照页面");
					openPanel(comp,new BillChangeOpenCameraPanel());
				}
	}
	/**
	 * 本人身份证黑灰名单
	 */
	public boolean meCheckTelephoneFraud(Component thisComp){
		logger.info("本人身份证黑名单查询接口");
		try {
			bcOpenBean.getReqMCM001().setReqBefor("01597");
			Map me01597 = IntefaceSendMsg.me01597(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)me01597.get("resCode"), (String)me01597.get("errMsg"));
			if(!"000000".equals(me01597.get("resCode"))){
				if ("0010".equals(me01597.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
				} else if ("0020".equals(me01597.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
				}else{
					prossDialog.disposeDialog();
					logger.info(me01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)me01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用01597接口异常!");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
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
			bcOpenBean.getReqMCM001().setReqBefor("07670");
			Map me07670 =IntefaceSendMsg.me07670(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)me07670.get("resCode"), (String)me07670.get("errMsg"));
			if(!"000000".equals(me07670.get("resCode"))){
				logger.info("本人联网核查失败："+me07670.get("errMsg"));
				prossDialog.disposeDialog();
				bcOpenBean.getReqMCM001().setIntereturnmsg((String)me07670.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(thisComp, "本人联网核查失败，请联系大堂经理", (String)me07670.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用联网核查接口07670失败"+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "调用联网核查接口失败，请联系大堂经理", "","调用07670接口异常");
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
			bcOpenBean.getReqMCM001().setReqBefor("07602");
			Map<String,String> map = new HashMap<String, String>();
			map = IntefaceSendMsg.inter07602(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				String errMsg = map.get("errMsg");
				logger.info("检验未通过："+errMsg);
				bcOpenBean.getReqMCM001().setIntereturnmsg(errMsg);
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(comp,"检验IC卡信息失败，请联系大堂经理",errMsg,"" );
				prossDialog.disposeDialog();
				return false;
			}
			prossDialog.disposeDialog();
		} catch (Exception e) {
			logger.error("检验IC卡信息异常："+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07602接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(comp, "检验IC卡信息时异常，请联系大堂经理", "","调用07602接口异常");
			return false;
		}
		return true;
	}

	/**
	 * 查询账户流水信息
	 */
	public void checksvjn(Component Comp){
		
		//根据身份信息查询出所有开卡记录
		if(!CheckIdNo(Comp)){
			return;
		}
		//根据卡号查询出所有子账号
		if(!CheckSubIdNo(Comp)){
			return;
		}
		//通过卡号查询出客户号
		if(!CheckCurt(Comp)){
			return;
		}
		//根据客户号查询出普通账号
		if(!CheckAcc(Comp)){
			return;
		}
		//通过客户号查询出电子账户
		if(!CheckDZAcc(Comp)){
			return;
		}
		
		openPanel(Comp,new BillChangeOpenSVJN());
	}
	/**
	 * 查询卡信息
	 */
	public boolean checkCardMsg(final Component thisComp){
		//通过卡号查询出身份信息
		
		logger.info("通过卡号查询出身份信息");
		try {
			bcOpenBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = IntefaceSendMsg.inter03845(bcOpenBean); 
			bcOpenBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
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
							openPanel(thisComp, new BillChangeOpenInputBankCardPasswordPanel());//录入密码页
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							//退银行卡
							openPanel(thisComp, new OutputBankCardPanel());
						}
						
					});
					return false;
					
				} else if (((String) inter03845.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03845.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "您的密码输入次数已达上限，卡已经锁死。",(String) inter03845.get("errMsg"),"");
					return false;
					
				}else if ("4444".equals(inter03845.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(inter03845.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03845.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, (String) inter03845.get("errMsg"),"","");
					return false;
					
				} else if ("5555".equals(inter03845.get("resCode"))){
					
					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03845.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String) inter03845.get("errMsg"),"","");
					return false; 
					
				}else{
					
					prossDialog.disposeDialog();
					logger.info(inter03845.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03845.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "卡信息查询失败",(String) inter03845.get("errMsg"),"");
					return false; 
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "卡信息查询失败，请联系大堂经理","", "调用03845接口异常");
			return false;
		}
		return true;
	}
	/**
	 * 开卡明细查询
	 * 
	 */
	public boolean CheckIdNo(final Component thisComp){
		logger.info("通过身份信息查询开卡明细");
		List<BillChangeOpenInfoBean> listBean = null;
		try {
			bcOpenBean.getReqMCM001().setReqBefor("07524");
			Map inter07524 = IntefaceSendMsg.inter07524(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)inter07524.get("resCode"), (String)inter07524.get("errMsg"));
			if(!"000000".equals(inter07524.get("resCode"))){
				closeDialog(prossDialog);
				logger.info((String) inter07524.get("errMsg"));
				bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter07524.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(thisComp,"开卡明细查询失败，请联系大堂经理", (String) inter07524.get("errMsg"),"");
				return false;
			}
			
			listBean = (List<BillChangeOpenInfoBean>)inter07524.get(IntefaceSendMsg.E_ACC_MSG);
			int flag = 0;
			//获取开卡明细查询记录信息
			if(listBean != null && listBean.size()!=0){
				logger.info("开卡明细查询记录条数："+listBean.size());
				
				bcOpenBean.getCardMap().put("BillMsgList", listBean);
				flag++;
			}else{
				
				closeDialog(prossDialog);
				logger.info("没有开卡记录");
				serverStop(thisComp,"没有符合开卡明细查询信息!","","");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用inter07524开卡明细查询查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用inter07524接口异常!");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp,"开卡明细查询，请联系大堂经理", "","调用inter07524接口异常!");
			return false;
		}
						
		return true;
		
	}
	/**
	 * 子账户列表查询
	 */
	public boolean CheckSubIdNo(final Component thisComp){
		logger.info("通过卡号查询出子账号");
		List<BillChangeOpenInfoBean> listBean = (List<BillChangeOpenInfoBean>)bcOpenBean.getCardMap().get("BillMsgList");
		
			try {
			for (int i = 0; i < listBean.size(); i++) {
		       if(!"".equals(listBean.get(i).getCard_no()) && listBean.get(i).getCard_no()!=null){
		    	   bcOpenBean.setOpen_card(listBean.get(i).getCard_no());
		       }
		       bcOpenBean.getReqMCM001().setReqBefor("03519");
		       Map inter03519 = IntefaceSendMsg.inter03519(bcOpenBean);
		       bcOpenBean.getReqMCM001().setReqAfter((String)inter03519.get("resCode"), (String)inter03519.get("errMsg"));
		       if(!"000000".equals(inter03519.get("resCode"))){
					closeDialog(prossDialog);
					logger.info((String) inter03519.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter03519.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,"子账户列表查询失败，请联系大堂经理", (String) inter03519.get("errMsg"),"");
					return false;
				}
		      
				}
			} catch (Exception e) {
				closeDialog(prossDialog);
				logger.error("调用inter03519子账户记录查询失败"+e);
				bcOpenBean.getReqMCM001().setIntereturnmsg("调用inter03519接口异常!");
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(thisComp,"子账户记录查询，请联系大堂经理", "","调用inter03519接口异常!");
				return false;
			}	

		return true;
	
	}
	/**
	 * 根据卡号查询客户号
	 */
	public boolean CheckCurt(final Component thisComp){
		logger.info("通过卡号号查询出客户号");
		List<BillChangeOpenInfoBean> list2 =  (List<BillChangeOpenInfoBean>)bcOpenBean.getCardMap().get("BillMsgList");
		 for (int i = 0; i < list2.size(); i++) {
				try {
			    	   bcOpenBean.setAccNo(list2.get(i).getCard_no());
			    	   bcOpenBean.getReqMCM001().setReqBefor("07601");
			       Map CMM07601 = IntefaceSendMsg.CMM07601(bcOpenBean);
			       bcOpenBean.getReqMCM001().setReqAfter((String)CMM07601.get("resCode"), (String)CMM07601.get("errMsg"));
			       if(!"000000".equals(CMM07601.get("resCode"))){
						closeDialog(prossDialog);
						logger.info((String) CMM07601.get("errMsg"));
						bcOpenBean.getReqMCM001().setIntereturnmsg((String) CMM07601.get("errMsg"));
						MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
						serverStop(thisComp,"客户号查询失败，请联系大堂经理", (String) CMM07601.get("errMsg"),"");
						return false;
					}
			      
				} catch (Exception e) {
					closeDialog(prossDialog);
					logger.error("调用CMM07601接口查询失败"+e);
					bcOpenBean.getReqMCM001().setIntereturnmsg("调用07601接口异常!");
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,"客户号查询失败，请联系大堂经理", "","调用CMM07601接口异常!");
					return false;
					
				}
		
			
		}
		
		return true;
		
	}
	/*
	 * 根据客户号查询出电子账户
	 */
	public boolean CheckDZAcc(final Component thisComp){
		
		try {
			bcOpenBean.getReqMCM001().setReqBefor("07819");
			Map inter07819 = IntefaceSendMsg.inter07819(bcOpenBean);
			 bcOpenBean.getReqMCM001().setReqAfter((String)inter07819.get("resCode"), (String)inter07819.get("errMsg"));
			if(!"000000".equals(inter07819.get("resCode"))){
				String str=  (String)inter07819.get("errMsg");
               if(str.startsWith("0007")){
            	   logger.info("查询电子账户子账户信息失败："+inter07819.get("errMsg"));
            	   return true;
				}else{
				   logger.info("查询电子账户子账户信息失败："+inter07819.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter07819.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				   serverStop(thisComp, "查询电子账户子账户信息失败，请联系大堂经理", (String) inter07819.get("errMsg"), "");
				   return false;
			    }
			}
		
		} catch (Exception e) {
			logger.error("调用电子账户信息接口异常："+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07819接口异常!");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "查询电子账户信息失败，请联系大堂经理", "", "调用07819接口失败");
			return false;
		}
		return true;		 
		
	}
	/*
	 * 根据客户号查询出普通账户
	 */
	public boolean CheckAcc(final Component thisComp){
		
		try {
			bcOpenBean.getReqMCM001().setReqBefor("07527");
			Map inter07527 = IntefaceSendMsg.inter07527(bcOpenBean);
			 bcOpenBean.getReqMCM001().setReqAfter((String)inter07527.get("resCode"), (String)inter07527.get("errMsg"));
			if(!"000000".equals(inter07527.get("resCode"))){
				logger.info("查询账户信息失败："+inter07527.get("errMsg"));
				bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter07527.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(thisComp, "查询账户信息失败，请联系大堂经理", (String) inter07527.get("errMsg"), "");
				return false;
			}
				
				
		} catch (Exception e) {
			logger.error("调用账户信息接口异常："+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07527接口失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "查询账户信息失败，请联系大堂经理", "", "调用07527接口失败");
			return false;
		}
		return true;		 
		
	}	
	
	/**
	 * 换开存单密码校验
	 */
	public void checkPass(final Component comp){
		openProssDialog();
		bcOpenBean.setIsCheckPass("1");//验密
		bcOpenBean.setBillType("102");

		try {
			bcOpenBean.getReqMCM001().setReqBefor("07601");
			Map inter07601 = IntefaceSendMsg.inter07601(bcOpenBean);
			 bcOpenBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"), (String)inter07601.get("errMsg"));
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
							openPanel(comp, new BillChangeOpensubAccBankorBillPassPanel());//录入密码页
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.dispose();
							prossDialog.disposeDialog();
							openPanel(comp, new  BillChangeOpenOutputDepositPanel());//返回退存单页			
						}
						
					});
					return;
					
				} else if (((String) inter07601.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info(inter07601.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter07601.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(comp, "您的密码输入次数已达上限，卡已经锁死。",(String) inter07601.get("errMsg"),"");
					return;
					
				}else{
					prossDialog.disposeDialog();
					logger.info(inter07601.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String) inter07601.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(comp, "存单密码校验失败，请联系大堂经理", (String)inter07601.get("errMsg"),"");
					return;
				}
			}
			
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用07601存单账号信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07601存单账号信息查询失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(comp,"存单密码校验失败","调用07601存单账号信息查询失败","");
			return;
		}
		prossDialog.disposeDialog();
		clearTimeText();
		stopTimer(voiceTimer);//关语音
		closeVoice();//关语音流
		//进入电子签名存单信息确认页
		openPanel(comp, new BillChangeOpenDepositAffirmSignaPanel());
	}
	
	/**
	 * 存单换开新凭证号查询
	 * @param thisComp
	 * @return
	 */
	public boolean checkNewCustNo(final Component comp){
		logger.info("新凭证号查询接口");
		try {
			bcOpenBean.getReqMCM001().setReqBefor("0005");
			Map<String,String> map =IntefaceSendMsg.Tms0005();
			bcOpenBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("新凭证号查询接口："+map.get("errMsg"));
				prossDialog.disposeDialog();
				bcOpenBean.getReqMCM001().setIntereturnmsg("存单换开新凭证号查询失败");
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(comp, "存单换开新凭证号查询失败，请联系大堂经理", (String)map.get("errMsg"), "");
				return false;
			}
			bcOpenBean.setNowNo(map.get("nowNo"));//当前凭证号
			bcOpenBean.setCertStartNo(map.get("startNo"));//起始凭证号
			bcOpenBean.setCertEndNo(map.get("endNo"));//结尾凭证号
			bcOpenBean.setCertNoId(map.get("nowId"));//凭证ID
			bcOpenBean.setCreateTime(map.get("createDate"));//创建日期
		} catch (Exception e) {
			logger.error("存单换开新凭证号查询"+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用存单换开新凭证号查询接口失败");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(comp, "调用存单换开新凭证号查询接口失败，请联系大堂经理", "","调用Tms0005接口异常");
			return false;
		}
		return true;
	}
	/**
	 * 存单换开处理
	 */
	public boolean xhBusinessDeal(final Component comp){
		logger.info("存单换开处理中");
		
		//查询新凭证号
		if(!checkNewCustNo(comp)){
			return false;
		}
		try {
			bcOpenBean.getReqMCM001().setReqBefor("07516");
			Map<String,String> inter07516 =IntefaceSendMsg.inter07516(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)inter07516.get("resCode"), (String)inter07516.get("errMsg"));
			if(!"000000".equals(inter07516.get("resCode"))){
				logger.info("存单换开："+inter07516.get("errMsg"));
				prossDialog.disposeDialog();//关闭弹框
				bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter07516.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(comp, "存单换开失败，请联系大堂经理", (String)inter07516.get("errMsg"), "");
				return false;
			}
			prossDialog.disposeDialog();//关闭弹框
			clearTimeText();
			//存单换开成功,调用存单打印界面
			openPanel(comp,new BillChangeOpenPrintPanel());
		} catch (Exception e) {
			logger.error("调用存单换开处理接口07516失败"+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07516接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(comp, "存单换开失败，请联系大堂经理", "","调用07516接口异常");
			return false;
		}
		
		return true;
	}

	/**
	 * 上传事后监督
	 */
	public void uploadPhoto(){
		Map <String,String> map = new HashMap<String,String>();
		map.put("HKSvrJrnlNo", bcOpenBean.getHKSvrJrnlNo());//  换开核心流水号
		map.put("ZMBillDTransDate", bcOpenBean.getSvrDate()+" "+DateUtil.getDateTime("HH:mm:ss"));//  操作日期
		map.put("accName", bcOpenBean.getAccName());//  户名
		map.put("proName", bcOpenBean.getProName());//产品名称
		map.put("AccNo", bcOpenBean.getAccNo());//  账号
		map.put("branchNo", GlobalParameter.branchNo);//  机构号
		
		map.put("transType", "存单换开");//  交易类型
		if(bcOpenBean.getOpenDate()!=null && !"".equals(bcOpenBean.getOpenDate())){
			map.put("openDate", bcOpenBean.getOpenDate());//开户日期
		}else{
			map.put("openDate", "");//开户日期
		}
		
		map.put("amount",bcOpenBean.getTotalAmt().trim());//  交易金额
		map.put("billNo", bcOpenBean.getCertNo());//  凭证号
		map.put("newbillNo", bcOpenBean.getNowNo());//  新凭证号
		map.put("supTellerNo", bcOpenBean.getSupTellerNo());// 授权号
        if(bcOpenBean.getTellNo1()==null){
			
			map.put("supTellerNo1", "无");// 授权号1
		}else{
			map.put("supTellerNo1", bcOpenBean.getTellNo1());// 授权号1
		}
		if(bcOpenBean.getTellNo2()==null){
			
			map.put("supTellerNo2", "无");// 授权号2
		}else{
			map.put("supTellerNo2", bcOpenBean.getTellNo2());// 授权号2
		}
		if(bcOpenBean.getTellNo3()==null){
			
			map.put("supTellerNo3", "无");// 授权号3
		}else{
			map.put("supTellerNo3", bcOpenBean.getTellNo3());// 授权号3
		}
		map.put("tellerNo", GlobalParameter.tellerNo);//柜员号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("bill_face",bcOpenBean.getBillPath_fc());//存单正面照
		map.put("bill_verso",bcOpenBean.getBillPath_rc());//存单反面照
		
		map.put("idCardName", bcOpenBean.getReadIdName());//  本人姓名
		map.put("idCardNo", bcOpenBean.getReadIdNo());// 本人身份证号
		map.put("idtype", bcOpenBean.getIdType());//证件类型
		map.put("qfjg", bcOpenBean.getQfjg());// 签发机关
		map.put("agentqfjg",bcOpenBean.getAgentqfjg() );// 代理人签发机关
        map.put("yz", bcOpenBean.getYz());
		map.put("agentFlag", bcOpenBean.getHavAgentFlag());//是否有代理人标志
		if("1".equals(bcOpenBean.getHavAgentFlag())){
		map.put("agentIdName",bcOpenBean.getReadAgentIdName() );//代理人名称
		map.put("agentIdCardNo", bcOpenBean.getReadAgentIdNo());//代理人证件号
		map.put("agentIdType", "身份证");//代理人证件类型
		}
				//生成事后图片
				JpgUtil_HK cg = new JpgUtil_HK();
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
		    		String ftpPath = Property.FTP_SER_PATH_HK + nowDate+"/000008/";
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
		private void deleteFile(File file) {
			// TODO Auto-generated method stub
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

	/**
	 * 代理人身份信息核查
	 */
	public void agentPolicecheck(final Component comp){
		
		if(bcOpenBean.getReadIdNo().equals(bcOpenBean.getReadAgentIdNo())){
			//弹框提示非同一人
			openConfirmDialog("代理人不能为本人,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//跳转代理人身份证插入页
					openPanel(comp, new BillChangeOpenInputAgentIdCardPanel());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//进入代理人选择页
					openPanel(comp, new BillChangeOpenDeputySelectionPanel());
				}
				
			});
			return;
		}
		
		//代理人查询
		if(!checkAgentInfo(comp)){
			return;
		}
		//是否为本行人员
		if("0".equals(bcOpenBean.getCheckResult())){
			//弹框提示代理人不能为本行员工
			openConfirmDialog("本行在职员工不能办理代理人业务,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//跳转代理人身份证插入页
					openPanel(comp, new BillChangeOpenInputAgentIdCardPanel());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//进入代理人选择页
					openPanel(comp, new BillChangeOpenDeputySelectionPanel());
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
		openPanel(comp, new BillChangeOpenCameraPanel());
		
	}
	/**
	 * 代理人身份证黑灰名单
	 */
	public boolean agentCheckTelephoneFraud(Component thisComp){
		try {
			bcOpenBean.getReqMCM001().setReqBefor("01597");
			Map agent01597 = IntefaceSendMsg.agent01597(bcOpenBean); 
			bcOpenBean.getReqMCM001().setReqAfter((String)agent01597.get("resCode"), (String)agent01597.get("errMsg"));
			if(!"000000".equals(agent01597.get("resCode"))){
				
				if ("0010".equals(agent01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) agent01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)agent01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String)agent01597.get("errMsg"), "","");
					return false;
					
				} else if ("0020".equals(agent01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) agent01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)agent01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String)agent01597.get("errMsg"), "","");
					return false;
				}else{
					
					prossDialog.disposeDialog();
					logger.info(agent01597.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)agent01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)agent01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用01597接口异常!");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
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
			bcOpenBean.getReqMCM001().setReqBefor("07670");
			Map agent07670 =IntefaceSendMsg.agent07670(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)agent07670.get("resCode"), (String)agent07670.get("errMsg"));
			if(!"000000".equals(agent07670.get("resCode"))){
				logger.info("代理人联网核查失败："+agent07670.get("errMsg"));
				prossDialog.disposeDialog();
				bcOpenBean.getReqMCM001().setIntereturnmsg((String)agent07670.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(thisComp, "代理人联网核查失败，请联系大堂经理", (String)agent07670.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用联网核查接口07670失败"+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "调用联网核查接口失败，请联系大堂经理", "","调用07670接口异常");
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
			bcOpenBean.getReqMCM001().setReqBefor("08021");
			Map<String,String> map = IntefaceSendMsg.inter08021(bcOpenBean);
			bcOpenBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("查询代理人身份信息失败："+map.get("errMsg"));
				prossDialog.disposeDialog();
				bcOpenBean.getReqMCM001().setIntereturnmsg((String)map.get("errMsg"));
				MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
				serverStop(comp, "查询代理人身份信息失败，请联系大堂经理", map.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08021接口失败"+e);
			prossDialog.disposeDialog();
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用08021接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(comp, "查询代理人身份信息失败，请联系大堂经理", "","调用08021接口失败");
			return false;
		}
		
		return true;
	}
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
		openPanel(comp, new BillChangeOpenCheckSVJN());
	}
	/**
	 * 卡账户信息查询1-03521
	 */
	public boolean checkCardAcct(final Component thisComp){
		try {
			bcOpenBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = IntefaceSendMsg.inter03521(bcOpenBean); 
			bcOpenBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				if("5555".equals(inter03521.get("resCode"))){
					prossDialog.disposeDialog();
					logger.info(inter03521.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03521.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String) inter03521.get("errMsg"),"","");
					return false; 
				}else if ("4444".equals(inter03521.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.error(inter03521.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03521.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, (String) inter03521.get("errMsg"),"","");
					return false;
				}else{
					prossDialog.disposeDialog();
					logger.info(inter03521.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)inter03521.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "卡账户信息查询失败",(String) inter03521.get("errMsg"),"");
					return false; 
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
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
			bcOpenBean.getReqMCM001().setReqBefor("07601");
			Map card07601 = IntefaceSendMsg.card07601(bcOpenBean); 
			bcOpenBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
			if(!"000000".equals(card07601.get("resCode"))){
				if("5555".equals(card07601.get("resCode"))){
					prossDialog.disposeDialog();
					logger.info(card07601.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)card07601.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp,(String) card07601.get("errMsg"),"","");
					return false; 
				}else if ("4444".equals(card07601.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.error(card07601.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)card07601.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, (String) card07601.get("errMsg"),"","");
					return false;
				}else{
					prossDialog.disposeDialog();
					logger.info(card07601.get("errMsg"));
					bcOpenBean.getReqMCM001().setIntereturnmsg((String)card07601.get("errMsg"));
					MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
					serverStop(thisComp, "卡账户信息查询失败",(String) card07601.get("errMsg"),"");
					return false; 
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("卡账户信息查询失败"+e);
			bcOpenBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
			MakeMonitorInfo.makeInfos(bcOpenBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用07601接口异常");
			return false;
		}
		return true;
	}
	
}
