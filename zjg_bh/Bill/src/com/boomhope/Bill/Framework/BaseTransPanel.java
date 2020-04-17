package com.boomhope.Bill.Framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.boomhope.Bill.Driver.KeypadDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.SFTPUtil;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.ICBankQuitBean;
import com.boomhope.Bill.peripheral.bean.IdCardCancelReadBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/***
 * 基础交易容器类
 * @author shaopeng
 *
 */
public class BaseTransPanel extends JPanel{
	
	Logger logger = Logger.getLogger(BaseTransPanel.class);

	private static final long serialVersionUID = 1L;
	
	private JLabel preTimeLabel;
	private JLabel timeLabel;
	private JLabel afterTimeLabel;
	Timer timeAction;	// 倒计时定时器
	private int second;
	protected int transFlag;	// 交易容器标签
	private static int index = 0; //标识位，来标记是第几次执行该方法

	
	/***
	 * 更改背景
	 */
	public void paintComponent(Graphics g)
	{
		g.drawImage(new ImageIcon("pic/trans_background1.png").getImage(), 0, 0, 
				GlobalParameter.TRANS_WIDTH, GlobalParameter.TRANS_HEIGHT, this); 
	}
	
	/***
	 * 界面初始化
	 */
	public BaseTransPanel(){
		this.setSize(GlobalParameter.TRANS_WIDTH, GlobalParameter.TRANS_HEIGHT);
		this.setLocation(GlobalParameter.WINDOW_WIDTH/2 - GlobalParameter.TRANS_WIDTH/2, 110);
		this.setOpaque(false);
		this.setLayout(null);
		if(index == 0){
			logTimer();
			index ++ ;
		}
	}


	/***
	 * 设置剩余时间信息
	 * @param second
	 */
	protected void showTimeText(int x, int y, int second){
		this.second = second;
		
		preTimeLabel = new JLabel("剩余时间:");
		preTimeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		preTimeLabel.setBounds(x, y, 110, 30);
		add(preTimeLabel);
		
		timeLabel = new JLabel(String.valueOf(this.second));
		timeLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		timeLabel.setForeground(Color.GREEN);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);
		timeLabel.setBounds(x + 80, y - 5, 70, 40);
		add(timeLabel);
		timeAction = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	updateTimeText(); 
            }      
        });            
        timeAction.start(); 
 		
		afterTimeLabel = new JLabel("秒");
		afterTimeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		afterTimeLabel.setBounds(x + 150, y, 70, 30);
		add(afterTimeLabel);
		
	}
	
	/***
	 * 清空倒计时
	 */
	protected void clearTimeText(){
		timeAction.stop();
		preTimeLabel.setText("");
		timeLabel.setText("");
		afterTimeLabel.setText("");
		
	}
	
	/***
	 * 设置剩余时间
	 */
	private void updateTimeText(){
		second = second - 1;
		timeLabel.setText(String.valueOf(second));
		
		if (second == 0){
			timeAction.stop();
		}
	}
	
	/**
	 * 密码键盘明文输入银行卡号，调用关闭密码键盘
	 */
	public String closePwd() throws Exception{
		if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isInputShutdown()){
			KeypadDriver.socket.shutdownInput();
		}if(!KeypadDriver.socket.isClosed()&&!KeypadDriver.socket.isOutputShutdown()){
			KeypadDriver.socket.shutdownOutput();
		}
		Thread.sleep(100);
		String res = new KeypadDriver().closePwd("6");
		return res;
	}
	
	/**
	 * 银行读卡器读取卡失败，退出，
	 */
	public ICBankQuitBean closeICBank() throws Exception{
		if(!ICBank.socket.isInputShutdown()){
			ICBank.socket.shutdownInput();
		}if(!ICBank.socket.isOutputShutdown()){
			ICBank.socket.shutdownOutput();
		}
		Thread.sleep(100);
		ICBankQuitBean icBankQuit = new ICBank().ICBankQuit("2","60");
		return icBankQuit;
	}
	
	/**
	 * 身份证阅读器读取卡失败，退出，
	 */
	public IdCardCancelReadBean closeIDCard() throws Exception{
		if(!IdCard.socket.isInputShutdown()){
			IdCard.socket.shutdownInput();
		}if(!IdCard.socket.isOutputShutdown()){
			IdCard.socket.shutdownOutput();
		}
		Thread.sleep(100);
		IdCardCancelReadBean res = new IdCard().idCardCancelRead("2");
		return res;
	}
	
	/**打开led灯
	 * @throws Exception */
	public void openLed(String code) throws Exception{
		LedStateBean openLed = new LedStateBean();
		openLed = MachineLed.openLed(code);
		logger.info(code+"号Led灯打开返回值："+openLed);
	}
	
	/**关闭led灯
	 * @throws Exception */
	public void closeLed(String code) throws Exception{
		LedStateBean closeLed = new LedStateBean();
		closeLed = MachineLed.closeLed(code);
		logger.info(code+"号Led灯关闭返回值："+closeLed);
	}
	
	// 定时上传日志文件到服务器
	private void logTimer() {
		java.util.Timer timer2 = new java.util.Timer();
		timer2.scheduleAtFixedRate(new TimerTask() {  
            public void run() {
            	logger.info("开始上传log日志......");
            	SFTPUtil sf = new SFTPUtil();
            	ChannelSftp sftp = null;
            	Session sshSession = null;
            	JSch jsch = new JSch();
            	try {
            		//连接SFTP
            		sshSession = jsch.getSession(Property.FTP_USER, Property.FTP_IP, Integer.parseInt(Property.FTP_PORT));
            		System.out.println("Session created.");
            		sshSession.setPassword(Property.FTP_PWD);
            		Properties sshConfig = new Properties();
            		sshConfig.put("StrictHostKeyChecking", "no");
            		sshSession.setConfig(sshConfig);
            		sshSession.connect();
            		System.out.println("Session connected.");
            		System.out.println("Opening Channel.");
            		Channel channel = sshSession.openChannel("sftp");
            		channel.connect();
            		sftp = (ChannelSftp) channel;
            		System.out.println("Connected to " + Property.FTP_IP + ".");
            		//获取本地文件
            		File file = new File(Property.FTP_LOG_LOCAL_PATH);
            		if(file.isDirectory()){
            			File[] listFiles = file.listFiles();
            			//先进入服务器指定目录
            			String nowDate = DateUtil.getNowDate("yyyyMMdd");
            			String ftpPath = Property.FTP_LOG_PATH + GlobalParameter.machineNo+"/"+ nowDate;
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
                		logger.info("进入指定目录-->"+paths);
            			//获得该文件夹下面以hdj_开头，以.log结尾的文件，来上传
            			for (File file2 : listFiles) {
    						if(file2.isFile()){
    							if(file2.getName().startsWith("hdj_") && file2.getName().endsWith(".log")){
    	                    		// 上传的目录
                            		boolean result = sf.upload(ftpPath, file2.getPath(), sftp);
                            		if(!result){
                            			logger.error("log日志文件上传失败--->"+file2.getName());
                            		}else{
                            			logger.info("log日志文件上传成功--->"+file2.getName());
                            		}
    							}
    						}
    					}
            			
            		}
				} catch (Exception e) {
					logger.error("log日志文件上传程序异常");
				}finally{
					if (sftp!= null && sftp.isConnected()){
        				sftp.disconnect();
        			}
        			if (sshSession!= null && sshSession.isConnected()){
        				sshSession.disconnect();
        			}
				}
            	logger.info("结束上传log日志......");
            }
        }, 1000*60,1000*60*60 );  
	}
}
