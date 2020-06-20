package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.specialtype
 * @NAME: ExportReview
 * @USER: "吴松达"
 * @DATE: 2020/6/17
 * @TIME: 11:20
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 11
 * @MINUTE: 20
 * @PROJECT_NAME: security
 **/
@Data
public class ExportReview {
    private Integer no;
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 文化程度
     */
    private String degreeOfEducation;
    /**
     * 工种名称
     */
    private String typeOfWork;
    /**
     * 操作项目
     */
    private String operationItems;
    /**
     * 工种年限
     */
    private String yearsOfWork;
    /**
     * 操作证号
     */
    private String operationCertificateNo;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 备注
     */
    private String remarks;
}
