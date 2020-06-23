package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthEndanger;
import com.rbi.security.tool.PageData;

public interface OccHealthEndangerService {
    /**
     * 分页查询
     **/
    PageData findOccHealthEndangerByPage(JSONObject json);

    /**
     * 单个查询
     */
    OccHealthEndanger findOccHealthEndangerById(JSONObject json);

    /**
     * 添加
     */
    void insertOccHealthEndanger(JSONObject json);

    /**
     * 删除
     */
    String deleteOccHealthEndanger(JSONArray result);

    /**
     * 修改
     */
    String updateOccHealthEndanger(JSONObject json);
}
