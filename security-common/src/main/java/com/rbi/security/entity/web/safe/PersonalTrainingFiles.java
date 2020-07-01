package com.rbi.security.entity.web.safe;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: PersonalTrainingFiles
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 14:48
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 48
 * @PROJECT_NAME: security
 **/
@Data
public class PersonalTrainingFiles {
    //培训需求id
    private Integer id;
    //培训计划id
    private Integer trainingPlanId;
    //组织培训部门Id
    private Integer organizationTrainingDepartmentId;
    //组织培训部门名称
    private String organizationTrainingDepartmentName;
    //培训类型Id
    private Integer trainingTypeId;
    //培训类型名称
    private String trainingTypeName;
    //培训内容
    private String trainingContent;
    //培训时间
    private String trainingime;
    //考试时间
    private String examinationTime;
    //学习时长
    private String learningTime;
    //考试成绩
    private String testResults;
}
