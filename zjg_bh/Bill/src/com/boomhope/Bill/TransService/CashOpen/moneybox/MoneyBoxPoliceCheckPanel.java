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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.FtpFileUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilPreFile;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.bck.BCK0008ResBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * 联网核查
 * @author gyw
 *
 */
public class MoneyBoxPoliceCheckPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(MoneyBoxPoliceCheckPanel.class);

	private static final long serialVersionUID = 1L;
	
	Timer checkTimer = null;
	
	public MoneyBoxPoliceCheckPanel(final PublicCashOpenBean transBean) {

		this.cashBean = transBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.POLICE_CHECK;

		/* 标题信息 */
		JLabel titleLabel = new JLabel("正在进行联网核查，请稍候...");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD,40));
		titleLabel.setBounds(0, 240, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		/* 加载核查动画 */
		JLabel billImage = new JLabel();   
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(395, 349, 220, 159);
		this.showJpanel.add(billImage);
		
		JButton button = new JButton("测试按钮-成功");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				success(transBean);
			}

		});
		button.setBounds(618, 649, 172, 23);
		Property property = new Property();
		if(property.getProperties().getProperty("push_button").equals("false")){
			button.setVisible(false);
		}else if (property.getProperties().getProperty("push_button").equals("true")){
			button.setVisible(true);
		}
		this.showJpanel.add(button);
		
		JButton button1 = new JButton("测试按钮-失败");
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fail(transBean, "测试失败");
			}

		});
		button1.setBounds(406, 649, 172, 23);
		if(property.getProperties().getProperty("push_button").equals("false")){
			button1.setVisible(false);
		}else if (property.getProperties().getProperty("push_button").equals("true")){
			button1.setVisible(true);
		}
		this.showJpanel.add(button1);
		
		/* 倒计时进入联网核查 */
		checkTimer = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	checkTimer.stop();
            	poiceCheck(transBean);
            }      
        });            
		checkTimer.start();
	
	}

	/**
	 * 调用接口核查
	 */
	public void poiceCheck(PublicCashOpenBean transBean){
		// 本人身份核查
		Map<String,String> map = new HashMap<String,String>();
		map.put("id",transBean.getIdCardNo());
		map.put("name", transBean.getIdCardName());
		
		transBean.getReqMCM001().setReqBefor("07670");//接口调用前流水信息记录
		
		BCK0008ResBean resBean = getPoiceInfo(map);
		String resCode = resBean.getHeadBean().getResCode();
		String resMsg = resBean.getHeadBean().getResMsg();
		logger.info("本人身份核查resCode："+resCode);
		logger.info("本人身份核查resMsg："+resMsg);
		if(!"000000".equals(resCode)){
			//接口调用后流水信息记录
			if(null == resBean){
				transBean.getReqMCM001().setIntereturnmsg("身份核查失败：调用07670接口异常");
			}else{
				transBean.getReqMCM001().setReqAfter(resCode, resMsg);
			}
			fail(transBean, resMsg);
			return;
		}
		try {
			String fileName = resBean.getBody().getFILE_NAME();
			FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
			UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_SELF);
		} catch (Exception e) {
			transBean.getReqMCM001().setIntereturnmsg("身份核查失败：调用07670接口异常");
			MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
			serverStop("请联系大堂经理","","获取联网核查结果异常");
			logger.error(e);
			return;
		}
		
		// 本人联网核查照片查看
		String tmp = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
		// 拷贝临时图片--------------------
		File from_f = new File(Property.ID_CARD_SELF);
		// 目标目录
		File to_f = new File(tmp+transBean.getIdCardNo()+"_mePoic.jpg");
		try {
			FileUtil.copyFileUsingJava7Files(from_f, to_f);
		} catch (IOException e) {
			logger.error("拷贝临时图片异常");
			logger.error(e);
		}
		
		transBean.setInspect(resBean.getBody().getCORE_RET_MSG());
		//代理人身份核查
		String agent = transBean.getImportMap().get("agent");
		if("true".equals(agent)){
			Map<String,String> agentmap = new HashMap<String,String>();
			agentmap.put("id", transBean.getAgentIdCardNo());
			agentmap.put("name", transBean.getAgentIdCardName());

			transBean.getReqMCM001().setReqBefor("07670");//接口调用前流水信息记录
			
			BCK0008ResBean resBeanAgent = getPoiceInfo(agentmap);
			String resCodeAgent = resBeanAgent.getHeadBean().getResCode();
			String resMsgAgent = resBeanAgent.getHeadBean().getResMsg();
			logger.error("本人身份核查resCode："+resCodeAgent);
			logger.error("本人身份核查resMsg："+resMsgAgent);
			if(!"000000".equals(resCodeAgent)){
				//接口调用后流水信息记录
				if(null == resBean){
					transBean.getReqMCM001().setIntereturnmsg("身份核查失败：调用07670接口异常");
				}else{
					transBean.getReqMCM001().setReqAfter(resCode, resMsg);
				}
				
				fail(transBean, resMsgAgent);
				return;
			}
			transBean.setAgentinspect(resBeanAgent.getBody().getCORE_RET_MSG());
			try {
				String fileName = resBeanAgent.getBody().getFILE_NAME();
				FtpFileUtils.downloadFileByFTPClient(Property.FTP_IP_DOWN, Integer.parseInt(Property.FTP_PORT_DOWN), Property.FTP_USER_DOWN, Property.FTP_PWD_DOWN, fileName, Property.FTP_LOCAL_PATH + fileName);
				UtilPreFile.getIdCardImage(Property.FTP_LOCAL_PATH+fileName,Property.ID_CARD_AGENT);
			} catch (Exception e) {
				transBean.getReqMCM001().setIntereturnmsg("身份核查失败：调用07670接口异常");
				MakeMonitorInfo.makeInfos(transBean.getReqMCM001());//接口调用失败，上送流水信息
				serverStop("请联系大堂经理","获取联网核查结果异常","");
				logger.error(e);
				logger.info(e);
				return;
			}
			
			// 代理人联网核查照片查看
			String tmp1 = Property.BH_TMP + "\\"+DateUtil.getNowDate("yyyyMMdd")+"\\" +transBean.getFid()+"\\";
			// 拷贝临时图片--------------------
			File from = new File(Property.ID_CARD_AGENT);
			// 目标目录
			File to = new File(tmp1+transBean.getIdCardNo()+"_agentPoic.jpg");
			try {
				FileUtil.copyFileUsingJava7Files(from, to);
			} catch (IOException e) {
				logger.error("拷贝临时图片异常");
				logger.error(e);
			}
		}
		
		// 全部核查成功
		success(transBean);
		
	}
	
	
	/**
	 * 联网核查
	 */
	public BCK0008ResBean getPoiceInfo(Map<String,String> map){
		
		String retMsg = "";
		SocketClient sc = new SocketClient();
		Socket socket=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			 socket = sc.createSocket();
            //构建IO  
             is = socket.getInputStream();  
             os = socket.getOutputStream(); 
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));  
            //向服务器端发送一条消息  
            bw.write(sc.BCK_07670(map) + "\n");  
            bw.flush();  
            //读取服务器返回的消息  
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
			String value = null;
			while ((value = br.readLine()) != null) {
				retMsg += value + "\n";
				if ("</Root>".equals(value)){
					break;
				}
			}
			logger.info(retMsg);
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", BCK0008ResBean.class);
			BCK0008ResBean bck0002ResBean = (BCK0008ResBean)reqXs.fromXML(retMsg);
			logger.info(bck0002ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return bck0002ResBean;
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}finally{
			try {
				if(os!=null){
					os.close();
				}
				if(is!=null){
					is.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (Exception e2) {
				logger.info(e2);
			}
		}
		return null;
	}
	
	
	/**
	 * 成功
	 */
	public void success(PublicCashOpenBean transBean){
		// 进入人脸识别
//		this.openPanel(GlobalPanelFlag.FACE_CHECK);
		String agentFalg = transBean.getImportMap().get("agent_persion");
		if(agentFalg != null && "yes".equals(agentFalg)){
			//存在代理人
			clearTimeText();
			openPanel(new MoneyBoxExistProcuratorPanel(transBean));
				
		}else{
			//不存在代理人
			clearTimeText();
			openPanel(new MoneyBoxNegationProcuratorPanel(transBean));
			
		}
	}
	
	/**
	 * 失败
	 */
	public void fail(PublicCashOpenBean transBean,String errmsg){
	 	// 代理人核查失败
		transBean.getImportMap().put("agent_idCard_check", GlobalPanelFlag.DEPUTY_SELECTION+"");
		// 本人核查失败
		transBean.getImportMap().put("idCard_check", GlobalPanelFlag.DEPUTY_SELECTION+"");
		//失败原因
		transBean.setErrmsg(errmsg);
		openPanel(new MoneyBoxMistakePanel(transBean));
	}
	
	
}
