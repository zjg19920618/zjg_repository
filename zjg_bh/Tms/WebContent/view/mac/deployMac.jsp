<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 	重置密码 -->
				<div class="reset_pwd reset_pwdA" style="margin-left:-290px;,margin-top:-170px;">
					<div class="firm_add_head">
						<span style='width:125px;'>重置密码</span>
					</div>
				<span class="edit_error"></span>
				<form action="">
					<div class="edit_admin_content">
						<p><span><b>*</b> 新密码：</span><input type="password" id="reset_pwd"/></p>
					</div>
				</form>
				<div>
					<button id="reset_pwd_no">取消</button>
					<button id="reset_pwd_ok">确定</button>
				</div>
			</div>
			
			<!-- 	投产 -->
				<div class="reset_pwd reset_pwd1">
					<div class="firm_add_head">
					
						<span id="tuochan1">提示</span>
					</div>
				<span class="edit_error"></span>
				<form action="">
					<div class="edit_admin_content">
						<p><span><b>*</b>备注：</span><input type="text" id="reset_pwd1"/></p>
					</div>
				</form>
				<div>
					<button id="reset_pwd1_no">取消</button>
					<button id="reset_pwd1_ok">确定</button>
				</div>
			</div>
    <!--部署维护的添加与编辑-->
    <div class="firm_add_add unit_pop2"style='margin-top:-270px;'>
        <form id = "form1" action="">
        <div class="firm_add_head">
            <span>添加部署信息</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
          <p>
          	<span><b>*</b>机器编号：</span><input class="validate[required]" type="text" id="jqbh1_add"/>
          	<span><b>*</b>所属区县：</span><select class="validate[required]"id="ssqx_add">
             		<option value="">请选择</option>
             	</select>
         </p>
         <p>
			<span><b>*</b>机构名称：</span><select class="validate[required]"id="jgmc_add"></select>
			<span><b>*</b>机器名称：</span><select class="validate[required]" name="" id="jqmc_add"></select>
         </p>
              <div class="common_info username_info"><b class="info_caret"></b>* 机器编号已存在!</div>
         <p>
             	<span><b>*</b>IP地址：</span><input class="validate[required]" type="text" id="ip_add"/>
             	<span><b>*</b>柜员号：</span><input class="validate[required]" type="text" id="gyh_add"/>
            </p>
            <div class="common_info email_info ip_info" style="right: 460px;"><b class="info_caret"></b>* 此IP已被使用!</div>   
            <p>
            	<span><b>*</b>键盘主密码标记：</span><input class="validate[required]" type="text" id="main_pwd_sign_add" disabled= "disabled"/>
             	<span><b>*</b>键盘工作密码标记：</span><input class="validate[required]" type="text" id="work_pwd_sign_add" disabled= "disabled"/>
            </p>
             <p>
             	<span><b>*</b>转加密密钥标记：</span><input class="validate[required]" type="text" id="other_pwd_sign_add" disabled= "disabled" />
             	<span><b>*</b>MAC密钥标记：</span><input class="validate[required]" type="text" id="mac_pwd_sign_add" disabled= "disabled" />
            </p>
            <p>
            	<span><b>*</b>出厂编号：</span><input class="validate[required]" type="text" id="access_pwd_sign_add" />
            	<span><b>*</b>渠道号：</span><input class="validate[required]" type="text" id="access_pwd_sign_qudao" />
            </p> 
            <p>
            	<span><b>*</b>商户号：</span><input class="validate[required]" type="text" id="access_pwd_sign_shanghu" />
            </p>
            <div class="common_info username_infoqq" style="display:none;top: 299px;left: 184px;"><b class="info_caret"></b><span id="errorInformation"></span></div>
        </div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add">保存</button>
            
        </div>
        </form>
    </div>
    <!--添加结束-->
    <div class="firm_add_edit unit_pop2" style='margin-top:-270px;'>
        <form id = "form2" action="">
        <div class="firm_add_head">
            <span>编辑部署信息</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
            <p><span><b>*</b>机器编号：</span><input type="text" id="jqbh_edit" disabled= "disabled"/>
            	<span><b>*</b>机器名称：</span><select class="validate[required]" name="" id="jqmc_edit"></select>
            </p>
            <p>
             	<span><b>*</b>机构名称：</span><select class="validate[required]" value="" id="jgmc_edit"></select>
             	<span><b>*</b>所属区县：</span><select class="validate[required]" id="ssqx_edit">
             		<option value="">请选择</option>
             	</select>
             </p>
             <p>
             		<span><b>*</b>IP地址：</span><input class="validate[required]" type="text" id="ip_edit"/>
             		<span><b>*</b>柜员号：</span><input class="validate[required]" type="text" id="gyh_edit"/>
             </p>
             <div class="common_info email_info ip_info bj_ip_info" style="right: 460px;"><b class="info_caret"></b>* 此IP已被使用!</div>
              <p>
              	<span><b>*</b>键盘主密码标记：</span><input class="validate[required]" type="text" id="main_pwd_sign_edit"/>
             	<span><b>*</b>键盘工作密码标记：</span><input class="validate[required]" type="text" id="work_pwd_sign_edit"/>
            </p>
             <p>
             	<span><b>*</b>转加密密钥标记：</span><input class="validate[required]" type="text" id="other_pwd_sign_edit" disabled= "disabled"/>
             	<span><b>*</b>MAC密钥标记：</span><input class="validate[required]" type="text" id="mac_pwd_sign_edit" disabled= "disabled"/>
            </p> 
            <p>
            	<span><b>*</b>出厂编号：</span><input class="validate[required]" type="text" id="access_pwd_sign_edit"  />
            	<span><b>*</b>渠道号：</span><input class="validate[required]" type="text" id="access_pwd_sign_qudao1" />
            </p>
            <p>
            	<span><b>*</b>商户号：</span><input class="validate[required]" type="text" id="access_pwd_sign_shanghu1" />
            </p>
        </div>
        <div>
            <button id="close_add_edit">取消</button>
            <button id="save_add_edit">保存</button>
            
        </div>
        </form>
    </div>
	<div class="com_nav">
    <table class="search_box">
			<tr>
				<td><span>机器编号：</span></td>
				<td><input type="text" id="jqbh"></td>
				<td><span>机器名称：</span></td>
				<td><input type="text" id="jqmc"></td>
				<td>机器类型：</td>
				<td>
					<select value="" class="firmZT" id="jqlx">
                   		<option value="">请选择</option>
                	</select>
                </td>
             </tr>
             <tr>
                <td><span>所属厂商：</span></td>
                <td><span><input type="text" id="sscs"/></span></td>
                <td><span>机构名称：</span></td>
                <td><input type="text" id="jgmc"/></td>
                <td><span>设备状态：</span></td>
                <td>
                	<select value="" class="firmZT" id="sbzt">
                	</select>
                </td>
			</tr>
			<tr>
                <td><span>出厂编号：</span></td>
                <td><span><input type="text" id="jiqiChuChangBianHao"/></span></td>
			</tr>
		</table>
		<div id="refresh">
		<div class="newSonstruction">
			<button id="button_search">
                <span class="sarch_ico"></span>
                <span class="sarch">查询</span>
            </button>
			<button id="button_add_add">
				<span class="new_ico"></span>
				<span class="sarch">添加</span>
			</button>
			<button id="button_edit">
				<span class="edit_icon"></span>
				<span class="sarch">编辑</span>
			</button>
			<button id="button_delete">
				<span class="delete_icon"></span>
				<span class="sarch">删除</span>
			</button>
			<button id="button_resetpwd" class="button_add_menu">
				<span class="resetPassword_icon"></span>
				<span class="sarch">重置密码</span>
			</button>
			<button id="button_touchan" disabled class="grayBtn">
				<span class="touchan_icon"></span>
				<span class="sarch">投产</span>
			</button>
			<button id="button_xiaxian" disabled class="grayBtn">
				<span class="xiaxian_icon"></span>
				<span class="sarch">下线</span>
			</button>
			<button id="button_dC">
                <span class="export_ico"></span>
                <span class="sarch">导出</span>
            </button>
		</div>
		<div style="padding-top: 10px">
			<table id="statementGrid" style="width:100%; height:auto;max-height:290px;"></table>
		</div>
		</div>
	</div>

