package com.boomhope.Bill.Framework.ChildBusiness;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransBean;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.TransService.BillAddPwd.Bean.AddPasswordBean;
import com.boomhope.Bill.TransService.BillAddPwd.BillAddPwdAction.AddPwdAction;
import com.boomhope.Bill.TransService.BillAddPwd.ServicePanel.SetPwdInputCDPanel;
import com.boomhope.Bill.TransService.BillChOpen.Action.BillChOpenAction;
import com.boomhope.Bill.TransService.BillChOpen.Bean.PublicBillChangeOpenBean;
import com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc.BillChangeOpenInputDepositPanel;
import com.boomhope.Bill.TransService.BillPrint.TransPrintAccOrAgrPanel;
import com.boomhope.Bill.TransService.BillPrint.Bean.BillPrintBean;
import com.boomhope.Bill.TransService.BudingJrnlNo.TJServicePanel;
import com.boomhope.Bill.TransService.BudingJrnlNo.TransBuldingJrnlNo;
import com.boomhope.Bill.TransService.BudingJrnlNo.bean.BulidingJrnNoBean;
import com.boomhope.Bill.TransService.LostReport.Action.LostAction;
import com.boomhope.Bill.TransService.LostReport.Bean.LostPubBean;
import com.boomhope.Bill.TransService.LostReport.Page.SelectLostOrSolveLostPanel;

public class OtherPanel extends BaseTransPanelNew {
	Logger logger = Logger.getLogger(OtherPanel.class);

	private JLabel printLabel;//子账户打印
	private JLabel svrbangLable;//流水绑定
	private JLabel awardLabel;//推荐有礼
	private boolean on_off=true;//跳转的开关控制
	
	private JLabel bcOpenLabel;//存单换开
	private JLabel addPwdLabel;//增设密码
	private JLabel lostLabel;//挂失解挂
	private JLabel nextPage;//下一页按钮
	private JLabel upPage;//上一页按钮
	private int page=1;//当前页数
	private int maxPage;//最大页数
	private int products;//产品数量
	/**
	 * 更改背景
	 */
	public void paintComponent(Graphics g){
		logger.info("加载其它子业务选择页的背景");
		g.drawImage(new ImageIcon("pic/newPic/ground.png").getImage(), 220,
				150, GlobalParameter.TRANS_WIDTH - 50,
				GlobalParameter.TRANS_HEIGHT - 140, this);
	}

