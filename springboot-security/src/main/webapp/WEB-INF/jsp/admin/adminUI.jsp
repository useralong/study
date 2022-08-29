<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>layout 后台大布局 - Layui</title>
		<link rel="stylesheet" href="../layui-master/lib/layui/css/layui.css">
		<link rel="stylesheet" type="text/css" href="../layui-master/css/hp-layui.css" />
        <link rel="shortcut icon" href="../layui-master/headimg.ico" />
        
       <%--  <link rel="stylesheet" href="${pageContext.request.contextPath }/css/demo.css" type="text/css">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zTreeStyle.css" type="text/css">
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ztree.core.js"></script> --%>
	</head>

	<body class="layui-layout-body hp-white-theme">
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">
					<marquee loop="-1" scrollamount="10">xx后台权限管理系统</marquee>
				</div>
				
				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item" style="margin:0 auto;">
						<a href="javascript:;">欢迎您，${role.rName}！</a>
					</li>
					<li class="layui-nav-item">
						<a class="name" href="javascript:;"><i class="layui-icon"></i>主题<span class="layui-nav-more"></span></a>
						<dl class="layui-nav-child layui-anim layui-anim-upbit">
							<dd>
								<a skin="hp-black-theme" class="hp-theme-skin-switch"  href="javascript:;">低调黑</a>
							</dd>
							<dd >
								<a skin="hp-blue-theme" class="hp-theme-skin-switch" href="javascript:;">炫酷蓝</a>
							</dd>
							<dd>
								<a skin="hp-green-theme" class="hp-theme-skin-switch"  href="javascript:;">原谅绿</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="${pageContext.request.contextPath }/user/remove">退出</a>
					</li>
				</ul>
			</div>

			<div class="layui-side hp-left-menu">
				<div class="layui-side-scroll">
					<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
					<ul class="layui-nav hp-nav-none">
						<li class="layui-nav-item">
							<a href="javascript:;"  class="hp-user-name">
								<img src="../img/1.jpg" class="layui-circle-img"><span class="hp-kd">${user.uLoginName}</span>
							</a>

							<dl class="layui-nav-child">
								<dd>
									<a href="">基本资料</a>
								</dd>
								<dd>
									<a href="">安全设置</a>
								</dd>
							</dl>
						</li>
					</ul>
				
					
					<ul  class="layui-nav layui-nav-tree" lay-filter="test">
						<li class="layui-nav-item layui-nav-itemed">
							<a class="" href="javascript:;">用户管理</a>
							<dl class="layui-nav-child">
								<dd>
									<a class="hp-tab-add" hp-href="${pageContext.request.contextPath }/page/userlist" href="javascript:;">查询用户</a>
								</dd>
								
							</dl>
						</li>
					
						
						
					
					</ul>
						
				</div>
			</div>

			<div class="layui-body">
				<!-- 内容主体区域 -->
					<div class="layui-tab hp-tab " style="" lay-filter="hp-tab-filter" lay-allowclose="true">
						<ul class="layui-tab-title" style="">
							<li class="layui-this" lay-id="0">首页</li>
						</ul>
						<div class="layui-tab-content">
							<div class="layui-tab-item layui-show">
								<div class="layui-carousel" id="test1">
									<div carousel-item>
										<div>
											<div class="layui-bg-green demo-carousel">
												<p style="font-size: 30px;">xx后台权限管理系统</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-red demo-carousel">
												<p style="font-size: 30px;">xx后台权限管理系统</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-blue demo-carousel">
												<p style="font-size: 30px;">xx后台权限管理系统</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-orange demo-carousel">
												<p style="font-size: 30px;">xx后台权限管理系统</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-cyan demo-carousel">
												<p style="font-size: 30px;">xx后台权限管理系统</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
									</div>
								</div>
                                	<ul>
										<li class="layui-timeline-item">
											<i class="layui-icon layui-timeline-axis"></i>
											<div class="layui-timeline-content layui-text">
												<div class="layui-timeline-title" style="color: red;">
													2018-没想到layui已经陪我到了2018年,感叹青春正在奔跑
												</div>
											</div>
										</li>										
										<li class="layui-timeline-item">
											<i class="layui-icon layui-timeline-axis"></i>
											<div class="layui-timeline-content layui-text">
												<div class="layui-timeline-title" style="color: red;">2017末尾,想把学习的layui成果分享->hp-layui-1.0 </div>
												<ul>
													<li>
														基本功能
													</li>
													<li>
														扩展组件(layui 模块写法)
													</li>
												</ul>
											</div>
										</li>
										<li class="layui-timeline-item">
											<i class="layui-icon layui-timeline-axis"></i>
											<div class="layui-timeline-content layui-text">
												<div class="layui-timeline-title" style="color: red;">2017 中途,更新版本layui-2.x 感觉layui-越来越好</div>
											</div>
										</li>
										<li class="layui-timeline-item">
											<i class="layui-icon layui-timeline-axis"></i>
											<div class="layui-timeline-content layui-text">
												<div class="layui-timeline-title" style="color: red;">2017伊始,接触 layui-1.x 学习使用 </div>
											</div>
										</li>
									
									</ul>
							</div>
						</div>
					</div>

				</div>
			</div>

			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© hp-layui-version-1.0
			</div>
		
		
		
		<script src="../layui-master/lib/layui/layui.js"></script>
		<script>
			// 配置
			layui.config({
				base: '../layui-master/hpModules/' // 扩展模块目录
			}).extend({ // 模块别名
				hpTab: 'hpTab/hpTab',
				hpRightMenu: 'hpRightMenu/hpRightMenu',
				hpFormAll: 'hpFormAll/hpFormAll'
			});
			//JavaScript代码区域
			layui.use(['element', 'carousel','hpTheme', 'hpTab', 'hpLayedit', 'hpRightMenu'], function() {
				var element = layui.element;
				var carousel = layui.carousel; //轮播
				var hpTab = layui.hpTab;
				var hpRightMenu = layui.hpRightMenu;
				var hpTheme=layui.hpTheme;
				$ = layui.jquery;
           	    // 初始化主题
				hpTheme.init();
				 //初始化轮播
				carousel.render({
					elem: '#test1',
					width: '100%', //设置容器宽度
					interval: 1500,
					height: '500px',
					arrow: 'none', //不显示箭头
					anim: 'fade', //切换动画方式
				});
			
				// 初始化 动态tab
				hpTab.init();
				// 右键tab菜单
				hpRightMenu.init();

			});
		</script>
	</body>

</html>