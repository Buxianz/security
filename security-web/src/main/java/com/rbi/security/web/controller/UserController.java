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
@CrossOrigin(origins = "*",allowCredentials = "true")
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
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public ResponseModel updateCompanyInfo(@RequestBody JSONObject date){
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
    public ResponseModel deleteCompany(@RequestBody JSONObject date){

        Integer id =date.getInteger("id");
        try{
            userService.deleteUser(id);
            return ResponseModel.build("1000", "更新成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看用户
     */
    @RequestMapping("/pageQueryUserInfo")
    @ResponseBody
    public ResponseModel<PageData<PagingUser>> getPageQueryCompanyInfo(@RequestBody JSONObject date){

        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;

            return null;
        }catch (Exception e){
            return null;
        }
    }
}
