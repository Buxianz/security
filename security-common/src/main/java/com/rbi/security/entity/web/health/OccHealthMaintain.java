package com.rbi.security.entity.web.health;

import lombok.Data;

@Data
public class OccHealthMaintain {
  /**
   * id
   */
  private Integer id;
  /**
   * 车间名称
   */
  private String healthMaintainWorkshop;
  /**
   * 防护设备名称
   */
  private String healthMaintainName;
  /**
   * 型号
   */
  private String healthMaintainType;
  /**
   * 安装时间
   */
  private String healthMaintainTime;
  /**
   * 安装位置
   */
  private String healthMaintainLocation;
  /**
   * 产害设备名称
   */
  private String healthMaintainDangerName;
  /**
   * 防护设备运行状况
   */
  private String healthMaintainSituation;
  /**
   * 防护设备失效原因
   */
  private String healthMaintainCause;
  /**
   * 备注
   */
  private String healthMaintainRemark;
}
