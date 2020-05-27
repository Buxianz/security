package com.rbi.security.web.service;

import com.rbi.security.entity.web.safe.SafeSubjectStore;

import java.util.List;

/**
 * @author 杨赞格
 */
public interface SafeSubjectStoreService {


    /**
     * 查询题库全部名称
     * @return
     */
    List<SafeSubjectStore> getAllSafeSubjectStore();

    /**
     * 插入题库名称
     * @param safeSubjectStore
     */
    void insertSafeSubjectStore(SafeSubjectStore safeSubjectStore);


    /**
     * 更新题库名称
     * @param safeSubjectStore
     */
    void updateSafeSubjectStore(SafeSubjectStore safeSubjectStore);

    /**
     * 删除题库名称
     * @param id
     */
    void deleteSafeSubjectStore(int id);





}
