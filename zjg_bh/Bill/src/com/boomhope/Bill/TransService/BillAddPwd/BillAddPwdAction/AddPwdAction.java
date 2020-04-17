package com.boomhope.Bill.TransService.BillAddPwd.BillAddPwdAction;

import java.awt.Component;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.SystemCache;

import com.boomhope.Bill.Framework.BaseLoginFrame;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;
import com.boomhope.Bill.TransService.BillAddPwd.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.BillAddPwd.ServicePanel.SetPasswordSuccessPanel;
import com.boomhope.Bill.TransService.BillAddPwd.ServicePanel.SetPwdCameraPanel;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil_ZM;
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

public class AddPwdAction extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(AddPwdAction.class);
	private static final long serialVersionUID = 1L;
	
	//本人身份核查
	public void mePolicecheck1(final Component comp,final AddPasswordBean addPwdBean) {
		     //查询黑灰名单
				if(!meCheckTelephoneFraud(comp,addPwdBean)){
					return;
				}
				
				//身份证联网核查
				if(!meCheckIdCardInfo(comp,addPwdBean)){
					return;
				}
					logger.info("进入拍照页面");
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关闭语音
	            	closeVoice();//关闭语音流
					openPanel(comp,new SetPwdCameraPanel());
	}
	/**
	 * 本人身份证黑灰名单
	 */
	public boolean meCheckTelephoneFraud(Component thisComp,final AddPasswordBean addPwdBean){
		logger.info("本人身份证黑名单查询接口");
		try {
			addPwdBean.getReqMCM001().setReqBefor("01597");
			Map me01597 = IntefaceSendMsg.me01597(addPwdBean); 
			addPwdBean.getReqMCM001().setReqAfter((String)me01597.get("resCode"),(String)me01597.get("errMsg"));
			if(!"000000".equals(me01597.get("resCode"))){
				
				if ("0010".equals(me01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					addPwdBean.getReqMCM001().setIntereturnmsg((String)me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
					
				} else if ("0020".equals(me01597.get("resCode"))) {
					
					prossDialog.disposeDialog();
					logger.info((String) me01597.get("errMsg"));
					addPwdBean.getReqMCM001().setIntereturnmsg((String)me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
					serverStop(thisComp,(String)me01597.get("errMsg"), "","");
					return false;
				}else{
					
					prossDialog.disposeDialog();
					logger.info(me01597.get("errMsg"));
					addPwdBean.getReqMCM001().setIntereturnmsg((String)me01597.get("errMsg"));
					MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
					serverStop(thisComp, "查询黑灰名单信息失败，请联系大堂经理", (String)me01597.get("errMsg"),"");
					return false;
				}
			}
		} catch (Exception e) {
			prossDialog.disposeDialog();
			logger.error("调用01597查询黑灰名单信息失败"+e);
			addPwdBean.getReqMCM001().setIntereturnmsg("调用01597接口异常!");
			MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
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
	public boolean meCheckIdCardInfo(final Component thisComp,final AddPasswordBean addPwdBean){
		logger.info("本人调用联网核查接口");
		try {
			addPwdBean.getReqMCM001().setReqBefor("07670");
			Map me07670 =IntefaceSendMsg.me07670(addPwdBean);
			addPwdBean.getReqMCM001().setReqAfter((String)me07670.get("resCode"),(String)me07670.get("errMsg"));
			if(!"000000".equals(me07670.get("resCode"))){
				logger.info("本人联网核查失败："+me07670.get("errMsg"));
				prossDialog.disposeDialog();
				addPwdBean.getReqMCM001().setIntereturnmsg((String)me07670.get("errMsg"));
				MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
				serverStop(thisComp, "本人联网核查失败，请联系大堂经理", (String)me07670.get("errMsg"), "");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("调用联网核查接口07670失败"+e);
			prossDialog.disposeDialog();
			addPwdBean.getReqMCM001().setIntereturnmsg("调用07670接口异常");
			MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
			serverStop(thisComp, "调用联网核查接口失败，请联系大堂经理", "","调用07670接口异常");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 增设密码处理
	 */
	public boolean ZSPassWord(final Component comp,final AddPasswordBean addPwdBean){
		logger.info("增设密码处理中");
		try {
			addPwdBean.getReqMCM001().setReqBefor("07517");
			Map inter07517 =IntefaceSendMsg.inter07517(addPwdBean);
			addPwdBean.getReqMCM001().setReqAfter((String)inter07517.get("resCode"),(String)inter07517.get("errMsg"));
			if(!"000000".equals(inter07517.get("resCode"))){
				logger.info("增设密码："+inter07517.get("errMsg"));
				prossDialog.disposeDialog();//关闭弹框
				addPwdBean.getReqMCM001().setIntereturnmsg((String)inter07517.get("errMsg"));
				MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
				serverStop(comp, "增设密码失败，请联系大堂经理", (String)inter07517.get("errMsg"), "");
				return false;
			}
			
			//背书打印
			sleep(1000);
			try {
				print();
			} catch (Exception e1) {
				logger.error("背书打印失败"+e1);
			}
			//生成事后监督图片
			uploadPhoto(addPwdBean);
			
			addPwdBean.getReqMCM001().setIntereturncode((String)inter07517.get("resCode"));
			addPwdBean.getReqMCM001().setIntereturnmsg((String)inter07517.get("errMsg"));
			addPwdBean.getReqMCM001().setCentertrjnno(addPwdBean.getZMSvrJrnlNo());
			addPwdBean.getReqMCM001().setAccount(addPwdBean.getAccNo());
			addPwdBean.getReqMCM001().setCustomername(addPwdBean.getAccName());
			addPwdBean.getReqMCM001().setTransamt(addPwdBean.getTotalAmt());
			addPwdBean.getReqMCM001().setUsevouchertype("102");
			addPwdBean.getReqMCM001().setUsevoucherno(addPwdBean.getCertNo());
			addPwdBean.getReqMCM001().setTransresult("0");
			MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
			
			closeVoice();
	    	stopTimer(voiceTimer);//关闭语音
	    	clearTimeText();//清空倒计时
			//增设密码成功
			openPanel(comp,new SetPasswordSuccessPanel());
		} catch (Exception e) {
			logger.error("调用增设密码处理接口07517失败"+e);
			prossDialog.disposeDialog();
			addPwdBean.getReqMCM001().setIntereturnmsg("调用07517接口异常");
			MakeMonitorInfo.makeInfos(addPwdBean.getReqMCM001());
			serverStop(comp, "增设密码失败，请联系大堂经理", "","调用07517接口异常");
			return false;
		}
		
		return true;
	}
	/**
	 * 上传事后监督
	 */
	public void uploadPhoto(final AddPasswordBean addPwdBean){
		Map <String,String> map = new HashMap<String,String>();
		map.put("ZMBillId", addPwdBean.getZMSvrJrnlNo());//  增密核心流水号
		map.put("ZMBillDTransDate", addPwdBean.getSvrDate()+" "+DateUtil.getDateTime("HH:mm:ss"));// 增密日期
		map.put("accName", addPwdBean.getAccName());//  户名
		map.put("proName", addPwdBean.getProName());//产品名称
		map.put("AccNo", addPwdBean.getAccNo());//  账号
		map.put("branchNo", GlobalParameter.branchNo);//  机构号
		if("3".equals(addPwdBean.getSubAccNoCancel())){
			map.put("transType", "存单增设密码");//  交易类型
		}else if("0".equals(addPwdBean.getSubAccNoCancel())){
			map.put("transType", "存单增设密码");//  交易类型
		}else{
			map.put("transType", "存单增设密码");//  交易类型
		}
		if(addPwdBean.getOpenDate()!=null && !"".equals(addPwdBean.getOpenDate())){
			map.put("openDate", addPwdBean.getOpenDate());//开户日期
		}else{
			map.put("openDate", "");//开户日期
		}
		
		map.put("amount",addPwdBean.getTotalAmt().trim());//  交易金额
		map.put("billNo", addPwdBean.getCertNo());//  凭证号
		map.put("supTellerNo", addPwdBean.getSupTellerNo());// 授权号
        if(addPwdBean.getTellNo1()==null){
			
			map.put("supTellerNo1", "无");// 授权号1
		}else{
			map.put("supTellerNo1", addPwdBean.getTellNo1());// 授权号1
		}
		if(addPwdBean.getTellNo2()==null){
			
			map.put("supTellerNo2", "无");// 授权号2
		}else{
			map.put("supTellerNo2", addPwdBean.getTellNo2());// 授权号2
		}
		if(addPwdBean.getTellNo3()==null){
			
			map.put("supTellerNo3", "无");// 授权号3
		}else{
			map.put("supTellerNo3", addPwdBean.getTellNo3());// 授权号3
		}
		map.put("tellerNo", GlobalParameter.tellerNo);//柜员号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("bill_face",addPwdBean.getBillPath_fc());//存单正面照
		map.put("bill_verso",addPwdBean.getBillPath_rc());//存单反面照
		
		map.put("idCardName", addPwdBean.getReadIdName());//  本人姓名
		map.put("idCardNo", addPwdBean.getReadIdNo());// 本人身份证号
		map.put("idtype", addPwdBean.getIdType());//证件类型
		map.put("qfjg", addPwdBean.getQfjg());// 签发机关
		map.put("fileName", addPwdBean.getSvrDate().trim()+addPwdBean.getZMSvrJrnlNo().trim());
		        //生成电子印章
				YiZhangUtil yizhang = new YiZhangUtil();
				try {
					boolean yiZhangPhoto = yizhang.getYiZhangPhoto(map);
					if(yiZhangPhoto){
						map.put("yz","true");
						logger.error("生成电子印章图片成功:"+yiZhangPhoto);
					}
				} catch (Exception e1) {
					logger.error("生成电子印章图片失败"+e1);
				}
				
				//生成事后图片
				JpgUtil_ZM cg = new JpgUtil_ZM();
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
		    		String ftpPath = Property.FTP_SER_PATH_ZM + nowDate+"/000007/";
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
			
			/**
			 * 增设密码信息
			 */
			public void print()throws Exception{
				//打印												
				String dy = "ZM";
				logger.debug("备注打印：BH_Print");
				Dispatch.call(BaseLoginFrame.dispath, "BH_Print",new Variant(dy));
				logger.debug("打印完睡10000ms等待程序响应");
		    	sleep(10000);
			}	
			
	
	
}
