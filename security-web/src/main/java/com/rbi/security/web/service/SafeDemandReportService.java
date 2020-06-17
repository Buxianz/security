package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.content.SafeDataPlan;
import com.rbi.security.entity.web.safe.demand.PagingTraniningNeeds;
import com.rbi.security.entity.web.safe.demand.SafeTrainingNeeds;
import com.rbi.security.entity.web.safe.testpaper.SafeTestPaper;
import com.rbi.security.tool.PageData;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: SafeDemandReportService 需求提报处理模块
 * @USER: "吴松达"
 * @DATE: 2020/5/31
 * @TIME: 22:15
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 31
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 22
 * @MINUTE: 15
 * @PROJECT_NAME: security
 **/
public interface SafeDemandReportService {
    void insertSafeDemandReport(SafeTrainingNeeds safeTrainingNeeds) throws RuntimeException;
    PageData<PagingTraniningNeeds> pagingSafeDemandReport(int pageNo, int pageSize, int startIndex,int processingStatus) throws RuntimeException;
    void handlingRequirements(SafeTrainingNeeds safeTrainingNeeds, List<SafeDataPlan> safeDataPlanList, SafeTestPaper safeTestPaper) throws RuntimeException;
    SafeTrainingNeeds getTrainingNeedsById(int id) throws RuntimeException;
}
