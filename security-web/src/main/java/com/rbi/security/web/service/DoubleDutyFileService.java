package com.rbi.security.web.service;

import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: DoubleDutyFileService
 * @USER: "谢青"
 * @DATE: 2020/7/21
 * @TIME: 11:45
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 11
 * @MINUTE: 45
 * @PROJECT_NAME: security
 **/
public interface DoubleDutyFileService {

    PageData findByPage(int pageNo, int pageSize);
}
