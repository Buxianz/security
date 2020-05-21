package com.rbi.security.web.DAO.system;

import com.rbi.security.entity.web.system.PagingSystemInfo;
import com.rbi.security.entity.web.system.SystemFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 创建人：吴松达
 * 操作制度文件表
 */
@Mapper
public interface SystemFileDAO {
    /**
     * 根据id删除文件
     */
    @Delete("delete sf.* from system_file sf where sf.id=#{id}")
    int deleteSystemFileById(@Param("id") int id);
    /**
     * 根据id查询文件信息
     */
    @Select("select sf.* from system_file sf where sf.id=#{id}")
    SystemFile getSystemFileById(@Param("id") int id);
    /**
     * 添加文件信息
     */
    @Insert("insert into system_file (system_category_id,file_name,file_path,operating_staff,idt)" +
            " values (#{systemCategoryId},#{fileName},#{filePath},#{operatingStaff},#{idt})")
    int insertSystemFile(SystemFile systemFile);

    /**
     * 获取记录条数
     */
    @Select("select count(id) from system_file")
    int getSystemFileCount();

    /**
     * 分页获取制度文件
     */
     @Select("SELECT sf.id,sf.system_category_id,sc.category_name,sf.file_name,sf.file_path from system_file sf INNER JOIN system_category sc ON sf.system_category_id=sc.id  LIMIT #{startIndex},#{pageSize}")
    List<PagingSystemInfo>  getPagingSystemInfo(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
