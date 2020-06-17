package com.rbi.security.entity.web.safe.testpaper;

import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.testpaper
 * @NAME: TestPaper
 * @USER: "吴松达"
 * @DATE: 2020/6/8
 * @TIME: 14:53
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 14
 * @MINUTE: 53
 * @PROJECT_NAME: security
 **/
@Data
public class TestPaper {
    /**
     * 试卷id
     */
    private Integer id;
    /**
     * 考试开始时间
     */
    private String startTime;
    /**
     * 考试结束时间
     */
    private String endTime;
    /**
     * 考试时长
     */
    private Integer duration;
    /**
     * 试卷名称
     */
    private String testPaperName;
    /**
     * 试卷的单选题目
     */
    private List<SafeTestQuestions> SingleChoiceQuestions;
    /**
     * 试卷的多选题目
     */
    private List<SafeTestQuestions> multipleChoiceQuestions;
    /**
     * 试卷的判断题目
     */
    private List<SafeTestQuestions> judgmentQuestions;
    /**
     * 试卷的填空题目
     */
    private List<SafeTestQuestions> completion;
}
