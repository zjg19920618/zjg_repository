<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!-- 	详情 -->
<div class="details_box">
	<form action="">
		<div class="user_add_head user_add_headGrq">
			<span>详情</span>
			<a href="javascript:;"></a>
		</div>
		<div class="details_box_content" style="display:block;">
			<table>
				<thead>
					<tr>
						<td>外设名称</td>
						<td>状态</td>
						<td>描述</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>打印机</td>
						<td>正常</td>
						<td>正常</td>
					</tr>
					<tr>
						<td>密码键盘</td>
						<td>异常</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<!-- 结束 -->
<div class="imgBlockFile_img " id="imgBlockFile_table" style="top: 35%;">
	<div class="user_add_head user_add_headGrq1">
		<span style="display:inline-block;margin-left:20px;">数据统计</span>
		<a href="javascript:;"></a>
	</div>
	<p style="margin-left:39%;font-size:18px;margin-top:6px;">机器异常统计表</p>
	<div class="search_box" style="margin-left:-2px;">
		<p class="dataSonstruction" style="padding-left:22px;">
			<span>开始时间：</span>
			<input id="ls_kssjq" type="text" class="easyui-datebox">
			<span>结束时间：</span>
			<input id="ls_jssjq" type="text" class="easyui-datebox">
			<button id="button_search22" style="height:29px;margin: -4px 135px 20px 0;">
							     <span class="sarch_ico sarch_icoqq"></span>
							     <span class="sarch sarchwer">查询</span>
							 </button>
		</p>
	</div>
	<div class="content content_statistics">

		<table id="tablebeizhu" border="1" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<td>时间</td>
					<td>异常备注</td>
					<td>编辑</td>
				</tr>
			</thead>

			<tbody id="tablebeizhuContent">

			</tbody>
		</table>

	</div>

	<div style="margin-top:5px;">
		<button id="imgDelectFile_grq">关闭</button>
	</div>
</div>
<div class="imgBlockFile_img" id="beizhuInformation" style="left: 25%;top: 10%;">
	<div class="user_add_head user_add_headGrq2">
		<span style="display:inline-block;margin-left:20px;">备注：</span>
		<a href="javascript:;"></a>
	</div>
	<div class="content content_statistics">

		<textarea id="textareaValue"></textarea>

	</div>

	<div style="margin-top:5px;">
		<button id="textareaBtn">保存</button>
	</div>
</div>
<div class="imgBlockFile_img" id="beizhuInformation1" style="left: 50%;top: 50%;margin-left: -350px;margin-top: -210px;">
	<div class="user_add_head beizhuInformationwe">
		<span style="display:inline-block;margin-left:20px;">备注：</span>
		<a href="javascript:;"></a>
	</div>
	<div class="content content_statistics" style="height: 217px;">

		<textarea id="textareaValue1"></textarea>
		<div style="margin-top:5px;">
			<button id="textareaBtn1">保存</button>
		</div>
	</div>

</div>
<!-- <div class="imgBlockFile_img button_analysis" style="top: 33%;">
	       <div class="user_add_head user_add_headd">
	           <span>异常统计图：</span>
	           <a href="javascript:;"></a>
	       </div>
	        <div class="content content_statistics" style=" height: 300px;">
	        		<p style="margin-left:39%;font-size:18px;"><span id="dateSuiJi"></span>年机器异常统计图</p>
		        	<div class="search_box" style="margin-left:-2px;">
		          		<p class="dataSonstruction" style="padding-left:22px;">
		          			<span>开始时间：</span>
			                <input id="ls_kssjq" type="text" class="easyui-datebox">
			                <span>结束时间：</span>
			                <input id="ls_jssjq" type="text" class="easyui-datebox">
							<button id="button_search22" style="height:29px;margin: -4px 146px 0 0;">
					             <span class="sarch_ico sarch_icoqq"></span>
					             <span class="sarch sarchwer">查询</span>
					        </button>
		          		</p>
			             
					</div>
		      
		       		
					<div id="informationChart" style="width:750px;height:218px;"></div>
	        </div>
			 <div style="margin-top:5px;">
	            <button id="offOpup">关闭</button>
        	</div>
    </div> -->
