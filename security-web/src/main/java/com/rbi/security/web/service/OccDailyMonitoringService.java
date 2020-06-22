package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccDailyMonitoring;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: OccDailyMonitoringService
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:19
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
public interface OccDailyMonitoringService {
    void add(OccDailyMonitoring occDailyMonitoring);

    void update(OccDailyMonitoring occDailyMonitoring);

    void delete(JSONObject json);

    PageData findByPage(int pageNo, int pageSize);
}
