package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.tool.Base64Util;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.controller.safe.SafeTrainingMaterialsController;
import com.rbi.security.web.service.HidDangerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @USER: "谢青"
 * @DATE: 2020/5/21
 * @TIME: 17:45
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/hid")
public class HidDangerCotroller {
    @Autowired
    HidDangerService hidDangerService;

    private final static Logger logger = LoggerFactory.getLogger(HidDangerCotroller.class);
    /**
     * 上报整改
     **/
    @RequiresPermissions("hidDangerTroubleshoot:repot")
    @PostMapping("/addReport")
    public ResponseModel report(HidDangerDO hidDangerDO, @RequestParam(value="beforeImg",required=false) MultipartFile[] beforeImg,
                                @RequestParam(value="afterImg",required=false) MultipartFile[] afterImg,
                                @RequestParam(value="plan",required=false) MultipartFile plan,
                                @RequestParam(value="report",required=false) MultipartFile report){
        try {
            String result = hidDangerService.addReport(hidDangerDO,beforeImg,afterImg,plan,report);
            if(result.equals("1000")){
                return ResponseModel.build("1000","隐患上报成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【上报整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    @PostMapping("/findAdmChoose")
    public ResponseModel<Map<String,Object>> findAdmChoose(@RequestBody JSONObject json) {
        try {
            JSONArray array = json.getJSONArray("data");
            Map<String,Object> map = hidDangerService.findAdmChoose(array);
            return ResponseModel.build("1000", "查询成功",map);
        } catch (Exception e) {
            logger.error("【下拉框查询公共接口】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

    /**
     * 责令整改
     * */
    @RequiresPermissions("hidDangerTroubleshoot:order")
    @PostMapping("/addOrder")
    public ResponseModel addOrder(HidDangerDO hidDangerDO, @RequestParam(value="beforeImg",required=false) MultipartFile[] beforeImg,
                                  @RequestParam(value="notice",required=false) MultipartFile notice){
        try {
            String result = hidDangerService.addOrder(hidDangerDO,beforeImg,notice);
            if(result.equals("1000")){
                return ResponseModel.build("1000","责令下发成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【责令整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 隐患处理分页
     * */
    @RequiresPermissions("hidDangerDeal:page")
    @PostMapping("/findDealByPage")
    public ResponseModel<PageData> findDealByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = hidDangerService.findDealByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【隐患处理分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 隐患档案分页
     * */
    @RequiresPermissions("hidDangerFile:page")
    @PostMapping("/findFinishByPage")
    public ResponseModel<PageData> findFinishByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = hidDangerService.findFinishByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【隐患档案分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 隐患处理详情
     * */
    @PostMapping("/findDealDetailByCode")
    public ResponseModel<Map<String,Object>> findDealDetailByCode(@RequestBody JSONObject json){
        try {
            String hidDangerCode = json.getString("hidDangerCode");
            Map<String,Object> map = hidDangerService.findDealDetailByCode(hidDangerCode);
            return ResponseModel.build("1000","查询成功！",map);
        }catch (Exception e){
            logger.error("【隐患处理详情】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 隐患档案详情
     * */
    @PostMapping("/findFinishDetailByCode")
    public ResponseModel<Map<String,Object>> findFinishDetailByCode(@RequestBody JSONObject json){
        try {
            String hidDangerCode = json.getString("hidDangerCode");
            Map<String,Object> map = hidDangerService.findFinishDetailByCode(hidDangerCode);
            return ResponseModel.build("1000","分页查询成功！",map);
        }catch (Exception e){
            logger.error("【隐患档案详情】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 完成整改
     **/
    @PostMapping("/complete")
    public ResponseModel complete(HidDangerDO hidDangerDO, @RequestParam(value="beforeImg",required=false) MultipartFile[] beforeImg,
                                @RequestParam(value="afterImg",required=false) MultipartFile[] afterImg,
                                @RequestParam(value="plan",required=false) MultipartFile plan,
                                @RequestParam(value="report",required=false) MultipartFile report){
        try {
            String result = hidDangerService.complete(hidDangerDO,beforeImg,afterImg,plan,report);
            if(result.equals("1000")){
                return ResponseModel.build("1000","完成整改！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【完成整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }

    }

    /**
     * 审核通过
     **/
    @PostMapping("/audit_pass")
    public ResponseModel auditPass(@RequestBody JSONObject json){
        try {
            hidDangerService.auditPass(json);
            return ResponseModel.build("1000","审核成功");
        }catch (Exception e){
            logger.error("【审核通过】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 审核不通过
     **/
    @PostMapping("/audit_false")
    public ResponseModel auditFalse(@RequestBody JSONObject json){
        try {
            hidDangerService.auditFalse(json);
            return ResponseModel.build("1000","审核成功");
        }catch (Exception e){
            logger.error("【审核不通过】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 通知整改
     **/
    @PostMapping("/rectification_notice")
    public ResponseModel rectificationNotice(@RequestBody JSONObject json){
        try {
            String result = hidDangerService.rectificationNotice(json);
            if (result.equals("1000")){
                return ResponseModel.build("1000","通知整改成功！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【通知整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 通知整改负责人列表
     **/
    @PostMapping("/findCorrector")
    public ResponseModel findCorrector(){
        try {
            Map<String,Object> map = hidDangerService.findCorrector();
            return ResponseModel.build("1000","查询成功！",map);
        }catch (Exception e){
            logger.error("【通知整改负责人列表】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 上报处理按钮
     **/
    @PostMapping("/report")
    public ResponseModel report(@RequestBody JSONObject json){
        try {
            String result  = hidDangerService.report(json);
            if (result.equals("1000")){
                return ResponseModel.build("1000","上报成功！");
            }else {
                return ResponseModel.build("1000",result);
            }
        }catch (Exception e){
            logger.error("【上报处理按钮】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 整改方案删除
     **/
    @PostMapping("/deletePlan")
    public ResponseModel deletePlan(@RequestBody JSONObject json){
        try {
            String hidDangerCode  = json.getString("hidDangerCode");
            hidDangerService.deletePlan(hidDangerCode);
            return ResponseModel.build("1000","删除成功!");
        }catch (Exception e){
            logger.error("【整改方案删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 验收报告删除
     **/
    @PostMapping("/deleteReport")
    public ResponseModel deleteReport(@RequestBody JSONObject json){
        try {
            String hidDangerCode  = json.getString("hidDangerCode");
            hidDangerService.deleteReport(hidDangerCode);
            return ResponseModel.build("1000","删除成功!");
        }catch (Exception e){
            logger.error("【验收报告删除】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 删除整改后的照片
     **/
    @PostMapping("/deletePicture")
    public ResponseModel deletePicture(@RequestBody JSONObject json){
        try {
            Integer id  = json.getInteger("id");
            hidDangerService.deletePicture(id);
            return ResponseModel.build("1000","删除成功!");
        }catch (Exception e){
            logger.error("【删除整改后的照片】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 隐患等级占比
     * */
    @PostMapping("/findByGrade")
    public ResponseModel findByGrade(){
        try {
            Map<String,Object> map = hidDangerService.findByGrade();
            return ResponseModel.build("1000","隐患等级占比查询成功！",map);
        }catch (Exception e){
            logger.error("【隐患等级占比】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 危害种类占比
     * */
    @PostMapping("/findByType")
    public ResponseModel findByType(){
        try {
            Map<String,Object> map = hidDangerService.findByType();
            return ResponseModel.build("1000","隐患等级占比查询成功！",map);
        }catch (Exception e){
            logger.error("【危害种类占比】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * 本年每月隐患数量统计
     * */
    @PostMapping("/findByMonth")
    public ResponseModel findByMonth(){
        try {
            Map<String,Object> map = hidDangerService.findByMonth();
            return ResponseModel.build("1000","本年每月隐患数量统计查询成功！",map);
        }catch (Exception e){
            logger.error("【本年每月隐患数量统计】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    /**
     * APP立即整改
     **/
    @PostMapping("/rectifyImmediately")
    public ResponseModel rectifyImmediately(@RequestBody JSONObject json){
        try {
            HidDangerDO hidDangerDO = JSON.toJavaObject(json,HidDangerDO.class);
            MultipartFile[] beforeImg = null;
            MultipartFile[] afterImg = null;
            JSONArray beforeImgJsonArray = json.getJSONArray("beforeImg");
            if (beforeImgJsonArray != null){
                List<String> beforeImgList = new ArrayList<>();
                for (int i = 0;i<beforeImgJsonArray.size();i++){
                    JSONObject jsonObject = (JSONObject)beforeImgJsonArray.get(i);
                    beforeImgList.add(jsonObject.getString("file"));
                }
                beforeImg = Base64Util.base64ToListFile(beforeImgList);
            }
            JSONArray afterImgJsonArray = json.getJSONArray("afterImg");
            if (afterImgJsonArray != null) {
                List<String> afterImgList = new ArrayList<>();
                for (int i = 0; i < afterImgJsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) afterImgJsonArray.get(i);
                    afterImgList.add(jsonObject.getString("file"));
                }
                afterImg = Base64Util.base64ToListFile(afterImgList);
            }
            String result = hidDangerService.rectifyImmediately(hidDangerDO,beforeImg,afterImg);
            if(result.equals("1000")){
                return ResponseModel.build("1000","立即整改完成！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【立即整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }

    }

    /**
     * APP上报整改
     **/
    @RequiresPermissions("hidDangerTroubleshoot:repot")
    @PostMapping("/addReport2")
    public ResponseModel report2(@RequestBody JSONObject json) {
        try {
            HidDangerDO hidDangerDO = JSON.toJavaObject(json, HidDangerDO.class);
            hidDangerDO.setIfDeal("否");
            MultipartFile[] beforeImg = null;
            MultipartFile[] afterImg = null;
            MultipartFile plan = null;
            MultipartFile report = null;
            JSONArray beforeImgJsonArray = json.getJSONArray("beforeImg");
            if (beforeImgJsonArray != null) {
                List<String> beforeImgList = new ArrayList<>();
                for (int i = 0; i < beforeImgJsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) beforeImgJsonArray.get(i);
                    beforeImgList.add(jsonObject.getString("file"));
                }
                beforeImg = Base64Util.base64ToListFile(beforeImgList);
            }
            JSONArray afterImgJsonArray = json.getJSONArray("afterImg");
            if (afterImgJsonArray != null) {
                List<String> afterImgList = new ArrayList<>();
                for (int i = 0; i < afterImgJsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) afterImgJsonArray.get(i);
                    afterImgList.add(jsonObject.getString("file"));
                }
                afterImg = Base64Util.base64ToListFile(afterImgList);
            }
            String result = hidDangerService.addReport(hidDangerDO,beforeImg,afterImg,plan,report);
            if (result.equals("1000")) {
                return ResponseModel.build("1000", "立即整改完成！");
            } else {
                return ResponseModel.build("1001", result);
            }
        } catch (Exception e) {
            logger.error("【上报整改】失败！，ERROR：{}", e);
            return ResponseModel.build("1001", "处理异常");
        }
    }


    /**
     * APP完成整改
     **/
    @PostMapping("/complete2")
    public ResponseModel complete2(@RequestBody JSONObject json){
        try {
            HidDangerDO hidDangerDO = JSON.toJavaObject(json, HidDangerDO.class);
            hidDangerDO.setIfDeal("是");
            MultipartFile[] beforeImg = null;
            MultipartFile[] afterImg = null;
            MultipartFile plan = null;
            MultipartFile report = null;
            JSONArray beforeImgJsonArray = json.getJSONArray("beforeImg");
            if (beforeImgJsonArray != null) {
                List<String> beforeImgList = new ArrayList<>();
                for (int i = 0; i < beforeImgJsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) beforeImgJsonArray.get(i);
                    beforeImgList.add(jsonObject.getString("file"));
                }
                beforeImg = Base64Util.base64ToListFile(beforeImgList);
            }
            JSONArray afterImgJsonArray = json.getJSONArray("afterImg");
            if (afterImgJsonArray != null) {
                List<String> afterImgList = new ArrayList<>();
                for (int i = 0; i < afterImgJsonArray.size(); i++) {
                    JSONObject jsonObject = (JSONObject) afterImgJsonArray.get(i);
                    afterImgList.add(jsonObject.getString("file"));
                }
                afterImg = Base64Util.base64ToListFile(afterImgList);
            }
            String result = hidDangerService.complete(hidDangerDO,beforeImg,afterImg,plan,report);
            if(result.equals("1000")){
                return ResponseModel.build("1000","完成整改！");
            }else {
                return ResponseModel.build("1001",result);
            }
        }catch (Exception e){
            logger.error("【完成整改】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }

    }


}
