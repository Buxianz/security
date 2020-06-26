package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.entity.web.notice.SysNotice;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.HidDangerService;
import com.rbi.security.web.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: xieqing
 * @Description:
 * @Date: create in 2020/6/26 19:29
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController {
    @Autowired
    SysNoticeService sysNoticeService;

    /**
     * 分页
     * */
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = sysNoticeService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @PostMapping("/add")
    public ResponseModel add(SysNotice sysNotice, @RequestParam(value="file",required=false) MultipartFile file){
        try {
            sysNoticeService.add(sysNotice,file);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
