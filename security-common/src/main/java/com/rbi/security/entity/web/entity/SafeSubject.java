package com.rbi.security.entity.web.entity;


import lombok.Data;

import java.util.List;

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
  /**
   * 题目对应题库的分类
   */
  private String subjectStoreName;
  /**
   * 分值
   */
  private Integer score;

  private List<SafeSubjectOption> safeSubjectOptionList;

}
