package com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.companyView;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Framework.MainFrame;
import com.boomhope.Bill.PublicControl.CheckConfirmDialog;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.Bean.TransferSelectBean;
import com.boomhope.Bill.TransService.AccTransfer.Interface.InterfaceSendMsg;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg.TransferAccInputIdCardPanel;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.IdCardMsg.TransferSignConfirmPanel;
import com.boomhope.Bill.Util.DateTool;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 到账方式选择
 * @author hao
 *
 */

public class TransferAccSelectPanel extends BaseTransPanelNew {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(TransferAccSelectPanel.class);
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private PublicAccTransferBean transferMoneyBean;//私有公共bean
	final Component comp;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	/**
	 * 初始化
	 */
	public TransferAccSelectPanel(final PublicAccTransferBean transferBean) {
		comp = this ;
		
		this.transferMoneyBean = transferBean;
		logger.info("进入到账方式选择页面");
		
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
		
		JLabel lblNewLabel = new JLabel("到账方式选择");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		lblNewLabel.setBounds(0, 100, 1009, 40);
		this.showJpanel.add(lblNewLabel);
		
		//判断是个人还是单位汇款
	
		//存放图片1
		label1 = new JLabel();
		label1.setBounds(100, 240, 230, 230);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
		});
		this.showJpanel.add(label1);
		label1.setVisible(false);
		
		//存放图片2
		label2 = new JLabel();
		label2.setBounds(390, 240, 230, 230);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
		});
		this.showJpanel.add(label2);
		label2.setVisible(false);
		
		//存放图片3
		label3 = new JLabel();
		label3.setBounds(680, 240, 230, 230);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
		});
		this.showJpanel.add(label3);
		label3.setVisible(false);
		
		//存放图片4
		label4 = new JLabel();
		label4.setBounds(225, 240, 230, 230);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
		});
		this.showJpanel.add(label4);
		label4.setVisible(false);
		
		//存放图片5
		label5 = new JLabel();
		label5.setBounds(555, 240, 230, 230);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
			}
		});
		this.showJpanel.add(label5);
		label5.setVisible(false);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				//跳转回上一页面
				transferMoneyBean.setNextDaySendFlag("");
				transferMoneyBean.setTransMarkNo("");
				transferMoneyBean.setZhangWay("");
				openPanel(new TransferRemitInfomationPanel(transferMoneyBean));
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		this.add(submitBtn);
		
		// 返回
		JLabel utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				//清空倒计时
				clearTimeText();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(utilButton);
		
		chooseMothed();
	}
	
	//确认进入下一页
	public void nextstep(){
		logger.info("进入下一页面前进行转账操作");
		new Thread() {
			@Override
			public void run() {
				openProssDialog();
				if(transferMoneyBean.getTransMarkNo()==null ||"".equals(transferMoneyBean.getTransMarkNo())){
					prossDialog.disposeDialog();
					on_off=true;
					openMistakeDialog("请选择到账方式");
					return;
				}else if("1".equals(transferMoneyBean.getTransMarkNo()) && "0".equals(transferMoneyBean.getNextDaySendFlag())){//大额实时到账
					//查询大额系统是否关闭
					logger.info("进入大额系统查询");
					transferMoneyBean.setParCode("CUR_SYS_STAT");
					if(!checkSystem()){
						//清空倒计时
						clearTimeText();
						closeDialog(prossDialog);
						on_off=true;
						return;
					}
					if(!chooseTrans()){
						return;
					}
				}
				transContiune();
				
			}
		}.start();
	}
	
	//显示到账方式
	public void chooseMothed(){
		logger.info("进入到账方式判断：首先判断为个人卡还是单位卡");
		/**
		 * 个人卡行内转账
		 */
		if("0".equals(transferMoneyBean.getIsCardBin())&&"0".equals(transferMoneyBean.getIsCardBank())){
			logger.info("个人卡行内转账");
			/**
			 * 行内同名卡号转账
			 */
			if(transferMoneyBean.getChuZhangcardName()!=null && transferMoneyBean.getRuZhangcardName()!=null && transferMoneyBean.getChuZhangcardName().equals(transferMoneyBean.getRuZhangcardName()) ){
				label2.setIcon(new ImageIcon("pic/real_timeAccount.png"));
				label2.setVisible(true);
				label2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("0");
						transferMoneyBean.setZhangWay("实时到账");
						nextstep();
					};
				});
			/**
			 * 行内不同名转账
			 */
			}else{
				//显示实时到账
				label4.setIcon(new ImageIcon("pic/real_timeAccount.png"));
				label4.setVisible(true);
				label4.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("0");
						transferMoneyBean.setZhangWay("实时到账");
						nextstep();
					}				
				});
				
				//显示次日到账 
				label5.setIcon(new ImageIcon("pic/nextDayAccount.png"));
				label5.setVisible(true);
				label5.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("1");
						transferMoneyBean.setTransMarkNo("0");
						transferMoneyBean.setZhangWay("次日到账");
						nextstep();
					}				
				});
				
			}
			
		/**
		 * 个人卡跨行转账	
		 */
		}else if("0".equals(transferMoneyBean.getIsCardBin()) && "1".equals(transferMoneyBean.getIsCardBank())){
			logger.info("个人卡跨行转账");
			//正常工作日
			if("0".equals(transferMoneyBean.getIsWorkDay())){
				logger.info("正常工作日");
				//金额>5万
				if(Float.parseFloat(transferMoneyBean.getRemitAmt())>50000){
					logger.info("金额>5万");
					//显示实时到账
					label4.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label4.setVisible(true);
					label4.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}				
					});
					
					//显示次日到账 
					label5.setIcon(new ImageIcon("pic/nextDayAccount.png"));
					label5.setVisible(true);
					label5.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("1");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("次日到账");
							nextstep();
						}				
					});
				//金额<=5万
				}else{
					logger.info("金额<=5万");
					//实时到账 
					label1.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label1.setVisible(true);
					label1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}						
					});
					//普通到账
					label2.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
					label2.setVisible(true);
					label2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("2");
							transferMoneyBean.setZhangWay("普通到账");
							nextstep();
						}
					});
					//次日到账
					label3.setIcon(new ImageIcon("pic/nextDayAccount.png"));
					label3.setVisible(true);
					label3.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("1");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("次日到账");
							nextstep();
						}
					});
				}
			//工作日最后一天
			}else if("1".equals(transferMoneyBean.getIsWorkDay())){
				logger.info("工作日最后一天");
				//金额>5万
				if(Float.parseFloat(transferMoneyBean.getRemitAmt())>50000){
					logger.info("金额>5万");
					//显示实时到账
					label4.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label4.setVisible(true);
					label4.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}				
					});
					
					//显示次日到账 
					label5.setIcon(new ImageIcon("pic/nextDayAccount.png"));
					label5.setVisible(true);
					label5.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("1");
							transferMoneyBean.setTransMarkNo("2");
							transferMoneyBean.setZhangWay("次日到账");
							nextstep();
						}				
					});
				//金额<=5万
				}else{
					logger.info("金额<=5万");
					//实时到账 
					label1.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label1.setVisible(true);
					label1.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}						
					});
					//普通到账
					label2.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
					label2.setVisible(true);
					label2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("2");
							transferMoneyBean.setZhangWay("普通到账");
							nextstep();
						}
					});
					//次日到账
					label3.setIcon(new ImageIcon("pic/nextDayAccount.png"));
					label3.setVisible(true);
					label3.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("1");
							transferMoneyBean.setTransMarkNo("2");
							transferMoneyBean.setZhangWay("次日到账");
							nextstep();
						}
					});
				}
			//正常节假日	
			}else if("3".equals(transferMoneyBean.getIsWorkDay())){
				logger.info("正常节假日");
				//实时到账
				label1.setIcon(new ImageIcon("pic/real_timeAccount.png"));
				label1.setVisible(true);
				label1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("1");
						transferMoneyBean.setZhangWay("实时到账");
						nextstep();
					}
				});
				//普通到账
				label2.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
				label2.setVisible(true);
				label2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("2");
						transferMoneyBean.setZhangWay("普通到账");
						nextstep();
					}
				});
				//次日到账
				label3.setIcon(new ImageIcon("pic/nextDayAccount.png"));
				label3.setVisible(true);
				label3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("1");
						transferMoneyBean.setTransMarkNo("2");
						transferMoneyBean.setZhangWay("次日到账");
						nextstep();
					}
				});
			//节假日最后一天
			}else if("2".equals(transferMoneyBean.getIsWorkDay())){
				logger.info("节假日最后一天");
				//实时到账
				label1.setIcon(new ImageIcon("pic/real_timeAccount.png"));
				label1.setVisible(true);
				label1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("1");
						transferMoneyBean.setZhangWay("实时到账");
						nextstep();
					}
				});
				//普通到账
				label2.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
				label2.setVisible(true);
				label2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("2");
						transferMoneyBean.setZhangWay("普通到账");
						nextstep();
					}
				});
				//次日到账
				label3.setIcon(new ImageIcon("pic/nextDayAccount.png"));
				label3.setVisible(true);
				label3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("1");
						transferMoneyBean.setTransMarkNo("1");
						transferMoneyBean.setZhangWay("次日到账");
						nextstep();
					}
				});
			}
			
		/**
		 * 单位卡行内转账	
		 */
		}else if("1".equals(transferMoneyBean.getIsCardBin()) && "0".equals(transferMoneyBean.getIsCardBank())){
			logger.info("单位卡行内转账");
			//行内实时转账
			label2.setIcon(new ImageIcon("pic/real_timeAccount.png"));
			label2.setVisible(true);
			label2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(!on_off){
						logger.info("开关当前状态"+on_off);
						return;
					}
					logger.info("开关当前状态"+on_off);
					on_off=false;
					transferMoneyBean.setNextDaySendFlag("0");//次日到账添加为实时到账
					transferMoneyBean.setTransMarkNo("0");
					transferMoneyBean.setZhangWay("实时到账");
					nextstep();
				}
			});
		/**
		 * 单位卡跨行转账	
		 */
		}else if("1".equals(transferMoneyBean.getIsCardBin()) && "1".equals(transferMoneyBean.getIsCardBank())){
			logger.info("单位卡跨行转账");
			//工作日
			if("0".equals(transferMoneyBean.getIsWorkDay()) || "1".equals(transferMoneyBean.getIsWorkDay()) ){
				logger.info("判断金额大小");
				//金额>5万
				if(Float.parseFloat(transferMoneyBean.getRemitAmt())>50000){
					logger.info("金额>5万");
					//实时到账
					label2.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label2.setVisible(true);
					label2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}
					});
					return;
				}
				//金额<=5万
				if(Float.parseFloat(transferMoneyBean.getRemitAmt())<=50000 && 0<Float.parseFloat(transferMoneyBean.getRemitAmt())){
					logger.info("金额<=5");
					//实时到账
					label4.setIcon(new ImageIcon("pic/real_timeAccount.png"));
					label4.setVisible(true);
					label4.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("1");
							transferMoneyBean.setZhangWay("实时到账");
							nextstep();
						}
					});
					//普通到账
					label5.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
					label5.setVisible(true);
					label5.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							if(!on_off){
								logger.info("开关当前状态"+on_off);
								return;
							}
							logger.info("开关当前状态"+on_off);
							on_off=false;
							transferMoneyBean.setNextDaySendFlag("0");
							transferMoneyBean.setTransMarkNo("2");
							transferMoneyBean.setZhangWay("普通到账");
							nextstep();
						}
					});
					return;
				}
			//节假日	
			}else if("2".equals(transferMoneyBean.getIsWorkDay()) || "3".equals(transferMoneyBean.getIsWorkDay())){
				logger.info("节假日");
				//实时到账
				label4.setIcon(new ImageIcon("pic/real_timeAccount.png"));
				label4.setVisible(true);
				label4.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("1");
						transferMoneyBean.setZhangWay("实时到账");
						nextstep();
					}
				});
				//普通到账
				label5.setIcon(new ImageIcon("pic/ordinaryAccount.png"));
				label5.setVisible(true);
				label5.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(!on_off){
							logger.info("开关当前状态"+on_off);
							return;
						}
						logger.info("开关当前状态"+on_off);
						on_off=false;
						transferMoneyBean.setNextDaySendFlag("0");
						transferMoneyBean.setTransMarkNo("2");
						transferMoneyBean.setZhangWay("普通到账");
						nextstep();
					}
				});
			}
			
			
		}
		
	}
	
	/**
	 * 查询查询汇款累计金额是否大于等于50万
	 */
	public boolean check(){
		try {
			transferBean.getReqMCM001().setReqBefor("07494");
			Map inter07494 = InterfaceSendMsg.inter07494(transferMoneyBean); 
			transferBean.getReqMCM001().setReqAfter((String)inter07494.get("resCode"), (String)inter07494.get("errMsg"));
			if(!"000000".equals(inter07494.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info(inter07494.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop("单日累计转账金额查询失败，请联系大堂经理", (String)inter07494.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("查询累计金额失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用07494接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("查询累计金额失败，请联系大堂经理","", "调用07494接口异常");
			return false;
		}
		
		logger.info("单日转账累计金额查询成功");
		return true;
	}
	
	
	/**
	 * 查询大额系统是否关闭
	 */
	public boolean checkSystem(){
		logger.info("开始查询大额系统是否关闭");
		try {
			transferBean.getReqMCM001().setReqBefor("CM021");
			Map interCM021 = InterfaceSendMsg.interCM021(transferMoneyBean); 
			transferBean.getReqMCM001().setReqAfter((String)interCM021.get("resCode"), (String)interCM021.get("errMsg"));
			if(!"000000".equals(interCM021.get("resCode"))){
				
				closeDialog(prossDialog);
				logger.info(interCM021.get("errMsg"));
				MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
				serverStop("大额系统查询失败，请联系大堂经理", (String)interCM021.get("errMsg"),"");
				return false;
			}
		} catch (Exception e) {
			closeDialog(prossDialog);
			logger.error("大额系统查询失败"+e);
			transferBean.getReqMCM001().setIntereturnmsg("调用CM021接口异常");
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("大额系统查询失败，请联系大堂经理", "","调用CM021接口异常");
			return false;
		}
		
		logger.info("大额系统查询成功");
		return true;
	}
	
	//大额关闭，选择是否继续交易
	public boolean chooseTrans(){
		if(!"10".equals(transferMoneyBean.getBigCode())){//大额系统已关闭
			closeDialog(prossDialog);
			logger.info("大额系统已关闭，不能选择实时到账");
			final CheckConfirmDialog confirmDialog=new CheckConfirmDialog(MainFrame.mainFrame, true,"");
			confirmDialog.showDialog("非人行支付系统运行时间，是否继续？是：立即受理，待下一工作日自动处理;否：请选择其他到账方式;");
			confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择‘是’");
					transferMoneyBean.setNextDaySendFlag("3");
					transferMoneyBean.setZhangWay("预约实时到账");
					closeDialog(confirmDialog);
					if("00".equals(transferMoneyBean.getBigCode())){
						transferMoneyBean.setParCode("CUR_SYS_DATE");
					}else{
						transferMoneyBean.setParCode("NEXT_SYS_DATE");
					}
					if(!checkSystem()){
						//清空倒计时
						clearTimeText();
						closeDialog(prossDialog);
						return;
					}
					transContiune();
				}
			});
			confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("选择‘否’");
					transferMoneyBean.setTransMarkNo("");
					transferMoneyBean.setNextDaySendFlag("");
					on_off=true;
					closeDialog(confirmDialog);
				}
			});
			return false;
		}
		return true;
	}
	
	//继续交易
	public void transContiune(){
		logger.info("进入累计转账金额查询");
		if(check()==false){//查询累计转账金额
			//清空倒计时
			clearTimeText();
			on_off=true;
			closeDialog(prossDialog);
			return;
		}
		try {
			if(!isOutLimit()){
				on_off=true;
				return;
			}
		} catch (Exception e) {
			logger.info("判断汇划交易金额是否超出限制异常："+e);
			closeDialog(prossDialog);
			//清空倒计时
			clearTimeText();
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("抱歉，校验交易金额时异常，请稍后再操作", "校验交易金额是否超限是异常","");
			return;
		}
		
		closeDialog(prossDialog);
		//清空倒计时
		clearTimeText();
		if(transferMoneyBean.getTransNo()==null || "".equals(transferMoneyBean.getTransNo()) || "1".equals(transferMoneyBean.getTransNo())){
			//返回时跳转页面
			openPanel(new TransferAccInputIdCardPanel(transferMoneyBean));
		}else if(Integer.parseInt(transferMoneyBean.getTransNo())>1){
			//第二笔开始，直接跳转到签字页面
			openPanel(new TransferSignConfirmPanel(transferMoneyBean));
		}else{
			MakeMonitorInfo.makeInfos(transferBean.getReqMCM001());
			serverStop("出错了，请重新操作", "","交易笔数异常");
		}
	}
	
	//判断交易金额是否超出限制金额
	public boolean isOutLimit() throws Exception{
		java.text.DecimalFormat  df= new java.text.DecimalFormat("0.00"); 
		String money = transferMoneyBean.getRemitAmt();
		String sumAmt = transferMoneyBean.getSumAmt();
		if("".equals(sumAmt) || null ==sumAmt){
			sumAmt="0.00";
		}
		logger.info("单日累计金额："+sumAmt);
		logger.info("汇款金额："+money);
		Double remitAmt = Double.valueOf(money);//汇款金额
		Double sumAmtFloat = Double.valueOf(sumAmt);//累计金额
		
		Double allDayAmt = new Double(Property.ALL_DAY_AMT);//单日限制最大累计金额
		Double workDayOnceAmt = new Double(Property.WORK_DAY_ONCE_AMT);//工作日单笔最大交易金额
		Double restDayOnceAmt = new Double(Property.REST_DAY_ONCE_AMT);//节假日单笔最大交易金额
		Double onceAmt=null;//单笔最大限制金额
		Double nextDayOnceAmt=null;//次日单笔交易金额限制
		if("0".equals(transferMoneyBean.getIsWorkDay())){//正常工作日
			onceAmt = workDayOnceAmt;
			nextDayOnceAmt = workDayOnceAmt;
		}else if("1".equals(transferMoneyBean.getIsWorkDay())){//工作日最后一天
			onceAmt = workDayOnceAmt;
			nextDayOnceAmt = restDayOnceAmt;
		}else if("2".equals(transferMoneyBean.getIsWorkDay())){//节假日最后一天
			onceAmt = restDayOnceAmt;
			nextDayOnceAmt = workDayOnceAmt;
		}else if("3".equals(transferMoneyBean.getIsWorkDay())){//节假日
			onceAmt = restDayOnceAmt;
			nextDayOnceAmt = restDayOnceAmt;
		}
		if("1".equals(transferMoneyBean.getNextDaySendFlag())){
			if("2".equals(transferMoneyBean.getIsWorkDay()) ||"0".equals(transferMoneyBean.getIsWorkDay())){//节假日最后一天和正常工作日
				if(remitAmt>=nextDayOnceAmt){//500
					closeDialog(prossDialog);
					logger.error("次日到账转账金额已超过单笔交易金额限制"+nextDayOnceAmt);
					openMistakeDialog("转账金额超出次日单笔限额，单笔交易金额必须低于"+df.format(nextDayOnceAmt)+"元");
					return false;
				}
			}else{//正常节假日和工作日最后一天
				if(remitAmt>nextDayOnceAmt){//50
					closeDialog(prossDialog);
					logger.error("次日到账转账金额已超过单笔交易金额限制"+nextDayOnceAmt);
					openMistakeDialog("转账金额超出次日单笔限额，单笔交易金额必须低于或者等于"+df.format(nextDayOnceAmt)+"元");
					return false;
				}
			}
			String nextDate = DateTool.nDaysAfterOneDateString(transferMoneyBean.getSvrTranDate(),1);
			if(!checkNextAmt(allDayAmt,remitAmt,nextDate)){
				return false;
			}
		}else if("3".equals(transferMoneyBean.getNextDaySendFlag())){//实时预约转账交易金额校验不得大于50万
			Double max_amt = new Double(Property.WORK_DAY_ONCE_AMT);
			if(remitAmt>=max_amt){//超过单笔交易金额限制
				closeDialog(prossDialog);
				logger.error("单笔转账金额已超过"+max_amt);
				openMistakeDialog("转账金额超出单笔限额，单笔交易金额必须低于"+df.format(max_amt)+"元");
				return false;
			}
			String nextDate = transferMoneyBean.getBigCode();
			if(!checkNextAmt(allDayAmt,remitAmt,nextDate)){
				return false;
			}
		}else{
			if("2".equals(transferMoneyBean.getIsWorkDay()) || "3".equals(transferMoneyBean.getIsWorkDay())){//节假日
				//节假日单笔超过五十万
				if(remitAmt>onceAmt){//超过单笔交易金额限制
					closeDialog(prossDialog);
					logger.error("单笔转账金额已超过"+onceAmt);
					openMistakeDialog("转账金额超出单笔限额，单笔交易金额必须低于或等于"+df.format(onceAmt)+"元");
					return false;
				}
			}else{
				//工作日单笔不超过500万
				if(remitAmt>=onceAmt){//超过单笔交易金额限制
					closeDialog(prossDialog);
					logger.error("单笔转账金额已超过"+onceAmt);
					openMistakeDialog("转账金额超出单笔限额，单笔交易金额必须低于"+df.format(onceAmt)+"元");
					return false;
				}
			}
			if((remitAmt+sumAmtFloat)>=allDayAmt){
				closeDialog(prossDialog);
				logger.error("次日转账累计金额已超过"+allDayAmt);
				openMistakeDialog("转账金额超出限额，请选择其他渠道办理");
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkNextAmt(Double allDayAmt,Double remitAmt,String nextDate) throws Exception{
		transferBean.getReqMCM001().setReqBefor("CM022");
		Map cm022 = InterfaceSendMsg.interCM022(transferMoneyBean);
		transferBean.getReqMCM001().setReqAfter((String)cm022.get("resCode"), (String)cm022.get("errMsg"));
		List<TransferSelectBean> list = (List) cm022.get(InterfaceSendMsg.TRANSFER_CANCEL_MSG);
		BigDecimal nextSumAmt = new BigDecimal(0);
		for(int i=0;i<list.size();i++){
			TransferSelectBean tsb = list.get(i);
			if(nextDate.equals(tsb.getDsSendDate())){
				nextSumAmt = nextSumAmt.add(new BigDecimal(tsb.getTransMoney()));
			}
		}
		if(new BigDecimal(allDayAmt).compareTo(nextSumAmt)>0 
				&& new BigDecimal(allDayAmt).compareTo(nextSumAmt.add(new BigDecimal(remitAmt)))>0){
				
		}else{
			closeDialog(prossDialog);
			logger.error("单日转账累计金额已超过"+allDayAmt);
			openMistakeDialog("转账金额超出限额，请选择其他渠道办理");
			return false;
		}
		return true;
	}
}
