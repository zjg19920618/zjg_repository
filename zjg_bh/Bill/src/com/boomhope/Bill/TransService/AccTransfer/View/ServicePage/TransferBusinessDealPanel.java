package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Framework.MainFrame;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.CheckConfirmDialog;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.JpgUtil_HY;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
/**
 * 业务处理提示
 * @author hao
 *
 */
public class TransferBusinessDealPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(TransferBusinessDealPanel.class);
	private static final long serialVersionUID = 1L;
	private PublicAccTransferBean transferMoneyBean;
	private Component comp;
	private boolean result;
	/**
	 * 初始化
	 */
	public TransferBusinessDealPanel(final PublicAccTransferBean transferBean) {
		logger.info("进入业务处理提示页面");
		transferMoneyBean =transferBean;
		comp = this;
		
		//设置倒计时
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start();

		JLabel lblNewLabel = new JLabel("系统正在处理您的汇款业务，请稍候...");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 180, 1009, 189);
		this.showJpanel.add(lblNewLabel);
		
		checkMothed();
				
	
		
	}
	
	/**
	 * 调用转账处理
	 */
	public boolean doTransing(){
		
		if(transferMoneyBean.getTransMarkNo()==null ||"".equals(transferMoneyBean.getTransMarkNo())){
			openMistakeDialog("请选择到账方式");
			return false;
		}else if("0".equals(transferMoneyBean.getTransMarkNo())){
			
			//调用机构号查询当前交易机构
			if(!checkSendBankNo()){
				return false ;
			}
			try {
				logger.info("调用行内转账接口");
				transferMoneyBean.getReqMCM001().setReqBefor("02600");
				Map inter02600 = InterfaceSendMsg.inter02600(transferMoneyBean);
				transferMoneyBean.getReqMCM001().setReqAfter((String)inter02600.get("resCode"), (String)inter02600.get("errMsg"));
				if(!"000000".equals(inter02600.get("resCode"))){
					
					logger.error((String) inter02600.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
					serverStop("您的业务操作失败,请联系大堂经理", (String) inter02600.get("errMsg"),"");
					return false;
				}
			} catch (Exception e) {
				logger.error("调用行内转账失败");
				transferMoneyBean.getReqMCM001().setIntereturnmsg("调用02600接口异常");
				MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
				serverStop("抱歉,您的业务在调用转账操作时失败,请联系大堂经理","","调用行内转账接口异常");
				return false;
			}		
		}else if("1".equals(transferMoneyBean.getTransMarkNo())){
			try {
				logger.info("调用跨行大额接口");
				transferMoneyBean.getReqMCM001().setReqBefor("02013");
				Map inter02013 = InterfaceSendMsg.inter02013(transferMoneyBean);
				transferMoneyBean.getReqMCM001().setReqAfter((String)inter02013.get("resCode"), (String)inter02013.get("errMsg"));
				if(!"000000".equals(inter02013.get("resCode"))){
					
					if("721107".equals(inter02013.get("resCode"))){
						final CheckConfirmDialog confirmDialog=new CheckConfirmDialog(MainFrame.mainFrame, true,"");
						confirmDialog.showDialog("非人行支付系统运行时间，是否继续？是：立即受理，待下一工作日自动处理;否：请选择其他到账方式;");
						confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								logger.info("选择‘是’");
								clearTimeText();
								closeDialog(confirmDialog);
								transferMoneyBean.setNextDaySendFlag("3");
								transferMoneyBean.setZhangWay("预约实时到账");
								openPanel(new TransferBusinessDealPanel(transferMoneyBean));
							}
						});
						confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								logger.info("选择‘否’");
								clearTimeText();
								closeDialog(confirmDialog);
								openPanel(new OutputBankCardPanel());
							}
						});
						return false;
					}else{
						logger.error((String) inter02013.get("errMsg"));
						transferMoneyBean.setBillMsg((String) inter02013.get("errMsg"));
						MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
						serverStop("您的业务操作失败", (String) inter02013.get("errMsg"),"");
						return false;
					}
				}
			} catch (Exception e) {
				logger.error("调用跨行大额转账失败"+e);
				transferMoneyBean.getReqMCM001().setIntereturnmsg("调用02013接口异常");
				MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
				serverStop("抱歉,您的业务在执行转账操作时失败,请联系大堂经理","","调用大额转账接口时异常");
				return false;
			}		
		}else if("2".equals(transferMoneyBean.getTransMarkNo())){
			try {
				logger.info("调用跨行小额接口");
				transferMoneyBean.getReqMCM001().setReqBefor("02224");
				Map inter02224 = InterfaceSendMsg.inter02224(transferMoneyBean);
				transferMoneyBean.getReqMCM001().setReqAfter((String)inter02224.get("resCode"), (String)inter02224.get("errMsg"));
				if(!"000000".equals(inter02224.get("resCode"))){
					
					logger.error((String) inter02224.get("errMsg"));
					MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
					serverStop("您的业务操作失败", (String) inter02224.get("errMsg"),"");
					return false;
				}
			} catch (Exception e) {
				logger.error("调用跨行小额转账失败"+e);
				transferMoneyBean.getReqMCM001().setIntereturnmsg("调用02224接口异常");
				MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
				serverStop("抱歉,您的业务在调用转账操作时失败,请联系大堂经理","","调用小额转账接口异常");
				return false;
			}		
		
		}
		
		//转账成功，设置上送信息
		transferMoneyBean.getReqMCM001().setCustomername(transferMoneyBean.getChuZhangcardName());//客户名称
		transferMoneyBean.getReqMCM001().setAccount(transferMoneyBean.getChuZhangCardNo());//账号
		transferMoneyBean.getReqMCM001().setLendirection("1");//0-借方，1-贷方
		transferMoneyBean.getReqMCM001().setTransamt(transferMoneyBean.getRemitAmt());//交易金额
		transferMoneyBean.getReqMCM001().setTurnflag("1");//0-现金，1-转账
		transferMoneyBean.getReqMCM001().setTonouns(transferMoneyBean.getRuZhangcardName());//对方名称
		transferMoneyBean.getReqMCM001().setToaccount(transferMoneyBean.getRuZhangCardNo());//对方账号
		transferMoneyBean.getReqMCM001().setTopenbankno(transferMoneyBean.getRecvBankNo());//对方开户行号
		transferMoneyBean.getReqMCM001().setTopenbankname(transferMoneyBean.getRecvBankName());//对方开户行名
		
		result=true;
		return true;
	}
	
	/**
	 * 查询当前交易机构
	 */
	public boolean checkSendBankNo(){
		PublicAccTransferBean transferBean = new PublicAccTransferBean();
		try {
			transferBean.setPayHbrInstNo(GlobalParameter.branchNo);
			transferBean.getReqMCM001().setReqBefor("01118");
			Map inter01118 = InterfaceSendMsg.inter01118(transferBean);
			transferBean.getReqMCM001().setReqAfter((String)inter01118.get("resCode"), (String)inter01118.get("errMsg"));
			if(!"000000".equals(inter01118.get("resCode"))){
				closeDialog(prossDialog);
				logger.info(inter01118.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop((String)inter01118.get("errMsg"), "","");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("查询机构信息失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用01118接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("查询机构信息失败,请联系大堂经理", "","调用01118接口异常");
			return false;
		}
		transferMoneyBean.setSendBankNo(transferBean.getSendBankNo());//当前交易机构
		transferMoneyBean.setSendBankName(transferBean.getSendBankName());//当前交易行名
		return true;
	}
	
	/**
	 * 生成任务号不为1，当天不重复的任务号
	 */
	public boolean taskId(){
		try {
			
			String	dateTime = DateUtil.getNowDate("yyyyMMdd");
			String date=dateTime.substring(2);
			String taskId="ZHFW"+date+transferMoneyBean.getPayHbrInstNo();
			transferMoneyBean.setTaskId(taskId);
			
		} catch (Exception e) {
			logger.error("生成任务号失败"+e);
			serverStop("转账失败,请联系大堂经理","","生成任务号时出现异常");
			return false;
		}
		return true;
	}
	/**
	 * 生成图片，上传事后监督
	 */ 
	public boolean uploadPhotoFile() {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("money", transferMoneyBean.getRemitAmt()+"元");//交易金额
		map.put("billId", transferMoneyBean.getSvrJrnlNo());// 核心流水号
		map.put("transDate", transferMoneyBean.getSvrTranDate());//  日期
		map.put("branchNo", GlobalParameter.branchNo);//  机构号
		map.put("chuNo", transferMoneyBean.getChuZhangCardNo());//  转出账号
		map.put("ruNo", transferMoneyBean.getRuZhangCardNo());//  转入账号
		
		map.put("chuName",transferMoneyBean.getChuZhangcardName());//转出户名
		map.put("ruName",transferMoneyBean.getRuZhangcardName());//转入户名
		map.put("chuBank",transferMoneyBean.getPayHbrInstName());//转出开户行
		map.put("ruBank",transferMoneyBean.getRecvBankName());//转入开户行
		String use = "";
		if("0".equals(transferMoneyBean.getIsCardBank())){
			use = transferMoneyBean.getPurpos().trim();//汇款用途
		}else{
			use = transferMoneyBean.getAppdText().trim();//汇款用途
		}
		map.put("use",use);//资金用途
		map.put("tellerNo", transferMoneyBean.getSupTellerNo());// 授权号
		map.put("tellerNo2", transferMoneyBean.getSupTellerNo2());// 授权号
		map.put("macNo", GlobalParameter.machineNo);// 终端号
		map.put("idCardName", transferMoneyBean.getIdCardName());//  本人姓名
		map.put("idCardNo", transferMoneyBean.getIdCardNo());// 本人身份证号
		map.put("qfjg", transferMoneyBean.getQfjg());// 签发机关
		map.put("sec", "人脸识别通过");
		map.put("transMethod", transferMoneyBean.getZhangWay());//到账方式
		
		String filepath = "";
		String Date = DateUtil.getNowDate("yyyyMMdd");//当前日期

		if("预约实时到账".equals(transferMoneyBean.getZhangWay()) || "次日到账".equals(transferMoneyBean.getZhangWay())){
			
			filepath = Property.FTP_LOCAL_PATH+transferMoneyBean.getTaskIdNo()+".jpg";//C端图片路径
		
		}else{
			
			filepath = Property.FTP_LOCAL_PATH+Date+transferMoneyBean.getSvrJrnlNo()+".jpg";//C端图片路径
		}

		map.put("jpgName", filepath);
		logger.info("生成事后监督内容："+map);
		boolean isupload = true;// 标记是否上传成功
		JpgUtil_HY cg = new JpgUtil_HY();
		String filePath = "";
		try {
			filePath = cg.graphicsGeneration(map);
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
    		String ftpPath ="";
    		if("预约实时到账".equals(transferMoneyBean.getZhangWay()) || "次日到账".equals(transferMoneyBean.getZhangWay())){
    			
    			ftpPath = Property.FTP_SER_PATH_KH + nowDate+"/000004/00000c";
    		
    		}else{
    			
    			ftpPath = Property.FTP_SER_PATH_KH + nowDate+"/000004";
    		}
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
	
	/*
	 * 调用各个方法成功
	 */
	public void checkMothed(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				openProssDialog();
				
				if(!checkCardStatus()){
					serverStop("抱歉,卡状态异常，服务终止！请先核对卡状态","","");
					closeDialog(prossDialog);
					return;
				}
				
				
				//生成任务号不为1，当天不重复的任务号
				if(!taskId()){
					closeDialog(prossDialog);
					return;
				}
				//调用转账方法
				if(!doTransing()){
					closeDialog(prossDialog);
					return;
				}
				
				//交易成功，上送成功的交易流水
				transferMoneyBean.getReqMCM001().setTransresult("0");
				MakeMonitorInfo.makeInfos(transferMoneyBean.getReqMCM001());
				//上传事后监督
				try {
					//转账成功,上送事后监督图片
					boolean flag = uploadPhotoFile();
					closeDialog(prossDialog);
					//返回时跳转页面
				} catch (Exception e) {
					logger.debug("上送事后监管图片失败："+e);
					closeDialog(prossDialog);
					serverStop("转账已经成功，但保存转账记录失败,请联系大堂经理","","上传事后监督异常");
					return;
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					closeDialog(prossDialog);
					logger.error("线程阻塞异常："+e);
				}
				openPanel(new TransferBusinessSuccessPanel(transferMoneyBean));
			}
		}).start();
	}
	
	//检测卡是否在设备中
	public boolean checkCardStatus(){
		logger.info("转账交易前检测卡座状态是否正常");
		try {
			ICBank bank = new ICBank();
			ICBankCheckStatusBean icbsta = bank.checkStatus("50");
			String touchStatus = icbsta.getTouchStatus();
			String notTouchStatus = icbsta.getNotTouchStatus();
			String samStatus = icbsta.getSamStatus();
			String status = icbsta.getStatus();
			logger.info("接触式卡座状态："+touchStatus+"||非接触式卡座状态："+notTouchStatus+"||Sam卡座状态："+samStatus+"||刷卡器状态："+status);
			if("0".equals(status) && ("1".equals(samStatus) || "1".equals(touchStatus) || "1".equals(notTouchStatus))){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			logger.error("检测读卡器状态失败"+e);
			return false;
		}
	}
	
}
