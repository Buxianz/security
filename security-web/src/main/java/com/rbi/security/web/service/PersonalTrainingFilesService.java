package com.rbi.security.web.service;

import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: PersonalTrainingFilesService
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 14:43
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 43
 * @PROJECT_NAME: security
 **/
public interface PersonalTrainingFilesService {
    PageData getPersonalTrainingFiles(int pageNo, int pageSize , int startIndex) throws RuntimeException;
}
