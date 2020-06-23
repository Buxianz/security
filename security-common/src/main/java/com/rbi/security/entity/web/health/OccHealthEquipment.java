package com.rbi.security.entity.web.health;

import lombok.Data;

@Data
public class OccHealthEquipment {
  /**
   * id
   */
  private Integer id;
  /**
   * 使用单位
   */
  private String healthEquipmentOrganization;
  /**
   * 设施名称
   */
  private String healthEquipmentName;
  /**
   * 设备型号
   */
  private String healthEquipmentModel;
  /**
   * 制造单位
   */
  private String healthEquipmentProduction;
  /**
   * 安装地点
   */
  private String healthEquipmentLocation;
  /**
   * 校验日期（年月日）
   */
  private String healthEquipmentTime;
  /**
   * 备注
   */
  private String healthEquipmentRemark;
}
