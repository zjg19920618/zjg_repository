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
		 var sel1  = {page:1,rows:10,projectName:"",unitCode:"",kstime:"",jstime:""}
		onld(sel1);//初始化
		$('#ip_edit,#jqbh1_add').blur(function(){
			$('.common_info').hide()
		})
	})
	function onld(sel){
		console.log(sel)
		statementGrid = $('#statementGrid').datagrid({
			url : '<%=basePath%>findStatisticsList.do',
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
					field : 'projectName',
					align : 'center',
					
				},
				{
					width : 70,
					title : '开始时间',
					field : 'ksTime',
					align : 'center',
					hidden:true,
				},
				
				{
					width : 70,
					title : '结束时间',
					field : 'jsTime',
					align : 'center',
					hidden:true,
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

//	
//var num=0;
//var page = 1;
//var rows = 10;
//var intoll = 0;
//var status;//状态编码
//var machineNo;
//oNload();
//setSelectMacType();
////setSelectManuCode();
//setSelectStatus();
//setSelectUnitCode();
//setPerType("jqlx_edit");// 
//setPerType("jqlx_add");//
setPerunitCode("ls_jgmc");
//setPerunitCode("jgmc_add");
//
//$(function(){
//	valid();
//});
//function valid(){
//	$('#form1').validationEngine('attach', {
//		relative: true,
//		overflownDIV:"#divOverflown",
//		promptPosition:"bottomLeft",
//		maxErrorsPerField:1,
//		onValidationComplete:function(form,status){
//			if(status){
//				$(".username_info").hide();
//				$(".email_info").hide();
//				subbswh();
//			}
//		}
//	});
//	
//	$('#form2').validationEngine('attach', {
//		relative: true,
//		overflownDIV:"#divOverflown",
//		promptPosition:"bottomLeft",
//		maxErrorsPerField:1,
//		onValidationComplete:function(form,status){
//			if(status){
//				$(".bj_ip_info").hide();
//				subbswh2();
//			}
//		}
//	});
//}
//
//function oNload(){
//	//初始化
//	console.log("初始化...");
//	$("#nav_type1").show();
//	 num=0;
//	 page = 1;
//	 rows = 10;
//	 intoll = 0;
//     var sel= {page:page,rows:rows};// 查询条件
//     pageL(sel);
//	
//    $("#selectpll").bind("change",function(){
//    	page =	 parseInt(1);//页数
//    	rows =	 parseInt($("#selectpll  option:selected").text()); // select的值
//    	console.log("页数"+page);
//    	console.log("select的值"+rows);
//    	num  = 0;
//    	var jgmc=$("#jgmc").val();
//    	var sjjg=$("#sjjg").val();
//    	var sel = {page:page,rows:rows,periCode:jgmc,parentName:sjjg}
//    	pageL(sel);
//    });
//    //上一页
//    $("#buttonm").on("click",function(){
//    	console.log("......");
//    	if(1 == page){
//  			return;
//  		}
//    		page = page -1;
//        	rows = rows;
//        	num= page*rows-rows
//        	console.log("abcdddd"+page);
//        	console.log("num"+num);
//        	var jgmc=$("#jgmc").val();
//        	var sjjg=$("#sjjg").val();
//        	var sel = {page:page,rows:rows,unitName:jgmc,parentName:sjjg}
//        	pageL(sel);
//	});
//    //下一页
//    $("#buttonm1").on("click",function(){
//    	console.log("intoll:"+intoll+"num:"+num);
//    	if(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text())) == page){
//    		return;
//    	}
//    	if(num % num >= intoll && num <=intoll ){
//    		$("#table_tbody").empty();
//    	}else{
//    		page = parseInt(page+1);
//        	rows = parseInt(rows);
//        	var jgmc=$("#jgmc").val();
//        	var sjjg=$("#sjjg").val();
//        	var sel = {page:page,rows:rows,unitName:jgmc,parentName:sjjg}
//        	pageL(sel);
//    	}
//    		
//	});
//    //尾页
//  	$("#buttonm2").on("click",function(){
//  		if(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text())) == page){
//  			return;
//  		}
//		page = Math.ceil(intoll / parseInt($("#selectpll  option:selected").text()));
//		//page = Math.ceil(intoll / parseInt(10))*10-intoll % pages;
//		if(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text()))-1 == 0){
//			num=0;
//		}else{
//			num= parseInt(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text())) - 1)  * rows;
//		}
//	  	rows = parseInt(rows);
//	  	console.log("weiye"+Math.ceil(intoll / parseInt($("#selectpll  option:selected").text()))+"  abc"+page);
//    	var jgmc=$("#jgmc").val();
//    	var sjjg=$("#sjjg").val();
//    	var sel = {page:page,rows:rows,unitName:jgmc,parentName:sjjg}
//	  	pageL(sel);
//	});
//	
//  //首页
//    $("#index_o").on("click",function(){
//    		page = 1;
//        	rows = rows;
//        	num=0;
//        	var jgmc=$("#jgmc").val();
//        	var sjjg=$("#sjjg").val();
//        	var sel = {page:page,rows:rows,unitName:jgmc,parentName:sjjg}
//        	pageL(sel);
//	});
//  var top =false;
//}
//var new_status;//设备状态
//function pageL(sel){
//	/* var  periId1; */
//	console.log("进入表格初始化.........."+sel);
//    $.ajax({
//        url:"<%=basePath%>findDeployMachineList.do",
//        cache:false,
//        type:"post",
//        datatype:'json',
//        data:sel,
//        beforeSend:function(){},
//        success:function(data){
//        	
//        console.log("page"+page +"rows"+rows);
//        console.log(data);
//        intoll = data.total;
//        $("#altogether").html(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text()))); //向上取整
//        console.log(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text())));
//        $("#spancll").html(intoll);
//        //设置分页说明中的起始条数
//        var tTz = 0;
//        if(page == 1){
//        	tTz = page;
//        }else{
//        	tTz = parseInt(page-1)*parseInt($("#selectpll  option:selected").text())+1;
//        }
//        $("#tTz").html(tTz);
//     	//设置分页说明中的结束条数 
//     	if(Math.ceil(intoll / parseInt($("#selectpll  option:selected").text())) == page){
//     		$("#uszr").html(intoll);
//     	}else{
//     		$("#uszr").html(tTz+rows-1);
//     	}
//        $("#table_tbody").empty();	
//        for(var i = 0;i<data.rows.length;i++){
//        	num++;
//        	// 机构类型
//		  /*   var new_unitType;
//			if(data.rows[i].unitType == 0){
//				new_unitType = "总行"; 
//			}else if(data.rows[i].unitType == 1){
//				new_unitType = "分行";
//			}else if(data.rows[i].unitType == 2){
//				new_unitType = "支行";
//			}else if(data.rows[i].unitType == 3){
//				new_unitType = "网点";
//			} */
//			var new_machineType;
//			// 外设类型
//			if(data.rows[i].machineType == 01){
//				new_machineType = "填单机";
//			}else if(data.rows[i].machineType == 02){
//				new_machineType = "发卡机";
//			}else if(data.rows[i].machineType == 03){
//				new_machineType = "自助查询机";
//			}
//				var new_manuCode;
//			//厂商
//				if(data.rows[i].manuCode == "CC0001"){
//					new_manuCode = "博宏";
//				}else if(data.rows[i].manuCode == "CC0002"){
//					new_manuCode = "长城";
//				}
//				
//				//状态
//				if(data.rows[i].status == 1){
//					new_status = "投产";
//				}else if(data.rows[i].status == 2){
//					new_status = "下线";
//				}else if(data.rows[i].status == 0){
//					new_status = "初始";
//				}
//				var new_unitCode;
//				//状态
//				if(data.rows[i].unitCode == 00001){
//					new_unitCode = "中金";
//				}else if(data.rows[i].unitCode == 00002){
//					new_unitCode = "中信";
//				}else if(data.rows[i].unitCode == ""){
//					new_unitCode = "";
//				}
//        	$("#table_tbody").append("<tr onclick=\"selectTr(this)\"><td>"
//        			+num+"</td><td>"
//        			+data.rows[i].id.machineNo+"</td><td>"   //机器编号
//        			+new_machineType+"</td><td>"  //机器类型
//        			+data.rows[i].machineName+"</td><td >" //机器名称
//        			+data.rows[i].manuName+"</td><td style=\"display:none\">" //机器名称
//        			+data.rows[i].manuCode+"</td><td>"    //机构名称--
//        			+data.rows[i].unitName+"</td><td style=\"display:none\">"    //机构名称
//        			+data.rows[i].unitCode+"</td><td style=\"display:none\">"  //机构名称
//        		 	+data.rows[i].status+"</td><td>"  //设备状态--
//        		 	+new_status+"</td><td style=\"display:none\">"   //设备状态
//        		 	+data.rows[i].machineType+"</td><td style=\"display:none\">"  //机器类型--
//        		 	+data.rows[i].tellerNo+"</td><td style=\"display:none\">"   //柜员号
//        		 	+data.rows[i].ip+"</td><td style=\"display:none\">"   //ip
//        		 	+data.rows[i].machineCode+"</td><td>"   //机器编号
//        		 	+data.rows[i].remark+"</td></tr>"        //备注
//        	);
//        //悬停变色
//        /*$(".table_box tbody tr").click(function(e){
//				rowsColor();
//				$(this).css("background","#879fb6");
//		        $(".table_menu").show().css({top:e.pageY-268+$(".table_box").scrollTop()-55,left:e.pageX-260});
//				$(".table_menu li:last").css("border-bottom","none");
//				if(data.rows[$(this).index()].status == 1){
//					$("#touchan,#touchan").html("下线");
//				}else if(data.rows[$(this).index()].status == 2){
//					$("#touchan,#touchan").html("投产");
//				}else if(data.rows[$(this).index()].status == 0){
//					$("#touchan,#touchan").html("投产");
//				}
//				status = data.rows[$(this).index()].status;
//				machineNo = data.rows[$(this).index()].id.machineNo;
//		});
//		$(document).bind('mouseover',function(e){
//			if(e.pageY>760||e.pageY<260){
//				$(".table_menu").hide();
//				rowsColor();
//			}
//		});*/
//
//	/* 	periId1 = data.rows[$(this).index()].periId; */
//		 //判断查询框个数设置表格高度
//		/*if($(".nav_type>form>ul").children("li").length>4){
//			$(".table_box").css("top","155px");
//		}*/
//		//
//    	}
//    },
//    complete:function(){},
//    error:function(){}
//	});
//}
//	//查询按钮事件
    $("#button_search").click(function(event){
    	event.preventDefault();
    	onld(selll());
		jgmc_dc=selll().unitCode;
		ywmc_dc=selll().projectName;
		kssj_dc=selll().kstime;
		jssj_dc=selll().jstime;
    })//查询按钮事件结束
//	//获取查询条件
 function selll(){
	 page = 1;
	 rows = 10;
	 var unitCode=$("#ls_jgmc").val();
	 var projectName=$("#ls_ywmc").val();
	 var kstime=$("#ls_kssj").datebox('getValue');
	 var jstime=$("#ls_jssj").datebox('getValue');
	 var sel  = {page:page,rows:rows,projectName:projectName,unitCode:unitCode,kstime:kstime,jstime:jstime}
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
			url:"<%=basePath%>outExcel.do",
			cache:false,
			type:"post",
			datatype:'json',
			data:{unitCode:jgmc_dc,projectName:ywmc_dc,ksTime:kssj_dc,jsTime:jssj_dc},
			async:false,
			beforeSend:function(){},
			success:function(data){
				window.open("../../ReportTemplate/BckReport.xls");
			},
			complete:function(){},
			error:function(){}
		})
	})

