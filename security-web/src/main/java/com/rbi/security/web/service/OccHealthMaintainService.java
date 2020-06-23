package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthMaintain;
import com.rbi.security.tool.PageData;

public interface OccHealthMaintainService {

    /**
     * 分页查询
     **/
    PageData findOccHealthMaintainByPage(JSONObject json);

    /**
     * 单个查询
     */
    OccHealthMaintain findOccHealthMaintainById(JSONObject json);

    /**
     * 添加
     */
    String insertOccHealthMaintain(JSONObject json);

    /**
     * 删除
     */
    String deleteOccHealthMaintain(JSONArray result);

    /**
     * 修改
     */
    String updateOccHealthMaintain(JSONObject json);
}