<div class="com_nav">
	<table class="search_box">
		<tr>
			<td><span>机器编号：</span></td>
			<td><input type="text" id='machineNo'></td>
			<td><span>机器名称：</span></td>
			<td><input type="text" id='machineName'></td>
			<td><span>机器类型：</span></td>
			<td>
				<select value="" id="machineTypeName" class='firmZT'>

				</select>
			</td>
		</tr>

		<tr>
			<td><span>机构名称：</span></td>
			<td><input type="text" id='unitName'></td>
			<td><span>所属厂商：</span></td>
			<td><input type="text" id='manuName'></td>
			<td><span>运行状态：</span></td>
			<td>
				<select value="" class='firmZT' id="repStatus">
					<option value="">请选择</option>
					<option value="1">正常</option>
					<option value="2">异常</option>
				</select>
			</td>
		</tr>
	</table>
	<div class="newSonstruction">
		<button id="button_search">
		<span class="sarch_ico"></span>
		<span class="sarch">查询</span>
	</button>
		<button id="button_statistics" class="button_statistics">
		<span class="unusual_deal"></span>
		<span class="sarch unusual_deal_text">异常处理</span>
	</button>
		<!-- <button id="button_analysis">
		<span class="unusual_analysis"></span>
		<span class="sarch unusual_analysis_text">异常分析</span>
	</button> -->
	</div>
	<div style="padding-top: 10px">
		<table id="statementGrid" style="width:100%; height:auto;max-height:321px;"></table>
	</div>
	<script type="text/javascript">
		$(function() {
			var repDescValue;
			var warDescValue;
			onload();
			setSelcted();
			$('#button_search').click(function() {
				onload(selll())
			});
		});
		// 设置查询条件中的下拉框值 
		function setSelcted() {
			setSelectMacType();
		}
		// 设置机器类型
		function setSelectMacType() {
			$("#machineTypeName").append("<option value=''>请选择</option>");
			var data = {
				groupName: "machineType"
			};
			$.ajax({
				url: "<%=basePath%>system/getDict.do",
				cache: false,
				type: "post",
				datatype: 'json',
				data: data,
				beforeSend: function() {},
				success: function(data) {
					if(data.success == true) {
						for(var i = 0; i < data.info.length; i++) {
							$("#machineTypeName").append("<option value='" + data.info[i].valueName + "'>" + data.info[i].valueDesc + "</option>");
						}
					} else {

					}
					
				},
				complete: function() {},
				error: function() {}
			})
		}

		function selll() {
			page = 1;
			rows = 10;
			machineNo = $("#machineNo").val();
			machineName = $("#machineName").val();
			manuName = $('#manuName').val();
			machineTypeName = $('#machineTypeName').val();
			if(machineTypeName == '01') {
				machineTypeName = '填单机'
			} else {
				machineTypeName = '发卡机'
			}
			machineTypeName1 = $('#machineTypeName').val();
			unitName = $('#unitName').val();
			repStatus = $('#repStatus').val();
			sel = {
				page: page,
				rows: rows,

				machineNo: machineNo,
				machineName: machineName,
				manuName: manuName,
				machineTypeName: machineTypeName1,
				unitName: unitName,
				repStatus: repStatus
			}
		
			return sel;
		}
		var row1;

		function onload(sel) {
			var statementGrid;

			statementGrid = $('#statementGrid').datagrid({
				title: '',
				url: '<%=basePath%>findDeployMachineList1.do',
				striped: true,
				rownumbers: true,
				pagination: true,
				idField: 'unitCode',
				queryParams: sel,
				singleSelect: true,
				autoRowHeight: true,
				//sortName : 'payDate',
				//sortOrder : 'asc',
				fitColumns: true,
				nowrap: false,
				rowStyler:function(index,row){
		   			if (row['repStatus'] == '2'){
		   				return 'color:red;';
		   			}
		           },
				/* onClickRow:function(value,row,index){
					row1 = row;
					}, */
				frozenColumns: [
					[{
							width: '20',
							field: 'ck',
							checkbox: true
						},

					]
				],
				columns: [
					[
						/*{
							width : '20',
							field : 'ck',
							checkbox : true
						},*/
						{
							width: 100,
							title: '机器编号',
							field: 'machineNo',
							align: 'center'

						}, {
							width: 100,
							title: '机器型号',
							field: 'machineCode',
							align: 'center',
						}, {
							width: 100,
							title: '机器类型',
							field: 'machineType',
							align: 'center',
							formatter: function(value, row, index) {
								value = "";

								if(row['machineType'] == '01') {
									value = '填单机';
								} else if(row['machineType'] == '02') {
									value = '发卡机';
								}
								return value;
							}
						}, {
							width: 100,
							title: '机器名称',
							field: 'machineName',
							align: 'center',

						}, {
							width: 100,
							title: '所属厂商',
							field: 'manuName',
							align: 'center',
						}, {
							width: 100,
							title: '所属机构',
							field: 'unitName',
							align: 'center',
						}, {
							width: 150,
							title: '最近一次上报时间',
							field: 'repTime',
							align: 'center',
							formatter : function(value, row,index) {
								return dateNew(value);
							}
						}, {
							width: 100,
							title: '异常次数',
							field: 'errorCount',
							align: 'center',
							formatter: function(value, row, index) {
								
								return "<a  href='javascript:;' onclick='easyuiOn(\"" + row.machineNo + "\")'>" + value + "</a>";
							},

						},

						{
							width: 100,
							title: '运行状态',
							field: 'repStatus',
							align: 'center',
							sortable: false,
							formatter: function(value, row, index) {
								value = "";

								if(row['repStatus'] == '1') {
									value = '1-正常';
								} else if(row['repStatus'] == '2') {
									value = '2-异常';
								}
								return value;
							},
							 
					           
						}, {
							width: 100,
							title: '异常描述',
							field: 'repDesc',
							align: 'center',
						},

					]
				],
				onClickRow: function(index, data) {
					var row = $('#statementGrid').datagrid('getSelected');
					repDescValue=row.repDesc
           			if(row.repStatus=='1'){
           				$('#button_statistics').attr("disabled","disabled")
           				$('#button_statistics').css("background","#ddd")
           				$('#button_statistics').css("cursor","not-allowed")
           				$('#button_statistics').css("border-color","#ddd")
           			}else if(row.repStatus=='2'){
           				$('#button_statistics').removeAttr("disabled","disabled")
           				$('#button_statistics').css("background","#6AA4C9")
           				$('#button_statistics').css("cursor","pointer")
           				$('#button_statistics').css("border-color","#6AA4C9")
           			}           			
				}

			});
			//设置分页控件   
			var p = $('#statementGrid').datagrid('getPager');
			$(p).pagination({
				//pageSize: pageSize,//每页显示的记录条数，默认为10   
				pageList: [10, 20, 30, 40, 50], //可以设置每页记录条数的列表   
				beforePageText: '第', //页数文本框前显示的汉字   
				afterPageText: '页    共 {pages} 页',
				displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
				onBeforeRefresh: function() {
					$(this).pagination('loading');
					$(this).pagination('loaded');
				}
			});

		}
		var $this; //获取索引 
		var reportDate; //上传日期
		function sqlAnalysis(row) {
			seachTime(row,"", "","<%=basePath%>/findExceptionList.do");							
		}
		$("#button_search22").click(function(event) {//button_search22
			event.preventDefault();
			var row = $('#statementGrid').datagrid('getSelected');
			var lskssjq = $("#ls_kssjq").datebox('getValue'); //获取开始时间
			var lsjssjq = $("#ls_jssjq").datebox('getValue'); //获取结束时间
			seachTime(row.machineNo,lskssjq, lsjssjq,"<%=basePath%>/findeStatusEveryMouth.do");
			//return false;
		});
		function seachTime(row,lskssjq, lsjssjq,pathC) {
			$("#tablebeizhuContent,#informationChart").empty();
			var machineNo = row; //机器编号
			var data = {
				machineNo: machineNo,
				startTime: lskssjq,
				endTime: lsjssjq
			};
			$.ajax({
				url: pathC,
				cache: false,
				type: "post",
				datatype: 'json',
				data: data,
				beforeSend: function() {},
				success: function(data) {
					if(data != "") {
						var dateTime = []; //柱状图横坐标时间
						var numberTimes = []; //柱状图横坐标次数
						var splt = {
							date1: [],
							date2: [],
							date3: [],
							date4: [],
							date5: [],
							date6: [],
							date7: [],
							date8: [],
							date9: [],
							date10: [],
							date11: [],
							date12: []
						}; //异常次数相加
						var daterelut = []; //去重之后盛放的时间结果
						$("#dateSuiJi").html(data[0].createDate.substring(0, 4));
						//console.log(data);
					//	var data = $.parseJSON(data);
						//console.log(data[0].createDate);
						for(var i = 0; i < data.length; i++) {
							$("#tablebeizhuContent").append("<tr><td class='beizhuDater'>" + dateNew(data[i].createDate) + "</td><td class='beizhuInvalue'>" + data[i].warDesc + "</td><td><a class='shujuBianJi' href='javascript:;'>编辑</a></td></tr>");
							/* if(dateNew1(data[i].createDate) == 01) {
								splt.date1.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 02) {
								splt.date2.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 03) {
								splt.date3.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 04) {
								splt.date4.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 05) {
								splt.date5.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 06) {
								splt.date6.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 07) {
								splt.date7.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 08) {
								splt.date8.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 09) {
								splt.date9.push(data[i].warDesc);
							} else if(dateNew1(data[i].createDate) == 10) {
								splt.date10.push(data[i].warDesc);
							} else if(dateNew1(data[i].reportDate) == 11) {
								splt.date11.push(data[i].warDesc);
							} else if(dateNew1(data[i].reportDate) == 12) {
								splt.date12.push(data[i].warDesc);
							}
							dateTime.unshift(dateNew1(data[i].reportDate) + "月"); */

						}
						if(splt.date1.length > 0) {
							numberTimes.push(splt.date1.length);
						}
						if(splt.date2.length > 0) {
							numberTimes.push(splt.date2.length);
						}
						if(splt.date3.length > 0) {
							numberTimes.push(splt.date3.length);
						}
						if(splt.date4.length > 0) {
							numberTimes.push(splt.date4.length);
						}
						if(splt.date5.length > 0) {
							numberTimes.push(splt.date5.length);
						}
						if(splt.date6.length > 0) {
							numberTimes.push(splt.date6.length);
						}
						if(splt.date7.length > 0) {
							numberTimes.push(splt.date7.length);
						}
						if(splt.date8.length > 0) {
							numberTimes.push(splt.date8.length);
						}
						if(splt.date9.length > 0) {
							numberTimes.push(splt.date9.length);
						}
						if(splt.date10.length > 0) {
							numberTimes.push(splt.date10.length);
						}
						if(splt.date11.length > 0) {
							numberTimes.push(splt.date11.length);
						}
						if(splt.date12.length > 0) {
							numberTimes.push(splt.date12.length);
						}
						//数组去重
						for(var j = 0; j < dateTime.length; j++) {
							if(dateTime[j] != dateTime[j - 1]) {
								daterelut.push(dateTime[j]);
							}
						}
						//console.log(numberTimes);
						function dateNew1(date) {
							var nn2 = date.substring(4, 6); //月
							return nn2;
						}

						//添加备注信息
						
						$("#tablebeizhuContent tr").click(function(event) {
							event.preventDefault();
							$this = $(this).index();
							reportDate = data[$this].createDate;
						});
						$("#tablebeizhuContent .shujuBianJi").on("click", function() {
							event.preventDefault();
							$this = $(this).index();
							warDescValue = data[$this].warDesc;
							$("#imgBlockFile_table").hide();
							$("#beizhuInformation").show();
							$('#textareaValue').html(warDescValue)
							aoff = false;
						});
						//柱状图
						/* require.config({ 
						    paths:{  
						        'echarts' : '../../js/echarts/echarts', 
						        'echarts/chart/bar' : '../../js/echarts/echarts',
						        'echarts/chart/line' : '../../js/echarts/echarts'
						    } 
						}); */
						//使用 
						/* require( 
						    [ 
						         'echarts',  
						         'echarts/chart/bar',  
						         'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载 
						    ], 
						    
						    function histogram(ec) { 
						           // 基于准备好的dom，初始化echarts图表 
						           var myChart = ec.init(document.getElementById("informationChart"));
						           option = {
							        		   
							        		    tooltip : {
							        		        trigger: 'axis',
							        		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
							        		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
							        		        }
							        		    },
							        		    grid: {
							        		        left: '3%',
							        		        right: '4%',
							        		        bottom: '3%',
							        		        containLabel: true
							        		    },
							        		    xAxis : [
							        		        {
							        		            type : 'category',
							        		            data :daterelut,//["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],//次数高度,
							        		            axisTick: {
							        		                alignWithLabel: true
							        		            }
							        		        }
							        		    ],
							        		    yAxis : [ 
							        		        {
							        		            type : 'value',//y轴固定数值
							        		            spliArea:{show:true},
							        		            min:0,
							        		            max:200,
							        		            splitNumber:10,
							        		            scale:false,
							        		        }
							        		    ],
							        		    series : [
							        		        {
							        		            name:'异常次数',
							        		            type:'bar',
							        		          /*   barWidth : 30,//柱图宽度 */
						//data:numberTimes,//次数高度
						/*         }
	            		        		    ]
	            		        		};
	            		        	
	            	 	        	 myChart.setOption(option);
	            	      
	            	    }         
	            	  );  */
					}
					
				},
				complete: function() {},
				error: function() {}
			})
		}
		//数据统计
		function easyuiOn(row) {		
			if(row) {
				sqlAnalysis(row);
				$("#imgBlockFile_table,.mask").show();
			} else {
				var tishi = new Tishi("请选择一条记录", "tishi");
			}
		}
		var aoff;
		$(".button_statistics").click(function() {
			var row = $('#statementGrid').datagrid('getSelected');
			if(row) {
				$("#beizhuInformation1,.mask").show();
				console.log(row.machineNo);
				$.ajax({
					url: "<%=basePath%>/findExceptionList.do",
					cache: false,
					type: "post",
					datatype: 'json',
					data: {machineNo:row.machineNo},
					beforeSend: function() {},
					success: function(data) {
						$('#textareaValue1').empty();
						$('#textareaValue1').val(data[0].warDesc);
						}
					}); 
				
				aoff = true;
			} else {
				var tishi = new Tishi("请选择一条记录", "tishi");
			}

		});
		//数据统计图
		$("#button_analysis").click(function() {
			var row = $('#statementGrid').datagrid('getSelected');
			if(row) {
				$(".button_analysis,.mask").show();
				sqlAnalysis(row);
			} else {
				var tishi = new Tishi("请选择一条记录", "tishi");
			}
		});

		//初始化easyui日期框
		$("#ls_kssjq").datebox({
			editable: false,
			formatter: formatDate, //formatDate,
			onSelect: function(date) {
				var ddd = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
				$("#ls_kssjq").datebox('setValue', ddd);
			}
		});
		$("#ls_jssjq").datebox({
			editable: false,
			formatter: formatDate, //formatDate,
			onSelect: function(date) {
				var ddd = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
				$("#ls_jssjq").datebox('setValue', ddd);
			}
		});

		function formatDate(myDate) {
			var y = myDate.getFullYear();
			var m = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
			var d = myDate.getDate(); //获取当前日(1-31)
			if(myDate.getMonth() >= 1 && myDate.getMonth() <= 8) {
				m = "0" + m;
			} //获取完整的年份(4位,1970-????)
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
			if(strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = year + seperator1 + month + seperator1 + strDate;
			return currentdate;
		}

		//备注信息确定/取消
		$("#textareaBtn1").click(function() {
			$("#beizhuInformation1").hide();
			if(aoff == false) {
				$("#imgBlockFile_table").show();
			} else {
				$(".mask").hide();
			}
			var reportDate = $("#textareaValue1").val()
			$(".beizhuInvalue").eq($this).html($("#textareaValue").val());
			//	$("#textareaValue1").val("");
			var row = $('#statementGrid').datagrid('getSelected');
			var data = {
				machineNo: row.machineNo,
				statusDesc: $(".beizhuInvalue").eq($this).html(),
				repDesc: reportDate
			};
			$.ajax({
				url: "<%=basePath%>/updateMachineStatus.do",
				cache: false,
				type: "post",
				datatype: 'json',
				data: data,
				beforeSend: function() {},
				success: function(data) {					
					//$("#textareaValue1").val("");
					onload();
				},
				complete: function() {},
				error: function() {}
			})

		});
		$("#textareaBtn").click(function() {
			$("#beizhuInformation").hide();
			if(aoff == false) {
				$("#imgBlockFile_table").show();
			} else {
				$(".mask").hide();
			}
			var reportDate1 = $("#textareaValue").val()
			$(".beizhuInvalue").eq($this).html($("#textareaValue").val());
			//$("#textareaValue").val("");						
			var row = $('#statementGrid').datagrid('getSelected');
			var data = {
				machineNo: row.machineNo,
				createDate:reportDate,
				repDesc: $(".beizhuInvalue").eq($this).html()
				
			};
			$.ajax({
				url: "<%=basePath%>/updateMachineStatus1.do",
				cache: false,
				type: "post",
				datatype: 'json',
				data: data,
				beforeSend: function() {},
				success: function(data) {					
					//$("#textareaValue").val("");
					//onload();
				},
				complete: function() {},
				error: function() {}
			})

		});
		$(".user_add_headGrq2 a,.beizhuInformationwe a").click(function() {
			$("#beizhuInformation").hide();
			$("#imgBlockFile_table").show();
			//$("#textareaValue,#textareaValue1").val("");
		});

		//取消按钮
		$(".user_add_headGrq a,#imgDelectFile_grq,.user_add_headGrq1 a,.user_add_headd a,#offOpup,.beizhuInformationwe a").click(function() {
			$("#imgBlockFile_table,.mask,.button_analysis,#beizhuInformation1").hide();
		});
	</script>