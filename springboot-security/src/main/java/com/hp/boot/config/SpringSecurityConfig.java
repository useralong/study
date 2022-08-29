package com.hp.boot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * AOP 横切 :拦截器！
 * 不用改变代码就可以执行类似拦截器的操作，比拦截器好玩多了，拦截器要大量代码功能也没这么多
 * @EnableWebSecurity 注解：打开WebSecurity
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 链式编程方法
     * 授权：configure(HttpSecurity http)[设置http安全策略]
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义，首页所有人可以访问，功能页只有对应有权限的人才可以访问
        //authorizeRequests认证请求，
        http.authorizeRequests()
                //antMatchers设置某一个路径，permitAll所有人可以访问
                .antMatchers("/", "/index", "/login").permitAll()
                //antMatchers设置某一个路径，hasRole某个角色的人才可以访问
                //.antMatchers("/level/**").hasRole("admin")
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2");

        //没有访问权限的，设置默认开启跳转到登陆页 http://localhost:8081/项目名/login
        //.loginPage()可以自定义formLogin登陆页面地址
        //.loginProcessingUrl()自定义登录认证请求
        //.usernameParameter()自定义登录表单提交的用户名name,.passwordParameter()自定义登录表单提交的密码name (般配合.loginProcessingUrl()一起使用)
        //使用loginPage()自定义登录页面后，登录页面的表单请求要改成.loginPage("/toLogin")中一样的路径才可以登录成功
        //使用loginProcessingUrl()自定义认证请求后，登录页面的表单请求就可以使用loginProcessingUrl()中自定义的认证请求
        //但是使用自定义认证请求后，登录提交的"用户名","密码" 的name默认名称是 "username","password"；
        //如果是其他名字，则还需要使用.usernameParameter("*").passwordParameter("*") 自定义"用户名","密码"的name
        http.formLogin().loginPage("/toLogin").usernameParameter("user").passwordParameter("pwd").loginProcessingUrl("/login");


        //防止网站攻击：get 不安全(明文传输)，post可以
        //csrf(跨站请求伪造,通过一些get方式让网站收到攻击，springboot 默认开启，需要关闭)  disable()关闭
        http.csrf().disable();

        //设置开启注销,正常注销后跳转首页或登录页：使用logoutSuccessUrl方法
        //.deleteCookies("remove").invalidateHttpSession(true);删除cookie清空session
        //.logoutSuccessUrl("/**");注销成功的页面;logoutUrl("/**");注销页面
        http.logout().logoutSuccessUrl("/");

        //.rememberMe()开启登录页的记住我功能,本身是个cookie 默认保存两周
        //.rememberMeParameter()自定义登陆页表单提交时 记住我 勾选框的name名称
        //(勾选登录后用户被保存进cookie 叉掉浏览器再访问，用户还在可以直接访问；浏览器可以手动清理就失效了)
        //如果登录页是自定义的，那么提交登录时记住我这个功能的 勾选框和表单的name
        //也是需要根据需求自定义的，就需要使用.rememberMeParameter()进行自定义
        http.rememberMe().rememberMeParameter("remember");
    }

    /**
     * 认证：configure(AuthenticationManagerBuilder auth)
     * 密码编码：PasswordEncoder
     * 在Spring Security 5.0+中新增了很多加密方法
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication()从内存中获取认证[从内存读取用户进行认证是一个特点]
        //auth.jdbcAuthentication()从数据库中获取认证[正常情况认证数据都是从数据库读取]
        //.withUser("用户名").password("密码").roles("角色1","角色2")

        //passwordEncoder(new BCryptPasswordEncoder())获取认证时进行密码编码选择加密，可以new很多种编码格式的加密
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                //withUser需要认证的用户，password认证用户的密码[new BCryptPasswordEncoder()与上面的方法同步使用进行明文密码编码转换]，roles用户的角色可以是多个
                .withUser("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2")
                //多个用户时，使用and()方法可以无限拼接多个用户
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }

}
