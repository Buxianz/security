package com.rbi.security.entity.web.entity;


import lombok.Data;

@Data
public class SafeSubject {

  /**
   * 题目id
   */
  private Integer id;
  /**
   * 题目类型
   */
  private Integer subjectType;
  /**
   * 题目
   */
  private String subject;
  /**
   * 正确答案
   */
  private String rightKey;
  /**
   * 题库id
   */
  private Integer subjectStoreId;
}