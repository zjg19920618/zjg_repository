package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccPrintCameraPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.Util.SoftKeyBoardPopups2;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;

/**
 * 输入收益人账号
 * 
 * @author zjg
 *
 */
public class AccInputBankMsgPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccInputBankMsgPanel.class);
	JLabel label = null;
	private static final long serialVersionUID = 1L;
	public JTextField textField2;//输入文本框
	private SoftKeyBoardPopups2 keyPopup;//软键盘类对象
	public JPanel KeyBordJp;//软键盘窗口
	JLabel errInfo1 = null;// 错误提示
	
	private Map<Object,Object> params;
	private AccPublicBean accBean;
	private Component comp ;
	private boolean on_off=true;
	
	public AccInputBankMsgPanel(Map<Object, Object> params2) {
		logger.info("进入输入收益人账号页面");
		comp =this;
		this.params = params2;
		accBean = AccountTradeCodeAction.transBean;
		
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondLongTime);
		delayTimer = new Timer(delaySecondLongTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("输入收益人页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		/* 标题信息 */
		JLabel titleLabel = new JLabel("收益人信息");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 90, GlobalParameter.TRANS_WIDTH - 50, 40);
		this.showJpanel.add(titleLabel);

		JLabel lblNewLabel_1 = new JLabel("收益人卡号：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(139, 203, 120, 50);
		this.showJpanel.add(lblNewLabel_1);

		/* 错误提示框 */
		errInfo1 = new JLabel();
		errInfo1.setHorizontalAlignment(JLabel.CENTER);
		errInfo1.setForeground(Color.RED);
		errInfo1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		errInfo1.setBounds(280, 280, 511, 23);
		this.showJpanel.add(errInfo1);

		// 账号
		textField2 = new JTextField();
		textField2.setFont(new Font("微软雅黑", Font.PLAIN, 40));
		textField2.setBounds(269, 203, 511, 50);
		textField2.setColumns(10);
		if ("LZ".equals(params2.get("PRODUCT_CODE"))
				|| "AX".equals(params2.get("PRODUCT_CODE"))) {
			textField2.setText(accBean.getCardNo());
		} else {
			textField2.setText("");
			errInfo1.setText("请点击文本输入框输入收益人帐号。");
		}
		textField2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击输入框");
				errInfo1.setText("");
				scanBill12();
			}
			
		});
		this.showJpanel.add(textField2);
		

		/**
		 * 创建软键盘
		 *
		 */
		textField2.setColumns(10);
		keyPopup = new SoftKeyBoardPopups2(textField2);
		keyPopup.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		
		/*软键盘面板*/
		KeyBordJp = new JPanel(new BorderLayout());
		KeyBordJp.setBackground(Color.WHITE);
		KeyBordJp.setPreferredSize(new Dimension(2024, 30));
		KeyBordJp.setLayout(new BorderLayout());
		KeyBordJp.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.showJpanel.add(KeyBordJp);
		

		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				backTrans();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 确认
		JLabel confirm = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击确认按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				nextStep();
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);

		/* 提示 */
		JLabel label_1 = new JLabel("提示：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_1.setBounds(131, 347, 155, 32);
		this.showJpanel.add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		label_2.setBounds(275, 347, 489, 236);
		this.showJpanel.add(label_2);

		// 清空按钮
		UtilButton utilButton = new UtilButton("pic/qk.png", "pic/qk.png");
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击清空按钮");
				textField2.setText("");

			}

		});
		utilButton.setBounds(800, 203, 100, 50);
		this.showJpanel.add(utilButton);
		if ("LZ".equals(accBean.getProductCode().substring(0, 2))) {
			label_2.setText("<html>1、立得存-自享，收益人卡号默认本卡号。<p>2、页面“清空”按钮，清空全部卡号。<p>3、点击输入框，启动软键盘，点击“删除”键，删除最后一位卡号。<p>4、该产品需客户指定本人名下的一张唐山银行的银行卡为收益账户。</html>");
		}
		if ("LT".equals(accBean.getProductCode().substring(0, 2))) {
			label_2.setText("<html>1、该产品需客户指定除本人以外的第三人名下的一张唐山银行的银行卡账户为收益账户。<p>2、页面“清空”按钮，清空全部卡号。<p>3、点击输入框，启动软键盘，点击“删除”键，删除最后一位卡号。</html>");
		}
		if ("AX".equals(accBean.getProductCode().substring(0, 2))) {
			label_2.setText("<html>1、安享存，收益人卡号默认本卡号。<p>2、页面“清空”按钮，清空全部卡号。<p>3、点击输入框，启动软键盘，点击“删除”键，删除最后一位卡号。<p>4、该产品需客户指定本人名下的一张唐山银行的银行卡为收益账户。</html>");
		}
	}

	/**
	 * 返回
	 * */
	public void backTrans() {
		logger.info("进入上一步方法");
		
		clearTimeText();//清空倒计时
    	stopTimer(voiceTimer);//关闭语音
    	closeVoice();
    	//跳转到信息确认页
    	if("1".equals(accBean.getTransChangeNo().trim())){
    		accBean.setCameraCount("");
    		openPanel(new AccPrintCameraPanel(params));
    	}else{
    		accBean.setZzAmt(accBean.getBeiZzAmt().trim());
    		openPanel(new AccProInputPanel(params));
    	}
	}

	/**
	 * 下一步
	 */
	public void nextStep() {
		logger.info("进入确认方法");

		new Thread() {
			public void run() {
				String benCardNo = textField2.getText();// 获取收益人卡号
				String IdNo = accBean.getIdCardNo();//开户卡证件号
				String IdName = accBean.getIdCardName();//开户卡姓名

				if (textField2.getText() == null
						|| textField2.getText().equals("")) {
					errInfo1.setText("提示：请输入帐号!");
					logger.info("输入框内无号码，需要输入。" + benCardNo);
					on_off=true;
					return;
				} else if (benCardNo.length() < 19 || benCardNo.length() > 19) {
					errInfo1.setText("输入的卡号不正确，请输入19位卡号");
					logger.info("输入的卡号不正确。" + benCardNo);
					on_off=true;
					return;
				}
				
				
				AccPublicBean benAccBean = new AccPublicBean();
				benAccBean.setCardNo(benCardNo);
				benAccBean.setCardIsPin("0");// 查询收益人时不用进行验密
				
				
				
				try {
					// 查询收益人卡信息
					prossDialog.showDialog();
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("03845");
					Map<String, Object> searchcard = IntefaceSendMsg
							.inter03845(benAccBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)searchcard.get("resCode"), (String)searchcard.get("errMsg"));
					prossDialog.disposeDialog();
					if ("4444".equals(searchcard.get("resCode"))) {
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();
						MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
						serverStop("抱歉，系统出错了，等会再试吧。", "","调用03845接口不通");
						return;
					}
					if ("9999".equals(searchcard.get("resCode"))) {
						logger.info("银行卡状态异常："
								+ searchcard.get("errMsg"));
						errInfo1.setText("此卡状态异常，请更换其它卡号！");
						on_off=true;
						return;
					}
					if("5555".equals(searchcard.get("resCode"))){
						logger.info("银行卡状态异常："
								+ searchcard.get("errMsg"));
						errInfo1.setText((String)searchcard.get("errMsg"));
						on_off=true;
						return;
					}
					if (!"000000".equals(searchcard.get("resCode"))) {
						logger.info("自享，查询姓名，账号。卡查询失败:"
								+ searchcard.get("errMsg"));
						errInfo1.setText("该卡号不存在或不是本行银行卡,请输入本行的银行卡号！");
						on_off=true;
						return;
					}
				} catch (Exception e1) {
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					prossDialog.disposeDialog();
					logger.info("收益人卡号信息查询失败后：" + benAccBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用03845接口异常");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop("抱歉，收益人卡信息查询时失败。", "","调用03845接口异常");
					return;
				}

				// 查询身份信息
				try {
					prossDialog.showDialog();
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("03445");
					Map<String, Object> reCustInfo = IntefaceSendMsg
							.inter03445(benAccBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)reCustInfo.get("resCode"), (String)reCustInfo.get("errMsg"));
					prossDialog.disposeDialog();
					if (!"000000".equals(reCustInfo.get("resCode"))) {
						if ("4444".equals(reCustInfo.get("resCode"))) {
							logger.info("调用身份信息查询失败");
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关闭语音
							closeVoice();
							MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
							serverStop("抱歉，系统出错了，等会再试吧。", "","调用03445接口不通");
							return;
						} else {
							errInfo1.setText("此卡身份信息异常，请更换其它卡号！");
							logger.info("身份信息异常");
							on_off=true;
							return;
						}
					}
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("查询身份信息异常：" + e);
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用03445接口异常");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop("抱歉，查询身份信息失败", "","调用03445接口异常");
					return;
				}
				logger.info("收益人身份信息查询后：" + benAccBean);
				
				
				//查询账户信息状态
				try {
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("03521");
					Map inter03521 = IntefaceSendMsg.inter03521(benAccBean); 
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)inter03521.get("resCode"), (String)inter03521.get("errMsg"));
					if(!"000000".equals(inter03521.get("resCode"))){
						
						if ("4444".equals(inter03521.get("resCode"))) {

							prossDialog.disposeDialog();
							logger.error(inter03521.get("errMsg"));
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关闭语音
							closeVoice();
							MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
							serverStop("抱歉，系统出错了，等会再试吧。","","调用03521接口不通");
							return;
							
						}else{
							
							prossDialog.disposeDialog();
							logger.info(inter03521.get("errMsg"));
							errInfo1.setText((String) inter03521.get("errMsg"));
							on_off=true;
							return; 
						}
					}
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("查询账户信息失败(03521)："+e);
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用03521接口异常");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop("查询账户信息失败，请联系大堂经理。", "", "调用账户信息查询接口失败（03521）");
					return;
				}
				
				//卡账户信息查询2-07601
				try {
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("07601");
					Map card07601 = IntefaceSendMsg.card07601(benAccBean); 
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)card07601.get("resCode"), (String)card07601.get("errMsg"));
					if(!"000000".equals(card07601.get("resCode"))){
						
						if ("4444".equals(card07601.get("resCode"))) {

							prossDialog.disposeDialog();
							logger.error(card07601.get("errMsg"));
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关闭语音
							closeVoice();
							MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
							serverStop("抱歉，系统出错了，等会再试吧。","","调用07601接口不通");
							return;
							
						}else{
							
							prossDialog.disposeDialog();
							logger.info(card07601.get("errMsg"));
							errInfo1.setText((String) card07601.get("errMsg"));
							on_off=true;
							return; 
						}
					}
					
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("卡账户信息查询失败"+e);
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用07601接口异常");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop("卡账户信息查询失败，请联系大堂经理","", "调用07601接口异常");
					return;
				}

				// 查询黑灰名单
				try {
					prossDialog.showDialog();
					AccountTradeCodeAction.transBean.getReqMCM001().setReqBefor("01597");
					Map<String, Object> reBlackInfo = IntefaceSendMsg
							.inter01597(benAccBean);
					AccountTradeCodeAction.transBean.getReqMCM001().setReqAfter((String)reBlackInfo.get("resCode"), (String)reBlackInfo.get("errMsg"));
					prossDialog.disposeDialog();
					if (!"000000".equals(reBlackInfo.get("resCode"))) {
						if ("0010".equals(reBlackInfo.get("resCode"))) {
							errInfo1.setText("此卡已被列入黑名单，请更换其它卡号！");
							logger.info("黑名单卡号");
						} else if ("0020".equals(reBlackInfo
								.get("resCode"))) {
							errInfo1.setText("此卡已被列入灰名单，请更换其它卡号！");
							logger.info("灰名单卡号");
						} else if ("4444".equals(reBlackInfo
								.get("resCode"))) {
							logger.info("查询黑灰名单失败");
							clearTimeText();// 清空倒计时
							stopTimer(voiceTimer);// 关闭语音
							MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
							serverStop("抱歉，系统出错了，等会再试吧。", "","调用01597接口不通");
							return;
						} else {
							logger.info("其它异常");
							errInfo1.setText("此卡存在黑灰名单异常，请更换其它卡号！");
						}
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();
						on_off=true;
						return;
					}
				} catch (Exception e) {
					prossDialog.disposeDialog();
					logger.error("查询黑灰名异常" + e);
					clearTimeText();// 清空倒计时
					stopTimer(voiceTimer);// 关闭语音
					closeVoice();
					AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("调用01597接口异常");
					MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
					serverStop("抱歉，黑灰名单信息失败", "","调用01597接口异常");
					return;
				}	
					
				// 判断自享还是他享或者安享
				if (accBean.getProductCode().startsWith("LZ")
						|| accBean.getProductCode().startsWith("AX")) {
					// 安享或者自享，查询客户号，姓名，账号
					try {
						
						if(!IdNo.equals(benAccBean.getIdCardNo()) || !IdName.equals(benAccBean.getIdCardName())){
							errInfo1.setText("收益卡号和开户卡号必须为同一人!");
							logger.info("收益人卡号和开户卡号不同");
							on_off=true;
							return;
						}
						// 查询各类信息无异常，将收益人卡号存入到accBean中
						accBean.setInputNo(benCardNo);// 存入收益人卡号
						accBean.setInputName(benAccBean.getIdCardName());
						accBean.setRelaAcctNo(benCardNo);// 收益人卡号

					} catch (Exception e) {
						logger.error("对比账号失败：" + e);
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();
						AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("对比受益人卡号和开户人卡号时异常");
						MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
						serverStop("抱歉，账号对比时出错了。", "","对比收益人卡号和开户卡号时异常");
						return;
					}
				} else if ("LT".equals(accBean.getProductCode().substring(0, 2))) {
					// 他享，查询姓名，账号
					try {
						
						if(IdNo.equals(benAccBean.getIdCardNo()) && IdName.equals(benAccBean.getIdCardName())){
							errInfo1.setText("收益人不能为本客户,请重新输入！");
							logger.info("收益人卡号与开户卡号相同");
							on_off=true;
							return;
						}
						
						// 查询各类信息无异常，将收益人卡号存入到accBean中
						accBean.setInputNo(benCardNo);
						accBean.setInputName(benAccBean.getIdCardName());
						accBean.setRelaAcctNo(benCardNo);
						
					} catch (Exception e) {
						logger.error("对比账号失败：" + e);
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关闭语音
						closeVoice();
						AccountTradeCodeAction.transBean.getReqMCM001().setIntereturnmsg("对比受益人卡号和开户人卡号时异常");
						MakeMonitorInfo.makeInfos(AccountTradeCodeAction.transBean.getReqMCM001());
						serverStop("抱歉，账号对比时出错了。", "","对比收益人卡号和开户卡号时异常");
						return;
					}
				}

			
				/**
				 * 跳转到收益人信息确认页
				 * 
				 */
				logger.info("核查信息无误，进入下一页");
				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();
				openPanel(new AccSyrPagesPanel(params));
			}
		}.start();

	}

	/***
	 * 软键盘调用
	 */
	private void scanBill12() {
		logger.info("进入调用软键盘方法");
		try {
			keyPopup.show(KeyBordJp, 330, 300);
			textField2.grabFocus();

		} catch (Exception e) {
			logger.info("调用软键盘" + e);
		}
	}

	/**
	 * 设置输入框内的内容
	 * 
	 * @param textField2
	 */
	public void setTextField2(JTextField textField2) {
		this.textField2 = textField2;
	}

	public JTextField getTextField2() {
		return textField2;
	}

}
