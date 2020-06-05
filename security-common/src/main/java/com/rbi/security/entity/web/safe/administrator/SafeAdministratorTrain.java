package com.rbi.security.entity.web.safe.administrator;

import lombok.Data;

import java.io.Serializable;

/**
 * (SafeAdministratorTrain)实体类
 * 安全管理人/主要负责人台账记录表
 * @author 吴松达
 * @since 2020-05-28 10:55:22
 */
@Data
public class SafeAdministratorTrain implements Serializable {
    private static final long serialVersionUID = -84080295054716708L;
    /**
    * 主要负责人/安全生产管理员培训id
    */
    private Integer id;
    /**
    * 身份证号码
    */
    private String idCardNo;
    /**
    * 公司人员信息id
    */
    private Integer companyPersonnelId;
    /**
    * 单位
    */
    private String unit;
    /**
    * 发证时间
    */
    private String dateOfIssue;
    /**
    * 有效期
    */
    private String termOfValidity;
    /**
    * 合格证类型
    */
    private String typeOfCertificate;
    /**
    * 第一次培训时间
    */
    private String oneTrainingTime;
    /**
    * 第二次培训时间
    */
    private String twoTrainingTime;
    /**
    * 第三次培训时间
    */
    private String threeTrainingTime;
    /**
    * 备注
    */
    private String remarks;
    /**
    * 操作人（公司人员信息id）
    */
    private Integer operatingStaff;
    /**
    * 创建时间
    */
    private String idt;

    private String udt;


}