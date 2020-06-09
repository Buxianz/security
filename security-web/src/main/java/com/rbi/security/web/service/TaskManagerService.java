package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.task.TestPaperInfo;
import com.rbi.security.entity.web.safe.testpaper.TestPaper;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: TaskManagerService
 * @USER: "吴松达"
 * @DATE: 2020/6/5
 * @TIME: 10:56
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 05
 * @DAY_NAME_SHORT: 周五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 56
 * @PROJECT_NAME: security
 **/
public interface TaskManagerService {
    PageData<TestPaperInfo> pagingSpecialReview(int pageNo, int startIndex, int pageSize, int processingStatus) throws RuntimeException;
    TestPaper getTestPaper(int id) throws RuntimeException;
}
