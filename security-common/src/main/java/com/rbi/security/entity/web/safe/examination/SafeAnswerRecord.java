package com.rbi.security.entity.web.safe.examination;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeAnswerRecord)人员答题记录实体类
 *
 * @author 吴松达
 * @since 2020-06-02 16:09:13
 */
@Data
public class SafeAnswerRecord implements Serializable {
    private static final long serialVersionUID = -81536751531445989L;
    /**
    * 人员答题记录id
    */
    private Integer id;
    /**
    * 人员培训记录id
    */
    private Integer personnelTrainingRecordId;
    /**
    * 答题结果(#分割答案）
    */
    private String answerResults;
    /**
     * 正确答案
     */
    private String rightKey;
    /**
     * 题目分数
     */
    private Integer score;
    /**
    * 是否正确（0：不正确 1：正确）
    */
    private Integer correct;
    /**
    * 试卷的题目id
    */
    private Integer testUestionsId;
    /**
    * 试卷id
    */
    private Integer testPapreId;



}