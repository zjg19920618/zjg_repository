<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <div class="firm_add_add">
        <form id ="form1" action="">
        <div class="firm_add_head">
            <span id='textAddHead'>添加厂商</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
            <p><span><b>*</b>厂商编号：</span><input class="validate[required]" type="text" id="manuCode"/>
            <span><b>*</b>厂商名称：</span><input class="validate[required]" type="text" id="manuName"/></p>
            <div class="common_info username_info"><b class="info_caret"></b>* 厂商编号已存在!</div>
			<div class="common_info email_info"><b class="info_caret"></b>* 厂商名称已存在!</div>
             <p><span><b>*</b>联系人：</span><input class="validate[required]" type="text" id="conPerson"/>
             <span><b>*</b>座机电话：</span><input class="validate[required,custom[phone],minSize[8]]" type="text" id="tel"/></p>
             <p><span><b>*</b>手机电话：</span><input class="validate[required,custom[phone],minSize[11],maxSize[11]]" type="text" id="phone"/>
             <span><b>*</b>厂商状态：</span><select class="validate[required]" value="" id="manuStatus">
                    <option value="">请选择</option>
                    <option value="1">正常</option>
                    <option value="2">废弃</option>
                </select></p>
             <p class="beizhu"><span>备注：</span><textarea name="" id="manuDesc" ></textarea></p>
        </div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add" onclick="saveAddAdd(this)">保存</button>
            
        </div>
        </form>
    </div>
    <!--添加结束-->
	
	<div class="com_nav">
    	<table class="search_box">
	         <tr>
	          	<td><span>厂商名称：</span></td>
	          	<td><input type="text" id="manuName_sel"/></td>
	          	<td><span>厂商状态：</span></td>
	          	<td>
	          		<select value="" id="manuStatus_sel" class="firmZT" name="state">
	          			<option value="">请选择</option>
						<option value="1">正常</option>
						<option value="2">废弃</option>
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
			<button class="edit" id="editA">
				<span class="edit_icon"></span>
				<span class="sarch">编辑</span>
			</button>
			<button class='delect'>
				<span class="delete_icon"></span>
				<span class="sarch">删除</span>
			</button>
			<button id="button_daochu">
                <span class="export_ico"></span>
                <span class="sarch">导出</span>
            </button>
		</div>
		<table id="statementGrid"  style="width:100%; height:auto;max-height:321px;"></table>
		<div class="table_box">
		<!-- 	
			<div class="table_menu">
				<b class="sanjiao"></b>
				<ul>
					<li class="edit">
						<b class="edit_icon"></b>
						<span class="litterJL">编辑</span>
					</li>
					<li class='delect'>
						<b class="delete_icon"></b>
						<span class="litterJL">删除</span>
					</li>
				</ul>
			</div> -->
		</div>
	</div>
