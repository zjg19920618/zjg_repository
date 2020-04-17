package com.boomhope.Bill.TransService.LostReport.Page.Lost;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.LostReport.Bean.AccCancelProFileBean;
/**
 * 销户成功
 * @author hao
 *
 */
public class LostAccCancelSuccessPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(LostAccCancelSuccessPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel accAmount = null;//本金
	private JLabel accAmount1 = null;
	private JLabel realAmt = null;//实付利息
	private JLabel realAmt1 = null;
	private JLabel tdErrMsg = null;//唐豆收回错误信息
	private JLabel tdErrMsg1 = null;
	private JLabel ptErrMsg = null;//凭条打印错误信息
	private JLabel ptErrMsg1 = null;
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public LostAccCancelSuccessPanel() {
		
		logger.info("进入销户成功页面");
		lostPubBean.setThisComponent(this);
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易！", "", "");
			}
			
		});
		delayTimer.start();
		/* 倒计时打开语音 */
		voiceTimer = new Timer(BaseTransPanelNew.voiceSecond, new ActionListener() {          
            public void actionPerformed(ActionEvent e) { 
            	stopTimer(voiceTimer);//关闭语音
            	
            }      
        });            
		voiceTimer.start();
		
		// 标题
		JLabel depoLum = new JLabel("挂失销户成功");
		depoLum.setFont(new Font("微软雅黑", Font.BOLD, 40));
		depoLum.setHorizontalAlignment(0);
		depoLum.setForeground(Color.decode("#412174"));
		depoLum.setBounds(0, 35, 1009, 60);
		this.showJpanel.add(depoLum);
		
		//本金
		accAmount = new JLabel("本          金：");
		accAmount.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		accAmount.setHorizontalAlignment(0);
		accAmount.setBounds(-700, 140, 1009, 60);
		accAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(accAmount);		
		
		//
		accAmount1 = new JLabel();
		accAmount1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		accAmount1.setHorizontalAlignment(0);
		accAmount1.setBounds(310, 140, 1009, 60);
		accAmount1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(accAmount1);
		
		//实付利息
		realAmt = new JLabel("实 付 利 息：");
		realAmt.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		realAmt.setHorizontalAlignment(0);
		realAmt.setBounds(-700, 190, 1009, 60);
		realAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(realAmt);		
		
		//
		realAmt1 = new JLabel();
		realAmt1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		realAmt1.setHorizontalAlignment(0);
		realAmt1.setBounds(310, 190, 1009, 60);
		realAmt1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(realAmt1);	
		
		//唐豆收回失败错误信息
		tdErrMsg = new JLabel("唐豆收回失败：");
		tdErrMsg.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		tdErrMsg.setHorizontalAlignment(0);
		tdErrMsg.setForeground(Color.red);
		tdErrMsg.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(tdErrMsg);		
		
		//
		tdErrMsg1 = new JLabel();
		tdErrMsg1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		tdErrMsg1.setHorizontalAlignment(0);
		tdErrMsg1.setForeground(Color.red);
		tdErrMsg1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(tdErrMsg1);			
				
		//凭条打印错误信息
		ptErrMsg = new JLabel("凭条打印失败：");
		ptErrMsg.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		ptErrMsg.setHorizontalAlignment(0);
		ptErrMsg.setForeground(Color.red);
		ptErrMsg.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(ptErrMsg);		
		
		//
		ptErrMsg1 = new JLabel();
		ptErrMsg1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		ptErrMsg1.setHorizontalAlignment(0);
		ptErrMsg1.setForeground(Color.red);
		ptErrMsg1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(ptErrMsg1);			
		
		//赋值排版
		setMsg();
		
		//退出按钮
		JLabel label_1 = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		label_1.setBounds(1258, 770, 150, 50);
		add(label_1);
		label_1.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				accCancelExit();
			}
		});
	}	
	
	/**排版信息*/
	public void setMsg(){
		//挂失销户收回金额信息
		if(null!=lostPubBean.getCanceAmt() && !"".equals(lostPubBean.getCanceAmt().trim())){//实付本金
			accAmount1.setText(lostPubBean.getCanceAmt().trim()+"元");
		}
		if(null!=lostPubBean.getCancelRealInte() && !"".equals(lostPubBean.getCancelRealInte().trim())){//实付利息
			realAmt1.setText(lostPubBean.getCancelRealInte().trim()+"元");
		}
		
		//销户流水号、收回的各种豆、衍生品排版
		List<String> listMsg = new ArrayList<String>();
		
		if(null != lostPubBean.getLostJrnlNo() && !"".equals(lostPubBean.getLostJrnlNo().trim())){
			listMsg.add("挂失销户流水号："+lostPubBean.getLostJrnlNo().trim());
		}
		if(null != lostPubBean.getAdvnInit() && !"".equals(lostPubBean.getAdvnInit().trim()) && Double.valueOf(lostPubBean.getAdvnInit().trim()) != 0){
			listMsg.add("扣回已付收益："+lostPubBean.getAdvnInit()+"元");
		}
		if(null != lostPubBean.getShtdy() && !"".equals(lostPubBean.getShtdy().trim()) && Double.valueOf(lostPubBean.getShtdy().trim()) != 0){
			listMsg.add("扣 回 唐 豆："+lostPubBean.getShtdy().trim()+"元");
		}
		if(null != lostPubBean.getXfdCount() && !"".equals(lostPubBean.getXfdCount().trim()) && Double.valueOf(lostPubBean.getXfdCount().trim()) != 0){
			listMsg.add("扣回消费豆："+lostPubBean.getXfdCount().trim()+"个");
		}
		
		//获取衍生品信息
		List<AccCancelProFileBean> listProMsg = (List<AccCancelProFileBean>)lostPubBean.getImportMap().get("cancelProFile");
		if(listProMsg!=null && listProMsg.size()>0){
			
			for (AccCancelProFileBean accCancelProFileBean : listProMsg) {
				
				if(accCancelProFileBean.getProName() == null || "".equals(accCancelProFileBean.getProName().trim()) || 
				   accCancelProFileBean.getGetProSjAmt() == null || "".equals(accCancelProFileBean.getGetProSjAmt().trim())){
			
					logger.info("销户衍生品信息返回不全");
					
				}else if(Double.valueOf(accCancelProFileBean.getGetProSjAmt().trim()) == 0){
					
					logger.info(accCancelProFileBean.getProName()+"实际收回金额为"+accCancelProFileBean.getGetProSjAmt().trim());
					
				}else{
					
//					logger.info(accCancelProFileBean.getProName()+"实际收回金额为"+accCancelProFileBean.getGetProSjAmt().trim());
//					
//					if("006".equals(accCancelProFileBean.getProtype())){
//						
//						listMsg.add("扣回优惠保险业务："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
//						
//					}else if("009".equals(accCancelProFileBean.getProtype())){
//						
//						listMsg.add("扣回活动奖励："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
//						
//					}
					//扣回增益豆暂不显示
//					else if("008".equals(accCancelProFileBean.getProtype())){
//						
//						listMsg.add("扣回增益豆："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
//					}
				}
			}
		}
		
		//排版
		for (int i = 0; i < listMsg.size(); i++) {
			
			int y = 140 + 50 * i;
			JLabel shMsg = new JLabel(listMsg.get(i));
			shMsg.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			shMsg.setHorizontalAlignment(0);
			shMsg.setHorizontalAlignment(SwingConstants.LEFT);
			shMsg.setBounds(550, y, 1009, 60);
			this.showJpanel.add(shMsg);	
		}
		
		
		//唐豆返回的错误信息
		if(!"".equals(lostPubBean.getTangErrMsg()) && null!=lostPubBean.getTangErrMsg()){
			//凭条打印返回的错误信息
			if(!"".equals(lostPubBean.getPinErrMsg()) && null!=lostPubBean.getPinErrMsg()){
				
				tdErrMsg.setVisible(true);
				tdErrMsg1.setVisible(true);
				tdErrMsg.setBounds(-515, 520, 1009, 60);
				tdErrMsg1.setBounds(500, 520, 1009, 60);
				tdErrMsg1.setText(lostPubBean.getTangErrMsg());
				
				ptErrMsg.setVisible(true);
				ptErrMsg1.setVisible(true);
				ptErrMsg.setBounds(-515, 550, 1009, 60);
				ptErrMsg1.setBounds(500, 550, 1009, 60);
				ptErrMsg1.setText(lostPubBean.getPinErrMsg());
				this.repaint();
			}else{
				
				ptErrMsg.setVisible(false);
				ptErrMsg1.setVisible(false);
				
				tdErrMsg.setVisible(true);
				tdErrMsg1.setVisible(true);
				tdErrMsg.setBounds(-515, 520, 1009, 60);
				tdErrMsg1.setBounds(500, 520, 1009, 60);
				tdErrMsg1.setText(lostPubBean.getTangErrMsg());
				this.repaint();
			}
		}else{
			//凭条打印返回的错误信息
			if(!"".equals(lostPubBean.getPinErrMsg()) && null!=lostPubBean.getPinErrMsg()){
				
				tdErrMsg.setVisible(false);
				tdErrMsg1.setVisible(false);
				
				ptErrMsg.setVisible(true);
				ptErrMsg1.setVisible(true);
				ptErrMsg.setBounds(-515, 520, 1009, 60);
				ptErrMsg1.setBounds(500, 520, 1009, 60);
				ptErrMsg1.setText(lostPubBean.getPinErrMsg());
				this.repaint();
			}else{
				
				ptErrMsg.setVisible(false);
				ptErrMsg1.setVisible(false);
				tdErrMsg.setVisible(false);
				tdErrMsg1.setVisible(false);
				this.repaint();
			}
		}
	}
	
}


