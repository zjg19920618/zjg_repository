<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--编辑部署信息-->
    <div class="firm_add_add  firm_box_position" style="display:none;">
        <form id="form1" action="">
        <div class="firm_add_head">
            <span>添加用户</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
       <p>	
				<span><b>*</b>登录名：</span><input class="validate[required]" type="text" id="csbhy"/>
				<span>Email地址：</span><input  type="text"  id="phoney" class="validate[custom[email]]"/>
			</p>
			<div class="common_info username_info"><b class="info_caret"></b>* 用户名已存在!</div>
			<div class="common_info email_info"><b class="info_caret"></b>* 邮箱已存在!</div>
      <p>
				<span><b>*</b>姓   名：</span><input class="validate[required]" type="text" id="lxry"/>
				<span>手机号：</span><input type="text" class="validate[custom[phone],minSize[11],maxSize[11]]" value="" id="cszty">
			</p>
        </div>
        <div class="Tsbtn">
            <p  class="clearclass" id="close_add_add">取消</p>
            <p  class="clearclass" id="saveSelect" onclick="saveEditEdit(this)" >保存</p>
        </div>
        </form>
    </div>
    <!-- 重置用户密码 -->
				<div class="reset_pwd reset_pwdB" style="left:67%;">
					<div class="firm_add_head">
						<span>重置密码</span>
					</div>
				<span class="edit_error" id="edit_error"></span>
				<form action="">
					<div class="edit_admin_content">
						<p><span><b>*</b> 新密码：</span><input type="password" id="reset_pwd"/></p>
					</div>
				</form>
				<div>
					<button id="up_pwd_no">取消</button>
					<button id="up_pwd_ok">确定</button>
				</div>
			</div>
    <!-- 添加角色 -->
     <div class="firm_addCharacter user_add_box" style="display:none;">
        <form action="" class="firm_addCharacSTRE">
        <div class="firm_add_head">
            <span>关联角色</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
        	<div class="user_in">
					<ul id="menuID" class="ztree">
					</ul>
			</div>
             
        </div>
        <div class="Tsbtn">
            <p class="clearclass" id="closewww">取消</p>
            <p class="clearclass" id="seve_addcsl" >保存</p>
            
        </div>
        </form>
    </div>
    <!--添加结束-->
    <div class="firm_add_edit firm_box_position" style="display:none;">
        <form id="form2"  action="">
        <div class="firm_add_head">
            <span>编辑用户</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
       <p>	
				<span><b>*</b>登录名：</span><input class="validate[required]" type="text" id="csbh1" readonly="true"/>
				<span>Email地址：</span><input  type="text" id="phone1" class="validate[custom[email]]" />
			</p>
			<div class="common_info email_info bj_email_info"><b class="info_caret"></b>* 邮箱已存在!</div>
      <p>
				<span><b>*</b>姓   名：</span><input class="validate[required]" type="text" id="lxr1"/>
				<span>手机号：</span><input type="text" value="" id="cszt1" class="validate[custom[phone],minSize[11],maxSize[11]]" />
			</p>
        </div>
        <div class="Tsbtn">
            <p class="clearclass" id="close_add_edit">取消</p>
            <p class="clearclass" id="save_add_edit" onclick="updateEditEdit(this)">保存</p>
            
        </div>
        </form>
    </div>
	<div class="com_nav">
    <table class="search_box username_margin">
			<tr>
				<td><span>姓名：</span></td>
				<td><form action=""><input type="text" id="TS_name"/></form></td>
			</tr>
		</table>
    <div class="newSonstruction">
			<button id="button_search">
					<span class="sarch_ico"></span>
					<span class="sarch">查询</span>
				</button>
				<button id="button_add_add">
					<span class="new_ico"></span>
					<span class="sarch">添加</span>
				</button>
				<button id="button_add" class="button_add_menu">
					<span class="new_ico add_role"></span>
					<span class="sarch">添加角色</span>
				</button>
				<button id="button_edit">
					<span class="edit_icon"></span>
					<span class="sarch">编辑</span>
				</button>
				<button id="button_delete">
					<span class="delete_icon"></span>
					<span class="sarch">删除</span>
				</button>
				<button id="button_reset" class="button_add_menu">
					<span class="resetPassword_icon"></span>
					<span class="sarch">重置密码</span>
				</button>
    </div>
    <div class="table_box">
        <table id="table_content" style="width:100%; height:auto;max-height:321px;">      
        </table>
    </div>
</div>
<script>
var num=0;
var page = 1;
var rows = 10;
var intoll = 0;
var roleCode;
var manuName;
var manuStatus;
var sel;
var flag;
var statementGrid;

