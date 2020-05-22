package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.HidDangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/addReport")
    public ResponseModel report(HidDangerDO hidDangerDO, @RequestParam(value="beforeImg",required=false) MultipartFile[] beforeImg,
                                @RequestParam(value="afterImg",required=false) MultipartFile[] afterImg,
                                @RequestParam(value="plan",required=false) MultipartFile plan,
                                @RequestParam(value="report",required=false) MultipartFile report){
        try {
            int userId  = 8;
            String result = hidDangerService.addReport(hidDangerDO,beforeImg,afterImg,plan,report);
            if(result.equals("1000")){
                return ResponseModel.build("1000","隐患上报成功！");
            }else {
                return ResponseModel.build("1000",result);
            }
        }catch (Exception e){
            System.out.println(e);
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
            System.out.println("错误:" + e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

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
            System.out.println(e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @PostMapping("/findDealByPage")
    public ResponseModel<PageData> findDealByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = hidDangerService.findDealByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @PostMapping("/findFinishByPage")
    public ResponseModel<PageData> findFinishByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = hidDangerService.findFinishByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1001","处理异常");
        }
    }


    @PostMapping("/findDealDetailByCode")
    public ResponseModel<Map<String,Object>> findDealDetailByCode(@RequestBody JSONObject json){
        try {
            String hidDangerCode = json.getString("hidDangerCode");
            Map<String,Object> map = hidDangerService.findDealDetailByCode(hidDangerCode);
            return ResponseModel.build("1000","分页查询成功！",map);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    @PostMapping("/findFinishDetailByCode")
    public ResponseModel<Map<String,Object>> findFinishDetailByCode(@RequestBody JSONObject json){
        try {
            String hidDangerCode = json.getString("hidDangerCode");
            Map<String,Object> map = hidDangerService.findFinishDetailByCode(hidDangerCode);
            return ResponseModel.build("1000","分页查询成功！",map);
        }catch (Exception e){
            System.out.println(e);
            return ResponseModel.build("1001","处理异常");
        }
    }








}
