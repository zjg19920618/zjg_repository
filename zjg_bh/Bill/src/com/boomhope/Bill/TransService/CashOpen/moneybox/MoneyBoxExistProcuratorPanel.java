package com.boomhope.Bill.TransService.CashOpen.moneybox;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.TransService.CashOpen.Bean.PublicCashOpenBean;
import com.boomhope.Bill.Util.BmpUtil;
import com.boomhope.Bill.Util.Property;
import com.boomhope.Bill.Util.UtilButton;
import com.boomhope.Bill.Util.UtilImage;
import com.boomhope.Bill.Util.UtilImages;

/**
 * 请授权-存在代理人
 * @author gyw
 *
 */
public class MoneyBoxExistProcuratorPanel extends BaseTransPanelNew{

	static Logger logger = Logger.getLogger(MoneyBoxExistProcuratorPanel.class);
	private static final long serialVersionUID = 1L;
	String imageFile;
	JLabel lblNewLabel_4desc = null;
	JLabel lblNewLabel_6desc = null;
	JLabel lblNewLabel_9desc = null;
	UtilImages image13 = null;
	UtilImages image12 = null;
	UtilImages image11 = null;
	UtilImages image10 = null;
	UtilImages image9 = null;
	UtilImages image8 = null;
	UtilImage image7 = null;
	UtilImage image6 = null;
	UtilImage image5 = null;
	UtilImage image4 = null;
	UtilImage image3 = null;
	UtilImage image3_1 = null;
	private UtilImage image3_3;
	UtilImage image2 = null;
	UtilImage image1 = null;
	UtilImage image = null;
	JPanel empower_panel = null;
	JPanel panel = null;
	JPanel empower_panel_1 = null;
	private boolean on_off=true;//开关控制
	/***
	 * 初始化
	 */
	public MoneyBoxExistProcuratorPanel(final PublicCashOpenBean transBean){
		
		this.cashBean = transBean;

		/* 显示时间倒计时 */
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	on_off=false;
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        }); 
		delayTimer.start(); 
		/* 倒计时打开语音 */
		voiceTimer = new Timer(voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	stopTimer(voiceTimer);
            	excuteVoice("voice/examine.wav");
            	
            }      
        });            
		voiceTimer.start(); 
		/* 标题信息 */
		JLabel titleLabel = new JLabel("请检查图片 ");
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 40));
		titleLabel.setForeground(Color.decode("#412174"));
		titleLabel.setBounds(0, 60, 1009, 60);
		this.showJpanel.add(titleLabel);
		
		
		
		
		//确认
		JLabel lblNewLabel = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		lblNewLabel.setBounds(890, 770, 150, 50);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				/* 处理下一页 */
				logger.info("点击确认按钮");	
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill(transBean);
			}

		});
		this.add(lblNewLabel);
		//上一步
		JLabel label = new JLabel(new ImageIcon("pic/preStep.png"));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");		
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				scanBill1(transBean); 
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
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				returnHome();
			}

		});
		label_1.setBounds(1258, 770, 150, 50);
		this.add(label_1);
		empower(transBean);
	}
	
	
	/***
	 * 下一步
	 */
	private void scanBill(PublicCashOpenBean transBean){
		//输入柜员号
		clearTimeText();
		openPanel(new MoneyBoxAdditionNumberPanel(transBean));
	}
	
	
	/***
	 * 上一步
	 */
	private void scanBill1(PublicCashOpenBean transBean){
		clearTimeText();
		//其他产品需要判断来返回对应页面
		if("0010".equals(transBean.getProductCode())){
			openPanel(new MoneyBoxInPutdepInfoPanel(transBean));
		}else if("QX".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringQXXLPanel(transBean));
		}else if("XF".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringXFYJYXLPanel(transBean));
		}else if("YX".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringYXCPanel(transBean));
		}else if("RY".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringRYCPanel(transBean));
		}else if("RJ".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringRYCJPanel(transBean));
		}else if("JJ".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringAXCPanel(transBean));
		}else if("LD".equals(transBean.getProductCode().substring(0, 2))){
			openPanel(new MoneyBoxEnteringLDCPanel(transBean));
		}else{
			on_off=true;
		}
		
		

	}
	
	
	
	
	
	

	/**
	 * 拍照
	 */
	public void empower(PublicCashOpenBean transBean){
	    /* 授权图片 */
		ImageIcon image22 = new ImageIcon(transBean.getBillPath_fc());
		image22.setImage(image22.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		
		//图片转换
		File tempFile =new File( Property.BILL_ID_SELF_JUST);           
		String fileName = tempFile.getName();  
		BmpUtil.bmpTojpg(Property.BILL_ID_SELF_JUST, "pic/"+fileName);
		logger.debug("pic/"+fileName);
		ImageIcon image23 = new ImageIcon("pic/"+fileName);
		image23.setImage(image23.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		
		//图片转换
		File tempFile1 =new File( Property.BILL_ID_AGENT_JUST.trim());           
		String fileName1 = tempFile1.getName();  
		BmpUtil.bmpTojpg(Property.BILL_ID_AGENT_JUST, "pic/"+fileName1);
		logger.debug("pic/"+fileName1);
		ImageIcon image24 = new ImageIcon("pic/"+fileName1);
		image24.setImage(image24.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		ImageIcon image25 = new ImageIcon(Property.ID_CARD_SELF);
		image25.setImage(image25.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
		final ImageIcon image31 = new ImageIcon(Property.CAMERA_PATH);
		image31.setImage(image31.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
		ImageIcon image26 = new ImageIcon(transBean.getBillPath_rc());
		image26.setImage(image26.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		
		//图片转换
		File tempFile3 =new File( Property.BILL_ID_SELF_AGAINST);           
		String fileName3 = tempFile3.getName();  
		BmpUtil.bmpTojpg(Property.BILL_ID_SELF_AGAINST, "pic/"+fileName3);
		logger.debug("pic/"+fileName3);
		ImageIcon image27 = new ImageIcon("pic/"+fileName3);
		image27.setImage(image27.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		
		//图片转换
		File tempFile4 =new File( Property.BILL_ID_AGENT_AGAINST.trim());           
		String fileName4 = tempFile4.getName();  
		BmpUtil.bmpTojpg(Property.BILL_ID_AGENT_AGAINST, "pic/"+fileName4);
		logger.debug("pic/"+fileName4);
		ImageIcon image28 = new ImageIcon("pic/"+fileName4);
		image28.setImage(image28.getImage().getScaledInstance(200, 110, Image.SCALE_DEFAULT));
		ImageIcon image29 = new ImageIcon(Property.ID_CARD_AGENT);
		image29.setImage(image29.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
		/**
		 * 主panel
		 * 
		 */
		panel = new JPanel();
		panel.setBounds(32, 125, 1000, 427);
		panel.setLayout(null);
		panel.setBackground(null);                     
		panel.setOpaque(false); 
		/**
		 * 第一页的panel
		 */
		empower_panel = new JPanel();
		empower_panel.setBounds(0, 0, 1000, 427);
		
		empower_panel.setLayout(null);
		//empower_panel.setPreferredSize(new Dimension(993, 403));//主要是这句代码，设置panel的首选大小，同时保证宽高大于JScrollPane的宽高，这样下面的JScrollPane才会出现滚动条
		empower_panel.setOpaque(false);
		/***
		 * 第二页的panel
		 */
		empower_panel_1 = new JPanel();
		empower_panel_1.setBounds(0, 0, 1000, 427);
		empower_panel_1.setLayout(null);
		empower_panel_1.setOpaque(false);
		panel.add(empower_panel);
		 //panel.add(empower_panel_1);
		this.showJpanel.add(panel);
		setVisible(true);
		
		image1 = new UtilImage("pic/"+fileName);
		image1.setBounds(140, 50, 270, 150);
		image1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image1.setIcon(image23);
		empower_panel.add(image1);
		image2 = new UtilImage("pic/"+fileName1);
		image2.setBounds(560, 50, 270, 150);
		image2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image2.setIcon(image24);
		empower_panel.add(image2);
		
		image3 = new UtilImage(Property.ID_CARD_SELF);
		image3.setSize(200, 150);
		image3.setLocation(182, 140);
		image3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image3.setIcon(image25);
		empower_panel_1.add(image3);
		image5 = new UtilImage("pic/"+fileName3);
		image5.setBounds(140, 230, 270, 150);
		image5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image5.setIcon(image27);
		empower_panel.add(image5);
		image6 = new UtilImage("pic/"+fileName4);
		image6.setBounds(560, 230, 270, 150);
		image6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image6.setIcon(image28);
		empower_panel.add(image6);
		
		image7 = new UtilImage(Property.ID_CARD_AGENT);
		image7.setSize(200, 150);
		image7.setLocation(600, 140);
		image7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		image7.setIcon(image29);
		empower_panel_1.add(image7);
		

		image9 = new UtilImages("pic/tghtj.png");		
		image9.setSize(124, 32);
		image9.setLocation(212, 0);
		image9.setIcon(new ImageIcon("pic/tghtj.png"));
		empower_panel.add(image9);
		image10 = new UtilImages("pic/fghy.png");
		image10.setSize(124, 32);
		image10.setLocation(630, 0);
		image10.setIcon(new ImageIcon("pic/fghy.png"));
		empower_panel.add(image10);
		
		image11 = new UtilImages("pic/me_id_check.png");
		image11.setSize(124, 32);
		image11.setLocation(212, 30);
		image11.setIcon(new ImageIcon("pic/me_id_check.png"));
		empower_panel_1.add(image11);
		
		image12 = new UtilImages("pic/agent_id_check.png");
		image12.setSize(124, 32);
		image12.setLocation(630, 30);
		image12.setIcon(new ImageIcon("pic/agent_id_check.png"));
		empower_panel_1.add(image12);		
		
		 
		   
	   lblNewLabel_4desc = new JLabel(transBean.getInspect());
	   lblNewLabel_4desc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
	   lblNewLabel_4desc.setBounds(182, 330, 200, 40);
	   lblNewLabel_4desc.setVerticalAlignment(SwingConstants.TOP);
	   lblNewLabel_4desc.setHorizontalAlignment(SwingConstants.LEFT);
	   setDesc("核查结果："+transBean.getAgentinspect(),lblNewLabel_4desc);
	   empower_panel_1.add(lblNewLabel_4desc);
		   
		   
	    
	    lblNewLabel_6desc = new JLabel();
	    lblNewLabel_6desc.setVerticalAlignment(SwingConstants.TOP);
	    lblNewLabel_6desc.setHorizontalAlignment(SwingConstants.LEFT);
	    lblNewLabel_6desc.setFont(new Font("微软雅黑", Font.PLAIN, 12));
	    lblNewLabel_6desc.setBounds(600, 330, 200, 40);
	    setDesc("核查结果："+transBean.getAgentinspect(),lblNewLabel_6desc);
	    empower_panel_1.add(lblNewLabel_6desc);	    
	    
	    /**
	     * 第一页的按钮
	     */
	    UtilButton btnNewButton_3 = new UtilButton("pic/nextbtn.png","pic/nextbtn.png");
	    btnNewButton_3.setBounds(948, 90, 40, 260);
	    empower_panel.add(btnNewButton_3);
	    
	    UtilButton btnNewButton_2 = new UtilButton("pic/stepbtn1.png","pic/stepbtn1.png");
	    btnNewButton_2.setBounds(0, 90, 40, 260);
	    empower_panel.add(btnNewButton_2);
	    /**
	     * 下一页的btn
	     */
	    UtilButton btnNewButton_1 = new UtilButton("pic/nextbtn1.png","pic/nextbtn1.png");
	    btnNewButton_1.setBounds(948, 90, 40, 260);
	    empower_panel_1.add(btnNewButton_1);	    
	    UtilButton btnNewButton = new UtilButton("pic/stepbtn.png","pic/stepbtn.png");
	    btnNewButton.setBounds(0, 90, 40, 260);
	    empower_panel_1.add(btnNewButton);
	    /***
	     * 对第一页监听
	     */
	    btnNewButton_3.addActionListener(new ActionListener() {//事件响应
			@Override
			public void actionPerformed(ActionEvent e) {		
				panel.removeAll();
				panel.add(empower_panel_1);
				panel.revalidate(); //重新验证
				panel.repaint(); //重画
			}
		});
	    /***
	     * 对下一页监听
	     */
	  
		    btnNewButton.addActionListener(new ActionListener() {//事件响应
		@Override
		public void actionPerformed(ActionEvent e) {		
			panel.removeAll();
			panel.add(empower_panel);
			panel.revalidate(); //重新验证
			panel.repaint(); //重画
		}
	});
		 
	}
	 
	
	/**
	 * 设置
	 */
	public void setDesc(String s,JLabel label){
		StringBuffer sbf = new StringBuffer();
		
		if(s == null ){
			return;
		}
		// 循环
		int cpCount = s.codePointCount(0, s.length());  
        for (int i = 0; i < cpCount; i++) {  
            int index = s.offsetByCodePoints(0, i);  
            int cp = s.codePointAt(index);  
            if (!Character.isSupplementaryCodePoint(cp)) {  
            	if(i == 0){
            		sbf.append("<html><p>");
            	}
            	sbf.append((char) cp);
            	if((i+1)%16 == 0){
            		sbf.append("</p><p>");
            	}
            	if(cpCount == (i+1)){
            		sbf.append("</p></html>");
            	}
            } else {  
            	if(i == 0){
            		sbf.append("<html><p>");
            	}
            	sbf.append((char) cp);
            	if((i+1)%10 == 0){
            		sbf.append("</p><p>");
            	}
            	if(cpCount == (i+1)){
            		sbf.append("</p></html>");
            	}
            }  
        }  
		label.setText(sbf.toString());
	}

}
