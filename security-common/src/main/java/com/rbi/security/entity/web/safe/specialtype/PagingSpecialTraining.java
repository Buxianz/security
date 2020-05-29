package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.specialtype
 * @NAME: PagingSpecialTraining
 * @USER: "吴松达"  特种人员培训信息分页拆开
 * @DATE: 2020/5/26
 * @TIME: 10:43
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 26
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 10
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSpecialTraining {
    /**
     * 特种人员培训档案id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 公司人员id（外键）
     */
    private Integer companyPersonnelId;
    /**
     * 身份证号码
     */
    private String idCardNo;
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
     * 工龄
     */
    private String workingYears;
    /**
     * 理论成绩
     */
    private  String theoreticalAchievements;
    /**
     * 实际成绩
     */
    private String actualResults;
    /**
     * 操作证号
     */
    private String operationCertificateNo;
    /**
     * 发证日期
     */
    private String dateOfIssue;
    /**
     * 第一次复审成绩
     */
    private String oneReviewResults;
    /**
     * 第一次复审时间
     */
    private String oneReviewTime;
    /**
     * 第二次复审成绩
     */
    private String towReviewResults;
    /**
     * 第二次复审时间
     */
    private String towReviewTime;
    /**
     * 第三次复审成绩
     */
    private String threeReviewResults;
    /**
     * 第三次复审时间
     */
    private String threeReviewTime;
    /**
     * 第四次复审成绩
     */
    private String fourReviewResults;
    /**
     * 第四次复审时间
     */
    private String fourReviewTime;
    /**
     * 第五次复审成绩
     */
    private String fiveReviewResults;
    /**
     * 第五次复审时间
     */
    private String fiveReviewTime;
    /**
     * 第六次复审成绩
     */
    private String sixReviewResults;
    /**
     * 第六次复审时间
     */
    private String sixReviewTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 操作人（公司人员信息id）
     */
    private Integer operatingStaff;
    /**
     * 创建时间
     */
    private String idt;
    /**
     * 复审年限
     */
    private Integer validityPeriod;
}
