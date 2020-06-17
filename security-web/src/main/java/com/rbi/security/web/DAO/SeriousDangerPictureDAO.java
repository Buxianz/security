package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.entity.SeriousDangerPicture;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeriousDangerPictureDAO {
    /**
     * 根据seriousDangerId分页获取
     */
    @Select("select * from serious_danger_picture where serious_danger_id=#{seriousDangerId}")
    List<SeriousDangerPicture> findSeriousDangerPictureByPageAndSeriousDangerId(@Param("seriousDangerId") Integer seriousDangerId);

    /**
     * 添加
     */
    @Insert( "insert into serious_danger_picture (serious_danger_id,serious_danger_picture_path) value(#{seriousDangerId}," +
            "#{seriousDangerPicturePath})")
    void insertSeriousDangerPicture(SeriousDangerPicture seriousDangerPicture);

    /**
     * 根据id修改
     */
    @Update(value = "delete from serious_danger_picture where serious_danger_id=#{seriousDangerId}")
    void deleteSeriousDangerPicture(@Param("seriousDangerId") Integer seriousDangerId);
}
