package com.rbi.security.entity.web.safe.specialtype;

import java.io.Serializable;

/**
 * (SafeSpecialReview)实体类
 * @USER: "吴松达"
 * @author makejava
 * @since 2020-05-26 10:05:00
 */
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
    * 创建时间
    */
    private String idt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpecialPersonnelId() {
        return specialPersonnelId;
    }

    public void setSpecialPersonnelId(Integer specialPersonnelId) {
        this.specialPersonnelId = specialPersonnelId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(Integer completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getReasonForHandling() {
        return reasonForHandling;
    }

    public void setReasonForHandling(String reasonForHandling) {
        this.reasonForHandling = reasonForHandling;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

}