package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import java.util.List;

public interface SafeSubjectService {

    /**
     * 添加自定义试题
     */
    void insertSafeSubject(JSONObject json);

    /**
     * 根据类型分页查询试题
     */
    PageData getSafeSubjectByPage(JSONObject json);


    /**
     * 更新试题信息
     */
    String updateSafeSubjectById(JSONObject json);

    /**
     * 根据id删除试题
     */
    String  deleteSafeSubjectById(JSONArray result);


}
