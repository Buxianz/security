package com.rbi.security.entity.web.safe.testpaper;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (SafeTestQuestions)实体类
 *  试卷的题目表（safe_test_questions）
 * @author 吴松达
 * @since 2020-05-31 22:00:21
 */
@Data
public class SafeTestQuestions implements Serializable {
    private static final long serialVersionUID = 888206743860220834L;
    /**
    * 试卷的题目id
    */
    private Object id;
    /**
    * 题目内容
    */
    private String subject;
    /**
    * 题目类型
    */
    private Integer subjectType;
    /**
    * 正确答案
    */
    private String rightKey;
    /**
    * 试卷id
    */
    private Integer testPapreId;


    /**
     * 试题的选项
     */
    private List<SafeTestQuestionOptions> safeTestQuestionOptionsList;

}