<script>
 	var statementGrid;
	$(function(){
		onld();//初始化
		$('#ip_edit,#jqbh1_add').blur(function(){
			$('.common_info').hide()
		})
		for(var i=0;i<Tms_bankInfo.length;i++){
			$("#ssqx_edit,#ssqx_add").append("<option value='"+Tms_bankInfo[i].city+"'>"+Tms_bankInfo[i].city+"</option>")
		}
	})
	function onld(sel){
		
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findDeployMachineList.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			idField : 'payId',
			autoRowHeight:true,
			queryParams:sel,
			//sortName : 'payDate',
			//sortOrder : 'asc',
			fitColumns:true ,
			nowrap:false,
			singleSelect: false, //是否单选
			columns : [ [ 
				{width : 20,field : 'ck',checkbox : true},
				{
					width : 70,
					title : '机器编号',
					field : 'machineNo',
					align : 'center',
					formatter : function(value, row,index) {
					
						return row['id']['machineNo'];
					}

				},
				{
					width : 70,
					title : '出厂编号',
					field : 'macFacNum',
					align : 'center',
				},
				{
					width : 40,
					title : '机器类型',
					field : 'machineType',
					align : 'center',
					formatter : function(value, row,index) {
					value="";
					if(row['machineType'] == '01'){
						value = '存单回收机';
					}else if(row['machineType'] == '02'){
						value = '存单机';
					}
					return value;
				}

				},
				{
					width : 40,
					title : '机器名称',
					field : 'machineName',
					align : 'center',
				},
				
				{
					width : 50,
					title : '所属厂商',
					field : 'manuName',
					align : 'center',
				},
				{
					width : 70,
					title : '所属区县',
					field : 'districtCounty',
					align : 'center',
				},
				{
					width : 70,
					title : '机构名称',
					field : 'unitName',
					align : 'center',
				},
				{
					width : 30,
					title : '设备状态',
					field : 'status',
					align : 'center',
					formatter : function(value, row,index) {
						console.log(row);
						value="";
						if(row['status'] == '1'){
							value = '投产';
						}else if(row['status'] == '2'){
							value = '下线';
						}else if(row['status'] == '0'){
							value = '初始';
						}
						return value;
					}
				},
				{
					title : '键盘主密钥标记',
					field : 'majorkeyflag',
					hidden:true,
				},
				{
					title : '键盘工作密码标记',
					field : 'workkeyflag',
					hidden:true,
				},
				{
					title : '转加密密钥标记',
					field : 'keyflag',
					hidden:true,
				},
				{
					title : 'MAC密钥标记',
					field : 'mackeyflag',
					hidden:true,
				},
				{
					title : '授权密钥标记',
					field : 'authorizekeyflag',
					hidden:true,
				},
			] ],
			onClickRow: function(index, data) {
				var row = $('#statementGrid').datagrid('getSelected');
				if(row){
					if(row.status==1){
						$("#button_xiaxian").attr("disabled","disabled").addClass("grayBtn");
						$("#button_touchan").attr("disabled","disabled").addClass("grayBtn");						
						$("#button_xiaxian").removeAttr("disabled").removeClass("grayBtn");
						$("#button_touchan").addClass("grayBtn1");
						$("#button_xiaxian").removeClass("grayBtn1");
					}else if(row.status==2){
						$("#button_xiaxian").attr("disabled","disabled").addClass("grayBtn");
						$("#button_touchan").attr("disabled","disabled").addClass("grayBtn");
						$("#button_touchan").removeAttr("disabled").removeClass("grayBtn");
						$("#button_touchan").removeClass("grayBtn1");
						$("#button_xiaxian").addClass("grayBtn1");
					}else if(row.status==0){
						$("#button_xiaxian").attr("disabled","disabled").addClass("grayBtn");
						$("#button_touchan").attr("disabled","disabled").addClass("grayBtn");
						$("#button_touchan").removeAttr("disabled").removeClass("grayBtn");
						$("#button_touchan").removeClass("grayBtn1");
						$("#button_xiaxian").addClass("grayBtn1");
					}
				}
			}
		});
	}
	//设置分页控件   
	var p = $('#statementGrid').datagrid('getPager');   
	$(p).pagination({   
	    //pageSize: pageSize,//每页显示的记录条数，默认为10   
	    pageList: [10,20,30,40,50],//可以设置每页记录条数的列表   
	    beforePageText: '第',//页数文本框前显示的汉字   
	    afterPageText: '页    共 {pages} 页',   
	    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	    onBeforeRefresh:function(){  
	        $(this).pagination('loading');  
	        $(this).pagination('loaded');  
	    } 
	});
	
