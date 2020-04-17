package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.CardsInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.EAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.IdCardCheckInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.Base64Util;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 
 * title:人脸识别处理中
 * @author ly
 * 2016年11月7日下午9:58:25
 */
public class TransPrintFaceCheckPanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransPrintFaceCheckPanel.class);
	Timer checkTimer = null;
	public TransPrintFaceCheckPanel(BillPrintBean transBean) {
		logger.info("进入人脸识别处理页面");
		this.billPrintBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(60);
		delayTimer = new Timer(60 * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：人脸识别已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 

		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/face.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(367, 210, 276, 267);
		this.showJpanel.add(billImage);

		JLabel label = new JLabel("人脸识别处理中...");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setForeground(Color.decode("#412174"));
		label.setBounds(0, 130, 1009, 30);
		this.showJpanel.add(label);
		
		process(transBean);
	}

	/**
	 * 业务处理
	 * 
	 * @return
	 */
	public void process(final BillPrintBean transBean) {
		new Thread("人脸识别线程") {
			public void run() {
				// 获取联网核查图片路径
				String check_photo=null;
				String idCard_check_result = transBean.getImportMap().get("agent_persion");
				if("yes".equals(idCard_check_result)){
					//代理人
					check_photo=Property.BILL_ID_AGENT_FACE;
				}else{
					check_photo = Property.BILL_ID_SELF_FACE;
				}
				String img2 = null;
				String img1 = null;
				try {
					img1 = Base64Util.GetImageStr(check_photo);
					img2 = Base64Util.GetImageStr(Property.CAMERA_PATH);
				} catch (Exception e) {
					logger.error(e);
					serverStop("人脸识别失败，请联系大堂经理", "","人脸识别图片获取异常");
					return;
				}

				JSONObject parMap = new JSONObject();
				parMap.put("img1", img1);
				parMap.put("img2", img2);
				parMap.put("img1Type", "1");
				parMap.put("img2Type", "1");
				logger.debug(parMap.toString());
				// 人脸识别
				HttpClientUtil http = new HttpClientUtil();
				String result = http.post(Property.NEW_FACE_CHECK_SYS_PATH, parMap);
				if (result == null || "-1".equals(result)) {
					transBean.getReqMCM001().setIntereturnmsg("人脸识别系统连接异常");
					MakeMonitorInfo.makeInfos(transBean.getReqMCM001());
					// 返回失败
					serverStop("人脸识别失败，请联系大堂经理","","人脸识别系统连接异常，请检查网络是否畅通");
					return;
				}
				logger.info("人脸识别结果：" + result);
				JSONObject json = JSONObject.fromObject(result);
				String r = String.valueOf(json.get("result"));
				// 人脸识别失败
				if (r.equals("0")) {
					String exCode = String.valueOf(json.get("exCode"));
					String exMsg = String.valueOf(json.get("exMsg"));
					logger.info("人脸识别失败原因："+exCode+"--"+exMsg);
					openPanel(new TransferFaceCheckFail("人脸识别失败:"+exMsg+",请联系大堂经理", "",transBean));
					return;
				}
				
				double sim = Double.parseDouble(String.valueOf(json.get("sim")));
				double defaultSim = Double.parseDouble(String.valueOf(json.get("defaultSim")));
				logger.info("阈值："+defaultSim+"||||"+"相似度:"+sim);
				if (sim >= defaultSim) {//sim >= defaultSim
					//当人脸识别成功之后则调用接口
					String sims = String.format("%.2f",Double.parseDouble(json.getString("sim")));
					transBean.setFaceCompareVal(sims);
					
					if("yes".equals(idCard_check_result)){
						//代理人
						clearTimeText();
						openPanel(new BackTransInputIdcard1Panel(transBean));
					}else{
						if("0".equals(transBean.getPrintChoice().trim())){
							clearTimeText();
							openPanel(new TransPrintOrStateChage(transBean));
						}else{
							if(!getCardInfo()){
								return;
							}
							clearTimeText();
							openPanel(new TransPrintAgreementShowCardsPanel());
						}
					}
				} else {
					//当人脸识别失败，则跳转终止页，重新拍照
					clearTimeText();
					openPanel(new TransferFaceCheckFail("人脸识别未通过，请联系大堂经理", "",transBean));
				}

			}
		}.start();
	}
	
	/**
	 * 若打印协议，则直接查询身份信息并查询卡信息
	 */
	public boolean getCardInfo(){
		
		//个人客户信息查询
		try {
			billPrintBean.getReqMCM001().setReqBefor("07519");
			Map map =InterfaceSendMsg.inter07519(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("个人客户查询失败(07519)："+map.get("resCode")+map.get("errMsg"));
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				clearTimeText();
				serverStop("个人客户信息查询失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return false;
			}
			
			List<IdCardCheckInfo> list = (List<IdCardCheckInfo>)map.get("ID_INFO");
			if(list.size()>0){
				for(IdCardCheckInfo subInfo : list){
					if(subInfo.getId_No().equals(billPrintBean.getReadIdcard())){
						logger.info("对应的客户号："+subInfo.getCust_No());
						billPrintBean.setCustNo(subInfo.getCust_No());
					}
				}
			}else{
				logger.info("没有查到相应的客户号："+list.size());
				serverStop("没有查到相应的客户号，请联系大堂经理", "", "获取的文件中无内容");
				clearTimeText();
				return false;
			}
			
		} catch (Exception e) {
			logger.error("查询客户信息失败(07519):"+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("个人客户查询失败(07519)");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			clearTimeText();
			serverStop("查询客户信息失败，请联系大堂经理。", "", "个人客户查询失败(07519)");
			return false;
		}
		List<CardsInfo> list1=null;
		//查询身份证下的卡信息
		try {
			billPrintBean.getReqMCM001().setReqBefor("07520");
			Map map = InterfaceSendMsg.inter07520(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("所有卡信息查询失败(07520)："+map.get("resCode")+map.get("errMsg"));
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				clearTimeText();
				serverStop("所有卡信息查询失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return false;
			}
			List<CardsInfo> list = (List<CardsInfo>)map.get("CARDS_INFO");
			list1 = new ArrayList<>();
			if(list.size()>0){
				logger.info("银行卡数量："+list.size());
				for(CardsInfo cardInfo : list){
					if(cardInfo.getCard_State().startsWith("N")){
						list1.add(cardInfo);
					}else{
						continue;
					}
				}
			}else{
				logger.info("该身份证下没有卡信息：(获取的文件内容)"+list.size());
				clearTimeText();
				serverStop("该身份证下未查到任何银行卡信息", "", "银行卡条数:"+list.size());
				return false;
			}
			
			
			
		} catch (Exception e) {
			logger.error("查询所有卡信息失败(07520)："+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("调用所有卡信息查询接口失败(07520)");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			clearTimeText();
			serverStop("查询身份证下的所有卡信息失败，请联系大堂经理", "", "调用所有卡信息查询接口失败(07520)");
			return false;
		}
		//查询电子账户信息
		try {
			billPrintBean.getReqMCM001().setReqBefor("07819");
			Map map = InterfaceSendMsg.inter07819(billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"),(String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info("所有电子账户信息失败(07819)："+map.get("resCode")+map.get("errMsg"));
				billPrintBean.getReqMCM001().setIntereturnmsg((String)map.get("errMsg"));
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
				clearTimeText();
				serverStop("所有电子账户信息查询失败，请联系大堂经理。", (String)map.get("errMsg"), "");
				return false;
			}
			List<EAccInfoBean> list = (List<EAccInfoBean>)map.get("CARDS_INFO");
			List<EAccInfoBean> elist=new ArrayList<EAccInfoBean>();
			if(list.size()>0){
				logger.info("电子账户数量："+list.size());
				for(EAccInfoBean cardInfo : list){
					if("0".equals(cardInfo.geteAccNoState())){
						CardsInfo a=new CardsInfo();
						a.setCard_No(cardInfo.geteCardNo());
						a.setOpen_inst(cardInfo.geteOpenInstNo());
						a.setCard_Type("电子账户");
						list1.add(a);
					}
					break;
				}
			}
			if(list.size()>0){
				logger.info("电子账户数量："+list.size());
				for(EAccInfoBean cardInfo : list){
					if("0".equals(cardInfo.geteAccNoState())){
						elist.add(cardInfo);
					}else{
						continue;
					}
				}
			}
			if(elist.size()>0){
				logger.info("可用电子子账户数量："+elist.size());
				billPrintBean.getSubInfo().put("eCardInfo", elist);
			}
		} catch (Exception e) {
			logger.error("所有电子账户信息查询失败(07819)："+e);
			billPrintBean.getReqMCM001().setIntereturnmsg("所有电子账户信息查询失败(07819)");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			clearTimeText();
			serverStop("所有电子账户信息查询失败，请联系大堂经理", "", "所有电子账户信息查询失败(07819)");
			return false;
		}
		if(list1.size()>0){
			logger.info("可用银行卡数量："+list1.size());
			billPrintBean.getSubInfo().put("CardInfo", list1);
		}else{
			logger.info("该身份证下无可用卡(获取的正常卡数量)"+list1.size());
			clearTimeText();
			serverStop("该身份证下无正常状态的卡", "", "可用银行卡数量："+list1.size());
			return false;
		}
		return true;
	}

}
