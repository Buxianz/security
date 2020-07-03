package com.rbi.security.web.controller;

import com.rbi.security.tool.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: CustomExceptionHandler
 * @USER: "吴松达"
 * @DATE: 2020/7/2
 * @TIME: 23:49
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 02
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 23
 * @MINUTE: 49
 * @PROJECT_NAME: security
 **/
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseModel noAccess(Exception e){
        return ResponseModel.build("1003", "无权访问");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseModel failed(Exception e){
        return ResponseModel.build("1004", "权限认证失败");
    }
}
