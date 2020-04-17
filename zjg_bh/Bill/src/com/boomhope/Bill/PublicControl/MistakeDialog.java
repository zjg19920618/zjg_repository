package com.boomhope.Bill.PublicControl;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.boomhope.Bill.Util.UtilButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


/**
 * 出现误操作提示弹框
 * @author wang.xm
 *
 */
@SuppressWarnings("serial")
public class MistakeDialog extends JDialog {
	
	private JLabel label;
	public UtilButton ExitButton;//退出
	public JLabel jlb;//背景
	
	/**
	 * 
	 * @param frame 父架构框
	 * @param isModel 是否模式化
	 */
	
	public MistakeDialog(JFrame frame,boolean isModel,String errorMsg){
		super(frame);
		initiDialog(errorMsg);
	}
	/**
	 * 初始化dialog
	 */
	public void initiDialog(String errorMsg){
		//获取屏幕大小
		Dimension screeSize=Toolkit.getDefaultToolkit().getScreenSize();
		//设置弹框未知大小
		this.setBounds(600,245, 600, 420);
		this.setModal(true);
		this.setUndecorated(true);
		
		//设置中间容器
		JLayeredPane b1 =new JLayeredPane();
		b1.setLayout(null);
		this.getContentPane().add(b1);
		
		//设置背景图
		jlb = new JLabel();
		jlb.setLocation(0, 0);
		ImageIcon image = new ImageIcon("pic/dialog.png");
		image.setImage(image.getImage().getScaledInstance(600,420,Image.SCALE_DEFAULT ));
		jlb.setIcon(image);
		jlb.setSize(600,420);
		jlb.setVisible(true);
		
		
				
		//确认错误信息退出按钮
		ExitButton = new UtilButton("pic/quit1.png","pic/quit.png");
		ExitButton.setSize(125, 35);
		ExitButton.setLocation(410, 350);				
		ExitButton.setVisible(true);
		b1.add(ExitButton,JLayeredPane.MODAL_LAYER);				
						
		//错误信息提示标签
		label = new JLabel();
		label.setForeground(Color.RED);
		b1.add(label,JLayeredPane.MODAL_LAYER);
		label.setText(setErrmsg(errorMsg));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label.setBounds(10, 22, 580,255);
		b1.setVisible(true);
		
		b1.add(jlb);				
						
		ExitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}

		});		
		
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
		final MistakeDialog dialog=this;
		//模式弹框必须在线程中打开负责会阻塞线程
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				dialog.setVisible(true);
			}			
		});
	}
	
	/**
	 * 显示模式弹框
	 */
	public void showDialog(String errorMsg){
		final MistakeDialog dialog=this;
		label.setText(setErrmsg(errorMsg));
		//模式弹框必须在线程中打开负责会阻塞线程
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				dialog.setVisible(true);
			}			
		});
	}
	
	/**
	 * 设置错误信息换行
	 */
	public String setErrmsg(String error){
		String msg = "";
		if(error == null || ""==error){
			return msg;
			
		}else if(error.length()<=18){
			msg = "<html><p style='margin:4px'>"+error+"</p></html>";
		}else if(error.length()>18 && error.length()<=36){
			msg = "<html><p style='margin:4px'>"+error.substring(0, 18)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(18, error.length())+"</p></html>";
		}else if(error.length()>36 && error.length()<=54){
			msg = "<html><p style='margin:4px'>"+error.substring(0, 18)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(18, 36)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(36, error.length())+"</p></html>";
		}else if(error.length()>54 && error.length()<=72){
			msg = "<html><p style='margin:4px'>"+error.substring(0, 18)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(18, 36)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(36, 54)+"</p>"
					+ "<p style='margin:4px'>"+error.substring(54, error.length())+"</p></html>";
		}
		return msg;
	}
	
	//测试
	public static void main(String[] args) throws InterruptedException {
		final JFrame frame=new JFrame();
		Dimension screeSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)screeSize.getWidth(), (int)screeSize.getHeight());
		frame.setVisible(true);
		final MistakeDialog dialog=new MistakeDialog(frame, true,"我我我我我我我我");
		dialog.showDialog();
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog.dispose();
			}
		});
		dialog.ExitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dialog.dispose();
			}

		});
		Thread.sleep(3000);
//		dialog.disposeDialog();
//		dialog.setVisible(false);
	}
}
