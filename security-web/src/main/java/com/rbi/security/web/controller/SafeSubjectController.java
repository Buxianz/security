package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysRole;
import com.rbi.security.entity.web.safe.PagingSafe;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SafeSubjectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller
 * @NAME: SafeSubjectController
 * @USER: "林新元"
 * @DATE: 2020/5/25
 * @TIME: 17:53
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 星期一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 17
 * @MINUTE: 53
 * @PROJECT_NAME: security
 **/
@RestController
@RequestMapping("/safeSubject")
public class SafeSubjectController {
    private final static Logger logger = LoggerFactory.getLogger(SafeSubjectController.class);

    @Autowired(required = false)
    SafeSubjectService safeSubjectService;
    /**
     * 根据类型分页查询试题
     *
     * @param json
     * @return
     */
    @RequiresPermissions("SafeSubject:page")
    @RequestMapping(value = "/getSafeSubjectByPage", method = RequestMethod.POST)
    public ResponseModel<PageData> getSafeSubjectByPage(@RequestBody JSONObject json) {
        try {
            PageData pageData = safeSubjectService.getSafeSubjectByPage(json);
            return ResponseModel.build("1000", "查询成功", pageData);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常",e.getMessage());
        }
    }
    /**
     * 根据题库id分页查询试题
     *
     * @param json
     * @return
     */
    @RequiresPermissions("SafeSubject:id")
    @RequestMapping(value = "/getSafeSubjectByPageAndSubjectStoreId", method = RequestMethod.POST)
    public ResponseModel<PageData> getSafeSubjectByPageAndSubjectStoreId(@RequestBody JSONObject json) {
        try {
            PageData pageData = safeSubjectService.getSafeSubjectByPageAndSubjectStoreId(json);
            return ResponseModel.build("1000", "查询成功", pageData);
        } catch (Exception e) {
            logger.error("分页查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常",e.getMessage());
        }
    }
    /**
     * 添加
     *
     * @param json
     * @return
     */
    @RequiresPermissions("SafeSubject:add")
    @RequestMapping(value = "/insertSafeSubject", method = RequestMethod.POST)
    public ResponseModel insertSafeSubject(@RequestBody JSONObject json) {
        try {
            safeSubjectService.insertSafeSubject(json);
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
    @RequiresPermissions("SafeSubject:delete")
    @RequestMapping(value = "/deleteSafeSubjectById", method = RequestMethod.POST)
    public ResponseModel deleteSafeSubjectById(@RequestBody JSONObject request) {
        try {
            JSONArray result=request.getJSONArray("data");
            String i = safeSubjectService.deleteSafeSubjectById(result);
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
    @RequiresPermissions("SafeSubject:update")
    @RequestMapping(value = "/updateSafeSubjectById", method = RequestMethod.POST)
    public ResponseModel updateSafeSubjectById(@RequestBody JSONObject json) {
        try {
            String i = safeSubjectService.updateSafeSubjectById(json);
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
