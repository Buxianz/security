package com.rbi.security.entity.web;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: LearningInformation 获取学习任务
 * @USER: "吴松达"
 * @DATE: 2020/6/11
 * @TIME: 10:43
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 11
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 10
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
@Data
public class LearningInformations {
    /**
     * 培训计划id
     */
    private Integer id;
    /**
     * 培训内容
     */
    private String trainingContent;
    /**
     * 培训开始时间
     */
    private String startTime;
    /**
     * 培训结束时间
     */
    private String endTime;
    /**
     * 是否截至 1：进行中  2：已截至
     */
    private Integer upTo;

}
