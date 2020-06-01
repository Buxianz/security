package com.rbi.security.entity.web.safe.testpaper;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeTestQuestionOptions)实体类
 *试卷题目的选项
 * @author makejava
 * @since 2020-06-01 09:59:30
 */
@Data
public class SafeTestQuestionOptions implements Serializable {
    private static final long serialVersionUID = 875442941111310333L;
    /**
    * 试卷的试题的选项id
    */
    private Integer id;
    /**
    * 试题id
    */
    private Integer subjectId;
    /**
    * 选项内容
    */
    private String option;
    /**
    * 选项顺序
    */
    private Integer order;
}