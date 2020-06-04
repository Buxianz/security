package com.rbi.security.entity.web.safe.testpaper;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.List;

/**
 * (SafeTestPaper)实体类
 *试卷
 * @author 吴松达
 * @since 2020-06-01 09:56:15
 */
@Data
public class SafeTestPaper implements Serializable {
    private static final long serialVersionUID = 621399436830992561L;
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
     * 需求计划id
     */
    private Integer trainingPlanId;
    /**
     * 试卷名称
     */
    private String testPaperName;
    /**
     * 试卷的题目
     */
    private List<SafeTestQuestions> safeTestQuestionsList;
}