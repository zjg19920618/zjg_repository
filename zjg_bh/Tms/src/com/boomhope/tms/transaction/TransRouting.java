package com.boomhope.tms.transaction;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/***
 * 服务端交易路由处理
 * @author shaopeng
 *
 */
public class TransRouting {
	Logger logger = Logger.getLogger(TransRouting.class);
	/***
	 * 交易分发
	 */
	public String Routing(String msg){
		
		String retMsg = "";	// 返回信息
		String transCode = "";	// 交易代码
		/* 从报文头中解析交易码 */
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new StringReader(msg));
			
			// 获取根元素
			Element rootElement = document.getRootElement();
			Element headElement = rootElement.element("Head");
			Element tradeCodeElement = headElement.element("TradeCode");
			transCode = tradeCodeElement.getTextTrim();
			logger.debug("transCode:" + transCode);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* 根据交易码进行交易处理 */
		switch (transCode){
			case "TMS_0001":	// 设备登录
				return new TransTms0001().handle(msg);
			case "TMS_0002":	// 外设状态上送
				return new TransTms0002().handle(msg);
			case "TMS_0004":	// 管理员查询/
				return new TransTms0004().handle(msg);
			case "TMS_0005":	// 凭证信息查询
				return new TransTms0005().handle(msg);
			case "TMS_0006":	// 更改凭证信息
				return new TransTms0006().handle(msg);
			case "TMS_0007":	// 鉴伪流水记录
				return new TransTms0007().handle(msg);
			case "TMS_0008":	//行名行号查询
				return new TransTms0008().handle(msg);
			case "TMS_0009":	//开关状态查询
				return new TransTms0009().handle(msg);
			case "ACCOUNT_0001":	// 开户入库方法
				return new TransTmsAccountOrder().handle(msg);
			case "BCK_0001":	// 回单机-证件信息查询
				return new TransBCK0001().handle(msg);
			case "BCK_0002":	// 回单机-账号信息综合查询
				return new TransBCK0002().handle(msg);
			case "BCK_0003":	// 回单机-普通定期存单预计利息信息查询
				return new TransBCK0003().handle(msg);
			case "BCK_0004":	// 回单机-个人账户销户
				return new TransBCK0004().handle(msg);
			case "BCK_0005":	// 回单机-唐豆收回查询
				return new TransBCK0005().handle(msg);
			case "BCK_0006":	// 回单机-唐豆收回
				return new TransBCK0006().handle(msg);
			case "BCK_0007":	// 回单机-卡信息查询
				return new TransBCK0007().handle(msg);
			case "BCK_0008":	// 回单机-身份核查
				return new TransBCK0008().handle(msg);
			case "BCK_0009":	// 回单机-柜员认证方式查询
				return new TransBCK0009().handle(msg);
			case "BCK_0010":	// 回单机-柜员授权
				return new TransBCK0010().handle(msg);
			case "BCK_0011":	// 回单机-代理人身份信息核对
				return new TransBCK0011().handle(msg);
			case "BCK_0012":	// 客户手机绑定信息查询
				return new TransBCK0012().handle(msg);
			case "BCK_0013":	// 单条短信发送
				return new TransBCK0013().handle(msg);
			case "BCK_0014":	// 凭证信息综合查询
				return new TransBCK0014().handle(msg);
			case "BCK_0015":	// 待打印存单查询
				return new TransBCK0015().handle(msg);
			case "BCK_0016":	// 存单打印
				return new TransBCK0016().handle(msg);
			case "BCK_0017":	// 存单打印状态变更
				return new TransBCK0017().handle(msg);
			case "BCK_0018":	// 子账户列表查询
				return new TransBCK0018().handle(msg);
			case "BCK_0019":	//账户信息查询及密码验证
				return new TransBCK0019().handle(msg);
			case "BCK_0020":	//产品利率信息查询
				return new TransBCK0020().handle(msg);
			case "BCK_0021":	//卡系统 子账户销户
				return new TransBCK0021().handle(msg);
			case "BCK_0022":	//电子账户 子账户销户
				return new TransBCK0022().handle(msg);
			case "BCK_01597":	// 回单机-黑白名单
				return new TransBCK01597().handle(msg);
			case "BCK_02808":	// 回单机-个人存款开户
				return new TransBCK02808().handle(msg);
			case "BCK_02864":	// 回单机-产品利率信息查询
				return new TransBCK02864().handle(msg);
			case "BCK_02867":	// 回单机-产品利率信息查询
				return new TransBCK02867().handle(msg);
			case "BCK_03445":	// 证件信息查询
				return new TransBCK03445().handle(msg);
			case "BCK_03446":	// 回单机-个人客户建立
				return new TransBCK03446().handle(msg);
			case "BCK_03510":	// 回单机-产品预计利息(24小时)
				return new TransBCK03510().handle(msg);
			case "BCK_03511":	// 回单机-产品可开立额度信息查询
				return new TransBCK03511().handle(msg);
			case "BCK_03512":	// 回单机-如意存明细查询
				return new TransBCK03512().handle(msg);
			case "BCK_03514":
				return new TransBCK03514().handle(msg);
			case "BCK_03524":	//客户评级查询03524
				return new TransBCK03524().handle(msg);
			case "BCK_03845":	// 回单机-卡信息查询
				return new TransBCK03845().handle(msg);
			case "BCK_03870":	// 回单机-存款开户积享存
				return new TransBCK03870().handle(msg);
			case "BCK_07505":	// 回单机-糖豆兑换
				return new TransBCK07505().handle(msg);
			case "BCK_07506":	// 回单机-糖豆规则查询
				return new TransBCK07506().handle(msg);
			case "BCK_07518":	//存款关联账号查询
				return new TransBCK07518().handle(msg);
			case "BCK_07519":	//个人客户查询
				return new TransBCK07519().handle(msg);
			case "BCK_07520":	//根据客户号查询所有卡号信息
				return new TransBCK07520().handle(msg);
			case "BCK_07522":	//子账号开户流水查询
				return new TransBCK07522().handle(msg);
			case "BCK_07523":	//银行卡换开查询原卡号
				return new TransBCK07523().handle(msg);
			case "BCK_07663":	// 回单机-钱柜糖豆
				return new TransBCK07663().handle(msg);
			case "BCK_07659":   //回单机-柜员查询
				return new TransBCK07659().handle(msg);
			case "BCK_07660":    //柜员授权
				return new TransBCK07660().handle(msg);
			case "BCK_07492":	//转账客户列表信息查询
				return new TransBCK07492().handle(msg);
			case "BCK_02224":	//小额普通贷记往帐录入
				return new TransBCK02224().handle(msg);
			case "BCK_03521":	//账户信息查询及密码验证
				return new TransBCK03521().handle(msg);
			case "BCK_02600":	//行内汇划
				return new TransBCK02600().handle(msg);
			case "BCK_CM021":	//大小额系统参数查询
				return new TransBCKCM021().handle(msg);
			case "BCK_01569":	//机构关系查询交易
				return new TransBCK01569().handle(msg);
			case "BCK_02013":	//大额普通汇兑往帐发送交易接口	
				return new TransBCK02013().handle(msg);
			case "BCK_07494":	//单日累计划转金额查询
				return new TransBCK07494().handle(msg);
			case "BCK_01118":	//根据机构号查询支付行信息
				return new TransBCK01118().handle(msg);
			case "BCK_02956":	//权限明细查询
				return new TransBCK02956().handle(msg);
			case "BCK_07495":	//核心节假日查询
				return new TransBCK07495().handle(msg);
			case "BCK_07498":	//转入唐宝账户查询【55060】-前置07498
				return new TransBCK07498().handle(msg);
			case "BCK_07499":	//唐宝x号转入【79100】-前置07499
				return new TransBCK07499().handle(msg);
			case "BCK_04422":	//反洗钱
				return new TransBCK04422().handle(msg);
			case "MoneyBox_QueryOrder":	// 回单机-钱柜订单查询
				return new TransTmsMoneyBoxQueryOrder().handle(msg);
			case "MoneyBox_TransApply":	// 回单机-钱柜业务申请
				return new TransTmsMoneyBoxTransApply().handle(msg);
			case "MoneyBox_TransCommit":	// 回单机-钱柜业务提交
				return new TransTmsMoneyBoxTransCommit().handle(msg);
			case "BCK_07670":	// 回单机-身份联网核查
				return new TransBCK07670().handle(msg);
			case "BCK_QRY00"://查询核心工作日期
				return new TransBCKQRY00().handle(msg);
			case "BCK_03453"://单位卡IC卡验证（核心77102）-前置03453
				return new TransBCK03453().handle(msg);
			case "BCK_CM022"://定时发送交易信息查询-前置CM022
				return new TransBCKCM022().handle(msg);	
			case "BCK_02693"://定时发送交易撤销-前置02693
				return new TransBCK02693().handle(msg);	
			case "BCK_07602"://个人IC卡验证(卡75048)	-前置【07602】
				return new TransBCK07602().handle(msg);
			case "BCK_02954"://校验对手信息【77016】(前置交易码02954)
				return new TransBCK02954().handle(msg);
			case "BCK_03740"://权限明细查询【78010】(前置交易码03740)
				return new TransBCK03740().handle(msg);
			case "BCK_02781"://账户限额查询【02879】-前置02781
				return new TransBCK02781().handle(msg);
			case "BCK_07496"://转账客户列表信息删除-前置07496
				return new TransBCK07496().handle(msg);
			case "BCK_08021"://代理人身份信息核对08021
				return new TransBCK08021().handle(msg);
			case "BCK_07601"://账号信息综合查询【02880】-前置07601
				return new TransBCK07601().handle(msg);
			case "BCK_07696"://普通定期存单预计利息【02944】-前置07696
				return new TransBCK07696().handle(msg);
			case "BCK_07622"://唐豆收回查询【02210】-前置07622
				return new TransBCK07622().handle(msg);
			case "BCK_07665"://唐豆收回【02209】-前置07665
				return new TransBCK07665().handle(msg);
			case "BCK_03519"://子账户列表查询-【75109】前置03519
				return new TransBCK03519().handle(msg);
			case "BCK_07819"://电子账户子账户列表查询【35109】（直连电子账户）-前置07819
				return new TransBCK07819().handle(msg);
			case "BCK_07624"://核心系统 个人账户销户【02026】-前置07624 
				return new TransBCK07624().handle(msg);
			case "BCK_03517"://卡系统 子账户销户【75104】前置03517
				return new TransBCK03517().handle(msg);
			case "BCK_03522"://电子账户 子账户销户【35104】-前置03522
				return new TransBCK03522().handle(msg);
			case "BCK_07515"://唐豆信息查询【02217】-前置07515
				return new TransBCK07515().handle(msg);
			case "BCK_03531"://卡系统-交易辅助登记【74073】前置03531
				return new TransBCK03531().handle(msg);
			case "BCK_03532"://推荐人奖励领取【17030】前置-03532
				return new TransBCK03532().handle(msg);
			case "BCK_03533"://推荐信息录入【17034】前置-03533
				return new TransBCK03533().handle(msg);
			case "BCK_02906"://客户账户基本信息维护【17024】前置-02906
				return new TransBCK02906().handle(msg);
			case "BCK_77017"://按交易授权前置77017
				return new TransBCK77017().handle(msg);
			case "openAccMsg"://开户路径信息查询
				return new TransTmsCdjOpenAccMsg().handle(msg);
			default:
		}
		
		return retMsg;
	}

}
