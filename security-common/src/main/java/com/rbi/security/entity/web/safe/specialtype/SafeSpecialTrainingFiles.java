package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeSpecialTrainingFiles)实体类
 * @USER: "吴松达"
 * @author makejava
 * @since 2020-05-26 10:04:59
 */
@Data
public class SafeSpecialTrainingFiles implements Serializable {
    private static final long serialVersionUID = 161851377491293595L;
    /**
    * 特种人员培训档案id
    */
    private Integer id;
    /**
    * 身份证号码
    */
    private String idCardNo;
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
     *取证成绩
     */
    private String evidenceCollectionResults;
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


}