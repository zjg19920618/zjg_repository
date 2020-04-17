<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 外设配置 -->
	    <div class="firm_add_add user_add_box">
	        <form  action="">
	        <div class="user_add_head">
	            <span>添加外设</span>
	            <a href="javascript:;"></a>
	        </div>
	        <div class="user_add_content" style="display:block;">
				<div class="user_in">
					<ul id="menuID" class="ztree">
					</ul>
				</div>
			</div>
	        <div class="Tsbtn">
	            <button  class="clearclass" id="per_quit">取消</button>
	            <button  class="clearclass" id="per_save">保存</button>
	        </div>
	        </form>
    	</div>
    	 <div class="firm_add_edit">
	        <form action="">
		        <div class="firm_add_head">
		            <span>添加机器</span>
		            <a href="javascript:;"></a>
		        </div>
		        <div class="firm_add_content">
		            <p><span><b>*</b>机器型号：</span><input type="text" id="jqxh_edit"/>
		            <span><b>*</b>机器类型：</span><select value="" id="jqlx_edit">
		                    <option value="">请选择</option>
		                    <option value="1">正常</option>
		                    <option value="0">废弃</option>
		                </select></p>
		             <p><span><b>*</b>机器名称：</span><input type="text" id="jqName_edit"/>
		             <span><b>*</b>所属厂商：</span><select value="" id="sscs_edit">
		                    <option value="">请选择</option>
		                    <option value="1">正常</option>
		                    <option value="0">废弃</option>
		                </select></p>
		             <p>
		             	<span><b>*</b>所属区县：</span><select class="validate[required]" value="" id="ssqx_edit">
             				<option value="">请选择</option>
                		</select>
		             <b>&nbsp;&nbsp;</b><span>备注：</span><input type="text" id="beizhu_edit"/>
		        </div>
		        <div>
		            <button id="close_add_edit">取消</button>
		            <button id="save_add_edit">保存</button>
		            
		        </div>
	        </form>
    </div>
    <!--图片上传  -->
    <div class="firmFile">
    	<form enctype="multipart/form-data" >
	        <div class="firm_add_head">
	            <span style="display:inline-block;margin-left:26px;">图片上传</span>
	            <a href="javascript:;"></a>
	        </div>
	        <div class="firm_add_content">
	        	<div class="imgfile_content">
	        		<input type="file" id="file" name="img1" onchange="document.getElementById('img_val').value=this.value" />
	        		<input type="text" class="img_opacity" id="img_val"  />
	        		<button id="img_submit">浏览</button>
	        		<p>图片大小不超过1M,支持GIF、JPG、PNG格式</p>
	        		<div class="common_info ImgErrorInfor" style="display:none"><b class="info_caret"></b><span id="ImgErrorInfor"></span></div>
	        	</div>
	        	<div id="fileType"></div>
				<div id="fileSize"></div>
		        <!-- <div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;">
					<div style="margin-bottom:40px">
						<input id="fileEaeyui"  class="easyui-filebox" labelPosition="top" data-options="prompt:'请选择上传文件'" style="width:100%">
					</div>
				</div> -->
	        <!--   <div class="kv-main">//图片预览ie9不支持
	            <br>
	            <form enctype="multipart/form-data">
	                
	                <div class="form-group">
	                    <input id="file-1" type="file" multiple class="file" data-overwrite-initial="false"  data-min-file-count="1">
	                </div>
	                
	            </form>
	        </div> -->
	        </div>
	        </form>
	        <div class="btnleftImg" style="height:32px;">
	            <button id="saveIMG" style="margin-right:20px;">保存</button>
	            <button id="close_seveIMG" style="">取消</button>
	        </div>
        
    </div>
    <!-- 查看图片  -->
    <div class="imgBlockFile_img" id="ImgLcok">
	       <div class="user_add_head">
	           <span style='margin-left:20px;'>查看图片</span>
	           <a id="lockImgFileClear" href="javascript:;"></a>
	       </div>
	       <div class="content" id="imgMover">
	       		
				<div class="banner">
				  <ul class="img" id="imgClaock">
				    <!--<li><a href="javascript:;"><img src="img/image1.jpg" alt="第1张图片"></a></li>
				    <li><a href="javascript:;"><img src="img/image2.jpg" alt="第2张图片"></a></li>
				    <li><a href="javascript:;"><img src="img/image3.jpg" alt="第3张图片"></a></li>
				    <li><a href="javascript:;"><img src="img/image4.jpg" alt="第4张图片"></a></li>
				    <li><a href="javascript:;"><img src="img/image5.jpg" alt="第5张图片"></a></li>-->
				  </ul>
				  <ul class="num"></ul> 
				  <div class="btn12">
				    <span class="prev"></span>
				    <span class="next"></span>
				  </div>
	           </div>
				
			</div>
			<div class="waicneg" style="width:471px;margin-left:10px;padding-left: 30px;overflow: hidden;">
				<div class="imgDelectFile" id="imgDelectFile" style="height:100px;">
				
				</div>
				<p class="nextLeftImg">  </p>
				<p class="prevRightImg">  </p>
			</div>
			
			<!-- <span class="imgFileDelectafter"></span> -->
			 <div style="margin-top:5px;">
	            <button id="imgDelectFile_grq">取消</button>
	            <button id="imgDelectFile_delect" >删除</button>
        	</div>
    </div>
    <div class="com_nav">
    	
        <table class="search_box">
	        <tr>
	            <td><span>机器名称：</span></td> 
	            <td><input type="text" id="machineName_sel"/> </td>
	           
	            <td><span>机器类型：</span></td>
	            <td>
	                <select value="" class="firmZT" id="machineType_sel">
	                </select>
	            </td>
	            <td><span>所属厂商：</span></td>
	            <td>
	            	<select class='firmZT' id="manuName_sel"/>
	            	</select>
	            </td>
	        </tr>
        </table>
   		 <!--编辑开始-->
    <div class="firm_add_add firm_add_addgqq">
        <form id="form1" action="">
        <div class="firm_add_head">
            <span id="form_title"></span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
			<p>
				<span><b>*</b>机器型号：</span><input class="validate[required]" type="text" id="machineCode"/>
            	<span><b>*</b>机器类型：</span><select class="validate[required]" id="machineType"/>
            </p>
            <div class="common_info username_info"><b class="info_caret"></b>* 机器型号已存在!</div>
            <p>
            	<span><b>*</b>机器名称：</span><input   class="validate[required]" type="text"  id="machineName"/>
             	<span><b>*</b>所属厂商：</span><select   class="validate[required]" id="manuCode"/>
             </p>
             <div class="common_info email_info_top "><b class="info_caret"></b>* 机器名称已存在!</div>
             <p>
             	<!-- <span><b>*</b>所属区县：</span><select class="validate[required]" value="" id="ssqx_add" >
             		<option value="">请选择</option>
                </select> -->
             	<p class="beizhu"><span>备注：</span><textarea   name="" id="machineDesc" ></textarea></p>
			 </p>
             
		</div>
        <div>
            <button id="close_add_add">取消</button>
            <button id="save_add_add">保存</button>
        </div>
        </form>
    </div>
     <div class="firm_add_add firm_add_addgqq1">
        <form id="form2" action="">
        <div class="firm_add_head">
            <span>添加机器</span>
            <a href="javascript:;"></a>
        </div>
        <div class="firm_add_content">
			<p>
				<span><b>*</b>机器型号：</span><input class="validate[required]" type="text" id="machineCode1"/>
            	<span><b>*</b>机器类型：</span><select class="validate[required]" id="machineTypeGrq"/>
            </p>
            <div class="common_info username_info"><b class="info_caret"></b>* 机器型号已存在!</div>
            <p>
            	<span><b>*</b>机器名称：</span><input   class="validate[required]" type="text"  id="machineName1"/>
             	<span><b>*</b>所属厂商：</span><select   class="validate[required]" id="manuCodeGrq"/>
             </p>
             <div class="common_info email_info_top "><b class="info_caret"></b>* 机器名称已存在!</div>
             <p>
             	<!-- <span><b>*</b>所属区县：</span><select class="validate[required]" value="" id="ssqx_add" >
             		<option value="">请选择</option>
                </select> -->
             	<p class="beizhu"><span>备注：</span><textarea   name="" id="machineDesc1" ></textarea></p>
			 </p>
             
		</div>
        <div>
            <button id="close_add_addGrq">取消</button>
            <button id="save_add_addGrq">保存</button>
        </div>
        </form>
    </div>
		<div id="refresh">
		<div class="newSonstruction">
			
	         <button id="button_search">
	              <span class="sarch_ico"></span>
	              <span class="sarch">查询</span>
	         </button>
			<button id="button_add_add">
				<span class="new_ico"></span>
				<span class="sarch">添加</span>
			</button>
			<button class="edit">
				<span class="edit_icon"></span>
				<span class="sarch">编辑</span>
			</button>
			<button class='delect'>
				<span class="delete_icon"></span>
				<span class="sarch">删除</span>
			</button>	
			<button class='pang_m1' id="imgtupian">
					<span class="img_manage"></span>
					<span class="sarch" style="right:0;">图片上传</span>
			</button>		
			<button class='pri_m saveInfor1'>
				<span class="prim_icon"></span>
				<span class="sarch" style="right:0;">外设管理</span>
			</button>
			<button class='pri_m2' id="ImgBtnlock">
				<span class="watch_img"></span>
				<span class="sarch" style="right:0;">查看图片</span>
			</button>	
			<button id="button_excel">
                <span class="export_ico"></span>
                <span class="sarch">导出</span>
            </button>	
		</div>
		<table id="statementGrid" style="width:100%; height:auto;max-height:321px;"></table>
		<div class="table_box">
		</div>
	</div>	
    </div>
    <div class="firmcontainer" style="display:none;">
	   	<div class="firm_add_head">
            <span id="form_title"></span>
            <a href="javascript:;"></a>
        </div>
		
			<div class="grid_8">
				<div id="sliderA" class="slider">
					<img id="img1"  />
					<img id="img2"  />
					<img id="img3"  />
				
				</div>
			</div>
	</div>
