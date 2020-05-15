package com.rbi.security.entity.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * (SysCompanyPersonnel)实体类
 *
 * @author makejava
 * @since 2020-05-14 16:21:37
 */
@Data
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
    private Long organizationId;
    /**
    * 姓名
    */
    private String name;
    /**
    * 性别
    */
    private String gender;
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
    /**
     * 更新时间
     */
    private String udt;
}