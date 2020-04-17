<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 预警参数维护 -->
<form id="form1" action="" style="width:100%;height:100%;background:url('../../img/index_bg.png') no-repeat;background-size:88%;">
	<div class="war_content">		
			<div class="left_ccc" style="left: 5%;top: 1%;">
				<span class="canshu_span">预警参数</span>
				<div class="conttent_ccc">
					<p class="tel_number">
						<span>监控时段（必选）</span>
						<input type="time" class="number validate[required]" style="width: 90px;" />
						<span>至</span>
						<input type="time" class="number validate[required]" style="width: 90px;" />
					</p>
					<p class="tel_number">
						<span>超期时限（分钟）</span>
						<input type="number" class="number validate[required]" style="text-align: center;" />
					</p>
					<p class="tel_number">
						<span>微信通知（可选）</span>
						<select name="" id="weChat" class="number" style="padding: 0px 67px;">
						  <option value="">请选择</option>
						  <option value="1">&nbsp;&nbsp;&nbsp;是</option>
						  <option value="2">&nbsp;&nbsp;&nbsp;否</option>
						</select>
					</p>
					<p class="tel_number">
						<span>短信通知（可选）</span>
						<select name="" id="information" class="number" style="padding: 0px 67px;">
						  <option value="">请选择</option>
						  <option value="1">&nbsp;&nbsp;&nbsp;是</option>
						  <option value="2">&nbsp;&nbsp;&nbsp;否</option>
						</select>
					</p>
					<p class="tel_number">
						<span>邮件通知（可选）</span>
						<select name="" id="e_mail" class="number" style="padding: 0px 67px;">
						  <option value="">请选择</option>
						  <option value="1">&nbsp;&nbsp;&nbsp;是</option>
						  <option value="2">&nbsp;&nbsp;&nbsp;否</option>
						</select>
					</p>
					<button class="war_button war_button1" onclick="saveAdd(this)">保存</button>
				</div>
			</div>
			<div class="left_ccc" style="right: 5%;top: 1%;">
				<span class="canshu_span">通讯参数</span>
				<div class="conttent_ccc">
					<p class="tel_number hide1">
						<span>微信公众号：</span>
						<input type="text" class="number" id="weChatNum" />
					</p>
					<p class="tel_number hide2">
						<span>短信通知号：</span>
						<input type="text" class="number" id="informationNum"/>
					</p>
					<p class="tel_number hide3">
						<span>&nbsp;&nbsp;&nbsp;邮件通知：</span>
						<input type="text" class="number" id="e_mailNum" />
					</p>
					<p class="tel_number hide3" style='visibility: hidden;'>
						<span>发件人地址：</span>
						<input type="text" class="number" id="addRessNum" />
					</p>
					
					<p class="tel_number" style="visibility: hidden;">
						<span>&nbsp;&nbsp;&nbsp;邮件通知：</span>
						<input type="text" class="number" id="e_mailNum" />
					</p>
					<button class="war_button war_button1" onclick="saveAdd(this)">保存</button>
				</div>
			</div>
	</div>
	<!--<label id="label3" style="cursor: pointer;"><span> 微信通知</span></label><input type="checkbox" style="position: absolute;left: 450px;margin: 12px 0 0;" class="checkbox3" />
		<label id="label2" style="position: absolute;left: 470px;z-index:10;width:110px;cursor: pointer;"><span> 短信通知</span></label><input type="checkbox" style="position: absolute;left: 550px;margin: 12px 0 0;" class="checkboxed" />
		<label id="label1" style="position: absolute;left: 570px;z-index:10;width:110px;cursor: pointer;"><span> 邮件通知</span></label><input type="checkbox" style="position: absolute;left: 650px;margin: 12px 0 0;" class="checkbox1" />-->

	<!--<button class="war_button" onclick="saveAdd(this)">保存</button>-->
</form>

<script>
	window.onload = function() {
			document.getElementsByTagName('html')[0].style.fontSize = document.documentElement.clientWidth / 16 + 'px';
			document.documentElement.style.fontSize = window.innerWidth / 16 + "px";

		}
		//显示
	oNload();
	$(function() {
		valid();
		$('#weChat').change(function(){
			if($(this).val()=='1'){
				$('.hide1').find('div').show()
			}else{
				$('.hide1').find('div').hide()
			}
		})
		$('#information').change(function(){
			if($(this).val()=='1'){
				$('.hide2').find('div').show()
			}else{
				$('.hide2').find('div').hide()
			}
		})
		$('#e_mail').change(function(){
			if($(this).val()=='1'){
				$('.hide3').find('div').show()
			}else{
				$('.hide3').find('div').hide()
			}
		})
	});
	
	function valid() {
		$('#form1').validationEngine('attach', {
			relative: true,
			overflownDIV: "#divOverflown",
			promptPosition: "bottomLeft",
			maxErrorsPerField: 1,
			onValidationComplete: function(form, status) {
				if(status) {
					subAddAdd();
				}
			}
		});
	}

	function oNload() {
		$.ajax({
			url: "<%=basePath%>findParameter1.do",
			cache: false,
			type: "post",
			datatype: 'json',
			data: "",
			beforeSend: function() {},
			success: function(data) {
				console.log(data);
				var data = $.parseJSON(data);
				console.log(data.time);
				//$(".number").val(data.time);
				if(data.email == "Y") {

				}
				if(data.tel == "Y") {

				}

			},
			complete: function() {

			},
			error: function() {

			}
		});
	}

//	通讯参数
	function saveAdd(event) {				
		$("#form1").submit();	
		switchMethod('#weChat','#weChatNum')				
		switchMethod('#information','#informationNum')	
		switchMethod('#e_mail','#e_mailNum')
		switchMethod('#e_mail','#addRessNum')		
	}
	//判断是否需要填写微信号、邮箱等
	function switchMethod(id,idNum){
		var $value=$(id).val()
		if($value=='1'){
		
			$(idNum).addClass('validate[required]')
			
		}else{
			$(idNum).removeClass('validate[required]')
		}
	}
	//保存
	function subAddAdd() {
		var email;
		var tel;
		var time;
		//var number = $(".number").val();
		$.ajax({
			url: "<%=basePath%>saveParameter.do",
			cache: false,
			type: "post",
			datatype: 'json',
			data: '',
			beforeSend: function() {},
			success: function(data) {
				if(data.success == true) {
					var tishi = new Tishi("修改成功", "chenggong");
					$(".reset_pwd").hide();
					selTable();
				} else {
					var tishi = new Tishi("修改失败", "shibai");
				}
			},
			complete: function() {

			},
			error: function() {
				var tishi = new Tishi("修改失败", "shibai");
			}
		});
	}
</script>