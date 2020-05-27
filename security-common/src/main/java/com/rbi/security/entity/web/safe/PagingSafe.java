package com.rbi.security.entity.web.safe;

import com.rbi.security.entity.web.entity.SafeSubject;
import com.rbi.security.entity.web.entity.SafeSubjectOption;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.rbi.security.entity.web.safe
 * @NAME: PagingSafe
 * @USER: "林新元"
 * @DATE: 2020/5/25
 * @TIME: 18:00
 * @YEAR: 2020
 * @MONTH: 05
 * @MONTH_NAME_SHORT: 五月
 * @MONTH_NAME_FULL: 五月
 * @DAY: 25
 * @DAY_NAME_SHORT: 星期一
 * @DAY_NAME_FULL: 星期一
 * @HOUR: 18
 * @MINUTE: 00
 * @PROJECT_NAME: security
 **/
@Data
public class PagingSafe {

//    /**
//     * 题目id
//     */
//    private Integer id;
//    /**
//     * 题目类型
//     */
//    private Integer subjectType;
//    /**
//     * 题目
//     */
//    private String subject;
//    /**
//     * 正确答案
//     */
//    private String rightKey;
    SafeSubject safeSubject;

    List<SafeSubjectOption> safeSubjectOptionList;
}
