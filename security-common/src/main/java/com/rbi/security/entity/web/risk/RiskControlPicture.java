package com.rbi.security.entity.web.risk;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.risk
 * @NAME: riskControlPicture
 * @USER: "谢青"
 * @DATE: 2020/6/19
 * @TIME: 15:46
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 19
 * @DAY_NAME_SHORT: 周五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 15
 * @MINUTE: 46
 * @PROJECT_NAME: security
 **/
@Data
public class RiskControlPicture {
    private Integer id;
    private String riskCode;
    private String picture;
}
