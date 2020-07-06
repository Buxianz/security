package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.LearningContent;
import com.rbi.security.entity.web.LearningInformations;
import com.rbi.security.entity.web.safe.examination.SafeAnswerRecord;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.TaskManagerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: TaskManagerController
 * @USER: "吴松达" 学习内容与考试管理模块
 * @DATE: 2020/6/5
 * @TIME: 9:50
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 05
 * @DAY_NAME_SHORT: 周五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 09
 * @MINUTE: 50
 * @PROJECT_NAME: security
 **/
@RestController
public class TaskManagerController {
    @Autowired
    TaskManagerService taskManagerService;
    /**
     * 分页查看自身培训计划信息safe:myTrainingPlanPage
     */
    @RequiresPermissions("safe:myTrainingPlanPage")
    @RequestMapping("/getLearningInformation")
    public ResponseModel getLearningInformation(@RequestBody JSONObject date){
        try{
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            PageData<LearningInformations> data=taskManagerService.pagingLearningInformation(pageNo,startIndex,pageSize);
            return ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }

    }
    /**
     * 根据培训计划id获取学习内容
     */
    @RequiresPermissions("safe:myTrainingPlanDetails")
    @RequestMapping("/getLearningContentById")
    public ResponseModel<LearningContent> getLearningContentById(@RequestBody JSONObject date){
        try{
            int id = date.getInteger("id");
            return ResponseModel.build("1000", "查询成功",taskManagerService.getLearningContent(id));
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }

    }
    /**
     * 分页查看自身考试信息 safe:paginationExamination
     */
    @RequiresPermissions("safe:paginationExamination")
    @RequestMapping("/pagingTestPaperInfo")
    public ResponseModel<PageData<TestPaperInfo>> pagingSpecialReview(@RequestBody JSONObject date){
        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            Integer processingStatus=date.getInteger("processingStatus");
            PageData<TestPaperInfo> data=taskManagerService.pagingSpecialReview(pageNo,startIndex,pageSize,processingStatus);
            return  ResponseModel.build("1000", "查询成功",data);
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }

    /**
     * 获取试卷内容safe:getTestPaper
     */
    @RequiresPermissions("safe:getTestPaper")
    @RequestMapping("/getTestPaper")
    public ResponseModel getTestPaper(@RequestBody JSONObject date){
        try {
            int id = date.getInteger("id");
            return  ResponseModel.build("1000", "查询成功",taskManagerService.getTestPaper(id));
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }

    /**
     * 完成考试safe:completeTheExam
     */
    @RequiresPermissions("safe:completeTheExam")
    @RequestMapping("/completeTheExam")
    public ResponseModel completeTheExam(@RequestBody JSONObject date){
        try {
            Integer personnelTrainingRecordId=date.getInteger("personnelTrainingRecordId");
            List<SafeAnswerRecord> safeAnswerRecordList= JSONArray.parseArray(date.getJSONArray("safeAnswerRecordList").toString(), SafeAnswerRecord.class);
            taskManagerService.completeTheExam(personnelTrainingRecordId,safeAnswerRecordList);
            return  ResponseModel.build("1000", "完成考试");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 查看考试详情，根据考试结果和正确答案
     */
    @RequiresPermissions("safe:getTheExamDetails")
    @RequestMapping("/getTheExamDetails")
    public ResponseModel getTheExamDetails(@RequestBody JSONObject date){
        try {
            //Integer personnelTrainingRecordId=date.getInteger("personnelTrainingRecordId");
            Integer testPapreId=date.getInteger("testPapreId");
            Integer personnelTrainingRecordId=date.getInteger("personnelTrainingRecordId");
            return  ResponseModel.build("1000", "获取到考试结果详情", taskManagerService.getTheExamDetails(testPapreId,personnelTrainingRecordId));
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
