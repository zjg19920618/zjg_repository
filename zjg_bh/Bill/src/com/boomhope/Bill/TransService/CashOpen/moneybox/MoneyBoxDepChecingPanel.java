package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.AccOpen.Bean.accOpenProFileBean02808And03870;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Interface.AccountDepositApi;
import com.boomhope.Bill.TransService.CashOpen.Interface.OpenJxJInfo;
import com.boomhope.Bill.TransService.CashOpen.Interface.SearchProducRateInfo;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.JpgUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.account.in.BCK02808ResBean;
import com.boomhope.tms.message.account.in.BCK02864ResBean;
import com.boomhope.tms.message.account.in.BCK07506ResBean;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.bck.BCK07663ResBean;
import com.boomhope.tms.message.in.bck.BCK07663ResBodyBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransApplyReqBean;
import com.boomhope.tms.message.moneybox.MoneyBoxTransCommitReqBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 交易处理过渡页
 * @author gyw
 *
 */
public class MoneyBoxDepChecingPanel extends BaseTransPanelNew{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxDepChecingPanel.class);
	Timer checkTimer = null;
	public MoneyBoxDepChecingPanel(final PublicCashOpenBean transBean){
		this.cashBean = transBean;
		/* 显示时间倒计时 */
		showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 标题  */
		JLabel titleLabel = new JLabel("交易正在进行,请稍后...");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		titleLabel.setBounds(0, 387, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		
		/* 加载核查动画 */
		JLabel accImage = new JLabel(); 
		accImage.setIcon(new ImageIcon("pic/depinputbankpass.gif"));
		accImage.setIconTextGap(6);
		accImage.setBounds(395, 204, 220, 159);
		this.showJpanel.add(accImage);
		JButton testButton = new JButton("测试按键");
		testButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
        			nextStep(transBean);
				
			}
		});
		testButton.setSize(150, 30);
		testButton.setLocation(508, 660);
		Property property = new Property();
		if(property.getProperties().getProperty("push_button").equals("false")){
			testButton.setVisible(false);
		}else if (property.getProperties().getProperty("push_button").equals("true")){
			testButton.setVisible(true);
		}
		this.showJpanel.add(testButton);
		checkTimer = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	checkTimer.stop();
            	//开户并入库，打印
        		try {
        			openAccount(transBean);
				} catch (Exception e2) {
					logger.error(e);
					serverStop("请联系大堂经理","","开户失败");
					return;
				}
        		
            }
        });
		checkTimer.start();
		
	}
	
	private void openAccount(final PublicCashOpenBean transBean)throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			
			logger.info("调用查询凭证号接口-------");
			//开户之前先获取凭证号
			//查询当前凭证号
			try{
			Tms0005ResBean tms0005 = Tms0005();
			if(tms0005 == null){
				serverStop("调用查询凭证号接口失败，请联系大堂经理!", "","");
				return;
			}
			String resCode = tms0005.getHeadBean().getResCode();
			String resMsg = tms0005.getHeadBean().getResMsg();
			
			if(!"000000".equals(resCode)){
				if("44444".equals(resCode)){
					serverStop("请联系大堂经理","查询凭证号失败，"+resMsg,"");
				}else{
					logger.error("错误信息："+resMsg);
					serverStop("请联系大堂经理","查询凭证号失败，"+resMsg,"");
				}
				return;
			}
			String certNo = tms0005.getBody().getNOW_BO();
			logger.info("当前凭证号："+certNo+" 开始凭证号："+tms0005.getBody().getSTART_NO()+" 结束凭证号："+tms0005.getBody().getEND_NO());
			
			//先调用钱柜业务申请
			try{
			MoneyBoxTransApplyReqBean moneyBoxTransApplyReqBean = getTransApply(transBean);
			if(moneyBoxTransApplyReqBean == null){
				serverStop("请联系大堂经理","","系统异常");
			}
			String retCode = moneyBoxTransApplyReqBean.getResponse().getRetCode().trim();
			String retMsg = moneyBoxTransApplyReqBean.getResponse().getRetCode().trim();
			logger.info("业务申请rescode："+retCode);
			logger.info("业务申请resmsg："+retMsg);
			if(!"0000".equals(retCode)){
				serverStop("请联系大堂经理","业务申请失败：" + retMsg,"");
				return;
			}
			//获取交易申请码
			//获取银行内部帐号
			transBean.setApplyCode(moneyBoxTransApplyReqBean.getResponse().getApplyCode().trim());
			transBean.setBankAccount(moneyBoxTransApplyReqBean.getResponse().getBankAccount().trim());
			}catch(Exception e){
				logger.error("调用钱柜业务申请接口异常"+e);
			}
			
			logger.info("开始调用开户接口----02808--------");
			//开户
			SimpleDateFormat PRE_FORMAT = new SimpleDateFormat("yyyyMMdd");
			Map<String, Object> openAccountMap = new HashMap<String, Object>();
			openAccountMap.put("START_INT_DATE", PRE_FORMAT.format(new Date()));
			openAccountMap.put("ID_TYPE", "10100");
			openAccountMap.put("ID_NO", transBean.getReadIdcard());
			openAccountMap.put("CUST_NO", transBean.getCustNo());
			openAccountMap.put("LIMIT",transBean.getLimit());
			openAccountMap.put("PASSWORD", transBean.getSubPwd());
			if(transBean.getProductCode().startsWith("XF")){
				String[] periodFields = getField4PeriodXF();
				openAccountMap.put("UNIT_FLAG", periodFields[0]);
				openAccountMap.put("DEP_TERM", periodFields[1]);
			}else{
				String[] periodFields = getField4Period(transBean.getMonthsUpper().trim());
				openAccountMap.put("UNIT_FLAG", periodFields[0]);
				openAccountMap.put("DEP_TERM", periodFields[1]);
				if(transBean.getProductCode().startsWith("LZ") || transBean.getProductCode().startsWith("LT")){
					openAccountMap.put("PUT_INT_ACCT", transBean.getInputNo());
				}
			}			
			openAccountMap.put("OPPO_DRAW_COND","");
			openAccountMap.put("DRAW_PASSWORD", "");
			openAccountMap.put("AMT", "" + transBean.getMoney()+".00");
			openAccountMap.put("NEW_CERT_NO", certNo);
			if("2".equals(transBean.getOrderStatus())){
				openAccountMap.put("SETT_TYPE", "1");
				openAccountMap.put("OPPO_ACCT_NO", transBean.getBankAccount());
			}else{
				openAccountMap.put("SETT_TYPE", "0");
				openAccountMap.put("OPPO_ACCT_NO", Property.getProperties().get("OPPO_ACCT_NO")+GlobalParameter.branchNo);
			}
			openAccountMap.put("EXCH_FLAG", transBean.getAutoSaving());
			openAccountMap.put("PROD_CODE", transBean.getProductCode());
			if(transBean.getAccNo() != null && transBean.getProductCode().startsWith("RJ")){
				openAccountMap.put("PUT_INT_ACCT", transBean.getAccNo());//关联如意存账号
			}
			if("1".equals(transBean.getJijvOrPuhui())){
				openAccountMap.put("CHL_ID", "5");// 普惠版:渠道模块标识为5
			}
			if(transBean.getProductCode().startsWith("XF")||transBean.getProductCode().startsWith("QX")){
				openAccountMap.put("RULE_NO", transBean.getQxGetHaveType());//千喜收益方式
			}
			
			AccountDepositApi accountDepositApi = new AccountDepositApi();
			BCK02808ResBean openAccount = accountDepositApi.openAccount(openAccountMap);
			if(openAccount == null){
				transBean.setCommiTtype("0");
				transBean.setSubCardNo("");
				transBean.setSvrJrnlNo("");
				//业务提交钱柜
				try{
				MoneyBoxTransCommitReqBean moneyBoxTransCommitReqBean = getTransCommit(transBean);
				if(moneyBoxTransCommitReqBean == null){
					serverStop("请联系大堂经理","","系统异常");
					return;
				}
				String retCode = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
				String retMsg = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
				logger.info("业务提交rescode："+retCode);
				logger.info("业务提交resmsg："+retMsg);
				if(!"0000".equals(retCode)){
					serverStop("请联系大堂经理","业务提交钱柜失败：" + retMsg,"");
					return;
				}
				serverStop("开户失败，请联系大堂经理!", "","");
				return;
				}catch(Exception e){
					logger.error("调用钱柜业务提交接口异常"+e);
				}
			}
			resCode = openAccount.getHeadBean().getResCode();
			resMsg = openAccount.getHeadBean().getResMsg();
			
			if(!"000000".equals(resCode) || "44444".equals(resCode)){
				transBean.setCommiTtype("0");
				transBean.setSubCardNo("");
				transBean.setSvrJrnlNo("");
				//业务提交钱柜
				try{
				MoneyBoxTransCommitReqBean moneyBoxTransCommitReqBean = getTransCommit(transBean);
				if(moneyBoxTransCommitReqBean == null){
					serverStop("请联系大堂经理","","系统异常");
					return;
				}
				String retCode = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
				String retMsg = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
				logger.info("业务提交rescode："+retCode);
				logger.info("业务提交resmsg："+retMsg);
//				if(!"0000".equals(retCode)){
//					serStop("业务提交钱柜失败：" + retMsg,"");
//					return;
//				}
				serverStop("请联系大堂经理","开户失败，"+resMsg,"");
				return;
				}catch(Exception e){
					logger.error("调用钱柜业务提交接口异常"+e);
				}
			}
			
			List<accOpenProFileBean02808And03870> listZYD = getZYDInfo(openAccount);
			if(listZYD!=null && listZYD.size()!=0){
				for(int i=0;i<listZYD.size();i++){
					accOpenProFileBean02808And03870 afa = listZYD.get(i);
					if("008".equals(afa.getDerivativeType())){
						logger.info("查询到增益豆信息");
						//如果预付标志位0，则增益豆数量为执行浮动值，如果为1，则为预付数量
						if("0".equals(afa.getPayMark())){
							transBean.setZydCount(String.format("%.2f", new Double(afa.getDoFloatVal())).toString());
						}else{
							transBean.setZydCount(String.format("%.2f", new Double(afa.getPayNums())).toString());
						}
					}else if("003".equals(afa.getDerivativeType())){
						logger.info("查询到消费豆信息");
						//如果预付标志位0，则增益豆数量为执行浮动值，如果为1，则为预付数量
						if("0".equals(afa.getPayMark())){
							transBean.setXfdCount(String.format("%.2f", new Double(afa.getDoFloatVal())).toString());
						}else{
							transBean.setXfdCount(String.format("%.2f", new Double(afa.getPayNums())).toString());
						}
					}
				}
			}else{
				logger.info("无衍生品文件，则无唐豆");
			}
			//计算唐豆总数（幸运豆+增益豆+消费豆）
			Double tdCount = 0.00;
			if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
				tdCount+=Double.valueOf(transBean.getZydCount());
			}
			if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
				tdCount+=Double.valueOf(transBean.getXfdCount());
			}
			transBean.setTdTotalCount(tdCount.toString().trim());
			
			transBean.setSubCardNo(openAccount.getBody().getACCT_NO());//账号
			transBean.setCreateTime(openAccount.getBody().getSVR_DATE());//开始日期
			transBean.setSvrJrnlNo(openAccount.getBody().getSVR_JRNL_NO());//流水
			transBean.setValueDate(openAccount.getBody().getINTE_DATE());//起息日期
			transBean.setEndTime(openAccount.getBody().getEND_DATE());//到期日期
			transBean.setInte(openAccount.getBody().getINTE().trim());//利息
			transBean.setRate(openAccount.getBody().getRATE().trim());//利率
			if(transBean.getProductCode().startsWith("RJ")){
				int endDate = DateTool.nDaysBetweenTwoDate(openAccount.getBody().getSVR_DATE(), openAccount.getBody().getEND_DATE());
				transBean.setMonthsUpperStr(endDate+"天");
			}
			transBean.setCommiTtype("1");
			//业务提交钱柜
			try{
			MoneyBoxTransCommitReqBean moneyBoxTransCommitReqBean = getTransCommit(transBean);
			if(moneyBoxTransCommitReqBean == null){
				serverStop("请联系大堂经理","","系统异常");
			}
			String retCode = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
			String retMsg = moneyBoxTransCommitReqBean.getResponse().getRetCode().trim();
			logger.info("业务提交rescode："+retCode);
			logger.info("业务提交resmsg："+retMsg);
			if(!"0000".equals(retCode)){
				serverStop("请联系大堂经理!", "业务提交钱柜失败：" + retMsg,"");
				return;
			}
			}catch(Exception e){
				logger.error("调用钱柜业务提交接口异常"+e);
			}
			logger.info("开始调用更新凭证号接口---------------");
			//调用更新凭证号接口
			try{
			Map<String, String> updateMap = new HashMap<String, String>();
			updateMap.put("isNo", "");
			updateMap.put("id", tms0005.getBody().getID());
			updateMap.put("startNo", tms0005.getBody().getSTART_NO());
			updateMap.put("endNo", tms0005.getBody().getEND_NO());
			updateMap.put("nowNo", String.format("%08d",(Long.parseLong(certNo)+1)));
			updateMap.put("createDate", tms0005.getBody().getCREATE_DATE());
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));
			Tms0005ResBean tms0006 = Tms0006(updateMap);
			if(null == tms0006 ){
				serverStop("请联系大堂经理!", "","更新凭证号失败");
				return;
			}
			resCode = tms0006.getHeadBean().getResCode();
			resMsg = tms0006.getHeadBean().getResMsg();
			if(!"000000".equals(resCode)){
				serverStop("请联系大堂经理!", resMsg,"");
				return;
			}
			
			//当产品为约享存时，计算窗口期
			if(transBean.getProductCode().startsWith("Y")){
				Map<String, Object> searchProducRateInfoMap=new HashMap<String, Object>();
				searchProducRateInfoMap.put("PROD_CODE", transBean.getProductCode());	//产品代码
				searchProducRateInfoMap.put("RATE_DATE", openAccount.getBody().getINTE_DATE().trim());
				BCK02864ResBean bck02864ResBean = accountDepositApi.searchProducRateInfo(searchProducRateInfoMap);
				String Rate_RET_CODE = bck02864ResBean.getHeadBean().getResCode();
				String Rate_RET_MSG = bck02864ResBean.getHeadBean().getResMsg();
				if(Rate_RET_CODE == null || "".equals(Rate_RET_CODE.trim())){
					serverStop("请联系大堂经理!", "约享存 取提前支取选择期查询失败:"+Rate_RET_MSG,"");
					return;
				}
				float floatRet=getFloatRet(openAccount.getBody().getAREA_FLOAT_RET()
						,openAccount.getBody().getCHL_FLOAT_RET()
						,openAccount.getBody().getBIRTH_FLOAT_RET()
						,openAccount.getBody().getTIME_FLOAT_RET(),openAccount.getBody().getCOMB_FLOT_RET());
				
				List<SearchProducRateInfo> productInfos=(List<SearchProducRateInfo>) searchProducRateInfoMap.get(AccountDepositApi.KEY_PRODUCT_RATES);
				List<SearchProducRateInfo> rateInfos = new ArrayList<SearchProducRateInfo>();
				StringBuffer str=new StringBuffer();
				for (int i = 0; i < productInfos.size(); i++)
				{
					SearchProducRateInfo producRateInfo=productInfos.get(i);
					if(producRateInfo.getDrawMonth()==null||producRateInfo.getDrawMonth().trim().length()==0){
						continue;
					}
					producRateInfo.setInteDate(openAccount.getBody().getINTE_DATE().trim());
					StringBuffer l51Str=producRateInfo.getL51Str(floatRet);
					if(l51Str.toString().trim().length()!=0){
						if(str.toString().length()==0){
							str.append("提前支取选择期：").append(l51Str);
						}else{
							str.append("；").append(l51Str);
						}
					}
				}
//				logger.info("================="+str.toString());
				transBean.setPrinterL51Str(str.toString());
			}
				}catch(Exception e){
				logger.error("调用tms0006异常"+e);
			}
			String accountRes = openAccountMap.get(AccountDepositApi.RESULT).toString();
			logger.info("开始调用入库接口------------");
			//调用入库接口
			//当不是幸福1+1系列时，在这里调用入库
			if(!transBean.getProductCode().startsWith("XF")){
				try {
					saveAccountOrder(transBean, accountDepositApi, certNo, accountRes, "");
				} catch (Exception e) {
					logger.error("saveAccountOrder方法异常"+e);
				}
			}
			
			//只有幸福1+1 整存整取才能调用糖豆接口
			if(("0010".equals(transBean.getProductCode()) || (!"1".equals(transBean.getJijvOrPuhui()) && transBean.getProductCode().startsWith("XF")))){
				
				transBean.setTangDouReturn(true);
				logger.info("开始调用    查唐豆规则--------07506------");
				Map<String, Object> TDMap = new HashMap<String, Object>();
				TDMap.put("PRODUCT_CODE", transBean.getProductCode());//产品编号
				logger.info("产品编号："+transBean.getProductCode());
				TDMap.put("DEP_TERM", transBean.getMonthsUpper());//存期
				logger.info("存期："+transBean.getMonthsUpper());
				TDMap.put("AMT", transBean.getMoney());//存款
				TDMap.put("ACCEPT_DATE", transBean.getSvrDate());//兑付日期
				TDMap.put("IN_INST_NO", GlobalParameter.branchNo);//机构号
				TDMap.put("ACT_CHNL", "");//活动渠道
				TDMap.put("DETAIL_NUM", "1");//循环数
				TDMap.put("ACCT_NO", openAccount.getBody().getACCT_NO());
				TDMap.put("SUB_ACCT_NO", "");
				TDMap.put("ACCT_BAL", transBean.getMoney());
				BCK07506ResBean searchTangDouRule = accountDepositApi.searchTangDouRule(TDMap);
				if(null == searchTangDouRule ){
					logger.error("唐豆查询失败"+searchTangDouRule);
//					this.serviceStop("唐豆查询失败", "，唐豆查询失败");
//					return;
					transBean.setTangDouReturn(false);
					transBean.getImportMap().put("TdChaXun", "0");
					transBean.setErrmsg("唐豆查询失败,请联系大堂经理!");
				}
				resCode = searchTangDouRule.getHeadBean().getResCode();
				resMsg = searchTangDouRule.getHeadBean().getResMsg();
				
				if(!"000000".equals(resCode)){
					if (StringUtils.isNotEmpty(resMsg) && resMsg.startsWith("D999")==false) {
						logger.error("唐豆查询失败"+searchTangDouRule.getHeadBean().getResMsg());
//						serviceStop("唐豆查询失败:"+searchTangDouRule.getHeadBean().getResMsg(),"");
//						return;
						transBean.getImportMap().put("TdChaXun", "0");
						transBean.setErrmsg("唐豆查询失败,请联系大堂经理!");
					}
					logger.error("唐豆查询失败"+searchTangDouRule.getHeadBean().getResMsg());
					transBean.setTangDouReturn(false);
				}
				if(transBean.getProductCode().startsWith("XF")){
					String TDres = TDMap.get(AccountDepositApi.RESULT).toString();
					try {
						saveAccountOrder(transBean, accountDepositApi, certNo, accountRes, TDres);
					} catch (Exception e) {
						logger.error("saveAccountOrder方法异常"+e);
					}
				}
				if(transBean.isTangDouReturn()){
					//充值糖豆兑付
					logger.info("开始调用    唐豆兑付--------07663------");
					Map<String, Object> exchangeMap = new HashMap<String, Object>();
					exchangeMap.put("ACCT_NO", openAccount.getBody().getACCT_NO());
					exchangeMap.put("SUB_ACCT_NO", "");
					exchangeMap.put("ACCT_BAL", transBean.getMoney());
					
					exchangeMap.put("DEP_TERM", transBean.getMonthsUpper());
					exchangeMap.put("ACCEPT_DATE", transBean.getSvrDate());
					exchangeMap.put("TOTAL_BAL", transBean.getMoney());
					exchangeMap.put("TERM_CODE", searchTangDouRule.getBody().getTERM_CODE());
					exchangeMap.put("COUNT", searchTangDouRule.getBody().getCOUNT());
					exchangeMap.put("EXCHANGE_AMT", searchTangDouRule.getBody().getEXCHANGE_AMT());
					exchangeMap.put("EXCHANGE_PROP", searchTangDouRule.getBody().getEXCHANGE_PROP());
					
					exchangeMap.put("OPPO_ACCT_NO", Property.getProperties().get("OPPO_ACCT_NO")+GlobalParameter.branchNo);
					exchangeMap.put("DEAL_TYPE",searchTangDouRule.getBody().getDEAL_TYPE());
					exchangeMap.put("PRODUCT_CODE", transBean.getProductCode());
					exchangeMap.put("EXCHANGE_MODE", "1");
					exchangeMap.put("CUSTOM_MANAGER_NO", "");
					exchangeMap.put("CUSTOM_MANAGER_NAME", "");
					exchangeMap.put("ACT_CHNL", "");
					exchangeMap.put("AMT_CHL_FLAG", "1");
					
					
					BCK07663ResBean bck07663ResBean = accountDepositApi.exchangeMoneyBoxTangDou(exchangeMap);
					if(null == bck07663ResBean || "44444".equals(bck07663ResBean.getHeadBean().getResCode())){
						logger.error("唐豆充值失败"+bck07663ResBean);
//						this.serviceStop("唐豆充值失败", "，唐豆充值失败");
//						return;
						transBean.setTangDouReturn(false);
						transBean.getImportMap().put("TdChongZhi", "0");
						transBean.setErrmsg("唐豆充值失败,请联系大堂经理!");
					}
					resCode = bck07663ResBean.getHeadBean().getResCode();
					resMsg = bck07663ResBean.getHeadBean().getResMsg();
					if(!"000000".equals(resCode)){
						if (StringUtils.isNotEmpty(resMsg) && resMsg.startsWith("D999")==false) {
							logger.error("唐豆充值失败");
//							this.serviceStop("唐豆充值失败："+resMsg, "，唐豆充值失败");
//							return;
							transBean.getImportMap().put("TdChongZhi", "0");
							transBean.setErrmsg("唐豆充值失败,请联系大堂经理!");
						}
						logger.error("唐豆充值失败"+resMsg);
						transBean.setTangDouReturn(false);
					
					}
					BCK07663ResBodyBean bck07663ResBodyBean = bck07663ResBean.getBody();
					transBean.setTangDouCount(bck07663ResBodyBean.getR_COUNT());//糖豆数量
					transBean.setTangDSvrJrnlNo(bck07663ResBodyBean.getSVR_JRNL_NO());//糖豆流水
					if(transBean.isTangDouReturn()){
						transBean.setTangDouExchangeAmt(String.format("%.2f",Double.valueOf(bck07663ResBodyBean.getR_COUNT())/500));
					}else{
						transBean.setTangDouExchangeAmt("");
					}
				}
			}
			
			//加息劵
			List<OpenJxJInfo> list=(List<OpenJxJInfo>) openAccountMap.get(AccountDepositApi.KEY_PRODUCT_RATES);
