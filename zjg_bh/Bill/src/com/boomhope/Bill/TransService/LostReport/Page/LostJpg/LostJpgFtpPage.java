package com.boomhope.Bill.TransService.LostReport.Page.LostJpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransBean;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.FtpUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

public class LostJpgFtpPage extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LostJpgFtpPage.class);
	private JLabel textLabel;
	private JLabel upFtp;
	private JLabel tishi;
	private boolean on_off=true;//用于控制页面跳转的开关
	private JLabel exit;
	public LostJpgFtpPage(){
	/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondLongTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondLongTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！","","");
			}
		});
		delayTimer.start();
		
		showPage();
	}
	public void showPage(){
		Property.initProperty();
		textLabel = new JLabel();
		textLabel.setBounds(0, 220, 1009, 90);
		textLabel.setFont(new Font("微软雅黑",Font.PLAIN,35));
		textLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(textLabel);
		tishi = new JLabel("上传完成!");
		tishi.setVisible(false);
		tishi.setBounds(0, 270, 989, 55);
		tishi.setForeground(Color.RED);
		tishi.setFont(new Font("微软雅黑",Font.PLAIN,25));
		tishi.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi);
		//上传按钮
		upFtp = new JLabel(new ImageIcon("pic/newPic/FtpJpg.jpg"));
		upFtp.setBounds(350, 300,300, 200);
		upFtp.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上传按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				
				JpgFtp();
			}
		});
		this.showJpanel.add(upFtp);
		// 退出
		exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.setVisible(true);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流
				returnHome();
			}
		});
		add(exit);
		int  str=FileLength();
		if(str==0){		
			textLabel.setText("没有可上传的文件！");
			upFtp.setVisible(false);
		}else{
			textLabel.setText("剩余文件数："+str);
			upFtp.setVisible(true);
		}
	
		
	}
	/**
	 * 统计上传文件失败数量
	 */
	public  int FileLength(){
		File file =new File(Property.bill_sb_path);
		if(!file.isDirectory()){
			logger.info("此文件夹不存在--》"+file);
			return 0;
		}
		File[] files=file.listFiles();
		return files.length;
		
	}
	/**
	 * JPG上传方法
	 * @param args
	 * @throws IOException
	 */
	public void JpgFtp(){
		textLabel.setText("上传图片中，请稍后......");
		upFtp.setVisible(false);	
		new Thread(new Runnable() {
			@Override
			public void run() {		
                int success=0;
                int last=0;
				File file =new File(Property.bill_sb_path);		
				File[] files=file.listFiles();
				for(int i=0; i<files.length;i++){
					File f = files[i];
					File filess = new File(f.getPath());
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(filess);
						boolean b= FtpUtils.uploadFile(Property.FTP_SH_IP,Integer.parseInt(Property.FTP_SH_PORT), Property.FTP_SH_USER, Property.FTP_SH_PWD, Property.FTP_SH_PATH, "",f.getName(),fis);	    		
						if(!b){//上传失败
							logger.error("上传图片失败"+ f);							
							last++;
						}else{
							boolean result=FtpJpg(f.getName());
							if(!result){//判断接口返回是否上传成功
								last++;
							}else{
						        success++;						    
						        deleteFile(f.getPath());//上传成功则删除失败文件里的
							}
						}
						textLabel.setText("上传成功："+success+"， 上传失败："+last+" 。");
					} catch (Exception e) {
						textLabel.setText("上传申请书图片程序异常,请稍后重试......");
						logger.error("上传图片程序异常"+e);
					}finally{
						if(fis!=null){
							try {
								fis.close();
							} catch (IOException e) {
								logger.error("关闭流失败"+e);
							}
						}
					}
				}	
				tishi.setVisible(true);
				if(last!=0){
					upFtp.setVisible(true);
				}
			}
			
		}).start();				
	}
	/**
	 * 上传挂失解挂申请书图片-前置【08233】
	 */
	public boolean FtpJpg(String fileName){
		try{
			logger.info("进入08233上传挂失解挂申请书图片接口");
			LostPubBean  lostPubBean=new LostPubBean();
			lostPubBean.setAllPubSvrDate(fileName.substring(18,26));//核心日期
			lostPubBean.setLostselect(fileName.substring(26,27));//挂失解挂标识
			lostPubBean.setLostApplNo(fileName.substring(0,18));//申请书号
			lostPubBean.setLostJpgPath(Property.FTP_SH_PATH+fileName);//路径
			
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("08233");
			Map inter08233 = InterfaceSendMsg.inter08233(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)inter08233.get("resCode"), (String)inter08233.get("errMsg"));
			
			if (!"000000".equals(inter08233.get("resCode"))) {
				logger.error((String) inter08233.get("errMsg"));	
				//上送流水信息
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				serverStop("上传挂失解挂申请书图片路径失败，请稍后再试...", "", (String)inter08233.get("errMsg"));
				return false;
				}
	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("上传挂失解挂申请书图片接口异常：" + e);
			//调用接口异常，上送流水信息
			lostPubBean.getReqMCM001().setIntereturnmsg("调用08233接口失败");
			MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
			serverStop("上传挂失解挂申请书图片路径接口异常", "", "调用接口08233失败");
			return false;
		}
		return true;
		
	}
	/**
	 * 成功上传事后图片后删除本地图片的方法
	 */
	private void deleteFile(String filePaths) {
	   File filel =new File(filePaths);
		if (filel.isFile()) {// 如果是文件
			System.gc();// 垃圾回收,主要是为了释放上传时被占用的资源图片
			boolean result = filel.delete();
		if (!result) {// 判断是否全部删除
			filel.delete();
		}
		logger.info("删除成功" + filel);
		}
	}
	public static void main(String[] args) throws IOException {
 
	}
	
}
