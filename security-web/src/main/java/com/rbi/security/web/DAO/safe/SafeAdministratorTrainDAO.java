package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.entity.SysCompanyPersonnel;
import com.rbi.security.entity.web.importlog.LogAdministratorTrain;
import com.rbi.security.entity.web.safe.PagingSafeFourLevel;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrainDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.service.DAO.safe
 * @NAME: SafeAdministratorTrainDAO 操作主要负责人，安全生产管理人员培训台账表（safe_administrator_train）
 * @USER: "吴松达"
 * @DATE: 2020/5/25
 * @TIME: 14:53
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 5月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 14
 * @MINUTE: 53
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeAdministratorTrainDAO {


    @Select("select * from sys_company_personnel where id_card_no = #{idCardNo}")
    SysCompanyPersonnel findPersonnelByIdCardNo(@Param("idCardNo") String idCardNo);

    @Select("select count(*) from sys_company_personnel where id_card_no = #{idCardNo}")
    int findPersonnelByIdCardNum(@Param("idCardNo") String idCardNo);

    @Select("select count(*) from safe_administrator_train where id_card_no = #{idCardNo}")
    int findIdCardNoNum(@Param("idCardNo") String idCardNo);

    @Insert("insert into safe_administrator_train (id_card_no,company_personnel_id,unit,date_of_issue,term_of_validity,type_of_certificate," +
            "one_training_time,two_training_time,three_training_time,remarks,operating_staff,idt) values (" +
            "#{idCardNo},#{companyPersonnelId},#{unit},#{dateOfIssue},#{termOfValidity},#{typeOfCertificate},#{oneTrainingTime}," +
            "#{twoTrainingTime},#{threeTrainingTime},#{remarks},#{operatingStaff},#{idt})")
    void add(SafeAdministratorTrain safeAdministratorTrain);

    @Delete("delete from safe_administrator_train where id = #{id}")
    void deleteById(Integer id);

    @Update("update safe_administrator_train set unit = #{unit},date_of_issue = #{dateOfIssue},term_of_validity = #{termOfValidity}," +
            "type_of_certificate = #{typeOfCertificate},one_training_time = #{oneTrainingTime},two_training_time = #{twoTrainingTime}," +
            "three_training_time = #{threeTrainingTime},remarks = #{remarks},udt = #{udt} where" +
            " id  = #{id}")
    void update(SafeAdministratorTrain safeAdministratorTrain);

    @Update("update safe_administrator_train set unit = #{unit},date_of_issue = #{dateOfIssue},term_of_validity = #{termOfValidity}," +
            "type_of_certificate = #{typeOfCertificate},one_training_time = #{oneTrainingTime},two_training_time = #{twoTrainingTime}," +
            "three_training_time = #{threeTrainingTime},remarks = #{remarks},udt = #{udt} where " +
            "id_card_no  = #{idCardNo}")
    void updateByIdNum(SafeAdministratorTrain safeAdministratorTrain);

    @Select("select * from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id and " +
            "(sys_company_personnel.organization_id in (select id from (\n" +
            "select t1.id,\n" +
            "if(find_in_set(parent_id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "from (\n" +
            "select id,parent_id from sys_organization t order by parent_id, id\n" +
            ") t1,\n" +
            "(select @pids := #{organizationId}) t2\n" +
            ") t3 where ischild != 0) or sys_company_personnel.organization_id = #{organizationId}) " +
            "order by safe_administrator_train.id DESC limit #{pageNo},#{pageSize}")
    List<SafeAdministratorTrainDTO> findByPage(@Param("organizationId")Integer organizationId,@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

    @Select("select count(*) from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id and " +
            "(sys_company_personnel.organization_id in (select id from (\n" +
            "select t1.id,\n" +
            "if(find_in_set(parent_id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n" +
            "from (\n" +
            "select id,parent_id from sys_organization t order by parent_id, id\n" +
            ") t1,\n" +
            "(select @pids := #{organizationId}) t2\n" +
            ") t3 where ischild != 0) or sys_company_personnel.organization_id = #{organizationId})")
    int findByPageNum(@Param("organizationId")Integer organizationId);

    @Select("select * from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and name like ${value2} order by safe_administrator_train.id DESC limit #{pageNo},#{pageSize}")
    List<SafeAdministratorTrainDTO> findByName(@Param("value2") String value2,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and name like ${value2}")
    int findByNameNum(@Param("value2") String value2);


    @Select("select * from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and safe_administrator_train.id_card_no like ${value2} order by safe_administrator_train.id DESC limit #{pageNo},#{pageSize}")
    List<SafeAdministratorTrainDTO> findByidCardNo(@Param("value2") String value2,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and safe_administrator_train.id_card_no like ${value2}")
    int findByidCardNoNum(@Param("value2") String value2);


    @Select("select * from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and safe_administrator_train.unit like ${value2} order by safe_administrator_train.id DESC limit #{pageNo},#{pageSize}")
    List<SafeAdministratorTrainDTO> findByUnit(@Param("value2") String value2,@Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    @Select("select count(*) from safe_administrator_train,sys_company_personnel where safe_administrator_train.company_personnel_id = sys_company_personnel.id " +
            "and safe_administrator_train.unit like ${value2}")
    int findByUnitNum(@Param("value2") String value2);
    /**
     * 批量添加管理人员信息 吴松达
     */
    @Insert({
            "<script>"+
            "insert into safe_administrator_train (id_card_no,company_personnel_id,unit,date_of_issue,term_of_validity,type_of_certificate,one_training_time,two_training_time,three_training_time,remarks,operating_staff,idt) values " +
                    "<foreach collection='safeAdministratorTrains' item='item' index='index' separator=','> " +
                    "(#{item.idCardNo},#{item.companyPersonnelId},#{item.unit},#{item.dateOfIssue},#{item.termOfValidity}," +
                    "#{item.typeOfCertificate},#{item.oneTrainingTime},#{item.twoTrainingTime},#{item.threeTrainingTime},#{item.remarks},#{item.operatingStaff},#{item.idt})" ,
            "</foreach>"+
            "</script>"
    })
    int adds(@Param("safeAdministratorTrains") List<SafeAdministratorTrain> safeAdministratorTrains);

    @Insert("insert into log_administrator_train (code,result,reason,idt,id_num)values(#{code},#{result},#{reason},#{idt},#{idNum})")
    void addLogAdministratorTrain(LogAdministratorTrain logAdministratorTrain);

    @Select("select * from safe_administrator_train,sys_company_personnel where safe_administrator_train.id_card_no = sys_company_personnel.id_card_no")
    List<SafeAdministratorTrainDTO> findAll();

    @Select("select * from sys_company_personnel where id = #{id}")
    SysCompanyPersonnel findPersonnelById(Integer personnelId);
}
