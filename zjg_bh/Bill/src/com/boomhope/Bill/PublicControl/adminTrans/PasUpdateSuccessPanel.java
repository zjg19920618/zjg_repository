package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;

/**
 * 密码修改正确页面
 * @author gyw
 *
 */
public class PasUpdateSuccessPanel extends BaseTransPanelNew{
	//final int delaySecond = 60;
	JLabel label = null;
	private static final long serialVersionUID = 1L;
	private AdminBean adminBean;
	
	public PasUpdateSuccessPanel(AdminBean AdminBean) {
		
		this.adminBean = AdminBean;
		
		/* 警告图标 */
		JLabel billImage = new JLabel();
		billImage.setIcon(new ImageIcon("pic/ok.png"));
		billImage.setIconTextGap(6);
		billImage.setBounds(370, 250, 66, 67);
		this.showJpanel.add(billImage);
		
		JLabel lblNewLabel = new JLabel("密码已修改");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblNewLabel.setBounds(470, 250, 347, 67);
		this.showJpanel.add(lblNewLabel);
		
		/* 上一步 */
		JButton backButton = new JButton(new ImageIcon("pic/preStep.png"));
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nextStep();
			}
		});
		backButton.setBounds(1075, 770, 150, 50);
		add(backButton);
		
		
		JButton button = new JButton(new ImageIcon("pic/newPic/exit.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scanBill1();
			}
		});
		button.setBounds(1258, 770, 150, 50);
		add(button);
	}
	
	
	/**
	 * 上一步
	 */
	public void nextStep(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				openPanel(new TransPrintManagePanel(adminBean));
			}
		});
	}
	
	/***
	 * 返回首页
	 */
	private void scanBill1() {
		BaseContentPanel comp=(BaseContentPanel)this.getParent();
		comp.getManagerButton().setVisible(true);
		returnHome();
	}
	
}
