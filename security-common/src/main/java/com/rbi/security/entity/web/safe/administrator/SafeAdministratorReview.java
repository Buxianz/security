package com.rbi.security.entity.web.safe.administrator;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.administrator
 * @NAME: SafeAdministratorReview
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:43
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
@Data
public class SafeAdministratorReview {
    private Integer id;
    private Integer safeAdministratorId;
    private Integer completionStatus;
    private String reasonForHandling;
    private Integer operatingStaff;
    private String processingTime;
    private String idt;
    private String udt;

}
