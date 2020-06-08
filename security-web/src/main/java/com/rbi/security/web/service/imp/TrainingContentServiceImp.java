package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.web.DAO.safe.TrainingContentServiceDAO;
import com.rbi.security.web.service.TrainingContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final Logger logger = LoggerFactory.getLogger(TrainingContentServiceImp.class);

    /**
     * 关联培训资料库与教育培训需求计划的关联
     */
    @Autowired
    TrainingContentServiceDAO trainingContentServiceDAO;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public void add(List<SafeDataPlan> safeDataPlanList) {
        try {
            for (int i = 0;i< safeDataPlanList.size();i++){
                int num = trainingContentServiceDAO.findCount(safeDataPlanList.get(i).getTrainingPlanId(),safeDataPlanList.get(i).getTrainingMaterialsId());
                if ( num == 0){
                    trainingContentServiceDAO.add(safeDataPlanList.get(i));
                }
            }
        }catch (Exception e){
            logger.error("计划id,内容id绑定异常，异常信息为{}", e);
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
