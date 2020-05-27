package com.rbi.security.entity.web.safe.specialtype;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeSpecialReview)实体类
 * @USER: "吴松达"  特种人员复审情况
 * @author makejava
 * @since 2020-05-26 10:05:00
 */
@Data
public class SafeSpecialReview implements Serializable {
    private static final long serialVersionUID = -98327916650856111L;
    /**
    * 特种人员复审id
    */
    private Integer id;
    /**
    * 特种人员培训档案id
    */
    private Integer specialPersonnelId;
    /*
    截止日期
     */
    private String deadline;
    /**
    * 完成状态
    */
    private Integer completionStatus;
    /**
    * 处理原因
    */
    private String reasonForHandling;
    /**
    * 处理时间
    */
    private String processingTime;
    /**
     * 操作人（公司员工信息id）
     *
     */
    private Integer operatingStaff;
    /**
    * 创建时间
    */
    private String idt;

}