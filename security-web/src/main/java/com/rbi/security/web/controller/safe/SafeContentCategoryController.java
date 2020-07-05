package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeContentCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafeContentCategoryController
 * @USER: "谢青"
 * @DATE: 2020/6/17
 * @TIME: 14:17
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 17
 * @PROJECT_NAME: security
 **/
@RequestMapping("category")
@RestController
public class SafeContentCategoryController {
    @Autowired
    SafeContentCategoryService safeContentCategoryService;

    /**
     * 分页
     * */
    @RequiresPermissions("safeContentCategory:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = safeContentCategoryService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     * */
    @RequiresPermissions("safeContentCategory:add")
    @PostMapping("/add")
    public ResponseModel<PageData> add(@RequestBody JSONObject json){
        try {
            SafeContentCategory safeContentCategory = JSON.toJavaObject(json,SafeContentCategory.class);
            safeContentCategoryService.add(safeContentCategory);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 修改
     * */
    @RequiresPermissions("safeContentCategory:update")
    @PostMapping("/update")
    public ResponseModel<PageData> update(@RequestBody JSONObject json){
        try {
            SafeContentCategory safeContentCategory = JSON.toJavaObject(json,SafeContentCategory.class);
            safeContentCategoryService.update(safeContentCategory);
            return ResponseModel.build("1000","修改成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @RequiresPermissions("safeContentCategory:delete")
    @PostMapping("/deleteById")
    public ResponseModel deleteById(@RequestBody JSONObject json){
        try {
            Integer id = json.getInteger("id");
            String result = safeContentCategoryService.deleteById(id);
            if (result.equals("1000")){
                return ResponseModel.build("1000","删除成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 名称搜索
     * */
    @RequiresPermissions("safeContentCategory:findByContentCategoryName")
    @PostMapping("/findByContentCategoryName")
    public ResponseModel<PageData> findByContentCategoryName(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            String contentCategoryName = json.getString("contentCategoryName");
            PageData pageData = safeContentCategoryService.findByContentCategoryName(pageNo,pageSize,contentCategoryName);
            return ResponseModel.build("1000","查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

}
