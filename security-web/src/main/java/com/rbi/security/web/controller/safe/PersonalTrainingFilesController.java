package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.PlatformSettings;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.PersonalTrainingFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: PersonalTrainingFilesController
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 14:45
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
@RestController
public class PersonalTrainingFilesController {
    @Autowired
    PersonalTrainingFilesService personalTrainingFilesService;
    /**
     * 获取自身培训档案
     */
    @RequestMapping("/getPersonalTrainingFiles")
    public ResponseModel getPersonalTrainingFiles(@RequestBody JSONObject date){
        try {
            int pageNo = date.getInteger("pageNo");
            int pageSize = date.getInteger("pageSize");
            int startIndex=(pageNo-1)*pageSize;
            return  ResponseModel.build("1000", "查询成功",personalTrainingFilesService.getPersonalTrainingFiles(pageNo,pageSize,startIndex));
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
