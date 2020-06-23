package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthMaintain;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OccHealthMaintainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.health
 * @NAME: OccHealthMaintainController
 * @USER: "林新元"
 * @DATE: 2020/6/23
 * @TIME: 10:32
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 32
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/occHealthMaintain")
public class OccHealthMaintainController {
    private final static Logger logger = LoggerFactory.getLogger(OccHealthMaintainController.class);

    @Autowired(required = false)
    OccHealthMaintainService occHealthMaintainService;


    /**
     * 分页查询
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/findOccHealthMaintainByPage", method = RequestMethod.POST)
    public ResponseModel<PageData> findOccHealthMaintainByPage(@RequestBody JSONObject json) {
        try {
            PageData pageData = occHealthMaintainService.findOccHealthMaintainByPage(json);
            return ResponseModel.build("1000", "查询成功", pageData);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }


    /**
     * 单个查询
     *
     * @param json 编号id
     * @return
     */
    @RequestMapping(value = "/findOccHealthMaintainById", method = RequestMethod.POST)
    public ResponseModel<OccHealthMaintain> findOccHealthMaintainById(@RequestBody JSONObject json) {
        try {
            OccHealthMaintain occHealthMaintain = occHealthMaintainService.findOccHealthMaintainById(json);
            return ResponseModel.build("1000", "查询成功", occHealthMaintain);
        } catch (Exception e) {
            logger.error("单个查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

    /**
     * 添加
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/insertOccHealthMaintain", method = RequestMethod.POST)
    public ResponseModel insertOccHealthMaintain(@RequestBody JSONObject json) {
        try {
            String i=occHealthMaintainService.insertOccHealthMaintain(json);
            if (i.equals("1000")) {
                return ResponseModel.build("1000", "添加成功");
            }else {
                return ResponseModel.build("1001", "防护设备名称不可重复！添加失败");
            }
        } catch (Exception e) {
            logger.error("添加异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }


    /**
     * 删除
     *
     * @param request
     * @return
     * @RequestParam
     */
    @RequestMapping(value = "/deleteOccHealthMaintain", method = RequestMethod.POST)
    public ResponseModel deleteOccHealthMaintain(@RequestBody JSONObject request) {
        try {
            JSONArray result=request.getJSONArray("data");

            String i = occHealthMaintainService.deleteOccHealthMaintain(result);
            if (i.equals("1000")) {
                return ResponseModel.build("1000", "删除成功");
            } else {
                return ResponseModel.build("1001", i+"不存在");
            }
        } catch (Exception e) {
            logger.error("删除异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }


    /**
     * 修改
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/updateOccHealthMaintain", method = RequestMethod.POST)
    public ResponseModel updateOccHealthMaintain(@RequestBody JSONObject json) {
        try {
            String i = occHealthMaintainService.updateOccHealthMaintain(json);
            if (i.equals("1000")) {
                return ResponseModel.build("1000", "更新成功");
            } else {
                return ResponseModel.build("1001", "不存在");
            }
        } catch (Exception e) {
            logger.error("更新异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }
}
