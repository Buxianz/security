package com.rbi.security.entity.web.safe.demand;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.demand
 * @NAME: PagingTraniningNeeds
 * @USER: "吴松达"
 * @DATE: 2020/6/1
 * @TIME: 16:00
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 00
 * @PROJECT_NAME: security
 **/
@Data
public class PagingTraniningNeeds {
    private Integer id;
    private String trainingTypeName;
    private String trainingContent;
    private Integer processingStatus;
    private Integer reportPerson;
    /**
     * 提出时间
     */
    private String proposedTime;
    /**
     * 上报人姓名
     */
    private String name;
}
