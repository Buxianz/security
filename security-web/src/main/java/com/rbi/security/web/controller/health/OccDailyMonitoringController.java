package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.controller.doubleduty.DoubleDutyTemplateController;
import com.rbi.security.web.service.HidDangerService;
import com.rbi.security.web.service.OccDailyMonitoringService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/dailyMonitoring")
public class OccDailyMonitoringController {
    @Autowired
    OccDailyMonitoringService occDailyMonitoringService;

    private final static Logger logger = LoggerFactory.getLogger(OccDailyMonitoringController.class);
    /**
     * 分页
     * */
    @RequiresPermissions("dailyMonitoring:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = occDailyMonitoringService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【 日常监测分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @RequiresPermissions("dailyMonitoring:insert")
    @PostMapping("/add")
    public ResponseModel add(@RequestBody JSONObject json){
        try {
            OccDailyMonitoring occDailyMonitoring = JSON.toJavaObject(json,OccDailyMonitoring.class);
            occDailyMonitoringService.add(occDailyMonitoring);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            logger.error("【 日常监测添加】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 修改
     **/
    @RequiresPermissions("dailyMonitoring:update")
    @PostMapping("/update")
    public ResponseModel update(@RequestBody JSONObject json){
        try {
            OccDailyMonitoring occDailyMonitoring = JSON.toJavaObject(json,OccDailyMonitoring.class);
            occDailyMonitoringService.update(occDailyMonitoring);
            return ResponseModel.build("1000","修改成功！");
        }catch (Exception e){
            logger.error("【 日常监测修改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除
     **/
    @RequiresPermissions("dailyMonitoring:delete")
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody JSONObject json){
        try {
            occDailyMonitoringService.delete(json);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            logger.error("【 日常监测删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
