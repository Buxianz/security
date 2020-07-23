package com.rbi.security.entity.web.doubleduty;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.doubleduty
 * @NAME: DoubleDutyEvaluationContent
 * @USER: "谢青"
 * @DATE: 2020/7/21
 * @TIME: 11:54
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 11
 * @MINUTE: 54
 * @PROJECT_NAME: security
 **/
@Data
public class DoubleDutyEvaluationContent {
    private Integer id;
    private Integer evaluationId;
    private Integer content;
    private String fraction;
    private String selfEvaluation;
    private Integer selfFraction;
    private String checkResult;
    private Integer checkFraction;

}
