package com.boomhope.Bill.Framework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Driver.NewKeypadDriver;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.PublicControl.CheckConfirmDialog;
import com.boomhope.Bill.PublicControl.ConfirmRePwdDialog;
import com.boomhope.Bill.PublicControl.MistakeDialog;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.PublicControl.ProssDialog;
import com.boomhope.Bill.PublicControl.PublicServerStop;
import com.boomhope.Bill.TransService.AccCancel.AccCacncelAction.NoAccAction;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelDempMethodPanel;
import com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse.AccCancelOutputDepositPanel;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.Interface.IntefaceSendMsg;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.PublicProPanel.AccInputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccTransfer.Action.TransferAction;
import com.boomhope.Bill.TransService.AccTransfer.Bean.PublicAccTransferBean;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferChooseBusiness;
import com.boomhope.Bill.TransService.AccTransfer.View.ServicePage.TransferCancel.TransferCancelInputBankCardPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.TransFlow.AllPublicTransFlow;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;
import com.boomhope.Bill.TransService.BillAddPwd.BillAddPwdAction.AddPwdAction;
import com.boomhope.Bill.TransService.BillAddPwd.ServicePanel.SetPwdInputCDPanel;
import com.boomhope.Bill.TransService.BillChOpen.Action.BillChOpenAction;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenInputDepositPanel;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.PublicUse.BillChangeOpenOutputDepositPanel;
import com.boomhope.Bill.TransService.BillPrint.TransPrintAccOrAgrPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BillPrint.Interface.ICBankBean;
import com.boomhope.Bill.TransService.BudingJrnlNo.TJServicePanel;
import com.boomhope.Bill.TransService.BudingJrnlNo.TransBuldingJrnlNo;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.moneybox.MoneyBoxEntryModePanel;
import com.boomhope.Bill.TransService.LostReport.Action.LostAction;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Page.SelectLostOrSolveLostPanel;
import com.boomhope.Bill.Util.BeansUtils;
import com.boomhope.Bill.Util.UtilImageDialog;
import com.boomhope.Bill.peripheral.action.Dimension;
import com.boomhope.Bill.peripheral.action.ICBank;
import com.boomhope.Bill.peripheral.action.IdCard;
import com.boomhope.Bill.peripheral.action.MachineLed;
import com.boomhope.Bill.peripheral.bean.ICBankCheckStatusBean;
import com.boomhope.Bill.peripheral.bean.LedStateBean;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class BaseTransPanelNew extends JLayeredPane{
	
	Logger logger = Logger.getLogger(BaseTransPanelNew.class);

	private static final long serialVersionUID = 1L;

	//最短时间
	public static int delaySecondMinTime = 15;
	//裁纸器打开时间
	public static int delaySecondthirtyTime = 30;
	//120秒时长
	public static int delaySecondLongTime = 120;
	//300秒时长
	public static int delaythreeLongTime = 300;
	//60秒时长 
	public static int delaySecondShortTime = 60;
	//600秒时长
	public static int delaySecondLongestTime = 600;
	//180秒时长
	public static int delaySecondMaxTime = 180;
	//语音倒计时
	public static int voiceSecond = 500;
	//显示倒计时标签
	protected static JLabel timeLabel;
	// 界面显示倒计时定时器
	protected static Timer timeAction;
	// 子界面倒计时定时器
	protected static Timer delayTimer;
	// 倒计时语音
	protected static Timer voiceTimer;
	//显示倒计时秒数
	private static JLabel timeSecond;
	//显示倒计时秒
	private static JLabel miaoLabel;
	//秒数
	protected int second;
	//只执行一次打开回单模块标识
	protected int index=1;
	
	//加载子页面的面板
	protected JPanel showJpanel;
	/**
	 * 公共路由
	 */
	protected static AllPublicTransFlow allPubTransFlow = new AllPublicTransFlow();
	
	//功能按钮
	JLabel printBt;//
	JLabel accountButton;//存单开户
	JLabel destoryButton;//存单销户
	JLabel printButton;//存单打印
	JLabel huihuaButton;//汇划转账
	JLabel huihuaCancelButton;//汇划撤销
	JLabel openPonsitButton;//存单换开
	JLabel changeButton;//存单换存单
	JLabel conversionButton;//现金开户
	JLabel upButton;//向上按钮
	JLabel downButton;//向下按钮
	JLabel getCashButton;//存单部提
	JLabel passWordButton;//密码增设
	JLabel svrBdButton;//流水号绑定
	JLabel awardButton;//推荐有礼
	JLabel lostButton;//挂失解挂
	/**
	 * 下方按钮
	 */
	public static JLabel exitBt;// 退出按钮
	public JLabel firstPage;//返回首页按钮
	boolean isCert = false;// 标记是否有凭证号
	private int picPage;//显示业务图标的页数
	private int maxPicPage;//最大图标页数
	
	/*----------公共组件变量--------*/
	//等待提示框
	public static ProssDialog prossDialog=new ProssDialog(null, true);
	//错误提示框
	public static MistakeDialog mistakeDialog;
	//信息确认框
	public static CheckConfirmDialog confirmDialog;
	//确认退出框
	public static ConfirmRePwdDialog confirmRePwdDialog;
	//图片显示框
	public static UtilImageDialog imageDialog;
	
	//所有项目共有bean
	public BaseTransBean baseTransBean;
	/*----------子项目bean--------*/
	//现金开户bean
	public static PublicCashOpenBean cashBean;
	//汇划Action
	public static TransferAction transferAction;
	//汇划bean
	public static PublicAccTransferBean transferBean;
	/**存单销户**/
	//存单销户bean
	public static PublicAccCancelBean accCancelBean;
	//销户无存单流程对象
	public static NoAccAction noAccAction;
	//子账户打印
	public static BillPrintBean billPrintBean;
	//增设密码Bean
	public static AddPasswordBean addPwdBean;
	//增设密码Action
	public static AddPwdAction addPwdAction;
	//存单换开
	public static PublicBillChangeOpenBean bcOpenBean;
	//存单换开Action
	public static BillChOpenAction bcOpenAction;
	//挂失解挂Bean
	public static LostPubBean lostPubBean;
	//挂失解挂Action
	public static LostAction lostAction;
	/** 
	 * @author Mr.hao
	 * 修改时间:2017/08/28 将生产代码移到新UI中（新增）
	 */
	//推荐有礼
	public static BulidingJrnNoBean jrnNoBean;
	//子账户待打印存单Bean
	public List<ICBankBean> bankBeans = new ArrayList<ICBankBean>();
	/*----------外设变量-----------*/
	//语音流
	protected static AudioStream as;
	
	public static BaseContentPanel contentPanel;
	public static CheckConfirmDialog getConfirmDialog() {
		return confirmDialog;
	}

	public static void setConfirmDialog(CheckConfirmDialog confirmDialog) {
		BaseTransPanelNew.confirmDialog = confirmDialog;
	}

	/***
	 * 更改背景
	 */
	public void paintComponent(Graphics g){
		g.drawImage(new ImageIcon("pic/newPic/ground.png").getImage(), 400, 150, 
				GlobalParameter.TRANS_WIDTH-50, GlobalParameter.TRANS_HEIGHT-140, this);
	}
	
	/***
	 * 界面初始化
	 */
	public BaseTransPanelNew(){
		//界面初始参数配置
		this.setSize(GlobalParameter.WINDOW_WIDTH, 830);
		this.setLocation(0,0);
		this.setOpaque(false);
		this.setLayout(null);
		
		showJpanel = new JPanel();
		showJpanel.setBounds(400, 150, GlobalParameter.TRANS_WIDTH-50, GlobalParameter.TRANS_HEIGHT-140);
		showJpanel.setLayout(null);
		showJpanel.setOpaque(false);
		this.add(showJpanel,JLayeredPane.MODAL_LAYER);
		
		if("0".equals(GlobalParameter.ACC_NO)){
			addAdminComponents();
		}else{
			addComponents();
			initPanel();
		}
		//每新建一次子界面一些参数回归
		closeAllControl();
	}
	
	public void initPanel(){
		logger.info("初始化图标状态");
		//根据打印机状态和选择的业务显示图标
		buttonShow();
		
		//显示上下翻页图标
		if(maxPicPage==1){
			upButton.setIcon(new ImageIcon("pic/newPic/up1.png"));
			downButton.setIcon(new ImageIcon("pic/newPic/down1.png"));
		}else if(picPage==1 && maxPicPage>1){
			upButton.setIcon(new ImageIcon("pic/newPic/up1.png"));
			downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
		}else if(picPage>1 && picPage<maxPicPage){
			upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
			downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
		}else if(picPage==maxPicPage && maxPicPage>1){
			upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
			downButton.setIcon(new ImageIcon("pic/newPic/down1.png"));
		}
		
		//根据选择的业务显示特定的图片
		switch (GlobalParameter.ACC_NO) {
		case "1":
			destoryButton.setIcon(new ImageIcon("pic/newPic/destroy1.png"));
			break;
		case "2":
			conversionButton.setIcon(new ImageIcon("pic/newPic/cash1.png"));
			break;
		case "3":
			huihuaButton.setIcon(new ImageIcon("pic/newPic/huiBt1.png"));
			break;
		case "4":
			huihuaCancelButton.setIcon(new ImageIcon("pic/newPic/huiCancelBt1.png"));
			break;
		case "5":
			accountButton.setIcon(new ImageIcon("pic/newPic/account1.png"));
			break;
		case "6":
			printButton.setIcon(new ImageIcon("pic/newPic/print1.png"));
			break;
		case "7":
			openPonsitButton.setIcon(new ImageIcon("pic/newPic/BillDepositShip1.png"));
			break;
		case "8":
			lostButton.setIcon(new ImageIcon("pic/newPic/selectLostOrSolve.png"));
			break;
		case "9":
			passWordButton.setIcon(new ImageIcon("pic/newPic/passWord1.png"));
			break;
		case "20":
			svrBdButton.setIcon(new ImageIcon("pic/newPic/SvrBing1.png"));
			break;
		case "21":
			awardButton.setIcon(new ImageIcon("pic/newPic/award1.png"));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 添加管理员控件
	 */
	public void addAdminComponents(){
		logger.info("添加管理员的控件图标");
		// 向上按钮
		upButton = new JLabel(new ImageIcon("pic/newPic/up1.png"));
		upButton.setBounds(159, 150, 40, 24);
		this.add(upButton);
		upButton.setVisible(true);
		// 向下按钮
		downButton = new JLabel(new ImageIcon("pic/newPic/down1.png"));
		downButton.setBounds(159, 720, 40, 24);
		this.add(downButton);
		downButton.setVisible(true);
		
		//管理员按钮
		JLabel adminLabel = new JLabel(new ImageIcon("pic/manager_1.png"));
		adminLabel.setBounds(50, 216, 258, 78);
		this.add(adminLabel);
		
	}
	
	
	/**
	 * 添加控件
	 */
	public void addComponents() {
		logger.info("加载子业务选择图标");
		// 向上按钮
		upButton = new JLabel(new ImageIcon("pic/newPic/up.png"));
		upButton.setBounds(159, 150, 40, 24);
		this.add(upButton);
		upButton.setVisible(true);
		// 向下按钮
		downButton = new JLabel(new ImageIcon("pic/newPic/down.png"));
		downButton.setBounds(159, 720, 40, 24);
		this.add(downButton);
		downButton.setVisible(true);
		
		//设置图标初始页为1和能显示的最大页数 
		if("0".equals(GlobalParameter.printStatus)){
			if("1".equals(GlobalParameter.ACC_NO)||"2".equals(GlobalParameter.ACC_NO)||"3".equals(GlobalParameter.ACC_NO)||"5".equals(GlobalParameter.ACC_NO)){
				picPage=1;
			}else if("4".equals(GlobalParameter.ACC_NO) || "6".equals(GlobalParameter.ACC_NO) || "20".equals(GlobalParameter.ACC_NO) || "21".equals(GlobalParameter.ACC_NO)){
				picPage=2;
			}else if("9".equals(GlobalParameter.ACC_NO) || "7".equals(GlobalParameter.ACC_NO) || "8".equals(GlobalParameter.ACC_NO)){
				picPage=3;
			}
		}else{
			if("1".equals(GlobalParameter.ACC_NO)||"3".equals(GlobalParameter.ACC_NO)||"4".equals(GlobalParameter.ACC_NO) || "21".equals(GlobalParameter.ACC_NO)){
				picPage=1;
			}else if("20".equals(GlobalParameter.ACC_NO) || "6".equals(GlobalParameter.ACC_NO)||"9".equals(GlobalParameter.ACC_NO)||"8".equals(GlobalParameter.ACC_NO)){
				picPage=2;
			}
		}
		if(GlobalParameter.TRANS_NOS%4==0){
			maxPicPage=GlobalParameter.TRANS_NOS/4;
		}else{
			maxPicPage=GlobalParameter.TRANS_NOS/4+1;
		}

		int buttheight = 216;
		int hSize = 128;
		
		if("0".equals(GlobalParameter.printStatus)){
			// 存单开户
			accountButton = new JLabel(new ImageIcon("pic/newPic/account.png"));
			accountButton.setBounds(50, buttheight, 258, 78);
			this.add(accountButton);
			accountButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单开户按钮");
					if("5".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							accountClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			// 存单销户
			destoryButton = new JLabel(new ImageIcon("pic/newPic/destroy.png"));
			destoryButton.setBounds(50, buttheight+hSize, 258, 78);
			this.add(destoryButton);
			destoryButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单销户按钮");
					if("1".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							destroyClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
					
				}
			});
			// 现金开户
			conversionButton = new JLabel(new ImageIcon("pic/newPic/cash.png"));
			conversionButton.setBounds(50, buttheight + hSize*2, 258, 78);
			this.add(conversionButton);
			conversionButton.setVisible(true);
			conversionButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击现金开户按钮");
					if("2".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							cashClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//汇划转账
			huihuaButton = new JLabel(new ImageIcon("pic/newPic/huiBt.png"));
			huihuaButton.setBounds(50, buttheight+hSize*3, 258, 78);
			this.add(huihuaButton);
			huihuaButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击汇划转账按钮");
					if("3".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							huiClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//汇划撤销
			huihuaCancelButton = new JLabel(new ImageIcon("pic/newPic/huiCancelBt.png"));
			huihuaCancelButton.setBounds(50, buttheight, 258, 78);
			this.add(huihuaCancelButton);
			huihuaCancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击汇划撤销按钮");
					if("4".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							huiCancelClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			// 存单打印
			printButton = new JLabel(new ImageIcon("pic/newPic/print.png"));
			printButton.setBounds(50, buttheight + hSize, 258, 78);
			this.add(printButton);
			printButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单打印按钮");
					if("6".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							printClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//推荐有礼
			awardButton = new JLabel(new ImageIcon("pic/newPic/award.png"));
			awardButton.setBounds(50, buttheight + hSize*2, 258, 78);
			this.add(awardButton);
			awardButton.setVisible(true);
			awardButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击推荐有礼按钮");
					if("21".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							awardGetClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//流水号绑定
			svrBdButton = new JLabel(new ImageIcon("pic/newPic/SvrBing.png"));
			svrBdButton.setBounds(50, buttheight+hSize*3, 258, 78);
			this.add(svrBdButton);
			svrBdButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击流水号绑定按钮");
					if("20".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							svrBdClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			// 密码增设
			passWordButton = new JLabel(new ImageIcon("pic/newPic/passWord.png"));
			passWordButton.setBounds(50, buttheight, 258, 78);//+ hSize * 3
			this.add(passWordButton);

			passWordButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击密码增设按钮");
					if("9".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							addPwd();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});

			//存单换开
			openPonsitButton = new JLabel(new ImageIcon("pic/newPic/BiillDepositShip.png"));
			openPonsitButton.setBounds(50, buttheight+hSize, 258, 78);
			this.add(openPonsitButton);
			openPonsitButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单换开按钮");
					if("7".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							billChangeOpenClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			
			//挂失解挂
			lostButton = new JLabel(new ImageIcon("pic/newPic/lostOrSolve.png"));
			lostButton.setBounds(50, buttheight+hSize*2, 258, 78);
			this.add(lostButton);
			lostButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击挂失解挂按钮");
					if("8".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							lostGetClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
		}else{
			//汇划转账
			huihuaButton = new JLabel(new ImageIcon("pic/newPic/huiBt.png"));
			huihuaButton.setBounds(50, buttheight, 258, 78);
			huihuaButton.setVisible(false);
			this.add(huihuaButton);
			huihuaButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击汇划转账按钮");
					if("3".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							huiClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//汇划撤销
			huihuaCancelButton = new JLabel(new ImageIcon("pic/newPic/huiCancelBt.png"));
			huihuaCancelButton.setBounds(50, buttheight+hSize*1, 258, 78);
			huihuaCancelButton.setVisible(false);
			this.add(huihuaCancelButton);
			huihuaCancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击汇划撤销按钮");
					if("4".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							huiCancelClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			// 存单销户
			destoryButton = new JLabel(new ImageIcon("pic/newPic/destroy.png"));
			destoryButton.setBounds(50, buttheight + hSize*2, 258, 78);
			this.add(destoryButton);
			destoryButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单销户按钮");
					if("1".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							destroyClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//推荐有礼
			awardButton = new JLabel(new ImageIcon("pic/newPic/award.png"));
			awardButton.setBounds(50, buttheight + hSize*3, 258, 78);
			this.add(awardButton);
			awardButton.setVisible(true);
			awardButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击推荐有礼按钮");
					if("21".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							awardGetClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			
			//流水号绑定
			svrBdButton = new JLabel(new ImageIcon("pic/newPic/SvrBing.png"));
			svrBdButton.setBounds(50, buttheight, 258, 78);
			this.add(svrBdButton);
			svrBdButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击流水号绑定按钮");
					if("20".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							svrBdClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			// 存单打印
			printButton = new JLabel(new ImageIcon("pic/newPic/print.png"));
			printButton.setBounds(50, buttheight + hSize, 258, 78);
			this.add(printButton);
			printButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击存单打印按钮");
					if("6".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							printClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			// 密码增设
			passWordButton = new JLabel(new ImageIcon("pic/newPic/passWord.png"));
			passWordButton.setBounds(50, buttheight+ hSize * 2, 258, 78);
			this.add(passWordButton);
			
			passWordButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					logger.info("点击密码增设按钮");
					if("9".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							addPwd();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
			//挂失解挂
			lostButton = new JLabel(new ImageIcon("pic/newPic/lostOrSolve.png"));
			lostButton.setBounds(50, buttheight+hSize*3, 258, 78);
			this.add(lostButton);
			lostButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击挂失解挂按钮");
					if("8".equals(GlobalParameter.ACC_NO)){
						return;
					}
					if(!GlobalParameter.OFF_ON){
						logger.info("开关为关闭状态，不能进行页面跳转");
						openMistakeDialog("抱歉，当前业务未完成，不能进行其他操作。");
						return;
					}
					openConfirmDialog("是否确认退出当前业务。是：退出当前业务，否：继续当前操作。");
					confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							lostGetClick();
						}
					});
					confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							confirmDialog.disposeDialog();
							return;
						}
					});
				}
			});
		}
		
		

		upButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击向上按钮");
				if(picPage>1){
					picPage-=1;
				}else{
					return;
				}
				//根据上下按钮显示图标
				buttonShow();
				
				//点击变换上下按钮图标
				if(picPage==1 && maxPicPage>1){
					upButton.setIcon(new ImageIcon("pic/newPic/up1.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
				}else if(picPage>1 && picPage<maxPicPage){
					upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
				}else if(picPage==maxPicPage && maxPicPage>1){
					upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down1.png"));
				}
			}
		});

		downButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				logger.info("点击向下按钮");
				if(picPage<maxPicPage){
					picPage+=1;
				}else{
					return;
				}
				//根据上下按钮显示图标
				buttonShow();
				
				//点击变换上下按钮图标
				if(picPage==1 && maxPicPage>1){
					upButton.setIcon(new ImageIcon("pic/newPic/up1.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
				}else if(picPage>1 && picPage<maxPicPage){
					upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down.png"));
				}else if(picPage==maxPicPage && maxPicPage>1){
					upButton.setIcon(new ImageIcon("pic/newPic/up.png"));
					downButton.setIcon(new ImageIcon("pic/newPic/down1.png"));
				}
			}
		});
	}
	
	/**
	 * 显示左侧各功能按钮 
	 */
	public void buttonShow(){
		if("0".equals(GlobalParameter.printStatus)){
			logger.info("打印机状态正常");
			if(picPage==1){
				accountButton.setVisible(true);
				destoryButton.setVisible(true);
				conversionButton.setVisible(true);
				huihuaButton.setVisible(true);
				huihuaCancelButton.setVisible(false);
				printButton.setVisible(false);
				awardButton.setVisible(false);
				svrBdButton.setVisible(false);
				passWordButton.setVisible(false);
				openPonsitButton.setVisible(false);
				lostButton.setVisible(false);
			}else if(picPage==2){
				accountButton.setVisible(false);
				destoryButton.setVisible(false);
				conversionButton.setVisible(false);
				huihuaButton.setVisible(false);
				huihuaCancelButton.setVisible(true);
				printButton.setVisible(true);
				awardButton.setVisible(true);
				svrBdButton.setVisible(true);
				passWordButton.setVisible(false);
				openPonsitButton.setVisible(false);
				lostButton.setVisible(false);
			}else if(picPage==3){
				accountButton.setVisible(false);
				destoryButton.setVisible(false);
				conversionButton.setVisible(false);
				huihuaButton.setVisible(false);
				huihuaCancelButton.setVisible(false);
				printButton.setVisible(false);
				awardButton.setVisible(false);
				svrBdButton.setVisible(false);
				passWordButton.setVisible(true);
				openPonsitButton.setVisible(true);
				lostButton.setVisible(true);
			}
		}else{
			logger.info("无打印机");
			if(picPage==1){
				huihuaButton.setVisible(true);
				huihuaCancelButton.setVisible(true);
				destoryButton.setVisible(true);
				awardButton.setVisible(true);
				svrBdButton.setVisible(false);
				printButton.setVisible(false);
				passWordButton.setVisible(false);
				lostButton.setVisible(false);
			}else if(picPage==2){
				huihuaButton.setVisible(false);
				huihuaCancelButton.setVisible(false);
				destoryButton.setVisible(false);
				awardButton.setVisible(false);
				svrBdButton.setVisible(true);
				printButton.setVisible(true);
				passWordButton.setVisible(true);
				lostButton.setVisible(true);
			}
		}
	}
	
	/*-------按钮被点击--------*/
	// 存单开户
	private void accountClick() {
		logger.info("调用跳转至存单开户的方法");
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				GlobalParameter.ACC_NO="5";
				@SuppressWarnings("rawtypes")
				Map map = new HashMap();
				map.put("transCode", "0001");
				AccountTradeCodeAction.transBean=new AccPublicBean();
				openPanel(new AccInputBankCardPanel(map));
			}
		});
		
	}

	// 存单销户
	private void destroyClick() {
		logger.info("调用跳转至存单销户的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="1";
				accCancelBean=new PublicAccCancelBean();
				noAccAction = new NoAccAction();
				openPanel(new AccCancelDempMethodPanel());
			}
		});
	}

	// 现金开户
	private void cashClick() {
		logger.info("调用跳转至现金开户的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="2";
				cashBean = new PublicCashOpenBean();
				openPanel(new MoneyBoxEntryModePanel(cashBean));
			}
		});
	}

	// 存单打印
	private void printClick() {
		logger.info("调用跳转至存单打印的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				billPrintBean = new BillPrintBean();
				GlobalParameter.ACC_NO="6";
				openPanel(new TransPrintAccOrAgrPanel(billPrintBean));
			}
		});
	}
	//汇划转账
	private void huiClick(){
		logger.info("调用跳转至汇划转账的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				transferBean = new PublicAccTransferBean();
				transferAction = new TransferAction();
				GlobalParameter.ACC_NO="3";
				openPanel(new TransferChooseBusiness(transferBean));
			}
		});
	}
	
	//汇划撤销 
	private void huiCancelClick(){
		logger.info("调用跳转至汇划撤销的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="4";
				transferAction = new TransferAction();
				Map<String,String> map = new HashMap<String, String>();
				openPanel(new TransferCancelInputBankCardPanel(map,transferBean));
			}
		});
	}
	
	//推荐有礼 
	private void awardGetClick(){
		logger.info("调用跳转至推荐有礼的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="21";
				jrnNoBean = new BulidingJrnNoBean();
				openPanel(new TJServicePanel());
			}
		});
	}
	
	//流水号绑定 
	private void svrBdClick(){
		logger.info("调用跳转至流水号绑定的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="20";
				jrnNoBean = new BulidingJrnNoBean();
				openPanel(new TransBuldingJrnlNo(jrnNoBean));
			}
		});
	}
	//存单换开 
	private void billChangeOpenClick(){
		logger.info("调用跳转至存单换开的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="7";
				bcOpenBean = new PublicBillChangeOpenBean();
				bcOpenAction = new BillChOpenAction();
				openPanel(new BillChangeOpenInputDepositPanel());
				
			}
		});
	}
	//密码增设
	private void addPwd(){
		logger.info("调用跳转密码增设的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="9";
				 addPwdBean=new AddPasswordBean();
				 addPwdAction=new AddPwdAction();
				 openPanel(new SetPwdInputCDPanel());
			}
		});
	}
	
	// 挂失解挂
	private void lostGetClick() {
		logger.info("调用跳转至挂失解挂的方法");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GlobalParameter.ACC_NO="8";
				lostPubBean = new LostPubBean();
				baseTransBean = new BaseTransBean();
				lostAction=new LostAction();
				lostPubBean.setUpStepName("BACK_HOME");//要返回的上一页标识
				openPanel(new SelectLostOrSolveLostPanel());
			}
		});
		
	}
	/**
	 * 查询凭证号
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkCertNo(final BaseTransBean transBean) {
		logger.info("调用查询凭证号的访求 ");
		// 查询凭证
		Map map = null;
		MistakeDialog mistakeDialog = null;
		try {
			if(transBean.getCertNoAdd()!=null && transBean.getCertEndNo()!=null && (Integer.parseInt(transBean.getCertEndNo())-Integer.parseInt(transBean.getCertNoAdd())>3)){
				return true;
			}
			logger.info("开始调用接口0005查询凭证号");
			map = IntefaceSendMsg.Tms0005();
			String resMsg1 = (String) map.get("errMsg");
			String resCode1 = (String) map.get("resCode");
			logger.info("调用接口0005完成" + resCode1 + "-" + resMsg1 + "-" + map);
			if (resCode1 != null && "" != resCode1) {
				if ("999998".equals(resCode1)) {
					// 凭证信息不存在
					mistakeDialog = new MistakeDialog(MainFrame.mainFrame,
							true, resMsg1 + ",请联系大堂经理！");
					mistakeDialog.showDialog();
					return isCert;
				} else if ("000000".equals(resCode1)) {
					// 凭证号不大于3张
					Integer now = Integer.valueOf((String) map.get("nowNo"));
					Integer end = Integer.valueOf((String) map.get("endNo"));
					if ((end - now) <= 3) {
						logger.info("凭证号不足数量为" + (end - now));
						transBean.setIsEnought("1");//凭证号不足
//						mistakeDialog = new MistakeDialog(MainFrame.mainFrame,
//								true, "凭证号不足,请联系大堂经理！");
//						mistakeDialog.showDialog();
						return isCert;
					} else {
						// 查询凭证成功
						transBean.setCertNoAdd((String) map.get("nowNo"));// 当前凭证号
						transBean.setCertNoId((String) map.get("nowId"));// 凭证ID
						transBean.setCertEndNo((String) map.get("endNo"));// 结束凭证号
						transBean.setCertStartNo((String) map.get("startNo"));// 起始凭证号
						transBean.setCreatTime((String) map.get("createDate"));//创建时间
						isCert = true;

					}
				} else {
					mistakeDialog = new MistakeDialog(MainFrame.mainFrame,
							true, resMsg1);
					mistakeDialog.showDialog();
					return isCert;
				}
			} else {
				// 其他异常
				mistakeDialog = new MistakeDialog(MainFrame.mainFrame, true,
						"查询凭证异常,请联系大堂经理！");
				mistakeDialog.showDialog();
				return isCert;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isCert;
	}

	
	/**
	 * 关闭组件，外设，定时
	 */
	public void closeAllControl(){
		logger.info("调用关闭组件，外设，定时器的方法");
		//判断上一个界面的定时器是否关闭
		stopTimer(delayTimer);
		stopTimer(timeAction);
		
		//判断上个界面的弹框并关闭
		closeDialog(prossDialog);
		closeDialog(mistakeDialog);
		closeDialog(confirmDialog);
		closeDialog(imageDialog);
		
		//判断上一个界面的语音是否关闭
		stopTimer(voiceTimer);
		closeVoice();
	}
	
	/*-------------组件公共处理方法-------------*/
	/***
	 * 设置剩余时间信息
	 * @param second
	 */
	protected void showTimeText(int second){
		logger.info("调用倒计时显示的方法");
		//在新建定时器前判断之前定时器是否关闭
		stopTimer(timeAction);
		this.second = second;		
		timeLabel = new JLabel("剩余时间:");
		timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setBounds(1260, 35, 100, 20);
		add(timeLabel);
		
		timeSecond=new JLabel(String.valueOf(this.second));
		timeSecond.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		timeSecond.setForeground(Color.GREEN);
		timeSecond.setHorizontalAlignment(SwingConstants.CENTER);
		timeSecond.setBounds(1360, 35, 50, 20);
		add(timeSecond);
		//界面倒计时显示
		timeAction = new Timer(1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	updateTimeText(); 
            }      
        });            
        timeAction.start(); 
        
        miaoLabel = new JLabel("秒");
        miaoLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        miaoLabel.setBounds(1410, 35, 40, 20);
        miaoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        miaoLabel.setForeground(Color.white);
		add(miaoLabel);
	}
	
	/***
	 * 设置剩余时间
	 */
	private void updateTimeText(){
		second = second - 1;
		timeSecond.setText(String.valueOf(second));
		if (second == 0){
			timeAction.stop();
		}
	}
	
	/**
	 * 判断定时器并关闭
	 * @param timer
	 */
	public void stopTimer(Timer timer){
		logger.info("调用判断定时器并关闭的方法");
		if(timer!=null&&timer.isRunning()){
			timer.stop();
		}
	}
	
	/***
	 * 清空倒计时
	 */
	protected void clearTimeText(){
		logger.info("调用清空倒计时的方法");
		//判断上一个界面的定时器是否关闭
		stopTimer(delayTimer);
		stopTimer(timeAction);
		timeLabel.setText("");
		timeSecond.setText("");
		miaoLabel.setText("");
	}
	
	/***
	 * 打开指定页面
	 * @param comp 打开的界面
	 * 
	 */
	protected void openPanel(final Component comp){
		final Component thisComp=this;
		logger.info("调用页面跳转的方法"+thisComp.getClass());
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				contentPanel.repaint();
				contentPanel.add(comp);
				
			}
		});
	}
	
	/***
	 * 打开指定页面
	 * @param thisComp 当前界面
	 * @param comp 打开的界面
	 * 
	 */
	protected void openPanel(final Component thisComp,final Component comp){
		logger.info("调用打开指定页面的方法"+thisComp.getClass());
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel =(BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				contentPanel.repaint();
				contentPanel.add(comp);
			}
		});
	}
	
	/**
	 * 退出按钮功能
	 * 
	 */
	protected void returnHome(){
		final Component thisComp=this;
		logger.info("点击退出按钮方法"+thisComp.getClass());
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					stopTimer(timeAction);
					return;
				}else{
					//关闭所有控件
					closeAllControl();
					//清除所有bean
					try {
						baseTransBean = new BaseTransBean();
						BeansUtils.beansSetNull(baseTransBean);
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("清空父类bean中的所有属性值失败："+e);
					}
					cashBean=null;
					transferAction=null;
					transferBean=null;
					accCancelBean=null;
					AccountTradeCodeAction.transBean=null;
					lostPubBean=null;
					lostAction=null;
					addPwdBean=null;
					addPwdAction=null;
					bcOpenAction=null;
					bcOpenBean=null;
					
					//解除开关
					GlobalParameter.OFF_ON=true;
					GlobalParameter.ACC_NO="";
					//清除当前页面
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
					contentPanel.repaint();
					//重新加载首页
					contentPanel.getManagerButton().setVisible(true);
					contentPanel.logoBt.setVisible(true);
					contentPanel.initShow();
				}
				
			}
		});
		
	}
	
	/**
	 * 退出按钮功能
	 * 
	 */
	protected void returnHomeTwo(Component thisCompNew){
		final Component thisComp = thisCompNew;
		logger.info("点击退出按钮方法"+thisComp.getClass());
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					stopTimer(timeAction);
					return;
				}else{
					//关闭所有控件
					closeAllControl();
					//清除所有bean
					try {
						baseTransBean = new BaseTransBean();
						BeansUtils.beansSetNull(baseTransBean);
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("清空父类bean中的所有属性值失败："+e);
					}
					cashBean=null;
					transferAction=null;
					transferBean=null;
					accCancelBean=null;
					AccountTradeCodeAction.transBean=null;
					lostPubBean=null;
					lostAction=null;
					addPwdBean=null;
					addPwdAction=null;
					bcOpenAction=null;
					bcOpenBean=null;
					
					//解除开关
					GlobalParameter.OFF_ON=true;
					GlobalParameter.ACC_NO="";
					//清除当前页面
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
					contentPanel.repaint();
					//重新加载首页
					contentPanel.getManagerButton().setVisible(true);
					contentPanel.logoBt.setVisible(true);
					contentPanel.initShow();
				}
				
			}
		});
		
	}

	/*-------------组件公共处理方法-------------*/
	/**
	 * 错误提示弹框
	 * @param errorMsg
	 */
	public void openMistakeDialog(String errorMsg){
		logger.info("调用错误提示框的方法");
		mistakeDialog=new MistakeDialog(MainFrame.mainFrame, true, errorMsg);
		mistakeDialog.showDialog(errorMsg);
	}
	
	/**
	 * 信息确认弹框
	 * @param msg
	 */
	protected void openConfirmDialog(String msg){
		logger.info("调用信息确认弹框的方法");
		confirmDialog=new CheckConfirmDialog(MainFrame.mainFrame, true, msg);
		confirmDialog.showDialog(msg);
	}
	
	/**
	 * 确认退出弹框
	 * @param msg
	 */
	protected void openConfirmExitDialog(String msg){
		logger.info("调用信息确认弹框的方法");
		confirmRePwdDialog=new ConfirmRePwdDialog(MainFrame.mainFrame, true, msg);
		confirmRePwdDialog.showDialog(msg);
	}
	
	/**
	 * 打开等待框
	 */
	protected void openProssDialog(){
		logger.info("调用打开等待框的方法");
		prossDialog=new ProssDialog(MainFrame.mainFrame, true);
		prossDialog.showDialog();
	}
	
	/**
	 * 判断弹框并关闭
	 * @param dialog
	 */
	protected void closeDialog(JDialog dialog){
		logger.info("调用判断弹框并关闭的方法");
		if(dialog!=null){
			dialog.dispose();
		}
	}
	
	/**
	 * 统一打开服务终止界面方法
	 * @param errorMsg 错误信息
	 * @param serStopMsg 错误提示
	 * @param usMsg 自定义原因
	 */
	protected void serverStop(String serStopMsg,String errorMsg,String usMsg){
		logger.info("调用打开统一终止界面的方法");
		openPanel(new PublicServerStop(serStopMsg, errorMsg,usMsg));
	}
	
	/**
	 * 统一打开服务终止界面方法
	 * @param errorMsg 错误信息
	 * @param serStopMsg 错误提示
	 * @param usMsg 自定义原因
	 */
	protected void serverStop(final Component thisComp,String serStopMsg,String errorMsg,String usMsg){
		logger.info("调用打开统一终止界面的方法");
		openPanel(thisComp,new PublicServerStop(serStopMsg, errorMsg,usMsg));
	}
	
	/*-------------外设公共处理方法-------------*/
	/**
	 * 执行语音
	 * @param voicePath
	 */
	public void excuteVoice(final String voicePath) {
		logger.info("调用执行语音的方法");
		new Thread(){
			@Override
			public void run(){
				try {
					FileInputStream fileau = new FileInputStream(voicePath);
					as = new AudioStream(fileau);
					AudioPlayer.player.start(as);
				} catch (Exception e) {
					logger.error("打开语音失败："+e);
				}
			}
		}.start();
	}
	/**
	 * 关闭语音
	 */
	public void closeVoice(){
		logger.info("调用关闭语音的方法");
		if(as==null){
			return;
		}
		try {
			as.close();
		} catch (Exception e) {
			logger.error("语音关闭失败："+e);
		}
	}
	/**
	 * 关闭socket
	 * @param socket
	 * @throws Exception
	 */
	private void closeSocket(Socket socket)throws Exception{
		logger.info("调用关闭socket的方法");
		if(socket!=null&&!socket.isClosed()&&!socket.isInputShutdown()){
			socket.shutdownInput();
		}
		if(socket!=null&&!socket.isClosed()&&!socket.isOutputShutdown()){
			socket.shutdownOutput();
		}
	}
	
	/**
	 * 调用关闭密码键盘
	 */
	public String closePwd() throws Exception{
		logger.info("调用关闭密码键盘的方法");
		closeSocket(NewKeypadDriver.socket);
		Thread.sleep(100);
		logger.info("密码键盘关闭");
		String res = new NewKeypadDriver().closePwd("6");
		return res;
		
	}
	
	/**
	 * 银行读卡器读取卡失败，退出，
	 */
	public void closeICBank() throws Exception{
		logger.info("调用读卡器失败并执行退卡的方法");
		closeSocket(ICBank.socket);
		Thread.sleep(100);
		try {
			logger.error("银行读卡器关闭");
			ICBank.cancelReadBankCard("2");
		} catch (Exception e) {
			logger.error("银行读卡器关闭失败："+e);
		}
	}
	
	
	/**
	 * 关闭身份证阅读器
	 */
	public void closeIDCard() throws Exception{
		logger.info("调用关闭身份证阅读器的方法");
		closeSocket(IdCard.socket);
		Thread.sleep(100);
		try {
			logger.info("身份证阅读器关闭");
			IdCard.idCardCancelRead("2");
		} catch (Exception e) {
			logger.error("身份证阅读器关闭失败："+e);
		}
	}
	
	/**打开led灯
	 * @throws Exception */
	public void openLed(String code) throws Exception{
		logger.info("调用打开led灯的方法");
		LedStateBean openLed = new LedStateBean();
		openLed = MachineLed.openLed(code);
		logger.info(code+"号Led灯打开返回值："+openLed);
	}
	
	/**关闭led灯
	 * @throws Exception */
	public void closeLed(String code) throws Exception{
		logger.info("调用关闭led灯的方法");
		LedStateBean closeLed = new LedStateBean();
		closeLed = MachineLed.closeLed(code);
		logger.info(code+"号Led灯关闭返回值："+closeLed);
	}
	/**
	 * 关闭二维码扫描仪
	 * @throws Exception
	 */
	public void closeDimension()throws Exception{
		logger.info("调用关闭二维码扫描仪的方法");
		closeSocket(Dimension.socket);
		Thread.sleep(100);
		try {
			logger.info("二维码扫描仪关闭");
			Dimension.dimensionCancelRead("2");
		} catch (Exception e) {
			logger.error("二维码扫描仪关闭失败："+e);
		}
	}
	/**
	 * 睡眠等待外设反应
	 * 单位毫秒
	 * @param ms
	 */
	public void sleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			logger.error("睡眠失败："+e);
		}
	}
	
	/**
	 * 检查并退卡
	 * @throws Exception
	 */
	public void quitBankCard()throws Exception{
		logger.info("调用检查并退卡的方法");
		new Thread(){
			@Override
			public void run(){
				ICBankCheckStatusBean bean;
				try {
					bean = ICBank.checkStatus("2");
					if(!"0".equals(bean.getStatus())){
						logger.error(bean.getStatusDesc());
						return;
					}
					//判断读卡器里是否有卡
					if(bean.getTouchStatus().equals("1")){
						//若有卡则退卡
						ICBank.ICBankQuit("2", "60");
					}
				} catch (Exception e) {
					logger.error("读卡器异常："+e);
				}
			}
		}.start();
	}
	
	/**
	 * 退存单方法
	 */
	public void outBill()throws Exception {
		new Thread() {
			public void run() {
				try {
					openLed("6");//开灯
					openLed("7");
					closeLed("6");//关灯
					closeLed("7");
				} catch (Exception e) {
					logger.error("调用回单退单口led灯失败："+e);
				}
				// 1.退票给用户
				Dispatch.call(BaseLoginFrame.dispath, "BH_Eject",new Variant("60000"));
				// 2.清理资源
				Dispatch.call(BaseLoginFrame.dispath, "BH_CleanResource");
				
				//存单退出，变更存单状态
				GlobalParameter.ACC_STATUS=false;
			}
		}.start();
	}
	
	/**
	 * 销户正常退出
	 */
	public void accCancelExit(){
		
		if(!GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){//无存单、无卡情况
			
			returnHome();//返回首页
			return;
		}
		if(!GlobalParameter.ACC_STATUS && GlobalParameter.CARD_STATUS){//无存单、有卡情况
			
			openPanel(new OutputBankCardPanel());//返回退卡页
			return;
		}
		if(GlobalParameter.ACC_STATUS && GlobalParameter.CARD_STATUS){//有存单、有卡情况
			
			openPanel(new  AccCancelOutputDepositPanel());//返回退存单页
			return;
		}
		if (GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){//有存单、无卡情况
			
			openPanel(new AccCancelOutputDepositPanel());//返回退存单页
			return;
		}
		
	}
	/**
	 * 存单换开正常退出
	 */
	public void billHKExit(){
		
		if(!GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){//无存单、无卡情况
			
			returnHome();//返回首页
			return;
		}
		if(!GlobalParameter.ACC_STATUS && GlobalParameter.CARD_STATUS){//无存单、有卡情况
			
			openPanel(new OutputBankCardPanel());//返回退卡页
			return;
		}
		if(GlobalParameter.ACC_STATUS && GlobalParameter.CARD_STATUS){//有存单、有卡情况
			
			openPanel(new BillChangeOpenOutputDepositPanel());//返回退存单页
			return;
		}
		if (GlobalParameter.ACC_STATUS && !GlobalParameter.CARD_STATUS){//有存单、无卡情况
			
			openPanel(new BillChangeOpenOutputDepositPanel());//返回退存单页
			return;
		}
		
	}
	
	/**
	 * 返回上一步赋值
	 */
	public void backStepMethod(){
		baseTransBean.setNextStepName(baseTransBean.getUpStepName());
	}
	
	
	
	
	
	
	
}
