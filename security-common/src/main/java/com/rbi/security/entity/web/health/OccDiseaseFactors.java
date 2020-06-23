package com.rbi.security.entity.web.health;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.health
 * @NAME: OccDailyMonitoring
 * @USER: "谢青"
 * @DATE: 2020/6/22
 * @TIME: 11:10
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 22
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 11
 * @MINUTE: 10
 * @PROJECT_NAME: security
 **/
@Data
public class OccDiseaseFactors {
    private Integer id;
    private String factor;
    private Integer totalNum;
    private Integer womenNum;
    private Integer trainingNum;
    private Integer medicalExaminationNum;
    private Integer occDiseaseNum;
    private Integer demobilizedNum;
    private String writer;
    private String writeDepartment;
    private String writeTime;
    private String trainingOrganization;
    private String trainingTime;
    private String medicalExaminationOrganization;
    private String medicalExaminationTime;
    private String idt;
    private String udt;
    private Integer operatingStaff;

}
