<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--添加结束-->
	<div class="com_nav">
    <table class="search_box" style='margin-left:0;'>
			<tr>
				<td><span>机构名称：</span></td>
				<td>
					<select id="ls_jgmc" class="firmZT"></select>
				</td>
				<td><span>产品名称：</span></td>
				<td><input type="text" id="ls_cpmc"></td>
				<td><span>转入卡号：</span></td>
				<td><input type="text" id="ls_zrkh" /></td>
             </tr>
             <tr>
				<td><span>终端编号：</span></td>
				<td>
					<input type="text" id="ls_zdbh">
				</td>
				<td><span>存单帐号：</span></td>
				<td><input type="text" id="ls_cdzh"></td>
				<td>凭证号：</td>
				<td><input type="text" id="ls_pzh" /></td>
             </tr>
             <tr>
                <td><span>转入姓名：</span></td>
                <td><input type="text" id="ls_zrxm"></td>
                <td><span>开始时间：</span></td>
                <td><input id="ls_kssj" type="text" class="easyui-datebox"></td>
                <td><span>结束时间：</span></td>
                <td><input id="ls_jssj" type="text" class="easyui-datebox"></td>
			</tr>
			<tr>
                <td><span>销户方式：</span></td>
                <td>
                	<select id="CancelMode" class="firmZT">
                		<option>请选择</option>
                		<option value="1">存单自动</option>
                		<option value="2">存单手动</option>
                		<option value="3">无存单</option>
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
	            <button id="button_daochu">
	                <span class="export_ico"></span>
	                <span class="sarch">导出</span>
            	</button>
			</div>
			<div style="padding-top: 10px" class="service_flow_query">
				<table id="statementGrid" style="width:100%; height:auto;max-height:280px;"></table>
			</div>
		</div>
	</div>

<script>
 	var statementGrid;
	$(function(){
		onld();//初始化
		$('#ip_edit,#jqbh1_add').blur(function(){
			$('.common_info').hide()
		});
		setPerunitCode("ls_jgmc");
	})
	function onld(sel){
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findFlowList.do',
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
					title : '业务流水ID',
					field : 'flowId',
					align : 'center',
				},
				{
					width : 80,
					title : '终端编号',
					field : 'machineNo',
					align : 'center',
					formatter : function(value, row,index) {
					return row['deployMachine']['machineNo'];
					}

				},
				{
					width : 60,
					title : '机构代码',
					field : 'unitCode',
					align : 'center',
					formatter : function(value, row,index) {
						return row['baseUnit']['unitCode'];
					}
				},
				
				{
					width : 70,
					title : '产品名称',
					field : 'projectName',
					align : 'center',
				},
				{
					width : 70,
					title : '存单帐号',
					field : 'billNo',
					align : 'center',
				},
				{
					width : 70,
					title : '凭证号',
					field : 'certNo',
					align : 'center',
				},
				{
					width : 50,
					title : '实付本金',
					field : 'realPri',
					align : 'center',
				},
				{
					width : 50,
					title : '实付利息',
					field : 'realInte',
					align : 'center',
				},
				{
					width : 60,
					title : '销户类型',
					field : 'canelType',
					align : 'center',
					formatter : function(value, row,index) {
						if(value==2){
							return "销户转电子账户";
						}else if(value==3){
							return "销户转唐行宝";
						}else{
							return "销户转银行卡";
						}
					}
				},
				{
					width : 70,
					title : '转入卡号',
					field : 'bankCardNo',
					align : 'center',
				},
				{
					width : 70,
					title : '转入卡姓名',
					field : 'bankCardName',
					align : 'center',
				},
				{
					width : 70,
					title : '创建时间',
					field : 'createDate',
					align : 'center',
					formatter : function(value, row,index) {
						return dateNew(value);
					}
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
	$("#ls_kssj").datebox({
		editable:false,
		formatter:formatDate,
		onSelect: function(date){
			var ddd=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear();
			$("#ls_kssj").datebox('setValue',ddd);
		}

	})
	$("#ls_jssj").datebox({
		editable:false,
		formatter:formatDate,
		onSelect: function(date){
			var ddd=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear();
			$("#ls_jssj").datebox('setValue',ddd);
		}

	})
	function formatDate(myDate){
		var y=myDate.getFullYear();
		var m=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
		var d=myDate.getDate();        //获取当前日(1-31)
		if(myDate.getMonth()>=1&&myDate.getMonth()<=8){
			m="0"+m;
		}//获取完整的年份(4位,1970-????)
		if(m==1){
			m="0"+m;
		}
		if(myDate.getDate()>=0&&myDate.getDate()<=9){
			d="0"+d;
		}
		var currentdate = ""+y+m+d;
	        return currentdate;
	}
		 function getNowFormatDate() {
		        var date = new Date();
		        var seperator1 = "-";
		        var year = date.getFullYear();
		        var month = date.getMonth() + 2;
		        var strDate = date.getDate();
		        if (month >= 1 && month <= 9) {
		            month = "0" + month;
		        }
		        if (strDate >= 0 && strDate <= 9) {
		            strDate = "0" + strDate;
		        }
		        var currentdate = year + seperator1 + month + seperator1 + strDate;
		        return currentdate;
		    }
//	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
    	reload();
    })//查询按钮事件结束
	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var unitCode = "";//机构名称
	 var projectName=$("#ls_cpmc").val();//产品名称
	 var bankCardNo=$("#ls_zrkh").val();//转入卡号
	 var bankCardName=$("#ls_zrxm").val();//转入姓名
	 var kstime=$("#ls_kssj").datebox('getValue');
	 var jstime=$("#ls_jssj").datebox('getValue');
	 var machineNo=$("#ls_zdbh").val();
	 var billNo=$("#ls_cdzh").val();
	 var certNo=$("#ls_pzh").val();
	 var checkStatus ="";
	 if($("#CancelMode").val() != "请选择"){
		checkStatus = $("#CancelMode").val();
		
	 }
	 if($("#ls_jgmc").val() != "请选择"){
		 unitCode = $("#ls_jgmc").val();
	 }
	 var sel  = {page:page,rows:rows,checkStatus:checkStatus,machineNo:machineNo,billNo:billNo,certNo:certNo,projectName:projectName,bankCardNo:bankCardNo,kstime:kstime,jstime:jstime,unitCode:unitCode,bankCardName:bankCardName}
	 console.log(sel);
	return sel;
}
//   //机构查询
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
   /*机构名称查询*/
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
           	console.log(data);
           },
           complete:function(){},
           error:function(){}
		})
   }
   /*导出  */
	$("#button_daochu").click(function(){
		 var kstime=$("#ls_kssj").datebox('getValue');
		 if(kstime == null || kstime == undefined || kstime == ""){
			$(".mask").show();
			new Tishi("请选择开始时间","tishi");
			return;
		 }
		console.log(selll());
		$.ajax({
			url:"<%=basePath%>outFlowList.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:selll(),
			async:false,
			beforeSend:function(){},
			success:function(data){
				window.open("../../ReportTemplate/colseAccountExcel.xls");
			},
			complete:function(){},
			error:function(){
				var tishi=new Tishi("导出失败","shibai");
			}
		})
	});
</script>

