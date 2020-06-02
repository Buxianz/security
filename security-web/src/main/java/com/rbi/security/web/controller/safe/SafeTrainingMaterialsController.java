package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeTrainingMaterialsService;
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

    /**
     * 分页
     * */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = safeTrainingMaterialsService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

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
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @PostMapping("/deleteByIds")
    public ResponseModel deleteById(@RequestBody JSONObject json){
        try {
            JSONArray array = json.getJSONArray("data");
            safeTrainingMaterialsService.deleteByIds(array);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }



    /**
     * 培训内容下拉搜索
     * */
    @PostMapping("/findByCondition")
    public ResponseModel<PageData> findByCondition(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            int value = json.getInteger("value");
            PageData pageData = safeTrainingMaterialsService.findByCondition(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 名称搜索
     * */
    @PostMapping("/findByName")
    public ResponseModel<PageData> findByName(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            String value = json.getString("value");
            PageData pageData = safeTrainingMaterialsService.findByName(pageNo,pageSize,value);
            return ResponseModel.build("1000","条件查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }



    /**
     * 内容类型下拉查询
     * */
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

}
