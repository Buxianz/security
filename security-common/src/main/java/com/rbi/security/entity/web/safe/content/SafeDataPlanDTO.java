package com.rbi.security.entity.web.safe.content;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.content
 * @NAME: SafeDataPlan
 * @USER: "谢青"
 * @DATE: 2020/6/1
 * @TIME: 20:32
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 20
 * @MINUTE: 32
 * @PROJECT_NAME: security
 **/
@Data
public class SafeDataPlanDTO {
    //培训资料id
    private Integer id;
    private String resourceName;
    private String resourcePath;



}
