package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.tool.PageData;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: SafeContentCategoryService
 * @USER: "谢青"
 * @DATE: 2020/6/17
 * @TIME: 14:21
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 21
 * @PROJECT_NAME: security
 **/
public interface SafeContentCategoryService {
    PageData findByPage(int pageNo, int pageSize);

    void add(SafeContentCategory safeAdministratorReviewDTO);

    void update(SafeContentCategory safeContentCategory);

    String deleteById(Integer id);

    PageData findByContentCategoryName(int pageNo, int pageSize, String contentCategoryName);
}
