package com.boomhope.Bill.Framework;

import java.awt.EventQueue;
import javax.swing.JFrame;

import com.boomhope.Bill.Public.GlobalParameter;
import com.boomhope.Bill.Util.Property;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/***
 * 工程主窗体
 * @author shaopeng
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(MainFrame.class);
	
	public static  MainFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// 打开窗体
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		// 加载日志
		PropertyConfigurator.configure( "config\\log4j.properties" );
		// 加载配置
		Property.initProperty();

		getContentPane().setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(GlobalParameter.WINDOW_WIDTH, GlobalParameter.WINDOW_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体关闭时的操作 退出程序
		this.setUndecorated(true);

        getContentPane().add(new BaseContentPanel());
        mainFrame=this;
        // 设备状态上送
     //   timer(); 
        
	}
	
}
