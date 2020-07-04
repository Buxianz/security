package com.rbi.security.web.controller.health;


import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.entity.web.health.OccStatusEvaluation;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OccRegularMonitoringService;
import com.rbi.security.web.service.OccStatusEvaluationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.health
 * @NAME: OccDailyMonitoringController
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:15
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 15
 * @PROJECT_NAME: security
 **/


@RestController
@RequestMapping("/statusEvaluation")
public class OccStatusEvaluationController {
    @Autowired
    OccStatusEvaluationService occStatusEvaluationService;

    /**
     * 分页
     * */
    @RequiresPermissions("statusEvaluation:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = occStatusEvaluationService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @RequiresPermissions("statusEvaluation:insert")
    @PostMapping("/add")
    public ResponseModel add(OccStatusEvaluation occStatusEvaluation,@RequestParam(value="file",required=false) MultipartFile file){
        try {
            occStatusEvaluationService.add(occStatusEvaluation,file);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 修改
     **/
    @RequiresPermissions("statusEvaluation:update")
    @PostMapping("/update")
    public ResponseModel update(OccStatusEvaluation occStatusEvaluation, @RequestParam(value="file",required=false) MultipartFile file){
        try {
            occStatusEvaluationService.update(occStatusEvaluation,file);
            return ResponseModel.build("1000","修改成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除
     **/
    @RequiresPermissions("statusEvaluation:delete")
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody JSONObject json){
        try {
            occStatusEvaluationService.delete(json);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除
     **/
    //@RequiresPermissions("statusEvaluation:page")
    @PostMapping("/deleteFile")
    public ResponseModel deleteFile(@RequestBody JSONObject json){
        try {
            Integer id = json.getInteger("id");
            occStatusEvaluationService.deleteFile(id);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }




}
