package com.rbi.security.entity.web.safe;

import com.rbi.security.entity.web.safe.testpaper.SafeTestQuestionOptions;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: PagePersonalMistakes
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 9:58
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 09
 * @MINUTE: 58
 * @PROJECT_NAME: security
 **/
@Data
public class PagePersonalMistakes {
    //个人的错题id
     private Integer id;
     //试卷的题目id
     private Integer subjectId;
     //题目内容
     private String subject;
     //正确答案
     private String rightKey;
     //题目类型
     private Integer subjectType;
     //分值
    private Double score;
    //题目下面的选项
    private List<SafeTestQuestionOptions> safeTestQuestionOptionsList;
}
