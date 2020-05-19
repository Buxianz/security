package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.tool.PageData;

import java.util.List;

public interface SysPermissionService {

    List<SysPermission> findSysPermissionAll();
    /**
     * 分页查询
     **/
    PageData findSysPermissionByPage(int pageNo, int pageSize);

    /**
     * 单个查询
     */
    SysPermission findSysPermissionById(Integer id);

    /**
     * 添加
     */
    void insertSysPermission(JSONObject json);

    /**
     * 删除我拥有的书籍
     */
    String deleteSysPermissionById(JSONArray result);

    /**
     * 修改
     */
    String updateSysPermission(JSONObject json);
}
