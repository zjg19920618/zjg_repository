package com.boomhope.Bill.TransService.AccOpen.controller;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo1;
import com.boomhope.Bill.TransService.AccOpen.Bean.ProductRateInfo2;
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchProducRateInfo;
import com.boomhope.Bill.TransService.AccOpen.Bean.SearchRYCDetail;
import com.boomhope.Bill.TransService.AccOpen.Bean.accOpenProFileBean02808And03870;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.Util.Base64Util;
import com.boomhope.Bill.TransService.AccOpen.Util.HttpClientUtil;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccChooseBusiness;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccDepoLumPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccSuccessDepPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccountConfirmPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckAgentPhotos;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccCheckPhotos;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccDeputySelectionPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccFaceCheckFail;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccInputAgentIdCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccInputIdCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccPrintCameraPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PrivateLine.AccPrivateLinePanel3;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccGuanLianRycProPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccInputBankMsgPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccJXCSuccessPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccJxcInputPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccOkInputDepInfoPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccProInputPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccRedPacketPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccRyjInputPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccSubProPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel.AccSyrPagesPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProtocolDeposit.AccProtocolDepPanel3;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PublicProPanel.AccInputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PublicProPanel.AccInputBankCardPassword;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FloatRetUtil;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.bean.IdCardReadBean;

/**
 * 流程代码对应的逻辑处理
 * 
 * @author hk
 *
 */

@SuppressWarnings("serial")
public class AccountTradeCodeAction extends BaseTransPanelNew {

	public static AccPublicBean transBean;
	Logger logger = Logger.getLogger(getClass());
	
	// 判断返回信息若读卡成功打开密码页面
	// 若读卡失败进入业务终止页面
	public void ACC0000(Component thisComp, Map<String, Object> params) {
		// 进入读卡页面
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transCode", "0001");
		openPanel(thisComp, new AccInputBankCardPanel(map));
	}

