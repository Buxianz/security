package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import com.rbi.security.tool.PageData;

public interface SafeFourLevelService {
    /**
     * 分页查询四级HSE教育培训台账
     */
    PageData findSafeFourLevelByPage(JSONObject json);
    /**
     * 根据id查询四级HSE教育培训台账
     */
    PagingSafeFourLevel getSafeFourLevelById(JSONObject json);
    /**
     * 搜索分页查询四级HSE教育培训台账
     */
    PageData findSafeFourLevel(JSONObject json);
    /**
     * 分页查询当前登录人的四级HSE教育培训台账
     */
    PagingSafeFourLevel findSafeFourLevelByOperatingStaff();
    /**
     * 添加
     */
    String insertSafeFourLevel(JSONObject json);
    /**
     * 删除
     */
    String deleteSafeFourLevelById(JSONArray result);
    /**
     * 修改
     */
    String updateSafeFourLevel(JSONObject json);
}