$(function(){
	valid();
	onld();
	$('#csbhy,#phoney').blur(function(){
		$('.common_info').hide()
	})
});
//查询
function findManu(){
	$('#table_content').datagrid('load',{
		manuName:$('#manu_info').val(), 
		manuStatus:$("#manu_status").val()
	})
	$("#table_content").datagrid('clearSelections');	
}
function onld(sel){
	 statementGrid = $('#table_content').datagrid({
		url : '<%=basePath%>system/getUserInfo.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'payId',
		autoRowHeight:true,
		//sortName : 'payDate',
		//sortOrder : 'asc',
		loadMsg:'数据加载中,请稍后...',
		fitColumns:true ,
		queryParams:sel,
		pageSize:10,
		pageList:[10,20,30,40,50], 
		nowrap:false,
		singleSelect:true,
		columns : [ [ 
				{width : '10%',field : 'ck',checkbox : true},
				{title:'姓名',field:"name",align:"center",width:'15%',sortable : false},
				{title:'电话号',field:"phone",align:"center",width:'17%',sortable : false},
				{title:'e-mail',field:"email",align:"center",width:'13%',sortable : false},
				{title:'登录名',field:"username",align:"center",width:'20%',sortable : false},
				{title:'状态',field:"status",align:"center",width:'12%',sortable : false,
					formatter : function(value, row,index) {
						value="";
						if(row['status'] == '0'){
							value = '0-总行';
						}else if(row['status'] == '1'){
							value = '1-分行';
						}else if(row['status'] == '2'){
							value = '2-支行';
						}else if(row['status'] == '3'){
							value = '3-网点';
						}
						return value;
					}},
				{title:'最近一次登录',field:"lastTime",align:"center",width:'20%',sortable : false,
					formatter : function(value, row,index) {
						return dateNew(value);
					}}
		] ]
		
	});
	//设置分页控件   
	var p = $('#table_content').datagrid('getPager');   
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
 	event.preventDefault();
 	onld(selTable());
 })
 // 查询表格方法
 function selTable(){
		page = 1;
		rows = 10;
		var name=$("#TS_name").val();//姓名
 		sel = {page:page,rows:rows,name:name}
 	return sel;
}

function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				$(".username_info").hide();	
				$(".email_info").hide();	
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
				$(".bj_email_info").hide();
				subEditEdit();
			}
		}
	});
} 
function selectTr(){
  	 var row = $('#table_content').datagrid('getSelected'); 
     if (row) { 
         $(".firm_add_edit,.mask").show();
      	 $("#csbh1").val(row.username);
      	 $("#phone1").val(row.email);
      	 $("#lxr1").val(row.name);
      	 $("#cszt1").val(row.phone);
      } else{
      	$(".mask").show();
      	var tishi=new Tishi("请选择一条记录","tishi");
      }
   }
//点击编辑
  $("#button_edit").on("click",function(){
			selectTr();
   });
  //添加
  $("#button_add_add").on("click",function(){
		$('#form1').validationEngine('hide');
		$("#csbhy,#csmc,#lxry,#phoney,#cszty").val("");//清空内容
  	$(".firm_add_add").show();
		$(".mask").show();
  });
  //添加角色
  $("#button_add").on("click",function(event){
	  	$(".mask").show();
     	event.preventDefault();
     	var row = $('#table_content').datagrid('getSelected'); 
    	if (row) {
    		var username = row.username;
    		$(".firm_addCharacter").show();
    	  $.ajax({
    	      url:"<%=basePath%>system/getUserRole.do",
    	      cache:false,
    	      type:"post",
    	      datatype:'json',
    	      data:{username:username},
    	      success:function(data){
		    	      console.log(data);
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
		    	       tree = $.fn.zTree.init($("#menuID"), setting, data.treeList);
    	      }
    	  });
    	}else{
    		var tishi=new Tishi("请选择一条记录","tishi");
    	}     
});
//添加用户权限
  $("#seve_addcsl").on("click",function(){
 	 $(".table_menu,.mask,.firm_addCharacter").hide();  	
  	var  nodes=tree.getCheckedNodes(true);
    var  per = "";
    for(var i=0;i<nodes.length;i++){
    	if(i == 0){
    		per +=nodes[i].id;
    	}else if(i != nodes.length){
    		per +=","+nodes[i].id;
    	}else{
    		per +=nodes[i].id;
    	}
    }
    var row = $('#table_content').datagrid('getSelected');
    var username = row.username;
    var data = {per:per,username:username};
    $.ajax({
    	url:"<%=basePath%>system/saveUserRole.do",
        cache:false,
        type:"post",
        datatype:'json',
        data:data,
        beforeSend:function(){},
        success:function(data){
        	if(data.success == true){	
        		$(".firm_addCharacter").hide();
        		var tishi=new Tishi("保存成功","chenggong");
        	}else{
        		
        	}
        },
        complete:function(XHR, textStatus){
        	statementGrid.datagrid('clearSelections');
					statementGrid.datagrid('reload');
        },
        error:function(){}
		 })
  });
  //重置密码
  $("#button_reset").click(function(){
		var row = $('#table_content').datagrid('getSelected'); 
		if (row) { 
			$(".reset_pwdB").show();
			$(".mask").show();
		}else{
			$(".mask").show();
    	var tishi=new Tishi("请选择一条记录","tishi");
		}
		//$(".table_menu").hide();
	});
  $("#up_pwd_no").bind("click",function(){
		$(".reset_pwdB").hide();
		$(".mask").hide();
	});
  $("#up_pwd_ok").click(function(){	
		var peoplePassword=$("#reset_pwd").val();
		var row = $('#table_content').datagrid('getSelected');
		var username = row.username;
		$.ajax({
			 url:"<%=basePath%>editpeoplepwd.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{pwd:peoplePassword,username:username},
			 beforeSend:function(){},
			 success:function(data){
				 $(".reset_pwd").hide();
				 if(data.success == true){	
						var tishi=new Tishi("重置成功","chenggong");
						$("#reset_pwd").val("");
					}else{
						var tishi=new Tishi("重置失败","shibai");
					}
					console.log(data);
			 },
			 complete:function(XHR, textStatus){
				 	statementGrid.datagrid('clearSelections');
					statementGrid.datagrid('reload');
			 },
			 error:function(){var tishi=new Tishi("重置失败","shibai");}
		})
	})
  
  
  //删除 username
	$("#button_delete").click(function(){
    	 	var row = $('#table_content').datagrid('getSelected'); 
				if (row) { 
		   			var rolename = row.username;
		   			var pop=new Pop("删除","remind_ok","tishi");
		        $(".remind_ok").on("click",function(){
		   				$.ajax({
		              url:"<%=basePath%>system/del.do",
		              type:"post",
		              datatype:'json',
		              data:{username:rolename},
		              success:function(data){
		            	  console.log(data);
		              },
									complete:function(XHR, textStatus){
											var tishi=new Tishi("删除成功","chenggong");
											statementGrid.datagrid('clearSelections');
											statementGrid.datagrid('reload');
						 			},
									error:function(){
							 				var tishi=new Tishi("删除失败","shibai");
									}
		     	 		});
		        })
				}else{
					$(".mask").show();
	    		var tishi=new Tishi("请选择一条记录","tishi");
				}
	});	

