package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SafeSubject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SafeSubjectDAO {

    /**
     * 添加自定义试题
     */
    @Insert("insert into safe_subject (subject_type,subject,right_key,subject_store_id,score) values (#{subjectType},#{subject},#{rightKey},#{subjectStoreId},#{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeSubject(SafeSubject safeSubject);

    /**
     * 获取全部试题
     */
    @Select("select safe_subject.*,subject_store_name from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id limit #{pageNo},#{pageSize}")
    List<SafeSubject> getSafeSubjectByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(safe_subject.id) from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id")
    int getCountSafeSubject();
    /**
     * 根据id获取试题
     */
    @Select("select * from safe_subject where id=#{id}")
    SafeSubject getSafeSubjectById(@Param("id") Integer id);

    /**
     * 更新试题信息
     */
    @Update("update safe_subject set subject_type=#{subjectType},subject=#{subject},right_key=#{rightKey},score=#{score} where id=#{id}")
    int updateSafeSubjectById(SafeSubject safeSubject);

    /**
     * 根据id删除试题
     */
    @Delete("delete from safe_subject where id=#{id}")
    void deleteSafeSubjectById(@Param("id") Integer id);

}
