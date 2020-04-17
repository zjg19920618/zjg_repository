<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String a = request.getScheme();
String b = request.getServerName();
String c = request.getServerPort()+"";
String d = path;
%>
<div class="secondaryMenu">
    <ul class="nav" >
        
    </ul>
</div>
<div class="com_nav" id="waishe">
   
</div>
<div class="loader" style="display:none;">
	    <div class="loader-inner line-spin-fade-loader">
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	      <div></div>
	    </div>
  	</div>
<script>

    //选项卡部分
     $("#nav_type1").show();
	$.ajax({
        url:"<%=basePath%>getMenu.do",
        cache:false,
        type:"post",
        datatype:'json',
        data:{parentId:menuId},
        beforeSend:function(){},
        success:function(data){
        	//var data1 =$.parseJSON(data);
        	console.log(data.info);
        	for(var i = 0;i<data.info.length;i++){
        		$(".nav").append("<li>"+data.info[i].roleMenuViewId.menuName+"</li>");
        		$(".nav li:eq(0)").addClass("activeBackgroundRgba");
        	}
        	busGoto("../"+data.info[0].roleMenuViewId.menuPath);
        	console.log(data.info[0].roleMenuViewId.menuPath);
        	$(".nav li").on("click",function(){
        		$(".nav li").parent().find("li").removeClass("activeBackgroundRgba");
    			//$(this).css({"background":"url(../../img/common/TSOS.png) no-repeat","color":"#fff"});
    			$(this).addClass("activeBackgroundRgba");
     	       busGoto("../"+data.info[$(this).index()].roleMenuViewId.menuPath);
    		});
    		
        },
        complete:function(){},
        error:function(){}
    });
  //公共跳转页面
	function busGoto(fileName)
	{
		$.get(fileName,{},function(res){
			$("#waishe").html(res);
		});
	}
</script>
