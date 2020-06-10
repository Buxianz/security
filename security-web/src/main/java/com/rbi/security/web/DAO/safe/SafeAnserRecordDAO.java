package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.examination.SafeAnswerRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafeAnserRecord
 * @USER: "吴松达"
 * @DATE: 2020/6/10
 * @TIME: 9:58
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 10
 * @DAY_NAME_SHORT: 周三
 * @DAY_NAME_FULL: 星期三
 * @HOUR: 09
 * @MINUTE: 58
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafeAnserRecordDAO {
    @Insert({
            "<script>",
            "insert into safe_answer_record (personnel_training_record_id,answer_results,right_key,correct,test_uestions_id,test_papre_id) values ",
            "<foreach collection='safeAnswerRecordList' item='item' index='index' separator=','>",
            "(#{item.personnelTrainingRecordId},#{item.answerResults},#{item.rightKey},#{item.correct},#{item.testUestionsId},#{item.testPapreId})",
            "</foreach>",
            "</script>"
    })
    int insertAnserRecords(@Param("safeAnswerRecordList") List<SafeAnswerRecord> safeAnswerRecordList);
}
