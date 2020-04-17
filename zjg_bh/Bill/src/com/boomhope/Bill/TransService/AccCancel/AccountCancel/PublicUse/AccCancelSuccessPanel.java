package com.boomhope.Bill.TransService.AccCancel.AccountCancel.PublicUse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AccCancel.Bean.AccCancelProFileBean;
import com.boomhope.Bill.TransService.AccCancel.Bean.PublicAccCancelBean;
import com.boomhope.Bill.monitor.sendInterface.MakeMonitorInfo;
/**
 * 销户成功
 * @author hao
 *
 */
public class AccCancelSuccessPanel extends BaseTransPanelNew {
	static Logger logger = Logger.getLogger(AccCancelSuccessPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel accAmount = null;//本金
	private JLabel accAmount1 = null;
	private JLabel inteAmt = null;//应付利息
	private JLabel inteAmt1 = null;
	private JLabel realAmt = null;//实付利息
	private JLabel realAmt1 = null;
	private JLabel yflx = null;//已支付利息
	private JLabel yflx1 = null;
	private JLabel tdErrMsg = null;//唐豆收回错误信息
	private JLabel tdErrMsg1 = null;
	private JLabel ptErrMsg = null;//凭条打印错误信息
	private JLabel ptErrMsg1 = null;
	private Component comp;//当前页面
	private boolean on_off=true;//用于控制页面跳转的开关
	
	public AccCancelSuccessPanel() {
		comp = this;
		/*显示时间倒计时*/
		this.showTimeText(BaseTransPanelNew.delaySecondShortTime);
		delayTimer = new Timer(BaseTransPanelNew.delaySecondShortTime*1000,new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTimeText();
				serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
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
		JLabel depoLum = new JLabel("销户成功");
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
		
		//应付利息
		inteAmt = new JLabel("应 付 利 息：");
		inteAmt.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		inteAmt.setHorizontalAlignment(0);
		inteAmt.setBounds(-700, 190, 1009, 60);
		inteAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(inteAmt);		
		
		//
		inteAmt1 = new JLabel();
		inteAmt1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		inteAmt1.setHorizontalAlignment(0);
		inteAmt1.setBounds(310, 190, 1009, 60);
		inteAmt1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(inteAmt1);	
		
		//实付利息
		realAmt = new JLabel("实 付 利 息：");
		realAmt.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		realAmt.setHorizontalAlignment(0);
		realAmt.setBounds(-700, 240, 1009, 60);
		realAmt.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(realAmt);		
		
		//
		realAmt1 = new JLabel();
		realAmt1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		realAmt1.setHorizontalAlignment(0);
		realAmt1.setBounds(310, 240, 1009, 60);
		realAmt1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(realAmt1);	
		
		//已支付利息
		yflx = new JLabel("已支付利息：");
		yflx.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		yflx.setHorizontalAlignment(0);
		yflx.setBounds(-700, 290, 1009, 60);
		yflx.setHorizontalAlignment(SwingConstants.RIGHT);
		this.showJpanel.add(yflx);		
		
		//
		yflx1 = new JLabel();
		yflx1.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		yflx1.setHorizontalAlignment(0);
		yflx1.setBounds(310, 290, 1009, 60);
		yflx1.setHorizontalAlignment(JLabel.LEFT);
		this.showJpanel.add(yflx1);	
		
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
		
		//继续交易 
		JLabel confirm = new JLabel(new ImageIcon("pic/jxjy.png"));
		confirm.setSize( 150, 50);
		confirm.setLocation(1075, 770);
		if("1".equals(accCancelBean.getHaveAcc())){//无存单可继续交易
			confirm.setVisible(true);
		}else{
			confirm.setVisible(false);
		}
		add(confirm);
		confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击继续交易按钮");
				clearTimeText();
				//继续交易
				backTrans();
			}
		});
		
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
		//销户收回金额信息
		StringBuffer otherAmts  = new StringBuffer("0");
		if(null!=accCancelBean.getRealPri() && !"".equals(accCancelBean.getRealPri().trim())){//实付本金
			accAmount1.setText(accCancelBean.getRealPri().trim()+"元");
		}
		if(null!=accCancelBean.getInteAmt() && !"".equals(accCancelBean.getInteAmt().trim())){//应付利息
			inteAmt1.setText(accCancelBean.getInteAmt().trim()+"元");
		}
		if(null!=accCancelBean.getRealAmt() && !"".equals(accCancelBean.getRealAmt().trim())){//实付利息
			realAmt1.setText(accCancelBean.getRealAmt().trim()+"元");
		}
		if(null!=accCancelBean.getAlreAmt() && !"".equals(accCancelBean.getAlreAmt().trim())){//已支付利息
			yflx1.setText(accCancelBean.getAlreAmt().trim()+"元");
		}
		//销户流水号、收回的各种豆、衍生品排版
		List<String> listMsg = new ArrayList<String>();
		
