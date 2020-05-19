package com.rbi.security.entity.web.hid;

import lombok.Data;

@Data
public class HidDangerProcessDTO {
    private Integer id;

    private String hidDangerCode;
    private Integer operatorId;
    private String operatorName;
    private Integer operatorOrganizationId;
    private String operatorOrganizationName;
    private Integer organizationId;
    private String organizationName;
    private String ifDeal;
    private String dealWay;//处理方式 1隐患上报 2责令下发 3处理 4通报整改 5审核通过 6审核不通过
    private String dealTime;
    private Integer correctorId;
    private String correctorName;
    private Integer organizationPrincipalId;
    private String organizationPrincipalName;
    private String idt;
    private String udt;

}
