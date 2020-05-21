package com.rbi.security.entity.web.system;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.system
 * @NAME: PagingSystemInfo
 * @USER: "吴松达"
 * @DATE: 2020/5/21
 * @TIME: 17:53
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 21
 * @DAY_NAME_SHORT: 周四
 * @DAY_NAME_FULL: 星期四
 * @HOUR: 17
 * @MINUTE: 53
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSystemInfo {
    private Integer id;
    private String fileName;
    private String filePath;
    private String categoryName;
    private Integer systemCategoryId;
}
