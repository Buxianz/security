package com.rbi.security.entity.web.entity;


import lombok.Data;

import java.sql.Date;

@Data
public class SafeFourLevel {
  private Integer id;
  private String idCardNo;
  private String organizationName;
  private String companyEducationTime;
  private Double companyFraction;
  private String factoryEducationTime;
  private Double factoryFraction;
  private String workshopEducationTime;
  private Double workshopFraction;
  private String classEducationTime;
  private Double classFraction;
  private Integer operatingStaff;
  private String idt;
  private String udt;
  private String remarks;
}
