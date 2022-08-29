<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css">
<script src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script src="${pageContext.request.contextPath }/layui/jquery-3.2.1.js"></script>
<script src="${pageContext.request.contextPath }/layui/jquery.form.js"></script>
<script src="${pageContext.request.contextPath }/layui/jquery-1.11.0.min.js"></script>
<title>添加页面</title>
</head>
<body>


	<!-- ---------------------------- 注册 ---------------------------------------- -->
<div class="form layer-form" id="importForm">	
<!-- 注册表单提交 -->
<form  id="addform" class="layui-form" action="${pageContext.request.contextPath }/user_register" enctype="multipart/form-data">
		<!-- 用户名和输入框 -->
		<div class="layui-form-item">
			<label class="layui-form-label">用户名：</label>
			<div class="layui-input-block" style="width: 220px">
				 <input type="text" name="loginName" required  lay-verify="required|loginName" lay-vertype="tipe" placeholder="请输入用户名：" autocomplete="off" class="layui-input">
			</div>
		</div>
		<!-- 密码和输入框 -->
		<div class="layui-form-item">
			<label class="layui-form-label">密码：</label>
			<div class="layui-input-block" style="width: 220px">
				<input id="password" type="password" name="loginPwd" required
					lay-verify="required|password" lay-vertype="tipe"
					placeholder="请输入6-12位密码：" autocomplete="off" class="layui-input">
			</div>
		</div>
		<!-- 姓名和输入框 -->
		<div class="layui-form-item">
			<label class="layui-form-label">姓名：</label>
			<div class="layui-input-block" style="width: 220px">
				<input type="text" name="userName" lay-verify="required"
					autocomplete="off" placeholder="请输入姓名" class="layui-input">
			</div>
		</div>
		<!-- 手机号和输入框-->
		<div class="layui-form-item">
			<label class="layui-form-label">电话：</label>
			<div class="layui-input-block" style="width: 220px">
				<input type="tel" name="phone" lay-verify="required|phone|number"
					lay-vertype="tips" placeholder="请输入手机号：" autocomplete="off"
					class="layui-input">
			</div>
		</div>

		<!-- 邮箱和输入框 -->
		<div class="layui-form-item">
			<label class="layui-form-label">邮箱：</label>
			<div class="layui-input-block" style="width: 220px">
				<input type="text" name="email" lay-verify="email"
					autocomplete="off" placeholder="请输入邮箱：" class="layui-input">
			</div>
		</div>
		<!-- 生日及输入框 -->
		<div class="layui-form-item">
			<label class="layui-form-label">生日：</label>
			<div class="layui-input-block" style="width: 220px">
				<input type="text" name="birthday" id="birthday" lay-verify="date"
					placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
			</div>
		</div>
		<!-- 头像及上传按钮 -->
		<div class="layui-form-item">
			<label class="layui-form-label">头像：</label>
			<button name="headimg" type="button" class="layui-btn" id="test1">
				<i class="layui-icon">&#xe67c;</i>上传头像
			</button>
			<div class="layui-upload-list">
				<img class="layui-upload-img" id="test2">
				<p id="demoText"></p>
			</div>
		</div>

		<!-- 性别及单选按钮 -->
		<div class="layui-form-item">
			<label class="layui-form-label">性别：</label>
			<div class="layui-input-block">
				<input type="radio" name="sex" value="男" title="男" checked="">
				<input type="radio" name="sex" value="女" title="女">
			</div>
		</div>
		<!-- 年龄 -->
		<div class="layui-form-item">
			<label class="layui-form-label">年龄：</label>
			<div class="layui-input-block" style="width: 220px">
				<input type="text" name="age" lay-verify="required"
					autocomplete="off" placeholder="请输入年龄" class="layui-input">
			</div>
		</div>


		<!-- 注册提交按钮-->
				<div class="layui-btn-container layui-col-md12" align="center">
					 <button id="register" class="layui-btn" lay-submit lay-filter="demo1"><i class="layui-icon layui-icon-edit"></i>立即提交</button>
					 <button type="reset" class="layui-btn layui-btn-primary">重置</button>	
				</div>
				
				<!-- 分割线 -->
				<hr class="layui-bg-red">
			
				
</form>
</div>	
	<!-- --------------------------------------script------------------------------------------- -->
	<script>
	
	//失去焦点
	$("input").blur(function(){
		//失去焦点样式
		$("input").css("background-color","#D6D6FF")
			//
			var name = $(this).val();
	 	//ajax
	 	$.ajax({
			data:{"loginName":name},
			type:"post",
			dataType:'json',
			url:"http://localhost:8080/yun-home/user_findLogin",
			success:function(data){
					if(data.code==200){
							layer.msg("此用户名已存在！");
							
						}/* else{
							layer.msg("用户名不能为空！")
						} */
				}
			})	  
		});

	//获取焦点
	$("input").focus(function(){
			//获取焦点样式
			$("input").css("background-color","#FFFFCC")
		});
	
 
	
	
	

	//Demo---form表单里的layui特效和ajax都要在layui.use('form', function(){--监听}里写
	layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form;//表单
	  var layer = layui.layer;//layer模块
	  var layedit = layui.layedit;//编辑器
	  var laydate = layui.laydate;//日期模块

	  //创建一个编辑器
	  // var editIndex = layedit.build('LAY_demo_editor');

	  //日期
	  laydate.render({
	    elem: '#birthday'
	  });
	  
	  /* //监听提交
	  form.on('submit(demo1)', function(data){
	    //layer.msg(JSON.stringify(data.field));
	    var jsonData = data.field;
	    //注册ajax
	    $.ajax({
	    	data:jsonData,
	    	type:"post",
	    	dataType:'json',
	    	url:"http://localhost:8080/yun-home/user_userRegister",
	    	success:function(data){
	    		if(data.code==200){
	    			layer.msg("注册成功！");
	    		}
	    	}
	    	
	    })
	    
	    return false;
	    
	  }); */
	  
	 
//自定义验证--form表单验证
form.verify({
	//用户名输入框验证
	loginName: function(value, item){//value=表单获取的值，item=表单的DOM对象
		if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s•]+$").test(value)){//验证value里的值是否与当前正则表达式相符
			return  '用户名不能有特殊字符';
		}
		if(/(^\_)|(\__)|(\_+$)/.test(value)){
			return '用户名首尾不能出现下划线\'_\'';
		}
		if(/^\d+\d+\d$/.test(value)){
			return '用户名不能全是数字';
		}
	}
		//支持上述函数式的方式，也支持下述数组的形式
		//数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
		,password: [/^[\S]{6,12}$/,'密码必须6到12位，且不能出现空格']
		,repassword: function(value, item){
			var pwd = $('#password').val();
			if(value!=pwd){
				return '两次输入密码不一致';
			}
		}

})  




});
</script>

<!-- 上传头像 -->
<script>
layui.use('upload', function(){
  var upload = layui.upload;
   
  //执行实例
  var uploadInst = upload.render({
    elem: '#test1' //绑定元素
    ,url: '/upload/' //上传接口
    ,done: function(res){
      //上传完毕回调
    }
    ,error: function(){
      //请求异常回调
    }
  });
});
</script>




</body>
</html>