//			 RET_CODE=openAccountResponse.RESPONSE.RET_CODE;
//			 RET_MSG=openAccountResponse.RESPONSE.RET_MSG;
			 if (list.size()>0 && list!= null) {
				 float money = 0;
				for (int i = 0; i < list.size(); i++) {
					OpenJxJInfo openJxJInfo = list.get(i);
					String jxjType = openJxJInfo.getJxjType(); //1 /2
					String jxjBiZi = openJxJInfo.getJxjBiZi(); // 0/1
					if (jxjType.equals("2")&&jxjBiZi.equals("1")) {
						String jxjMoney = openJxJInfo.getJxjMoney();
						money += Float.parseFloat(jxjMoney);
					}
				}
				if (money!=0) {
					
					transBean.setInterestCount(money+"");
					tdCount+=money;
					transBean.setTdTotalCount(tdCount.toString().trim());
					try {
						JxPage(transBean);
					} catch (Exception e1) {
						logger.error("跳转加息券页面异常"+e1);
					}
					//调转加息劵页面
					try {
						ftpupLoad(transBean);
					} catch (Exception e) {
						logger.error("上传事后监督图片异常"+e);
					}
					return;
				}
				
			}
			 
			try {
				ftpupLoad(transBean);
			} catch (Exception e) {
				logger.error("上传事后监督图片异常"+e);
			}
 			nextStep(transBean);
			}catch(Exception e){
				logger.error(e);
			}

		}}).start();
	}
	
	/**
	 * 业务提交
	 */
	public MoneyBoxTransCommitReqBean getTransCommit(PublicCashOpenBean transBean)throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("IDNum", transBean.getReadIdcard());//身份证
		map.put("OrderNum", transBean.getOrderNo());//订单号
		map.put("Password", transBean.getOrderPwd());//密码
		map.put("ApplyCode", transBean.getApplyCode());//申请交易码
		map.put("CommitStatus", transBean.getCommiTtype());//业务办理结果//成功1，失败0
		map.put("DepositReceiptNum", transBean.getSubCardNo().trim());//存单号
		map.put("CoreJournal",transBean.getSvrJrnlNo().trim());//核心流水号
		
		
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			socket = sc.createSocket();
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(SocketClient.moneyBoxTransCommit(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", MoneyBoxTransCommitReqBean.class);
			MoneyBoxTransCommitReqBean moneyBoxTransCommitReqBean = (MoneyBoxTransCommitReqBean)reqXs.fromXML(retMsg);
			logger.info(moneyBoxTransCommitReqBean);
			logger.info("CLIENT retMsg:" + retMsg);

			return moneyBoxTransCommitReqBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			if(socket != null){
				try {
					br.close();
					bw.close();
					is.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}		
	}
	
	/**
	 * 业务申请
	 */
	public MoneyBoxTransApplyReqBean getTransApply(PublicCashOpenBean transBean)throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		map.put("IDNum", transBean.getReadIdcard());//身份证
		map.put("OrderNum", transBean.getOrderNo());//订单号
		map.put("Password", transBean.getOrderPwd());//密码
		map.put("Account", "");//客户账户
		map.put("Amount", String.valueOf(transBean.getMoney()));//交易金额
		map.put("CartonNum", "");//尾箱号
		map.put("TellerNum", GlobalParameter.tellerNo);//柜员号
		map.put("TermNum", GlobalParameter.machineNo);//终端号
		map.put("TransType", "BillDeposit");//交易类型
		
		SocketClient sc = new SocketClient();
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			socket = sc.createSocket();
            //构建IO  
            is = socket.getInputStream();  
            os = socket.getOutputStream();  
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(SocketClient.moneyBoxTransApply(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			
			String retMsg = "";
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", MoneyBoxTransApplyReqBean.class);
			MoneyBoxTransApplyReqBean moneyBoxTransApplyReqBean = (MoneyBoxTransApplyReqBean)reqXs.fromXML(retMsg);
			logger.info(moneyBoxTransApplyReqBean);
			logger.info("CLIENT retMsg:" + retMsg);

			return moneyBoxTransApplyReqBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			return null;
		} catch (IOException e) {
			logger.error(e);
			return null;
		}finally{
			if(socket != null){
				try {
					br.close();
					bw.close();
					is.close();
					os.close();
					socket.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}		
	}

	public void saveAccountOrder(PublicCashOpenBean transBean,AccountDepositApi accountDepositApi,String certNo,String rep02808,String rep07506)throws Exception{
		Map<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put("TERMINAL_CODE", GlobalParameter.macFacNum);//机器号
		saveMap.put("RATE", transBean.getRate());//利率
		saveMap.put("PRODUCT_NAME", transBean.getProductName());//产品名称
		saveMap.put("PRODUCT_CODE", transBean.getProductCode());//产品编号
		saveMap.put("UNIT_CODE", GlobalParameter.branchNo);//机构编号
		saveMap.put("UNIT_NAME", GlobalParameter.unitName);//机构名称
		saveMap.put("DEPOSIT_PERIOD", transBean.getMonthsUpperStr());//存期
		saveMap.put("DEPOSIT_AMT", String.valueOf(transBean.getMoney()));//金额
		saveMap.put("DEPOSIT_RESAVE_ENABLED", "1".equals(transBean.getAutoSaving())?"1":"0");//自动转存
		saveMap.put("CARD_NO", "");//卡号
		saveMap.put("SUB_ACCOUNT_NO", transBean.getSubCardNo());//子账户
		saveMap.put("CUSTOMER_NAME", transBean.getCardName());//卡姓名
		saveMap.put("DEPOSIT_PASSWORD_ENABLED", transBean.getSubPwd().length()>5?"1":"2");//子账户卡密码
		saveMap.put("CERT_NO", certNo);//凭证号
		saveMap.put("PAY_TYPE", transBean.getPayType());//支付类型
		accountDepositApi.saveAccountOrder(saveMap, rep02808, rep07506, "");
	}
	private float getFloatRet(String area,String chl,String birth,String time,String comb){
		return toFloat(area)+toFloat(chl)+toFloat(birth)+toFloat(time)+toFloat(comb);
	}
	private float toFloat(String f){
		if(f!=null){
			try
			{
				f=f.trim();
				return Float.parseFloat(f);
			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
		}
		return 0.000f;
	}
	/*凭证信息修改*/
	public Tms0005ResBean Tms0006(Map<String,String> map)throws Exception{
		SocketClient sc = new SocketClient();
		Socket socket=null;
		try {
			 socket =sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0006(map));
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0005ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", Tms0005ResBodyBean.class);
			Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0005ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return tms0005ResBean;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;
	}

	
	/*查询凭证信息*/
	public Tms0005ResBean Tms0005()throws Exception{
		SocketClient sc = new SocketClient();
		Socket socket = null;
		try {
			socket = sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0005());
			// 响应
			String retMsg = sc.response(socket);
			
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0005ResBean.class);
			reqXs.alias("Head", InResBean.class);
			reqXs.alias("Body", Tms0005ResBodyBean.class);
			Tms0005ResBean tms0005ResBean = (Tms0005ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0005ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return tms0005ResBean;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.closeSocket(socket);
		}
		return null;
	}
	
	/**
	 * 下一步
	 */
	private void nextStep(PublicCashOpenBean transBean){
		clearTimeText();
		//开户成功页面MONEYBOX_JXC_SUCCESS_PAGE
		logger.info("下一步进入存入成功页面");
		openPanel(new MoneyBoxSuccessDepPanel(transBean));
				
	}
	/**
	 * 跳转积享存成功页
	 */
	private void nextPage(PublicCashOpenBean transBean)throws Exception{
		clearTimeText();
		openPanel(new MoneyBoxJXCSuccessPage(transBean));
		
	}
	/**
	 * 跳转加息劵页面
	 */
	private void JxPage(PublicCashOpenBean transBean)throws Exception{
		clearTimeText();
		openPanel(new MoneyBoxAgainRedPacketPanel(transBean));
			
	}
	/**
	 * 协议存款存期转换（除幸福1+1）
	 * @param str
	 * @return
	 */
	private String [] getField4Period(String str){
		try{
			str=str.toUpperCase();
			Integer n=Integer.parseInt(str.replaceAll("\\D",""));
			if(str.indexOf("D")!=-1){
				return new String[] { "D", n+"", n+"天" };
			}else if(str.indexOf("M")!=-1){
				return new String[] { "M", n+"", n+"个月" };
			}else if(str.indexOf("Y")!=-1){
				return new String[] { "Y", n+"", n+"年" };
			}
		}catch (Exception e){
			logger.error("存期转换错误，原存期："+str,e);
		}
		return new String[] { "", "","" };
	}
	/**
	 * 幸福存期转换
	 * @return
	 */
	private String [] getField4PeriodXF(){
		return new String[] {  "Y","1", "1年+1天" };
	}
	
	
	/**
	 * 生成图片，上传事后监督
	 */
	public boolean ftpupLoad(PublicCashOpenBean transBean) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		String flag = transBean.getImportMap().get("agent_persion");
		if (flag != null && "yes".equals(flag)) {
			map.put("agentFlag", "1");// 1-存在代理人 2-不存在代理人
		} else {
			map.put("agentFlag", "2");
		}
		map.put("canelBillId",transBean.getSvrJrnlNo().trim());// 核心流水号
		map.put("canelBillDTransDate", transBean.getSvrDate().trim());// 开户日期
		map.put("accName", transBean.getCardName().trim());// 户名
		map.put("branchNo", GlobalParameter.branchNo);// 机构号
		map.put("transType", "存单开户");// 交易类型
		map.put("proCode", transBean.getProductCode());//产品编号
		map.put("amount", transBean.getMoney()+"".trim());// 交易金额
		map.put("accNo", transBean.getSubCardNo().trim());// 账号
//		map.put("billNo", transBean.getBillNo());// 凭证号
		map.put("orderNo", transBean.getOrderNo().trim());// 订单号
		// 授权号
		String supTellerNo = transBean.getSupTellerNo();
		//手动授权号
		String handSupTellerNo = transBean.getHandSupTellerNo();
		if (supTellerNo == null) {
			supTellerNo = "无";
		}
		map.put("supTellerNo", supTellerNo);// 授权号

		if (handSupTellerNo == null) {
			handSupTellerNo = "无";
		}
		map.put("handSupTellerNo", handSupTellerNo);//手动授权号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("idCardName", transBean.getCardName());// 本人姓名
		map.put("idCardNo", transBean.getIdCardNo());// 本人身份证号
		map.put("qfjg", transBean.getQfjg());// 签发机关
		map.put("teller", GlobalParameter.tellerNo);// 操作员
		map.put("busType", "存单开户");// 业务类型
		map.put("agentIdCardName", transBean.getAgentIdCardName());// 代理人姓名
		map.put("agentIdCardNo", transBean.getAgentIdCardNo());// 代理人卡号
		map.put("agentqfjg", transBean.getAgentqfjg());// 代理人签发机关
		map.put("bill_face", transBean.getBillPath_fc());
		map.put("bill_verso", transBean.getBillPath_rc());
		if(transBean.getInterestCount()!=null && !"".equals(transBean.getInterestCount()) && Double.valueOf(transBean.getInterestCount())>0){
			map.put("xydCount", transBean.getInterestCount());//幸运豆金额
		}
		if(transBean.getZydCount()!=null && !"".equals(transBean.getZydCount()) && Double.valueOf(transBean.getZydCount())>0){
			map.put("zydCount", transBean.getZydCount());//增益豆金额
		}
		if(transBean.getXfdCount()!=null && !"".equals(transBean.getXfdCount()) && Double.valueOf(transBean.getXfdCount())>0){
			map.put("xfdCount", transBean.getXfdCount());//消费豆金额
		}
		if ("yes".equals(transBean.getImportMap().get("customer_identification"))){
			map.put("customer_identification", "yes");//是否新建客户 
			map.put("tel", transBean.getTel());//手机号
			map.put("CUST_NAME", transBean.getIdCardName());//用户名
			map.put("DOMICILE_PLACE",transBean.getAddress());//地址
			map.put("CUST_SEX", transBean.getSex().trim().equals("男")? "1":"2");//性别
//			map.put("ID_TYPE", "1");//证件类型
			map.put("ID_NO",transBean.getReadIdcard());//证件号码
			map.put("ISSUE_INST", transBean.getQfjg());//签发机关
//			map.put("CUST_TYPE", "1");//客户类型
			map.put("BUS_STATUS", transBean.getJobs());//职业
			map.put("custSvrNo", transBean.getCustSvrNo());//交易流水号
		}else{
			map.put("customer_identification", "no");
		}
		logger.info("生成事后监督内容："+map);
		boolean isupload = true;// 标记是否上传成功
		JpgUtil cg = new JpgUtil();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration1(map);
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
    		sshSession = jsch.getSession(Property.FTP_USER_KH, Property.FTP_IP_KH, Integer.parseInt(Property.FTP_PORT_KH));
    		logger.info("Session created.");
    		sshSession.setPassword(Property.FTP_PWD_KH);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
//    		logger.info("Session connected.");
//    		logger.info("Opening Channel.");
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		logger.info("Connected to " + Property.FTP_IP_KH + ".");
    		
    		String nowDate = DateUtil.getNowDate("yyyyMMdd");
    		// 上传的目录
    		String ftpPath = Property.FTP_SER_PATH_KH +"/"+ nowDate;
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
//    			file.delete();
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
	
	//开户后获取增益豆信息
	public List<accOpenProFileBean02808And03870> getZYDInfo(BCK02808ResBean openAccount){
		logger.info("开户后查询衍生品文件");
		String fileName = openAccount.getBody().getPRO_NAME();
		if(fileName == null || "".equals(fileName.trim())){
			logger.info("开户衍生品文件下载路径为空");
		}
		List<accOpenProFileBean02808And03870> list  = null;
		try {
			// 下载文件
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, accOpenProFileBean02808And03870.class);
		} catch (Exception e) {
			logger.error("开户衍生品文件下载失败"+e);
		}
		if(list == null){
			
			logger.info("开户衍生品信息获取失败");
			
		}else if(list.size() == 0){
			
			logger.info("开户衍生品返回文件信息为空");
		}
		return list;
	}
}
