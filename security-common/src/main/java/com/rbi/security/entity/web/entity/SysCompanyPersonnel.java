package com.rbi.security.entity.web.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysCompanyPersonnel)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:21:37
 */
public class SysCompanyPersonnel implements Serializable {
    private static final long serialVersionUID = -82522653392194187L;
    /**
    * 公司人员id
    */
    private Long id;
    /**
    * 员工号
    */
    private String employeeNumber;
    /**
    * 组织id
    */
    private Integer organizationId;
    /**
    * 姓名
    */
    private String name;
    /**
    * 性别
    */
    private Integer gender;
    /**
    * 民族
    */
    private String nation;
    /**
    * 婚姻状况
    */
    private String maritalStatus;
    /**
    * 身份证号码
    */
    private String idCardNo;
    /**
    * 出生日期
    */
    private Date dateOfBirth;
    /**
    * 文化程度
    */
    private String degreeOfEducation;
    /**
    * 所在岗位
    */
    private String position;
    /**
    * 岗位性质
    */
    private String jobNature;
    /**
    * 工种
    */
    private String workType;
    /**
    * 入厂时间
    */
    private Date entryTime;
    /**
    * 备注
    */
    private String remarks;
    /**
    * 创建时间
    */
    private String idt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDegreeOfEducation() {
        return degreeOfEducation;
    }

    public void setDegreeOfEducation(String degreeOfEducation) {
        this.degreeOfEducation = degreeOfEducation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

}