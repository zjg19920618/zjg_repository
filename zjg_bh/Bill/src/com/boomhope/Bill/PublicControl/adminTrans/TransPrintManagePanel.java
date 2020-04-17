package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;

/**
 * 
 * title:终端管理界面
 * @author ly
 * 2016年11月8日上午11:42:40
 */
public class TransPrintManagePanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransPrintManagePanel.class);
	private static final long serialVersionUID = 1L;
	private AdminBean adminBean;
	
	/***
	 * 初始化
	 */
	public TransPrintManagePanel(AdminBean AdminBean) {

		this.adminBean = AdminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_ADMIN_MANAGE;
		/* 标题信息 */
		JLabel label = new JLabel("终端管理");
		label.setBounds(425, 150, 159, 28);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		
		JPanel panel = new JPanel();
		panel.setBounds(305, 260, 397, 173);
		panel.setBackground(Color.WHITE);
		this.showJpanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("当前凭证号:");
		lblNewLabel.setBounds(56, 10, 156, 48);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("剩余存单数:");
		lblNewLabel_1.setBounds(56, 59, 156, 40);
		lblNewLabel_1.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		panel.add(lblNewLabel_1);
		
		if("".equals(this.adminBean.getNowBo()) && "".equals(adminBean.getNowNumber())){
			JLabel label_1 = new JLabel("0");
			label_1.setBounds(105, 15, 256, 38);
			label_1.setHorizontalAlignment(JLabel.CENTER);
			label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
			panel.add(label_1);
			
			JLabel label_2 = new JLabel("0 张");
			label_2.setBounds(144, 59, 200, 40);
			label_2.setHorizontalAlignment(JLabel.CENTER);
			label_2.setFont(new Font("微软雅黑", Font.BOLD, 18));
			panel.add(label_2);
		}else{
			JLabel label_1 = new JLabel(String.format("%08d",(Long.parseLong(this.adminBean.getNowBo()))));
			label_1.setBounds(105, 15, 256, 38);
			label_1.setHorizontalAlignment(JLabel.CENTER);
			label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
			panel.add(label_1);
			
			JLabel label_2 = new JLabel(String.format("%08d",(Long.parseLong(this.adminBean.getNowNumber())))+"张");
			label_2.setBounds(144, 59, 200, 40);
			label_2.setHorizontalAlignment(JLabel.CENTER);
			label_2.setFont(new Font("微软雅黑", Font.BOLD, 18));
			panel.add(label_2);
		}
	
		JButton button = new JButton(new ImageIcon("pic/lostPaper.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//处理缺纸处理
				PaperOut();
			}
		});
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		button.setOpaque(false);//设置控件是否透明，true为不透明，false为透明  
		button.setContentAreaFilled(false);//设置图片填满按钮所在的区域  
		button.setFocusPainted(true);//设置这个按钮是不是获得焦点  
		button.setBorderPainted(false);//设置是否绘制边框  
		button.setBorder(null);//设置边框  
		
		button.setBounds(33, 109, 150, 50);
		panel.add(button);
		
		JButton button_1 = new JButton(new ImageIcon("pic/changePassword.png"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//处理修改密码
				ChangePassword();
			}
		});
		button_1.setHorizontalTextPosition(SwingConstants.CENTER);
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		button_1.setOpaque(false);//设置控件是否透明，true为不透明，false为透明  
		button_1.setContentAreaFilled(false);//设置图片填满按钮所在的区域  
		button_1.setFocusPainted(true);//设置这个按钮是不是获得焦点  
		button_1.setBorderPainted(false);//设置是否绘制边框  
		button_1.setBorder(null);//设置边框  
		button_1.setBounds(214, 109, 150, 50);
		panel.add(button_1);
		
		//退出按钮
		JButton backbtn = new JButton(new ImageIcon("pic/newPic/exit.png"));
		backbtn.setBounds(1258, 770, 150, 50);
		add(backbtn);
		backbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				backStep();
			}
		});
	}
	
	/*
	 * 缺纸处理
	 */
	private void PaperOut(){
		this.openPanel(new TransPaperOutPanel(adminBean));
	}
	
	/*
	 * 修改密码
	 */
	private void ChangePassword(){
		openPanel(new TransPrintChangePassPanel(adminBean));
	}
	
	//退出
	private void backStep(){
		BaseContentPanel comp=(BaseContentPanel)this.getParent();
		comp.getManagerButton().setVisible(true);
		returnHome();
	}
	
}
