<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all">
<script src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script src="${pageContext.request.contextPath }/layui/jquery-3.2.1.js"></script>
<script src="${pageContext.request.contextPath }/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 --> 

<title>查询所有</title> 
</head>
<body>


<!-- <div class="layui-btn-group demoTable">
  <button id="addBtn" class="layui-btn layui-btn-normal " ><i class="layui-icon"></i>添加</button>
  <button class="layui-btn layui-btn-danger" ><i class="layui-icon"></i>删除</button>
  <button id="refrsh" class="layui-btn hp-btn-green"><i class="layui-icon">ဂ</i>刷新</button>
</div> -->
<!-- table表格 -->
<table id="demo" lay-filter="test">
	<style type="text/css">
		.layui-table-cell{height: auto;line-height: 30px}
	</style> 
</table>


<script>
layui.use(['table','form'],function(){
	var table = layui.table;
	var form = layui.form;//加载表单模块

  /*-----------------------------------------------表格渲染----------------------------------------------------------  */
  //第一个实例
  var tableIns = table.render({//表格渲染的方法
	  initSort:{field:'uId',type:'desc'}//field--对应cols
    ,elem: '#demo'//指向DOM元素 
    ,height:500//设置表格高度
    ,url:'${pageContext.request.contextPath }/user/list'//后台数据接口
    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
    ,skin:'row'//开启列边框风格
    ,even:true//开启隔行背景功能
    ,cols: [[//设置表头
    	 {type:'numbers'}
        ,{type: 'checkbox'}
      ,{field:'uId', title: 'ID', sort: true}//fixed 'left'固定最左边列
      ,{field:'uLoginName', title: '登录名', sort: true, align: 'center'}
      ,{field:'uUserName', title: '用户名',edit:'text',style:'background-color: #009688; color: #fff;'} 
      ,{field:'uPwd', title: '密码',edit:'test'}
      ,{field:'uBirthday', title: '生日', align: 'center',templet:'#uBirthday'} 
      ,{field:'uEntryTime', title: '入职时间', sort: true, align: 'right',templet:'#uEntryTime'}
      ,{field:'uIsdelete', title: '是否删除',templet:'#uIsdelete'}	//templet:'#sexid'
      /* ,{field :'role',width : 180,title : '职位',align : "center",templet:"#role"} */
      ,{fixed:'right',title: '操作',align:'center',toolbar:'#barDemo'}
    ]]
  ,done:function(res,curr,count){//如果是异步请求信息 res即为你接口返回的信息
		console.info(res)
		console.info(curr)
		console.info(count)
	}
  ,page:true //开启分页
  ,limits:[5,10,20,30,40]//每页显示条数
  });
 //监听用户类型下拉框，并重新渲染表格，带上新的参数where ------------------------------------
form.on('select(usertype)',function(data){
	//调用上面方法渲染的表格声明的tableIns进行重新渲染表格
	tableIns.reload({
		where:{"userTypeId":data.value},//data.value--获取类型下拉框当前点击事件的id；userTypeId--后台id?
		url:'${pageContext.request.contextPath }/user_userList',			//后台接口地址
		page:{curr:1}	//分页--从第一页开始
	})
	return false;
});
//监听搜索提交form表单，并重新渲染表格，带上新的参数where ------------------------------------
form.on('submit(searchQuery)',function(data){
	//调用上面方法渲染的表格声明的tableIns进行重新渲染表格
	tableIns.reload({
		where:data.field,//data.field--data调用field(field就是代表此提交监听里的所有name里的value)
		url:'${pageContext.request.contextPath }/user_fuzzyQueryUser',			//后台接口地址
		page:{curr:1}	//分页--从第一页开始
	})
	return false;
}); 
  
//监听工具条
table.on('tool(test)',function(obj){//注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	var data = obj.data;//获得当前行数据
 	var layEvent = obj.event;//获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
 	//if判断是否点击按钮事件
 	if(layEvent === 'detail'){ //对应查看按钮事件
 	 	layer.open({//弹出层
 	 		 type: 2//选择弹出层类型--2=iframe弹出层
 	 		 ,area: ['400px', '480px']
 	 	     ,maxmin:true
 	 	   	 ,anim: 1
 	 	   	 ,closeBtn:0//不用按钮
	 	     ,closeBtn:2//关闭按钮样式  	
 	 	     ,shade: 0.3 //不显示遮罩
 	 	   	 ,shadeClose: true //点击遮罩关闭弹出层
 	 	     ,moveOut: true //可以移除父容器
 	 	     ,title:'🌈查看用户详情😂'  //弹出层标题
 	 	     ,content:'${pageContext.request.contextPath }/page/onelist'
 	 	 	 ,success:function(layero,index){
					 var body = layer.getChildFrame("body",index);
					 body.find("#uId").html(data.uId),
					 body.find("#uLoginName").html(data.uLoginName),
					 body.find("#uUserName").html(data.uUserName),
					 body.find("#uPwd").html(data.uPwd),
					 body.find("#uBirthday").html(data.uBirthday),
					 body.find("#uEntryTime").html(data.uEntryTime),
					 body.find("#uIsdelete").html(data.uIsdelete)
					
					 if(data.uIsdelete == 0){
						 body.find("#uIsdelete").html("未删除");
					 }else{
						body.find("#uIsdelete").html("已删除");
						}
			
	
 	 	 	 	 }  	 	 	     
 	 	 });
 	 	
 	}else if(layEvent === 'edit'){//对应编辑按钮事件 
 		layer.open({//弹出层
	 		 type: 2//选择弹出层类型--2=iframe弹出层
	 		 ,area: ['520px', '520px']
	 	     ,maxmin:true
	 	   	 ,anim: 1
	 	   	 ,closeBtn:0//不用按钮
	 	     ,closeBtn:2//关闭按钮样式  	
	 	     ,shade: 0.3 //不显示遮罩
	 	   	 ,shadeClose: true //点击遮罩关闭弹出层
	 	     ,moveOut: true //可以移除父容器
	 	     ,title:'🌈修改更新😂'  //弹出层标题
	 	     ,content:'${pageContext.request.contextPath }/page/userupdate'
	 	 	 ,success:function(layero,index){
					 var body = layer.getChildFrame("body",index);
					 body.find("#uId").val(data.uId);
					 body.find("#uLoginName").val(data.uLoginName);
					 body.find("#uPwd").val(data.uPwd);
					 body.find("#uUserName").val(data.uUserName);
					 body.find("#uEntryTime").val(data.uEntryTime);
					 /* body.find("#uBirthday").val(data.uBirthday); */
					 body.find("#uBirthday").val(timetrans(data.uBirthday)); 
	
					 var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：
					/* ---------------性别回显渲染------------------- */
					body.find("#uIsdelete").val(data.uIsdelete); //给到一个隐藏域的input
					iframeWin.uIsdelete() //调用iframe页面的sex()方法 
					body.find("#addrole").val(roleIdList(data.userRoles)); 
					
				

	 	 	 	 }   
	 	 });
 	 	
 	}else if(layEvent === 'del'){ //对应删除按钮事件
 	 	layer.confirm('确定删除此条数据？',function(index){//点击确定则执行此方法
			obj.del();//删除对应行（tr）的DOM结构，并更新缓存
			layer.close(index);//删除后关闭删除框
			//向服务端发送删除指令带上id到后台去ajax删除
			$.ajax({//前台根据id删除的数据用ajax发送到后台进行删除
				data:{"uId":data.uId},
				type:'post',
				dataType:'json',
				url:'${pageContext.request.contextPath }/user/del',
                
			})	
 	 	 });
 	}
	 	
});
//表格刷新--放在表格监听里
$("#refrsh").click(function(){
	      //执行重载--选择的table表格的id
  table.reload('demo', {
    page: {
      curr: 1 //重新从第 1 页开始
    }
    ,where: {
      // 参数
      kd:'kd'
    }
  });
}) 

});//
</script>
<!-- 添加按钮   删除按钮   刷新按钮-->
<script type="text/javascript">
//表格添加
$('#addBtn').on('click', function(){
    layer.open({
      type: 2,//选择弹出层类型--2=iframe弹出层
      title: '🌈添加😂',
      anim: 1,
      shade: 0.3,
      resize: true,
      maxmin: true,
      closeBtn:0,//不用按钮
	  closeBtn:2,//关闭按钮样式
	  moveOut: true, //可以移除父容器  	
      shadeClose: true, //点击遮罩关闭层
      area : ['400px' , '450px'],
      content: '${pageContext.request.contextPath }/page/useradd'
  
    });
  });

