package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeSpecialTrainingFiles)实体类
 * @USER: "吴松达" 特种人员培训信息表
 * @author makejava
 * @since 2020-05-26 10:04:59
 */

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
     * 公司人员id（外键）
     */
    private Integer companyPersonnelId;
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
     * 工种年限
     */
    private String yearsOfWork;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        System.out.println("设置身份证号码，");
        this.idCardNo = idCardNo;
    }

    public Integer getCompanyPersonnelId() {
        return companyPersonnelId;
    }

    public void setCompanyPersonnelId(Integer companyPersonnelId) {
        this.companyPersonnelId = companyPersonnelId;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getOperationItems() {
        return operationItems;
    }

    public void setOperationItems(String operationItems) {
        this.operationItems = operationItems;
    }

    public String getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(String workingYears) {
        this.workingYears = workingYears;
    }

    public String getTheoreticalAchievements() {
        return theoreticalAchievements;
    }

    public void setTheoreticalAchievements(String theoreticalAchievements) {
        this.theoreticalAchievements = theoreticalAchievements;
    }

    public String getActualResults() {
        return actualResults;
    }

    public void setActualResults(String actualResults) {
        this.actualResults = actualResults;
    }

    public String getOperationCertificateNo() {
        return operationCertificateNo;
    }

    public void setOperationCertificateNo(String operationCertificateNo) {
        this.operationCertificateNo = operationCertificateNo;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getYearsOfWork() {
        return yearsOfWork;
    }

    public void setYearsOfWork(String yearsOfWork) {
        this.yearsOfWork = yearsOfWork;
    }

    public String getOneReviewResults() {
        return oneReviewResults;
    }

    public void setOneReviewResults(String oneReviewResults) {
        this.oneReviewResults = oneReviewResults;
    }

    public String getOneReviewTime() {
        return oneReviewTime;
    }

    public void setOneReviewTime(String oneReviewTime) {
        this.oneReviewTime = oneReviewTime;
    }

    public String getTowReviewResults() {
        return towReviewResults;
    }

    public void setTowReviewResults(String towReviewResults) {
        this.towReviewResults = towReviewResults;
    }

    public String getTowReviewTime() {
        return towReviewTime;
    }

    public void setTowReviewTime(String towReviewTime) {
        this.towReviewTime = towReviewTime;
    }

    public String getThreeReviewResults() {
        return threeReviewResults;
    }

    public void setThreeReviewResults(String threeReviewResults) {
        this.threeReviewResults = threeReviewResults;
    }

    public String getThreeReviewTime() {
        return threeReviewTime;
    }

    public void setThreeReviewTime(String threeReviewTime) {
        this.threeReviewTime = threeReviewTime;
    }

    public String getFourReviewResults() {
        return fourReviewResults;
    }

    public void setFourReviewResults(String fourReviewResults) {
        this.fourReviewResults = fourReviewResults;
    }

    public String getFourReviewTime() {
        return fourReviewTime;
    }

    public void setFourReviewTime(String fourReviewTime) {
        this.fourReviewTime = fourReviewTime;
    }

    public String getFiveReviewResults() {
        return fiveReviewResults;
    }

    public void setFiveReviewResults(String fiveReviewResults) {
        this.fiveReviewResults = fiveReviewResults;
    }

    public String getFiveReviewTime() {
        return fiveReviewTime;
    }

    public void setFiveReviewTime(String fiveReviewTime) {
        this.fiveReviewTime = fiveReviewTime;
    }

    public String getSixReviewResults() {
        return sixReviewResults;
    }

    public void setSixReviewResults(String sixReviewResults) {
        this.sixReviewResults = sixReviewResults;
    }

    public String getSixReviewTime() {
        return sixReviewTime;
    }

    public void setSixReviewTime(String sixReviewTime) {
        this.sixReviewTime = sixReviewTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOperatingStaff() {
        return operatingStaff;
    }

    public void setOperatingStaff(Integer operatingStaff) {
        this.operatingStaff = operatingStaff;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}