package com.rbi.security.entity.web.doubleduty;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.doubleduty
 * @NAME: DoubleDutyTemplate
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
public class DoubleDutyTemplate {
    private Integer id;
    private String job;
    private Integer personnelId;;
    private String idt;
    private String udt;

}
