package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.hid.HidDangerDO;
import com.rbi.security.entity.web.safe.content.SafeContentCategory;
import com.rbi.security.entity.web.safe.content.SafeTrainingMaterials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SafeTrainingMaterialsDAO {

    @Insert("insert into safe_training_materials (resource_name,content_category_id,resource_type,resource_path,idt,udt,operating_staff,operator_name) values " +
            "(#{resourceName},#{contentCategoryId},#{resourceType},#{resourcePath},#{idt},#{udt},#{operatingStaff},#{operatorName})")
    void add(SafeTrainingMaterials safeTrainingMaterials);

    @Delete("delete from safe_training_materials where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id limit #{pageNo},#{pageSize}")
    List<SafeTrainingMaterials> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id")
    int findByPageNum();

    @Select("select count(*) from safe_training_materials where resource_path = #{resourcePath}")
    int countByResourcePath(String resourcePath);

    @Select("select * from safe_content_category")
    List<SafeContentCategory> findType();

    @Select("select * from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id " +
            "and content_category_id = #{contentCategoryId} limit #{pageNo},#{pageSize}")
    List<SafeTrainingMaterials> findByCondition(Integer pageNo, Integer pageSize,Integer contentCategoryId);

    @Select("select count(*) from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id and " +
            "content_category_id = #{contentCategoryId}")
    int findByConditionNum(Integer contentCategoryId);

    @Select("select * from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id and resource_name like ${resourceName} limit #{pageNo},#{pageSize}")
    List<SafeTrainingMaterials> findByName(@Param("pageNo") int pageNo,@Param("pageSize") int pageSize,@Param("resourceName") String resourceName);

    @Select("select count(*) from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id and resource_name like ${resourceName}")
    int findByNameNum(@Param("resourceName") String resourceName);


    @Select("select * from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id " +
            "and content_category_id = #{contentCategoryId} and resource_type = '文件' limit #{pageNo},#{pageSize}")
    List<SafeTrainingMaterials> findFileByCategory(int pageNo, int pageSize, int contentCategoryId);

    @Select("select count(*) from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id and " +
            "content_category_id = #{contentCategoryId} and resource_type = '文件'")
    int findFileByCategoryNum(int contentCategoryId);

    @Select("select * from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id " +
            "and content_category_id = #{contentCategoryId} and resource_type = '视频' limit #{pageNo},#{pageSize}")
    List<SafeTrainingMaterials> findVideoByCategory(int pageNo, int pageSize, int contentCategoryId);

    @Select("select count(*) from safe_training_materials,safe_content_category where safe_training_materials.content_category_id = safe_content_category.id and " +
            "content_category_id = #{contentCategoryId} and resource_type = '视频'")
    int findVideoByCategoryNum(int contentCategoryId);
}
