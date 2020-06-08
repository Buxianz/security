package com.rbi.security.entity.web.safe.administrator;

import lombok.Data;

/**
 * (SafeAdministratorTrainDTO)查询实体类
 * @author 谢青
 * @since 2020-05-28 10:55:22
 */
@Data
public class SafeAdministratorReviewDTO {
    private Integer id;
    private String idCardNo;
    private String name;
    private String gender;
    private String degreeOfEducation;
    private Integer companyPersonnelId;
    private String unit;
    private String dateOfIssue;
    private String termOfValidity;
    private String typeOfCertificate;
//    private String oneTrainingTime;
//    private String twoTrainingTime;
//    private String threeTrainingTime;

    private Integer safeAdministratorId;
    private Integer completionStatus;
    private String reasonForHandling;
    private String processingTime;
    private Integer operatingStaff;
    private String idt;
    private String udt;
}