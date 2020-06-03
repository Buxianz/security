package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.entity.SafeFourLevel;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SafeFourLevelDAO {
    /**
     * 添加
     */
    @Insert("insert into safe_four_level (name,id_card_no,organization_name,gender,date_of_birth,entry_time,work_type," +
            "job_nature,company_education_time,company_fraction,factory_education_time,factory_fraction,workshop_education_time," +
            "workshop_fraction,class_education_time,class_fraction,operating_staff,idt,udt) values (#{name},#{idCardNo},#{organizationName}," +
            "#{gender},#{dateOfBirth},#{entryTime},#{workType},#{jobNature},#{companyEducationTime},#{companyFraction},#{factoryEducationTime}," +
            "#{factoryFraction},#{workshopEducationTime},#{workshopFraction},#{classEducationTime}," +
            "#{classFraction},#{operatingStaff},#{idt},#{udt})")
    int insertSafeFourLevel(SafeFourLevel safeFourLevel);

    /**
     * 根据id获取
     */
    @Select("select * from safe_four_level where id=#{Id}")
    SafeFourLevel getSafeFourLevelById(@Param("Id") Integer Id);

    /**
     * 根据姓名获取
     */
    @Select("select * from safe_four_level where name=#{name} limit #{pageNo},#{pageSize}")
    List<SafeFourLevel> findSafeFourLevelByName(@Param("name") String name,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from safe_four_level where name=#{name}")
    int getCountSafeFourLevelByName(@Param("name") String name);

    /**
     * 根据工种获取
     */
    @Select("select * from safe_four_level where work_type=#{workType} limit #{pageNo},#{pageSize}")
    List<SafeFourLevel> findSafeFourLevelByWorkType(@Param("workType") String workType,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from safe_four_level where work_type=#{workType}")
    int getCountSafeFourLevelByWorkType(@Param("workType") String workType);

    /**
     * 获取全部
     */
    @Select("select * from safe_four_level limit #{pageNo},#{pageSize}")
    List<SafeFourLevel> getSafeFourLevelByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from safe_four_level")
    int getCountSafeFourLevel();

    /**
     * 根据当前登录人获取全部
     */
    @Select("select * from safe_four_level where operating_staff=#{personnelId} limit #{pageNo},#{pageSize}")
    List<SafeFourLevel> findSafeFourLevelByOperatingStaff(@Param("personnelId") int personnelId,
                                                          @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from safe_four_level where operating_staff=#{personnelId}")
    int getCountSafeFourLevelByOperatingStaff(@Param("personnelId") int personnelId);

    /**
     * 更新
     */
    @Update("update safe_four_level set name=#{name},id_card_no=#{idCardNo},organization_name=#{organizationName},gender=#{gender},date_of_birth=#{dateOfBirth}," +
            "entry_time=#{entryTime},work_type=#{workType},job_nature=#{jobNature},company_education_time=#{companyEducationTime}," +
            "company_fraction=#{companyFraction},factory_education_time=#{factoryEducationTime},factory_fraction=#{factoryFraction}," +
            "workshop_education_time=#{workshopEducationTime},workshop_fraction=#{workshopFraction},class_education_time=#{classEducationTime}," +
            "class_fraction=#{classFraction},operating_staff=#{operatingStaff},idt=#{idt},udt=#{udt} where id=#{id}")
    int updateSafeFourLevelById(SafeFourLevel safeFourLevel);


    /**
     * 根据id删除
     */
    @Delete("delete from safe_four_level where id=#{id}")
    void  deleteSafeFourLevelById(@Param("id") Integer id);
}