var num=0;
var page = 1;
var rows = 10;
var intoll = 0;
var status;//状态编码
var machineNo;
setSelectMacType();
//setSelectManuCode();
setSelectStatus();
setSelectUnitCode();
setPerType("jqlx_edit");// 
setPerType("jqlx_add");//
setPerunitCode("jgmc_edit");
setPerunitCode("jgmc_add");

$(function(){
	valid();
});
function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				$(".username_info").hide();
				$(".email_info,.username_infoqq").hide();
				subbswh();
			}
		}
	});
	
	$('#form2').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				$(".bj_ip_info").hide();
				subbswh2();
			}
		}
	});
}
var new_status;//设备状态
	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
    })//查询按钮事件结束
	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var jqbh=$("#jqbh").val();
	 var jqmc=$("#jqmc").val();
	 var jqlx=$("#jqlx").val();
	 var sscs=$("#sscs").val();
	 var jgmc=$("#jgmc").val();
	 var sbzt=$("#sbzt").val();
	 var jiqiChuChangBianHao = $("#jiqiChuChangBianHao").val();
	 var sel  = {page:page,rows:rows,machineNo:jqbh,machineName:jqmc,manuName:sscs,machineType:jqlx,unitName:jgmc,status:sbzt,macFacNum:jiqiChuChangBianHao}
	return sel;
}
//机器名称动态添加
function machineAdd(){
	$.ajax({
		url:"<%=basePath%>findMachineList1.do",
		cache:false,
		type:"post",
		datatype:'json',
		async:false,
		beforeSend:function(){},
		success:function(data){
			$("#jqmc_add").html("<option value=''>请选择</option>");
			for(var i = 0;i<data.rows.length;i++){
           		$("#jqmc_add").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
           	}
		},
		complete:function(){},
		error:function(){}
	})
}
//编辑中机器名称动态添加
function machineEdit(){
	$.ajax({
		url:"<%=basePath%>findMachineList1.do",
		cache:false,
		type:"post",
		datatype:'json',
		async:false,
		beforeSend:function(){},
		success:function(data){
			$("#jqmc_edit").html("<option value=''>请选择</option>");
			for(var i = 0;i<data.rows.length;i++){
           		$("#jqmc_edit").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
           	}
		},
		complete:function(){},
		error:function(){}
	})
}

 //表格编辑单选事件