function saveEditEdit(event){
	$("#form1").submit();
}
function updateEditEdit(event){
	$("#form2").submit();
}
//添加
function subAddAdd(){
		var csbh2=$("#csbhy").val();//登录账号
		var lxr2=$("#lxry").val();//姓名
		var phone2=$("#phoney").val();//email
	   
		var csbz2=$("#cszty").val();//手机号
		var par = {username:csbh2,name:lxr2,email:phone2,phone:csbz2};
		console.log(par);
		$.ajax({
			url:"<%=basePath%>system/saveUser.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:par,
			success:function(data){
				//debugger;
				console.log(data);
				//debugger;
				if(data.success == true){
					var tishi=new Tishi("保存成功","chenggong");
					$(".firm_add_add").hide();
				}else if(data.errmsg == 01){
					$(".username_info").show();
				}else{
					$(".email_info").show();	
				}
			},
			 complete:function(XHR, textStatus){
					statementGrid.datagrid('clearSelections');
					statementGrid.datagrid('reload');
			 },
			error:function(){}
		});
	}
//点击取消
$("#close_add_edit").click(function(){
	 $(".firm_add_edit").hide();
	 $(".mask").hide();
	 $('form').validationEngine('hideAll');
});

 //编辑
function subEditEdit(){
    	var csbh = $("#csbh1").val(); //登录账号
    	var lxr = $("#lxr1").val();//姓名
    	var zuoNum = $("#phone1").val();//email
    	var phone = $("#cszt1").val(); //手机号
    	var json2 = {username:csbh,name:lxr,Email:zuoNum,phone:phone};
    	console.log(json2);
    	$.ajax({
            url:"<%=basePath%>system/saveUser1.do",
            cache:false,
            type:"post",
            datatype:'json',
            data: json2,
            beforeSend:function(){},
            success:function(data){
            	console.log(data);
            	//onld();
            	if(data.success==true){
								var tishi=new Tishi("保存成功","chenggong");
								$(".firm_add_edit").hide();
							}else{
								$(".bj_email_info").show();
							}	
            },
            complete:function(XHR, textStatus){
            	statementGrid.datagrid('clearSelections');
							statementGrid.datagrid('reload');
						},
            error:function(){
						}
    	});
}
 $("#close_add_add,#closewww").click(function(){
    $(".firm_add_add,.firm_addCharacter,.mask").hide();
		$(".mask").hide();
		$('form').validationEngine('hide');
});
  //x号隐藏
 $(".firm_add_head a,.firm_addCharacter a").click(function(){
    $(".firm_add_add,.firm_add_edit,.firm_addCharacter,.mask").hide();
	 	$(".mask").hide();
	 	$('form').validationEngine('hideAll');
 })
</script>
