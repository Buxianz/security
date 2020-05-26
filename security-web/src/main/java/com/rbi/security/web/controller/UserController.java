package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysUser;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: 用户管理模块
 * @USER: "吴松达"
 * @DATE: 2020/5/21
 * @TIME: 17:44
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 星期四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 44
 * @PROJECT_NAME: security
 **/
@Controller
public class UserController {
    @Autowired
    UserService userService;
    /**
     * 添加用户
     */
    @RequestMapping("/insertUser")
    @ResponseBody
    public ResponseModel insertUser(@RequestBody JSONObject date) {
        try{
            SysUser sysUser= JSONObject.parseObject(date.toJSONString(), SysUser.class);
            userService.insertUser(sysUser);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 更新用户
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public ResponseModel updateUser(@RequestBody JSONObject date){
        try{
            SysUser sysUser= JSONObject.parseObject(date.toJSONString(), SysUser.class);
            userService.updateUserInfo(sysUser);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser")
    //@RequiresPermissions("user:del")
    @ResponseBody
    public ResponseModel deleteUser(@RequestBody JSONObject date){

        Integer id =date.getInteger("id");
        try{
            userService.deleteUser(id);
            return ResponseModel.build("1000", "删除成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看用户
     */
    @RequestMapping("/pageQueryUserInfo")
    @ResponseBody
    public ResponseModel<PageData<PagingUser>> getPageQueryUserInfo(@RequestBody JSONObject date){

        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingUser> data=userService.pagingQueryUserInfo(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
