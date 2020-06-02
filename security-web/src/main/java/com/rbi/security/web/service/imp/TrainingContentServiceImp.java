package com.rbi.security.web.service.imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import com.rbi.security.web.DAO.safe.TrainingContentServiceDAO;
import com.rbi.security.web.service.TrainingContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    /**
     * 关联培训资料库与教育培训需求计划的关联
     */
    @Autowired
    TrainingContentServiceDAO trainingContentServiceDAO;


    @Override
    public void add(Integer trainingPlanId, JSONArray array) {
        for (int i = 0;i< array.size();i++){
            JSONObject json = (JSONObject)array.get(i);
            int id = json.getInteger("id");
            if (trainingContentServiceDAO.findCount(trainingPlanId,id) == 0){
                trainingContentServiceDAO.add(trainingPlanId,id);
            }
        }
    }

    @Override
    public List<SafeDataPlanDTO> findAllByTrainingPlanId(Integer trainingPlanId) {
        List<SafeDataPlanDTO> safeDataPlanDTOS = trainingContentServiceDAO.findAllByTrainingPlanId(trainingPlanId);
        return safeDataPlanDTOS;
    }

    @Override
    public void deleteById(Integer id) {
        trainingContentServiceDAO.deleteById(id);
    }

    @Override
    public Map<String, Object> findPreviewById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<SafeDataPlanDTO> safeDataPlanDTOS = trainingContentServiceDAO.findDataById(id);
        System.out.println("111:"+safeDataPlanDTOS);
        List<SafeDataPlanDTO> safeDataPlanDTOS2 = trainingContentServiceDAO.findVideoById(id);
        System.out.println("222:"+safeDataPlanDTOS2);
        map.put("培训资料",safeDataPlanDTOS);
        map.put("培训视频",safeDataPlanDTOS2);
        return map;
    }
}
