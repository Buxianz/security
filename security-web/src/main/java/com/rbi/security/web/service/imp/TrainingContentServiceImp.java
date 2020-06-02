package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.web.service.TrainingContentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: TrainingContentServiceImp
 * @USER: "吴松达"
 * @DATE: 2020/6/1
 * @TIME: 10:19
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 10
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
@Service
public class TrainingContentServiceImp implements TrainingContentService {
    /**
     * 关联培训资料库与教育培训需求计划的关联
     */
    @Override
    public String add(Integer trainingPlanId, JSONArray array) {
        for (int i = 0;i< array.size();i++){
            
        }


        return null;
    }
}
