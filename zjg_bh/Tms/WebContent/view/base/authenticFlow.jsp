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
					<input type="text" id="ls_jgmc">
				</td>
				<td><span>交易日期：</span></td>
                <td><input id="ls_kssj" type="text" class="easyui-datebox"></td>
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
			$('.common_info').hide();
		});
	});
	function onld(sel){
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findAuthenticFlowList.do',
			striped : true,
			rownumbers : true,
			pagination : true,
			idField : 'unitCode',
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
					title : '机构编号',
					field : 'unitCode',
					align : 'center'

				}, 
			 	{
					width : 90,
					title : '机构名称',
					field : 'unitName',
					align : 'center'
					/* formatter : function(value, row,index) {
						return row['unitName'];
					}  */
				}, 
				{
					width : 70,
					title : '鉴伪通过',
					field : 'status0',
					align : 'center'
					/* formatter : function(value, row,index) {
						value="";
						if(row['status'] == '1'){
							value = '投产';
						}else if(row['status'] == '2'){
							value = '下线';
						}else if(row['status'] == '0'){
							value = '鉴伪通过';
						}
						return value;
					}  */
				},
				{
					width : 50,
					title : '1（边框）',
					field : 'status1',
					align : 'center',
				},
				{
					width : 50,
					title : '2（左上）',
					field : 'status2',
					align : 'center',
				},
				{
					width : 50,
					title : '4（左下）',
					field : 'status4',
					align : 'center',
				},
				{
					width : 50,
					title : '6（全假）',
					field : 'status6',
					align : 'center',
				}
				,
				{
					width : 50,
					title : '通过率',
					field : 'tgl',
					align : 'center',
				}
				,
				{
					width : 50,
					title : '排除6后通过率',
					field : 'tgl6',
					align : 'center',
				}
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

	});
	$("#ls_jssj").datebox({
		editable:false,
		formatter:formatDate,
		onSelect: function(date){
			var ddd=(date.getMonth()+1)+"/"+date.getDate()+"/"+date.getFullYear();
			$("#ls_jssj").datebox('setValue',ddd);
		}

	});
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
    });//查询按钮事件结束
	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var unitName=$("#ls_jgmc").val();//机构名称
	 var machineNo=$("#ls_zdbh").val();
	 var flowDate=$("#ls_kssj").datebox('getValue');//开始时间
	 var sel  = {page:page,rows:rows,machineNo:machineNo,unitName:unitName,flowDate:flowDate};
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
		});
   }
   /*导出  */
   $("#button_dC").click(function(){
   	
   	$.ajax({
   		url:"<%=basePath%>exportAuthenticFlowList.do",
   		cache:false,
   		type:"post",
   		datatype:'json',
   		data:selll(),
   		async:false,
   		beforeSend:function(){},
   		success:function(data){
   			//window.open("../../ReportTemplate/开户流水数据表.xls");
   			if(data.success){
   				window.open("../../ReportTemplate/AuthenticFlowExcel1.xls");
   				//var tishi=new Tishi("导出成功","chenggong");
   			}else{
   				var tishi=new Tishi(data.errmsg,"shibai");
   			}
   		},
   		complete:function(){},
   		error:function(){
   			var tishi=new Tishi("导出失败","shibai");
   		}
   	});
   });
</script>

