package com.rbi.security.web.DAO.system;

import com.rbi.security.entity.web.system.SystemFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 创建人：吴松达
 * 操作制度文件表
 */
@Mapper
public interface SystemFileDAO {
    /**
     * 添加文件信息
     */
    @Insert("insert into system_file (system_category_id,file_name,file_path,operating_staff,idt)" +
            " values (#{systemCategoryId},#{fileName},#{filePath},#{operatingStaff},#{idt})")
    int insertSystemFile(SystemFile systemFile);
}
