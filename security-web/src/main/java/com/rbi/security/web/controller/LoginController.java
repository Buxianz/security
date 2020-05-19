package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.AuthenticationUserDTO;
import com.rbi.security.tool.ResponseModel;
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
@Controller
public class LoginController {
    @RequestMapping("/notLanded")
    @ResponseBody
    public ResponseModel showHello(){
        return ResponseModel.build("1002", "未登录");
    }
    @RequestMapping("/noAccess")
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseModel noAccess(){

        return ResponseModel.build("1003", "无权访问");
    }
    @RequestMapping("/failed")
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public ResponseModel failed(){

        return ResponseModel.build("1004", "权限认证失败");
    }
    @RequestMapping("/kickout")
    @ResponseBody
    public  ResponseModel kickOut(){
        return ResponseModel.build("1005", "他人登陆，被迫下线");
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseModel login(@RequestBody JSONObject data, HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        String username=data.getString("username");
        String password=data.getString("password");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            System.out.println(subject.getSession().getId());
            //未完成获取用户的权限菜单
            return ResponseModel.build("1000", "登陆成功", (String) subject.getSession().getId(),(AuthenticationUserDTO)subject.getPrincipal());
        }catch (Exception e){
            if(e.getClass().getName()!=null){
                if(DisabledAccountException.class.getName().equals(e.getClass().getName())) {

                    return ResponseModel.build("1006", "账号被锁定");
                }else  if(UnknownAccountException.class.getName().equals(e.getClass().getName())){
                    //抛出账号不存在异常
                    return ResponseModel.build("1007", "无此账号");
                }else   if(IncorrectCredentialsException.class.getName().equals(e.getClass().getName())){
                    return ResponseModel.build("1008", "密码错误");
                }else if(UnknownSessionException.class.getName().equals(e.getClass().getName())){
                    return ResponseModel.build("1009", "会话失效");
                }else if(NullPointerException.class.getName().equals(e.getClass().getName())){
                    return ResponseModel.build("1001", "验证失败");
                }else {
                    return ResponseModel.build("1001", "验证失败");
                }
            }else {
                return ResponseModel.build("1001", "验证失败");
            }
        }

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
