package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.config.OrganizationTree;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * 获取公司人员树状结构
     */
    @RequestMapping("/getCompanyPersonnelTree")
    public ResponseModel<List<OrganizationTree>> getCompanyPersonnelTree(){
        try{
            return ResponseModel.build("1000", "查询成功",configService.getCompanyPersonnelTree());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
