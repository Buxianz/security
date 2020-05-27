package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SafeSubjectOption;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SafeSubjectOptionDAO  {

    /**
     * 添加自定义试题选项
     */
    @Insert("insert into safe_subject_option (subject_id,`option`,`order`) values (#{subjectId},#{option},#{order})")
    @Options(useGeneratedKeys = true, keyProperty = "id",keyColumn="id")
    int insertSafeSubjectOption(@Param("option") String option,@Param("order") Integer order,
                                @Param("subjectId") Integer subjectId);


    /**
     * 根据题目id获取试题选项
     */
    @Select("select * from safe_subject_option where subject_id=#{subjectId}")
    List<SafeSubjectOption> getSafeSubjectOptionBySubjectId(@Param("subjectId") Integer subjectId);

    /**
     * 更新试题选项信息
     */
    @Update("update safe_subject_option set subject_id=#{subjectId},option=#{option},order=#{order} where id=#{id}")
    int updateSafeSubjectOptionById(SafeSubjectOption safeSubjectOption);


    /**
     * 根据id删除试题选项
     */
    @Delete("delete from safe_subject_option where id=#{id}")
    void  deleteSafeSubjectOptionById(@Param("id") Integer id);

    /**
     * 根据id删除试题选项
     */
    @Delete("delete from safe_subject_option where subject_id=#{subjectId}")
    void  deleteSafeSubjectOptionBySubjectId(@Param("subjectId") Integer subjectId);
}
