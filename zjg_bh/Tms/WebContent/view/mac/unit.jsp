<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

    <!--添加开始-->
    <div class="firm_add_add unit_pop" style='margin-top:-270px;'>
        <form id="form1" action="">
        <div class="firm_add_head">
            <span>添加机构</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
			<div class="common_info jgbh_info"><b class="info_caret"></b>*机构编号已存在！</div>
			<div class="common_info jgmc_info"><b class="info_caret"></b>*机构名称已存在！</div>
			<p><span><b>*</b>机构编号：</span><input class="validate[required]" type="text" id="jgbh_add" name="jgbh_add"/>
            <span><b>*</b>机构名称：</span><input class="validate[required]" type="text" id="jgmc_add" /></p>
             <p><span><b>*</b>机构类型：</span><select class="validate[required]" value="" id="jglx_add" >
             		<option value="">请选择</option>
                    <option value="0">总行</option>
                    <option value="1">分行</option>
                    <option value="2">支行</option>
                    <option value="3">网点</option>
                </select>
             <span><b>*</b>机构地址：</span><input  type="text" id="jgdz_add" /></p>
             <p>
             	<span><b>*</b>所属区县：</span><select class="validate[required]"id="ssqx_add">
             		<option value="">请选择</option>
             	</select>
             	<span><b>*</b>机构电话：</span><input  type="text" id="jgdh_add" />
             </p>
             <p>
             	<span><b>*</b>机构负责人：</span><input  type="text" value="" id="jgfzr_add" />
             	<span><b>*</b>E-mail：</span><input  type="text" id="mail_add" />
              </p>
             <p>
             	<span><b>*</b>手机：</span><input  type="text" id="phone_add" />
             	<span><b>*</b>机构状态：</span><select class="validate[required]" value="" id="jgzt_add" >
                    <option value="1">正常</option>
                    <option value="0">废弃</option>
                </select>
			</p>
			<p>
				<span><b>*</b>上级机构：</span><select class="validate[required]" value="" id="sjjg_add" >
                    <option value="">请选择</option>
                    
                </select>
			</p>
			</div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add">保存</button>
        </div>
        </form>
    </div>
    <!--添加结束-->
    <div class="firm_add_edit unit_pop" style='margin-top:-270px;'>
        <form id="form2" action="">
        <div class="firm_add_head">
            <span>编辑机构</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
			<div class="common_info jgmc_info"><b class="info_caret"></b>*机构名称已存在！</div>
			<p><span><b>*</b>机构编号：</span><input class="validate[required,custom[number]]" type="text" id="jgbh_edit" />
            <span><b>*</b>机构名称：</span><input class="validate[required]" type="text" id="jgmc_edit"/></p>
             <p><span><b>*</b>机构类型：</span><select class="validate[required]" value="" id="jglx_edit">
             		<option value="">请选择</option>
                    <option value="0">总行</option>
                    <option value="1">分行</option>
                    <option value="2">支行</option>
                    <option value="3">网点</option>
                </select>
             <span><b>*</b>机构地址：</span><input type="text" id="jgdz_edit"/></p>
             <p>
             	<span><b>*</b>所属区县：</span><select class="validate[required]"id="ssqx_edit">
             		<option value="">请选择</option>
             	</select>
             	<span><b>*</b>机构电话：</span><input  type="text" id="jgdh_edit"/>
             </p>
             <p>
             	<span><b>*</b>机构负责人：</span><input  type="text" value="" id="jgfzr_edit">
             	<span><b>*</b>E-mail：</span><input  type="text" id="mail_edit"/>
              </p>
             <p>
             	<span><b>*</b>手机：</span><input  type="text" id="phone_edit"/>
             	<span><b>*</b>机构状态：</span><select class="validate[required]" value="" id="jgzt_edit">
                    <option value="1">正常</option>
                    <option value="0">废弃</option>
                </select>
			</p>
			<p>
				<span><b>*</b>上级机构：</span><select class="validate[required]" value="" id="sjjg_edit">
                </select>
			</p>
        </div>
        <div>
            <button id="close_add_edit">取消</button>
            <button id="save_add_edit">保存</button>
        </div>
        </form>
    </div>
	
		<table class="search_box">
			<tr>
				<td><span>机构名称：</span></td>
				<td><input type="text" id="manu_info"></td>
				<td><span>上级机构：</span></td>
				<td><input type="text" id="manu_status"></td>
			</tr>
		</table>
