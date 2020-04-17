package com.boomhope.Bill.PublicControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;



/**
 * 数据交互等待弹框
 * @author wang.xm
 *
 */
@SuppressWarnings("serial")
public class ProssDialog extends JDialog {
	
	Logger logger = Logger.getLogger(ProssDialog.class);
	
	/**
	 * 
	 * @param frame 父架构框
	 * @param isModel 是否模式化
	 */
	
	public ProssDialog(JFrame frame,boolean isModel){
		super(frame);
		initiDialog();
	}
	/**
	 * 初始化dialog
	 */
	public void initiDialog(){
		//获取屏幕大小
		Dimension screeSize=Toolkit.getDefaultToolkit().getScreenSize();
		//设置弹框未知大小
		this.setBounds((int)(screeSize.getWidth()/2)-15, (int)(screeSize.getHeight()/2)-145, 400, 300);
		//一下几步是设置Jdialog框背景为透明
		this.setUndecorated(true);
		this.getRootPane().setOpaque(false);
		Color c=new Color(0, 0, 0, 0);//无颜色
		this.getContentPane().setBackground(c);
		this.setBackground(c);
		//等待图标
		JLabel billImage = new JLabel(new ImageIcon("pic/processing.gif"));
		add(billImage);
		this.setModal(true);
	}
	/**
	 * 关闭弹框
	 */
	public void disposeDialog(){
		this.dispose();
	}
	
	/**
	 * 显示模式弹框
	 */
	public void showDialog(){
		final ProssDialog dialog=this;
		//模式弹框必须在线程中打开负责会阻塞线程
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				dialog.setVisible(true);
			}
		});
	}
	
	//测试
	public static void main(String[] args) throws Exception {
		final JFrame frame=new JFrame();
		Dimension screeSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)screeSize.getWidth(), (int)screeSize.getHeight());
		frame.setVisible(true);
		ProssDialog dialog=new ProssDialog(frame, true);
		dialog.showDialog();
//		BaseTransPanel base=new BaseTransPanel();
//		base.waitOtherThread();
		dialog.disposeDialog();
	}
}
