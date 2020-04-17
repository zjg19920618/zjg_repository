<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

    <!--添加开始-->
    <div class="firm_add_edit firm_add_Add1 firm_box_position">
        <form id="form1" action="">
        <div class="firm_add_head">
            <span>角色添加</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
					<p>
						<span><b>*</b>角色名称：</span><input class="validate[required]" type="text" id="jgbh_Add"/>
			      <span><b>*</b>角色描述：</span><input class="validate[required]" type="text" id="jgmc_Add"/>
					</p>
					<div class="common_info username_info tj_info"><b class="info_caret"></b>* 角色名称已存在!</div>
        </div>
        <div class="Tsbtn">
            <p class="clearclass" id="close_add_add">取消</p>
            <p class="clearclass" id="save_add_Add" onclick="saveAddAdd();">保存</p>
        </div>
        </form>
    </div>
    <!--添加结束-->
    <div class="firm_add_edit  firm_box_position">
        <form id="form2" action="">
	        <div class="firm_add_head">
	            <span>角色编辑</span>
	            <a href="javascript:;"></a>
	        </div>
	        <div class="firm_add_content">
						<p>
							<span><b>*</b>角色名称：</span><input class="validate[required]" type="text" id="jgbh_ed"/>
				      <span><b>*</b>角色描述：</span><input class="validate[required]" type="text" id="jgmc_erit"/>
						</p>
						<div class="common_info username_info bj_info"><b class="info_caret"></b>* 角色名称已存在!</div>
	        </div>
	        <div class="Tsbtn">
	            <p class="clearclass" id="close_add_edit">取消</p>
	            <p class="clearclass" id="save_add_ediW" onclick="saveAddEdit();">保存</p>
	        </div>
        </form>
    </div>
    <div class="firm_add_add user_add_box" style="display:none;">
        <form action="">
        <div class="user_add_head">
            <span>菜单权限</span>
            <a href="javascript:;"></a>
        </div>
        <div class="user_add_content" style="display:block;">
					<div class="user_in">
						<ul id="menuID" class="ztree">
						</ul>
					</div>
				</div>
        <div class="Tsbtn">
            <p  class="clearclass" id="close_add_user">取消</p>
            <p  class="clearclass" id="per_save">保存</p>
        </div>
        </form>
    </div>
	<div class="com_nav">
   <table class="search_box">
			<tr>
				<td><span>角色名称：</span></td>
				<td><form action=""><input type="text" id="jgmcmc"/></form></td>
			</tr>
		</table>
		<div id="refresh">
			<div class="newSonstruction">
				<button id="button_search">
					<span class="sarch_ico"></span>
					<span class="sarch">查询</span>
				</button>
				<button id="button_add_add" class="button_add_menu">
					<span class="new_ico add_role"></span>
					<span class="sarch">添加角色</span>
				</button>
				<button id="button_add" class="button_add_menu">
					<span class="new_ico add_menu"></span>
					<span class="sarch">添加权限</span>
				</button>
				<button id="button_edit">
					<span class="edit_icon"></span>
					<span class="sarch">编辑</span>
				</button>
				<button id="button_delete">
					<span class="delete_icon"></span>
					<span class="sarch">删除</span>
				</button>
			</div>
			<table id="table_content" style="width:100%; height:auto;max-height:321px;"></table>
			<div class="table_box"></div>
		</div>
	</div>
<script>
var page = 1;
var rows = 10;
var manuName;
var manuStatus;
var sel;
var flag;
var statementGrid;

$(function(){
	valid();
	onld();//初始化表格
	$('#jgbh_Add').blur(function(){
		$('.common_info').hide()
	})
});

