package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SafeSubject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SafeSubjectDAO {

    /**
     * 添加自定义试题
     */
    @Insert("insert into safe_subject (subject_type,subject,right_key,subject_store_id) values (#{subjectType},#{subject},#{rightKey},#{subjectStoreId})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeSubject(SafeSubject safeSubject);

    /**
     * 获取全部试题
     */
    @Select("select * from safe_subject where subject_type=#{subjectType}")
    List<SafeSubject> getSafeSubjectBySubjectType(@Param("subjectType") Integer subjectType);

    /**
     * 根据id获取试题
     */
    @Select("select * from safe_subject where id=#{id}")
    SafeSubject getSafeSubjectById(@Param("id") Integer id);

    /**
     * 更新试题信息
     */
    @Update("update safe_subject set subject_type=#{subjectType},subject=#{subject},right_key=#{rightKey} where id=#{id}")
    int updateSafeSubjectById(SafeSubject safeSubject);

    /**
     * 根据id删除试题
     */
    @Delete("delete from safe_subject where id=#{id}")
    void deleteSafeSubjectById(@Param("id") Integer id);

}
