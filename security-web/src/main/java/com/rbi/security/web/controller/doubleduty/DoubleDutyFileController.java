package com.rbi.security.web.controller.doubleduty;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.DoubleDutyFileService;
import com.rbi.security.web.service.DoubleDutyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.doubleduty
 * @NAME: DoubleDutyFileController
 * @USER: "谢青"
 * @DATE: 2020/7/14
 * @TIME: 12:00
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 14
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 12
 * @MINUTE: 00
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/db_file")
public class DoubleDutyFileController {
    @Autowired
    DoubleDutyFileService doubleDutyFileService;

    /**
     * 一岗双责档案分页
     **/
    @PostMapping("/findByPage")
    public ResponseModel<PageData> findByPage(@RequestBody JSONObject json){
        try {
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getInteger("pageSize");
            PageData pageData = doubleDutyFileService.findByPage(pageNo,pageSize);
            return ResponseModel.build("1000","分页查询成功！",pageData);
        }catch (Exception e){
            System.out.println("错误："+e);
            return ResponseModel.build("1001","处理异常");
        }
    }


}