<div id="refresh">
<div class="newSonstruction">
	<button id="button_search" onclick="findManu()">
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
	<button id="button_daoc">
         <span class="export_ico"></span>
         <span class="sarch">导出</span>
     </button>
</div>
<div style="padding-top: 10px" > 
	<table id="statementGrid" style="width:100%; height:auto;max-height:350px;"></table>
</div>
</div>

<script>
var statementGrid;
$(function(){
	onld();
	valid();
	$('#jgbh_add,#jgmc_add').blur(function(){
		$('.common_info').hide()
	})
	for(var i=0;i<Tms_bankInfo.length;i++){
		$("#ssqx_edit,#ssqx_add").append("<option value='"+Tms_bankInfo[i].city+"'>"+Tms_bankInfo[i].city+"</option>")
	}
})
function onld(sel){
	statementGrid = $('#statementGrid').datagrid({
		title : '',
		url : '<%=basePath%>system/findunit.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'unitCode',
		singleSelect: true,
		autoRowHeight:true,
		queryParams:sel,
		//sortName : 'payDate',
		//sortOrder : 'asc',
		fitColumns:true ,
		nowrap:false,
		columns : [ [ 
			{
				width : 20,
				field : 'ck',
				checkbox : true
			},
			{
				width : 100,
				title : '机构编号',
				field : 'unitCode',
				align : 'center',
				formatter : function(value, row,index) {
					value=row['unitViewId']['unitCode'] ;
					return value;
				}
			}
			,
			{
				width : 100,
				title : '机构名称',
				field : 'unitName',
				align : 'center',
			},
			{
				width : 210,
				title : '机构地址',
				field : 'address',
				align : 'center',
			},
			{
				width : 100,
				title : '机构类型',
				field : 'unitType',
				align : 'center',
				formatter : function(value, row,index) {
					value="";
					if(row['unitType'] == '0'){
						value = '0-总行';
					}else if(row['unitType'] == '1'){
						value = '1-分行';
					}else if(row['unitType'] == '2'){
						value = '2-支行';
					}else if(row['unitType'] == '3'){
						value = '3-网点';
					}
					return value;
				}
			},
			{
				width : 150,
				title : '机构电话',
				field : 'tel',
				align : 'center',
			},
			{
				width : 100,
				title : '机构负责人',
				field : 'contactor',
				align : 'center',
			},
			{
				width : 150,
				title : 'E-mail',
				field : 'email',
				align : 'center',
			},
			{
				width : 150,
				title : '所属区县',
				field : 'districtCounty',
				align : 'center',
				hidden:true
			},
			
			{
				width : 100,
				title : '机构状态',
				field : 'status',
				align : 'center',
				sortable : false,
				formatter : function(value, row,index) {
					value="";
					if(row['status'] == '1'){
						value = '1-正常';
					}else if(row['status'] == '0'){
						value = '0-废弃';
					}
					return value;
				}
			},
			{
				width : 100,
				title : '上级机构',
				field : 'parentName',
				align : 'center',
			}
		] ]
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

function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				$(".jgbh_info").hide();
				$(".jgmc_info").hide();
				subAddAdd();
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
				$(".jgbh_info").hide();
				$(".jgmc_info").hide();
				saveEdite();
			}
		}
	});
}
    $("#close_add_edit").click(function(){
        $(".firm_add_edit").hide();
		$(".mask").hide();
    })
//	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
    })//查询按钮事件结束
	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 unitName=$("#manu_info").val();
	 parentName=$("#manu_status").val();
	 sel  = {page:page,rows:rows,unitName:unitName,parentName:parentName}
	return sel;
}
////关闭按钮
    $(".firm_add_head a").click(function(){
        $(".firm_add_add").hide();
        $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
    })
////取消按钮
    $("#close_add_add,#close_add_edit").click(function(event){
        event.preventDefault();
        $(".firm_add_add").hide();
        $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
		return false;
    });
