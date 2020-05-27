package com.rbi.security.entity.web.safe;

import lombok.Data;

/**
 * @ClassName SafeSubjectStore
 * @Description 题目对应的题库分类，例如某题属于哪个一个题库
 * @Author 杨赞格
 * @Date 2020/5/26 17:00
 * @Version 1.0
 **/
@Data
public class SafeSubjectStore {

    /**
     * 题库id
    */
    private Integer id ;

    /**
     * 题库名称
     */
    private String subjectStoreName;


}
