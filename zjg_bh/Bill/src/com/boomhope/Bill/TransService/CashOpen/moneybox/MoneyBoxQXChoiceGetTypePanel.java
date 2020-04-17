package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.TransService.CashOpen.Bean.QXRateInfosBean;
import com.boomhope.Bill.Util.AccZYDRulePanel;
import com.boomhope.Bill.Util.Property;


/**
 * @author 千禧收益方式选择页面
 *
 */
@SuppressWarnings("unchecked")
public class MoneyBoxQXChoiceGetTypePanel extends BaseTransPanelNew{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(MoneyBoxQXChoiceGetTypePanel.class);
	
	private boolean on_off=true;//控制双击开关
	
	private List<QXRateInfosBean> listInfos=null;
	private List<QXRateInfosBean> listInfosNew=null;
	private JPanel panelTitle;//表头面板
	private JPanel panelOver;//底部面板
	private JPanel[] panels;//信息展示面板组
	private JLabel[] labels;//背景框
	private JLabel zydrComfirm;//规则确认按钮
	private AccZYDRulePanel azyd;
	
	
	public MoneyBoxQXChoiceGetTypePanel(final PublicCashOpenBean transBean){
		logger.info("进入千禧产品收益方式选择页面");
		this.cashBean = transBean;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	on_off=false;
            	logger.info("录入存单信息页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();//清空倒计时
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
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
				backTrans(transBean);
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
				nextStep(transBean);
			}
		});
		confirm.setSize( 150, 50);
		confirm.setLocation(890, 770);
		add(confirm);
		
		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		add(backButton);
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
		    	returnHome();
			}
		});
		
		//加载收益方式信息
		showInfos();
		
	}
	
	//上一步
	public void backTrans(PublicCashOpenBean transBean){
		cashBean.setInte("");
		cashBean.setRate("");
		cashBean.setQxGetHaveType("");
		cashBean.setRuleName("");
		cashBean.setProductName("");
		cashBean.setProductedNameNew("");
		clearTimeText();
		openPanel(new MoneyBoxAgreementPanel(transBean));
	}
	
	//确认
	public void nextStep(PublicCashOpenBean transBean){
		if(cashBean.getQxGetHaveType()==null || "".equals(cashBean.getQxGetHaveType())){
			openMistakeDialog("请先选择收益方式");
			on_off=true;
			return;
		}
		clearTimeText();
		if(transBean.getProductCode().startsWith("QX")){
			openPanel(new MoneyBoxEnteringQXXLPanel(transBean));
		}else{
			openPanel(new MoneyBoxEnteringXFYJYXLPanel(transBean));
		}
	}
	
	//显示所有利率利息信息
	public void showInfos(){
		listInfos=(List<QXRateInfosBean>)cashBean.getAccountList().get("qxRateList");
		cashBean.setQxGetHaveType("");
		if(listInfos.size()>0){
			//显示所有方式的面板数组
			panels = new JPanel[3];
			labels = new JLabel[3];
			//唐豆规则的信息
			azyd = new AccZYDRulePanel(showJpanel,Property.acc_td_rules_path);
			azyd.getJsp().setVisible(false);
			//整理需要使用的数据
			listInfosNew=new ArrayList<QXRateInfosBean>();
			listPut();
			
			panelTitle = new JPanel();
			panelTitle.setBounds(0, 0, 1009, 150);
			panelTitle.setOpaque(false);
			panelTitle.setLayout(null);
			this.showJpanel.add(panelTitle);
			
			panelOver = new JPanel();
			panelOver.setBounds(0, 550, 1009, 30);
			panelOver.setLayout(null);
			panelOver.setOpaque(false);
			this.showJpanel.add(panelOver);
			
			JLabel labelTitle = new JLabel("请选择收益方式");
			labelTitle.setBounds(0, 40, 1009, 50);
			labelTitle.setHorizontalAlignment(SwingUtilities.CENTER);
			labelTitle.setFont(new Font("微软雅黑",Font.BOLD,40));
			panelTitle.add(labelTitle);
			
			JLabel label1 = new JLabel("收益方式");
			label1.setBounds(50, 100, 227, 40);
			label1.setHorizontalAlignment(SwingUtilities.CENTER);
			label1.setFont(new Font("微软雅黑",Font.PLAIN,30));
			panelTitle.add(label1);
			
			JLabel label2 = new JLabel("到期利率");
			label2.setBounds(277, 100, 227, 40);
			label2.setHorizontalAlignment(SwingUtilities.CENTER);
			label2.setFont(new Font("微软雅黑",Font.PLAIN,30));
			panelTitle.add(label2);
			
			JLabel label3 = new JLabel("唐豆数量");
			label3.setBounds(504, 100, 227, 40);
			label3.setHorizontalAlignment(SwingUtilities.CENTER);
			label3.setFont(new Font("微软雅黑",Font.PLAIN,30));
			panelTitle.add(label3);
			
			JLabel label4 = new JLabel("综合收益率");
			label4.setBounds(731, 100, 229, 40);
			label4.setHorizontalAlignment(SwingUtilities.CENTER);
			label4.setFont(new Font("微软雅黑",Font.PLAIN,30));
			panelTitle.add(label4);
			
			JLabel rlueLabel =new JLabel("了解详情可点击  ");
			rlueLabel.setBounds(0, 0, 300, 30);
			rlueLabel.setFont(new Font("微软雅黑",Font.PLAIN,30));
			rlueLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
			panelOver.add(rlueLabel);
			
			JLabel rlueButton = new JLabel("唐豆规则(链接)");
			rlueButton.setBounds(300, 0, 300, 30);
			rlueButton.setFont(new Font("微软雅黑",Font.BOLD,30));
			rlueButton.setHorizontalAlignment(SwingUtilities.CENTER);
			rlueButton.setForeground(Color.red);
			rlueButton.setBackground(Color.BLUE);
			panelOver.add(rlueButton);
			rlueButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击唐豆规则按钮");
					azyd.getJsp().setVisible(true);
					showRlues();
				}
			});
			
			
			JLabel rlueOver =new JLabel("查看具体唐豆相关规则");
			rlueOver.setBounds(600, 0, 409, 30);
			rlueOver.setFont(new Font("微软雅黑",Font.PLAIN,30));
			rlueOver.setHorizontalAlignment(SwingUtilities.LEFT);
			panelOver.add(rlueOver);
			
			/* 确认 */
			zydrComfirm = new JLabel(new ImageIcon("pic/affirm.png"));
			zydrComfirm.setBounds((showJpanel.getWidth()-200)/2, showJpanel.getHeight()-70, 200, 50);
			zydrComfirm.setVisible(false);
			this.showJpanel.add(zydrComfirm);
			zydrComfirm.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					logger.info("点击规则确认按钮");
					azyd.getJsp().setVisible(false);
					notShowRule();
				}
			});
			
			
			int y = 170;//panel面板的纵坐标
			for(int i=0;i<3;i++){
				final int a = i;
				
				panels[i] = new JPanel();
				panels[i].setLayout(null);
				panels[i].setBounds(10, y, 989, 100);
				panels[i].setOpaque(false);
				this.showJpanel.add(panels[i]);
				
				ImageIcon image = null;
				image = new ImageIcon("pic/newPic/accNoSelect.png");
				image.setImage(image.getImage().getScaledInstance(1009, 100,Image.SCALE_DEFAULT ));
				
				labels[i] = new JLabel(image);
				labels[i].setBounds(10, y, 989, 100);
				this.showJpanel.add(labels[i]);
				
				//收益方式名称
				String ruleName="";
				if("0".equals(listInfosNew.get(i).getRuleNo().trim())){
					ruleName = "利率";
				}else if("1".equals(listInfosNew.get(i).getRuleNo().trim())){
					ruleName = "利率+消费豆";
				}else if("2".equals(listInfosNew.get(i).getRuleNo().trim())){
					ruleName = "利率+增益豆";
				}
				JLabel labelType = new JLabel(ruleName);
				labelType.setBounds(50, 25, 227, 50);
				labelType.setHorizontalAlignment(SwingUtilities.CENTER);
				labelType.setFont(new Font("微软雅黑",Font.PLAIN,30));
				panels[i].add(labelType);
				
				JLabel labelRate = new JLabel(listInfosNew.get(i).getDoFuVal()+"%");
				labelRate.setBounds(277, 25, 227, 50);
				labelRate.setHorizontalAlignment(SwingUtilities.CENTER);
				labelRate.setFont(new Font("微软雅黑",Font.PLAIN,30));
				panels[i].add(labelRate);
				
				JLabel labelTang = new JLabel(listInfosNew.get(i).getGetTdSum());
				labelTang.setBounds(504, 25, 227, 50);
				labelTang.setHorizontalAlignment(SwingUtilities.CENTER);
				labelTang.setFont(new Font("微软雅黑",Font.PLAIN,30));
				panels[i].add(labelTang);
				
				JLabel labelZong = new JLabel(listInfosNew.get(i).getZongGetRate()+"%");
				labelZong.setBounds(731, 25, 227, 50);
				labelZong.setHorizontalAlignment(SwingUtilities.CENTER);
				labelZong.setFont(new Font("微软雅黑",Font.PLAIN,30));
				panels[i].add(labelZong);
				
				labels[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						for(int j=0;j<3;j++){
							if(a==j){
								ImageIcon imageSelect = new ImageIcon("pic/newPic/accYesSelect.png");
								imageSelect.setImage(imageSelect.getImage().getScaledInstance(1009, 100,Image.SCALE_DEFAULT ));
								labels[j].setIcon(imageSelect);
							}else{
								ImageIcon imageSelect = new ImageIcon("pic/newPic/accNoSelect.png");
								imageSelect.setImage(imageSelect.getImage().getScaledInstance(1009, 100,Image.SCALE_DEFAULT ));
								labels[j].setIcon(imageSelect);
							}
						}
						cashBean.setQxGetHaveType(listInfosNew.get(a).getRuleNo());//千禧收益规则
						cashBean.setInte(listInfosNew.get(a).getGetInte());//赋值预计利息
						cashBean.setRate(listInfosNew.get(a).getDoFuVal().trim()+"00");//获取预计利息
						cashBean.setRuleName(listInfosNew.get(a).getRuleName().trim());//规则名称
					}
				});
				
				//加载完第一个面板后调整纵坐标位置
				y+=100;
			}
		}
		
		
	}
	
	//将五条规则数据合并成3条
	public void listPut(){
		for(QXRateInfosBean bean : listInfos){
			if(listInfosNew!=null && listInfosNew.size()>0){
				boolean start=false;
				for(int j=0;j<listInfosNew.size();j++){
					QXRateInfosBean beanNew = listInfosNew.get(j);
					if(bean.getRuleNo().equals(beanNew.getRuleNo())){
						start=true;
						if(bean.getGiveType().equals(beanNew.getGiveType())){
							continue;
						}else{
							if("0".equals(bean.getGiveType())){//如果是利率
								listInfosNew.get(j).setGetInte(bean.getPaySum());//赋值为到期利息
								listInfosNew.get(j).setDoFuVal(bean.getDoFuVal().trim()+"00");//赋值为到期利率
							}else{
								listInfosNew.get(j).setGetTdSum(bean.getPaySum());//赋值为唐豆数量
							}
						}
					}else{
						continue;
					}
				}
				//如果新的集合中没有数据
				if(!start){
					bean.setGetInte(bean.getPaySum());
					listInfosNew.add(bean);
				}
			}else{
				bean.setGetInte(bean.getPaySum());
				listInfosNew.add(bean);
			}
		}
	}
	
	//显示唐豆规则
	public void showRlues(){
		azyd.getJsp().setVisible(true);
		zydrComfirm.setVisible(true);
		panelTitle.setVisible(false);
		panelOver.setVisible(false);
		for(int i=0;i<panels.length;i++){
			panels[i].setVisible(false);
			labels[i].setVisible(false);
		}
	}
	
	//关闭规则
	public void notShowRule(){
		azyd.getJsp().setVisible(false);
		zydrComfirm.setVisible(false);
		panelTitle.setVisible(true);
		panelOver.setVisible(true);
		for(int i=0;i<panels.length;i++){
			panels[i].setVisible(true);
			labels[i].setVisible(true);
		}
	}
}
