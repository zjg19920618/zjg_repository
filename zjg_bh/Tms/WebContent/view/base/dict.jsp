<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div class="com_nav">
    <!--外设维护的添加与编辑-->
    <div class="firm_add">
        <form action="">
        <div class="firm_add_head">
            <span>添加厂商</span>
            <a href="javascript:;">×</a>
        </div>
        <div class="firm_add_content">
            <p><b>*</b><span>厂商编号：</span><input type="text" id="csbh"/>
            <b>*</b><span>厂商名称：</span><input type="text"/ id="csmc"></p>
             <p><b>*</b><span>联系人：</span><input type="text" id="lxr"/>
             <b>*</b><span>座机电话：</span><input type="text" id="zuoNum"/></p>
             <p><b>*</b><span>手机电话：</span><input type="text" id="phone"/>
             <b>*</b><span>厂商状态：</span><select value="" id="cszt">
                    <option value="">请选择</option>
                    <option value="1">正常</option>
                    <option value="0">废弃</option>
                </select></p>
             <p class="beizhu"><span>备注：</span><textarea name="" id="csbz" ></textarea></p>
        </div>
        <div>
            <button id="close_add">取消</button>
            <button id="save_add">保存</button>
            
        </div>
        </form>
    </div>
    <!--添加结束-->
    <div class="firm_add">
        <form action="">
        <div class="firm_add_head">
            <span>添加厂商</span>
            <a href="javascript:;">×</a>
        </div>
        <div class="firm_add_content">
            <p><b>*</b><span>厂商编号：</span><input type="text" id="csbh1"/>
            <b>*</b><span>厂商名称：</span><input type="text"/ id="csmc1"></p>
             <p><b>*</b><span>联系人：</span><input type="text" id="lxr1"/>
             <b>*</b><span>座机电话：</span><input type="text" id="zuoNum1"/></p>
             <p><b>*</b><span>手机电话：</span><input type="text" id="phone1"/>
             <b>*</b><span>厂商状态：</span><select value="" id="cszt1">
                    <option value="">请选择</option>
                    <option value="1">正常</option>
                    <option value="0">废弃</option>
                </select></p>
             <p class="beizhu"><span>备注：</span><textarea name="" id="csbz1" ></textarea></p>
        </div>
        <div>
            <button id="close_add1">取消</button>
            <button id="save_add1">保存</button>
            
        </div>
        </form>
    </div>
    <div class="nav_type" id="nav_type1">
        <form action="">
        <ul>
            <li><span>厂商名称 </span> <input type="text" id="csName"/></li>
            <li><span>厂商状态 </span>
                <select value="" class="firmZT" id="cszt">
                    <option value="">请选择</option>
                    <option value="1">正常</option>
                    <option value="0">废弃</option>
                </select>
            </li>
            <li class="button_sarch">
                <button id="button_search">
                    <span class="sarch_ico"></span>
                    <span class="sarch">查询</span>
                </button>
            </li>
            <li class="button_add">
                <button id="button_add">
                    <span class="add_ico"></span>
                    <span class="add">添加</span>
                </button>
            </li>
        </ul>
        </form>
    </div>
    <div class="newSonstruction">
        <span class="new_ico"></span>
        <span class="new_S">新建</span>
    </div>
    <div class="table_top">
					<ul>
						<li></li>
						<li>厂商编号</li>
						<li>厂商名称</li>
						<li>联系人</li>
						<li>座机电话</li>
						<li>手机电话</li>
						<li>厂商状态</li>
						<li>备注</li>
						<li>创建日期</li>
						<li>创建人</li>
						<li>操作</li>
					</ul>
				</div>
    <div class="com_table">
        <table id="table_content" cellspacing="0" cellpadding="0">
            <!--<thead class="table_head">-->
            <!--<tr>-->
                <!--<td></td>-->
                <!--<td>组件编号</td>-->
                <!--<td>组件编号</td>-->
                <!--<td>组件编号</td>-->
            <!--</tr>-->
            <!--</thead>-->
            <tbody id="table_tbody">
            </tbody>
        </table>

        <div class="pagination">
            <select name="" id="">
                <option>10</option>
                <option>20</option>
                <option>30</option>
                <option>40</option>
            </select>
            <span> < </span>
            <span> 1 </span>
            <span> 2 </span>
            <span> 3 </span>
            <span> 4 </span>
            <span> 5 </span>
            <span> > </span>
            <span>共<span>100</span>页</span>
            <span>显示到1到10页，共100条记录</span>
        </div>
        <div class="table_menu">
            <b class="sanjiao"></b>
            <ul>
                <li>
                    <b class="stop"></b>
                    <span class="litterJL">停用</span>
                </li>
                <li class="saveInfor1">
                    <b class="weihu "></b>
                    <span>终端维护</span>
                </li>
                <li class="saveInfor1">
                    <b class="xiufu"></b>
                    <span>人工修复</span>
                </li>
                <li class="saveInfor1">
                    <b class="wubao"></b>
                    <span>异常误报</span>
                </li>
                <li class="saveInfor1">
                    <b class="peizhi"></b>
                    <span>角色权限配置</span>
                </li>
            </ul>
        </div>
    </div>
