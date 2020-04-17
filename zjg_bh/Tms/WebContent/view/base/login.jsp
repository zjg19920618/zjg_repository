<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8,9,10"/>
    <link rel="stylesheet" type="text/css" href="../../css/login.css">
</head>
<body>
    <div class="login_main">
        <div class="container">
            <div class="login_left">
                <img src="../../img/common/login_logo.png"/>
            </div>
            <div class="login_right">
                <span class="login_error" id="login_error"></span>
                <form id="login_form" action="#" method="post">
                    <input type="text" class="login_txt" placeholder="用户名" onkeypress="if (event.keyCode == 13) keydown()" id="username" name="username" autofocus>
                    <input type="password" class="login_pwd" placeholder="密码" id="pwd" name="pwd" onkeypress="if (event.keyCode == 13) keydown()"/>
                    <a href="javascript:;"  id="login_btn" onclick="keydown()" class="login_btn">登&nbsp;&nbsp;&nbsp;录</a>
                </form>
            </div>
        </div>
    </div>
    <script src="../../js/jquery-1.11.3.min.js"></script>
    <script src="../../js/common.js"></script>
    <script>
        $("#login_form input[type='text']").blur(function(){
            if($(this).val()==""){
                $("#login_error").html("用户名不能为空！");
            }else{
             var reg=/^[0-9a-zA-Z]*$/;
             if(!reg.test($(this).val())){
                 $("#login_error").html("用户名格式不正确！")
             }
            }
        });
       function keydown(){
            if($("#login_form input[type='password']").val()==""){
                $("#login_error").html("请输入密码！");
            }else if($("#login_form input[type='text']").val()==""){
                $("#login_error").html("用户名不能为空！");
            }else{
            	var username = $("#username").val();// 编号
        		var pwd = $("#pwd").val();// 名称
        		var manuData={username:username,pwd:pwd}
                $.ajax({
                    url:"<%=basePath%>login.do",
                    cache:false,
                    type:"post",
                    datatype:'json',
                    data:manuData,
                    beforeSend:function(){},
                    success:function(data){                   	
                    	if(data.success== true){
                           // busGoto("");
                            location.href = "main.jsp";
        				}else{
                            $("#login_error").html("用户名或密码不正确！")}
                    },
                    complete:function(){},
                    error:function(){}
                })
            }
        };
        function busGoto(fileName)
		{
			$.get(fileName,{},function(res){
				$("body").html(res);
			});
		}
        $("#login_form input").focus(function(){
            $("#login_error").html("");
        })
    </script>
</body>
</html>