</div>
<script>
var statementGrid;
var page = 1;
var rows = 10;
var manuName;
var manuStatus;
var sel;
var flag;
$(function(){
	onld();//初始化
	 valid();
	//监听
		$('#manuCode,#manuName').blur(function(){
			$('.common_info').hide()
		})
		/*导出  */
		$("#button_daochu").click(function(){
			var dataN = {
					manuName:selll().manuName,
					manuStatus :selll().manuStatus 
				};
			console.log(dataN);
			$.ajax({
				url:"<%=basePath%>outManuEXCE.do",
				cache:false,
				type:"post",
				datatype:'json',
				data:dataN,
				async:false,
				beforeSend:function(){},
				success:function(data){
					window.open("../../ReportTemplate/ManuEXCEt.xls");
				},
				complete:function(){},
				error:function(){
					var tishi=new Tishi("导出失败","shibai");
				}
			})
		});
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
				$(".email_info").hide();
			}
		}
	});
}
function onld(sel){
	statementGrid = $('#statementGrid').datagrid({
		url : '<%=basePath%>findManu.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'payId',
		autoRowHeight:true,
		fitColumns:true ,
		queryParams:sel,
		pageSize:10,
		loadMsg:'数据加载中,请稍后...',
		pageList:[10,20,30,40,50], 
		nowrap:false,
		singleSelect: true, //是否单选
		/*  onClickRow: function (rowIndex)  
         {  //点击事件
             var row = $('#statementGrid').datagrid('getSelected'); 
             if (row) { 
            	 
            	 console.log(getMousePos().y);
            	 $(".table_menu").show().css({top:getMousePos().y-180+$(".table_box").scrollTop(),left:getMousePos().x-260});
                 //alert('manuCode:' + row.manuCode + "\n manuName:" + row.manuName);  
             }  
         }, */
		columns : [ [ 
			{width : '20',field : 'ck',checkbox : true},
			{title:'厂商编号',field:"manuCode",align:"center",width:70,sortable : false},
			{title:'厂商名称',field:"manuName",align:"center",width:70,sortable : false},
			{title:'联系人',field:"conPerson",align:"center",width:70,sortable : false},
			{title:'座机电话',field:"tel",align:"center",width:70,sortable : false},
			{title:'手机电话',field:"phone",align:"center",width:70,sortable : false},
			{title:'厂商状态',field:"manuStatus",align:"center",width:70,sortable : false,formatter : function(value, row,index) {
				value="";
				 if(row['manuStatus'] == '1'){
					value = '1-正常';
				}else if(row['manuStatus'] == '2'){
					value = '2-废弃';
				}
				return value;
			}},
			{title:'备注',field:"manuDesc",align:"center",width:70,sortable : false}
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
function saveAddAdd(event){
	$("#form1").submit();
}
$("#button_search").click(function(event){
	onld(selll());
}); 
//添加厂商
function subAddAdd(){
	$(".mask").show();
	var manuCode=$("#manuCode").val();//厂商编号
	var manuName=$("#manuName").val();// 厂商名称
	var conPerson=$("#conPerson").val();// 联系人
	var tel=$("#tel").val();// 座机
	var phone=$("#phone").val();// 手机
	var manuStatus=$("#manuStatus").val();// 厂商状态
	var manuDesc=$("#manuDesc").val();// 备注
	var url = "";
	var data={manuCode:manuCode,manuName:manuName,conPerson:conPerson,tel:tel,phone:phone,manuStatus:manuStatus,manuDesc:manuDesc}
	
		if(flag =="edit"){
			url = "<%=basePath%>editManu.do";
		}else{
			url = "<%=basePath%>saveManu.do";
		}
		$.ajax({
	        url:url,
	        cache:false,
	        type:"post",
	        datatype:'json',
	        data:data,
	        beforeSend:function(){},
	        success:function(data){
	        	if(flag =="edit"){
	        		if(data.success==true){
						var tishi=new Tishi("保存成功","chenggong");
						$(".firm_add_add").hide();
						onld();
					}else if(data.errmsg == 01){
						$(".username_info").show();
						console.log(data.errmsg);
					}else{
						$(".email_info").show();	
					}
	        	}else{
	        		if(data.success==true){
						var tishi=new Tishi("保存成功","chenggong");
						$(".firm_add_add").hide();
						onld();
					}else if(data.errmsg == 01){
						$(".username_info").show();
						console.log(data.errmsg);
					}else{
						$(".email_info").show();	
					}
	        	}
				
	        },
	        complete:function(XHR, textStatus){
					statementGrid.datagrid('clearSelections')
					statementGrid.datagrid('reload');
	        },
	        error:function(){
			}
		});
	
}

//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 manuName=$("#manuName_sel").val();
	 manuStatus=$("#manuStatus_sel").val();
	 sel  = {page:page,rows:rows,manuName:manuName,manuStatus:manuStatus}
	return sel;
}
//添加
 $("#button_add_add").click(function(event){
 	$("#manuCode").removeAttr("disabled");
 	$('#textAddHead').html('添加厂商')
 	clearForm();
 	flag = "add";
 	$(".firm_add_add").show();
 	$(".mask").show();
 });
//	删除
 $(".delect").click(function(){
	 var row = $('#statementGrid').datagrid('getSelected'); 
     if (row) { 
    		var manuCode = row.manuCode;
    		console.log(manuCode);
    		var pop=new Pop("删除","remind_ok","tishi");
    		$("#remind_ok").on("click",function(){
    	 		 $.ajax({
    	 			 url:"<%=basePath%>delManu.do",
    	 			 cache:false,
    	 			 type:"post",
    	 			 datatype:'json',
    	 			 data:{manuCode:manuCode},
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
    	 				var tishi=new Tishi("删除失败","shibai");
    	 			 }
    	 		})
    	 	});
    	 }else{
    		 var tishi=new Tishi("请选择一条记录","tishi");
    	 }
 	
 });
//清空表单
 function clearForm(){
 	$("#manuCode").val("");//厂商编号
 	$("#manuName").val("");// 厂商名称
 	$("#conPerson").val("");// 联系人
 	$("#tel").val("");// 座机
 	$("#phone").val("");// 手机
 	$("#manuStatus").val("");// 厂商状态
 	$("#manuDesc").val("");// 备注
 }
//表格单选事件
 function selectTr(){
	 var row = $('#statementGrid').datagrid('getSelected'); 
     if (row) { 
         $(".firm_add_add,.mask").show();
    	    $("#manuCode").val(row.manuCode);//厂商编号
    	 	$("#manuName").val(row.manuName);// 厂商名称
    	 	$("#conPerson").val(row.conPerson);// 联系人
    	 	$("#tel").val(row.tel);// 座机
    	 	$("#phone").val(row.phone);// 手机
    	 	$("#manuDesc").val(row.manuDesc);// 备注
    	 	$("#manuStatus option").eq(row.manuStatus).attr("selected",true);
     } else{
    	 $(".mask").show();
    	 var tishi=new Tishi("请选择一条记录","tishi");
     }
 	
 }
//编辑
 $("#editA").on("click",function(){
	 selectTr();
	 $('#textAddHead').html('编辑厂商')
 	$("#manuCode").attr("disabled",true);
 	
 	flag = "edit";
 	$(".table_menu").hide();
 });
//关闭按钮
 $(".firm_add_head a,#close_add_add").click(function(){
     $(".firm_add_add,.common_info").hide();
     $(".firm_add_edit").hide();
 	$(".mask").hide();
 	$('form').validationEngine('hide');
 	return false;
 });
/*  //移出菜单事件
 $(document).bind('mouseover',function(e){
		if(e.pageY>760||e.pageY<260){
			$(".table_menu").hide();
		}
}); */
/*  //获取鼠标坐标
 function getMousePos(event) {
     var e = event || window.event;
     var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
     var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
     var x = e.pageX || e.clientX + scrollX;
     var y = e.pageY || e.clientY + scrollY;
     //alert('x: ' + x + '\ny: ' + y);
     return { 'x': x, 'y': y };
 } */
</script>
