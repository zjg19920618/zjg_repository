package com.boomhope.Bill.TransService.LostReport.Action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.LostXHPrintPt;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelDeputySelectionPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.checkInfo.AccCancelInputAgentIdCardPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicAccFaceCheckFail;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicOutputIdCardPanel;
import com.boomhope.Bill.TransService.LostReport.Bean.AccCancelProFileBean;
import com.boomhope.Bill.TransService.LostReport.Bean.AccLostAndReturnInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.AccTdMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.BillChangeInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.IdCardCheckInfo;
import com.boomhope.Bill.TransService.LostReport.Bean.LostCheckBean;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SubAccInfoBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostAccCancelSuccessPanel;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostCancelAccCardPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostEnterAccNoMsgPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostGetCardOrEcardPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostPassSelectPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostProcessingPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostShowAccNoPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostTypeSelectPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostApplNoInfos;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostApplNoInfosConfirmPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostCancelAccCardPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostGetCardOrEcardPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostInputApplyNoPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostReMakePwdSusscessPage;
import com.boomhope.Bill.Util.CompareFaces;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.NumberZH;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;
import com.sun.media.controls.MonitorAdapter;

/**
 * 挂失/解挂业务逻辑处理
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
@SuppressWarnings(value={"static-access","rawtypes","unchecked","unused"})
public class LostAction extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(LostAction.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询符合挂失条件的账号信息
	 */
	public void chenkAcc(){
		
		List<ShowAccNoMsgBean> acclist = new ArrayList<ShowAccNoMsgBean>();//存放符合条件的挂失账户
		
		if(!checkAllCardNo()){//查询所有卡号
			return;
		}
		//查询所有的客户号
		Map checkAllCust = checkAllCust();
		if(!"000000".equals((String)checkAllCust.get("resCode"))){//查询客户号
			return;
		}
		List<IdCardCheckInfo> idList = (List<IdCardCheckInfo>)checkAllCust.get("custNoList");
		
		if("0".equals(lostPubBean.getAllPubIsDeputy()) && "0".equals(lostPubBean.getYseNoPass())){//本人且知道密码
			
			logger.info("本人选择知道密码");
			if("0".equals(lostPubBean.getLostType())){//查询符合挂失的所有银行卡账户信息
				
				logger.info("选择银行卡，查询符合挂失的所有银行卡账户信息");
				if(lostPubBean.getCards()!=null && lostPubBean.getCards().length > 0){
					
					for (int i = 0; i < lostPubBean.getCards().length; i++) {
						
						lostPubBean.setCardNo(lostPubBean.getCards()[i]);
						if(!checkCardMsg()){//查询所有卡信息
							return;
						}
						//筛掉卡信息与证件信息不一致的卡
						if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getAccIdName())){
							logger.info("银行卡信息与身份证读取信息 不一致");
							continue;
						}
						Map card03521 = null;
						Map card07601 = null;
						//查询卡状态1
						try {
							//上送流水调用接口前信息
							lostPubBean.getReqMCM001().setReqBefor("03521");
							card03521 = InterfaceSendMsg.card03521(lostPubBean);
							//上送流水调用接口后信息
							lostPubBean.getReqMCM001().setReqAfter((String)card03521.get("resCode"), (String)card03521.get("errMsg"));
							if(!"000000".equals(card03521.get("resCode"))){
								   logger.info("调用账户信息查询及密码验证-前置03521接口失败："+card03521.get("errMsg"));
								   closeDialog(prossDialog);
								   //发送流水信息
								   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
								   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
								   return;
							}
						
						} catch (Exception e) {
							
							logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
							closeDialog(prossDialog);
							//调用接口失败，上送流水
							lostPubBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03521接口失败");
							 return;
						}
						//查询卡状态2
						try {
							//上送流水调用接口前信息
							lostPubBean.getReqMCM001().setReqBefor("07601");
							card07601 = InterfaceSendMsg.card07601(lostPubBean);
							//上送流水调用接口后信息
							lostPubBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
							if(!"000000".equals(card07601.get("resCode"))){
								
								   logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+card07601.get("errMsg"));
								   closeDialog(prossDialog);
								   //发送流水信息
								   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
								   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card07601.get("errMsg"), "");
								   return;
							}
						
						} catch (Exception e) {
							
							logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
							closeDialog(prossDialog);
							//调用接口失败，上送流水信息
							lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用07601接口失败");
							 return;
						}
						
						//选择未销户的银行卡账户
						if(!"Q".equals(lostPubBean.getCardState())){
							
							ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
							acc.setCardOrAccOrAcc1("0");//银行卡标识
							acc.setCardNo(lostPubBean.getCards()[i]);//银行卡号
							acc.setOpenDate(lostPubBean.getOpenDate());
							acc.setDepTerm(lostPubBean.getDepTerm());
							acc.setOpenRate(lostPubBean.getOpenRate());
							acc.setAccType(lostPubBean.getAccType());
							acc.setCertNo(lostPubBean.getCertNo());
							acc.setCustName(lostPubBean.getCustName());
							acc.setEndAmt(lostPubBean.getEndAmt());
							acc.setCardEndAmt(lostPubBean.getCardEndAmt());
							acc.setProName(lostPubBean.getProName());
							acc.setProCode(lostPubBean.getProCode());
							acc.setCardState(lostPubBean.getCardState());
							acc.setCardState1((String)card03521.get("cardState1"));
							acc.setCardState2((String)card07601.get("cardState2"));
							acc.setAccState(lostPubBean.getAccState());
							acc.setDrawCond(lostPubBean.getDrawCond());
							acc.setIsAgreedDep(lostPubBean.getIsAgreedDep());
							acc.setCustNo(lostPubBean.getCustNo());
							acc.setPrintState("");
							acclist.add(acc);
							logger.info("未销户的银行卡号:"+lostPubBean.getCards()[i]);
						}else{
							logger.info("已销户的银行卡号:"+lostPubBean.getCards()[i]);
						}
					}
					
				}else{
					
					logger.info("没有查询到符合条件的银行卡信息");
					//弹框提示没有银行卡信息
					openMistakeDialog("没有查询到符合条件的银行卡信息");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							openPanel(lostPubBean.getThisComponent(), new LostTypeSelectPage());
						}
					});
					return;
				}
				
			}else if("1".equals(lostPubBean.getLostType())){//查询符合挂失的所有存单账户信息
				
				if(idList != null && idList.size()>0){//查询每个客户号的账户信息
					for (int j = 0; j < idList.size(); j++) {
						
						lostPubBean.setCustNo(idList.get(j).getCust_No().trim());
						logger.info("客户号："+idList.get(j).getCust_No());
						
						logger.info("选择存单，查询符合挂失的所有存单账户信息");
						Map checkAllAcc = checkAllAcc();//查询存单个人账号
						if("000000".equals(checkAllAcc.get("resCode"))){
							
							List<BillChangeInfoBean> list = (List<BillChangeInfoBean>)checkAllAcc.get("AccMsgList");
							
							if(list != null && list.size()>0){
								
								for (BillChangeInfoBean infoBean : list) {
									
									//存单个人账户
									if(infoBean.getAcct_thing() !=null && "1".equals(infoBean.getAcct_thing().trim())){
										
										//查询一本通外部账号
										Map inter08176 = null;
										try {
											logger.info("查询一本通外部账号:"+infoBean.getAcct_no().trim());
											lostPubBean.setAllPubAccNo(infoBean.getAcct_no().trim());
											//上送流水调用接口前信息
											lostPubBean.getReqMCM001().setReqBefor("08176");
											inter08176 = InterfaceSendMsg.inter08176(lostPubBean);
											//上送流水调用接口后信息
											lostPubBean.getReqMCM001().setReqAfter((String)inter08176.get("resCode"), (String)inter08176.get("errMsg"));
											if(!"000000".equals(inter08176.get("resCode"))){
												
												logger.info("调用一本通账号查询【20098】-前置【08176】接口失败："+inter08176.get("errMsg"));
											}
										
										} catch (Exception e) {
											
											logger.error("调用一本通账号查询【20098】-前置【08176】接口异常："+e);
											
										}
										String accNo = (String)inter08176.get("SVR_JRNL_NO_R");
										String maxState = (String)inter08176.get("MX_STAT");
										if(accNo!=null && !"".equals(accNo)){
											if(!"2".equals(maxState)){
												infoBean.setAcct_no(accNo);
											}
										}
										//筛掉活期存折和一本通(0000-活期存折，0001 0011 0012 0013 0014 0015 0018-其他存折类，8888-定期一本通，9999-活期一本通)
										if(!"0000".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0001".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
												!"0011".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0012".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
												!"0013".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0014".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
												!"0015".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0018".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
												!"8888".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"9999".equals(infoBean.getAcct_no().trim().substring(9, 13)) ){
											
											ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
											acc.setCardOrAccOrAcc1("1");//存单个人账户标识
											acc.setAccNo(infoBean.getAcct_no());//存单账号
											acc.setOpenDate(infoBean.getOpen_date());
											acc.setStartDate(infoBean.getStart_date());
											acc.setEndDate(infoBean.getDue_date());
											acc.setDepTerm(infoBean.getDep_term());
											acc.setOpenRate(infoBean.getOpen_rate());
											acc.setAccType("个人存单");
											acc.setCertNo(infoBean.getCert_no());
											acc.setCustName(infoBean.getCust_name());
											acc.setEndAmt(infoBean.getEnd_amt());
											acc.setProCode(infoBean.getProduct_code());
											acc.setAccState(infoBean.getAcct_stat());
											acc.setDrawCond(infoBean.getPay_cond());
											acc.setPrintState("");
											acc.setCustNo(idList.get(j).getCust_No().trim());
											lostPubBean.setCardSubNo(infoBean.getAcct_no().trim());
											Map checkAcc = checkAcc();
											if("000000".equals(checkAcc.get("resCode"))){
												acc.setProName((String)checkAcc.get("proName"));
												//替换挂失状态值
												String str = (String)checkAcc.get("accStatus");
												char[] ch = str.toCharArray();
												ch[7] = infoBean.getAcct_stat().charAt(7);
												acc.setAccState(String.valueOf(ch));
												acc.setTotalAmt((String)checkAcc.get("totalAmt"));
												acc.setOpenChannel((String)checkAcc.get("openChanel"));
												acc.setPreDate((String)checkAcc.get("preDate"));
											}else{
												return;
											}
											acclist.add(acc);	
										}
									}
								}
							}
							
						}else{
							return;
						}
						
						//查询存单电子子账号
						Map checkECardSubAcc = checkECardSubAcc();
						if("000000".equals(checkECardSubAcc.get("resCode"))){
							
							List<EAccInfoBean> list = (List<EAccInfoBean>)checkECardSubAcc.get("eSubAcc");
							
							if(list != null && list.size()>0){
								
								for (EAccInfoBean infoBean : list) {
									
									ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
									acc.setCardOrAccOrAcc1("1_2");//存单电子子账户标识
									acc.setAccNo(infoBean.geteCardNo()+"-"+infoBean.geteSubAccNo());//存单账号
									acc.setOpenDate(infoBean.geteOpenDate());
									acc.setEndDate(infoBean.geteEndDate());
									acc.setDepTerm(infoBean.geteDepTerm());
									acc.setOpenRate(infoBean.geteOpenRate());
									acc.setAccType("电子子账户");
									acc.setCertNo(infoBean.geteCertNo());
									acc.setCustName(lostPubBean.getAllPubIdCardName());
									acc.setEndAmt(infoBean.geteBalance());
									acc.setProName(infoBean.geteProductName());
									acc.setProCode(infoBean.geteProductCode());
									acc.setAccState(infoBean.geteAccNoState());
									acc.setPrintState(infoBean.getePrintState());
									acc.setCustNo(idList.get(j).getCust_No().trim());
									lostPubBean.setCardSubNo(infoBean.geteCardNo().trim()+"-"+infoBean.geteSubAccNo().trim());
									Map checkAcc = checkAcc();
									if("000000".equals(checkAcc.get("resCode"))){
										acc.setProName((String)checkAcc.get("proName"));
										acc.setAccState((String)checkAcc.get("accStatus"));
										acc.setTotalAmt((String)checkAcc.get("totalAmt"));
										acc.setDrawCond((String)checkAcc.get("drawCond"));
										acc.setOpenChannel((String)checkAcc.get("openChanel"));
										acc.setPreDate((String)checkAcc.get("preDate"));
										acc.setStartDate((String)checkAcc.get("startDate"));
									}else{
										return;
									}
									acclist.add(acc);	
								}
							}
							
						}else{
							return;
						}
					}
				
				}else{
					logger.info("未查询到客户号");
				}	
					
				//查询存单卡下子账号
				if(lostPubBean.getCards()!=null && lostPubBean.getCards().length > 0){
					
					for (int i = 0; i < lostPubBean.getCards().length; i++) {
						
						//查询卡状态
						LostPubBean bean = new LostPubBean();
						bean.setCardNo(lostPubBean.getCards()[i]);
						try {
							//上送流水调用接口前信息
							lostPubBean.getReqMCM001().setReqBefor("03845");
							Map inter03845 = InterfaceSendMsg.inter03845(bean);
							//上送流水调用接口后信息
							lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
							if(!"000000".equals(inter03845.get("resCode"))){
								   logger.info("调用卡信息查询【75010】--03845接口失败："+inter03845.get("errMsg"));
								   closeDialog(prossDialog);
								   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
								   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) inter03845.get("errMsg"), "");
								   return;
							}
						
						} catch (Exception e) {
							
							logger.error("调用卡信息查询【75010】--03845接口异常："+e);
							closeDialog(prossDialog);
							lostPubBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03845接口失败");
							return;
						}
						//筛掉卡信息与证件信息不一致的卡
						if(!lostPubBean.getAllPubIDNo().equals(bean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(bean.getAccIdName())){
							logger.info("银行卡信息与身份证读取信息 不一致");
							continue;
						}
						
						if("N".equals(bean.getCardState())){//不查询未激活的卡
							continue;
						}
						
						lostPubBean.setCardNo(lostPubBean.getCards()[i]);
						
						Map checkCardSubAcc = checkCardSubAcc();
						if("000000".equals(checkCardSubAcc.get("resCode"))){
							
							List<SubAccInfoBean> list = (List<SubAccInfoBean>)checkCardSubAcc.get("cardSubAcc");
							
							if(list != null && list.size()>0){
								
								for (SubAccInfoBean infoBean : list) {
									
									ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
									acc.setCardOrAccOrAcc1("1_1");//存单卡下子账户标识
									acc.setAccNo(infoBean.getAccNo()+"-"+infoBean.getSubAccNo());//存单账号
									acc.setOpenDate(infoBean.getOpenDate());
									acc.setEndDate(infoBean.getEndIntDate());
									acc.setDepTerm(infoBean.getDepTerm());
									acc.setOpenRate(infoBean.getOpenRate());
									acc.setAccType("卡下子账户");
									acc.setCertNo(infoBean.getBill());
									acc.setCustName(lostPubBean.getAllPubIdCardName());
									acc.setEndAmt(infoBean.getBalance());
									acc.setProName(infoBean.getProductName());
									acc.setProCode(infoBean.getProductCode());
									acc.setAccState(infoBean.getAccNoState());
									acc.setPrintState(infoBean.getPrintState());
									acc.setCustNo(bean.getCustNo());
									lostPubBean.setCardSubNo(infoBean.getAccNo().trim()+"-"+infoBean.getSubAccNo().trim());
									Map checkAcc = checkAcc();
									if("000000".equals(checkAcc.get("resCode"))){
										acc.setProName((String)checkAcc.get("proName"));
										acc.setAccState((String)checkAcc.get("accStatus"));
										acc.setTotalAmt((String)checkAcc.get("totalAmt"));
										acc.setDrawCond((String)checkAcc.get("drawCond"));
										acc.setOpenChannel((String)checkAcc.get("openChanel"));
										acc.setPreDate((String)checkAcc.get("preDate"));
										acc.setStartDate((String)checkAcc.get("startDate"));
									}else{
										return;
									}
									acclist.add(acc);	
								}
							}
							
						}else{
							return;
						}
					}
				}
				
				if(acclist.size()==0){
					
					logger.info("没有查询到符合条件的存单信息");
					//弹框提示没有存单信息
					openMistakeDialog("没有查询到符合条件的存单信息");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							openPanel(lostPubBean.getThisComponent(), new LostTypeSelectPage());
						}
					});
					return;
				}
				
			}else if("2".equals(lostPubBean.getLostType())){//查询符合挂失的所有存折账户信息
				
				if(idList != null && idList.size()>0){//查询每个客户号的账户信息
					for (int j = 0; j < idList.size(); j++) {
						
						lostPubBean.setCustNo(idList.get(j).getCust_No().trim());
						logger.info("客户号："+idList.get(j).getCust_No());
						
						logger.info("选择存折，查询符合挂失的所有存折账户信息");
						Map checkAllAcc = checkAllAcc();//查询存折账号
						if("000000".equals(checkAllAcc.get("resCode"))){
							
							List<BillChangeInfoBean> accList = (List<BillChangeInfoBean>)checkAllAcc.get("AccMsgList");
							
							if(accList != null && accList.size()>0){
								
								for (BillChangeInfoBean infoBean : accList) {
									
									if(infoBean.getAcct_thing() !=null && "1".equals(infoBean.getAcct_thing().trim())){//存折账户
										
										//查询一本通外部账号
										Map inter08176 = null;
										try {
											logger.info("查询一本通外部账号:"+infoBean.getAcct_no().trim());
											lostPubBean.setAllPubAccNo(infoBean.getAcct_no().trim());
											inter08176 = InterfaceSendMsg.inter08176(lostPubBean);
											if(!"000000".equals(inter08176.get("resCode"))){
												
												logger.info("调用一本通账号查询【20098】-前置【08176】接口失败："+inter08176.get("errMsg"));
											}
										
										} catch (Exception e) {
											
											logger.error("调用一本通账号查询【20098】-前置【08176】接口异常："+e);
											
										}
										String accNo = (String)inter08176.get("SVR_JRNL_NO_R");
										String maxState = (String)inter08176.get("MX_STAT");
										if(accNo!=null && !"".equals(accNo)){
											if(!"2".equals(maxState)){
												infoBean.setAcct_no(accNo);
											}
										}
										
										if("0000".equals(infoBean.getAcct_no().trim().substring(9, 13)) || "0001".equals(infoBean.getAcct_no().trim().substring(9, 13)) ||
												"0014".equals(infoBean.getAcct_no().trim().substring(9, 13)) ||"0015".equals(infoBean.getAcct_no().trim().substring(9, 13))){
											
											ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
											acc.setCardOrAccOrAcc1("2");//存折标识
											acc.setAccNo1(infoBean.getAcct_no());//存折账号
											acc.setOpenDate(infoBean.getOpen_date());
											acc.setEndDate(infoBean.getDue_date());
											acc.setDepTerm(infoBean.getDep_term());
											acc.setOpenRate(infoBean.getOpen_rate());
											acc.setAccType("存折");
											acc.setCertNo(infoBean.getCert_no());
											acc.setCustName(infoBean.getCust_name());
											acc.setEndAmt(infoBean.getEnd_amt());
											acc.setProCode(infoBean.getProduct_code());
											acc.setAccState(infoBean.getAcct_stat());
											acc.setDrawCond(infoBean.getPay_cond());
											acc.setPrintState("");
											acc.setCustNo(idList.get(j).getCust_No().trim());
											lostPubBean.setCardSubNo(infoBean.getAcct_no().trim());
											Map checkAcc = checkAcc();
											if("000000".equals(checkAcc.get("resCode"))){
												acc.setProName((String)checkAcc.get("proName"));
												//替换挂失状态值
												String str = (String)checkAcc.get("accStatus");
												char[] ch = str.toCharArray();
												ch[7] = infoBean.getAcct_stat().charAt(7);
												acc.setAccState(String.valueOf(ch));
												acc.setTotalAmt((String)checkAcc.get("totalAmt"));
												acc.setOpenChannel((String)checkAcc.get("openChanel"));
												acc.setPreDate((String)checkAcc.get("preDate"));
											}else{
												return;
											}
											acclist.add(acc);	
										}
									}
								}
							}
							
						}else{
							return;
						}
					}
				}else{
					logger.info("未查询到客户号");
				}
				
				if(acclist.size()==0){
					
					logger.info("没有查询到符合条件的存折信息");
					//弹框提示没有存折信息
					openMistakeDialog("没有查询到符合条件的存折信息");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							mistakeDialog.dispose();
							openPanel(lostPubBean.getThisComponent(), new LostTypeSelectPage());
						}
					});
					return;
				}
			}
			
		}else{//本人且忘记密码
			
			//查询符合挂失的所有账户信息
			logger.info("本人选择忘记密码");
			
			//查询银行卡账户信息
			logger.info("先查询符合挂失的所有银行卡账户信息");
			if(lostPubBean.getCards()!=null && lostPubBean.getCards().length > 0){
				
				for (int i = 0; i < lostPubBean.getCards().length; i++) {
					
					lostPubBean.setCardNo(lostPubBean.getCards()[i]);
					if(!checkCardMsg()){//查询所有卡信息
						return;
					}
					//筛掉卡信息与证件信息不一致的卡
					if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getAccIdName())){
						logger.info("银行卡信息与身份证读取信息 不一致");
						continue;
					}
					Map card03521 = null;
					Map card07601 = null;
					//查询卡状态1
					try {
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("03521");
						card03521 = InterfaceSendMsg.card03521(lostPubBean);
						//上送流水调用接口后信息
						lostPubBean.getReqMCM001().setReqAfter((String)card03521.get("resCode"), (String)card03521.get("errMsg"));
						if(!"000000".equals(card03521.get("resCode"))){
							
							   logger.info("调用账户信息查询及密码验证-前置03521接口失败："+card03521.get("errMsg"));
							   closeDialog(prossDialog);
							   //上送流水信息
							   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
							   return;
						}
					
					} catch (Exception e) {
						
						logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
						closeDialog(prossDialog);
						//调用接口异常，上送流水信息
						lostPubBean.getReqMCM001().setIntereturnmsg("调用接口03521异常");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03521接口失败");
						 return;
					}
					//查询卡状态2
					try {
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("07601");
						card07601 = InterfaceSendMsg.card07601(lostPubBean);
						//上送流水调用接口后信息
						lostPubBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
						if(!"000000".equals(card07601.get("resCode"))){
							
							   logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+card07601.get("errMsg"));
							   closeDialog(prossDialog);
							   //上送流水信息
							   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card07601.get("errMsg"), "");
							   return;
						}
					
					} catch (Exception e) {
						
						logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
						closeDialog(prossDialog);
						//调用接口异常，上送流水信息
						lostPubBean.getReqMCM001().setIntereturnmsg("调用接口07601异常");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用07601接口失败");
						 return;
					}
					
					//选择未销户的银行卡账户
					if(!"Q".equals(lostPubBean.getCardState())){
						
						ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
						acc.setCardOrAccOrAcc1("0");//银行卡标识
						acc.setCardNo(lostPubBean.getCards()[i]);//银行卡号
						acc.setOpenDate(lostPubBean.getOpenDate());
						acc.setDepTerm(lostPubBean.getDepTerm());
						acc.setOpenRate(lostPubBean.getOpenRate());
						acc.setAccType(lostPubBean.getAccType());
						acc.setCertNo(lostPubBean.getCertNo());
						acc.setCustName(lostPubBean.getCustName());
						acc.setEndAmt(lostPubBean.getEndAmt());
						acc.setCardEndAmt(lostPubBean.getCardEndAmt());
						acc.setProName(lostPubBean.getProName());
						acc.setProCode(lostPubBean.getProCode());
						acc.setCardState(lostPubBean.getCardState());
						acc.setCardState1((String)card03521.get("cardState1"));
						acc.setCardState2((String)card07601.get("cardState2"));
						acc.setAccState(lostPubBean.getAccState());
						acc.setDrawCond(lostPubBean.getDrawCond());
						acc.setIsAgreedDep(lostPubBean.getIsAgreedDep());
						acc.setPrintState("");
						acc.setCustNo(lostPubBean.getCustNo());
						acclist.add(acc);
						logger.info("未销户的银行卡号:"+lostPubBean.getCards()[i]);
					}else{
						logger.info("已销户的银行卡号:"+lostPubBean.getCards()[i]);
					}
				}
			}
			
			if(idList != null && idList.size()>0){//查询每个客户号的账户信息
				for (int j = 0; j < idList.size(); j++) {
					
					lostPubBean.setCustNo(idList.get(j).getCust_No().trim());
					logger.info("客户号："+idList.get(j).getCust_No());
					
					//查询存单个人账户/存折一本通信息
					logger.info("继续查询符合挂失的所有存单个人账户/存折一本通账户信息");
					Map checkAllAcc = checkAllAcc();
					if("000000".equals(checkAllAcc.get("resCode"))){
						
						List<BillChangeInfoBean> list = (List<BillChangeInfoBean>)checkAllAcc.get("AccMsgList");
						
						if(list != null && list.size()>0){
							
							for (BillChangeInfoBean infoBean : list) {
								
								if("1".equals(infoBean.getAcct_thing().trim())){
									
									//查询一本通外部账号
									Map inter08176 = null;
									try {
										logger.info("查询一本通外部账号:"+infoBean.getAcct_no().trim());
										lostPubBean.setAllPubAccNo(infoBean.getAcct_no().trim());
										inter08176 = InterfaceSendMsg.inter08176(lostPubBean);
										if(!"000000".equals(inter08176.get("resCode"))){
											
											logger.info("调用一本通账号查询【20098】-前置【08176】接口失败："+inter08176.get("errMsg"));
										}
									
									} catch (Exception e) {
										
										logger.error("调用一本通账号查询【20098】-前置【08176】接口异常："+e);
										
									}
									String accNo = (String)inter08176.get("SVR_JRNL_NO_R");
									String maxState = (String)inter08176.get("MX_STAT");
									if(accNo!=null && !"".equals(accNo)){
										if(!"2".equals(maxState)){
											infoBean.setAcct_no(accNo);
										}
									}
									//筛掉活期存折和一本通(0000-活期存折，0001 0011 0012 0013 0014 0015 0018-其他存折类，8888-定期一本通，9999-活期一本通)
									if(!"0000".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0001".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
											!"0011".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0012".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
											!"0013".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0014".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
											!"0015".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"0018".equals(infoBean.getAcct_no().trim().substring(9, 13)) &&
											!"8888".equals(infoBean.getAcct_no().trim().substring(9, 13)) && !"9999".equals(infoBean.getAcct_no().trim().substring(9, 13))){
										
										ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
										acc.setCardOrAccOrAcc1("1");//存单个人账户标识
										acc.setAccNo(infoBean.getAcct_no());//存单账号
										acc.setOpenDate(infoBean.getOpen_date());
										acc.setStartDate(infoBean.getStart_date());
										acc.setEndDate(infoBean.getDue_date());
										acc.setDepTerm(infoBean.getDep_term());
										acc.setOpenRate(infoBean.getOpen_rate());
										acc.setAccType("个人存单");
										acc.setCertNo(infoBean.getCert_no());
										acc.setCustName(infoBean.getCust_name());
										acc.setEndAmt(infoBean.getEnd_amt());
										acc.setProCode(infoBean.getProduct_code());
										acc.setAccState(infoBean.getAcct_stat());
										acc.setDrawCond(infoBean.getPay_cond());
										acc.setPrintState("");
										acc.setCustNo(idList.get(j).getCust_No().trim());
										lostPubBean.setCardSubNo(infoBean.getAcct_no().trim());
										Map checkAcc = checkAcc();
										if("000000".equals(checkAcc.get("resCode"))){
											acc.setProName((String)checkAcc.get("proName"));
											//替换挂失状态值
											String str = (String)checkAcc.get("accStatus");
											char[] ch = str.toCharArray();
											ch[7] = infoBean.getAcct_stat().charAt(7);
											acc.setAccState(String.valueOf(ch));
											acc.setTotalAmt((String)checkAcc.get("totalAmt"));
											acc.setOpenChannel((String)checkAcc.get("openChanel"));
											acc.setPreDate((String)checkAcc.get("preDate"));
										}else{
											return;
										}
										acclist.add(acc);	
										
									}else if("0000".equals(infoBean.getAcct_no().trim().substring(9, 13)) || "0001".equals(infoBean.getAcct_no().trim().substring(9, 13)) ||
											"0014".equals(infoBean.getAcct_no().trim().substring(9, 13)) || "0015".equals(infoBean.getAcct_no().trim().substring(9, 13))){//存折账户
										
										ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
										acc.setCardOrAccOrAcc1("2");//存折标识
										acc.setAccNo1(infoBean.getAcct_no());//存折账号
										acc.setOpenDate(infoBean.getOpen_date());
										acc.setEndDate(infoBean.getDue_date());
										acc.setDepTerm(infoBean.getDep_term());
										acc.setOpenRate(infoBean.getOpen_rate());
										acc.setAccType("存折");
										acc.setCertNo(infoBean.getCert_no());
										acc.setCustName(infoBean.getCust_name());
										acc.setEndAmt(infoBean.getEnd_amt());
										acc.setProCode(infoBean.getProduct_code());
										acc.setAccState(infoBean.getAcct_stat());
										acc.setDrawCond(infoBean.getPay_cond());
										acc.setPrintState("");
										acc.setCustNo(idList.get(j).getCust_No().trim());
										lostPubBean.setCardSubNo(infoBean.getAcct_no().trim());
										Map checkAcc = checkAcc();
										if("000000".equals(checkAcc.get("resCode"))){
											acc.setProName((String)checkAcc.get("proName"));
											//替换挂失状态值
											String str = (String)checkAcc.get("accStatus");
											char[] ch = str.toCharArray();
											ch[7] = infoBean.getAcct_stat().charAt(7);
											acc.setAccState(String.valueOf(ch));
											acc.setTotalAmt((String)checkAcc.get("totalAmt"));
											acc.setOpenChannel((String)checkAcc.get("openChanel"));
											acc.setPreDate((String)checkAcc.get("preDate"));
										}else{
											return;
										}
										acclist.add(acc);	
									}
								}
							}
						}
						
					}else{
						return;
					}
					
					//查询存单电子子账号
					logger.info("继续查询符合挂失的所有存单电子子账户信息");
					Map checkECardSubAcc = checkECardSubAcc();
					if("000000".equals(checkECardSubAcc.get("resCode"))){
						
						List<EAccInfoBean> list = (List<EAccInfoBean>)checkECardSubAcc.get("eSubAcc");
						
						if(list != null && list.size()>0){
							
							for (EAccInfoBean infoBean : list) {
								
								ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
								acc.setCardOrAccOrAcc1("1_2");//存单电子子账户标识
								acc.setAccNo(infoBean.geteCardNo()+"-"+infoBean.geteSubAccNo());//存单账号
								acc.setOpenDate(infoBean.geteOpenDate());
								acc.setEndDate(infoBean.geteEndDate());
								acc.setDepTerm(infoBean.geteDepTerm());
								acc.setOpenRate(infoBean.geteOpenRate());
								acc.setAccType("电子子账户");
								acc.setCertNo(infoBean.geteCertNo());
								acc.setCustName(lostPubBean.getAllPubIdCardName());
								acc.setEndAmt(infoBean.geteBalance());
								acc.setProName(infoBean.geteProductName());
								acc.setProCode(infoBean.geteProductCode());
								acc.setAccState(infoBean.geteAccNoState());
								acc.setPrintState(infoBean.getePrintState());
								acc.setCustNo(idList.get(j).getCust_No().trim());
								lostPubBean.setCardSubNo(infoBean.geteCardNo().trim()+"-"+infoBean.geteSubAccNo().trim());
								Map checkAcc = checkAcc();
								if("000000".equals(checkAcc.get("resCode"))){
									acc.setProName((String)checkAcc.get("proName"));
									acc.setAccState((String)checkAcc.get("accStatus"));
									acc.setTotalAmt((String)checkAcc.get("totalAmt"));
									acc.setDrawCond((String)checkAcc.get("drawCond"));
									acc.setOpenChannel((String)checkAcc.get("openChanel"));
									acc.setPreDate((String)checkAcc.get("preDate"));
									acc.setStartDate((String)checkAcc.get("startDate"));
								}else{
									return;
								}
								acclist.add(acc);	
							}
						}
						
					}else{
						return;
					}
				}
			}else{
				logger.info("未查询到客户号");
			}
			
			//查询存单卡下子账号
			logger.info("继续查询符合挂失的所有存单卡下子账户信息");
			if(lostPubBean.getCards()!=null && lostPubBean.getCards().length > 0){
				
				for (int i = 0; i < lostPubBean.getCards().length; i++) {
					
					//查询卡状态
					LostPubBean bean = new LostPubBean();
					bean.setCardNo(lostPubBean.getCards()[i]);
					try {
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("03845");
						Map inter03845 = InterfaceSendMsg.inter03845(bean);
						//上送流水调用接口后信息
						lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"),(String)inter03845.get("errMsg"));
						if(!"000000".equals(inter03845.get("resCode"))){
							
							   logger.info("调用卡信息查询【75010】--03845接口失败："+inter03845.get("errMsg"));
							   closeDialog(prossDialog);
							   //上送流水信息
							   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) inter03845.get("errMsg"), "");
							   return;
						}
					
					} catch (Exception e) {
						
						logger.error("调用卡信息查询【75010】--03845接口异常："+e);
						closeDialog(prossDialog);
						//调用接口异常，上送流水信息
						lostPubBean.getReqMCM001().setIntereturnmsg("调用接口03845异常");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03845接口失败");
						return;
					}
					//筛掉卡信息与证件信息不一致的卡
					if(!lostPubBean.getAllPubIDNo().equals(bean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(bean.getAccIdName())){
						logger.info("银行卡信息与身份证读取信息 不一致");
						continue;
					}
					
					if("N".equals(bean.getCardState())){//不查询未激活的卡
						continue;
					}
					
					lostPubBean.setCardNo(lostPubBean.getCards()[i]);
					
					Map checkCardSubAcc = checkCardSubAcc();
					if("000000".equals(checkCardSubAcc.get("resCode"))){
						
						List<SubAccInfoBean> list = (List<SubAccInfoBean>)checkCardSubAcc.get("cardSubAcc");
						
						if(list != null && list.size()>0){
							
							for (SubAccInfoBean infoBean : list) {
								
								ShowAccNoMsgBean acc = new ShowAccNoMsgBean();
								acc.setCardOrAccOrAcc1("1_1");//存单卡下子账户标识
								acc.setAccNo(infoBean.getAccNo()+"-"+infoBean.getSubAccNo());//存单账号
								acc.setOpenDate(infoBean.getOpenDate());
								acc.setEndDate(infoBean.getEndIntDate());
								acc.setDepTerm(infoBean.getDepTerm());
								acc.setOpenRate(infoBean.getOpenRate());
								acc.setAccType("卡下子账户");
								acc.setCertNo(infoBean.getBill());
								acc.setCustName(lostPubBean.getAllPubIdCardName());
								acc.setEndAmt(infoBean.getBalance());
								acc.setProName(infoBean.getProductName());
								acc.setProCode(infoBean.getProductCode());
								acc.setAccState(infoBean.getAccNoState());
								acc.setPrintState(infoBean.getPrintState());
								acc.setCustNo(bean.getCustNo());
								lostPubBean.setCardSubNo(infoBean.getAccNo().trim()+"-"+infoBean.getSubAccNo().trim());
								Map checkAcc = checkAcc();
								if("000000".equals(checkAcc.get("resCode"))){
									acc.setProName((String)checkAcc.get("proName"));
									acc.setAccState((String)checkAcc.get("accStatus"));
									acc.setTotalAmt((String)checkAcc.get("totalAmt"));
									acc.setDrawCond((String)checkAcc.get("drawCond"));
									acc.setOpenChannel((String)checkAcc.get("openChanel"));
									acc.setPreDate((String)checkAcc.get("preDate"));
									acc.setStartDate((String)checkAcc.get("startDate"));
								}else{
									return;
								}
								acclist.add(acc);	
							}
						}
						
					}else{
						return;
					}
				}
			}
			
			if(acclist.size()==0){
				
				//弹框提示没有银行卡/存单/存折信息
				openMistakeDialog("没有查询到符合条件的账户信息");
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						lostPubBean.setLostType("");
						lostPubBean.setRecOrCan("");
						mistakeDialog.dispose();
						openPanel(lostPubBean.getThisComponent(), new LostPassSelectPage());
					}
				});
				return;
			}
		}
		
		lostPubBean.getAccMap().put("list", acclist);
		
		//跳转账户信息展现页
		openPanel(lostPubBean.getThisComponent(),new LostShowAccNoPage());
		
	}
	
	/**
	 * 校验本人身份证信息和联网核查
	 */
	public void checkMySelfIdCardInfos(){
		//代开等待图标
		prossDialog.showDialog();
		IdCardReadBean bean =lostPubBean.getAllPubIDCardReadInfo();
		lostPubBean.setAllPubIDNo(bean.getIdCode());
		lostPubBean.setAllPubIdCardName(bean.getName());
		lostPubBean.setAllPubQfjg(bean.getIssuingUnit());
		lostPubBean.setAllPubAddress(bean.getAddress());
		lostPubBean.setAllPubSex(bean.getSex());
		lostPubBean.setAllPubIdCardtitle(bean.getPhotoPath());
		lostPubBean.setAllPubIdCardback(bean.getBackFace());
		lostPubBean.setAllPubIdCardface(bean.getFrontFace());
		lostPubBean.setAllPubBirthday(bean.getBirthday());
		lostPubBean.setAllPubEndDate(bean.getEndDate());
		
		//查询核心日期
		try {
			
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08169");
			Map result08169 = InterfaceSendMsg.inter08169(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08169.get("resCode"), (String)result08169.get("errMsg"));
			if(!"000000".equals(result08169.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)result08169.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//发送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"查询核心日期失败",(String)result08169.get("errMsg"),"");
				return;
			}
		} catch (Exception e) {
			logger.error("查询核心日期失败："+e);
			prossDialog.disposeDialog();
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口08169异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "抱歉，查询核心日期失败，请联系大堂经理！", "", "");
			return;
		}
		
		//日期转换工具
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//当前核心日期
		String nowDateStr = lostPubBean.getAllPubSvrDate();
		try {
			Date nowDate = sdf.parse(nowDateStr);
			//判断身份证有效期是否到期（若为长期则有效）
//			if (!lostPubBean.getAllPubEndDate().contains("长期")) {
//				//如果身份证有效期不是长期时转换成日期
//				Date endDate = sdf.parse(bean.getEndDate().replace(".", "/"));
//				if(endDate.before(nowDate)){
//					prossDialog.disposeDialog();
//					logger.info("身份证有已过有效期，当前核心日期："+nowDateStr+"；身份证有效期为："+bean.getEndDate());
//					try {
//						closeLed("1");
//					} catch (Exception e1) {
//						logger.error("LED灯关闭失败" + e1);
//					}
//					//上送流水信息
//					lostPubBean.getReqMCM001().setReqBefor("");
//					lostPubBean.getReqMCM001().setIntereturnmsg("身份证超过有效期");
//					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
//					serverStop(lostPubBean.getThisComponent(), "抱歉，您的身份证已过有效期。", "", "");
//					return;
//				}
//			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			prossDialog.disposeDialog();
			logger.error("校验身份证有效期失败："+e1);
			try {
				closeLed("1");
			} catch (Exception e2) {
				logger.error("LED灯关闭失败" + e2);
			}
			//上送流水信息
			lostPubBean.getReqMCM001().setReqBefor("");
			lostPubBean.getReqMCM001().setIntereturnmsg("校验身份证有效期失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "抱歉，校验身份证有效期失败，请联系大堂经理！", "", "");
			return;
		}
		
		try {
			//将本人的身份证号和名字赋予查询的公共字段
			lostPubBean.setAllPubCheckIdCardNo(bean.getIdCode());
			lostPubBean.setAllPubCheckIdCardName(bean.getName());
			
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07670");
			
			Map reCustInfo=InterfaceSendMsg.inter07670(lostPubBean);
			
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"), (String)reCustInfo.get("errMsg"));
			
			if(!"000000".equals(reCustInfo.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)reCustInfo.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//发送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"身份联网核查失败",(String)reCustInfo.get("errMsg"),"");
				return;
			}
			lostPubBean.setAllPubPhotoPath((String)reCustInfo.get("filePath"));
			lostPubBean.setAllPubInspect((String)reCustInfo.get("CORE_RET_MSG"));
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用07670身份联网核查失败:"+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口07670异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"身份联网核查失败","","调用07670接口异常!");
			return;
		}
		
		//查询本人电话信息
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("04422");
			Map result04422 = InterfaceSendMsg.inter04422(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result04422.get("resCode"), (String)result04422.get("errMsg"));
			
			if(!"000000".equals(result04422.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)result04422.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//发送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"客户基本信息查询失败",(String)result04422.get("errMsg"),"");
				return;
			}
			String mphone = (String)result04422.get("Mphone");
			String phone = (String)result04422.get("phone");
			if(mphone!=null && !"".equals(mphone)){
				lostPubBean.setAllPubPhone(mphone);
			}else if(phone!=null && !"".equals(phone)){
				lostPubBean.setAllPubPhone(phone);
			}
			String address = (String)result04422.get("address");
			if(address!=null && !"".equals(address)){
				lostPubBean.setAllPubAddress(address);
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用04422客户基本信息查询失败失败:"+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口04422异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"客户基本信息查询失败","","调用04422接口异常!");
			return;
		}
		
		//校验本人年龄是否年满16周岁
		String birthday = bean.getBirthday().replace("-", "/");
		try {
			boolean result = DateTool.checkYearsIsOk(birthday, nowDateStr, 16);
			if(!result){
				prossDialog.disposeDialog();
				logger.error("本人未满十六周岁，不允许本人办理.");
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//发送流水信息
				lostPubBean.getReqMCM001().setIntereturnmsg("本人未满十六周岁，不允许办理挂失申请");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"本人未满十六周岁，不允许在机具申请挂失。","","");
				return;
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			e.printStackTrace();
			logger.error("校验本人是否年满16周岁异常："+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("校验本人是否年满十六周岁失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"校验本人是否年满16周岁异常：","","");
			return;
		}
		
		
		//进行黑灰名单查询
		//0-正常 1-涉案名单 2-可疑名单
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("01597");
			Map map01597 = InterfaceSendMsg.inter01597(lostPubBean);
			String resCode = (String)map01597.get("resCode");
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map01597.get("resCode"), (String)map01597.get("errMsg"));
			if(!"000000".equals(resCode)){
				if("0010".equals(resCode)){
					logger.info("本人身份为涉案身份："+resCode);
					lostPubBean.setAllPubSttn("1");//身份涉案
					prossDialog.disposeDialog();
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//发送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(),"抱歉，本人身份涉案，禁止办理业务。",(String)map01597.get("errMsg"),"");
					return;
				}else if("0020".equals(resCode)){
					logger.info("本人身份为可疑身份："+resCode);
					lostPubBean.setAllPubSttn("2");//身份可疑
					prossDialog.disposeDialog();
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//发送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(),"抱歉，本人身份可疑，禁止办理业务。",(String)map01597.get("errMsg"),"");
					return;
				}else{
					prossDialog.disposeDialog();
					logger.error((String)map01597.get("errMsg"));
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//发送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					this.serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理",(String)map01597.get("errMsg"),"");
					return;
				}
			}else{
				logger.info("本人身份正常："+resCode);
				lostPubBean.setAllPubSttn("0");
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597黑灰名单查询接口失败："+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口01597异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理","","调用黑灰名单查询接口失败");
			return;
		}
		
		
		//判断是挂失业务还是解挂业务
		if(lostPubBean.getLOrS()!=null && "0".equals(lostPubBean.getLOrS())){
			//判断是否有代理人
			//若为代理人办理，则进入插入代理人身份证页面
			//无代理人则进入拍照页面
			if("0".equals(lostPubBean.getAllPubIsDeputy())){
				lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
			}else{
				lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL");
			}
			lostPubBean.setUpStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
		}else{
			//下一步进入拍照页面
			lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
			lostPubBean.setUpStepName("SOLVE_LOST_SELECT_PAGE");
		}
		prossDialog.disposeDialog();
		try {
			closeLed("1");
		} catch (Exception e1) {
			logger.error("LED灯关闭失败" + e1);
		}
		openPanel(lostPubBean.getThisComponent(),new AllPublicOutputIdCardPanel());
	}
	
	/**
	 * 	校验代理人身份证信息和联网核查
	 */
	public void checkAgentIdCardInfos(){
		//打开等待图标
		prossDialog.showDialog();
		IdCardReadBean bean =lostPubBean.getAllPubAgentIDCardReadInfo();
		lostPubBean.setAllPubAgentIDNo(bean.getIdCode());
		lostPubBean.setAllPubAgentIdCardName(bean.getName());
		lostPubBean.setAllPubAgentQfjg(bean.getIssuingUnit());
		lostPubBean.setAllPubAgentAddress(bean.getAddress());
		lostPubBean.setAllPubAgentSex(bean.getSex());
		lostPubBean.setAllPubAgentIdCardtitle(bean.getPhotoPath());
		lostPubBean.setAllPubAgentIdCardback(bean.getBackFace());
		lostPubBean.setAllPubAgentIdCardface(bean.getFrontFace());
		lostPubBean.setAllPubAgentBirthday(bean.getBirthday());
		lostPubBean.setAllPubAgentEndDate(bean.getEndDate());
		//将代理人的身份证号和姓名赋值给用于查询的公共字段
		lostPubBean.setAllPubCheckIdCardNo(bean.getIdCode());
		lostPubBean.setAllPubCheckIdCardName(bean.getName());
		
		//判断代理人是否为本人
		if(lostPubBean.getAllPubIDNo().equals(lostPubBean.getAllPubAgentIDNo())){
			logger.info("代理人是本人，不能办理业务");
			//弹框提示不能为同一人
			openConfirmDialog("代理人不能为本人,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//跳转代理人身份证插入页
					confirmDialog.disposeDialog();
					lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL");
					allPubTransFlow.transFlow();
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
					//进入代理人选择页
					confirmDialog.disposeDialog();
					lostPubBean.setNextStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
					allPubTransFlow.transFlow();
				}
				
			});
			return;
		}
		
		//日期转换工具
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//当前核心日期
		String nowDateStr = lostPubBean.getAllPubSvrDate();
		try {
			Date nowDate = sdf.parse(nowDateStr);
			//判断身份证有效期是否到期（若为长期则有效）
//			if (!lostPubBean.getAllPubAgentEndDate().contains("长期")) {
//				//如果身份证有效期不是长期时转换成日期
//				Date endDate = sdf.parse(bean.getEndDate().replace(".", "/"));
//				if(endDate.before(nowDate)){
//					prossDialog.disposeDialog();
//					logger.info("身份证有已过有效期，当前核心日期："+nowDateStr+"；身份证有效期为："+bean.getEndDate());
//					try {
//						closeLed("1");
//					} catch (Exception e1) {
//						logger.error("LED灯关闭失败" + e1);
//					}
//					openConfirmDialog("抱歉，您的身份证已过有效期，是否继续操作。是：重新插入身份证；否：退出。");
//					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
//						@Override
//						public void mouseReleased(MouseEvent e) {
//							logger.info("选择是重新插入代理人身份证");
//							confirmDialog.disposeDialog();
//							lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL");
//							allPubTransFlow.transFlow();
//						}
//					});
//					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
//						@Override
//						public void mouseReleased(MouseEvent e) {
//							logger.info("选择否退出");
//							confirmDialog.disposeDialog();
//							lostPubBean.setNextStepName("BACK_HOME");
//							allPubTransFlow.transFlow();
//						}
//					});
//					return;
//				}
//			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			prossDialog.disposeDialog();
			logger.error("校验身份证有效期失败："+e1);
			try {
				closeLed("1");
			} catch (Exception e2) {
				logger.error("LED灯关闭失败" + e2);
			}
			//上送流水信息
			lostPubBean.getReqMCM001().setReqBefor("");
			lostPubBean.getReqMCM001().setIntereturnmsg("身份证超过有效期");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "抱歉，校验身份证有效期失败，请联系大堂经理！", "", "");
			return;
		}
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07670");
			//将代理人的身份证号和名字赋予查询的公共字段
			Map reCustInfo=InterfaceSendMsg.inter07670(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"), (String)reCustInfo.get("errMsg"));
			if(!"000000".equals(reCustInfo.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)reCustInfo.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				
				this.serverStop(lostPubBean.getThisComponent(),"身份联网核查失败",(String)reCustInfo.get("errMsg"),"");
				return;
			}
			lostPubBean.setAllPubAgentPhotoPath((String)reCustInfo.get("filePath"));
			lostPubBean.setAllPubAgentInspect((String)reCustInfo.get("CORE_RET_MSG"));
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用07670代理人身份联网核查失败:"+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口07670异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"代理人身份联网核查失败","","调用07670接口异常!");
			return;
		}
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08021");
			Map result08021 = InterfaceSendMsg.inter08021(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08021.get("resCode"), (String)result08021.get("errMsg"));
			if(!"000000".equals(result08021.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)result08021.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"校验代理人是否为本行员工失败",(String)result08021.get("errMsg"),"");
				return;
			}
			
			//校验结果
			String result = (String)result08021.get("checkResult");
			//如果代理人为本行员工
			if("0".equals(result)){
				prossDialog.disposeDialog();
				openConfirmDialog("本行在职员工不能办理代理人业务,是否继续。是：重新插入身份证，否：重新选择是否代理。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						confirmDialog.disposeDialog();
						lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL");
						allPubTransFlow.transFlow();
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						confirmDialog.disposeDialog();
						lostPubBean.setNextStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
						allPubTransFlow.transFlow();
					}
				});
				return;
			}
			
			
		} catch (Exception e2) {
			logger.error("调用查询代理人是否为本行员工接口异常：08021"+e2);
			prossDialog.disposeDialog();
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口08021异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"代理人是否为本行员工查询失败","","调用08021接口异常!");
			return;
		}
		
		//查询代理人电话信息
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("04422");
			Map result04422 = InterfaceSendMsg.inter04422(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result04422.get("resCode"), (String)result04422.get("errMsg"));
			if(!"000000".equals(result04422.get("resCode")) && !((String)result04422.get("errMsg")).contains("客户不存在")){
				prossDialog.disposeDialog();
				logger.error((String)result04422.get("errMsg"));
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				this.serverStop(lostPubBean.getThisComponent(),"客户基本信息查询失败",(String)result04422.get("errMsg"),"");
				return;
			}
			String mphone = (String)result04422.get("Mphone");
			String phone = (String)result04422.get("phone");
			if(mphone!=null && !"".equals(mphone)){
				lostPubBean.setAllPubAgentPhone(mphone);
			}else if(phone!=null && !"".equals(phone)){
				lostPubBean.setAllPubAgentPhone(phone);
			}
			String address = (String)result04422.get("address");
			if(address!=null && !"".equals(address)){
				lostPubBean.setAllPubAgentAddress(address);
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用04422客户基本信息查询失败失败:"+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用接口04422异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"客户基本信息查询失败","","调用04422接口异常!");
			return;
		}

		
		//校验代理人年龄是否年满16周岁
		String birthday = bean.getBirthday().replace("-", "/");
		try {
			boolean result = DateTool.checkYearsIsOk(birthday, nowDateStr, 16);
			if(!result){
				prossDialog.disposeDialog();
				logger.error("代理人未满十六周岁，不允许本人办理.");
				try {
					closeLed("1");
				} catch (Exception e1) {
					logger.error("LED灯关闭失败" + e1);
				}
				openConfirmDialog("代理人未满十六周岁，不允许代理，是否继续操作。是：重新插入身份证；否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("选择是重新插入代理人身份证");
						confirmDialog.disposeDialog();
						lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL");
						allPubTransFlow.transFlow();
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("选择否退出");
						confirmDialog.disposeDialog();
						lostPubBean.setNextStepName("BACK_HOME");
						allPubTransFlow.transFlow();
					}
				});
				return;
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			e.printStackTrace();
			logger.error("校验代理人是否年满16周岁异常："+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//上送流水信息
			lostPubBean.getReqMCM001().setReqBefor("");
			lostPubBean.getReqMCM001().setIntereturnmsg("校验代理人是否年满十六周岁失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"校验代理人是否年满16周岁异常：","","");
			return;
		}
		
		//进行黑灰名单查询
		//0-正常 1-涉案名单 2-可疑名单
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("01597");
			Map map01597 = InterfaceSendMsg.inter01597(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map01597.get("resCode"), (String)map01597.get("errMsg"));
			String resCode = (String)map01597.get("resCode");
			if(!"000000".equals(resCode)){
				if("0010".equals(resCode)){
					prossDialog.disposeDialog();
					logger.info("代理人身份为涉案身份："+resCode);
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(),"抱歉，代理人身份涉案，禁止办理业务。",(String)map01597.get("errMsg"),"");
					return;
				}else if("0020".equals(resCode)){
					prossDialog.disposeDialog();
					logger.info("代理人身份为可疑身份："+resCode);
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(),"抱歉，代理人身份可疑，禁止办理业务。",(String)map01597.get("errMsg"),"");
					return;
				}else{
					prossDialog.disposeDialog();
					logger.error((String)map01597.get("errMsg"));
					try {
						closeLed("1");
					} catch (Exception e1) {
						logger.error("LED灯关闭失败" + e1);
					}
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理",(String)map01597.get("errMsg"),"");
					return;
				}
			}else{
				logger.info("代理人身份正常："+resCode);
				lostPubBean.setAllPubSttn("0");
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597黑灰名单查询接口失败："+e);
			try {
				closeLed("1");
			} catch (Exception e1) {
				logger.error("LED灯关闭失败" + e1);
			}
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理","","调用黑灰名单查询接口失败");
			return;
		}
		
		prossDialog.disposeDialog();
		lostPubBean.setUpStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
		lostPubBean.setNextStepName("LOST_INPUT_PHONE_AND_ADRESS");
		try {
			closeLed("1");
		} catch (Exception e1) {
			logger.error("LED灯关闭失败" + e1);
		}
		openPanel(lostPubBean.getThisComponent(),new AllPublicOutputIdCardPanel());
	}
	
	/**
	 * 查询客户号
	 */
	public boolean checkCust(){
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07519");
			Map inter07519 = InterfaceSendMsg.inter07519(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07519.get("resCode"), (String)inter07519.get("errMsg"));
			if(!"000000".equals(inter07519.get("resCode"))){
				
				   logger.info("调用个人客户查询( 01020)-前置07519接口失败："+inter07519.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop(lostPubBean.getThisComponent(), "查询客户信息失败，请联系大堂经理", (String) inter07519.get("errMsg"), "");
				   return false;
			}
		
		} catch (Exception e) {
			
			logger.error("调用个人客户查询( 01020)-前置07519接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07519接口异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询客户信息失败，请联系大堂经理", "", "调用07519接口失败");
			return false;
		}
		return true;		 
	}
	
	/**
	 * 查询身份下所有客户号
	 */
	public Map checkAllCust(){
		Map check07519 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07519");
			check07519 = InterfaceSendMsg.check07519(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)check07519.get("resCode"), (String)check07519.get("errMsg"));
			if(!"000000".equals(check07519.get("resCode"))){
				
				   logger.info("调用个人客户查询( 01020)-前置07519接口失败："+check07519.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop(lostPubBean.getThisComponent(), "查询客户信息失败，请联系大堂经理", (String) check07519.get("errMsg"), "");
				   return check07519;
			}
		
		} catch (Exception e) {
			
			logger.error("调用个人客户查询( 01020)-前置07519接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07519接口异常");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询客户信息失败，请联系大堂经理", "", "调用07519接口失败");
			check07519.put("resCode", "999999");
			return check07519;
		}
		return check07519;		 
	}
	
	/**
	 * 查询所有账户信息
	 */
	public Map checkAllAcc(){
		
		Map inter07527 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07527");
			inter07527 = InterfaceSendMsg.inter07527(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07527.get("resCode"), (String)inter07527.get("errMsg"));
			if(!"000000".equals(inter07527.get("resCode"))){
				
				   logger.info("调用按客户查询账户信息【20103】-前置07527接口失败："+inter07527.get("errMsg"));
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop(lostPubBean.getThisComponent(), "查询客户所有账户信息失败，请联系大堂经理", (String) inter07527.get("errMsg"), "");
				   return inter07527;
			}
		
		} catch (Exception e) {
			
			logger.error("调用按客户查询账户信息【20103】-前置07527接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07527接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询客户所有账户信息失败，请联系大堂经理", "", "调用07527接口失败");
			inter07527.put("resCode","4444");
			return inter07527;
		}
		return inter07527;	
	}
	
	/**
	 * 查询开卡明细
	 */
	public boolean checkAllCardNo(){
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07524");
			Map inter07524 = InterfaceSendMsg.inter07524(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07524.get("resCode"), (String)inter07524.get("errMsg"));
			if(!"000000".equals(inter07524.get("resCode"))){
				
				   logger.info("调用开卡明细联动查询【70028】-07524接口失败："+inter07524.get("errMsg"));
				   closeDialog(prossDialog);
				   //上送流水信息
				   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				   serverStop(lostPubBean.getThisComponent(), "查询客户所有银行卡失败，请联系大堂经理", (String) inter07524.get("errMsg"), "");
				   return false;
			}
		
		} catch (Exception e) {
			
			logger.error("调用开卡明细联动查询【70028】-07524接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07524接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询客户所有银行卡失败，请联系大堂经理", "", "调用07524接口失败");
			return false;
		}
		return true;	
		
	}
	
	/**
	 * 查询卡信息
	 */
	public boolean checkCardMsg(){
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = InterfaceSendMsg.inter03845(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if(!"000000".equals(inter03845.get("resCode"))){
				
				logger.info("调用卡信息查询【75010】--03845接口失败："+inter03845.get("errMsg"));
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) inter03845.get("errMsg"), "");
				return false;
			}
		
		} catch (Exception e) {
			
			logger.error("调用卡信息查询【75010】--03845接口异常："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03845接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03845接口失败");
			return false;
		}
		return true;	
		
	}
	
	/**
	 * 查询卡下子账户信息
	 */
	public Map checkCardSubAcc(){
		
		Map inter03519 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03519");
			inter03519 = InterfaceSendMsg.inter03519(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03519.get("resCode"), (String)inter03519.get("errMsg"));
			if(!"000000".equals(inter03519.get("resCode"))){
				
				logger.info("调用子账户列表查询-【75109】前置03519接口失败："+inter03519.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", (String) inter03519.get("errMsg"), "");
				return inter03519;
			}
		
		} catch (Exception e) {
			
			logger.error("调用子账户列表查询-【75109】前置03519接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03519接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", "", "调用03519接口失败");
			inter03519.put("resCode","4444");
			return inter03519;
		}
		return inter03519;	
		
	}
	
	/**
	 * 查询电子子账户信息
	 */
	public Map checkECardSubAcc(){
		
		Map inter07819 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07819");
			inter07819 = InterfaceSendMsg.inter07819(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07819.get("resCode"), (String)inter07819.get("errMsg"));
			if(!"000000".equals(inter07819.get("resCode"))){
				String msg = (String)inter07819.get("errMsg");
				if(msg.startsWith("0007")){
					logger.info("无电子账户客户");
					inter07819.put("resCode","000000");
					return inter07819;
				}else{
					logger.info("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口失败："+inter07819.get("errMsg"));
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "查询电子子账户信息失败，请联系大堂经理", (String) inter07819.get("errMsg"), "");
					return inter07819;
				}
			}
		
		} catch (Exception e) {
			
			logger.error("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07819接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询电子子账户信息失败，请联系大堂经理", "", "调用07819接口失败");
			inter07819.put("resCode","4444");
			return inter07819;
		}
		return inter07819;
	}
	
	/**
	 * 账户信息查询
	 */
	public Map checkAcc(){
		
		Map check07601 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07601");
			check07601 = InterfaceSendMsg.check07601(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)check07601.get("resCode"), (String)check07601.get("errMsg"));
			if(!"000000".equals(check07601.get("resCode"))){
				
				logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+check07601.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询账户信息失败，请联系大堂经理", (String) check07601.get("errMsg"), "");
				return check07601;
			}
		
		} catch (Exception e) {
			
			logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询账户信息失败，请联系大堂经理", "", "调用07601接口失败");
			check07601.put("resCode","4444");
			return check07601;
		}
		return check07601;
		
	}
	
	
	/**
	 * 拍照人脸识别
	 */
	public void camera(){
		openProssDialog();
		//判断是否有代理人
		if ("1".equals(lostPubBean.getAllPubIsDeputy())) {
			try {
				logger.info("进行代理人人脸识别");
				String camera_path=null;
				if("1".equals(lostPubBean.getAllPubReCamera())){
					camera_path=Property.getProperties().getProperty("re_camera_path");
				}else{
					camera_path=Property.getProperties().getProperty("camera_path");
				}
				Map map =CompareFaces.compares(Property.getProperties().getProperty("bill_id_agent_face"),camera_path);
				String resultCode = (String) map.get("exCode");
				if("000000".equals(resultCode)){
					String sims = (String) map.get("sims");
					lostPubBean.setAllPubSims(sims);
					if("1".equals(lostPubBean.getAllPubReCamera())){
						FileUtil.copy(new File(Property.RE_CAMERA_PATH), new File(Property.CAMERA_PATH));
						lostPubBean.setAllPubReCamera("");
					}
					closeDialog(prossDialog);
					//如果是授权柜员进行授权审核时
					if("3".equals(lostPubBean.getAllPubIsCheckAuthor())){
						logger.info("进入联网核查页面");
						lostPubBean.setNextStepName("ALL_PUBLIC_CHECK_AGENT_PHOTOS_PANLE");
					}else{
						logger.info("进入选择是否记得密码页面");
						lostPubBean.setNextStepName("LOST_PASS_SELECT_PAGE");
					}
					allPubTransFlow.transFlow();
					return;
				}else{
					logger.info("进入人脸识别失败页面");
					closeDialog(prossDialog);
					lostPubBean.setAllPubCameraResultMsg((String)map.get("exMsg"));
					openPanel(lostPubBean.getThisComponent(), new AllPublicAccFaceCheckFail());
					return;
				}
			} catch (Exception e) {
				logger.error("人脸识别接口调用失败" + e);
				closeDialog(prossDialog);
				//上送流水信息
				lostPubBean.getReqMCM001().setReqBefor("");
				lostPubBean.getReqMCM001().setIntereturnmsg("人脸识别失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "人脸识别失败","", "人脸识别时异常:"+e);
				return;
			}
		} else {
			try {
				logger.info("进行本人人脸识别");
				String camera_path=null;
				if("1".equals(lostPubBean.getAllPubReCamera())){
					camera_path=Property.getProperties().getProperty("re_camera_path");
				}else{
					camera_path=Property.getProperties().getProperty("camera_path");
				}
				Map map = CompareFaces.compares(Property.getProperties().getProperty("bill_id_self_face"),camera_path);
				String resultCode = (String) map.get("exCode");
				if("000000".equals(resultCode)){
					String sims = (String) map.get("sims");
					lostPubBean.setAllPubSims(sims);
					if("1".equals(lostPubBean.getAllPubReCamera())){
						FileUtil.copy(new File(Property.RE_CAMERA_PATH), new File(Property.CAMERA_PATH));
						lostPubBean.setAllPubReCamera("");
					}
					closeDialog(prossDialog);
					if("3".equals(lostPubBean.getAllPubIsCheckAuthor())){
						logger.info("进入联网核查页面");
						lostPubBean.setNextStepName("ALL_PUBLIC_CHECK_PHOTOS_PANLE");
					}else{
						if(lostPubBean.getLOrS()!=null && "0".equals(lostPubBean.getLOrS())){
							logger.info("进入选择是否记得密码页面");
							lostPubBean.setNextStepName("LOST_PASS_SELECT_PAGE");
						}else{
							if("4".equals(lostPubBean.getLostOrSolve())||"3".equals(lostPubBean.getLostOrSolve())){
								logger.info("进入解挂补领和销户页面");
								lostPubBean.setNextStepName("SOLVE_LOST_TYPE_SELECT_PAGE");
							}else{
								logger.info("进入录入申请书号页面");
								lostPubBean.setNextStepName("SOLVE_LOST_INPUT_APPLY_NO_PAGE");
							}
						}
					}
					allPubTransFlow.transFlow();
					return;
				}else{
					logger.info("进入人脸识别失败页面");
					closeDialog(prossDialog);
					lostPubBean.setAllPubCameraResultMsg((String)map.get("exMsg"));
					openPanel(lostPubBean.getThisComponent(), new AllPublicAccFaceCheckFail());
					return;
				}
			} catch (Exception e) {
				logger.error("人脸识别接口调用失败" + e);
				closeDialog(prossDialog);
				//上送流水信息
				lostPubBean.getReqMCM001().setReqBefor("");
				lostPubBean.getReqMCM001().setIntereturnmsg("人脸识别失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "人脸识别失败","", "人脸识别时异常:"+e);
				return;
			}
		}
	}
	
	
	/**
	 * @Description: 查询授权柜员的授权方式
	 * @Author: hk
	 * @date 2018年3月23日 上午10:46:05
	 */
	public void checkAuthorMod(){
		logger.info("查询柜员授权方式");
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07659");
			Map params = InterfaceSendMsg.inter07659(lostPubBean);
			//上送流水调用接口后信息
			String resCode = (String) params.get("resCode");
			String errMsg = (String) params.get("errMsg");
			lostPubBean.getReqMCM001().setReqAfter(resCode, errMsg);
			if (!"000000".equals(resCode)) {
				logger.info("柜员查询异常：" + errMsg);
				lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_NO_PANEL");
				allPubTransFlow.transFlow();
				openMistakeDialog(errMsg);
				return;
			}
			//授权柜员信息
			lostPubBean.getReqMCM001().setAuthemp1(lostPubBean.getAllPubFristSupTellerNo());;
			
			if ("1".equals(lostPubBean.getAllPubFristCheckMod())) {// 1 口令2 指纹
				logger.info("进入口令授权"+lostPubBean.getAllPubFristCheckMod());
				lostPubBean.getReqMCM001().setAuthmethod1("1");
				lostPubBean.setUpStepName("ALL_PUBLIC_AUTHOR_NO_PANEL");
				lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_PASS_PANEL");
			} else {
				logger.info("进入指纹授权"+lostPubBean.getAllPubFristCheckMod());
				lostPubBean.getReqMCM001().setAuthmethod1("2");
				lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_FINGER_PRINT_PANEL");
			}
			allPubTransFlow.transFlow();
			return;
		} catch (Exception e) {
			logger.error("柜员查询异常：" + e);
			//调用接口异常，上送流水
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07659接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询授权方式异常，请联系大堂经理。","", "");
			return;
		}
	}
	
	
	/**
	 * 授权
	 */
	public void authorIng(){
		logger.info("授权");
		try {
			logger.info("调用07660方法");
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07660");
			Map params=InterfaceSendMsg.inter07660(lostPubBean);
			String resCode=(String)params.get("resCode");
			String errMsg=(String)params.get("errMsg");
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter(resCode, errMsg);
			if(!"000000".equals(resCode)){
				logger.info("柜员授权失败："+resCode+"/"+errMsg);
				openConfirmDialog(errMsg+"是否重新录入？是：请重新录入。否：请录入新的柜员号。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//清空倒计时
						closeDialog(confirmDialog);
						if("1".equals(lostPubBean.getAllPubFristCheckMod())){
							lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_PASS_PANEL");
						}else if("2".equals(lostPubBean.getAllPubFristCheckMod())){
							lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_FINGER_PRINT_PANEL");
						}
						allPubTransFlow.transFlow();
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//清空倒计时
						closeDialog(confirmDialog);
						lostPubBean.setNextStepName("ALL_PUBLIC_AUTHOR_NO_PANEL");
						allPubTransFlow.transFlow();
					}
				});
				return;
			}
			openPanel(lostPubBean.getThisComponent(),new LostProcessingPage());
			
		} catch (Exception e) {
			logger.error("柜员授权异常："+e);
			//调用接口异常，上送流水
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07660接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "授权异常，请联系大堂经理。","", "");
			return;
		}
		
	}
	
	/**
	 * 校验密码
	 */
	public void checkAccPwd(){
		openProssDialog();
		
		//校验账户密码
		//若是解挂并且挂失方式为双挂或者为银行卡单挂但未激活的卡，则校验重置的密码和输入的密码是否相同，若不相同这提示重新输入。
		logger.info("解挂类型："+lostPubBean.getLostOrSolve()+",挂失方式："+lostPubBean.getSolveAccState()+",银行卡是否激活："+lostPubBean.getCardState());
		if(("3".equals(lostPubBean.getLostOrSolve())||"4".equals(lostPubBean.getLostOrSolve()))
				&&(((lostPubBean.getSolveAccState()!=null && !"".equals(lostPubBean.getSolveAccState()))
				&&("1".equals(lostPubBean.getSolveAccState())||"3".equals(lostPubBean.getSolveAccState())||"5".equals(lostPubBean.getSolveAccState())))
				||(lostPubBean.getCardState()!=null && "N".equals(lostPubBean.getCardState())))){
			if(lostPubBean.getAllPubAccPwd()!=null && !"".equals(lostPubBean.getAllPubAccPwd().trim())
					&& lostPubBean.getReMakePwd()!=null && !"".equals(lostPubBean.getReMakePwd().trim())
					&&lostPubBean.getReMakePwd().equals(lostPubBean.getAllPubAccPwd())){
				logger.info("重置的密码和校验的密码相同");
			}else{
				openConfirmDialog("输入的密码错误，是否重新输入？是：重新输入密码；否：退出；");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						logger.info("选择是：重新输入密码");
						//重新输入密码
						lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL");
						allPubTransFlow.transFlow();
						return;
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						logger.info("选择退出：退出");
						//退出
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						return;
					}
				});
				return;
			}
		//除双挂解挂外的其他输入密码的情况下输入密码校验密码是否正确
		}else{
			try {
				LostPubBean bean = new LostPubBean();
				bean.setIsPinPass("1");
				bean.setAllPubAccPwd(lostPubBean.getAllPubAccPwd());
				bean.setAllPubPassAccNo(lostPubBean.getAllPubPassAccNo());
				Map result07601=null;
				if("0".equals(lostPubBean.getLostType()) ||"0".equals(lostPubBean.getSolveType()) ){
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("03845");
					result07601 = InterfaceSendMsg.inter03845(bean);
				}else{
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("07601");
					result07601 = InterfaceSendMsg.inter07601(bean);
				}
				//上送流水调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)result07601.get("resCode"),(String)result07601.get("errMsg") );
				 
				if(!"000000".equals(result07601.get("resCode"))){//校验密码失败
					
					if (((String) result07601.get("errMsg")).startsWith("A102")) {
						
						closeDialog(prossDialog);
						logger.info(((String) result07601.get("errMsg")));
						//选择重新输入密码或者退出
						openConfirmDialog("输入密码错误,是否重新输入密码；是：重新输入密码。否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								logger.info("选择是：重新输入密码");
								//重新输入密码
								lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL");
								allPubTransFlow.transFlow();
								return;
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								logger.info("选择退出：退出");
								//退出
								serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								return;
							}
						});
						return;
					} else if (((String) result07601.get("errMsg")).startsWith("A103") ||((String) result07601.get("errMsg")).startsWith("P039") ) {
						closeDialog(prossDialog);
						//如果是解挂补发、销户、撤销，密码锁死后直接退出
						if("3".equals(lostPubBean.getLostOrSolve()) ||"4".equals(lostPubBean.getLostOrSolve())||"5".equals(lostPubBean.getLostOrSolve())){
							logger.info("抱歉，你输入的密码次数已达上限。");
							//上送流水信息
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop(lostPubBean.getThisComponent(), "抱歉，你输入的密码失败次数已达输入上限，密码已锁死。", "", "");
							return;
							//其他业务时直接终止业务办理
						}else if("0".equals(lostPubBean.getAllPubIsDeputy())){
							logger.info(result07601.get("errMsg"));
							openConfirmDialog("您的密码已锁定，是否重新选择账户？是：重新选择；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择是：重新选择");				
									openPanel(lostPubBean.getThisComponent(), new LostShowAccNoPage());
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择否：退出");
									//退出
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}else if("1".equals(lostPubBean.getAllPubIsDeputy())){
							logger.info(result07601.get("errMsg"));
							openConfirmDialog("您的密码已锁定，是否重新输入账号？是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择是：重新输入");		
									baseTransBean.setAllPubAccPwd("");
									openPanel(lostPubBean.getThisComponent(), new LostEnterAccNoMsgPage());
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择否：退出");
									//退出
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}
						
					}else{
						closeDialog(prossDialog);
						logger.info(result07601.get("errMsg"));
						//上送流水信息
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "密码校验失败，请联系大堂经理", (String)result07601.get("errMsg"),"");
						return;
					}
				}else{
					if(bean.getAccState()!=null && !"".equals(bean.getAccState().trim()) 
							&&((("0".equals(lostPubBean.getLostType()) ||"0".equals(lostPubBean.getSolveType()))&& "2".equals(bean.getAccState().substring(11,12)))
									||(!"0".equals(lostPubBean.getLostType()) &&!"0".equals(lostPubBean.getSolveType())&&bean.getAccState().trim().endsWith("2")))){
						logger.info("该账户为密码挂失状态："+bean.getAccState());
						//如果是解挂补发、销户、撤销，密码锁死后直接退出
						if("3".equals(lostPubBean.getLostOrSolve()) ||"4".equals(lostPubBean.getLostOrSolve())||"5".equals(lostPubBean.getLostOrSolve())){
							openConfirmDialog("您的账户为密码挂失状态,是否重新输入挂失申请书号？是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择是：重新输入");		
									baseTransBean.setAllPubAccPwd("");
									openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择否：退出");
									//退出
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
							//其他业务时直接终止业务办理
						}else if("0".equals(lostPubBean.getAllPubIsDeputy())){
							openConfirmDialog("您的账户为密码挂失状态，是否重新选择挂失账？是：重新选择；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择是：重新选择");				
									openPanel(lostPubBean.getThisComponent(), new LostShowAccNoPage());
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择否：退出");
									//退出
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}else if("1".equals(lostPubBean.getAllPubIsDeputy())){
							openConfirmDialog("您的账户为密码挂失状态，是否重新输入挂失信息？是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择是：重新输入");		
									baseTransBean.setAllPubAccPwd("");
									openPanel(lostPubBean.getThisComponent(), new LostEnterAccNoMsgPage());
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									logger.info("选择否：退出");
									//退出
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}
					}
				}
				
			} catch (Exception e) {
				closeDialog(prossDialog);
				logger.error("校验账户密码及信息状态异常："+e);
				lostPubBean.getReqMCM001().setIntereturnmsg("校验密码失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "校验账户密码及信息状态异常，请联系大堂经理。","", "");
				return;
			}
			
		}
		
		//跳转
		if("1".equals(lostPubBean.getAllPubIsDeputy())){//代理人
			closeDialog(prossDialog);
			
			//进入账户信息签字页面
			lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
			allPubTransFlow.transFlow();
			return;
			
		}else{//本人
			
			//状态异常走单挂失流程
			if(lostPubBean.getStateName()!=null && !"".equals(lostPubBean.getStateName())){
				
				closeDialog(prossDialog);
				
				//进入账户信息签字页面
				lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
				allPubTransFlow.transFlow();
				return;
				
			}else{//状态正常后，本人补领/销户选择页面
				
				if("0".equals(lostPubBean.getRecOrCan())){//补领
					
					closeDialog(prossDialog);
					
					//进入是否立即补领选择页
					lostPubBean.setNextStepName("LOST_CHECK_IS_RECEIVE_PAGE");
					allPubTransFlow.transFlow();
					return;
					
				}else if("1".equals(lostPubBean.getRecOrCan())){//销户
					
					//校验账户是否可挂失销户
					selectCancel();
					return;
					
				}
			}
			
			if("3".equals(lostPubBean.getLostOrSolve())){//解挂补发
				closeDialog(prossDialog);
				//进入账户信息签字页面
				openPanel(lostPubBean.getThisComponent(), new SolveLostApplNoInfosConfirmPage());
				
			}else if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
				
				if("0".equals(lostPubBean.getSolveLostType()) || "2".equals(lostPubBean.getSolveLostType()) || "1".equals(lostPubBean.getSolveLostType())){//银行卡、存折/个人普通存单
					
					//查询解挂销户转入的银行卡信息
					checkAllCards();
					return;
					
				}else if("1_1".equals(lostPubBean.getSolveLostType()) || "1_2".equals(lostPubBean.getSolveLostType())){//存单卡下子账号、存单电子子账号
					
					closeDialog(prossDialog);
					//进入自动带出卡/电子账号显示页
					String[] split = lostPubBean.getAllPubAccNo().split("-");
					lostPubBean.setSelectCardNo(split[0]);
					openPanel(lostPubBean.getThisComponent(), new SolveLostGetCardOrEcardPage());
					return;
				}
			}else if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销
				closeDialog(prossDialog);
				openPanel(lostPubBean.getThisComponent(),new SolveLostApplNoInfosConfirmPage());
				return;
			}
			
		}
	}
	
	/**
	 * 输入密码正确，校验账户是否可挂失销户
	 */
	public void selectCancel(){
		
		final ShowAccNoMsgBean accMsg = (ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg");
		
		if("0".equals(accMsg.getCardOrAccOrAcc1())){//银行卡
			
			//查询银行卡是否有到期的在途理财
			if(!checkMoneyPro()){
				return;
			}
			//查询银行卡是否有关联的未还清贷款
			if(!checkMoneyHaveLoan()){
				return;
			}
			//查询银行卡是否有基金
			checkFundMoney();
				
			//查询银行卡是否有未销户的子账户
			LostPubBean bean = new LostPubBean();
			bean.setCardNo(lostPubBean.getAllPubAccNo());
			Map checkSubAcc = checkSubCardAcc(bean);
			if(!"000000".equals(checkSubAcc.get("resCode"))){
				return;
			}
			
			StringBuffer errmsg = new StringBuffer("");
			if("N".equals(lostPubBean.getChkFlag())){
				
				//仅挂失，无法销户
				logger.info("该银行卡有理财");
				errmsg.append("理财账户未解约，");
			}
			if("1".equals(lostPubBean.getAmtFlag())){
				
				//仅挂失，无法销户
				logger.info("该银行卡有关联的未还清贷款");
				errmsg.append("有未还清贷款，");
			}
			if(lostPubBean.getFundFlag()!=null && "F".equals(lostPubBean.getFundFlag())){
				
				//仅挂失，无法销户
				logger.info("该银行卡有基金");
				errmsg.append("有基金，");
				
			}
			List<SubAccInfoBean> subList = (List<SubAccInfoBean>)checkSubAcc.get("cardSub");
			if(subList.size()>0){
				for (SubAccInfoBean subAccInfoBean : subList) {
					if(!"Q".equals(subAccInfoBean.getMark()) && !"*".equals(subAccInfoBean.getMark())){
						
						//仅挂失，无法销户
						logger.info("该银行卡有未销户子账户，不允许销户："+subAccInfoBean.getSubAccNo());
						errmsg.append("有未销户子账户，");
						break;
					}
				}
			}
			
			if(!"".equals(errmsg.toString())){
				
				closeDialog(prossDialog);
				openConfirmDialog("该银行卡"+errmsg+"仅支持挂失，是否继续：是：仅对账户进行挂失。否：请重新选择。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						lostPubBean.setLostOrSolve("0");
						lostPubBean.getReqMCM001().setTranscode("11039");
						
						//进入账户信息签字页面(只挂失)
						lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
						allPubTransFlow.transFlow();
					}
					
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						//重新选择
						openPanel(lostPubBean.getThisComponent(), new LostShowAccNoPage());
					}
					
				});
				return;
			}
		}
		
		if("0".equals(lostPubBean.getLostOrSolve())){
			
			closeDialog(prossDialog);
			//进入账户信息签字页面(只挂失)
			lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
			allPubTransFlow.transFlow();
			
		}else{
			
			if("1".equals(lostPubBean.getTiZhiQu())){//选择未到期继续挂失销户
				
				ShowAccNoMsgBean acc = (ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg");
				if("1".equals(acc.getCardOrAccOrAcc1()) || "2".equals(acc.getCardOrAccOrAcc1())){//个人存单/存折
					
		    		//查询转入的银行卡
		    		lostAction.checkAllCards();
					
				}else if("1_1".equals(acc.getCardOrAccOrAcc1()) || "1_2".equals(acc.getCardOrAccOrAcc1())){
					
					closeDialog(prossDialog);
					// 进入自动带出卡/电子账号显示页
					lostPubBean.setLostOrSolve("2");
					openPanel(lostPubBean.getThisComponent(),new LostGetCardOrEcardPage());

				}
				
			}else{//到期
				
				closeDialog(prossDialog);
				//进入是否立即销户选择页
				lostPubBean.setNextStepName("LOST_CHECK_IS_CANCEL_PAGE");
				allPubTransFlow.transFlow();
			}
		}
	}
	
	/**
	 * 查询转入的银行卡
	 */
	public void checkAllCards(){
		
		lostPubBean.getBackCards().clear();
		
		//查询所有银行卡
		if(!checkAllCardNo()){//查询所有卡号
			return;
		}
		
		if(lostPubBean.getCards()!=null && lostPubBean.getCards().length > 0){
			
			logger.info("查询销户转入的卡号，筛掉已销户、非同一张卡的银行卡");
			LostPubBean bean = new LostPubBean();
			for (int i = 0; i < lostPubBean.getCards().length; i++) {
				
				bean.setCardNo(lostPubBean.getCards()[i]);
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("03845");
					Map inter03845 = InterfaceSendMsg.inter03845(bean);
					//上送流水调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
					if(!"000000".equals(inter03845.get("resCode"))){
						
						   logger.info("调用卡信息查询【75010】--03845接口失败："+inter03845.get("errMsg"));
						   closeDialog(prossDialog);
						   //上送流水信息
						   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) inter03845.get("errMsg"), "");
						   return;
					}
				
				} catch (Exception e) {
					
					logger.error("调用卡信息查询【75010】--03845接口异常："+e);
					closeDialog(prossDialog);
					//调用接口异常，上送流水
					lostPubBean.getReqMCM001().setIntereturnmsg("调用03845接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03845接口失败");
					return;
				}
				//筛掉卡信息与证件信息不一致的卡
				if(!lostPubBean.getAllPubIDNo().equals(bean.getAccIdNo()) || !lostPubBean.getAllPubIdCardName().equals(bean.getAccIdName())){
					logger.info("银行卡信息与身份证读取信息 不一致");
					continue;
				}
				//筛选非销户、非同一张挂失的卡
				if(!"Q".equals(bean.getCardState().trim()) && !lostPubBean.getCards()[i].equals(lostPubBean.getAllPubAccNo())){
					
					logger.info("转入卡号："+lostPubBean.getCards()[i]);
					lostPubBean.getBackCards().add(lostPubBean.getCards()[i]);
				}
			}
			
			if(lostPubBean.getBackCards().size()==0){
				
				if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
					
					//弹框提示阻止继续
					logger.info("无可转入的银行卡");
					closeDialog(prossDialog);
					openMistakeDialog("无可转入的银行卡");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(mistakeDialog);
							
							//退出
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
						
					});
					return;
					
				}else {//挂失销户
					
					//弹框提示是否继续
					logger.info("销户金额无可转入的银行卡");
					closeDialog(prossDialog);
					openConfirmDialog("您无其他有效银行卡，仅支持挂失，无法实现销户，是否继续：是：仅对账户进行挂失。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							lostPubBean.setLostOrSolve("0");
							lostPubBean.getReqMCM001().setTranscode("11039");
							//进入账户信息签字页面(只挂失)
							lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
							allPubTransFlow.transFlow();
						}
						
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							
							//退出
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
						
					});
					return;
				}
			}
			
		}else{
			
			if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
				
				//弹框提示阻止继续
				logger.info("无可转入的银行卡");
				closeDialog(prossDialog);
				openMistakeDialog("无可转入的银行卡");
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(mistakeDialog);
						
						//退出
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
					
				});
				return;
				
			}else {//挂失销户
				
				//弹框提示是否继续
				logger.info("销户金额无可转入的银行卡");
				closeDialog(prossDialog);
				openConfirmDialog("您无其他有效银行卡，仅支持挂失，无法实现销户，是否继续：是：仅对账户进行挂失。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						lostPubBean.setLostOrSolve("0");
						lostPubBean.getReqMCM001().setTranscode("11039");
						//进入账户信息签字页面(只挂失)
						lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
						allPubTransFlow.transFlow();
					}
					
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						
						//退出
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
					
				});
				return;
			}
		}
		if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
			
			closeDialog(prossDialog);
			openPanel(lostPubBean.getThisComponent(), new SolveLostCancelAccCardPage());// 进入选择解挂销户转入卡选择页
			
		}else{//挂失销户
			
			lostPubBean.setLostOrSolve("2");
			closeDialog(prossDialog);
			openPanel(lostPubBean.getThisComponent(), new LostCancelAccCardPage());// 进入选择挂失销户转入卡选择页
		}
	}
	
	/**
	 * 理财客户销户可销户状态查询【520104】-前置【08199】
	 */
	public boolean checkMoneyPro(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08199");
			Map check08199 = InterfaceSendMsg.check08199(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)check08199.get("resCode"), (String)check08199.get("errMsg"));
			
			if(!"000000".equals(check08199.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("理财客户销户可销户状态查询接口失败："+check08199.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "理财客户销户可销户状态查询失败，请联系大堂经理。", (String)check08199.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08199接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			logger.error("调用理财客户销户可销户状态查询【520104】-前置【08199】失败："+e);
			serverStop(lostPubBean.getThisComponent(),"理财客户销户可销户状态查询失败，请联系大堂经理。", "", "调用理财客户销户可销户状态查询接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 帐户/卡户是否关联贷款信息查询【02945】-前置【08240】
	 */
	public boolean checkMoneyHaveLoan(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08240");
			Map check08240 = InterfaceSendMsg.check08240(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)check08240.get("resCode"), (String)check08240.get("errMsg"));
			
			if(!"000000".equals(check08240.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("帐户/卡户是否关联贷款信息查询接口失败："+check08240.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				
				serverStop(lostPubBean.getThisComponent(), "帐户/卡户是否关联贷款信息查询失败，请联系大堂经理。", (String)check08240.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用 帐户/卡户是否关联贷款信息查询【02945】-前置【08240】失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08240接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"帐户/卡户是否关联贷款信息查询失败，请联系大堂经理。", "", "调用帐户/卡户是否关联贷款信息查询接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 卡户是否有基金销户查询（100304） –前置 【08160】
	 */
	public boolean checkFundMoney(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08160");
			Map check08160 = InterfaceSendMsg.inter08160(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)check08160.get("resCode"), (String)check08160.get("errMsg"));
			if(!"000000".equals(check08160.get("resCode"))){
				logger.info("调用是否有基金销户查询（100304） –前置 【08160】接口失败："+check08160.get("errMsg"));
			}
			
		} catch (Exception e) {
			logger.error("调用 是否有基金销户查询（100304） –前置 【08160】失败："+e);
		}
		return true;
	}
	
	/**
	 * 子账户列表查询-【75109】前置03519
	 */
	public Map checkSubAcc(){
		Map inter03519 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03519");
			inter03519 = InterfaceSendMsg.check03519(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03519.get("resCode"), (String)inter03519.get("errMsg"));
			if(!"000000".equals(inter03519.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info("调用子账户列表查询-【75109】前置03519接口失败："+inter03519.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", (String) inter03519.get("errMsg"), "");
				return inter03519;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用子账户列表查询-【75109】前置03519接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03519接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", "", "调用03519接口失败");
			inter03519.put("resCode","4444");
			return inter03519;
		}
		return inter03519;	
	}
	/**
	 * 子账户列表查询-【75109】前置03519
	 */
	public Map checkSubCardAcc(LostPubBean bean){
		Map inter03519 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("03519");
			inter03519 = InterfaceSendMsg.check03519(bean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03519.get("resCode"), (String)inter03519.get("errMsg"));
			if(!"000000".equals(inter03519.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info("调用子账户列表查询-【75109】前置03519接口失败："+inter03519.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", (String) inter03519.get("errMsg"), "");
				return inter03519;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用子账户列表查询-【75109】前置03519接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03519接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询卡下子账户信息失败，请联系大堂经理", "", "调用03519接口失败");
			inter03519.put("resCode","4444");
			return inter03519;
		}
		return inter03519;	
	}
	
	/**
	 * 电子账户子账户列表查询【35109】（直连电子账户）-前置07819
	 */
	public Map checkESubAcc(){
		Map inter07819= null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07819");
			inter07819 = InterfaceSendMsg.check07819(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07819.get("resCode"), (String)inter07819.get("errMsg"));
			if(!"000000".equals(inter07819.get("resCode"))){
				
				String msg = (String)inter07819.get("errMsg");
				if(msg.startsWith("0007")){
					logger.info("无电子账户客户");
					inter07819.put("resCode","000000");
					return inter07819;
				}else{
					closeDialog(prossDialog);
					logger.info("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口失败："+inter07819.get("errMsg"));
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "查询电子子账户信息失败，请联系大堂经理", (String) inter07819.get("errMsg"), "");
					return inter07819;
				}
				
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用电子账户子账户列表查询【35109】（直连电子账户）-前置07819接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用07819接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "查询电子子账户信息失败，请联系大堂经理", "", "调用07819接口失败");
			inter07819.put("resCode","4444");
			return inter07819;
		}
		return inter07819;	
	}
	
	/**
	 * 单独挂失银行卡
	 */
	public boolean lostCardProcessing(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08190");
			Map result08190 = InterfaceSendMsg.inter08190(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08190.get("resCode"), (String)result08190.get("errMsg"));
			if(!"000000".equals(result08190.get("resCode"))){
				logger.info("调用银行卡单独挂失接口失败："+result08190.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失流程已失败，请联系大堂经理。", (String)result08190.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用卡挂失接口（08190）失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08190接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"挂失流程已失败，请联系大堂经理。", "", "调用银行卡单独挂失接口异常");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 存单或者存折挂失
	 */
	public boolean lostAccProcessing(){
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08193");
			Map result08193 = InterfaceSendMsg.inter08193(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08193.get("resCode"), (String)result08193.get("errMsg"));
			if(!"000000".equals(result08193.get("resCode"))){
				logger.info("调用存单/存折单独挂失接口失败："+result08193.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失流程已失败，请联系大堂经理。", (String)result08193.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用存单/存折挂失接口（08193）失败："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08193接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"挂失流程已失败，请联系大堂经理。", "", "调用存单/存折单独挂失接口异常");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 挂失补发
	 */
	public boolean lostAndGetNew(){
		//查询凭证号
		//上送流水信息调用接口前信息
		lostPubBean.getReqMCM001().setReqBefor("BH0001");
		if(!checkCertNo(lostPubBean)){
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter("555555","查询凭证号失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			logger.info("查询凭证号失败");
			serverStop(lostPubBean.getThisComponent(), "查询凭证号失败。", "", "");
			return false;
		}
		
		try {
			Map mapResult=null;
			if("1".equals(lostPubBean.getLostOrSolve())){//挂失立即补发
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("08181");
				mapResult = InterfaceSendMsg.inter08181(lostPubBean);
			}else{//挂失七天后补发
				if(lostPubBean.getSolveAccState()!=null && "3".equals(lostPubBean.getSolveAccState().trim())){
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("08257");
					mapResult = InterfaceSendMsg.inter08257(lostPubBean);
				}else{
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("08230");
					mapResult = InterfaceSendMsg.inter08230(lostPubBean);
				}
			}
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)mapResult.get("resCode"), (String)mapResult.get("errMsg"));
			
			if(!"000000".equals(mapResult.get("resCode"))){
				logger.info("挂失补发失败："+mapResult.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失补发失败，请联系大堂经理。", (String)mapResult.get("errMsg"), "");
				return false;
			}
			List<AccLostAndReturnInfoBean> list = (List<AccLostAndReturnInfoBean>)mapResult.get("resList");
			if(list!=null && list.size()!=0){
				AccLostAndReturnInfoBean bean = (AccLostAndReturnInfoBean)list.get(0);
				lostPubBean.getAccMap().put("resAccInfo", bean);
				lostPubBean.setOpenAmtUpper(NumberZH.change(Double.parseDouble(bean.getBalance())));
			}
		} catch (Exception e) {
			logger.error("调用挂失补发接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用挂失补发接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "挂失补发失败，请联系大堂经理。", "", "调用挂失补发接口异常。");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 挂失/解挂销户
	 */
	public boolean lostCancel(){
		
		//银行卡有自动转存的
		if("0".equals(lostPubBean.getLostType()) || "0".equals(lostPubBean.getSolveLostType())){//银行卡挂失销户
			
			if("1".equals(lostPubBean.getIsAgreedDep())){
				
//				if(!"4".equals(lostPubBean.getLostOrSolve())){
//					
//					//有约定转存，先取消约定转存
//					if(!CancelAgreeDeposit()){
//						return false;
//					}
//				}
				//卡挂失销户
				if(!cardCancel()){
					return false;
				}
				
			}else{
				
				//卡挂失销户
				if(!cardCancel()){
					return false;
				}
			}
			
		}else{
			
			//存单挂失销户
			if(!accCancel()){
				return false;
			}
			
			//唐豆收回查询
			tdCheck();
			
			if(!"0.00".equals(lostPubBean.getShtdy())){//只有当accCancelBean.getShtdy()的值不为"0.00"时调唐豆收回
				//执行唐豆收回
				tdGetBack();
			}
			
		}
		return true;
	}
	
	/**
	 * 卡挂失销户70030（新增）-前置【08186】、卡挂失期满销户（7天）【70008】-前置【08222】
	 */
	public boolean cardCancel(){
		String yuZhuan = "";
		if("1".equals(lostPubBean.getIsAgreedDep().trim())){
			if(lostPubBean.getAgreeDepJrnlNo()!=null && !"".equals(lostPubBean.getAgreeDepJrnlNo().trim())){
				yuZhuan = "，已取消约定转存，约转结息"+lostPubBean.getAgreeRealInt().trim()+"元";
			}
		}
		
		if("2".equals(lostPubBean.getLostOrSolve())){
			try {
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("08186");
				Map interResult = InterfaceSendMsg.inter08186(lostPubBean);
				//上送接口调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)interResult.get("resCode"), (String)interResult.get("errMsg"));
				if(!"000000".equals(interResult.get("resCode"))){
					logger.info("卡挂失销户销户失败："+interResult.get("errMsg"));
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "挂失销户失败"+yuZhuan, (String)interResult.get("errMsg"), "");
					return false;
				}
				
			} catch (Exception e) {
				logger.error("调用卡挂失销户接口异常："+e);
				//调用接口异常，上送流水信息
				lostPubBean.getReqMCM001().setIntereturnmsg("调用08186接口失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失销户失败"+yuZhuan, "", "调用卡挂失销户接口异常。");
				return false;
			}
		}else if("4".equals(lostPubBean.getLostOrSolve())){
			if(lostPubBean.getSolveAccState()!=null && "1".equals(lostPubBean.getSolveAccState().trim())){
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("08260");
					Map inter08260 = InterfaceSendMsg.inter08260(lostPubBean);
					//上送接口调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)inter08260.get("resCode"), (String)inter08260.get("errMsg"));
					if(!"000000".equals(inter08260.get("resCode"))){
						logger.info("改密销户一体化【70036】-前置【08260】："+inter08260.get("errMsg"));
						//上送流水信息
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "解挂销户失败"+yuZhuan, (String)inter08260.get("errMsg"), "");
						return false;
					}
					
				} catch (Exception e) {
					logger.error("改密销户一体化【70036】-前置【08260】："+e);
					//调用接口异常，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用08260接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "解挂销户失败"+yuZhuan, "", "调用卡改密销户一体化【70036】-前置【08260】接口异常。");
					return false;
				}
			}else{
				try {
					Map interResult = null;
					if(lostPubBean.getCardState()!=null && "N".equals(lostPubBean.getCardState())){
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("08262");
						interResult = InterfaceSendMsg.inter08262(lostPubBean);
					}else{
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("08222");
						interResult = InterfaceSendMsg.inter08222(lostPubBean);
					}
					//上送接口调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)interResult.get("resCode"), (String)interResult.get("errMsg"));
					if(!"000000".equals(interResult.get("resCode"))){
						logger.info("卡挂失期满销户（7天）【70008】-前置【08222】失败："+interResult.get("errMsg"));
						//上送流水信息
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "解挂销户失败"+yuZhuan, (String)interResult.get("errMsg"), "");
						return false;
					}
					
				} catch (Exception e) {
					logger.error("调用卡挂失期满销户（7天）【70008】-前置【08222】接口异常："+e);
					//调用接口异常，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用解挂销户接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "解挂销户失败"+yuZhuan, "", "调用卡挂失期满销户（7天）【70008】-前置【08222】接口异常。");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * (核心)挂失销户02961（新增）-前置【08182】、一步解双挂并销户【02969】-前置【08258】
	 */
	public boolean accCancel(){
		
		if("2".equals(lostPubBean.getLostOrSolve())){
			try {
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("08182");
				Map inter08182 = InterfaceSendMsg.inter08182(lostPubBean);
				//上送接口调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)inter08182.get("resCode"), (String)inter08182.get("errMsg"));
				if(!"000000".equals(inter08182.get("resCode"))){
					logger.info("(核心)挂失销户02961（新增）-前置【08182】销户失败："+inter08182.get("errMsg"));
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "挂失销户失败，请联系大堂经理。", (String)inter08182.get("errMsg"), "");
					return false;
				}
				List<AccCancelProFileBean> list  = (List<AccCancelProFileBean>)inter08182.get("ShdAmt");
				if(list.size()>0 && list !=null){
					for(AccCancelProFileBean accshod:list){
					   if("008".equals(accshod.getProtype())){//增益豆
						   lostPubBean.setZydAmt(accshod.getGetProAllNumber());
						}
					   if("003".equals(accshod.getProtype())){//消费豆数量
						   lostPubBean.setXfdCount(accshod.getGetProSjNumber());
						}
					}
				}
			} catch (Exception e) {
				logger.error("调用(核心)挂失销户02961（新增）-前置【08182】接口异常："+e);
				//调用接口异常，上送流水信息
				lostPubBean.getReqMCM001().setIntereturnmsg("调用解挂销户接口失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失销户失败，请联系大堂经理。", "", "调用(核心)挂失销户02961（新增）-前置【08182】接口异常。");
				return false;
			}
		}else if("4".equals(lostPubBean.getLostOrSolve())){
			if(lostPubBean.getSolveAccState()!=null && ("3".equals(lostPubBean.getSolveAccState().trim())||"5".equals(lostPubBean.getSolveAccState().trim()))){
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("08258");
					Map inter08258 = InterfaceSendMsg.inter08258(lostPubBean);
					//上送接口调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)inter08258.get("resCode"), (String)inter08258.get("errMsg"));
					if(!"000000".equals(inter08258.get("resCode"))){
						logger.info("一步解双挂并销户【02969】-前置【08258】："+inter08258.get("errMsg"));
						//上送流水信息
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "解挂销户失败，请联系大堂经理。", (String)inter08258.get("errMsg"), "");
						return false;
					}
					List<AccCancelProFileBean> list  = (List<AccCancelProFileBean>)inter08258.get("ShdAmt");
					if(list.size()>0 && list !=null){
						for(AccCancelProFileBean accshod:list){
						   if("008".equals(accshod.getProtype())){//增益豆
							   lostPubBean.setZydAmt(accshod.getGetProAllNumber());
							}
						   if("003".equals(accshod.getProtype())){//消费豆数量
							   lostPubBean.setXfdCount(accshod.getGetProSjNumber());
							}
						}
					}
				} catch (Exception e) {
					logger.error("一步解双挂并销户【02969】-前置【08258】："+e);
					//调用接口异常，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用08258接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "解挂销户失败，请联系大堂经理。", "", "一步解双挂并销户【02969】-前置【08258】调用异常。");
					return false;
				}
			}else{
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("08231");
					Map inter08231 = InterfaceSendMsg.inter08231(lostPubBean);
					//上送接口调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)inter08231.get("resCode"), (String)inter08231.get("errMsg"));
					if(!"000000".equals(inter08231.get("resCode"))){
						logger.info("挂失期满销户（7天）【02833】-前置【08231】："+inter08231.get("errMsg"));
						//上送流水信息
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop(lostPubBean.getThisComponent(), "解挂销户失败，请联系大堂经理。", (String)inter08231.get("errMsg"), "");
						return false;
					}
					List<AccCancelProFileBean> list  = (List<AccCancelProFileBean>)inter08231.get("ShdAmt");
					if(list.size()>0 && list !=null){
						for(AccCancelProFileBean accshod:list){
						   if("008".equals(accshod.getProtype())){//增益豆
							   lostPubBean.setZydAmt(accshod.getGetProAllNumber());
							}
						   if("003".equals(accshod.getProtype())){//消费豆数量
							   lostPubBean.setXfdCount(accshod.getGetProSjNumber());
							}
						}
					}
				} catch (Exception e) {
					logger.error("挂失期满销户（7天）【02833】-前置【08231】："+e);
					//调用接口异常，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用08258接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "解挂销户失败，请联系大堂经理。", "", "挂失期满销户（7天）【02833】-前置【08231]接口调用异常。");
					return false;
				}
			}
		}
		return true;
		
	}
	
	/**
	 * 唐豆收回查询
	 */
	public void tdCheck(){
		logger.info("调用唐豆收回查询");
		try {
			Map inter07622 = InterfaceSendMsg.inter07622(lostPubBean); 
			if(!"000000".equals(inter07622.get("resCode"))){
				
				logger.info(inter07622.get("errMsg"));
				lostPubBean.setTangCode((String)inter07622.get("resCode"));
				lostPubBean.setTangErrMsg((String)inter07622.get("errMsg"));
				lostPubBean.setShtdy("0.00");//不调唐豆收回
			}
			
		} catch (Exception e) {
			
			logger.error("调用07622唐豆收回查询失败"+e);
			lostPubBean.setTangCode("999999");
			lostPubBean.setTangErrMsg("唐豆收回查询失败");
			lostPubBean.setShtdy("0.00");//不调唐豆收回
		}
	}
	
	/**
	 * 唐豆收回
	 */
	public void tdGetBack(){
		logger.info("调用唐豆收回");
		try {
			Map inter07665 = InterfaceSendMsg.inter07665(lostPubBean); 
			if(!"000000".equals(inter07665.get("resCode"))){
				
				logger.info(inter07665.get("errMsg"));
				lostPubBean.setTangCode((String)inter07665.get("resCode"));
				lostPubBean.setTangErrMsg((String)inter07665.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07665唐豆收回失败"+e);
			lostPubBean.setTangCode("999999");
			lostPubBean.setTangErrMsg("唐豆收回失败");
		}
	}
	
	/**
	 * 卡权限管理【75008】前置03527
	 */
	public boolean CancelAgreeDeposit(){
		
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08258");
			Map inter03527 = InterfaceSendMsg.inter03527(lostPubBean);
			//上送接口调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter03527.get("resCode"), (String)inter03527.get("errMsg"));
			if(!"000000".equals(inter03527.get("resCode"))){
				logger.info("卡权限管理【75008】前置03527失败："+inter03527.get("errMsg"));
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "取消约定转存失败，请联系大堂经理。", (String)inter03527.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用卡权限管理【75008】前置03527接口异常："+e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用03527接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(), "取消约定转存失败，请联系大堂经理。", "", "调用卡权限管理【75008】前置03527接口异常。");
			return false;
		}
		return true;
		
	}
	
	/**
	 * 
	 * @Description:撤销挂失申请书信息查询 
	 * @Author: hk
	 * @date 2018年4月17日 上午10:23:27
	 */
	public String solveReBackApplNoInfos(){
		boolean resultCheck = false;
		String resultMsg = "";
		//先查询卡挂失申请书号信息
		Map resultCard = checkApplyNoCard();
		if("000000".equals(resultCard.get("resCode"))){
			String mark = lostPubBean.getApplInfos().getLostOrSolveState();//当前挂失状态标志
			String date1 = lostPubBean.getApplInfos().getSolveDate1();//解挂日期1
			String date2 = lostPubBean.getApplInfos().getSolveDate2();//解挂日期2
			logger.info("该卡当前挂失状态标志为："+mark+";解挂日期1为："+date1+";解挂日期2为："+date2);
			if(mark!=null &&("3".equals(mark.substring(3,4)) ||(("0".equals(mark.substring(3,4)) || "".equals(mark.substring(3,4).trim())) 
					&& !"1900/01/01".equals(date1) && "1900/01/01".equals(date2)))){//挂失已解
				return "2-该账户已非挂失状态，无法进行解挂操作，请重新输入挂失申请书号。";
			}
			resultMsg = "0";
			resultCheck=true;
		}
		
		//如果卡挂失申请书号信息查询失败，再查询核心挂失申请书信息
		Map resultOther=null;
		if(!resultCheck){
			resultOther = checkApplyNoOther();
			if("000000".equals(resultOther.get("resCode"))){
				String mark = lostPubBean.getApplInfos().getLostOrSolveState();//当前挂失状态标志
				logger.info("该账户当前挂失状态标志为："+mark);
				if(mark!=null && "1".equals(mark.substring(0,1))){
					return "2-该账户已非挂失状态，无法进行解挂操作，请重新输入挂失申请书号。";
				}
				resultMsg = "0";
				resultCheck = true;
			}
		}
		
		//查询完卡系统和核心系统的挂失申请书号，根据查询结果来处理
		if(!resultCheck){
			logger.info("没有查询到相对应挂失信息："+(String)resultCard.get("errMsg")+";"+resultOther.get("errMsg"));
			if("4444".equals(resultCard.get("resCode")) && "4444".equals(resultOther.get("resCode"))){
				resultMsg = "1-"+resultCard.get("errMsg")+";"+resultOther.get("errMsg");
			}else{
				if("5555".equals(resultCard.get("resCode")) && "5555".equals(resultOther.get("resCode"))){
					resultMsg = "2-未查到相应的挂失信息。";
				}else{
					resultMsg = "2-"+resultCard.get("errMsg")+";"+resultOther.get("errMsg");
				}
			}
		}
		return resultMsg;
	}
	
	/**
	 * 
	 * @Description:补全所有信息 
	 * @Author: hk
	 * @date 2018年4月17日 上午11:38:31
	 */
	public void checkAllInfos(){
		openProssDialog();
		//查询相关信息
		//判断挂失证件信息是否一致
		if(!lostPubBean.getAllPubIDNo().equals(lostPubBean.getApplInfos().getId_no().trim()) || !lostPubBean.getAllPubIdCardName().equals(lostPubBean.getApplInfos().getCust_name().trim())){
			
			//弹框提示
			logger.info("挂失信息与证件信息不一致");
			closeDialog(prossDialog);
			openConfirmDialog("挂失信息与证件信息不一致，请重新输入：是：重新输入。否：退出。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(confirmDialog);
					openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(confirmDialog);
					serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
				}
			});
			return;
		}

		
		//卡挂失的查询账户信息
		if("0".equals(lostPubBean.getSolveType())){
			//判断是否同一个机构办理
			if(!GlobalParameter.branchNo.equals(lostPubBean.getApplInfos().getLostInstNo().trim())){
				
				//弹框提示
				logger.info("非本机构办理的挂失业务，不能办理解挂");
				closeDialog(prossDialog);
				openConfirmDialog("非本机构办理挂失业务，是否重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			//判断挂失渠道
			if(!"08".equals(lostPubBean.getApplInfos().getChannel().trim())){
				logger.info("非本渠道的挂失业务，不能办理解挂");
				closeDialog(prossDialog);
				openConfirmDialog("非本渠道挂失业务，是否重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			if(!checkCardMsg()){
				return;
			}
		//存单、存折的查询账户信息
		}else{
			//判断是否同一个机构办理
			if(!GlobalParameter.branchNo.equals(lostPubBean.getApplInfos().getLostInstNo().trim())){
				//弹框提示
				logger.info("非本机构办理的挂失业务，不能办理解挂");
				closeDialog(prossDialog);
				openConfirmDialog("非本机构办理挂失业务，是否重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						return;
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			//判断挂失渠道
			if(!"0035".equals(lostPubBean.getApplInfos().getChannel().trim())){
				logger.info("非本渠道的挂失业务，不能办理解挂");
				closeDialog(prossDialog);
				openConfirmDialog("非本渠道挂失业务，是否重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			
			if(!checkOutAccNo()){//查询存单外部账号
				return;
			}
			
			//查询该账号所有信息
			Map map = checkSolveLostAcc();
			if(!"000000".equals(map.get("resCode"))){
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "查询账户信息失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return;
			}
			
			if(lostPubBean.getAllPubAccNo().contains("-")){
				if(lostPubBean.getAccKind()!=null && "11".equals(lostPubBean.getAccKind())){//电子子账户
					lostPubBean.setSolveLostType("1_2");
				}else{//卡下子账户
					lostPubBean.setSolveLostType("1_1");
				}
			}else{
				//筛掉活期存折和一本通(0000-活期存折，0001 0011 0012 0013 0014 0015 0018-其他存折类，8888-定期一本通，9999-活期一本通)
				if(!"0000".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
						!"0011".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0012".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
						!"0013".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"0014".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
						!"0015".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"0018".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
						!"8888".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"9999".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){
					lostPubBean.setSolveLostType("1");
				}else if("0000".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) || "0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) ||
						"0014".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) || "0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){//存折账户
					lostPubBean.setSolveLostType("2");
				}
			}
		}
		//赋值代理人信息
		lostPubBean.setAllPubAgentIdCardName(lostPubBean.getApplInfos().getId_AgentName());
		lostPubBean.setAllPubAgentIDNo(lostPubBean.getApplInfos().getId_AgentNo());
		lostPubBean.setAllPubAgentAddress(lostPubBean.getApplInfos().getId_AgentAddress());
		lostPubBean.setAllPubAgentPhone(lostPubBean.getApplInfos().getId_AgentPhone());
		
		closeDialog(prossDialog);
		openPanel(lostPubBean.getThisComponent(), new SolveLostApplNoInfos());
	}
	
	/**
	 * 校验卡系统挂失申请书号
	 * @return
	 */
	public Map checkApplyNoCard(){
		logger.info("查询卡挂失的申请书号信息");
		Map result08180 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08180");
			result08180 = InterfaceSendMsg.inter08180(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08180.get("resCode"),(String)result08180.get("errMsg") );
			if(!"000000".equals(result08180.get("resCode"))){
				logger.info("调用挂失申请书打印查询70031-前置【08180】失败："+result08180.get("errMsg"));
				return result08180;
			}
			if((List<LostCheckBean>)result08180.get("LostApplnoCheck")==null || ((List<LostCheckBean>)result08180.get("LostApplnoCheck")).size()==0){
				logger.error("未查询到卡系统挂失相关信息：");
				result08180.put("resCode","5555");
				result08180.put("errMsg","无此挂失申请书号对应的卡挂失信息");
				return result08180;
			}
			LostCheckBean bean = ((List<LostCheckBean>)result08180.get("LostApplnoCheck")).get(0);
			lostPubBean.setApplInfos(bean);
			lostPubBean.setSolveType("0");
			lostPubBean.setSolveLostType("0");
			lostPubBean.setCardNo(bean.getAcc_no());
			lostPubBean.setAllPubAccNo(bean.getAcc_no());
			lostPubBean.setLostDate(lostPubBean.getLostApplNo().substring(5,13));
		} catch (Exception e) {
			logger.error("调用挂失申请书打印查询70031-前置【08180】接口异常："+e);
			result08180.put("resCode","4444");
			result08180.put("errMsg","查询挂失申请书异常");
		}
		return result08180;
	}
	
	/**
	 * 校验存单/存折挂失申请书号
	 */
	public Map checkApplyNoOther(){
		logger.info("查询核心挂失的申请书号信息");
		Map result08179 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08179");
			result08179 = InterfaceSendMsg.inter08179(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)result08179.get("resCode"),(String)result08179.get("errMsg") );
			if(!"000000".equals(result08179.get("resCode"))){
				logger.info("调用挂失申请书打印查询02962-前置【08179】失败："+result08179.get("errMsg"));
				return result08179;
			}
			
			lostPubBean.setLostDate(lostPubBean.getLostApplNo().substring(5,13));
			if((List<LostCheckBean>)result08179.get("LostApplnoCheck")==null || ((List<LostCheckBean>)result08179.get("LostApplnoCheck")).size()==0){
				logger.error("未查询到核心系统挂失相关信息：");
				result08179.put("resCode","5555");
				result08179.put("errMsg","无此挂失申请书号对应的存单或存折挂失信息");
				return result08179;
			}
			LostCheckBean bean = ((List<LostCheckBean>)result08179.get("LostApplnoCheck")).get(0);
			lostPubBean.setAllPubAccNo(bean.getAcc_no());
			lostPubBean.setApplInfos(bean);
			lostPubBean.setSolveType("1");
		} catch (Exception e) {
			logger.error("调用挂失申请书打印查询02962-前置【08179】接口异常："+e);
			result08179.put("resCode","4444");
			result08179.put("errMsg","查询挂失申请书异常");
		}
		return result08179;
	}
	
	/**
	 * 账户信息查询
	 */
	public Map checkSolveLostAcc(){
		
		Map inter07601 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07601");
			lostPubBean.setIsPinPass("0");
			inter07601 = InterfaceSendMsg.inter07601(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07601.get("resCode"),(String)inter07601.get("errMsg") );
			if(!"000000".equals(inter07601.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+inter07601.get("errMsg"));
				return inter07601;
			}
		
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
			inter07601.put("resCode","4444");
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter("4444","调用07601接口失败" );
			return inter07601;
		}
		return inter07601;
		
	}

	/**
	 * 查询挂失申请书，判断账户是否允许解挂
	 */
	public void checkLostBook(){
		
		openProssDialog();
		Map LostMsg = null;
		String name = "";
		
		if(!checkCust()){//查询客户号
			return;
		}
				
		//查询挂失申请书
		if("3".equals(lostPubBean.getLostOrSolve())){//补发
			
			//查询存单
			LostMsg = checkApplyNoOther();
			name = "补发";
			
		}else if("4".equals(lostPubBean.getLostOrSolve())){//销户
			
			if("0".equals(lostPubBean.getSolveLostType())){
				
				//查银行卡
				LostMsg = checkApplyNoCard();
				
			}else{
				
				//查存单/存折
				LostMsg = checkApplyNoOther();
			}
			name = "销户";
		}
		
		//查询结果判断
		if(!"000000".equals((String)LostMsg.get("resCode"))){
			
			//弹框提示
			closeDialog(prossDialog);
			openMistakeDialog((String)LostMsg.get("errMsg")+",请重新输入");
			mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					closeDialog(mistakeDialog);
					openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
				}
			});	
			return;
			
		}else{
			
			List<LostCheckBean> list = (List<LostCheckBean>)LostMsg.get("LostApplnoCheck");
			
			//判断文件是否为空
			if(list != null && list.size()==0){
				
				//弹框提示
				logger.info("查询挂失申请书返回文件为空");
				closeDialog(prossDialog);
				openMistakeDialog("您输入的挂失申请书号有误,请重新输入");
				mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(mistakeDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				return;
			}
			
			//获取已挂失的天数
			int date = 0;
			try {
				String svrDate = lostPubBean.getAllPubSvrDate().trim().replaceAll("/", "");
				String lostDate = lostPubBean.getLostApplNo().substring(5,13).replace("/", "");
				SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
				Date a1 = fommter.parse(svrDate);
				Date b1 = fommter.parse(lostDate);
				logger.info("核心日期："+a1);
				logger.info("挂失日期："+b1);
				date = DateTool.differentsDays(b1, a1);
				logger.info("挂失天数："+date);
						
			} catch (Exception e) {
				logger.error("挂失日期计算失败"+e);
			}
			
			//判断已解挂的银行卡/存单、存折
			if("0".equals(lostPubBean.getSolveLostType())){//银行卡
				
				String mark = list.get(0).getLostOrSolveState();//当前挂失状态标志
				String date1 = list.get(0).getSolveDate1();//解挂日期1
				String date2 = list.get(0).getSolveDate2();//解挂日期2
				logger.info("该卡当前挂失状态标志为："+mark+";解挂日期1为："+date1+";解挂日期2为："+date2);
				if(mark!=null &&("3".equals(mark.substring(3,4)) ||(("0".equals(mark.substring(3,4)) || "".equals(mark.substring(3,4).trim())) 
						&& !"1900/01/01".equals(date1) && "1900/01/01".equals(date2)))){//挂失已解
					//弹框提示
					logger.info("查询挂失卡号已解挂");
					closeDialog(prossDialog);
					openMistakeDialog("该账户已解挂,请重新输入");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(mistakeDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					return;
				}
				
			}else{//存单/存折
				
				String mark = list.get(0).getLostOrSolveState();//当前挂失状态标志
				logger.info("该账户当前挂失状态标志为："+mark);
				if(mark!=null && "1".equals(mark.substring(0,1))){
					//弹框提示
					logger.info("查询挂失账号已解挂");
					closeDialog(prossDialog);
					openMistakeDialog("该账户已解挂,请重新输入");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(mistakeDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					return;
				}
				
			}
			
			//判断挂失证件信息是否一致
			if(!lostPubBean.getAllPubIDNo().equals(list.get(0).getId_no().trim()) || !lostPubBean.getAllPubIdCardName().equals(list.get(0).getCust_name().trim())){
				
				//弹框提示
				logger.info("挂失信息与证件信息不一致");
				closeDialog(prossDialog);
				openConfirmDialog("挂失信息与证件信息不一致，请重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			
			//判断是否同一个机构办理
			if(!GlobalParameter.branchNo.equals(list.get(0).getLostInstNo())){
				
				//弹框提示
				logger.info("非本机构办理的挂失业务，不能办理解挂");
				closeDialog(prossDialog);
				openConfirmDialog("非本机构办理挂失业务，是否重新输入：是：重新输入。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
				});
				return;
			}
			
			//赋值代理人信息
			lostPubBean.setAllPubAgentIdCardName(list.get(0).getId_AgentName());
			lostPubBean.setAllPubAgentIDNo(list.get(0).getId_AgentNo());
			lostPubBean.setAllPubAgentAddress(list.get(0).getId_AgentAddress());
			lostPubBean.setAllPubAgentPhone(list.get(0).getId_AgentPhone());
			
			if("0".equals(lostPubBean.getSolveLostType())){//银行卡
				
				logger.info("挂失的银行卡号:"+list.get(0).getAcc_no().trim());
				lostPubBean.setCardNo(list.get(0).getAcc_no().trim());
				lostPubBean.setAllPubAccNo(list.get(0).getAcc_no().trim());
				lostPubBean.setAllPubPassAccNo(list.get(0).getAcc_no().trim());
				//查询卡信息
				if(!checkCardMsg()){
					return;
				}
				Map card03521 = null;
				Map card07601 = null;
				//查询卡状态1
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("03521");
					card03521 = InterfaceSendMsg.card03521(lostPubBean);
					//上送流水调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)card03521.get("resCode"), (String)card03521.get("errMsg"));
					if(!"000000".equals(card03521.get("resCode"))){
						
						   logger.info("调用账户信息查询及密码验证-前置03521接口失败："+card03521.get("errMsg"));
						   closeDialog(prossDialog);
						   //上送流水信息
						   MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						   serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card03521.get("errMsg"), "");
						   return;
					}
				
				} catch (Exception e) {
					
					logger.error("调用账户信息查询及密码验证-前置03521接口异常："+e);
					closeDialog(prossDialog);
					//调用接口失败，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用03521接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用03521接口失败");
					 return;
				}
				
				//查询卡状态2
				try {
					//上送流水调用接口前信息
					lostPubBean.getReqMCM001().setReqBefor("07601");
					card07601 = InterfaceSendMsg.card07601(lostPubBean);
					//上送流水调用接口后信息
					lostPubBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
					if(!"000000".equals(card07601.get("resCode"))){
						
						 logger.info("调用账号信息综合查询【02880】-前置07601接口失败："+card07601.get("errMsg"));
						 closeDialog(prossDialog);
						 //上送流水信息
						 MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						 serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", (String) card07601.get("errMsg"), "");
						 return;
					}
				
				} catch (Exception e) {
					
					logger.error("调用账号信息综合查询【02880】-前置07601接口异常："+e);
					closeDialog(prossDialog);
					//调用接口失败，上送流水信息
					lostPubBean.getReqMCM001().setIntereturnmsg("调用07601接口失败");
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "查询银行卡信息失败，请联系大堂经理", "", "调用07601接口失败");
					return;
				}
				
				String accStateFY = "";
				if(!"08".equals(list.get(0).getChannel().trim())){//判断挂失渠道
					accStateFY = "非本渠道挂失业务";
				}if("*".equals(lostPubBean.getCardState())){
					accStateFY = "已作废";
				}else if("Q".equals(lostPubBean.getCardState())){//判断卡状态
					accStateFY = "已销户";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(1)))){//判断卡账户状态
					accStateFY = "封闭冻结";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(2)))){
					accStateFY = "部分冻结";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(3)))){
					accStateFY = "只收不付";
				}else if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(3)))){
					accStateFY = "电信诈骗全额止付";
				}else if("3".equals(String.valueOf(lostPubBean.getAccState().charAt(3)))){
					accStateFY = "电信诈骗全额冻结";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(21)))){
					accStateFY = "部分止付";
				}else if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(21)))){
					accStateFY = "全额止付";
				}else if(card03521.get("cardState1")!=null && !"".equals((String)card03521.get("cardState1"))){
					accStateFY = (String)card03521.get("cardState1");
				}else if(card07601.get("cardState2")!=null && !"".equals((String)card07601.get("cardState2"))){
					accStateFY = (String)card07601.get("cardState2");
				}else if(Integer.valueOf(lostPubBean.getAllPubSvrDate().trim().replaceAll("/", "")) >= Integer.valueOf(lostPubBean.getLostApplNo().substring(5,13)) && date < 7){
					if("N".equals(lostPubBean.getCardState())){//判断未激活7天之内
						accStateFY = "挂失未满七天";
					}else if(list.get(0).getLostType()!=null && "4".equals(list.get(0).getLostType())){//判断双挂失7天之内
						if("1".equals(list.get(0).getLostOrSolveState().substring(3,4))){
							accStateFY = "双挂未满七天";
						}
					}
				}
				
				if(!"".equals(accStateFY)){
					
					//弹框提示
					logger.info("银行卡状态:"+accStateFY+"，不能办理解挂销户");
					closeDialog(prossDialog);
					openConfirmDialog("该银行卡"+accStateFY+"，不允许解挂销户，是否重新输入：是：重新输入。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
					});
					return;
				}
				
				if(!"N".equals(lostPubBean.getCardState())){//判断未激活
					
					logger.info("该银行卡未激活");
					
					//查询银行卡是否有到期的在途理财
					if(!checkMoneyPro()){
						return;
					}
					//查询银行卡是否有关联的未还清贷款
					if(!checkMoneyHaveLoan()){
						return;
					}
					//查询银行卡是否有基金
					checkFundMoney();
					
					//查询子账户列表
					Map checkSubAcc = checkSubAcc();
					if(!"000000".equals(checkSubAcc.get("resCode"))){
						return;
					}
					
					//判断
					StringBuffer cardMsg  = new StringBuffer("");
					if("N".equals(lostPubBean.getChkFlag())){
						
						logger.info("该银行卡有理财产品");
						cardMsg.append("理财账户未解约，");
					}
					if("1".equals(lostPubBean.getAmtFlag())){
						
						logger.info("该银行卡有关联的未还清贷款");
						cardMsg.append("有未还清贷款，");
					}
					if(lostPubBean.getFundFlag()!=null && "F".equals(lostPubBean.getFundFlag())){
						
						logger.info("该银行卡有基金");
						cardMsg.append("有基金，");
					}
					List<SubAccInfoBean> subList = (List<SubAccInfoBean>)checkSubAcc.get("cardSub");
					if(subList.size()>0){
						for (SubAccInfoBean subAccInfoBean : subList) {
							if(!"Q".equals(subAccInfoBean.getMark()) && !"*".equals(subAccInfoBean.getMark())){
								
								logger.info("该银行卡有未销户子账户，不允许解挂销户："+subAccInfoBean.getSubAccNo());
								cardMsg.append("有未销户子账户，");
								break;
							}
						}
					}
					
					if(!"".equals(cardMsg.toString())){
						
						//弹框提示
						closeDialog(prossDialog);
						openConfirmDialog("该银行卡"+cardMsg+"不允许解挂销户，是否重新输入：是：重新输入。否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
							}
							
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
							}
							
						});
						return;
					}
				}
				
			}else{//存单/存折
				
				lostPubBean.setAllPubAccNo(list.get(0).getAcc_no().trim());
				lostPubBean.setAllPubPassAccNo(list.get(0).getAcc_no().trim());
				
				if(!checkOutAccNo()){//查询存单外部账号
					return;
				}
				logger.info("挂失的账号："+lostPubBean.getAllPubAccNo());
				
				if(lostPubBean.getAllPubAccNo().contains("-")){//子账户存单
					
					if("2".equals(lostPubBean.getSolveLostType())){//选择存折解挂销户，不能办理存单业务
						
						//弹框提示
						logger.info("与选择的账户种类不匹配");
						closeDialog(prossDialog);
						openConfirmDialog("账户信息不匹配，请录入对应的存折账户，是否重新输入。是：重新输入；否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
							}
						});
						return;
					}
					
					if(!"0008".equals(lostPubBean.getAllPubAccNo().substring(6,10))){//卡下子账户
						
						//选择卡下子账户存单并赋值校验密码的账号
						lostPubBean.setAllPubPassAccNo(lostPubBean.getAllPubAccNo().substring(0, lostPubBean.getAllPubAccNo().indexOf("-")));
						lostPubBean.setSolveLostType("1_1");
						
					}else{//电子子账户
						
						//选择电子子账户存单并赋值校验密码的账号
						lostPubBean.setAllPubPassAccNo(lostPubBean.getAllPubAccNo().substring(0, lostPubBean.getAllPubAccNo().indexOf("-")));
						lostPubBean.setSolveLostType("1_2");
						
					}
					
				}else{//普通存单/存折
					
					//查询一本通外部账号
					Map inter08176 = null;
					try {
						logger.info("查询一本通外部账号:"+lostPubBean.getAllPubAccNo().trim());
						//上送流水调用接口前信息
						lostPubBean.getReqMCM001().setReqBefor("08176");
						inter08176 = InterfaceSendMsg.inter08176(lostPubBean);
						//上送流水调用接口后信息
						lostPubBean.getReqMCM001().setReqAfter((String)inter08176.get("resCode"), (String)inter08176.get("errMsg"));
						if(!"000000".equals(inter08176.get("resCode"))){
							
							logger.info("调用一本通账号查询【20098】-前置【08176】接口失败："+inter08176.get("errMsg"));
						}
					
					} catch (Exception e) {
						
						logger.error("调用一本通账号查询【20098】-前置【08176】接口异常："+e);
						
					}
					String accNo = (String)inter08176.get("SVR_JRNL_NO_R");
					String maxState = (String)inter08176.get("MX_STAT");
					if(accNo!=null && !"".equals(accNo)){
						if(!"2".equals(maxState)){
							lostPubBean.setAllPubAccNo(accNo);
						}
					}
				
					//不支持一本通办理解挂补发/销户
					if("8888".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "9999".equals(lostPubBean.getAllPubAccNo().substring(9, 13))){
						
						//弹框提示
						logger.info("不支持一本通办理解挂补发/销户");
						closeDialog(prossDialog);
						openConfirmDialog("机具不支持一本通账户解挂"+name+"，是否重新输入。是：重新输入；否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
							}
						});
						return;
					}
					
					if("0000".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) || "0001".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) ||
							"0014".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) || "0015".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){
						
						if("1".equals(lostPubBean.getSolveLostType())){
							
							//弹框提示
							logger.info("与选择的账户种类不匹配");
							closeDialog(prossDialog);
							openConfirmDialog("账户信息不匹配，请录入对应的存单账户，是否重新输入。是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}
						
						if("3".equals(lostPubBean.getLostOrSolve())){
						
							//弹框提示
							logger.info("存折账户不支持解挂补发");
							closeDialog(prossDialog);
							openConfirmDialog("机具不支持存折账户解挂"+name+"，是否重新输入。是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}
					}else if(!"0011".equals(lostPubBean.getAllPubAccNo().substring(9, 13)) && !"0012".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) &&
							!"0013".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13)) && !"0018".equals(lostPubBean.getAllPubAccNo().trim().substring(9, 13))){
						
						if("2".equals(lostPubBean.getSolveLostType())){
							
							//弹框提示
							logger.info("与选择的解挂种类不匹配");
							closeDialog(prossDialog);
							openConfirmDialog("账户信息不匹配，请录入对应的存折账户，是否重新输入。是：重新输入；否：退出。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									closeDialog(confirmDialog);
									serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
								}
							});
							return;
						}
					}else{
						
						//弹框提示
						logger.info("0011、0012、0013、0018账户不支持挂失、解挂");
						closeDialog(prossDialog);
						openConfirmDialog("该账户类型不支持解挂"+name+"，是否重新输入。是：重新输入；否：退出。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								closeDialog(confirmDialog);
								serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
							}
						});
						return;
					}
				}
				
				//查询账户信息
				Map checkSolveLostAcc = checkSolveLostAcc();
				if(!"000000".equals(checkSolveLostAcc.get("resCode"))){
					
					//弹框提示
					closeDialog(prossDialog);
					openMistakeDialog((String)checkSolveLostAcc.get("errMsg")+",请重新输入");
					mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(mistakeDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});	
					return;
				}
				
				String accstate = ""; 
				if(!"0035".equals(list.get(0).getChannel().trim())){//判断挂失渠道
					accstate = "非本渠道挂失业务";
				}else if("*".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){//判断存单/存折账户,卡下子账户、电子子账户状态
					accstate = "已作废";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
					accstate = "已停用";
				}else if("Q".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
					accstate = "已销户";
				}else if("3".equals(String.valueOf(lostPubBean.getAccState().charAt(0)))){
					accstate = "渠道限制";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(2)))){
					accstate = "封闭冻结";
				}else if("2".equals(String.valueOf(lostPubBean.getAccState().charAt(2)))){
					accstate = "部分冻结";
				}else if("3".equals(String.valueOf(lostPubBean.getAccState().charAt(2)))){
					accstate = "只收不付";
				}else if("1".equals(String.valueOf(lostPubBean.getAccState().charAt(3)))){
					accstate = "止付";
				}else if(Integer.valueOf(lostPubBean.getAllPubSvrDate().trim().replaceAll("/", "")) >= Integer.valueOf(lostPubBean.getLostApplNo().substring(5,13)) && date < 7){
					
					if(list.get(0).getLostType()!=null && list.get(0).getLostType().trim().endsWith("6")){//判断双挂失7天之内
						if("1".equals(list.get(0).getLostOrSolveState().substring(2,3))){
							accstate = "双挂未满七天";
						}
					}else if("0".equals(lostPubBean.getDrawCond()) || "2".equals(lostPubBean.getDrawCond()) || "3".equals(lostPubBean.getDrawCond())){
						accstate = "挂失未满七天";
					}
				}
				
				if(!"".equals(accstate)){
					
					//弹框提示
					logger.info("账户状态:"+accstate+"，不能办理解挂"+name);
					closeDialog(prossDialog);
					openConfirmDialog("该账户"+accstate+"，不允许解挂"+name+"，是否重新输入：是：重新输入。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
					});
					return;
				}
			}
		}
		//查询黑灰名单
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("01597");
			Map map01597 = InterfaceSendMsg.inter01597(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map01597.get("resCode"), (String)map01597.get("errMsg"));
			String resCode = (String)map01597.get("resCode");
			if(!"000000".equals(resCode)){
				if("0010".equals(resCode) || "0020".equals(resCode)){
					String type = null;
					if ("0010".equals(resCode)) {
						logger.info("账户为涉案账户："+resCode);
						type = "涉案账户";
					} else if ("0020".equals(resCode)) {
						logger.info("账户为可疑账户："+resCode);
						type = "可疑账户";
					}		
					closeDialog(prossDialog);
					openConfirmDialog("该账户为"+type+"，是否重新输入。是：重新输入；否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
					});
					return;
				
				}else{
					closeDialog(prossDialog);
					//上送流水
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					this.serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理",(String)map01597.get("errMsg"),"");
					return;
				}
			}
		} catch (Exception e) {
			logger.error("调用01597黑灰名单查询接口失败："+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水
			lostPubBean.getReqMCM001().setIntereturnmsg("调用01597接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			this.serverStop(lostPubBean.getThisComponent(),"黑灰名单查询失败，请联系大堂经理","","调用黑灰名单查询接口失败");
			return;
		}
		
		//查询买卖账户、失信账户(黑名单)
		if(!checkHMD()){
			return;
		}
		
		//全部检验成功，进入挂失账户信息展现页
		closeDialog(prossDialog);
		openPanel(lostPubBean.getThisComponent(), new SolveLostApplNoInfos());
	}
	
	/**
	 * 判断该账户是否允许解挂
	 */
	public void accIsSolve(){
		openProssDialog();
		
		if("0".equals(lostPubBean.getSolveLostType())){
			
			//判断银行卡约定转存
			if(lostPubBean.getIsAgreedDep()!=null && "1".equals(lostPubBean.getIsAgreedDep())){
				
				//弹框提示
				logger.info("该银行卡已开通约定转存");
				closeDialog(prossDialog);
				openConfirmDialog("该银行卡已开通约定转存，将要解除，是否继续：是：继续。否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						
						//银行卡未激活满足7天挂失期限
						closeDialog(confirmDialog);
						if("N".equals(lostPubBean.getCardState())){
							
							//进入重置密码
							logger.info("该银行卡未激活满足期限,进入重置密码页");
							lostPubBean.setReMakePwdNo("0");
							lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
							allPubTransFlow.transFlow();	
								
						}else{//已激活
							acc1IsSolve();
						}
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						closeDialog(confirmDialog);
						serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
					}
					
				});
				return;
			}
			
			if("N".equals(lostPubBean.getCardState())){//银行卡未激活满足7天挂失期限
				
				//进入重置密码
				logger.info("该银行卡未激活满足期限,进入重置密码页");
				closeDialog(prossDialog);
				lostPubBean.setReMakePwdNo("0");
				lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
				allPubTransFlow.transFlow();	
					
			}else{//已激活
				acc1IsSolve();
			}
		}else{//存单/存折
			
			//解挂销户，判断存单是否未到期
			if(!"2".equals(lostPubBean.getLostOrSolve()) && "4".equals(lostPubBean.getLostOrSolve())){
				
				logger.info("到期日:"+lostPubBean.getEndDate().trim().replace("/", ""));//到期日
				logger.info("当前核心日:"+lostPubBean.getAllPubSvrDate().trim().replace("/", ""));//当前核心日
				int endDate = Integer.parseInt(lostPubBean.getEndDate().trim().replace("/", ""));//到期日
				int nowDate = Integer.parseInt(lostPubBean.getAllPubSvrDate().trim().replace("/", ""));//当前核心日
				lostPubBean.setTiZhiQu("0");//已到期标识
				if(endDate > nowDate){//未到期
					
					lostPubBean.setTiZhiQu("1");//未到期标识
					checkTD();//查询唐豆
					checkXYD();//查询幸运豆-("改为已付收益")
					String tdMsg = "";
					String xydMsg = "";
					String msg = "";
					if(lostPubBean.getTangChaErrMsg()!=null && !"".equals(lostPubBean.getTangChaErrMsg().trim())){
						
						tdMsg = lostPubBean.getTangChaErrMsg() +"、";
						logger.info("唐豆查询失败:"+lostPubBean.getTangChaErrMsg());
						
						if(lostPubBean.getXYDChaErrMsg()!=null && !"".equals(lostPubBean.getXYDChaErrMsg().trim())){
							
							xydMsg = lostPubBean.getXYDChaErrMsg();
							logger.info("已付收益查询失败:"+lostPubBean.getXYDChaErrMsg());
							
						}else{
							if(lostPubBean.getAdvnInit()!=null && !"".equals(lostPubBean.getAdvnInit().trim())){
								double xyd = Double.valueOf(lostPubBean.getAdvnInit());
								if(xyd!=0){
									
									xydMsg = "提前支取扣回已付收益"+lostPubBean.getAdvnInit().trim()+"元";
									logger.info("已付收益查询成功:"+lostPubBean.getAdvnInit().trim()+"元");
								}
							}
						}
						
						msg = tdMsg + xydMsg;
								
					}else{
						if(lostPubBean.getShtdy()!=null && !"".equals(lostPubBean.getShtdy().trim())){
							double td = Double.valueOf(lostPubBean.getShtdy());
							if(td!=0){
								
								tdMsg = "提前支取将扣回唐豆"+lostPubBean.getShtdy().trim()+"元"+"、";
								logger.info("唐豆查询成功:"+lostPubBean.getShtdy().trim()+"元");
							}
						}
						if(lostPubBean.getXYDChaErrMsg()!=null && !"".equals(lostPubBean.getXYDChaErrMsg().trim())){
							
							xydMsg = lostPubBean.getXYDChaErrMsg();
							logger.info("已付收益查询失败:"+lostPubBean.getXYDChaErrMsg());
							
						}else{
							if(lostPubBean.getAdvnInit()!=null && !"".equals(lostPubBean.getAdvnInit().trim())){
								double xyd = Double.valueOf(lostPubBean.getAdvnInit());
								if(xyd!=0){
									
									xydMsg = "扣回已付收益"+lostPubBean.getAdvnInit().trim()+"元";
									logger.info("已付收益查询成功:"+lostPubBean.getAdvnInit().trim()+"元");
								}
							}
						}
						
						msg = tdMsg + xydMsg;
					}
					if(!"".equals(msg)){
						msg = msg+",";
					}
					
					//弹框提示是否继续
					logger.info("该存单未到期，是否继续办理解挂销户");
					logger.info("msg："+msg);
					closeDialog(prossDialog);
					openConfirmDialog("存单未到期，"+msg+"是否继续：是：继续解挂销户。否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							//判断挂失期限
							closeDialog(confirmDialog);
							acc1IsSolve();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostApplNoInfos());
						}
					});
					return;
				}	
			}
			
			//判断挂失期限
			acc1IsSolve();
		}
	}
	
	/**
	 * 判断挂失期限 
	 */
	public void acc1IsSolve(){
		
		if("0".equals(lostPubBean.getSolveAccState()) || "2".equals(lostPubBean.getSolveAccState()) || "4".equals(lostPubBean.getSolveAccState())){//单挂失
			
			if("0".equals(lostPubBean.getDrawCond()) || "2".equals(lostPubBean.getDrawCond()) || "3".equals(lostPubBean.getDrawCond())){//判断存单/存折无密码、凭证件、凭印鉴
				
				if("3".equals(lostPubBean.getLostOrSolve())){//解挂补发
					//进入账户信息签字页面
					closeDialog(prossDialog);
					openPanel(lostPubBean.getThisComponent(), new SolveLostApplNoInfosConfirmPage());
					
				}else{//解挂销户
					
					if("0".equals(lostPubBean.getSolveLostType()) || "2".equals(lostPubBean.getSolveLostType()) || "1".equals(lostPubBean.getSolveLostType())){//银行卡、存折/个人普通存单
						
						//查询解挂销户转入的银行卡信息
						checkAllCards();
						return;
						
					}else if("1_1".equals(lostPubBean.getSolveLostType()) || "1_2".equals(lostPubBean.getSolveLostType())){//存单卡下子账号、存单电子子账号
						
						//进入自动带出卡/电子账号显示页
						closeDialog(prossDialog);
						String[] split = lostPubBean.getAllPubAccNo().split("-");
						lostPubBean.setSelectCardNo(split[0]);
						openPanel(lostPubBean.getThisComponent(), new SolveLostGetCardOrEcardPage());
						return;
					}
				}
				
				
			}else{//有密码
				
				//输入密码页
				logger.info("单挂，进入输入密码页");
				closeDialog(prossDialog);
				lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_PASSWORD_PANEL");
				allPubTransFlow.transFlow();
			}
			
		}else{//双挂失满足7天挂失期限
			
			//进入重置密码
			logger.info("双挂且满足挂失期限,进入重置密码页");
			closeDialog(prossDialog);
			lostPubBean.setReMakePwdNo("0");
			lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
			allPubTransFlow.transFlow();	
				
		}
	}
	
	/**
	 * 
	 * @Description:校验重置的密码 
	 * @Author: hk
	 * @date 2018年4月13日 下午6:31:53
	 */
	public void checkReMakePwd(){
		//如果是第一次输入密码
		if("0".equals(lostPubBean.getReMakePwdNo()) && lostPubBean.getAllPubAccPwd()!=null && !"".equals(lostPubBean.getAllPubAccPwd())){
			try {
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("01325");
				Map result01325 = InterfaceSendMsg.inter01325(lostPubBean);
				//上送流水调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)result01325.get("resCode"), (String)result01325.get("resCode"));
				if(!"000000".equals(result01325.get("resCode"))){
					logger.info("校验弱密码失败："+result01325.get("errMsg"));
					openConfirmDialog("您输入的密码为弱密码,请重新重置密码 是：重新重置密码；否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							//重置密码
							logger.info("选择是：重新重置密码");
							lostPubBean.setReMakePwdNo("0");
							lostPubBean.setReMakePwd("");
							lostPubBean.setAllPubAccPwd("");
							//重置密码
							lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
							allPubTransFlow.transFlow();
							return;
						}
					});
					
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							//退出
							logger.info("选择否：退出");
							serverStop(lostPubBean.getThisComponent(), "服务终止。", "", "");
							return;
						}
					});
					return;
				}
				logger.info("第一次输入的密码："+lostPubBean.getAllPubAccPwd());
				lostPubBean.setReMakePwdNo("1");
				lostPubBean.setReMakePwd(lostPubBean.getAllPubAccPwd());
				lostPubBean.setAllPubAccPwd("");
				
				//第二次输入密码
				lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
				allPubTransFlow.transFlow();
				
				
			} catch (Exception e) {
				logger.error("校验密码失败，调用接口失败：01325");
				//上送流水
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(),"校验弱密码异常，请联系大堂经理。", "", "调用弱密码校验接口01325异常");
				return;
			}
		//重置密码已经通过若密码校验
		}else if("1".equals(lostPubBean.getReMakePwdNo())&&lostPubBean.getAllPubAccPwd()!=null && !"".equals(lostPubBean.getAllPubAccPwd())){
			if(lostPubBean.getReMakePwd()!=null && lostPubBean.getAllPubAccPwd()!=null && lostPubBean.getReMakePwd().equals(lostPubBean.getAllPubAccPwd())){
				logger.info("两次输入的密码相同，第二次输入的密码："+lostPubBean.getAllPubAccPwd());
				lostPubBean.setReMakePwdNo("");//清除重置密码标志
				//重新输入密码
				openPanel(lostPubBean.getThisComponent(),new SolveLostReMakePwdSusscessPage());
				return;
			}else{ 
				openConfirmDialog("两次输入的密码不一致，请重新输入。是：重新输入；否：退出。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//重置密码
						logger.info("选择是：重新重置密码");
						lostPubBean.setReMakePwdNo("0");
						lostPubBean.setReMakePwd("");
						lostPubBean.setAllPubAccPwd("");
						//重置密码
						lostPubBean.setNextStepName("ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE");
						allPubTransFlow.transFlow();
						return;
					}
				});
				
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						//退出
						logger.info("选择否：退出");
						serverStop(lostPubBean.getThisComponent(), "服务终止。", "", "");
						return;
					}
				});
				return;
			}
			
		}else{
			logger.info("校验两次输入的密码是否相同异常："+lostPubBean.getReMakePwdNo()+";新密码："+lostPubBean.getReMakePwd()+";设置的密码："+lostPubBean.getAllPubAccPwd());
			//上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("重置密码失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"重置密码失败，请联系大堂经理。", "", "重置密码时异常");
			return;
		}
	}
	
