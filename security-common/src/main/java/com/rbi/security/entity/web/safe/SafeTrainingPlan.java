package com.rbi.security.entity.web.safe;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeTrainingPlan)培训计划实体类
 *
 * @author 吴松达
 * @since 2020-06-02 16:13:03
 */
@Data
public class SafeTrainingPlan implements Serializable {
    private static final long serialVersionUID = 450181305544719811L;
    /**
    * 培训计划id
    */
    private Integer id;
    /**
    * 培训需求id
    */
    private Integer trainingNeedsId;
    /**
    * 被培训目标集合（公司人员信息id集合）
    */
    private String targetSet;
    /**
    * 创建时间
    */
    private String idt;


}