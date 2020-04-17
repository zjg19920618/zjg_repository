package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PublicProPanel.AccInputBankCardPassword;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.EAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.TextFileReader;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK03519ResBean;
import com.boomhope.tms.message.in.bck.BCK03521ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * title:银行卡密码校验
 * @author ly
 * 2016年11月9日上午10:39:35
 */
public class TransPassingPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	Timer checkTimer;
	static Logger logger = Logger.getLogger(TransPassingPanel.class);
	
	private boolean checkResult=true;//查询子账户列表结果
	public TransPassingPanel(final BillPrintBean transBean,final List<ICBankBean> bankBeans) {

		this.billPrintBean = transBean;
		this.bankBeans = bankBeans;
		logger.info("进入银行卡密码校验页面");
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/processing.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(404, 227, 200, 200);
		this.showJpanel.add(billImage);
		
		JLabel label = new JLabel("银行卡密码校验中，请稍后...");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.decode("#412174"));
		label.setFont(new Font("微软雅黑", Font.BOLD, 40));
		label.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(label);
		
		/* 倒计时进入接口调用 */
		checkTimer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTimer(checkTimer);
				cheakICBankPwd(transBean,bankBeans);

			}
		});
		checkTimer.start();

	}
	
	private void cheakICBankPwd(final BillPrintBean transBean,final List<ICBankBean> bankBeans) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(transBean.getAccNo().startsWith("6231930008")){
					try {
						transBean.getReqMCM001().setReqBefor("07601");
						Map map = InterfaceSendMsg.inter07601(transBean);
						transBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
						if(!"000000".equals(map.get("resCode"))){
							String resMsg = (String)map.get("errMsg");
							if(resMsg.startsWith("A102")){
								logger.error(resMsg);
								openConfirmDialog("密码输入错误，是否重新输入。是：重新输入密码，否：退出业务。");
								confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										clearTimeText();
										openPanel(new TransInputBankCardPassPanel(transBean,bankBeans));
										return;
									}
								});
								confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										clearTimeText();
										returnHome();
										return;
									}
								});
							}else{
								clearTimeText();
								MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
								serverStop("请联系大堂经理",resMsg, "");
								return;
							}
						}
					} catch (Exception e) {
						logger.error("电子账户验证密码失败(07601):"+e);
						transBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						clearTimeText();
						serverStop("电子账户查询信息失败，请联系大堂经理。", "", "调用接口07601异常");
						return;
					}
				}else{
					String accNo = transBean.getAccNo();
					String passWord = transBean.getInputOrderPwd();
					Map<String,String> map = new HashMap<String,String>();
					map.put("acctNo", accNo);
					map.put("pinValFlag", "1");
					map.put("password", passWord);
					// 读卡成功，调用接口
					transBean.getReqMCM001().setReqBefor("03521");
					BCK03521ResBean bean = getBankInfo(map);
					if(bean== null){
						transBean.getReqMCM001().setIntereturnmsg("调用03521银行卡信息查询失败");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						serverStop("请联系大堂经理","", "银行卡信息查询失败！");
						return;
					}
					String resCode = bean.getHeadBean().getResCode();
					String resMsg = bean.getHeadBean().getResMsg();
					logger.info("卡信息查询resCode："+resCode);
					logger.info("卡信息查询resMsg："+resMsg);
					transBean.getReqMCM001().setReqAfter(resCode,resMsg);
					if(!"000000".equals(resCode)){
						if(resMsg.startsWith("A102")){
							logger.error(resMsg);
							openConfirmDialog("密码输入错误，是否重新输入。是：重新输入密码，否：退出业务。");
							confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									clearTimeText();
									openPanel(new TransInputBankCardPassPanel(transBean,bankBeans));
									return;
								}
							});
							confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseReleased(MouseEvent e) {
									clearTimeText();
									returnHome();
									return;
								}
							});
						}else{
							clearTimeText();
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							serverStop("请联系大堂经理",resMsg, "");
						}
						return;
					}
					
					try {
						transBean.getReqMCM001().setReqBefor("03845");
						Map map03845 = InterfaceSendMsg.inter03845(transBean);
						transBean.getReqMCM001().setReqAfter((String)map03845.get("resCode"),(String)map03845.get("errMsg"));
						if(!"000000".equals(map03845.get("resCode"))){
							logger.info("查询失败："+map03845.get("errMsg"));
							MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
							clearTimeText();
							serverStop("查询银行卡信息失败，请联系大堂经理。", (String)map03845.get("errMsg"),"");
							return;
						}
					} catch (Exception e) {
						logger.error("查询卡信息失败：(03845)"+e);
						transBean.getReqMCM001().setIntereturnmsg("调用查询接口03845异常");
						MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
						clearTimeText();
						serverStop("查询银行卡信息失败，请联系大堂经理。", "", "调用查询接口03845异常");
						return;
					}
				}
				//跳转
				nextstep(transBean);
			}
		}).start();
	}
	private BCK03521ResBean getBankInfo(Map<String, String> map) {
		String retMsg = "";
		Socket socket = null;
		SocketClient sc = new SocketClient();
		try {
			socket = sc.createSocket();
			// 构建IO
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
					"UTF-8"));
			// 向服务器端发送一条消息
			String s=sc.BCK_0019(map);
			logger.info("03521请求信息"+s);
			bw.write(s + "\n");
			bw.flush();

			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)) {
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03521ResBean.class);
			BCK03521ResBean bck0019ResBean = (BCK03521ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0019ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0019ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			sc.closeSocket(socket);
		}
		return null;
	}
	
	/*银行卡密码校验成功页面跳转*/
	private void nextstep(final BillPrintBean transBean){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if("0".equals(transBean.getPrintChoice())){
					if("bankCard".equals(transBean.getImportMap().get("bankCardPath"))){
						clearTimeText();
						openPanel(new TransPrintOrStateChage(transBean));
//						if(transBean.isPrint()==true){
//							clearTimeText(); 
//							openPanel(new TransProcessingPanel(transBean, bankBeans));
//						}else if(transBean.isPrint()==false){
//							clearTimeText();
//							openPanel(new TransPrintOrStateChage(transBean));
//						}
					}else {
						//身份证查询
						if("0".equals(transBean.getChoose())){
							//打印页面
							clearTimeText();
							openPanel(new TransPrintBankMimeographs(transBean, bankBeans));
						}else if("1".equals(transBean.getChoose())){
							//状态变更页面
							clearTimeText();
							openPanel(new TransPrintBillStateChage(transBean, bankBeans));
						}
//						if(transBean.isPrint()){
//							clearTimeText(); 
//							openPanel(new TransProcessingPanel(transBean, bankBeans));
//						}else if(transBean.isPrint()==false){
//							//身份证查询
//							if("0".equals(transBean.getChoose())){
//								//打印页面
//								clearTimeText();
//								openPanel(new TransPrintBankMimeographs(transBean, bankBeans));
//							}else if("1".equals(transBean.getChoose())){
//								//状态变更页面
//								clearTimeText();
//								openPanel(new TransPrintBillStateChage(transBean, bankBeans));
//							}
//						}
					}
				}else{
					findSubAccInfos();
				}
			}
		});
	}
	
	//查询子账户列表信息
	private void findSubAccInfos(){
		if("电子账户".equals(billPrintBean.getCardType())&&billPrintBean.getAccNo().startsWith("6231930008")){
			//将电子子账户合并
			List<EAccInfoBean> elist= (List<EAccInfoBean>)billPrintBean.getSubInfo().get("eCardInfo");
			List<BillPrintSubAccInfoBean> blist = new ArrayList<BillPrintSubAccInfoBean>();
			for (EAccInfoBean bean: elist) {
				if("0".equals(bean.geteMark())&&!"0010".equals(bean.geteProductCode().trim())&&!bean.geteProductCode().startsWith("TB")){
					BillPrintSubAccInfoBean billBean=new BillPrintSubAccInfoBean();
					billBean.setAccNo(bean.geteCardNo());//卡号
					billBean.setSubAccNo(bean.geteSubAccNo());//子账号
					billBean.setProductName(bean.geteProductName());//产品名称
					billBean.setProductCode(bean.geteProductCode());//产品代码
					billBean.setOpenATM(bean.geteOpenATM());//开户金额
					billBean.setOpenDate(bean.geteOpenDate());//开户日
					billBean.setEndIntDate(bean.geteEndDate());//到期日
					billBean.setDepTerm(bean.geteDepTerm());//存期
					billBean.setChannel("0028");//开户渠道
					billBean.setOpenRate(bean.geteOpenRate());//开户利率
					blist.add(billBean);
				}
			}
			billPrintBean.getSubInfo().put("SUB_ACC_NUMS",blist.size());//数据数量
			billPrintBean.getSubInfo().put("SUB_ACC_MSG", blist);
			//跳转至下一个页面
			goOn();
		}else{
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Map<String,String> map = new HashMap<String,String>();
					map.put("CARD_NO", billPrintBean.getAccNo());
					billPrintBean.getReqMCM001().setReqBefor("03519");
					BCK03519ResBean resBean = getSubInfo(map);
					
					if(resBean== null){
						billPrintBean.getReqMCM001().setIntereturnmsg("调用03519接口异常");
						MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
						serverStop("请联系大堂经理","", "子账户信息查询失败！");
						checkResult=false;
						return;
					}
					String resCode = resBean.getHeadBean().getResCode();
					String resMsg = resBean.getHeadBean().getResMsg();
					logger.info("卡信息查询resCode："+resCode);
					logger.info("卡信息查询resMsg："+resMsg);
					billPrintBean.getReqMCM001().setReqAfter(resCode,resMsg);
					if(!"000000".equals(resCode)){
						MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
						serverStop("请联系大堂经理",resMsg, "");
						checkResult=false;
						return;
					}
					
					String fileName = resBean.getBody().getFILE_NAME();
					List<BillPrintSubAccInfoBean> list  = null;
					List<BillPrintSubAccInfoBean> list1 = new ArrayList<BillPrintSubAccInfoBean>();
					try {
						// 下载文件
						FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
						list = TextFileReader.paddingList(Property.FTP_LOCAL_PATH + fileName, BillPrintSubAccInfoBean.class);
						if(list.size()>0){
							for(BillPrintSubAccInfoBean subBean:list){
								if("0".equals(subBean.getMark()) && !subBean.getProductCode().startsWith("TB")&&!"0010".equals(subBean.getProductCode().trim())){
									list1.add(subBean);
								}
							}
						}
					} catch (Exception e) {
						logger.error("子账户列表信息下载失败"+e);
						clearTimeText();
						checkResult=false;
						serverStop("请联系大堂经理",resMsg, "");
						return;
					}
					billPrintBean.getSubInfo().put("SUB_ACC_NUMS",list1.size());//数据数量
					billPrintBean.getSubInfo().put("SUB_ACC_MSG", list1);
					//跳转至下一个页面
					goOn();
				}
		}).start();
		}
	}

	//跳转下一个打印协议的页面
	public void goOn(){
		if(!checkResult){
			return;
		}
		if(billPrintBean.getSubInfo().get("SUB_ACC_MSG")==null||(Integer)billPrintBean.getSubInfo().get("SUB_ACC_NUMS")==0){
			serverStop("该账户没有可以打印协议书的子账户", "", "");
			return;
		}else{
			clearTimeText();
			openPanel(new TransChoiceSubInfoPanel());
		}
	}
	
	private BCK03519ResBean getSubInfo(Map<String, String> map) {
		String retMsg = "";
		Socket socket = null;
		SocketClient sc = new SocketClient();
		try {
			socket = sc.createSocket();
			// 构建IO
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,
					"UTF-8"));
			// 向服务器端发送一条消息
			String s=sc.BCK_03519(map);
			logger.info("03519请求信息"+s);
			bw.write(s + "\n");
			bw.flush();

			// 读取服务器返回的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)) {
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK03519ResBean.class);
			BCK03519ResBean bck03519ResBean = (BCK03519ResBean)reqXs.fromXML(retMsg);
			logger.info(bck03519ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck03519ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
			checkResult=false;
			serverStop("请联系大堂经理","", "银行卡信息查询失败！");
		} catch (IOException e) {
			logger.error(e);
			checkResult=false;
			serverStop("请联系大堂经理","", "银行卡信息查询失败！");
		} finally {
			sc.closeSocket(socket);
		}
		checkResult=false;
		return null;
	}
	
}
