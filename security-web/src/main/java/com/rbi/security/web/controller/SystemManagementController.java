package com.rbi.security.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.system.PagingSystemInfo;
import com.rbi.security.entity.web.system.SystemFile;
import com.rbi.security.entity.web.user.PagingUser;
import com.rbi.security.exception.NonExistentException;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SystemManagementService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 创建人：吴松达
 * 制度管理模块
 */
@RestController
public class SystemManagementController {
    /**
     * 上传制度文件
     * @param multipartFile
     * @return
     */
    @Autowired
    SystemManagementService systemManagementService;
    @RequiresPermissions("systemDocuments:upload")
    @RequestMapping(value = "/uploadSystemDocuments", method = RequestMethod.POST)
    public ResponseModel uploadFiles(MultipartFile[] multipartFiles,int systemCategoryId) {
        try {
            systemManagementService.uploadDemandSystemFiles(multipartFiles,systemCategoryId);
        }catch (NonExistentException e){
            return ResponseModel.build("1010", e.getMessage());
        }
        catch (Exception e) {
            return ResponseModel.build("1001", e.getMessage());
        }
        return ResponseModel.build("1000", "上传文件成功");
    }
    /**
    * 删除制度文件
    */
    @RequestMapping("/deleteSystemFile")
    @RequiresPermissions("systemDocuments:delete")
    @ResponseBody
    public ResponseModel deleteSystemFile(@RequestBody JSONObject date){
        Integer id =date.getInteger("id");
        try{
            systemManagementService.deleteSystemFile(id);
            return ResponseModel.build("1000", "删除成功");
        }catch (NonExistentException e){
            return ResponseModel.build("1010", e.getMessage());
        } catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     *修改制度文件
     */

    /**
     * 分页查看制度文件
     */
    @RequestMapping("/getPageSystemInfo")
    @RequiresPermissions("systemDocuments:page")
    @ResponseBody
    public ResponseModel<PageData<PagingSystemInfo>> getPagingSystemInfo(@RequestBody JSONObject date){

        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingSystemInfo> data=systemManagementService.getPagingSystemInfo(pageNo,pageSize,startIndex);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 根据文件类型id，获取文件信息
     */
    @RequestMapping("/getSystemFileByTypeId")
    @ResponseBody
    public ResponseModel<List<SystemFile>> getSystemFileByTypeId(@RequestBody JSONObject date){

        try {
            int systemCategoryId = date.getInteger("systemCategoryId");
            List<SystemFile> data=systemManagementService.getSystemFileByTypeId(systemCategoryId);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
