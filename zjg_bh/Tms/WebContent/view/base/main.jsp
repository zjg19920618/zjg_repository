<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>首页</title>
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
		<link id="eeee" rel="stylesheet" href="../../css/jq-easyui/themes/default/easyui.css" />
		<link rel="stylesheet" href="../../css/jq-easyui/themes/icon.css" />
		<link rel="stylesheet" href="../../css/jq-easyui/demo.css" />
		
		<link id="style2" rel="stylesheet" href="../../css/zTreeStyle.css" />
		<link id="stylef" rel="stylesheet" href="../../css/bootstrap.min.css" />
		<link id="style1" rel="stylesheet" href="../../css/bootstrap.css" />
		<link id="style1" rel="stylesheet" href="../../css/bootstrap-select.css" />
		
		<link id="style3" rel="stylesheet" href="../../css/linear.css" />
		<link id="stylea" rel="stylesheet" href="../../css/jquery.excoloSlider.css" />
		<link id="style3" rel="stylesheet" href="../../css/common.css" />
		<link id="style4" rel="stylesheet" href="../../css/page.css" />
		<link id="style5" rel="stylesheet" href="../../js/jquery-easyui-1.3.1/themes/default/tree.css" />
		<link id="style6" rel="stylesheet" type="text/css" href="../../css/validationEngine.jquery.css">
		<link href="../../css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../js/jquery-1.11.3.min.js" ></script>
		<script type="text/javascript" src="../../js/jquery.min.js" ></script>
        <script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
    	<script src="../../js/bootstrap.min.js" type="text/javascript"></script>
    	<script src="../../js/bootstrap-select.js" type="text/javascript"></script>
    	
		<script id="js1" type="text/javascript" src="../../js/jquery.ztree.core-3.5.js" ></script>
		<script id="jsr" type="text/javascript" src="../../js/echarts/echarts.js" ></script>
		<script id="jsq" type="text/javascript" src="../../js/echarts/esl.js" ></script>
		<script id="js2" type="text/javascript" src="../../js/jquery.ztree.excheck-3.5.js" ></script>
		<script id="js3" type="text/javascript" src="../../js/jquery.excoloSlider.js" ></script>
		<script id="js4" type="text/javascript" src="../../js/Chart.bundle.min.js" ></script>
		<script id="js5" type="text/javascript" src="../../js/common.js" ></script>
		<script id="js6" type="text/javascript" src="../../js/terminalInformation.js" ></script>
		<script id="js7" type="text/javascript" src="../../js/jQuery-Validation-Engine/jquery.validationEngine-zh_CN.js"></script>
    	<script id="js8" type="text/javascript" src="../../js/jQuery-Validation-Engine/jquery.validationEngine.js"></script>
		<script src="../../js/fileinput.js" type="text/javascript"></script>
		<script src="../../js/fileinput_locale_zh.js" type="text/javascript"></script>
    	<script type="text/javascript" src="../../js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>  	
	    <!-- 
		    <script id="js12" type="text/javascript" src="../../js/jquery.cityselect.js"></script>
		    <script id="js11" type="text/javascript" src="../../js/city.min.js"></script> 
		    <script id="js13" type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=cuoimjTOn2cxYXVbmPGfjTMYTNaZ4RMW"></script>
	    -->
	   	<script id="js14" type="text/javascript" src="../../js/tms.bankInfo.js"></script>
   	
		<script type="text/javascript">
			//清除css缓存
			document.getElementById('style1').href = '../../css/bootstrap.css?'+ Math.random();
			document.getElementById('style2').href = '../../css/zTreeStyle.css?'+ Math.random();
			document.getElementById('style3').href = '../../css/common.css?'+ Math.random();
			document.getElementById('style4').href = '../../css/page.css?'+ Math.random();
			document.getElementById('style6').href = '../../css/jquery.excoloSlider.css?'+ Math.random();
			document.getElementById('style5').href = '../../js/jquery-easyui-1.3.1/themes/default/tree.css?' + Math.random();
			document.getElementById('style6').href = '../../css/validationEngine.jquery.css?' + Math.random();
			document.getElementById('eeee').href = '../../css/jq-easyui/themes/default/easyui.css?' + Math.random();
			//js清除缓存
			document.getElementById('js1').href = '../../js/jquery.ztree.core-3.5.js?'+ Math.random();
			document.getElementById('js2').href = '../../js/jquery.ztree.excheck-3.5.js?'+ Math.random();
			document.getElementById('js3').href = '../../js/jquery.excoloSlider.js?'+ Math.random();
			document.getElementById('js4').href = '../../js/Chart.bundle.min.js?'+ Math.random();
			document.getElementById('js5').href = '../../js/common.js?'+ Math.random();
			document.getElementById('js7').href = '../../js/jQuery-Validation-Engine/jquery.validationEngine-zh_CN.js?'+ Math.random();
			document.getElementById('js8').href = '../../js/jQuery-Validation-Engine/jquery.validationEngine.js?'+ Math.random();
			//document.getElementById('js6').href = '../../js/jquery-easyui-1.3.1/themes/default/tree.css?' + Math.random();
			//document.getElementById('js11').href = '../../js/city.min.js?'+ Math.random();
			//document.getElementById('js12').href = '../../js/jquery.cityselect.js?'+ Math.random();
			//document.getElementById('js13').href = 'http://api.map.baidu.com/api?v=2.0&ak=cuoimjTOn2cxYXVbmPGfjTMYTNaZ4RMW?'+ Math.random();
			document.getElementById('js14').href = '../../js/tms.bankInfo.js?'+ Math.random();
		</script>
	</head>
	<body>
		<div class="TS_bank TMSBackgroundIMG">
			
			<div class="TS_bank_box">
				<!-- 左部分 -->
				<div class="common_left">
					<!-- 左部分头部 -->
					<div class="log"></div>
					<!-- 左部分菜单 -->
					<div id="menu" class="common_menu">
						<div class="opsitioyY"></div>
						<ul>
							<li class="menu_G" style="display:none;"><span></span><b>功能菜单</b></li>
						</ul>
					</div>
				</div>
				<!-- 左部分end -->
				<!--右部分-->
				<div class="common_right">
					<!-- 右头部分部分 -->
					<div class="right_top">	
						<span class="ico">唐山银行终端管理系统</span>
						<div class="rightTop_bg">
							<div class="filterBJ"></div>
							<ul>
								<li><span ><span class="geRenInfor" style="display:block;float:left; margin-top: 8px;"></span><span id="system1" style="display:block;float:left; width: 53px;text-overflow:ellipsis;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;"></span></span></li>
								<li><span  id="skin"><span class="geRenInfor huanfuInfor"></span>换肤</span></li>
								<li><span id="edit_admin" ><span class="geRenInfor passwordInfor"></span>修改密码</span></li>
								<li><span id="exit" ><span class="geRenInfor outInfor"></span>退出</span></li>
							</ul>
							<div class="skinBg">
								<ul>
									<li><img src="../../img/backgroundImg/TMS_bank.jpg" data-url="0" draggable="false" /><p data-url="0"><span></span></p></li>
									<li><img src="../../img/backgroundImg/TMS_bank1.jpg" data-url="1" draggable="false" /><p data-url="1"><span></span></p></li>
									<li><img src="../../img/backgroundImg/TMS_bank2.jpg" data-url="2" draggable="false" /><p data-url="2"><span></span></p></li>
									<li><img src="../../img/backgroundImg/TMS_bank3.jpg" data-url="3" draggable="false" /><p data-url="3"><span></span></p></li>
									<li><img src="../../img/backgroundImg/TMS_bank4.jpg" data-url="4" draggable="false" /><p data-url="4"><span></span></p></li>
									<li><img src="../../img/backgroundImg/TMS_bank5.jpg" data-url="5" draggable="false" /><p data-url="5"><span></span></p></li>
								</ul>
								<div class="bgConfirm">
									<button id="bgKeep">保存</button>
									<button id="cancelKeep">取消</button>
									<!-- <button id="">自定义</button> -->
								</div>
							</div>
						</div>
						<div class="ico_right" style="display:none;">
