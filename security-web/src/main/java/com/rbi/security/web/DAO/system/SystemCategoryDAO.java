package com.rbi.security.web.DAO.system;

import com.rbi.security.entity.web.system.SystemCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.annotation.ManagedBean;
import java.util.List;

/**
 * 创建人:吴松达
 * 操作制度类别表
 */
@Mapper
public interface SystemCategoryDAO {
    @Select("select * from system_category where id=#{id}")
    SystemCategory getSystemCategoryById(@Param("id") int id);

    @Select("select * from system_category")
    List<SystemCategory> getAllSystemCategory();
}
