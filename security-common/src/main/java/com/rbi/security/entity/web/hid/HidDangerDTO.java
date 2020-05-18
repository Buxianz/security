package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerDTO {

    private Integer id;

    private String hidDangerCode;
    private Integer organizationId;
    private String organizationName;

    private String troubleshootingTime;
    private String hiddenDangerContent;
    private Integer hiddenDangerGrade;
    private Integer ifControlMeasures;
    private Integer ifRectificationPlan;
    private Integer copyOrganizationId;
    private String copyOrganizationName;

    private Integer ifDeal;
    private Double governanceFunds;
    private String completionTime;
    private String completionSituation;
    private String rectificationPlan;
    private String acceptanceReport;

    private String processingStatus;//隐患处理状态：1、上报未整改 2、责令未整改 3、已通知待整改 4、已整改待审核 5、审核通过 6、审核不通过

    private Integer companyId;
    private Integer auditorId;
    private Integer correctorId;

    private String rectificationOpinions;
    private String specifiedRectificationTime;
    private String rectificationUnitId;
    private String rectificationContent;
    private String requiredCompletionTime;
    private String rectificationNoticeAnnex;
    private String companyName;
    private String auditorName;
    private String correctorName;

    private String idt;
    private String udt;


}
