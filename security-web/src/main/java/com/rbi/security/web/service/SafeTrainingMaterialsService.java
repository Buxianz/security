package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import com.rbi.security.tool.PageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service
 * @NAME: SafeTrainingMaterialsService
 * @USER: "谢青"
 * @DATE: 2020/5/27
 * @TIME: 11:10
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 27
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 11
 * @MINUTE: 10
 * @PROJECT_NAME: security
 **/
public interface SafeTrainingMaterialsService {
    String add(SafeTrainingMaterials safeTrainingMaterials, MultipartFile file) throws IOException;

    PageData findByPage(int pageNo, int pageSize);

    PageData findByCondition(int pageNo, int pageSize, int value);

    List<SafeContentCategory> findType();

    String deleteByIds(JSONArray array);

    PageData findByName(int pageNo, int pageSize, String value);

    PageData findFileByCategory(int pageNo, int pageSize, Integer value);

    PageData findVideoByCategory(int pageNo, int pageSize, Integer value);
}
