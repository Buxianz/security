package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONObject;
import com.rbi.security.entity.web.health.OccHealthExamine;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: OccHealthExamineService
 * @USER: "谢青"
 * @DATE: 2020/6/24
 * @TIME: 10:29
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 24
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 29
 * @PROJECT_NAME: security
 **/
public interface OccHealthExamineService {

    PageData findByPage(int pageNo, int pageSize);

    String add(OccHealthExamine occHealthExamine);

    String update(OccHealthExamine occHealthExamine);

    void delete(JSONObject json);
}