function selectTr(row){
   $(".firm_add_edit,.mask").show();
        $("#access_pwd_sign_qudao1").val(row.merNo); // 渠道号
		$("#access_pwd_sign_shanghu1").val(row.channel); // 商户号
    	$("#jqbh_edit").val(row.id.machineNo);//机器编号
    	$("#jqmc_edit").val(row.machineCode);// 机器名称
    	$("#gyh_edit").val(row.tellerNo);// 柜员号
    	$("#jgmc_edit").val(row.unitCode);// 机构名称
    	$("#ip_edit").val(row.ip);// ip地址
    	$("#main_pwd_sign_edit").val(row.majorkeyflag);// 主密码
    	$("#work_pwd_sign_edit").val(row.workkeyflag);// 工作密码
    	$("#other_pwd_sign_edit").val(row.keyflag);// 密钥密码
    	$("#mac_pwd_sign_edit").val(row.mackeyflag);// mac密码
    	$("#access_pwd_sign_edit").val(row.macFacNum);// 机器出厂编号
    	$("#ssqx_edit").val(row.districtCounty);
}

 //表格删除单选事件
function deleteTr(){
	var row = $('#statementGrid').datagrid('getSelected'); 
    if (row) { 
        delUnit(row.id.machineNo);
    }else{
    	$(".mask").show();
    	var tishi=new Tishi("请选择一条记录","tishi");
    }
}
 //投产单选事件
function touChanTr(){
	var row = $('#statementGrid').datagrid('getSelected'); 
    if (row) { 
        touChan(row.status,row.id.machineNo,row.macFacNum);
        console.log(row.macFacNum);
    }else{
    	$(".mask").show();
    	var tishi=new Tishi("请选择一条记录","tishi");
    }
}
 //编辑
