<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--外设维护的添加与编辑-->
   <div class="firm_add_add">
        <form id="form1" action="">
        <div class="firm_add_head">
            <span>添加外设</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
            <p><span><b>*</b>外设型号：</span><input class="validate[required]" type="text" id="jgmc_add2"/>
            <span><b>*</b>外设类型：</span>
            <select class="validate[required]" value="" id="sjjg_add2">
              
                   
             </select></p>
             <div class="common_info username_info"><b class="info_caret"></b>* 外设型号已存在!</div>
			
             <p><span><b>*</b>外设名称：</span><input class="validate[required]" type="text" id="jgdz_add2"/></p>
              <div class="common_info email_info_top "><b class="info_caret"></b>* 外设名称已存在!</div>
        </div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add">保存</button>
        </div>
        </form>
    </div>
    <!--添加结束-->
<!-- 	编辑开始 -->
   <div class="firm_add_edit">
        <form id="form2" action="">
        <div class="firm_add_head">
            <span>编辑外设</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
            <p><span><b>*</b>外设型号：</span><input class="validate[required]" type="text" id="jgmc_add"/>
            <span><b>*</b>外设类型：</span>
            <select class="validate[required]" value="" id="jglx_add">
                    <option value="">请选择</option>
                </select></p>
            <div class="common_info username_info"><b class="info_caret"></b>* 外设型号已存在!</div>
             <p>
             	<span><b>*</b>外设名称：</span><input class="validate[required]" type="text" id="jgdz_add"/>
             	<input style="display:none" class="validate[required]" type="text" id="jgdz_add11"/>
             	<span style="display:none"><b>*</b>外设代码：</span><input style="display:none" type="text" id="jgbh_addd"/>
             </p>
              <div class="common_info email_info_top "><b class="info_caret"></b>* 外设名称已存在!</div>
        </div>
        <div>
            <button id="close_add_edit">取消</button>
            <button id="save_add_edit"  onclick="saveEditEdit(this)">保存</button>
        </div>
        </form>
    </div>
<!-- 编辑结束 -->
	<div class="com_nav">
		<table class="search_box">
				<tr>
					<td><span>外设名称： </span> </td>
					<td><input type="text" id="jgdz_add1" /></td>
					<td><span>外设型号： </span></td>
					<td><input type="text" id="jgmc_add1"/></td>
					<td><span>外设类型： </span></td>
					<td>
						<select value="" class="firmZT" id="sjjg_add1">
							<option value="">请选择</option>
						</select>
					</td>
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
				
				<button class="edit">
					<span class="edit_icon"></span>
					<span class="sarch">编辑</span>
				</button>
				<button class="delete">
					<span class="delete_icon"></span>
					<span class="sarch">删除</span>
				</button>
				<button id="button_d">
	                <span class="export_ico"></span>
	                <span class="sarch">导出</span>
            	</button>
			</div>
			<table id="statementGrid" style="width:100%; height:auto;max-height:321px;"></table>
			<div class="table_box">
			</div>
		</div>
	</div>
<script>

var page = 1;
var rows = 10;
var sel;
var statementGrid;
var jgmc = "";
var sjjg = "";
var jgdz = "";
$(function(){
	onlds();
	setSelectMacType();
	setPerType("jglx_add");// 初始化 编辑框中的外设类型
	setPerType("sjjg_add1")// 初始化 查询条件中的外设类型
	valid();
	/*导出  */
	$("#button_d").click(function(){
		var dataN = {
				periName : selll().periName,
				periType : selll().periType,
				periCode : selll().periCode
			};
		console.log(selll());
		console.log(dataN);
		$.ajax({
			url:"<%=basePath%>outPeripherals.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:dataN,
			async:false,
			beforeSend:function(){},
			success:function(data){
				window.open("../../ReportTemplate/Peripherals.xls");
			},
			complete:function(){},
			error:function(){
				var tishi=new Tishi("导出失败","shibai");
			}
		})
	});
});

