package com.rbi.security.entity.web.risk;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.risk
 * @NAME: RiskControl
 * @USER: "谢青"
 * @DATE: 2020/6/18
 * @TIME: 11:14
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 11
 * @MINUTE: 14
 * @PROJECT_NAME: security
 **/
@Data
public class RiskControl {
    private Integer id;
    private String riskCode;
    private String riskType;
    private String taskCode;
    private String code;
    private Integer organizationId;
    private String organizationName;
    private String workType;
    private String step;
    private String harmName;
    private String harmKind;
    private String harmDescription;
    private String riskDescription;
    private String riskKind;
    private String riskCategory;
    private String exposeInfo;
    private String controlMeasures;
    private Double consequence;
    private Double expose;
    private Double possibility;
    private Double riskValue;
    private String riskGrad;
    private String adviceMeasures;
    private Double measuresEffective;
    private Double measuresCost;
    private Double measuresResult;
    private String measuresUse;

    private Integer companyId;
    private String companyName;
    private Integer factoryId;
    private String factoryName;
    private Integer workshopId;
    private String workshopName;
    private Integer classId;
    private String className;
    private String evaluateTime;
    private String idt;
    private String udt;
}