<!-- 							<ul class="ico_name"> -->
<!-- 								<li><span>机构代码: </span><span id="system">系统</span></li> -->
<!-- 								<li><span>机构名称: </span><span id="name">中华</span></li> -->
<!-- 								<li><span>工作日期: </span><span id="timer">2016年8月10日</span></li> -->
<!-- 							</ul> -->
							
							<div class="ico_user">
								<div class="ico_content">
									<span class="ico_con" id="outBtn" style=""></span>
										<a class="ico_con" href="javasript:;">
											<span id="system1">游客</span>
										</a>
									<span class="ico_con sanjiao2"></span>
									
								</div>
								<div class="MyCenter" style="display:none; cursor:pointer;">
									<p >修改密码</p>
									<p >退出</p>
								</div>
							</div>
							
						</div>
					</div>
					<!-- 右头部分部分end -->
						<!--内容部分-->
						<div class="informationLunBo"></div>
						<div class="informationLunBo_O">
							 <div class="informationLunBo_t" id="informationLunBo_F">
							 	<span></span>
								<ul id="informationLunBo_S">
									<!-- <li>qwerwaerawr第三方公司打工暗示法撒旦法师的沙僧的风格</li>
									<li>规划法规和规划梵蒂冈地方规定个非官方个石头而为如今已添加和</li>
									<li>第132123僧的风格</li> -->
								</ul>
							 </div>
						</div>
					<div id="content">
						    
					</div>
					<div class="footer">
						<p>Copyright© 2016-2030 BOOMHOPE.COM.ALL Right Reserved 博宏信息技术有限公司 版权所有</p>
					</div>
				</div>
			</div>
		</div>