function onlds(sel){
	
	statementGrid = $('#statementGrid').datagrid({
		url : '<%=basePath%>findPeripherals.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'payId',
		autoRowHeight:true,
		fitColumns:true,
		queryParams:sel,
		pageSize:10,
		loadMsg:'数据加载中,请稍后...',
		pageList:[10,20,30,40,50], 
		nowrap:false,
		singleSelect: true, //是否单选
		columns : [ [ 
			
			{width : '20',field : 'ck',checkbox : true},
			{title:'外设代码',field:"periId",align:"center",width:70,sortable : false},
			{title:'外设型号',field:"periCode",align:"center",width:70,sortable : false},
			{title:'外设类型',field:"periType",align:"center",width:70,sortable : false,formatter : function(value, row,index) {
				value="";
				//console.log(row);
				 if(row['periType'] == '0'){
					value = '0-存单机';
				}else if(row['periType'] == '01'){
					value = '01-填单机';
				}else if(row['periType'] == '2'){
					value = '2-读卡器';
				}else if(row['periType'] == '04'){
					value = '04-读卡器';
				}else if(row['periType'] == '03'){
					value = '03-指纹';
				}else if(row['periType'] == '05'){
					value = '05-身份扫描';
				}else if(row['periType'] == '06'){
					value = '06-密码键盘';
				}else if(row['periType'] == '07'){
					value = '07-回单模块';
				}else if(row['periType'] == '08'){
					value = '08-裁纸器';
				}
				return value;
			}},
			{title:'外设名称',field:"periName",align:"center",width:70,sortable : false},
			{title:'创建日期',field:"createDate",                           
                formatter: function (val, row, index) {
                	 return  dateNew(val);
                } ,align:"center",width:70,sortable : false},
			{title:'创建人',field:"creater",align:"center",width:70,sortable : false}
		] ]
	});
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
}
//查询按钮事件
$("#button_search").click(function(event){
	onlds(selll());
});
//表单提交事件
function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				subAddAdd();
				$(".username_info").hide();	
				$(".email_info_top").hide();	
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
				subEditEdit();
				$(".username_info").hide();	
				$(".email_info_top").hide();
			}
		}
	});
	return false;
}
//表格单选事件
function selectTr(){
	
	
}
//编辑开始
$(".edit").on("click",function(){
	$(".mask").show();
	 var row = $('#statementGrid').datagrid('getSelected'); 
   if (row) { 
   	var periType;
   	var value;
   	$(".firm_add_edit").show();
   	$("#jgmc_add").val(row.periCode);// 外设型号
   	$("#jgdz_add").val(row.periName);// 外设名称
   /* 	 if(row.periType == '0'){
				value = '存单机';
			}else if(row.periType == '01'){
				value = '填单机';
			}else if(row.periType == '2'){
				value = '读卡器';
			}else if(row.periType == '04'){
				value = '读卡器';
			}else if(row.periType == '03'){
				value = '指纹';
			}else if(row.periType == '05'){
				value = '身份扫描';
			}else if(row.periType == '06'){
				value = '密码键盘';
			}else if(row.periType == '07'){
				value = '回单模块';
			}
   	if(row.periType == "03"){
   		periType = 1;
   	}else if(row.periType == "04"){
   		periType = 2;
   	}else if(row.periType == "05"){
   		periType = 3;
   	}else if(row.periType == "06"){
   		periType = 4;
   	}else if(row.periType == "07"){
   		periType = 5;
   	} */
   //	console.log(periType);
   	$("#jglx_add").val(row.periType);
  	 	$("#jglx_add").val();//外设类型
  	 //	console.log("长度"+$("#jglx_add option").length);
   } else{
  	 var tishi=new Tishi("请选择一条记录","tishi");
   }
});

//添加保存
$("#save_add_add").click(function(){
	$("#form1").submit();
	return false;
});

