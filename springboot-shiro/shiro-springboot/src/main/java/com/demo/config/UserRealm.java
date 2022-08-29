package com.demo.config;

import com.demo.pojo.User;
import com.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的 Realm（Shiro对权限相关的会在这个类 认证 授权）
 * 需要“继承” AuthorizingRealm
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 引入userService，从数据库中获取用户认证
     */
    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        //new SimpleAuthorizationInfo对象给用户授予权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //info.addStringPermission("user:add"); 这样授权后发现每个用户都有这个权限(因为每个用户都会经过这里)，正常来说用户权限是根据对应角色所属的权限走的

        //拿到当前登录对象
        Subject subject = SecurityUtils.getSubject();
        //通过subject.getPrincipal()方法，拿到认证方法中SimpleAuthenticationInfo对象的第一个传入的参数"用户名"
        String username = subject.getPrincipal().toString();
        //使用用户名查询对应的用户
        User currentUser = userService.queryUserByName(username);
        //根据当前的用户所对应的权限进行设置授权
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthorizationInfo");
        //调用登录方法的UsernamePasswordToken并获取登录的用户加密后的token信息（这里和登录方法那是一个全局关系）
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //连接真实数据库
        User user = userService.queryUserByName(userToken.getUsername());
        //判断数据库有没有这个人
        if(null == user){
            //返回null 会抛出异常（用户名不存在） UnknownAccountException
            return null;
        }
        //当前Realm对象的name
        String realmName = getName();
        //默认简单的加密(明文)，可以加密：MD5：单纯的密码加密，密文固定简单密码容易破解   MD5盐值加密：密码和用户名一同加密，不容易破解
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getName()+user.getSalt());
        //密码认证，shiro做~ 加密了（避免密码泄露）
        //封装用户信息，返回时new出AuthenticationInfo接口的用户认证信息实现类SimpleAuthenticationInfo传三个参数("当前用户的认证","密码对象","认证名")
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userToken.getUsername(), user.getPwd(),credentialsSalt, realmName);
        //获取登录后的用户存入session
        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.getSession().setAttribute("isLoginUser","yes");

        return simpleAuthenticationInfo;
    }
}
