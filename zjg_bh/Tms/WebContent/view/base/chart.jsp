<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>		
	<div style="width:100%;height:100%; background:url('../../img/index_bg.png') no-repeat;background-size:88%;">	
		<div class="container" style="width:100%;">
			<div class="row" style="margin-top: 40px;">
				<div class="col-xs-4" align="center" id="chart" style="inline-height:310px;height:310px;"></div>
				<div class="col-xs-4" align="center" id="chart2" style="inline-height:310px;height:310px;"></div>
				<div class="col-xs-4" align="left" id="chart3" style="inline-height:310px;height:310px;"></div>
			</div>
		</div>	
	</div>
	<script>
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
	                var myChart3 = ec.init(document.getElementById("chart3"));
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
	    	                            x:'center' 
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
	    	                            x:'center' 
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
	           		
	           //按照机器的上报状态来统计数量 
	           		 $.ajax({
		        	        url:"<%=basePath%>queryDeployMachineRepStatus.do",
		        	        cache:false,
		        	        type:"post",
		        	        datatype:'json',
		        	        data:"",
		        	        beforeSend:function(){},
		        	        success:function(data){
		        	        	var username = [];
		        	        	for(var i=0;i<data.length;i++){
		        	        		username.push(data[i].name);
		        	        	}
		        	        	option = { 
		    	                        title : { 
		    	                            text: '终端启用情况', 
		    	                            subtext: '', 
		    	                            x:'center' 
		    	                        }, 
		    	                        tooltip : { 
		    	                            trigger: 'item', 
		    	                            formatter: "{c}台 ({d}%)" 
		    	                        }, 
		    	                        /* legend: { 
		    	                            orient : 'vertical', 
		    	                            x : 'left', 
		    	                            data:username 
		    	                        }, */ 
		    	                      
		    	                        calculable : true, 
		    	                        series : [ 
		    	                            { 
		    	                                name:'', 
		    	                                type:'pie', 
		    	                                radius : '55%', 
		    	                                center: ['50%', '60%'], 
		    	                                data:[{value:"100%", name:'暂无数据'}]
		    	                            } 
		    	                        ],
		    	                        color:['#f78c68','#4cc3a5','#1f8a70'] 
		    	                    };
		        	        	 option.series[0].data = data;
		           	        	 myChart3.setOption(option);
		        	        },
		        	        complete:function(){
		        	        	
		        	        },
		        	        error:function(){
		        	        	
		        	        }
		        	    });
	                      
	         }         
	       );           
	               
         
	</script>
