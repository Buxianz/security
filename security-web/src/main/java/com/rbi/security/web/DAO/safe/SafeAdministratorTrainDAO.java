package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.administrator.SafeAdministratorTrain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
    /**
     * 添加安全管理员台账
     * @param safeAdministratorTrain
     * @return
     */
   /*@Insert("insert into ")
    int insertAdministratorTrain(SafeAdministratorTrain safeAdministratorTrain);*/
}
