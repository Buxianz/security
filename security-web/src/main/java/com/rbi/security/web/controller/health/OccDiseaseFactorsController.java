package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccDiseaseFactors;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OccDailyMonitoringService;
import com.rbi.security.web.service.OccDiseaseFactorsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.health
 * @NAME: OccDiseaseFactorsController
 * @USER: "谢青"
 * @DATE: 2020/6/23
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
@RequestMapping("/diseaseFactors")
public class OccDiseaseFactorsController {
    @Autowired
    OccDiseaseFactorsService occDiseaseFactorsService;

    private final static Logger logger = LoggerFactory.getLogger(OccDiseaseFactorsController.class);
    /**
     * 分页
     * */
    @RequiresPermissions("diseaseFactors:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = occDiseaseFactorsService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【 接触职业病危害因素人员管理分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @RequiresPermissions("diseaseFactors:insert")
    @PostMapping("/add")
    public ResponseModel add(@RequestBody JSONObject json){
        try {
            OccDiseaseFactors occDiseaseFactors = JSON.toJavaObject(json,OccDiseaseFactors.class);
           String result =  occDiseaseFactorsService.add(occDiseaseFactors);
           if (result.equals("1000")){
               return ResponseModel.build("1000","添加成功！");
           }else {
               return ResponseModel.build("1001",result);
           }

        }catch (Exception e){
            logger.error("【 接触职业病危害因素人员管理添加】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 修改
     **/
    @RequiresPermissions("diseaseFactors:update")
    @PostMapping("/update")
    public ResponseModel update(@RequestBody JSONObject json){
        try {
            OccDiseaseFactors occDiseaseFactors = JSON.toJavaObject(json,OccDiseaseFactors.class);
            String result =  occDiseaseFactorsService.update(occDiseaseFactors);
            if (result.equals("1000")){
                return ResponseModel.build("1000","修改成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【 接触职业病危害因素人员管理更新】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除
     **/
    @RequiresPermissions("diseaseFactors:delete")
    @PostMapping("/delete")
    public ResponseModel delete(@RequestBody JSONObject json){
        try {
            occDiseaseFactorsService.delete(json);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            logger.error("【 接触职业病危害因素人员管理删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
