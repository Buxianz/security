package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDiseaseFactors;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: OccDiseaseFactorsService
 * @USER: "谢青"
 * @DATE: 2020/6/23
 * @TIME: 14:29
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 23
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 14
 * @MINUTE: 29
 * @PROJECT_NAME: security
 **/
public interface OccDiseaseFactorsService {
    PageData findByPage(int pageNo, int pageSize);

    String add(OccDiseaseFactors occDiseaseFactors);

    String update(OccDiseaseFactors occDiseaseFactors);

    void delete(JSONObject json);
}