</div>
<script>
    //选项卡部分
    $("#nav_type1").show();
   
    /*表格部分*/
    $("#table_tbody tr:odd").css("background","#fff9f8");
    $("#table_tbody tr:even").css("background","#ebf2f7");
    //悬停变色
    $("#table_content>tbody>tr").mouseover(function(){
        $(this).addClass("mouse_hover");
    })
    $("#table_content>tbody>tr").mouseout(function(){
        $(this).removeClass("mouse_hover");
    })
    $("#table_content tbody tr").on("click",function(e){//表格选中效果
        $(".click").removeClass("click");
//							$(this).parent().find("td").addClass("click");

    });
    $.ajax({
        url:"<%=basePath%>findManu.do",
        cache:false,
        type:"post",
        datatype:'json',
        data:{pages:1,rows:10},
        beforeSend:function(){},
        success:function(data){
        	//var data1 =$.parseJSON(data);
        	console.log(data);
        	for(var i = 0;i<data.rows.length;i++){
        		index+=i;
        		$("#table_tbody").append("<tr><td>"+i+"</td><td>"+data.rows[i].conPerson+"</td><td>"+data.rows[i].createDate+"</td><td>"+data.rows[i].creater+"</td><td>"+data.rows[i].manuCode+"</td><td>"+data.rows[i].manuDesc+"</td><td>"+data.rows[i].manuName+"</td><td><a class='edit'>编辑</a></td></tr>");
        	}
        },
        complete:function(){},
        error:function(){}
    });
    $("#button_search").click(function(event){
    	event.preventDefault();
    	var csName=$("#csName").val();
    	var cszt=$("#cszt").val();
    	console.log(cszt);
    	$.ajax({
            url:"<%=basePath%>findManu.do",
            cache:false,
            type:"post",
            datatype:'json',
            data:{pages:1,rows:10,manuName:csName,manuStatus:cszt},
            beforeSend:function(){},
            success:function(data){
            	//var data1 =$.parseJSON(data);
            	console.log(data);
            	$("#table_tbody").html("");
            	for(var i = 0;i<data.rows.length;i++){
            		index+=i;
            		$("#table_tbody").append("<tr><td>"+i+"</td><td>"+data.rows[i].conPerson+"</td><td>"+data.rows[i].createDate+"</td><td>"+data.rows[i].creater+"</td><td>"+data.rows[i].manuCode+"</td><td>"+data.rows[i].manuDesc+"</td><td>"+data.rows[i].manuName+"</td><td>"+"</tr><>");
            	}
            },
            complete:function(){},
            error:function(){}
        });
    })
    $("#button_add").click(function(event){
    	event.preventDefault();
    	$(".firm_add").css("display","block");
    })
    $("#save_add").click(function(event){
    	event.preventDefault();
    	var csbh=$("#csbh").val();
    	var csmc=$("#csmc").val();
    	var lxr=$("#lxr").val();
    	var zuoNum=$("#zuoNum").val();
    	var phone=$("#phone").val();
    	var cszt=$("#cszt").val();
    	var csbz=$("#csbz").val();
    	$.ajax({
            url:"<%=basePath%>saveManu.do",
            cache:false,
            type:"post",
            datatype:'json',
            data:{pages:1,rows:10,manuName:csmc,manuCode:csbh,conPerson:lxr,phone:phone,tel:zuoNum,manuStatus:cszt,manuDesc:csbz},
            beforeSend:function(){},
            success:function(data){
            	//var data1 =$.parseJSON(data);
            	console.log(data);
            	$(".firm_add").css("display","none")
            },
            complete:function(){},
            error:function(){}
        
    })
    })
    $("#close_add").click(function(event){
    	event.preventDefault();
    	$(".firm_add").css("display","none");
    })
    $(".edit").click(function(){
    	
    })
</script>
