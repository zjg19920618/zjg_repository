<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
	
	<div class="firm_add_add">
        <form action="">
        <div class="firm_add_head">
            <span>预警处理</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
             <p class="beizhu"><span><b>*</b>处理结果：</span><textarea name="" id="proDesc" ></textarea></p>
        </div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add">保存</button>
            
        </div>
        </form>
    </div>
    
	<div class="com_nav">
		<table class="search_box">
			<tr>
				<td><span>机器名称：</span></td>
				<td><input type="text" id="machineName"></td>
				<td><span>机构名称：</span></td>
				<td><input type="text" id='unitName'></td>
				<td><span>处理状态：</span></td>
				<td>
					<select value="" id="proStatus" class='firmZT'>
	             		<option value="">请选择</option>
	                    <option value="1">未处理</option>
	                    <option value="2">已处理</option>
	                </select>
	                
				</td>
			</tr>
		</table>
<div class="newSonstruction">
	<button id="button_search">
		<span class="sarch_ico"></span>
		<span class="sarch">查询</span>
	</button>
	<button id="button_add_add">
		<span class="warning_deal"></span>
		<span class="sarch warning_text">预警处理</span>
	</button>
	
</div>
<div style="padding-top: 10px" > 
	<table id="statementGrid" style="width:100%; height:auto;max-height:321px;" ></table>
</div>		
   
</div>
<script type="text/javascript">
$(function(){
	onload()
	$('#button_search').click(function(){
		onload(selll())
	})
	var proId ;
    // 表格单选事件
   
	// 预警信息处理
    	    $("#button_add_add").on("click",function(){
    	    	//alert($('#proStatus').val())
    	    	
    	    		selectTr()
    	    	
    	    	
    	    	
    	    });
	//添加中的保存结束
	//关闭按钮
    $(".firm_add_head a").click(function(){
        $(".firm_add_add").hide();
        $(".mask").hide();
    })
	//取消按钮
    $("#close_add_add,#close_add_edit").click(function(event){
    	$('#button_add_add').attr("disabled", true); 
    	$('#button_add_add').css("background-color", '#6AA4C9');
        event.preventDefault();
        $(".firm_add_add").hide();
        $(".mask").hide();
    });
	
 	//保存处理结果
    $("#save_add_add").click(function(event){
    	event.preventDefault();
    	var row = $('#statementGrid').datagrid('getSelected');
    	var proDesc=$("#proDesc").val();//处理结果
    	var data={proDesc:proDesc,id:row['id']['id']}
    	$.ajax({
            url:"<%=basePath%>saveMacWarning.do",
            cache:false,
            type:"post",
            datatype:'json',
            data:data,
            beforeSend:function(){},
            success:function(data){
    			if(data.success==true){
    				onload();
    				var tishi=new Tishi("保存成功","chenggong");
    				$(".firm_add_add").hide();
    				selTable();
    			}else{
    				var tishi=new Tishi("保存失败","shibai");
    				$(".firm_add_add").hide();
    			}
            },
            complete:function(){
				statementGrid.datagrid('clearSelections')
				statementGrid.datagrid('reload');
            },
            error:function(){
    			var tishi=new Tishi("保存失败","shibai");
    		}
    	})
    });
})
function selectTr(){
	 var row = $('#statementGrid').datagrid('getSelected');
	
	 console.log(row)
     if (row) { 
    	 $(".mask").show();
	    	$(".table_menu").hide();
	        $(".firm_add_add").show();
    		
     } else{
    	 $(".mask").show();
    	 var tishi=new Tishi("请选择一条记录","tishi");
     }
 	
 }

function selll(){
	 page = 1;
	 rows = 10;
	 machineName=$("#machineName").val();
	 unitName=$("#unitName").val();
	 proStatus=$('#proStatus').val();
	 sel  = {page:page,rows:rows,machineName:machineName,unitName:unitName,proStatus:proStatus}
	return sel;
}
	
function onload(sel){
	var statementGrid;

	statementGrid = $('#statementGrid').datagrid({
		title : '',
		url : '<%=basePath%>findMacWarningViewList.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'unitCode',
		singleSelect: true,
		autoRowHeight:true,
		queryParams:sel,
		//sortName : 'payDate',
		//sortOrder : 'asc',
		fitColumns:true ,
		nowrap:false,
		rowStyler:function(index,row){
   			if (row['proStatus'] == '1'){
   				return 'color:red;';
   			}
           },
		columns : [ [ 
			{
				width : 20,
				field : 'ck',
				checkbox : true
			},
			{
				width : 100,
				title : '机器编号',
				field : 'machineNo',
				align : 'center',
				formatter : function(value, row,index) {
					value=row['id']['machineNo'] ;
					return value;
				}
			}
			,
			{
				width : 80,
				title : '机器名称',
				field : 'machineName',
				align : 'center',
			},
			{
				width : 100,
				title : '所属厂商',
				field : 'manuName',
				align : 'center',
			},
			{
				width : 100,
				title : '所属机构',
				field : 'unitName',
				align : 'center',
			},
			{
				width : 130,
				title : '预警时间',
				field : 'createDate',
				align : 'center',
				formatter : function(value, row,index) {
					return dateNew(value);
				}
			},
			{
				width : 60,
				title : '处理状态',
				field : 'proStatus',
				align : 'center',
				sortable : false,
				formatter : function(value, row,index) {
					value="";
					if(row['proStatus'] == '1'){
						value = '1-未处理';
					}else if(row['proStatus'] == '2'){
						value = '2-已处理';
					}
					return value;
				}
			},
		
			{
				width : 100,
				title : '处理描述',
				field : 'proDesc',
				align : 'center',
			},
			{
				width : 130,
				title : '处理时间',
				field : 'proDate',
				align : 'center',
				formatter : function(value, row,index) {
					return dateNew(value);
				}
			}
			
			
		] ],
		onClickRow: function(index, data) {
            var row = $('#statementGrid').datagrid('getSelected');
            var status = row['proStatus'];
            if(status == '2'){
            	$('#button_add_add').attr("disabled", true); 
            	$('#button_add_add').css("background-color", '#EEE5DE');
            	$('#button_add_add').css("border-color", '#EEE5DE');
            	$('#button_add_add').css("cursor", 'no-drop');
            }else{
            	$('#button_add_add').removeAttr("disabled"); 
            	$('#button_add_add').css("background-color", '#6AA4C9');
            	$('#button_add_add').css("border-color", '#6AA4C9');
            	$('#button_add_add').css("cursor", 'pointer');
            }
		}
		
	});
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

	
}
</script>