//	/**
//	 * 
//	 * @Description: 重置密码
//	 * @Author: hk
//	 * @date 2018年4月16日 下午4:47:42
//	 */
//	public void ReMakePwd(){
//		logger.info("重置密码");
//		//卡系统重置密码
//		if("0".equals(lostPubBean.getSolveLostType())){
//			try {
//				Map result08159 = InterfaceSendMsg.inter08159(lostPubBean);
//				if(!"000000".equals(result08159.get("resCode"))){
//					logger.info("重置密码失败："+result08159.get("errMsg"));
//					serverStop(lostPubBean.getThisComponent(), "重置密码失败，请联系大堂经理。", (String)result08159.get("errMsg"), "");
//					return;
//				}
//			} catch (Exception e) {
//				logger.error("重置卡密码异常："+e);
//				serverStop(lostPubBean.getThisComponent(), "重置密码失败，请联系大堂经理。", "", "调用08159异常");
//				return;
//			}
//		//非卡重置密码
//		}else{
//			try {
//				Map result07517 = InterfaceSendMsg.inter07517(lostPubBean);
//				if(!"000000".equals(result07517.get("resCode"))){
//					logger.info("重置密码失败："+result07517.get("errMsg"));
//					serverStop(lostPubBean.getThisComponent(), "重置密码失败，请联系大堂经理。", (String)result07517.get("errMsg"), "");
//					return;
//				}
//			} catch (Exception e) {
//				logger.error("重置卡密码异常："+e);
//				serverStop(lostPubBean.getThisComponent(), "重置密码失败，请联系大堂经理。", "", "调用07517异常");
//				return;
//			}
//		}
//		openPanel(lostPubBean.getThisComponent(),new SolveLostReMakePwdSusscessPage());
//		return;
//	}
	
	/**
	 * 
	 * @Description:挂失撤销 
	 * @Author: hk
	 * @date 2018年4月16日 下午6:12:10
	 */
	public boolean solveLostReBack(){
		logger.info("进行挂失撤销处理，解挂类型："+lostPubBean.getSolveType());
		//卡挂失撤销
		if("0".equals(lostPubBean.getSolveType())){
			try {
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("08198");
				Map result08198 = InterfaceSendMsg.inter08198(lostPubBean);
				//上送流水调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)result08198.get("resCode"), (String)result08198.get("errMsg"));
				if(!"000000".equals(result08198.get("resCode"))){
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "挂失撤销失败，请联系大堂经理。", (String)result08198.get("errMsg"), "");
					return false;
				}
			} catch (Exception e) {
				logger.error("卡系统挂失撤销异常："+e);
				//调用接口异常，上送流水信息
				lostPubBean.getReqMCM001().setIntereturnmsg("调用08198接口失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失撤销失败，请联系大堂经理。", "", "调用挂失接口08198异常");
				return false;
			}
		//非银行卡的挂失撤销	
		}else{
			try {
				//上送流水调用接口前信息
				lostPubBean.getReqMCM001().setReqBefor("08194");
				Map result08194 = InterfaceSendMsg.inter08194(lostPubBean);
				//上送流水调用接口后信息
				lostPubBean.getReqMCM001().setReqAfter((String)result08194.get("resCode"), (String)result08194.get("errMsg"));
				if(!"000000".equals(result08194.get("resCode"))){
					//上送流水信息
					MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
					serverStop(lostPubBean.getThisComponent(), "挂失撤销失败，请联系大堂经理。", (String)result08194.get("errMsg"), "");
					return false;
				}
			} catch (Exception e) {
				logger.error("卡系统挂失撤销异常："+e);
				//调用接口异常，上送流水号
				lostPubBean.getReqMCM001().setIntereturnmsg("调用08194接口失败");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(), "挂失撤销失败，请联系大堂经理。", "", "调用挂失接口08194异常");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 挂失销户凭条打印处理
	 */
	public void lostXhPrint(){
		
		//凭条打印
		LostXHPrintPt print = new LostXHPrintPt();
		Map<String,String> map = new HashMap<String,String>();
		map.put("selectCardNo", lostPubBean.getSelectCardNo());//转入卡号
		if("2".equals(lostPubBean.getLostOrSolve())){//挂失销户
			map.put("cardType", ((ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg")).getCardOrAccOrAcc1());//转入账号类型
		}else if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
			map.put("cardType", lostPubBean.getSolveLostType());//转入账号类型
		}
		map.put("lostAccNo", lostPubBean.getAllPubAccNo());//挂失账户
		map.put("lostType", "挂失销户");//交易类型
		map.put("svrDate", lostPubBean.getAllPubSvrDate().replace("/", ""));//核心日期
		map.put("custName", lostPubBean.getCustName());//客户名
		map.put("realPri", lostPubBean.getCancelRealInte());//实付利息
		map.put("realAmt", lostPubBean.getCanceAmt());//实付本金
		map.put("lostJrnlNo", lostPubBean.getLostJrnlNo());//挂失销户流水
		
		Map<String, String> result = print.print(map);
		if(result.get("resCode") != null && !"000000".equals(result.get("resCode"))){
			lostPubBean.setPinErrMsg(result.get("errMsg"));//获取凭条打印错误信息
		}
		
		sleep(5);
		logger.info("睡眠５s,进入销户成功展现页面");
		
		//进入销户成功页面
		openPanel(lostPubBean.getThisComponent(), new LostAccCancelSuccessPanel());//销户成功页面
	}
	
	/**
	 * 07515唐豆查询
	 */
	public void checkTD(){
		logger.info("开始调用唐豆信息查询【02217】-前置07515");
		Map inter07515 = null;
		Date svrdate = null;//核心日
		Date openDate = null;//开户日期
		String demTerm = null;//获取存期
		inter07515 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07515");
			inter07515 = InterfaceSendMsg.inter07515(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07515.get("resCode"), (String)inter07515.get("resCode"));
			if(!"000000".equals(inter07515.get("resCode"))){
				
				logger.info(inter07515.get("errMsg"));
				lostPubBean.setTangChaErrMsg((String)inter07515.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07515唐豆信息查询失败"+e);
			lostPubBean.setTangChaErrMsg("唐豆信息查询失败");
		}	
		
		List<AccTdMsgBean> list = (List<AccTdMsgBean>)inter07515.get("TdMsg");
		
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
							svrdate =  DateUtil.getDate(lostPubBean.getAllPubSvrDate());
							openDate = DateUtil.getDate(lostPubBean.getOpenDate());
							//获取开户日期之后的一年
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(openDate);
							int year = calendar.get(Calendar.YEAR);
							calendar.set(Calendar.YEAR, year+1);
							//一年后的开户日
							openDate = calendar.getTime();
							demTerm = accTdMsgBean.getDepTerm().trim();
							
						} catch (ParseException e) {
							logger.info("获取唐豆信息失败:"+e);
							lostPubBean.setTangChaErrMsg("获取唐豆信息失败");
						}
						
						//判断存期，当存期为两年，超过1年再支取的，不调用唐豆收回接口
						if(demTerm.contains("Y") && Integer.parseInt(demTerm.replace("Y", "")) == 2 && openDate.compareTo(svrdate) <= 0){
							
							logger.info("存期为两年，超过1年再支取,不扣回唐豆");
							lostPubBean.setTangChaErrMsg("存期为两年，超过1年再支取,不扣回唐豆");
							
						}else{
							
							logger.info("获取提前支取需要扣回的唐豆金额:"+accTdMsgBean.getShouhuiAmt());
							lostPubBean.setShtdy(accTdMsgBean.getShouhuiAmt());//获取唐豆金额
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
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("07696");
			inter07696 = InterfaceSendMsg.inter07696(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter07696.get("resCode"), (String)inter07696.get("errMsg"));
			if(!"000000".equals(inter07696.get("resCode"))){
				
				logger.info(inter07696.get("errMsg"));
				lostPubBean.setXYDChaErrMsg((String)inter07696.get("errMsg"));
			}
			
		} catch (Exception e) {
			
			logger.error("调用07696已付收益查询失败"+e);
			lostPubBean.setXYDChaErrMsg("已付收益查询失败");
		}	
	}
	/**
	 * 查询外部账号
	 * 
	 */
	public boolean checkOutAccNo(){
		logger.info("调用08237接口查询外部账号");
		Map inter08237 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08237");
			inter08237 = InterfaceSendMsg.inter08237(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08237.get("resCode"), (String) inter08237.get("errMsg"));
			if(!"000000".equals(inter08237.get("resCode"))){
				logger.info(inter08237.get("errMsg"));
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(),"查询外部账号失败",(String) inter08237.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用查询外部账号接口失败08237"+e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08237接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"查询外部账号失败，请联系大堂经理","", "调用08237接口查询外部账号失败");
			return false;
		}	
		return true;
	}
	
	/**
	 * 
	 * 黑名单查询【20115】-前置【08236】
	 */
	public boolean checkHMD(){
		// 黑名单查询
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08236");
			Map map08236 = InterfaceSendMsg.inter08236(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map08236.get("resCode"), (String)map08236.get("errMsg"));
			if ("000000".equals(map08236.get("resCode"))) {
				String IDstata = (String) map08236.get("IDstata");
				logger.info("此账户状态：" + IDstata + "(2.买卖账户 3.失信账户)");
				if ("2".equals(IDstata) || "3".equals(IDstata)) {
					String type = null;
					if ("2".equals(IDstata)) {
						type = "买卖账户";
					} else if ("3".equals(IDstata)) {
						type = "失信账户";
					}		
					closeDialog(prossDialog);
					openConfirmDialog("该账户为"+type+"，是否重新输入。是：重新输入；否：退出。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							openPanel(lostPubBean.getThisComponent(), new SolveLostInputApplyNoPage());
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							serverStop(lostPubBean.getThisComponent(),"感谢您的使用，请收好您的随身物品。","","");
						}
					});
					return false;
				}

			} else {
				logger.error((String) map08236.get("errMsg"));
				closeDialog(prossDialog);
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop(lostPubBean.getThisComponent(),"黑名单查询失败",(String) map08236.get("errMsg"), "");
				return false;
			}
		} catch (Exception e) {
			logger.error("调用08236黑名单查询接口失败：" + e);
			closeDialog(prossDialog);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08236接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop(lostPubBean.getThisComponent(),"黑名单查询失败，请联系大堂经理","", "调用黑名单查询接口08236失败");
			return false;
		}
		return true;
	}
}
