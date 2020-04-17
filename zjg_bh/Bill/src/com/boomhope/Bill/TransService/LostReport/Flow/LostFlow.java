package com.boomhope.Bill.TransService.LostReport.Flow;

import com.boomhope.Bill.Framework.BaseTransPanelNew;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicAuthorFingerprintPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicAuthorNoPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.Author.AllPublicAuthorPassPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicCheckAgentPhotos;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicCheckPhotos;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicDeputySelectionPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicInputAgentIdCardPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicInputIdCardPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.IDCard.AllPublicPrintCameraPanel;
import com.boomhope.Bill.TransService.AllTransPublicPanel.InputCardAndPwd.AllPublicInputPasswordPanel;
import com.boomhope.Bill.TransService.LostReport.Page.SelectLostOrSolveLostPanel;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostCheckIsCancelPage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostCheckIsReceivePage;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostConfirmPanel;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostInputPhoneAndAdress;
import com.boomhope.Bill.TransService.LostReport.Page.Lost.LostPassSelectPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostApplNoInfosConfirmPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostInputApplyNoPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostSelectPage;
import com.boomhope.Bill.TransService.LostReport.Page.SolveLost.SolveLostTypeSelectPage;


/**
 * Title: 挂失解挂子业务路由
 *
 * @author: hk
 * 
 * @date: 2018年3月23日 上午10:27:25  
 * 
 */
@SuppressWarnings({ "static-access", "serial" })
public class LostFlow extends BaseTransPanelNew {

