package com.rbi.security.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.permission.SysPermissionDTO;
import com.rbi.security.tool.PageData;
import com.rbi.security.tool.ResponseModel;
import com.rbi.security.web.service.SysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController {
        private final static Logger logger = LoggerFactory.getLogger(SysPermissionController.class);

        @Autowired(required = false)
        SysPermissionService sysPermissionService;

    /**
     * 查询所有权限信息(树型）
     * @return
     */
    @PostMapping("/findSysPermissionAll")
    public ResponseModel<List<SysPermissionDTO>> findSysPermissionAll(){
        try {
            List<SysPermissionDTO> sysPermissionDTOList=sysPermissionService.findSysPermissionAll();
            if (sysPermissionDTOList!=null) {
                return ResponseModel.build("1000", "查询成功",sysPermissionDTOList);
            }else {
                return ResponseModel.build("1001","查询失败");
            }
        } catch (Exception e) {
            logger.error("【权限配置类】查询权限信息异常，ERROR：{}",e);
            return ResponseModel.build("1001","服务器处理失败");
        }
    }


        /**
         * 分页查询
         *
         * @param json
         * @return
         */
        @RequestMapping(value = "/findSysPermissionByPage", method = RequestMethod.POST)
        public ResponseModel<PageData> findSysPermissionByPage(@RequestBody JSONObject json) {
            try {
//                SysUser local = CommonUtil.getCurrentUser();
//                String uuid = local.getUuid();
                int pageNo = json.getInteger("pageNo");
                int pageSize = json.getByteValue("pageSize");
                PageData pageData = sysPermissionService.findSysPermissionByPage(pageNo, pageSize);
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
        @RequestMapping(value = "/findSysPermissionById", method = RequestMethod.POST)
        public ResponseModel<SysPermission> findSysPermissionById(@RequestBody JSONObject json) {
            try {
                Integer Id = json.getInteger("id");
                SysPermission sysPermission = sysPermissionService.findSysPermissionById(Id);
                return ResponseModel.build("1000", "查询成功", sysPermission);
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
        @RequestMapping(value = "/insertSysPermission", method = RequestMethod.POST)
        public ResponseModel insertSysPermission(@RequestBody JSONObject json) {
            try {
                sysPermissionService.insertSysPermission(json);
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
        @RequestMapping(value = "/deleteSysPermissionById", method = RequestMethod.POST)
        public ResponseModel deleteSysPermissionById(@RequestBody JSONObject request) {
            try {
            JSONArray result=request.getJSONArray("data");

                String i = sysPermissionService.deleteSysPermissionById(result);
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
        @RequestMapping(value = "/updateSysPermission", method = RequestMethod.POST)
        public ResponseModel updateSysPermission(@RequestBody JSONObject json) {
            try {
                String i = sysPermissionService.updateSysPermission(json);
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
