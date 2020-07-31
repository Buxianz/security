package com.rbi.security.entity.web.safe.examination;

import lombok.Data;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.examination
 * @NAME: SimulationSafeAnswerRecord
 * @USER: "吴松达"
 * @DATE: 2020/7/30
 * @TIME: 22:37
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 22
 * @MINUTE: 37
 * @PROJECT_NAME: security
 **/
@Data
public class SimulationSafeAnswerRecord implements Serializable {
    private static final long serialVersionUID = -81536751531445989L;
    /**
     * 题库题目id
     */
    private Integer id;
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
}
