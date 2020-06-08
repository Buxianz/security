package com.rbi.security.entity.web.safe.content;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.specialtype
 * @NAME: SafeTrainingMaterials
 * @USER: "谢青"
 * @DATE: 2020/5/27
 * @TIME: 10:51
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 51
 * @PROJECT_NAME: security
 **/
@Data
public class SafeTrainingMaterials {
    private Integer id;
    private String resourceName;
    private Integer contentCategoryId;
    private String contentCategoryName;
    private String resourceType;
    private String resourcePath;
    private Integer operatingStaff;
    private String operatorName;
    private String idt;
    private String udt;

}
