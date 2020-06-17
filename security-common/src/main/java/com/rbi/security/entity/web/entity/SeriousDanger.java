package com.rbi.security.entity.web.entity;


import lombok.Data;

@Data
public class SeriousDanger {

  /**
   * id
   */
  private Integer id;
  /**
   * 重大危险源名称
   */
  private String seriousDangerName;
  /**
   * 重大危险源所在位置
   */
  private String seriousDangerLocation;
  /**
   * 危险因素
   */
  private String seriousDangerElement;
  /**
   * 危险源主要控制措施
   */
  private String seriousDangerMeasure;
  /**
   * 危险源管控状态
   */
  private String seriousDangerStatus;
  /**
   * 危险源管控周期
   */
  private String seriousDangerCycle;
  /**
   * 危险源主要负责人
   */
  private String seriousDangerPrincipal;
  /**
   * 危险源最近管控时间
   */
  private String seriousDangerTime;
}
