package com.rbi.security.entity.web.importlog;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.importlog
 * @NAME: ImportSpecialTrainingLOg
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 10:32
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 32
 * @PROJECT_NAME: security
 **/
@Data
public class ImportSpecialTrainingLOg {
    private Integer id;
    private Integer code;
    private String idCardNo;
    private String result;
    private String reason;
    private String idt;
}