		if(null != accCancelBean.getXHSvrJrnlNo() && !"".equals(accCancelBean.getXHSvrJrnlNo().trim())){
			listMsg.add("销户流水号："+accCancelBean.getXHSvrJrnlNo().trim());
		}
		if(null != accCancelBean.getShtdy() && !"".equals(accCancelBean.getShtdy().trim()) && !"0.00".equals(accCancelBean.getShtdy().trim())){
			listMsg.add("扣 回 唐 豆："+accCancelBean.getShtdy().trim()+"元");
			otherAmts.append(","+accCancelBean.getShtdy().trim());
		}
		if(null != accCancelBean.getAdvnInit() && !"".equals(accCancelBean.getAdvnInit().trim()) && Double.valueOf(accCancelBean.getAdvnInit().trim()) != 0){
			listMsg.add("扣回幸运豆："+accCancelBean.getAdvnInit().trim()+"元");
			otherAmts.append(","+accCancelBean.getAdvnInit().trim());
		}
		
		//获取衍生品信息
		List<AccCancelProFileBean> listProMsg = (List<AccCancelProFileBean>)accCancelBean.getImportMap().get("cancelProFile");
		if(listProMsg!=null && listProMsg.size()>0){
			
			for (AccCancelProFileBean accCancelProFileBean : listProMsg) {
				
				if(accCancelProFileBean.getProName() == null || "".equals(accCancelProFileBean.getProName().trim()) || 
				   accCancelProFileBean.getGetProSjAmt() == null || "".equals(accCancelProFileBean.getGetProSjAmt().trim())){
			
					logger.info("销户衍生品信息返回不全");
					
				}else if(Double.valueOf(accCancelProFileBean.getGetProSjAmt().trim()) == 0){
					
					logger.info(accCancelProFileBean.getProName()+"实际收回金额为"+accCancelProFileBean.getGetProSjAmt().trim());
					
				}else{
					
					logger.info(accCancelProFileBean.getProName()+"实际收回金额为"+accCancelProFileBean.getGetProSjAmt().trim());
					otherAmts.append(","+accCancelProFileBean.getGetProSjAmt().trim());
					
					if("006".equals(accCancelProFileBean.getProtype())){
						
						listMsg.add("扣回优惠保险业务："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
						
					}else if("009".equals(accCancelProFileBean.getProtype())){
						
						listMsg.add("扣回活动奖励："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
						
					}else if("008".equals(accCancelProFileBean.getProtype())){
						
						listMsg.add("扣回增益豆："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
					}else if("205".equals(accCancelProFileBean.getProtype())){
						
						listMsg.add("扣回课时券："+accCancelProFileBean.getGetProSjAmt().trim()+"元");
					}
				}
			}
		}
		
		String amt = otherAmts.toString();
		String[] split = amt.split(",");
		String amts = "0";
		for (int i = 0; i < split.length; i++) {
			BigDecimal countAmt = new BigDecimal(amts);
			BigDecimal other = new BigDecimal(split[i]);
			amts = String.valueOf(countAmt.add(other));
		}
		
		if("07498".equals(accCancelBean.getReqMCM001().getIntecode())){
			
			if(!"000000".equals(accCancelBean.getReqMCM001().getIntereturncode())){
				accCancelBean.getReqMCM001().setIntereturnmsg("调用07498接口异常");
			}else{
			}
			
		}else if("07499".equals(accCancelBean.getReqMCM001().getIntecode())){
			
			if(!"000000".equals(accCancelBean.getReqMCM001().getIntereturncode())){
				accCancelBean.getReqMCM001().setIntereturnmsg("调用07499接口异常");
			}else{
			}
		
		}else{
			
		}
		//销户成功流水信息上送
		accCancelBean.getReqMCM001().setTransamt(accCancelBean.getRealPri().trim());//本金
		accCancelBean.getReqMCM001().setInterest(accCancelBean.getRealAmt().trim());//利息
		accCancelBean.getReqMCM001().setRecoverypea("0".equals(amts)?"":amts);//收回的唐豆和各种衍生品金额
		MakeMonitorInfo.makeInfos(accCancelBean.getReqMCM001());
		
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
		if(!"".equals(accCancelBean.getTangErrMsg()) && null!=accCancelBean.getTangErrMsg()){
			//凭条打印返回的错误信息
			if(!"".equals(accCancelBean.getErrmsg()) && null!=accCancelBean.getErrmsg()){
				
				tdErrMsg.setVisible(true);
				tdErrMsg1.setVisible(true);
				tdErrMsg.setBounds(-515, 520, 1009, 60);
				tdErrMsg1.setBounds(500, 520, 1009, 60);
				tdErrMsg1.setText(accCancelBean.getTangErrMsg());
				
				ptErrMsg.setVisible(true);
				ptErrMsg1.setVisible(true);
				ptErrMsg.setBounds(-515, 550, 1009, 60);
				ptErrMsg1.setBounds(500, 550, 1009, 60);
				ptErrMsg1.setText(accCancelBean.getErrmsg());
				this.repaint();
			}else{
				
				ptErrMsg.setVisible(false);
				ptErrMsg1.setVisible(false);
				
				tdErrMsg.setVisible(true);
				tdErrMsg1.setVisible(true);
				tdErrMsg.setBounds(-515, 520, 1009, 60);
				tdErrMsg1.setBounds(500, 520, 1009, 60);
				tdErrMsg1.setText(accCancelBean.getTangErrMsg());
				this.repaint();
			}
		}else{
			//凭条打印返回的错误信息
			if(!"".equals(accCancelBean.getErrmsg()) && null!=accCancelBean.getErrmsg()){
				
				tdErrMsg.setVisible(false);
				tdErrMsg1.setVisible(false);
				
				ptErrMsg.setVisible(true);
				ptErrMsg1.setVisible(true);
				ptErrMsg.setBounds(-515, 520, 1009, 60);
				ptErrMsg1.setBounds(500, 520, 1009, 60);
				ptErrMsg1.setText(accCancelBean.getErrmsg());
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
	
	/**
	 * 无存单继续交易
	 */
	public void backTrans() {
		if(!on_off){
			logger.info("开关当前状态"+on_off);
			return;
		}
		logger.info("开关当前状态"+on_off);
		on_off=false;
		PublicAccCancelBean accBean = accCancelBean;
		accCancelBean=new PublicAccCancelBean();
		accCancelBean.setAccCancelType(accBean.getAccCancelType());
		accCancelBean.setHaveAcc(accBean.getHaveAcc());
		accCancelBean.setCardNo(accBean.getcNo());
		accCancelBean.setCardPwd(accBean.getCardPwd());
		accCancelBean.getReqMCM001().setLendirection(accBean.getReqMCM001().getLendirection());
		accCancelBean.getReqMCM001().setTranscode(accBean.getReqMCM001().getTranscode());
		accCancelBean.getReqMCM001().setToaccount(accBean.getReqMCM001().getToaccount());
		noAccAction.passWord(comp);
		
	}
}