	// 判断返回信息若读卡成功打开密码页面
	// 若读卡失败进入业务终止页面
	public void ACC0001(Component thisComp, Map<String, Object> params) {
		String resultCode = (String) params.get("resultCode");
		// 判断返回信息若读卡成功打开密码页面
		if ("0000".equals(resultCode)) {
			params.put("transCode", "0002");
			transBean.setCardNo((String) params.get("cardNo"));
			//判断插入卡Bin是个人还是单位卡，单位卡或者其他行卡无法办理此业务
			String cardNo = (String) params.get("cardNo");
			if(cardNo.startsWith("623193") || cardNo.startsWith("622167")){//个人卡
				openPanel(thisComp, new AccInputBankCardPassword(params));
				return;
			}else if(cardNo.startsWith("62326558")){//单位卡
				logger.info("单位卡不支持此业务，卡号："+cardNo);
				transBean.getReqMCM001().setIntereturnmsg("卡类型不支持该业务");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp,"抱歉，单位卡不支持此业务","", "");
				return;
			}else{//其他卡
				logger.info("该卡不支持此业务");
				logger.info("卡号："+cardNo);
				transBean.getReqMCM001().setIntereturnmsg("卡类型不支持该业务");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp,"抱歉，该卡不支持此业务","", "");
				return;
			}
		}
		// 若读卡失败进入业务终止页面
	}

	/**
	 * 获取密码失败进入服务终止页 获取密码成功 卡查询接口 若密码错误调错误信息提示弹框不跳页面，显示密码错误次数提示 证件信息查询 查询灰白名单
	 * 查询失败，或者返回不通过统一进服务终止页。
	 * 
	 * @param thisComp
	 * @param params
	 */

	public void ACC0002(final Component thisComp,final Map<String, Object> params) {
		transBean.setCardIsPin("1");// 卡插入时必须验密

		String resultCode = (String) params.get("resultCode");
		if ("0000".equals(resultCode)) {// 获取密码成功
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("transCode", "0003");
			transBean.setCardPwd((String) params.get("passWord"));
			// 查询卡信息
			try {
				prossDialog.showDialog();
				//设置监控平台流水上送接口调用前信息
				transBean.getReqMCM001().setReqBefor("03845");
				final Map<String, Object> reCardInfo = IntefaceSendMsg
						.inter03845(transBean);

				transBean.getReqMCM001().setReqAfter((String)reCardInfo.get("resCode"), (String)reCardInfo.get("errMsg"));
				if ("4444".equals(reCardInfo.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(reCardInfo.get("errMsg"));
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop(thisComp, "查询卡信息时出现异常，请稍后再试",
							(String) reCardInfo.get("errMsg"),"");
					return;
				} 
				
				if (!"000000".equals(reCardInfo.get("resCode"))) {
					if ("5555".equals(reCardInfo.get("resCode"))) {

						prossDialog.disposeDialog();
						logger.info(reCardInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp,(String) reCardInfo.get("errMsg"),"","");
						return;
					} else if (((String) reCardInfo.get("errMsg"))
							.startsWith("A102")) {

						prossDialog.disposeDialog();
						logger.error(((String) reCardInfo.get("errMsg")));
						openConfirmDialog("密码输入错误，是否重新输入。是：重新输入密码，否：退出业务。");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								params.put("resCode", reCardInfo.get("resCode"));
								params.put("errMsg", reCardInfo.get("errMsg"));
								openPanel(thisComp, new AccInputBankCardPassword(params));
								return;
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								params.put("resCode", reCardInfo.get("resCode"));
								params.put("errMsg", reCardInfo.get("errMsg"));
								openPanel(thisComp,new OutputBankCardPanel());
								return;
							}
						});
						return;
					} else if (((String) reCardInfo.get("errMsg"))
							.startsWith("A103")) {

						prossDialog.disposeDialog();
						logger.error(reCardInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "您的密码输入次数已达上限，卡已经锁死。",
								(String) reCardInfo.get("errMsg"),"");
						return;
					}else{
						prossDialog.disposeDialog();
						logger.error(reCardInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "卡信息查询失败",
								(String) reCardInfo.get("errMsg"),"");
						return;
					}
				}
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("调用03845卡信息查询失败");
				transBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "卡信息查询失败","", "调用03845接口异常!");
				return;
			}

			//查询账户信息状态
			try {
				transBean.getReqMCM001().setReqBefor("03521");
				Map inter03521 = IntefaceSendMsg.inter03521(transBean); 
				transBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"),(String)inter03521.get("errMsg"));
				if(!"000000".equals(inter03521.get("resCode"))){
					
					if("5555".equals(inter03521.get("resCode"))){
						
						prossDialog.disposeDialog();
						logger.info(inter03521.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp,(String) inter03521.get("errMsg"),"","");
						return; 
						
					}else if ("4444".equals(inter03521.get("resCode"))) {

						prossDialog.disposeDialog();
						logger.error(inter03521.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, (String) inter03521.get("errMsg"),"","");
						return;
						
					}else{
						
						prossDialog.disposeDialog();
						logger.info(inter03521.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "卡账户信息查询失败",(String) inter03521.get("errMsg"),"");
						return; 
					}
				}
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("查询账户信息失败(03521)："+e);
				transBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("查询账户信息失败，请联系大堂经理。", "", "调用账户信息查询接口失败（03521）");
				return;
			}
			
			//卡账户信息查询2-07601
			try {
				transBean.getReqMCM001().setReqBefor("07601");
				Map card07601 = IntefaceSendMsg.card07601(transBean); 
				transBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"),(String)card07601.get("errMsg"));
				if(!"000000".equals(card07601.get("resCode"))){
					
					if("5555".equals(card07601.get("resCode"))){
						
						prossDialog.disposeDialog();
						logger.info(card07601.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp,(String) card07601.get("errMsg"),"","");
						return; 
						
					}else if ("4444".equals(card07601.get("resCode"))) {

						prossDialog.disposeDialog();
						logger.error(card07601.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, (String) card07601.get("errMsg"),"","");
						return;
						
					}else{
						
						prossDialog.disposeDialog();
						logger.info(card07601.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "卡账户信息查询失败",(String) card07601.get("errMsg"),"");
						return; 
					}
				}
				
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("卡账户信息查询失败"+e);
				transBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用07601接口异常");
				return;
			}
			
			// 查询身份信息
			try {
				transBean.getReqMCM001().setReqBefor("03445");
				Map<String, Object> reCustInfo = IntefaceSendMsg
						.inter03445(transBean);
				transBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"),(String)reCustInfo.get("errMsg"));
				if ("4444".equals(reCustInfo.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.error(reCustInfo.get("errMsg"));
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					this.serverStop(thisComp, "身份证件信息查询失败",
							(String) reCustInfo.get("errMsg"),"");
					return;
				}

				if (!"000000".equals(reCustInfo.get("resCode"))) {
					prossDialog.disposeDialog();
					logger.error(reCustInfo.get("errMsg"));
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					this.serverStop(thisComp, "身份证件信息查询失败",
							(String) reCustInfo.get("errMsg"),"");
					return;
				}
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("调用03445身份证件信息查询失败");
				transBean.getReqMCM001().setIntereturnmsg("调用03445接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "证件信息查询失败","", "调用03445接口异常!");
				return;
			}
			// 查询黑灰名单
			try {
				transBean.getReqMCM001().setReqBefor("01597");
				Map<String, Object> reBlackInfo = IntefaceSendMsg
						.inter01597(transBean);
				transBean.getReqMCM001().setReqAfter((String)reBlackInfo.get("resCode"),(String)reBlackInfo.get("errMsg"));
				if (!"000000".equals(reBlackInfo.get("resCode"))) {
					
					if ("0010".equals(reBlackInfo.get("resCode"))) {
						
						prossDialog.disposeDialog();
						logger.error((String) reBlackInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						this.serverStop(thisComp,(String) reBlackInfo.get("errMsg"),
								"", "");
						return;
					} else if ("0020".equals(reBlackInfo.get("resCode"))) {
						
						prossDialog.disposeDialog();
						logger.error((String) reBlackInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						this.serverStop(thisComp,(String) reBlackInfo.get("errMsg"),
								"", "");
						return;
					} else if ("4444".equals(reBlackInfo.get("resCode"))) {
						
						prossDialog.disposeDialog();
						logger.error((String) reBlackInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						this.serverStop(thisComp, "查询黑灰名单信息失败",
								(String) reBlackInfo.get("errMsg"),"");
						return;
					}else{
						
						prossDialog.disposeDialog();
						logger.error((String) reBlackInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						this.serverStop(thisComp, "查询黑灰名单信息失败",
								(String) reBlackInfo.get("errMsg"),"");
						return;
					}
				}
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("调用01597查询黑灰名单信息失败");
				transBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "查询黑灰名单信息失败","", "调用01597接口异常!");
				return;
			}
			
			//根据证件信息查询出客户是否有相应的信息，然后进行客户的基本信息查询
			if("0018".equals(transBean.getSvrRetCode())){
				prossDialog.disposeDialog();
				transBean.getReqMCM001().setIntereturnmsg("未预留信息或尚未进行客户身份识别");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp , "您在我行尚未预留信息或尚未进行客户身份识别，请先联系大堂经理","","");
				return;
			}else if("0000".equals(transBean.getSvrRetCode())){
				//查询客户基本信息
				try {
					transBean.getReqMCM001().setReqBefor("04422");
					Map<String, String> reBlackInfo = IntefaceSendMsg.inter04422(transBean);
					transBean.getReqMCM001().setReqAfter((String)reBlackInfo.get("resCode"),(String)reBlackInfo.get("errMsg"));
					if(!"000000".equals(reBlackInfo.get("resCode"))){
						prossDialog.disposeDialog();
						logger.info("查询客户基本信息失败(04422)"+reBlackInfo.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "查询客户基本信息失败，请联系大堂经理", reBlackInfo.get("errMsg"), "");
						return;
					}
					if("1".equals(transBean.getInfoQualityFlag())){
						prossDialog.disposeDialog();
						transBean.getReqMCM001().setIntereturnmsg("反洗钱查询客户无相信信息");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "您在我行尚未预留信息或尚未进行客户身份识别，请先联系大堂经理.", "", "反洗钱查询客户无相应信息");
						return;
					}else if("0".equals(transBean.getNineMsg()) && "0".equals(transBean.getInfoQualityFlag())){
						openMistakeDialog("您在我行预留信息不完整，请尽快补充完善信息.");
					}
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("反洗钱客户基本信息查询异常(04422)"+e);
					transBean.getReqMCM001().setIntereturnmsg("调用04422接口异常");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop("查询客户基本信息失败，请联系大堂经理。", "", "调用客户基本信息查询接口异常(04422)");
					return;
				}
				
			}
			prossDialog.disposeDialog();
			openPanel(thisComp, new AccChooseBusiness(map));
		}
	}

	// 整存争取
	// 协议存款
	// 私行快线
	public void ACC0003(Component thisComp, Map<Object, Object> params) {
		prossDialog.showDialog();
		//第二笔或更多交易时调用开信息查询余额
		if(transBean.getCardAmt() == null || "".equals(transBean.getCardAmt())){
			try {
				transBean.getReqMCM001().setReqBefor("03845");
				final Map<String,String> map = IntefaceSendMsg.inter03845(transBean);
				transBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
				if ("4444".equals(map.get("resCode"))) {

					prossDialog.disposeDialog();
					logger.error(map.get("errMsg"));
					prossDialog.disposeDialog();
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop(thisComp, "查询卡信息时出现异常，请稍后再试",
							(String) map.get("errMsg"),"");
					return;
				} 
				
				if (!"000000".equals(map.get("resCode"))) {
					if ("9999".equals(map.get("resCode"))) {

						prossDialog.disposeDialog();
						logger.error(map.get("errMsg"));
						prossDialog.disposeDialog();
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "您的的卡已被冻结，请联系工作人员解冻",
								(String) map.get("errMsg"),"");
						return;
					}else if (((String) map.get("errMsg"))
							.startsWith("A103")) {

						prossDialog.disposeDialog();
						logger.error(map.get("errMsg"));
						prossDialog.disposeDialog();
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "您的密码输入次数已达上限，卡已经锁死。",
								(String) map.get("errMsg"),"");
						return;
					}else{
						prossDialog.disposeDialog();
						logger.error(map.get("errMsg"));
						prossDialog.disposeDialog();
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop(thisComp, "卡信息查询失败",
								(String) map.get("errMsg"),"");
						return;
					}
				}
			} catch (Exception e) {
				logger.error("查询卡相关信息异常："+e);
				prossDialog.disposeDialog();
				transBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop("查询卡的相关信息异常，请联系大堂经理。", "", "调用卡信息查询接口异常(03845)");
				return;
			}
		}
		
		String resultCode = (String) params.get("resultCode");
		if (!"0000".equals(resultCode)) {
			prossDialog.disposeDialog();
			return;
		}
		String productType = (String) params.get("productType");
		Map<String, Object> map = new HashMap<String, Object>();
		if ("ZCZQ".equals(productType)) {
			prossDialog.disposeDialog();
			openPanel(thisComp, new AccDepoLumPanel(transBean));
			return;
		}
		// 协议存款
		if ("1".equals(params.get("CHL_ID")) || "5".equals(params.get("CHL_ID"))) {
			params.put("transCode", "0016");
			prossDialog.disposeDialog();
			openPanel(thisComp, new AccProtocolDepPanel(params));
			return;
		}

		// 私行千禧
		if ("2".equals(params.get("CHL_ID"))) {
			try {
				transBean.getReqMCM001().setReqBefor("03524");
				Map<String,String> resultMap = IntefaceSendMsg.inter03524(transBean);
				transBean.getReqMCM001().setReqAfter((String)resultMap.get("resCode"), (String)resultMap.get("errMsg"));
				if(!"000000".equals(resultMap.get("resCode"))){
					logger.info("查询客户评级信息失败(03524):"+resultMap.get("errMsg"));
					prossDialog.disposeDialog();
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					serverStop(thisComp,"查询客户评级信息失败，请联系大堂经理。", resultMap.get("errMsg"),"");
					return;
				}
				
			} catch (Exception e) {
				logger.error("查询客户评级失败，调用接口（03524）");
				prossDialog.disposeDialog();
				transBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp,"查询客户评级失败，请联系大堂经理。", "", "调用查询客户评级接口异常(03524)");
				return;
			}
			
			params.put("transCode", "0016");
			prossDialog.disposeDialog();
			openPanel(thisComp, new AccPrivateLinePanel(params));
			return;
		}
	}
	
	
	// 选择金额和存期后产品利息试算
	// 判断金额是否大于5万大于于进入授权页面
	// 若不大于进入信息确认页面
	public void ACC0004(Component thisComp, Map<Object, Object> params) {
		if("0".equals(transBean.getTransChangeNo().trim())){
			openPanel(thisComp, new AccountConfirmPanel(transBean,params));
		}else if("2".equals(transBean.getTransChangeNo().trim())){
			params.put("transCode", "0015");
			ACC0006(thisComp,params);
		}else if("1".equals(transBean.getTransChangeNo().trim())){
			params.put("transCode", "0007");
			openPanel(thisComp, new AccDeputySelectionPanel(params));
		}
	}

	// 点击确认
	// 进入银行卡密码输入页
	public void ACC0005(Component thisComp, Map<String, Object> params) {
		params.put("transCode", "0015");
		params.put("cardNo", transBean.getCardNo());
		openPanel(thisComp, new AccInputBankCardPassword(params));
	}

	// 授权成功后进入存单信息确认页面
	public void ACC0006(Component thisComp, Map<Object, Object> params) {
		// 根据产品类型判断进入哪种信息确认页
		// 4 整存整取
		if ("4".equals(transBean.getProductType())) {
			openPanel(thisComp, new AccountConfirmPanel(transBean,params));
			// 协议、私行金额大于5万授权后信息确认页
		} else if (transBean.getProductCode().startsWith("AX")
				|| transBean.getProductCode().startsWith("LZ")
				|| transBean.getProductCode().startsWith("LT")) {
			if("1".equals(transBean.getIsCheckedPic())){
				openPanel(thisComp, new AccSyrPagesPanel(params));
				return;
			}
			openPanel(thisComp, new AccInputBankMsgPanel(params));
		} else {
			openPanel(thisComp, new AccOkInputDepInfoPanel(params));
		}
	}

	// 选择是或否都进入插入本账户对应的身份证
	@SuppressWarnings("unchecked")
	public void ACC0007(Component thisComp, Map<String, Object> params) {
		transBean.setHaveAgentFlag((String) params.get("haveAgentFlag"));
		params.put("idCardNo", transBean.getIdCardNo());
		openPanel(thisComp, new AccInputIdCardPanel(params));
	}

	// 读取身份证成功进行联网核查
	// 核查不通过进行是否再操作提示
	// 核查通过
	// 若本人办理 进入摄像头拍摄页面
	// 若代理人办理进入插入代理人身份证页面
	// 身份证读取失败，进入服务终止页
	public void ACC0008(Component thisComp,Map<String, Object> params){
		IdCardReadBean bean =(IdCardReadBean)params.get("cardInfo");
		transBean.setIdCardNo(bean.getIdCode());
		transBean.setIdCardName(bean.getName());
		transBean.setQfjg(bean.getIssuingUnit());
		transBean.setAddress(bean.getAddress());
		transBean.setSex(bean.getSex());
		transBean.setIdCardtitle(bean.getPhotoPath());
		transBean.setIdCardback(bean.getBackFace());
		transBean.setIdCardface(bean.getFrontFace());
		transBean.setBirthday(bean.getBirthday());
		transBean.setEndDate(bean.getEndDate());
		try {
			prossDialog.showDialog();
			transBean.getReqMCM001().setReqBefor("07670");
			Map reCustInfo=IntefaceSendMsg.inter07670(transBean);
			transBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"), (String)reCustInfo.get("errMsg"));
			if("4444".equals((String)reCustInfo.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)reCustInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp,"身份联网核查失败",(String)reCustInfo.get("errMsg"),"");
				return;
			}
			if(!"000000".equals(reCustInfo.get("resCode"))){
				prossDialog.disposeDialog();
				logger.error((String)reCustInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp,"身份联网核查失败",(String)reCustInfo.get("errMsg"),"");
				return;
			}
			transBean.setPhotoPath((String)reCustInfo.get("filePath"));
			transBean.setInspect((String)reCustInfo.get("CORE_RET_MSG"));
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用07670身份联网核查失败");
			transBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			this.serverStop(thisComp,"身份联网核查失败","","调用07670接口异常!");
			return;
		}
		if("0".equals(transBean.getHaveAgentFlag())){
			openPanel(thisComp, new AccInputAgentIdCardPanel(params));

		}else{
			openPanel(thisComp, new AccPrintCameraPanel(params));
		}
		prossDialog.disposeDialog();
	}

	// /读取身份证成功进行联网核查
	// 核查不通过进行是否再操作提示
	// 核查通过 进入摄像头拍摄页面
	// 身份证读取失败，进入服务终止页
	public void ACC0009(final Component thisComp,final Map<String, Object> params) {
		IdCardReadBean bean = (IdCardReadBean) params.get("cardInfo");
		transBean.setAgentIdCardNo(bean.getIdCode());
		transBean.setAgentIdCardName(bean.getName());
		transBean.setAgentqfjg(bean.getIssuingUnit());
		transBean.setAgentaddress(bean.getAddress());
		transBean.setAgentsex(bean.getSex());
		transBean.setAgentIdCardtitle(bean.getPhotoPath());
		transBean.setAgentIdCardback(bean.getBackFace());
		transBean.setAgentIdCardface(bean.getFrontFace());
		transBean.setAgentBirthday(bean.getBirthday());
		transBean.setAgentEndDate(bean.getEndDate());
		if(transBean.getAgentIdCardNo().equals(transBean.getIdCardNo())){
			openConfirmDialog("代理人不能为本人,是否继续。是：重新插入身份证，否：重新选择是否代理。");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					confirmDialog.disposeDialog();
					openPanel(thisComp,new AccInputAgentIdCardPanel(params));
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					confirmDialog.disposeDialog();
					openPanel(thisComp,new AccDeputySelectionPanel(params));
				}
			});
			return;
		}
		
		try {
			transBean.getReqMCM001().setReqBefor("08021");
			Map checkAgentInfo=IntefaceSendMsg.inter08021(transBean);
			transBean.getReqMCM001().setReqAfter((String)checkAgentInfo.get("resCode"), (String)checkAgentInfo.get("errMsg"));
			if ("4444".equals((String) checkAgentInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) checkAgentInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "核查代理人身份信息失败",
						(String) checkAgentInfo.get("errMsg"),"");
				return;
			}
			if (!"000000".equals(checkAgentInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) checkAgentInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "核查代理人身份信息失败",
						(String) checkAgentInfo.get("errMsg"),"");
				return;
			}
			if("0".equals(transBean.getAgentCheckResult())){
				openConfirmDialog("本行在职员工不能办理代理人业务,是否继续。是：重新插入身份证，否：重新选择是否代理。");
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						confirmDialog.disposeDialog();
						openPanel(thisComp,new AccInputAgentIdCardPanel(params));
					}
				});
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						confirmDialog.disposeDialog();
						openPanel(thisComp,new AccDeputySelectionPanel(params));
					}
				});
				return;
			}
			
		} catch (Exception e) {
			logger.error("查询代理人身份信息失败："+e);
			transBean.getReqMCM001().setIntereturnmsg("调用08021接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			serverStop(thisComp,"查询代理人身份信息失败", "","调用08021接口异常");
			return;
		}
		
		try {
			
			// 07670默认查的是本人身份，因此进行数据调换
			AccPublicBean pBean = new AccPublicBean();
			pBean.setIdCardNo(bean.getIdCode());
			pBean.setIdCardName(bean.getName()); 
			prossDialog.showDialog();
			transBean.getReqMCM001().setReqBefor("07670");
			Map reCustInfo = IntefaceSendMsg.inter07670(pBean);
			transBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"), (String)reCustInfo.get("errMsg"));
			
			if ("4444".equals((String) reCustInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) reCustInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "身份联网核查失败",
						(String) reCustInfo.get("errMsg"),"");
				return;
			}
			if (!"000000".equals(reCustInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) reCustInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "身份联网核查失败",
						(String) reCustInfo.get("errMsg"),"");
				return;
			}
			transBean.setAgentPhotoPath((String) reCustInfo.get("filePath"));
			transBean.setAgentinspect((String) reCustInfo.get("CORE_RET_MSG"));
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用07670身份联网核查失败");
			transBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			this.serverStop(thisComp, "身份联网核查失败","", "调用07670接口异常!");
			return;
		}
		openPanel(thisComp, new AccPrintCameraPanel(params));
	}

	// 拍摄成功，进入图片校验页
	// 拍摄失败进入服务终止页
	public void ACC0010(Component thisComp, Map<Object, Object> params) {
		openProssDialog();
		params.put("inspect", transBean.getInspect());
		params.put("photoPath", transBean.getPhotoPath());
		if ("0".equals(transBean.getHaveAgentFlag())) {
			params.put("agentinspect", transBean.getAgentinspect());
			params.put("agentPhotoPath", transBean.getAgentPhotoPath());
			try {
				logger.info("进行代理人人脸识别");
				String camera_path=null;
				if("1".equals(transBean.getIsReCamera())){
					camera_path=Property.getProperties().getProperty("re_camera_path");
				}else{
					camera_path=Property.getProperties().getProperty("camera_path");
				}
				boolean result = compareFaces(Property.getProperties().getProperty("bill_id_agent_face"),camera_path);
				if(result){
					if("1".equals(transBean.getIsReCamera())){
						FileUtil.copy(new File(Property.RE_CAMERA_PATH), new File(Property.CAMERA_PATH));
						transBean.setIsReCamera("");
					}
					prossDialog.disposeDialog();
					if("2".equals(transBean.getIsCheckedPic())){
						logger.info("进入联网核查页面");
						openPanel(thisComp,new AccCheckAgentPhotos(params));
					}else{
						logger.info("进入代理人信息核查页面");
						ACC0006(thisComp,params);
					}
				}else{
					logger.info("进入人脸识别失败页面");
					prossDialog.disposeDialog();
					openPanel(thisComp, new AccFaceCheckFail(transBean,params));
				}
			} catch (Exception e) {
				logger.error("人脸识别接口调用失败" + e);
				prossDialog.disposeDialog();
				transBean.getReqMCM001().setIntereturnmsg("人脸识别异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp, "人脸识别失败","", "人脸识别时异常:"+e);
			}
		} else {
			try {
				logger.info("进行本人人脸识别");
				String camera_path=null;
				if("1".equals(transBean.getIsReCamera())){
					camera_path=Property.getProperties().getProperty("re_camera_path");
				}else{
					camera_path=Property.getProperties().getProperty("camera_path");
				}
				boolean result = compareFaces(Property.getProperties().getProperty("bill_id_self_face"),camera_path);
				if(result){
					if("1".equals(transBean.getIsReCamera())){
						FileUtil.copy(new File(Property.RE_CAMERA_PATH), new File(Property.CAMERA_PATH));
						transBean.setIsReCamera("");
					}
					prossDialog.disposeDialog();
					if("2".equals(transBean.getIsCheckedPic())){
						logger.info("进入联网核查页面");
						openPanel(thisComp,new AccCheckPhotos(params));
					}else{
						logger.info("进入本人信息核查页面");
						ACC0006(thisComp,params);
					}
				}else{
					logger.info("进入人脸识别失败页面");
					prossDialog.disposeDialog();
					openPanel(thisComp, new AccFaceCheckFail(transBean,params));
				}
			} catch (Exception e) {
				logger.error("人脸识别接口调用失败" + e);
				prossDialog.disposeDialog();
				transBean.getReqMCM001().setIntereturnmsg("人脸识别异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				serverStop(thisComp, "人脸识别失败","", "人脸识别时异常:"+e);
			}
		}
	}


	// 密码输入成功
	// 子账户开户
	// 开户失败进入服务终止页

	// 唐豆查询
	// 唐豆兑付
	// 唐豆失败弹框提示
	public void ACC0015(Component thisComp, Map<String, Object> params) {
		Map<String, Object> reCardInfo = null;
		Map<String, Object> map = new HashMap<String, Object>();

		/* 开户 */
		try {
			prossDialog.showDialog();
			transBean.getReqMCM001().setReqBefor("03870");
			reCardInfo = IntefaceSendMsg.inter03870(transBean);
			transBean.getReqMCM001().setReqAfter((String)reCardInfo.get("resCode"), (String)reCardInfo.get("errMsg"));
			if ("4444".equals(reCardInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) reCardInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "开户失败",
						(String) reCardInfo.get("errMsg"),"");
				return;
			}
			if (!"000000".equals(reCardInfo.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error((String) reCardInfo.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				this.serverStop(thisComp, "开户失败",
						(String) reCardInfo.get("errMsg"),"");
				return;
			}
			//开户成功，设置上送信息
			transBean.getReqMCM001().setAuthemp1(transBean.getSupTellerNo());
			transBean.getReqMCM001().setAuthmethod1(transBean.getCheckMod());
			transBean.getReqMCM001().setCustomername(transBean.getIdCardName());
			transBean.getReqMCM001().setAccount(transBean.getCardNo());
			transBean.getReqMCM001().setLendirection("1");
			transBean.getReqMCM001().setTurnflag("1");
			transBean.getReqMCM001().setTransamt(String.format("%.2f", new Double(transBean.getMoney())).toString());
			transBean.getReqMCM001().setToaccount(transBean.getCardNo().trim()
					+((transBean.getSubAcctNo()!=null&&!"".equals(transBean.getSubAcctNo().trim()))?"-"+transBean.getSubAcctNo().trim():""));
			transBean.getReqMCM001().setTonouns(transBean.getIdCardName());
			transBean.getReqMCM001().setIntereturncode("0");
			transBean.getReqMCM001().setIntereturnmsg("开户成功");
			
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用03870开户失败："+e);
			transBean.getReqMCM001().setIntereturnmsg("调用03870接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			this.serverStop(thisComp, "开户失败", "","调用03870接口异常!");
			return;
		}

		/* 当产品为约享存时，计算窗口期 */
		if ("YA".equals(params.get("PRODUCT_CODE"))
				|| "YB".equals(params.get("PRODUCT_CODE"))
				|| "YC".equals(params.get("PRODUCT_CODE"))) {
			yxRate(thisComp);
		}

		/* 当产品为安享存时，计算窗口期 */
		if ("AX".equals(params.get("PRODUCT_CODE"))) {
			axRate(thisComp);
		}

		/* 只有协议和私行的幸福1+1才进行唐豆查询和兑付*/
		if ("0010".equals(transBean.getProductCode()) || ("XF".equals(params.get("PRODUCT_CODE")) && ("1".equals(params.get("CHL_ID")) || "2".equals(params.get("CHL_ID"))))){
			// 唐豆规则查询
			try {
				Map<String, String> reCustInfo = new HashMap<String, String>();
				prossDialog.showDialog();
				reCustInfo = IntefaceSendMsg.inter07506(transBean);
				params.put("errMsg", reCustInfo.get("errMsg"));
				if (!"000000".equals(reCustInfo.get("resCode"))) {
					logger.info("查询唐豆信息异常：" + reCustInfo.get("errMsg"));
				}
				prossDialog.disposeDialog();
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("调用07506唐豆派发异常：" + e);

			}
			// 唐豆兑付
			try {
				prossDialog.showDialog();
				if (!((String) params.get("errMsg")).startsWith("D999")
						&& transBean.getCount() != null
						&& !transBean.getCount().equals("")
						&& Double.parseDouble(transBean.getCount()) > 0) {
					Map<String, Object> reBlackInfo = IntefaceSendMsg.inter07505(transBean);
					transBean.setIsHonour("0");// 糖豆兑付成功
					if (!"000000".equals(reBlackInfo.get("resCode"))) {
						transBean.setIsHonour("1");// 糖豆兑付失败
						logger.info("唐豆兑付失败" + transBean);
					}
				}
				prossDialog.disposeDialog();
			} catch (Exception e) {
				prossDialog.disposeDialog();
				logger.error("调用07505唐豆兑付异常：" + e);

			}
		}

		List<accOpenProFileBean02808And03870> listTD = (List<accOpenProFileBean02808And03870>)reCardInfo.get("ZYD_FILE");
		if(listTD!=null && listTD.size()!=0){
			for(int i=0;i<listTD.size();i++){
				accOpenProFileBean02808And03870 afa = listTD.get(i);
				if("008".equals(afa.getDerivativeType())){
					logger.info("查询到增益豆信息");
					//如果预付标志位0，则增益豆数量为执行浮动值，如果为1，则为预付数量
					if("0".equals(afa.getPayMark())){
						transBean.setZydCount(String.format("%.2f", new Double(afa.getDoFloatVal())).toString());
					}else{
						transBean.setZydCount(String.format("%.2f", new Double(afa.getPayNums())).toString());
					}
				}else if("003".equals(afa.getDerivativeType())){
					logger.info("查询到增益豆信息");
					//如果预付标志位0，则增益豆数量为执行浮动值，如果为1，则为预付数量
					if("0".equals(afa.getPayMark())){
						transBean.setXfdCount(String.format("%.2f", new Double(afa.getDoFloatVal())).toString());
					}else{
						transBean.setXfdCount(String.format("%.2f", new Double(afa.getPayNums())).toString());
					}
				}
			}
		}else{
			logger.info("无唐豆信息");
		}
		
		//计算唐豆总数（幸运豆+增益豆+消费豆）
		Double tdCount = 0.00;
		if("yes".equals(reCardInfo.get("hasJxq"))){
			tdCount+=new Double(String.valueOf(reCardInfo.get("jxqAmt")));
		}
		if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
			tdCount+=Double.valueOf(transBean.getZydCount());
		}
		if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
			tdCount+=Double.valueOf(transBean.getXfdCount());
		}
		transBean.setTdTotalCount(tdCount.toString().trim());
		transBean.getReqMCM001().setGrantpea(String.format("%.2f", tdCount).toString().trim());
		if ("yes".equals(reCardInfo.get("hasJxq"))) {
			// 有加息券跳转红包图片显示页面
			transBean.setIntMoney(String.valueOf(reCardInfo.get("jxqAmt")));
			
//			try {
//				Map inter07523 = IntefaceSendMsg.inter07523(transBean);
//				if(!"000000".equals(inter07523.get("resCode"))){
//					logger.info("查询幸运豆交易流水号失败："+inter07523.get("errMsg"));
//				}else{
//					List<SearchXYDSvrNoInfo> listXYD=(List<SearchXYDSvrNoInfo>)inter07523.get("XYD_SVR_NO");
//					if(listXYD.size()!=0 && listXYD!=null){
//						SearchXYDSvrNoInfo subInfo=listXYD.get(0);
//						transBean.setIntSvrNo(subInfo.getIntSvrNo());
//					}else{
//						logger.info("未查询到幸运豆的交易流水号，查询流水号文件长度："+listXYD.size());
//					}
//				}
//			} catch (Exception e) {
//				logger.error("查询幸运豆交易流水号失败(07523)："+e);
//			}
			
			openPanel(thisComp, new AccRedPacketPanel(params));

		} else {
			// 跳转是否打印存单
			if ("JX".equals(params.get("PRODUCT_CODE"))) {
				// 积享不打印存单，跳转积享存存入成功页面
				openPanel(thisComp, new AccJXCSuccessPanel(transBean,params));
			} else {
				// 调存单打印
				openPanel(thisComp, new AccSuccessDepPanel(transBean,params));
			}
		}
		return;
	}

	/**
	 * 协议、私行产品查询
	 * 
	 * @param thisComp
	 * @param params
	 */
	public void ACC0016(final Component thisComp, Map<Object, Object> params) {
		List<ProductRateInfo1> searchProduct = null;
		Map inter02867 = new HashMap();
		params.put("accBean", transBean);
		String minAmt = "";// 最低起存金额
		String maxAmt = "";// 最高起存金额
		String chlId = "";// 渠道标识
		String proCode = "";// 产品代码
		if ("1".equals(params.get("CHL_ID"))) {// 协议
			minAmt = "0";
			maxAmt = "500000";
		} else if ("2".equals(params.get("CHL_ID"))) {// 私行
			minAmt = "500000";
			if("1".equals(transBean.getCustLevel().trim()) 
				|| "2".equals(transBean.getCustLevel().trim())
				|| "3".equals(transBean.getCustLevel().trim())){
				minAmt = "0";
			}
			maxAmt = "";
			chlId = "3";
		}else if("5".equals(params.get("CHL_ID"))){
			chlId = "5";
		}
		if ("QX".equals(params.get("PRODUCT_CODE"))) {// 千禧
			proCode = "QX";
		} else if ("XF".equals(params.get("PRODUCT_CODE"))) {// 幸福
			proCode = "XF";
		} else if ("RY".equals(params.get("PRODUCT_CODE"))) {// 如意
			proCode = "RY";
		} else if ("AX".equals(params.get("PRODUCT_CODE"))) {// 安享
			proCode = "AX";
		} else if ("LT".equals(params.get("PRODUCT_CODE"))) {// 立得他享
			proCode = "LT";
		} else if ("LZ".equals(params.get("PRODUCT_CODE"))) {// 立得自享
			proCode = "LZ";
		} else if ("YA".equals(params.get("PRODUCT_CODE"))) {// 约享A系列
			proCode = "YA";
		} else if ("YB".equals(params.get("PRODUCT_CODE"))) {// 约享B系列
			proCode = "YB";
		} else if ("YC".equals(params.get("PRODUCT_CODE"))) {// 约享C系列
			proCode = "YC";
		}
		transBean.setProductType("1");// 查询类型
		transBean.setProductCode(proCode);// 产品代码
		transBean.setMinAmt(minAmt);// 最低起存金额
		transBean.setMaxAmt(maxAmt);// 最高起存金额
		transBean.setChlId(chlId);
		logger.info("开始调用产品利率信息查询—02867");
		try {
			prossDialog.showDialog();
			// 02867接口查询产品
			transBean.getReqMCM001().setReqBefor("02867");
			inter02867 = IntefaceSendMsg.inter02867(transBean);
			searchProduct = (List<ProductRateInfo1>) inter02867
					.get(IntefaceSendMsg.KEY_PRODUCT_RATES);
			transBean.getReqMCM001().setReqAfter((String)inter02867.get("resCode"), (String)inter02867.get("errMsg"));

			if (null == searchProduct || searchProduct.size() == 0) {
				prossDialog.disposeDialog();
				logger.error("调用接口02867产品利率信息查询，文件返回数据为空");
				transBean.getReqMCM001().setIntereturnmsg("返回文件为空");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 弹框提醒文件为空
				serverStop(thisComp, "抱歉，查无此产品信息!","",
						"文件返回数据为空");
				return;
			}

			if (!"000000".equals(inter02867.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error(inter02867.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 跳转错误页面
				serverStop(thisComp, "抱歉，查询子产品信息失败",
						(String) inter02867.get("errMsg"),"");
				return;
			}
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用02867接口异常!");
			transBean.getReqMCM001().setIntereturnmsg("调用02867接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			// 跳转错误页面
			serverStop(thisComp, "抱歉，查询子产品信息失败","", "调用02867接口异常!");
			return;
		}
		logger.info("02867产品利率信息查询成功");
		params.put("02867List", searchProduct);
		openPanel(thisComp, new AccSubProPanel(params));

	}

	/**
	 * 查询如意存+、积享存子产品(如意存明细查询)
	 * 
	 * @param thisComp
	 * @param params
	 */
	public void ACC0017(Component thisComp,Map<Object, Object> params) {
		List<SearchRYCDetail> list = null;
		Map inter03512 = new HashMap();
		try {
			prossDialog.showDialog();
			transBean.getReqMCM001().setReqBefor("03512");
			inter03512 = IntefaceSendMsg.inter03512(transBean);
			list = (List<SearchRYCDetail>) inter03512
					.get(IntefaceSendMsg.PRODUCT_INFOS);
			transBean.getReqMCM001().setReqAfter((String)inter03512.get("resCode"), (String)inter03512.get("errMsg"));
			if ("4444".equals(inter03512.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error(inter03512.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 跳转错误页面
				serverStop(thisComp, "抱歉，查询关联如意存产品信息失败",
						(String) inter03512.get("errMsg"),"");
				return;
			}
			if (!"000000".equals(inter03512.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error(inter03512.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 跳转错误页面
				serverStop(thisComp, "抱歉，查询关联如意存产品信息失败",
						(String) inter03512.get("errMsg"),"");
				return;
			}
			if (null == list) {
				prossDialog.disposeDialog();
				logger.error("调用接口03512如意存明细查询，文件返回数据为空");
				// 弹框提醒文件为空
				transBean.setHaveRelaAcc("1");
				openMikMsg("抱歉，查无此关联产品信息",thisComp,params);
				return;
			}

			if (list.size() == 0) {// 提示信息
				prossDialog.disposeDialog();
				logger.error("请先购买如意存产品!");
				// 购买如意存提示
				transBean.setHaveRelaAcc("1");
				openMikMsg("请先购买关联的如意存产品!",thisComp,params);
				return;
			}
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用03512接口异常!");
			transBean.getReqMCM001().setIntereturnmsg("调用03512接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			// 跳转错误页面
			serverStop(thisComp, "抱歉，查询关联如意存产品信息失败", "","调用03512接口异常!");
			return;
		}

		List<SearchRYCDetail> searchRYCDetail2 = new ArrayList<SearchRYCDetail>();
		if ("1".equals(params.get("CHL_ID"))) {// 协议
			// 协议如意+的关联如意存产品必须大于等于5万，小于50万才可购买
			if ("RJ".equals(params.get("PRODUCT_CODE"))) {
				for (SearchRYCDetail searchRYCDetail : list) {
					Float money = Float.valueOf(searchRYCDetail.getBalance());
					if (money >= 50000 && money < 500000) {
						logger.info("协议如意+的关联如意存产品(>=5万，<500000.00)"
								+ searchRYCDetail.getProductCodeName());
						logger.info("金额" + searchRYCDetail.getBalance());
						SearchRYCDetail rycj = new SearchRYCDetail();
						rycj.setAcctNo(searchRYCDetail.getAcctNo());
						rycj.setBalance(searchRYCDetail.getBalance());
						rycj.setDepTerm(searchRYCDetail.getDepTerm());
						rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
						rycj.setEndDate(searchRYCDetail.getEndDate());
						rycj.setOpenDate(searchRYCDetail.getOpenDate());
						rycj.setOpenInst(searchRYCDetail.getOpenInst());
						rycj.setProductCode(searchRYCDetail.getProductCode());
						rycj.setProductCodeName(searchRYCDetail
								.getProductCodeName());
						rycj.setStartDate(searchRYCDetail.getStartDate());
						rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
						searchRYCDetail2.add(rycj);
					}
				}
				if (searchRYCDetail2.size() == 0) {
					// 请先购买如意存产品
					transBean.setHaveRelaAcc("1");
					openMikMsg("请先购买关联的如意存产品!",thisComp,params);
					return;
				}
				params.put("03512List", searchRYCDetail2);
			} else {
				// 协议积享的关联如意存产品1-1000万都可购买
				for (SearchRYCDetail searchRYCDetail : list) {
					logger.info("协议积享的关联如意存产品(1-1000万)"
							+ searchRYCDetail.getProductCodeName());
					logger.info("金额" + searchRYCDetail.getBalance());
					SearchRYCDetail rycj = new SearchRYCDetail();
					rycj.setAcctNo(searchRYCDetail.getAcctNo());
					rycj.setBalance(searchRYCDetail.getBalance());
					rycj.setDepTerm(searchRYCDetail.getDepTerm());
					rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
					rycj.setEndDate(searchRYCDetail.getEndDate());
					rycj.setOpenDate(searchRYCDetail.getOpenDate());
					rycj.setOpenInst(searchRYCDetail.getOpenInst());
					rycj.setProductCode(searchRYCDetail.getProductCode());
					rycj.setProductCodeName(searchRYCDetail
							.getProductCodeName());
					rycj.setStartDate(searchRYCDetail.getStartDate());
					rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
					searchRYCDetail2.add(rycj);	
				}
				if (searchRYCDetail2.size() == 0) {
					// 请先购买如意存产品
					transBean.setHaveRelaAcc("1");
					openMikMsg("请先购买关联的如意存产品!",thisComp,params);
					return;
				}
				params.put("03512List", searchRYCDetail2);
			}
		} else if("5".equals(params.get("CHL_ID"))){//普惠产品
			for(SearchRYCDetail searchRYCDetail : list){
				Float money = Float.valueOf(searchRYCDetail.getBalance());
				if ("RJ".equals(params.get("PRODUCT_CODE"))) {
					//如意+5万-1000万
					if(money >= 50000){
						logger.info("普惠如意+产品(5万-1000万)"
								+ searchRYCDetail.getProductCodeName());
						logger.info("金额" + searchRYCDetail.getBalance());
						SearchRYCDetail rycj = new SearchRYCDetail();
						rycj.setAcctNo(searchRYCDetail.getAcctNo());
						rycj.setBalance(searchRYCDetail.getBalance());
						rycj.setDepTerm(searchRYCDetail.getDepTerm());
						rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
						rycj.setEndDate(searchRYCDetail.getEndDate());
						rycj.setOpenDate(searchRYCDetail.getOpenDate());
						rycj.setOpenInst(searchRYCDetail.getOpenInst());
						rycj.setProductCode(searchRYCDetail.getProductCode());
						rycj.setProductCodeName(searchRYCDetail
								.getProductCodeName());
						rycj.setStartDate(searchRYCDetail.getStartDate());
						rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
						searchRYCDetail2.add(rycj);
					}
				}else{
					//积享存1-1000万
					logger.info("普惠积享产品(1-1000万)"
							+ searchRYCDetail.getProductCodeName());
					logger.info("金额" + searchRYCDetail.getBalance());
					SearchRYCDetail rycj = new SearchRYCDetail();
					rycj.setAcctNo(searchRYCDetail.getAcctNo());
					rycj.setBalance(searchRYCDetail.getBalance());
					rycj.setDepTerm(searchRYCDetail.getDepTerm());
					rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
					rycj.setEndDate(searchRYCDetail.getEndDate());
					rycj.setOpenDate(searchRYCDetail.getOpenDate());
					rycj.setOpenInst(searchRYCDetail.getOpenInst());
					rycj.setProductCode(searchRYCDetail.getProductCode());
					rycj.setProductCodeName(searchRYCDetail
							.getProductCodeName());
					rycj.setStartDate(searchRYCDetail.getStartDate());
					rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
					searchRYCDetail2.add(rycj);
				}
			}
			if (searchRYCDetail2.size() == 0) {
				// 请先购买如意存产品
				transBean.setHaveRelaAcc("1");
				openMikMsg("请先购买关联的如意存产品!",thisComp,params);
				return;
			}
			params.put("03512List", searchRYCDetail2);
		}else {
			// 私行产品
			for (SearchRYCDetail searchRYCDetail : list) {
				Float money = Float.valueOf(searchRYCDetail.getBalance());
				//私行级别客户
				if("1".equals(transBean.getCustLevel().trim()) || "2".equals(transBean.getCustLevel().trim()) || "3".equals(transBean.getCustLevel().trim())){
					if ("RJ".equals(params.get("PRODUCT_CODE"))) {
						//私行级别客户如意+5万-1000万
						if(money >= 50000){
							logger.info("私行级别客户如意+产品(5万-1000万)"
									+ searchRYCDetail.getProductCodeName());
							logger.info("金额" + searchRYCDetail.getBalance());
							SearchRYCDetail rycj = new SearchRYCDetail();
							rycj.setAcctNo(searchRYCDetail.getAcctNo());
							rycj.setBalance(searchRYCDetail.getBalance());
							rycj.setDepTerm(searchRYCDetail.getDepTerm());
							rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
							rycj.setEndDate(searchRYCDetail.getEndDate());
							rycj.setOpenDate(searchRYCDetail.getOpenDate());
							rycj.setOpenInst(searchRYCDetail.getOpenInst());
							rycj.setProductCode(searchRYCDetail.getProductCode());
							rycj.setProductCodeName(searchRYCDetail
									.getProductCodeName());
							rycj.setStartDate(searchRYCDetail.getStartDate());
							rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
							searchRYCDetail2.add(rycj);
						}
					}else{
						//私行级别客户积享存1-1000万
						logger.info("私行级别客户积享产品(1-1000万)"
								+ searchRYCDetail.getProductCodeName());
						logger.info("金额" + searchRYCDetail.getBalance());
						SearchRYCDetail rycj = new SearchRYCDetail();
						rycj.setAcctNo(searchRYCDetail.getAcctNo());
						rycj.setBalance(searchRYCDetail.getBalance());
						rycj.setDepTerm(searchRYCDetail.getDepTerm());
						rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
						rycj.setEndDate(searchRYCDetail.getEndDate());
						rycj.setOpenDate(searchRYCDetail.getOpenDate());
						rycj.setOpenInst(searchRYCDetail.getOpenInst());
						rycj.setProductCode(searchRYCDetail.getProductCode());
						rycj.setProductCodeName(searchRYCDetail
								.getProductCodeName());
						rycj.setStartDate(searchRYCDetail.getStartDate());
						rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
						searchRYCDetail2.add(rycj);
					}
					
				//普通级别客户如意+、积享存50-1000万
				}else{
					if ("RJ".equals(params.get("PRODUCT_CODE"))) {
						if (money >= 500000){
							logger.info("私行如意+产品(50万-1000万)"
									+ searchRYCDetail.getProductCodeName());
							logger.info("金额" + searchRYCDetail.getBalance());
							SearchRYCDetail rycj = new SearchRYCDetail();
							rycj.setAcctNo(searchRYCDetail.getAcctNo());
							rycj.setBalance(searchRYCDetail.getBalance());
							rycj.setDepTerm(searchRYCDetail.getDepTerm());
							rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
							rycj.setEndDate(searchRYCDetail.getEndDate());
							rycj.setOpenDate(searchRYCDetail.getOpenDate());
							rycj.setOpenInst(searchRYCDetail.getOpenInst());
							rycj.setProductCode(searchRYCDetail.getProductCode());
							rycj.setProductCodeName(searchRYCDetail
									.getProductCodeName());
							rycj.setStartDate(searchRYCDetail.getStartDate());
							rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
							searchRYCDetail2.add(rycj);
						}
						
					}else{
						logger.info("私行积享产品(1万-1000万)"
								+ searchRYCDetail.getProductCodeName());
						logger.info("金额" + searchRYCDetail.getBalance());
						SearchRYCDetail rycj = new SearchRYCDetail();
						rycj.setAcctNo(searchRYCDetail.getAcctNo());
						rycj.setBalance(searchRYCDetail.getBalance());
						rycj.setDepTerm(searchRYCDetail.getDepTerm());
						rycj.setDepTermStr(searchRYCDetail.getDepTermStr());
						rycj.setEndDate(searchRYCDetail.getEndDate());
						rycj.setOpenDate(searchRYCDetail.getOpenDate());
						rycj.setOpenInst(searchRYCDetail.getOpenInst());
						rycj.setProductCode(searchRYCDetail.getProductCode());
						rycj.setProductCodeName(searchRYCDetail
								.getProductCodeName());
						rycj.setStartDate(searchRYCDetail.getStartDate());
						rycj.setSubAcctNo(searchRYCDetail.getSubAcctNo());
						searchRYCDetail2.add(rycj);
					}
				}
			}
			if (searchRYCDetail2.size() == 0) {
				// 请先购买如意存产品
				transBean.setHaveRelaAcc("1");
				openMikMsg("请先购买关联的如意存产品!",thisComp,params);
				return;
			}
			params.put("03512List", searchRYCDetail2);
		}
		openPanel(thisComp, new AccGuanLianRycProPanel(params));
	}

	/**
	 * 请先购买如意存产品（弹框提示信息）
	 */
	private void openMikMsg(String msg,final Component thisComp, final Map<Object, Object> params){
		openMistakeDialog(msg);
		mistakeDialog.ExitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				closeDialog(mistakeDialog);
				if ("1".equals(params.get("CHL_ID")) || "5".equals(params.get("CHL_ID"))) {// 协议/普惠
					openPanel(thisComp, new AccProtocolDepPanel3(params));
				}
				if ("2".equals(params.get("CHL_ID"))) {// 私行
					openPanel(thisComp, new AccPrivateLinePanel3(params));
				}
			}

		});
	}
	/**
	 * 产品录入信息查询
	 * 
	 * @param thisComp
	 * @param params
	 */
	public void ACC0020(Component thisComp, Map<Object, Object> params) {
		List<ProductRateInfo2> searchProduct;
		Map inter02867 = new HashMap<>();
		transBean.setProductType("3");// 查询类型

		if ("1".equals(params.get("CHL_ID"))) {// 协议
			transBean.setMinAmt("0");// 最低起存金额
			transBean.setMaxAmt("500000");// 最高起存金额
		}
		if ("2".equals(params.get("CHL_ID"))) {// 私行
			transBean.setChlId("3");
			transBean.setMinAmt("500000");// 最低起存金额
			if("1".equals(transBean.getCustLevel().trim()) 
					|| "2".equals(transBean.getCustLevel().trim())
					|| "3".equals(transBean.getCustLevel().trim())){
				transBean.setMinAmt("0");
				}
			transBean.setMaxAmt("");// 最高起存金额
		}
		
		if ("JX".equals(params.get("PRODUCT_CODE"))) {
			transBean.setProductCode(transBean.getProductCode().replace("RY",
					"JX"));// 产品代码
			if ("1".equals(params.get("CHL_ID"))) {// 协议
				transBean.setMinAmt("0");// 最低起存金额
				transBean.setMaxAmt("500000");// 最高起存金额
			}
			if ("2".equals(params.get("CHL_ID"))) {// 私行
				transBean.setChlId("3");
				transBean.setMinAmt("0");
				transBean.setMaxAmt("");// 最高起存金额
			}
			
		} else if ("RJ".equals(params.get("PRODUCT_CODE"))) {
			
			transBean.setProductCode(transBean.getProductCode().replace("RY",
					"RJ"));// 产品代码
			if ("1".equals(params.get("CHL_ID"))) {// 协议
				transBean.setMinAmt("0");// 最低起存金额
				transBean.setMaxAmt("500000");// 最高起存金额
			}
			if ("2".equals(params.get("CHL_ID"))) {// 私行
				transBean.setChlId("3");
				transBean.setMinAmt("0");
				transBean.setMaxAmt("");// 最高起存金额
			}
		} else {
			
		}

		try {
			prossDialog.showDialog();
			transBean.getReqMCM001().setReqBefor("02867");
			inter02867 = IntefaceSendMsg.inter02867(transBean);
			searchProduct = (List<ProductRateInfo2>) inter02867
					.get(IntefaceSendMsg.KEY_PRODUCT_RATES);
			transBean.getReqMCM001().setReqAfter((String)inter02867.get("resCode"), (String)inter02867.get("errMsg"));
			if (null == searchProduct || searchProduct.size() == 0) {
				prossDialog.disposeDialog();
				logger.error("调用接口02867产品利率信息查询，文件返回数据为空");
				transBean.getReqMCM001().setIntereturnmsg("返回文件为空");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 弹框提醒文件为空
				serverStop(thisComp, "抱歉，查无此产品信息","",
						"文件返回数据为空");
				return;
			}
			if (!"000000".equals(inter02867.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error(inter02867.get("errMsg"));
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
				// 跳转错误页面
				serverStop(thisComp, "抱歉，查询子产品信息失败",
						(String) inter02867.get("errMsg"),"");
				return;
			}
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用02867接口异常!");
			transBean.getReqMCM001().setIntereturnmsg("调用02867接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
			// 跳转错误页面
			serverStop(thisComp, "抱歉，查询子产品信息失败","", "调用02867接口异常!");
			return;
		}

		//如果是千禧的私行级别和普惠开户这需要进行第二次查询
		if("QX".equals(params.get("PRODUCT_CODE"))){
			if(("2".equals(params.get("CHL_ID"))&&("1".equals(transBean.getCustLevel().trim())||
					"2".equals(transBean.getCustLevel().trim())||
					"3".equals(transBean.getCustLevel().trim())))||
					"5".equals(params.get("CHL_ID"))){
				if("QXC".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXK");
				}else if("QXK".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXC");
				}else if("QXD".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXN");
				}else if("QXN".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXD");
				}else if("QXE".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXJ");
				}else if("QXJ".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXE");
				}else if("QXF".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXI");
				}else if("QXI".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXF");
				}else if("QXG".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXL");
				}else if("QXL".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXG");
				}else if("QXH".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXM");
				}else if("QXM".equals(transBean.getProductCode().trim())){
					transBean.setProductCode("QXH");
				}
				
				try {
					prossDialog.showDialog();
					transBean.getReqMCM001().setReqBefor("02867");
					Map inter02867new = IntefaceSendMsg.inter02867(transBean);
					List<ProductRateInfo2> searchProductNew = (List<ProductRateInfo2>) inter02867new
							.get(IntefaceSendMsg.KEY_PRODUCT_RATES);
					transBean.getReqMCM001().setReqAfter((String)inter02867.get("resCode"), (String)inter02867.get("errMsg"));
					if (null == searchProductNew || searchProductNew.size() == 0) {
						prossDialog.disposeDialog();
						logger.error("调用接口02867产品利率信息查询，文件返回数据为空");
						transBean.getReqMCM001().setIntereturnmsg("返回文件数据为空");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						// 弹框提醒文件为空
						serverStop(thisComp, "抱歉，查无此产品信息","",
								"文件返回数据为空");
						return;
					}
					if (!"000000".equals(inter02867.get("resCode"))) {
						prossDialog.disposeDialog();
						logger.error(inter02867.get("errMsg"));
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						// 跳转错误页面
						serverStop(thisComp, "抱歉，查询子产品信息失败",
								(String) inter02867.get("errMsg"),"");
						return;
					}
					searchProduct.addAll(searchProductNew);
					prossDialog.disposeDialog();
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("调用02867接口异常!");
					transBean.getReqMCM001().setIntereturnmsg("调用02867接口异常");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					// 跳转错误页面
					serverStop(thisComp, "抱歉，查询子产品信息失败","", "调用02867接口异常!");
					return;
				}
			}
		}
		
		// 协议增加1-4万
		if ("1".equals(params.get("CHL_ID")) || "5".equals(params.get("CHL_ID"))) {
			List<ProductRateInfo2> productInfos = new ArrayList<ProductRateInfo2>();
			if("JX".equals(params.get("PRODUCT_CODE")) || "RJ".equals(params.get("PRODUCT_CODE"))){
				for (ProductRateInfo2 productrateinfo2 : searchProduct) {
					if ("10000.00".equals(productrateinfo2.getStartMoney())) {
						for (int i = 0; i < 4; i++) {
							ProductRateInfo2 productinfo = new ProductRateInfo2();
							productinfo.setInterestRate(productrateinfo2
									.getInterestRate());
							productinfo.setIsPrint(productrateinfo2.getIsPrint());
							productinfo.setMinimumBalance(productrateinfo2
									.getMinimumBalance());
							productinfo.setProductCode(productrateinfo2
									.getProductCode());
							productinfo.setProductCodeName(productrateinfo2
									.getProductCodeName());
							productinfo.setSavingCount(productrateinfo2
									.getSavingCount());
							productinfo.setSubProductCode(productrateinfo2
									.getSubProductCode());
							productinfo.setSubProductName(productrateinfo2
									.getSubProductName());
							productinfo.setVoucherType(productrateinfo2
									.getVoucherType());
							float startMoney = Float.parseFloat(productrateinfo2
									.getStartMoney());
							productinfo.setStartMoney((String.valueOf(startMoney
									+ i * 10000) + "0").trim());
							productinfo.setEndMoney(String.valueOf(productrateinfo2
									.getEndMoney()));
							productInfos.add(productinfo);
						}
					} else {
						productInfos.add(productrateinfo2);
					}
				}
			}else{
				for(int i=0;i<searchProduct.size()+3;i++){
					productInfos.add(null);
				}
				for (ProductRateInfo2 productrateinfo2 : searchProduct) {
					if ("10000.00".equals(productrateinfo2.getStartMoney())) {
						for (int i = 0; i < 4; i++) {
							ProductRateInfo2 productinfo = new ProductRateInfo2();
							productinfo.setInterestRate(productrateinfo2
									.getInterestRate());
							productinfo.setIsPrint(productrateinfo2.getIsPrint());
							productinfo.setMinimumBalance(productrateinfo2
									.getMinimumBalance());
							productinfo.setProductCode(productrateinfo2
									.getProductCode());
							productinfo.setProductCodeName(productrateinfo2
									.getProductCodeName());
							productinfo.setSavingCount(productrateinfo2
									.getSavingCount());
							productinfo.setSubProductCode(productrateinfo2
									.getSubProductCode());
							productinfo.setSubProductName(productrateinfo2
									.getSubProductName());
							productinfo.setVoucherType(productrateinfo2
									.getVoucherType());
							float startMoney = Float.parseFloat(productrateinfo2
									.getStartMoney());
							productinfo.setStartMoney((String.valueOf(startMoney
									+ i * 10000) + "0").trim());
							productinfo.setEndMoney(String.valueOf(productrateinfo2
									.getEndMoney()));
							productInfos.set(i,productinfo);
						}
					} else {
						if("50000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(4,productrateinfo2);
						}else if("150000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(5,productrateinfo2);
						}else if("300000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(6,productrateinfo2);
						}else if("500000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(7,productrateinfo2);
						}else if("1000000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(8,productrateinfo2);
						}else if("5000000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(9,productrateinfo2);
						}else if("10000000.00".equals(productrateinfo2.getStartMoney())){
							productInfos.set(10,productrateinfo2);
						}
					}
				}
			}
			params.put(IntefaceSendMsg.PRODUCT_INFOS, productInfos);

		} else if ("2".equals(params.get("CHL_ID"))) {
			List<ProductRateInfo2> productInfos1 = new ArrayList<ProductRateInfo2>();
			if("JX".equals(params.get("PRODUCT_CODE")) || "RJ".equals(params.get("PRODUCT_CODE"))){
				for (ProductRateInfo2 productrateinfo2 : searchProduct) {
					if ("10000.00".equals(productrateinfo2.getStartMoney())) {
						for (int i = 0; i < 4; i++) {
							ProductRateInfo2 productinfo = new ProductRateInfo2();
							productinfo.setInterestRate(productrateinfo2
									.getInterestRate());
							productinfo.setIsPrint(productrateinfo2.getIsPrint());
							productinfo.setMinimumBalance(productrateinfo2
									.getMinimumBalance());
							productinfo.setProductCode(productrateinfo2
									.getProductCode());
							productinfo.setProductCodeName(productrateinfo2
									.getProductCodeName());
							productinfo.setSavingCount(productrateinfo2
									.getSavingCount());
							productinfo.setSubProductCode(productrateinfo2
									.getSubProductCode());
							productinfo.setSubProductName(productrateinfo2
									.getSubProductName());
							productinfo.setVoucherType(productrateinfo2
									.getVoucherType());
							float startMoney = Float.parseFloat(productrateinfo2
									.getStartMoney());
							productinfo.setStartMoney((String.valueOf(startMoney
									+ i * 10000) + "0").trim());
							productinfo.setEndMoney(String.valueOf(productrateinfo2
									.getEndMoney()));
							productInfos1.add(productinfo);
						}
					} else {
						productInfos1.add(productrateinfo2);
					}
				}
			}else{
				if("1".equals(transBean.getCustLevel().trim())||
						"2".equals(transBean.getCustLevel().trim())||
						"3".equals(transBean.getCustLevel().trim())){
					for(int i=0;i<searchProduct.size()+3;i++){
						productInfos1.add(null);
					}
				}else{
					for(int i=0;i<searchProduct.size();i++){
						productInfos1.add(null);
					}
				}
				for (ProductRateInfo2 productrateinfo2 : searchProduct) {
					if ("10000.00".equals(productrateinfo2.getStartMoney())) {
						for (int i = 0; i < 4; i++) {
							ProductRateInfo2 productinfo = new ProductRateInfo2();
							productinfo.setInterestRate(productrateinfo2
									.getInterestRate());
							productinfo.setIsPrint(productrateinfo2.getIsPrint());
							productinfo.setMinimumBalance(productrateinfo2
									.getMinimumBalance());
							productinfo.setProductCode(productrateinfo2
									.getProductCode());
							productinfo.setProductCodeName(productrateinfo2
									.getProductCodeName());
							productinfo.setSavingCount(productrateinfo2
									.getSavingCount());
							productinfo.setSubProductCode(productrateinfo2
									.getSubProductCode());
							productinfo.setSubProductName(productrateinfo2
									.getSubProductName());
							productinfo.setVoucherType(productrateinfo2
									.getVoucherType());
							float startMoney = Float.parseFloat(productrateinfo2
									.getStartMoney());
							productinfo.setStartMoney((String.valueOf(startMoney
									+ i * 10000) + "0").trim());
							productinfo.setEndMoney(String.valueOf(productrateinfo2
									.getEndMoney()));
							productInfos1.set(i,productinfo);
						}
					} else {
						if("50000.00".equals(productrateinfo2.getStartMoney())){
							productInfos1.set(4,productrateinfo2);
						}else if("150000.00".equals(productrateinfo2.getStartMoney())){
							productInfos1.set(5,productrateinfo2);
						}else if("300000.00".equals(productrateinfo2.getStartMoney())){
							productInfos1.set(6,productrateinfo2);
						}else if("500000.00".equals(productrateinfo2.getStartMoney())){
							if("1".equals(transBean.getCustLevel().trim())||
									"2".equals(transBean.getCustLevel().trim())||
									"3".equals(transBean.getCustLevel().trim())){
								productInfos1.set(7,productrateinfo2);
							}else{
								productInfos1.set(0,productrateinfo2);
							}
						}else if("1000000.00".equals(productrateinfo2.getStartMoney())){
							if("1".equals(transBean.getCustLevel().trim())||
									"2".equals(transBean.getCustLevel().trim())||
									"3".equals(transBean.getCustLevel().trim())){
								productInfos1.set(8,productrateinfo2);
							}else{
								productInfos1.set(1,productrateinfo2);
							}
						}else if("5000000.00".equals(productrateinfo2.getStartMoney())){
							if("1".equals(transBean.getCustLevel().trim())||
									"2".equals(transBean.getCustLevel().trim())||
									"3".equals(transBean.getCustLevel().trim())){
								productInfos1.set(9,productrateinfo2);
							}else{
								productInfos1.set(2,productrateinfo2);
							}
						}else if("10000000.00".equals(productrateinfo2.getStartMoney())){
							if("1".equals(transBean.getCustLevel().trim())||
									"2".equals(transBean.getCustLevel().trim())||
									"3".equals(transBean.getCustLevel().trim())){
								productInfos1.set(10,productrateinfo2);
							}else{
								productInfos1.set(3,productrateinfo2);
							}
						}
					}
				}
				
			}
			// 私行
			params.put(IntefaceSendMsg.PRODUCT_INFOS, productInfos1);

		}

		// 跳转页面
		if ("JX".equals(params.get("PRODUCT_CODE"))) {// 积享存信息录入页
			openPanel(thisComp, new AccJxcInputPanel(params));

		} else if ("RJ".equals(params.get("PRODUCT_CODE"))) {// 如意+信息录入页
			openPanel(thisComp, new AccRyjInputPanel(params));

		} else {// 其他信息录入页
			openPanel(thisComp, new AccProInputPanel(params));
		}
	}

	/** 协议、私行判断金额是否进入授权 */
	public void ACC0021(final Component thisComp,
			final Map<Object, Object> params) {

		if("0".equals(transBean.getTransChangeNo().trim())){
			// 如果 为安享存、立得存，则进行受益人卡号录入页面
			if (transBean.getProductCode().startsWith("AX")
					|| transBean.getProductCode().startsWith("LZ")
					|| transBean.getProductCode().startsWith("LT")) {
				openPanel(thisComp, new AccInputBankMsgPanel(params));
			} else {
				openPanel(thisComp, new AccOkInputDepInfoPanel(params));

			}
		}else if("1".equals(transBean.getTransChangeNo().trim())){
			params.put("transCode", "0007");
			openPanel(thisComp, new AccDeputySelectionPanel(params));
		}else if("2".equals(transBean.getTransChangeNo().trim())){
			ACC0006(thisComp,params);
		}
//		if ("0".equals(transBean.getIsAuthorize())) {// 金额>=5万,进入授权处理
//			if("1".equals(transBean.getGoOnTrans())&&!"".equals(transBean.getCameraCount()) && transBean.getCameraCount() != null){
//				if(Double.parseDouble(transBean.getBeiZzAmt())>50000.00){
//					ACC0006(thisComp,params);
//				}else{
//					params.put("transCode", "0007");
//					openPanel(thisComp, new AccDeputySelectionPanel(params));
//				}
//			}else{
//				params.put("transCode", "0007");
//				openPanel(thisComp, new AccDeputySelectionPanel(params));
//			}
//
//		} else {// 金额小于5万,进入产品信息确认页
//
//			// 如果 为安享存、立得存，则进行受益人卡号录入页面
//			if (transBean.getProductCode().startsWith("AX")
//					|| transBean.getProductCode().startsWith("LZ")
//					|| transBean.getProductCode().startsWith("LT")) {
//				openPanel(thisComp, new AccInputBankMsgPanel(params));
//			} else {
//				openPanel(thisComp, new AccOkInputDepInfoPanel(params));
//
//			}
//		}
	}

	/** 约享存窗口期计算 */
	public void yxRate(Component comp) {
		try {
			transBean.getReqMCM001().setReqBefor("02864");
			Map<String, Object> inter02864 = IntefaceSendMsg
					.inter02864(transBean);
			transBean.getReqMCM001().setReqAfter((String)inter02864.get("resCode"), (String)inter02864.get("errMsg"));
			if (!"000000".equals(inter02864.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error("约享存利率查询异常：" + inter02864.get("errMsg"));
				return;
			}
			// 利率转换
			FloatRetUtil floatRet = new FloatRetUtil();
			float floatRet2 = Float.parseFloat(((String)inter02864.get("float")).trim());

			List<SearchProducRateInfo> productInfos = (List<SearchProducRateInfo>) inter02864
					.get(IntefaceSendMsg.KEY_PRODUCT_RATES);

			if (productInfos != null && productInfos.size() > 0) {
				List<SearchProducRateInfo> rateInfos = new ArrayList<SearchProducRateInfo>();

				StringBuffer str = new StringBuffer();
				StringBuffer str2 = new StringBuffer();
				for (int i = 0; i < productInfos.size(); i++) {
					SearchProducRateInfo producRateInfo = productInfos.get(i);
					if (producRateInfo.getDrawMonth() == null
							|| producRateInfo.getDrawMonth().trim().length() == 0) {
						continue;
					}
					producRateInfo.setInteDate(transBean.getStartDate().trim());
					StringBuffer l51Str=producRateInfo.getL51Str(floatRet2);
					StringBuffer l51Str2=producRateInfo.getL51Str2(floatRet2);
					if (l51Str.toString().trim().length() != 0) {
						if (str.toString().length() == 0) {
							str.append("提前支取选择期：").append(l51Str);
							str2.append("提前支取选择期：").append(l51Str2);
						} else {
							str.append("；").append(l51Str);
							str2.append("；").append(l51Str2);
						}
					}
				}
				transBean.setPrinterL51Str(str.toString());
				transBean.setBillPrinterL51Str(str2.toString());
			}

			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("约享存利率查询异常：" + e);
		}
	}

	/** 安享存窗口期计算 */
	public void axRate(Component comp) {
		Map<String, Object> inter02864 = null;
		try {
			transBean.getReqMCM001().setReqBefor("02864");
			inter02864 = IntefaceSendMsg.inter02864(transBean);
			transBean.getReqMCM001().setReqAfter((String)inter02864.get("resCode"), (String)inter02864.get("errMsg"));
			if (!"000000".equals(inter02864.get("resCode"))) {
				prossDialog.disposeDialog();
				logger.error("安享存利率查询异常：" + inter02864.get("errMsg"));
				return;
			}
			String floats = (String) inter02864.get("float");
			BigDecimal b1 = new BigDecimal(floats.trim());
			List<SearchProducRateInfo> list = (List<SearchProducRateInfo>) inter02864
					.get(IntefaceSendMsg.KEY_PRODUCT_RATES);
			String s = "";
			String billS="";
			for (int i = 0; i < list.size(); i++) {

				SearchProducRateInfo producRateInfo = list.get(i);
				String intUppercaseXYCK = MoneyUtils
						.intUppercaseXYCK(producRateInfo.getSavingCount());
				BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
				BigDecimal add = b1.add(b2);
				String rate = add + "%";
				s = s + intUppercaseXYCK + rate + ";";
				billS = billS + intUppercaseXYCK+";";
			}
			if (s.toString().trim().length() != 0) {
				s = "结息利率提示：" + s;
				s = s.substring(0, s.length() - 1);
				s = s.replaceAll("年", "期");
			}
			if (billS.toString().trim().length() != 0) {
				billS = "结息利率提示：" + billS;
				billS = billS.substring(0, billS.length() - 1);
				billS = billS.replaceAll("年", "期");
			}
			transBean.setBillPrinterL52Str(billS.trim());
			transBean.setPrinterL52Str(s.trim());
			prossDialog.disposeDialog();
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("安享存利率查询异常：" + e);
		}
	}

	/**
	 * 人脸比对
	 * 
	 * @throws Exception
	 */
	public boolean compareFaces(String idFacePath, String cameraPath)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("img1", Base64Util.GetImageStr(idFacePath));
		json.put("img1Type", "1");
		json.put("img2", Base64Util.GetImageStr(cameraPath));
		json.put("img2Type", "1");
		HttpClientUtil util = new HttpClientUtil();
		logger.debug("开始调用人脸识别接口");
		String result = util.post(Property.NEW_FACE_CHECK_SYS_PATH,json);
		logger.debug("执行结果：" + result);
		JSONObject obj = JSONObject.fromObject(result);
		String r = String.valueOf(obj.get("result"));
		// 人脸识别失败
		if (r.equals("0")) {
			String exCode = String.valueOf(obj.get("exCode"));
			String exMsg = String.valueOf(obj.get("exMsg"));
			logger.info("人脸识别失败原因："+exCode+"--"+exMsg);
			transBean.setCheckFaceMsg(exCode+"--"+exMsg);
			return false;
		}
		
		double sim = Double.parseDouble(String.valueOf(obj.get("sim")));
		double defaultSim = Double.parseDouble(String.valueOf(obj.get("defaultSim")));
		logger.info("阈值："+defaultSim+"||||"+"相似度:"+sim);
		if (sim >= defaultSim) {//sim >= defaultSim
			//当人脸识别成功之后则调用接口
			String sims = String.format("%.2f",Double.parseDouble(obj.getString("sim")));
			transBean.setFaceCompareVal(sims);
			return true;
		}
		transBean.setCheckFaceMsg("人脸识别相似度过低，未通过识别");
		return false;
	}

	public static void main(String[] args) {
	}
	
}
