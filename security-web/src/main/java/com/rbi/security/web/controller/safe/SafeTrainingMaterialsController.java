package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeTrainingMaterialsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafeTrainingMaterialsController
 * @USER: "谢青"
 * @DATE: 2020/5/27
 * @TIME: 11:07
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 11
 * @MINUTE: 07
 * @PROJECT_NAME: security
 **/
@RequestMapping("training")
@RestController
public class SafeTrainingMaterialsController {
    @Autowired
    SafeTrainingMaterialsService safeTrainingMaterialsService;

    private final static Logger logger = LoggerFactory.getLogger(SafeTrainingMaterialsController.class);
    /**
     * 分页
     * */
    @RequiresPermissions("safeTrainingMaterials:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = safeTrainingMaterialsService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【培训内容分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }
    @RequiresPermissions("safeTrainingMaterials:add")
    @PostMapping("/add")
    public ResponseModel add(SafeTrainingMaterials safeTrainingMaterials,@RequestParam(value="file",required=false) MultipartFile file) throws IOException {
        try {
            String result = safeTrainingMaterialsService.add(safeTrainingMaterials,file);
            if(result.equals("1000")){
                return ResponseModel.build("1000","上传成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【培训内容添加】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }
    @RequiresPermissions("safeTrainingMaterials:delete")
    @PostMapping("/deleteByIds")
    public ResponseModel deleteById(@RequestBody JSONObject json){
        try {
            JSONArray array = json.getJSONArray("data");
            String result = safeTrainingMaterialsService.deleteByIds(array);
            if(result.equals("1000")){
                return ResponseModel.build("1000","删除成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【培训内容删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 内容类型下拉框
     * */
    //@RequiresPermissions("safeTrainingMaterials:findType")
    @PostMapping("/findType")
    public ResponseModel findType(){
        try {
            List<SafeContentCategory> safeContentCategories = safeTrainingMaterialsService.findType();
            return ResponseModel.build("1000","分页查询成功！",safeContentCategories);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 名称搜索
     * */
//    @RequiresPermissions("user:page")
    @PostMapping("/findByName")
    public ResponseModel<PageData> findByName(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            String value = json.getString("value");
            PageData pageData = safeTrainingMaterialsService.findByName(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            logger.error("【名称搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 开始学习 培训内容下拉搜索
     * */
    @RequiresPermissions("safeTrainingMaterials:findByCondition")
    @PostMapping("/findByCondition")
    public ResponseModel<PageData> findByCondition(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int value = json.getInteger("value");
            PageData pageData = safeTrainingMaterialsService.findByCondition(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            logger.error("【培训内容下拉搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 开始学习 培训内容文件下拉搜索 暂时保留不添加权限
     * */
    @RequiresPermissions("safeTrainingMaterials:findFileByCategory")
    @PostMapping("/findFileByCategory")
    public ResponseModel<PageData> findFileByCategory(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            Integer value = json.getInteger("value");
            PageData pageData = safeTrainingMaterialsService.findFileByCategory(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            logger.error("【培训内容文件下拉搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 开始学习 培训内容视频类下拉搜索 暂时保留不添加权限
     * */
    @RequiresPermissions("safeTrainingMaterials:findVideoByCategory")
    @PostMapping("/findVideoByCategory")
    public ResponseModel<PageData> findVideoByCategory(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            Integer value = json.getInteger("value");
            PageData pageData = safeTrainingMaterialsService.findVideoByCategory(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            logger.error("【培训内容视频类下拉搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }



}
