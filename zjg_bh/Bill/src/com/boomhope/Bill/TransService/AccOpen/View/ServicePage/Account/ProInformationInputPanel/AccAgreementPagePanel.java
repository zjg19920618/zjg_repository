package com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.ProInformationInputPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.PublicControl.OutputBankCardPanel;
import com.boomhope.Bill.TransService.AccOpen.Bean.AccPublicBean;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.AccChooseBusiness;
import com.boomhope.Bill.TransService.AccOpen.View.ServicePage.Account.Author.AccAuthorNoPanel;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTradeCodeAction;
import com.boomhope.Bill.TransService.AccOpen.controller.AccountTransFlow;
import com.boomhope.Bill.Util.Property;

/***
 * 产品协议书
 * 
 * @author gyw
 *
 */
public class AccAgreementPagePanel extends BaseTransPanelNew {
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AccAgreementPagePanel.class);

	JLabel lblNewLabel = null;// 协议书
	JButton backButton = null;// 同意（橘红色）
	JButton backButton1 = null;// 同意（灰色）
	JButton backButton2 = null;// 同意框（没选中）
	JButton backButton3 = null;// 同意框（选中）
	private Timer agreeTimer;
	String change = "1";// 状态变化 1，2
	int n = 10;
	JLabel lblNewLabel_2;//
	JLabel lblNewLabel_3;
	public AccPublicBean transBean;
	private Map<Object, Object> params;
	private Component comp;
	private boolean on_off=true;//用于控制页面跳转的开关

	public AccAgreementPagePanel(final Map<Object, Object> params) {
		logger.info("进入产品协议书页面");
		transBean = AccountTradeCodeAction.transBean;
		
		comp = this;
		this.params = params;

		JPanel panel = new JPanel();
		panel.setBounds(55, 40, 900, 400);
		panel.setLayout(null);
		this.showJpanel.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 900, 400);
		panel.add(scrollPane);

		/* 显示时间倒计时 */
		if(delayTimer!=null){
			clearTimeText();
		}
		this.showTimeText(delaySecondShortTime);
		delayTimer = new Timer(delaySecondShortTime * 1000, new ActionListener() {          
            public void actionPerformed(ActionEvent e) {  
            	logger.info("产品协议书页面倒计时结束 ");
            	/* 倒计时结束退出交易 */ 
            	clearTimeText();
            	serverStop("温馨提示：服务已超时，请结束您的交易并收好您的银行卡及其它随身物品 ！","","");
            }      
        });            
		delayTimer.start(); 
		
		if("JX".equals(params.get("PRODUCT_CODE"))){//积享存协议
		    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
		    		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“积享存”产品协议书</h2> "
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“积享存”系列个人协议存款产品是为已经办理唐山银行“如意存”产品的客"
		    		+ "<br></p>户提供的，在客户名下盛唐卡开立，约定每月从盛唐卡活期账户或随身银行电子账户自动扣划固定金额资金并存入该产品账户的人民<br></p>"
		    		+ "币存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照中国法律法规和有关的金融监管规定，经平等自愿协商一<br></p>"
		    		+ "致，达成如下条款，承诺信守：<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
		    		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议不"
		    		+ "<br></p>违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金与扣划规则。本产品每月从盛唐卡活期账户或随身银行电子账户扣划固定金额资金，每月扣划资金即构成本产品存"
		    		+ "<br></p>款本金，客户可在产品办理页面设置每月扣划金额。客户签约本产品当日，我行系统即从盛唐卡活期账户或随身银行电子账户自动扣"
		    		+ "<br></p>划第1笔固定金额资金并存入本产品存款账户，从次月开始，我行系统每月15日从盛唐卡活期账户或随身银行电子账户自动扣划固定"
		    		+ "<br></p>金额资金并存入本产品存款账户。同一盛唐卡或随身银行电子账户下办理多笔本产品的，按产品开户先后顺序依次扣划。本产品存续"
		    		+ "<br></p>期间，客户需确保每月15日前存入盛唐卡或随身银行电子账户足额资金，由于客户当月账户余额不足等原因造成系统自动扣划失败的<br></p>"
		    		+ "，该产品协议自当月起自动终止。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“积享存A”系列：本产品涉及到的存款期限为客户关联的“如意存A”产品剩余期限，最长不超过60个月，最短不少于3个月；<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“积享存B”系列：本产品涉及到的存款期限为客户关联的“如意存B”产品剩余期限，最长不超过50个月，最短不少于3个月；<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“积享存C”系列：本产品涉及到的存款期限为客户关联的“如意存C”产品剩余期限，最长不超过40个月，最短不少于3个月。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“积享存”系列个人"
		    		+ "<br></p>协议存款产品说明》的规定；该利率以年化利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、计息与自动转存。本产品到期或提前支取时，根据本产品账户存款金额、实际存期和对应利率计息，计息公式：利息=账"
		    		+ "<br></p>户存款金额/2×利率×实际存期/360，其中，实际存期为产品起息日（含）至到期日或提前支取日（不含）实际天数；本产品到期或产"
		    		+ "<br></p>品协议自动终止后，不设自动转存功能，如客户仍未支取，存款将按照支取日唐山银行公布的活期存款利率计息。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、关联规则。<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户名下单笔“如意存”产品只能关联办理一笔“积享存”产品；客户相关联的“如意存”产品提前支取的，则本产品协议自动<br></p>"
		    		+ "终止。<br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本产品无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支取单笔协议存款。对于被提前支取的存<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款，按照本协议书第五条之规定计息。由于自动扣划失败、关联“如意存”产品提前支取等原因造成该产品协议自动终止的，视<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同客户提前支取。行政/司法机构强制扣划或依据客户/法律法规的许可且被我行认可的权利人行使存款支取权均视同客户提前<br></p>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支取。"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、协议的生效、变更或终止。<br>"
		    		+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提前终止：（1）如因本协议和/或"
		    		+ "<br></p>本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止的；（2）国家有关法律、法规"
		    		+ "<br></p>、规章、政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）因不可抗力因素导致其必须修"
		    		+ "<br></p>改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止日之前，将通过官方网站、"
		    		+ "<br></p>手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向唐山银行提出异议，否则<br></p>"
		    		+ "公告期满后将视为 客户同意上述变更。 <br></p>"
		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
		    lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
			scrollPane.setViewportView(lblNewLabel);
		}else if("LZ".equals(params.get("PRODUCT_CODE"))){//立得存协议
     	    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
         	   		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“立得存”系列个人协议存款产品协议书</h2> "
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“立得存”系列个人协议存款产品是为客户提供的允许客户根据自身资金使用"
         	   		+ "<br></p>安排，一次存入存款本金、分次支取存款利息的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依"
         	   		+ "<br></p>照中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以"
         	   		+ "<br></p>其为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议"
         	   		+ "<br></p>不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“立得存”系列个人协议存款产品说"
         	   		+ "<br></p>明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br>“立得存”之“自享A”、“他享A”、“自享F”、“他享F”系列：本产品涉及到的存款期限固定为40个月；<br>“立得存”之“自享B”、“他享B”、“自享E”、“他享E”系列：本产品涉及到的存款期限固定为50个月；<br>“立得存”之“自享C”、“他享C”、“自享D”、“他享D”系列：本产品涉及到的存款期限固定为60个月。<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“立得存”系列个人"
         	   		+ "<br></p>协议存款产品说明》的规定；该利率以年化利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通过<br></p>"
         	   		+ "我行营业网点自助设备打印领取。<br>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、利息收益账户绑定与自动转存。自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品，产品到期后，不设"
         	   		+ "<br></p>自动转存功能，如客户仍未支取，存款将按照到期日（即为新的起息日）我行挂牌活期利率计息。该产品须指定一个唐山银行的银行"
         	   		+ "<br>卡或随身银行电子账户为收益账户，唐山银行按照客户选择的产品类型，定期将对应的利息划入该收益账户。可选择本人银行卡、随"
         	   		+ "<br></p>身银行电子账户或由客户指定第三人的银行卡、随身银行电子账户为收益账户。"
         	   		+ "<br>指定本人名下唐山银行的银行卡或随身银行电子账户为收益账户。"
         	   		+ "<br>指定第三人名下唐山银行的银行卡或随身银行电子账户为收益账户。"
         	   		+ "<br>对于存款利息分次支取的时间，客户只能做出以下六种选择中的一种，每种选择都对应不同的存款计息规则和支取次数，具体如下："
         	   		+ "<br>“立得存”之“自享A”、“他享A”系列：自起息日起，每月按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
         	   		+ "<br>“立得存”之“自享B”、“他享B”系列：自起息日起，每周按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
         	   		+ "<br>“立得存”之“自享C”、“他享C”系列：自起息日起，每天按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
         	   		+ "<br>“立得存”之“自享D”、“他享D”系列：自起息日起，每季按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
         	   		+ "<br>“立得存”之“自享E”、“他享E”系列：自起息日起，每半年按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
         	   		+ "<br>“立得存”之“自享F”、“他享F”系列：自起息日起，每年按照约定的存款利率计息，并将相应的利息划入客户的收益账户。<br></p>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、计息规则。根据客户选择的产品，唐山银行于指定的结息日期将利息转入客户的收益账户。利息计算方法为："
         	   		+ "<br></p>存款金额*到期年化利率*结息周期内实际存入天数/360。指定的结息日期以《唐山银行“立得存”系列个人协议存款产品说明》<br></p>"
         	   		+ "为准。<br>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道"
         	   		+ "<br></p>申请提前支取单笔存款。其中，在客户已经办理了纸质存单凭证的情况下，如客户需要进行存款支取时，只能通过我行营业网点进行"
         	   		+ "<br></p>办理。提前支取时，按支取日我行挂牌活期利率计息，已按产品原年化利率支付的利息从本金中扣除，再支付客户剩余本金和活期利<br></p>"
         	   		+ "息。<br>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
         	   		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须"
         	   		+ "<br></p>修改或提前终止的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的台、市场环境发生重大变化导致其必须修改或提前终"
         	   		+ "<br></p>止的；（3）因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改"
         	   		+ "<br></p>生效日/终止日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个<br></p>"
         	   		+ "工作日内向唐山银行提出异议，否则公告期满后将视为客户同意上述变更。<br>"
         	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
         	           lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
         	           scrollPane.setViewportView(lblNewLabel);
         	           
                 }else if("LT".equals(params.get("PRODUCT_CODE"))){//立得存协议
              	    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
                 	   		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“立得存”系列个人协议存款产品协议书</h2> "
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“立得存”系列个人协议存款产品是为客户提供的允许客户根据自身资金使用"
                 	   		+ "<br></p>安排，一次存入存款本金、分次支取存款利息的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依"
                 	   		+ "<br></p>照中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以"
                 	   		+ "<br></p>其为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议"
                 	   		+ "<br></p>不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“立得存”系列个人协议存款产品说"
                 	   		+ "<br></p>明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br>“立得存”之“自享A”、“他享A”、“自享F”、“他享F”系列：本产品涉及到的存款期限固定为40个月；<br>“立得存”之“自享B”、“他享B”、“自享E”、“他享E”系列：本产品涉及到的存款期限固定为50个月；<br>“立得存”之“自享C”、“他享C”、“自享D”、“他享D”系列：本产品涉及到的存款期限固定为60个月。<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“立得存”系列个人"
                 	   		+ "<br></p>协议存款产品说明》的规定；该利率以年化利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通过<br></p>"
                 	   		+ "我行营业网点自助设备打印领取。<br>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、利息收益账户绑定与自动转存。自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品，产品到期后，不设"
                 	   		+ "<br></p>自动转存功能，如客户仍未支取，存款将按照到期日（即为新的起息日）我行挂牌活期利率计息。该产品须指定一个唐山银行的银行"
                 	   		+ "<br>卡或随身银行电子账户为收益账户，唐山银行按照客户选择的产品类型，定期将对应的利息划入该收益账户。可选择本人银行卡、随"
                 	   		+ "<br></p>身银行电子账户或由客户指定第三人的银行卡、随身银行电子账户为收益账户。"
                 	   		+ "<br>指定本人名下唐山银行的银行卡或随身银行电子账户为收益账户。"
                 	   		+ "<br>指定第三人名下唐山银行的银行卡或随身银行电子账户为收益账户。"
                 	   		+ "<br>对于存款利息分次支取的时间，客户只能做出以下六种选择中的一种，每种选择都对应不同的存款计息规则和支取次数，具体如下："
                 	   		+ "<br>“立得存”之“自享A”、“他享A”系列：自起息日起，每月按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
                 	   		+ "<br>“立得存”之“自享B”、“他享B”系列：自起息日起，每周按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
                 	   		+ "<br>“立得存”之“自享C”、“他享C”系列：自起息日起，每天按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
                 	   		+ "<br>“立得存”之“自享D”、“他享D”系列：自起息日起，每季按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
                 	   		+ "<br>“立得存”之“自享E”、“他享E”系列：自起息日起，每半年按照约定的存款利率计息，并将相应的利息划入客户的收益账户；"
                 	   		+ "<br>“立得存”之“自享F”、“他享F”系列：自起息日起，每年按照约定的存款利率计息，并将相应的利息划入客户的收益账户。<br></p>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、计息规则。根据客户选择的产品，唐山银行于指定的结息日期将利息转入客户的收益账户。利息计算方法为："
                 	   		+ "<br></p>存款金额*到期年化利率*结息周期内实际存入天数/360。指定的结息日期以《唐山银行“立得存”系列个人协议存款产品说明》<br></p>"
                 	   		+ "为准。<br>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道"
                 	   		+ "<br></p>申请提前支取单笔存款。其中，在客户已经办理了纸质存单凭证的情况下，如客户需要进行存款支取时，只能通过我行营业网点进行"
                 	   		+ "<br></p>办理。提前支取时，按支取日我行挂牌活期利率计息，已按产品原年化利率支付的利息从本金中扣除，再支付客户剩余本金和活期利<br></p>"
                 	   		+ "息。<br>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
                 	   		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须"
                 	   		+ "<br></p>修改或提前终止的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的台、市场环境发生重大变化导致其必须修改或提前终"
                 	   		+ "<br></p>止的；（3）因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改"
                 	   		+ "<br></p>生效日/终止日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个<br></p>"
                 	   		+ "工作日内向唐山银行提出异议，否则公告期满后将视为客户同意上述变更。<br>"
                 	   		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
                 	           lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
                 	           scrollPane.setViewportView(lblNewLabel);
                 	           
              }else if("RY".equals(params.get("PRODUCT_CODE"))){//如意存
    		    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
    		    		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“如意存”系列个人协议存款产品协议书</h2> "
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“如意存”系列个人协议存款产品是为客户提供的旨在提高客户收益，同时避"
    		    		+ "<br></p>免客户因灵活支取而造成相应利息损失的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照中国"
    		    		+ "<br></p>法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以"
    		    		+ "<br></p>其为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议<br></p>"
    		    		+ "不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“如意存”系列个人协议存款产品说"
    		    		+ "<br></p>明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。如意存A系列：本产品涉及到的存款期限固定为5年；如意存B系列：本产品涉及到的存款期限固定为50个月；"
    		    		+ "<br></p>如意存C系列：本产品涉及到的存款期限固定为40个月。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“如意存”系列个人"
    		    		+ "<br></p>协议存款产品说明》的规定；该利率以年利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通过<br></p>"
    		    		+ "我行营业网点自助设备打印领取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、计息与自动转存。自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品；产品到期后，不设自动转存功能"
    		    		+ "<br></p>，如客户仍未支取，存款将按照到期日（即为新的起息日）人民银行的活期存款利率计息。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支取单"
    		    		+ "<br></p>笔协议存款。对于被提前支取的存款，按照业务办理时规定利率计算。其中，在客户已经办理了纸质存单凭证的情况下，如客户需"
    		    		+ "<br>要进行存款支取时，只能通过我行营业网点进行办理。"
    		    		+ "<br>行政/司法机构强制扣划或依据客户/法律法规的许可且被我行认可的权利人行使存款支取权均视同客户提前支取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
    		    		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止"
    		    		+ "<br></p>的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）"
    		    		+ "<br></p>因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止"
    		    		+ "<br></p>日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向"
    		    		+ "<br></p>唐山银行提出异议，否则公告期满后将视为客户同意上述变更。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
    		          lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    		          scrollPane.setViewportView(lblNewLabel);	
    		          
    		}else if("RJ".equals(params.get("PRODUCT_CODE"))){//如意存＋协议
    		    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
    		    		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“如意存+”个人协议存款产品协议书</h2> "
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“如意存+”系列个人协议存款产品是为已经办理唐山银行“如意存”产品且"
    		    		+ "<br></p>产品剩余期限不少于3个月的客户提供的，与“如意存”产品相关联，旨在提高客户收益，同时避免客户因灵活支取而造成相应利息损"
    		    		+ "<br></p>失的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照中国法律法规和有关的金融监管规定，经</p>"
    		    		+ "<br>平等自愿协商一致，达成如下条"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
    		    		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议不"
    		    		+ "<br>违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体要求参照《唐山银行“如意存+”系列个人协议存款产品说明》的规</p>"
    		    		+ "<br>定。"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。“如意存A+”系列：本产品涉及到的存款期限为客户关联的“如意存A”产品剩余期限，最长不超过60个月，"
    		    		+ "<br></p>最短不少于3个月；“如意存B+系列：本产品涉及到的存款期限为客户关联的“如意存B”产品剩余期限，最长不超过50个月，最短"
    		    		+ "<br></p>不少于3个月；“如意存C+”系列：本产品涉及到的存款期限为客户关联的“如意存C”产品剩余期限，最长不超过40个月，最短不"
    		    		+ "<br></p>少于3个月。"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“如意存+”系列个"
    		    		+ "<br></p>人协议存款产品说明》的规定；该利率以年化利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通"
    		    		+ "<br></p>过我行营业网点自助设备打印领取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、计息与自动转存。自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品；该产品到期后，不设自动转存"
    		    		+ "<br></p>功能，如客户仍未支取，存款将按照支取日唐山银行公布的活期存款利率计息。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。客户名下单笔“如意存”产品只能关联办理一笔“如意存+”产品；客户“如意存”产品存续期间，相关联的“"
    		    		+ "<br></p>如意存+”产品可根据需要进行提前支取；客户“如意存”产品需提前支取的，相关联的“如意存+”产品需同时办理提前支取，否"
    		    		+ "<br></p>则“如意存”产品不予办理提前支取。"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支"
    		    		+ "<br></p>取单笔协议存款。对于被提前支取的存款，按照业务办理时规定利率计算。其中，在客户已经办理了纸质存单凭证的情况下，如"
    		    		+ "<br></p>客户需要进行存款支取时，只能通过我行营业网点进行办理。对于被提前支取的存款，按照业务办理时规定利率计算。"
    		    		+ "<br>行政/司法机构强制扣划或依据客户/法律法规的许可且被我行认可的权利人行使存款支取权均视同客户提前支取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或"
    		    		+ "<br></p>提前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前"
    		    		+ "<br></p>终止的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；"
    		    		+ "<br></p>（3）因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效"
    		    		+ "<br></p>日/终止日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工</p>"
    		    		+ "<br></p>作日内向唐山银行提出异议，否则公告期满后将视为客户同意上述变更。"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p><br></p><br></p></html>");
    		          lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    		          scrollPane.setViewportView(lblNewLabel);	
    		          
    		}else if("YA".equals(params.get("PRODUCT_CODE"))){//约享存A
    		    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
    		    		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“约享存A”系列个人协议存款产品协议书</h2> "
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行“约享存A”系列个人协议存款产品是为客户提供的允许客户根据自身资金使用安排，在产品存续期内就提前支取时间"
    		    		+ "<br></p>、提前支取次数事先约定，并因该约定而获得不同收益的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务"
    		    		+ "<br></p>，双方依照中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
    		    		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议不</p>"
    		    		+ "<br>违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“约享存A”系列个人协议存款产品"
    		    		+ "<br></p>说明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。本产品涉及到的存款期限固定为40个月。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“约享存A”系列个"
    		    		+ "<br></p>人协议存款产品说明》的规定；该利率以年利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通</p>"
    		    		+ "<br>过我行营业网点自助设备打印领取。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、计息与自动转存。自起息日起，产品本金即构成存款期限为40个月的存款产品；产品到期后，不设自动转存功能，如客户"
    		    		+ "<br></p>仍未支取，存款将按照到期日（即为新的起息日）唐山银行的活期存款利率计息。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支取"
    		    		+ "<br></p>单笔存款。对于提前支取的时间以及次数，客户只能做出以下三种选择中的一种，每种选择都对应不同的计息规则，具体如下：<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.1 存款期间，在第14个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存"
    		    		+ "<br></p>款并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存A”系"
    		    		+ "<br></p>列个人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行<br></p>"
    		    		+ "活期存款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.2 存款期间，在第26个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存款"
    		    		+ "<br></p>并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存A”系列"
    		    		+ "<br></p>个人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行活期<br></p>"
    		    		+ "存款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.3 存款期间，在第14个月（选择期）和第26个月（选择期）两个整月分别给客户一次选择是否在当月提前支取存款的权利，如"
    		    		+ "<br></p>客户选择在第14个月当月提前支取存款并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠"
    		    		+ "<br></p>道版本的《唐山银行“约享存A”系列个人协议存款产品说明》），如客户未在第14个月发生提前支取，则仍可在第26个月当月选择"
    		    		+ "<br></p>提前支取存款并只能在当月完成存款提前支取，也可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约"
    		    		+ "<br></p>享存A”系列个人协议存款产品说明》），否则在除存款期满的其他任一时间发生提前支取的，提前支取部分均按照结息日对应的唐<br></p>"
    		    		+ "山银行活期存款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行政/司法机构强制扣划或依据客户/法律法规的许可且被唐山银行认可的权利人行使存款支取权均视同客户提前支取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取选择权利的形成与终止。本协议涉及到的上述第六条约定的三项存款提前支取选择说明构成了客户提前支取的选择"
    		    		+ "<br></p>权利，并且自产品成立之日起，该权利自动生成。如客户在唐山银行已提前告知提醒的情况下，未在选择期行使提前支取选择权利，"
    		    		+ "<br></p>则视同客户自愿放弃在选择期行使提前支取的权利，提前支取选择权利自动终止。客户在除存款期满的其他任一时间发生提前支取的<br></p>"
    		    		+ "，提前支取部分均按照结息日对应的唐山银行活期存款计息。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
    		    		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止"
    		    		+ "<br></p>的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）"
    		    		+ "<br></p>因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止"
    		    		+ "<br></p>日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向<br></p>"
    		    		+ "唐山银行提出异议，否则公告期满后将视为客户同意上述变更。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
    		          lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    		          scrollPane.setViewportView(lblNewLabel);	
    		          
    		}else if("YB".equals(params.get("PRODUCT_CODE"))){ //约享存B
    		    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
    		    		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“约享存B”系列个人协议存款产品协议书</h2> "
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行“约享存B”系列个人协议存款产品是为客户提供的允许客户根据自身资金使用安排，在产品存续期内就提前支取时间、"
    		    		+ "<br></p>次数事先约定，并因该约定而获得不同收益的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照"
    		    		+ "<br></p>中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、 声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以"
    		    		+ "<br></p>其为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议"
    		    		+ "<br></p>不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“约享存B”系列个人协议存款产品"
    		    		+ "<br></p>说明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。本产品涉及到的存款期限固定为50个月。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“约享存B”系列个"
    		    		+ "<br></p>人协议存款产品说明》的规定；该利率以年利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通<br></p>"
    		    		+ "过我行营业网点自助设备打印领取。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、计息与自动转存。自起息日起，产品本金即构成存款期限为50个月的存款产品；产品到期后，不设自动转存功能，如客户"
    		    		+ "<br></p>仍未支取，存款将按照到期日（即为新的起息日）唐山银行的活期存款利率计息。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支取"
    		    		+ "<br></p>单笔存款。对于提前支取的时间以及次数，客户只能做出以下三种选择中的一种，每种选择都对应不同的计息规则，具体如下：<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.1 存款期间，在第19个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存款"
    		    		+ "<br></p>并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存B”系列"
    		    		+ "<br></p>个人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行活期<br></p>"
    		    		+ "存款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.2 存款期间，在第38个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存款"
    		    		+ "<br></p>并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存B”系列个"
    		    		+ "<br></p>人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行活期存 <br></p>"
    		    		+ "款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.3 存款期间，在第19个月（选择期）和第38个月（选择期）两个整月分别给客户一次选择是否在当月提前支取存款的权利，如"
    		    		+ "<br></p>客户选择在第19个月当月提前支取存款并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠"
    		    		+ "<br></p>道版本的《唐山银行“约享存B”系列个人协议存款产品说明》），如客户未在第19个月发生提前支取，则仍可在第38个月当月选择"
    		    		+ "<br></p>提前支取存款并只能在当月完成存款提前支取，也可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约"
    		    		+ "<br></p>享存B”系列个人协议存款产品说明》），否则在除存款期满的其他任一时间发生提前支取的，提前支取部分均按照结息日对应的唐山<br></p>"
    		    		+ "银行活期存款计息；<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行政/司法机构强制扣划或依据客户/法律法规的许可且被唐山银行认可的权利人行使存款支取权均视同客户提前支取。<br></p>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取选择权利的形成与终止。本协议涉及到的上述第六条约定的三项存款提前支取选择说明构成了客户提前支取的选择"
    		    		+ "<br></p>权利，并且自产品成立之日起，该权利自动生成。如客户在唐山银行已提前告知提醒的情况下，未在选择期行使提前支取选择权利，"
    		    		+ "<br></p>则视同客户自愿放弃在选择期行使提前支取的权利，提前支取选择权利自动终止。客户在除存款期满的其他任一时间发生提前支取的<br></p>"
    		    		+ "，提前支取部分均按照结息日对应的唐山银行活期存款计息。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
    		    		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止"
    		    		+ "<br></p>的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）"
    		    		+ "<br></p>因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止"
    		    		+ "<br></p>日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向<br></p>"
    		    		+ "唐山银行 提出异议，否则公告期满后将视为客户同意上述变更。<br>"
    		    		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
    		          lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    		          scrollPane.setViewportView(lblNewLabel);	
    		          
    		}else if("YC".equals(params.get("PRODUCT_CODE"))){//约享存C
      	    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
      	  		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“约享存C”系列个人协议存款产品协议书</h2> "
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行“约享存C”系列个人协议存款产品是为客户提供的允许客户根据自身资金使用安排，在产品存续期内就提前支取时间、"
      	  		+ "<br></p>次数事先约定，并因该约定而获得不同收益的系列个人协议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照"
      	  		+ "<br></p>中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、 声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
      	  		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议不"
      	  		+ "<br></p>违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“约享存C”系列个人协议存款产品"
      	  		+ "<br></p>说明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。本产品涉及到的存款期限固定为60个月。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的存款利率不同，详细利率参照相应购买渠道版本的《唐山银行“约享存C”系列个"
      	  		+ "<br></p>人协议存款产品说明》的规定；该利率以年利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需通过<br></p>"
      	  		+ "我行营业网点自助设备打印领取。<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、计息与自动转存。自起息日起，产品本金即构成存款期限为60个月的存款产品；产品到期后，不设自动转存功能，如客户仍"
      	  		+ "<br></p>未支取，存款将按照到期日（即为新的起息日）唐山银行的活期存款利率计息。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、提前支取。本协议存款为固定金额存款，无法部分提前支取。客户可随时通过我行营业网点柜台或自助渠道申请提前支取单"
      	  		+ "<br></p>笔存款。对于提前支取的时间以及次数，客户只能做出以下三种选择中的一种，每种选择都对应不同的计息规则，具体如下：<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.1 存款期间，在第24个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存款"
      	  		+ "<br></p>并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存C”系列"
      	  		+ "<br></p>个人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行活<br></p>"
      	  		+ "银行活期存款计息；<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.2 存款期间，在第36个月一整月（选择期）给客户一次选择是否在当月提前支取存款的权利，如客户选择在当月提前支取存款"
      	  		+ "<br></p>并只能在当月完成存款提前支取，则可享受与该选择相匹配的存款利率（具体参照相应购买渠道版本的《唐山银行“约享存C”系列"
      	  		+ "<br></p>个人协议存款产品说明》），否则在除存款期满的其他任一时间内发生提前支取的，提前支取部分均按照结息日对应的唐山银行活<br></p>"
      	  		+ "期存款计息；<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7.3 存款期间，在第24个月（选择期）和第36个月（选择期）两个整月分别给客户一次选择是否在当月提前支取存款的权利，如"
      	  		+ "<br></p>客户选择在第24个月当月提前支取存款并只能在当月完成存款提前支取。则可享受与该选择相匹配的存款利率（具体参照相应购买"
      	  		+ "<br></p>渠道版本的《唐山银行“约享存C”系列个人协议存款产品说明》），如客户未在第24个月发生提前支取，则仍可在第36个月当月选"
      	  		+ "<br></p>择提前支取存款并只能在当月完成存款提前支取，也可享受与该选择相匹配的存款利率（具体参照参照相应购买渠道版本的《唐山银"
      	  		+ "<br></p>行“约享存C”系列个人协议存款产品说明》），否则在除存款期满的其他任一时间发<br></p>"
      	  		+ "生提前支取的，提前支取部分均按照结息日对应的唐山银行活期存款计息；<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;行政/司法机构强制扣划或依据客户/法律法规的许可且被唐山银行认可的权利人行使存款支取权均视同客户提前支取。<br></p>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取选择权利的形成与终止。本协议涉及到的上述第六条约定的三项存款提前支取选择说明构成了客户提前支取的选择"
      	  		+ "<br></p>权利，并且自产品成立之日起，该权利自动生成。如客户在唐山银行已提前告知提醒的情况下，未在选择期行使提前支取选择权利，"
      	  		+ "<br></p>则视同客户自愿放弃在选择期行使提前支取的权利，提前支取选择权利自动终止。客户在除存款期满的其他任一时间发生提前支取的<br></p>"
      	  		+ "，提前支取部分均按照结息日对应的唐山银行活期存款计息。<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议的生效、变更或终止。本协议自客户购买产品成功后生效。客户同意：发生下列事项时，唐山银行可对本协议修改或提"
      	  		+ "<br></p>前终止：（1）如因本协议和/或本产品与相关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止"
      	  		+ "<br></p>的；（2）国家有关法律、法规、规章、政策的改变、紧急措施的出、台市场环境发生重大变化导致其必须修改或提前终止的；（3）"
      	  		+ "<br></p>因不可抗力因素导致其必须修改或提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止"
      	  		+ "<br></p>日之前，将通过官方网站、手机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向<br></p>"
      	  		+ "唐山银行提出异议，否则公告期满后将视为客户同意上述变更。<br>"
      	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。<br></p></html>");
      	        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
      	        scrollPane.setViewportView(lblNewLabel);
      	        
          }else if("AX".equals(params.get("PRODUCT_CODE"))){ //安享存
        	    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
        	  		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“安享存”系列个人协议存款产品协议书</h2> "
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“安享存”系列个人协议存款产品是我行为中老年客户提供的固定金额、固定"
        	  		+ "<br></p>期限，一次存本、按期结息、利息收益逐期递增的人民币存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照"
        	  		+ "<br></p>中国法律法规和有关的金融监管规定，经平等自愿协商一致，达成如下条款，承诺信守：<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
        	  		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议不"
        	  		+ "<br></p>违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，具体的固定金额要求参照《唐山银行“安享存”系列个人协议存款产品"
        	  		+ "<br></p>说明》的规定。客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“安享存A”系列产品的存款期限为3年；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“安享存B”系列产品的存款期限为4年；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“安享存C”系列产品的存款期限为5年。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品在不同渠道购买执行的利率不同，详细利率参照相应购买渠道的《唐山银行“安享存”个人协议存款产品"
        	  		+ "<br></p>说明》；该系列产品利率以年利率表示，换算成月利率时按一年12个月计算，换算成日利率时按一年360天计算。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、选择是否打印纸质存单。客户在办理该协议存款业务时，可选择是否进行纸质存单打印，如约定需要纸质存单凭证，需要通<br></p>"
        	  		+ "过我行营业网点自助设备打印领取。<br>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、利息收益账户绑定与自动转存。"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本产品须指定一个本人名下唐山银行的银行卡或随身银行电子账户为利息收益账户，唐山银行按照客户选择的产品类型，按期将"
        	  		+ "<br></p>对应的利息划入该收益账户。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品；产品到期后，不设自动转存功能，如客户仍未支取，到"
        	  		+ "<br></p>期日至支取日之间的存款利息将按支取日唐山银行公布的活期存款利率计算。 <br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、计息和结息规则。"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本产品按期结息，安享存A系列产品分三期结息，安享存B系列产品分四期结息，安享存C系列产品分五期结息。具体结息时间见"
        	  		+ "<br></p>各系列产品说明书。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(一)存满一期，即按照存入日双方约定的第一期执行利率结息，利息转入客户的收益账户；"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(二)存满二期，即按照存入日双方约定的第二期执行利率结息，利息转入客户的收益账户；"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(三)存满三期，即按照存入日双方约定的第三期执行利率结息，利息转入客户的收益账户；"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(四)存满四期，即按照存入日双方约定的第四期执行利率结息，利息转入客户的收益账户；"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(五)存满五期，即按照存入日双方约定的第五期执行利率结息，利息转入客户的收益账户；"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;八、提前支取。本协议存款为固定金额存款，无法部分提前支取，只能一次性支取全部存款本金。提前支取时，实际存期不足一"
        	  		+ "<br></p>期的，按照支取日唐山银行公布的活期存款利率计息；实际存期超过一期的，对于支取日至上次结息日期间的存款按照支取日唐山银"
        	  		+ "<br></p>行公布的活期存款利率计息，已结转的利息不再扣回。"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;九、协议变更或终止。客户同意：发生下列事项时，唐山银行可对本协议修改或提前终止：（1）如因本协议和/或本产品与相关"
        	  		+ "<br></p>政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止的；（2）国家有关法律、法规、规章、政策的"
        	  		+ "<br></p>改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）因不可抗力因素导致其必须修改或提前终止的；"
        	  		+ "<br></p>（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止日之前，将通过官方网站、手机短信、营业网点"
        	  		+ "<br></p>告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向唐山银行提出异议，否则公告期满后将视为客户"
        	  		+ "<br></p>同意上述变更。"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。本协议经客户签署"
        	  		+ "<br></p>且唐山银行营业网点审核同意并完成系统设置（银行打印）后生效。<br></p></html>");
        	        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        	        scrollPane.setViewportView(lblNewLabel);
            }else if("XF".equals(params.get("PRODUCT_CODE"))){ //幸福1+1
        	    lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
        	  		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;幸福1+1个人协议存款产品协议书</h2> "
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“千禧”系列个人协议存款产品是为客户提供的特定存期、特定收益的个人协"
        	  		+ "<br></p>议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照中国法律法规和有关的金融监管规定，经平等自愿协商"
        	  		+ "<br></p>一致，达成如下条款，承诺信守：<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、 声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
        	  		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议"
        	  		+ "<br></p>不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，实际存款金额以客户交付银行清点后，在本协议书银行打印栏记载为准。"
        	  		+ "<br></p>客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“幸福1+1”存款期限为1年零1天；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“合赢千禧”存款期限为18个月；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“溢彩千禧”存款期限为24个月；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“喜迎千禧”存款期限为1000天；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“惠德千禧”存款期限为40个月；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“祥瑞千禧”存款期限为50个月；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“福临千禧”存款期限为60个月；<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品涉及到的存款利率以本协议书银行打印栏记载内容为准；该利率以年化利率表示，换算成月利率时按一年"
        	  		+ "<br></p>12个月计算，换算成日利率时按一年360天计算。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、计息与自动转存。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品；产品到期后，不设自动转存功能，如客户仍未支取，到<br></p>"
        	  		+ "期日至支取日之间的存款利息将按照支取日唐山银行公布的活期存款利率计算。<br>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、提前支取。本协议存款为固定金额存款，无法部分提前支取,只能一次性支取全部存款本金。提前支取的存款按照支取日唐"
        	  		+ "<p>山银行公布的活期存款利率计息。<br>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、协议变更或终止。客户同意：发生下列事项时，唐山银行可对本协议修改或提前终止：（1）如因本协议和/或本产品与相"
        	  		+ "<br></p>关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止的；（2）国家有关法律、法规、规章、<br></p>"
        	  		+ "<p>政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）因不可抗力因素导致其必须修改或<br></p>"
        	  		+ "<p>提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止日之前，将通过官方网站、手<br></p>"
        	  		+ "<p>机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向唐山银行提出异议，否则公<br></p>"
        	  		+ "<p>告期满后将视为客户同意上述变更。<br></p>"
        	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 八、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。本协议"
        	  		+ "<br></p>经客户签署且唐山银行营业网点审核同意并完成系统设置（银行打印）后生效。<br></p></html>");
        	        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        	        scrollPane.setViewportView(lblNewLabel);
            }else if("QX".equals(params.get("PRODUCT_CODE"))){//千禧
            	 lblNewLabel = new JLabel("<html><h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp"
             	  		+ ";&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;千禧系列个人协议存款产品协议书</h2> "
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;唐山银行股份有限公司（以下简称“唐山银行”）“千禧”系列个人协议存款产品是为客户提供的特定存期、特定收益的个人协"
             	  		+ "<br></p>议存款产品。为规范唐山银行和客户双方在本服务下的权利义务，双方依照中国法律法规和有关的金融监管规定，经平等自愿协商"
             	  		+ "<br></p>一致，达成如下条款，承诺信守：<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一、 声明与保证。双方中的每一方均在此向另一方声明并保证该方具有完全适当的资格与能力订立、接受及履行本协议以及以其"
             	  		+ "<br></p>为一方的其他任何有关文件。客户保证本协议下的存款本金是其合法所有的资金并且依法可由本人存放和使用，订立和履行本协议"
             	  		+ "<br></p>不违反其应遵守的法令或任何协议/合同、承诺，不侵犯他人的合法权益。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;二、存款本金。本产品涉及到的存款本金为固定金额，实际存款金额以客户交付银行清点后，在本协议书银行打印栏记载为准。"
             	  		+ "<br></p>客户可存入一笔或多笔固定金额存款，存款账户数量不做限制。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;三、存款期限。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“幸福1+1”存款期限为1年零1天；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“合赢千禧”存款期限为18个月；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“溢彩千禧”存款期限为24个月；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“喜迎千禧”存款期限为1000天；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“惠德千禧”存款期限为40个月；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“祥瑞千禧”存款期限为50个月；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“福临千禧”存款期限为60个月；<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;四、存款利率。本产品涉及到的存款利率以本协议书银行打印栏记载内容为准；该利率以年化利率表示，换算成月利率时按一年"
             	  		+ "<br></p>12个月计算，换算成日利率时按一年360天计算。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;五、计息与自动转存。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自起息日起，产品本金即构成存款期限为办理产品约定期限的存款产品；产品到期后，不设自动转存功能，如客户仍未支取，到<br></p>"
             	  		+ "期日至支取日之间的存款利息将按照支取日唐山银行公布的活期存款利率计算。<br>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;六、提前支取。本协议存款为固定金额存款，无法部分提前支取,只能一次性支取全部存款本金。提前支取的存款按照支取日唐"
             	  		+ "<p>山银行公布的活期存款利率计息。<br>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;七、协议变更或终止。客户同意：发生下列事项时，唐山银行可对本协议修改或提前终止：（1）如因本协议和/或本产品与相"
             	  		+ "<br></p>关政策、法律法规、规章的规定或监管机构的要求等存在冲突导致其必须修改或提前终止的；（2）国家有关法律、法规、规章、<br></p>"
             	  		+ "<p>政策的改变、紧急措施的出台、市场环境发生重大变化导致其必须修改或提前终止的；（3）因不可抗力因素导致其必须修改或<br></p>"
             	  		+ "<p>提前终止的；（4）唐山银行认为应该修改或提前终止的其他情况。但唐山银行在修改生效日/终止日之前，将通过官方网站、手<br></p>"
             	  		+ "<p>机短信、营业网点告示等方式公告客户，若客户不同意上述变更，可自公告发布之日起三个工作日内向唐山银行提出异议，否则公<br></p>"
             	  		+ "<p>告期满后将视为客户同意上述变更。<br></p>"
             	  		+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 八、附则。本协议项下的及与之有关的一切争议，如友好协商不成，应交由协议履行地的人民法院诉讼解决。本协议"
             	  		+ "<br></p>经客户签署且唐山银行营业网点审核同意并完成系统设置（银行打印）后生效。<br></p></html>");
             	        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
             	        scrollPane.setViewportView(lblNewLabel);
            }
		
		// 同意框（没选中）
		backButton2 = new JButton(new ImageIcon("pic/initial.png"));
		backButton2.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton2.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backButton2.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		backButton2.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		backButton2.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		backButton2.setBorderPainted(false);// 设置是否绘制边框
		backButton2.setBorder(null);// 设置边框
		final Thread thread = new Thread("Strattime") {
			@Override
			public void run() {
				try {
					Thread.sleep(10000);
					backButton2.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							logger.info("点击同意框没有选中状态的同意按钮");
							// 同意
							change();
						}
					});
				} catch (InterruptedException e1) {
					logger.error(e1);
				}
			}
		};
		thread.start();

		backButton2.setSize(25, 25);
		backButton2.setLocation(100, 453);
		this.showJpanel.add(backButton2);

		// 同意框（选中）
		backButton3 = new JButton(new ImageIcon("pic/pitchon.png"));
		backButton3.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backButton3.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		backButton3.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		backButton3.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		backButton3.setBorderPainted(false);// 设置是否绘制边框
		backButton3.setBorder(null);// 设置边框
		backButton3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击同意框选中状态的同意按钮");
				// 同意
				change();
			}
		});
		backButton3.setSize(25, 25);
		backButton3.setLocation(100, 453);
		this.showJpanel.add(backButton3);

		// 图片
		JLabel accImage = new JLabel();
		accImage.setIcon(new ImageIcon("pic/ico_wxts.png"));
		accImage.setIconTextGap(6);
		accImage.setBounds(89, 483, 36, 36);
		this.showJpanel.add(accImage);

		JLabel lblNewLabel_1 = new JLabel("温馨提示：请向大堂经理索取或自取纸质产品说明书和协议");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(131, 486, 435, 23);
		this.showJpanel.add(lblNewLabel_1);

		lblNewLabel_3 = new JLabel("(" + n + ")");
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(350, 519, 54, 50);	
		this.showJpanel.add(lblNewLabel_3);

		// 同意(未选中)
		backButton = new JButton(new ImageIcon("pic/ty1.png"));
		backButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		backButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		backButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		backButton.setBorderPainted(false);// 设置是否绘制边框
		backButton.setBorder(null);// 设置边框
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击同意按钮");
				// 同意
			}
		});
		backButton.setSize(200, 50);
		backButton.setLocation(227, 519);
		this.showJpanel.add(backButton);

		// 同意
		backButton1 = new JButton(new ImageIcon("pic/ty.png"));
		backButton1.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton1.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		backButton1.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		backButton1.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		backButton1.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		backButton1.setBorderPainted(false);// 设置是否绘制边框
		backButton1.setBorder(null);// 设置边框
		backButton1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击同意按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 同意
				okTrans();
			}
		});

		backButton1.setSize(200, 50);
		backButton1.setLocation(227, 519);
		this.showJpanel.add(backButton1);

		// 不同意
		JButton okButton = new JButton(new ImageIcon("pic/bty.png"));

		okButton.setHorizontalTextPosition(SwingConstants.CENTER);
		okButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		okButton.setOpaque(false);// 设置控件是否透明，true为不透明，false为透明
		okButton.setContentAreaFilled(false);// 设置图片填满按钮所在的区域
		okButton.setFocusPainted(true);// 设置这个按钮是不是获得焦点
		okButton.setBorderPainted(false);// 设置是否绘制边框
		okButton.setBorder(null);// 设置边框
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击不同意按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				// 不同意
				backTrans();
			}

		});
		okButton.setSize(200, 50);
		okButton.setLocation(594, 519);
		this.showJpanel.add(okButton);
		agreeTimer= new Timer(n*100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logger.info("点击同意按钮前的倒计时变化");
				updateTimeText();
			}
		});
		agreeTimer.start();
		
		lblNewLabel_2 = new JLabel("本人已了解并理解上述内容，自愿遵守相关规定，并对协议予以确认。");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(131, 453, 490, 23);
		this.showJpanel.add(lblNewLabel_2);
		
		// 上一步
		JLabel submitBtn = new JLabel(new ImageIcon("pic/preStep.png"));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击上一步按钮");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				/* 处理上一页 */
				backUp();
			}
		});
		submitBtn.setSize(150, 50);
		submitBtn.setLocation(1075, 770);
		add(submitBtn);

		// 返回
		JLabel backButton = new JLabel(new ImageIcon("pic/newPic/exit.png"));
		backButton.setBounds(1258, 770, 150, 50);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				logger.info("点击返回");
				if(!on_off){
					logger.info("开关当前状态"+on_off);
					return;
				}
				logger.info("开关当前状态"+on_off);
				on_off=false;
				clearTimeText();
				stopTimer(voiceTimer);//关闭语音
		    	closeVoice();
				openPanel(new OutputBankCardPanel());
			}
		});
		add(backButton);

	}
	
	/**
	 * 返回上一步
	 */
	public void backUp(){
		logger.info("进入返回上一步的方法");
		clearTimeText();// 清空倒计时
		openPanel(new AccOkInputDepInfoPanel(params));
	}

	/**
	 * 返回
	 * */
	public void backTrans() {
		logger.info("进入返回业务选择的方法");
		clearTimeText();// 清空倒计时
		AccountTradeCodeAction.transBean.setIsSign("0");
		AccountTradeCodeAction.transBean.setIsCheckedPic("");
		AccountTradeCodeAction.transBean.setDepUnit("");
		AccountTradeCodeAction.transBean.setZzAmt(AccountTradeCodeAction.transBean.getBeiZzAmt());
		if("1".equals(AccountTradeCodeAction.transBean.getTransChangeNo().trim())){
			AccountTradeCodeAction.transBean.setTransChangeNo("0");
		}
		// 返回业务选择页
		openPanel(new AccChooseBusiness(params));
	}

	/**
	 * 确认
	 * */
	public void okTrans() {
		logger.info("进入确认方法");
		//开户金额
		Float money = Float.valueOf(transBean.getMoney().trim());
		
		//开户授权超限金额20万
		String amt = Property.getProperties().getProperty("acc_open_tellNoAmt");
		Float accOpenAmt = Float.valueOf(amt);
		if(money>=accOpenAmt){
			logger.info("金额大于等于20万进入授权页面");
			clearTimeText();//清空倒计时
			stopTimer(voiceTimer);//关语音
			closeVoice();//关语音流
			openPanel(new AccAuthorNoPanel(params));
		}else{
			if(transBean.getProductCode().startsWith("JX")){
				clearTimeText();// 清空倒计时
				stopTimer(voiceTimer);// 关语音
				closeVoice();// 关语音流
				transBean.setCertPrint("0");
				params.put("transCode", "0015");
				AccountTransFlow.startTransFlow(comp, params);
			}else{
				
				openConfirmDialog("是否打印存单。是：打印。否：不打印，到期后本息自动转入银行卡。");
				//打印存单
				confirmDialog.YseButten.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("选择打印存单按钮");
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关语音
						closeVoice();// 关语音流
						transBean.setCertPrint("1");
						confirmDialog.disposeDialog();
						params.put("transCode", "0015");
						AccountTransFlow.startTransFlow(comp, params);
					}
				});
				//不打印存单
				confirmDialog.Nobutton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						logger.info("选择否不打印存单");
						clearTimeText();// 清空倒计时
						stopTimer(voiceTimer);// 关语音
						closeVoice();// 关语音流
						transBean.setCertPrint("0");
						confirmDialog.disposeDialog();
						params.put("transCode", "0015");
						AccountTransFlow.startTransFlow(comp, params);
					}
				});
			}
		}
		
	}

	/**
	 * 变化
	 * */
	public void change() {
		logger.info("进入变化方法");
		if (change == "1") {
			change = "2";
			backButton.setVisible(false);
			backButton1.setVisible(true);
			backButton2.setVisible(false);
			backButton3.setVisible(true);
		} else {
			change = "1";
			backButton.setVisible(true);
			backButton1.setVisible(false);
			backButton2.setVisible(true);
			backButton3.setVisible(false);
		}

	}

	/***
	 * 设置剩余时间
	 */
	private void updateTimeText() {
		logger.info("同意按钮前的倒计时");
		n = n - 1;
		lblNewLabel_3.setText("(" + String.valueOf(n) + ")");

		if (n == 0) {
			agreeTimer.stop();
			lblNewLabel_3.setText("");
		}
	}
}
