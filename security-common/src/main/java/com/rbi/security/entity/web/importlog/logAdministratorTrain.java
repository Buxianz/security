package com.rbi.security.entity.web.importlog;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.importlog
 * @NAME: logAdministratorTrain
 * @USER: "谢青"
 * @DATE: 2020/6/28
 * @TIME: 16:49
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 16
 * @MINUTE: 49
 * @PROJECT_NAME: security
 **/
@Data
public class logAdministratorTrain {
    private Integer id;
    private Integer code;
    private String idNum;
    private String result;
    private String reason;
    private String idt;

}
