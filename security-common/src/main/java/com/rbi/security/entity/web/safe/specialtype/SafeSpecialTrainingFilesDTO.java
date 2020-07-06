package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeSpecialTrainingFilesDTO)实体类
 * @USER: "谢青"
 * @author makejava
 * @since 2020-07-06 10:04:59
 */
@Data
public class SafeSpecialTrainingFilesDTO implements Serializable {

    private String name;
    private String gender;
    private String idCardNo;
    private String degreeOfEducation;
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

    private String yearsOfWork;
    private String oneReviewResults;
    private String oneReviewTime;
    private String towReviewResults;
    private String towReviewTime;
    private String threeReviewResults;
    private String threeReviewTime;
    private String fourReviewResults;
    private String fourReviewTime;
    private String fiveReviewResults;
    private String fiveReviewTime;
    private String sixReviewResults;
    private String sixReviewTime;
    private String remarks;

}