//查询
function findManu(){
	$('#table_content').datagrid('load',{
		manuName:$('#manu_info').val(),
		manuStatus:$("#manu_status ").val()
	})
	$("#table_content").datagrid('clearSelections');	
}
/*使用框架初始化数据表格*/
function onld(sel){
	statementGrid = $('#table_content').datagrid({
		url : "<%=basePath%>system/getRole.do",
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'payId',
		autoRowHeight:true,
		fitColumns:true ,
		queryParams:sel,
		loadMsg:'数据加载中,请稍后...',
		pageSize:10,
		pageList:[10,20,30,40], 
		nowrap:false,
		singleSelect:true,
		columns : [ [ 
			{width : '10%',field : 'ck',checkbox : true},
			{title:'序号',field:"roleCode",align:"center",width:'17%',sortable : false},
			{title:'角色名称',field:"roleName",align:"center",width:'40%'},
			{title:'角色描述',field:"roleDesc",align:"center",width:'40%'}
		] ]
	});
	//设置分页控件   
	var p = $('#table_content').datagrid('getPager');   
	$(p).pagination({   
	    //pageSize: pageSize,//每页显示的记录条数，默认为10   
	    pageList: [10,20,30,40],//可以设置每页记录条数的列表   
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
 	event.preventDefault();
 	onld(selTable());
 });
//查询表格方法
 function selTable(){
		page = 1;
		rows = 10;
		var csName=$("#jgmcmc").val();//姓名
 		sel = {page:page,rows:rows,roleName:csName}
 	return sel;
}
//添加弹窗出现
$("#button_add_add").click(function(){
	$("#jgbh_Add,#jgmc_Add").val(""); //清空缓存内容
	$(".firm_add_Add1").show();
	$(".mask").show();
});
//角色添加保存方法;
function saveAddAdd(){
	$("#form1").submit();
}
//角色添加和编辑取消按钮;
$("#close_add_add,#close_add_edit").click(function(event){
    event.preventDefault();
    $(".firm_add_add").hide();
    $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
});
//判断是否有选中记录
function selectTr(){
	 var row = $('#table_content').datagrid('getSelected'); 
   if (row) { 
       $(".firm_add_edit,.mask").show();
    	 $("#jgbh_ed").val(row.roleName);
    	 $("#jgmc_erit").val(row.roleDesc);
    } else{
    	$(".mask").show();
    	var tishi=new Tishi("请选择一条记录","tishi");
    }
 }

function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				$(".tj_info").hide();
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
				$(".bj_info").hide();
				subAddEdit();
			}
		}
	});
}
function saveAddEdit(){
	$("#form2").submit();
}
// 删除
$("#button_delete").unbind("click");
$("#button_delete").click(function(){
    	var row = $('#table_content').datagrid('getSelected'); 
    	if (row) { 
	    	 var roleCode = row.roleCode;
	    	 var pop=new Pop("删除","remind_ok","tishi");
	    	 $("#remind_ok").on("click",function(){
	    		   	$.ajax({
	                url:"<%=basePath%>system/delRole.do",
	                cache:false,
	                type:"post",
	                datatype:'json',
	                data:{roleCode:roleCode},
	                beforeSend:function(){},
	                success:function(data){
	                		if(data.success == true){	
	  										var tishi=new Tishi("删除成功","chenggong");
	                   		sel= {page:page,rows:rows}
	                   		onld();
	                  	}else{
	                   		var tishi=new Tishi("删除失败","shibai");
	                  	}
	                 },
	                 complete:function(XHR, textStatus){
	                  	statementGrid.datagrid('clearSelections');
	      							statementGrid.datagrid('reload');
	                 },
	  							error:function(){var tishi=new Tishi("删除失败","shibai");}
	        			})
	    	   });
    		} else{
    		   $(".mask").show();
    		   var tishi=new Tishi("请选择一条记录","tishi");
    		}
});
//添加菜单
$("#button_add").on("click",function(){
    	var row = $('#table_content').datagrid('getSelected'); 
    	var rolename;
			if (row) { 
				$(".user_add_box,.mask").show();
				rolename = row.roleCode;
				$.ajax({
	    	        url:"<%=basePath%>system/getRoleMenu.do",
	    	        cache:false,
	    	        type:"post",
	    	        datatype:'json',
	    	        data: {roleCode:rolename},
	    	        beforeSend:function(){},
	    	        success:function(data){
	    	        	var zTree;
	    	        	var demoIframe;
	    	        	var setting = {
	    	        		check: {//点击事件
	    	        				enable: true
	    	        		},
	    	        		view: {
	    	        			dblClickExpand: true,
	    	        			showLine: true,
	    	        			selectedMulti: true
	    	        		},
	    	        		data: {
	    	        			simpleData: {
	    	        				enable:true,
	    	        				idKey: "id",
	    	        				parentIdKey: "parentId",
	    	        				rootPId: 0
	    	        			}
	    	        		},
	    	        		callback: {
	    	        			beforeClick: function(treeId, treeNode) {
	    	        				var zTree = $.fn.zTree.getZTreeObj("tree");
	    	        			}
	    	        		}
	    	        	}; 
	    	        	
	    	        	tree = $.fn.zTree.init($("#menuID"), setting, data.roleMenu);
	        	    		console.log(data);
	    	        		$(".firm_add_edit").hide();
	    	        }
	    	});
			}else{
				 var tishi=new Tishi("请选择一条记录","tishi");
			} 	 	 
});

