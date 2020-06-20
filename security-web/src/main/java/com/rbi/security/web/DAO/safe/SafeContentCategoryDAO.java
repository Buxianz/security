package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeContentCategoryServiceDAO
 * @USER: "谢青"
 * @DATE: 2020/6/17
 * @TIME: 14:28
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 17
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 14
 * @MINUTE: 28
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeContentCategoryDAO {

    @Select("select * from safe_content_category limit #{pageNo},#{pageSize}")
    List<SafeContentCategory> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from safe_content_category")
    int findByPageNum();

    @Insert("insert into safe_content_category (content_category_name,operating_staff,idt) values (#{contentCategoryName},#{operatingStaff},#{idt})")
    void add(SafeContentCategory safeContentCategory);

    @Update("update safe_content_category set content_category_name = #{contentCategoryName},udt = #{udt} where id = #{id}")
    void update(SafeContentCategory safeContentCategory);

    @Delete("delete from safe_content_category where id = #{id}")
    void deleteById(Integer id);

    @Select("select count(*) from safe_training_materials where content_category_id = #{id}")
    int findUseNum(Integer id);

    @Select("select * from safe_content_category where content_category_name like ${name} limit #{pageNo},#{pageSize}")
    List<SafeContentCategory> findByContentCategoryName(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("name") String name);

    @Select("select count(*) from safe_content_category where content_category_name like ${name}")
    int findByContentCategoryNameNum(@Param("name") String name);

}
