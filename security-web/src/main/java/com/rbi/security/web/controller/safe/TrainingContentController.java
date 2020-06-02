package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.TrainingContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: TrainingContentController
 * @USER: "谢青"
 * @DATE: 2020/6/1
 * @TIME: 21:07
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 21
 * @MINUTE: 07
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/safePlan")
public class TrainingContentController {
    @Autowired
    TrainingContentService trainingContentService;

    /**
     * 多选添加
     * */
    @PostMapping("/add")
    public ResponseModel add(@RequestBody JSONObject json){
        try {
            Integer trainingPlanId = json.getInteger("trainingPlanId");
            JSONArray array = json.getJSONArray("trainingMaterialsId");
            trainingContentService.add(trainingPlanId,array);
            return ResponseModel.build("1000","添加成功");
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1000","处理异常");
        }
    }

    /**
     * 添加的列表查询
     * */
    @PostMapping("/findAllByTrainingPlanId")
    public ResponseModel findAll(@RequestBody JSONObject json){
        try {
            Integer trainingPlanId = json.getInteger("trainingPlanId");
            List<SafeDataPlanDTO> safeDataPlanDTOS  =  trainingContentService.findAllByTrainingPlanId(trainingPlanId);
            return ResponseModel.build("1000","查询成功",safeDataPlanDTOS);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1000","处理异常");
        }
    }

    /**
     * 列表删除
     * */
    @PostMapping("/deleteById")
    public ResponseModel deleteById(@RequestBody JSONObject json){
        try {
            Integer id = json.getInteger("id");
            trainingContentService.deleteById(id);
            return ResponseModel.build("1000","删除成功");
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1000","处理异常");
        }
    }

    /**
     * 已添加资料预览
     * */
    @PostMapping("/findPreviewById")
    public ResponseModel findPreviewById(@RequestBody JSONObject json){
        try {
            Integer trainingPlanId = json.getInteger("trainingPlanId");
            Map<String,Object> map = trainingContentService.findPreviewById(trainingPlanId);
            return ResponseModel.build("1000","查询成功!",map);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1000","处理异常");
        }
    }












}
