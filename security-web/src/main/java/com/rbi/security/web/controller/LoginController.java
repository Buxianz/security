package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin(origins = "*",allowCredentials = "true")
@Controller
public class LoginController {
    @RequestMapping("/notLanded")
    @ResponseBody
    public JSONObject showHello(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status", 1012);
        jsonObject.put("msg", "未登录");
        return jsonObject;
    }
    @RequestMapping("/noAccess")
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public JSONObject noAccess(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status", 1015);
        jsonObject.put("msg", "无权访问");
        return jsonObject;
    }
    @RequestMapping("/failed")
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public JSONObject failed(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status", 1016);
        jsonObject.put("msg", "权限认证失败");
        return jsonObject;
    }
    @RequestMapping("/kickout")
    @ResponseBody
    public  JSONObject kickOut(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status", 1013);
        jsonObject.put("msg", "他人登陆，被迫下线");
        return jsonObject;
    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject data, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String userName=data.getString("userName");
        String password=data.getString("password");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            System.out.println(subject.getSession().getId());
            //未完成获取用户的权限菜单
            jsonObject.put("status", 1000);//验证成功;
            jsonObject.put("msg", "登陆成功");//验证成功;
            jsonObject.put("key",subject.getSession().getId());
        }catch (Exception e){
            if(e.getClass().getName()!=null){
                if(DisabledAccountException.class.getName().equals(e.getClass().getName())) {

                    jsonObject.put("status", 1018);//账号被锁定
                }else  if(UnknownAccountException.class.getName().equals(e.getClass().getName())){
                    //抛出账号不存在异常
                    jsonObject.put("status", 1017);//无此账号
                }else   if(IncorrectCredentialsException.class.getName().equals(e.getClass().getName())){
                    jsonObject.put("status", 1007);//密码错误
                }else if(UnknownSessionException.class.getName().equals(e.getClass().getName())){
                    jsonObject.put("status", 1014);//会话失效
                }else if(NullPointerException.class.getName().equals(e.getClass().getName())){
                    System.out.println(e);
                }else {
                    jsonObject.put("status", 1001);//验证失败;
                    jsonObject.put("msg", e.getMessage());//验证失败;
                }
            }else {
                jsonObject.put("status", 1001);//验证失败;
            }
        }
        return jsonObject;
    }
    @RequiresPermissions("come:1")
    @RequestMapping("/come")
    @ResponseBody
    public  JSONObject come(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("come", "我来了");
        return jsonObject;
    }
}
