package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.PlatformSettings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: PlatformSettings
 * @USER: "吴松达"
 * @DATE: 2020/6/28
 * @TIME: 10:11
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 28
 * @DAY_NAME_SHORT: 周日
 * @DAY_NAME_FULL: 星期日
 * @HOUR: 10
 * @MINUTE: 11
 * @PROJECT_NAME: security
 **/
@Mapper
public interface PlatformSettingsDAO {
    @Select("select `values` from platform_settings where type=1")
    int getSpecialDay();

    @Update("update platform_settings set values=#{values} where id=#{id}")
    int updateValues(PlatformSettings platformSettings);

    @Select("Select * from platform_settings where type=1")
    PlatformSettings getSpecialDaySet();
}
