package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Comm.PrintAgreements;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintSubAccInfoBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.HistoryCardNo;
import com.boomhope.Bill.TransService.BillPrint.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 打印协议书页面
 * @author Administrator
 *
 */

public class TransPrintAgreementsPanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransPrintAgreementsPanel.class);
	
	private JLabel label1;//提示信息
	private JPanel panel1;//提示信息2
	private JLabel label3;//提示信息3
	private JScrollPane jsPanel;//显示打印失败信息
	private JList list;//显示打印失败的信息列表
	private int time=60;//倒计时时间
	private List<BillPrintSubAccInfoBean> listBean;//需要打印协议书的子账号信息
	
	private List<String> agreementList = new ArrayList<>();//协议书版本集合
	private Map<String,String> resultMap;//查询接口失败
	private boolean printAgrResult=true;//打印结果
	
	public TransPrintAgreementsPanel(){
		
		/* 显示时间倒计时 */
		TimeNums();
		this.showTimeText(time);
		delayTimer = new Timer(time * 1000, new ActionListener() {          
			public void actionPerformed(ActionEvent e) { 
				logger.info("打印协议页面倒计时结束 ");
				/* 倒计时结束退出交易 */ 
				clearTimeText();//清空倒计时
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}      
		});            
		delayTimer.start();
		
		label1 = new JLabel("正在打印协议书，请耐心等待。");
		label1.setBounds(0, 200, 1009, 40);
		label1.setFont(new Font("微软雅黑",Font.BOLD,34));
		label1.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(label1);
		
		
		label3 = new JLabel("打印失败的协议书");
		label3.setBounds(250, 330, 510, 30);
		label3.setFont(new Font("微软雅黑",Font.BOLD,20));
		label3.setHorizontalAlignment(SwingUtilities.LEFT);
		label3.setVisible(false);
		this.showJpanel.add(label3);
		
		panel1=new JPanel();
		panel1.setBounds(250, 360, 510, 200);
		panel1.setLayout(null);
		panel1.setVisible(false);
		this.showJpanel.add(panel1);
		
		jsPanel = new JScrollPane();
		jsPanel.setBounds(0, 0, 510, 200);
		panel1.add(jsPanel);
		
		list = new JList<>();
		list.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		list.setForeground(Color.red);
		list.setFixedCellHeight(25);
		jsPanel.getViewport().setView(list);
		
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}
		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//调用打印协议书
				initDo();
			}
		}).start();
	}
	
	//调用执行
	public void initDo(){
		prossDialog.showDialog();
		//查询所有协议版本号
		boolean result1=getAgreementDdition();
		if(!result1){
			billPrintBean.getReqMCM001().setIntereturnmsg("查询本地版本协议书失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			serverStop("查询本地版本协议书失败，请联系大堂经理。", "", "获取本地文件中的协议版本号失败");
			return;
		}
		
		printAgr();
		label1.setText("打印完成，请仔细核对协议书信息");
		
		//打印完成
		if(billPrintBean.getFailPrint()!=null && !"".equals(billPrintBean.getFailPrint().trim())){
			logger.info("打印失败的子账号："+billPrintBean.getFailPrint());
			panel1.setVisible(true);
			label3.setVisible(true);
			showLostInfo();
		}
		prossDialog.disposeDialog();
	}
	
	/**
	 * 计算倒计时时间
	 */
	public void TimeNums(){
		listBean=(List<BillPrintSubAccInfoBean>)billPrintBean.getSubInfo().get("SUB_ACC_MSG");//子账户信息集合
		for(BillPrintSubAccInfoBean bean:listBean){
			if(bean.isChoice()){
				time+=30;
			}
		}
	}
	
	/*
	 * 显示打印失败的信息
	 */
	public void showLostInfo(){
		String[] listInfo=billPrintBean.getFailPrint().trim().substring(0,billPrintBean.getFailPrint().trim().length()-1) .split("/");
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for(int i=0;i<listInfo.length;i++){
			dlm.addElement(listInfo[i]);
		}
		list.setModel(dlm);
	}
	
	
	/**
	 * 获取需要打印的协议书的版本号
	 */
	public boolean getEditionNo(BillPrintSubAccInfoBean bean){
		//放置获取的版本号
		String edition="";
		//用于比较那个版本更近
		int result=-1;
		String openDateStr=bean.getOpenDate().trim();
		String dateStr=null;
		Pattern p=Pattern.compile("^[0-9]*$");
		Matcher ma=p.matcher(openDateStr);
		boolean pMa= ma.matches();
		if(pMa && openDateStr.length()==8){
			dateStr=openDateStr.trim();
		}else{
			dateStr=bean.getOpenDate().substring(0,4)+bean.getOpenDate().substring(5,7)+bean.getOpenDate().substring(8);
		}
		int openDate = Integer.parseInt(dateStr);
		for(String a:agreementList){
			int aNo=Integer.parseInt(a.trim());
			int b=openDate-aNo;
			if(b<0 && aNo==Integer.parseInt(Property.PRINT_AGREEMENT_NUMBER.trim())){
				edition=a;
				bean.setHavePic(false);
				break;
			}
			if(b<0){
				continue;
			}
			if(result==-1 && b>=0){
				result=b;
				edition=a;
			}else if(b<result){
				result=b;
				edition=a;
			}
		}
		bean.setAgreementEdition(edition.trim());
		return true;
	}
	
	
	/**
	 * 获取所有协议书版本号
	 */
	public boolean getAgreementDdition(){
		File agreementFile = new File("config\\agreement_edition.properties");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(agreementFile));
			String tempStr=null;
			int line=2;
			while((tempStr=reader.readLine())!=null){
				logger.info("协议书版本号："+tempStr);
				agreementList.add(tempStr);
				line++;
			}
			reader.close();
			if(agreementList.size()==0 || agreementList==null){
				logger.info("无协议书版本:"+agreementList.size());
				return false;
			}
		} catch (Exception e) {
			logger.error("读取协议书版本失败："+e);
			return false;
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (Exception e2) {
					logger.error("关闭读取协议书版本文件的流失败："+e2);
				}
			}
		}
		return true;
	}
	
	//查看需要打印的子账户列表
	public void printAgr(){
		logger.info("开始打印协议书");
		//记录失败的子账号
		String str="";
		
		for(BillPrintSubAccInfoBean bean:listBean){
			if(bean.isChoice()){
				//根据不同产品查询关联账号
				if(bean.getProductCode().startsWith("AX")){
					bean.setQry_type("5");
					if(!findCognateInfo(bean)){
						if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
							str += bean.getSubAccNo()+":查询关联账号失败/";
						}else{
							str += bean.getAccNo()+":查询关联账号失败/";
						}
						continue;
					}
				}else if(bean.getProductCode().startsWith("LZ")||bean.getProductCode().startsWith("LT")){
					bean.setQry_type("1");
					findCognateInfo(bean);
					if(!findCognateInfo(bean)){
						if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
							str += bean.getSubAccNo()+":查询关联账号失败/";
						}else{
							str += bean.getAccNo()+":查询关联账号失败/";
						}
						continue;
					}
				}else if(bean.getProductCode().startsWith("JX")){
					bean.setQry_type("2");
					if(!findCognateInfo(bean)){
						if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
							str += bean.getSubAccNo()+":查询关联账号失败/";
						}else{
							str += bean.getAccNo()+":查询关联账号失败/";
						}
						continue;
					}
				}else if(bean.getProductCode().startsWith("RJ")){
					bean.setQry_type("3");
					if(!findCognateInfo(bean)){
						if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
							str += bean.getSubAccNo()+":查询关联账号失败/";
						}else{
							str += bean.getAccNo()+":查询关联账号失败/";
						}
						continue;
					}
				}else if(bean.getProductCode().startsWith("FL")){
					//产品代码第4位如果不是0-7中的某个数字，则为新增聚享存产品，暂不支持打印协议书
					if(!Pattern.compile("[0-7]*").matcher(bean.getProductCode().substring(3,4)).matches()){
						if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
							str += bean.getSubAccNo()+": 该产品暂不支持协议书打印/";
						}else{
							str += bean.getAccNo()+":该产品暂不支持协议书打印/";
						}
						continue;
					}
				}else if(bean.getProductCode().startsWith("QXP")){
				     if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
				    	 str += bean.getSubAccNo()+": 该产品暂不支持协议书打印/";
				     }else{
				         str += bean.getAccNo()+":该产品暂不支持协议书打印/";
				     }
				     continue;
				}
				
				//通过渠道号判断是否保存了电子签名和电子印章
				if(!"08".equals(bean.getChannel().trim()) && !"0035".equals(bean.getChannel().trim())){
					bean.setHavePic(false);
				}
				
				//查询开户时的协议书版本号
				if(!getEditionNo(bean)){
					if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
						str += bean.getSubAccNo()+"：查询协议书版本失败/";
					}else{
						str += bean.getAccNo()+"：查询协议书版本失败/";
					}
					continue;
				}
				
				//查询开户时流水号
				if(!findCreateSvrNo(bean)){
					if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
						str += bean.getSubAccNo()+"：查询开户时流水号失败/";
					}else{
						str += bean.getAccNo()+"：查询开户时流水号失败/";
					}
					continue;
				}
				
				
				//下载电子签名和电子印章
				if(bean.isHavePic()!=false){
						
					if(!downPic(bean)){
						logger.info("下载签名和印章图片失败，查询原卡号");
						
						Map checkCardNo = checkCardNo(billPrintBean);
						if(!"000000".equals(checkCardNo.get("resCode"))){
							
							if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
								str += bean.getSubAccNo()+"：下载签名和印章图片失败/";
							}else{
								str += bean.getAccNo()+"：下载签名和印章图片失败/";
							}	
							continue;
							
						}else{
							
							List<HistoryCardNo> list = (List<HistoryCardNo>)checkCardNo.get("historyCardNo");
							if(checkCardNo.get("historyCardNo")==null || list.size()==0){
								
								if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
									str += bean.getSubAccNo()+"：下载签名和印章图片失败/";
								}else{
									str += bean.getAccNo()+"：下载签名和印章图片失败/";
								}	
								continue;
								
							}else{
								String xiazai = "0";
								for (int i = 0; i < list.size(); i++) {
									BillPrintSubAccInfoBean billBean = new BillPrintSubAccInfoBean();
									billBean.setAccNo(list.get(i).getCardNo());
									billBean.setSubAccNo(bean.getSubAccNo());
									billBean.setAgreementEdition(bean.getAgreementEdition());
									
									if (downPic(billBean)){
										xiazai = "1";
										bean.setOldAccNo(list.get(i).getCardNo());
										break;
									}
								}
								if("0".equals(xiazai)){
									if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
										str += bean.getSubAccNo()+"：下载签名和印章图片失败/";
									}else{
										str += bean.getAccNo()+"：下载签名和印章图片失败/";
									}	
									continue;
								}
							}
						}
					}
				}
				billPrintBean.setSubBean(bean);
				//打印协议书
				print(bean);
				if(!printAgrResult){
					if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
						str += bean.getSubAccNo()+"：打印失败/";
					}else{
						str += bean.getAccNo()+"：打印失败/";
					}
					printAgrResult=true;
				}
				
				//删除电子签名和电子印章
				if(bean.isHavePic()!=false){
					for(int i=0;i<2;i++){
						String fileName="";
						if(i==0){
							fileName = bean.getAccNo().trim()+"-"+bean.getSubAccNo().trim()+"_DZQM.png"; 
						}else{
							fileName = bean.getAccNo().trim()+"-"+bean.getSubAccNo().trim()+"_DZYZ.png";
						}
						deleteFile(new File("D:\\"+fileName.trim()));
					}
				}
			
				billPrintBean.getReqMCM001().setCentertrjnno(bean.getCreateSvrNo());
				if(bean.getSubAccNo()!=null && !"".equals(bean.getSubAccNo())){
					billPrintBean.getReqMCM001().setAccount(bean.getAccNo()+"-"+bean.getSubAccNo());
				}else{
					billPrintBean.getReqMCM001().setAccount(bean.getAccNo());
				}
				billPrintBean.getReqMCM001().setCustomername(billPrintBean.getCardName());
				billPrintBean.getReqMCM001().setTransamt(bean.getOpenATM());
				billPrintBean.getReqMCM001().setTransresult("0");
				MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			}else{
				continue;
			}
		}
		billPrintBean.setFailPrint(str);
	}
	
	/**
	 * 查询开户时流水号
	 */
	public boolean findCreateSvrNo(BillPrintSubAccInfoBean bean){
		try {
			billPrintBean.getReqMCM001().setReqBefor("07522");
			Map map = InterfaceSendMsg.inter07522(bean,billPrintBean);
			billPrintBean.getReqMCM001().setReqAfter((String)map.get("resCode"), (String)map.get("errMsg"));
			if(!"000000".equals(map.get("resCode"))){
				logger.info(map.get("errMsg"));
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				return false;
			}
		} catch (Exception e) {
			logger.error("查询开户流水号失败(07522)");
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07522查询开户时流水号失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			return false;
		}
		return true;
	}
	/**
	 * 下载电子签名和下载电子印章
	 */
	public boolean downPic(BillPrintSubAccInfoBean bean){
		SFTPUtil sf = new SFTPUtil();
		ChannelSftp sftp = null;
		Session sshSession = null;
    	JSch jsch = new JSch();
		try {
			//连接SFTP
    		sshSession = jsch.getSession(Property.FTP_USER,Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
    		sshSession.setPassword(Property.FTP_PWD);
    		Properties sshConfig = new Properties();
    		sshConfig.put("StrictHostKeyChecking", "no");
    		sshSession.setConfig(sshConfig);
    		sshSession.connect();
    		Channel channel = sshSession.openChannel("sftp");
    		channel.connect();
    		sftp = (ChannelSftp) channel;
    		
			for(int i=0;i<2;i++){
				String fileName="";
				if(i==0){
					fileName = bean.getAccNo().trim()+"-"+bean.getSubAccNo().trim()+"_DZQM.png"; 
				}else{
					fileName = bean.getAccNo().trim()+"-"+bean.getSubAccNo().trim()+"_DZYZ.png";
				}
				boolean result = download("/home/print/"+bean.getAgreementEdition(), fileName, "D:\\"+fileName, sftp);
				if(!result){
					return false;
				}
			}
			sftp.disconnect();
			sshSession.disconnect();
		} catch (Exception e) {
			logger.error("下载电子签名和电子印章失败："+e);
			return false;
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
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public boolean download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
		logger.info("调用下载方法");
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			logger.error("下载电子签名图片和电子印章失败："+e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * 成功上传事后监督图片后删除本地图片的方法
	 * */
	private void deleteFile(File file) {
		if (file.isFile()) {// 如果是文件
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = file.delete();
		if (!result) {// 判断是否全部删除
			file.delete();
		}
		logger.info("删除成功" + file);
		}
	}
	
	//调用打印机打印协议书
	public void print(final BillPrintSubAccInfoBean bean){
		try {
			try {
				openLed("5");
				logger.info("协议书打印机Led灯打开");
			} catch (Exception e2) {
				logger.error("协议书打印机Led灯打开失败");
			}
			logger.info("开始打印协议书:"+bean.getProductName());
			PrintAgreements print =new PrintAgreements();
			boolean printResult = print.PrintBills(bean.getProductCode().substring(0,2),billPrintBean);
			if(!printResult){
				printAgrResult=false;
			}
			try {
				closeLed("5");
				logger.info("协议书打印机Led灯关闭");
			} catch (Exception e1) {
				logger.info("协议书打印机Led灯关闭");
			}
			repaint();
		} catch (Exception e) {
			logger.error("打印协议书异常"+e);
			try {
				closeLed("5");
				logger.info("协议书打印机Led灯关闭");
			} catch (Exception e1) {
				logger.info("协议书打印机Led灯关闭");
			}
			printAgrResult=false;
		}
				
	}
	
	/**
	 * 查询关联账号
	 * @param bean
	 * @return
	 */
	public boolean findCognateInfo(BillPrintSubAccInfoBean bean){
		try {
			billPrintBean.getReqMCM001().setReqBefor("07518");
			resultMap = InterfaceSendMsg.inter07518(bean);
			billPrintBean.getReqMCM001().setReqAfter((String)resultMap.get("resCode"), (String)resultMap.get("errMsg"));
			if(!"000000".equals(resultMap.get("resCode"))){
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				logger.info("查询关联账号失败：(07518)"+resultMap.get("errMsg"));
				return false;
			}
		} catch (Exception e) {
			logger.error("调用07518接口查询关联账号失败");
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07518接口查询关联账号失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			return false;
		}
		return true;
	}
	
	/**
	 * 银行卡换开查询原卡号【08029】前置07523
	 * @param bean
	 * @return
	 */
	public Map checkCardNo(BillPrintBean bean){
		Map inter07523 = null;
		try {
			billPrintBean.getReqMCM001().setReqBefor("07523");
			inter07523 = InterfaceSendMsg.inter07523(bean);
			billPrintBean.getReqMCM001().setReqAfter((String)inter07523.get("resCode"), (String)inter07523.get("errMsg"));
			if(!"000000".equals(inter07523.get("resCode"))){
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				return inter07523;
			}
			return inter07523;
		} catch (Exception e) {
			logger.error("调用07523银行卡换开查询原卡号失败");
			inter07523.put("resCode", "4444");
			inter07523.put("errMsg", "调用07523银行卡换开查询原卡号失败");
			billPrintBean.getReqMCM001().setIntereturnmsg("调用07523银行卡换开查询原卡号失败");
			MakeMonitorInfo.makeInfos(billPrintBean.getReqMCM001());
			return inter07523;
		}
	}
}
