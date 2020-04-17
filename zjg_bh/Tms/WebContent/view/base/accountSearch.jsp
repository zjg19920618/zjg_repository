<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--开户流水查询-->
	<div class="com_nav">
    <table class="search_box" style='margin-left:0;'>
			<tr>
				<td><span>机构名称：</span></td>
				<td>
					<select id="ls_jgmc" class="firmZT"></select>
				</td>
				<td><span>产品名称：</span></td>
				<td><input type="text" id="ls_cpmc"></td>
				<td><span>转出卡号：</span></td>
				<td><input type="text" id="ls_zckh" /></td>
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
                <td><span>客户姓名：</span></td>
                <td><input type="text" id="ls_khxm"></td>
                <td><span>开始时间：</span></td>
                <td><input id="ls_kssj" type="text" class="easyui-datebox"></td>
                <td><span>结束时间：</span></td>
                <td><input id="ls_jssj" type="text" class="easyui-datebox"></td>
			</tr>
		</table>
		<div id="refresh">
		<div class="newSonstruction">
			<button id="button_search">
                <span class="sarch_ico"></span>
                <span class="sarch">查询</span>
            </button>
            <button id="button_dC">
                <span class="export_ico"></span>
                <span class="sarch">导出</span>
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
		$('#ip_edit,#jqbh1_add').blur(function(){
			$('.common_info').hide()
		})
	})
	function onld(sel){
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findkhList.do',
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
					field : 'terminalCode',
					align : 'center',
					formatter : function(value, row,index) {
					return row['deployMachine']['machineNo'];
					}

				}, 
			 	{
					width : 90,
					title : '机构名称',
					field : 'unitName',
					align : 'center',
					/* formatter : function(value, row,index) {
						return row['baseUnit']['unitName'];
					} */
				}, 
				{
					width : 70,
					title : '凭证号',
					field : 'certNo',
					align : 'center',
					/* formatter : function(value, row,index) {
						value="";
						if(row['status'] == '1'){
							value = '投产';
						}else if(row['status'] == '2'){
							value = '下线';
						}else if(row['status'] == '0'){
							value = '初始';
						}
						return value;
					} */
				},
				{
					width : 70,
					title : '转出卡号',
					field : 'cardNo',
					align : 'center',
				},
				{
					width : 50,
					title : '客户名称',
					field : 'customerName',
					align : 'center',
				},
				{
					width : 50,
					title : '业务名称',
					field : 'productName',
					align : 'center',
				},
				{
					width : 50,
					title : '支付类型',
					field : 'payType',
					align : 'center',
					formatter : function(value, row,index) {
						if(row['payType']){
							if(row['payType']=='C'){
								return '银行卡开户';
							}else{
								return '现金开户';
							}
						}
						return '';
					}
				},
				{
					width : 50,
					title : '存单帐号',
					field : 'subAccountNo',
					align : 'center',
				},
			
				{
					width : 50,
					title : '存期',
					field : 'depositPeriod',
					align : 'center',
				},
				{
					width : 50,
					title : '存款金额',
					field : 'depositAmt',
					align : 'center',
				},
				{
					width : 60,
					title : '状态',
					field : 'status',
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
	//设置分页控件   payType
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

setPerunitCode("ls_jgmc");
	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
    })//查询按钮事件结束
	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var unitName=$("#ls_jgmc").val();//机构名称
	 var productName=$("#ls_cpmc").val();//产品名称
	 var cardNo=$("#ls_zckh").val();//转出卡号
	 var customerName=$("#ls_khxm").val();//客户姓名
	 var sDate=$("#ls_kssj").datebox('getValue');//开始时间
	 var jDate=$("#ls_jssj").datebox('getValue');//结束时间
	 var machineNo=$("#ls_zdbh").val();
	 var subAccountNo=$("#ls_cdzh").val();//存单账户
	 var certNo=$("#ls_pzh").val();//凭证叫
	 var sel  = {page:page,rows:rows,machineNo:machineNo,subAccountNo:subAccountNo,certNo:certNo,productName:productName,cardNo:cardNo,sDate:sDate,jDate:jDate,unitName:unitName,customerName:customerName}
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
           	}
           },
           complete:function(){},
           error:function(){}
		})
   }
   /*导出  */
   $("#button_dC").click(function(){
   	
   	$.ajax({
   		url:"<%=basePath%>exportkhList.do",
   		cache:false,
   		type:"post",
   		datatype:'json',
   		data:selll(),
   		async:false,
   		beforeSend:function(){},
   		success:function(data){
   			//window.open("../../ReportTemplate/开户流水数据表.xls");
   			if(data.success){
   				window.open("../../ReportTemplate/CdjOrderExcel1.xls");
   				//var tishi=new Tishi("导出成功","chenggong");
   			}else{
   				var tishi=new Tishi(data.errmsg,"shibai");
   			}
   		},
   		complete:function(){},
   		error:function(){
   			var tishi=new Tishi("导出失败","shibai");
   		}
   	})
   })
</script>

