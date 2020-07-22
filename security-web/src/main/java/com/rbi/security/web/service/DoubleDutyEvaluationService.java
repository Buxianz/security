package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: DoubleDutyEvaluationService
 * @USER: "谢青"
 * @DATE: 2020/7/22
 * @TIME: 10:24
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 24
 * @PROJECT_NAME: security
 **/
public interface DoubleDutyEvaluationService {
    PageData findPersonelByPage(int pageNo, int pageSize);

    PageData findAuditByPage(int pageNo, int pageSize);

    String write(JSONObject json);
}
