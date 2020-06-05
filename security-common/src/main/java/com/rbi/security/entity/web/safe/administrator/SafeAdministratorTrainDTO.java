package com.rbi.security.entity.web.safe.administrator;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeAdministratorTrainDTO)查询实体类
 * @author 谢青
 * @since 2020-05-28 10:55:22
 */
@Data
public class SafeAdministratorTrainDTO{
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
    private String oneTrainingTime;
    private String twoTrainingTime;
    private String threeTrainingTime;
    private String remarks;
    private Integer operatingStaff;
    private String idt;
    private String udt;
}