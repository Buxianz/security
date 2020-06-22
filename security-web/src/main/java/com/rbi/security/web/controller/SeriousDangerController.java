package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.SeriousDanger.PagingSeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDanger;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SeriousDangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: SeriousDangerController
 * @USER: "林新元"
 * @DATE: 2020/6/16
 * @TIME: 15:28
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 16
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 28
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/seriousDanger")
public class SeriousDangerController {

    @Autowired(required = false)
    SeriousDangerService seriousDangerService;
    /**
     * 添加重大危险源
     **/
    @PostMapping("/insertSeriousDanger")
    public ResponseModel insertSeriousDanger(SeriousDanger seriousDanger, @RequestParam(value="seriousDangerPicture",required=false) MultipartFile[] seriousDangerPicture){
        try {
            String result = seriousDangerService.insertSeriousDanger(seriousDanger,seriousDangerPicture);
            if(result.equals("1000")){
                return ResponseModel.build("1000","添加成功！");
            }else {
                return ResponseModel.build("1001",result+"添加失败");
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }

    }

    /**
     * 分页查询重大危险源
     **/
    @PostMapping("/findSeriousDangerByPage")
    public ResponseModel<PageData> findSeriousDangerByPage(@RequestBody JSONObject json){
        try {
            PageData pageData = seriousDangerService.findSeriousDangerByPage(json);
            return ResponseModel.build("1000","查询报成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }

    }

    /**
     * 根据重大危险源名称查询重大危险源
     **/
    @PostMapping("/findSeriousDangerByPageAndName")
    public ResponseModel<PageData> findSeriousDangerByPageAndName(@RequestBody JSONObject json){
        try {
            PageData pageData = seriousDangerService.findSeriousDangerByPageAndName(json);
            return ResponseModel.build("1000","查询报成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }

    }

    /**
     * 查询重大危险源
     **/
    @PostMapping("/findSeriousDangerByID")
    public ResponseModel<PagingSeriousDanger> findSeriousDangerByID(@RequestBody JSONObject json){
        try {
            PagingSeriousDanger pagingSeriousDanger = seriousDangerService.findSeriousDangerByID(json);
            return ResponseModel.build("1000","查询报成功！",pagingSeriousDanger);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }

    }


    /**
     * 修改重大危险源
     **/
    @PostMapping("/updateSeriousDanger")
    public ResponseModel updateSeriousDanger(SeriousDanger seriousDanger,Integer pictureId, @RequestParam(value="seriousDangerPicture",required=false) MultipartFile[] seriousDangerPicture){
        try {
            String result = seriousDangerService.updateSeriousDanger(seriousDanger,pictureId,seriousDangerPicture);
            if(result.equals("1000")){
                return ResponseModel.build("1000","修改成功！");
            }else {
                return ResponseModel.build("1001",result+"修改失败");
            }
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }

    }
}
