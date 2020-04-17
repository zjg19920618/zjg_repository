package com.boomhope.Bill.TransService.LostReport.Page.LostJpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintJobAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.Util.Property;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
/**
 * 打印申请书页面
 */
public class LostPrintAllPage extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LostSelectPrintOrFtpJpg.class);
	private	static ActiveXComponent word=null;
	private static Dispatch doc=null;
	private static String printName = null;// 打印机名             
	private static boolean printer=false;
	private JLabel	textLabel;
	private  JLabel  printLost;
	private JLabel tishi;
	private boolean on_off = true;// 用于控制页面跳转的开关
	public LostPrintAllPage(){
		/* 显示时间倒计时 */
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime * 1000,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						/* 倒计时结束退出交易 */
						clearTimeText();
						serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
					}
				});
		delayTimer.start();
		showPage();
	}
	public void showPage(){
		Property.initProperty();
		textLabel = new JLabel();
		textLabel.setBounds(0, 220, 1009, 90);
		textLabel.setFont(new Font("微软雅黑",Font.PLAIN,35));
		textLabel.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(textLabel);
		tishi = new JLabel("打印完成!");
		tishi.setVisible(false);
		tishi.setBounds(0, 270, 989, 55);
		tishi.setForeground(Color.RED);
		tishi.setFont(new Font("微软雅黑",Font.PLAIN,25));
		tishi.setHorizontalAlignment(SwingUtilities.CENTER);
		this.showJpanel.add(tishi);
		//打印按钮
		printLost = new JLabel(new ImageIcon("pic/newPic/printLost.jpg"));
		printLost.setBounds(350, 300,300, 200);
		printLost.addMouseListener(new MouseAdapter() {
		  public void mouseReleased(MouseEvent e) {
				logger.info("点击打印申请书按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				Print();	
				}
		  });
		this.showJpanel.add(printLost);
		// 退出
		JLabel exit = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		exit.setBounds(1258, 770, 150, 50);
		exit.setVisible(true);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				clearTimeText();
				stopTimer(voiceTimer);// 关闭语音
				closeVoice();// 关闭语音流
				returnHome();
			}
		});
		add(exit);
		int str = FileLength();
		if (str == 0) {
			textLabel.setText("没有可打印的申请书！");
			printLost.setVisible(false);
		} else {
			textLabel.setText("剩余可打印文件数：" + str);
			printLost.setVisible(true);
		}
	}
	/**
	 * 统计打印申请书失败数量
	 */
	public  int FileLength(){
		File file =new File(Property.bill_print_path);
		if(!file.isDirectory()){
			logger.info("此文件夹不存在--》"+file);
			return 0;
		}
		File[] files=file.listFiles();
		return files.length;
	}
	/**
	 * 开始打印保存的申请书
	 */
	public void Print(){
		 getPrinterName();//获取可用打印机
		if(!printer){
			textLabel.setText("获取打印机异常。");
			printLost.setVisible(false);	
		}else{
			textLabel.setText("正在打印申请书，请稍后......");
			printLost.setVisible(false);	
			new Thread(new Runnable() {
				@Override
				public void run() {	
				try{
					int success=0;//打印成功
					int  last=0;//打印失败
					File file =new File(Property.bill_print_path);		
					File[] files=file.listFiles();
					for(int i=0; i<files.length;i++){
						File f = files[i];
						boolean result=PrintDocx(f.getPath());
						if(!result){
							logger.info("打印失败："+f.getPath());	
							last++;
						}
						success++;
						textLabel.setText("打印成功："+success+"，打印失败："+last+"。");
					}
					tishi.setVisible(true);
					if(last!=0){
						printLost.setVisible(true);
					}
				  }catch(Exception e){
					  logger.error("打印申请书程序异常"+e);
					  textLabel.setText("打印申请书程序异常，请稍后重试...");
				  }
				}
			}).start();	
	  }
	}
	/**
	 * 加载要打印的word
	 * @param docPath
	 * @return
	 */
	public boolean PrintDocx(String docPath){
		try{
		    ComThread.InitSTA();
		    word=new ActiveXComponent("Word.Application");
		    Dispatch.put(word, "Visible", new Variant(false));
		    // 设置打印机
		    word.setProperty("ActivePrinter", new Variant(printName));
		    Dispatch docs = word.getProperty("Documents").toDispatch();
		    doc = Dispatch.call(docs, "Open",docPath).toDispatch();
		
			Dispatch.call(doc, "PrintOut");//打印  
		}catch(Exception e){
			logger.error("打印申请书程序异常："+e);
			return false;
		}finally{
			try{
				 Dispatch.call(doc, "Close",new Variant(0));
	             Dispatch.call(word, "Quit");
	             ComThread.Release();
			}catch(Exception e){
				logger.error("关闭文档失败："+e);
			}
		}
		return true;
	}
	/**
	 * 获取打印机名称
	 * 
	 * @param productedCode
	 * @param transBean
	 * @return
	 */
	private static boolean  getPrinterName() {
		logger.error("获取可用打印机");
		HashPrintJobAttributeSet hpjas = new HashPrintJobAttributeSet();
		DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, hpjas);

		for (int i = 0; i < ps.length; i++) {
			if (ps[i].getName().contains("FX DocuPrint P228 db")) {
				printName = "FX DocuPrint P228 db";
				printer=true;
			} else if (ps[i].getName().contains("Brother HL-2240 series")) {
				printName = "Brother HL-2240 series";
				printer=true;
			}else if (ps[i].getName().contains("Brother HL-2260 Printer")) {
				   printName = "Brother HL-2260 Printer";
				   printer =true;
			   }
		}
		return printer;

	}
	
}
