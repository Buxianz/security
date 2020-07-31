package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SafeSubject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SafeSubjectDAO {

    /**
     * 添加
     */
    @Insert("insert into safe_subject (subject_type,subject,right_key,subject_store_id,score) values (#{subjectType},#{subject},#{rightKey},#{subjectStoreId},#{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeSubject(SafeSubject safeSubject);

    /**
     * 获取全部
     */
    @Select("select safe_subject.*,subject_store_name from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id limit #{pageNo},#{pageSize}")
    List<SafeSubject> getSafeSubjectByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(safe_subject.id) from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id")
    int getCountSafeSubject();

    /**
     * 根据题库获取
     */
    @Select("select safe_subject.*,subject_store_name from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id and safe_subject.subject_store_id=#{subjectStoreId} limit #{pageNo},#{pageSize}")
    List<SafeSubject> getSafeSubjectByPageAndSubjectStoreId(@Param("subjectStoreId") int subjectStoreId,
                                                            @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(safe_subject.id) from safe_subject,safe_subject_store_type WHERE " +
            "safe_subject.subject_store_id=safe_subject_store_type.id and safe_subject.subject_store_id=#{subjectStoreId}")
    int getCountSafeSubjectBySubjectStoreId(@Param("subjectStoreId") int subjectStoreId);
    /**
     * 根据id获取
     */
    @Select("select * from safe_subject where id=#{id}")
    SafeSubject getSafeSubjectById(@Param("id") Integer id);

    /**
     * 更新
     */
    @Update("update safe_subject set subject_type=#{subjectType},subject=#{subject},right_key=#{rightKey},score=#{score} where id=#{id}")
    int updateSafeSubjectById(SafeSubject safeSubject);

    /**
     * 根据id删除
     */
    @Delete("delete from safe_subject where id=#{id}")
    void deleteSafeSubjectById(@Param("id") Integer id);

    /**
     * 根据题库id 题目类型 随机生成n个题目
     */
    @Select("select q2.* from safe_subject q2 where q2.subject_type=2 and q2.subject_store_id=13 order by rand() limit #{n}")
    List<SafeSubject> getRandomSafeSubjectBy(@Param("subjectType") int subjectType,@Param("subjectStoreId") int subjectStoreId,@Param("n") int n);
}
