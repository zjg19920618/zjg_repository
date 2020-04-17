package com.boomhope.Bill.TransService.BillChOpen.BillChangeOpen.HaveAcc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.BillChOpen.Bean.AccNoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.BillChangeInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.EAccInfoBean;
import com.boomhope.Bill.TransService.BillChOpen.Bean.SubAccInfoBean;
import com.boomhope.Bill.Util.DateTool;

public class BillChangeOpenSVJN  extends BaseTransPanelNew{
	/**
	 * 客户流水信息 确认
	 */
	private static final long serialVersionUID = 1L;
	private static final int interge = 0;
	static Logger logger = Logger.getLogger(BillChangeOpenSVJN.class);
	private JLabel submitBtn;
	private JLabel backBtn;
	private JLabel utilButton;
	JLabel[] labels=null;
	JPanel panel;
	int page=1;//第一页
	private JLabel nextBtn;
	private JPanel[] panels=new JPanel[3];
	//普通账户
	List<BillChangeInfoBean> infos=null;
	//电子账户
	List<EAccInfoBean>       info=null;
	//卡下子账号
	List<SubAccInfoBean>     inf=null;

	List<AccNoBean> list=new ArrayList<AccNoBean>();
	private Component comp;
	@SuppressWarnings("unchecked")
	public BillChangeOpenSVJN(){
		comp=this;
		/* 显示时间倒计时 */
		this.showTimeText(delaySecondMaxTime);
		delayTimer = new Timer(delaySecondMaxTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	//流程
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		
		
		infos = (List<BillChangeInfoBean>)bcOpenBean.getAccMap().get("AccMsgList");//普通账户
		info=(List<EAccInfoBean>)bcOpenBean.getDZAccMap().get("DZMsgList");//电子账户
		inf=(List<SubAccInfoBean>)bcOpenBean.getSubMap().get("SubMsgList");//卡下子账户
		
		
		if(infos!=null && infos.size()>0){	
			for(int j=0;j<infos.size();j++){				
				AccNoBean acc=new AccNoBean();
				acc.setAccNo(infos.get(j).getAcct_no());
				acc.setProcode(infos.get(j).getProduct_code());
				acc.setEnddata(infos.get(j).getDue_date());
				acc.setDepmter(infos.get(j).getDep_term());
				acc.setAmt(infos.get(j).getUn_use_amt());
				acc.setCertNo(infos.get(j).getCert_no());
				acc.setOpendata(infos.get(j).getOpen_date());
				acc.setFlag(false);
				list.add(acc);				
					
			}
		}
		if(info!=null && info.size()>0){
			for(int k=0;k<info.size();k++){
			
				AccNoBean acc=new AccNoBean();
				acc.setAccNo(info.get(k).geteCardNo()+"-"+info.get(k).geteSubAccNo());
				acc.setProcode(info.get(k).geteProductCode());
				acc.setEnddata(info.get(k).geteEndDate());
				acc.setDepmter(info.get(k).geteDepTerm());
				acc.setAmt(info.get(k).geteOpenATM());
				acc.setCertNo(info.get(k).geteCertNo());
				acc.setOpendata(info.get(k).geteOpenDate());
				acc.setFlag(false);
				list.add(acc);				
		
			}
		}
		if(inf !=null && inf.size()>0){
			for(int i=0;i<inf.size();i++){
	
				AccNoBean acc=new AccNoBean();
				acc.setAccNo(inf.get(i).getAccNo()+"-"+inf.get(i).getSubAccNo());
				acc.setProcode(inf.get(i).getProductCode());
				acc.setEnddata(inf.get(i).getEndIntDate());
				acc.setDepmter(inf.get(i).getDepTerm());
				acc.setAmt(inf.get(i).getOpenATM());
				acc.setCertNo(inf.get(i).getBill());
				acc.setOpendata(inf.get(i).getOpenDate());
				acc.setFlag(false);
				list.add(acc);				
				
				
			}
		}
		
		
		
			if(list.size()>0&&list!=null){
				initPanel1();
				showInfos(page,list); 
			}else{
				//可选择业务信息
				JLabel label = new JLabel("该卡未查询到当日开户记录");
				label.setFont(new Font("微软雅黑",Font.PLAIN,30));
				label.setHorizontalAlignment(0);
				label.setBounds(0,180,989,40);
				this.showJpanel.add(label);
				
				//退出
				JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
				label_1.setBounds(1258, 770, 150, 50);
				label_1.addMouseListener(new MouseAdapter(){
					public void mouseReleased(MouseEvent e){
						closeVoice();
						clearTimeText();
						stopTimer(voiceTimer);
						billHKExit();
					}
				});		
				add(label_1);
				
				
			}
		
			
			
			
	}
	private void initPanel1() {
		labels = new JLabel[list.size()];
		panel=new JPanel();
		panel.setBounds(10,140,989,450);
		panel.setBackground(Color.decode("#f7f2ff"));
		panel.setLayout(null);
		this.showJpanel.add(panel);
		//可选择业务信息
		JLabel label = new JLabel("可选择业务信息");
		label.setFont(new Font("微软雅黑",Font.BOLD,40));
		label.setHorizontalAlignment(0);
		label.setBounds(0,60,989,40);
		this.showJpanel.add(label);
		JLabel labelLine_1=new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		labelLine_1.setBounds(0,143,989,10);
		labelLine_1.setFont(new Font("", Font.PLAIN, 20));
		panel.add(labelLine_1);
		
		JLabel label_10 = new JLabel("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -- - - --");
		label_10.setFont(new Font("Dialog", Font.PLAIN, 20));
		label_10.setBounds(0, 296, 989, 10);
		panel.add(label_10);
		
		// 上一页
		submitBtn = new JLabel(new ImageIcon("pic/back_page.png"));
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(890, 770);
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
					upStep();
					showInfos(page,list);
					
				}
		});
		add(submitBtn);
		// 下一页
		nextBtn= new JLabel(new ImageIcon("pic/next_page.png"));
		nextBtn.setSize(150, 50);
		nextBtn.setLocation(1075, 770);
		nextBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nextStep();
				showInfos(page,list);
			}	
		});
		add(nextBtn);

		// 确认
		backBtn = new JLabel(new ImageIcon("pic/newPic/confirm.png"));
		backBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// 清空倒计时
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				confim();
			}
		});
		backBtn.setSize(150, 50);
		backBtn.setLocation(705, 770);
		add(backBtn);
		
		// 返回
		utilButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		utilButton.setBounds(1258, 770, 150, 50);
		utilButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// 清空倒计时
				
				closeVoice();
				clearTimeText();
				stopTimer(voiceTimer);
				billHKExit();
			}
		});
		add(utilButton);
	}
	public void showInfos(final int page,final List<AccNoBean> list) {
		for (int i = 0; i < panels.length; i++) {
			if (panels[i] != null) {
				panel.remove(panels[i]);
			}
		}
		panel.repaint();
		for (int i = (page-1)*3; i <list.size()&&i<page*3 ; i++) {
			
			final int a=i;
			int x=0;
			int y=153*i;
			if(i-(page-1)*3<1){
				x=0;
				y=0;
			}else if(i-(page-1)*3<2){
				x=0;
				y=153;
			}else{
				x=0;
				y=153*2;
			}
		
			
			JPanel panel1=new JPanel();
			panel1.setBounds(x, y, 989, 143);
			panel1.setLayout(null);
			panel1.setBackground(Color.decode("#f7f2ff"));
			panels[i-(page-1)*3]=panel1;
			panel.add(panel1);
			
			JLabel label_1 = new JLabel("账     号：");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_1.setBounds(80, 10, 130, 30);
			panel1.add(label_1);
			
			JLabel lblNewLabel = new JLabel("金     额：");
			lblNewLabel.setBounds(80, 35, 130, 30);
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
			panel1.add(lblNewLabel);

			
			JLabel label_2 = new JLabel("存     期：");
			label_2.setHorizontalAlignment(SwingConstants.RIGHT);
			label_2.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_2.setBounds(80, 60, 130, 30);
			panel1.add(label_2);

			JLabel label_3 = new JLabel("到 期 日：");
			label_3.setHorizontalAlignment(SwingConstants.RIGHT);
			label_3.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_3.setBounds(80, 88, 130, 30);
			panel1.add(label_3);
			JLabel label_4 = new JLabel("凭 证 号：");
			label_4.setHorizontalAlignment(SwingConstants.RIGHT);
			label_4.setFont(new Font("微软雅黑", Font.BOLD, 20));
			label_4.setBounds(80, 113, 130, 30);
			panel1.add(label_4);
			
			//账号		
		    JLabel lblNewLabel_2 = new JLabel(list.get(i).getAccNo());
		    lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		    lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(209, 10, 840, 30);
			panel1.add(lblNewLabel_2);	
					
			// 金额
			JLabel lblNewLabel_1 = new JLabel(list.get(i).getAmt());
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblNewLabel_1.setBounds(209, 35, 840, 30);
			panel1.add(lblNewLabel_1);

			// 存期
			String depterm=list.get(i).getProcode();//产品代码
			String depter=list.get(i).getDepmter();//存期
			String opendata=list.get(i).getOpendata();//开户日
			String enddata=list.get(i).getEnddata();//到期日
			String depm="";
			try{
			if(depterm.startsWith("RJ")){
				/* 存期时间 */
				int date = 0;

				String openTime = opendata.replaceAll("/","").trim();
				String endTime = enddata.replaceAll("/","").trim();
				
				SimpleDateFormat fommter = new SimpleDateFormat("yyyyMMdd");
				
				Date a1 = fommter.parse(openTime);
				Date b1 = fommter.parse(endTime);
				System.out.println(a1);
				System.out.println(b1);
				date = DateTool.differentsDays(a1, b1);
				depm=String.valueOf(date)+"天";//积享存、如意存存期(天数)
			}else{
				//将存期的数字变为中文
				String months="";
				// 当存期的单位是月时加“个”如：3个月，单位是年时则没有如1年
				String measure = "";
				if(depter.contains("M") || depter.contains("Y")){
					if("01".equals(depter.substring(0, 2))){
						months="一";
					}else if("02".equals(depter.substring(0, 2))){
						months="二";
					}else if("03".equals(depter.substring(0, 2))){
						months="三";
					}else if("05".equals(depter.substring(0, 2))){
						months="五";
					}else if("06".equals(depter.substring(0, 2))){
						months="六";
					}else{
						months=depter.substring(0, 2);
					}
					
					if (depter.indexOf("M") != -1) {
						measure = "个月";
					} else if (depter.indexOf("Y") != -1) {
						measure = "年";
					}
					
					
			      }else{
					
					months=depter;
					measure="天";
				  
			      }
				 depm=months+measure;
		    }
			}catch(Exception e){
				logger.error("存单换开存期获取失败"+e);
				
			}
			JLabel label_8 = new JLabel(depm);
			label_8.setHorizontalAlignment(SwingConstants.LEFT);
			label_8.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			label_8.setBounds(209, 60, 840, 30);
			panel1.add(label_8);
			
			// 到期日
			JLabel lblsadad = new JLabel(list.get(i).getEnddata());
			lblsadad.setHorizontalAlignment(SwingConstants.LEFT);
			lblsadad.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblsadad.setBounds(209, 88, 840, 30);
			panel1.add(lblsadad);	
			// 凭证号
			JLabel lblsadad1 = new JLabel(list.get(i).getCertNo());
			lblsadad1.setHorizontalAlignment(SwingConstants.LEFT);
			lblsadad1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
			lblsadad1.setBounds(209, 113, 840, 30);
			panel1.add(lblsadad1);
			
			labels[i]=new JLabel();
			labels[i].setBounds(800, 60, 30,30);
			if(!list.get(i).isFlag()){
			    labels[i].setIcon(new ImageIcon("pic/wxz.png"));
			}else{
				 labels[i].setIcon(new ImageIcon("pic/selected.png"));
			}
			labels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					for(int l=0;l<labels.length;l++){
						if (labels[a].equals(labels[l])) {
						    if(!list.get(a).isFlag()){		
					           list.get(a).setFlag(true);						       
					           bcOpenBean.setSetFlag(a+"");						      
					           labels[l].setIcon(new ImageIcon("pic/selected.png"));
	
						      }else{
						    	  bcOpenBean.setSetFlag("");
						    	  list.get(a).setFlag(false);						        	    
						    	  labels[l].setIcon(new ImageIcon("pic/wxz.png"));
					    	}
						    
				    	}else if(labels[l]!=null && !labels[a].equals(labels[l])){
				    		 list.get(l).setFlag(false);						        	    
				    		 if(!list.get(l).isFlag()){
				 			    labels[l].setIcon(new ImageIcon("pic/wxz.png"));
				 			}else{
				 				 labels[l].setIcon(new ImageIcon("pic/selected.png"));
				 			};
				    	}
					}
					
				   }
				});
			panel1.add(labels[i]);
			
		
		}			
		
		}
		/**
		 * 上一页
		 * @return 
		 */
		public void  upStep(){
			
			if(page<=1){
				openMistakeDialog("已经没有更多的信息了");
				return;
			}
			page--;
			showInfos(page, list);
		
		}
		
		/**
		 * 下一页
		 */
		public void nextStep(){
			
			int pages=list.size()%3==0?list.size()/3:list.size()/3+1;
			if(page>=pages){
				openMistakeDialog("已经没有更多信息了");
				return;
			}
			page++;
			showInfos(page, list);


		}
		/**
		 * 确定
		 */
		public void confim(){
			
			String flag=bcOpenBean.getSetFlag();
			if("".equals(flag)||flag==null){
				openMistakeDialog("请选择至少一条可换开记录");
				return;
			}
			
		    bcOpenBean.setAccNo(list.get(Integer.parseInt(flag)).getAccNo().trim());
			bcOpenBean.setBillNo(list.get(Integer.parseInt(flag)).getCertNo().trim());
			
			     String accno=bcOpenBean.getAccNo();
			     //判断识别为账号/卡号-子账号			
				
				logger.debug("判断账号："+accno);
				
				if(accno.contains("-") && "0008".equals(accno.substring(6,10))){
					
					bcOpenBean.setSubAccNoCancel("3");
					logger.debug("识别账号为电子子账号，匹配成功");
					String[] split = accno.split("-");
					bcOpenBean.setAccNo(accno);//账号-子账号
					bcOpenBean.setSubCardNo(split[0]);//账号
					bcOpenBean.setSubAccNo(split[1]);//子账号
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					
			    	//进入存单信息查询页
			    	openPanel(new BillChangeOpenCheckTFSecondPanel());
				}else if(!"0008".equals(accno.substring(6,10)) && accno.contains("-")){//识别为卡下子账号
					
					logger.debug("识别账号为卡下子账号，匹配成功");
					bcOpenBean.setSubAccNoCancel("0");//卡下子账户标识
					bcOpenBean.setBillType("102");//凭证种类
					String[] split = accno.split("-");
					bcOpenBean.setAccNo(accno);//账号-子账号
					bcOpenBean.setSubCardNo(split[0]);//账号
					bcOpenBean.setSubAccNo(split[1]);//子账号
					
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					
			    	//进入存单信息查询页
			    	openPanel(new BillChangeOpenCheckTFSecondPanel());
				}else	if(!accno.contains("-")){//识别为普通账号
					//选择普通账户
					
					logger.debug("识别账号为普通账号，匹配成功");
					bcOpenBean.setSubAccNoCancel("1");//普通账号标识
					bcOpenBean.setAccNo(accno);//账号
					clearTimeText();//清空倒计时
					stopTimer(voiceTimer);//关语音
			    	closeVoice();//关语音流
					
			    	//进入存单信息查询页
			    	openPanel(new BillChangeOpenCheckTFSecondPanel());
				}else{
					
					logger.info("选择账号非电子账户，卡子子账户、普通账号，匹配失败");					
					openMistakeDialog("抱歉，选择账号非电子账户，卡子子账户、普通账号，匹配失败");
					return;
				} 
				
				
		}


}
