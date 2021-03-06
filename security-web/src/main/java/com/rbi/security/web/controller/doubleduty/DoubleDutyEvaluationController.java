package com.rbi.security.web.controller.doubleduty;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.doubleduty.DoubleDutyTemplate;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.DoubleDutyEvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.doubleduty
 * @NAME: DoubleDutyEvaluationController
 * @USER: "谢青"
 * @DATE: 2020/7/22
 * @TIME: 12:00
 **/
@RestController
@RequestMapping("/db_evaluation")
public class DoubleDutyEvaluationController {
    @Autowired
    DoubleDutyEvaluationService doubleDutyEvaluationService;
    private final static Logger logger = LoggerFactory.getLogger(DoubleDutyEvaluationController.class);

    /**
     * 一岗双责个人测评分页
     **/
    @PostMapping("/findPersonelByPage")
    public ResponseModel<PageData> findPersonelByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = doubleDutyEvaluationService.findPersonelByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【 一岗双责个人测评分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 一岗双责待审核测评分页
     **/
    @PostMapping("/findAuditByPage")
    public ResponseModel<PageData> findAuditByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = doubleDutyEvaluationService.findAuditByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【 一岗双责待审核分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 填写清单，查询模板
     **/
    @PostMapping("/findTemplate")
    public ResponseModel findTemplate(){
        try {
            DoubleDutyTemplate doubleDutyTemplate = doubleDutyEvaluationService.findTemplate();
            return ResponseModel.build("1000","查询成功！",doubleDutyTemplate);
        }catch (Exception e){
            logger.error("【 责任清单模板查询】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }
    /**
     * 一岗双责待审核测评提交
     **/
    @PostMapping("/write")
    public ResponseModel<PageData> write(@RequestBody JSONObject json){
        try {
            String result = doubleDutyEvaluationService.write(json);
            if(result.equals("1000")){
                return ResponseModel.build("1000","已提交！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【 责任清单填写】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 一岗双责待审核
     **/
    @PostMapping("/audit")
    public ResponseModel<PageData> audit(@RequestBody JSONObject json){
        try {
            String result = doubleDutyEvaluationService.audit(json);
            if(result.equals("1000")){
                return ResponseModel.build("1000","已审核！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【 责任清单审核】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }







}