	/**
	 * 界面初始化
	 */
	public OtherPanel() {
		logger.info("进入其它子业务选择页面");
		GlobalParameter.ACC_NO = "100";
		this.removeAll();
		repaint();

		/* 显示时间倒计时 */
		if (delayTimer != null) {
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						logger.info("其它子业务选择页倒计时结束");
						clearTimeText();
					}

				});
		delayTimer.start();

		// 界面初始化参数配置
		this.setSize(GlobalParameter.WINDOW_WIDTH, 830);
		this.setLocation(0, 0);
		this.setOpaque(false);
		this.setLayout(null);

		// 子账户打印
		printLabel = new JLabel(new ImageIcon("pic/newPic/subPrintBt.png"));
		printLabel.setBounds(320, 300, 200, 300);
		add(printLabel);
		printLabel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击子账户打印按钮");
						printBill();
					}
				});
			}
		});

		// 流水绑定
		svrbangLable = new JLabel(new ImageIcon("pic/newPic/svrBang.jpg"));
		svrbangLable.setBounds(620, 300, 200, 300);
		add(svrbangLable);
		svrbangLable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击流水绑定按钮");
						svrBang();
					}
				});
			}
		});

		// 推荐有礼
		awardLabel = new JLabel(new ImageIcon("pic/newPic/putForAward.png"));
		awardLabel.setBounds(920, 300, 200, 300);
		add(awardLabel);
		awardLabel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击推荐有礼按钮");
						awardGet();
					}
				});
			}
		});
		// 存单换开
		bcOpenLabel = new JLabel(new ImageIcon("pic/newPic/BillDeposit.png"));
		bcOpenLabel.setBounds(620, 300, 200, 300);
		bcOpenLabel.setVisible(false);
		add(bcOpenLabel);
		bcOpenLabel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击存单换开按钮");
						billChangeOpen();
					}
				});
			}
		});
		// 增设密码
		addPwdLabel = new JLabel(new ImageIcon("pic/newPic/addPassword.png"));
		addPwdLabel.setBounds(320, 300, 200, 300);
		addPwdLabel.setVisible(false);
		add(addPwdLabel);
		addPwdLabel.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (!on_off) {
							return;
						}
						on_off = false;
						logger.info("点击增设密码按钮");
						addPwd();
					}
				});
			}
		});
		
		 //挂失解挂
		lostLabel = new JLabel(new ImageIcon("pic/newPic/lostSolve.jpg"));
		lostLabel.setBounds(920, 300, 200,300);
		lostLabel.setVisible(false);
		add(lostLabel);
		lostLabel.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent arg0){
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						if(!on_off){
							return;
						}
						on_off=false;
						logger.info("点击挂失解挂按钮");
						lostGet();
					}
				});
			}
		});
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1079, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");
				clearTimeText();
				returnHome();
			}
		});
		add(backButton);

		// 下一页
		nextPage = new JLabel(new ImageIcon("pic/newPic/right.png"));
		nextPage.setBounds(1150, 400, 57, 98);
		nextPage.setVisible(false);
		add(nextPage);

		nextPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				page += 1;
				initShow();
			}
		});

		// 上一页
		upPage = new JLabel(new ImageIcon("pic/newPic/left.png"));
		upPage.setBounds(243, 400, 57, 98);
		upPage.setVisible(false);
		add(upPage);
		upPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				page -= 1;
				initShow();
			}
		});

		initShow();
	}

	// 页面显示图片根据打印机情况而定
	public void initShow() {
		// 隐藏所有图标
		hidePic();
		// 有打印机
		if ("0".equals(GlobalParameter.printStatus)) {
			products = 6;
			maxPageMethods();
			if (page == 1) {
				printLabel.setLocation(320, 300);
				printLabel.setVisible(true);
				svrbangLable.setLocation(620, 300);
				svrbangLable.setVisible(true);
				awardLabel.setLocation(920, 300);
				awardLabel.setVisible(true);

			} else if (page == 2) {
				addPwdLabel.setLocation(320, 300);
				addPwdLabel.setVisible(true);
				bcOpenLabel.setLocation(620, 300);
				bcOpenLabel.setVisible(true);
				lostLabel.setLocation(920, 300);
				lostLabel.setVisible(true);
			}

		} else {
			// 无打印机
			products = 5;
			maxPageMethods();
			if (page == 1) {
				printLabel.setLocation(320, 300);
				printLabel.setVisible(true);
				svrbangLable.setLocation(620, 300);
				svrbangLable.setVisible(true);
				awardLabel.setLocation(920, 300);
				awardLabel.setVisible(true);

			} else if (page == 2) {
				addPwdLabel.setLocation(320, 300);
				addPwdLabel.setVisible(true);
				lostLabel.setLocation(620, 300);
				lostLabel.setVisible(true);
			}

		}
		// 显示上下页面按钮
		nextOrUpPage();
	}

	// 隐藏所有图标
	public void hidePic() {
		bcOpenLabel.setVisible(false);
		printLabel.setVisible(false);
		awardLabel.setVisible(false);
		addPwdLabel.setVisible(false);
		svrbangLable.setVisible(false);
		lostLabel.setVisible(false);
		nextPage.setVisible(true);
		upPage.setVisible(true);
	}

	// 设置最大页数
	public void maxPageMethods() {
		int pages = products % 3;
		if (pages == 0) {
			maxPage = products / 3;
		} else {
			maxPage = products / 3 + 1;
		}
	}

	// 显示上下页按钮
	public void nextOrUpPage() {
		if (page == 1 && maxPage == 1) {
			nextPage.setVisible(false);
			upPage.setVisible(false);
		} else if (page == 1 && maxPage > 1) {
			upPage.setVisible(false);
		} else if (page == maxPage && maxPage > 1) {
			nextPage.setVisible(false);
		}
	}

	// 流水绑定
	public void svrBang() {
		logger.info("进入流水绑定的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (thisComp.getParent() == null) {
					return;
				} else {
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO = "20";
				contentPanel.repaint();
				jrnNoBean = new BulidingJrnNoBean();
				contentPanel.add(new TransBuldingJrnlNo(jrnNoBean));
			}
		});
	}

	// 推荐有礼
	public void awardGet() {
		logger.info("进入推荐有礼的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (thisComp.getParent() == null) {
					return;
				} else {
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO = "21";
				contentPanel.repaint();
				jrnNoBean = new BulidingJrnNoBean();
				contentPanel.add(new TJServicePanel());
			}
		});
	}

	// 子账户打印
	public void printBill() {
		logger.info("进入子账户打印的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (thisComp.getParent() == null) {
					return;
				} else {
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO = "6";
				contentPanel.repaint();
				billPrintBean = new BillPrintBean();
				contentPanel.add(new TransPrintAccOrAgrPanel(billPrintBean));
			}
		});
	}

	// 存单换开
	public void billChangeOpen() {
		logger.info("存单换开的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (thisComp.getParent() == null) {
					return;
				} else {
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO = "7";
				contentPanel.repaint();
				bcOpenBean = new PublicBillChangeOpenBean();
				BaseTransPanelNew.bcOpenAction = new BillChOpenAction();
				bcOpenBean.getReqMCM001().setTranscode("11048");
				// 插入存单
				contentPanel.add(new BillChangeOpenInputDepositPanel());
			}
		});
	}

	// 增设密码
	public void addPwd() {
		logger.info("进入增设密码的方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (thisComp.getParent() == null) {
					return;
				} else {
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO = "9";
				contentPanel.repaint();
				addPwdBean = new AddPasswordBean();
				addPwdAction = new AddPwdAction();
				addPwdBean.getReqMCM001().setTranscode("11049");
				contentPanel.add(new SetPwdInputCDPanel());
			}
		});
	}
	
	//挂失解挂
	public void lostGet(){
		logger.info("进入挂失解挂方法");
		final Component thisComp = this;
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				if(thisComp.getParent()==null){
					return;
				}else{
					contentPanel = (BaseContentPanel) thisComp.getParent();
					contentPanel.remove(thisComp);
				}
				GlobalParameter.ACC_NO="8";
				contentPanel.repaint();
				lostPubBean=new LostPubBean();
				baseTransBean = new BaseTransBean();
				lostAction=new LostAction();
				contentPanel.add(new SelectLostOrSolveLostPanel());
			}
		});
	}
}
