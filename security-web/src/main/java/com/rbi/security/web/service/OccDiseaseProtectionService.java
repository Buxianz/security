package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDiseaseProtection;
import com.rbi.security.tool.PageData;

/**
 * @Author: 谢青
 * @Description:
 * @Date: create in 2020/6/26 10:22
 */
public interface OccDiseaseProtectionService {

    PageData findByPage(int pageNo, int pageSize);

    void add(OccDiseaseProtection occDiseaseProtection);

    void update(OccDiseaseProtection occDiseaseProtection);

    void delete(JSONObject json);
}