<!-- 		遮罩层 -->
		<div class="mask"></div>
<!-- 		退出确认框 -->
			<div class="exit_sure">
				<div class="firm_add_head">
					<span>提示</span>
				</div>
				<div class="exit_sure_content">
					<img src="../../img/common/tishi.png" alt="" /><span>确认退出?</span>
				</div>
				<div>
					<button id="exit_no">取消</button>
					<button id="exit_ok">确定</button>
				</div>
			</div>
			<!-- 			警告框通用 -->
<!-- 			<div class="remind_sure"> -->
<!-- 				<div class="firm_add_head"> -->
<!-- 					<span>提示</span> -->
<!-- 				</div> -->
<!-- 				<div class="remind_sure_content"> -->
<!-- 					<img src="../../img/common/tishi.png" alt="" /><span>确认<span id="remind_text"></span>?</span> -->
<!-- 				</div> -->
<!-- 				<div> -->
<!-- 					<button id="remind_no">取消</button> -->
<!-- 					<button id="remind_ok">确定</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 修改密码 -->
			<div class="edit_admin" style="height:240px;">
					<div class="firm_add_head">
					<span>修改密码</span>
				</div>
				<span class="edit_error" id="edit_error" style="color:red;margin-left:85px;"></span>
				<form id="passwordName" action="">
				<div class="edit_admin_content">
					<p><span><b>*</b> 原密码：</span><input class="validate[required]" type="password" id="old_pwd"/></p>
					<p><span><b>*</b> 新密码：</span><input class="validate[required]" type="password" id="new_pwd"/></p>
					<p><span><b>*</b> 确认密码：</span><input class="validate[required]" type="password" id="new_pwd_s"/></p>
				</div>
				</form>
				<div>
					<button id="edit_admin_no" style="margin-bottom:6px;">取消</button>
					<button id="edit_admin_ok" onclick="updatePwd(this)">确定</button>
				</div>
			</div>
			<div class="loader" style="display:none;">
	    <div class="loader-inner line-spin-fade-loader">
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	    </div>
  	</div>
		<script>
		//公共跳转页面
