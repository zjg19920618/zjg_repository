<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <!--添加结束-->
	<div class="com_nav">
    <table class="search_box"  style='margin-left:0; float:left;'>
			<tr>
				<td><span>机构名称：</span></td>
				<td>
					<select name="" id="ls_jgmc" class="firmZT"></select>
				</td>
				<td><span>业务名称：</span></td>
				<td><input type="text" id="ls_ywmc"></td>
				
             </tr>
             <tr>
             	<td>开始时间：</td>
				<td><input id="ls_kssj" type="text" class="easyui-datebox"></td>
				<td>结束时间：</td>
				<td><input id="ls_jssj" type="text" class="easyui-datebox"></td>
             </tr>
	</table>
	
	<!-- <div style="position:absolute; z-index:999; left:560px; top:-60px;" class="container">
			
			<div class="row" style="margin-top: 0px;">
				<div class="col-xs-3" id="chart" style="inline-height:180px;height:180px;"></div>
				<div class="col-xs-3" id="chart2" style="inline-height:180px;height:180px;"></div>
			</div>				
	</div> -->
	
		<div id="refresh">
		<div class="newSonstruction">
			<button id="button_search">
                <span class="sarch_ico"></span>
                <span class="sarch">查询</span>
            </button>
			<button id="button_export">
                <span class="export_ico"></span>
                <span class="sarch">导出</span>
            </button>
		</div>
		<div style="padding-top: 10px">
			<table id="statementGrid" style="width:100%; height:auto;max-height:321px;"></table>
		</div>
		</div>
	</div>
<%-- <script>
		 require.config({ 
	         paths:{  
	             'echarts' : '../../js/echarts/echarts', 
	             'echarts/chart/pie' : '../../js/echarts/echarts' 
	         } 
	     }); 
      
      // 使用 
	     require( 
	         [ 
	             'echarts', 
	             'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载 
	         ], 
	         function (ec) { 
	                // 基于准备好的dom，初始化echarts图表 
	                var myChart = ec.init(document.getElementById("chart"));
	                var myChart2 = ec.init(document.getElementById("chart2"));
	               
	                //第一个图表
	                $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineType.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:"",
	        	        beforeSend:function(xmlHttp){
	        	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
			            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	        	        },
	        	        success:function(data){
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端类型情况', 
	    	                            subtext: '', 
	    	                            x:'center',	    	                     
	    	                            y:'bottom',
	    	                            textStyle: {
	    	                            	fontSize:14
	    	                            },
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie', 
	    	                                label:{
	    	                                	normal:{
	    	                                	show:false ,
	    	                                	position : 'outside'
	    	                                	}},
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
	    	                            },
	    	                            
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70']
	    	                    };
	        	        	 option.series[0].data = data;
	           	        	 myChart.setOption(option);
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    });
	            
	           //按照机器的使用状态来统计数量 
	           		 $.ajax({
	        	        url:"<%=basePath%>queryDeployMachineStatus.do",
	        	        cache:false,
	        	        type:"post",
	        	        datatype:'json',
	        	        data:"",
	        	        beforeSend:function(xmlHttp){
	        	        	xmlHttp.setRequestHeader("If-Modified-Since","0");
			            	xmlHttp.setRequestHeader("Cache-Control","no-cache");
	        	        },
	        	        success:function(data){
	        	        	var username = [];
	        	        	for(var i=0;i<data.length;i++){
	        	        		username.push(data[i].name);
	        	        	}
	        	        	console.log(username);
	        	        	option = { 
	    	                        title : { 
	    	                            text: '终端运行状态', 
	    	                            subtext: '', 
	    	                            x:'center',
	    	                            y:'bottom',
	    	                            textStyle: {
	    	                            	fontSize:14
	    	                            }
	    	                        }, 
	    	                        tooltip : { 
	    	                            trigger: 'item', 
	    	                            formatter: "{c}台 ({d}%)" 
	    	                        }, 
	    	                        /* legend: { 
	    	                            orient : 'vertical', 
	    	                            x : 'left', 
	    	                            data:username 
	    	                        },  */
	    	                      
	    	                        calculable : true, 
	    	                        series : [ 
	    	                            { 
	    	                                name:'', 
	    	                                type:'pie',
	    	                                label:{
	    	                                	normal:{
	    	                                	show:false ,
	    	                                	position : 'outside'
	    	                                	}},
	    	                                radius : '55%', 
	    	                                center: ['50%', '60%'], 
	    	                                data:[{value:"100%", name:'暂无数据'}]
		    	                                	
	    	                            } 
	    	                        ],
	    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
	    	                    };
	        	        	option.series[0].data =data;
	        	        	//option.legend[0].data = username;
	       	        		myChart2.setOption(option);
	        	        	 
	        	        },
	        	        complete:function(){
	        	        	
	        	        },
	        	        error:function(){
	        	        	
	        	        }
	        	    });            
	         }         
	       );           
	               
         
</script> --%>
<script>
 	var statementGrid;
 	var jgmc_dc="";
 	var ywmc_dc="";
 	var kssj_dc="";
 	var jssj_dc="";
	$(function(){
		 var sel1  = {page:1,rows:10,productName:"",unitName:"",kstime:"",jstime:""}
		onld(sel1);//初始化
		$('#ip_edit,#jqbh1_add').blur(function(){
			$('.common_info').hide()
		})
	})
	function onld(sel){
		console.log(sel)
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findkhtjList.do',
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
					title : '机构名称',
					field : 'unitName',
					align : 'center',
				},
				{
					width : 70,
					title : '业务名称',
					field : 'productName',
					align : 'center',
					
				},
				{
					width : 70,
					title : '办理次数(次)',
					field : 'number',
					align : 'center',
					
				},
				{
					width : 70,
					title : '金额合计(元)',
					field : 'total',
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
//	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
		jgmc_dc=selll().unitName;
		ywmc_dc=selll().productName;
		kssj_dc=selll().kstime;
		jssj_dc=selll().jstime;
    })//查询按钮事件结束
//	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var unitCode=$("#ls_jgmc").val();
	 var productName=$("#ls_ywmc").val();
	 var kstime=$("#ls_kssj").datebox('getValue');
	 var jstime=$("#ls_jssj").datebox('getValue');
	 var sel  = {page:page,rows:rows,productName:productName,unitCode:unitCode,kstime:kstime,jstime:jstime}
	return sel;
}
	/*导出  */
	$("#button_export").click(function(){
		 var kstime=$("#ls_kssj").datebox('getValue');
		 if(kstime == null || kstime == undefined || kstime == ""){
			$(".mask").show();
			new Tishi("请选择开始时间","tishi");
			return;
		 }
		$.ajax({
			url:"<%=basePath%>accountExcel.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:{unitName:jgmc_dc,productName:ywmc_dc,ksTime:kssj_dc,jsTime:jssj_dc},
			async:false,
			beforeSend:function(){},
			success:function(data){
				window.open("../../ReportTemplate/BckReport.xls");
			},
			complete:function(){},
			error:function(){}
		})
	})

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
           			console.log("qwer"+data.rows[i].unitViewId.unitCode);
           			console.log(data);
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
</script>

