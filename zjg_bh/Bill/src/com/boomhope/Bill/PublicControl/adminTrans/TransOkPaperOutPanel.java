package com.boomhope.Bill.PublicControl.adminTrans;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseContentPanel;
import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Public.GlobalPanelFlag;
import com.boomhope.Bill.PublicControl.adminTrans.Bean.AdminBean;

/**
 * 
 * title:确认凭证信息页
 * @author ly
 * 2016年11月8日下午2:17:44
 */
public class TransOkPaperOutPanel extends BaseTransPanelNew{
	static Logger logger = Logger.getLogger(TransOkPaperOutPanel.class);
	private static final long serialVersionUID = 1L;
	
	private AdminBean adminBeans;
	

	/***
	 * 初始化
	 */
	public TransOkPaperOutPanel(AdminBean adminBean) {

		this.adminBeans = adminBean;
		GlobalPanelFlag.CurrentFlag = GlobalPanelFlag.PRINT_OK_PAPER_OUT;
	
		/* 标题信息 */
		JLabel label = new JLabel("请再次确认凭证号");
		label.setBounds(370, 150, 240, 37);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		this.showJpanel.add(label);
		
		JLabel label_1 = new JLabel("起始凭证号：");
		label_1.setBounds(375, 230, 108, 37);
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_1);
		
		JLabel lblNewLabel = new JLabel("终止凭证号：");
		lblNewLabel.setBounds(375, 290, 108, 30);
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(lblNewLabel);
		
		JButton button_1 = new JButton(new ImageIcon("pic/newPic/confirm.png"));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//返回广告页
				first();
			}
		});
		button_1.setBounds(1258, 770, 150, 50);
		add(button_1);
		
		JLabel label_3 = new JLabel(adminBeans.getStartBo());
		label_3.setBounds(530, 230, 212, 44);
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_3);
		
		JLabel label_4 = new JLabel(adminBeans.getEndBo());
		label_4.setBounds(530, 290, 188, 41);
		label_4.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_4);
		
		Integer end = Integer.valueOf(adminBeans.getEndBo());
		Integer start = Integer.valueOf(adminBeans.getStartBo());
		String count = String.valueOf(end-start+1);
		
		JLabel label_5 = new JLabel("总计：");
		label_5.setBounds(430, 350, 69, 50);
		label_5.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_5);
		
		JLabel label_2 = new JLabel(count+"张");
		label_2.setBounds(490, 350, 201, 51);
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 18));
		this.showJpanel.add(label_2);
	}
	
	//确认
	public void first(){
		BaseContentPanel comp=(BaseContentPanel)this.getParent();
		comp.getManagerButton().setVisible(true);
		returnHome();
	}
	
}
