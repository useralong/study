<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>layout 后台大布局 - Layui</title>
		<link rel="stylesheet" href="lib/layui/css/layui.css">
		<link rel="stylesheet" type="text/css" href="css/hp-layui.css" />
        <link rel="shortcut icon" href="headimg.ico" />
	</head>

	<body class="layui-layout-body hp-white-theme">
		<div class="layui-layout layui-layout-admin">
			<div class="layui-header">
				<div class="layui-logo">
					yun+ 与你同在
				</div>
<!-- --------------------------------------导航栏------------------------------------------- -->
	<ul class="layui-nav" lay-filter="" style="text-align: center;">
		<li class="layui-nav-item"><a href="">yun+</a></li>
		<li class="layui-nav-item layui-this"><a href="javascript:;">首页</a></li>
		<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/user_loginUI">登录</a></li>
		<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/user_registerUI">注册</a></li>
		<li class="layui-nav-item"><a href="javascript:;">更多</a>
			<dl class="layui-nav-child">
				<!-- 二级菜单 -->
				<dd>
					<a href="javascript:;">广告租赁</a>
				</dd>
				<dd>
					<a href="javascript:;">广告租赁</a>
				</dd>
				<dd>
					<a href="javascript:;">广告租赁</a>
				</dd>
			</dl></li>
		<li class="layui-nav-item"><a href="javascript:;">社区</a></li>
		<li class="layui-nav-item"><a href="javascript:;"><i class="layui-icon layui-icon-cellphone-fine"></i>联系电话：123456789</a></li>
	</ul>
				
				<ul class="layui-nav layui-layout-right">
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
						<a href="">退出</a>
					</li>
				</ul>
			</div>

			<div class="layui-side hp-left-menu">
				<div class="layui-side-scroll">
					<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
					<ul class="layui-nav hp-nav-none">
						<li class="layui-nav-item">
							<a href="javascript:;"  class="hp-user-name">
								<img src="img/headimg.jpg" class="layui-circle-img"><span class="hp-kd">苏棂泠</span>
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

					<ul class="layui-nav layui-nav-tree" lay-filter="test">
						<li class="layui-nav-item layui-nav-itemed">
							<a class="" href="javascript:;">基本功能</a>
							<dl class="layui-nav-child">
								<dd>
									<a class="hp-tab-add" hp-href="${pageContext.request.contextPath }/page_userlist" href="javascript:;">查询所有</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="demo/form.html" href="javascript:;">表单</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="demo/table.html" href="javascript:;">表格</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="demo/layout.html" href="javascript:;">布局</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="demo/layer.html" href="javascript:;">弹窗</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="demo/upload.html" href="javascript:;">上传</a>
								</dd>
							</dl>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;">扩展组件</a>
							<dl class="layui-nav-child">
								<dd>
									<a class="hp-tab-add" hp-href="hpDemo/hpTab.html" href="javascript:;" >动态选项卡</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="hpDemo/hpRightMenu.html" href="javascript:;">右键菜单项</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="hpDemo/hpLayedit.html"  href="javascript:;">多图编辑器</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="hpDemo/hpFormAll.html"  href="javascript:;">表单全屏层</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="hpDemo/hpWindow.html"  >弹出窗口层</a>
								</dd>
							</dl>
						</li>
						
				       <li class="layui-nav-item">
							<a href="javascript:;">示例页面</a>
							<dl class="layui-nav-child">
								<dd>
									<a  target="_blank"   href="pageDemo/login/login1.html" >登录页面</a>
								</dd>
								<dd>
									<a  target="_blank"   href="pageDemo/404.html" >404页面</a>
								</dd>
							</dl>
						</li>
				       <li class="layui-nav-item">
							<a href="javascript:;">数据列表</a>
							<dl class="layui-nav-child">
								<dd>
									<a class="hp-tab-add" hp-href="pageDemo/list/dataList.html" href="javascript:;" >基础列表</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="pageDemo/list/imgList.html" href="javascript:;" >图文列表</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="pageDemo/list/formList.html" href="javascript:;" >表单列表</a>
								</dd>
							</dl>
						</li>
				       <li class="layui-nav-item">
							<a href="javascript:;">数据分析</a>
							<dl class="layui-nav-child">
								<dd>
									<a class="hp-tab-add" hp-href="pageDemo/echarts/bar.html" href="javascript:;" >柱状图</a>
								</dd>
								<dd>
									<a class="hp-tab-add" hp-href="pageDemo/echarts/pie.html" href="javascript:;" >饼图</a>
								</dd>
							
							</dl>
						</li>
						
						
						
						<li class="layui-nav-item">
							<a  target="_blank"   href="https://github.com/hpit-BAT">github组织</a>
						</li>
						<li class="layui-nav-item">
							<a  target="_blank"   href="https://hpit-bat.github.io/hpit-BAT-home">黑科技</a>
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
												<p style="font-size: 30px;">yun+ 与你同在</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-red demo-carousel">
												<p style="font-size: 30px;">yun+ 与你同在</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-blue demo-carousel">
												<p style="font-size: 30px;">yun+ 与你同在</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-orange demo-carousel">
												<p style="font-size: 30px;">yun+ 与你同在</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
										<div>
											<div class="layui-bg-cyan demo-carousel">
												<p style="font-size: 30px;">yun+ 与你同在</p>
												<p style="font-size: 28px;">苏棂泠</p>
											</div>
										</div>
									</div>
								</div>
                                
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
		</div>
		<script src="lib/layui/layui.js"></script>
		<script>
			// 配置
			layui.config({
				base: './hpModules/' // 扩展模块目录
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