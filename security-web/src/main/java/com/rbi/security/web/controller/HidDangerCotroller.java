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
            int userId  = 14;
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





}
