package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.boomhope.Bill.Comm.CDJPrintBill;
import com.boomhope.Bill.Comm.GSPrintApplication;
import com.boomhope.Bill.Comm.LostPrint;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.LostReport.Bean.AccLostAndReturnInfoBean;
import com.boomhope.Bill.TransService.LostReport.Bean.SearchProducRateInfo02864;
import com.boomhope.Bill.TransService.LostReport.Bean.ShowAccNoMsgBean;
import com.boomhope.Bill.TransService.LostReport.Interface.InterfaceSendMsg;
import com.boomhope.Bill.Util.DateUtil;
import com.boomhope.Bill.Util.FileUtil;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.JpgUtil_GS;
import com.boomhope.Bill.Util.JpgUtil_LostSH;
import com.boomhope.Bill.Util.MoneyUtils;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
import com.jacob.com.Dispatch;

@SuppressWarnings({"static-access","rawtypes"})
public class LostProcessingPage extends BaseTransPanelNew{

	private static Logger logger = Logger.getLogger(LostProcessingPage.class);
	private static final long serialVersionUID = 1L;
	
	private JLabel textLabel;
	private JLabel exit;
	private JLabel errorShow;
	private int processNo=0;//0-开始处理 1-挂失处理完成,打印申请书和补发存单 2-业务处理完成
	
