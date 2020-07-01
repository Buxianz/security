package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.importlog.ImportSpecialTrainingLOg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO
 * @NAME: LogSpecialTrainingDAO
 * @USER: "吴松达"
 * @DATE: 2020/7/1
 * @TIME: 10:49
 * @YEAR: 2020
 * @MONTH: 07
 * @MONTH_NAME_SHORT: 7月
 * @MONTH_NAME_FULL: 七月
 * @DAY: 01
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 10
 * @MINUTE: 49
 * @PROJECT_NAME: security
 **/
@Mapper
public interface LogSpecialTrainingDAO {
    @Insert({
            "<script>",
            "insert into log__special_training (code,id_card_no,result,reason,idt) values ",
            "<foreach collection='importSpecialTrainingLOgs' item='item' index='index' separator=','>",
            "(#{item.code},#{item.idCardNo},#{item.result},#{item.reason},#{item.idt})",
            "</foreach>",
            "</script>"
    })
    int adds(@Param("importSpecialTrainingLOgs") List<ImportSpecialTrainingLOg> importSpecialTrainingLOgs);
}
