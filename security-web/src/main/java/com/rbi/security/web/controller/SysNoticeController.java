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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(SysNoticeController.class);

    /**
     * 分页
     * */
    @RequiresPermissions("informationNotice:page")
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = sysNoticeService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            logger.error("【综合信息分页】查询失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }

    /**
     * 添加
     **/
    @RequiresPermissions("publicInformation:add")
    @PostMapping("/add")
    public ResponseModel add(SysNotice sysNotice, @RequestParam(value="file",required=false) MultipartFile file){
        try {
            sysNoticeService.add(sysNotice,file);
            return ResponseModel.build("1000","添加成功！");
        }catch (Exception e){
            logger.error("【综合信息添加】失败！，ERROR：{}",e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
