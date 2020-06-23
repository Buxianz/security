package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthEquipment;
import com.rbi.security.tool.PageData;

public interface OccHealthEquipmentService {

    /**
     * 分页查询
     **/
    PageData findOccHealthEquipmentByPage(JSONObject json);

    /**
     * 单个查询
     */
    OccHealthEquipment findOccHealthEquipmentById(JSONObject json);

    /**
     * 添加
     */
    String insertOccHealthEquipment(JSONObject json);

    /**
     * 删除
     */
    String deleteOccHealthEquipment(JSONArray result);

    /**
     * 修改
     */
    String updateOccHealthEquipment(JSONObject json);
}