<script>
var page = 1;
var rows = 10;
var sel;
var tree;
var statementGrid;

$(function(){
	onlds();
	setSelcted();
	selManuCode('#manuName_sel');
	selManuCodeAdd();//添加菜单设置
	/* setSelectMacType();
	setPerType("jglx_add");// 初始化 编辑框中的外设类型
	setPerType("sjjg_add1")// 初始化 查询条件中的外设类型*/
	valid(); 
	//监听
	$('#machineCode,#machineName').blur(function(){
		$('.common_info').hide()
	});
	 <%-- initFileInput("#file-1", "saveMacMachinePic.do"); --%>
	for(var i=0;i<Tms_bankInfo.length;i++){
		$("#ssqx_add,#ssqx_edit").append("<option value='"+Tms_bankInfo[i].city+"'>"+Tms_bankInfo[i].city+"</option>")
	}
});

<%-- $("#file-1").fileinput({
	language: 'zh', //设置语言
	iframe:true,
   <%--  uploadUrl: "<%=basePath%>saveMacMachinePic.do", //上传的地址 --%>
   /*  allowedFileExtensions : ['jpg', 'png','gif'],
    overwriteInitial: false,
    showUpload: false, //是否显示上传按钮
    showCaption: false,//是否显示标题
    browseClass: "btn btn-primary", //按钮样式             
    previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
    maxFileSize: 1000,
    maxFilesNum: 10,
    msgFilesTooMany:1,//"选择上传的文件数量({n}) 超过允许的最大数值{m}"
    //allowedFileTypes: ['image', 'video', 'flash'],
    slugCallback: function(filename) {
    	
        return filename.replace('(', '_').replace(']', '_');
    }
}); --%> */
//判断图片类型
var Pic;
$("#file").change(function () {
	$(".ImgErrorInfor").hide();
    var filepath = $("input[name='img1']").val();
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
    	$("#ImgErrorInfor").html("* 图片格式不正确");//图片限于bmp,png,gif,jpeg,jpg格式
    	 $(".ImgErrorInfor").show();
       /*  $("#fileType").text("")
        $("#fileSize").text(""); */
        return false;
    } else { //$("#fileType").text(ext)
    	}
    var file_size = 0;
    if ( /msie/.test(navigator.userAgent.toLowerCase())) {//$.browser.msie判断浏览器内核
        var img = new Image();
        img.src = filepath;
        while (true) {
            if (img.fileSize > 0) {
                if (img.fileSize > 10240) {
                   // alert("图片不大于10MB。");
                   $(".ImgErrorInfor").show();
                	$("#ImgErrorInfor").html("* 上传的图片大小不能超过10M！");
                	return;
                } else {
                    var num03 = img.fileSize / 1024;
                    num04 = num03.toFixed(2)
                   // $("#fileSize").text(num04 + "KB");
                }
                break;
            }
        }
    } else {
        file_size = this.files[0].size;
        var size = file_size / 1024;
        if (size > 10240) {
        	$(".ImgErrorInfor").show();
        	$("#ImgErrorInfor").html("* 上传的图片大小不能超过10M！");
           // alert("上传的图片大小不能超过10M！");
        	return;
        } else {
            var num01 = file_size / 1024;
            num02 = num01.toFixed(2);
            //$("#fileSize").text(num02 + " KB");
        }
    }
});
	var oneClick = true;
    $("#saveIMG").click(function(){
    	if(oneClick == true){
    		oneClick = false;
    		var row = $('#statementGrid').datagrid('getSelected'); 
        	var machineCode = row.machineCode;
        	var fileObj = document.getElementById("file").files[0]; // 获取文件对象
            var FileController = "<%=basePath%>saveMacMachinePic.do";                    // 接收上传文件的后台地址
            // FormData 对象
            var form = new FormData();
            form.append("Pic", fileObj);                        //上传参数
            form.append("file", fileObj);
            form.append("machineCode", machineCode);// 文件对象
            // XMLHttpRequest 对象
            var xhr = new XMLHttpRequest();
            xhr.open("post", FileController, true);
            /*  xhr.onload = function () {
            };  */
            xhr.onreadystatechange = function(data){
            	var data = $.parseJSON(xhr.response);
            	 if(data.errmsg == undefined){
            		 $(".firmFile,.mask").hide();
         	    	var tishi=new Tishi("上传成功","chenggong");
         	    	oneClick = true;
         	    	clearInterval(timer);
         	    	$(".stamp,#Carousel,#imgDelectFile").empty();
         	    	
            	}else{
            		oneClick = true;
            		 $(".firmFile,.mask").hide();
            		var tishi=new Tishi(data.errmsg,"shibai");
            	} 
            }
            xhr.send(form); 
    	}
        return false;
    });
    



//上传图片
$("#imgtupian").click(function(){
	var row = $('#statementGrid').datagrid('getSelected'); 
	 if(row){
		 $("#file,#img_val").val("");
		 $(".firmFile,.mask").show();
	 }else{
		 var tishi=new Tishi("请选择一条记录","tishi");
	 }
});
var pathImg; //图片路径
var timer = null;
var offTimerU = true;
var uoWidth = 0;
//查看图片
$("#ImgBtnlock").click(function(){
			 var row = $('#statementGrid').datagrid('getSelected'); 
			 if(row){
				 $("#ImgLcok,.mask").show();
				 $(".nextLeftImg,.prevRightImg").off();
				 var data = row.machineCode;
				 $.ajax({
				        url:"<%=basePath%>findMacMachinePicList.do",
				        cache:false,
				        type:"post",
				        datatype:'json',
				        data:{machineCode:data},
				        beforeSend:function(){},
				        success:function(data){
				        	var html = "";
				        	var mg;
				        	var i = 0;
				        	 var firstimg = null;
				        	 $(".num,#imgClaock,#imgDelectFile").empty();
				        	for( arry in data){
				        		html += "<img src='../.."+data[arry].pic+"'/>";
				        		$("#imgClaock").append("<li><a href='javascript:;'><img src='../.."+data[arry].pic+"' ></a></li>");
				        		$(".stamp").append("<span></span>");
				        	}
				        	
				        	$("#imgDelectFile").append(html);
				        	$(".waicneg").mouseover(function(){
				        		$(".nextLeftImg,.prevRightImg").show();
				        	});
				        	$(".waicneg").mouseout(function(){
				        		$(".nextLeftImg,.prevRightImg").hide();
				        	});
				        	$(".imgDelectFile").css("width",$("#imgDelectFile img").length*110);
				        	uoWidth = 0;
				        	var widthD = $("#imgDelectFile img").length;
				        	$("#imgDelectFile").css("margin-left",uoWidth*(-115)+"px");
				        	ImgLeftRight(widthD);//左右切换
				        	var onff = true;
				        	$("#imgDelectFile img").click(function(){
				        		var leftImg = 0;
				        		$("#imgDelectFile img").parent().find("img").css("border","0px solid #fff");
				        		$(this).css({
				        			"border-radius":"6px",
				        			"border":"2px solid #82B200"
				        		});
								var path = ($(this).attr("src")).split("/");
			     			pathImg = path[path.length-1];
				        		
				        	});
					        	lunbotu(firstimg,i);
				            $("#imgDelectFile_grq,#lockImgFileClear").click(function(){
				            	//$(".num,#imgClaock,#imgDelectFile").html("");
				            	$("#ImgLcok").hide();
				            	clearInterval(timer);
				            });
				        },
				        complete:function(XHR, textStatus){
				        	
				        },
				        error:function(){}
						});
				 }else{
					 var tishi=new Tishi("请选择一条记录","tishi");
				 }
	return false;
});
function ImgLeftRight(widthD){
	$(".nextLeftImg").click(function(){
		  if(widthD > 4){
			  uoWidth++;
			  console.log("3333");
			  //总-margin-left的值可以知道是否是最后一张
			 if(parseInt(widthD-4)*-115 > uoWidth*(-115)){
	        	uoWidth = 0;
        	    $("#imgDelectFile").css("margin-left",uoWidth*(-115)+"px");
        	 } 
			 $("#imgDelectFile").css("margin-left",uoWidth*(-115)+"px");//默认往左
    	} 
		  return false;
	});
	$(".prevRightImg").click(function(){
		if(widthD>4){
    		if(parseInt( $("#imgDelectFile").css('marginLeft')) != 0){
    			uoWidth--;
	        	$("#imgDelectFile").css("margin-left",uoWidth*(-115)+"px");	
        	}else{
        		uoWidth = parseInt(widthD)-4;
				$("#imgDelectFile").css("margin-left",uoWidth*(-115)+"px");
        	}
		}
		 return false;
	});
}
function lunbotu(firstimg,i){
	
    for (var j = 0; j < $('.img li').length; j++) {  //创建圆点
      $('.num').append('<li></li>')
    }
    $('.num li').first().addClass('active'); //给第一个圆点添加样式
  
    firstimg=$('.img li').first().clone(); //复制第一张图片
    $('.img').append(firstimg).width($('.img li').length*500); //将第一张图片放到最后一张图片后，设置ul的宽度为图片张数*图片宽度
  
  
    // 下一个按钮
    $('.next').click(function(){
      i++;
      if (i==$('.img li').length) {
        i=1; //这里不是i=0
        $('.img').css({left:0}); //保证无缝轮播，设置left值
      };
  
      $('.img').stop().animate({left:-i*500},300);
      if (i==$('.img li').length-1) {   //设置小圆点指示
        $('.num li').eq(0).addClass('active').siblings().removeClass('active');
      }else{
        $('.num li').eq(i).addClass('active').siblings().removeClass('active');
      }
        
    })
  
    // 上一个按钮
    $('.prev').click(function(){
      i--;
      if (i==-1) {
        i=$('.img li').length-2;
        $('.img').css({left:-($('.img li').length-1)*500});
      }
      $('.img').stop().animate({left:-i*500},300);
      $('.num li').eq(i).addClass('active').siblings().removeClass('active');
    })
  
    //设置按钮的显示和隐藏
    $('.banner').hover(function(){
      $('.btn12').show();
    },function(){
      $('.btn12').hide();
    })
  
    //鼠标划入圆点
    $('.num li').mouseover(function(){
      var _index=$(this).index();
      $('.img').stop().animate({left:-_index*500},150);
      $('.num li').eq(_index).addClass('active').siblings().removeClass('active');
    })
  
    //定时器自动播放
   timer=setInterval(function(){
      i++;
      if (i==$('.img li').length) {
        i=1;
        $('.img').css({left:0});
      };
  
      $('.img').stop().animate({left:-i*500},300);
      if (i==$('.img li').length-1) { 
        $('.num li').eq(0).addClass('active').siblings().removeClass('active');
      }else{
        $('.num li').eq(i).addClass('active').siblings().removeClass('active');
      }
    },2000)
  
    //鼠标移入，暂停自动播放，移出，开始自动播放
    $('#imgClaock,#imgMover .btn12').hover(function(){ 
      clearInterval(timer);
    },function(){
    	clearInterval(timer);
    	timer=setInterval(function(){
      i++;
      if (i==$('.img li').length) {
        i=1;
        $('.img').css({left:0});
      };
  		
      $('.img').stop().animate({left:-i*500},300);
      if (i==$('.img li').length-1) { 
        $('.num li').eq(0).addClass('active').siblings().removeClass('active');
      }else{
        $('.num li').eq(i).addClass('active').siblings().removeClass('active');
      }
    },2000)
    })
}
//图片删除
$("#imgDelectFile_delect").click(function(){
	clearInterval(timer);
	var row = $('#statementGrid').datagrid('getSelected');
	var machineCode = row.machineCode;
	var data = {Pic:pathImg,machineCode:machineCode};
	$("#ImgLcok,.mask").hide();
	if(pathImg == undefined){
		 var tishi=new Tishi("请选择一张图片","tishi");
		 return;
	}
	
	 $.ajax({
	        url:"<%=basePath%>delMacMachinePic.do",
	        cache:false,
	        type:"post",
	        datatype:'json',
	        data:data,
	        beforeSend:function(){},
	        success:function(data){
	        	$(".num,#imgClaock,#imgDelectFile").html("");
	        	$(".imgFileDelectafter").hide();
	        	var tishi=new Tishi("删除成功","chenggong");
	        },
	        complete:function(XHR, textStatus){
	        	 statementGrid.datagrid('clearSelections');
				 statementGrid.datagrid('reload');
	        },
	        error:function(){
	        	
	        }
			});
});
function onlds(sel){
	statementGrid = $('#statementGrid').datagrid({
		url : '<%=basePath%>findMachineList.do',
		striped : true,
		rownumbers : true,
		pagination : true,
		idField : 'payId',
		autoRowHeight:true,
		fitColumns:true,
		queryParams:sel,
		pageSize:10,
		loadMsg:'数据加载中,请稍后...',
		pageList:[10,20,30,40,50], 
		nowrap:false,
		singleSelect: true, //是否单选
		columns : [ [ 
			{width : '20',field : 'ck',checkbox : true},
			{title:'机器型号',field:"machineCode",align:"center",width:70,sortable : false},
			{title:'机器类型',field:"machineTypeName",align:"center",width:70,sortable : false},
			{title:'机器名称',field:"machineName",align:"center",width:70,sortable : false},
			{
				width : 150,
				title : 'districtCounty',
				field : 'districtCounty',
				align : 'center',
				hidden:true
			},
			{title:'所属厂商',field:"manuCode",formatter : function(value, row,index) {
				
				return row['manu']['manuName'];
			},align:"center",width:70,sortable : false},
			{title:'备注',field:"machineDesc",align:"center",width:70,sortable : false}
			/* {title:'机器图片',field:"creater",align:"center",width:70,sortable : true} */
		] ]
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
//查询按钮事件结束
$("#button_search").click(function(event){
	onlds(selll());
});
//获取查询条件
function selll(){
	var machineName=$("#machineName_sel").val();
	var machineType=$("#machineType_sel").val();
	var manuName=$("#manuName_sel option:selected").text();
	if(manuName=='请选择'){
		manuName='';
	}
	var sel  = {page:page,rows:rows,machineName:machineName,machineType:machineType,manuName:manuName}
	return sel;
}
//设置查询条件中的下拉框值 
function setSelcted(){
	setSelectMacType("machineType_sel");
	setSelectMacType("machineType");
	setSelectMacType("machineTypeGrq");
	selManuCode("#manuCode");
}
//取消按钮
$("#close_add_add,#close_add_edit,.firm_add_head a,#imgDelectFile_grq,.user_add_head a,#per_quit,#close_seveIMG").click(function(event){
    $(".firm_add_add,.common_info,.firmFile,#ImgLcok,.imgFileDelectafter").hide();
	$(".mask,.ImgErrorInfor").hide();
	$('form').validationEngine('hideAll');
	clearInterval(timer);
	$(".stamp,#Carousel,#imgDelectFile").empty();
	return false;
});
//编辑
$(".edit").on("click",function(){
	 var row = $('#statementGrid').datagrid('getSelected'); 
	 $(".mask").show();
     if (row) {
    	var periType;
         $(".firm_add_addgqq").show();
         $('.user_add_box').hide()
         $("#form_title").text("编辑");
         $("#machineCode").attr("disabled",true).css("cursor","no-drop");
      	 // 表格单选事件
         $("#machineCode").val(row.machineCode);//机器型号
   	 	 $("#machineType").val(row.machineType);//机器类型
     	 $("#machineName").val(row.machineName);//机器名称
     	 $("#manuCode").val(row['manu']['manuCode']);//所属厂商
     	 $("#machineDesc").val(row.machineDesc);//备注
     	 $("#ssqx_edit").val(row.districtCounty);
         flag = "edit";
     }else{
    	 var tishi=new Tishi("请选择一条记录","tishi");
     }
		
});
// 设置机器类型
function setSelectMacType(sel){
	   $("#"+sel).append("<option value=''>请选择</option>");
	   var data= {groupName:"machineType"};
	   $.ajax({
        url:"<%=basePath%>system/getDict.do",
        cache:false,
        type:"post",
        datatype:'json',
        data:data,
        beforeSend:function(){},
        success:function(data){
        	if(data.success == true){	
        		for(var i = 0;i<data.info.length;i++){
        		    $("#"+sel).append("<option value='"+data.info[i].valueName+"'>"+data.info[i].valueDesc+"</option>");
        		}
        	}else{
        		
        	}
        	
        },
        complete:function(XHR, textStatus){
        	 statementGrid.datagrid('clearSelections');
			 statementGrid.datagrid('reload');
        },
        error:function(){}
		})
}
// 设置所属厂商下拉框
function selManuCode(manuCodeId){
	   $(manuCodeId).append("<option value=''>请选择</option>");
	   var data= {};
	   $.ajax({
         url:"<%=basePath%>findManuList.do",
         cache:false,
         type:"post",
         datatype:'json',
         data:data,
         beforeSend:function(){},
         success:function(data){
        	 console.log(data);
         	if(data.success == true){	
         		for(var i = 0;i<data.rows.length;i++){
         		  $(manuCodeId).append("<option value='"+data.rows[i].manuCode+"'>"+data.rows[i].manuName+"</option>");
         		}
         	}else{
         		
         	}
         	
         },
         complete:function(XHR, textStatus){
        	 statementGrid.datagrid('clearSelections');
			 statementGrid.datagrid('reload');
         },
         error:function(){}
		})
 }
//设置添加所属厂商下拉框
function selManuCodeAdd(){
	  $("#manuCodeGrq").append("<option value=''>请选择</option>");
	   var data= {};
	   $.ajax({
         url:"<%=basePath%>findManuList.do",
         cache:false,
         type:"post",
         datatype:'json',
         data:data,
         beforeSend:function(){},
         success:function(data){
        	 console.log(data);
         	if(data.success == true){	
         		for(var i = 0;i<data.rows.length;i++){
         			 if(data.rows[i].manuStatus == 1){
         				 $("#manuCodeGrq").append("<option value='"+data.rows[i].manuCode+"'>"+data.rows[i].manuName+"</option>");
         			} 
         		}
         	}
         },
         complete:function(XHR, textStatus){
        	 statementGrid.datagrid('clearSelections');
			 statementGrid.datagrid('reload');
         },
         error:function(){}
		})
 }
function valid(){
	$('#form1').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				subAddjiqi();
				$(".username_info").hide();	
				$(".email_info_top").hide();
			}
		}
	});
	$('#form2').validationEngine('attach', {
		relative: true,
		overflownDIV:"#divOverflown",
		promptPosition:"bottomLeft",
		maxErrorsPerField:1,
		onValidationComplete:function(form,status){
			if(status){
				subAddjiqi1();
				$(".username_info").hide();	
				$(".email_info_top").hide();
			}
		}
	});
}
//编辑机器
function subAddjiqi(){
	var machineCode = $("#machineCode").val();//机器型号
    var machineType = $("#machineType").val();//机器类型
    var machineName = $("#machineName").val();//机器名称
    var manuCode = $("#manuCode").val();//所属厂商
    var machineDesc =  $("#machineDesc").val();//备注
    var cityS=$("#ssqx_add").val();		
	var url = "";
	if(flag =="edit"){
		url = "<%=basePath%>editMachine.do";
	}else{
		url = "<%=basePath%>saveMachine.do";
	}
	var data={cityS:cityS,machineCode:machineCode,machineType:machineType,machineName:machineName,manuCode:manuCode,machineDesc:machineDesc}
	$.ajax({
        url:url,
        cache:false,
        type:"post",
        datatype:'json',
        data:data,
        beforeSend:function(){},
        success:function(data){
        	if(data.success == true){	
        		// 保存成功后隐藏
        		$(".firm_add_add").css("display","none");
				var tishi=new Tishi("保存成功","chenggong");
        		// 刷新表格
        		onlds();
        	}else if(data.errmsg == 01){
				$(".username_info").show();
			}else{
				$(".email_info_top").show();	
			}
        },
        complete:function(XHR, textStatus){
        	statementGrid.datagrid('clearSelections');
			statementGrid.datagrid('reload');
        },
        error:function(){				
			var tishi=new Tishi("保存失败","shibai");
			$(".firm_add_add").hide();
		}
	})
};
//添加机器
function subAddjiqi1(){
	var machineCode = $("#machineCode1").val();//机器型号
    var machineType = $("#machineTypeGrq").val();//机器类型
    var machineName = $("#machineName1").val();//机器名称
    var manuCode = $("#manuCodeGrq").val();//所属厂商
    var machineDesc =  $("#machineDesc1").val();//备注
    var cityS=$("#ssqx_add1").val();	
   
	var url = "";
	if(flag =="edit"){
		url = "<%=basePath%>editMachine.do";
	}else{
		url = "<%=basePath%>saveMachine.do";
	}
	var data={cityS:cityS,machineCode:machineCode,machineType:machineType,machineName:machineName,manuCode:manuCode,machineDesc:machineDesc}
	 console.log(data);
	$.ajax({
        url:url,
        cache:false,
        type:"post",
        datatype:'json',
        data:data,
        beforeSend:function(){},
        success:function(data){
        	if(data.success == true){	
        		// 保存成功后隐藏
        		$(".firm_add_add").css("display","none");
				var tishi=new Tishi("保存成功","chenggong");
        		// 刷新表格
        		onlds();
        	}else if(data.errmsg == 01){
				$(".username_info").show();
			}else{
				$(".email_info_top").show();	
			}
        },
        complete:function(XHR, textStatus){
        	statementGrid.datagrid('clearSelections');
			statementGrid.datagrid('reload');
        },
        error:function(){				
			var tishi=new Tishi("保存失败","shibai");
			$(".firm_add_add").hide();
		}
	})
};
//添加
$("#button_add_add").click(function(event){
	clearForm();//清空表单值 
	$("#form_title").text("添加");
	$("#machineCode").removeAttr("disabled").css("cursor","");
	flag = "add";
	//clearForm();
	$(".firm_add_addgqq1").show();
	$(".mask").show();
	$('.user_add_box').hide();
	$("#machineCode1,#machineTypeGrq,#machineName1,#manuCodeGrq,#machineDesc1,#ssqx_add1").val("");//机器型号
});
$("#close_add_addGrq").click(function(){
	$(".firm_add_addgqq1,.mask").hide();
	return false;
});
//	删除
$(".delect").click(function(){
	$(".mask").show();
	 var row = $('#statementGrid').datagrid('getSelected'); 
	 var machineCode;
   if (row) { 
	   machineCode = row.machineCode;
	   var pop=new Pop("删除","remind_ok","tishi");
		$("#remind_ok").on("click",function(){
			 $.ajax({
				 url:"<%=basePath%>delMachine.do",
				 cache:false,
				 type:"post",
				 datatype:'json',
				 data:{machineCode:machineCode},
				 beforeSend:function(){},
				 success:function(data){
					if(data.success==true){
						var tishi=new Tishi("删除成功","chenggong");
						onlds(sel);
					}else{
						var tishi=new Tishi(data.errmsg,"shibai");
					}
				 },
				 complete:function(XHR, textStatus){
					 statementGrid.datagrid('clearSelections')
					 statementGrid.datagrid('reload');
				 },
				 error:function(){
					var tishi=new Tishi(data.errmsg,"shibai");
				 }
			})
		});
   }else{
	   var tishi=new Tishi("请选择一条记录","tishi");
   }
	
});

