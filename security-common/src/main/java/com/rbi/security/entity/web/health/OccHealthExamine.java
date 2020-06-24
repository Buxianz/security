package com.rbi.security.entity.web.health;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.health
 * @NAME: OccHealthExamine
 * @USER: "谢青"
 * @DATE: 2020/6/24
 * @TIME: 10:22
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 24
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 22
 * @PROJECT_NAME: security
 **/
@Data
public class OccHealthExamine {
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String marriage;
    private String phone;
    private String organization;
    private String idNum;
    private String factor;
    private String workType;
    private String deadline;
    private String workTime;
    private String leaveTime;
    private String remark;
    private String unitName;
    private String reserveTime;
    private String idt;
    private String udt;
    private Integer operatingStaff;

}
