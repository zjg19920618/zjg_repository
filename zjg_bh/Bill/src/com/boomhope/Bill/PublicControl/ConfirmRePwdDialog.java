package com.boomhope.Bill.PublicControl;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.boomhope.Bill.Util.UtilButton;



/**
 * 选择确认弹框
 * @author hk
 */
@SuppressWarnings("serial")
public class ConfirmRePwdDialog extends JDialog {
	
	public JLabel label;
	public UtilButton YseButten;//是
	public UtilButton Nobutton;//否
	public UtilButton RePwdButton;//否
	public JLabel jlb;//背景
	 
	
	/**
	 * 
	 * @param frame 父架构框
	 * @param isModel 是否模式化
	 */
	
	public ConfirmRePwdDialog(JFrame frame,boolean isModel,String Msg){
		super(frame);
		initiDialog(Msg);
	}
	/**
	 * 初始化dialog
	 */
	public void initiDialog(String msg){
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
		b1.add(jlb);				
		
		
				
		//是
		YseButten = new UtilButton("pic/true.png","pic/true.png");
		YseButten.setSize(125, 35);
		YseButten.setLocation(56, 350);				
		YseButten.setVisible(true);
		b1.add(YseButten,JLayeredPane.MODAL_LAYER);	
		
		//否
		Nobutton = new UtilButton("pic/false.png","pic/false.png");
		Nobutton.setSize(125, 35);
		Nobutton.setLocation(237, 350);				
		Nobutton.setVisible(true);
		b1.add(Nobutton,JLayeredPane.MODAL_LAYER);		
		
		//否
		RePwdButton = new UtilButton("pic/rePwd.png","pic/rePwd.png");
		RePwdButton.setSize(125, 35);
		RePwdButton.setLocation(418, 350);				
		RePwdButton.setVisible(true);
		b1.add(RePwdButton,JLayeredPane.MODAL_LAYER);
						
		//信息提示标签
		label = new JLabel();
		b1.add(label,JLayeredPane.MODAL_LAYER);
		label.setText(setMsg(msg));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		label.setBounds(10, 66, 580,263);
		b1.setVisible(true);	
		
						
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
	public void showDialog(String msg){
		final ConfirmRePwdDialog dialog=this;
		label.setText(setMsg(msg));
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
	public void showDialog(){
		final ConfirmRePwdDialog dialog=this;
		//模式弹框必须在线程中打开负责会阻塞线程
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				dialog.setVisible(true);
			}			
		});
	}
	
	/**
	 * 设置信息换行
	 */
	public String setMsg(String Msg){
		String msg1 = "";
		if(Msg == null || ""==Msg){
			return msg1;
			
		}else{
			String tellInfo="";
			String yesDo="";
			String noDo="";
			tellInfo=Msg.substring(0,Msg.indexOf("是："));
			yesDo=Msg.substring(Msg.indexOf("是："),Msg.indexOf("否：")-1);
			noDo=Msg.substring(Msg.indexOf("否："),Msg.length()-1);
			if(tellInfo.length()<=18){
				msg1 = "<html><p style='margin:4px'>"+tellInfo+"</p>"
						+"<p style='margin:4px'>"+yesDo+"</p>"
						+"<p style='margin:4px'>"+noDo+"</p></html>";
			}else if(tellInfo.length()>18 && tellInfo.length()<=36){
				msg1 = "<html><p style='margin:4px'>"+tellInfo.substring(0,18)+"</p>"
						+"<p style='margin:4px'>"+tellInfo.substring(18)+"</p>"
						+"<p style='margin:4px'>"+yesDo+"</p>"
						+"<p style='margin:4px'>"+noDo+"</p></html>";
			}else if(tellInfo.length()>36 && tellInfo.length()<=54){
				msg1 = "<html><p style='margin:4px'>"+tellInfo.substring(0,18)+"</p>"
						+"<p style='margin:4px'>"+tellInfo.substring(18,36)+"</p>"
						+"<p style='margin:4px'>"+tellInfo.substring(36)+"</p>"
						+"<p style='margin:4px'>"+yesDo+"</p>"
						+"<p style='margin:4px'>"+noDo+"</p></html>";
			}
		}
		return msg1;
	}
	
	//测试
	public static void main(String[] args) throws InterruptedException {
		final JFrame frame=new JFrame();
		Dimension screeSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, (int)screeSize.getWidth(), (int)screeSize.getHeight());
		frame.setVisible(true);
		final ConfirmRePwdDialog dialog=new ConfirmRePwdDialog(frame, true,"你好！*******************************");
		dialog.showDialog("");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//		dialog.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				dialog.dispose();
//			}
//		});
		dialog.YseButten.addMouseListener(new MouseAdapter() {
			
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
