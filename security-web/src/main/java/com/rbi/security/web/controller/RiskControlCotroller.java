package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.risk.RiskControl;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.RiskControlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(RiskControlCotroller.class);

    /**
     * 区域内添加
     * */
    @RequiresPermissions("riskManage:addInside")
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
            logger.error("【区域内风险添加】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域外添加
     * */
    @RequiresPermissions("riskManage:addOutiside")
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
            logger.error("【区域外风险添加】失败！，ERROR：{}",e);
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
            logger.error("【风险等级计算】失败！，ERROR：{}",e);
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
            logger.error("【风险计算】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域内分页
     * */
    @RequiresPermissions("riskFile:insidePage")
    @PostMapping("/findInsideByPage")
    public ResponseModel<PageData> findInsideByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = riskControlService.findByPage("1",pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【区域内分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域外分页
     * */
    @RequiresPermissions("riskFile:outsidePage")
    @PostMapping("/findOutsideByPage")
    public ResponseModel<PageData> findOutsideByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = riskControlService.findByPage("2",pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【区域外分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 重大风险分页
     * */
    @RequiresPermissions("riskFile:seriousRiskPage")
    @PostMapping("/findSeriousRiskByPage")
    public ResponseModel<PageData> findSeriousRiskByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            String riskGrad = "一级";
            PageData pageData = riskControlService.findSeriousRiskByPage(riskGrad,pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【重大风险分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 修改
     * */
    @RequiresPermissions("riskFile:update")
    @PostMapping("/update")
    public ResponseModel update(RiskControl riskControl, @RequestParam(value="picture",required=false) MultipartFile[] picture){
        try {
            String result = riskControlService.update(riskControl,picture);
            if(result.equals("1000")){
                return ResponseModel.build("1000","修改成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【风险档案修改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除照片
     * */
    @PostMapping("/deleteByPictureId")
    public ResponseModel deleteByPictureId(@RequestBody JSONObject json){
        try {
            int id = json.getInteger("id");
            riskControlService.deleteByPictureId(id);
            return ResponseModel.build("1000","删除成功！");
        }catch (Exception e){
            logger.error("【照片删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域内条件搜索
     * */
    @PostMapping("/findInsideByCondition")
    public ResponseModel<PageData> findInsideByCondition(@RequestBody JSONObject json){
        try {
            String type = json.getString("type");
            String value = json.getString("value");
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = riskControlService.findInsideByCondition(type,value,pageNo,pageSize);
            return ResponseModel.build("1000","条件搜索成功！",pageData);
        }catch (Exception e){
            logger.error("【区域内条件搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 区域外条件搜索
     * */
    @PostMapping("/findOutsideByCondition")
    public ResponseModel<PageData> findOutsideByCondition(@RequestBody JSONObject json){
        try {
            String type = json.getString("type");
            String value = json.getString("value");
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = riskControlService.findOutsideByCondition(type,value,pageNo,pageSize);
            return ResponseModel.build("1000","条件搜索成功！",pageData);
        }catch (Exception e){
            logger.error("【区域外条件搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 重大风险条件搜索
     * */
    @PostMapping("/findSeriousByCondition")
    public ResponseModel<PageData> findSeriousByCondition(@RequestBody JSONObject json){
        try {
            String type = json.getString("type");
            String value = json.getString("value");
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = riskControlService.findSeriousByCondition(type,value,pageNo,pageSize);
            return ResponseModel.build("1000","条件搜索成功！",pageData);
        }catch (Exception e){
            logger.error("【重大风险条件搜索】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 危害种类占比统计
     * */
    @PostMapping("/findByHarmKind")
    public ResponseModel findHarmKind(){
        try {
            Map<String,Object> map = riskControlService.findHarmKind();
            return ResponseModel.build("1000","风险危害种类占比查询成功！",map);
        }catch (Exception e){
            logger.error("【危害种类占比统计】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 风险等级数量统计
     * */
    @PostMapping("/findByGrade")
    public ResponseModel findByGrade(){
        try {
            Map<String,Object> map = riskControlService.findByGrade();
            return ResponseModel.build("1000","风险等级查询成功！",map);
        }catch (Exception e){
            logger.error("【风险等级数量统计】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 风险范畴统计
     * */
    @PostMapping("/findByRiskCategory")
    public ResponseModel findByRiskCategory(){
        try {
            Map<String,Object> map = riskControlService.findByRiskCategory();
            return ResponseModel.build("1000","风险范畴分类查询成功！",map);
        }catch (Exception e){
            logger.error("【风险范畴统计】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }













}
