package com.rbi.security.entity.web;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web
 * @NAME: PlatformSettings
 * @USER: "吴松达"
 * @DATE: 2020/6/28
 * @TIME: 10:19
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 10
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
@Data
public class PlatformSettings {
    private Integer id;
    private Integer values;
    private String ramks;
    private Integer type;
}

