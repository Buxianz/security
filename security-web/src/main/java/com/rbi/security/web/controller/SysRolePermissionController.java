package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SysRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysRolePermission")
public class SysRolePermissionController {

    private final static Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

    @Autowired(required = false)
    SysRolePermissionService sysRolePermissionService;



    /**
     * 分页查询
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/findSysRolePermissionByPage", method = RequestMethod.POST)
    public ResponseModel<PageData> findSysRolePermissionByPage(@RequestBody JSONObject json) {
        try {
//                SysUser local = CommonUtil.getCurrentUser();
//                String uuid = local.getUuid();
            int pageNo = json.getInteger("pageNo");
            int pageSize = json.getByteValue("pageSize");
            PageData pageData = sysRolePermissionService.findSysRolePermissionByPage(pageNo, pageSize);
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
    @RequestMapping(value = "/findSysRolePermissionById", method = RequestMethod.POST)
    public ResponseModel<SysRolePermission> findSysRolePermissionById(@RequestBody JSONObject json) {
        try {
            Integer Id = json.getInteger("id");
            SysRolePermission sysRolePermission = sysRolePermissionService.findSysRolePermissionById(Id);
            return ResponseModel.build("1000", "查询成功", sysRolePermission);
        } catch (Exception e) {
            logger.error("单个查询异常，ERROR：{}", e);
            return ResponseModel.build("1001", "服务器处理异常");
        }
    }



//    /**
//     * 根据角色编号查询权限信息
//     * @param json
//     * @return
//     */
//    @RequestMapping(value = "/findSysPermissionByRoleCode",method = RequestMethod.POST)
//    public ResponseModel<SysPermission> findSysPermissionByRoleId(@RequestBody JSONObject json) {
//        try {
//            Integer RoleId = json.getInteger("RoleId");
//            SysPermission sysPermission = sysPermissionService.findSysPermissionByRoleCode(RoleId);
//            return ResponseModel.build("1000", "查询成功", sysPermission);
//        } catch (Exception e) {
//            logger.error("单个查询异常，ERROR：{}", e);
//            return ResponseModel.build("1001", "服务器处理异常");
//        }
//    }


    /**
     * 添加
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertSysRolePermission", method = RequestMethod.POST)
    public ResponseModel insertSysRolePermission(@RequestBody JSONObject request) {
        try {
//            JSONArray result=request.getJSONArray("data");
            sysRolePermissionService.insertSysRolePermission(request);
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
    @RequestMapping(value = "/deleteSysRolePermissionById", method = RequestMethod.POST)
    public ResponseModel deleteSysRolePermissionById(@RequestBody JSONObject request) {
        try {
            JSONArray result=request.getJSONArray("data");

            String i = sysRolePermissionService.deleteSysRolePermissionById(result);
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
    @RequestMapping(value = "/updateSysRolePermission", method = RequestMethod.POST)
    public ResponseModel updateSysRolePermission(@RequestBody JSONObject json) {
        try {
            String i = sysRolePermissionService.updateSysRolePermission(json);
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
