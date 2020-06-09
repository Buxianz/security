package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReview;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorReviewDTO;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeAdministratorReviewDAO
 * @USER: "谢青"
 * @DATE: 2020/6/8
 * @TIME: 16:27
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 08
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 16
 * @MINUTE: 27
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeAdministratorReviewDAO {

    @Select("select * from safe_administrator_review,safe_administrator_train,sys_company_personnel where " +
            "safe_administrator_review.safe_administrator_id = safe_administrator_train.id and " +
            "safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "order by name,safe_administrator_review.idt desc limit #{pageNo},#{pageSize}")
    List<SafeAdministratorReviewDTO> findByPage(int pageNo, int pageSize);


    @Select("select count(*) from safe_administrator_review,safe_administrator_train,sys_company_personnel where " +
            "safe_administrator_review.safe_administrator_id = safe_administrator_train.id and " +
            "safe_administrator_train.company_personnel_id = sys_company_personnel.id")
    int findByPageNum();






    @Select("select * from safe_administrator_train")
    List<SafeAdministratorTrain> findAll();

    @Select("select * from safe_administrator_review where safe_administrator_id = #{safeAdministratorId}")
    List<SafeAdministratorReview> findReviewBySafeAdministratorId(Integer safeAdministratorId);

    @Insert("insert into safe_administrator_review (safe_administrator_id,completion_status,operating_staff,idt) values " +
            "(#{safeAdministratorId},#{completionStatus},#{operatingStaff},#{idt})")
    void addReview(SafeAdministratorReview safeAdministratorReview);

    @Update("update safe_administrator_review set operating_staff = #{operating_staff},processing_time = #{processing_time}," +
            "reason_for_handling = #{reason_for_handling},completion_status = #{completion_status} where " +
            "id = #{id}")
    void review(SafeAdministratorReviewDTO safeAdministratorReviewDTO);

    @Update("update safe_administrator_review set operating_staff = #{operatingStaff},processing_time = #{processingTime}," +
            "reason_for_handling = #{reasonForHandling},completion_status = #{completionStatus} where " +
            "id = #{id}")
    void updateReview(SafeAdministratorReviewDTO safeAdministratorReviewDTO);

    @Update("update safe_administrator_train set one_training_time = #{oneTrainingTime},two_training_time = #{twoTrainingTime}," +
            "three_training_time = #{threeTrainingTime} where id = #{safeAdministratorId}")
    void updateFile(SafeAdministratorReviewDTO safeAdministratorReviewDTO);
}
