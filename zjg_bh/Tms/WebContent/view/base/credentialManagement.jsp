<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--添加结束-->
	<div class="com_nav">
    <table class="search_box" style='margin-left:0;'>
			<tr>
				<td><span>终端编号：</span></td>
				<td><input type="text" id="ls_cpmc" /></td>
				<td><span style="display:inline-block;width:95px;">包含凭证号：</span></td>
				<td><input type="text" id="ls_zrkh" /></td>
             </tr>
		</table>
		<div id="refresh">
		<div class="newSonstruction">
			<button id="button_search">
                <span class="sarch_ico"></span>
                <span class="sarch">查询</span>
            </button>
		</div>
		<div style="padding-top: 10px">
			<table id="statementGrid" style="width:100%; height:auto;max-height:321px;"></table>
		</div>
		</div>
	</div>

<script>
 	var statementGrid;
	$(function(){
		onld();//初始化
	})
	function onld(sel){
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findBusBillMan.do',
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
			singleSelect: true, //是否单选
			columns : [ [ 
				{width : 20,field : 'ck',checkbox : true},
				{
					width : 70,
					title : '终端编号',
					field : 'machineNo',
					align : 'center',
					formatter : function(value, row,index) {
					return row['deployMachine']['machineNo'];
					}

				},
				{
					width : 70,
					title : '开始凭证号',
					field : 'startBno',
					align : 'center',
					
				},
				
				{
					width : 70,
					title : '结束凭证号',
					field : 'endBno',
					align : 'center',
				},
				{
					width : 70,
					title : '当前凭证号',
					field : 'nowBno',
					align : 'center',
				},
				
			] ],
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

//	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
    })//查询按钮事件结束
	//获取查询条件
 function selll(){
	 var page = 1;
	 var rows = 10;
	 var machineNo=$("#ls_cpmc").val();//终端编号
	 var bnobno=$("#ls_zrkh").val();//包含凭证号
	 var sel  = {page:page,rows:rows,machineNo:machineNo,bnobno:bnobno}
	return sel;
}
</script>

