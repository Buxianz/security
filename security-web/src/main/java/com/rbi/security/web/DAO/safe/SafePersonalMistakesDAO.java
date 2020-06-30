package com.rbi.security.web.DAO.safe;

import com.rbi.security.entity.web.safe.PagePersonalMistakes;
import com.rbi.security.entity.web.safe.SafePersonalMistakes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.web.DAO.safe
 * @NAME: SafePersonalMistakesDAO
 * @USER: "吴松达"
 * @DATE: 2020/6/30
 * @TIME: 9:30
 * @YEAR: 2020
 * @MONTH: 06
 * @MONTH_NAME_SHORT: 6月
 * @MONTH_NAME_FULL: 六月
 * @DAY: 30
 * @DAY_NAME_SHORT: 周二
 * @DAY_NAME_FULL: 星期二
 * @HOUR: 09
 * @MINUTE: 30
 * @PROJECT_NAME: security
 **/
@Mapper
public interface SafePersonalMistakesDAO {
    /**
     *
     * @param
     * @return
     */
    @Select("select * from safe_personal_mistakes where company_personnel_id=#{companyPersonnelId} and subject_id=#{subjectId}")
    SafePersonalMistakes getSafePersonalMistakes(@Param("companyPersonnelId")int companyPersonnelId,@Param("subjectId")int subjectId);
    @Insert("insert into safe_personal_mistakes (company_personnel_id,subject_id,idt) values #{companyPersonnelId},#{subjectId},#{idt}")
    int add(SafePersonalMistakes safePersonalMistakes);

    @Insert({ "<script>",
            "insert into safe_personal_mistakes (company_personnel_id,subject_id,idt) values ",
            "<foreach collection='safePersonalMistakesList' item='item' index='index' separator=','>",
            "(#{item.companyPersonnelId},#{item.subjectId},#{item.idt})",
            "</foreach>",
            "</script>"})
    int adds(List<SafePersonalMistakes> safePersonalMistakesList);

    /**
     * 分页获取个人错题信息
     */
    @Select("SELECT spm.id,spm.subject_id,stq.`subject`,stq.subject_type,stq.right_key,stq.score FROM (SELECT id,subject_id,idt FROM safe_personal_mistakes WHERE company_personnel_id=#{companyPersonnelId} ORDER BY idt  LIMIT #{startIndex},#{pageSize}) spm \n" +
            "LEFT JOIN safe_test_questions stq on stq.id=spm.subject_id")
    List<PagePersonalMistakes> getPagePersonalMistakes(@Param("companyPersonnelId")int companyPersonnelId, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
   @Select("select count(*) from safe_personal_mistakes WHERE company_personnel_id=#{companyPersonnelId}")
    int getPagePersonalMistakesCount(@Param("companyPersonnelId")int companyPersonnelId);
   /**
     * 批量删除
     */
    @Delete({"<script> Delete spm.* from safe_personal_mistakes spm where spm.id in\n"+
            "<foreach collection='ids' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach>\n"+
            "</script>"})
    int deletes(@Param("ids") List<Integer> ids);
}
