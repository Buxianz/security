package com.rbi.security.web.shiro;


import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.web.service.LoginVerificationService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class MyShiroRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    public Object getKey(Subject subject){
//得到一个身份集合，其包含了Realm验证成功的身份信息
        PrincipalCollection principals = subject.getPrincipals();
        Object key;
        key = this.getAuthorizationCacheKey(principals);
        return key;
    }
    /*@Autowired
    RedisOperator redisOperator;*/
    @Autowired
    LoginVerificationService loginVerificationService;
    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        // 从数据库获取对应用户名密码的用户
        AuthenticationUserDTO user=loginVerificationService.getUserPrincipalInfo(name);

        if (user != null) {
            // 用户为禁用状态
            if (user.getEnabled() != 1) {
                throw new DisabledAccountException();
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalf()),
                    getName());
            return authenticationInfo;
        }
        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //String systemKey =(String) redisOperator.get(UserAccessSystemCacheKey.getUserAccessSystemCacheKey(((AuthenticationUserDTO)principal).getUserName()));
        //Set<String>  permissions=loginVerificationService.getUserPermissionName(((AuthenticationUserDTO)principal).getUserCode(),systemKey);
       /* if (permissions!=null) {
            authorizationInfo.addStringPermissions(permissions);
        }*/
        //Set<String> roles=loginVerificationService.getUserRoleName(((AuthenticationUserDTO)principal).getUserCode());
        //authorizationInfo.addRoles(roles);
       /* logger.info("---------------- Shiro 权限获取成功 ----------------------");
        if (principal instanceof SysUser) {
            SysUser userLogin = (SysUser) principal;
            Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }*/
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;
    }

  /*  //如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值

@Autowired
    SysUserDAO sysUserDAO;
    *//**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     *
     *//*
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
        AuthenticationUserDTO user=sysUserDAO.findUserByUserName(userName);
        if(user==null){
            return null;
        }
        ByteSource bytes=ByteSource.Util.bytes("ABC");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
        return authenticationInfo;
    }

    *//**
     * 授权
     *//*
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String>  permissions=new HashSet<String>();
         permissions.add("come:1");
        authorizationInfo.addStringPermissions(permissions);
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        *//*if (principal instanceof SysUser) {
            SysUser userLogin = (SysUser) principal;
            Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }
        logger.info("---- 获取到以下权限 ----");
        logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");*//*
        return authorizationInfo;
    }*/

}
