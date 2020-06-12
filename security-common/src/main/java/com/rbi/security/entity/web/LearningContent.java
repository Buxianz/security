package com.rbi.security.entity.web;

import com.rbi.security.entity.web.safe.content.SafeDataPlanDTO;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web
 * @NAME: LearningContent
 * @USER: "吴松达" 学习内容
 * @DATE: 2020/6/11
 * @TIME: 11:21
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 11
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 11
 * @MINUTE: 21
 * @PROJECT_NAME: security
 **/
@Data
public class LearningContent {
     private List<SafeDataPlanDTO> file;
     private List<SafeDataPlanDTO> video;
}