$("#button_edit").on("click",function(){
	var row = $('#statementGrid').datagrid('getSelected');
	if(row){
		$("#jgmc_edit").html("<option value=''>请选择</option>");
	/* if(row.status == 1){
		console.log("状态 "+row.status)
		$(".mask").show();
		var tishi=new Tishi("本机投产中！不能操作","tishi");
		return ;
	} */
		$.ajax({
			 url:"<%=basePath%>findUnitName.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{cityS:row.districtCounty},
			 beforeSend:function(){},
			 success:function(data){
				for(var i=0;i<data.length;i++){
					$("#jgmc_edit").append("<option value='"+data[i].unitCode+"'>"+data[i].unitName+"</option>")
				}
				selectTr(row);
			 },
			 complete:function(){
				statementGrid.datagrid('clearSelections')
				statementGrid.datagrid('reload');
			 },
			 error:function(){}
		});
		machineEdit();
		selectTr(row);
	}else{
		$(".mask").show();
		var tishi=new Tishi("请选择一条记录","tishi");
	}
});
//删除
$("#button_delete").click(function(){
		 var row = $('#statementGrid').datagrid('getSelections'); 
		 var machineNo ="";
		 if(row.length>1){
				var tishi=new Tishi("只能选择一条信息","tishi");
				return;
			}
	     if (row) { 
	    	 machineNo = row[0].id.machineNo;
	    			/* for(var i=0;i<row.length;i++){
	    				if(row[i].id.machineNo>1){
	    		    		 var tishi=new Tishi("只能选择一条记录","tishi");
	    		    		 return;
	    		    	 }
	   	    		 machineNo.push(row[i].id.machineNo);
	   	    	 	}
	    	 	console.log(machineNo); */
	    		var pop=new Pop("删除","remind_ok","tishi");
	    		$("#remind_ok").on("click",function(){
	    	 		 $.ajax({
	    	 			 url:"<%=basePath%>delDeployMachine.do",
	    	 			 cache:false,
	    	 			 type:"post",
	    	 			 datatype:'json',
	    	 			 data:{machineNo:machineNo},
	    	 			 beforeSend:function(){},
	    	 			 success:function(data){
	    	 				if(data.success==true){
	    	 					var tishi=new Tishi("删除成功","chenggong");
	    	 					onld();
	    	 				}else{
	    	 					 $(".mask").show();
	    	 					var tishi=new Tishi(data.errmsg,"shibai");
	    	 				//	var tishi=new Tishi("删除失败","shibai");
	    	 				}
	    	 			 },
	    	 			 complete:function(XHR, textStatus){
	    	 				statementGrid.datagrid('clearSelections');
	    					statementGrid.datagrid('reload');
	    	 			 },
	    	 			 error:function(){var tishi=new Tishi(data.errmsg,"shibai");
	    	 			//	var tishi=new Tishi("删除失败","shibai");
	    	 			 }
	    	 		})
	    	 	});
	    	 }else{
	    		 var tishi=new Tishi("请选择一条记录","tishi");
	    	 }
	 	
	 });
 //投产
$("#button_touchan").on("click",function(){
	touChanTr();
 });
$("#button_xiaxian").on("click",function(){
	touChanTr();
});
//添加中的保存结束
//关闭按钮
    $(".firm_add_head a").click(function(){
        $(".firm_add_add").hide();
        $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
    })
//取消按钮
    $("#close_add_add,#close_add_edit").click(function(event){
        event.preventDefault();
        $(".firm_add_add").hide();
        $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
    });

	$("#save_add_add").click(function(){
		event.preventDefault();
		$("#form1").submit();
		
		return false;
	})
    	
	$("#save_add_edit").click(function(){
		event.preventDefault();
		$("#form2").submit();
		return false;
	})
	// 添加按钮事件
    $("#button_add_add").click(function(event){
    	$(".username_info").hide();
    	event.preventDefault();
		clearForm();
    	$("#sjjg_add").attr("disabled","disabled"); //上级机构默认为不可编辑
    	$("#jgmc_add").attr("disabled","disabled"); //上级机构默认为不可编辑
    	$(".firm_add_add").css("display","block");
    	$("#access_pwd_sign_shanghu").val("123456789012345");
    	$("#access_pwd_sign_qudao").val("0035");
    	$("#other_pwd_sign_add").val("ZZQZ.00000001.zpk");//转加密密钥标记为
    	$("#mac_pwd_sign_add").val("ZZQZ.00000001.zak");//MAC密钥标记
		$(".mask").show();
		    $.ajax({
				url:"<%=basePath%>findMachineList1.do",
				cache:false,
				type:"post",
				datatype:'json',
				beforeSend:function(){},
				success:function(data){
					$("#jqmc_add").html("<option value=''>请选择</option>");
					for(var i = 0;i<data.rows.length;i++){
           				$("#jqmc_add").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
           			}
				},
				complete:function(){
					
				},
				error:function(){}
			})
    });
	var machineCode;
	$("#jqmc_add").change(function(){
		var jqmc_add=$("#jqmc_add option:selected").text();
		$.ajax({
			url:"<%=basePath%>findMachineList.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:{machineName:jqmc_add,page:1,rows:10},
			beforeSend:function(){},
			success:function(data){
				machineCode=data.rows[0].machineCode;
			},
		});
	})
	$("#jqbh1_add").blur(function(){
		if($(this).val() != ""){
			$("#main_pwd_sign_add").val("CODM."+$(this).val()+".zmk");// 键盘主密码标记
			$("#work_pwd_sign_add").val("CODM."+$(this).val()+".zpk");// 键盘工作密码标记
		}
	});
	function subbswh(){
		
		var keyboard_major_keyflag=$("#main_pwd_sign_add").val();// 键盘主密码标记
		var keyboard_work_keyflag=$("#work_pwd_sign_add").val();// 键盘工作密码标记
		var machineNo=$("#jqbh1_add").val();//机器编号 
		var machineName=$("#jqmc_add").val();// 机器名称
		var unitCode=$("#jgmc_add").val();// 机构名称
		var tellerNo=$("#gyh_add").val();// 柜员号
		var ip=$("#ip_add").val();// IP地址
		
		var key_flag=$("#other_pwd_sign_add").val();// 转加密密钥标记
		var mac_keyflag=$("#mac_pwd_sign_add").val();// MAC密钥标记
		var authorize_keyflag=$("#access_pwd_sign_add").val();// 机器出厂编号
		var qudao  = $("#access_pwd_sign_qudao").val(); // 渠道号
		var shanghu  = $("#access_pwd_sign_shanghu").val(); // 商户号
		var cityS=$("#ssqx_add").val();
		var data={cityS:cityS,machineNo:machineNo,machineName:machineName,unitCode:unitCode,tellerNo:tellerNo,ip:ip,machineCode:machineCode,machineCode:machineCode,keyboard_major_keyflag:keyboard_major_keyflag,keyboard_work_keyflag:keyboard_work_keyflag,key_flag:key_flag,mac_keyflag:mac_keyflag,macFacNum:authorize_keyflag,merNo:shanghu,channel:qudao}
		$.ajax({
			url:"<%=basePath%>saveDeployMachine.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:data,
			beforeSend:function(){},
			success:function(data){
				if(data.success == true){	
					$(".firm_add_add").css("display","none");
					var tishi=new Tishi("保存成功","chenggong");
				}else if(data.errmsg == 01){
					$(".username_info").show();
				}else if(data.errmsg == 02){
					$(".email_info").show();
				}else if(data.errmsg == 03){
					$(".username_infoqq").show();
					$("#errorInformation").html("* 此机器已投产使用!");
				}else if(data.errmsg == 04){
					$(".username_infoqq").show();
					$("#errorInformation").html("* 此机器已部署到本行!");
				}
			},
			complete:function(){
				statementGrid.datagrid('clearSelections')
				statementGrid.datagrid('reload');
			},
			error:function(){$(".firm_add_add").css("display","none");var tishi=new Tishi("保存失败","shibai");}
		})
	};
			// 清空表单
