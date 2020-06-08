package com.rbi.security.entity.web.safe;

import lombok.Data;

import java.sql.Date;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: PagingSafeFourLevel
 * @USER: "林新元"
 * @DATE: 2020/6/5
 * @TIME: 10:25
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 05
 * @DAY_NAME_SHORT: 星期五
 * @DAY_NAME_FULL: 星期五
 * @HOUR: 10
 * @MINUTE: 25
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSafeFourLevel {
    /**
     * 四级培训台账id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 组织名
     */
    private String organizationName;
    /**
     * 性别 男女
     */
    private String gender;
    /**
     * 出生日期
     */
    private Date dateOfBirth;
    /**
     * 入厂时间
     */
    private Date entryTime;
    /**
     * 工种
     */
    private String workType;
    /**
     * 岗位
     */
    private String jobNature;
    /**
     * 公司培训时间
     */
    private String companyEducationTime;
    /**
     * 成绩
     */
    private Double companyFraction;
    /**
     *厂培训时间
     */
    private String factoryEducationTime;
    /**
     * 成绩
     */
    private Double factoryFraction;
    /**
     * 车间培训时间
     */
    private String workshopEducationTime;
    /**
     * 成绩
     */
    private Double workshopFraction;
    /**
     * 班组培训时间
     */
    private String classEducationTime;
    /**
     * 成绩
     */
    private Double classFraction;
    /**
     * 操作人id
     */
    private Integer operatingStaff;
    /**
     * 添加时间
     */
    private String idt;
    /**
     * 更新时间
     */
    private String udt;
}
