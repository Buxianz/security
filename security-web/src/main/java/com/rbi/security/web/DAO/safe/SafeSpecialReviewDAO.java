package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.entity.SysRolePermission;
import com.rbi.security.entity.web.safe.specialtype.ExportReview;
import com.rbi.security.entity.web.safe.specialtype.PagingSpecialReview;
import com.rbi.security.entity.web.safe.specialtype.SafeSpecialReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 *
 *
 * @author 吴松达
 * @since 2020-05-27 14:50:08
 */
@Mapper
public interface SafeSpecialReviewDAO {
    /**
     * 批量添加任务
     */
    @Insert({
            "<script>",
            "insert into safe_special_review (special_personnel_id,deadline,completion_status,reason_for_handling,operating_staff,processing_time,idt) values ",
            "<foreach collection='safeSpecialReviewList' item='item' index='index' separator=','>",
            "(#{item.specialPersonnelId},#{item.deadline},#{item.completionStatus},#{item.reasonForHandling},#{item.operatingStaff},#{item.processingTime},#{item.idt})",
            "</foreach>",
            "</script>"
    })
    int insertSpecialReviews(@Param("safeSpecialReviewList") List<SafeSpecialReview> safeSpecialReviewList);

    /**
     * 处理特种复审任务
     */
    @Update("update safe_special_review set operating_staff=#{operatingStaff},processing_time=#{processingTime},completion_status=#{completionStatus},reason_for_handling=#{reasonForHandling} where id=#{id}")
    int updateSpecialReview(SafeSpecialReview safeSpecialReview);

    /**
     * 分页获取特种复审信息
     */
    @Select("SELECT ssr.id,ssr.special_personnel_id,scp.`name`,scp.gender,scp.degree_of_education,sstf.operation_items,sstf.working_years,sstf.operation_certificate_no,scp.id_card_no,\n" +
            "ssr.deadline,ssr.completion_status,ssr.reason_for_handling,ssr.processing_time,ssr.idt,sstf.date_of_issue,sstf.one_review_time,sstf.tow_review_time,sstf.three_review_time,sstf.four_review_time,sstf.five_review_time,sstf.six_review_time\n" +
            "FROM (SELECT * FROM safe_special_review ORDER BY completion_status LIMIT #{startIndex},#{pageSize}) ssr LEFT JOIN \n" +
            "safe_special_training_files sstf ON ssr.special_personnel_id=sstf.id  LEFT JOIN sys_company_personnel scp ON sstf.company_personnel_id=scp.id ORDER BY ssr.completion_status")
    List<PagingSpecialReview> pagingSpecialReview(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
    /**
     * 获取记录数
     */
    @Select("select count(id) from safe_special_review")
    int getSpecialReviewCount();

    /**
     * 获取所有未复审的名单
     */
    @Select("SELECT @n:=@n+1 \"no\",sstf.* FROM\n" +
            "(SELECT scp.`name`,scp.gender,scp.degree_of_education,sstf.id_card_no,sstf.type_of_work,sstf.operation_items,sstf.years_of_work,sstf.operation_certificate_no,sstf.remarks FROM\n" +
            "(SELECT sstf.company_personnel_id,sstf.id_card_no,sstf.type_of_work,sstf.operation_items,sstf.years_of_work,sstf.operation_certificate_no,sstf.remarks\n" +
            "from (SELECT special_personnel_id FROM safe_special_review where completion_status=#{completionStatus}) ssr LEFT JOIN safe_special_training_files sstf on sstf.id=ssr.special_personnel_id) sstf LEFT JOIN sys_company_personnel scp on scp.id=sstf.company_personnel_id) sstf,(select @n:= 0) d")
    List<ExportReview> getAllReviewByStatus(@Param("completionStatus") int completionStatus);
}
