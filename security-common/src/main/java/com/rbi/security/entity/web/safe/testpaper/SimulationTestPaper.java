package com.rbi.security.entity.web.safe.testpaper;

import com.rbi.security.entity.web.entity.SafeSubject;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.testpaper
 * @NAME: SimulationTestPaper 模拟试卷
 * @USER: "吴松达"
 * @DATE: 2020/7/30
 * @TIME: 21:08
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 21
 * @MINUTE: 08
 * @PROJECT_NAME: security
 **/
@Data
public class SimulationTestPaper {
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
    private List<SafeSubject> SingleChoiceQuestions;
    /**
     * 试卷的多选题目
     */
    private List<SafeSubject> multipleChoiceQuestions;
    /**
     * 试卷的判断题目
     */
    private List<SafeSubject> judgmentQuestions;
    /**
     * 试卷的填空题目
     */
    private List<SafeSubject> completion;
}
