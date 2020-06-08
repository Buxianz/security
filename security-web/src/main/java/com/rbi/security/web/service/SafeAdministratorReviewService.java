package com.rbi.security.web.service;

import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: SafeAdministratorReviewService
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:21
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 21
 * @PROJECT_NAME: security
 **/
public interface SafeAdministratorReviewService {

    PageData findByPage(int pageNo, int pageSize);
}
