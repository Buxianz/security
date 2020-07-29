package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.controller.health.OccStatusEvaluationController;
import com.rbi.security.web.service.SafeAdministratorReviewService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: SafeAdministratorReviewController
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:11
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 11
 * @PROJECT_NAME: security
 **/
@RequestMapping("administratorReview")
@RestController
public class SafeAdministratorReviewController {
    @Autowired
    SafeAdministratorReviewService safeAdministratorReviewService;

    private final static Logger logger = LoggerFactory.getLogger(SafeAdministratorReviewController.class);
    /**
     * 导出安全负责人复审名单
     */
    @RequestMapping("/exportAdminstratorReview")
    public ResponseModel exportAdminstratorReview(@RequestBody JSONObject date){
        try {
            int completionStatus = date.getInteger("completionStatus");
            return  ResponseModel.build("1000", "导出成功",null,safeAdministratorReviewService.exportAdminstratorReview(completionStatus));
        }catch (Exception e){
            logger.error("【 导出安全负责人复审名单】失败！，ERROR：{}",e);
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 分页查看负责人、安全生产管理人员培训记录 safe:securityPersonnelReviewPagination
     */
    @RequiresPermissions("safe:securityPersonnelReviewPagination")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = safeAdministratorReviewService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【 安全负责人分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 完成复审
     */
    @RequiresPermissions("safe:completeSafetyReview")
    @PostMapping("/review")
    public ResponseModel review(@RequestBody JSONObject json){
        try {
            SafeAdministratorReviewDTO safeAdministratorReviewDTO = JSON.toJavaObject(json,SafeAdministratorReviewDTO.class);
            safeAdministratorReviewService.review(safeAdministratorReviewDTO);
            return ResponseModel.build("1000","完成复审！");
        }catch (Exception e){
            logger.error("【 安全负责人复审】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 取消
     */
    @RequiresPermissions("safe:cancelSafetyReview")
    @PostMapping("/cancel")
    public ResponseModel cancel(@RequestBody JSONObject json){
        try {
            SafeAdministratorReviewDTO safeAdministratorReviewDTO = JSON.toJavaObject(json,SafeAdministratorReviewDTO.class);
            safeAdministratorReviewService.cancel(safeAdministratorReviewDTO);
            return ResponseModel.build("1000","已取消！");
        }catch (Exception e){
            logger.error("【 安全负责人取消】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }
    @RequiresPermissions("safe:exportSecurityPersonnelReviewList")
    @PostMapping("/writeAdmin")
    public ResponseModel writeAdmin() {
        try {
            Map<String,Object> map = safeAdministratorReviewService.writeAdmin();
            return ResponseModel.build("1000", "导出完成", map);
        } catch (Exception e) {
            logger.error("【 安全负责人导出】失败！，ERROR：{}",e);
            return ResponseModel.build("1002", "服务器处理异常", e);
        }
    }




}
