package com.rbi.security.entity.web.safe.task;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.web.controller.safe
 * @NAME: TestPaperInfo
 * @USER: "吴松达"
 * @DATE: 2020/6/5
 * @TIME: 10:19
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 05
 * @DAY_NAME_SHORT: 周五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 19
 * @PROJECT_NAME: security
 **/
@Data
public class TestPaperInfo {
    private Integer id;//试卷id

    private String testPaperName;//试卷名称
    private Integer processingStatus;//考试完成状态
    private String testResults;//考试结果
    private String startTime;//开始考试时间
    private String endTime;//结束考试时间
    private String duration;//考试时长
    private Integer personnelTrainingRecordId;//人员培训记录id
}
