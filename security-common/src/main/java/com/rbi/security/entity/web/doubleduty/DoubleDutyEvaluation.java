package com.rbi.security.entity.web.doubleduty;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.doubleduty
 * @NAME: DoubleDutyEvaluation
 * @USER: "谢青"
 * @DATE: 2020/7/14
 * @TIME: 11:56
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 14
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 11
 * @MINUTE: 56
 * @PROJECT_NAME: security
 **/
@Data
public class DoubleDutyEvaluation {
    private Integer id;
    private Integer templateId;
    private Integer personnelId;
    private String personnelName;
    private Integer selfFraction;////
    private Integer score;//////
    private String badSituation;
    private String correctSituation;
    private String writeTime;
    private String auditTime;
    private Integer auditorId;
    private String auditorName;
    private String status;
    private String statusName;
    private String idt;
    private String udt;

    private String position;
    private String templateName;
    private Integer organizationId;
    private String organizationName;
    private String companyName;
    private String factoryName;
    private String workshopName;
    private String className;

    private List<DoubleDutyEvaluationContent> doubleDutyEvaluationContents;

}