function clearForm(){
	$("#jqbh1_add").val("");//厂商编号
	$("#jqbh_add").val("");// 厂商名称
	$("#jgmc_add").val("");// 联系人
	$("#gyh_add").val("");// 座机
	$("#phone").val("");// 手机
	$("#ip_add").val("");// 厂商状态
	$("#jqmc_add").val("");// 备注
	$("#main_pwd_sign_add").val("");
	$("#work_pwd_sign_add").val("");
	$("#other_pwd_sign_add").val("");
	$("#mac_pwd_sign_add").val("");
	$("#access_pwd_sign_add").val("");
	$("#ssqx_add").val("");
}
	$("#jqmc_edit").on("change",function(){
		var jqmc_edit=$("#jqmc_edit option:selected").text();
		$.ajax({
			url:"<%=basePath%>findMachineList.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:{machineName:jqmc_edit,page:1,rows:10},
			beforeSend:function(){},
			success:function(data){
				var machineCode=data.rows[0].machineCode; 
			},
		});
	});
	  function subbswh2(){
		//	event.preventDefault();
			var machineNo=$("#jqbh_edit").val();//机器编号 
			var machineType=$("#jqlx_edit").val();// 机器类型
			var machineCode=$("#jqmc_edit").val();// 机器名称
			var unitCode=$("#jgmc_edit").val();// 机构名称
			var tellerNo=$("#gyh_edit").val();// 柜员号
			var ip=$("#ip_edit").val();// IP地址
			var keyboard_major_keyflag=$("#main_pwd_sign_edit").val();// 键盘主密码标记
			var keyboard_work_keyflag=$("#work_pwd_sign_edit").val();// 键盘工作密码标记
			var key_flag=$("#other_pwd_sign_edit").val();// 转加密密钥标记
			var mac_keyflag=$("#mac_pwd_sign_edit").val();// MAC密钥标记
			var authorize_keyflag=$("#access_pwd_sign_edit").val();// 机器出厂编号
			var qudao  = $("#access_pwd_sign_qudao1").val(); // 渠道号
			var shanghu  = $("#access_pwd_sign_shanghu1").val(); // 商户号
			var cityS=$("#ssqx_edit").val();
			var data={cityS:cityS,machineNo:machineNo,machineType:machineType,machineCode:machineCode,unitCode:unitCode,tellerNo:tellerNo,ip:ip,keyboard_major_keyflag:keyboard_major_keyflag,keyboard_work_keyflag:keyboard_work_keyflag,key_flag:key_flag,mac_keyflag:mac_keyflag,macFacNum:authorize_keyflag,merNo:shanghu,channel:qudao}
			$.ajax({
				url:"<%=basePath%>editDeployMachine.do",
				cache:false,
				type:"post",
				datatype:'json',
				data:data,
				beforeSend:function(){},
				success:function(data){
					if(data.success == true){
						$(".unit_pop2").hide();
						var tishi=new Tishi("保存成功","chenggong");
						$("#table_tbody").html("");	
					}else if(data.errmsg == 03){
						$(".unit_pop2").hide();
						var tishi=new Tishi("出产编号正在被使用","tishi");
					}else{
						$(".bj_ip_info").show();
					}
				},
				complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
				},
				error:function(){var tishi=new Tishi("保存失败","shibai");}
			});
};    
// 设置机器类型
   function setSelectMacType(){
	   var data= {groupName:"machineType"};
	   $.ajax({
           url:"<%=basePath%>system/getDict.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
           	if(data.success == true){	
           		for(var i = 0;i<data.info.length;i++){
           		    $("#jqlx").append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
           		}
           	}else{
           		
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
// 状态
   function setSelectStatus(){
	   $("#sbzt").append("<option value=''>请选择</option>");
	   var data= {groupName:"status"};
	   $.ajax({
           url:"<%=basePath%>system/getDict.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
           	if(data.success == true){	
           		for(var i = 0;i<data.info.length;i++){
           		    $("#sbzt").append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
           		}
           	}else{
           		
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
// 机构
   function setSelectUnitCode(){
	   $("#jgmc").append("<option value=''>请选择</option>");
	   var data= {};
	   $.ajax({
           url:"<%=basePath%>system/findParentUnit.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
           	if(data.success == true){	
           		for(var i = 0;i<data.rows.length;i++){
           		    $("#jgmc").append("<option value='"+data.rows[i].unitViewId.unitCode+"'>"+data.rows[i].unitName+"</option>");
           		}
           	}else{
           		
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
// 设置机器类型
   function setPerType(par){
	   var data= {groupName:"machineType"};
	   $.ajax({
           url:"<%=basePath%>system/getDict.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
           	if(data.success == true){	
           		for(var i = 0;i<data.info.length;i++){
           		    $("#"+par).append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
           		}
           	}else{
           		
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
   //机构查询
   function setPerunitCode(par){
	   $("#"+par).append("<option value=''>请选择</option>");
	   var data= {};
	   $.ajax({
           url:"<%=basePath%>/system/findParentUnit.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
           	if(data.success == true){	
           		for(var i = 0;i<data.rows.length;i++){
           		    $("#"+par).append("<option value='"+data.rows[i].unitViewId.unitCode+"'>"+data.rows[i].unitName+"</option>");
           		}
           	}else{
           		
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
 //测试投产
	function touChan(status,machineNo,macFacNum){
		if(status == 0 || status == 2){
			if(status  == 0 || status ==2){
				status = 1;
			}else if(status == 1){
				status = 2;
			}
			$.ajax({
				 url:"<%=basePath%>editDeployMachine2.do",
				 cache:false,
				 type:"post",
				 datatype:'json',
				 data:{status:status,machineNo:machineNo,macFacNum:macFacNum},
				 beforeSend:function(){},
				 success:function(data){
					if(data.success == true){	
						var tishi=new Tishi("投产成功","chenggong");
						$(".reset_pwd").hide();
					}else{
						var tishi=new Tishi(data.errmsg,"shibai");
					}
				 },
				 complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
				 },
				 error:function(){var tishi=new Tishi("投产失败","shibai");}
			});
		}else{
			if(status == 1){
				status = 2;
				$.ajax({
				 url:"<%=basePath%>editDeployMachine2.do",
				 cache:false,
				 type:"post",
				 datatype:'json',
				 data:{status:status,machineNo:machineNo},
				 beforeSend:function(){},
				 success:function(data){
					if(data.success == true){	
						var tishi=new Tishi("下线成功","chenggong");
						$(".reset_pwd").hide();
					}else{
						var tishi=new Tishi("下线失败","shibai");
					}
				 },
				 complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
				 },
				 error:function(){var tishi=new Tishi("下线失败","shibai");}
			});
			}
		}
	};
	$("#reset_pwd1_no").click(function(){
	 $(".reset_pwd1").hide(); 
		$(".mask").hide();
	});
	$("#reset_pwd1_ok").click(function(){
		//var managePassword=$("#reset_pwd1").val();
		if(status  == 0 || status ==2){
			status = 1;
		}else if(status == 1){
			status = 2;
		}
		var nn = {status:status,machineNo:machineNo,remark:$("#reset_pwd1").val()};
		$.ajax({
			 url:"<%=basePath%>editDeployMachineRemark.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{status:status,machineNo:machineNo,remark:$("#reset_pwd1").val()},
			 beforeSend:function(){},
			 success:function(data){
				if(data.success == true){	
					var tishi=new Tishi("重置成功","chenggong");
					$(".reset_pwd").hide();
					selTable();
				}else{
					var tishi=new Tishi("重置失败","shibai");
				}
			 },
			 complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
			 },
			 error:function(){var tishi=new Tishi("重置失败","shibai");}
		})
	})
		$("#reset_pwd_no").click(function(){
			$(".reset_pwdA").hide();
			$(".mask").hide();
		});
		//单击获取事件
		 function resetTr(){
			 var row = $('#statementGrid').datagrid('getSelected'); 
			 if (row) { 
				 $(".reset_pwdA").show();
				 $(".mask").show();
				 $("#reset_pwd_ok").click(function(){
					resetPwd(row.machineNo);
				})
			 } else{
				 $(".mask").show();
				 var tishi=new Tishi("请选择一条记录","tishi");
			 }
		 }
		function resetPwd(machineNo){
			var managePassword=$("#reset_pwd").val();
			$.ajax({
				 url:"<%=basePath%>editDeployMachine1.do",
				 cache:false,
				 type:"post",
				 datatype:'json',
				 data:{ManagePassword:managePassword,machineNo:machineNo},
				 beforeSend:function(){},
				 success:function(data){
					if(data.success == true){	
						var tishi=new Tishi("重置成功","chenggong");
						$(".reset_pwd").hide();
					}else{
						var tishi=new Tishi("重置失败","tishi");
					}
				 },
				 complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
				 },
				 error:function(){var tishi=new Tishi("重置失败","shibai");}
			})
		}
		$("#button_resetpwd").click(function(){
			resetTr();
		})
		$("#ssqx_add").change(function(){
			$("#jgmc_add").html("<option value=''>请选择</option>");
			var cityS=$("#ssqx_add").val();
			if($("#ssqx_add").val()!=""){
				$("#jgmc_add").removeAttr("disabled");
				$.ajax({
					 url:"<%=basePath%>findUnitName.do",
					 cache:false,
					 type:"post",
					 datatype:'json',
					 data:{cityS:cityS},
					 beforeSend:function(){},
					 success:function(data){
						for(var i=0;i<data.length;i++){
							$("#jgmc_add").append("<option value='"+data[i].unitCode+"'>"+data[i].unitName+"</option>")
						}
					 },
					 complete:function(){
						statementGrid.datagrid('clearSelections')
						statementGrid.datagrid('reload');
					 },
					 error:function(){}
				})
			}else{
				$("#jgmc_add").attr("disabled","disabled");
			}
			
		})
			$("#ssqx_edit").change(function(){
			$("#jgmc_edit").html("<option value=''>请选择</option>");
			var cityS=$("#ssqx_edit").val();
			if($("#ssqx_edit").val()!=""){
				$("#jgmc_edit").removeAttr("disabled");
				$.ajax({
					 url:"<%=basePath%>findUnitName.do",
					 cache:false,
					 type:"post",
					 datatype:'json',
					 data:{cityS:cityS},
					 beforeSend:function(){},
					 success:function(data){
						for(var i=0;i<data.length;i++){
							$("#jgmc_edit").append("<option value='"+data[i].unitCode+"'>"+data[i].unitName+"</option>")
						}
					 },
					 complete:function(){
						statementGrid.datagrid('clearSelections')
						statementGrid.datagrid('reload');
					 },
					 error:function(){}
				})
			}else{
				$("#jgmc_edit").attr("disabled","disabled");
			}
			
		})
		 /*导出  */
   $("#button_dC").click(function(){
   	
   	console.log(selll());
   	$.ajax({
   		url:"<%=basePath%>outDeployMachineExcel.do",
   		cache:false,
   		type:"post",
   		datatype:'json',
   		data:selll(),
   		async:false,
   		beforeSend:function(){},
   		success:function(data){
   			window.open("../../ReportTemplate/DeployMachineExcel.xls");
   		},
   		complete:function(){},
   		error:function(){
   			var tishi=new Tishi("导出失败","shibai");
   		}
   	})
   });
</script>

