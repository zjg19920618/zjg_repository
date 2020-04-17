<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
				<div class="table_menu" id="table_menus">
					<b class="sanjiao"></b>
					<ul>
						<li>
							<b class="stop"></b>
							<span>停用</span>
						</li>
						<li>
							<b class="weihu"></b>
							<span>终端维护</span>
						</li>
						<li>
							<b class="xiufu"></b>
							<span>人工修复</span>
						</li>
						<li>
							<b class="wubao"></b>
							<span>异常误报</span>
						</li>
						<li>
							<b class="peizhi"></b>
							<span>角色权限配置</span>
						</li>
					</ul>
				</div>
				<div class="firm_add">
				<form action="">
					<div class="firm_add_head">
						<span>添加厂商</span>
						<a href="javascript:;" id="close_cha"></a>
					</div>
					<div class="firm_add_content">
						<p>
							<b>*</b><span>厂商编号：</span><input type="text" id="csbh"/>
							<b>*</b><span>厂商名称：</span><input type="text" id="csmc"/>
						</p>
						<p>
							<b>*</b><span>联系人：</span><input type="text" id="lxr"/>
							<b>*</b><span>座机电话：</span><input type="text" id="zuoNum"/>
						</p>
						<p>
							<b>*</b><span>手机电话：</span><input type="text" id="phone"/>
							<b>*</b><span>厂商状态：</span>
								<select value="" id="cszt">
								<option value="">请选择</option>
								<option value="1">正常</option>
								<option value="0">废弃</option>
								</select>
						</p>
						<p class="beizhu"><span>备注：</span><textarea name="" id="csbz" ></textarea></p>
					</div>
					<div>
					<button id="close_add">取消</button>
					<button id="save_add">保存</button>
					</div>
				</form>
			</div>
			<div class="navbar secondaryMenu">
				<ul class="nav nav-tabs nav-justified" >
					<li id="nav1">产品类型管理2</li>
					<li id="nav2">产品类型管理2</li>
					<li id="nav3">产品类型管理2</li>
				</ul>
			</div>
			<div class="com_nav">
				<div class="nav_type" id="nav_type1">
					<ul>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li><span>出厂终端编号 </span> <input type="text" /></li>
						<li class="button_sarch">
							<button>
								<span class="sarch_ico"></span>
								<span class="sarch">查询</span>
							</button>
						</li>
					</ul>
				</div>
				<div class="nav_type" id="nav_type2">
					<ul>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li><span>出厂终端编号2 </span> <input type="text" /></li>
						<li class="button_sarch">
							<button>
								<span class="sarch_ico"></span>
								<span class="sarch">查询</span>
							</button>
						</li>
					</ul>
				</div>
				<div class="nav_type" id="nav_type3">
					<ul>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li><span>出厂终端编号3 </span> <input type="text" /></li>
						<li class="button_sarch">
							<button>
								<span class="sarch_ico"></span>
								<span class="sarch">查询</span>
							</button>
						</li>
					</ul>
				</div>
				<div class="newSonstruction">
					<span class="new_ico"></span>
					<span class="new_S">新建</span>
				</div>
				<div class="table_box">
					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<td></td>
								<td>厂商编号</td>
								<td>厂商名称</td>
								<td>联系人</td>
								<td>座机电话</td>
								<td>手机电话</td>
								<td>厂商状态</td>
								<td>备注</td>
								<td>创建日期</td>
								<td>创建人</td>
								<td>创建人</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
								<td>默认</td>
							</tr>
							<tr>
								<td>2</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>3</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>4</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>5</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>6</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>7</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>8</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>9</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>10</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				</div>
					<script>
					//选项卡部分
					$("#nav_type1").show();
					$("#nav1").css("background","#cfe1e6");
					$("#nav1").click(function(){
						$(this).css("background","#cfe1e6");
						$("#nav_type1").show();
						$("#nav_type2 ,#nav_type3").hide();
						$("#nav2, #nav3").css("background","#fff");
					});
					$("#nav2").click(function(){
						$(this).css("background","#cfe1e6");
						$("#nav_type2").show();
						$("#nav_type1 ,#nav_type3").hide();
						$("#nav1 ,#nav3").css("background","#fff");
					});
					$("#nav3").click(function(){
						$(this).css("background","#cfe1e6");
						$("#nav_type3").show();
						$("#nav_type1, #nav_type2").hide();
						$("#nav1 ,#nav2").css("background","#fff");
					});
					/*表格部分*/
					//$("table tr:odd").css("background","#fff9f8");
					//$("table tr:even").css("background","#ebf2f7");

					//悬停变色
					$(".table tr").mouseover(function(){
					$(this).addClass("mouse_hover");
					})
					$(".table tr").mouseout(function(){
					$(this).removeClass("mouse_hover");
					})
					$(".table tr").on("click",function(e){//表格选中效果
					$(".click").removeClass("click");
					//							$(this).parent().find("td").addClass("click");

					});
					$("#close_cha").click(function(){
						$(".firm_add").hide();
					})
					//$(".table_box tbody tr").click(function(e){
						//$(".table_box tbody tr:odd,.table_box tbody tr:odd").css("background","#ebf2f7");
						// $(".table_box tbody tr:even,.table_box tbody tr:even").css("background","#fff9f8");
						//$(this).css("background","#879fb6");
						//$(".table_menu").show().css({top:e.pageY-12,left:e.pageX+6});
					//})
				</script>