////隔行变色封装
//	// 添加按钮事件
    $("#button_add_add").click(function(event){
    	event.preventDefault();
    	clearForm();
    	$("#sjjg_add").attr("disabled","disabled"); //上级机构默认为不可编辑
    	$(".firm_add_add").css("display","block");
		$(".mask").show();
    })
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
    	
	function subAddAdd(){
		var unitCode=$("#jgbh_add").val();//机构编号
    	var unitName=$("#jgmc_add").val();// 机构名称
    	var unitType=$("#jglx_add  option:selected").val();// 机构类型
    	var address=$("#jgdz_add").val();// 机构地址
    	var tel=$("#jgdh_add").val();// 机构电话
    	var contactor=$("#jgfzr_add").val();// 负责人
    	var email=$("#mail_add").val();// email
    	var contactorPhone=$("#phone_add").val();// 手机
    	var status=$("#jgzt_add").val();//结构状态
    	var parentCode= $("#sjjg_add  option:selected").val();// 上级机构
    	var cityS=$("#ssqx_add").val();
    	var data={cityS:cityS,unitCode:unitCode,unitName:unitName,unitType:unitType,address:address,tel:tel,contactor:contactor,email:email,contactorPhone:contactorPhone,status:status,parentCode:parentCode};
    		$.ajax({
                url:"<%=basePath%>system/saveUnit.do",
                cache:false,
                type:"post",
                datatype:'json',
                data:data,
                beforeSend:function(){},
                success:function(data){
					console.log(data)
					if(data.errmsg == 01){
						$(".jgbh_info").show();
					}else if(data.errmsg == 02){
						$(".jgmc_info").show();
					}else{
						$(".firm_add_add").css("display","none");
						var tishi=new Tishi("保存成功","chenggong");
					}
                },
                complete:function(){
                	statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
                },
                error:function(){var tishi=new Tishi("保存失败","shibai");$(".firm_add_add").css("display","none"); }
    		});	
	}
//	//	删除
	$("#button_delete").click(function(){
		 var row = $('#statementGrid').datagrid('getSelected'); 
	     if (row) { 
	    		var unitCode = row.unitViewId.unitCode;
	    		console.log(unitCode);
	    		var pop=new Pop("删除","remind_ok","tishi");
	    		$("#remind_ok").on("click",function(){
	    	 		 $.ajax({
	    	 			 url:"<%=basePath%>system/delUnit.do",
	    	 			 cache:false,
	    	 			 type:"post",
	    	 			 datatype:'json',
	    	 			 data:{unitCode:unitCode},
	    	 			 beforeSend:function(){},
	    	 			 success:function(data){
	    	 				if(data.success==true){
	    	 					var tishi=new Tishi("删除成功","chenggong");
	    	 					onld();
	    	 				}else{
	    	 					 $(".mask").show();
	    	 					var tishi=new Tishi(data.errmsg,"shibai");
	    	 				}
	    	 			 },
	    	 			 complete:function(XHR, textStatus){
	    	 				statementGrid.datagrid('clearSelections');
	    					statementGrid.datagrid('reload');
	    	 			 },
	    	 			 error:function(){
	    	 				var tishi=new Tishi(data.errmsg,"shibai");
	    	 			 }
	    	 		})
	    	 	});
	    	 }else{
	    		 var tishi=new Tishi("请选择一条记录","tishi");
	    	 }
	 	
	 });

	//	编辑开始
	$("#button_edit").on("click",function(){
			$(".jgbh_info").hide();
			$(".jgmc_info").hide();
			selectTr();
	});
	//表格单选事件
 function selectTr(){
	 var row = $('#statementGrid').datagrid('getSelected'); 
	 console.log(row)
     if (row) { 
			$(".firm_add_edit,.mask").show();
			$("#jgbh_edit").attr("disabled",true);
			$("#sjjg_edit").attr("disabled","disabled");
    	    $("#jgbh_edit").val(row.unitViewId.unitCode);//机器编号
    	    $("#jgmc_edit").val(row.unitName);//机器编号
    	 	$("#jglx_edit").val(row.unitType);// 机器名称
    	 	$("#jgdz_edit").val(row.address);// 柜员号
    	 	$("#jgfzr_edit").val(row.contactor);// 机构名称
    	 	$("#mail_edit").val(row.email);// ip地址
    	 	$("#jgdh_edit").val(row.tel);// ip地址
    	 	$("#phone_edit").val(row.contactorPhone);// ip地址
    	 	$("#jgzt_edit").val(row.status);// ip地址
    	 	$("#sjjg_edit").html("");
    	 	$("#sjjg_edit").append("<option value='"+row.parentCode+"'>"+row.parentName+"</option>");// ip地址
    	 	console.log("111111111111");
    	 	console.log(row.parentName);
    	 	$("#ssqx_edit").val(row.districtCounty);
     } else{
    	 $(".mask").show();
    	 var tishi=new Tishi("请选择一条记录","tishi");
     }
 	
 }
