<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!--添加结束-->
<div class="com_nav">
	<table class="search_box" style='margin-left:0;'>
		<tr>
			<td><span>分析类型：</span></td>
			<td>
				<select name="" id="fxlx" class="firmZT" value=''>
					<option value=''>请选择</option>
					<option value='1'>机构名称</option>
					<option value='2'>业务名称</option>
					<option value='3'>机器类型</option>
				</select>
			</td>
			<td><span>分析条件：</span></td>
			<td>
				<select name="" id="fen_xi_tj" class="firmZT">
					<option value=''>请选择</option>
				</select>
			</td>
			<td>开始时间：</td>
			<td><input id="ls_kssj" type="text" class="easyui-datebox"></td>
			<td>结束时间：</td>
			<td><input id="ls_jssj" type="text" class="easyui-datebox"></td>
		</tr>
	</table>

	<div id="refresh">
		<div class="newSonstruction">
			<button id="button_search">
                <span class="sarch">开始分析</span>
            </button>
		</div>
		<div class="container" style="">

			<div class="row" style="margin-top: 0px;">
				<div id="chart" style="width:800px;height:350px;"></div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	require.config({
		paths: {
			'echarts': '../../js/echarts/echarts',
			'echarts/chart/bar': '../../js/echarts/echarts',
			'echarts/chart/line': '../../js/echarts/echarts'
		}
	});

	// 使用 
	require(
		[
			'echarts',
			'echarts/chart/bar',
			'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载 
		],

		function AA(ec) {
			// 基于准备好的dom，初始化echarts图表 
			var myChart = ec.init(document.getElementById("chart"));
			option = {
			    title : {
			        text: '唐山银行终端设备分析图',
			        x:'center'

			    },
			    grid:{
			    	 x: 20,
			    	y: 80, 
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['使用次数','金额']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            data : []
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			 	
			    series : [
			        {
			            name:'',
			            type:'bar',
			            data:[],
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }
			        }
			        
			    ]
			};

			myChart.setOption(option);

		}
	);
