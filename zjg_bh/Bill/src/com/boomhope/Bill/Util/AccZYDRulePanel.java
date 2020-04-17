package com.boomhope.Bill.Util;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

public class AccZYDRulePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccZYDRulePanel.class);

	boolean on_off=true;
	JScrollPane jsp;
	
	public JScrollPane getJsp() {
		return jsp;
	}

	public AccZYDRulePanel(JPanel showJpanel,String propertiesPath) {
		
		jsp = new JScrollPane();
		jsp.setBounds(10, 70, showJpanel.getWidth()-20, showJpanel.getHeight()-160);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		showJpanel.add(jsp);
		
		JLabel labelInfo =new JLabel();
		labelInfo.setFont(new Font("微软雅黑",Font.PLAIN,20));
		labelInfo.setHorizontalAlignment(SwingUtilities.LEFT);
		labelInfo.setVerticalAlignment(SwingUtilities.TOP);
		jsp.setViewportView(labelInfo);
		
		BufferedReader br=null;
		try {
			File file = new File(propertiesPath);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String readerStr=null;
			StringBuffer sb = new StringBuffer();
			sb.append("<html>");
			int line=1;
			int StringLengt=48;
			while((readerStr = br.readLine())!=null){
				if(line==1){
					line++;
					continue;
				}
//				if(readerStr.length()/StringLengt>0){
//					int account =readerStr.length()%StringLengt>0?readerStr.length()/StringLengt+1:readerStr.length()/StringLengt;
//					for(int i=0;i<account;i++){
//						if(i==0){
//							sb.append("<p style='margin:4px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+readerStr.substring(0,(i+1)*StringLengt-2)+"</p>");
//						}else if(i!=0 && i!=account-1){
//							sb.append("<p style='margin:4px'>"+readerStr.substring((i-1)*StringLengt+StringLengt-2,(i+1)*StringLengt-2)+"</p>");
//						}else{
//							sb.append("<p style='margin:4px'>"+readerStr.substring((i-1)*StringLengt+StringLengt-2)+"</p>");
//						}
//					}
//				}else{
//					sb.append("<p style='margin:4px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+readerStr+"</p>");
//				}
				sb.append("<p style='margin:4px'>"+readerStr+"</p>");
				line++;
			}
			sb.append("</html>");
			br.close();
			labelInfo.setText(sb.toString());
			
		} catch (Exception e) {
			logger.error("加载增益豆赠送规则失败："+e);
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (Exception e2) {
					logger.error("关闭读取增益豆规则文件的流失败："+e2);
				}
			}
		}
		
	}
}



















