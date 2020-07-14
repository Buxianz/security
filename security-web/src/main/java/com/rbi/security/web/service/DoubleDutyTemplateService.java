package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: DoubleDutyTemplateService
 * @USER: "谢青"
 * @DATE: 2020/7/14
 * @TIME: 15:08
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 14
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 08
 * @PROJECT_NAME: security
 **/
public interface DoubleDutyTemplateService {

    String add(JSONObject json);

    String update(JSONObject json);

    String delete(JSONObject json);

    PageData findByPage(int pageNo, int pageSize);

    String release(JSONObject json);
}
