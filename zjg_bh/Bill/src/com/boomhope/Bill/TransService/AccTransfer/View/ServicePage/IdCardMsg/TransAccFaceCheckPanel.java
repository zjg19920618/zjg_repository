package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.Util.Base64Util;
import com.boomhope.Bill.Util.HttpClientUtil;
import com.boomhope.Bill.Util.Property;

/**
 * 
 * title:人脸识别处理中
 * @author ly
 * 2016年11月7日下午9:58:25
 */
public class TransAccFaceCheckPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransAccFaceCheckPanel.class);
	Timer checkTimer = null;

	public TransAccFaceCheckPanel(final PublicAccTransferBean transferBean) {
		
		/* 显示时间倒计时 */
		this.showTimeText(20);
		delayTimer = new Timer(20 * 1000, new ActionListener() {          
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
		label.setBounds(0, 130, 1009, 30);
		this.showJpanel.add(label);
		
		process(transferBean);
	}

	/**
	 * 业务处理
	 * 
	 * @return
	 */
	public void process(final PublicAccTransferBean transferBean) {
		new Thread("人脸识别线程") {
			public void run() {
				// 获取联网核查图片路径
				String check_photo = Property.BILL_ID_SELF_FACE;
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
					openPanel(new TransferFaceCheckFail("人脸识别失败:"+exMsg+",请联系大堂经理", "",transferBean));
					return;
				}
				
				double sim = Double.parseDouble(String.valueOf(json.get("sim")));
				double defaultSim = Double.parseDouble(String.valueOf(json.get("defaultSim")));
				logger.info("阈值："+defaultSim+"||||"+"相似度:"+sim);
				if (sim >= defaultSim) {//sim >= defaultSim
					//当人脸识别成功之后则调用接口
					String sims = String.format("%.2f",Double.parseDouble(json.getString("sim")));
					transferBean.setFaceCompareVal(sims);
					openPanel(new TransferSignConfirmPanel(transferBean));
				} else {
					//当人脸识别失败，则跳转终止页，重新拍照
					openPanel(new TransferFaceCheckFail("人脸识别未通过，请联系大堂经理", "",transferBean));
				}

			}
		}.start();
	}
}
