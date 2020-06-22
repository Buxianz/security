package com.rbi.security.entity.web.health;

import lombok.Data;

/**
 * @ClassName OccHealthProject
 * @Description TODO
 * @Author muyizg
 * @Date 2020/6/19 11:15
 * @Version 1.0
 **/
@Data
public class OccHealthProject {

    /**
     * 职业卫生‘三同时’表'id
     */
    private Integer id;
    /**
     *项目名称
     */
    private String healthProjectName;
    /**
     *项目类型
     */
    private String healthProjectType;
    /**
     *项目投资
     */
    private String healthProjectInvestment;
    /**
     *项目建设工期
     */
    private String healthProjectDuration;
    /**
     *存在主要职业病危害因素
     */
    private String healthProjectDanger;
    /**
     *预评价时间
     */
    private String healthProjectEvaluateTime;
    /**
     *预评价单位
     */
    private String healthProjectEvaluateOrganization;
    /**
     *预评价结论
     */
    private String healthProjectEvaluateConclusion;
    /**
     *设施设计时间
     */
    private String healthProjectDesignTime;
    /**
     *设施设计单位
     */
    private String healthProjectDesignOrganization;
    /**
     *设施设计结论
     */
    private String healthProjectDesignConclusion;
    /**
     *竣工验收时间
     */
    private String healthProjectCheckTime;
    /**
     *竣工验收组织
     */
    private String healthProjectCheckOrganization;
    /**
     *竣工验收结论
     */
    private String healthProjectCheckConclusion;
    /**
     *控制效果评价时间
     */
    private String healthProjectResultTime;
    /**
     *控制效果评价单位
     */
    private String healthProjectResultOrganization;
    /**
     *控制效果评价结论
     */
    private String healthProjectResultConclusion;

}
