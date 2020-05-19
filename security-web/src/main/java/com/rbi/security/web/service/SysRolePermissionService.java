package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.tool.PageData;

public interface SysRolePermissionService {

    /**
     * 分页查询
     **/
    PageData findSysRolePermissionByPage(int pageNo, int pageSize);

    /**
     * 单个查询
     */
    SysRolePermission findSysRolePermissionById(Integer id);

    /**
     * 添加
     */
    void insertSysRolePermission(JSONObject json,JSONArray result);

    /**
     * 删除
     */
    String deleteSysRolePermissionById(JSONArray result);

    /**
     * 修改
     */
    String updateSysRolePermission(JSONObject json);
}
