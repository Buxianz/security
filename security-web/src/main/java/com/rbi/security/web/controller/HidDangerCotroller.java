package com.rbi.security.web.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.hid.HidDangerDTO;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.HidDangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
@RequestMapping("/hid")
public class HidDangerCotroller {
    @Autowired
    HidDangerService hidDangerService;


    @PostMapping("/addReport")
    public ResponseModel report(HidDangerDTO hidDangerDTO,@RequestParam(value="beforeImg",required=false) MultipartFile[] beforeImg,
                                @RequestParam(value="afterImg",required=false) MultipartFile[] afterImg,
                                @RequestParam(value="plan",required=false) MultipartFile plan,
                                @RequestParam(value="report",required=false) MultipartFile report){
        try {
            int userId  = 1;
            String result = hidDangerService.addReport(userId,hidDangerDTO,beforeImg,afterImg,plan,report);
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
}
