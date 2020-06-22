package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.SafeSubjectStore;
import com.rbi.security.entity.web.system.SystemCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 杨赞格
 */
@Mapper
public interface SafeSubjectStoreDAO {


    @Select("select * from safe_subject_store_type where subject_store_name=#{subjectStoreName}")
    SafeSubjectStore getSafeSubjectStoreByName(@Param("subjectStoreName") String subjectStoreName);

    @Select("select * from safe_subject_store_type")
    List<SafeSubjectStore> getAllSafeSubjectStore();

    @Select("select subject_store_name from safe_subject_store_type where id=#{id}")
    String getSafeSubjectStoreById(@Param("id") int id);

    @Insert("INSERT INTO safe_subject_store_type (subject_store_name) VALUE (#{subjectStoreName})")
    void insertSubjectStoreName(@Param("subjectStoreName") String subjectStoreName);

    @Update("UPDATE safe_subject_store_type SET subject_store_name = #{subjectStoreName} WHERE id =#{id}")
    void updateSubjectStoreNameById(SafeSubjectStore safeSubjectStore);

    @Delete("DELETE FROM safe_subject_store_type WHERE id = #{id}")
    void deleteSubjectStoreNameById(@Param("id") int id);

    @Select("select safe_subject.subject_store_id from safe_subject WHERE safe_subject.subject_store_id= #{id}")
    List<Integer> getSafeSubjectStoreIdById(@Param("id") int id);
}