//菜单保存
    $("#per_save").click(function(event){
    		//debugger;
        var  nodes = tree.getCheckedNodes(true);
        console.log(nodes);
       //debugger;
        var  per = "";
        var row = $('#table_content').datagrid('getSelected'); 
				var roleCode;
				if (row) { 
						roleCode = row.roleCode;
				}
        for(var i=0;i<nodes.length;i++){
        	if(i == 0){
        		per +=nodes[i].id;
        	}else if(i != nodes.length){
        		per +=","+nodes[i].id;
        	}else{
        		per +=nodes[i].id;
        	}
        }
        var data = {menuIds:per,roleCode:roleCode};
        //debugger;
        console.log(data);
        //debugger;
        $.ajax({
            url:"<%=basePath%>system/saveRoleMenu.do",
            cache:false,
            type:"post",
            datatype:'json',
            data:data,
            beforeSend:function(){},
            success:function(data){
            	$(".firm_add_add ,.user_add_box,.mask").hide();
            	if(data.success==true){
    						var tishi=new Tishi("保存成功","chenggong");
    					}
            },
            complete:function(XHR, textStatus){
            	statementGrid.datagrid('clearSelections');
							statementGrid.datagrid('reload');
            },
            error:function(){}
 				})
    });
//编辑 保存
function subAddEdit(){
			var row = $('#table_content').datagrid('getSelected'); 
			var roleCode = row.roleCode;
			//debugger;
			console.log(roleCode);
			//debugger;
    	var csbh = $("#jgbh_ed").val(); //角色名称
    	var csmc = $("#jgmc_erit").val(); //角色描述
    	var json2 = {roleName:csbh,roleDesc:csmc,roleCode:roleCode};
    	console.log(json2);
    	$.ajax({
            url:"<%=basePath%>system/editRole.do",
            cache:false,
            type:"post",
            datatype:'json',
            data: json2,
            beforeSend:function(){},
            success:function(data){
							if(data.success==true){
								var tishi=new Tishi("保存成功","chenggong");
								$(".firm_add_edit").hide();
								statementGrid.datagrid('clearSelections');
								statementGrid.datagrid('reload');
							}else{
								$(".bj_info").show();
							}
            }
    });
}
//添加角色
	function subAddAdd(){
    	var csbh = $("#jgbh_Add").val(); //角色名称
    	var csmc = $("#jgmc_Add").val(); //角色描述
    	var json2 = {roleName:csbh,roleDesc:csmc};
    	console.log(json2);
    	$.ajax({
            url:"<%=basePath%>system/addRole.do",
            cache:false,
            type:"post",
            datatype:'json',
            data: json2,
            beforeSend:function(){},
            success:function(data){
            	console.log(data);
							if(data.success == true){
								var tishi=new Tishi("保存成功","chenggong");
								$(".firm_add_edit").hide();
								onld();
							}else{//角色名称已存在
								$(".tj_info").show();
							}
            },
            complete:function(XHR, textStatus){
            	statementGrid.datagrid('clearSelections');
							statementGrid.datagrid('reload');
           	},
			error:function(){
				var tishi=new Tishi("保存失败","shibai");
			}
    });
}
//关闭按钮
$(".firm_add_head a,.user_add_head a").click(function(){
    $(".firm_add_add").hide();
    $(".firm_add_edit").hide();
		$(".mask").hide();
		$('form').validationEngine('hideAll');
})
//隔行变色封装
	function rowsColor(){
		$(".table_box tbody tr:odd,.table_box tbody tr:odd").css("background","#ebf2f7");
    $(".table_box tbody tr:even,.table_box tbody tr:even").css("background","#fff9f8");
	}
//编辑开始
$("#button_edit").on("click",function(){
		selectTr();
});   
$("#close_add_user").click(function(event){
	   event.preventDefault();
	   $(".user_add_box,.mask").hide();
});
$(".user_add_content").children().find("ul").css("padding-left","20px");
</script>
