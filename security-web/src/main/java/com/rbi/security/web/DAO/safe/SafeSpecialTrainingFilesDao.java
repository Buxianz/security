package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.specialtype.PagingSpecialTraining;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialTrainingFiles;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * (SafeSpecialTrainingFiles)表数据库访问层
 *
 * @author 吴松达
 * @since 2020-05-26 10:17:08
 */
@Mapper
public interface SafeSpecialTrainingFilesDao {
    /**
     * 获去没有在复审中的特种人员信息
     */
    @Select("SELECT sstf.* FROM safe_special_training_files sstf LEFT JOIN \n" +
            "(SELECT special_personnel_id FROM safe_special_review where completion_status=1) ssr ON sstf.id=ssr.special_personnel_id where ssr.special_personnel_id IS NULL")
    List<SafeSpecialTrainingFiles> getAllSpecialTraining();
    /**
     * 通过idCardNo查询单条数据
     *
     * @param idCardNo 身份证号码
     * @return 实例对象
     */
    @Select("select * from safe_special_training_files where id_card_no=#{idCardNo}")
    SafeSpecialTrainingFiles queryByIdCardNo(@Param("idCardNo") String idCardNo);
    /**
     * 获取当前记录数量
     */
    @Select("select count(*) from safe_special_training_files")
    int  getRecordCount();
    /**
     * 查询指定行数据
     *
     * @param startIndex 查询起始位置
     * @param pageSize 查询条数
     * @return 对象列表
     */
    @Select("SELECT sptf.*,scp.`name`,scp.gender,scp.degree_of_education FROM (select * from safe_special_training_files LIMIT ORDER BY idt #{startIndex},#{pageSize}) sptf LEFT JOIN  sys_company_personnel scp ON scp.id=sptf.company_personnel_id")
    List<PagingSpecialTraining> queryAllByLimit(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 根据id获取数据
     */
    @Select("SELECT sptf.*,scp.`name`,scp.gender,scp.degree_of_education FROM (select * from safe_special_training_files WHERE id=#{id}) sptf LEFT JOIN  sys_company_personnel scp ON scp.id=sptf.company_personnel_id")
    PagingSpecialTraining getSpecialTrainingById(@Param("id") int id);
    /**
     * 新增数据
     *
     * @param safeSpecialTrainingFiles 实例对象
     * @return 影响行数
     */
    @Insert("insert into safe_special_training_files (validity_period,id_card_no,company_personnel_id,type_of_work,operation_items,working_years,theoretical_achievements,actual_results," +
            "operation_certificate_no,date_of_issue,years_of_work,one_review_results,one_review_time,tow_review_results,tow_review_time,three_review_results," +
            "three_review_time,four_review_results,four_review_time,five_review_results,five_review_time,six_review_results,six_review_time," +
            "remarks,operating_staff,idt) values (#{validityPeriod},#{idCardNo},#{companyPersonnelId},#{typeOfWork},#{operationItems},#{workingYears},#{theoreticalAchievements},#{actualResults}," +
            "#{operationCertificateNo},#{dateOfIssue},#{yearsOfWork},#{oneReviewResults},#{oneReviewTime},#{towReviewResults},#{towReviewTime},#{threeReviewResults}," +
            "#{threeReviewTime},#{fourReviewResults},#{fourReviewTime},#{fiveReviewResults},#{fiveReviewTime},#{sixReviewResults},#{sixReviewTime}," +
            "#{remarks},#{operatingStaff},#{idt})")
    int insert(SafeSpecialTrainingFiles safeSpecialTrainingFiles);
    /**
     * 批量添加数据
     */
    @Insert({
            "<script>",
            "insert into safe_special_training_files (validity_period,id_card_no,company_personnel_id,type_of_work,operation_items,working_years,theoretical_achievements,actual_results," +
                    "operation_certificate_no,date_of_issue,years_of_work,one_review_results,one_review_time,tow_review_results,tow_review_time,three_review_results," +
                    "three_review_time,four_review_results,four_review_time,five_review_results,five_review_time,six_review_results,six_review_time," +
                    "remarks,operating_staff,idt) values <foreach collection='safes' item='item' index='index' separator=','>" +
                    "(#{item.validityPeriod},#{item.idCardNo},#{item.companyPersonnelId},#{item.typeOfWork},#{item.operationItems},#{item.workingYears},#{item.theoreticalAchievements},#{item.actualResults}," +
                      "#{item.operationCertificateNo},#{item.dateOfIssue},#{item.yearsOfWork},#{item.oneReviewResults},#{item.oneReviewTime},#{item.towReviewResults},#{item.towReviewTime},#{item.threeReviewResults}," +
                      "#{item.threeReviewTime},#{item.fourReviewResults},#{item.fourReviewTime},#{item.fiveReviewResults},#{item.fiveReviewTime},#{item.sixReviewResults},#{item.sixReviewTime}," +
                      "#{item.remarks},#{item.operatingStaff},#{item.idt})" ,
            "</foreach>",
            "</script>"
    })
    int inserts(@Param("safes") List<SafeSpecialTrainingFiles> safes);
    /**
     * 修改数据
     *
     * @param safeSpecialTrainingFiles 实例对象
     * @return 影响行数
     */
    @Update("update safe_special_training_files set validity_period=#{validityPeriod},type_of_work=#{typeOfWork},operation_items=#{operationItems},working_years=#{workingYears},theoretical_achievements=#{theoreticalAchievements},actual_results=#{actualResults}," +
            "operation_certificate_no=#{operationCertificateNo},date_of_issue=#{dateOfIssue},years_of_work=#{yearsOfWork},one_review_results=#{oneReviewResults},one_review_time=#{oneReviewTime},tow_review_results=#{towReviewResults},tow_review_time=#{towReviewTime},three_review_results=#{threeReviewResults}," +
            "three_review_time=#{threeReviewTime},four_review_results=#{fourReviewResults},four_review_time=#{fourReviewTime},five_review_results=#{fiveReviewResults},five_review_time=#{fiveReviewTime},six_review_results=#{sixReviewResults},six_review_time=#{sixReviewTime}," +
            "remarks=#{remarks} where id=#{id}")
    int update(SafeSpecialTrainingFiles safeSpecialTrainingFiles);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete ssptf.* from safe_special_training_files ssptf where ssptf.id=#{id}")
    int deleteById(Integer id);

    @Select("select id_card_no from sys_company_personnel where id = #{id}")
    String findIdCardNo(int id);

    @Select("select * from safe_special_training_files,sys_company_personnel where safe_special_training_files.id_card_no = sys_company_personnel.id_card_no and " +
            "safe_special_training_files.id_card_no = #{idCardNo}")
    PagingSpecialTraining findAllByIdCardNo(String idCardNo);
}