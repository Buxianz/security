package com.rbi.security.web.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rbi.security.tool.PageData;
import java.util.List;

public interface SafeSubjectService {

    /**
     * 添加自定义试题
     */
    void insertSafeSubject(JSONObject json) throws RuntimeException;

    /**
     * 根据类型分页查询
     */
    PageData getSafeSubjectByPage(JSONObject json) throws RuntimeException;

    /**
     * 根据题库id获取
     */
    PageData getSafeSubjectByPageAndSubjectStoreId(JSONObject json)  throws RuntimeException;

    /**
     * 更新试题信息
     */
    String updateSafeSubjectById(JSONObject json) throws RuntimeException;

    /**
     * 根据id删除试题
     */
    String  deleteSafeSubjectById(JSONArray result) throws RuntimeException;


}
