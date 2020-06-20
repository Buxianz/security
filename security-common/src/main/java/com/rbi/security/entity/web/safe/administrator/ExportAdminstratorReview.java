package com.rbi.security.entity.web.safe.administrator;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.administrator
 * @NAME: ExportAdminstratorReview  导出主要负责人所需用到的类
 * @USER: "吴松达"
 * @DATE: 2020/6/18
 * @TIME: 10:09
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 18
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 10
 * @MINUTE: 09
 * @PROJECT_NAME: security
 **/
@Data
public class ExportAdminstratorReview {
    /**
     * 序号
     */
    private Integer no;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 单位
     */
    private String unit;
    /**
     * 发证时间
     */
    private String dateOfIssue;
    /**
     * 性别
     */
    private String gender;

    /**
     * 文化程度
     */
    private String degreeOfEducation;
    /**
     * 合格证类型
     */
    private String typeOfCertificate;

}
