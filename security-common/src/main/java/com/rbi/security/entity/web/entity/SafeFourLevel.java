package com.rbi.security.entity.web.entity;


import lombok.Data;

@Data
public class SafeFourLevel {
  /**
   * 四级培训台账id
   */
  private Integer id;
  /**
   * 姓名
   */
  private String name;
  /**
   * 身份证号
   */
  private Integer idCardNo;
  /**
   * 组织名
   */
  private String organizationName;
  /**
   * 性别 男1女2
   */
  private Integer gender;
  /**
   * 入厂时间
   */
  private Integer dateOfBirth;
  /**
   * 整改单位名
   */
  private String entryTime;
  /**
   * 工种
   */
  private Integer workType;
  /**
   * 岗位
   */
  private String jobNature;
  /**
   * 公司培训时间
   */
  private String companyEducationTime;
  /**
   * 成绩
   */
  private Double companyFraction;
  /**
   *厂培训时间
   */
  private String factoryEducationTime;
  /**
   * 成绩
   */
  private Double factoryFraction;
  /**
   * 车间培训时间
   */
  private String workshopEducationTime;
  /**
   * 成绩
   */
  private Double workshopFraction;
  /**
   * 班组培训时间
   */
  private String classEducationTime;
  /**
   * 成绩
   */
  private Double classFraction;
  /**
   * 操作人id
   */
  private Integer operatingStaff;
  /**
   * 添加时间
   */
  private String idt;
  /**
   * 更新时间
   */
  private String udt;
}