	public LostProcessingPage(){
		logger.info("进入处理页面");
		lostPubBean.setThisComponent(this);
		
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondMaxTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondMaxTime * 1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*倒计时结束退出交易*/
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！","","");
			}
		});
		delayTimer.start();
		
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	excuteVoice("voice/lostProssing.wav");
            	stopTimer(voiceTimer);//关闭语音
            	closeVoice();
            }      
        });            
		voiceTimer.start();
		
		textLabel = new JLabel();
		textLabel.setBounds(0, 220, 1009, 90);
		textLabel.setFont(new Font("微软雅黑",Font.BOLD,35));
		textLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(textLabel);
		errorShow = new JLabel("打印挂失申请书失败");
		errorShow.setBounds(0, 400, 1009, 90);
		errorShow.setFont(new Font("微软雅黑",Font.PLAIN,25));
		errorShow.setForeground(Color.RED);
		errorShow.setHorizontalAlignment(SwingUtilities.CENTER);
		errorShow.setVisible(false);
		this.showJpanel.add(errorShow);
		//退出
		exit= new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.setVisible(false);
		exit.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
            	closeVoice();//关闭语音流
            	returnHome();
			}
		});
		add(exit);	
		
		setTextShow();
		processing();
		
	}
	
	//挂失处理方法
	public void processing(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				if("0".equals(lostPubBean.getLostOrSolve())){//单独挂失
					String style =((ShowAccNoMsgBean)lostPubBean.getAccMap().get("selectMsg")).getCardOrAccOrAcc1();
					if("0".equals(style)){//挂失银行卡
						if(!lostAction.lostCardProcessing()){
							logger.info("银行卡单独挂失失败");
							return;
						}
			//			lostPubBean.getReqMCM001().setUsevouchertype("113");
					}else{//存单或者存折挂失
						if(!lostAction.lostAccProcessing()){
							logger.info("存单/存折单独挂失失败");
							return;
						}
//						if("2".equals(((ShowAccNoMsgBean) lostPubBean.getAccMap().get("selectMsg")).getCardOrAccOrAcc1())){//存折
//							lostPubBean.getReqMCM001().setUsevouchertype("101");
//						}else{
//							lostPubBean.getReqMCM001().setUsevouchertype("102");
//							lostPubBean.getReqMCM001().setUsevoucherno(lostPubBean.getCertNo());
//						}
					}
				}else if("1".equals(lostPubBean.getLostOrSolve()) || "3".equals(lostPubBean.getLostOrSolve())){//挂失补领
					if(!lostAction.lostAndGetNew()){
						logger.info("挂失补发失败");
						return;
					}
					lostPubBean.getReqMCM001().setUsevouchertype("102");
					lostPubBean.getReqMCM001().setUsevoucherno(lostPubBean.getCertNoAdd());
					lostPubBean.getReqMCM001().setRvouchertype("102");
					lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getCertNo());
					lostPubBean.getReqMCM001().setRecoverypea(lostPubBean.getShtdy()!=null&&Double.valueOf(lostPubBean.getShtdy())>0?String.format("%.2",new Double(lostPubBean.getShtdy())):"");
					lostPubBean.getReqMCM001().setInterest(lostPubBean.getCancelRealInte()!=null && !"".equals(lostPubBean.getCancelRealInte().trim())?String.format("%.2f", new Double(lostPubBean.getCancelRealInte())):"");
				}else if("2".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//挂失销户
					
					if(!lostAction.lostCancel()){
						logger.info("挂失销户失败");
						return;
					}
					if("4".equals(lostPubBean.getLostOrSolve())){//解挂销户
						if("0".equals(lostPubBean.getSolveLostType())){
							lostPubBean.getReqMCM001().setRvouchertype("113");
							lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getAllPubAccNo());
						}else if("2".equals(lostPubBean.getSolveLostType())){//存折
							lostPubBean.getReqMCM001().setRvouchertype("101");
							lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getCertNo());
						}else{
							lostPubBean.getReqMCM001().setRvouchertype("102");
							lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getCertNo());
						}
					}else{//挂失销户
						ShowAccNoMsgBean show=(ShowAccNoMsgBean) lostPubBean.getAccMap().get("selectMsg");//挂失种类
						if("0".equals(show.getCardOrAccOrAcc1())){
							lostPubBean.getReqMCM001().setRvouchertype("113");
							lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getAllPubAccNo());
						}else if("2".equals(show.getCardOrAccOrAcc1())){
			            	  lostPubBean.getReqMCM001().setRvouchertype("101");
			            	  lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getCertNo());
			              }else{
			            	  lostPubBean.getReqMCM001().setRvouchertype("102");
			            	  lostPubBean.getReqMCM001().setRvoucherno(lostPubBean.getCertNo());
		    		     }
					}
					lostPubBean.getReqMCM001().setLendirection("1");
					lostPubBean.getReqMCM001().setToaccount(lostPubBean.getSelectCardNo());
					lostPubBean.getReqMCM001().setInterest(lostPubBean.getCancelRealInte());
				}else if("5".equals(lostPubBean.getLostOrSolve())){
					if(!lostAction.solveLostReBack()){
						logger.info("挂失撤销失败");
						return;
					}
				}
				
				//业务完成，准备流水上送的信息
				lostPubBean.getReqMCM001().setCentertrjnno(lostPubBean.getLostJrnlNo());//核心流水号
				lostPubBean.getReqMCM001().setCustomername(lostPubBean.getAllPubIdCardName());//客户名称
				if("1".equals(lostPubBean.getLostOrSolve()) || "3".equals(lostPubBean.getLostOrSolve())){//补发
					ShowAccNoMsgBean show=(ShowAccNoMsgBean) lostPubBean.getAccMap().get("selectMsg");//挂失种类
					if("1".equals(show.getCardOrAccOrAcc1())){
        			      lostPubBean.getReqMCM001().setAccount(lostPubBean.getNewAccNo());
        			  }else{
        				  lostPubBean.getReqMCM001().setAccount(lostPubBean.getAllPubAccNo());//账号
        			  } 
				}else{
					lostPubBean.getReqMCM001().setAccount(lostPubBean.getAllPubAccNo());//账号
				}
				lostPubBean.getReqMCM001().setTransamt(lostPubBean.getEndAmt());//交易金额
				lostPubBean.getReqMCM001().setReportlossid(lostPubBean.getLostApplNo());//挂失申请书号
				
				//生成电子印章图片
				boolean flag = false;
				// 拼接调用电子印章所需要的参数
				JSONObject jsonObject = getLostJsons();
				// 调用电子印章http接口（base64编码）
				String dzyz = getDZYZ(Property.dzyz_url, jsonObject);
				if (dzyz != null && !"-1".equals(dzyz)) {
					// 将base64编码转换成图片
					flag = GenerateImage(dzyz, Property.dzyz_ml);
					// 获取电子印章
					if (!flag) {
						logger.error("获取电子印章失败");
					}else{
						lostPubBean.setAllPubDzyz("Y");
					}
				} else {
					logger.error("获取电子印章失败");
				}
				
				//调用生成事后监督
				JpgUtil_GS gs=new JpgUtil_GS();
				String resultJpg = gs.ShjdJpgPrint(lostPubBean);
				if("".equals(resultJpg)){
					logger.info("生成PDF图片失败。");
				}else{
					logger.info("生成PDF图片成功。。");
				    boolean result=gs.wordToJpg(lostPubBean, resultJpg);
				    if(!result){
				    	logger.info("事后监督图片生成失败——————>");
				    }
				}
				try{//进入睡眠3秒
					Thread.sleep(3000);
				}catch(Exception e){
					logger.info("睡眠失败"+e);
				}
				//生成事后申请书图片上传
				JpgUtil_LostSH gsJpg = new JpgUtil_LostSH();
				String resultl = gsJpg.ShjdJpgPrint(lostPubBean);				
				if(!"".equals(resultl)){
					boolean resut=gsJpg.pdfToJpgG(lostPubBean, resultl);
					if(!resut){
						 logger.info("事后申请书图片jpg生成失败...");
					}else{
					  logger.info("发送申请书图片路径给前置...");
					  //把上传的路径发给前置
					  if(!FtpJpg()){
						  try {
							logger.info("发送申请书图片路径失败...复制图片到失败文件夹里");
							File file=new  File(resultl.substring(0,resultl.indexOf("."))+".jpg");
							FileUtil.copyFileUsingJava7Files(file,new File(Property.bill_sb_path+file.getName()));
						  } catch (IOException e) {
							  logger.info("复制文件失败："+e);
						  }
					    }
					}
				}else{
					logger.info("事后申请书图片pdf生成失败...");
				}
				try{//进入睡眠3秒
					Thread.sleep(3000);
				}catch(Exception e){
					logger.info("睡眠失败"+e);
				}
				
				//更新页面显示
				processNo=1;
				setTextShow();
				//打印银行联申请书
				LostPrint lostprint=new LostPrint();
				boolean resultPrint=lostprint.lostPrint(lostPubBean);
				if(!resultPrint){
					errorShow.setVisible(true);
					logger.info("打印银行联申请书失败");	
					lostPubBean.getReqMCM001().setTransreserve0("银行联挂失申请书打印失败");
					
				}	
				//调用打印机打印申请书和存单
				if("1".equals(lostPubBean.getLostOrSolve())|| "3".equals(lostPubBean.getLostOrSolve())){//如果补发存单，则打印存单
					
					//若产品为安享存、约享存、聚享存，则查询窗口期利率
					if(lostPubBean.getProCode().startsWith("Y") || lostPubBean.getProCode().startsWith("A") || lostPubBean.getProCode().startsWith("JJ") || lostPubBean.getProCode().startsWith("F")){
						
						Map rateInfo = getRateInfo();
						if(!"000000".equals(rateInfo.get("resCode"))){
							logger.info("查询窗口期利率失败");
							//上送流水信息
							lostPubBean.getReqMCM001().setTransresult("2");
							MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
							serverStop("挂失成功，查询窗口期失败，请联系大堂经理。", "", (String)rateInfo.get("errMsg"));
							return;
						}
					}
					
					//打印存单
					CDJPrintBill printBill = new CDJPrintBill();
					if(!printBill.lostPrintBill(lostPubBean)){
						logger.info("打印存单失败");
						//打印存单失败，上送流水信息
						lostPubBean.getReqMCM001().setTransresult("2");
						lostPubBean.getReqMCM001().setReqBefor("");
						lostPubBean.getReqMCM001().setIntereturnmsg("打印存单失败");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop("挂失成功，打印存单失败，请联系大堂经理。", "", "调用存单打印失败");
						return;
					}
					//打印存单成功后更新凭证号
					updateBill();
					
				}
				if("0".equals(lostPubBean.getLostOrSolve())){//单独挂失打印申请书 
					GSPrintApplication gspApp = new GSPrintApplication();
					Map map = gspApp.PrintApplication(lostPubBean);
					String result = (String)map.get("resCode");
				   if(!"000000".equals(result)){
						logger.info("打印挂失申请书失败："+map.get("error"));					 
						clearTimeText();
						//打印存单失败，上送流水信息
						lostPubBean.getReqMCM001().setTransresult("2");
						lostPubBean.getReqMCM001().setReqBefor("");
						lostPubBean.getReqMCM001().setIntereturnmsg("打印挂失申请书失败");
						MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
						serverStop("打印挂失申请书失败，请联系大堂经理。", "", (String)map.get("error"));
						return;
				    }
				}
				
				//业务办理成功，上送业务流水信息
				lostPubBean.getReqMCM001().setTransresult("0");
				MakeMonitorInfo.makeInfos(lostPubBean.getReqMCM001());
				
				//业务处理完成提示语
				processNo=2;
				setTextShow();
				//挂失完成提示语音
				if("5".equals(lostPubBean.getLostOrSolve())){
					voiceTimer = new Timer(voiceSecond, new ActionListener() {          
						public void actionPerformed(ActionEvent e) {  
							excuteVoice("voice/lostCancelSuccess.wav");
							stopTimer(voiceTimer);//关闭语音
							closeVoice();
						}      
					});     
					
				}else{
					voiceTimer = new Timer(voiceSecond, new ActionListener() {          
						public void actionPerformed(ActionEvent e) {  
							excuteVoice("voice/lostOver.wav");
							stopTimer(voiceTimer);//关闭语音
							closeVoice();
						}      
					});            
				}
				voiceTimer.start();
				
			}
		}).start();
	}
	
	/** 更新凭证号 */
	public void updateBill() {
		logger.info("进入更新凭证号方法");
		try {
			Map<String, String> updateMap = new HashMap<String, String>();
			updateMap.put("nowNo", String.format("%08d",
					(Long.parseLong(lostPubBean.getCertNoAdd()) + 1)));// 更新当前凭证号
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));
			updateMap.put("isNo", "");
			updateMap.put("id", lostPubBean.getCertNoId());
			updateMap.put("startNo", lostPubBean.getCertStartNo());
			updateMap.put("endNo", lostPubBean.getCertEndNo());
			updateMap.put("createDate", lostPubBean.getCreatTime());
			updateMap.put("updateDate", DateUtil.getNowDate("yyyyMMddHHmmss"));

			Map<String, String> tms0006 = InterfaceSendMsg.Tms0006(updateMap);
			if (!"000000".equals(tms0006.get("resCode"))) {// 凭证更新失败依然打印
				logger.error("更新凭证号失败：" + tms0006.get("errMsg"));
				openMistakeDialog("更新凭证号失败");
			}
		} catch (Exception e) {
			logger.error("更新凭证号失败：" + e);
			openMistakeDialog("更新凭证号失败");
		}
	}
	
	//设置显示处理的提示语
	public void setTextShow(){
		if("0".equals(lostPubBean.getLostOrSolve())){//单独挂失
			if(processNo==0){//正在处理挂失
				textLabel.setText("系统正在处理您的挂失申请，请稍后...");
			}else if(processNo==1){//挂失成功，打印申请书
				textLabel.setText("<html><p text-align:center>挂失成功！</p><p>正在为您打印挂失申请书，请稍后...</p></html>");
			  
			}else if(processNo==2){//打印申请书完成，业务办理完成
				textLabel.setText("请取走您的挂失申请书，妥善保管！");
				exit.setVisible(true);
			}
		}else if("1".equals(lostPubBean.getLostOrSolve()) || "3".equals(lostPubBean.getLostOrSolve())){//挂失补发、解挂补发
			if(processNo==0){//正在处理挂失
				textLabel.setText("系统正在处理您的挂失补发申请，请稍后...");
			}else if(processNo==1){//挂失成功，打印申请书和存单
				textLabel.setText("<html><p text-align:center>挂失补发成功！</p><p>正在为您打印存单和挂失申请书，请稍后...</p></html>");
			}else if(processNo==2){//打印申请书和存单完成，业务办理完成
				textLabel.setText("请取走您的存单和挂失申请书，妥善保管！");
				exit.setVisible(true);
			}
		}else if("2".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//挂失销户、解挂销户
			if(processNo==0){//正在处理挂失销户
				textLabel.setText("系统正在处理您的挂失销户申请，请稍后...");
			}else if(processNo==2){//挂失销户成功，打印凭条，跳转成功页面
				
				//打印凭条，跳转销户成功
				lostCancel();
			}
		}else if("5".equals(lostPubBean.getLostOrSolve())){//挂失撤销
			if(processNo==0){//正在处理挂失
				textLabel.setText("系统正在处理您的挂失撤销申请，请稍后...");
			}else if(processNo==1){//挂失成功，打印申请书
				textLabel.setText("<html><p text-align:center>挂失撤销成功！</p><p>正在为您打印挂失申请书，请稍后...</p></html>");
			}else if(processNo==2){//打印申请书完成，业务办理完成
				textLabel.setText("请取走您的挂失申请书，妥善保管！");
				exit.setVisible(true);
			}
		}
	}
	
	//挂失销户后显示销户后的信息
	public void lostCancel(){
		
		openConfirmDialog("挂失销户成功，是否打印凭条：是：打印。否：不打印。");
		confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
				closeVoice();//关闭语音流
				closeDialog(confirmDialog);
				new Thread(new Runnable() {
					@Override
					public void run() {
						//打印凭条
						lostAction.lostXhPrint();
					}
				}).start();
			}
		});
		confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				clearTimeText();//清空倒计时
				stopTimer(voiceTimer);//关闭语音
				closeVoice();//关闭语音流
				closeDialog(confirmDialog);
				//销户成功页面
				openPanel(new LostAccCancelSuccessPanel());//销户成功页面
			}
		});
		return;
	}
	
	/**
	 * 利率获取
	 */
	public Map getRateInfo(){
		logger.info("查询窗口期利率");
		Map<String,String> map = new HashMap<String, String>();
		BigDecimal fRate = null;
		Map map02864 = null;
		try {
			//上送流水调用接口前信息
			lostPubBean.getReqMCM001().setReqBefor("02864");
			map02864 = InterfaceSendMsg.inter02864(lostPubBean);
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter((String)map02864.get("resCode"), (String)map02864.get("errMsg"));
			if (!"000000".equals(map02864.get("resCode"))) {
				logger.error("窗口期利率查询异常：" + map02864.get("errMsg"));
			}else{
				logger.info("查询窗口期利率成功");
				fRate =new BigDecimal(((String)map02864.get("float")).trim());
				List<SearchProducRateInfo02864> list = (List<SearchProducRateInfo02864>) map02864.get("rateList");
				StringBuffer str = new StringBuffer();
				if(lostPubBean.getProCode().startsWith("FL") || lostPubBean.getProCode().startsWith("FM")){
					float floatRet = Float.parseFloat((String)map02864.get("float"));
					String s = "";
					
					SearchProducRateInfo02864 producRateInfo=list.get(0);
					//获得存单的封闭期	
					String saveDate = producRateInfo.getSavingCount();//存期
					String closeDate="";
					if(saveDate.indexOf("D")!=-1){
						 closeDate = saveDate.substring(0,saveDate.indexOf("D")+1).toUpperCase();
					}else{
						closeDate = saveDate.substring(0,3).toUpperCase();
					}
					String rate = producRateInfo.getRate();//封闭期利率
					double b = Double.parseDouble(rate);
					BigDecimal a1 = new BigDecimal(floatRet);
					BigDecimal b2 = new BigDecimal(b);
					DecimalFormat df1 = new DecimalFormat("0.0000");
					String rate2 = df1.format(a1.add(b2).doubleValue());
					String startDate = producRateInfo.getLockStarDate();
					String endDate = producRateInfo.getLockEndDate();
					if(closeDate.indexOf("D")!=-1){
						s = "锁定期："+startDate+"-"+endDate+",锁定期利率："+rate2+"%";
					}else if(closeDate.indexOf("M")!=-1){
						s = "锁定期："+startDate+"-"+endDate+",锁定期利率："+rate2+"%";
					}
						
					if(s.toString().trim().length()!=0){
						s = "提示："+s;
						s.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
					}
					logger.info("窗口期："+s.toString());
					lostPubBean.setWindownTepterm(s.toString());
				}else{
					if(lostPubBean.getProCode().startsWith("A") ||lostPubBean.getProCode().startsWith("JJ") ){
						String s = "";
						String billS="";
						for (int i = 0; i < list.size(); i++) {

							SearchProducRateInfo02864 producRateInfo = list.get(i);
							String intUppercaseXYCK = MoneyUtils
									.intUppercaseXYCK(producRateInfo.getSavingCount());
							BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
							BigDecimal add = fRate.add(b2);
							String rate = add + "%";
							s = s + intUppercaseXYCK + rate + ";";
							billS = billS + intUppercaseXYCK+";";
						}
						if (s.toString().trim().length() != 0) {
							s = "结息利率提示：" + s;
							s = s.substring(0, s.length() - 1);
							s = s.replaceAll("年", "期");
						}
						if (billS.toString().trim().length() != 0) {
							billS = "结息利率提示：" + billS;
							billS = billS.substring(0, billS.length() - 1);
							billS = billS.replaceAll("年", "期");
						}
						if("1".equals(GlobalParameter.printRateStatus)){
							str.append(billS);
						}else{
							str.append(s);
						}
					}else{
						for (int i = 0; i < list.size(); i++) {
							
							SearchProducRateInfo02864 producRateInfo = list.get(i);
							BigDecimal b2 = new BigDecimal(producRateInfo.getRate().trim());
							BigDecimal b3 = b2.add(fRate);
							if(producRateInfo.getDrawMonth() == null
									|| producRateInfo.getDrawMonth().trim().length() == 0){
								continue;
							}else{
								float rate = Float.parseFloat((String)map02864.get("float"));
								producRateInfo.setInteDate(lostPubBean.getOpenDate());
								StringBuffer l51Str=new StringBuffer();
								if("1".equals(GlobalParameter.printRateStatus)){
									l51Str =producRateInfo.getL51Str2(rate);
								}else{
									l51Str = producRateInfo.getL51Str(rate);
								}
								if (l51Str.toString().trim().length() != 0) {
									if (str.toString().length() == 0) {
										str.append("提前支取选择期：").append(l51Str);
									} else {
										str.append("；").append(l51Str);
									}
								}
							}
							
						}
					}
					logger.info("窗口期："+str.toString());
					lostPubBean.setWindownTepterm(str.toString());
				}
			}
			
		} catch (Exception e) {
			logger.error("调用利率查询接口（02864）异常"+e);
			map02864.put("resCode","999999");
			map02864.put("errMsg","调用利率查询接口（02864）异常");
			//上送流水调用接口后信息
			lostPubBean.getReqMCM001().setReqAfter("999999", "调用02864接口失败");
		}
		return map02864;
	}
	/**
	 * 上传挂失解挂申请书图片-前置【08233】
	 */
	public boolean FtpJpg(){
		try{
			Map inter08233 = InterfaceSendMsg.inter08233(lostPubBean);
			if (!"000000".equals(inter08233.get("resCode"))) {
				logger.error((String) inter08233.get("errMsg"));		
				return false;
				}
	
		}catch(Exception e){
			logger.error("上传挂失解挂申请书图片接口异常：" + e);
			return false;
		}
		return true;
		
	}
	/**
	 * 拼接电子印章信息
	 * @Description: 调用电子印章信息
	 * @Author: hk
	 * @date 2018年4月2日 上午10:51:06
	 */
	public JSONObject getLostJsons() {
		logger.info("开始拼接调用电子印章接口所需要的参数");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "0035");// 渠道号
		jsonObject.put("fserialno", lostPubBean.getAllPubSvrDate()
				+ lostPubBean.getLostJrnlNo().trim());// 开始日期+流水号
		jsonObject.put("instno", GlobalParameter.branchNo);// 机构号
		jsonObject.put("tellerno", GlobalParameter.tellerNo);// 柜员号
		jsonObject.put("chl_tran_code", "");// 外围系统交易码
		jsonObject.put("chl_tran_name", "");// 外围系统交易名称
		jsonObject.put("vouchername", "存单");// 凭证名称
		jsonObject.put("sealtype", "0001");// 电子印章种类（业务专用章）

		// 拼接tradeinfo参数值
		Map<String, String> map = new HashMap<String, String>();
		map.put("柜员号", GlobalParameter.tellerNo);
		map.put("办理渠道", "存单回收机");
		map.put("产品代码", lostPubBean.getProCode());// 产品代码
		map.put("储种", lostPubBean.getProName());// 产品名称
		map.put("账号", lostPubBean.getAllPubAccNo());// 设置账号
		map.put("金额(大写)", "人民币" + lostPubBean.getOpenAmtUpper());// 设置金额大写
		map.put("金额(小写)", "CNY" + lostPubBean.getOpenAmt());// 设置存款金额（小写）
		
		if("0".equals(lostPubBean.getLostOrSolve())){//单挂
			
		}else if("1".equals(lostPubBean.getLostOrSolve()) || "3".equals(lostPubBean.getLostOrSolve())){//补发
			
			AccLostAndReturnInfoBean bean = (AccLostAndReturnInfoBean)lostPubBean.getAccMap().get("resAccInfo");
			map.put("存入日", bean.getOpenDate());// 设置开始日期
			map.put("起息日", bean.getStartRateDate());// 设置核心日期（起息日）
			map.put("到期日", bean.getEndIntDate());
			map.put("存期", bean.getDepTerm());// 设置存期
			map.put("利率", bean.getOpenRate() + "%");// 设置定期利率
			map.put("到期利息", bean.getRateSum());// 设置到期利息
			
		}else if("2".equals(lostPubBean.getLostOrSolve()) || "4".equals(lostPubBean.getLostOrSolve())){//销户
			
		}
		jsonObject.put("tradeinfo", JSONObject.fromObject(map).toString());
		logger.info("拼接调用电子印章接口所需要的参数结束---结果为-->" + jsonObject.toString());
		return jsonObject;
	}
	
	
	/**
	 * base64编码转换图片
	 * @param imgStr 	//base64编码的字符串
	 * @param rootPath	//保存的路径（图片完整路径）
	 * @return
	 */
	public boolean GenerateImage(String imgStr,String rootPath) {
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(rootPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 调用电子印章接口
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public String getDZYZ(String url,JSONObject paramMap){
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String result = httpClientUtil.httpPost(url, paramMap);
		return result;
	}
}


























