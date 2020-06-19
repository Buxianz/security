package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.risk.RiskControl;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.RiskControlService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: RiskControlCotroller
 * @USER: "谢青"
 * @DATE: 2020/6/18
 * @TIME: 11:01
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 11
 * @MINUTE: 01
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/risk")
public class RiskControlCotroller {
    @Autowired
    RiskControlService riskControlService;

    /**
     * 区域内添加
     * */
    @PostMapping("/addInside")
    public ResponseModel addInside(RiskControl riskControl, @RequestParam(value="picture",required=false) MultipartFile[] picture){
        try {
            String result = riskControlService.addInside(riskControl,picture);
            if(result.equals("1000")){
                return ResponseModel.build("1000","添加成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域外添加
     * */
    @PostMapping("/addOutside")
    public ResponseModel addOutside(RiskControl riskControl, @RequestParam(value="picture",required=false) MultipartFile[] picture){
        try {
            String result = riskControlService.addOutside(riskControl,picture);
            if(result.equals("1000")){
                return ResponseModel.build("1000","添加成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }



    @PostMapping("/riskValueAndGrade")
    public ResponseModel riskValueAndGrade(@RequestBody JSONObject json){
        try {
            RiskControl riskControl = JSON.toJavaObject(json,RiskControl.class);
            Map<String,Object> map = riskControlService.riskValueAndGrade(riskControl);
            return ResponseModel.build("1000","计算成功",map);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @PostMapping("/measuresResult")
    public ResponseModel measuresResult(@RequestBody JSONObject json){
        try {
            RiskControl riskControl = JSON.toJavaObject(json,RiskControl.class);
            Map<String,Object> map = riskControlService.measuresResult(riskControl);
            return ResponseModel.build("1000","计算成功",map);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