	// 交易路由
	/**
	 * @Description: 
	 * @Author: hk
	 * @date 2018年3月23日 下午4:30:04
	 */
	public void flow() {
		// 下一步页面名称
		String nextStepName = lostPubBean.getNextStepName();
		switch (nextStepName) {
		case "BACK_HOME":// 返回首页
			returnHomeTwo(lostPubBean.getThisComponent());
			break;
		case "SELECT_LOST_OR_PANEL"://首页进入，下一步进入选择挂失还是解挂
			lostPubBean.setNextStepName("ALL_PUBLIC_DEPUTY_SELECTION_PANEL");
			lostPubBean.setUpStepName("BACK_HOME");
			openPanel(lostPubBean.getThisComponent(),
					new SelectLostOrSolveLostPanel());
			break;
		case "ALL_PUBLIC_DEPUTY_SELECTION_PANEL":// 下一步进入选择代理人页面
			lostPubBean.setNextStepName("ALL_PUBLIC_INPUT_ID_CARD_PANEL");// 下一步插入本人身份证
			lostPubBean.setUpStepName("SELECT_LOST_OR_PANEL");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicDeputySelectionPanel());
			break;
		case "ALL_PUBLIC_INPUT_ID_CARD_PANEL":// 选择完代理人进入插入本人身份证页面
			lostPubBean.setNextStepName("ACTION_MYSELFIDCARD");// 下一步校验本人身份信息
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicInputIdCardPanel());
			break;
		case "ALL_PUBLIC_PRINT_CAMERA_PANEL":// 下一步进入拍照页面
			lostPubBean.setNextStepName("ACTION_CAMERA");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicPrintCameraPanel());
			break;
		case "ALL_PUBLIC_INPUT_AGENT_ID_CARD_PANEL":// 下一步插入代理人身份证
			lostPubBean.setNextStepName("ACTION_AGENTIDCARD");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicInputAgentIdCardPanel());
			break;
		case "LOST_PASS_SELECT_PAGE":// 下一步选择是否记得密码
			lostPubBean.setUpStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
			openPanel(lostPubBean.getThisComponent(), new LostPassSelectPage());
			break;
		case "ALL_PUBLIC_CHECK_PHOTOS_PANLE":// 下一步进入查看本人身份照片
			lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicCheckPhotos());
			break;
		case "ALL_PUBLIC_CHECK_AGENT_PHOTOS_PANLE":// 下一步进入查看代理人身份照片
			lostPubBean.setNextStepName("LOST_CONFIRM_PANEL");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicCheckAgentPhotos());
			break;
		case "ALL_PUBLIC_AUTHOR_NO_PANEL":// 下一步进入授权页面
			lostPubBean.setUpStepName("LOST_CONFIRM_PANEL");
			lostPubBean.setNextStepName("ACTION_CHECK_AUTHOR_METHOD");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicAuthorNoPanel());
			break;
		case "ALL_PUBLIC_AUTHOR_PASS_PANEL":// 下一步进入密码授权页面
			lostPubBean.setNextStepName("ACTION_AUTHORING");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicAuthorPassPanel());
			break;
		case "ALL_PUBLIC_AUTHOR_FINGER_PRINT_PANEL":// 下一步进入指纹授权
			lostPubBean.setNextStepName("ACTION_AUTHORING");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicAuthorFingerprintPanel());
			break;
		case "ALL_PUBLIC_ACC_INPUT_PWD_PANEL":// 下一步进入指纹授权
			lostPubBean.setNextStepName("ACTION_PWD");
			openPanel(lostPubBean.getThisComponent(),
					new AllPublicInputPasswordPanel());
			break;
		case "LOST_CONFIRM_PANEL":// 下一步进入确认信息页面
			if("0".equals(lostPubBean.getLOrS())){//挂失的确认信息页面
				openPanel(lostPubBean.getThisComponent(), new LostConfirmPanel());
			}else{//解挂的确认信息页面
				openPanel(lostPubBean.getThisComponent(),new SolveLostApplNoInfosConfirmPage());
			}
			break;
		case "LOST_CHECK_IS_RECEIVE_PAGE"://下一步进入选择是否补领页面
			openPanel(lostPubBean.getThisComponent(),new LostCheckIsReceivePage());
			break;
		case "LOST_CHECK_IS_CANCEL_PAGE"://下一步进入选择是否立即销户
			openPanel(lostPubBean.getThisComponent(),new LostCheckIsCancelPage());
			break;
		case "ALL_PUBLIC_INPUT_PASSWORD_PANEL"://下一步进入输入密码页面
			lostPubBean.setNextStepName("ACTION_PWD");
			openPanel(lostPubBean.getThisComponent(),new AllPublicInputPasswordPanel());
			break;
		case "LOST_INPUT_PHONE_AND_ADRESS"://下一步进入代理人信息展示页面
			lostPubBean.setNextStepName("ALL_PUBLIC_PRINT_CAMERA_PANEL");
			openPanel(lostPubBean.getThisComponent(),new LostInputPhoneAndAdress());
			break;
		case "SOLVE_LOST_INPUT_APPLY_NO_PAGE"://下一步进入录入挂失申请号页面
			openPanel(lostPubBean.getThisComponent(),new SolveLostInputApplyNoPage());
			break;
		case "ALL_PUBLIC_ACC_INPUT_PWD_PANEL_REMAKE"://下一步进入输入密码页面
			lostPubBean.setNextStepName("ACTION_CHECK_RE_PWD");
			openPanel(lostPubBean.getThisComponent(),new AllPublicInputPasswordPanel());
			break;
		case "SOLVE_LOST_SELECT_PAGE"://下一步进入选择解挂补发销户撤销页面
			openPanel(lostPubBean.getThisComponent(), new SolveLostSelectPage());
			break;
		case "SOLVE_LOST_TYPE_SELECT_PAGE"://下一步进入解挂业务种类选择页面
			openPanel(lostPubBean.getThisComponent(),new SolveLostTypeSelectPage());
			break;
			
		case "ACTION_CAMERA"://下一步进入拍照后人脸识别方法
			lostAction.camera();
			break;
		case "ACTION_CHECK_AUTHOR_METHOD":// 下一步进入查询授权方式的方法
			lostAction.checkAuthorMod();
			break;
		case "ACTION_AUTHORING":// 下一步进入授权的方法
			lostAction.authorIng();
			break;
		case "ACTION_MYSELFIDCARD":// 读取完本人身份证信息后进行校验
			lostAction.checkMySelfIdCardInfos();
			break;
		case "ACTION_AGENTIDCARD":// 读取完代理人身份证信息后进行校验
			lostAction.checkAgentIdCardInfos();
			break;
		case "ACTION_PWD":// 输入密码后校验秘密
			lostAction.checkAccPwd();
			break;
		case "ACTION_CHECK_RE_PWD"://校验重置的密码是否符合要求
			lostAction.checkReMakePwd();
			break;
		}
	}

}
