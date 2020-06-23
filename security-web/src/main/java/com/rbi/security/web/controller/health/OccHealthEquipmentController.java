package com.rbi.security.web.controller.health;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthEquipment;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.OccHealthEquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.health
 * @NAME: OccHealthEquipmentController
 * @USER: "林新元"
 * @DATE: 2020/6/23
 * @TIME: 10:31
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 星期二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 31
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/occHealthEquipment")
public class OccHealthEquipmentController {
    private final static Logger logger = LoggerFactory.getLogger(OccHealthEquipmentController.class);

    @Autowired(required = false)
    OccHealthEquipmentService occHealthEquipmentService;


    /**
     * 分页查询
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/findOccHealthEquipmentByPage", method = RequestMethod.POST)
    public ResponseModel<PageData> findOccHealthEquipmentByPage(@RequestBody JSONObject json) {
        try {
            PageData pageData = occHealthEquipmentService.findOccHealthEquipmentByPage(json);
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
    @RequestMapping(value = "/findOccHealthEquipmentById", method = RequestMethod.POST)
    public ResponseModel<OccHealthEquipment> findOccHealthEquipmentById(@RequestBody JSONObject json) {
        try {
            OccHealthEquipment occHealthEquipment = occHealthEquipmentService.findOccHealthEquipmentById(json);
            return ResponseModel.build("1000", "查询成功", occHealthEquipment);
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
    @RequestMapping(value = "/insertOccHealthEquipment", method = RequestMethod.POST)
    public ResponseModel insertOccHealthEquipment(@RequestBody JSONObject json) {
        try {
            occHealthEquipmentService.insertOccHealthEquipment(json);
            return ResponseModel.build("1000", "添加成功");
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
    @RequestMapping(value = "/deleteOccHealthEquipment", method = RequestMethod.POST)
    public ResponseModel deleteOccHealthEquipment(@RequestBody JSONObject request) {
        try {
            JSONArray result=request.getJSONArray("data");

            String i = occHealthEquipmentService.deleteOccHealthEquipment(result);
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
    @RequestMapping(value = "/updateOccHealthEquipment", method = RequestMethod.POST)
    public ResponseModel updateOccHealthEquipment(@RequestBody JSONObject json) {
        try {
            String i = occHealthEquipmentService.updateOccHealthEquipment(json);
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
