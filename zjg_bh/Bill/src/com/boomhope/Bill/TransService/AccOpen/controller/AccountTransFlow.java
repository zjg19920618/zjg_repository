package com.boomhope.Bill.TransService.AccOpen.controller;

import java.awt.Component;
import java.util.Map;

/**
 * 开户流程控制类
 * @author wang。xm
 *
 */
@SuppressWarnings("serial")
public class AccountTransFlow {
	
	/**
	 * 开始调用流程
	 * @param params
	 */
	public static void startTransFlow(final Component thisComp,final Map params){
		new Thread(){
			@Override
			public void run() {
//				BaseTransPanel.prossDialog.showDialog();
				transFlowControll(thisComp,params);
			}
		}.start();
	}
	
	/**
	 * 开户流程控制
	 * @param params
	 */
	public static void transFlowControll(Component thisComp,Map params){
		AccountTradeCodeAction accAction=new AccountTradeCodeAction();
		String transCode=(String)params.get("transCode");
		if("0000".equals(transCode)){//0000进入是读取卡信息页面
			//判断返回信息若读卡成功打开密码页面
			//若读卡失败进入业务终止页面
			accAction.ACC0000(thisComp, params);
		}else if("0001".equals(transCode)){//0001是读取卡信息页面
			//判断返回信息若读卡成功打开密码页面
			//若读卡失败进入业务终止页面
			accAction.ACC0001(thisComp, params);
		}else if("0002".equals(transCode)){//0002是银行卡密码读取页
			//获取密码失败进入服务终止页
			//获取密码成功
			//卡查询接口
			//若密码错误调错误信息提示弹框不跳页面，显示密码错误次数提示
			//证件信息查询
			//查询灰白名单
			//查询失败，或者返回不通过统一进服务终止页。
			accAction.ACC0002(thisComp, params);
		}else if("0003".equals(transCode)){//0003产品选择页
			//整存争取
			//协议存款
			//私行快线
			//普惠产品
			accAction.ACC0003(thisComp, params);
		}else if("0004".equals(transCode)){//0004整存争取信息输入页面
			//选择金额和存期后产品利息试算
			//判断金额是否大于5万大于于进入授权页面
			//若不大于进入信息确认页面
			accAction.ACC0004(thisComp, params);
		}else if("0005".equals(transCode)){//0005产品信息确认页
			//点击确认
			//进入银行卡密码输入页
			accAction.ACC0005(thisComp, params);
		}else if("0015".equals(transCode)){//0015银行卡密码输入页
			//密码输入成功
			//子账户开户
			//开户失败进入服务终止页
			
			//唐豆查询
			//唐豆兑付
			//唐豆失败弹框提示
			
			//选择框是否答应存单
			//选择否进入交易成功提示页
			
			//打印失败进入服务终止页，
			//打印成功进入交易成功提示页
			accAction.ACC0015(thisComp, params);
		}else if("0006".equals(transCode)){//0006授权成功后进入存单信息确认
			accAction.ACC0006(thisComp, params);
		}else if("0007".equals(transCode)){//0007授权页面第一页是否代理人
			//选择是或否都进入插入本账户对应的身份证
			accAction.ACC0007(thisComp, params);
		}else if("0008".equals(transCode)){//0008本账户对应身份证读取页面
			//读取身份证成功进行联网核查
			//核查不通过进行是否再操作提示
			//核查通过
			//若本人办理 进入摄像头拍摄页面
			//若代理人办理进入插入代理人身份证页面
			//身份证读取失败，进入服务终止页
			accAction.ACC0008(thisComp, params);
		}else if("0009".equals(transCode)){//0009读取代理人身份证信息
			//读取身份证成功进行联网核查
			//核查不通过进行是否再操作提示
			//核查通过 进入摄像头拍摄页面
			//身份证读取失败，进入服务终止页
			accAction.ACC0009(thisComp, params);
		}else if("0010".equals(transCode)){//0010摄像头拍摄页面
			//拍摄成功，进入图片校验页
			//拍摄失败进入服务终止页
			accAction.ACC0010(thisComp, params);
		}else if("0011".equals(transCode)){//0011图片校验页面
			//校验通过进入授权页
//			accAction.ACC0011(thisComp, params);
		}else if("0012".equals(transCode)){//0012授权页面 输入柜员号
			//检查柜员号
			//不通过错误框提示
			//通过根据结果判断是密码授权，还是指纹授权
			//根据结果跳转不同的授权
		}else if("0013".equals(transCode)){//0013密码授权页
			//密码通过进入产品信息确认页
			//不通过错误提示框提示
		}else if("0014".equals(transCode)){//0014指纹授权页
			//授权通过进入产品信息确认页
			//不通过错误提示框提示
		}else if("0016".equals(transCode)){//0016协议、私行产品选择页
			//查询子产品
			//点击进入子产品选择页
			accAction.ACC0016(thisComp, params);
		}else if("0017".equals(transCode)){//如意存+、积享存子产品查询
			accAction.ACC0017(thisComp, params);
		}else if("0018".equals(transCode)){//打印存单处理
			//查询凭证号，调存单打印，更新凭证号
			
		}else if("0020".equals(transCode)){//0020产品信息录入
			//产品信息录入
			//选择金额后产品利息试算
			accAction.ACC0020(thisComp, params);
		}else if("0021".equals(transCode)){//0021是否进入授权
			//判断金额是否大于5万大于于进入授权页面
			//若不大于进入信息确认页面
			accAction.ACC0021(thisComp, params);
		}
	}
	
}