</script>
<script type="text/javascript">
	function roleIdList(data){
		var roleId = new Array();
		var i = 0;
		$.each(data, function(index, content){ 
			roleId[i] = content.role.rId ;
			i++;
		});
		return roleId;
	}
	
</script>



<!-- 设置删除状态   0为未删除，1为已删除；删除则禁用   （启用|禁用  {{d.isdel==0?'checked':''}} 第一个启用=0为默认选中） -->
<script type="text/html" id="uIsdelete">
<input type="checkbox" name="uIsdelete" value="{{d.uId}}" lay-skin="switch" lay-text="未离职|已离职" lay-filter="uIsdelete" {{d.uIsdelete==0?'checked':''}}>
</script>
<!-- 关联type显示typename--  d.userType--对应User实体类的Type对象字段名 {{d.role.rName}} -->
<script type="text/html" id="Role">
 {{# layui.each(d.roleMenu,function(index,item){ }}
	{{ item.role.rName}} 
 {{# }); }}
</script>
<script type="text/html"  id="uBirthday">
		{{timetrans(d.uBirthday)}}
</script>
<script type="text/html"  id="uEntryTime">
		{{timetrans(d.uEntryTime)}}
</script>
<script type="text/html"  id="role">
		{{roleList(d.userRoleList)}}
</script>

<script type="text/javascript">
	function roleList(data){
		var role = "";
		$.each(data, function(index, content){ 
			role += content.role.rName +","
		});
		return role;
	}
	
	function timetrans(date){
	    var date = new Date(date);//如果date为10位不需要乘1000
	    var Y = date.getFullYear() + '-';
	    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
	    return Y+M+D;
	}
	
	</script>



<!-- 操作按钮 <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>-->
<script type="text/html" id="barDemo">
  
  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/javascript">
layui.use(['table','form','layer'],function(){
		var table = layui.table;
		var layer = layui.layer;
		var form = layui.form;
		form.on('switch(uIsdelete)', function(obj){
			//if判断当前点击事件--obj.elem.checked==false判断是否为默认选中状态（false代表不是默认选中状态）
		    if(obj.elem.checked==false){		//（不为默认选中的启用状态）时则删除
		    	 layer.msg('已禁用(则删除)',{icon:2,Time:1000},function(){
					window.location.reload();//刷新父页面--浏览器窗口-父页面-定位-刷新
		 		 });
		    	 $.ajax({//前台修改的数据用ajax发送到后台更新修改
		 			data:{"uId":obj.value},	//当前点击的value值与input按钮的value值相对应
		 			type:'post',
		 			dataType:'json',
		 			url:'${pageContext.request.contextPath }/user/FakeDel',
		 		})
		     }else{
		    	 layer.msg('已启用(则恢复)',{icon:1,Time:1000},function(){
					window.location.reload();//刷新父页面--浏览器窗口-父页面-定位-刷新
		 		 });
					$.ajax({//前台修改的数据用ajax发送到后台更新修改
			 			data:{"uId":obj.value},	//当前点击的value值与input按钮的value值相对应
			 			type:'post',
			 			dataType:'json',
			 			url:'${pageContext.request.contextPath }/user/RecoverFake',
			 		})
		     }
		  });	
	
});
</script>





</body>
</html>