//表格删除单选事件
//
    function saveEdite(){
        	var unitCode=$("#jgbh_edit").val();//机构编号
        	var unitName=$("#jgmc_edit").val();// 机构名称
        	var unitType=$("#jglx_edit  option:selected").val();// 机构类型
        	var address=$("#jgdz_edit").val();// 机构地址
        	var tel=$("#jgdh_edit").val();// 机构电话
        	var contactor=$("#jgfzr_edit").val();// 负责人
        	var email=$("#mail_edit").val();// email
        	var contactorPhone=$("#phone_edit").val();// 手机
         	var status=$("#jgzt_edit").val();//结构状态
        	var parentCode= $("#sjjg_edit  option:selected").val();// 上级机构
        	var cityS=$("#ssqx_edit").val();
        	var data={cityS:cityS,unitCode:unitCode,unitName:unitName,unitType:unitType,address:address,tel:tel,contactor:contactor,email:email,contactorPhone:contactorPhone,status:status,parentCode:parentCode}
        	$.ajax({
				url:"<%=basePath%>system/saveUnit1.do",
				cache:false,
				type:"post",
				datatype:'json',
				data:data,
				beforeSend:function(){},
				success:function(data){
					console.log(data)
					if(data.errmsg == 02){
						$(".jgmc_info").show();
					}else{
						$(".firm_add_edit").css("display","none");
						var tishi=new Tishi("保存成功","chenggong");
					}
				},
				complete:function(){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
				}
           });
 	}     
   // 添加机构界面中的 机构下拉框选中事件
$("#jglx_add").change(function(){
	  $("#sjjg_add").empty();
	  $("#sjjg_add").append("<option value=''>请选择</option>");
 	  var checkValue=$("#jglx_add").val();
 	  if(checkValue != "" && checkValue != "0"){
 		$('#sjjg_add').removeAttr("disabled");
 		var sel = "sjjg_add";
 		getUnitSelect(checkValue,sel);
 	  }else{
 		$("#sjjg_add").attr("disabled","disabled"); 
 	  }
   }); 
   
   // 添加机构界面中的 机构下拉框选中事件
   $("#jglx_edit").change(function(){
	  $("#sjjg_edit").empty();
	  $("#sjjg_edit").append("<option value=''>请选择</option>");
 	  var checkValue=$("#jglx_edit").val();
 	  if(checkValue != " " && checkValue != "0"){
 		$('#sjjg_edit').removeAttr("disabled");
 		var sel = "sjjg_edit";
 		getUnitSelect(checkValue,sel);
 	  }else{
 		$("#sjjg_edit").attr("disabled","disabled"); 
 	  }
   }); 
    
   //获取指定机构类型的上一级机构
   function getUnitSelect(unitType,sel){
	   $("#"+sel).empty();
	   $("#"+sel).append("<option value=''>请选择</option>");
	   var data= {unitType:unitType};
	   $.ajax({
           url:"<%=basePath%>system/findParentUnit.do",
           cache:false,
           type:"post",
           datatype:'json',
           data:data,
           beforeSend:function(){},
           success:function(data){
        	   console.log(data);
           	if(data.success == true){	
           		for(var i = 0;i<data.rows.length;i++){
           		    $("#"+sel).append("<option value='"+data.rows[i].unitViewId.unitCode+"'>"+data.rows[i].unitName+"</option>");
           		}
           	}else{
           		
           	}
           	console.log(data);
           },
           complete:function(){},
           error:function(){}
		})
   }
   /*导出  */
   $("#button_daoc").click(function(){
   	
   	console.log(selll());
   	$.ajax({
   		url:"<%=basePath%>outUnit.do",
   		cache:false,
   		type:"post",
   		datatype:'json',
   		data:selll(),
   		async:false,
   		beforeSend:function(){},
   		success:function(data){
   			window.open("../../ReportTemplate/unitEXCEt.xls");
   		},
   		complete:function(){},
   		error:function(){
   			var tishi=new Tishi("导出失败","shibai");
   		}
   	})
   });
   function clearForm(){
	   $("#jgbh_add").val("");//机器编号
  	    $("#jgmc_add").val("");//机器编号
  	 	$("#jglx_add").val("");// 机器名称
  	 	$("#jgdz_add").val("");// 柜员号
  	 	$("#jgfzr_add").val("");// 机构名称
  	 	$("#mail_add").val("");// ip地址
  	 	$("#jgdh_add").val("");// ip地址
  	 	$("#phone_add").val("");// ip地址
  	 	$("#sjjg_add").val("");
  	 	$("#ssqx_add").val("");
   }
</script>
