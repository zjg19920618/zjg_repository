var mapStatusTimer = null;
$(function(){
	//busGoto("../../html/terminalInformation.jsp");//默认跳转的页面
	var kk;
	//产品信息管理
	$("#productInformation").click(function(){
	//	$(this).addClass('choice');
	});
	//终端信息管理
	$("#terminalInformation").click(function(){
		
	});
	//运维管理
	$("#AppOps").click(function(){
		
	});
	//终端监控
	$("#terminalMonitoring").click(function(){
		
	});
	//系统管理
	$("#systemManagement").click(function(){
		
	});
	//业务管理
	$("#businessManagement").click(function(){
		
		
	});
	 
	//公共跳转页面
	function busGoto(fileName)
	{
		$.get(fileName,{},function(res){
			$("#content").html(res);
		});
	}
	

});
//事件方法封装
function dateNew(date){
    var nn = date.substring(0,4);//年
    var nn2 = date.substring(4,6);//月
    var nn3 = date.substring(6,8);//日
    var nn4 = date.substring(8,10);//时
    var nn5 = date.substring(10,12);//分
    var nn6 = date.substring(12,14);//秒
   
    return nn+"-"+nn2+"-"+nn3+" "+nn4+":"+nn5+":"+nn6;
}
//弹出框
function Pop(sure,remind,tishi){
	this.sure=sure;
	this.remind=remind;
	this.tishi=tishi;
	$(".TS_bank").append("<div class='remind_sure'><div class='firm_add_head'><span>提示</span></div><div class='remind_sure_content'><img src='../../img/common/"+this.tishi+".png'/><span>确认</span><span class='remind_text'>"+this.sure+"</span><span>?</span></div><div><button class='remind_no'>取消</button><button class='"+this.remind+"' id='"+this.remind+"'>确定</button></div></div>")
	$(".mask").show();
	$(".remind_sure").show();
	$(".table_menu").hide();
	$(".remind_no").click(function(){
		$(".remind_sure").remove();
		$(".remind_finish").remove();
		$(".mask").hide();
		$(".firm_add_edit").hide();
	});
};
function Tishi(sure,tishi){
	this.sure=sure;
	this.tishi=tishi;
	$(".TS_bank").append("<div class='remind_finish'><div class='firm_add_head'><span>提示</span></div><div class='remind_sure_content'><img src='../../img/common/"+this.tishi+".png'/><span class='remind_text'>"+this.sure+"</span><span>!</span></div><div><button class='remind_finish_button'>确定</button></div></div>")
	$(".mask").show();
	$(".remind_finish").show();
	$(".table_menu").hide();
	$(".remind_finish_button").click(function(){
		$(".remind_finish").hide();
		$(".remind_sure").remove();
		$(".mask").hide();
		$(".firm_add_edit").hide();
	});
};
/*
//图片预加载
var images = new Array();
var preImg = {images:[]};
function preload() {
    for (i = 0; i < preload.arguments.length; i++) {
    	preImg.images[i] = new Image();
        preImg.images[i].src = preload.arguments[i];
    }
}
preload("../../img/map/city_zhs.png",
		"../../img/map/city_qxx.png",
		"../../img/map/city_cfdq.png",
		"../../img/map/city_fnq.png",
		"../../img/map/city_frq.png",
		"../../img/map/city_gyq.png",
		"../../img/map/city_kpq.png",
		"../../img/map/city_lbq.png",
		"../../img/map/city_lnq.png",
		"../../img/map/city_lnx.png",
		"../../img/map/city_ltkfq.png",
		"../../img/map/city_ltx.png",
		"../../img/map/city_lx.png",
		"../../img/map/city_qas.png",
		"../../img/map/city_ytx.png",
		"../../img/map/city_zhs.gif",
		"../../img/map/city_qxx.gif",
		"../../img/map/city_cfdq.gif",
		"../../img/map/city_fnq.gif",
		"../../img/map/city_frq.gif",
		"../../img/map/city_gyq.gif",
		"../../img/map/city_kpq.gif",
		"../../img/map/city_lbq.gif",
		"../../img/map/city_lnq.gif",
		"../../img/map/city_lnx.gif",
		"../../img/map/city_ltkfq.gif",
		"../../img/map/city_ltx.gif",
		"../../img/map/city_lx.gif",
		"../../img/map/city_qas.gif",
		"../../img/map/city_ytx.gif",
		"../../img/map/city_zhs/city_zhs_zhzh.png",
		"../../img/map/city_zhs/city_zhs_zhflczh.png",
		"../../img/map/city_qxx/city_qxx_qxzh.png",
		"../../img/map/city_qas/city_qas_qazh.png",
		"../../img/map/city_qas/city_qas_qaxyzh.png",
		"../../img/map/city_cfdq/city_cfdq_cfdzh.png",
		"../../img/map/city_cfdq/city_cfdq_cfdgyqzh.png",
		"../../img/map/city_cfdq/city_cfdq_nbzh.png",
		"../../img/map/city_fnq/city_fnq_fnhfsqzh.png",
		"../../img/map/city_fnq/city_fnq_fnzh.png",
		"../../img/map/city_frq/city_frq_gysqzh.png",
		"../../img/map/city_frq/city_frq_frzh.png",
		"../../img/map/city_gyq/city_gyq_lxzh.png",
		"../../img/map/city_gyq/city_gyq_zgzzh.png",
		"../../img/map/city_gyq/city_gyq_gyzh.png",
		"../../img/map/city_kpq/city_kpq_lnzh.png",
		"../../img/map/city_kpq/city_kpq_mjgzh.png",
		"../../img/map/city_kpq/city_kpq_xylzh.png",
		"../../img/map/city_lbq/city_lbq_lgzh.png",
		"../../img/map/city_lbq/city_lbq_gmzh.png",
		"../../img/map/city_lbq/city_lbq_fhzh.png",
		"../../img/map/city_lbq/city_lbq_yyzh.png",
		"../../img/map/city_lbq/city_lbq_wzzh.png",
		"../../img/map/city_lbq/city_lbq_xylzh.png",
		"../../img/map/city_lbq/city_lbq_tyzh.png",
		"../../img/map/city_lbq/city_lbq_tczh.png",
		"../../img/map/city_lbq/city_lbq_wtzh.png",
		"../../img/map/city_lbq/city_lbq_yhzh.png",
		"../../img/map/city_lbq/city_lbq_cndzh.png",
		"../../img/map/city_lbq/city_lbq_hjlzh.png",
		"../../img/map/city_lbq/city_lbq_hyzh.png",
		"../../img/map/city_lbq/city_lbq_lzlzh.png",
		"../../img/map/city_lbq/city_lbq_gylzh.png",
		"../../img/map/city_lbq/city_lbq_hdzh.png",
		"../../img/map/city_lbq/city_lbq_spzh.png",
		"../../img/map/city_lbq/city_lbq_cydzh.png",
		"../../img/map/city_lbq/city_lbq_wglzh.png",
		"../../img/map/city_lbq/city_lbq_jclzh.png",
		"../../img/map/city_lbq/city_lbq_jslzh.png",
		"../../img/map/city_lbq/city_lbq_whlzh.png",
		"../../img/map/city_lbq/city_lbq_lbzh.png",
		"../../img/map/city_lbq/city_lbq_bxzh.png",
		"../../img/map/city_lbq/city_lbq_xsdzh.png",
		"../../img/map/city_lbq/city_lbq_xhzh.png",
		"../../img/map/city_lnq/city_lnq_hmzh.png",
		"../../img/map/city_lnq/city_lnq_dllzh.png",
		"../../img/map/city_lnq/city_lnq_hynlzh.png",
		"../../img/map/city_lnq/city_lnq_yyb.png",
		"../../img/map/city_lnx/city_lnx_lnzh.png",
		"../../img/map/city_ltkfq/city_ltkfq_ltzh.png",
		"../../img/map/city_ltx/city_ltx_ltzh.png",
		"../../img/map/city_ltx/city_ltx_hgzh.png",
		"../../img/map/city_lx/city_lx_lxzh.png",
		"../../img/map/city_ytx/city_ytx_ytzh.png",
		"../../img/map/city_ytx/city_ytx_ytyxzh.png",
		"../../img/map/city_zhs/city_zhs_zhzh.gif",
		"../../img/map/city_zhs/city_zhs_zhflczh.gif",
		"../../img/map/city_qxx/city_qxx_qxzh.gif",
		"../../img/map/city_qas/city_qas_qazh.gif",
		"../../img/map/city_qas/city_qas_qaxyzh.gif",
		"../../img/map/city_cfdq/city_cfdq_cfdzh.gif",
		"../../img/map/city_cfdq/city_cfdq_cfdgyqzh.gif",
		"../../img/map/city_cfdq/city_cfdq_nbzh.gif",
		"../../img/map/city_fnq/city_fnq_fnhfsqzh.gif",
		"../../img/map/city_fnq/city_fnq_fnzh.gif",
		"../../img/map/city_frq/city_frq_gysqzh.gif",
		"../../img/map/city_frq/city_frq_frzh.gif",
		"../../img/map/city_gyq/city_gyq_lxzh.gif",
		"../../img/map/city_gyq/city_gyq_zgzzh.gif",
		"../../img/map/city_gyq/city_gyq_gyzh.gif",
		"../../img/map/city_kpq/city_kpq_lnzh.gif",
		"../../img/map/city_kpq/city_kpq_mjgzh.gif",
		"../../img/map/city_kpq/city_kpq_xylzh.gif",
		"../../img/map/city_lbq/city_lbq_lgzh.gif",
		"../../img/map/city_lbq/city_lbq_gmzh.gif",
		"../../img/map/city_lbq/city_lbq_fhzh.gif",
		"../../img/map/city_lbq/city_lbq_yyzh.gif",
		"../../img/map/city_lbq/city_lbq_wzzh.gif",
		"../../img/map/city_lbq/city_lbq_xylzh.gif",
		"../../img/map/city_lbq/city_lbq_tyzh.gif",
		"../../img/map/city_lbq/city_lbq_tczh.gif",
		"../../img/map/city_lbq/city_lbq_wtzh.gif",
		"../../img/map/city_lbq/city_lbq_yhzh.gif",
		"../../img/map/city_lbq/city_lbq_cndzh.gif",
		"../../img/map/city_lbq/city_lbq_hjlzh.gif",
		"../../img/map/city_lbq/city_lbq_hyzh.gif",
		"../../img/map/city_lbq/city_lbq_lzlzh.gif",
		"../../img/map/city_lbq/city_lbq_gylzh.gif",
		"../../img/map/city_lbq/city_lbq_hdzh.gif",
		"../../img/map/city_lbq/city_lbq_spzh.gif",
		"../../img/map/city_lbq/city_lbq_cydzh.gif",
		"../../img/map/city_lbq/city_lbq_wglzh.gif",
		"../../img/map/city_lbq/city_lbq_jclzh.gif",
		"../../img/map/city_lbq/city_lbq_jslzh.gif",
		"../../img/map/city_lbq/city_lbq_whlzh.gif",
		"../../img/map/city_lbq/city_lbq_lbzh.gif",
		"../../img/map/city_lbq/city_lbq_bxzh.gif",
		"../../img/map/city_lbq/city_lbq_xsdzh.gif",
		"../../img/map/city_lbq/city_lbq_xhzh.gif",
		"../../img/map/city_lnq/city_lnq_hmzh.gif",
		"../../img/map/city_lnq/city_lnq_dllzh.gif",
		"../../img/map/city_lnq/city_lnq_hynlzh.gif",
		"../../img/map/city_lnq/city_lnq_yyb.gif",
		"../../img/map/city_lnx/city_lnx_lnzh.gif",
		"../../img/map/city_ltkfq/city_ltkfq_ltzh.gif",
		"../../img/map/city_ltx/city_ltx_ltzh.gif",
		"../../img/map/city_ltx/city_ltx_hgzh.gif",
		"../../img/map/city_lx/city_lx_lxzh.gif",
		"../../img/map/city_ytx/city_ytx_ytzh.gif",
		"../../img/map/city_ytx/city_ytx_ytyxzh.gif"
);
*/