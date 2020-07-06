package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.config.SystemMenuPermisson;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.safe.demand.SafaTrainingType;
import com.rbi.security.entity.web.system.SystemCategory;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: 配置信息获取模块
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
@RestController
public class ConfigController {
    @Autowired
    ConfigService configService;
    /**
     * 获取公司组织架构树形结构
     */
    @RequestMapping("/getOrganizationTree")
    public ResponseModel<List<OrganizationTree>> getOrganizationTree(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getOrganizationTree());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 获取所有角色信息
     */
    @RequestMapping("/getRole")
    public ResponseModel<List<SysRole>> getRole(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getRole());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }

    /**
     * 获取公司人员树状结构getSystemMenuPermissonTree
     */
    @RequestMapping("/getCompanyPersonnelTree")
    public ResponseModel<List<OrganizationTree>> getCompanyPersonnelTree(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getCompanyPersonnelTree());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    @RequestMapping("/getSystemMenuPermissonTree")
    public ResponseModel<List<SystemMenuPermisson>> getSystemMenuPermissonTree(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getSystemMenuPermissonTree());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 获取制度类型下拉框信息
     */
    @RequestMapping("/getSystemTypeBox")
    public ResponseModel<List<SystemCategory>> getSystemTypeBox(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getSystemCategoryBox());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 获取所有培训类型下拉框
     */

    @RequestMapping("/getSafaTrainingType")
    public ResponseModel<List<SafaTrainingType>> getSafaTrainingType(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getSafaTrainingType());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
