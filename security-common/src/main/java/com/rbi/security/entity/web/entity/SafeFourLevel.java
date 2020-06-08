package com.rbi.security.entity.web.entity;


import lombok.Data;

import java.sql.Date;

@Data
public class SafeFourLevel {
  /**
   * 四级培训台账id
   */
  private Integer id;
  /**
   * 身份证号
   */
  private String idCardNo;
  /**
   * 单位
   */
  private String organizationName;
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