</script>
<script>
	//声明一堆全局变量
	var unitCode = []; //存储unitCode传到后台
	var machineCodeValue = []; //存储machineCode传到后台
	var kstime, jstime, $unitType, $unitType1, data,machineCodeRemoval;
	$(function() {
			$("#ls_kssj").datebox({
				editable: false,
				formatter: formatDate,
				onSelect: function(date) {
					var ddd = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
					$("#ls_kssj").datebox('setValue', ddd);
				}

			})
			$("#ls_jssj").datebox({
				editable: false,
				formatter: formatDate,
				onSelect: function(date) {
					var ddd = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
					$("#ls_jssj").datebox('setValue', ddd);
				}

			})

			function formatDate(myDate) {
				var y = myDate.getFullYear();
				var m = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
				var d = myDate.getDate(); //获取当前日(1-31)
				if(myDate.getMonth() >= 1 && myDate.getMonth() <= 8) {
					m = "0" + m;
				} //获取完整的年份(4位,1970-????)
				if(m==1){
					m="0"+m;
				}
				if(myDate.getDate() >= 0 && myDate.getDate() <= 9) {
					d = "0" + d;
				}
				var currentdate = "" + y + m + d;
				return currentdate;
			}

			function getNowFormatDate() {
				var date = new Date();
				var seperator1 = "-";
				var year = date.getFullYear();
				var month = date.getMonth() + 2;
				var strDate = date.getDate();
				if(month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if(month==1){
					month="0"+month;
				}
				if(strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				var currentdate = year + seperator1 + month + seperator1 + strDate;
				return currentdate;
			}
			//下拉列表获取
			$('#fxlx').change(function() {
				var $value = $(this).val()
				$('#fen_xi_tj').empty() //清空
				if($value == '1') {
					
					$.ajax({
						url: "<%=basePath%>system/findParentUnit.do",
						cache: false,
						type: "post",
						datatype: 'json',
						beforeSend: function() {},
						success: function(data) {
							if(data.success == true) {
								console.log(data);
								for(var i = 0; i < data.rows.length; i++) {
									$("#fen_xi_tj").append("<option value=" + i + ">" + data.rows[i].unitName + "</option>");
									unitCode.push(data.rows[i].unitViewId.unitCode)
								}
								
							} else {}
						},
						complete: function() {},
						error: function() {}
					})
				} else if($value == '2') {
					
					$.ajax({
						url: "<%=basePath%>findFlowLists.do",
						cache: false,
						type: "post",
						datatype: 'json',
						beforeSend: function() {},
						success: function(data) {
							if(data.success == true) {
								
								var json = []
								for(var j = 0; j < data.rows.length; j++) {
									json.push(data.rows[j].projectName)
								}
								
								var b = json
								var c = b.unique1()
								
								for(var i = 0; i < c.length; i++) {
									$("#fen_xi_tj").append("<option>" + c[i] + "</option>");
								}
							} else {}
						},
						complete: function() {},
						error: function() {}
					})
				} else if($value == '3') {
					
					$.ajax({
						url: "<%=basePath%>findMachineList1.do",
						cache: false,
						type: "post",
						datatype: 'json',
						beforeSend: function() {},
						success: function(data) {
							if(data.success == true) {
							
								var json = []
								for(var j = 0; j < data.rows.length; j++) {
									json.push(data.rows[j].machineTypeName)
									machineCodeValue.push(data.rows[j].machineCode)
								}
								//去重
								var b = json
								var c = b.unique1()
								var e = machineCodeValue
								machineCodeRemoval = e.unique1()
								
								for(var i = 0; i < c.length; i++) {
									$("#fen_xi_tj").append("<option value=" + i + ">" + c[i] + "</option>");
								}
							} else {}
						},
						complete: function() {},
						error: function() {}
					})
				}
			})
			$('#button_search').click(function() {				
				kstime = $("#ls_kssj").datebox('getValue'); //获取开始时间
				jstime = $("#ls_jssj").datebox('getValue'); //获取结束时间	
				$unitType = $('#fen_xi_tj').val()
				$unitType1 = $('#fen_xi_tj option:selected').html()
				
				var analysisType = $('#fxlx').val() //获取分析类型
				if(analysisType == '1') {
					data = {
						unitCode: unitCode[$unitType],
						kstime: kstime,
						jstime: jstime
					}
				
					var interFaceValue = "<%=basePath%>queryDeployMachineType22.do"
					echart(interFaceValue); //调用统计图机构名称
				} else if(analysisType == '2') {
					data = {
						projectName: $unitType1,
						kstime: kstime,
						jstime: jstime
					}
					console.log(data)
					var interFaceValue = "<%=basePath%>findFlowLists1.do"
					echart1(interFaceValue); //调用统计图业务名称
				} else if(analysisType == '3') {
					data = {
						machineCode: machineCodeRemoval[$unitType],
						kstime: kstime,
						jstime: jstime
					}
									
					var interFaceValue = "<%=basePath%>queryDeployMachineType24.do"
					echart(interFaceValue); //调用统计图机器名称
				}

			})

		})
		//统计图表
		//interFace 接口
	function echart(interFace) {
		$.ajax({
			url: interFace,
			cache: false,
			type: "post",
			datatype: 'json',
			data: data,
			beforeSend: function(xmlHttp) {
				xmlHttp.setRequestHeader("If-Modified-Since", "0");
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
			},
			success: function(data) {
				console.log(data)
				var jsonName = []
				var jsonValue = []
				for(var x = 0; x < data.length; x++) {
					jsonName.push(data[x].name);
					jsonValue.push(data[x].value)
				}
				

					// 使用 
				require(
					[
						'echarts',
						'echarts/chart/bar',
						'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载 
					],
					function AA(ec) {
						// 基于准备好的dom，初始化echarts图表 
						var myChart = ec.init(document.getElementById("chart"));
						option = {
						    title : {
						        text: $unitType1 + '终端设备数据对比图'
						    },
						    tooltip : {
						        trigger: 'axis'
						    },
						    grid:{
						    	 x: 20,//x轴高度
						    	y: 80, //y轴与x轴的距离
						    },
						    legend: {
						        data:['使用次数']
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            dataView : {show: true, readOnly: false},
						            magicType : {show: true, type: ['line', 'bar']},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    xAxis : [
						        {
						            type : 'category',
						            data : jsonName
						        }
						    ],
						    yAxis : [
						    	{
						            type : 'value',
						            name : '次数',
						            axisLabel : {
						                formatter: '{value}'
						            }
						        }
						        
						    ],
						 // 网格
						    grid: {
						        x: 80,
						        y: 100,
						        x2: 80,
						        y2: 60,
						        // width: {totalWidth} - x - x2,
						        // height: {totalHeight} - y - y2,
						        backgroundColor: 'rgba(0,0,0,0)',
						        borderWidth: 1,
						        borderColor: '#ccc'
						    },
						    series : [
						        {
						            name:'使用次数',
						            type:'bar',
						            data:jsonValue,
						            markPoint : {
						                data : [
						                    {type : 'max', name: '最大值'},
						                    {type : 'min', name: '最小值'}
						                ]
						            },
						            barMaxWidth:'60',
						            markLine : {
						                data : [
						                    {type : 'average', name: '平均值'}
						                ]
						            }
						        }
						    ]
						};
						myChart.setOption(option);
					}
				);
			},
			complete: function() {},
			error: function() {}
		})

	}
//折线柱状图
function echart1(interFace) {
		$.ajax({
			url: interFace,
			cache: false,
			type: "post",
			datatype: 'json',
			data: data,
			beforeSend: function(xmlHttp) {
				xmlHttp.setRequestHeader("If-Modified-Since", "0");
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
			},
			success: function(data) {
				console.log(data.rows)
				var jsonName = [];
				var jsonValue = [];
				var totalValue=[];
				for(var x = 0; x < data.rows.length; x++) {
					jsonName.push(data.rows[x][1]);
					jsonValue.push(data.rows[x][3])
					totalValue.push(data.rows[x][4])
				}
			
					// 使用 
				require(
					[
						'echarts',
						'echarts/chart/bar',
						'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载 
					],
					function AA(ec) {
						// 基于准备好的dom，初始化echarts图表 
						var myChart = ec.init(document.getElementById("chart"));
						option = {
						    title : {
						        text:  $unitType1 + '终端设备数据对比图',
						        
						    },
						    grid:{
						    	 x: 20,
						    	y: 80, 
						    },
						    //color: ['#3398DB','#E43708'],
						    tooltip : {
						        trigger: 'axis'
						    },
						    legend: {
						        data:['使用次数','金额']
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            dataView : {show: true, readOnly: false},
						            magicType : {show: true, type: ['line', 'bar']},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						 // 网格
						    grid: {
						        x: 80,
						        y: 100,
						        x2: 80,
						        y2:60,
						        // width: {totalWidth} - x - x2,
						        // height: {totalHeight} - y - y2,
						        backgroundColor: 'rgba(0,0,0,0)',
						        borderWidth: 1,
						        borderColor: '#ccc'
						    },
						    calculable : true,
						    xAxis : [
						        {
						            type : 'category',
						            data : jsonName
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value',
						            name : '次数',
						            axisLabel : {
						                formatter: '{value}'
						            }
						        },
						        {
						            type : 'value',
						            name : '金额',
						            axisLabel : {
						                formatter: '{value}'
						            }
						        }
						    ],
						    series : [
						        {
						            name:'使用次数',
						            type:'bar',
						            data:jsonValue,
						            markPoint : {
						                data : [
						                    {type : 'max', name: '最大值'},
						                    {type : 'min', name: '最小值'}
						                ]
						            },
						            barMaxWidth:'60',
						            markLine : {
						                data : [
						                    {type : 'average', name: '平均值'}
						                ]
						            }
						        },
						        {
						            name:'金额',
						            type:'bar',
						            data:totalValue,
						            markPoint : {
						                data : [
						                    {type : 'max', name: '最大值'},
						                    {type : 'min', name: '最小值'}
						                ]
						            },
						            barMaxWidth:'60',
						            yAxisIndex: 1,
						            markLine : {
						                data : [
						                    {type : 'average', name : '平均值'}
						                ]
						            }
						        }
						    ]
						};
						myChart.setOption(option);
					}
				);
			},
			complete: function() {},
			error: function() {}
		})

	}
	function setSelectStatus() {
		$("#fen_xi_tj").append("<option value=''>请选择</option>");
		var $value = $(this).val()
			//var data= {groupName:"status"};
		$.ajax({
			url: "<%=basePath%>system/findParentUnit.do",
			cache: false,
			type: "post",
			datatype: 'json',
			//data:data,
			beforeSend: function() {},
			success: function(data) {
				if(data.success == true) {
					for(var i = 0; i < data.info.length; i++) {
						$("#fen_xi_tj").append("<option>" + data.info[i].UnitName + "</option>");
					}
				} else {}
				console.log(data);
			},
			complete: function() {},
			error: function() {}
		})
	}
	//数组去重
	Array.prototype.unique1 = function() {
		var n = [];
		for(var i = 0; i < this.length; i++) {
			if(n.indexOf(this[i]) == -1) {
				n.push(this[i])
			};
		}
		return n;
	}
</script>