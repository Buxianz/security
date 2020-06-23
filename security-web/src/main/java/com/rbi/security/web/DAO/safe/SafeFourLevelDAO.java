package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.entity.SafeFourLevel;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface SafeFourLevelDAO {
    /**
     * 添加
     */
    @Insert("insert into safe_four_level (id_card_no,organization_name,company_education_time,company_fraction,factory_education_time,factory_fraction," +
            "workshop_education_time,workshop_fraction,class_education_time,class_fraction,operating_staff,idt,udt) values (#{idCardNo},#{organizationName}," +
            "#{companyEducationTime},#{companyFraction},#{factoryEducationTime},#{factoryFraction},#{workshopEducationTime},#{workshopFraction},#{classEducationTime}," +
            "#{classFraction},#{operatingStaff},#{idt},#{udt})")
    int insertSafeFourLevel(SafeFourLevel safeFourLevel);

    /**
     * 根据id获取
     */
    @Select("select sys_company_personnel.work_type,sys_company_personnel.`name`,sys_company_personnel.job_nature,sys_company_personnel.gender," +
            "sys_company_personnel.entry_time,sys_company_personnel.date_of_birth,safe_four_level.* from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and safe_four_level.id=#{Id}")
    PagingSafeFourLevel getSafeFourLevelById(@Param("Id") Integer Id);

    /**
     * 根据姓名获取
     */
    @Select("select sys_company_personnel.work_type,sys_company_personnel.`name`,sys_company_personnel.job_nature,sys_company_personnel.gender," +
            "sys_company_personnel.entry_time,sys_company_personnel.date_of_birth,safe_four_level.* from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and sys_company_personnel.name=#{name} limit #{pageNo},#{pageSize}")
    List<PagingSafeFourLevel> findSafeFourLevelByName(@Param("name") String name,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(safe_four_level.id) from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and sys_company_personnel.name=#{name}")
    int getCountSafeFourLevelByName(@Param("name") String name);

    /**
     * 根据工种获取
     */
    @Select("select sys_company_personnel.work_type,sys_company_personnel.`name`,sys_company_personnel.job_nature,sys_company_personnel.gender," +
            "sys_company_personnel.entry_time,sys_company_personnel.date_of_birth,safe_four_level.* from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and sys_company_personnel.work_type=#{workType} limit #{pageNo},#{pageSize}")
    List<PagingSafeFourLevel> findSafeFourLevelByWorkType(@Param("workType") String workType,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(safe_four_level.id) from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and sys_company_personnel.work_type=#{workType}")
    int getCountSafeFourLevelByWorkType(@Param("workType") String workType);

    /**
     * 获取全部
     */
    @Select("select sys_company_personnel.work_type,sys_company_personnel.`name`,sys_company_personnel.job_nature,sys_company_personnel.gender," +
            "sys_company_personnel.entry_time,sys_company_personnel.date_of_birth,safe_four_level.* from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no limit #{pageNo},#{pageSize}")
    List<PagingSafeFourLevel> getSafeFourLevelByPage(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
    @Select("select count(id) from safe_four_level")
    int getCountSafeFourLevel();

    /**
     * 根据当前登录人获取
     */
    @Select("select sys_company_personnel.work_type,sys_company_personnel.`name`,sys_company_personnel.job_nature,sys_company_personnel.gender," +
            "sys_company_personnel.entry_time,sys_company_personnel.date_of_birth,safe_four_level.* from safe_four_level,sys_company_personnel " +
            "where safe_four_level.id_card_no=sys_company_personnel.id_card_no and safe_four_level.operating_staff=#{personnelId}")
    PagingSafeFourLevel findSafeFourLevelByOperatingStaff(@Param("personnelId") int personnelId);

    /**
     * 更新
     */
    @Update("update safe_four_level set id_card_no=#{idCardNo},organization_name=#{organizationName},company_education_time=#{companyEducationTime}," +
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