package com.rbi.security.web.controller.doubleduty;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.DoubleDutyTemplateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.doubleduty
 * @NAME: DoubleDutyTemplateController
 * @USER: "谢青"
 * @DATE: 2020/7/14
 * @TIME: 12:00
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 14
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 12
 * @MINUTE: 00
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/db_template")
public class DoubleDutyTemplateController {
    @Autowired
    DoubleDutyTemplateService doubleDutyTemplateService;

    /**
     * 一岗双责模板分页
     **/
    @RequiresPermissions("doubleDutyMake:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = doubleDutyTemplateService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 一岗双责模板添加
     **/
    @RequiresPermissions("doubleDutyMake:add")
    @PostMapping("/add")
    public ResponseModel add(@RequestBody JSONObject json){
        try {
            String result = doubleDutyTemplateService.add(json);
            if(result.equals("1000")){
                return ResponseModel.build("1000","添加成功");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 一岗双责模板修改
     **/
    @RequiresPermissions("doubleDutyMake:update")
    @PostMapping("/update")
    public ResponseModel update(@RequestBody JSONObject json){
        try {
            String result = doubleDutyTemplateService.update(json);
            if(result.equals("1000")){
                return ResponseModel.build("1000","修改成功");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 一岗双责模板删除
     **/
    @RequiresPermissions("doubleDutyMake:delete")
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody JSONObject json){
        try {
            String result = doubleDutyTemplateService.delete(json);
            if(result.equals("1000")){
                return ResponseModel.build("1000","删除成功");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
