package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.imp
 * @NAME: TestQuestionsService 试卷服务
 * @USER: "吴松达"
 * @DATE: 2020/5/31
 * @TIME: 22:04
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 31
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 22
 * @MINUTE: 04
 * @PROJECT_NAME: security
 **/
public interface TestPaperService {
    SafeTestPaper insertTestQaper(SafeTestPaper safeTestPaper);
}
