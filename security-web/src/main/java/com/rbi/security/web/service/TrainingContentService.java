package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: TrainingContentService   培训内容处理模块
 * @USER: "吴松达"
 * @DATE: 2020/6/1
 * @TIME: 10:17
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 10
 * @MINUTE: 17
 * @PROJECT_NAME: security
 **/
public interface TrainingContentService {
    /**
     * 关联培训资料库与教育培训需求计划的关联
     */
    //添加计划内容
    void add(List<SafeDataPlan> safeDataPlanList);

}
