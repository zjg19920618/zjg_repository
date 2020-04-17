<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<div id="refresh">
<div class="newSonstruction">
	<button id="button_open" disabled class="grayBtn">
		<span class="sarch">启用利率</span>
	</button>
	<button id="button_close" disabled class="grayBtn">
		<span class="sarch">关闭利率</span>
	</button>
</div>
<div style="padding-top: 10px" > 
	<table id="statementGrid" style="width:100%; height:auto;max-height:350px;"></table>
</div>
</div>

<script>
	var statementGrid;
	var onOffId;
	var onOffName;
	var onOffState;
	$(function(){
		onld(selll());//初始化
	})
	function onld(sel){
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findRateState.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			idField : 'onOffId',
			autoRowHeight:true,
			queryParams:sel,
			//sortName : 'payDate',
			//sortOrder : 'asc',
			fitColumns:true,
			nowrap:false,
			singleSelect: false, //是否单选
			columns : [ [ 
				{width : 20,field : 'ck',checkbox : true},
				
			 	{
					width : 10,
					title : '利率状态',
					field : 'onOffState',
					align : 'center',
					formatter : function(value, row,index) {
						value="";
						if(row['onOffState'] == '1'){
							value = '利率已关闭';
						}else if(row['onOffState'] == '0'){
							value = '利率已启用';
						}
						return value;
					} 
				},
		] ],
		onClickRow: function(index, data) {
			var row = $('#statementGrid').datagrid('getSelected');
			if(row){
				if(row.onOffState==1){
					$("#button_close").attr("disabled","disabled").addClass("grayBtn");
					$("#button_open").attr("disabled","disabled").addClass("grayBtn");
					$("#button_open").removeAttr("disabled").removeClass("grayBtn");
					$("#button_open").removeClass("grayBtn1");
					$("#button_close").addClass("grayBtn1");
				}else if(row.onOffState==0){
					$("#button_close").attr("disabled","disabled").addClass("grayBtn");
					$("#button_open").attr("disabled","disabled").addClass("grayBtn");						
					$("#button_close").removeAttr("disabled").removeClass("grayBtn");
					$("#button_open").addClass("grayBtn1");
					$("#button_close").removeClass("grayBtn1");
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

//获取查询条件
function selll(){
 var page = 1;
 var rows = 10;
 onOffName= 'rate';//利率id
 var sel  = {page:page,rows:rows,onOffName:onOffName};
return sel;
}

//启用
$("#button_open").on("click",function(){
	touChanTr();
 });
 //关闭
$("#button_close").on("click",function(){
	touChanTr();
});
 
//活体启用单选事件
function touChanTr(){
	var row = $('#statementGrid').datagrid('getSelected'); 
    if (row) { 
    	onOffId = row.onOffId;
    	onOffName = row.onOffName;
    	onOffState = row.onOffState;
        touChan(onOffId,onOffName,onOffState);
        
    }else{
    	$(".mask").show();
    	var tishi=new Tishi("请选择部署记录","tishi");
    }
}

function touChan(onOffId,onOffName,onOffState){
	
	if(onOffState == 0){
		onOffState = 1;
		$.ajax({
			 url:"<%=basePath%>editRateState.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{onOffId:onOffId,onOffName:onOffName,onOffState:onOffState},
			 beforeSend:function(){},
			 success:function(data){
				if(data.success == true){	
					var tishi=new Tishi("关闭利率成功","chenggong");
					$(".reset_pwd").hide();
				}else{
					var tishi=new Tishi(data.errmsg,"shibai");
				}
			 },
			 complete:function(){
				 
				statementGrid.datagrid('clearSelections');
				statementGrid.datagrid('reload');
			 },
			 error:function(){
				 
				 var tishi=new Tishi("关闭利率失败","shibai");
			 }
		});
	}else if(onOffState == 1){
		onOffState = 0;
		$.ajax({
			 url:"<%=basePath%>editRateState.do",
			 cache:false,
			 type:"post",
			 datatype:'json',
			 data:{onOffId:onOffId,onOffName:onOffName,onOffState:onOffState},
			 beforeSend:function(){},
			 success:function(data){
				if(data.success == true){	
					var tishi=new Tishi("启用利率成功","chenggong");
				}else{
					var tishi=new Tishi(data.errmsg,"shibai");
				}
			 },
			 complete:function(){
				statementGrid.datagrid('clearSelections');
				statementGrid.datagrid('reload');
			 },
			 error:function(){
				 
				 var tishi=new Tishi("启用利率失败","shibai");
			 }
		});
	}
};

</script>

