package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeFourLevelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: SafeFourLevelController
 * @USER: "林新元"
 * @DATE: 2020/5/29
 * @TIME: 10:22
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 29
 * @DAY_NAME_SHORT: 星期五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/safeFourLevel")
public class SafeFourLevelController {
    private final static Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

    @Autowired(required = false)
    SafeFourLevelService safeFourLevelService;
    /**
     * 分页查询四级HSE教育培训台账
     *
     * @param json
     * @return
     */
    @RequiresPermissions("safeFour:page")
    @RequestMapping(value = "/findSafeFourLevelByPage", method = RequestMethod.POST)
    public ResponseModel<PageData> findSafeFourLevelByPage(@RequestBody JSONObject json) {
        try {
            PageData pageData = safeFourLevelService.findSafeFourLevelByPage(json);
            return ResponseModel.build("1000", "查询成功", pageData);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }
    /**
     * 根据id查询四级HSE教育培训台账
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/findSafeFourLevelById", method = RequestMethod.POST)
    public ResponseModel<PagingSafeFourLevel> findSafeFourLevelById(@RequestBody JSONObject json) {
        try {
            PagingSafeFourLevel pagingSafeFourLevel = safeFourLevelService.getSafeFourLevelById(json);
            return ResponseModel.build("1000", "查询成功", pagingSafeFourLevel);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

    /**
     * 搜索分页查询四级HSE教育培训台账
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/findSafeFourLevel", method = RequestMethod.POST)
    public ResponseModel<PageData> findSafeFourLevel(@RequestBody JSONObject json) {
        try {
            PageData pageData = safeFourLevelService.findSafeFourLevel(json);
            return ResponseModel.build("1000", "查询成功", pageData);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

    /**
     * 分页查询当前登录人的四级HSE教育培训台账
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/findSafeFourLevelByOperatingStaff", method = RequestMethod.POST)
    public ResponseModel<PagingSafeFourLevel> findSafeFourLevelByOperatingStaff() {
        try {
            PagingSafeFourLevel pagingSafeFourLevel = safeFourLevelService.findSafeFourLevelByOperatingStaff();
            return ResponseModel.build("1000", "查询成功", pagingSafeFourLevel);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }

    /**
     * 添加四级HSE教育培训台账
     *
     * @param json
     * @return
     */
    @RequiresPermissions("safeFour:add")
    @RequestMapping(value = "/insertSafeFourLevel", method = RequestMethod.POST)
    public ResponseModel insertSafeFourLevel(@RequestBody JSONObject json) {
        try {
            String i=safeFourLevelService.insertSafeFourLevel(json);
            if (i.equals("1000")) {
                return ResponseModel.build("1000", "添加成功");
            }else if (i.equals("1002")){
                return ResponseModel.build("1002", "添加失败，身份证号不存在！");
            }else {
                return ResponseModel.build("1003", "添加失败，此人台账已存在！");
            }
        } catch (Exception e) {
            logger.error("添加异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }
    /**
     * 删除四级HSE教育培训台账
     *
     * @param request
     * @return
     * @RequestParam
     */
    @RequiresPermissions("safeFour:delete")
    @RequestMapping(value = "/deleteSafeFourLevelById", method = RequestMethod.POST)
    public ResponseModel deleteSafeFourLevelById(@RequestBody JSONObject request) {
        try {
            JSONArray result=request.getJSONArray("data");
            String i = safeFourLevelService.deleteSafeFourLevelById(result);
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
     * 修改四级HSE教育培训台账
     *
     * @param json
     * @return
     */
    @RequiresPermissions("safeFour:update")
    @RequestMapping(value = "/updateSafeFourLevel", method = RequestMethod.POST)
    public ResponseModel updateSafeFourLevel(@RequestBody JSONObject json) {
        try {
            String i = safeFourLevelService.updateSafeFourLevel(json);
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

    /**
     * 导入
     * */
    @RequiresPermissions("safeFour:import")
    @PostMapping("/excelImport")
    public ResponseModel excelImport(MultipartFile file) {
        try {
            Map<String,Object> map = safeFourLevelService.excelImport(file);
            return ResponseModel.build("1000", "导入完成", map);
        } catch (Exception e) {
            System.out.println("错误："+e);
            return ResponseModel.build("1002", "服务器处理异常", e);
        }
    }

    @RequiresPermissions("safeFour:export")
    @PostMapping("/excelwrite")
    public ResponseModel excelwrite() {
        try {
            Map<String,Object> map = safeFourLevelService.excelwrite();
            return ResponseModel.build("1000", "导出完成", map);
        } catch (Exception e) {
            System.out.println("错误："+e);
            return ResponseModel.build("1002", "服务器处理异常", e);
        }
    }
}
