package com.demo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro对请求的过滤会在这个类
 */
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean：3
     * @Qualifier("UserRealm") UserRealm userRealm [@Qualifier注解将参数对象与某个对应的方法绑定。]
     * @Bean 将方法注入Spring容器
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        //声明ShiroFilterFactoryBean对象并返回
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //关联DefaultWebSecurityManager【由于下面DefaultWebSecurityManager的方法已经注入bean被Spring接管，直接将返回的securityManager对象传进当前方法的参数中】
        //设置安全管理器；使用ShiroFilterFactoryBean对象中的setSecurityManager方法关联，securityManager对象
        bean.setSecurityManager(securityManager);

        //添加Shiro的内置过滤器
        /**
         * anon:无需认证就可以访问
         * authc:必须认证了才能访问
         * user:必须拥有 记住我 功能才能使用
         * perms: 拥有对某个资源的权限才能访问
         * role:拥有某个角色权限才能访问
         */
        //这里的授权要放在拦截的前面，不然授权无效
        Map<String, String> filterMap = new LinkedHashMap<>();
        //授权(user用户才有add权限可以访问/user/add路径)，未授权时需要跳转到未授权页面
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        //拦截，设置必须认证后才能访问的请求
        filterMap.put("/user/*","authc");

        //logout是shiro提供的过滤器,调用登出方法后拦截可以不跳转登陆页,这是走自定义的 shiroLogoutFilter 上面有配置
        filterMap.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置认证必须的登录请求路径
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }

    /**
     * DefaultWebSecurtiyManager：2
     * @Qualifier("UserRealm") UserRealm userRealm [@Qualifier注解将参数对象与某个对应的方法绑定。]
     * @Bean 将方法注入Spring容器以便调用
     * @Bean(name="UserRealm") bean注入时自定义声明一个name便于区分及绑定，未声明只能用方法名称
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm") UserRealm userRealm){
        //声明DefaultWebSecurityManager对象并返回
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm【由于下面UserRealm的方法已经注入bean被Spring接管，直接将返回的UserRealm对象传进当前方法的参数中】
        //使用DefaultWebSecurityManager对象中的setRealm方法关联，Realm对象
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建 Relam 对象（需要自定义）：1
     * @Bean 将方法注入Spring容器以便调用
     *  @Bean(name="UserRealm") bean注入时自定义声明一个name便于区分及绑定，未声明只能用方法名称
     */
    @Bean
    public UserRealm UserRealm(){
        return new UserRealm();
    }

    /**
     * 整合ShiroDialect:用来整合Shiro 和 Thymeleaf
     * shiro方言  支持shiro标签
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
