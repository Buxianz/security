package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.HandlePersonalMistakes;
import com.rbi.security.tool.PageData;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: SafePersonalMistakes
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 9:50
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 09
 * @MINUTE: 50
 * @PROJECT_NAME: security
 **/
public interface SafePersonalMistakesService {
    PageData findPersonalMistakesByPage(int pageNo, int pageSize , int startIndex) throws RuntimeException;
    void handlePersonalMistakes(List<HandlePersonalMistakes> handlePersonalMistakes) throws RuntimeException;
}
