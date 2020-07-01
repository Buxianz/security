package com.rbi.security.entity.web.SeriousDanger;

import com.rbi.security.entity.web.entity.SeriousDanger;
import com.rbi.security.entity.web.entity.SeriousDangerPicture;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.SeriousDanger
 * @NAME: PagingSeriousDanger
 * @USER: "林新元"
 * @DATE: 2020/6/17
 * @TIME: 17:50
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 六月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 星期三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 17
 * @MINUTE: 50
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSeriousDanger {
    /**
     * id
     */
    private Integer id;
    /**
     * 重大危险源名称
     */
    private String seriousDangerName;
    /**
     * 重大危险源所在位置
     */
    private String seriousDangerLocation;
    /**
     * 危险因素
     */
    private String seriousDangerElement;
    /**
     * 危险源主要控制措施
     */
    private String seriousDangerMeasure;
    /**
     * 危险源管控状态
     */
    private String seriousDangerStatus;
    /**
     * 危险源管控周期
     */
    private String seriousDangerCycle;
    /**
     * 危险源主要负责人
     */
    private String seriousDangerPrincipal;
    /**
     * 危险源最近管控时间
     */
    private String seriousDangerTime;
    /**
     * 危险源管控层级
     */
    private String seriousDangerControlLevel;
    /**
     * 危险源应急措施
     */
    private String seriousDangerEmergencyMeasure;

    List<SeriousDangerPicture> seriousDangerPictureList=new ArrayList<>();
}
