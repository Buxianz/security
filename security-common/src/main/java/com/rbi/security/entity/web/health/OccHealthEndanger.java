package com.rbi.security.entity.web.health;

import lombok.Data;

@Data
public class OccHealthEndanger {

  /**
   * id
   */
  private Integer id;
  /**
   * 职业病危害名称（代码）
   */
  private String healthEndangerName;
  /**
   * 作业场所名称
   */
  private String healthEndangerPosition;
  /**
   * 职业病危害因素来源
   */
  private String healthEndangerSource;
  /**
   * 设备状态
   */
  private String healthEndangerStatus;
  /**
   * 操作方式
   */
  private String healthEndangerMode;
  /**
   * 是否隔离
   */
  private String healthEndangerInsulate;
  /**
   * 接触职业病危害人数
   */
  private Integer healthEndangerPeopleNumber;
  /**
   * 接触职业病危害女工人数
   */
  private Integer healthEndangerFemaleNumber;
  /**
   * 作业场所强度（浓度）
   */
  private String healthEndangerStrength;
  /**
   * 工程防护设施有（名称）、无
   */
  private String healthEndangerEquipment;
  /**
   * 个体防护用品有（名称）、无
   */
  private String healthEndangerGoods;
  /**
   * 填表部门
   */
  private String healthEndangerDepartment;
  /**
   * 填表人
   */
  private String healthEndangerOperation;
  /**
   * 审核人
   */
  private String healthEndangerAuditor;
  /**
   * 填表时间
   */
  private String healthEndangerOperationTime;
  /**
   * 职业病危害因素检测机构
   */
  private String healthEndangerTest;
  /**
   * 检测时间
   */
  private String healthEndangerTestTime;

}
