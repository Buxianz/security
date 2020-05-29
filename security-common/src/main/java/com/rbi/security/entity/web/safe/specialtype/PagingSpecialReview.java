package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.specialtype
 * @NAME: PagingSpecialReview 复审名单分页查看类
 * @USER: "吴松达"
 * @DATE: 2020/5/27
 * @TIME: 17:07
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 17
 * @MINUTE: 07
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSpecialReview {
    /**
     * 特种人员复审id
     */
    private Integer id;
    /**
     * 特种人员培训档案id
     */
    private Integer specialPersonnelId;
    /**
     * 姓名
     */
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
     * 操作项目
     */
    private String operationItems;
    /**
     * 工龄
     */
    private String workingYears;
    /**
     * 操作证号
     */
    private String operationCertificateNo;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /*
    截止日期
     */
    private String deadline;
    /**
     * 完成状态
     */
    private Integer completionStatus;
    /**
     * 处理原因
     */
    private String reasonForHandling;
    /**
     * 处理时间
     */
    private String processingTime;
    /**
     * 操作人（名称）
     *
     */
    private String operatingStaff;
    /**
     * 创建时间
     */
    private String idt;
}
