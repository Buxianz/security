package com.rbi.security.entity.web.safe.examination;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe.examination
 * @NAME: SimulationReults
 * @USER: "吴松达"
 * @DATE: 2020/7/30
 * @TIME: 22:38
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 22
 * @MINUTE: 38
 * @PROJECT_NAME: security
 **/
@Data
public class SimulationReults  implements Serializable {
    private static final long serialVersionUID = -81536751531445989L;
    private int totalScore;
    private int result;
    private List<SimulationSafeAnswerRecord> simulationSafeAnswerRecords;
}
