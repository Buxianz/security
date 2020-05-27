package com.rbi.security.entity.web.entity;


import lombok.Data;

@Data
public class SafeSubjectOption {

  /**
   * 试题选项id
   */
  private Integer id;
  /**
   * 题目id
   */
  private Integer subjectId;
  /**
   * 选择题的选项
   */
  private String option;
  /**
   * 选项排序
   */
  private Integer order;

}
