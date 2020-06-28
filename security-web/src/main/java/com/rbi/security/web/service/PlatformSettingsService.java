package com.rbi.security.web.service;

import com.rbi.security.entity.web.PlatformSettings;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: PlatformSettingsService
 * @USER: "吴松达"
 * @DATE: 2020/6/28
 * @TIME: 10:24
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 10
 * @MINUTE: 24
 * @PROJECT_NAME: security
 **/
public interface PlatformSettingsService {
    PlatformSettings getSpecialDaySet() throws RuntimeException;
    void updateSpecialDaySet(PlatformSettings platformSettings)throws RuntimeException;
}
