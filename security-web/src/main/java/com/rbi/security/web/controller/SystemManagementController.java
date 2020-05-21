package com.rbi.security.web.controller;


import com.rbi.security.exception.NonExistentException;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SystemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}
