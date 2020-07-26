package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeDemandReportService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafeDemandReportController 需求提报处理模块
 * @USER: "吴松达"
 * @DATE: 2020/5/31
 * @TIME: 22:14
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 31
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 22
 * @MINUTE: 14
 * @PROJECT_NAME: security
 **/
@RestController
public class SafeDemandReportController {
    @Autowired
    SafeDemandReportService safeDemandReportService;
    /*
    添加需求（培训需求提报）
     */
    @RequiresPermissions("safe:submitRequirements")
    @RequestMapping("/insertTrainingNeeds")
    public ResponseModel insertTrainingNeeds(@RequestBody JSONObject date) {
        try{
            SafeTrainingNeeds safeTrainingNeeds= JSONObject.parseObject(date.toJSONString(), SafeTrainingNeeds.class);
            safeDemandReportService.insertSafeDemandReport(safeTrainingNeeds);
            return ResponseModel.build("1000", "添加成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 新增需求和资料以及试卷  发布教育培训计划
     */
    @RequiresPermissions("safe:releaseEducationPlan")
    @RequestMapping("/insertTrainingPlanTest")
    public ResponseModel insert(@RequestBody JSONObject date) {
        try{
            SafeTrainingNeeds safeTrainingNeeds =JSONObject.parseObject(date.getJSONObject("safeTrainingNeeds").toString(), SafeTrainingNeeds.class);
            List<SafeDataPlan> safeDataPlanList= JSONArray.parseArray(date.getJSONArray("safeDataPlanList").toString(),SafeDataPlan.class);
            SafeTestPaper safeTestPaper=JSONObject.parseObject(date.getJSONObject("safeTestPaper").toString(), SafeTestPaper.class);
            safeDemandReportService.insert(safeTrainingNeeds,safeDataPlanList,safeTestPaper);
            return ResponseModel.build("1000", "新增需求和资料以及试卷成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看需求 已处理或者未处理
     */
    @RequiresPermissions("safe:TrainingPlanProcessedPage")
    @RequestMapping("/pagingSafeDemandReport")
    public ResponseModel<PageData<PagingTraniningNeeds>> pagingSafeDemandReport(@RequestBody JSONObject date) {
        try{
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            int processingStatus=date.getInteger("processingStatus");
            PageData<PagingTraniningNeeds> pagingTraniningNeedsList=safeDemandReportService.pagingSafeDemandReport(pageNo,pageSize,startIndex,processingStatus);
            return ResponseModel.build("1000", "查询成功",pagingTraniningNeedsList);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 处理需求
     */
    @RequiresPermissions("safe:handlingTrainingNeeds")
    @RequestMapping("/handlingRequirements")
    public ResponseModel handlingRequirements(@RequestBody JSONObject date) {
        try{
            SafeTrainingNeeds safeTrainingNeeds =JSONObject.parseObject(date.getJSONObject("safeTrainingNeeds").toString(), SafeTrainingNeeds.class);
            List<SafeDataPlan> safeDataPlanList= JSONArray.parseArray(date.getJSONArray("safeDataPlanList").toString(),SafeDataPlan.class);
            SafeTestPaper safeTestPaper=JSONObject.parseObject(date.getJSONObject("safeTestPaper").toString(), SafeTestPaper.class);
            safeDemandReportService.handlingRequirements(safeTrainingNeeds,safeDataPlanList,safeTestPaper);
            return ResponseModel.build("1000", "发布成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 根据id获取需求计划信息 safe:TrainingPlanDetailsProcessed 详情
     */
    @RequiresPermissions("safe:TrainingPlanDetailsProcessed")
    @RequestMapping("/getTrainingNeedsById")
    public ResponseModel<SafeTrainingNeeds> getTrainingNeedsById(@RequestBody JSONObject date) {
        try{
            Integer id=date.getInteger("id");
            return ResponseModel.build("1000", "查询成功",safeDemandReportService.getTrainingNeedsById(id));
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }

    /**
     * 根据请求人，查看当前人所在部门的需求 分页查看需求 已处理或者未处理
     */
    //@RequiresPermissions("safe:TrainingPlanProcessedPage")
    @RequestMapping("/pagingSafeConditionDemandReport")
    public ResponseModel<PageData<PagingTraniningNeeds>> pagingSafeConditionDemandReport(@RequestBody JSONObject date) {
        try{
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<PagingTraniningNeeds> pagingTraniningNeedsList=safeDemandReportService.pagingSafeConditionDemandReport(pageNo,pageSize,startIndex);
            return ResponseModel.build("1000", "查询成功",pagingTraniningNeedsList);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
