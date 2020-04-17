package com.boomhope.Bill.TransService.AccTransfer.Action;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Framework.MainFrame;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.CheckConfirmDialog;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PaymentTermsMsgBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TelMsgBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TransferSelectBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferChooseBusiness;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.CardConfirm.TransferInputBankCardPassword;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInfo;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInputBankCardPassword;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelSuccessPanel;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView.TransferAccountInfo;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil_HYCancel;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 汇划流程处理
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class TransferAction extends BaseTransPanelNew{
	
	Logger logger = Logger.getLogger(TransferAction.class);
	

	/**
	 * 进入汇划转账
	 * @param thisComp
	 */
	public void openHuiHua(Component thisComp,final PublicAccTransferBean transferBean) {
		logger.info("进入汇划转账业务选择");
		//汇划功能选择页
		openPanel(new TransferChooseBusiness(transferBean));
	}
	
	/**
	 * 进入汇划撤销
	 * @param thisComp
	 */
	public void openHuiHuaCX(Component thisComp) {
		logger.info("进入汇划撤销的插卡页面");
		//汇划功能选择页
		openPanel(new TransferCancelInputBankCardPanel(new HashMap(),transferBean));
	}
	
	/**
	 * 密码输入后调核心接口
	 * @param thisComp
	 */
	public void passWord(Component thisComp,final PublicAccTransferBean transferBean){
		//判断插入卡Bin是个人还是单位
		String cardNo = transferBean.getChuZhangCardNo();
		if(cardNo.startsWith("623193") || cardNo.startsWith("622167")){//个人卡
			if(!"0".equals(transferBean.getIsCardBin())){
				logger.info("选择个人卡汇划业务，只能插入个人借记卡");
				logger.info("卡号："+cardNo);
				serverStop(thisComp,"请退出重新操作，选择个人汇划业务","", "");
				return;
			}
		}else if(cardNo.startsWith("62326558")){//单位卡
			if(!"1".equals(transferBean.getIsCardBin())){
				logger.info("选择单位卡汇划业务，只能插入单位结算卡");
				logger.info("卡号："+cardNo);
				serverStop(thisComp,"请退出重新操作，选择单位结算卡汇划业务","", "");
				return;
			}
		}else{//其他卡
			logger.info("选择单位卡汇划业务，只能插入单位结算卡");
			logger.info("卡号："+cardNo);
			serverStop(thisComp,"抱歉，该卡不支持此业务","", "");
			return;
		}
			
		openProssDialog();//打开处理等待框
		if(cardNo.startsWith("623193") || cardNo.startsWith("622167")){
			//查询账户信息
			logger.info("进入卡账户信息查询");
			if(!checkCardAcct(thisComp,transferBean)){
				return;
			}
			
			//查询卡信息
			logger.info("查询卡信息");
			if(!checkCardMsg(thisComp,transferBean)){
				return;
			}
			
			//个人IC卡验证
			logger.info("查询企个人IC卡信息");
			if(!checkIcInfo(thisComp,transferBean)){
				return;
			}
			
			
		}else if(cardNo.startsWith("62326558")){
			
			//查询账户信息
			logger.info("进入卡账户信息查询");
			if(!checkCardAcct(thisComp,transferBean)){
				return;
			}
			
			//查询余额单位信息
			logger.info("查询余额单位信息");
			if(!checkBalance(thisComp,transferBean)){
				return;
			}
		}
		
		// 查询黑灰名单
		logger.info("进入电信诈骗查询");
		transferBean.setProgramFlag("100501");//单元素
		transferBean.setIsChuRu("0");//出账信息查询
		if (!checkTelephoneFraud(thisComp,transferBean)) {
			return;
		};		
	
		//单位卡
		if(cardNo.startsWith("62326558")){
			//判断卡Bin，单位卡查询余额是否有权限，个人卡不用
			logger.info("进入单位卡权限查询");
			if(!checkRoot(thisComp,transferBean)){
				return;
			}
			//判断卡Bin，单位卡查询本行是否允许通存通兑
			logger.info("进入单位卡查询本行是否允许通存通兑");
			if(!checkUtilCardTcTd(thisComp,transferBean)){
				return;
			}
		}
		
		//查询核心日期
		if(!checkSvrDate(thisComp,transferBean)){
			return;
		}
		
		//查询开户行机构
		logger.info("进入账号开户机构查询");
		if (!checkHbrInstNo(thisComp,transferBean)) {
			return;
		}
		
		//查询历史转账客户信息
		logger.info("进入历史转账客户信息");
		if(!checkCustMsg(thisComp,transferBean)){
			return;
		}
		
		//查询绑定手机号
		if(!checkTelMsg(thisComp,transferBean)){
			return;
		}
		
		
		closeDialog(prossDialog);//全部查询成功关闭等待框
		
		//进入账户显示页面	
		logger.info("全部核查成功进入账户信息返显页面");
		openPanel(thisComp,new TransferAccountInfo(transferBean));
	}
	
	
	
	/**
	 * 查询转账卡账户信息
	 */
	public boolean checkCardAcct(final Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = InterfaceSendMsg.inter03521(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				
				if (((String) inter03521.get("errMsg")).startsWith("A102")) {

					prossDialog.disposeDialog();
					logger.info(((String) inter03521.get("errMsg")));
					final CheckConfirmDialog confirmDialog=new CheckConfirmDialog(MainFrame.mainFrame, true,"");
					confirmDialog.showDialog("输入密码错误，是否继续？是：重新输入密码;否：退出业务;");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							closeDialog(prossDialog);
							openPanel(thisComp,new TransferInputBankCardPassword(transferBean));
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							closeDialog(prossDialog);
							openPanel(thisComp,new OutputBankCardPanel());
						}
					});
					return false;
					
				} else if (((String) inter03521.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info((String) inter03521.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop(thisComp, "您的密码输入次数已达上限，卡已被锁定，请联系工作人员。",(String) inter03521.get("errMsg"),"");
					return false;
				}
				
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"),"","");
				return false;
			}
			
			if("5555".equals(inter03521.get("resCode"))){//个人卡状态
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"),"","");
				return false;
			}
			if("6666".equals(inter03521.get("resCode"))){//单位卡状态
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"), "","");
				return false;
			}

		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡账户信息查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询卡信息
	 */
	public boolean checkCardMsg(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("03845");
			Map inter03845 = InterfaceSendMsg.inter03845(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter03845.get("resCode"), (String)inter03845.get("errMsg"));
			if(!"000000".equals(inter03845.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("卡信息查询失败");
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03845.get("errMsg"), "","");
				return false;
			}
			if("5555".equals(inter03845.get("resCode"))){
				closeDialog(prossDialog);
				logger.info("卡信息查询失败");
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03845.get("errMsg"),"","");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡信息查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "卡信息查询失败，请联系大堂经理","", "调用03845接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询开户机构信息
	 */
	public boolean checkHbrInstNo(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("01118");
			Map inter01118 = InterfaceSendMsg.inter01118(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter01118.get("resCode"), (String)inter01118.get("errMsg"));
			if(!"000000".equals(inter01118.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(inter01118.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter01118.get("errMsg"), "","");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("查询机构信息失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用01118接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "查询机构信息失败，请联系大堂经理","", "调用01118接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询核心日期
	 */
	public boolean checkSvrDate(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("QRY00");
			Map interQRY00 = InterfaceSendMsg.interQRY00(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)interQRY00.get("resCode"), (String)interQRY00.get("errMsg"));
			if(!"000000".equals(interQRY00.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(interQRY00.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop("抱歉，查询核心日期失败，请联系大堂经理",(String)interQRY00.get("errMsg"),"");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("抱歉，查询核心日期失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用QRY00接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("抱歉，查询核心日期失败，请联系大堂经理","", "调用QRY00接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 * 单位卡查询余额是否有权限
	 */
	public boolean checkRoot(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("02956");
			Map inter02956 = InterfaceSendMsg.inter02956(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter02956.get("resCode"), (String)inter02956.get("errMsg"));
			if(!"000000".equals(inter02956.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(inter02956.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, "权限明细查询失败，请联系大堂经理", (String)inter02956.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("权限明细查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用02956接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "权限明细查询失败，请联系大堂经理","","调用02956接口异常");
			return false;
		}
		return true;
	}
	
	/**
	 *单位卡查询本行是否允许通存通兑
	 */
	public boolean checkUtilCardTcTd(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("01569");
			Map inter01569 = InterfaceSendMsg.inter01569(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter01569.get("resCode"), (String)inter01569.get("errMsg"));
			if(!"000000".equals(inter01569.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info(inter01569.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, "机构关系查询交易失败，请联系大堂经理", (String)inter01569.get("errMsg"),"");
				return false;
			}
			if("1".equals(transferBean.getTcTdFlag())){
				
				closeDialog(prossDialog);
				logger.info("本行不支持此单位卡通兑:是否通兑标志"+transferBean.getTcTdFlag());
				logger.info("本行机构号："+GlobalParameter.branchNo);
				transferBean.getReqMCM001().setIntereturnmsg("不支持通存通兑");
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, "抱歉，本行不支持此单位卡通存通兑", "","");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用01569机构关系查询交易失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用01569接口异常!");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "机构关系查询交易失败，请联系大堂经理","", "调用01569接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 查询黑灰名单
	 */
	public boolean checkTelephoneFraud(Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("01597");
			Map inter01597 = InterfaceSendMsg.inter01597(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter01597.get("resCode"), (String)inter01597.get("errMsg"));
			if(!"000000".equals(inter01597.get("resCode"))){
				
				if ("0010".equals(inter01597.get("resCode"))) {
					
					closeDialog(prossDialog);
					logger.info((String) inter01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop(thisComp,(String)inter01597.get("errMsg"), "","");
					return false;
				} else if ("0020".equals(inter01597.get("resCode"))) {
					
					closeDialog(prossDialog);
					logger.info((String) inter01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop(thisComp,(String)inter01597.get("errMsg"), "","");
					return false;
				}
				closeDialog(prossDialog);
				logger.info(inter01597.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)inter01597.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用01597查询黑灰名单信息失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", "","调用01597接口异常!");
			return false;
		}
		return true;
	}
	
	/**
	 * 转账客户列表信息查询-
	 */
	public boolean checkCustMsg(Component comp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("07492");
			Map inter07492 = InterfaceSendMsg.inter07492(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter07492.get("resCode"), (String)inter07492.get("errMsg"));
			if(!"000000".equals(inter07492.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info((String) inter07492.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(comp,"历史转账客户信息查询失败，请联系大堂经理", (String) inter07492.get("errMsg"),"");
				return false;
			}
			List<PaymentTermsMsgBean> PayList = (List<PaymentTermsMsgBean>)inter07492.get(InterfaceSendMsg.KEY_PRODUCT_RATES);
			logger.info("转账客户列表信息条数："+PayList.size());
			if(PayList == null || PayList.size()==0){
				logger.info("历史转账记录为空");
			}
			transferBean.getParams().put("PayList", PayList);//历史转账客户信息
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用07492转账客户列表信息查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用07492接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(comp,"转账客户列表信息查询失败，请联系大堂经理","", "调用07492接口异常!");
			return false;
		}
		return true;
	}
	
	
	/**
	 * 单位卡余额及结存额查询
	 */
	public boolean checkBalance(Component comp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("03453");
			Map inter03453 = InterfaceSendMsg.inter03453(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter03453.get("resCode"), (String)inter03453.get("errMsg"));
			if(!"000000".equals(inter03453.get("resCode"))){
				closeDialog(prossDialog);
				logger.info((String)inter03453.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(comp, "单位卡余额及结存额查询失败，请联系大堂经理",(String)inter03453.get("errMsg"),"");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用03453单位卡余额查询失败");
			transferBean.getReqMCM001().setIntereturnmsg("调用03453接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(comp,"单位卡余额查询失败，请联系大堂经理","","调用03453接口异常");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 个人IC卡验证
	 */
	public boolean checkIcInfo(Component comp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("07602");
			Map inter07602 = InterfaceSendMsg.inter07602(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter07602.get("resCode"), (String)inter07602.get("errMsg"));
			if(!"000000".equals(inter07602.get("resCode"))){
				closeDialog(prossDialog);
				logger.info((String)inter07602.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(comp, "个人ic卡信息查询失败，请联系大堂经理",(String)inter07602.get("errMsg"),"");
				return false;
			}
			
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用07602个人IC卡信息查询 失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用07602接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(comp,"个人ic卡信息查询失败，请联系大堂经理","","调用07602接口异常");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 查询绑定手机号
	 */
	public boolean checkTelMsg(Component comp,final PublicAccTransferBean transferBean){
		List<TelMsgBean> list = null;
		try {
			Map inter03740 = InterfaceSendMsg.inter03740(transferBean);
			if(!"000000".equals(inter03740.get("resCode"))){
				
				logger.info((String)inter03740.get("errMsg"));
			}
			list = (List<TelMsgBean>)inter03740.get(InterfaceSendMsg.TEL_MSG);
			if(list!=null && list.size()>0){
				if(list.get(0).getTelNo()!=null && !"".equals(list.get(0).getTelNo())){
					
					transferBean.setTelNo(list.get(0).getTelNo().trim());//获取手机号
					
				}else{
					logger.info("返回文件中的手机号为空");
				}
				
			}else{
				logger.info("返回文件中的手机号为空");
			}
		} catch (Exception e) {
			logger.error("调用03740查询绑定手机号失败"+e);
		}
		return true;
	}
	
	
	
	/**
	 * 密码输入后走个人转账撤销
	 * @param thisComp
	 */
	public void huihuaCancel(Component thisComp,final PublicAccTransferBean transferBean){
		logger.info("进入个人撤销流程");
		String cardNo = transferBean.getChuZhangCardNo();
		if(cardNo.startsWith("62326558")){//单位卡
			logger.info("单位卡暂不支持撤销业务");
			logger.info("卡号："+cardNo);
			serverStop(thisComp,"该卡为单位结算卡，不能发起汇划业务撤销","","");
			return;	
		}
		
		openProssDialog();//打开处理等待框
		//查询账户信息
		logger.info("进入撤销卡账户信息查询");
		if(!CancelCheckCardAcct(thisComp,transferBean)){
			return;
		}
		
		//个人IC卡验证
		logger.info("查询企个人IC卡信息");
		if(!checkIcInfo(thisComp,transferBean)){
			return;
		}
		
		//查询可撤销记录
		logger.info("查询可撤销往账记录");
		
		List<TransferSelectBean> listBean = null;
		try {
			transferBean.getReqMCM001().setReqBefor("CM022");
			Map interCM022 = InterfaceSendMsg.interCM022(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)interCM022.get("resCode"), (String)interCM022.get("errMsg"));
			if(!"000000".equals(interCM022.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info((String) interCM022.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp,"可撤销往账记录查询失败，请联系大堂经理", (String) interCM022.get("errMsg"),"");
				return ;
			}
			
			listBean = (List<TransferSelectBean>)interCM022.get(InterfaceSendMsg.TRANSFER_CANCEL_MSG);
			int flag = 0;
			//获取可撤销往账记录信息
			if(listBean != null && listBean.size()!=0){
				logger.info("可撤销往账记录条数："+listBean.size());
				for (int i = 0; i < listBean.size(); i++) {
					listBean.get(i).setSelectFlag(String.valueOf(flag));
					flag++;
				}
				transferBean.getParams().put("CancelMsgList", listBean);
				
			}else{
				
				closeDialog(prossDialog);
				logger.info("没有符合撤销条件的汇款信息");
				transferBean.getReqMCM001().setIntereturnmsg("无可撤销记录");
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp,"没有符合撤销条件的汇款信息!","","");
				return;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("调用CM022可撤销往账记录查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用CM022接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp,"转账记录查询失败，请联系大堂经理", "","调用CM022接口异常!");
			return ;
		}	
		
		closeDialog(prossDialog);
		//进入 可撤销展示页面
		openPanel(thisComp, new TransferCancelInfo(transferBean));
	}	
	
	
	/**
	 * 选中可撤销记录后，调撤销
	 * @param thisComp
	 */
	public void CanaclHuiHua(Component thisComp,final PublicAccTransferBean transferBean){
		logger.info("开始调用撤销处理");
		openProssDialog();//打开处理等待框
		String cancelSvr = "";
		String taskId = "";
		List<TransferSelectBean> chooseList =  (List<TransferSelectBean>)transferBean.getParams().get("chooseCancelList");//获取选中撤销的list
		Map inter02693 = null;
		for (int i = 0; i < chooseList.size(); i++) {//批量撤销
			try {
				logger.info("第"+i+"条撤销所需原交易渠道："+chooseList.get(i).getTransMethod());
				logger.info("第"+i+"条撤销所需原交易前置日期："+chooseList.get(i).getWtDate());
				logger.info("第"+i+"条撤销所需原交易任务号："+chooseList.get(i).getTransRWCode());
				
				//获取撤销所需数据
				if(chooseList.get(i).getTransMethod()!=null && chooseList.get(i).getTransMethod()!=""){
					transferBean.setOrigChannel(chooseList.get(i).getTransMethod().trim());//获取原交易渠道
				}
				
				if(chooseList.get(i).getWtDate()!=null && chooseList.get(i).getWtDate()!=""){
					transferBean.setOrigSysDate(chooseList.get(i).getWtDate().trim());//获取原交易前置日期
				}
				
				if(chooseList.get(i).getTransRWCode()!=null && chooseList.get(i).getTransRWCode()!=""){
					transferBean.setOrigTaskId(chooseList.get(i).getTransRWCode().trim());//获取原交易任务号
				}
				transferBean.getReqMCM001().setReqBefor("02693");
				//调用撤销接口
				inter02693 = InterfaceSendMsg.inter02693(transferBean);
				transferBean.getReqMCM001().setReqAfter((String)inter02693.get("resCode"), (String)inter02693.get("errMsg"));
				if(!"000000".equals(inter02693.get("resCode"))){//撤销失败
					
					if("4444".equals(inter02693.get("resCode"))){
						logger.info("第"+i+"笔撤销失败："+(String) inter02693.get("errMsg"));
						chooseList.get(i).setCancelResult("失败");//撤销失败结果
						MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
						chooseList.get(i).setFailCause((String) inter02693.get("errMsg"));//撤销失败原因
						continue;
					}
					
					logger.info("第"+i+"笔撤销失败："+(String) inter02693.get("errMsg"));
					chooseList.get(i).setCancelResult("失败");//撤销失败结果
					chooseList.get(i).setFailCause((String) inter02693.get("errMsg"));//撤销失败原因
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					continue;
				}
					
			} catch (Exception e) {
				logger.error("第"+i+"笔撤销失败："+(String) inter02693.get("errMsg")+e);
				chooseList.get(i).setCancelResult("失败");//撤销失败结果
				transferBean.getReqMCM001().setIntereturnmsg("调用02693接口异常");
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				chooseList.get(i).setFailCause("调用撤销接口处理失败");//撤销失败原因
				continue;
			}
			chooseList.get(i).setCancelId(transferBean.getMsJrnlNo());//撤销流水
			cancelSvr = transferBean.getMsJrnlNo();//撤销流水
			chooseList.get(i).setCancelResult("成功");//撤销成功结果
			
			transferBean.getReqMCM001().setTrjnno(cancelSvr);
			transferBean.getReqMCM001().setInteresult("0");
			transferBean.getReqMCM001().setCustomername(chooseList.get(i).getZcName());
			transferBean.getReqMCM001().setAccount(chooseList.get(i).getZcNo());
			transferBean.getReqMCM001().setLendirection("1");
			transferBean.getReqMCM001().setTransamt(chooseList.get(i).getTransMoney());
			transferBean.getReqMCM001().setTurnflag("1");
			transferBean.getReqMCM001().setTonouns(chooseList.get(i).getZrName());
			transferBean.getReqMCM001().setToaccount(chooseList.get(i).getZrNo());
			transferBean.getReqMCM001().setTopenbankname(chooseList.get(i).getPayeeHbrName());
			
			//撤销成功，上送撤销流水信息
			transferBean.getReqMCM001().setTransresult("0");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
		}
		closeDialog(prossDialog);
		//生成事后监督图片
		String nowDate = DateUtil.getNowDate("yyyyMMdd");
		logger.info("获取今天日期-----》"+nowDate);
		// 8位日期+任务号+柜员号+机构号
		String tellerNo = GlobalParameter.machineNo.substring(3);
		taskId = transferBean.getOrigTaskId().substring(transferBean.getOrigTaskId().length()-5, transferBean.getOrigTaskId().length());
		String fileName = nowDate+"_"+taskId+tellerNo+GlobalParameter.branchNo;
		
		logger.info("获取日期+第一条撤销信息流水号---->" + fileName);
		String filepath = Property.FTP_LOCAL_PATH+fileName+".jpg";
		logger.info("获取本地生成的图片路径--->"+filepath);
		Map map=new HashMap<>();
		map.put("list",chooseList);
		map.put("path", filepath);
		uploadPhotoFile(map);
		//进入撤销结果展示
		
		openPanel(thisComp, new TransferCancelSuccessPanel(transferBean));
	}
	/**
	 * 生成图片，上传事后监督
	 */ 
	public boolean uploadPhotoFile(Map list) {
		boolean isupload = true;// 标记是否上传成功
		JpgUtil_HYCancel cg = new JpgUtil_HYCancel();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration(list);
		} catch (IOException e2) {
			logger.error("事后监管程序，生成事后监管图片异常！"+ e2);
			logger.info("事后监管程序，生成事后监管图片异常！");
			return false;
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
    		String ftpPath = Property.FTP_SER_PATH_KH + nowDate+"/000005";
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
    			isupload = false;
    		}else{
    			logger.info("事后监管上传图片成功-->" + file.getName());
    			//删除图片
    			deleteFile(file);
    		}
    		
		} catch (Exception e) {
			logger.error("事后监督上传图片，进入目录失败");
			isupload = false;
		}finally{
			if (sftp!= null && sftp.isConnected()){
				sftp.disconnect();
			}
			if (sshSession!= null && sshSession.isConnected()){
				sshSession.disconnect();
			}
		}
		return true;
    	
	}
	/**
	 * 成功上传事后监督图片后删除本地图片的方法
	 * */
	private void deleteFile(File file) {
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
	 * 查询撤销卡账户信息
	 */
	public boolean CancelCheckCardAcct(final Component thisComp,final PublicAccTransferBean transferBean){
		try {
			transferBean.getReqMCM001().setReqBefor("03521");
			Map inter03521 = InterfaceSendMsg.inter03521(transferBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
			if(!"000000".equals(inter03521.get("resCode"))){
				
				if (((String) inter03521.get("errMsg")).startsWith("A102")) {

					prossDialog.disposeDialog();
					logger.info(((String) inter03521.get("errMsg")));
					final CheckConfirmDialog confirmDialog=new CheckConfirmDialog(MainFrame.mainFrame, true,"");
					confirmDialog.showDialog("输入密码错误，是否继续？是：重新输入密码;否：退出业务;");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							closeDialog(prossDialog);
							openPanel(thisComp,new TransferCancelInputBankCardPassword(transferBean));
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							closeDialog(confirmDialog);
							closeDialog(prossDialog);
							openPanel(thisComp,new OutputBankCardPanel());
						}
					});
					return false;
					
				} else if (((String) inter03521.get("errMsg")).startsWith("A103")) {

					prossDialog.disposeDialog();
					logger.info((String) inter03521.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
					serverStop(thisComp, "您的密码输入次数已达上限，卡已被锁定，请联系工作人员。",(String) inter03521.get("errMsg"),"");
					return false;
				}
				
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"),"","");
				return false;
			}
			
			if("5555".equals(inter03521.get("resCode"))){//个人卡状态
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"),"","");
				return false;
			}
			if("6666".equals(inter03521.get("resCode"))){//单位卡状态
				closeDialog(prossDialog);
				logger.info(inter03521.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop(thisComp, (String)inter03521.get("errMsg"), "","");
				return false;
			}

		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("卡账户信息查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop(thisComp, "卡账户信息查询失败，请联系大堂经理","", "调用03521接口异常");
			return false;
		}
		return true;
	}
}
