package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SysPermission;
import com.rbi.security.entity.web.permission.PagingPermission;
import com.rbi.security.entity.web.permission.SysPermissionDTO;
import com.rbi.security.tool.PageData;

import java.util.List;
/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: a
 * @USER: "林新元"
 * @DATE: 2020/5/21
 * @TIME: 17:44
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 星期四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 44
 * @PROJECT_NAME: security
 **/
public interface SysPermissionService {

    /**
     * 分页查询
     **/
    PageData findSysPermissionByPage(int pageNo, int pageSize);

    /**
     * 单个查询
     */
    PagingPermission findSysPermissionById(Integer id);

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
