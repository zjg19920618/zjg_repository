package com.boomhope.Bill.TransService.BillPrint;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillPrint.Interface.CardsInfo;
import com.boomhope.Bill.TransService.BillPrint.Interface.EAccInfoBean;


/**
 * 打印协议书身份证查询到的银行卡选择页
 * @author Administrator
 *
 */
public class TransPrintAgreementShowCardsPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransPrintAgreementShowCardsPanel.class);
	private static final long serialVersionUID = 1L; 
	
	private int nowPage=1;//当前页数
	private int maxPage;//最大页数
	private	int cardNums;//银行卡数量
	
	private JPanel panel;//底板
	
	private JLabel labelUp;//上一页按钮
	private JLabel labelDown;//下一页按钮
	
	private List<CardsInfo> list;//银行卡信息集合
	
	public TransPrintAgreementShowCardsPanel(){
		logger.info("进入展示银行卡页面");
		
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime*1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("银行卡展示页面倒计时结束 ");
				/* 倒计时结束退出交易 */ 
				clearTimeText();//清空倒计时
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
			}
		});
		
		JLabel labelHead = new JLabel("请选择要打印协议的银行卡");
		labelHead.setBounds(0, 40, 1009, 40);
		labelHead.setFont(new Font("微软雅黑",Font.BOLD,34));
		labelHead.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(labelHead);
		
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");				
				backTrans();
			}

		});
		label.setBounds(1075, 770, 150, 50);
		this.add(label);
		//退出
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击退出按钮");				
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);

		/*添加修饰样式*/
		//向左
		labelUp=new JLabel();
		labelUp.setIcon(new ImageIcon("pic/newPic/left.png"));
		labelUp.setBounds(30,265 ,57, 98);
		labelUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向左按钮");
				upPage();
			}
		});
		this.showJpanel.add(labelUp);
	   
		//向右
		labelDown=new JLabel();
		labelDown.setIcon(new ImageIcon("pic/newPic/right.png"));
		labelDown.setBounds(922,265,57, 98);
		labelDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击向右按钮");
				downPage();
			}
		});
		this.showJpanel.add(labelDown);
		
		//展示所有银行卡的底板
		panel = new JPanel();
		panel.setBounds(105, 110, 800, 500);
		panel.setLayout(null);
		panel.setOpaque(false);
		this.showJpanel.add(panel);
		
		//初始页面
		init();
	}
	
	
	/**
	 * 初始化数据
	 */
	public void init(){
		list=(List<CardsInfo>)billPrintBean.getSubInfo().get("CardInfo");
		
		//银行卡数量
		cardNums = list.size();
		
		//计算最大页数
		if(cardNums%4!=0){
			maxPage=cardNums/4+1;
		}else{
			maxPage=cardNums/4;
		}
		
		//显示上下翻页按钮
		upAndDown();
		//显示银行卡
		showCards();
	}
	
	/**
	 * 显示上下页翻页按钮
	 */
	public void upAndDown(){
		if(maxPage==1){
			labelUp.setVisible(false);
			labelDown.setVisible(false);
		}else if(nowPage==1 && maxPage>1){
			labelUp.setVisible(false);
			labelDown.setVisible(true);
		}else if(nowPage>1 && maxPage>nowPage){
			labelUp.setVisible(true);
			labelDown.setVisible(true);
		}else if(nowPage>1 && nowPage==maxPage){
			labelUp.setVisible(true);
			labelDown.setVisible(false);
		}
	}
	
	//上一页按钮
	public void upPage(){
		nowPage -= 1;
		panel.removeAll();
		panel.repaint();
		upAndDown();
		showCards();
	}
	
	//下一页按钮
	public void downPage(){
		nowPage += 1;
		panel.removeAll();
		panel.repaint();
		upAndDown();
		showCards();
	}
	
	//展示当前页的卡信息
	public void showCards(){
		//当前页面银行卡的最大数量
		int pageNum=0;
		if(nowPage==maxPage){
			pageNum=cardNums;
		}else{
			pageNum=nowPage*4;
		}
		for(int i=(nowPage-1)*4;i<pageNum;i++){
			
			//下标
			final int indexNum=i;
			
			int x=0;//显示位置的横坐标
			int y=0;//显示位置的纵坐标
			//确定每张卡显示的位置
			if(i%4==0){
				x=35;
				y=50;
			}else if(i%4==1){
				x=415;
				y=50;
			}else if(i%4==2){
				x=35;
				y=270;
			}else{
				x=415;
				y=270;
			}
			
			
			JPanel cardPanel = new JPanel();
			cardPanel.setBounds(x, y, 350, 180);
			cardPanel.setLayout(null);
			panel.add(cardPanel);
			
			//卡号
			JLabel label1=new JLabel("卡  号：");
			label1.setBounds(10, 35, 80, 20);
			label1.setFont(new Font("微软雅黑",Font.BOLD,20));
			label1.setHorizontalAlignment(SwingUtilities.LEFT);
			cardPanel.add(label1);
			
			//类型
			JLabel label2 = new JLabel();
			label2.setBounds(75, 35, 265, 20);
			label2.setFont(new Font("微软雅黑",Font.BOLD,20));
			label2.setHorizontalAlignment(SwingUtilities.LEFT);
			label2.setText(list.get(i).getCard_No());
			cardPanel.add(label2);
			//卡号
			JLabel label3=new JLabel("类  型：");
			label3.setBounds(10, 75, 80, 20);
			label3.setFont(new Font("微软雅黑",Font.BOLD,20));
			label3.setHorizontalAlignment(SwingUtilities.LEFT);
			cardPanel.add(label3);
			
			//开户机构
			JLabel label4 = new JLabel();
			label4.setBounds(75, 75, 265, 20);
			label4.setFont(new Font("微软雅黑",Font.BOLD,20));
			label4.setHorizontalAlignment(SwingUtilities.LEFT);
			label4.setText(list.get(i).getCard_Type().equals("1")?"银行卡子账户":list.get(i).getCard_Type());
			cardPanel.add(label4);//卡号
			JLabel label5=new JLabel("开户机构：");
			label5.setBounds(10, 115, 100, 20);
			label5.setFont(new Font("微软雅黑",Font.BOLD,20));
			label5.setHorizontalAlignment(SwingUtilities.LEFT);
			cardPanel.add(label5);
			
			//卡号
			JLabel label6 = new JLabel();
			label6.setBounds(110, 115, 265, 20);
			label6.setFont(new Font("微软雅黑",Font.BOLD,20));
			label6.setHorizontalAlignment(SwingUtilities.LEFT);
			label6.setText(list.get(i).getOpen_inst());
			cardPanel.add(label6);
			//设置背景图片
			JLabel jlb = new JLabel();
			jlb.setLocation(0, 0);
			ImageIcon image = new ImageIcon("pic/bck.png");
			image.setImage(image.getImage().getScaledInstance(350, 180,Image.SCALE_DEFAULT ));
			jlb.setIcon(image);
			jlb.setSize(350, 180);
			cardPanel.add(jlb);
			
			//点击银行卡
			cardPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					billPrintBean.setAccNo(list.get(indexNum).getCard_No());
					billPrintBean.setCardType(list.get(indexNum).getCard_Type());
					nextStep();
				}
			});
		}
	}
	
	/**
	 * 点击银行卡跳转
	 */
	public void nextStep(){
		clearTimeText();
		openPanel(new TransInputBankCardPassPanel(billPrintBean,bankBeans));
	}
	
	
	/**
	 * 上一步
	 */
	public void backTrans(){
		clearTimeText();
		openPanel(new TransferPrintCameraPanel(billPrintBean));
	}
}