////机器名称动态添加
//function machineAdd(){
//	$.ajax({
//		url:"<%=basePath%>findMachineList1.do",
//		cache:false,
//		type:"post",
//		datatype:'json',
//		async:false,
//		beforeSend:function(){},
//		success:function(data){
//			$("#jqmc_add").html("<option value=''>请选择</option>");
//			console.log(data);
//			for(var i = 0;i<data.rows.length;i++){
//           		$("#jqmc_add").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
//           	}
//		},
//		complete:function(){},
//		error:function(){}
//	})
//}
////编辑中机器名称动态添加
//function machineEdit(){
//	$.ajax({
//		url:"<%=basePath%>findMachineList1.do",
//		cache:false,
//		type:"post",
//		datatype:'json',
//		async:false,
//		beforeSend:function(){},
//		success:function(data){
//			$("#jqmc_edit").html("<option value=''>请选择</option>");
//			console.log(data);
//			for(var i = 0;i<data.rows.length;i++){
//           		$("#jqmc_edit").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
//           	}
//		},
//		complete:function(){},
//		error:function(){}
//	})
//}
////表格单选事件
///*function selectTr(){
//	var row = $('#statementGrid').datagrid('getSelected'); 
//	console.log(row)
//    if (row) { 
//        $(".firm_add_add,.mask").show();
//    	$("#jqbh1_add").val(row.machineCode);//机器编号
//    	$("#jqmc_add").val(row.machineCode);// 机器名称
//    	$("#gyh_add").val(row.manuCode);// 柜员号
//    	$("#jgmc_add").val(row.unitCode);// 机构名称
//    	$("#ip_add").val(row.ip);// ip地址
//    } else{
//    	$(".mask").show();
//    	var tishi=new Tishi("请选择一条记录","tishi");
//    }
//}*/
// //表格编辑单选事件
//function selectTr(){
//	var row = $('#statementGrid').datagrid('getSelected'); 
//	console.log(row)
//    if (row) { 
//        $(".firm_add_edit,.mask").show();
//    	$("#jqbh_edit").val(row.id.machineNo);//机器编号
//    	$("#jqmc_edit").val(row.machineCode);// 机器名称
//    	$("#gyh_edit").val(row.manuCode);// 柜员号
//    	$("#jgmc_edit").val(row.unitCode);// 机构名称
//    	$("#ip_edit").val(row.ip);// ip地址
//    	$("#main_pwd_sign_edit").val(row.majorkeyflag);// 主密码
//    	$("#work_pwd_sign_edit").val(row.workkeyflag);// 工作密码
//    	$("#other_pwd_sign_edit").val(row.keyflag);// 密钥密码
//    	$("#mac_pwd_sign_edit").val(row.mackeyflag);// mac密码
//    	$("#access_pwd_sign_edit").val(row.authorizekeyflag);// 授权密码
//    }else{
//    	$(".mask").show();
//    	var tishi=new Tishi("请选择一条记录","tishi");
//    }
//}
//
// //表格删除单选事件
//function deleteTr(){
//	var row = $('#statementGrid').datagrid('getSelected'); 
//	console.log(row)
//    if (row) { 
//        delUnit(row.id.machineNo);
//    }else{
//    	$(".mask").show();
//    	var tishi=new Tishi("请选择一条记录","tishi");
//    }
//}
// //投产单选事件
//function touChanTr(){
//	var row = $('#statementGrid').datagrid('getSelected'); 
//	console.log(row)
//    if (row) { 
//        touChan(row.status,row.id.machineNo);
//    }else{
//    	$(".mask").show();
//    	var tishi=new Tishi("请选择一条记录","tishi");
//    }
//}
// //编辑
//$("#button_edit").on("click",function(){
//	machineEdit();
//	selectTr();
//});
////删除
//$("#button_delete").click(function(){
//		 var row = $('#statementGrid').datagrid('getSelected'); 
//	     if (row) { 
//	    		var machineNo = row.id.machineNo;
//	    		console.log(row);
//	    		var pop=new Pop("删除","remind_ok","tishi");
//	    		$("#remind_ok").on("click",function(){
//	    	 		 $.ajax({
//	    	 			 url:"<%=basePath%>delDeployMachine.do",
//	    	 			 cache:false,
//	    	 			 type:"post",
//	    	 			 datatype:'json',
//	    	 			 data:{machineNo:machineNo},
//	    	 			 beforeSend:function(){},
//	    	 			 success:function(data){
//	    	 				if(data.success==true){
//	    	 					var tishi=new Tishi("删除成功","chenggong");
//	    	 					onld();
//	    	 				}else{
//	    	 					 $(".mask").show();
//	    	 					var tishi=new Tishi("删除失败","shibai");
//	    	 				}
//	    	 			 },
//	    	 			 complete:function(XHR, textStatus){
//	    	 				statementGrid.datagrid('clearSelections');
//	    					statementGrid.datagrid('reload');
//	    	 			 },
//	    	 			 error:function(){
//	    	 				var tishi=new Tishi("删除失败","shibai");
//	    	 			 }
//	    	 		})
//	    	 	});
//	    	 }else{
//	    		 var tishi=new Tishi("请选择一条记录","tishi");
//	    	 }
//	 	
//	 });
// //投产
//$("#button_touchan").on("click",function(){
//	touChanTr();
// });
//$("#button_xiaxian").on("click",function(){
//	touChanTr();
//});
////添加中的保存结束
////关闭按钮
//    $(".firm_add_head a").click(function(){
//        $(".firm_add_add").hide();
//        $(".firm_add_edit").hide();
//		$(".mask").hide();
//		$('form').validationEngine('hideAll');
//    })
////取消按钮
//    $("#close_add_add,#close_add_edit").click(function(event){
//        event.preventDefault();
//        $(".firm_add_add").hide();
//        $(".firm_add_edit").hide();
//		$(".mask").hide();
//		$('form').validationEngine('hideAll');
//    });
////隔行变色封装
//	/*function rowsColor(){
//		$(".table_box tbody tr:odd,.table_box tbody tr:odd").css("background","#ebf2f7");
//        $(".table_box tbody tr:even,.table_box tbody tr:even").css("background","#fff9f8");
//	}*/
//	function savebswh(event){
//    	$("#form1").submit();
//    }
//	function updatebswh(event){
//	    	$("#form2").submit();
//	}
//	// 添加按钮事件
//	$("#save_add_add").click(function(event){
//		event.preventDefault();
//	});
//    $("#button_add_add").click(function(event){
//    	$(".username_info").hide();
//    	event.preventDefault();
//		clearForm();
//    	$("#sjjg_add").attr("disabled","disabled"); //上级机构默认为不可编辑
//    	$(".firm_add_add").css("display","block");
//		$(".mask").show();
//		    $.ajax({
//				url:"<%=basePath%>findMachineList1.do",
//				cache:false,
//				type:"post",
//				datatype:'json',
//				beforeSend:function(){},
//				success:function(data){
//					$("#jqmc_add").html("<option value=''>请选择</option>");
//					console.log(data);
//					for(var i = 0;i<data.rows.length;i++){
//           				$("#jqmc_add").append("<option value='"+data.rows[i].machineCode+"'>"+data.rows[i].machineName+"</option>");
//           			}
//				},
//				complete:function(){
//					
//				},
//				error:function(){}
//			})
//    });
//	var machineCode;
//	$("#jqmc_add").change(function(){
//		var jqmc_add=$("#jqmc_add option:selected").text();
//		console.log(jqmc_add);
//		$.ajax({
//			url:"<%=basePath%>findMachineList.do",
//			cache:false,
//			type:"post",
//			datatype:'json',
//			data:{machineName:jqmc_add,page:1,rows:10},
//			beforeSend:function(){},
//			success:function(data){
//				machineCode=data.rows[0].machineCode;
//				console.log(machineCode);
//			},
//		});
//	})
//	function subbswh(){
//		console.log(machineCode);
//		var machineNo=$("#jqbh1_add").val();//机器编号 
////		var machineType=$("#jqlx_add").val();// 机器类型
//		var machineName=$("#jqmc_add").val();// 机器名称
//		var unitCode=$("#jgmc_add").val();// 机构名称
//		var tellerNo=$("#gyh_add").val();// 柜员号
//		var ip=$("#ip_add").val();// IP地址
//		var keyboard_major_keyflag=$("#main_pwd_sign_add").val();// 键盘主密码标记
//		var keyboard_work_keyflag=$("#work_pwd_sign_add").val();// 键盘工作密码标记
//		var key_flag=$("#other_pwd_sign_add").val();// 转加密密钥标记
//		var mac_keyflag=$("#mac_pwd_sign_add").val();// MAC密钥标记
//		var authorize_keyflag=$("#access_pwd_sign_add").val();// 授权密钥标记
//		var data={machineNo:machineNo,machineName:machineName,unitCode:unitCode,tellerNo:tellerNo,ip:ip,machineCode:machineCode,machineCode:machineCode,keyboard_major_keyflag:keyboard_major_keyflag,keyboard_work_keyflag:keyboard_work_keyflag,key_flag:key_flag,mac_keyflag:mac_keyflag,authorize_keyflag:authorize_keyflag}
//		$.ajax({
//			url:"<%=basePath%>saveDeployMachine.do",
//			cache:false,
//			type:"post",
//			datatype:'json',
//			data:data,
//			beforeSend:function(){},
//			success:function(data){
//				console.log(0808080);
//				if(data.success == true){	
//					$(".firm_add_add").css("display","none");
//					var tishi=new Tishi("保存成功","chenggong");
//				}else if(data.errmsg == 01){
//					$(".username_info").show();
//				}else{
//					$(".email_info").show();
//				}
//			},
//			complete:function(){
//				statementGrid.datagrid('clearSelections')
//				statementGrid.datagrid('reload');
//			},
//			error:function(){var tishi=new Tishi("保存失败","shibai");console.log("失败1")}
//		})
//	};
//			// 清空表单
//function clearForm(){
//	$("#jqbh1_add").val("");//厂商编号
//	$("#jqbh_add").val("");// 厂商名称
//	$("#jgmc_add").val("");// 联系人
//	$("#gyh_add").val("");// 座机
//	$("#phone").val("");// 手机
//	$("#ip_add").val("");// 厂商状态
//	$("#jqmc_add").val("");// 备注
//	$("#main_pwd_sign_add").val("");
//	$("#work_pwd_sign_add").val("");
//	$("#other_pwd_sign_add").val("");
//	$("#mac_pwd_sign_add").val("");
//	$("#access_pwd_sign_add").val("");
//}
//	$("#jqmc_edit").on("change",function(){
//		var jqmc_edit=$("#jqmc_edit option:selected").text();
//		console.log(jqmc_edit);
//		$.ajax({
//			url:"<%=basePath%>findMachineList.do",
//			cache:false,
//			type:"post",
//			datatype:'json',
//			data:{machineName:jqmc_edit,page:1,rows:10},
//			beforeSend:function(){},
//			success:function(data){
//				var machineCode=data.rows[0].machineCode; 
//			},
//		});
//	});
//	  function subbswh2(){
//		//	event.preventDefault();
//			var machineNo=$("#jqbh_edit").val();//机器编号 
//			var machineType=$("#jqlx_edit").val();// 机器类型
//			var machineCode=$("#jqmc_edit").val();// 机器名称
//			var unitCode=$("#jgmc_edit").val();// 机构名称
//			var tellerNo=$("#gyh_edit").val();// 柜员号
//			var ip=$("#ip_edit").val();// IP地址
//			var keyboard_major_keyflag=$("#main_pwd_sign_edit").val();// 键盘主密码标记
//			var keyboard_work_keyflag=$("#work_pwd_sign_edit").val();// 键盘工作密码标记
//			var key_flag=$("#other_pwd_sign_edit").val();// 转加密密钥标记
//			var mac_keyflag=$("#mac_pwd_sign_edit").val();// MAC密钥标记
//			var authorize_keyflag=$("#access_pwd_sign_edit").val();// 授权密钥标记
//
//			var data={machineNo:machineNo,machineType:machineType,machineCode:machineCode,unitCode:unitCode,tellerNo:tellerNo,ip:ip,keyboard_major_keyflag:keyboard_major_keyflag,keyboard_work_keyflag:keyboard_work_keyflag,key_flag:key_flag,mac_keyflag:mac_keyflag,authorize_keyflag:authorize_keyflag}
//			$.ajax({
//				url:"<%=basePath%>editDeployMachine.do",
//				cache:false,
//				type:"post",
//				datatype:'json',
//				data:data,
//				beforeSend:function(){},
//				success:function(data){
//				console.log(data);
//					if(data.success == true){
//						console.log("dfdfdfdfdf")
//						var tishi=new Tishi("保存成功","chenggong");
//						$("#table_tbody").html("");	
//					}else{
//						$(".bj_ip_info").show();
//					}
//				},
//				complete:function(){
//					statementGrid.datagrid('clearSelections')
//					statementGrid.datagrid('reload');
//				},
//				error:function(){var tishi=new Tishi("保存失败","shibai");console.log(machineCode)}
//			});
//};    
//// 设置机器类型
//   function setSelectMacType(){
//	   var data= {groupName:"machineType"};
//	   $.ajax({
//           url:"<%=basePath%>system/getDict.do",
//           cache:false,
//           type:"post",
//           datatype:'json',
//           data:data,
//           beforeSend:function(){},
//           success:function(data){
//           	if(data.success == true){	
//           		for(var i = 0;i<data.info.length;i++){
//           		    $("#jqlx").append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
//           		}
//           	}else{
//           		
//           	}
//           	console.log(data);
//           },
//           complete:function(){},
//           error:function(){}
//		})
//   }
//// 状态
//   function setSelectStatus(){
//	   $("#sbzt").append("<option value=''>请选择</option>");
//	   var data= {groupName:"status"};
//	   $.ajax({
//           url:"<%=basePath%>system/getDict.do",
//           cache:false,
//           type:"post",
//           datatype:'json',
//           data:data,
//           beforeSend:function(){},
//           success:function(data){
//           	if(data.success == true){	
//           		for(var i = 0;i<data.info.length;i++){
//           		    $("#sbzt").append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
//           		}
//           	}else{
//           		
//           	}
//           	console.log(data);
//           },
//           complete:function(){},
//           error:function(){}
//		})
//   }
//// 机构
//   function setSelectUnitCode(){
//	   $("#jgmc").append("<option value=''>请选择</option>");
//	   var data= {};
//	   $.ajax({
//           url:"<%=basePath%>system/findParentUnit.do",
//           cache:false,
//           type:"post",
//           datatype:'json',
//           data:data,
//           beforeSend:function(){},
//           success:function(data){
//        	   console.log("123456789");
//        	   console.log(data);
//           	if(data.success == true){	
//           		for(var i = 0;i<data.rows.length;i++){
//           		    $("#jgmc").append("<option value='"+data.rows[i].unitViewId.unitCode+"'>"+data.rows[i].unitName+"</option>");
//           		}
//           	}else{
//           		
//           	}
//           	console.log(data);
//           },
//           complete:function(){},
//           error:function(){}
//		})
//   }
//// 设置机器类型
//   function setPerType(par){
//	   var data= {groupName:"machineType"};
//	   $.ajax({
//           url:"<%=basePath%>system/getDict.do",
//           cache:false,
//           type:"post",
//           datatype:'json',
//           data:data,
//           beforeSend:function(){},
//           success:function(data){
//           	if(data.success == true){	
//           		for(var i = 0;i<data.info.length;i++){
//           		    $("#"+par).append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
//           		}
//           	}else{
//           		
//           	}
//           	console.log(data);
//           },
//           complete:function(){},
//           error:function(){}
//		})
//   }
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
// //测试投产
//	function touChan(status,machineNo){
//		if(status == 0 || status == 2){
//			if(status  == 0 || status ==2){
//				status = 1;
//			}else if(status == 1){
//				status = 2;
//			}
//			console.log(status)
//			$.ajax({
//				 url:"<%=basePath%>editDeployMachine2.do",
//				 cache:false,
//				 type:"post",
//				 datatype:'json',
//				 data:{status:status,machineNo:machineNo},
//				 beforeSend:function(){},
//				 success:function(data){
//					 console.log(data)
//					if(data.success == true){	
//						var tishi=new Tishi("投产成功","chenggong");
//						$(".reset_pwd").hide();
//					}else{
//						var tishi=new Tishi("投产失败","shibai");
//					}
//					console.log(data);
//				 },
//				 complete:function(){
//					statementGrid.datagrid('clearSelections')
//					statementGrid.datagrid('reload');
//				 },
//				 error:function(){var tishi=new Tishi("投产失败","shibai");}
//			});
//		}else{
//			if(status == 1){
//				status = 2;
//				$.ajax({
//				 url:"<%=basePath%>editDeployMachine2.do",
//				 cache:false,
//				 type:"post",
//				 datatype:'json',
//				 data:{status:status,machineNo:machineNo},
//				 beforeSend:function(){},
//				 success:function(data){
//					 console.log(data)
//					if(data.success == true){	
//						var tishi=new Tishi("下线成功","chenggong");
//						$(".reset_pwd").hide();
//					}else{
//						var tishi=new Tishi("下线失败","shibai");
//					}
//					console.log(data);
//				 },
//				 complete:function(){
//					statementGrid.datagrid('clearSelections')
//					statementGrid.datagrid('reload');
//				 },
//				 error:function(){var tishi=new Tishi("下线失败","shibai");}
//			});
//			}
//		}
//	};
//	$("#reset_pwd1_no").click(function(){
//	 $(".reset_pwd1").hide(); 
//		$(".mask").hide();
//	});
//	$("#reset_pwd1_ok").click(function(){
//		//var managePassword=$("#reset_pwd1").val();
//		if(status  == 0 || status ==2){
//			status = 1;
//		}else if(status == 1){
//			status = 2;
//		}
//		var nn = {status:status,machineNo:machineNo,remark:$("#reset_pwd1").val()};
//		console.log(status+"::wwwwwwwwwwwww");
//		console.log(nn);
//		$.ajax({
//			 url:"<%=basePath%>editDeployMachineRemark.do",
//			 cache:false,
//			 type:"post",
//			 datatype:'json',
//			 data:{status:status,machineNo:machineNo,remark:$("#reset_pwd1").val()},
//			 beforeSend:function(){},
//			 success:function(data){
//				if(data.success == true){	
//					var tishi=new Tishi("重置成功","chenggong");
//					$(".reset_pwd").hide();
//					selTable();
//				}else{
//					var tishi=new Tishi("重置失败","shibai");
//				}
//				console.log(data);
//			 },
//			 complete:function(){
//					statementGrid.datagrid('clearSelections')
//					statementGrid.datagrid('reload');
//			 },
//			 error:function(){var tishi=new Tishi("重置失败","shibai");}
//		})
//	})
//		$("#reset_pwd_no").click(function(){
//			$(".reset_pwdA").hide();
//			$(".mask").hide();
//		});
//		//单击获取事件
//		 function resetTr(){
//			 var row = $('#statementGrid').datagrid('getSelected'); 
//			 console.log(row)
//			 if (row) { 
//				 $(".reset_pwdA").show();
//				 $(".mask").show();
//				 $("#reset_pwd_ok").click(function(){
//					resetPwd(row.machineNo);
//				})
//			 } else{
//				 $(".mask").show();
//				 var tishi=new Tishi("请选择一条记录","tishi");
//			 }
//		 }
//		function resetPwd(machineNo){
//			var managePassword=$("#reset_pwd").val();
//			$.ajax({
//				 url:"<%=basePath%>editDeployMachine1.do",
//				 cache:false,
//				 type:"post",
//				 datatype:'json',
//				 data:{ManagePassword:managePassword,machineNo:machineNo},
//				 beforeSend:function(){},
//				 success:function(data){
//					if(data.success == true){	
//						var tishi=new Tishi("重置成功","chenggong");
//						$(".reset_pwd").hide();
//					}else{
//						var tishi=new Tishi("重置失败","tishi");
//					}
//					console.log(data);
//				 },
//				 complete:function(){
//					statementGrid.datagrid('clearSelections')
//					statementGrid.datagrid('reload');
//				 },
//				 error:function(){var tishi=new Tishi("重置失败","shibai");}
//			})
//		}
//		$("#button_resetpwd").click(function(){
//			resetTr();
//		})
</script>

