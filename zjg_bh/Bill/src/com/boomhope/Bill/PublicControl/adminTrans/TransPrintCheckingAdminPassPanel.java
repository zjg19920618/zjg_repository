package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.PublicServerStop;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;
import com.boomhope.Bill.Socket.SocketClient;
import com.boomhope.tms.message.XStreamNameCoder;
import com.boomhope.tms.message.in.InResBean;
import com.boomhope.tms.message.in.tms.Tms0004ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBean;
import com.boomhope.tms.message.in.tms.Tms0005ResBodyBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/** 
 * 
 * title:管理员密码过渡页
 * @author ly
 * 2016年11月8日上午11:31:49
 */
public class TransPrintCheckingAdminPassPanel extends BaseTransPanelNew {
	Timer checkTimer = null;
	static Logger logger = Logger.getLogger(TransPrintCheckingAdminPassPanel.class);
	private AdminBean adminBean;
	

	public TransPrintCheckingAdminPassPanel(AdminBean AdminBean) {

		this.adminBean = AdminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_CHECK_ADMIN_LOGIN;

		/* 标题信息 */
		JLabel titleLabel = new JLabel("密码验证中，请稍候...");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		titleLabel.setBounds(0, 240, GlobalParameter.TRANS_WIDTH, 30);
		this.showJpanel.add(titleLabel);

		/* 加载核查动画 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/checking.gif"));
		billImage.setIconTextGap(6);
		billImage.setBounds(450, 349, 220, 159);
		this.showJpanel.add(billImage);
		

		/* 执行密码检查 */
		checkTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTimer.stop();
				checkAdminPass();
				success();
			}
		});
		checkTimer.start();
	}

	/**
	 * 成功
	 */
	private void success() {
		//进入终端管理界面
		openPanel(new TransPrintManagePanel(adminBean));
		
	}

	/**
	 * 管理员密码核查
	 */
	public void checkAdminPass() {
		Map<String,String> map =new HashMap<String,String>();
		map.put("isXiu","0"); //管理员
		map.put("username",adminBean.getAdminName()); //管理员
		map.put("password",adminBean.getPaw()); //密码
		//密码校验
		Tms0004ResBean tms0004 = Tms0004(map);
		if("000000".equals(tms0004.getHeadBean().getResCode())){
			// 查询凭证
			Tms0005ResBean tms0005 = Tms0005();
			if("000000".equals(tms0005.getHeadBean().getResCode())){
				Integer now = Integer.valueOf(tms0005.getBody().getNOW_BO());
				Integer end = Integer.valueOf(tms0005.getBody().getEND_NO());
				adminBean.setNowNumber(String.valueOf(end-now+1));  //剩余张数
				adminBean.setBusBillId(tms0005.getBody().getID()); //当前凭证Id
				adminBean.setNowBo(tms0005.getBody().getNOW_BO()); //当前凭证号
				adminBean.setStartBo(tms0005.getBody().getSTART_NO()); //起始凭证号
				adminBean.setEndBo(tms0005.getBody().getEND_NO()); //结束凭证号
				adminBean.setCreateDate(tms0005.getBody().getCREATE_DATE()); //创建时间
				adminBean.setUpdateDate(tms0005.getBody().getUPDATE_DATE()); //修改时间
				success();
			}else if("999998".equals(tms0005.getHeadBean().getResCode())){
				//凭证信息不存在
				adminBean.setNowBo(""); //当前凭证号
				adminBean.setNowNumber("");  //剩余张数
//				this.transBean.setErrmsg(tms0005.getHeadBean().getResMsg());
				success();
			}else{
				//查询凭证失败
				serviceStop("查询凭证号失败,请联系大堂经理!",",查询凭证号失败","");
			}
		}else{
			//密码校验失败处理
			this.adminBean.setErrmsg(tms0004.getHeadBean().getResMsg());
			serviceStop(tms0004.getHeadBean().getResMsg(), "",""); 
		}
		
	}

	/**
	 * 查询密码
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Tms0004ResBean Tms0004(Map<String,String>map){
	
		SocketClient sc = new SocketClient();
		try {
			Socket socket =sc.createSocket();
			// 发送请求
			sc.sendRequest(socket,sc.Tms0004(map));
			// 响应
			String retMsg = sc.response(socket);
		
			XStream reqXs = new XStream(new DomDriver("UTF-8",new XStreamNameCoder()));
			reqXs.alias("Root", Tms0004ResBean.class);
			reqXs.alias("Head", InResBean.class);
			Tms0004ResBean tms0004ResBean = (Tms0004ResBean)reqXs.fromXML(retMsg);
			logger.info(tms0004ResBean);
			logger.info("CLIENT retMsg:" + retMsg);
			return tms0004ResBean;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("管理员校验连接异常",e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("管理员校验io传输异常",e);
		}
		return null;
	}
	/**
	 * 查询凭证信息
	 */
	public Tms0005ResBean Tms0005(){
		SocketClient sc = new SocketClient();
		try {
			Socket socket =sc.createSocket();
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
			logger.error("凭证查询连接异常",e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("凭证io传输异常",e);
		}
		return null;
	}

	/**
	 * 失败后处理
	 */
	private void fail() {
		this.openPanel(new TransPrintAdminPassFailPanel(adminBean));
//		this.transBean.setErrmsg(errmsg);
//		transBean.getImportMap().put("backStep",
//				GlobalPanelFlag.PRINT_INPUT_IDCARD + "");
//		this.openPanel(GlobalPanelFlag.PRINT_ERROR_MSG);
	}

	/**
	 * 服务终止
	 * 
	 * @param errmsg
	 * @param serStopMsg
	 */
	private void serviceStop(String errmsg, String serStopMsg,String usMsg) {
		this.openPanel(new PublicServerStop(errmsg,serStopMsg,usMsg));
	}
}
