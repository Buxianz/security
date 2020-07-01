package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.demand.SafaTrainingType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafaTrainingTypeDAO
 * @USER: "吴松达"
 * @DATE: 2020/6/1
 * @TIME: 22:49
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 22
 * @MINUTE: 49
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafaTrainingTypeDAO {
    @Select("Select * from  safa_training_type")
    List<SafaTrainingType> getAllSafaTrainingType();
    @Select("Select * from  safa_training_type where id=#{id}")
    SafaTrainingType getSafaTrainingTypeById(@Param("id") int id);
}