//	显示外设
$(".pri_m").on("click",function(){
	$('.user_add_box').show()
	$(".mask").show();
	 var row = $('#statementGrid').datagrid('getSelected'); 
	 var machineCode;
	  if (row) { 
		  machineCode = row.machineCode;
		  $(".user_add_box").show();
	        $.ajax({
		        url:"<%=basePath%>findMacPer.do",
		        cache:false,
		        type:"post",
		        datatype:'json',
		        data: {machineCode:machineCode},
		        beforeSend:function(){},
		        success:function(data){
		        	var zTree;
		        	var demoIframe;
		        	var setting = {
		        		check: {//点击事件
		        				enable: true
		        			},
		        		view: {
		        			dblClickExpand: true,
		        			showLine: true,
		        			selectedMulti: true
		        		},
		        		data: {
		        			simpleData: {
		        				enable:true,
		        				idKey: "id",
		        				parentIdKey: "parentId",
		        				rootPId: 0
		        			}
		        		},
		        		callback: {
		        			beforeClick: function(treeId, treeNode) {
		        				var zTree = $.fn.zTree.getZTreeObj("tree");
		        			}
		        		}
		        	}; 
		        	
	    	    	tree = $.fn.zTree.init($("#menuID"), setting, data.treeList);
		        }
		}); 
	  }else{
		  $('.user_add_box').hide()
		  var tishi=new Tishi("请选择一条记录","tishi");
		  
	  }
      
});
//外设配置 ---保存
$("#per_save").click(function(){
	var row = $('#statementGrid').datagrid('getSelected'); 
	 var machineCode;
    $(".mask").show();
    var nodes=tree.getCheckedNodes(true);
    if (row) { 
    	machineCode = row.machineCode;
	  }
    if(nodes){
    	 var  per = "";
    	    for(var i=0;i<nodes.length;i++){
    	    	if(i == 0){
    	    		per +=nodes[i].id;
    	    	}else if(i != nodes.length){
    	    		per +=","+nodes[i].id;
    	    	}else{
    	    		per +=nodes[i].id;
    	    	}
    	    	
    	    }
    	    var data = {per:per,machineCode:machineCode};
    	    console.log(data);
    	    $.ajax({
    	        url:"<%=basePath%>saveMacPer.do",
    	        cache:false,
    	        type:"post",
    	        datatype:'json',
    	        data:data,
    	        beforeSend:function(){},
    	        success:function(data){
    	        	$(".mask").hide();
    	        	if(data.success == true){	
    	        		$(".user_add_box").hide();
    	        		var tishi=new Tishi("保存成功","chenggong");
    	        		// 刷新表格
    	        		onlds();
    	        	}else{
    	        		/* var tishi=new Tishi("保存失败","shibai"); */
    	        		var tishi=new Tishi(data.errmsg,"shibai");
    					 $(".firm_add_edit").hide();
    	        	}
    	        },
    	        complete:function(XHR, textStatus){
    	        	statementGrid.datagrid('clearSelections')
    				statementGrid.datagrid('reload');
    	        },
    	        error:function(){}
    			})
    }else{
    	var tishi=new Tishi("请选择一条记录","tishi");
    }
   return false;
});
$("#save_add_add").click(function(){
	$("#form1").submit();
	return false;
});
$("#save_add_addGrq").click(function(){
	$("#form2").submit();
	return false;
});
/*导出  */
$("#button_excel").click(function(){
	$.ajax({
		url:"<%=basePath%>outMachine.do",
		cache:false,
		type:"post",
		datatype:'json',
		data:selll(),
		async:false,
		beforeSend:function(){},
		success:function(data){
			window.open("../../ReportTemplate/MachineEXCEt.xls");
		},
		complete:function(){},
		error:function(){
			var tishi=new Tishi("导出失败","shibai");
		}
	})
});
//清空页面数据
function clearForm(){
	$("#machineCode").val("");//机器型号
    $("#machineType").val("");//机器类型
    $("#machineName").val("");//机器名称
    $("#manuCode").val("");//所属厂商
    $("#machineDesc").val("");//备注
    $("#ssqx_add").val("");
}
</script>