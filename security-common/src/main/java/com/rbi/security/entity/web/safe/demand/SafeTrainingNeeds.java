package com.rbi.security.entity.web.safe.demand;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (SafeTrainingNeeds)实体类
 * 需求提报表
 * @author 吴松达
 * @since 2020-06-01 14:28:13
 */
@Data
public class SafeTrainingNeeds implements Serializable {
    private static final long serialVersionUID = -14970343215588718L;
    /**
    * 培训需求id
    */
    private Integer id;
    /**
    * 培训目标集合（用户账号集合 ，格式（a,b,c,d））
    */
    private String  targetSet;
    /**
    * 培训类型id
    */
    private Integer trainingTypeId;
    /**
     * 培训类型名称
     */
    private String trainingTypeName;
    /**
    * 培训内容
    */
    private String trainingContent;
    /**
    * 培训时长
    */
    private String trainingDuration;
    /**
    * 开始时间
    */
    private String startTime;
    /**
    * 结束时间
    */
    private String endTime;
    /**
    * 组织培训部门id（为组织信息表中id的某个值）
    */
    private Integer organizationTrainingDepartmentId;
    /**
     * 组织名称
     */
    private String organizationName;
    /**
    * 提出时间
    */
    private String proposedTime;
    /**
    * 上报人(公司人员信息id）
    */
    private Integer reportPerson;
    /**
    * 操作原因
    */
    private String operationReason;
    /**
    * 处理时间
    */
    private String processingTime;
    /**
     * 处理人
     */
    private Integer operatingStaff;
    /**
     * 处理状态
     */
    private Integer processingStatus;
    /**
    * 创建时间
    */
    private String idt;

}