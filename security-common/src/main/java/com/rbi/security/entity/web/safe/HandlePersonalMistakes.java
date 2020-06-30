package com.rbi.security.entity.web.safe;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: HandlePersonalMistakes
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 15:42
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 15
 * @MINUTE: 42
 * @PROJECT_NAME: security
 **/
@Data
public class HandlePersonalMistakes {
    /**
     * 个人的错题id
     */
    private Integer id;
    //正确答案
    private String rightKey;
    /****个人答题结果***/
    private String answerResults;
}
