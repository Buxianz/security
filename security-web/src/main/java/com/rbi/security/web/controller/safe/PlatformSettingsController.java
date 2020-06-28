package com.rbi.security.web.controller.safe;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.PlatformSettings;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.PlatformSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: PlatformSettings
 * @USER: "吴松达"
 * @DATE: 2020/6/28
 * @TIME: 10:22
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 10
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
public class PlatformSettingsController {
    @Autowired
    PlatformSettingsService platformSettingsService;
    /**
     * 获取特种人员复审提前通知时间设置
     */
    @RequestMapping("/getSpecialDaySet")
    public ResponseModel<PlatformSettings> getSpecialDaySet(@RequestBody JSONObject date){
        try {
            return  ResponseModel.build("1000", "查询成功",platformSettingsService.getSpecialDaySet());
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
    /**
     * 更新特种人员复审提前通知时间设置
     */
    @RequestMapping("/updateSpecialDaySet")
    public ResponseModel updateSpecialDaySet(@RequestBody JSONObject date){
        try {
            PlatformSettings platformSettings= JSONObject.parseObject(date.toJSONString(), PlatformSettings.class);
            platformSettingsService.updateSpecialDaySet(platformSettings);
            return  ResponseModel.build("1000", "查询成功");
        }catch (Exception e){
            return ResponseModel.build("1001", e.getMessage());
        }
    }
}
