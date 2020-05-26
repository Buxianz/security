package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerDO {

    private Integer id;
    private String hidDangerCode;
    private Integer hidDangerType;
    private Integer organizationId;//
    private String organizationName;//
    private String troubleshootingTime;//
    private String hidDangerContent;//隐患内容
    private String hidDangerGrade;//隐患等级
    private String ifControlMeasures;//
    private String ifRectificationPlan;//
    private Integer copyOrganizationId;
    private String copyOrganizationName;
    private String ifDeal;//
    private Double governanceFunds;//
    private String completionTime;//
    private String completionSituation;//
    private String rectificationPlan;//
    private String acceptanceReport;//
    private String processingStatus;//隐患处理状态：1、上报未整改 2、责令未整改 3、已通知待整改 4、已整改待审核 5、审核通过 6、审核不通过
    private Integer auditorId;
    private String auditorName;
    private String auditTime;
    private String auditReason;
    private Integer correctorId;
    private String correctorName;
    private String rectificationNoticeTime;
    private String rectificationOpinions;//整改意见
    private String rectificationEvaluate;//整改评估
    private String specifiedRectificationTime;//规定整改完成时间
    private Integer rectificationUnitId;//整改单位
    private String rectificationUnitName;//整改单位名
    private String rectificationNoticeAnnex;//整改通知附件
    private String requiredCompletionTime;//要求完成时间
    private Integer hidTypeThing;
    private Integer hidTypePerson;
    private Integer hidTypeManage;
    private String idt;
    private String udt;
    private Integer companyId;
    private String companyName;
    private Integer factoryId;
    private String factoryName;
    private Integer workshopId;
    private String workshopName;
    private Integer classId;
    private String className;
    private String color;


}