// 添加按钮事件
$("#button_add_add").click(function(event){
	$("#sjjg_add").attr("disabled","disabled"); //上级机构默认为不可编辑
	$(".firm_add_add").css("display","block");
	$(".mask").show();
	$(".firm_add_add input").val("");
	$(".firm_add_add select").val("");
});
//添加保存
function subAddAdd(){
	var periId=$("#jgbh_add").val();//外设代码 
	var periCode=$("#jgmc_add2").val();// 外设型号
	var periType=$("#sjjg_add2  option:selected").val();//外设类型
	var periName=$("#jgdz_add2").val();// 外设名称
	var data={periId:periId,periCode:periCode,periType:periType,periName:periName}
	$.ajax({
        url:"<%=basePath%>savePeripherals.do",
        cache:false,
        type:"post",
        datatype:'json',
        data:data,
        beforeSend:function(){},
        success:function(data){
        	if(data.success == true){	
        		$(".firm_add_add").css("display","none");
        		// 刷新表格
        		onlds();
				var tishi=new Tishi("保存成功","chenggong");
        	}else if(data.errmsg == 01){
				$(".username_info").show();
			}else{
				$(".email_info_top").show();	
			}
        },
        complete:function(XHR, textStatus){
        	statementGrid.datagrid('clearSelections')
			statementGrid.datagrid('reload');
        },
        error:function(){}
	})
}

//获取查询条件
 function selll(){
	page = 1;
	rows = 10;
	jgmc=$("#jgmc_add1").val();//外设型号
	sjjg=$("#sjjg_add1 option:selected").val();//外设类型
	jgdz=$("#jgdz_add1").val();//外设名称
	var sel = {page:page,rows:rows,periCode:jgmc,periType:sjjg,periName:jgdz}
	return sel;
}
//取消按钮
 $("#close_add_add,#close_add_edit,.firm_add_head a").click(function(event){
     $(".firm_add_add,.common_info").hide();
     $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hide');
		return false;
 });
//编辑提交
function saveEditEdit(event){
	$("#form2").submit();
}
//编辑保存
   function subEditEdit(){
		 var row = $('#statementGrid').datagrid('getSelected'); 
		 var periId;
	     if (row) {
	    	 periId = row.periId;
	     }
        	var periCode=$("#jgmc_add").val();// 外设型号
        	var periType=$("#jglx_add  option:selected").val();// 外设类型
        	var periName=$("#jgdz_add").val();// 外设名称
        	var data={periCode:periCode,periId:periId,periName:periName,periType:periType};
        	$.ajax({
            url:"<%=basePath%>editPeripherals.do",
            cache:false,
            type:"post",
            datatype:'json',
            data:data,
            beforeSend:function(){},
            success:function(data){
				if(data.success == true){
					var tishi=new Tishi("保存成功","chenggong");
					onlds();
					 $(".firm_add_edit").hide();
	            }else if(data.errmsg == 01){
					$(".username_info").show();
				}else{
					$(".email_info_top").show();	
				}
            },
            complete:function(XHR, textStatus){
            	statementGrid.datagrid('clearSelections');
				statementGrid.datagrid('reload');
            },
            error:function(){var tishi=new Tishi("保存失败","shibai");}
            });
           
  } 
//	删除
	$(".delete").click(function(){
		 var row = $('#statementGrid').datagrid('getSelected'); 
    	 var periId;
         if (row) { 
        	 periId = row.periId;
        	 var pop=new Pop("删除","remind_ok","tishi");
        	 $("#remind_ok").on("click",function(){
     			$(".mask").show();
     			$.ajax({
                    url:"<%=basePath%>delPeripherals.do",
                    cache:false,
                    type:"post",
                    datatype:'json',
                    data:{periId:periId},
                    beforeSend:function(){},
                    success:function(data){
                    	if(data.success == true){	
                    		$(".table_menu").hide();
       					var tishi=new Tishi("删除成功","chenggong");
       					onlds();
                    		
                    	}else{
                    		var tishi=new Tishi(data.errmsg,"shibai");
                    	}
                    },
                    complete:function(XHR, textStatus){
                   	statementGrid.datagrid('clearSelections');
    					statementGrid.datagrid('reload');
                    },
                    error:function(){var tishi=new Tishi("删除失败","shibai");}
         		})
     		});
        	 
         }else{
        	 var tishi=new Tishi("请选择一条记录","tishi");
         }
		
		
	});
   	
//设置机器类型
function setSelectMacType(){
	   $("#sjjg_add2").append("<option value=''>请选择</option>");
	   var data= {groupName:"perType"};
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
        		    $("#sjjg_add2").append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
        		}
        	}else{
        		
        	}
        },
        complete:function(){},
        error:function(){}
		})
}

//设置机器类型
function setPerType(par){
	   var data= {groupName:"perType"};
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
</script>
