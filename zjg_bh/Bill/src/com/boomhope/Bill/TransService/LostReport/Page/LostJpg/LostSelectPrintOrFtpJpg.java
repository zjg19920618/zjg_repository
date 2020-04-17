package com.boomhope.Bill.TransService.LostReport.Page.LostJpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostConfirmPanel;
import com.boomhope.Bill.Util.UtilButton;
/**
 * 选择上传图片  还是补打申请书
 * @author zb
 *
 */
public class LostSelectPrintOrFtpJpg extends BaseTransPanelNew {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LostSelectPrintOrFtpJpg.class);
	private boolean on_off = true;// 用于控制页面跳转的开关
	public LostSelectPrintOrFtpJpg(){
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
					}
				});
		delayTimer.start();
		/* 上传事后图片 */
		UtilButton trueButton = new UtilButton("pic/newPic/FtpJpg.jpg","pic/newPic/FtpJpg.jpg");
		trueButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击是按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流			
				openPanel(new LostJpgFtpPage());
			}
		});
		trueButton.setBounds(160, 280, 200, 90);
		this.showJpanel.add(trueButton);
		JLabel tishi = new JLabel("温馨提示：仅对图片路径上传失败的图片上传！");
		tishi.setForeground(Color.RED);
		tishi.setBounds(70, 240, 400, 300);
		tishi.setFont(new Font("微软雅黑",Font.PLAIN,14));
		tishi.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi);
		/* 打印申请书 */
		UtilButton falseButton = new UtilButton("pic/newPic/printLost.jpg","pic/newPic/printLost.jpg");
		falseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击打印申请书按钮");
				if (!on_off) {
					logger.info("开关当前状态" + on_off);
					return;
				}
				logger.info("开关当前状态" + on_off);
				on_off = false;

				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流
				openPanel(new LostPrintAllPage());
			}
		});
		falseButton.setBounds(640, 280, 200, 90);
		this.showJpanel.add(falseButton);
		JLabel tishi2 = new JLabel("温馨提示：仅对上笔申请书打印失败的进行打印。");
		tishi2.setForeground(Color.RED);
		tishi2.setBounds(560, 240, 400, 300);
		tishi2.setFont(new Font("微软雅黑",Font.PLAIN,14));
		tishi2.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi2);
		JLabel tishi3 = new JLabel("无A4打印机请勿点击！");
		tishi3.setForeground(Color.RED);
		tishi3.setBounds(565, 260, 400, 300);
		tishi3.setFont(new Font("微软雅黑",Font.PLAIN,14));
		tishi3.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi3);
		// 退出
		JLabel  exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
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
	}
}