$(function(){
	valid();
});
function valid(){
	$('#passwordName').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				subupdatePwd();
			}
		}
	});
}
		function busGotow(file)
		{
			$.get(file,{},function(res){
				$("#content").html(res);
			});
		}
		//$(".right_top").css("width",$("body").width()-290 + "px");
		var b = false;
		var menuId;
		//修改密码
		$("#edit_admin").click(function(){
			$("#old_pwd").val("");
			$("#new_pwd").val("");
    		$("#new_pwd_s,#edit_error").val("");
    		$("#edit_error").html("");
			$(".edit_admin").show();
			$(".mask").show();
		});
		$("#edit_admin_no").click(function(){
			$(".edit_admin").hide();
			$(".mask").hide();
			$('form').validationEngine('hideAll');
		});
		//信息显示框/切换背景
		$("#skin").click(function(){
			$(".skinBg").show();
			return false;
		});
		var pathImgBj;//设置数组下标
		var bjJson = ["TMS_bank.jpg","TMS_bank1.jpg","TMS_bank2.jpg","TMS_bank3.jpg","TMS_bank4.jpg","TMS_bank5.jpg"]; // 图片地址
		$(".skinBg li").eq(0).find("span").css("display","inline-block");
		$(".skinBg li img,.skinBg li p").click(function(){
			$(".skinBg li span").hide();
			$(this).parent().find("span").css("display","inline-block");
			pathImgBj = $(this).attr("data-url");
			console.log($(this).attr("data-url"));//获取自定义属性
			return false;
	    });
		//下拉信息框
		 $(".ico_con").click(function(){
			$(".MyCenter").show();
				return false;
		}); 
		//取消隐藏
		  $("#cancelKeep,#bgKeep,body").click(function(){
			$(".skinBg").hide();
		   }); 
		//背景保存
		$("#bgKeep").click(function(){  
			$(".TS_bank").css({"background":"url(../../img/backgroundImg/"+bjJson[pathImgBj]+")","background-size":"100% 100%"});
			console.log("../img/backgroundImg/"+bjJson[pathImgBj]);
			return false;
		});
		$(".mask,loader").show();
		
		 function updatePwd(event){
			// $("#old_pwd").val("");
			//var aa = $("#old_pwd").val("");
			// alert("aa");
		    	$("#passwordName").submit();
		    	
		    }
		
		function subupdatePwd(){
			if($("#new_pwd").val() !== $("#new_pwd_s").val()){
			$("#edit_error").html("两次密码不一致，请重新输入！");	
			}else{
				var old_pwd=$("#old_pwd").val();
				var new_pwd=$("#new_pwd").val();
				var new_pwd_s=$("#new_pwd_s").val();
					$.ajax({
		            url:"<%=basePath%>getEditPwd.do",//方法名待修改
		            cache:false,
		            type:"post",
		            preventCache:true,
		            datatype:'json',
		            data:{pwd:old_pwd,nepwd:new_pwd,nepwds:new_pwd_s},
		            beforeSend:function(xmlHttp){//清除ie浏览器缓存
		            	xmlHttp.setRequestHeader("If-Modified-Since","0");
		            	xmlHttp.setRequestHeader("Cache-Control","no-cache"); 
		            },
		            success:function(data){
		            	$(".mask,loader").hide();
		            	$(".edit_admin").hide();
		            	if(data.success == true){
		            		var tishi=new Tishi("修改成功","chenggong");
        				}else{
        					var tishi=new Tishi(data.errmsg,"shibai");
        					
        					}	
	                },
		            complete:function(){
		            	
		            },
		            error:function(){
		            }
		        });
			}
		};
		//退出页面
		$("#exit").click(function(){
			$(".mask").show();
			$(".exit_sure").show();
			$(".MyCenter").hide();
		});
		$("#exit_ok").click(function(){
			/* //busGotoq("login.jsp"); */ 
			 location.href = "login.jsp";
		})
		$("#exit_no").click(function(){
			$(".mask").hide(); 
			$(".exit_sure").hide();
		})
		 function busGotoq(file)
			{
				$.get(file,{},function(res){
					$("body").html(res);
				});
			}
		var index;
			$.ajax({
	            url:"<%=basePath%>getMenu.do",
	            cache:false,
	            type:"post",
	            datatype:'json',
	            data:{parentId:menuId},
	            beforeSend:function(xmlHttp){
	            	xmlHttp.setRequestHeader("If-Modified-Since","0");
	            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	            },
	            success:function(data){
	            	//var data1 =$.parseJSON(data);
					$(".mask").hide();
	            	console.log(data.info);
	            	for(var i = 0;i<data.info.length;i++){
	            		$("#menu ul").append("<li><div class=menu_left_common>"+icoArr[i].ico+"</div>"+data.info[i].roleMenuViewId.menuName+"</li>");
	            		$("#system1").html(data.info[i].roleMenuViewId.username);
	            		$(".common_menu ul li:eq(1)").attr("class","choice").children().children("span").css("background-position-x","-372px");
	            	}
	            	
            		$(".common_menu ul li").on("click",function(e){//菜单选中效果
            			clearInterval(mapStatusTimer);
            			menuId = data.info[$(this).index()-1].roleMenuViewId.menuId;
            			$(".choice").children().children("span").css("background-position-x",parseInt($(".choice").children().children("span").css("background-position"))+100+"px");
            			$(".choice").removeClass("choice");
            			$(this).addClass("choice");
            			$(this).children().children("span").css("background-position-x",parseInt($(this).children().children("span").css("background-position"))-100+"px");
            			console.log(parseInt($(this).children().children("span").css("background-position")));
            			$(".menu_G").removeClass("choice");
            			if(data.info[$(this).index()-1].roleMenuViewId.menuPath == ''){
            				alert("出错啦！！！")
            			}else{
            				busGotow(data.info[$(this).index()-1].roleMenuViewId.menuPath); //跳转的页面
            			}
            			
            		  	$("#table_tbody").empty();
            		});
            		busGotow(data.info[0].roleMenuViewId.menuPath); //默认跳转的页面
	            },
	           
	            complete:function(){},
	            error:function(){}
	        });
			InforMationLBT();
			// 信息轮播函数
			setInterval("InforMationLBT()",30000);
			function InforMationLBT(){
				$.ajax({
			        url:"<%=basePath%>findAllCounts.do",
			        cache:false,
			        type:"post",
			        datatype:'json',
			        data:"",
			        beforeSend:function(){},
			        success:function(data){
					$("#informationLunBo_S").empty();
					$("#informationLunBo_S").append("<li>共有网点"+data[0].machineCounts+"个,终端设备"+data[0].machineTypeCounts+"种</li><li>终端运行状态:初始"+data[0].machineInitialCounts+"台,投产"+data[0].machineOperationCounts+"台,下线"+data[0].machineOfflineCounts+"台</li><li>终端启用情况:正常"+data[0].machineNormalCounts+"台,异常"+data[0].machineErrCounts+"台,未知"+data[0].machineUnknownCounts+"台</li>");
			    		
					 $("#city_num").html(data[0].machineCounts);
			        },
			        complete:function(){},
			        error:function(){}
			    });
			}
			//文字轮播效果
			function autoScroll(obj, ul_bz){
				$(obj).find(ul_bz).animate({
					marginTop : "-20px"
				},500,function(){
				$(this).css({marginTop : "0px"}).find("li:first").appendTo(this);
				});
				}
				setInterval('autoScroll("#informationLunBo_F", "#informationLunBo_S")',4000)
			//主页面小图标
			var icoArr = [
			  {ico:"<span class=banks_map></span>"},
			  {ico:"<span class=devices_manage></span>"},
			  {ico:"<span class=deploy_manage></span>"},
			  {ico:"<span class=treminal_monitoring></span>"},
			  {ico:"<span class=system_manage></span>"},
			  {ico:"<span class=business_manage></span>"},
			  {ico:"<span class=business_manage></span>"}
			]
		</script>
	</body>
</html>
