package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerProcessDTO {
    private Integer id;
    private String hidDangerCode;
    private Integer organizationId;
    private Integer reportPersonId;
    private Integer reportOrganizationId;
    private Integer orderSendPersonId;
    private Integer orderSendOrganizationId;
    private String organizationPrincipalId;
    private Integer ifDeal;
    private Integer dealWay;
    private Integer auditorId;
    private String dealTime;
    private String reportPersonName;
    private String reportOrganizationName;
    private String orderSendPersonName;
    private String orderSendOrganizationName;
    private String organizationPrincipalName;
    private String idt;//隐患处理状态：1、上报未整改 2、责令未整改 3、已通知待整改 4、已整改待审核 5、审核通过 6、审核不通过
    private String udt;

}
