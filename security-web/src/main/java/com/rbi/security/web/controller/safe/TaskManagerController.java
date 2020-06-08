package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialReview;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.DAO.safe.SafeTrainingTasksDAO;
import com.rbi.security.web.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 分页查看自身学习信息
     */
    /**
     * 分页查看自身考试信息
     */
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
     * 获取试卷内容
     */

    /**
     * 完成考试
     */
    @RequestMapping("/completeTheExam")
    public ResponseModel completeTheExam(@RequestBody JSONObject date){
        try {

            return  ResponseModel.build("1000", "完成考试");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看考试记录
     */
}
