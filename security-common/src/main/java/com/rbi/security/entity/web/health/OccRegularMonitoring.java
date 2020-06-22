package com.rbi.security.entity.web.health;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.health
 * @NAME: OccDailyMonitoring
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:10
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 10
 * @PROJECT_NAME: security
 **/
@Data
public class OccRegularMonitoring {
    private Integer id;
    private String time;
    private String monitoringOrganization;
    private String monitoringProject;
    private String monitoringResult;
    private String annex;
    private String idt;
    private String udt;
    private Integer operatingStaff;
}
