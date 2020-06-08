package com.rbi.security.entity.web.safe.task;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeTrainingTasks)人员培训记录实体类
 *
 * @author 吴松达
 * @since 2020-06-02 16:22:08
 */
@Data
public class SafeTrainingTasks implements Serializable {
    private static final long serialVersionUID = -71260158759651338L;
    /**
    * 人员培训记录id
    */
    private Integer id;
    /**
    * 公司人员信息id
    */
    private Integer companyPersonnelId;
    /**
    * 培训计划id
    */
    private Integer trainingPlanId;
    /**
    * 学习时长
    */
    private String learningTime;
    /**
    * 考试成绩
    */
    private String testResults;
    /**
     * 处理状态
     */
    private Integer processingStatus;
    /**
    * 创建时间
    */
    private String idt;

}